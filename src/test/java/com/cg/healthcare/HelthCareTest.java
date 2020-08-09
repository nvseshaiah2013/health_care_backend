package com.cg.healthcare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.healthcare.dao.BedRepository;
import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.service.DiagnosticCenterService;

@SpringBootTest

public class HelthCareTest {
	@Mock
	BedRepository dao;
	@InjectMocks
	DiagnosticCenterService service;
	
	static ArrayList<Bed> allbeds = new ArrayList<Bed>();
	static ArrayList<Bed> vacantBeds = new ArrayList<Bed>();
	static ArrayList<Bed> assignedBeds = new ArrayList<Bed>();
	static Bed bed1=new Bed();
	static Bed bed2=new Bed();
	
	@Test
	void contextLoads() {
	}
	
	@BeforeAll
	public static void beforeAll() {

		bed1.setAppointment(null);
		bed1.setDiagnosticCenter(null);
		bed1.setId(1);
		bed1.setOccupied(false);
		bed1.setPricePerDay(500.0);
		allbeds.add(bed1);
		bed2.setAppointment(null);
		bed2.setDiagnosticCenter(null);
		bed2.setId(2);
		bed2.setOccupied(true);
		bed2.setPricePerDay(500.0);
		allbeds.add(bed2);
		vacantBeds.add(bed1);
	}
	
	@Test
	public void testGetBeds() {

		when(dao.getAllBeds()).thenReturn(allbeds);
		assertEquals(allbeds,service.getBeds());
		
	}
	
	@Test
	public void admitPatients() {
		
		when(dao.getVacantBeds()).thenReturn(vacantBeds);
		assertEquals(vacantBeds,service.admitPatient());
		
	}
	
	@Test
	public void canNotAdmitPatients() {
		vacantBeds.remove(0);
		vacantBeds.add(bed2);
		when(dao.getVacantBeds()).thenReturn(vacantBeds);
		assertEquals(vacantBeds,service.canNotAdmitPatient());
		
	}
	
	@Test
	public void dischargePatient() {
		assignedBeds.add(bed2);
		when(dao.deallocateAssignedBed()).thenReturn(assignedBeds);
		assertEquals(assignedBeds,service.dischargePatient());
		
		
	}

}
