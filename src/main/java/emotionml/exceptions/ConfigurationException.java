package emotionml.exceptions;

public class ConfigurationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConfigurationException() {
	}

	public ConfigurationException(String msg) {
		super(msg);
	}

	public ConfigurationException(Throwable cause) {
		super(cause);
	}

	public ConfigurationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
