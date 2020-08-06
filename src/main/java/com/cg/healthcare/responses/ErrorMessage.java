package com.cg.healthcare.responses;

import java.io.Serializable;

public class ErrorMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	private String header;
	private String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ErrorMessage(String header, String message) {
		this.header = header;
		this.message = message;
	}

	@Override
	public String toString() {
		return "{ \"message\":" + message + ", \"header\":" + header + "}";
	}
	
	
}
