package com.cg.healthcare.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.healthcare.dao.BedDao;
import com.cg.healthcare.dao.BedRepository;
import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.service.BedService;



@ExtendWith(MockitoExtension.class)
public class HealthCareTest {
	@Mock
	BedDao dao;

	BedRepository bedDao;

	@Mock
	BedService service;
	
	static ArrayList<Bed> allBeds = new ArrayList<Bed>();
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
		allBeds.add(bed1);
		bed2.setAppointment(null);
		bed2.setDiagnosticCenter(null);
		bed2.setId(2);
		bed2.setOccupied(true);
		bed2.setPricePerDay(500.0);
		allBeds.add(bed2);
		vacantBeds.add(bed1);

	}
	
//	@Test
//	public void testGetBeds() {
//
//		when(bedDao.getAllBeds()).thenReturn(allBeds);
//		assertEquals(allBeds,service.getBeds());
//		
//	}
	
	@Test
	public void admitPatients() {
		
		when(service.admitPatient()).thenReturn(true);
		assertTrue(service.admitPatient());
//	ass(service.admitPatient());
		
	}
	
//	@Test
//	public void canNotAdmitPatients() {
//		vacantBeds.remove(0);
//		vacantBeds.add(bed2);
//		when(bedDao.getVacantBeds(false)).thenReturn(vacantBeds);
//		assertEquals(vacantBeds,service.canNotAdmitPatient());
//		
//	}
	
	@Test
	public void dischargePatient() {
		assignedBeds.add(bed2);
		when(service.dischargePatient()).thenReturn(true);
		assertTrue(service.dischargePatient());
		
		
	}

}
