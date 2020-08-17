package com.cg.healthcare.tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
import com.cg.healthcare.service.AppointmentService;

@ExtendWith(MockitoExtension.class)
public class AppointmentTests {
	
	@InjectMocks
	private AppointmentService appointmentService;

	@Mock
	private AppointmentRepository appointmentRepository;
	@Mock
	private PatientRepository patientRepository;
	@Mock
	private TestRepository testRepository; 
	
	@Mock
	private DiagnosticCenterRepository diagnosticCenterRepository;
	 
	@Mock
	private UserRepository userRepository;
	
	private static User mockDiagnosticUser;
	private static User mockPatientUser;
	private static Patient patient;
	private static Patient patient1;
	private static DiagnosticCenter diagnosticCenter;
	private static DiagnosticCenter diagnosticCenter1;
	private static DiagnosticTest diagnosticTest1;
	private static DiagnosticTest diagnosticTest2;
	private static Appointment appointment;
	private static Appointment appointment1;
	
	@BeforeEach
	public void init()
	{ 
		
		Timestamp instant=Timestamp.valueOf(LocalDateTime.now());
		mockDiagnosticUser = new User(2000, "center1@gmail.com", "Password@123", "ROLE_CENTER");
		mockPatientUser = new User(100, "vikram@gmail.com", "Password@123", "ROLE_PATIENT");
		patient=new Patient(100,"Vikram",22,"Male","9254879648");
		patient1=new Patient(101,"Ram",22,"Male","9254879648");
		diagnosticCenter1=new DiagnosticCenter(1999,"Center3",
				"9800457", "Address","email@gmail.com", "Services");
		
		diagnosticCenter=new DiagnosticCenter(2000,"Center2",
				"9800457", "Address","email@gmail.com", "Services");
		diagnosticTest1=new DiagnosticTest("Urine Test", 20, "normalValue", "units");
		diagnosticTest2=new DiagnosticTest("Blood test", 50, "normalValue", "units");
		appointment=new Appointment(123, instant, "diagnosis", "symptoms",diagnosticTest1, patient, diagnosticCenter);
		appointment1=new Appointment(124,instant,"diagnosis", "symptoms",diagnosticTest2, patient1, diagnosticCenter1); 
	}
	
	@Test
	public void makeAppointment() throws Exception
	{
	 when(userRepository.findByUsername("vikram@gmail.com")).thenReturn(mockPatientUser);
	 when(patientRepository.getOne(100)).thenReturn(patient);
	 when(testRepository.getOne(10)).thenReturn(diagnosticTest1);
	 when(diagnosticCenterRepository.findById(2000)).thenReturn(Optional.of(diagnosticCenter));
	 when(appointmentRepository.save(appointment)).thenReturn(appointment);
	 assertEquals(123, appointmentService.makeAppointment(appointment, "vikram@gmail.com",10,2000).getId());
	}
	
	@Test
	public void viewAppointments() throws Exception
	{   
		patient.getAppointments().add(appointment);
		patient.getAppointments().add(appointment1);
		when(userRepository.findByUsername("vikram@gmail.com")).thenReturn(mockPatientUser);
		when(patientRepository.getOne(100)).thenReturn(patient);
		assertEquals(2, appointmentService.viewAppointments("vikram@gmail.com").size());
	}
	
	@Test
	public void viewMyAppointment() throws Exception
	{
		patient.getAppointments().add(appointment);
		patient.getAppointments().add(appointment1);
		when(userRepository.findByUsername("vikram@gmail.com")).thenReturn(mockPatientUser);
		when(patientRepository.getOne(100)).thenReturn(patient);
		when(appointmentRepository.findById(123)).thenReturn(Optional.of(appointment));
		assertEquals(123, appointmentService.viewMyAppointment(appointment.getId(), "vikram@gmail.com").getId());
	}
}
