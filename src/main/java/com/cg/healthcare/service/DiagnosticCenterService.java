package com.cg.healthcare.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.healthcare.dao.BedRepository;
import com.cg.healthcare.entities.Bed;

@Service
@Transactional


public class DiagnosticCenterService {
	
	BedRepository dao;

	public List<Bed> getBeds() {
		return dao.getAllBeds();
		
	}
	public List<Bed> admitPatient() {
		return dao.getVacantBeds();
		
	}
	public List<Bed> canNotAdmitPatient(){
		return dao.getVacantBeds();
	}
	public List<Bed> dischargePatient() {
		return dao.deallocateAssignedBed();
		
	}
}
