package com.cg.healthcare.service;

import java.util.List;
import java.util.Set;

import com.cg.healthcare.entities.Appointment;
import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.DiagnosticTest;
import com.cg.healthcare.entities.WaitingPatient;
import com.cg.healthcare.exception.DataBaseException;
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
	
	/**
	 * Function To Allocate Beds to Waiting Patients
	 * @param diagnosticCenterId
	 * @param waitingPatientIds
	 * @param type
	 * @throws Exception
	 */

	void allocateBeds(int diagnosticCenterId, List<Integer> waitingPatientIds, String type) throws Exception;

	Set<Bed> getBeds(int diagnosticCenterId) throws Exception;

	List<DiagnosticTest> getAllTest();

	List<DiagnosticCenter> getAllDiagnosticCenters();

	DiagnosticTest addNewTest(DiagnosticTest test);

	DiagnosticTest updateTestDetail(DiagnosticTest test);

	DiagnosticCenter getDiagnosticCentersById(int diagnosticCenterId);

	List<DiagnosticTest> getTestsOfDiagnosticCenter(int centerId) throws Exception;

	List<Bed> listOfVacantBeds();

	List<Appointment> getApppointmentList(int centreId, String test, int status) throws Exception;

	String processAppointment(int centreId, String test, int bursttime, int seats) throws Exception;

	List<DiagnosticTest> removeTestFromDiagnosticCenter(int centerId, DiagnosticTest test) throws Exception;

	List<DiagnosticTest> addTestToDiagnosticCenter(int centerId, DiagnosticTest test) throws Exception;

	/**
	 * Returns the list Of Waiting Patients in the HealthCare system
	 * @return - List<WaitingPatient>
	 *
	 */
	List<WaitingPatient> getWaitingPatients() throws Exception;

}