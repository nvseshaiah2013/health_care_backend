package com.cg.healthcare.responses;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

public class ErrorMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	private HttpStatus status;
	private String message;
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ErrorMessage(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	@Override
	public String toString() {
		return "{ \"message\":" + message + ", \"status\":" + status + "}";
	}
	
	
}
