package emotionml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;

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
	
	public static InputStream emotionStream(String attributes, String content) {
		String emotion = "<emotion xmlns=\"http://www.w3.org/2009/10/emotionml\" %s>%s</emotion>";
		return stringAsStream(String.format(emotion, attributes, content));
	}
	
	public static InputStream validEmotionStream(String extraAttributes, String extraContent) {
		return emotionStream("category-set=\"http://www.w3.org/TR/emotion-voc/xml#big6\" "+extraAttributes, "<category name=\"anger\"/>"+extraContent);
	}
	
	
	public static Document parse(InputStream in) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		return factory.newDocumentBuilder().parse(in);
	}
	
	public static DocumentFragment parseFragment(InputStream in) throws Exception {
		Document doc = parse(in);
		DocumentFragment frag = doc.createDocumentFragment();
		frag.appendChild(doc.getDocumentElement());
		return frag;
	}
}
