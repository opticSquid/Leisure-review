package com.cts.interim_project.Service_Providers.Exceptions;

public class ProviderNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6406749517618158767L;
	private String message;

	public ProviderNotFoundException(String message) {
		super(message);
		this.message = message;
	}

	public ProviderNotFoundException() {

	}

	public String getMessage() {
		return message;
	}

}
