package com.cg.healthcare.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.healthcare.entities.Bed;

//public interface BedRepository extends JpaRepository<Bed, Integer>{
//
//
//}
@Repository
public interface BedRepository{
	public List<Bed> getAllBeds();
	public List<Bed> getVacantBeds();
	public List<Bed> deallocateAssignedBed();
}