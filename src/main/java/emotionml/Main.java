package emotionml;

import java.io.FileInputStream;

import emotionml.exceptions.ConfigurationException;

public class Main {

	public static void main(String[] args) throws ConfigurationException {
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
}
