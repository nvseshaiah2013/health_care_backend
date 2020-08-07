package com.cg.healthcare.exception;

public class BedNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String header;
	private String message;
	
	public BedNotFoundException(String header, String message) {
		super();
		this.message = message;
		this.header = header;
	}
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "BedNotFoundException [header=" + header + ", message=" + message + "]";
	}
	
	
}
