package com.cts.interim.beta.exceptions;

public class DataCouldNotbeSavedException extends IllegalArgumentException {
	private static final long serialVersionUID = -8244760538107820448L;
	private String errorMessage;
	private Throwable cause;

	public DataCouldNotbeSavedException(String errorMessage, Throwable cause) {
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
