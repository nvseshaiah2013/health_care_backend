package com.cg.healthcare.exception;

public class DataBaseException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataBaseException() {
		super("Some problem occured with database. Try again later");
	}
}