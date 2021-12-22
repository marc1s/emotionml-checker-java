package emotionml;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import emotionml.exceptions.NoSuchVocabularyException;

public class EmotionVocabulary {

	public enum Type { category, dimension, appraisal, actionTendency };
	
	private static DocumentBuilder builder = setupBuilder();
	private static Map<String, String> localVocabularyDocuments = setupLocalVocabularies();
	
	private static DocumentBuilder setupBuilder() {
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		f.setNamespaceAware(true);
		try {
			f.setSchema(new Checker().getEmotionmlSchema());
			return builder = f.newDocumentBuilder();
		} catch (Exception e) {
			throw new Error("Cannot instantiate XML parser", e);
		}
	}
	
	private static Map<String, String> setupLocalVocabularies() {
		Map<String, String> vocs = new HashMap<String, String>();
		vocs.put("http://www.w3.org/TR/emotion-voc/xml", "emotion-voc.emotionml");
		vocs.put("http://www.example.com/custom/category/interpersonal-stances.xml", "examples/interpersonal.emotionml");
		vocs.put("http://www.example.com/custom/dimension/friendliness.xml", "examples/friendliness.emotionml");
		vocs.put("http://www.example.com/custom/appraisal/likelihood.xml", "examples/likelihood.emotionml");
		vocs.put("http://www.example.com/custom/action/robot.xml", "examples/robot.emotionml");
		return Collections.unmodifiableMap(vocs);
	}
	
	/**
	 * Obtain an EmotionVocabulary object representing the emotion vocabulary defined at the given vocabularyURL.
	 * @param vocabularyURL the exact URL of the vocabulary, including the fragment identifier, e.g. <code>http://www.w3.org/TR/emotion-voc/xml#big6</code>.
	 * @throws NoSuchVocabularyException if there is no emotion vocabulary defined at the given URL
	 * @return a new EmotionVocabulary
	 */
	public static EmotionVocabulary get(String vocabularyURL) throws NoSuchVocabularyException {
		return new EmotionVocabulary(vocabularyURL, true);
	}
	
	public static EmotionVocabulary get(Document ownerDocument,	String vocabularyId) throws NoSuchVocabularyException {
		return new EmotionVocabulary(ownerDocument, vocabularyId);
	}

	
	///////////////////////////// One EmotionVocabulary instance //////////////////////////////////
	
	private Type type;
	
	private Set<String> items;
	
	
	
	
	
	
	
	
	private EmotionVocabulary(String vocabularyURL, boolean useLocalIfPossible) throws NoSuchVocabularyException {
		Element voc = getVocabularyDOM(vocabularyURL, useLocalIfPossible);
		initFromDOM(voc);
	}
	
	private EmotionVocabulary(Document doc, String vocabularyId) throws NoSuchVocabularyException {
		Element voc = getVocabularyElement(doc, vocabularyId);
		initFromDOM(voc);
	}

	private Element getVocabularyElement(Document doc, String vocabularyId)
			throws NoSuchVocabularyException {
		Element voc = doc.getElementById(vocabularyId);
		if (voc == null) {
			throw new NoSuchVocabularyException("Local document does not contain a vocabulary with id '"+vocabularyId+"'");
		}
		if (!EmotionML.namespaceURI.equals(voc.getNamespaceURI()) || !"vocabulary".equals(voc.getLocalName())) {
			throw new NoSuchVocabularyException("Element in local document with id '"+vocabularyId+"' is not an EmotionML vocabulary element");
		}
		return voc;
	}

	private void initFromDOM(Element voc) throws NoSuchVocabularyException {
		type = getTypeFromDOM(voc);
		items = getItemsFromDOM(voc);
	}

	private Set<String> getItemsFromDOM(Element voc) throws NoSuchVocabularyException {
		Set<String> vocItems = new HashSet<String>();
		NodeList itemNodes = voc.getElementsByTagNameNS(EmotionML.namespaceURI, "item");
		for (int i=0, len=itemNodes.getLength(); i<len; i++) {
			String name = createItem(itemNodes.item(i));
			if (vocItems.contains("name")) {
				throw new NoSuchVocabularyException("Item '"+name+"' is not unique in vocabulary");
			}
			vocItems.add(name);
		}
		return Collections.unmodifiableSet(vocItems);
	}

	private Type getTypeFromDOM(Element voc) throws NoSuchVocabularyException {
		String typeString = voc.getAttribute("type");
		if ("action-tendency".equals(typeString)) {
			typeString = "actionTendency";
		}
		try {
			return Type.valueOf(typeString);
		} catch (Exception e) {
			throw new NoSuchVocabularyException("Illegal vocabulary type '"+typeString+"'", e);
		}
	}

	private Element getVocabularyDOM(String vocabularyURL, boolean useLocalIfPossible) throws NoSuchVocabularyException {
		String baseURL = extractBaseURL(vocabularyURL);
		String vocabularyID = extractVocabularyID(vocabularyURL);
		Document doc = parseVocabularyDocument(baseURL, useLocalIfPossible);
		return getVocabularyElement(doc, vocabularyID);
	}

	private int extractHashPosition(String vocabularyURL) throws NoSuchVocabularyException {
		int iHash = vocabularyURL.indexOf('#');
		if (iHash == -1) {
			throw new NoSuchVocabularyException("Vocabulary URL '"+vocabularyURL+"' does not match the expected scheme of documentURL#vocabularyID");
		}
		return iHash;
	}
	
	private String extractBaseURL(String vocabularyURL) throws NoSuchVocabularyException {
		return vocabularyURL.substring(0, extractHashPosition(vocabularyURL));
	}
	
	private String extractVocabularyID(String vocabularyURL) throws NoSuchVocabularyException {
		return vocabularyURL.substring(extractHashPosition(vocabularyURL)+1);
	}

	private Document parseVocabularyDocument(String baseURL, boolean useLocalIfPossible) throws NoSuchVocabularyException {
		try {
			if (useLocalIfPossible && localVocabularyDocuments.containsKey(baseURL)) {
				return builder.parse(EmotionVocabulary.class.getResourceAsStream(localVocabularyDocuments.get(baseURL)));
			} else {
				return builder.parse(baseURL);
			}
		} catch (Exception e) {
			throw new NoSuchVocabularyException("Cannot parse vocabulary definition", e);
		}
	}
	
	private String createItem(Node itemNode) throws NoSuchVocabularyException {
		Element itemElement = (Element) itemNode;
		if (!itemElement.hasAttribute("name")) {
			throw new NoSuchVocabularyException("Vocabulary has item with no name");
		}
		String itemName = itemElement.getAttribute("name");
		return itemName;
	}

	public Type getType() {
		return type;
	}
	
	public Set<String> getItems() {
		return items;
	}

	

}
