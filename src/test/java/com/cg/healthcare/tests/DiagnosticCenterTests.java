package com.cg.healthcare.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.healthcare.dao.AppointmentRepository;
import com.cg.healthcare.dao.BedRepository;
import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.TestRepository;
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
import com.cg.healthcare.entities.User;
import com.cg.healthcare.entities.VentilatorBed;
import com.cg.healthcare.exception.BedNotFoundException;
import com.cg.healthcare.exception.InvalidGeneralBedException;
import com.cg.healthcare.exception.InvalidICCUBedException;
import com.cg.healthcare.exception.InvalidICUBedException;
import com.cg.healthcare.exception.InvalidVentilatorBedException;
import com.cg.healthcare.exception.OccupiedBedException;
import com.cg.healthcare.requests.TestResultForm;
import com.cg.healthcare.service.DiagnosticCenterService;

@ExtendWith(MockitoExtension.class)
public class DiagnosticCenterTests {
	
	@InjectMocks
	private DiagnosticCenterService diagnosticCenterService;

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private TestRepository testRepository;

	@Mock
	private DiagnosticCenterRepository diagnosticCenterRepository;
	
	@Mock
	private TestResultRepository testResultRepository;
	
	@Mock
	private AppointmentRepository appointmentRepository;
	
	@Mock
	private BedRepository bedRepository; 

	private static User mockDiagnosticUser;
	private static DiagnosticCenter mockDiagnosticCenter;
	private static User mockDiagnosticUser2;
	private static DiagnosticCenter mockDiagnosticCenter2;
	private static User mockPatientUser;
	private static Patient mockPatient;
	private static DiagnosticTest mockDiagnosticTest;
	private static Appointment mockAppointment;
	private static TestResultForm testResultForm;
	private static TestResult mocKTestResult;
	
	private static User mockPatientUser1;
	private static Patient mockPatient1;
	private static User mockPatientUser2;
	private static Patient mockPatient2;
	private static User mockPatientUser3;
	private static Patient mockPatient3;
	
	
	@BeforeEach
	public void init() {
		mockDiagnosticUser = new User(10, "center1@gmail.com", "Password@123", "ROLE_CENTER");
		mockDiagnosticCenter = new DiagnosticCenter(mockDiagnosticUser.getId(), "Center 1", "9876543210", "Address",
				"email@gmail.com", "Services");
		mockPatientUser = new User(11, "patient1@gmail.com", "Password@123", "ROLE_PATIENT");
		mockPatient = new Patient(mockPatientUser.getId(),"Patient",21,"Male","9988776655");
		mockDiagnosticTest =new DiagnosticTest("HemoglobinTest",1700.00,"23.4","21.4");
		mockDiagnosticTest.setId(1001);
		final Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(LocalDate.of(2018, 10, 7), LocalTime.of(8, 45, 0)));
		mockAppointment= new Appointment(1000,timestamp, 1, "Blood","Blood Pressure", mockPatient, mockDiagnosticCenter );
		testResultForm=new TestResultForm(1000,"Normal",21.3);
		mocKTestResult=new TestResult(1000,21.3,"Normal",mockAppointment);
		
		mockPatientUser1 = new User(11, "patient1@gmail.com", "Password@123", "ROLE_PATIENT");
		
		mockPatient1 = new Patient(mockPatientUser1.getId(),"Patient 1",21,"Male","9988776655");		

		mockPatientUser2 = new User(13, "patient2@gmail.com", "Password@123", "ROLE_PATIENT");
		
		mockPatient2 = new Patient(mockPatientUser2.getId(), "Patient 2", 21, "Female", "9797979797");
		
		mockPatientUser3 = new User(14, "patient3@gmail.com", "Password@123", "ROLE_PATIENT");
		
		mockPatient3 = new Patient(mockPatientUser3.getId(), "Patient 3", 21, "Female", "9697979797");
	
		mockDiagnosticUser2 = new User(12, "center2@gmail.com", "Password@123", "ROLE_CENTER");
		
		mockDiagnosticCenter2 = new DiagnosticCenter(mockDiagnosticUser2.getId(), "Center2", "9898989899", "Address", "email2@gmail.com", "Services 2");
	
		
	}
	@Test
	public void getTestInfoTest()  {
		String testname="HemoglobinTest";
		Mockito.when(testRepository.findBytestName(testname)).thenReturn(mockDiagnosticTest);
		assertEquals("HemoglobinTest", diagnosticCenterService.getTestInfo(testname).getTestName());
	}

	@Test
	public void getTestInfoIdTest() {
		String testname="HemoglobinTest";
		Mockito.when(testRepository.findBytestName(testname)).thenReturn(mockDiagnosticTest);
		assertEquals(1001, diagnosticCenterService.getTestInfo(testname).getId());
	}
	
	@Test
	public void updateTestResultsTest() throws Exception
	{
		Mockito.when(appointmentRepository.existsById(1000)).thenReturn(true);
		Mockito.when(appointmentRepository.getOne(1000)).thenReturn(mockAppointment);
		assertEquals("Test results of "+testResultForm.getAppointmentId()+" Updated",diagnosticCenterService.updateTestResult(testResultForm));
	}
	
	
	/*
	 * Gets the list of appointments present in a diagnostic center 
	 */
	@Test
	public void getListOfAppointmentsSuccessfull() throws Exception {
		Appointment appointment1 = new Appointment(101, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient1, mockDiagnosticCenter);
		Appointment appointment2 = new Appointment(102, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient2, mockDiagnosticCenter);
		Appointment appointment3 = new Appointment(103, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient3, mockDiagnosticCenter);
		List<Appointment> appointments = new ArrayList<>();
		appointments.add(appointment1);
		appointments.add(appointment2);
		appointments.add(appointment3);
		Mockito.when(appointmentRepository.findAll()).thenReturn(appointments);
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		assertEquals(3, diagnosticCenterService.listOfCenterAppointment("center1@gmail.com").size());
	}
	
	/*
	 * Only gives the list of appointments with given username
	 */
	
	@Test
	public void getListOfAppointmentsFiltersOutOtherCenters() throws Exception {
		Appointment appointment1 = new Appointment(101, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient1, mockDiagnosticCenter);
		Appointment appointment2 = new Appointment(102, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient2, mockDiagnosticCenter);
		Appointment appointment3 = new Appointment(103, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient3, mockDiagnosticCenter2);
		List<Appointment> appointments = new ArrayList<>();
		appointments.add(appointment1);
		appointments.add(appointment2);
		appointments.add(appointment3);
		Mockito.when(appointmentRepository.findAll()).thenReturn(appointments);
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		assertEquals(2, diagnosticCenterService.listOfCenterAppointment("center1@gmail.com").size());
	}
	
	/*
	 * Only gives the list of Accepted Appointments
	 */
	@Test
	public void getListOfAppointmentsFiltersOutPendingAppointments() throws Exception {
		Appointment appointment1 = new Appointment(101, Timestamp.valueOf(LocalDateTime.now()), 0, "Diagnosis", "Symptoms", mockPatient1, mockDiagnosticCenter);
		Appointment appointment2 = new Appointment(102, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient2, mockDiagnosticCenter);
		Appointment appointment3 = new Appointment(103, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient3, mockDiagnosticCenter);
		List<Appointment> appointments = new ArrayList<>();
		appointments.add(appointment1);
		appointments.add(appointment2);
		appointments.add(appointment3);
		Mockito.when(appointmentRepository.findAll()).thenReturn(appointments);
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		assertEquals(2, diagnosticCenterService.listOfCenterAppointment("center1@gmail.com").size());
	}
	
	/*
	 * Only Gives the list of accepted appointments 
	 */
	@Test
	public void getListOfAppointmentsFiltersOutRejectedAppointments() throws Exception {
		Appointment appointment1 = new Appointment(101, Timestamp.valueOf(LocalDateTime.now()), 2, "Diagnosis", "Symptoms", mockPatient1, mockDiagnosticCenter);
		Appointment appointment2 = new Appointment(102, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient2, mockDiagnosticCenter);
		Appointment appointment3 = new Appointment(103, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient3, mockDiagnosticCenter);
		List<Appointment> appointments = new ArrayList<>();
		appointments.add(appointment1);
		appointments.add(appointment2);
		appointments.add(appointment3);
		Mockito.when(appointmentRepository.findAll()).thenReturn(appointments);
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		assertEquals(2, diagnosticCenterService.listOfCenterAppointment("center1@gmail.com").size());
	}
	
	@Test
	public void vacantBedsList() {
		Mockito.when(bedRepository.findAll()).thenReturn(Stream
				.of( new Bed(1000,false,mockAppointment,1200.00,mockDiagnosticCenter)).collect(Collectors.toList()));
		assertEquals(1,diagnosticCenterService.listOfVacantBeds().size());
	}

	/**
	 * Test To Add Intensive Care Beds
	 * @throws Exception
	 */
	@Test
	@DisplayName("Add Intensive Care Beds Test")
	public void addICUBedsTest() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		for(int bedIndex = 0; bedIndex < 5; ++bedIndex) {
			mockDiagnosticCenter.getBeds().add(new IntensiveCareBed(10000.0,true, true, true, 5));
		}
		Mockito.when(diagnosticCenterRepository.save(mockDiagnosticCenter)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addICUBeds("center1@gmail.com", 5, 10000.0,true, true, true, 5);
		Set<Bed> beds = diagnosticCenterService.getBeds("center1@gmail.com");
		Long count = beds.stream().filter(bed -> (bed instanceof IntensiveCareBed)).count();
		assertEquals(5, count);
	}

	/**
	 * Test to Add Intensive Critical Care Beds
	 * @throws Exception
	 */
	@Test
	@DisplayName("Add Intensive Critical Care Bed Test")
	public void addICCUBedsTest() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		for(int bedIndex = 0; bedIndex < 3; ++bedIndex) {
			mockDiagnosticCenter.getBeds().add(new IntensiveCriticalCareBed(11000.0,true, true, true, "Fowler"));
		}
		Mockito.when(diagnosticCenterRepository.save(mockDiagnosticCenter)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addICCUBeds("center1@gmail.com", 3, 11000.0,true, true, true, "Fowler");
		Set<Bed> beds = diagnosticCenterService.getBeds("center1@gmail.com");
		Long count = beds.stream().filter(bed -> (bed instanceof IntensiveCriticalCareBed)).count();
		assertEquals(3, count);
	}

	/**
	 * Test to Add General Beds Test
	 * @throws Exception
	 */
	@Test
	@DisplayName("Add General Beds Test")
	public void addGeneralBedsTest() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		for(int bedIndex = 0; bedIndex < 6; ++bedIndex) {
			mockDiagnosticCenter.getBeds().add(new GeneralBed(1000.0,true, "Steel"));
		}
		Mockito.when(diagnosticCenterRepository.save(mockDiagnosticCenter)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addGeneralBeds("center1@gmail.com", 6, 1000.0,true, "Steel");
		Set<Bed> beds = diagnosticCenterService.getBeds("center1@gmail.com");
		Long count = beds.stream().filter(bed -> (bed instanceof GeneralBed)).count();
		assertEquals(6, count);
	}

	/**
	 * Test To Add Ventilator Beds
	 * @throws Exception
	 */
	@Test
	@DisplayName("Add Ventilator Beds")
	public void addVentilatorBedsTest() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		for(int bedIndex = 0; bedIndex < 7; ++bedIndex) {
			mockDiagnosticCenter.getBeds().add(new VentilatorBed(5000.0,11,"Volume"));
		}
		Mockito.when(diagnosticCenterRepository.save(mockDiagnosticCenter)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addVentilatorBeds("center1@gmail.com", 7, 5000.0,11,"Volume");
		Set<Bed> beds = diagnosticCenterService.getBeds("center1@gmail.com");
		Long count = beds.stream().filter(bed -> (bed instanceof VentilatorBed)).count();
		assertEquals(7, count);
	}

	/**
	 * Test To Remove Bed Successfully
	 * @throws Exception
	 */
	@Test
	@DisplayName("Test To Remove Bed")
	public void removeBedSuccessfull() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		for(int bedIndex = 0; bedIndex < 7; ++bedIndex) {
			VentilatorBed bed = new VentilatorBed(5000.0,11,"Volume");
			bed.setId(bedIndex+1);
			mockDiagnosticCenter.getBeds().add(bed);
		}
		diagnosticCenterService.removeBed("center1@gmail.com",2);
		assertEquals(6,diagnosticCenterService.getBeds("center1@gmail.com").size());
	}

	/**
	 * Removal of Non Existing Bed Throws Exception
	 * @throws Exception
	 */
	@Test
	@DisplayName("Removing A Non Existing Bed Throws Exception")
	public void removeBedThrowsBedNotFoundException() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		for(int bedIndex = 0; bedIndex < 7; ++bedIndex) {
			VentilatorBed bed = new VentilatorBed(5000.0,11,"Volume");
			bed.setId(bedIndex+1);
			mockDiagnosticCenter.getBeds().add(bed);
		}
		Set<Integer> bedIds = mockDiagnosticCenter.getBeds().stream().map(bed -> bed.getId()).collect(Collectors.toSet());
		assertThrows(BedNotFoundException.class, () -> {
			diagnosticCenterService.removeBed(mockDiagnosticUser.getUsername(),Collections.max(bedIds) + 1);
		} );
		
	}
	
	/**
	 * Test to Throw an Exception when an Occupied Bed is being tried to delete
	 * @throws Exception
	 */
	
	@Test
	@DisplayName("Occupied Exception on Bed Removal")
	public void removeBedThrowsOccupiedException() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		for(int bedIndex = 0; bedIndex < 7; ++bedIndex) {
			VentilatorBed bed = new VentilatorBed(5000.0,11,"Volume");
			bed.setId(bedIndex+1);
			mockDiagnosticCenter.getBeds().add(bed);
		}
		Random random = new Random();
		int noOfBeds = mockDiagnosticCenter.getBeds().size();
		int currentIndex = 0;
		int randomIndex = random.nextInt(noOfBeds);
		Bed bedToBeRemoved = null;
		Iterator<Bed> iterator = mockDiagnosticCenter.getBeds().iterator();
		while(iterator.hasNext()) {
			if(currentIndex == randomIndex) {
				bedToBeRemoved = iterator.next();
				break;
			}
			++currentIndex;
		}
		Appointment appointment = new Appointment(1000, Timestamp.valueOf(LocalDateTime.now()),1,"","",mockPatient,mockDiagnosticCenter);
		bedToBeRemoved.setAppointment(appointment);
		bedToBeRemoved.setOccupied(true);
		final int bedId = bedToBeRemoved.getId();
		assertThrows(OccupiedBedException.class, () -> {
			diagnosticCenterService.removeBed(mockDiagnosticUser.getUsername(),bedId);
		} );
	}
	/**
	 * Method To Test Throwing Exception when invalid Ventilator Bed is Added
	 * @throws Exception
	 */
	@Test
	@DisplayName("Add Ventilator Bed Will Throw Exception")
	public void addVentilatorBedThrowsException() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		assertThrows(InvalidVentilatorBedException.class, () -> {
			diagnosticCenterService.addVentilatorBeds("center1@gmail.com", 7, 5000.0,11,"Vome");
		});
	}
	
	/**
	 * Method To Test Throwing Exception when Invalid General Bed is added 
	 * @throws Exception
	 */
	@Test
	@DisplayName("Adding General Bed Throws Exception")
	public void addGeneralBedThrowsException() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		assertThrows(InvalidGeneralBedException.class, () -> {
			diagnosticCenterService.addGeneralBeds("center1@gmail.com", 7,1000.0,true, "Some Random Value");
		});
	}
	
	/**
	 * Method To Test Throwing Exception when invalid ICU Bed is Added
	 * @throws Exception
	 */
	@Test
	@DisplayName("Adding ICU Bed Throws Exception")
	public void addICUBedThrowsException() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		assertThrows(InvalidICUBedException.class, () -> {
			diagnosticCenterService.addICUBeds("center1@gmail.com", 3, 11000.0,true, true, true,9);
		});
	}
	
	/**
	 * Method To Test Throwing Exception when invalid ICCU Bed is added
	 * @throws Exception
	 */
	
	@Test
	@DisplayName("Adding ICCU Bed Throws Exception")
	public void addICCUBedThrowsException() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		assertThrows(InvalidICCUBedException.class, () -> {
			diagnosticCenterService.addICCUBeds("center1@gmail.com", 3, 11000.0,true, true, true, "Some Random Type");
		});
	}
	
}


