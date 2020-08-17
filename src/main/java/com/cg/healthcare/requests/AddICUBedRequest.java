package com.cg.healthcare.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class AddICUBedRequest {

	@Min(value = 1000, message = "Min Price can be 1000 Rs.")
	private double pricePerDay;

	@NotNull(message = "Knee Tilt Detail is Required")
	private boolean isKneeTiltAvailable;
	
	@NotNull(message = "Head Tilt Detail is Required")
	private boolean isHeadTiltAvailable;
	
	@NotNull(message = "Electronic Category is Required")
	private boolean isElectric;

	@Range(min = 3, max=5, message = "The number of functions can be in range of 3 and 5")
	private int noOfFunctions;
	
	@Min(value = 1, message = "No Of Beds cannot be less than 1")
	private int noOfBeds;

	public AddICUBedRequest(@Min(value = 1000, message = "Min Price can be 1000 Rs.") double pricePerDay,
			@NotNull(message = "Knee Tilt Detail is Required") boolean isKneeTiltAvailable,
			@NotNull(message = "Head Tilt Detail is Required") boolean isHeadTiltAvailable,
			@NotNull(message = "Electronic Category is Required") boolean isElectric,
			@Range(min = 3, max = 5) int noOfFunctions,
			@Min(value = 1, message = "No Of Beds cannot be less than 1")
	int noOfBeds) {
		this.pricePerDay = pricePerDay;
		this.isKneeTiltAvailable = isKneeTiltAvailable;
		this.isHeadTiltAvailable = isHeadTiltAvailable;
		this.isElectric = isElectric;
		this.noOfFunctions = noOfFunctions;
		this.noOfBeds = noOfBeds;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public boolean isKneeTiltAvailable() {
		return isKneeTiltAvailable;
	}

	public void setKneeTiltAvailable(boolean isKneeTiltAvailable) {
		this.isKneeTiltAvailable = isKneeTiltAvailable;
	}

	public boolean isHeadTiltAvailable() {
		return isHeadTiltAvailable;
	}

	public void setHeadTiltAvailable(boolean isHeadTiltAvailable) {
		this.isHeadTiltAvailable = isHeadTiltAvailable;
	}

	public boolean isElectric() {
		return isElectric;
	}

	public void setElectric(boolean isElectric) {
		this.isElectric = isElectric;
	}

	public int getNoOfFunctions() {
		return noOfFunctions;
	}

	public void setNoOfFunctions(int noOfFunctions) {
		this.noOfFunctions = noOfFunctions;
	}

	public int getNoOfBeds() {
		return noOfBeds;
	}

	public void setNoOfBeds(int noOfBeds) {
		this.noOfBeds = noOfBeds;
	}
	
	
	
}
