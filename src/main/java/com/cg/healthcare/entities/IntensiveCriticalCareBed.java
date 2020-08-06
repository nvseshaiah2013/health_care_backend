package com.cg.healthcare.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class IntensiveCriticalCareBed extends Bed{

	private static final long serialVersionUID = 1L;
	
	public IntensiveCriticalCareBed()
	{
		
	}
	
	public IntensiveCriticalCareBed(double pricePerDay) {
			super(pricePerDay);
	}

}
