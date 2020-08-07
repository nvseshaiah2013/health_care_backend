package com.cg.healthcare.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="ICCU_BED")
public class IntensiveCriticalCareBed extends Bed{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "BAT_BACKUP")
	private boolean batteryBackup;
	
	@Column(name = "ABS")
	private boolean hasABS;
	
	@Column(name = "REMOTE_OP")
	private boolean remoteOperated;
	
	@Column(name="TYPE", nullable = false)
	@Pattern(regexp = "Standard|Fowler|Manual", message = "Types can be Standard or Fowler or Manual")
	private String type;
	
	public IntensiveCriticalCareBed()
	{
		
	}

	public IntensiveCriticalCareBed(double pricePerDay,boolean batteryBackup, boolean hasABS, boolean remoteOperated, String type) {
		super(pricePerDay);
		this.batteryBackup = batteryBackup;
		this.hasABS = hasABS;
		this.remoteOperated = remoteOperated;
		this.type = type;
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
	
	

}
