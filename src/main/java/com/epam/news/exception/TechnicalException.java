package com.epam.news.exception;

public class TechnicalException extends Exception {

    /**
	 * generated serial version uid
	 */
	private static final long serialVersionUID = 4881876207875068577L;

	public TechnicalException() {
        super();
    }

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TechnicalException(Throwable cause) {
        super(cause);
    }
}
