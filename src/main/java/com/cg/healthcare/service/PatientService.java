package com.cg.healthcare.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
import com.cg.healthcare.entities.GeneralBed;
import com.cg.healthcare.entities.Patient;
import com.cg.healthcare.entities.TestResult;
import com.cg.healthcare.entities.TestResultId;
import com.cg.healthcare.entities.User;
import com.cg.healthcare.entities.WaitingPatient;

@Service
@Transactional
public class PatientService {
	
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
	public Patient getPatientByUserName(String patientUserName) {
		User user= userRepository.findByUsername(patientUserName);
		Patient patient=patientRepository.getOne(user.getId());
		return patient;
	}
	
	//fetch all the TestResult taken by the patient
	public Set<TestResult> getAllTestResult(String patientUserName){
		return null;
	}
	
	//fetch all the vacant beds
	public DiagnosticCenter getDiagnosticCenterByUsername(String diagnosticCenterUsername) {
		User user = userRepository.findByUsername(diagnosticCenterUsername);
		DiagnosticCenter diagnosticCenter = diagnosticCenterRepo.getOne(user.getId());
		return diagnosticCenter;
	}
	
	public Set<Bed> getAllBed(String diagnosticCenterUserName){
		
		DiagnosticCenter diagnosticCenter = getDiagnosticCenterByUsername(diagnosticCenterUserName);
		Set<Bed> vacantBeds=diagnosticCenter.getBeds().stream().filter(b->b.isOccupied()==false).collect(Collectors.toSet());
		return vacantBeds;
		
	}
	
	//apply for bed
	public boolean applyForBed(int appointmentId) {
		Appointment appointment= appointmentRepository.getOne(appointmentId);
		Patient patient=appointment.getPatient();
		DiagnosticCenter diagnosticCenter=appointment.getDiagnosticCenter();
		Bed bed = diagnosticCenter.getBeds().stream().filter(b->b.isOccupied()==false).findFirst().get();
		if(bed==null) {
			
			WaitingPatient waitingPatient=new WaitingPatient();
			waitingPatient.setAppointment(appointment);
			waitingPatient.setRequestedOn(Timestamp.valueOf(LocalDateTime.now()));
			
			return false;
			}
		else {
			bed.setOccupied(true);
			bed.setAppointment(appointment);
			return true;
			}
	}
	
	//view bed allocation status and details(example no of days and Total price)
	public Bed viewBedStatus(int appointmentId) throws Exception{
		Appointment appointment= appointmentRepository.getOne(appointmentId);
		DiagnosticCenter diagnosticCenter= appointment.getDiagnosticCenter();
		Bed bed = diagnosticCenter.getBeds().stream().filter(b->b.getAppointment().getId()==appointmentId).findFirst().get();
		if (bed==null)
			throw new Exception("No Bed booked");
		else
			return bed;
	}
	
	// view test result for patient
	public TestResult viewTestResult(TestResultId testResultId) throws Exception{
		TestResult testResult=testResultRepository.getOne(testResultId);
		if(testResult==null)
			throw new Exception("No Test");
		else
			return testResult;
	}
	
	/* 
	 * 
	 * Pritam ends
	 * 
	   */
	
	
	
	

}
