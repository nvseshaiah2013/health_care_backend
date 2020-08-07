package com.cg.healthcare.exception;

public class InvalidICUBedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String header;
	
	public InvalidICUBedException(String header, String message)
	{
		super(message);
		this.header = header;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	@Override
	public String toString() {
		return "InvalidICUBedException [header=" + header + "]";
	}
	
}
