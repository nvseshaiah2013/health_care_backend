package com.cg.healthcare.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.UserRepository;
import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.GeneralBed;
import com.cg.healthcare.entities.IntensiveCareBed;
import com.cg.healthcare.entities.IntensiveCriticalCareBed;
import com.cg.healthcare.entities.User;
import com.cg.healthcare.entities.VentilatorBed;
import com.cg.healthcare.service.DiagnosticCenterService;

@SpringBootTest
public class DiagnosticCenterTests {

	@InjectMocks
	private DiagnosticCenterService diagnosticCenterService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private DiagnosticCenterRepository diagnosticCenterRepository;

	private static User mockUser;
	private static DiagnosticCenter mockDiagnosticCenter;

	@BeforeAll
	public static void init() {
		mockUser = new User(10, "center1@gmail.com", "Password", "ROLE_CENTER");
		mockDiagnosticCenter = new DiagnosticCenter(mockUser.getId(), "Center 1", "9876543210", "Address",
				"email@gmail.com", "Services");
	}

	@Test
	public void addICUBedsTest() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addICUBeds("center1@gmail.com", 5, 10000.0);
		Set<Bed> beds = diagnosticCenterService.getBeds("center1@gmail.com");
		Long count = beds.stream().filter(bed -> (bed instanceof IntensiveCareBed)).count();
		assertEquals(5, count);
	}

	@Test
	public void addICCUBedsTest() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addICCUBeds("center1@gmail.com", 3, 11000.0);
		Set<Bed> beds = diagnosticCenterService.getBeds("center1@gmail.com");
		Long count = beds.stream().filter(bed -> (bed instanceof IntensiveCriticalCareBed)).count();
		assertEquals(3, count);
	}

	@Test
	public void addGeneralBedsTest() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addGeneralBeds("center1@gmail.com", 6, 1000.0);
		Set<Bed> beds = diagnosticCenterService.getBeds("center1@gmail.com");
		Long count = beds.stream().filter(bed -> (bed instanceof GeneralBed)).count();
		assertEquals(6, count);
	}

	@Test
	public void addVentilatorBedsTest() throws Exception {
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		diagnosticCenterService.addVentilatorBeds("center1@gmail.com", 7, 5000.0);
		Set<Bed> beds = diagnosticCenterService.getBeds("center1@gmail.com");
		Long count = beds.stream().filter(bed -> (bed instanceof VentilatorBed)).count();
		assertEquals(7, count);
	}

	@Test
	public void removeBedsSuccessfull() throws Exception {

	}

	@Test
	public void removeBedsThrowsException() throws Exception {

	}
}
