package com.cg.healthcare.controller;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.healthcare.entities.Appointment;
import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.DiagnosticTest;
import com.cg.healthcare.entities.WaitingPatient;
import com.cg.healthcare.requests.AllocateBedRequest;
import com.cg.healthcare.requests.DiagnosticCenterSignUpRequest;
import com.cg.healthcare.responses.SuccessMessage;
import com.cg.healthcare.service.IAdminService;
import com.cg.healthcare.service.IJwtUtil;

@RestController
@RequestMapping(path="/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	
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
	@PutMapping("/updateDiagnosticCenter/{diagnosticCenterId}")
	public ResponseEntity<SuccessMessage> updateDiagnosticCenter(@PathVariable("diagnosticCenterId") int diagnosticCenterId, @RequestBody DiagnosticCenter diagnosticCenter) throws Exception
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
	
	/*
	 * Madhu Starts
	 */

	@GetMapping("/vacantbeds")
	public List<Bed> listOfVacantBeds(){
		return adminService.listOfVacantBeds();
		
	}
	
	
	/*
	 * Madhu Ends
	 * 
	 */
	
	/*
	 * Sachin Pant Starts
	 * 
	 */	
	
	@GetMapping("/getappointment/{id}")
	public ResponseEntity<List<Appointment>> getAppointmentList(@PathVariable("id") String id, @RequestBody Map<String, String> request) throws Exception{
		
		int centreId=Integer.parseInt(id);
		int status1= Integer.parseInt(request.get("status"));
		List<Appointment> response=adminService.getApppointmentList(centreId,request.get("test"),status1); 
		LOGGER.info("Admin watching appointments for centre id "+centreId+" , test "+request.get("test")+" having status "+status1);
		return new ResponseEntity<List<Appointment>>(response,HttpStatus.OK);
	}
	
	@PostMapping("/processappointment")
	public ResponseEntity<String> processAppointment(@RequestBody Map<String,String> appointmentDetails) throws Exception{
		int centreId=Integer.parseInt(appointmentDetails.get("id"));
		String test=appointmentDetails.get("test");
		int testtime=Integer.parseInt(appointmentDetails.get("testtime"));			
		int seats=Integer.parseInt(appointmentDetails.get("seats"));				// no of appointment can handle
		LOGGER.info("Processing appointments with centre id "+centreId+" ,test "+test+" ,testtime "+testtime+"  & no of seat at a time "+seats);
		String response=adminService.processAppointment(centreId,test,testtime,seats);	
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}
	
	/*
	 * Sachin Pant Ends
	 * 
	 */
	
	/*
	 * Ayush Gupta's code start
	 * 
	 */
	//this method is for getting all tests
	@GetMapping("/getAllTests")
	public ResponseEntity<List<DiagnosticTest>> getAllTests(){
		List<DiagnosticTest> tests=adminService.getAllTest();
		return new ResponseEntity<List<DiagnosticTest>>(tests,HttpStatus.OK);
	}
	//this method is for adding new test
	@PostMapping("/addNewTest")
	public ResponseEntity<DiagnosticTest> addNewTest(@RequestBody DiagnosticTest newTest){
		DiagnosticTest addedTest=adminService.addNewTest(newTest);
		return new ResponseEntity<DiagnosticTest>(addedTest,HttpStatus.OK);
	}
	//this test is for updating the details of test..
	@PutMapping("/editTest")
	public ResponseEntity<DiagnosticTest> editTest(@RequestBody DiagnosticTest test){
		DiagnosticTest updatedTest=adminService.updateTestDetail(test);
		return new ResponseEntity<DiagnosticTest>(updatedTest,HttpStatus.OK);
	}
	/*@GetMapping("/getAllDiagnosticCenter")
	public ResponseEntity<List<DiagnosticCenter>> getAllDiagnosticCenter(){
		List<DiagnosticCenter> diagnosticCenters=adminService.getAllDiagnosticCenter();
		return new ResponseEntity<List<DiagnosticCenter>>(diagnosticCenters,HttpStatus.OK);
	}*/
	//this method for getting tests of a diagnostic center
	@GetMapping("/getTestsOfADiagnosticCenter/{centerId}")
	public ResponseEntity<List<DiagnosticTest>> getTestsOfADiagnosticTest(@PathVariable int centerId) throws Exception{
		List<DiagnosticTest> testsOfCenter=adminService.getTestsOfDiagnosticCenter(centerId);
		return new ResponseEntity<List<DiagnosticTest>>(testsOfCenter,HttpStatus.OK);
	}
	//this method is for adding new test to a diagnostic center...
	@PutMapping("/addTestAtTheDiagnosticCenter/{centerId}")
	public ResponseEntity<List<DiagnosticTest>> addTestToADiagnosticCenter(@PathVariable int centerId,
			@RequestBody DiagnosticTest test) throws Exception{
		List<DiagnosticTest> updatedTestList=adminService.addTestToDiagnosticCenter(centerId, test);
		return new ResponseEntity<List<DiagnosticTest>>(updatedTestList,HttpStatus.OK);
	}
	//this method is for removing tests from diagnostic center..
	@PutMapping("/removeTestFromDiagnosticCenter/{centerId}")
	public ResponseEntity<List<DiagnosticTest>> removeTestsFromDiagnosticCenter(@PathVariable int centerId,
			@RequestBody DiagnosticTest test) throws Exception{
		List<DiagnosticTest> updatedTestList=adminService.removeTestFromDiagnosticCenter(centerId, test);
		return new ResponseEntity<List<DiagnosticTest>>(updatedTestList,HttpStatus.OK);
	}
	
	@GetMapping("/getDiagnosticCenterById/{centerId}")
	public ResponseEntity<DiagnosticCenter> getDiagnosticCenterbyId(@PathVariable("centerId") int centerId){
		DiagnosticCenter center=adminService.getDiagnosticCenterById(centerId);
		return new ResponseEntity<DiagnosticCenter>(center,HttpStatus.OK);
	}
	/*
	 * Ayush Gupta code ends
	 */
	
	/**
	 * @author Venkat
	 * @return Returns Success if All patients are allocated beds successfully.
	 */
	
	@PostMapping(value = "/allocateBeds", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessMessage> allocateBedsToWaitingPatients( @RequestBody AllocateBedRequest request) throws Exception {
		adminService.allocateBeds(request.getDiagnosticId(), request.getWaitingPatientIds(),request.getType());
		LOGGER.info("Beds Allocated Successfully");
		return new ResponseEntity<SuccessMessage>(new SuccessMessage("Bed Allocation", "Beds Allocated Successfully"),HttpStatus.OK);
	}
	
	/**
	 * 
	 * @author Venkat
	 * @return A List of WaitingPatients
	 * @throws Exception
	 */
	
	@GetMapping(value = "/getWaitingPatients", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<WaitingPatient>> getWaitingPatients() throws Exception{
		List<WaitingPatient> patients = adminService.getWaitingPatients();
		LOGGER.info(patients.size() + " Waiting Patients Fetched!");
		return new ResponseEntity<List<WaitingPatient>>(patients,HttpStatus.OK);
	}
}