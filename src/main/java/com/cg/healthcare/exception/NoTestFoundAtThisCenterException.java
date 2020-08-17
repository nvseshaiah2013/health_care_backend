package com.cg.healthcare.exception;



public class NoTestFoundAtThisCenterException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NoTestFoundAtThisCenterException(String message) {
		super(message);
	}
}
