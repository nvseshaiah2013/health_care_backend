package com.cg.healthcare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.healthcare.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer>{
	
}
