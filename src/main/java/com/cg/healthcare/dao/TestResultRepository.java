package com.cg.healthcare.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.cg.healthcare.entities.TestResult;
import com.cg.healthcare.entities.TestResultId;

public interface TestResultRepository extends JpaRepository<TestResult, TestResultId>{
	@Query("SELECT test FROM TestResult test WHERE test.id.appointmentId:= appointmentId")
	List<TestResult> findAllTests();
}
