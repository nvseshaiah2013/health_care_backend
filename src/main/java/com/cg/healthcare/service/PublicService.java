package com.cg.healthcare.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.cg.healthcare.dao.PatientRepository;
import com.cg.healthcare.dao.UserRepository;
import com.cg.healthcare.entities.Patient;
import com.cg.healthcare.entities.User;
import com.cg.healthcare.exception.UsernameAlreadyExistsException;
import com.cg.healthcare.requests.PatientSignUpRequest;
import com.cg.healthcare.responses.LoginResponse;

@Service
@Transactional
public class PublicService implements IPublicService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private LoginUserService loginService;
	
	// Venkat Starts

	@Override
	public void registerPatient(PatientSignUpRequest patientRequest) throws Exception {
		User user = userRepository.findByUsername(patientRequest.getUsername());
		if (user != null) {
			throw new UsernameAlreadyExistsException("Sign Up Exception"," Username Already exists in the System");
		} else {
			String salt = BCrypt.gensalt(10);
			User newUser = new User(patientRequest.getUsername(), BCrypt.hashpw(patientRequest.getPassword(), salt),
					"ROLE_PATIENT");
			User savedUser = userRepository.save(newUser);
			Patient patient = new Patient(savedUser.getId(), patientRequest.getName(), patientRequest.getAge(),
					patientRequest.getGender(),patientRequest.getPhoneNo());
			patientRepository.save(patient);
		}
	}

	@Override
	public LoginResponse getAuthenticationToken(String username, String password) throws Exception {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password");
		}

		final UserDetails userDetails = loginService.loadUserByUsername(username);

		final String jwt = jwtUtil.generateToken(userDetails);
		User user = userRepository.findByUsername(username);
		return new LoginResponse(jwt, user);
	}
	
	//Venkat Ends

	@Override
	public void registerAdmin(String username, String password) throws Exception {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			throw new UsernameAlreadyExistsException("Sign Up Exception"," Username Already exists in the System");
		} else {
			String salt = BCrypt.gensalt(10);
			User newUser = new User(username, BCrypt.hashpw(password, salt),
					"ROLE_ADMIN");
			userRepository.save(newUser);
		}
	}
}
