package com.cts.interim.beta.exceptions;

public class ResourceNotFoundEception extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5994008560800399391L;
	private String message;

	public ResourceNotFoundEception(String msg) {
		super(msg);
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

}
