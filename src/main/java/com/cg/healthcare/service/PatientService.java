package com.cg.healthcare.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
import com.cg.healthcare.exception.NoTestTakenException;
import com.cg.healthcare.exception.NoVacantBedForPatient;

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

	
	/*
	 * Pritam starts
	 */
	
	//fetch patient details from username
	@Override
	public Patient getPatientByUserName(String patientUserName) {
		User user= userRepository.findByUsername(patientUserName);
		Patient patient=patientRepository.getOne(user.getId());
		return patient;
	}
	
	//fetch all the TestResult taken by the patient
	@Override
	public Set<TestResult> getAllTestResult(String patientUserName){
		return null;
	}
	
	//fetch all the vacant beds
	@Override
	public DiagnosticCenter getDiagnosticCenterByUsername(String diagnosticCenterUsername) {
		User user = userRepository.findByUsername(diagnosticCenterUsername);
		DiagnosticCenter diagnosticCenter = diagnosticCenterRepo.getOne(user.getId());
		return diagnosticCenter;
	
	}
	

	@Override
	public Set<Bed> getAllBed(String diagnosticCenterUserName) throws Exception{

		
		DiagnosticCenter diagnosticCenter = getDiagnosticCenterByUsername(diagnosticCenterUserName);
		Set<Bed> vacantBeds=diagnosticCenter.getBeds().stream().filter(b->b.isOccupied()==false).collect(Collectors.toSet());
		if(vacantBeds.isEmpty()) {
			LOGGER.error("No Vacant Bed Available Right Now");
			throw new NoVacantBedForPatient("No Vacant Bed Now");
			}
		else {
			LOGGER.info("Vacant Bed Found");
			return vacantBeds;}
		
	}
	
	//apply for bed
	@Override
	public boolean applyForBed(int appointmentId) {
		Appointment appointment= appointmentRepository.getOne(appointmentId);
		//Patient patient=appointment.getPatient();
		DiagnosticCenter diagnosticCenter=appointment.getDiagnosticCenter();
		Bed bed = diagnosticCenter.getBeds().stream().filter(b->b.isOccupied()==false).findFirst().get();
		if(bed==null) {
			
			LOGGER.info("No Bed Available adding Bed Request to Waiting Bed List");
			WaitingPatient waitingPatient=new WaitingPatient();
			waitingPatient.setAppointment(appointment);
			waitingPatient.setRequestedOn(Timestamp.valueOf(LocalDateTime.now()));
			
			return false;
			}
		else {
			LOGGER.info("Successfully Applied for Bed");
			bed.setOccupied(true);
			bed.setAppointment(appointment);
			return true;
			}
	}
	
	//view bed allocation status and details(example no of days and Total price)
	@Override
	public Bed viewBedStatus(int appointmentId) throws Exception{
		Appointment appointment= appointmentRepository.getOne(appointmentId);
		DiagnosticCenter diagnosticCenter= appointment.getDiagnosticCenter();
		Bed bed = diagnosticCenter.getBeds().stream().filter(b->b.getAppointment().getId()==appointmentId).findFirst().get();
		if (bed==null) {
			LOGGER.error("No Bed Booked Till Now");
			throw new BedNotFoundException("Bed Not Found","No Bed booked");
			}
		else {
			LOGGER.info("Booked Bed Found");
			return bed;
			}
	}
	
	// view test result for patient
	@Override
	public TestResult viewTestResult(int testResultId) throws Exception{
		TestResult testResult=testResultRepository.getOne(testResultId);
		if(testResult==null) {
			LOGGER.error("No Test Report are Present");
			throw new NoTestTakenException("No Test Taken");
			}
		else {
			LOGGER.info("Test Result Found");
			return testResult;
			}
	}
	
	/* 
	 * 
	 * Pritam ends
	 * 
	   */
	
	
	
	

}
