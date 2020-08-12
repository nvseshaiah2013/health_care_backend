package com.cg.healthcare.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AddGeneralBedRequest {

	@Min(value = 500,message = "Price Per day cannot be less than 500")
	private double pricePerDay;

	@NotNull(message = "Movability Details should be given")
	private boolean isMovable;
	
	@Pattern(regexp = "Wood|Steel", message = "FrameMaterial can only be Steel or Wood")
	private String frameMaterial;
	
	@Min(value = 1, message = "No Of Beds cannot be less than 1")
	private int noOfBeds;

	public AddGeneralBedRequest(@Min(value = 500, message = "Price Per day cannot be less than 500") double pricePerDay,
			@NotNull(message = "Movability Details should be given") boolean isMovable,
			@Pattern(regexp = "Wood|Steel", message = "FrameMaterial can only be Steel or Wood") String frameMaterial,
			@Min(value = 1, message = "No Of Beds cannot be less than 1")
		int noOfBeds) {
		this.pricePerDay = pricePerDay;
		this.isMovable = isMovable;
		this.noOfBeds = noOfBeds;
		this.frameMaterial = frameMaterial;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public boolean isMovable() {
		return isMovable;
	}

	public void setMovable(boolean isMovable) {
		this.isMovable = isMovable;
	}

	public String getFrameMaterial() {
		return frameMaterial;
	}

	public void setFrameMaterial(String frameMaterial) {
		this.frameMaterial = frameMaterial;
	}

	public int getNoOfBeds() {
		return noOfBeds;
	}

	public void setNoOfBeds(int noOfBeds) {
		this.noOfBeds = noOfBeds;
	}
	
	

	
}
