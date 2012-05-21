package emotionml;

import static emotionml.TestUtil.resourceAsStream;
import static emotionml.TestUtil.stringAsStream;
import static emotionml.TestUtil.parse;
import static emotionml.TestUtil.parseFragment;
import static emotionml.TestUtil.emotionStream;
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
	public void assertions103To105() throws Exception {
		// 103	emotion	[2.1.1]	N	N	The <emotionml> element MAY contain one or more <emotion> elements.			
		// 104	vocabulary	[2.1.1]	N	N	The <emotionml> element MAY contain one or more <vocabulary> elements.			
		// 105	info	[2.1.1]	N	N	The <emotionml> element MAY contain a single <info> element.
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
	public void assertion155() throws Exception {
		// 155	info	[2.1.2]	N	N	The <emotion> element MAY contain a single <info> element.
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
	

}


