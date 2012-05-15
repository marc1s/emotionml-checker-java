package emotionml.exceptions;

public class NoSuchVocabularyException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoSuchVocabularyException() {
	}

	public NoSuchVocabularyException(String msg) {
		super(msg);
	}

	public NoSuchVocabularyException(Throwable cause) {
		super(cause);
	}

	public NoSuchVocabularyException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
