package com.cg.healthcare.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class IntensiveCareBed extends Bed{
	private static final long serialVersionUID = 1L;
	
	public IntensiveCareBed() {
		
	}
	
	public IntensiveCareBed(double pricePerDay) {
		super(pricePerDay);
	}

}
