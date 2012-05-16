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
		validateVocabularySets(emotionmlDocument.getDocumentElement());
	}
	
	public void validateFragmentManually(DocumentFragment emotionmlFragment) throws NotValidEmotionmlException {
		NodeList kids = emotionmlFragment.getChildNodes();
		for (int i=0, len=kids.getLength(); i<len; i++) {
			Node n = kids.item(i);
			if (n.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element e = (Element) n;
			if ("emotion".equals(e.getTagName()) && EmotionML.namespaceURI.equals(e.getNamespaceURI())) {
				validateVocabularySets(e);
			}
		}
	}

	private void validateVocabularySets(Element element) throws NotValidEmotionmlException {
		assert element != null;
		for (String attName : EmotionML.vocabularyAttributeTypes.keySet()) {
			EmotionVocabulary.Type expectedType = EmotionML.vocabularyAttributeTypes.get(attName);
			if (element.hasAttribute(attName)) {
				String value = element.getAttribute(attName);
				EmotionVocabulary voc;
				try {
					voc = EmotionVocabulary.get(value);
				} catch (NoSuchVocabularyException e) {
					throw new NotValidEmotionmlException("Cannot get vocabulary definition from "+value, e);
				}
				if (voc.getType() != expectedType) {
					throw new NotValidEmotionmlException("The vocabulary referred to in '"+attName+"' should be of type '"+expectedType+"' but is of type '"+voc.getType()+"'");
				}
			}
		}
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
