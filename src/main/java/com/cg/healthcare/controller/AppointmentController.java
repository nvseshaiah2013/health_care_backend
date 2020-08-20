package com.cg.healthcare.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.healthcare.entities.Appointment;
import com.cg.healthcare.entities.DiagnosticTest;
import com.cg.healthcare.requests.MakeAppointmentRequest;
//import com.cg.healthcare.requests.MakeAppointmentRequest;
import com.cg.healthcare.service.AppointmentService;
//import com.cg.healthcare.service.IJwtUtil;
import com.cg.healthcare.service.IJwtUtil;

@RestController
@RequestMapping("/api/patient/appointment")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private IJwtUtil jwtUtil;
	
	@GetMapping("/all")
	public Set<Appointment> viewAppointments(HttpServletRequest request) throws Exception
	{
		String username = getPatientByUsername(request);
		return appointmentService.viewAppointments(username);
	}
	
	@GetMapping(value="/{appointmentId}")
	public Appointment viewMyAppointment(@PathVariable int appointmentId, HttpServletRequest request) throws Exception 
	{
		String username = getPatientByUsername(request);
		return appointmentService.viewMyAppointment(appointmentId, username);
	}

	@PostMapping(value="/add",consumes= MediaType.APPLICATION_JSON_VALUE)
	public Appointment makeAppointment(@RequestBody MakeAppointmentRequest appointment, HttpServletRequest request) throws Exception
	{
		String username = getPatientByUsername(request);
		Appointment newAppointment = new Appointment();
		newAppointment.setAppointmentDate(appointment.getAppointmentDate());
		newAppointment.setApprovalStatus(0);
		newAppointment.setSymptoms(appointment.getSymptoms());
		return appointmentService.makeAppointment(newAppointment, username, appointment.getTestId(), appointment.getDiagnosticCenterId());
	}
	
	
	@GetMapping(value="/search/{diagnosticCenterID}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<DiagnosticTest> getTestNames(@PathVariable int diagnosticCenterID) throws Exception{
		
		Set<DiagnosticTest> tests = appointmentService.getTestNames(diagnosticCenterID);
		System.out.print(tests.size());
		return tests;
	}
	
	public String getPatientByUsername(HttpServletRequest request) throws Exception {
		String header = request.getHeader("Authorization");
		String token = header.substring(7);
		String username = jwtUtil.extractUsername(token);
		return username;
	}
}
