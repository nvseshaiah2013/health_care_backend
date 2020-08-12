package com.cg.healthcare.service;

import java.util.List;
import java.util.Set;

import com.cg.healthcare.entities.Appointment;
import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.DiagnosticTest;
import com.cg.healthcare.requests.DiagnosticCenterSignUpRequest;

public interface IAdminService {

	// Add
	DiagnosticCenter addDiagnosticCenter(DiagnosticCenterSignUpRequest diagnosticCenter) throws Exception;

	// Get by Id
	DiagnosticCenter getDiagnosticCenterById(int diagnosticCenterId);

	// Remove
	List<DiagnosticCenter> removeDiagnosticCenter(int diagnosticCenterId) throws Exception;

	// Update
	DiagnosticCenter updateDiagnosticCenter(DiagnosticCenter diagnosticCenter);

	// GetAll
	List<DiagnosticCenter> getAllDiagnosticCenter();

	void allocateBeds(int diagnosticCenterId, List<Integer> waitingPatientIds, String type) throws Exception;

	Class<?> getBedType(String bedType) throws Exception;

	Set<Bed> getBeds(int diagnosticCenterId) throws Exception;

	List<DiagnosticTest> getAllTest();

	List<DiagnosticCenter> getAllDiagnosticCenters();

	DiagnosticTest addNewTest(DiagnosticTest test);

	DiagnosticTest updateTestDetail(DiagnosticTest test);

	DiagnosticCenter getDiagnosticCentersById(int diagnosticCenterId);

	List<DiagnosticTest> getTestsOfDiagnosticCenter(int centerId);

//	List<DiagnosticTest> addTestToDiagnosticCenter(int centerId, List<DiagnosticTest> tests) throws Exception;

//	List<DiagnosticTest> removeTestFromDiagnosticCenter(int centerId, List<DiagnosticTest> tests) throws Exception;

	List<Bed> listOfVacantBeds();

	/**
	 * Sachin Pant Starts
	 * 
	 */
	List<Appointment> getApppointmentList(int centreId, String test, int status);

	String processAppointment(int centreId, String test, int bursttime, int seats);

	List<DiagnosticTest> removeTestFromDiagnosticCenter(int centerId, DiagnosticTest test) throws Exception;

	List<DiagnosticTest> addTestToDiagnosticCenter(int centerId, DiagnosticTest test) throws Exception;

}