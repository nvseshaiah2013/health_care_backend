package com.cg.healthcare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.TestRepository;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.DiagnosticTest;
import com.cg.healthcare.exception.NoTestFoundAtThisCenterException;
import com.cg.healthcare.exception.TestAlreadyFoundException;
import com.cg.healthcare.exception.TestNotPresentInCenter;
import com.cg.healthcare.service.AdminService;


@SpringBootTest
class HealthCareMasterApplicationTests {

	@Test
	void contextLoads() {
	}
	/*
	 * Ayush Gupta's code starts
	 * 
	 */
	@Autowired
	private AdminService adminService;
	
	@MockBean
	private TestRepository testRepository;
	
	@MockBean
	private DiagnosticCenterRepository centerRepository;
	
	private static DiagnosticCenter diagnosticCenter;
	private static DiagnosticTest test1,test2,test3;
	
	@BeforeEach
	public void init() {
		diagnosticCenter=new DiagnosticCenter("Akash Diagnostic Center","1223","UP","akash@gmail.com","testing");
		diagnosticCenter.setId(101);
		test1=new DiagnosticTest("blood test",1000,"13-17","gm/dl");
		test2=new DiagnosticTest("Eye Test",1000,"6/6","mm");
		test3=new DiagnosticTest("LFT",2000,"<1.1","milligm/dl");
	}
	
	@org.junit.jupiter.api.Test
	public void getAllTestsTest() {
		List<DiagnosticTest> tests=new LinkedList<>();
		tests.add(test1);
		tests.add(test2);
		when(testRepository.findAll()).thenReturn(tests);
		assertEquals(2, adminService.getAllTest().size());
	}
	
	@org.junit.jupiter.api.Test
	public void addNewTestTest() {
		when(testRepository.save(test1)).thenReturn(test1);
		assertEquals(test1,adminService.addNewTest(test1));
	}
	

	
	@org.junit.jupiter.api.Test
	public void noTestFoundAtThisCenterExceptionTest() throws Exception{
		when(centerRepository.findById(diagnosticCenter.getId())).thenReturn(Optional.of(diagnosticCenter));
		assertThrows(NoTestFoundAtThisCenterException.class,()->{
			adminService.getTestsOfDiagnosticCenter(diagnosticCenter.getId());
		});
	}
	@org.junit.jupiter.api.Test
	public void addTestToDiagnosticCenterTest() throws Exception{
		diagnosticCenter.getTests().add(test1);
		when(centerRepository.findById(diagnosticCenter.getId())).thenReturn(Optional.of(diagnosticCenter));
		List<DiagnosticTest> tests=new LinkedList<>();
		tests.add(test2);
		tests.add(test3);
		assertEquals(3,adminService.addTestToDiagnosticCenter(diagnosticCenter.getId(),tests).size());
	}
	
	
	@org.junit.jupiter.api.Test
	public void removeTestFromDiagnosticCenterTest() throws Exception {
		when(centerRepository.findById(diagnosticCenter.getId())).thenReturn(Optional.of(diagnosticCenter));
		diagnosticCenter.getTests().add(test1);
		diagnosticCenter.getTests().add(test2);
		diagnosticCenter.getTests().add(test3);
		List<DiagnosticTest> tests=new LinkedList<>();
		tests.add(test2);
		assertEquals(2,adminService.removeTestFromDiagnosticCenter(diagnosticCenter.getId(), tests).size());
	}
	
	@org.junit.jupiter.api.Test
	@Order(6)
	public void getTestsOfDiagnosticCenterTest() throws Exception {
		when(centerRepository.findById(diagnosticCenter.getId())).thenReturn(Optional.of(diagnosticCenter));
		diagnosticCenter.getTests().add(test1);
		diagnosticCenter.getTests().add(test2);
		assertEquals(2,adminService.getTestsOfDiagnosticCenter(diagnosticCenter.getId()).size());
	}
	
	@org.junit.jupiter.api.Test
	public void testAlreadyFoundTest() throws Exception{
		when(centerRepository.findById(diagnosticCenter.getId())).thenReturn(Optional.of(diagnosticCenter));
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
		when(centerRepository.findById(diagnosticCenter.getId())).thenReturn(Optional.of(diagnosticCenter));
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
