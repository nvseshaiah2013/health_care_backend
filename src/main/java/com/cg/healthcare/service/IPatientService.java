package com.cg.healthcare.service;

import java.util.Set;

import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.Patient;
import com.cg.healthcare.entities.TestResult;
import com.cg.healthcare.entities.TestResultId;

public interface IPatientService {

	//fetch patient details from username
	Patient getPatientByUserName(String patientUserName);

	//fetch all the TestResult taken by the patient
	Set<TestResult> getAllTestResult(String patientUserName);

	//fetch all the vacant beds
	DiagnosticCenter getDiagnosticCenterByUsername(String diagnosticCenterUsername);

	Set<Bed> getAllBed(String diagnosticCenterUserName);

	//apply for bed
	boolean applyForBed(int appointmentId);

	//view bed allocation status and details(example no of days and Total price)
	Bed viewBedStatus(int appointmentId) throws Exception;

	// view test result for patient
	TestResult viewTestResult(TestResultId testResultId) throws Exception;

}