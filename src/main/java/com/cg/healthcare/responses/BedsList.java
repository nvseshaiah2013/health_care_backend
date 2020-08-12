package com.cg.healthcare.responses;

import java.util.HashSet;
import java.util.Set;

import com.cg.healthcare.entities.GeneralBed;
import com.cg.healthcare.entities.IntensiveCareBed;
import com.cg.healthcare.entities.IntensiveCriticalCareBed;
import com.cg.healthcare.entities.VentilatorBed;

public class BedsList {

	private Set<IntensiveCareBed> intensiveCareBeds;
	
	private Set<GeneralBed> generalBeds;
	
	private Set<IntensiveCriticalCareBed> intensiveCriticalCareBeds;
	
	private Set<VentilatorBed> ventilatorBeds;	

	public BedsList() {
		this.intensiveCareBeds = new HashSet<>();
		this.generalBeds = new HashSet<>();
		this.intensiveCriticalCareBeds = new HashSet<>();
		this.ventilatorBeds = new HashSet<>();
	}

	public Set<IntensiveCareBed> getIntensiveCareBeds() {
		return intensiveCareBeds;
	}

	public void setIntensiveCareBeds(Set<IntensiveCareBed> intensiveCareBeds) {
		this.intensiveCareBeds = intensiveCareBeds;
	}

	public Set<GeneralBed> getGeneralBeds() {
		return generalBeds;
	}

	public void setGeneralBeds(Set<GeneralBed> generalBeds) {
		this.generalBeds = generalBeds;
	}

	public Set<IntensiveCriticalCareBed> getIntensiveCriticalCareBeds() {
		return intensiveCriticalCareBeds;
	}

	public void setIntensiveCriticalCareBeds(Set<IntensiveCriticalCareBed> intensiveCriticalCareBeds) {
		this.intensiveCriticalCareBeds = intensiveCriticalCareBeds;
	}

	public Set<VentilatorBed> getVentilatorBeds() {
		return ventilatorBeds;
	}

	public void setVentilatorBeds(Set<VentilatorBed> ventilatorBeds) {
		this.ventilatorBeds = ventilatorBeds;
	}
	
	
}
