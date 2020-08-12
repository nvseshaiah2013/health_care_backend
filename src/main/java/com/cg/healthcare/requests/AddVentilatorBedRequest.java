package com.cg.healthcare.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

public class AddVentilatorBedRequest {

	@Min(value = 1000, message = "Min Value of Ventilator Bed is 1000")
	private double pricePerDay;
	
	@Range(min = 10, max = 15, message = "Respiratory Rate can only be between 10 and 15")
	private int respiratoryRate;

	@Pattern(regexp = "Volume|Time|Pressure", message = "Type of Ventilator can be only of Volume, Time or Pressure")
	private String type;
	
	@Min(value = 1, message = "No Of Beds cannot be less than 1")
	private int noOfBeds;

	public AddVentilatorBedRequest(
			@Min(value = 1000, message = "Min Value of Ventilator Bed is 1000") double pricePerDay,
			@Range(min = 10, max = 15, message = "Respiratory Rate can only be between 10 and 15") int respiratoryRate,
			@Pattern(regexp = "Volume|Time|Pressure", message = "Type of Ventilator can be only of Volume, Time or Pressure") String type,
			@Min(value = 1, message = "No Of Beds cannot be less than 1")
			int noOfBeds) {
		this.pricePerDay = pricePerDay;
		this.respiratoryRate = respiratoryRate;
		this.type = type;
		this.noOfBeds = noOfBeds;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public int getRespiratoryRate() {
		return respiratoryRate;
	}

	public void setRespiratoryRate(int respiratoryRate) {
		this.respiratoryRate = respiratoryRate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNoOfBeds() {
		return noOfBeds;
	}

	public void setNoOfBeds(int noOfBeds) {
		this.noOfBeds = noOfBeds;
	}
	
	
	
}
