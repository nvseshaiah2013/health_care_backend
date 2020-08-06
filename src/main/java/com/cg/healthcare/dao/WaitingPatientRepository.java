package com.cg.healthcare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.healthcare.entities.WaitingPatient;

public interface WaitingPatientRepository extends JpaRepository<WaitingPatient, Integer>{

}
