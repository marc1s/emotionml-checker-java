package emotionml;

import static emotionml.TestUtil.stringAsStream;

import org.junit.Test;

import emotionml.exceptions.NotValidEmotionmlException;

public class ManualValidationTest {

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion101() throws Exception {
		// 101	emotionml	[2.1.1]	Y	N	The root element of standalone EmotionML documents MUST be <emotionml>.
		new Checker().parse(stringAsStream("<info xmlns=\"http://www.w3.org/2009/10/emotionml\"/>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion114() throws Exception {
		// 114		[2.1.1]	Y	Y	SUB CONSTRAINT: The "category-set" attribute of <emotionml>, if present, MUST refer to the ID of a <vocabulary> element with type="category".
		new Checker().parse(stringAsStream("<emotionml version=\"1.0\" xmlns=\"http://www.w3.org/2009/10/emotionml\" category-set=\"http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions\"/>"));
	}
}
