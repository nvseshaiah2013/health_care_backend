package com.cg.healthcare.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.healthcare.entities.Bed;


@Repository
public interface BedRepository extends JpaRepository<Bed, Integer>{
	
//	@Query("SELECT bed FROM Bed WHERE bed.isOccupied = ?1")
//	public boolean getVacantBeds(boolean isOccupied);
//	public boolean deallocateAssignedBed();
//	public boolean addBed(Bed bed);
//	public List<Bed> getAllBeds();
//	public List<Bed> getAllBeds();
}



//public interface BedRepository extends JpaRepository<Bed, Integer>{
//
//
//}


