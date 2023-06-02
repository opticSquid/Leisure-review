package com.cts.interim.beta.exceptions;

public class UserNotValidException extends RuntimeException {
	private static final long serialVersionUID = 559627242079357995L;
	private String errorMessage;
	private Throwable cause;

	public UserNotValidException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
		this.errorMessage = errorMessage;
		this.cause = cause;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public Throwable getCause() {
		return cause;
	}

}
