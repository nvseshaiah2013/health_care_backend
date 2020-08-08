package com.cg.healthcare.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.UserRepository;
import com.cg.healthcare.dao.WaitingPatientRepository;
import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.User;
import com.cg.healthcare.entities.WaitingPatient;
import com.cg.healthcare.exception.NoBedAvailableException;
import com.cg.healthcare.exception.PartialBedsAllocationException;
import com.cg.healthcare.exception.UsernameAlreadyExistsException;
import com.cg.healthcare.requests.DiagnosticCenterSignUpRequest;

@Service
@Transactional
public class AdminService {
	
	/*
	 * Sachin Kumar (Starts)
	 */
	
	@Autowired
	private DiagnosticCenterRepository diagnosticCenterRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WaitingPatientRepository waitingPatientRepository;
	
	//Add
	public DiagnosticCenter addDiagnosticCenter(DiagnosticCenterSignUpRequest diagnosticCenter) throws Exception
	{ 
		User toFindUser = userRepository.findByUsername(diagnosticCenter.getUserName());
		if(toFindUser!=null) {
			throw new UsernameAlreadyExistsException("Username Exception", "Username Exists Exception");
		}
		String salt = BCrypt.gensalt(10);
		User user = new User(diagnosticCenter.getUserName(), BCrypt.hashpw(diagnosticCenter.getPassword(),salt), "ROLE_CENTER");
		User newUser = userRepository.save(user);
		DiagnosticCenter newDiagnosticCenter = new DiagnosticCenter(newUser.getId(),diagnosticCenter.getName(), diagnosticCenter.getContactNo(), 
				diagnosticCenter.getAddress(), diagnosticCenter.getContactEmail(), diagnosticCenter.getServicesOffered());
		DiagnosticCenter addedCenter = diagnosticCenterRepository.save(newDiagnosticCenter);
		return addedCenter;
	}
	
	//Get by Id
	public DiagnosticCenter getDiagnosticCenterById(int diagnosticCenterId)
	{
		DiagnosticCenter center = diagnosticCenterRepository.findById(diagnosticCenterId).get();
		return center;
	}
	
	//Remove
	public List<DiagnosticCenter> removeDiagnosticCenter(int diagnosticCenterId)
	{
		DiagnosticCenter center = getDiagnosticCenterById(diagnosticCenterId);
		diagnosticCenterRepository.delete(center);
		return getAllDiagnosticCenter();
	}
	
	//Update
	public DiagnosticCenter updateDiagnosticCenter(DiagnosticCenter diagnosticCenter)
	{
		DiagnosticCenter updatedCenter = diagnosticCenterRepository.save(diagnosticCenter);
		return updatedCenter;
	}
	
	
	//GetAll
	public List<DiagnosticCenter> getAllDiagnosticCenter()
	{	
		List<DiagnosticCenter> centers = diagnosticCenterRepository.findAll();
		return centers;
	}
	
	/*
	 * Sachin Kumar (Ends)
	 */

	
	// Venkat Starts
	
	public void allocateBeds(int diagnosticCenterId, List<Integer> waitingPatientIds ) throws Exception {
		DiagnosticCenter diagnosticCenter = diagnosticCenterRepository.getOne(diagnosticCenterId);
		if(diagnosticCenter == null) {
			throw new Exception();
		}
		Collections.sort(waitingPatientIds);
		int waitingPatientsLength = waitingPatientIds.size();
		int waitingIndex = 0;
		int allocations = 0;
		for(Bed bed : diagnosticCenter.getBeds()) {
			if(waitingIndex < waitingPatientsLength) 
			{
				if(!bed.isOccupied()) {
					++allocations;
					bed.setOccupied(true);
					WaitingPatient patient = waitingPatientRepository.getOne(waitingPatientIds.get(waitingIndex));
					bed.setAppointment(patient.getAppointment());
					waitingPatientRepository.delete(patient);
				}
			}
			else 
				break;
			++waitingIndex;
		}
		diagnosticCenterRepository.save(diagnosticCenter);
		if(allocations > 0 && allocations < waitingPatientsLength) {
			throw new PartialBedsAllocationException("Bed Allocation", "All waiting patients not be able to allocate beds");
		}
		if(allocations == 0) {
			throw new NoBedAvailableException("Bed Allocation", "No Beds Vacant");
		}
	}
	
	public Set<Bed> getBeds(int diagnosticCenterId) throws Exception {
		Optional<DiagnosticCenter> diagnosticCenter = diagnosticCenterRepository.findById(diagnosticCenterId);
		if(diagnosticCenter.isPresent()) {
			return diagnosticCenter.get().getBeds();
		}
		else
		{
				return null;
		}
	}
	
	//Venkat Ends
}
