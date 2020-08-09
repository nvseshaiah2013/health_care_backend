package com.cg.healthcare.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.DiagnosticTest;
import com.cg.healthcare.service.AdminService;
import com.cg.healthcare.service.JwtUtil;

@RestController
@RequestMapping(path="/api/admin")
public class AdminController {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AdminService adminService;

	public String getAdminByUsername(HttpServletRequest request) throws Exception {
		String header = request.getHeader("Authorization");
		String token = header.substring(7);
		String username = jwtUtil.extractUsername(token);
		return username;	
	}
	
	@GetMapping("/getAllTests")
	public ResponseEntity<List<DiagnosticTest>> getAllTests(){
		List<DiagnosticTest> tests=adminService.getAllTest();
		return new ResponseEntity<List<DiagnosticTest>>(tests,HttpStatus.OK);
	}
	
	@PostMapping("/addNewTest")
	public ResponseEntity<DiagnosticTest> addNewTest(@RequestBody DiagnosticTest test){
		DiagnosticTest testAdded=adminService.addNewTest(test);
		return new ResponseEntity<DiagnosticTest>(testAdded,HttpStatus.OK);
	}
	@PutMapping("/editTest")
	public ResponseEntity<DiagnosticTest> editTest(@RequestBody DiagnosticTest test){
		DiagnosticTest updatedTest=adminService.updateTestDetail(test);
		return new ResponseEntity<DiagnosticTest>(updatedTest,HttpStatus.OK);
	}
	@GetMapping("/getAllDiagnosticCenter")
	public ResponseEntity<List<DiagnosticCenter>> getAllDiagnosticCenter(){
		List<DiagnosticCenter> diagnosticCenters=adminService.getAllDiagnosticCenter();
		return new ResponseEntity<List<DiagnosticCenter>>(diagnosticCenters,HttpStatus.OK);
	}
}
