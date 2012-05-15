package emotionml;

import static emotionml.TestUtil.resourceAsStream;
import static emotionml.TestUtil.stringAsStream;
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
}
