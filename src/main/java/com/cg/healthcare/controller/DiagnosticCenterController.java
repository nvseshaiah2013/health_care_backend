package com.cg.healthcare.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.GeneralBed;
import com.cg.healthcare.entities.IntensiveCareBed;
import com.cg.healthcare.entities.IntensiveCriticalCareBed;
import com.cg.healthcare.entities.VentilatorBed;
import com.cg.healthcare.requests.AddGeneralBedRequest;
import com.cg.healthcare.requests.AddICCUBedRequest;
import com.cg.healthcare.requests.AddICUBedRequest;
import com.cg.healthcare.requests.AddVentilatorBedRequest;
import com.cg.healthcare.requests.TestResultForm;
import com.cg.healthcare.responses.BedsList;
import com.cg.healthcare.responses.SuccessMessage;
import com.cg.healthcare.service.IDiagnosticCenterService;
import com.cg.healthcare.service.IJwtUtil;

@RestController
@RequestMapping(path = "/api/center")
public class DiagnosticCenterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DiagnosticCenterController.class);

	@Autowired
	private IDiagnosticCenterService diagnosticService;

	@Autowired
	private IJwtUtil jwtUtil;

	/**
	 * Adds General Beds to the Diagnostic Center
	 * 
	 * @param request    - HttpServletRequest
	 * @param bedRequest - AdGeneralBedRequest
	 * @return - SuccessMessage
	 * @throws Exception - InvalidDiagnosticCenterException
	 */
	@PostMapping(value = "/addGeneralBed", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessMessage> addGeneralBed(HttpServletRequest request,
			
			@Valid @RequestBody AddGeneralBedRequest bedRequest) throws Exception {
		
		String username = getDiagnosticCenterByUsername(request);
		
		diagnosticService.addGeneralBeds(username, bedRequest.getNoOfBeds(), bedRequest.getPricePerDay(),
				
				bedRequest.isMovable(), bedRequest.getFrameMaterial());
		
		LOGGER.info("General Beds Added");
		
		return new ResponseEntity<SuccessMessage>(new SuccessMessage("Bed Addition", "General Beds Added Successfully"),
				
				HttpStatus.ACCEPTED);
	}

	/**
	 * Adds Ventilator Beds to The Diagnostic Center
	 * 
	 * @param request    - HttpServletRequest
	 * @param bedRequest - AddVentilatorBedRequest
	 * @return - SuccessMessage
	 * @throws Exception - InvalidDiagnosticCenterException,
	 */

	@PostMapping(value = "/addVentilatorBed", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessMessage> addVentilatorBed(HttpServletRequest request,
			
			@Valid @RequestBody AddVentilatorBedRequest bedRequest) throws Exception {
		
		String username = getDiagnosticCenterByUsername(request);
		
		diagnosticService.addVentilatorBeds(username, bedRequest.getNoOfBeds(), bedRequest.getPricePerDay(),
				
				bedRequest.getRespiratoryRate(), bedRequest.getType());
		
		LOGGER.info("Ventilator Beds Added");
		
		return new ResponseEntity<SuccessMessage>(new SuccessMessage("Bed Addition", "Ventilator Beds Added"),
				
				HttpStatus.ACCEPTED);
	}
	
	/**
	 * Adds ICU Bed to the Diagnostic Center
	 * @param request HttpServletRequest
	 * @param bedRequest - AddICUBedRequest
	 * @return - SuccessMessage 
	 * @throws Exception - InvalidDiagnosticCenterException,
	 */
	
	@PostMapping(value = "/addICUBed", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessMessage> addICUBeds(HttpServletRequest request, 
			@Valid @RequestBody AddICUBedRequest bedRequest
			) throws Exception {
		
		String username = getDiagnosticCenterByUsername(request);
		
		diagnosticService.addICUBeds(username, bedRequest.getNoOfBeds(), bedRequest.getPricePerDay(), bedRequest.isKneeTiltAvailable(), 
				bedRequest.isHeadTiltAvailable(),
					bedRequest.isElectric(), bedRequest.getNoOfFunctions());
		
		LOGGER.info("Added ICU Beds");
		
		return new ResponseEntity<SuccessMessage>(new SuccessMessage("Bed Addition","ICU Bed Added"), HttpStatus.ACCEPTED);
	}

	/**
	 * Add ICCU Beds to Diagnostic Center
	 * @param request - HttpServletRequest
	 * @param bedRequest - AddICCUBedRequest
	 * @return - SuccessMessage
	 * @throws Exception - InvalidDiagnosticCenterException,
	 */
	
	@PostMapping(value = "/addICCUBed", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessMessage> addICCUBeds(HttpServletRequest request, @Valid @RequestBody AddICCUBedRequest bedRequest) throws Exception {
		
		String username = getDiagnosticCenterByUsername(request);
		
		diagnosticService.addICCUBeds(username, bedRequest.getNoOfBeds(), bedRequest.getPricePerDay(), bedRequest.isBatteryBackup(), 
				bedRequest.isHasABS(), bedRequest.isRemoteOperated(), bedRequest.getType());
		
		LOGGER.info("Added ICCU Beds");
		
		return new ResponseEntity<SuccessMessage>(new SuccessMessage("Bed Addition","ICCU Bed Added"),HttpStatus.ACCEPTED);
	}
	
	/**
	 * Remove Bed Function
	 * @param request - HttpServletRequest
	 * @param bedId - Integer
	 * @return - SuccessMessage
	 * @throws Exception - InvalidBedId, InvalidDiagnosticCenterRequest
	 */
	@DeleteMapping(value = "/removeBed/{bedId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessMessage> removeBed(HttpServletRequest request, @PathVariable("bedId") Integer bedId ) throws Exception {
		
		String username = getDiagnosticCenterByUsername(request);
		
		diagnosticService.removeBed(username, bedId);
		
		LOGGER.info("Removed Bed with Id : " + bedId);
		
		return new ResponseEntity<SuccessMessage>(new SuccessMessage("Bed Removal", "Bed with Id : " + bedId  + " removed" ),
				HttpStatus.ACCEPTED);
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/getBeds", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BedsList> getBedsOfDiagnosticCenter(HttpServletRequest request) throws Exception {
		
		String username = getDiagnosticCenterByUsername(request);
		
		Set<Bed> beds = diagnosticService.getBeds(username);
		
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
		
		LOGGER.info("List of Beds Returned");
		
		return new ResponseEntity<BedsList>(bedsList, HttpStatus.OK);
	}
	
	
	@PostMapping("/updatetestresult")
	public String updateTestResult(@RequestBody TestResultForm testresultForm)
	{
			return diagnosticService.updateTestResult(testresultForm);
	}
	
	/**
	 * Gets the Username From the JWToken
	 * 
	 * @param request - HttpServletRequest
	 * @return - SuccessMessage
	 * @throws Exception - JWTException
	 */
	
	

	public String getDiagnosticCenterByUsername(HttpServletRequest request) throws Exception {
		String header = request.getHeader("Authorization");
		String token = header.substring(7);
		String username = jwtUtil.extractUsername(token);
		return username;
	}
}
