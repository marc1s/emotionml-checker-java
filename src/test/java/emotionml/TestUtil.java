package emotionml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TestUtil {
	
	public static InputStream resourceAsStream(String resourceName) {
		return SchemaValidationTest.class.getResourceAsStream(resourceName);
	}
	
	public static InputStream stringAsStream(String xmlDocumentAsString) {
		return new ByteArrayInputStream(xmlDocumentAsString.getBytes());
	}
}
