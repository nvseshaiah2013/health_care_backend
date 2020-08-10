package com.cg.healthcare.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cg.healthcare.entities.Bed;

@Repository
public interface BedDao{
	public List<Bed> getAllBeds();
	public List<Bed> getVacantBeds();
	public List<Bed> deallocateAssignedBed();
}