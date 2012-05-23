package emotionml;

import java.io.FileInputStream;

import emotionml.exceptions.ConfigurationException;

public class Main {

	public static void main(String[] args) throws ConfigurationException {
		if (args.length == 0) {
			usage();
			System.exit(0);
		}
		Checker checker = new Checker();
		for (String filename : args) {
			System.err.print(filename+"... ");
			try {
				FileInputStream in = new FileInputStream(filename);
				checker.parse(in);
				System.err.println(" ok!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void usage() {
		System.out.println("Usage:");
		System.out.println();
		System.out.println("java -jar emotionml-checker-java.jar file.emotionml [more emotionml files]");
		System.out.println();
		System.out.println("where `file.emotionml` is an XML file containing the EmotionML document to be validated.");
	}
}
