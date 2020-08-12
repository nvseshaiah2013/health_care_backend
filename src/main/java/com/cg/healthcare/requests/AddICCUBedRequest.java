package com.cg.healthcare.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AddICCUBedRequest {

	@Min(value = 2000,message = "Price Per Day cannot be less than 2000")
	private double pricePerDay;
	
	@NotNull(message = "Battery Backup Availability is Required")
	private boolean batteryBackup;
	
	@NotNull(message = "ABS Facility is Required")
	private boolean hasABS;
	
	@NotNull(message = "Remote Operated Facility Details is Required")
	private boolean remoteOperated;
	
	@Pattern(regexp = "Standard|Fowler|Manual", message = "Types can be Standard or Fowler or Manual")
	private String type;

	
	@Min(value = 1, message = "No Of Beds cannot be less than 1")
	private int noOfBeds;
	
	
	public AddICCUBedRequest(
			@Min(value = 2000,message = "Price Per Day cannot be less than 2000")double pricePerDay,
			@NotNull(message = "Battery Backup Availability is Required") boolean batteryBackup,
			@NotNull(message = "ABS Facility is Required") boolean hasABS,
			@NotNull(message = "Remote Operated Facility Details is Required") boolean remoteOperated,
			@Pattern(regexp = "Standard|Fowler|Manual", message = "Types can be Standard or Fowler or Manual") String type,
			@Min(value = 1, message = "No Of Beds cannot be less than 1")
			int noOfBeds) {
		this.pricePerDay = pricePerDay;
		this.batteryBackup = batteryBackup;
		this.hasABS = hasABS;
		this.remoteOperated = remoteOperated;
		this.type = type;
		this.noOfBeds = noOfBeds;
	}

	public boolean isBatteryBackup() {
		return batteryBackup;
	}

	public void setBatteryBackup(boolean batteryBackup) {
		this.batteryBackup = batteryBackup;
	}

	public boolean isHasABS() {
		return hasABS;
	}

	public void setHasABS(boolean hasABS) {
		this.hasABS = hasABS;
	}

	public boolean isRemoteOperated() {
		return remoteOperated;
	}

	public void setRemoteOperated(boolean remoteOperated) {
		this.remoteOperated = remoteOperated;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public int getNoOfBeds() {
		return noOfBeds;
	}

	public void setNoOfBeds(int noOfBeds) {
		this.noOfBeds = noOfBeds;
	}
	
	
	
}
