package emotionml.xslt;

import java.util.StringTokenizer;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import emotionml.EmotionML;

/**
 * This class provides a number of static helper functions to be used as extension functions in XSLT taking EmotionML as input.
 * @author marc
 *
 */
public class Helpers {

	/**
	 * For a given emotion element, provide the declared category vocabulary's URI,
	 * if any, or the empty string.
	 * The concept of a declared vocabulary is explained in
	 * http://www.w3.org/TR/2012/CR-emotionml-20120510/#s2.1.2
	 * @param emotion an emotion node
	 * @return a String containing the declared vocabulary, or else the empty string.
	 */
	public static String declaredCategoryVocabulary(Node emotion) {
		return declaredVocabulary("category", emotion);
	}

	/**
	 * For a given emotion element, provide the declared dimension vocabulary's URI,
	 * if any, or the empty string.
	 * The concept of a declared vocabulary is explained in
	 * http://www.w3.org/TR/2012/CR-emotionml-20120510/#s2.1.2
	 * @param emotion an emotion node
	 * @return a String containing the declared vocabulary, or else the empty string.
	 */
	public static String declaredDimensionVocabulary(Node emotion) {
		return declaredVocabulary("dimension", emotion);
	}

	/**
	 * For a given emotion element, provide the declared appraisal vocabulary's URI,
	 * if any, or the empty string.
	 * The concept of a declared vocabulary is explained in
	 * http://www.w3.org/TR/2012/CR-emotionml-20120510/#s2.1.2
	 * @param emotion an emotion node
	 * @return a String containing the declared vocabulary, or else the empty string.
	 */
	public static String declaredAppraisalVocabulary(Node emotion) {
		return declaredVocabulary("appraisal", emotion);
	}

	/**
	 * For a given emotion element, provide the declared action tendency vocabulary's URI,
	 * if any, or the empty string.
	 * The concept of a declared vocabulary is explained in
	 * http://www.w3.org/TR/2012/CR-emotionml-20120510/#s2.1.2
	 * @param emotion an emotion node
	 * @return a String containing the declared vocabulary, or else the empty string.
	 */
	public static String declaredActionTendencyVocabulary(Node emotion) {
		return declaredVocabulary("action-tendency", emotion);
	}

	/**
	 * Determine whether the given emotion element is expressed through the given modality.
	 * @param emotionNode emotion element in question
	 * @param modality name of a single modality
	 * @param defaultValue whether to assume no expressed-through attribute means that the result should be 'true'.
	 * @return true if the given modality is one of the values in the 'expressed-through' attributes of emotion;
	 * false if there is an 'expressed-through' attribute which does not list modality as one of the values.
	 * If emotion has no 'expressed-through' attribute, defaultValue is returned.
	 */
	public static boolean expressedThrough(Node emotionNode, String modality, boolean defaultValue) {
		if (!isEmotionMLElement(emotionNode, "emotion")) {
			return false;
		}
		Element emotion = (Element) emotionNode;
		if (!emotion.hasAttribute("expressed-through")) {
			return defaultValue;
		}
		StringTokenizer st = new StringTokenizer(emotion.getAttribute("expressed-through"));
		while (st.hasMoreTokens()) {
			String one = st.nextToken();
			if (one.equals(modality)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	private static boolean isEmotionMLElement(Node n, String localName) {
		if (n == null || n.getNodeType() != Node.ELEMENT_NODE) {
			return false;
		}
		Element e = (Element) n;
		return EmotionML.namespaceURI.equals(e.getNamespaceURI())
				&& localName.equals(n.getLocalName());
	}
	
	private static String declaredVocabulary(String type, Node emotionNode) {
		String attname = type+"-set";
		if (!isEmotionMLElement(emotionNode, "emotion")) {
			return "";
		}
		Element emotion = (Element) emotionNode;
		if (emotion.hasAttribute(attname)) {
			return emotion.getAttribute(attname);
		}
		Node parentNode = emotion.getParentNode();
		if (!isEmotionMLElement(parentNode, "emotionml")) {
			return "";
		}
		Element parentElement = (Element) parentNode;
		return parentElement.getAttribute(attname);
	}
}
