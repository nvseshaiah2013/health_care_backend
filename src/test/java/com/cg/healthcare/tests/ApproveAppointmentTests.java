package com.cg.healthcare.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.healthcare.dao.AppointmentRepository;
import com.cg.healthcare.entities.Appointment;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.DiagnosticTest;
import com.cg.healthcare.service.AdminService;

@ExtendWith(MockitoExtension.class)
public class ApproveAppointmentTests {

	@InjectMocks
	AdminService adminService;
	
	@Mock
	AppointmentRepository appointmentRepo;
	
	static Appointment a1,a2,a3;
	static List<Appointment> appointments;
	
	@BeforeAll
	public static void sachinInit() {
		
		appointments=new ArrayList<>();
		Appointment a1=new Appointment();
		a1.setAppointmentDate(Timestamp.valueOf(LocalDateTime.now().plusDays(1).withHour(9).withMinute(0)));
		a1.setApprovalStatus(0);
		DiagnosticCenter dc=new DiagnosticCenter();
		dc.setId(101);
		DiagnosticTest dt=new DiagnosticTest();
		dt.setTestName("corona");
		a1.setDiagnosticCenter(dc);
		a1.setDiagnosticTest(dt);
		
		a2=new Appointment();
		a2.setAppointmentDate(Timestamp.valueOf(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0)));
		a2.setApprovalStatus(0);
		dc=new DiagnosticCenter();
		dc.setId(101);
		dt=new DiagnosticTest();
		dt.setTestName("corona");
		a2.setDiagnosticCenter(dc);
		a2.setDiagnosticTest(dt);;
		
		a3=new Appointment();
		a3.setAppointmentDate(Timestamp.valueOf(LocalDateTime.now().plusDays(1).withHour(9).withMinute(40)));
		a3.setApprovalStatus(0);
		dc=new DiagnosticCenter();
		dc.setId(101);
		dt=new DiagnosticTest();
		dt.setTestName("corona");
		a3.setDiagnosticCenter(dc);
		a3.setDiagnosticTest(dt);
		
		appointments.add(a3);			// with time 9:45
		appointments.add(a1);			// with time 9:0
		appointments.add(a2);			// with time 10:0
	}
	
	@Test
	@Order(1)
	public void getUnapprovedAppointment() {
		when(appointmentRepo.findAll()).thenReturn(appointments);
		assertEquals(adminService.getApppointmentList(101, "corona",0).size(),3);
	}
	
	@Test
	@Order(2)
	public void processAllAppointmentTest() {
		when(appointmentRepo.findAll()).thenReturn(appointments);
		when(appointmentRepo.save(a1)).thenReturn(a1);
		String result=adminService.processAppointment(101,"corona",60,2);		// 60 time taken and 2 is no of patient can be handled
		assertEquals("done",result);
	}
	
	@Test
	@Order(3)
	public void getApprovedAppointmentTest() {
		assertEquals(adminService.getApppointmentList(101, "corona",1).size(),1);
	}

}
