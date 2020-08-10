package com.cg.healthcare.service;

import com.cg.healthcare.requests.PatientSignUpRequest;
import com.cg.healthcare.responses.LoginResponse;

public interface IPublicService {

	void registerPatient(PatientSignUpRequest patientRequest) throws Exception;

	LoginResponse getAuthenticationToken(String username, String password) throws Exception;

}