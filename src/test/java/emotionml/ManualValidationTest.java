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

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion172() throws Exception {
		// 172	version	[2.1.2]	Y	N	The "version" attribute of <emotion>, if present, MUST have the value "1.0".
		new Checker().validateFragment(parseFragment(emotionStream("version=\"1.1\" category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<category name=\"anger\"/>")));
	}
	
	@Test
	public void assertion210emotionml() throws Exception {
		// 210	category	[2.2.1]	Y	N	If the <category> element is used, a category vocabulary MUST be declared using a "category-set" attribute on either the enclosing <emotion> element or the root element <emotionml>.
		new Checker().parse(emotionmlStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<emotion><category name=\"anger\"/></emotion>"));
	}

	@Test
	public void assertion210emotion() throws Exception {
		// 210	category	[2.2.1]	Y	N	If the <category> element is used, a category vocabulary MUST be declared using a "category-set" attribute on either the enclosing <emotion> element or the root element <emotionml>.
		new Checker().parse(emotionmlStream("", "<emotion category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"><category name=\"anger\"/></emotion>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion210none() throws Exception {
		// 210	category	[2.2.1]	Y	N	If the <category> element is used, a category vocabulary MUST be declared using a "category-set" attribute on either the enclosing <emotion> element or the root element <emotionml>.
		new Checker().parse(emotionmlStream("", "<emotion><category name=\"anger\"/></emotion>"));
	}

	@Test
	public void assertion212ok() throws Exception {
		// 212		[2.2.1]	Y	Y	SUB CONSTRAINT: The value of the "name" attribute of the <category> element MUST be contained in the declared category vocabulary. If both the <emotionml> and the <emotion> element has a "category-set" attribute, then the <emotion> element's attribute defines the declared category vocabulary.
		new Checker().validateFragment(parseFragment(emotionStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<category name=\"anger\"/>")));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion212fail() throws Exception {
		// 212		[2.2.1]	Y	Y	SUB CONSTRAINT: The value of the "name" attribute of the <category> element MUST be contained in the declared category vocabulary. If both the <emotionml> and the <emotion> element has a "category-set" attribute, then the <emotion> element's attribute defines the declared category vocabulary.
		new Checker().validateFragment(parseFragment(emotionStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<category name=\"somethingelse\"/>")));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion212conflict() throws Exception {
		// 212		[2.2.1]	Y	Y	SUB CONSTRAINT: The value of the "name" attribute of the <category> element MUST be contained in the declared category vocabulary. If both the <emotionml> and the <emotion> element has a "category-set" attribute, then the <emotion> element's attribute defines the declared category vocabulary.
		new Checker().parse(emotionmlStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#everyday-categories\"",
				"<emotion category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"><category name=\"content\"/></emotion>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion216() throws Exception {
		// 216	value / trace	[2.2.1]	Y	N	A <category> MUST NOT contain both a "value" attribute and a <trace> element.
		new Checker().validateFragment(parseFragment(emotionStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<category name=\"anger\" value=\"0.5\"><trace freq=\"10.5 Hz\" samples=\"0.1 0.1 0.2\"/></category>")));
	}
	
	@Test
	public void assertion220emotionml() throws Exception {
		// 220	dimension	[2.2.2]	Y	N	If the <dimension> element is used, a dimension vocabulary MUST be declared using a "dimension-set" attribute on either the enclosing <emotion> element or the root element <emotionml>.
		new Checker().parse(emotionmlStream("dimension-set=\"http://www.w3.org/TR/emotion-voc/xml#pad-dimensions\"", "<emotion><dimension value=\"0.1\" name=\"arousal\"/></emotion>"));
	}

	@Test
	public void assertion220emotion() throws Exception {
		// 220	dimension	[2.2.2]	Y	N	If the <dimension> element is used, a dimension vocabulary MUST be declared using a "dimension-set" attribute on either the enclosing <emotion> element or the root element <emotionml>.
		new Checker().parse(emotionmlStream("", "<emotion dimension-set=\"http://www.w3.org/TR/emotion-voc/xml#pad-dimensions\"><dimension value=\"0.1\" name=\"arousal\"/></emotion>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion220none() throws Exception {
		// 220	dimension	[2.2.2]	Y	N	If the <dimension> element is used, a dimension vocabulary MUST be declared using a "dimension-set" attribute on either the enclosing <emotion> element or the root element <emotionml>.
		new Checker().parse(emotionmlStream("", "<emotion><dimension value=\"0.1\" name=\"arousal\"/></emotion>"));
	}
	
	@Test
	public void assertion222ok() throws Exception {
		// 222		[2.2.2]	Y	Y	SUB CONSTRAINT: The value of the "name" attribute of the <dimension> element MUST be contained in the declared dimension vocabulary. If both the <emotionml> and the <emotion> element has a "dimension-set" attribute, then the <emotion> element's attribute defines the declared dimension vocabulary.
		new Checker().validateFragment(parseFragment(emotionStream("dimension-set=\"http://www.w3.org/TR/emotion-voc/xml#pad-dimensions\"", "<dimension value=\"0.1\" name=\"arousal\"/>")));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion222fail() throws Exception {
		// 222		[2.2.2]	Y	Y	SUB CONSTRAINT: The value of the "name" attribute of the <dimension> element MUST be contained in the declared dimension vocabulary. If both the <emotionml> and the <emotion> element has a "dimension-set" attribute, then the <emotion> element's attribute defines the declared dimension vocabulary.
		new Checker().validateFragment(parseFragment(emotionStream("dimension-set=\"http://www.w3.org/TR/emotion-voc/xml#pad-dimensions\"", "<dimension value=\"0.1\" name=\"somethingelse\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion222conflict() throws Exception {
		// 222		[2.2.2]	Y	Y	SUB CONSTRAINT: The value of the "name" attribute of the <dimension> element MUST be contained in the declared dimension vocabulary. If both the <emotionml> and the <emotion> element has a "dimension-set" attribute, then the <emotion> element's attribute defines the declared dimension vocabulary.
		new Checker().parse(emotionmlStream("dimension-set=\"http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions\"",
				"<emotion dimension-set=\"http://www.w3.org/TR/emotion-voc/xml#pad-dimensions\"><dimension name=\"unpredictability\" value=\"0.1\"/></emotion>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion224none() throws Exception {
		// 224	value / trace	[2.2.2]	Y	N	A <dimension> MUST contain either a "value" attribute or a <trace> element.
		new Checker().validateFragment(parseFragment(emotionStream("dimension-set=\"http://www.w3.org/TR/emotion-voc/xml#pad-dimensions\"", "<dimension name=\"arousal\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion224both() throws Exception {
		// 224	value / trace	[2.2.2]	Y	N	A <dimension> MUST contain either a "value" attribute or a <trace> element.
		new Checker().validateFragment(parseFragment(emotionStream("dimension-set=\"http://www.w3.org/TR/emotion-voc/xml#pad-dimensions\"", "<dimension name=\"arousal\" value=\"0.5\"><trace freq=\"10.5 Hz\" samples=\"0.1 0.1 0.2\"/></dimension>")));
	}

	@Test
	public void assertion230emotionml() throws Exception {
		// 230	appraisal	[2.2.3]	Y	N	If the <appraisal> element is used, an appraisal vocabulary MUST be declared using an "appraisal-set" attribute on either the enclosing <emotion> element or the root element <emotionml>.
		new Checker().parse(emotionmlStream("appraisal-set=\"http://www.w3.org/TR/emotion-voc/xml#occ-appraisals\"", "<emotion><appraisal value=\"0.1\" name=\"effort\"/></emotion>"));
	}

	@Test
	public void assertion230emotion() throws Exception {
		// 230	appraisal	[2.2.3]	Y	N	If the <appraisal> element is used, an appraisal vocabulary MUST be declared using an "appraisal-set" attribute on either the enclosing <emotion> element or the root element <emotionml>.
		new Checker().parse(emotionmlStream("", "<emotion appraisal-set=\"http://www.w3.org/TR/emotion-voc/xml#occ-appraisals\"><appraisal value=\"0.1\" name=\"effort\"/></emotion>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion230none() throws Exception {
		// 230	appraisal	[2.2.3]	Y	N	If the <appraisal> element is used, an appraisal vocabulary MUST be declared using an "appraisal-set" attribute on either the enclosing <emotion> element or the root element <emotionml>.
		new Checker().parse(emotionmlStream("", "<emotion><appraisal value=\"0.1\" name=\"effort\"/></emotion>"));
	}

	@Test
	public void assertion232ok() throws Exception {
		// 232		[2.2.3]	Y	Y	SUB CONSTRAINT: The value of the "name" attribute of the <appraisal> element MUST be contained in the declared appraisal vocabulary. If both the <emotionml> and the <emotion> element has an "appraisal-set" attribute, then the <emotion> element's attribute defines the declared appraisal vocabulary.
		new Checker().validateFragment(parseFragment(emotionStream("appraisal-set=\"http://www.w3.org/TR/emotion-voc/xml#occ-appraisals\"", "<appraisal value=\"0.1\" name=\"effort\"/>")));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion232fail() throws Exception {
		// 232		[2.2.3]	Y	Y	SUB CONSTRAINT: The value of the "name" attribute of the <appraisal> element MUST be contained in the declared appraisal vocabulary. If both the <emotionml> and the <emotion> element has an "appraisal-set" attribute, then the <emotion> element's attribute defines the declared appraisal vocabulary.
		new Checker().validateFragment(parseFragment(emotionStream("appraisal-set=\"http://www.w3.org/TR/emotion-voc/xml#occ-appraisals\"", "<appraisal value=\"0.1\" name=\"somethingelse\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion232conflict() throws Exception {
		// 232		[2.2.3]	Y	Y	SUB CONSTRAINT: The value of the "name" attribute of the <appraisal> element MUST be contained in the declared appraisal vocabulary. If both the <emotionml> and the <emotion> element has an "appraisal-set" attribute, then the <emotion> element's attribute defines the declared appraisal vocabulary.
		new Checker().parse(emotionmlStream("appraisal-set=\"http://www.w3.org/TR/emotion-voc/xml#occ-appraisals\"",
				"<emotion appraisal-set=\"http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals\"><appraisal name=\"effort\" value=\"0.1\"/></emotion>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion236() throws Exception {
		// 236	value / trace	[2.2.3]	Y	N	An <apraisal> element MUST NOT contain both a "value" attribute and a <trace> element.
		new Checker().validateFragment(parseFragment(emotionStream("appraisal-set=\"http://www.w3.org/TR/emotion-voc/xml#occ-appraisals\"", "<appraisal name=\"effort\" value=\"0.5\"><trace freq=\"10.5 Hz\" samples=\"0.1 0.1 0.2\"/></appraisal>")));
	}

	@Test
	public void assertion240emotionml() throws Exception {
		// 240	action-tendency	[2.2.4]	Y	N	If the <action-tendency> element is used, an action tendency vocabulary MUST be declared using an "action-tendency-set" attribute on either the enclosing <emotion> element or the root element <emotionml>.
		new Checker().parse(emotionmlStream("action-tendency-set=\"http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies\"", "<emotion><action-tendency name=\"approach\"/></emotion>"));
	}

	@Test
	public void assertion240emotion() throws Exception {
		// 240	action-tendency	[2.2.4]	Y	N	If the <action-tendency> element is used, an action tendency vocabulary MUST be declared using an "action-tendency-set" attribute on either the enclosing <emotion> element or the root element <emotionml>.
		new Checker().parse(emotionmlStream("", "<emotion action-tendency-set=\"http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies\"><action-tendency name=\"approach\"/></emotion>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion240none() throws Exception {
		// 240	action-tendency	[2.2.4]	Y	N	If the <action-tendency> element is used, an action tendency vocabulary MUST be declared using an "action-tendency-set" attribute on either the enclosing <emotion> element or the root element <emotionml>.
		new Checker().parse(emotionmlStream("", "<emotion><action-tendency name=\"approach\"/></emotion>"));
	}

	@Test
	public void assertion242ok() throws Exception {
		// 242		[2.2.4]	Y	Y	SUB CONSTRAINT: The value of the "name" attribute of the <action-tendency> element MUST be contained in the declared action tendency vocabulary. If both the <emotionml> and the <emotion> element has an "action-tendency-set" attribute, then the <emotion> element's attribute defines the declared action tendency vocabulary.
		new Checker().validateFragment(parseFragment(emotionStream("action-tendency-set=\"http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies\"", "<action-tendency value=\"0.1\" name=\"approach\"/>")));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion242fail() throws Exception {
		// 242		[2.2.4]	Y	Y	SUB CONSTRAINT: The value of the "name" attribute of the <action-tendency> element MUST be contained in the declared action tendency vocabulary. If both the <emotionml> and the <emotion> element has an "action-tendency-set" attribute, then the <emotion> element's attribute defines the declared action tendency vocabulary.
		new Checker().validateFragment(parseFragment(emotionStream("action-tendency-set=\"http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies\"", "<action-tendency value=\"0.1\" name=\"somethingelse\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion242conflict() throws Exception {
		// 242		[2.2.4]	Y	Y	SUB CONSTRAINT: The value of the "name" attribute of the <action-tendency> element MUST be contained in the declared action tendency vocabulary. If both the <emotionml> and the <emotion> element has an "action-tendency-set" attribute, then the <emotion> element's attribute defines the declared action tendency vocabulary.
		new Checker().parse(emotionmlStream("action-tendency-set=\"http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies\"",
				"<emotion action-tendency-set=\"http://www.example.com/custom/action/robot.xml#voc\"><action-tendency name=\"approach\" value=\"0.1\"/></emotion>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion246() throws Exception {
		// 246	value / trace	[2.2.4]	Y	N	An <action-tendency> element MUST NOT contain both a "value" attribute and a <trace> element.
		new Checker().validateFragment(parseFragment(emotionStream("action-tendency-set=\"http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies\"", "<action-tendency name=\"approach\" value=\"0.5\"><trace freq=\"10.5 Hz\" samples=\"0.1 0.1 0.2\"/></action-tendency>")));
	}

	// TODO: Don't know how to verify assertion 417:
	// 417		[2.4.1]	Y	Y	SUB CONSTRAINT: The value of the "media-type" attribute of the <reference> element, if present, MUST be a valid MIME type.
	
	
}
