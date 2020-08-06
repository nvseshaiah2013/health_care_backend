package com.cg.healthcare.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("4")
public class VentilatorBed extends Bed{

	private static final long serialVersionUID = 1L;
	public VentilatorBed() {
		
	}
	
	public VentilatorBed(double pricePerDay) {
		super(pricePerDay);
	}
}
