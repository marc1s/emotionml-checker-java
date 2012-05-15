package emotionml;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
}
