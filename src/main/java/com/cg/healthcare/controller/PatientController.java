package com.cg.healthcare.controller;


import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.TestResult;
import com.cg.healthcare.service.PatientService;

@RestController
@RequestMapping(path="/api/patient")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	/*
	 * pritam start
	 */
	
	@PostMapping(value="/allBeds", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<Bed>> getAllBeds(@RequestBody Map<String,String> request) throws Exception{
		Set<Bed> beds=patientService.getAllBed(request.get("username"));
		return new ResponseEntity<Set<Bed>>(beds,HttpStatus.OK);
	}
	
	@PostMapping(value="/allTestResult", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<TestResult>> getAllTestResult(@RequestBody Map<String,String> request) throws Exception{
		Set<TestResult> testResults=patientService.getAllTestResult(request.get("username"));
		return new ResponseEntity<Set<TestResult>>(testResults,HttpStatus.OK);
	}
	
	@PostMapping(value="/bedStatus", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bed> getBedStatus(@RequestBody Map<String,Integer> request) throws Exception{
		return new ResponseEntity<Bed>(patientService.viewBedStatus(request.get("appointmentId")),HttpStatus.OK);
	}
	
	@PostMapping(value="/testResult", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TestResult> getTestResultById(@RequestBody Map<String,Integer> request) throws Exception{
		return new ResponseEntity<TestResult>(patientService.viewTestResult(request.get("testResultId")),HttpStatus.OK);
	}
	
	// Pritam Ends

}
