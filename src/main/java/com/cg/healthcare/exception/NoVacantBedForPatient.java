package com.cg.healthcare.exception;

public class NoVacantBedForPatient extends Exception {

	private static final long serialVersionUID = 1L;

	public NoVacantBedForPatient() {
		super();
	}

	public NoVacantBedForPatient(String message) {
		super(message);
		
	}

	
}
