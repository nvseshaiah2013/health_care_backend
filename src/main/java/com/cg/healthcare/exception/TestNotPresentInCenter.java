   package com.cg.healthcare.exception;

public class TestNotPresentInCenter extends RuntimeException{

	public TestNotPresentInCenter(String message) {
		super(message);
	}
}
