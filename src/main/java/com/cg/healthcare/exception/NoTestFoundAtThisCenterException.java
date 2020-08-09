package com.cg.healthcare.exception;

public class NoTestFoundAtThisCenterException extends RuntimeException{

	public NoTestFoundAtThisCenterException(String message) {
		super(message);
	}
}
