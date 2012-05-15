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
	
	public static InputStream emotionmlStream(String attributes, String content) {
		String emotionml = "<emotionml version=\"1.0\" xmlns=\"http://www.w3.org/2009/10/emotionml\" %s>%s</emotionml>";
		return stringAsStream(String.format(emotionml, attributes, content));
	}
}
