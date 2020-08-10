package com.cg.healthcare.service;

import java.util.List;

import com.cg.healthcare.entities.Bed;

public interface BedServiceInterface {
	public List<Bed> admitPatient();
	public List<Bed> canNotAdmitPatient();
	public List<Bed> dischargePatient();

}
