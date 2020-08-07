package com.cg.healthcare.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.UserRepository;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.User;
import com.cg.healthcare.exception.UsernameAlreadyExistsException;
import com.cg.healthcare.requests.DiagnosticCenterSignUpRequest;
import com.cg.healthcare.service.AdminService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AdminTests {

	@InjectMocks
	private AdminService adminService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private DiagnosticCenterRepository diagnosticCenterRepository;
	
	private static User mockDiagnosticUser;
	private static DiagnosticCenter mockDiagnosticCenter;
	private static DiagnosticCenterSignUpRequest mockRequestSignUp;
	
	@BeforeEach
	public void init()
	{
		mockDiagnosticUser = new User(10, "center1@gmail.com", "Password@123", "ROLE_CENTER");
		mockDiagnosticCenter = new DiagnosticCenter(mockDiagnosticUser.getId(), "Center 1", "9876543210", "Address",
				"email@gmail.com", "Services");
		mockRequestSignUp = new DiagnosticCenterSignUpRequest("center1@gmail.com","Password@123","","","","","");
	}
	
	@Test
	public void addDiagnosticCenter() throws Exception
	{
		assertThrows(UsernameAlreadyExistsException.class,() -> {
			
		Mockito.when(userRepository.findByUsername(mockRequestSignUp.getUserName())).thenReturn(mockDiagnosticUser);
		Mockito.when(userRepository.save(mockDiagnosticUser)).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.save(mockDiagnosticCenter)).thenReturn(mockDiagnosticCenter);
		DiagnosticCenter center = adminService.addDiagnosticCenter(mockRequestSignUp);
		
		});
		
	}
	
}
