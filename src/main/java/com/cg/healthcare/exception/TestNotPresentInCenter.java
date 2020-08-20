   package com.cg.healthcare.exception;

public class TestNotPresentInCenter extends Exception{

	private static final long serialVersionUID = 1L;

	public TestNotPresentInCenter(String message) {
		super(message);
	}
}
