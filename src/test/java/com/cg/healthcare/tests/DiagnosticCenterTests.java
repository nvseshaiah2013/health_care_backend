package com.cg.healthcare.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

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
import com.cg.healthcare.exception.BedNotFoundException;
import com.cg.healthcare.exception.OccupiedBedException;
import com.cg.healthcare.service.DiagnosticCenterService;

@SpringBootTest
public class DiagnosticCenterTests {

	@InjectMocks
	private DiagnosticCenterService diagnosticCenterService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private DiagnosticCenterRepository diagnosticCenterRepository;

	private static User mockDiagnosticUser;
	private static DiagnosticCenter mockDiagnosticCenter;
	private static User mockPatientUser;
	private static Patient mockPatient;

	@BeforeEach
	public void init() {
		mockDiagnosticUser = new User(10, "center1@gmail.com", "Password@123", "ROLE_CENTER");
		mockDiagnosticCenter = new DiagnosticCenter(mockDiagnosticUser.getId(), "Center 1", "9876543210", "Address",
				"email@gmail.com", "Services");
		mockPatientUser = new User(11, "patient1@gmail.com", "Password@123", "ROLE_PATIENT");
		mockPatient = new Patient(mockPatientUser.getId(),"Patient",21,"Male","9988776655");
	}

	@Test
	public void addICUBedsTest() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addICUBeds("center1@gmail.com", 5, 10000.0);
		Set<Bed> beds = diagnosticCenterService.getBeds("center1@gmail.com");
		Long count = beds.stream().filter(bed -> (bed instanceof IntensiveCareBed)).count();
		assertEquals(5, count);
	}

	@Test
	public void addICCUBedsTest() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addICCUBeds("center1@gmail.com", 3, 11000.0);
		Set<Bed> beds = diagnosticCenterService.getBeds("center1@gmail.com");
		Long count = beds.stream().filter(bed -> (bed instanceof IntensiveCriticalCareBed)).count();
		assertEquals(3, count);
	}

	@Test
	public void addGeneralBedsTest() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addGeneralBeds("center1@gmail.com", 6, 1000.0);
		Set<Bed> beds = diagnosticCenterService.getBeds("center1@gmail.com");
		Long count = beds.stream().filter(bed -> (bed instanceof GeneralBed)).count();
		assertEquals(6, count);
	}

	@Test
	public void addVentilatorBedsTest() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addVentilatorBeds("center1@gmail.com", 7, 5000.0);
		Set<Bed> beds = diagnosticCenterService.getBeds("center1@gmail.com");
		Long count = beds.stream().filter(bed -> (bed instanceof VentilatorBed)).count();
		assertEquals(7, count);
	}

	@Test
	public void removeBedSuccessfull() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addVentilatorBeds("center1@gmail.com", 7, 5000.0);
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
		diagnosticCenterService.addVentilatorBeds("center1@gmail.com", 7, 5000.0);
		Set<Integer> bedIds = mockDiagnosticCenter.getBeds().stream().map(bed -> bed.getId()).collect(Collectors.toSet());
		assertThrows(BedNotFoundException.class, () -> {
			diagnosticCenterService.removeBed(mockDiagnosticUser.getUsername(),Collections.max(bedIds) + 1);
		} );
		
	}
	
	@Test
	public void removeBedThrowsOccupiedException() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addVentilatorBeds("center1@gmail.com", 7, 5000.0);
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
}