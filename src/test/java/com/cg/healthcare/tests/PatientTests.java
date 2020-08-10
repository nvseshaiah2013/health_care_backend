package com.cg.healthcare.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.cg.healthcare.dao.AppointmentRepository;
import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.TestResultRepository;
import com.cg.healthcare.dao.UserRepository;
import com.cg.healthcare.entities.Appointment;
import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.DiagnosticTest;
import com.cg.healthcare.entities.GeneralBed;
import com.cg.healthcare.entities.IntensiveCareBed;
import com.cg.healthcare.entities.IntensiveCriticalCareBed;
import com.cg.healthcare.entities.Patient;
import com.cg.healthcare.entities.TestResult;
import com.cg.healthcare.entities.TestResultId;
import com.cg.healthcare.entities.User;
import com.cg.healthcare.entities.VentilatorBed;
import com.cg.healthcare.exception.InvalidVentilatorBedException;
import com.cg.healthcare.exception.NoVacantBedForPatient;
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
	
	@Mock
	private TestResultRepository testResultRepository;
	
	private static User mockDiagnosticCenterUser;
	private static DiagnosticCenter mockDiagnosticCenter;
	private static User mockPatientUser;
	private static Patient mockPatient;
	private static GeneralBed mockGeneralBed;
	private static IntensiveCareBed mockIntensiveCareBed;
	private static IntensiveCriticalCareBed mockIntensiveCriticalCareBed;
	private static VentilatorBed mockVentilatorBed;
	private static Appointment mockAppointment;
	private static DiagnosticTest mockDiagnosticTest;
	private static TestResult mockTestResult;
	private static TestResultId mockTestResultId;
	
	
	
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
		
		mockDiagnosticTest=new DiagnosticTest("Blood Test", 100.0, "12-16", "gram");
		mockDiagnosticCenter.getTests().add(mockDiagnosticTest);
		mockDiagnosticTest.setId(101);
		mockDiagnosticTest=new DiagnosticTest("Corona Test", 4000.0, "19", "ppi");
		mockDiagnosticCenter.getTests().add(mockDiagnosticTest);
		mockDiagnosticTest.setId(102);
		mockTestResultId=new TestResultId(10, 101);
		mockTestResult=new TestResult(mockTestResultId, 13.5, "Normal", mockAppointment, mockDiagnosticTest);
		
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
	public void getAllVacantBedFailTest() throws Exception    
	{
		Mockito.when(userRepository.findByUsername("dcenter@gmail.com")).thenReturn(mockDiagnosticCenterUser);
		Mockito.when(diagnosticCenterRepository.getOne(20)).thenReturn(mockDiagnosticCenter);
		Set<Bed> vacantBeds=mockDiagnosticCenter.getBeds();
		for(Bed b: vacantBeds) {
			b.setOccupied(true);
		}
		assertThrows(NoVacantBedForPatient.class, () -> {
			patientService.getAllBed("dcenter@gmail.com");
		});
	}
	
	@Test 
	public void viewBedStatusTest() throws Exception
	{
		Mockito.when(appointmentRepository.getOne(10)).thenReturn(mockAppointment);
		boolean result=patientService.applyForBed(mockAppointment.getId());
		Bed bed=patientService.viewBedStatus(mockAppointment.getId());
		int id=bed.getAppointment().getId();
		assertEquals(10,id );
	}
	
	@Test
	public void viewTestResult() throws Exception
	{
		Mockito.when(appointmentRepository.getOne(10)).thenReturn(mockAppointment);
		Mockito.when(diagnosticCenterRepository.getOne(20)).thenReturn(mockDiagnosticCenter);
		DiagnosticTest diagnosticTest=mockAppointment.getDiagnosticCenter().getTests().stream().findFirst().get();
		TestResultId testResultId=new TestResultId(10,diagnosticTest.getId());
		Mockito.when(testResultRepository.getOne(testResultId)).thenReturn(mockTestResult);
		TestResult testResult=patientService.viewTestResult(testResultId);
		double d=testResult.getTestReading();
		assertEquals(13.5,d);
	}
	
	

	
	
	
	

}
