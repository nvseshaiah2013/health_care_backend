package com.cg.healthcare.exception;

public class DiagnosticCenterNotPresentException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DiagnosticCenterNotPresentException(String message) {
		super(message);
	}

}
