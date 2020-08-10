package com.cg.healthcare.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
import com.cg.healthcare.dao.TestRepository;
import com.cg.healthcare.dao.UserRepository;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.DiagnosticTest;
import com.cg.healthcare.entities.User;
import com.cg.healthcare.exception.NoTestFoundAtThisCenterException;
import com.cg.healthcare.exception.TestAlreadyFoundException;
import com.cg.healthcare.exception.TestNotPresentInCenter;
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
		mockRequestSignUp = new DiagnosticCenterSignUpRequest("Center 1","9876543210","Address","email@gmail.com", "Services",
				"center1@gmail.com","Password@123");
		
		diagnosticCenter=new DiagnosticCenter("Akash Diagnostic Center","1223","UP","akash@gmail.com","testing");
		diagnosticCenter.setId(101);
		test1=new DiagnosticTest("blood test",1000,"13-17","gm/dl");
		test2=new DiagnosticTest("Eye Test",1000,"6/6","mm");
		test3=new DiagnosticTest("LFT",2000,"<1.1","milligm/dl");
	}
	
	/*
	 * Sachin Kumar( Starts )
	 */
	@Test
	public void addDiagnosticCenter() throws Exception
	{
		assertThrows(UsernameAlreadyExistsException.class,() -> {
			
		Mockito.when(userRepository.findByUsername(mockRequestSignUp.getUserName())).thenReturn(mockDiagnosticUser);
		Mockito.when(userRepository.save(mockDiagnosticUser)).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.save(mockDiagnosticCenter)).thenReturn(mockDiagnosticCenter);
		adminService.addDiagnosticCenter(mockRequestSignUp);
		});
		
	}
	
	@Test
	public void getDiagnosticCenterById()
	{
		Mockito.when(diagnosticCenterRepository.findById(10)).thenReturn(Optional.of(mockDiagnosticCenter));
		DiagnosticCenter center = adminService.getDiagnosticCenterById(10);
		assertEquals(10, center.getId());
	}
	
	@Test 
	public void removeDiagnosticCenter()
	{
		Mockito.when(diagnosticCenterRepository.findById(10)).thenReturn(Optional.of(mockDiagnosticCenter));
		//Mockito.when(diagnosticCenterRepository.delete(mockDiagnosticCenter)).thenReturn(Optional.of(mockDiagnosticCenter));
		List<DiagnosticCenter> center = adminService.removeDiagnosticCenter(mockDiagnosticCenter.getId());
		assertEquals(0,center.size());
	}
	
	@Test
	public void updateDiagnosticCenter()
	{
		Mockito.when(diagnosticCenterRepository.save(mockDiagnosticCenter)).thenReturn(mockDiagnosticCenter);
		DiagnosticCenter center = adminService.updateDiagnosticCenter(mockDiagnosticCenter);
		assertEquals(10, center.getId());
	}
	
	@Test
	public void getAllDiagnosticCenter()
	{
		List<DiagnosticCenter> centers = new LinkedList<>();
		centers.add(mockDiagnosticCenter);
		Mockito.when(diagnosticCenterRepository.findAll()).thenReturn(centers);
		assertEquals(1, adminService.getAllDiagnosticCenter().size());
	}
	
	/*
	 * Sachin Kumar( Ends )
	 */
	
	
	
	@Mock
	private TestRepository testRepository;
	
	@Mock
	private DiagnosticCenterRepository centerRepository;
	
	private static DiagnosticCenter diagnosticCenter;
	private static DiagnosticTest test1,test2,test3;
	
	
	
	@Test
	public void getAllTestsTest() {
		List<DiagnosticTest> tests=new LinkedList<>();
		tests.add(test1);
		tests.add(test2);
		Mockito.when(testRepository.findAll()).thenReturn(tests);
		assertEquals(2, adminService.getAllTest().size());
	}
	
	@Test
	public void addNewTestTest() {
		Mockito.when(testRepository.save(test1)).thenReturn(test1);
		assertEquals(test1,adminService.addNewTest(test1));
	}
	

	
	@Test
	public void noTestFoundAtThisCenterExceptionTest() throws Exception{
		Mockito.when(centerRepository.getOne(diagnosticCenter.getId())).thenReturn(diagnosticCenter);
		assertThrows(NoTestFoundAtThisCenterException.class,()->{
			adminService.getTestsOfDiagnosticCenter(diagnosticCenter.getId());
		});
	}
	@Test
	public void addTestToDiagnosticCenterTest() throws Exception{
		diagnosticCenter.getTests().add(test1);
		Mockito.when(centerRepository.getOne(diagnosticCenter.getId())).thenReturn(diagnosticCenter);
		List<DiagnosticTest> tests=new LinkedList<>();
		tests.add(test2);
		tests.add(test3);
		assertEquals(3,adminService.addTestToDiagnosticCenter(diagnosticCenter.getId(),tests).size());
	}
	
	
	@Test
	public void removeTestFromDiagnosticCenterTest() throws Exception {
		Mockito.when(centerRepository.getOne(diagnosticCenter.getId())).thenReturn(diagnosticCenter);
		diagnosticCenter.getTests().add(test1);
		diagnosticCenter.getTests().add(test2);
		diagnosticCenter.getTests().add(test3);
		List<DiagnosticTest> tests=new LinkedList<>();
		tests.add(test2);
		assertEquals(2,adminService.removeTestFromDiagnosticCenter(diagnosticCenter.getId(), tests).size());
	}
	
	@Test
	public void getTestsOfDiagnosticCenterTest() throws Exception {
		Mockito.when(centerRepository.getOne(diagnosticCenter.getId())).thenReturn(diagnosticCenter);
		diagnosticCenter.getTests().add(test1);
		diagnosticCenter.getTests().add(test2);
		assertEquals(2,adminService.getTestsOfDiagnosticCenter(diagnosticCenter.getId()).size());
	}
	
	@org.junit.jupiter.api.Test
	public void testAlreadyFoundTest() throws Exception{
		Mockito.when(centerRepository.getOne(diagnosticCenter.getId())).thenReturn(diagnosticCenter);
		diagnosticCenter.getTests().add(test2);
		diagnosticCenter.getTests().add(test3);
		List<DiagnosticTest> tests=new LinkedList<>();
		tests.add(test3);
		assertThrows(TestAlreadyFoundException.class,()->{
			adminService.addTestToDiagnosticCenter(diagnosticCenter.getId(), tests);
		});
	}
	@org.junit.jupiter.api.Test
	public void testNotPresentInCenterExceptionTest() throws Exception{
		Mockito.when(centerRepository.getOne(diagnosticCenter.getId())).thenReturn(diagnosticCenter);
		diagnosticCenter.getTests().add(test1);
		List<DiagnosticTest> tests=new LinkedList<>();
		tests.add(test2);
		assertThrows(TestNotPresentInCenter.class,()->{
			adminService.removeTestFromDiagnosticCenter(diagnosticCenter.getId(), tests);
		});
	}
	/*
	 * Ayush Gupta's code ends
	 */
}
