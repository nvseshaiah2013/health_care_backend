package com.cg.healthcare.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "G_BEDS")
public class GeneralBed extends Bed{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "IS_MOVABLE")
	private boolean isMovable;
	
	@Column(name = "MATERIAL", nullable = false)
	@Pattern(regexp = "Wood|Steel", message = "FrameMaterial can only be Steel or Wood")
	private String frameMaterial;

	public GeneralBed() {
		
	}	

	public GeneralBed(double pricePerDay,boolean isMovable,
			@Pattern(regexp = "Wood|Steel", message = "Frame Material can only be Steel or Wood") String frameMaterial) {
		super(pricePerDay);
		this.isMovable = isMovable;
		this.frameMaterial = frameMaterial;
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
	
	
}
