package com.cts.interim.beta.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5994008560800399391L;
	private String message;

	public ResourceNotFoundException(String msg) {
		super(msg);
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

}
