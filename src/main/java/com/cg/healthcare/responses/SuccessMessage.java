package com.cg.healthcare.responses;

public class SuccessMessage {

	private String header;
	private String message;
	public SuccessMessage() {
		
	}
	
	public SuccessMessage(String header,String message) {
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
}
