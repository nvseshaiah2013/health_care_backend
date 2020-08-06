package com.cg.healthcare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.healthcare.entities.DiagnosticCenter;

public interface DiagnosticCenterRepository extends JpaRepository<DiagnosticCenter, Integer>{

}
