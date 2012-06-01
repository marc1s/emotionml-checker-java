package emotionml.xslt;

import static emotionml.TestUtil.emotionmlStream;
import static emotionml.TestUtil.emotionStream;
import static emotionml.TestUtil.parseFragment;
import static emotionml.TestUtil.validEmotionStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;

import emotionml.Checker;
import emotionml.EmotionML;
public class HelpersTest {

	@Test
	public void globalCategoryVocabulary() throws Exception {
		Document doc = new Checker().parse(emotionmlStream("category-set='http://www.w3.org/TR/emotion-voc/xml#big6'", "<emotion><category name='anger'/></emotion>"));
		Element emotion = (Element) doc.getElementsByTagNameNS(EmotionML.namespaceURI, "emotion").item(0);
		assertEquals("http://www.w3.org/TR/emotion-voc/xml#big6", Helpers.declaredCategoryVocabulary(emotion));
	}

	@Test
	public void localCategoryVocabulary() throws Exception {
		DocumentFragment frag = parseFragment(emotionStream("category-set='http://www.w3.org/TR/emotion-voc/xml#big6'", "<category name='anger'/>"));
		Element emotion = (Element) frag.getFirstChild();
		assertEquals("http://www.w3.org/TR/emotion-voc/xml#big6", Helpers.declaredCategoryVocabulary(emotion));
	}

	@Test
	public void globalAndLocalCategoryVocabulary() throws Exception {
		Document doc = new Checker().parse(emotionmlStream("category-set='http://www.w3.org/TR/emotion-voc/xml#big6'", "<emotion category-set='http://www.w3.org/TR/emotion-voc/xml#everyday-categories'><category name='angry'/></emotion>"));
		Element emotion = (Element) doc.getElementsByTagNameNS(EmotionML.namespaceURI, "emotion").item(0);
		assertEquals("http://www.w3.org/TR/emotion-voc/xml#everyday-categories", Helpers.declaredCategoryVocabulary(emotion));
	}

	@Test
	public void voiceModality() throws Exception {
		DocumentFragment frag = parseFragment(validEmotionStream("expressed-through='face voice gesture'", ""));
		Element emotion = (Element) frag.getFirstChild();
		assertTrue(Helpers.expressedThrough(emotion, "voice", false));
		assertTrue(Helpers.expressedThrough(emotion, "voice", true));
	}

	@Test
	public void voiceModalityDefault() throws Exception {
		DocumentFragment frag = parseFragment(validEmotionStream("", ""));
		Element emotion = (Element) frag.getFirstChild();
		assertTrue(Helpers.expressedThrough(emotion, "voice", true));
		assertFalse(Helpers.expressedThrough(emotion, "voice", false));
	}

}
