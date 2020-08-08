package com.cg.healthcare.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.UserRepository;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.User;
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
	
	//Add
	public DiagnosticCenter addDiagnosticCenter(DiagnosticCenterSignUpRequest diagnosticCenter) throws Exception
	{ 
		User toFindUser = userRepository.findByUsername(diagnosticCenter.getUserName());
		if(toFindUser!=null) {
<<<<<<< HEAD
			throw new UsernameAlreadyExistsException(null, null);
=======
			throw new UsernameAlreadyExistsException("Username Exception", "Username Exists Exception");
>>>>>>> 2cad45aeff8d47a41d5c69f624eebf93a2976dcf
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

}
