package com.cg.healthcare.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.requests.DiagnosticCenterSignUpRequest;
import com.cg.healthcare.responses.SuccessMessage;
import com.cg.healthcare.service.IAdminService;
import com.cg.healthcare.service.IJwtUtil;

@RestController
@RequestMapping(path="/api/admin")
public class AdminController {
	
	@Autowired
	private IJwtUtil jwtUtil;
	
	@Autowired
	private IAdminService adminService;

	public String getAdminByUsername(HttpServletRequest request) throws Exception {
		String header = request.getHeader("Authorization");
		String token = header.substring(7);
		String username = jwtUtil.extractUsername(token);
		return username;	
	}
	
	/*
	 * Sachin Kumar (starts)
	 */
	
	//Add diagnostic center
	@PostMapping(value = "/addDiagnosticCenter", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessMessage> addDiagnosticCenter(@RequestBody DiagnosticCenterSignUpRequest diagnosticCenterSignUpDetails)
			throws Exception {
		adminService.addDiagnosticCenter(diagnosticCenterSignUpDetails);
		return new ResponseEntity<SuccessMessage>(
				new SuccessMessage("Diagnostic Center SignUp Request", "Diagnostic Center added Successfully"), HttpStatus.OK);
	}
	
	//Remove diagnostic center
	@DeleteMapping("/removeDiagnosticCenter/{diagnosticCenterId}")
	public ResponseEntity<SuccessMessage> removeDiagnosticCenter(@PathVariable("diagnosticCenterId") int diagnosticCenterId) throws Exception
	{
		adminService.removeDiagnosticCenter(diagnosticCenterId);
		return new ResponseEntity<SuccessMessage>(
				new SuccessMessage("Remove Diagnostic Center", "Diagnostic Center removed Successfully"), HttpStatus.OK);
	}
	
	//Update diagnostic center
	@PutMapping("/updateDiagnosticCenter")
	public ResponseEntity<SuccessMessage> updateDiagnosticCenter(@RequestBody DiagnosticCenter diagnosticCenter) throws Exception
	{
		adminService.updateDiagnosticCenter(diagnosticCenter);
		return new ResponseEntity<SuccessMessage>(
				new SuccessMessage("Update Diagnostic Center", "Diagnostic Center updated Successfully"), HttpStatus.OK);
	}
	
	//Get all diagnostic center
	@GetMapping("/getAllDiagnosticCenter")
	public ResponseEntity<List<DiagnosticCenter>> getAllDiagnosticCenter()
	{
		List<DiagnosticCenter> centerList = adminService.getAllDiagnosticCenter();
		return new ResponseEntity<List<DiagnosticCenter>>(centerList,HttpStatus.OK);
	}
	
	/*
	 * Sachin Kumar (ends)
	 */
	
	
}
