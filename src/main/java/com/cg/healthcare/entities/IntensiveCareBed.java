package com.cg.healthcare.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name="ICU_BED")
public class IntensiveCareBed extends Bed{
	private static final long serialVersionUID = 1L;
	
	@Column(name="IS_KNEE", nullable = false)
	private boolean isKneeTiltAvailable;
	
	@Column(name = "IS_HEAD", nullable = false)
	private boolean isHeadTiltAvailable;
	
	@Column(name = "IS_ELECTRIC", nullable = false)
	private boolean isElectric;
	
	@Column(name = "NO_FUNCS", nullable = false)
	@Range(min = 3, max=5)
	private int noOfFunctions;
	
	public IntensiveCareBed() {
		
	}
	
	

	public IntensiveCareBed(double pricePerDay,boolean isKneeTiltAvailable, boolean isHeadTiltAvailable, boolean isElectric,
			@Range(min = 3, max = 5) int noOfFunctions) {
		super(pricePerDay);
		this.isKneeTiltAvailable = isKneeTiltAvailable;
		this.isHeadTiltAvailable = isHeadTiltAvailable;
		this.isElectric = isElectric;
		this.noOfFunctions = noOfFunctions;
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

	
}
