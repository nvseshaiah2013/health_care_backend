package com.cg.healthcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.healthcare.responses.SuccessMessage;
import com.cg.healthcare.service.BedService;

@RestController
@RequestMapping("/api/center/bed")
@CrossOrigin(origins = "http://localhost:4200")
public class BedController {
	
	@Autowired
	private BedService bedService;
	
	@PostMapping(value="/admitPatient/{appointmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<SuccessMessage> admitPatient(@PathVariable("appointmentId") int appointmentId)
				throws Exception {
			bedService.admittedSuccessfully(appointmentId);
			return new ResponseEntity<SuccessMessage>(
					new SuccessMessage("Admitted successfully", "Patient Admitted Successfully"), HttpStatus.ACCEPTED);
		
		}
	
	@PostMapping(value="/admitGeneralBedPatient/{appointmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessMessage> admitGeneralBedPatient(@PathVariable("appointmentId") int appointmentId)
			throws Exception {
		boolean status = bedService.admittedGeneralBedSuccessfully(appointmentId);
		if(status)
		return new ResponseEntity<SuccessMessage>(
				new SuccessMessage("Admitted successfully", "Patient Admitted to general bed Successfully"), HttpStatus.ACCEPTED);
		else {
			return new ResponseEntity<SuccessMessage>(new SuccessMessage("Waiting List", 
					"Patient Added to Waiting List of General Bed"),HttpStatus.OK);
		}
	
	}
	
	@PostMapping(value="/admitIntensiveCareBedPatient/{appointmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessMessage> admitIntesiveCareBedPatient(@PathVariable("appointmentId") int appointmentId)
			throws Exception {
		boolean status = bedService.admittedIntensiveCareBedSuccessfully(appointmentId);
		if(status)
		return new ResponseEntity<SuccessMessage>(
				new SuccessMessage("Admitted successfully", "Patient Admitted to Intensive Care bed Successfully"), HttpStatus.ACCEPTED);
		else {
			return new ResponseEntity<SuccessMessage>(new SuccessMessage("Waiting List", 
					"Patient Added to Waiting List of Intensive Care Bed"),HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/admitIntensiveCriticalCareBedPatient/{appointmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessMessage> admitIntesiveCriticalCareBedPatient(@PathVariable("appointmentId") int appointmentId)
			throws Exception {
		boolean status = bedService.admittedIntensiveCriticalCareBedSuccessfully(appointmentId);
		if(status)
		return new ResponseEntity<SuccessMessage>(
				new SuccessMessage("Admitted successfully", "Patient Admitted to Intensive Critical Care bed Successfully"), HttpStatus.ACCEPTED);
		else {
			return new ResponseEntity<SuccessMessage>(new SuccessMessage("Waiting List", 
					"Patient Added to Waiting List of Intensive Critical Care Bed"),HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/admitVentilatorBedPatient/{appointmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessMessage> admitVentilatorBedPatient(@PathVariable("appointmentId") int appointmentId)
			throws Exception {
		boolean status = bedService.admittedVentilatorBedSuccessfully(appointmentId);
		if(status)
		return new ResponseEntity<SuccessMessage>(
				new SuccessMessage("Admitted successfully", "Patient Admitted to Ventilator bed Successfully"), HttpStatus.ACCEPTED);
		else {
			return new ResponseEntity<SuccessMessage>(new SuccessMessage("Waiting List", 
					"Patient Added to Waiting List of Ventilator Bed"),HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/dischargePatient/{appointmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessMessage> discargePatient(@PathVariable("appointmentId") int appointmentId)
			throws Exception {
		bedService.dischargePatient(appointmentId);
		return new ResponseEntity<SuccessMessage>(
				new SuccessMessage("Discharged successfully", "Patient discharged Successfully"), HttpStatus.ACCEPTED);
	
	}

}