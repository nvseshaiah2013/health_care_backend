package com.cg.healthcare.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "V_BEDS")
public class VentilatorBed extends Bed {

	private static final long serialVersionUID = 1L;

	@Column(name = "RESP_RATE", nullable = false)
	@Range(min = 10, max = 15, message = "Respiratory Rate can only be between 10 and 15")
	private int respiratoryRate;

	@Column(name = "TYPE", nullable = false)
	@Pattern(regexp = "Volume|Time|Pressure", message = "Type of Ventilator can be only of Volume, Time or Pressure")
	private String type;

	public VentilatorBed() {

	}

	public VentilatorBed(double pricePerDay,
			@Range(min = 10, max = 15, message = "Respiratory Rate can only be between 10 and 15") int respiratoryRate,
			@Pattern(regexp = "Volume|Time|Pressure", message = "Type of Ventilator can be only of Volume, Time or Pressure") String type) {
		super(pricePerDay);
		this.respiratoryRate = respiratoryRate;
		this.type = type;
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

}
