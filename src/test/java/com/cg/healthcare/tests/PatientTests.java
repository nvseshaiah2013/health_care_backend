package com.cg.healthcare.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.ContextConfiguration;

import com.cg.healthcare.dao.AppointmentRepository;
import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.UserRepository;
import com.cg.healthcare.entities.Appointment;
import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.GeneralBed;
import com.cg.healthcare.entities.IntensiveCareBed;
import com.cg.healthcare.entities.IntensiveCriticalCareBed;
import com.cg.healthcare.entities.Patient;
import com.cg.healthcare.entities.User;
import com.cg.healthcare.entities.VentilatorBed;
import com.cg.healthcare.service.PatientService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PatientTests {
	
	@InjectMocks
	private PatientService patientService;
	
	@Mock
	private DiagnosticCenterRepository diagnosticCenterRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private AppointmentRepository appointmentRepository;
	
	private static User mockDiagnosticCenterUser;
	private static DiagnosticCenter mockDiagnosticCenter;
	private static User mockPatientUser;
	private static Patient mockPatient;
	private static Set<Bed> mockBeds;
	private static GeneralBed mockGeneralBed;
	private static IntensiveCareBed mockIntensiveCareBed;
	private static IntensiveCriticalCareBed mockIntensiveCriticalCareBed;
	private static VentilatorBed mockVentilatorBed;
	private static Appointment mockAppointment;
	
	
	@BeforeEach
	public void init() {
		mockDiagnosticCenterUser= new User(20,"dcenter@gmail.com","Password@123","ROLE_CENTER");
		mockDiagnosticCenter= new DiagnosticCenter(mockDiagnosticCenterUser.getId(),"Center 20","9638527410","Place xyz",
				"dc@gmail.com","serviceOffered");
		mockPatientUser= new User(22,"p@gmail.com","Password@123","ROLE_PATIENT");
		mockPatient= new Patient(mockPatientUser.getId(),"P1",23,"Male","9876432106");
		mockGeneralBed = new GeneralBed(400.0,false, "Wood");
		mockIntensiveCareBed=new IntensiveCareBed(600.0, true, true, true, 4);
		mockIntensiveCriticalCareBed=new IntensiveCriticalCareBed(1800.0, true, true, true, "Manual");
		mockVentilatorBed =new VentilatorBed(3000.0, 60, "Time");
		mockDiagnosticCenter.getBeds().add(mockGeneralBed);
		mockDiagnosticCenter.getBeds().add(mockIntensiveCareBed);
		mockDiagnosticCenter.getBeds().add(mockIntensiveCriticalCareBed);
		mockDiagnosticCenter.getBeds().add(mockVentilatorBed);
		
		mockAppointment= new Appointment(10, new Timestamp(System.currentTimeMillis()), 1, "diagnosis", "symptoms", mockPatient, mockDiagnosticCenter);
		
	}
	
	@Test
	public void getAllVacantBedsTest() throws Exception{
		Mockito.when(userRepository.findByUsername("dcenter@gmail.com")).thenReturn(mockDiagnosticCenterUser);
		Mockito.when(diagnosticCenterRepository.getOne(20)).thenReturn(mockDiagnosticCenter);
		Set<Bed> vacantBeds=patientService.getAllBed("dcenter@gmail.com");
		Long count=vacantBeds.stream().count();
		assertEquals(4, count);
		
		
	}
	
	@Test 
	public void applyForBedPassTest()
	{
		Mockito.when(appointmentRepository.getOne(10)).thenReturn(mockAppointment);
		boolean result=patientService.applyForBed(mockAppointment.getId());
		assertEquals(true, result);
	}
	
	@Test
	public void getAllVacantBedFailTest()    
	{
		Mockito.when(userRepository.findByUsername("dcenter@gmail.com")).thenReturn(mockDiagnosticCenterUser);
		Mockito.when(diagnosticCenterRepository.getOne(20)).thenReturn(mockDiagnosticCenter);
		Set<Bed> vacantBeds=patientService.getAllBed("dcenter@gmail.com");
		for(Bed b: vacantBeds) {
			b.setOccupied(true);
		}
		Set<Bed> b=patientService.getAllBed("dcenter@gmail.com");
		Long count=b.stream().count();
		assertEquals(0, count);
	}
	
	

	
	
	
	

}
