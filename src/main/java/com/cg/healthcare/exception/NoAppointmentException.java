package com.cg.healthcare.exception;

public class NoAppointmentException extends Exception {

	private static final long serialVersionUID=1L;
	
	private String header;
	
	public NoAppointmentException(String header, String message)
	{
		super(message);
		this.header=header;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	@Override
	public String toString() {
		return "NoAppointmentException [header=" + header + "]";
	}
}
