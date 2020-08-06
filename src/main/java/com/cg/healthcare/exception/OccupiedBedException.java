package com.cg.healthcare.exception;

public class OccupiedBedException extends Exception {

	private static final long serialVersionUID = 1L;

	private String header;
	private String message;
	
	public OccupiedBedException(String header, String message) {
		super();
		this.header = header;
		this.message =message;
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
		return "OccupiedBedException [header=" + header + ", message=" + message + "]";
	}

}
