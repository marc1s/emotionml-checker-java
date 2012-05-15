package emotionml.exceptions;

public class NotValidEmotionmlException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotValidEmotionmlException() {
	}

	public NotValidEmotionmlException(String msg) {
		super(msg);
	}

	public NotValidEmotionmlException(Throwable cause) {
		super(cause);
	}

	public NotValidEmotionmlException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
