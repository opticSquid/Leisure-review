package com.cts.interim.beta.exceptions;

public class UserOperationNotPermitted extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8875074645543659034L;
	private String errorMsg;

	public UserOperationNotPermitted(String msg) {
		super(msg);
		this.errorMsg = msg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}