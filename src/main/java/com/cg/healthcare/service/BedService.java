package com.cg.healthcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.healthcare.dao.BedRepository;
import com.cg.healthcare.entities.Bed;
@Service
@Transactional
public class BedService implements BedServiceInterface{
	@Autowired
	private BedRepository bedDao;

	
	public List<Bed> getBeds() {
		return bedDao.getAllBeds();
		
	}
	
	public boolean addBed(Bed bed) {
		return bedDao.addBed(bed);
	}
	
	@Override
	public List<Bed> admitPatient() {
		return bedDao.getVacantBeds();
		
	}
	@Override
	public List<Bed> canNotAdmitPatient(){
		return bedDao.getVacantBeds();
	}
	@Override
	public List<Bed> dischargePatient() {
		return bedDao.deallocateAssignedBed();
		
	}

}
