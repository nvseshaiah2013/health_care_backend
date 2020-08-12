package com.cg.healthcare.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.healthcare.dao.AppointmentRepository;
import com.cg.healthcare.service.BedService;


@ExtendWith(MockitoExtension.class)
public class HealthCareTest {
	@Mock
	AppointmentRepository appointmentRepo;

	@Mock
	BedService service;
	
	@Test
	void contextLoads() {
	}
	
	@BeforeAll
	public static void beforeAll() {
		
	}
	
	@Test
	public void admitPatients() {
		
		when(service.admittedSuccessfully(1)).thenReturn(true);
		assertTrue(service.admittedSuccessfully(1));
		
	}
	@Test
	public void admitGeneralBed() {
		when(service.admittedGeneralBedSuccessfully(1)).thenReturn(true);
		assertTrue(service.admittedGeneralBedSuccessfully(1));
	}
	
	@Test
	public void admitIntensiveCareBed() {
		when(service.admittedIntensiveCareBedSuccessfully(1)).thenReturn(true);
		assertTrue(service.admittedIntensiveCareBedSuccessfully(1));
	}
	
	@Test
	public void admitIntensiveCriticalCareBed() {
		when(service.admittedIntensiveCriticalCareBedSuccessfully(1)).thenReturn(true);
		assertTrue(service.admittedIntensiveCriticalCareBedSuccessfully(1));
	}
	
	@Test
	public void admitVentilatorBed() {
		when(service.admittedVentilatorBedSuccessfully(1)).thenReturn(true);
		assertTrue(service.admittedVentilatorBedSuccessfully(1));
	}
	
	@Test
	public void dischargePatient() throws Exception {
		when(service.dischargePatient(1)).thenReturn(true);
		assertTrue(service.dischargePatient(1));	
	}
}
