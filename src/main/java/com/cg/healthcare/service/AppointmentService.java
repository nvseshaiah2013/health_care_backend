package com.cg.healthcare.service;

import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cg.healthcare.dao.AppointmentRepository;
import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.PatientRepository;
import com.cg.healthcare.dao.TestRepository;
import com.cg.healthcare.dao.UserRepository;
import com.cg.healthcare.entities.Appointment;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.DiagnosticTest;
import com.cg.healthcare.entities.Patient;
import com.cg.healthcare.entities.User;
import com.cg.healthcare.exception.DiagnosticCenterNotPresentException;
import com.cg.healthcare.exception.NoAppointmentException;
import com.cg.healthcare.exception.NoTestFoundAtThisCenterException;

@Service
@Transactional
public class AppointmentService implements IAppointmentService{
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private TestRepository testRepository;
	
	@Autowired
	private DiagnosticCenterRepository diagnosticCenterRepository;

	@Override
	public Appointment makeAppointment(Appointment appointment, String username, int testId, int diagnosticCenterId) throws Exception {
		DiagnosticTest diagnosticTest = getDiagnosticTestById(testId);
		Patient patient = findPatientByUsername(username);
		DiagnosticCenter diagnosticCenter = getDiagnosticCenterById(diagnosticCenterId);
		appointment.setDiagnosticCenter(diagnosticCenter);
		appointment.setDiagnosticTest(diagnosticTest);
		appointment.setPatient(patient);
		Appointment addAppointment=appointmentRepository.save(appointment);
		patient.getAppointments().add(addAppointment);
		return addAppointment;
	}

	@Override
	public Set<Appointment> viewAppointments(String username) throws Exception {
		Patient patient = findPatientByUsername(username);
		Set<Appointment> appointments= patient.getAppointments();
		return appointments;
	}

	@Override
	public Appointment viewMyAppointment(int appointmentId, String username ) throws Exception{
		
		Set<Appointment> list=viewAppointments(username);
		Optional<Appointment> optional=list.stream().
				filter(f1->f1.getId() ==appointmentId).findFirst();
		if(optional.isPresent())
		{
			return appointmentRepository.findById(appointmentId).get();
		}
		else
			throw new NoAppointmentException("Appointment not found","ID Exception");
		
	}

	public Patient findPatientByUsername(String username) throws Exception {
			User user = userRepository.findByUsername(username);
			if(user == null) {
				throw new UsernameNotFoundException("User with " + username + " does not exist");
			}
			Patient patient = patientRepository.getOne(user.getId());
			return patient;
	}
	
	public DiagnosticTest getDiagnosticTestById(int id) throws Exception {
		DiagnosticTest diagnosticTest = testRepository.getOne(id);
		if(diagnosticTest == null) {
			throw new NoTestFoundAtThisCenterException("No Diagnostic Test found with given Id");
		}
		return diagnosticTest;
	}
	
	public DiagnosticCenter getDiagnosticCenterById(int id) throws Exception {
		Optional<DiagnosticCenter> diagnosticCenter = diagnosticCenterRepository.findById(id);
		if(diagnosticCenter.isPresent()) {
			return diagnosticCenter.get();
		}
		else  {
			throw new DiagnosticCenterNotPresentException(" Diagnostic Center Not Present");
		}
	}
	
}
