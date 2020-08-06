package com.cg.healthcare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.healthcare.entities.TestResult;
import com.cg.healthcare.entities.TestResultId;

public interface TestResultRepository extends JpaRepository<TestResult, TestResultId>{

}
