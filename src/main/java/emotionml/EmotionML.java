package emotionml;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import emotionml.EmotionVocabulary.Type;

public class EmotionML {

	public static final String namespaceURI = "http://www.w3.org/2009/10/emotionml";
	
	/**
	 * Map the names of attributes declaring emotion vocabularies to the type of the vocabulary they declare. 
	 */
	public static final Map<String, EmotionVocabulary.Type> vocabularyAttributeTypes = fillVocabularyAttributeTypes();

	private static Map<String, Type> fillVocabularyAttributeTypes() {
		Map<String, Type> att2type = new HashMap<String, EmotionVocabulary.Type>();
		att2type.put("category-set", Type.category);
		att2type.put("dimension-set", Type.dimension);
		att2type.put("appraisal-set", Type.appraisal);
		att2type.put("action-tendency-set", Type.actionTendency);
		return Collections.unmodifiableMap(att2type);
	}
	
	/**
	 * Convenience set listing the four types of emotion description tags:
	 * category, dimension, appraisal, and action-tendency.
	 */
	public static final Set<String> descriptionTags = fillDescriptionTags();

	private static Set<String> fillDescriptionTags() {
		Set<String> s = new HashSet<String>(Arrays.asList(
				"category", "dimension", "appraisal", "action-tendency"
		));
		return Collections.unmodifiableSet(s);
	}
	
}
