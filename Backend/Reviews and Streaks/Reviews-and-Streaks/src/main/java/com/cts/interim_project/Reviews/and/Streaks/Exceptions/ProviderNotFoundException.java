package com.cts.interim_project.Reviews.and.Streaks.Exceptions;

public class ProviderNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5542145330217050418L;
	private String message;

	public ProviderNotFoundException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
