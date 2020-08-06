package com.cg.healthcare.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("3")
public class GeneralBed extends Bed{

	private static final long serialVersionUID = 1L;

	public GeneralBed() {
		
	}
	
	public GeneralBed(double pricePerDay) {
		super(pricePerDay);
	}
}
