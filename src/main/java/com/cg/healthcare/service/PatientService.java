package com.cg.healthcare.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.healthcare.dao.AppointmentRepository;
import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.PatientRepository;
import com.cg.healthcare.dao.TestResultRepository;
import com.cg.healthcare.dao.UserRepository;
import com.cg.healthcare.entities.Appointment;
import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.Patient;
import com.cg.healthcare.entities.TestResult;
import com.cg.healthcare.entities.User;
import com.cg.healthcare.entities.WaitingPatient;
import com.cg.healthcare.exception.BedNotFoundException;
import com.cg.healthcare.exception.NoAppointmentException;
import com.cg.healthcare.exception.NoTestTakenException;
import com.cg.healthcare.exception.NoVacantBedForPatient;

/**
 * @author Pritam 
 * @author Vikram Start date : 06/08/2020
 * 
 *         Description: This class implements the Service provided to the
 *         Patient.
 * 
 */

@Service
@Transactional
public class PatientService implements IPatientService {
	
	private static final Logger LOGGER  = LoggerFactory.getLogger(PatientService.class);

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private TestResultRepository testResultRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private DiagnosticCenterRepository diagnosticCenterRepo;

	
	/**
	 * Function to fetch Patient by UserName;
	 *
	 * @param patientUserName
	 * @return patient : details of the patient.
	 *
	 */
	@Override
	public Patient getPatientByUserName(String patientUserName) {
		User user = userRepository.findByUsername(patientUserName);
		Patient patient = patientRepository.getOne(user.getId());
		return patient;
	}

	/**
	 * To fetch all the test results taken by the Patient.
	 * 
	 * @param patientUserName
	 * @return Set<TestResult> collection of the test results taken by patient
	 * @exception NoAppointmentException and NoTestTakenException
	 * 
	 */
	@Override
	public Set<TestResult> getAllTestResult(String patientUserName) throws Exception {
		Patient patient = getPatientByUserName(patientUserName);
		Set<Appointment> appointments = patient.getAppointments();
		if (appointments.isEmpty()) {
			throw new NoAppointmentException("Appointment Exception","No Appointment Present");
		}
		Set<TestResult> tests = new HashSet<>();
		for (Appointment a : appointments) {
			tests.add(a.getTestResult());
		}
		if (tests.isEmpty()) {
			throw new NoTestTakenException("No Test Taken");
		}
		return tests;
	}

	/**
	 * This function fetches the diagnostic center by user name
	 * 
	 * @param diagnosticCenterUsername
	 * @return diagnosticCenter : Diagnostic center entity.
	 * 
	 */
	@Override
	public DiagnosticCenter getDiagnosticCenterByUsername(String diagnosticCenterUsername) {
		User user = userRepository.findByUsername(diagnosticCenterUsername);
		DiagnosticCenter diagnosticCenter = diagnosticCenterRepo.getOne(user.getId());
		return diagnosticCenter;

	}

	/**
	 * This fucntion fetches all the beds which are vacant in the diagnostic center.
	 * 
	 * @param diagnosticCenterUserName
	 * @return Set<vacantBeds>
	 * @exception NoVacantBedForPatient
	 * 
	 */
	@Override
	public Set<Bed> getAllBed(String diagnosticCenterUserName) throws Exception {
		DiagnosticCenter diagnosticCenter = getDiagnosticCenterByUsername(diagnosticCenterUserName);
		Set<Bed> vacantBeds = diagnosticCenter.getBeds().stream().filter(b -> b.isOccupied() == false)
				.collect(Collectors.toSet());
		if (vacantBeds.isEmpty()) {
			LOGGER.error("No Vacant Bed Available Right Now");
			throw new NoVacantBedForPatient("No Vacant Bed Now");
		} else {
			LOGGER.info("Vacant Bed Found");
			return vacantBeds;
		}

	}

	/**
	 * This Functionality has been removed from patient service. It cannot be
	 * accessed from patient role. It has been added to the diagnostic Center role.
	 * 
	 */

	@Override
	public boolean applyForBed(int appointmentId) {
		Appointment appointment = appointmentRepository.getOne(appointmentId);

		DiagnosticCenter diagnosticCenter = appointment.getDiagnosticCenter();
		Bed bed = diagnosticCenter.getBeds().stream().filter(b -> b.isOccupied() == false).findFirst().get();
		if (bed == null) {

			LOGGER.info("No Bed Available adding Bed Request to Waiting Bed List");
			WaitingPatient waitingPatient = new WaitingPatient();
			waitingPatient.setAppointment(appointment);
			waitingPatient.setRequestedOn(Timestamp.valueOf(LocalDateTime.now()));

			return false;
		} else {
			LOGGER.info("Successfully Applied for Bed");
			bed.setOccupied(true);
			bed.setAppointment(appointment);
			return true;
		}
	}

	/**
	 * This function view the current status of the bed booked by patient. It is
	 * also used to fetch details of the bed booked like total price, no of days
	 * since its booking.
	 * 
	 * @param appointmentId : patient has appointment id when he make appointment
	 *                      with diagnostic center.
	 * @return bed : Bed entity
	 * @throws BedNotFoundException
	 */

	@Override
	public Bed viewBedStatus(int appointmentId) throws Exception {
		Appointment appointment = appointmentRepository.getOne(appointmentId);
		DiagnosticCenter diagnosticCenter = appointment.getDiagnosticCenter();
		Bed bed = diagnosticCenter.getBeds().stream().filter(b -> b.getAppointment().getId() == appointmentId)
				.findFirst().get();
		if (bed == null) {
			LOGGER.error("No Bed Booked Till Now");
			throw new BedNotFoundException("Bed Not Found", "No Bed booked");
		} else {
			LOGGER.info("Booked Bed Found");
			return bed;
		}
	}

	/**
	 * This function return a single test result
	 * 
	 * @param testResultId
	 * @return testResult : TestResult Entity
	 * @exception NoTestTakenException
	 * 
	 */
	@Override
	public TestResult viewTestResult(int testResultId) throws Exception {
		TestResult testResult = testResultRepository.getOne(testResultId);
		if (testResult == null) {
			LOGGER.error("No Test Report are Present");
			throw new NoTestTakenException("No Test Taken");
		} else {
			LOGGER.info("Test Result Found");
			return testResult;
		}
	}

	/*
	 * Pritam ends
	 */	
	
	
	

}
