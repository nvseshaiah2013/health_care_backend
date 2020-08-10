package com.cg.healthcare.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
import com.cg.healthcare.entities.User;
import com.cg.healthcare.entities.VentilatorBed;
import com.cg.healthcare.exception.BedNotFoundException;
import com.cg.healthcare.exception.InvalidGeneralBedException;
import com.cg.healthcare.exception.InvalidICCUBedException;
import com.cg.healthcare.exception.InvalidICUBedException;
import com.cg.healthcare.exception.InvalidVentilatorBedException;
import com.cg.healthcare.exception.OccupiedBedException;
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
	private BedRepository bedRepository; 

	private static User mockDiagnosticUser;
	private static DiagnosticCenter mockDiagnosticCenter;
	private static User mockPatientUser;
	private static Patient mockPatient;
	private static DiagnosticTest mockDiagnosticTest;
	private static Appointment mockAppointment;
	
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
	public void vacantBedsList() {
		Mockito.when(bedRepository.findAll()).thenReturn(Stream
				.of( new Bed(1000,false,mockAppointment,1200.00,mockDiagnosticCenter)).collect(Collectors.toList()));
		assertEquals(1,diagnosticCenterService.listOfVacantBeds().size());
	}

	@Test
	public void addICUBedsTest() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addICUBeds("center1@gmail.com", 5, 10000.0,true, true, true, 5);
		Set<Bed> beds = diagnosticCenterService.getBeds("center1@gmail.com");
		Long count = beds.stream().filter(bed -> (bed instanceof IntensiveCareBed)).count();
		assertEquals(5, count);
	}

	@Test
	public void addICCUBedsTest() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addICCUBeds("center1@gmail.com", 3, 11000.0,true, true, true, "Fowler");
		Set<Bed> beds = diagnosticCenterService.getBeds("center1@gmail.com");
		Long count = beds.stream().filter(bed -> (bed instanceof IntensiveCriticalCareBed)).count();
		assertEquals(3, count);
	}

	@Test
	public void addGeneralBedsTest() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addGeneralBeds("center1@gmail.com", 6, 1000.0,true, "Steel");
		Set<Bed> beds = diagnosticCenterService.getBeds("center1@gmail.com");
		Long count = beds.stream().filter(bed -> (bed instanceof GeneralBed)).count();
		assertEquals(6, count);
	}

	@Test
	public void addVentilatorBedsTest() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addVentilatorBeds("center1@gmail.com", 7, 5000.0,11,"Volume");
		Set<Bed> beds = diagnosticCenterService.getBeds("center1@gmail.com");
		Long count = beds.stream().filter(bed -> (bed instanceof VentilatorBed)).count();
		assertEquals(7, count);
	}

	@Test
	public void removeBedSuccessfull() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addVentilatorBeds("center1@gmail.com", 7, 5000.0,11,"Volume");
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
		diagnosticCenterService.removeBed(mockDiagnosticUser.getUsername(), bedToBeRemoved.getId());
		assertEquals(6,mockDiagnosticCenter.getBeds().size());
	}

	@Test
	public void removeBedThrowsBedNotFoundException() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addVentilatorBeds("center1@gmail.com", 7, 5000.0,11,"Volume");
		Set<Integer> bedIds = mockDiagnosticCenter.getBeds().stream().map(bed -> bed.getId()).collect(Collectors.toSet());
		assertThrows(BedNotFoundException.class, () -> {
			diagnosticCenterService.removeBed(mockDiagnosticUser.getUsername(),Collections.max(bedIds) + 1);
		} );
		
	}
	
	@Test
	public void removeBedThrowsOccupiedException() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addVentilatorBeds("center1@gmail.com", 7, 5000.0,11,"Volume");
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
	
	@Test
	public void addVentilatorBedThrowsException() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		assertThrows(InvalidVentilatorBedException.class, () -> {
			diagnosticCenterService.addVentilatorBeds("center1@gmail.com", 7, 5000.0,11,"Vome");
		});
	}
	
	@Test
	public void addGeneralBedThrowsException() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		assertThrows(InvalidGeneralBedException.class, () -> {
			diagnosticCenterService.addGeneralBeds("center1@gmail.com", 7,1000.0,true, "Some Random Value");
		});
	}
	
	@Test
	public void addICUBedThrowsException() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		assertThrows(InvalidICUBedException.class, () -> {
			diagnosticCenterService.addICUBeds("center1@gmail.com", 3, 11000.0,true, true, true,9);
		});
	}
	
	@Test
	public void addICCUBedThrowsException() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		assertThrows(InvalidICCUBedException.class, () -> {
			diagnosticCenterService.addICCUBeds("center1@gmail.com", 3, 11000.0,true, true, true, "Some Random Type");
		});
	}
	
}


