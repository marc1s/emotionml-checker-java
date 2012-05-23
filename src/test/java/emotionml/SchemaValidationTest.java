package emotionml;

import static emotionml.TestUtil.resourceAsStream;
import static emotionml.TestUtil.stringAsStream;
import static emotionml.TestUtil.parse;
import static emotionml.TestUtil.parseFragment;
import static emotionml.TestUtil.emotionmlStream;
import static emotionml.TestUtil.emotionStream;
import static emotionml.TestUtil.validEmotionStream;
import static org.junit.Assert.assertNotNull;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;

import org.junit.Test;
import org.w3c.dom.Document;

import emotionml.exceptions.NotValidEmotionmlException;

public class SchemaValidationTest {


	@Test
	public void validatingParseFromInputStream() throws Exception {
		// setup SUT
		Checker emotionmlChecker = new Checker();
		// exercise SUT
		Document emotionml = emotionmlChecker.parse(resourceAsStream("doc1.emotionml"));
		// verify
		assertNotNull(emotionml);
	}
	
	@Test
	public void canInstantiateSchema() throws Exception {
		Checker c = new Checker();
		assertNotNull(c.getEmotionmlSchema());
	}
	
	@Test
	public void canValidateWithSchema() throws Exception {
		Validator emotionmlValidator = new Checker().getEmotionmlSchema().newValidator();
		emotionmlValidator.validate(new StreamSource(resourceAsStream("doc1.emotionml")));
	}
	
	@Test
	public void checkerCanValidate() throws Exception {
		new Checker().validate(parse(resourceAsStream("doc1.emotionml")));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void checkerCanFailValidation() throws Exception {
		new Checker().validate(parse(stringAsStream("<bla/>")));
	}
	
	@Test
	public void checkerCanValidateFragment() throws Exception {
		new Checker().validateFragment(parseFragment(resourceAsStream("fragment1.xml")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void checkerCanFailValidateFragment() throws Exception {
		new Checker().validateFragment(parseFragment(stringAsStream("<bla/>")));
	}

	
	// Tests verifying that the checker implements the Implementation Report assertions
	// for those features that can be verified by schema validation.
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion102() throws Exception {
		// 102	emotionml	[2.1.1]	Y	N The <emotionml> element MUST define the EmotionML namespace: "http://www.w3.org/2009/10/emotionml".
		new Checker().parse(stringAsStream("<emotionml/>"));
	}
	
	@Test
	public void assertions103To105_303_305() throws Exception {
		// 103	emotion	[2.1.1]	N	N	The <emotionml> element MAY contain one or more <emotion> elements.			
		// 104	vocabulary	[2.1.1]	N	N	The <emotionml> element MAY contain one or more <vocabulary> elements.			
		// 105	info	[2.1.1]	N	N	The <emotionml> element MAY contain a single <info> element.
		// 303	info	[2.3.3]	N	N	The <info> element MAY contain arbitrary plain text.
		// 305	id	[2.3.3]	N	N	The <info> element MAY contain an attribute "id".
		new Checker().parse(resourceAsStream("doc3.emotionml"));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion110() throws Exception {
		// 110	version	[2.1.1]	Y	N	The root element of a standalone EmotionML document MUST have an attribute "version".
		new Checker().parse(stringAsStream("<emotionml xmlns=\"http://www.w3.org/2009/10/emotionml\"/>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion111() throws Exception {
		// 111	version	[2.1.1]	Y	N	The "version" attribute of <emotionml> MUST have the value "1.0"
		new Checker().parse(stringAsStream("<emotionml version=\"abc\" xmlns=\"http://www.w3.org/2009/10/emotionml\"/>"));
	}

	@Test
	public void assertions112_115_118_121() throws Exception {
		// 112	category-set	[2.1.1]	N	N	The <emotionml> element MAY contain an attribute "category-set".		
		// 115	dimension-set	[2.1.1]	N	N	The <emotionml> element MAY contain an attribute "dimension-set".			
		// 118	appraisal-set	[2.1.1]	N	N	The <emotionml> element MAY contain an attribute "appraisal-set".
		// 121	action-tendency-set	[2.1.1]	N	N	The <emotionml> element MAY contain an attribute "action-tendency-set".
		new Checker().parse(resourceAsStream("doc2.emotionml"));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion113() throws Exception {
		// 113	category-set	[2.1.1]	Y	N	The "category-set" attribute of <emotionml>, if present, MUST be of type xsd:anyURI.
		new Checker().parse(stringAsStream("<emotionml version=\"1.0\" xmlns=\"http://www.w3.org/2009/10/emotionml\" category-set=\";:\"/>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion116() throws Exception {
		// 116	dimension-set	[2.1.1]	Y	N	The "dimension-set" attribute of <emotionml>, if present, MUST be of type xsd:anyURI.
		new Checker().parse(stringAsStream("<emotionml version=\"1.0\" xmlns=\"http://www.w3.org/2009/10/emotionml\" dimension-set=\";:\"/>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion119() throws Exception {
		// 119	appraisal-set	[2.1.1]	Y	N	The "appraisal-set" attribute of <emotionml>, if present, MUST be of type xsd:anyURI.
		new Checker().parse(stringAsStream("<emotionml version=\"1.0\" xmlns=\"http://www.w3.org/2009/10/emotionml\" appraisal-set=\";:\"/>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion122() throws Exception {
		// 122	action-tendency-set	[2.1.1]	Y	N	The "action-tendency-set" attribute of <emotionml>, if present, MUST be of type xsd:anyURI.
		new Checker().parse(stringAsStream("<emotionml version=\"1.0\" xmlns=\"http://www.w3.org/2009/10/emotionml\" action-tendency-set=\";:\"/>"));
	}

	@Test
	public void assertion124() throws Exception {
		// 124	emotionml	[2.1.1]	N	N	The <emotionml> element MAY contain arbitrary plain text.
		new Checker().parse(resourceAsStream("doc1.emotionml"));
	}
	
	@Test
	public void assertions150To153() throws Exception {
		// 150	category	[2.1.2]	N	N	The <emotion> element MAY contain one or more <category> elements.			
		// 151	dimension	[2.1.2]	N	N	The <emotion> element MAY contain one or more <dimension> elements.			
		// 152	appraisal	[2.1.2]	N	N	The <emotion> element MAY contain one or more <appraisal> elements.			
		// 153	action-tendency	[2.1.2]	N	N	The <emotion> element MAY contain one or more <action-tendency> elements.
		new Checker().parse(resourceAsStream("examples-2.2.emotionml"));
	}

	@Test
	public void assertion154() throws Exception {
		// 154	reference	[2.1.2]	N	N	The <emotion> element MAY contain one or more <reference> elements.
		new Checker().validateFragment(parseFragment(resourceAsStream("fragment3.xml")));
	}
	
	@Test
	public void assertions155_302() throws Exception {
		// 155	info	[2.1.2]	N	N	The <emotion> element MAY contain a single <info> element.
		// 302	info	[2.3.3]	N	N	The <info> element MAY contain any elements with a namespace different from the EmotionML namespace, "http://www.w3.org/2009/10/emotionml".
		new Checker().parse(resourceAsStream("doc4.emotionml"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion156() throws Exception {
		// 156	emotion	[2.1.2]	Y	N	The <emotion> element MUST contain at least one <category> or <dimension> or <appraisal> or <action-tendency> element.
		new Checker().validateFragment(parseFragment(emotionStream("", "")));
	}

	@Test
	public void assertions157_158_159_162_165_168() throws Exception {
		// 157	emotion	[2.1.2]	N	N	The allowed child elements of <emotion> MAY occur in any order.			
		// 158	emotion	[2.1.2]	N	N	The allowed child elements of <emotion> MAY occur in any combination.
		// 159	category-set	[2.1.2]	N	N	The <emotion> element MAY contain an attribute "category-set".
		// 162	dimension-set	[2.1.2]	N	N	The <emotion> element MAY contain an attribute "dimension-set".			
		// 165	appraisal-set	[2.1.2]	N	N	The <emotion> element MAY contain an attribute "appraisal-set".			
		// 168	action-tendency-set	[2.1.2]	N	N	The <emotion> element MAY contain an attribute "action-tendency-set".
		new Checker().validateFragment(parseFragment(resourceAsStream("fragment2.xml")));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion160() throws Exception {
		// 160	category-set	[2.1.2]	Y	N	The "category-set" attribute of <emotion>, if present, MUST be of type xsd:anyURI.
		new Checker().validateFragment(parseFragment(stringAsStream("<emotion version=\"1.0\" xmlns=\"http://www.w3.org/2009/10/emotionml\" category-set=\";:\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion163() throws Exception {
		// 163	dimension-set	[2.1.2]	Y	N	The "dimension-set" attribute of <emotion>, if present, MUST be of type xsd:anyURI.
		new Checker().validateFragment(parseFragment(stringAsStream("<emotion version=\"1.0\" xmlns=\"http://www.w3.org/2009/10/emotionml\" dimension-set=\";:\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion166() throws Exception {
		// 166	appraisal-set	[2.1.2]	Y	N	The "appraisal-set" attribute of <emotion>, if present, MUST be of type xsd:anyURI.
		new Checker().validateFragment(parseFragment(stringAsStream("<emotion version=\"1.0\" xmlns=\"http://www.w3.org/2009/10/emotionml\" appraisal-set=\";:\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion169() throws Exception {
		// 169	action-tendency-set	[2.1.2]	Y	N	The "action-tendency-set" attribute of <emotion>, if present, MUST be of type xsd:anyURI.
		new Checker().validateFragment(parseFragment(stringAsStream("<emotion version=\"1.0\" xmlns=\"http://www.w3.org/2009/10/emotionml\" action-tendency-set=\";:\"/>")));
	}

	
	@Test
	public void assertion171() throws Exception {
		// 171	version	[2.1.2]	N	N	The <emotion> element MAY have an attribute "version".
		new Checker().validateFragment(parseFragment(emotionStream("version=\"1.0\" category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<category name=\"anger\"/>")));
	}

	@Test
	public void assertion173() throws Exception {
		// 173	id	[2.1.2]	N	N	The <emotion> element MAY contain an attribute "id".
		new Checker().validateFragment(parseFragment(emotionStream("id=\"myid\" category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<category name=\"anger\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion174() throws Exception {
		// 174	id	[2.1.2]	Y	N	The "id" attribute of <emotion>, if present, MUST be of type xsd:ID.
		new Checker().validateFragment(parseFragment(emotionStream("id=\"123\"", "<category name=\"bla\"/>")));
	}
				
	@Test
	public void assertions172To180() throws Exception {
		// 175	start	[2.1.2]	N	N	The <emotion> element MAY have an attribute "start".			
		// 176	end	[2.1.2]	N	N	The <emotion> element MAY have an attribute "end".			
		// 177	duration	[2.1.2]	N	N	The <emotion> element MAY have an attribute "duration".			
		// 178	time-ref-uri	[2.1.2]	N	N	The <emotion> element MAY have an attribute "time-ref-uri".			
		// 179	time-ref-anchor-point	[2.1.2]	N	N	The <emotion> element MAY have an attribute "time-ref-anchor-point".			
		// 180	offset-to-start	[2.1.2]	N	N	The <emotion> element MAY have an attribute "offset-to-start".
		new Checker().parse(resourceAsStream("doc7.emotionml"));
	}
	
	@Test
	public void assertion181() throws Exception {
		// 155	info	[2.1.2]	N	N	The <emotion> element MAY contain a single <info> element.
		new Checker().parse(resourceAsStream("doc6.emotionml"));
	}

	@Test
	public void assertion182() throws Exception {
		// 182	emotion	[2.1.2]	N	N	The <emotion> element MAY contain arbitrary plain text.
		new Checker().validateFragment(parseFragment(emotionStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "arbitrary <category name=\"anger\"/> text")));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion211() throws Exception {
		// 211	name	[2.2.1]	Y	N	A category element MUST contain a "name" attribute.
		new Checker().validateFragment(parseFragment(emotionStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<category/>")));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion213() throws Exception {
		// 213	name	[2.2.1]	Y	N	For any given category name in the set, zero or one occurrence is allowed within an <emotion> element, i.e. a category with name "x" MUST NOT appear twice in one <emotion> element.
		new Checker().validateFragment(parseFragment(emotionStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<category name=\"anger\"/> <category name=\"anger\"/>")));
	}

	@Test
	public void assertions214_215() throws Exception {
		// 214	value	[2.2.1]	N	N	A <category> MAY contain a "value" attribute.			
		// 215	trace	[2.2.1]	N	N	A <category> MAY contain a <trace> element.
		new Checker().parse(resourceAsStream("examples-2.5.emotionml"));
	}

	@Test
	public void assertions217_225() throws Exception {
		// 217	confidence	[2.2.1]	N	N	A <category> element MAY contain a "confidence" attribute.
		// 225	confidence	[2.2.2]	N	N	A <dimension> element MAY contain a "confidence" attribute.
		new Checker().parse(resourceAsStream("examples-2.3.emotionml"));
	}


	@Test(expected=NotValidEmotionmlException.class)
	public void assertion221() throws Exception {
		// 221	name	[2.2.2]	Y	N	A <dimension> element MUST contain a "name" attribute.
		new Checker().validateFragment(parseFragment(emotionStream("dimension-set=\"http://www.w3.org/TR/emotion-voc/xml#pad-dimensions\"", "<dimension value=\"0.1\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion223() throws Exception {
		// 223	name	[2.2.2]	Y	N	For any given dimension name in the set, zero or one occurrence is allowed within an <emotion> element i.e. a dimension with name "x" MUST NOT appear twice in one <emotion> element.
		new Checker().validateFragment(parseFragment(emotionStream("dimension-set=\"http://www.w3.org/TR/emotion-voc/xml#pad-dimensions\"", "<dimension name=\"arousal\" value=\"0.1\"/> <dimension name=\"arousal\" value=\"0.2\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion231() throws Exception {
		// 231	name	[2.2.3]	Y	N	An <appraisal> element MUST contain the "name" attribute.
		new Checker().validateFragment(parseFragment(emotionStream("appraisal-set=\"http://www.w3.org/TR/emotion-voc/xml#occ-appraisals\"", "<appraisal/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion233() throws Exception {
		// 233	name	[2.2.3]	Y	N	For any given appraisal name in the set, zero or one occurrence is allowed within an <emotion> element, i.e. an appraisal with name "x" MUST NOT appear twice in one <emotion> element.
		new Checker().validateFragment(parseFragment(emotionStream("appraisal-set=\"http://www.w3.org/TR/emotion-voc/xml#occ-appraisals\"", "<appraisal name=\"effort\"/> <appraisal name=\"effort\"/>")));
	}

	@Test
	public void assertion234() throws Exception {
		// 234	value	[2.2.3]	N	N	An <appraisal> element MAY contain a "value" attribute.
		new Checker().validateFragment(parseFragment(emotionStream("appraisal-set=\"http://www.w3.org/TR/emotion-voc/xml#occ-appraisals\"", "<appraisal name=\"effort\" value=\"0.5\"/>")));
	}

	@Test
	public void assertion235() throws Exception {
		// 235	trace	[2.2.3]	N	N	An <appraisal> element MAY contain a <trace> element.
		new Checker().validateFragment(parseFragment(emotionStream("appraisal-set=\"http://www.w3.org/TR/emotion-voc/xml#occ-appraisals\"", "<appraisal name=\"effort\"><trace freq=\"10.5 Hz\" samples=\"0.1 0.1 0.2\"/></appraisal>")));
	}

	@Test
	public void assertion237() throws Exception {
		// 237	confidence	[2.2.3]	N	N	An <appraisal> element MAY contain a "confidence" attribute.
		new Checker().validateFragment(parseFragment(emotionStream("appraisal-set=\"http://www.w3.org/TR/emotion-voc/xml#occ-appraisals\"", "<appraisal name=\"effort\" confidence=\"0.5\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion241() throws Exception {
		// 241	name	[2.2.4]	Y	N	An <action-tendency> element MUST contain the "name" attribute.
		new Checker().validateFragment(parseFragment(emotionStream("action-tendency-set=\"http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies\"", "<action-tendency/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion243() throws Exception {
		// 243	name	[2.2.4]	Y	N	For any given action tendency name in the set, zero or one occurrence is allowed within an <emotion> element, i.e. an action tendency with name "x" MUST NOT appear twice in one <emotion> element.
		new Checker().validateFragment(parseFragment(emotionStream("action-tendency-set=\"http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies\"", "<action-tendency name=\"approach\"/> <action-tendency name=\"approach\"/>")));
	}

	@Test
	public void assertion244() throws Exception {
		// 244	value	[2.2.4]	N	N	An <action-tendency> element MAY contain a "value" attribute.
		new Checker().validateFragment(parseFragment(emotionStream("action-tendency-set=\"http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies\"", "<action-tendency name=\"approach\" value=\"0.5\"/>")));
	}

	@Test
	public void assertion245() throws Exception {
		// 245	trace	[2.2.4]	N	N	An <action-tendency> element MAY contain a <trace> element.
		new Checker().validateFragment(parseFragment(emotionStream("action-tendency-set=\"http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies\"", "<action-tendency name=\"approach\"><trace freq=\"10.5 Hz\" samples=\"0.1 0.1 0.2\"/></action-tendency>")));
	}

	@Test
	public void assertion247() throws Exception {
		// 247	confidence	[2.2.4]	N	N	An <action-tendency> element MAY contain a "confidence" attribute.
		new Checker().validateFragment(parseFragment(emotionStream("action-tendency-set=\"http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies\"", "<action-tendency name=\"approach\" confidence=\"0.5\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion300() throws Exception {
		// 300	confidence	[2.3.1]	Y	N	The value of the "confidence" attribute MUST be a floating point number in the closed interval [0, 1].
		new Checker().validateFragment(parseFragment(emotionStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<category name=\"anger\" confidence=\"1.1\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion301() throws Exception {
		// 301	expressed-through	[2.3.2]	Y	N	The attribute "expressed-through" of the <emotion> element, if present, MUST be of type xsd:nmtokens.
		new Checker().validateFragment(parseFragment(emotionStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<category name=\"anger\" expressed-through=\"#1.1\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion304() throws Exception {
		// 304	info	[2.3.3]	Y	N	The <info> element MUST NOT contain any elements in the EmotionML namespace, "http://www.w3.org/2009/10/emotionml".
		new Checker().parse(emotionStream("", "<info><reference /></info>"));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion306() throws Exception {
		// 306	id	[2.3.3]	Y	N	The "id" attribute of the <info> element, if present, MUST be of type xsd:ID.
		new Checker().parse(emotionStream("", "<info id=\"123\"></info>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion410() throws Exception {
		// 410	uri	[2.4.1]	Y	N	The <reference> element MUST contain a "uri" attribute.
		new Checker().validateFragment(parseFragment(validEmotionStream("", "<reference />")));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion411() throws Exception {
		// 411	uri	[2.4.1]	Y	N	The "uri" attribute of <reference> MUST be of type xsd:anyURI.
		new Checker().validateFragment(parseFragment(validEmotionStream("", "<reference uri=\";:\"/>")));
	}

	@Test
	public void assertion412() throws Exception {
		// 412		[2.4.1]	N	Y	SUB CONSTRAINT: The URI in the "uri" attribute of a <reference> element MAY be extended by a media fragment.
		new Checker().validateFragment(parseFragment(validEmotionStream("", "<reference uri=\"http://example.com/audio.wav?t=1,3\"/>")));
	}

	@Test
	public void assertion413_414() throws Exception {
		// 413	role	[2.4.1]	N	N	The <reference> element MAY contain a "role" attribute.			
		// 414	role	[2.4.1]	Y	N	The value of the "role" attribute of the <reference> element, if present, MUST be one of "expressedBy", "experiencedBy", "triggeredBy", "targetedAt".
		new Checker().validateFragment(parseFragment(validEmotionStream("", "<reference uri=\"http://example.com\" role=\"experiencedBy\"/>")));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion414fail() throws Exception {
		// 414	role	[2.4.1]	Y	N	The value of the "role" attribute of the <reference> element, if present, MUST be one of "expressedBy", "experiencedBy", "triggeredBy", "targetedAt".
		new Checker().validateFragment(parseFragment(validEmotionStream("", "<reference uri=\"http://example.com\" role=\"somethingelse\"/>")));
	}
	
	@Test
	public void assertions415_416() throws Exception {
		// 415	media-type	[2.4.1]	N	N	The <reference> element MAY contain a "media-type" attribute.
		// 416	media-type	[2.4.1]	Y	N	The value of the "media-type" attribute of the <reference> element, if present, MUST be of type xsd:string.
		new Checker().validateFragment(parseFragment(validEmotionStream("", "<reference uri=\"http://example.com\" media-type=\"audio/wav\"/>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion420() throws Exception {
		// 420	start	[2.4.2]	Y	N	The value of the "start" attribute of <emotion>, if present, MUST be of type xsd:nonNegativeInteger.			
		new Checker().validateFragment(parseFragment(validEmotionStream("start=\"-1\"", "")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion421() throws Exception {
		// 421	end	[2.4.2]	Y	N	The value of the "end" attribute of <emotion>, if present, MUST be of type xsd:nonNegativeInteger.			
		new Checker().validateFragment(parseFragment(validEmotionStream("end=\"-1\"", "")));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion422() throws Exception {
		// 422	duration	[2.4.2]	Y	N	The value of "duration" attribute of <emotion>, if present, MUST be of type xsd:nonNegativeInteger.			
		new Checker().validateFragment(parseFragment(validEmotionStream("duration=\"-1\"", "")));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion423() throws Exception {
		// 423	time-ref-uri	[2.4.2]	Y	N	The value of the "time-ref-uri" attribute of <emotion>, if present, MUST be of type xsd:anyURI.			
		new Checker().validateFragment(parseFragment(validEmotionStream("time-ref-uri=\";:\"", "")));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion424() throws Exception {
		// 424	time-ref-anchor-point	[2.4.2]	Y	N	The value of the "time-ref-anchor-point" attribute of <emotion>, if present, MUST be either "start" or "end".			
		new Checker().validateFragment(parseFragment(validEmotionStream("time-ref-anchor-point=\"-1\"", "")));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion425() throws Exception {
		// 425	offset-to-start	[2.4.2]	Y	N	The value of the "offset-to-start" attribute of <emotion>, if present, MUST be of type xsd:integer.
		new Checker().validateFragment(parseFragment(validEmotionStream("offset-to-start=\"-1.2\"", "")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion500() throws Exception {
		// 500	value	[2.5.1]	Y	N	The value of a "value" attribute, if present, MUST be a floating point value from the closed interval [0, 1].
		new Checker().validateFragment(parseFragment(emotionStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<category name=\"anger\" value=\"1.1\"/>")));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion501() throws Exception {
		// 501	freq	[2.5.2]	Y	N	The <trace> element MUST have a "freq" attribute.
		new Checker().validateFragment(parseFragment(emotionStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<category name=\"anger\"><trace samples=\"0\"/></category>")));
	}

	@Test
	public void assertion502ok() throws Exception {
		// 502	freq	[2.5.2]	Y	N	The value of the "freq" attribute of <trace> MUST be a positive floating point number followed by optional whitespace followed by "Hz".
		new Checker().validateFragment(parseFragment(emotionStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<category name=\"anger\"><trace freq=\"1.234   Hz\" samples=\"0\"/></category>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion502fail() throws Exception {
		// 502	freq	[2.5.2]	Y	N	The value of the "freq" attribute of <trace> MUST be a positive floating point number followed by optional whitespace followed by "Hz".
		new Checker().validateFragment(parseFragment(emotionStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<category name=\"anger\"><trace freq=\"1.234\" samples=\"0\"/></category>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion503() throws Exception {
		// 503	samples	[2.5.2]	Y	N	The <trace> element MUST have a "samples" attribute.
		new Checker().validateFragment(parseFragment(emotionStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<category name=\"anger\"><trace freq=\"10 Hz\"/></category>")));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion504() throws Exception {
		// 504	samples	[2.5.2]	Y	N	The value of the "samples" attribute of <trace> MUST be a space-separated list of floating point values from the closed interval [0, 1].
		new Checker().validateFragment(parseFragment(emotionStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\"", "<category name=\"anger\"><trace freq=\"10Hz\" samples=\"-0.4 2.0\"/></category>")));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion600() throws Exception {
		// 600	item	[3.1.1]	Y	N	A <vocabulary> element MUST contain one or more <item> elements.
		new Checker().parse(emotionmlStream("", "<vocabulary type=\"category\" id=\"bla\"><info /></vocabulary>"));
	}

	@Test
	public void assertion601() throws Exception {
		// 601	info	[3.1.1]	N	N	A <vocabulary> element MAY contain a single <info> element.
		new Checker().parse(emotionmlStream("", "<vocabulary type=\"category\" id=\"bla\"><info>...</info><item name=\"anger\"/></vocabulary>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion602() throws Exception {
		// 602	type	[3.1.1]	Y	N	A <vocabulary> element MUST contain a "type" attribute.
		new Checker().parse(emotionmlStream("", "<vocabulary id=\"bla\"><item name=\"anger\"/></vocabulary>"));
	}
	
	@Test(expected=NotValidEmotionmlException.class)
	public void assertion603() throws Exception {
		// 603	type	[3.1.1]	Y	N	The value of the "type" attribute of the <vocabulary> element MUST be one of "category", "dimension", "action-tendency" or "appraisal".
		new Checker().parse(emotionmlStream("", "<vocabulary type=\"somethingelse\" id=\"bla\"><item name=\"anger\"/></vocabulary>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion604() throws Exception {
		// 604	id	[3.1.1]	Y	N	A <vocabulary> element MUST contain an "id" attribute
		new Checker().parse(emotionmlStream("", "<vocabulary type=\"category\"><item name=\"anger\"/></vocabulary>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion605() throws Exception {
		// 605	id	[3.1.1]	Y	N	The value of the "id" attribute of the <vocabulary> element MUST be of type xsd:ID .
		new Checker().parse(emotionmlStream("", "<vocabulary type=\"category\" id=\"123\"><item name=\"anger\"/></vocabulary>"));
	}

	@Test
	public void assertion606() throws Exception {
		// 606	info	[3.1.2]	N	N	An <item> element MAY contain a single <info> element.
		new Checker().parse(emotionmlStream("", "<vocabulary type=\"category\" id=\"bla\"><item name=\"anger\"><info>...</info></item></vocabulary>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion607() throws Exception {
		// 607	name	[3.1.2]	Y	N	An <item> element MUST contain a "name" attribute.
		new Checker().parse(emotionmlStream("", "<vocabulary type=\"category\" id=\"bla\"><item/></vocabulary>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion608() throws Exception {
		// 608	name	[3.1.2]	Y	N	An <item> MUST NOT have the same name as any other <item> within the same <vocabulary>.
		new Checker().parse(emotionmlStream("", "<vocabulary type=\"category\" id=\"bla\"><item name=\"anger\"/><item name=\"anger\"/></vocabulary>"));
	}

	@Test(expected=NotValidEmotionmlException.class)
	public void assertion700() throws Exception {
		// 700		[4.1]	Y	N	All EmotionML elements MUST use the EmotionML namespace, "http://www.w3.org/2009/10/emotionml".
		new Checker().parse(emotionmlStream("xmlns:other=\"http://www.example.com\"", "<other:info />"));
	}
	
		
	
	
	
	
	
	

	
				
	
	
	

	
}


