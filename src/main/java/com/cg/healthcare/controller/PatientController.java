package com.cg.healthcare.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.healthcare.entities.Appointment;
import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.DiagnosticTest;
import com.cg.healthcare.entities.GeneralBed;
import com.cg.healthcare.entities.IntensiveCareBed;
import com.cg.healthcare.entities.IntensiveCriticalCareBed;
import com.cg.healthcare.entities.TestResult;
import com.cg.healthcare.entities.VentilatorBed;
import com.cg.healthcare.responses.BedsList;
import com.cg.healthcare.service.IJwtUtil;
import com.cg.healthcare.service.PatientService;

@RestController
@RequestMapping(path="/api/patient")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired 
	private IJwtUtil jwtUtil;
	
	/*
	 * pritam start
	 */
	

	/*
	 * @PostMapping(value="/allBeds", produces=MediaType.APPLICATION_JSON_VALUE)
	 * public ResponseEntity<Set<Bed>> getAllBeds(@RequestBody int
	 * diagnosticCenterId) throws Exception{ String
	 * diagosticCenterUsername=patientService.getUsername(diagnosticCenterId);
	 * Set<Bed> beds=patientService.getAllBed(diagosticCenterUsername); return new
	 * ResponseEntity<Set<Bed>>(beds,HttpStatus.OK); }
	 */
	
	@GetMapping(value="/allTest", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<DiagnosticTest>> getAllTestResult(HttpServletRequest request) throws Exception{
		String patientUserName=getPatientByUsername(request);
		ArrayList<DiagnosticTest> allTest=patientService.getAllTestResult(patientUserName);
		return new ResponseEntity<ArrayList<DiagnosticTest>>(allTest,HttpStatus.OK);
	}
	
	@GetMapping(value="/bedStatus/{appointmentId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bed> getBedStatus(@PathVariable int appointmentId) throws Exception{
		//System.out.println("Hello"+appointmentId);
		return new ResponseEntity<Bed>(patientService.viewBedStatus(appointmentId),HttpStatus.OK);
	}
	
	@GetMapping(value="/testResult/{testResultId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TestResult> getTestResultById(@PathVariable int testResultId) throws Exception{
		return new ResponseEntity<TestResult>(patientService.viewTestResult(testResultId),HttpStatus.OK);
	}
	
	@GetMapping(value="/getAllDiagnosticCenter")
	public ResponseEntity<List<DiagnosticCenter>> getAllDiagnosticCenter()
	{
		List<DiagnosticCenter> centerList = patientService.getAllDiagnosticCenter();
		return new ResponseEntity<List<DiagnosticCenter>>(centerList,HttpStatus.OK);
	}
	
	@GetMapping(value = "/getBeds/{diagnosticCenterId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BedsList> getBedsOfDiagnosticCenter(@PathVariable Integer diagnosticCenterId) throws Exception {
		String diagnosticCenterUsername=patientService.getUsername(diagnosticCenterId);
		
		Set<Bed> beds = patientService.getBeds(diagnosticCenterUsername);
		
//		GeneralBed bed = (GeneralBed) beds.stream().filter(b -> b instanceof GeneralBed).findFirst().get();
		
		BedsList bedsList = new BedsList();
		
		for(Bed bed : beds) {
			if(bed instanceof GeneralBed) {
				bedsList.getGeneralBeds().add((GeneralBed)bed);
			}
			else if(bed instanceof IntensiveCareBed) {
				bedsList.getIntensiveCareBeds().add((IntensiveCareBed)bed);
			}
			else if(bed instanceof IntensiveCriticalCareBed) {
				bedsList.getIntensiveCriticalCareBeds().add((IntensiveCriticalCareBed)bed);
			}
			else if(bed instanceof VentilatorBed) {
				bedsList.getVentilatorBeds().add((VentilatorBed)bed);
			}
		}
		
		return new ResponseEntity<BedsList>(bedsList, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/getAllAppointments", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Appointment>> getAllAppointments(HttpServletRequest request) throws Exception {
	String username=getPatientByUsername(request)	;
	ArrayList appointments=patientService.getAllAppointments(username);
	return new ResponseEntity<ArrayList<Appointment>>(appointments,HttpStatus.OK);
	}
	
	
	  public String getPatientByUsername(HttpServletRequest request) throws
	  Exception { String header = request.getHeader("Authorization"); String token
	  = header.substring(7); String username = jwtUtil.extractUsername(token);
	  return username; }	 
	
	
	// Pritam Ends

}
