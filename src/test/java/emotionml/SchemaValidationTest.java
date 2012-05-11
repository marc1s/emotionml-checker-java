package emotionml;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import org.junit.Test;
import org.w3c.dom.Document;

public class SchemaValidationTest {

	@Test
	public void validatingParseFromInputStream() throws Exception {
		// setup SUT
		Checker emotionmlChecker = new Checker();
		InputStream sampleStream = SchemaValidationTest.class.getResourceAsStream("doc1.emotionml"); 
		// exercise SUT
		Document emotionml = emotionmlChecker.parse(sampleStream);
		// verify
		assertNotNull(emotionml);
	}
	
	@Test
	public void canInstantiateSchema() throws Exception {
		Checker c = new Checker();
		assertNotNull(c.getEmotionmlSchema());
	}
}
