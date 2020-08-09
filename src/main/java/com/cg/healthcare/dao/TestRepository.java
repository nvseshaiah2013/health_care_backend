package com.cg.healthcare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.healthcare.entities.DiagnosticTest;

public interface TestRepository extends JpaRepository<DiagnosticTest, Integer>{

}
