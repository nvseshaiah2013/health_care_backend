package com.cg.healthcare.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.healthcare.requests.LoginRequest;
import com.cg.healthcare.requests.PatientSignUpRequest;
import com.cg.healthcare.responses.LoginResponse;
import com.cg.healthcare.responses.SuccessMessage;
import com.cg.healthcare.service.PublicService;

@RestController
@RequestMapping(value = "/api/public")
@CrossOrigin(origins = "http://localhost:4200")
public class PublicController {

	@Autowired
	private PublicService publicService;
	
	// Venkat Starts

	@PostMapping(value = "/registerPatient", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessMessage> registerPatient(@RequestBody @Valid PatientSignUpRequest patientDetails)
			throws Exception {
		publicService.registerPatient(patientDetails);
		return new ResponseEntity<SuccessMessage>(
				new SuccessMessage("Patient Registration", "The Patient Registered Successfully"), HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginResponse> getAuthenticationToken(@RequestBody @Valid LoginRequest loginRequest)
			throws Exception {
		LoginResponse loginResponse = publicService.getAuthenticationToken(loginRequest.getUsername(),
				loginRequest.getPassword());
		return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
	}
	
	@PostMapping(value = "/registerAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessMessage> registerPatient(@RequestBody Map<String, String> list)
			throws Exception {
//		publicService.registerPatient(patientDetails);
		publicService.registerAdmin(list.get("username"), list.get("password"));
		return new ResponseEntity<SuccessMessage>(
				new SuccessMessage("Patient Registration", "The Patient Registered Successfully"), HttpStatus.ACCEPTED);
	}
	
	// Venkat Ends
}
