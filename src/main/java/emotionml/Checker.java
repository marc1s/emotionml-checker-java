package emotionml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import emotionml.exceptions.ConfigurationException;

public class Checker {
	private DocumentBuilderFactory factory;
	private Schema emotionmlSchema;
	private DocumentBuilder builder;

	public Checker() throws ConfigurationException {
		factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		try {
			emotionmlSchema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(Checker.class.getResource("emotionml.xsd"));
		} catch (SAXException e) {
			throw new ConfigurationException("Could not instantiate schema", e);
		}
		factory.setSchema(emotionmlSchema);
		try {
			builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new ErrorHandler());
		} catch (ParserConfigurationException e) {
			throw new ConfigurationException("Cannot instantiate parser", e);
		} 
	}

	public Document parse(InputStream emotionmlStream) throws SAXException, IOException {
		return builder.parse(emotionmlStream);
	}
	
	public Schema getEmotionmlSchema() {
		return emotionmlSchema;
	}
	
	private static class ErrorHandler implements org.xml.sax.ErrorHandler {

		public void error(SAXParseException exception) throws SAXException {
			throw exception;
		}

		public void fatalError(SAXParseException exception) throws SAXException {
			throw exception;
		}

		public void warning(SAXParseException exception) throws SAXException {
			throw exception;
		}
		
	}

}
