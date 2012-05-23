package emotionml;

import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import emotionml.exceptions.ConfigurationException;
import emotionml.exceptions.NoSuchVocabularyException;
import emotionml.exceptions.NotValidEmotionmlException;

public class Checker {
	private DocumentBuilderFactory factory;
	private Schema emotionmlSchema;
	private DocumentBuilder builder;

	public Checker() throws ConfigurationException {
		factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		try {
			emotionmlSchema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(Checker.class.getResource("emotionml.xsd"));
		} catch (SAXException e) {
			throw new ConfigurationException("Could not instantiate schema", e);
		}
		factory.setSchema(emotionmlSchema);
		try {
			builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new ErrorHandler());
		} catch (ParserConfigurationException e) {
			throw new ConfigurationException("Cannot instantiate parser", e);
		} 
	}

	public Document parse(InputStream emotionmlStream) throws NotValidEmotionmlException {
		Document doc = null;
		try {
			doc = builder.parse(emotionmlStream);
		} catch (Exception e) {
			throw new NotValidEmotionmlException("Cannot parse EmotionML", e);
		}
		validateStandaloneManually(doc);
		return doc;
	}
	
	public void validate(Document emotionml) throws NotValidEmotionmlException {
		try {
			emotionmlSchema.newValidator().validate(new DOMSource(emotionml));
		} catch (Exception e) {
			throw new NotValidEmotionmlException("Could not schema-validate source", e);
		}
		validateStandaloneManually(emotionml);
	}
	
	public void validateFragment(DocumentFragment emotionmlFragment) throws NotValidEmotionmlException {
		try {
			emotionmlSchema.newValidator().validate(new DOMSource(emotionmlFragment));
		} catch (Exception e) {
			throw new NotValidEmotionmlException("Could not schema-validate source", e);
		}
		validateFragmentManually(emotionmlFragment);
		
	}
	
	public void validateStandaloneManually(Document emotionmlDocument) throws NotValidEmotionmlException {
		validateRootElement(emotionmlDocument);
		Element emotionmlElement = emotionmlDocument.getDocumentElement();
		validateVocabularySets(emotionmlElement);
		validateManually(emotionmlElement);
	}

	public void validateFragmentManually(DocumentFragment emotionmlFragment) throws NotValidEmotionmlException {
		validateManually(emotionmlFragment);
	}
	
	private void validateManually(Node emotionmlFragment) throws NotValidEmotionmlException {
		NodeList kids = emotionmlFragment.getChildNodes();
		for (int i=0, len=kids.getLength(); i<len; i++) {
			Node n = kids.item(i);
			if (n.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element e = (Element) n;
			if ("emotion".equals(e.getTagName()) && EmotionML.namespaceURI.equals(e.getNamespaceURI())) {
				validateVocabularySets(e);
				validateVersion(e);
				validateDescriptors(e);
			}
		}
	}


	private void validateDescriptors(Element emotion) throws NotValidEmotionmlException {
		assert emotion.getLocalName().equals("emotion");
		NodeList children = emotion.getChildNodes();
		for (int i=0, len=children.getLength(); i<len; i++) {
			Node n = children.item(i);
			if (n.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element c = (Element) n;
			String childLocalName = c.getLocalName();
			if (EmotionML.descriptionTags.contains(childLocalName)) {
				validateDescription(c);
			}
		}
	}

	private void validateDescription(Element desc) throws NotValidEmotionmlException {
		String descType = desc.getLocalName();
		assert EmotionML.descriptionTags.contains(descType);
		String vocabularyAttribute = descType+"-set";
		Element emotion = (Element) desc.getParentNode();
		String declaredVocabularyUri = emotion.getAttribute(vocabularyAttribute);
		if ("".equals(declaredVocabularyUri)) {
			Element root = desc.getOwnerDocument().getDocumentElement();
			if (EmotionML.namespaceURI.equals(root.getNamespaceURI())
					&& "emotionml".equals(root.getLocalName())) {
				declaredVocabularyUri = root.getAttribute(vocabularyAttribute);
			}
		}
		if ("".equals(declaredVocabularyUri)) {
			throw new NotValidEmotionmlException("<"+descType+"> element used without declaring a "+descType+" vocabulary through the '"+vocabularyAttribute+"' attribute");
		}
		EmotionVocabulary declaredVocabulary;
		try {
			if (isLocallyDefined(declaredVocabularyUri)) {
				declaredVocabulary = EmotionVocabulary.get(desc.getOwnerDocument(), getIdFromLocalVocabularyUri(declaredVocabularyUri));
			} else {
				declaredVocabulary = EmotionVocabulary.get(declaredVocabularyUri);
			}
		} catch (NoSuchVocabularyException e) {
			throw new NotValidEmotionmlException("Cannot get vocabulary for uri '"+declaredVocabularyUri+"'", e);
		}
		String name = desc.getAttribute("name");
		if (!declaredVocabulary.getItems().contains(name)) {
			throw new NotValidEmotionmlException("The name '"+name+"' of element <"+descType+"> is not contained in the declared vocabulary '"+declaredVocabularyUri+"'");
		}
		boolean hasTraceChild = desc.getElementsByTagNameNS(EmotionML.namespaceURI, "trace").getLength() > 0;
		boolean hasValueAttribute = desc.hasAttribute("value");
		if (hasTraceChild && hasValueAttribute) {
			throw new NotValidEmotionmlException("Either a 'value' attribute or a <trace> child may be used, but not both");
		}
		if ("dimension".equals(descType) && !(hasTraceChild || hasValueAttribute)) {
			throw new NotValidEmotionmlException("<dimension> requires either a 'value' attribute or a <trace> child.");
		}
	}

	private void validateVersion(Element emotion) throws NotValidEmotionmlException {
		assert emotion.getLocalName().equals("emotion");
		if (!emotion.hasAttribute("version")) return;
		String version = emotion.getAttribute("version");
		if (!version.equals("1.0")) {
			throw new NotValidEmotionmlException("Version attribute of <emotion> should be '1.0' but is '"+version+"'");
		}
	}

	private void validateVocabularySets(Element element) throws NotValidEmotionmlException {
		assert element != null;
		for (String attName : EmotionML.vocabularyAttributeTypes.keySet()) {
			EmotionVocabulary.Type expectedType = EmotionML.vocabularyAttributeTypes.get(attName);
			if (element.hasAttribute(attName)) {
				String vocabularyUri = element.getAttribute(attName);
				EmotionVocabulary voc;
				try {
					if (isLocallyDefined(vocabularyUri)) {
						voc = EmotionVocabulary.get(element.getOwnerDocument(), getIdFromLocalVocabularyUri(vocabularyUri));
					} else {
						voc = EmotionVocabulary.get(vocabularyUri);
					}
				} catch (NoSuchVocabularyException e) {
					throw new NotValidEmotionmlException("Cannot get vocabulary definition from "+vocabularyUri, e);
				}
				if (voc.getType() != expectedType) {
					throw new NotValidEmotionmlException("The vocabulary referred to in '"+attName+"' should be of type '"+expectedType+"' but is of type '"+voc.getType()+"'");
				}
			}
		}
	}

	private String getIdFromLocalVocabularyUri(String vocabularyUri) {
		assert vocabularyUri.startsWith("#");
		return vocabularyUri.substring(1);
	}

	private boolean isLocallyDefined(String vocabularyUri) {
		return vocabularyUri.startsWith("#");
	}

	private void validateRootElement(Document emotionmlDocument) throws NotValidEmotionmlException {
		if (emotionmlDocument == null || emotionmlDocument.getDocumentElement() == null
				|| !"emotionml".equals(emotionmlDocument.getDocumentElement().getLocalName())) {
			throw new NotValidEmotionmlException("The root element of standalone EmotionML documents MUST be <emotionml>.");
		}
	}

	public Schema getEmotionmlSchema() {
		return emotionmlSchema;
	}
	
	private static class ErrorHandler implements org.xml.sax.ErrorHandler {

		public void error(SAXParseException exception) throws SAXException {
			throw exception;
		}

		public void fatalError(SAXParseException exception) throws SAXException {
			throw exception;
		}

		public void warning(SAXParseException exception) throws SAXException {
			throw exception;
		}
		
	}

}
