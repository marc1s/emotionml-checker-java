package emotionml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import emotionml.exceptions.NoSuchVocabularyException;

public class VocabularyTest {

	@Test(expected=NoSuchVocabularyException.class)
	public void rejectURLWithouHash() throws Exception {
		EmotionVocabulary.get("http://www.w3.org/TR/emotion-voc/xml");
	}
	
	@Test
	public void canInstantiateBig6() throws Exception {
		EmotionVocabulary big6 = EmotionVocabulary.get("http://www.w3.org/TR/emotion-voc/xml#big6");
		assertNotNull(big6);
		assertEquals(EmotionVocabulary.Type.category, big6.getType());
		assertEquals(6, big6.getItems().size());
		assertTrue(big6.getItems().contains("anger"));
	}
}
