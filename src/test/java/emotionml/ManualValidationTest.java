package emotionml;

import static emotionml.TestUtil.stringAsStream;
import static emotionml.TestUtil.emotionmlStream;
import static emotionml.TestUtil.emotionStream;
import static emotionml.TestUtil.parseFragment;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.w3c.dom.Document;

import emotionml.exceptions.NotValidEmotionmlException;

public class ManualValidationTest {

	// Tests verifying that the checker implements the Implementation Report assertions
	// for those features that go beyond Schema validation and need to be manually implemented in the processor.

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion101() throws Exception {
		// 101	emotionml	[2.1.1]	Y	N	The root element of standalone EmotionML documents MUST be <emotionml>.
		new Checker().parse(stringAsStream("<info xmlns=\"http://www.w3.org/2009/10/emotionml\"/>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion114fail() throws Exception {
		// 114		[2.1.1]	Y	Y	SUB CONSTRAINT: The "category-set" attribute of <emotionml>, if present, MUST refer to the ID of a <vocabulary> element with type="category".
		new Checker().parse(emotionmlStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions\"", ""));
	}
	
	@Test
	public void assertion114ok() throws Exception {
		// 114		[2.1.1]	Y	Y	SUB CONSTRAINT: The "category-set" attribute of <emotionml>, if present, MUST refer to the ID of a <vocabulary> element with type="category".
		Document doc = new Checker().parse(emotionmlStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", ""));
		assertNotNull(doc);
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion117fail() throws Exception {
		// 117		[2.1.1]	Y	Y	SUB CONSTRAINT: The "dimension-set" attribute of <emotionml>, if present, MUST refer to the ID of a <vocabulary> element with type="dimension".
		new Checker().parse(emotionmlStream("dimension-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", ""));
	}
	
	@Test
	public void assertion117ok() throws Exception {
		// 117		[2.1.1]	Y	Y	SUB CONSTRAINT: The "dimension-set" attribute of <emotionml>, if present, MUST refer to the ID of a <vocabulary> element with type="dimension".
		Document doc = new Checker().parse(emotionmlStream("dimension-set=\"http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions\"", ""));
		assertNotNull(doc);
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion120fail() throws Exception {
		// 120		[2.1.1]	Y	Y	SUB CONSTRAINT: The "appraisal-set" attribute of <emotionml>, if present, MUST refer to the ID of a <vocabulary> element with type="appraisal".
		new Checker().parse(emotionmlStream("appraisal-set=\"http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions\"", ""));
	}
	
	@Test
	public void assertion120ok() throws Exception {
		// 120		[2.1.1]	Y	Y	SUB CONSTRAINT: The "appraisal-set" attribute of <emotionml>, if present, MUST refer to the ID of a <vocabulary> element with type="appraisal".
		Document doc = new Checker().parse(emotionmlStream("appraisal-set=\"http://www.w3.org/TR/emotion-voc/xml#occ-appraisals\"", ""));
		assertNotNull(doc);
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion123fail() throws Exception {
		// 123		[2.1.1]	Y	Y	SUB CONSTRAINT: The "action-tendency-set" attribute of <emotionml>, if present, MUST refer to the ID of a <vocabulary> element with type="action-tendency".
		new Checker().parse(emotionmlStream("action-tendency-set=\"http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions\"", ""));
	}
	
	@Test
	public void assertion123ok() throws Exception {
		// 123		[2.1.1]	Y	Y	SUB CONSTRAINT: The "action-tendency-set" attribute of <emotionml>, if present, MUST refer to the ID of a <vocabulary> element with type="action-tendency".
		Document doc = new Checker().parse(emotionmlStream("action-tendency-set=\"http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies\"", ""));
		assertNotNull(doc);
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion161fail() throws Exception {
		// 161		[2.1.2]	Y	Y	SUB CONSTRAINT: The "category-set" attribute of <emotion>, if present, MUST refer to the ID of a <vocabulary> element with type="category".
		new Checker().parse(emotionmlStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions\"", ""));
	}
	
	@Test
	public void assertion161ok() throws Exception {
		// 161		[2.1.2]	Y	Y	SUB CONSTRAINT: The "category-set" attribute of <emotion>, if present, MUST refer to the ID of a <vocabulary> element with type="category".
		new Checker().validateFragment(parseFragment(emotionStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<category name=\"anger\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion164fail() throws Exception {
		// 164		[2.1.2]	Y	Y	SUB CONSTRAINT: The "dimension-set" attribute of <emotion>, if present, MUST refer to the ID of a <vocabulary> element with type="dimension".
		new Checker().validateFragment(parseFragment(emotionStream("dimension-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<dimension name=\"anger\"/>")));
	}
	
	@Test
	public void assertion164ok() throws Exception {
		// 164		[2.1.2]	Y	Y	SUB CONSTRAINT: The "dimension-set" attribute of <emotion>, if present, MUST refer to the ID of a <vocabulary> element with type="dimension".
		new Checker().validateFragment(parseFragment(emotionStream("dimension-set=\"http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions\"", "<dimension name=\"valence\" value=\"0.5\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion167fail() throws Exception {
		// 167		[2.1.2]	Y	Y	SUB CONSTRAINT: The "appraisal-set" attribute of <emotion>, if present, MUST refer to the ID of a <vocabulary> element with type="appraisal".
		new Checker().validateFragment(parseFragment(emotionStream("appraisal-set=\"http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions\"", "<appraisal name=\"valence\"/>")));
	}
	
	@Test
	public void assertion167ok() throws Exception {
		// 167		[2.1.2]	Y	Y	SUB CONSTRAINT: The "appraisal-set" attribute of <emotion>, if present, MUST refer to the ID of a <vocabulary> element with type="appraisal".
		new Checker().validateFragment(parseFragment(emotionStream("appraisal-set=\"http://www.w3.org/TR/emotion-voc/xml#occ-appraisals\"", "<appraisal name=\"desirability\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion170fail() throws Exception {
		// 170		[2.1.2]	Y	Y	SUB CONSTRAINT: The "action-tendency-set" attribute of <emotion>, if present, MUST refer to the ID of a <vocabulary> element with type="action-tendency".
		new Checker().validateFragment(parseFragment(emotionStream("action-tendency-set=\"http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions\"", "<action-tendency name=\"valence\"/>")));
	}
	
	@Test
	public void assertion170ok() throws Exception {
		// 170		[2.1.2]	Y	Y	SUB CONSTRAINT: The "action-tendency-set" attribute of <emotion>, if present, MUST refer to the ID of a <vocabulary> element with type="action-tendency".
		new Checker().validateFragment(parseFragment(emotionStream("action-tendency-set=\"http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies\"", "<action-tendency name=\"approach\"/>")));
	}
}
