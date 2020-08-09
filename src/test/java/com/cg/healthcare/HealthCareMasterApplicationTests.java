package com.cg.healthcare;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.TestRepository;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.DiagnosticTest;
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
	
	@BeforeAll
	public static void init() {
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
		List<DiagnosticTest> tests=new LinkedList<>();
		tests.add(test2);
		assertEquals(2,adminService.removeTestFromDiagnosticCenter(diagnosticCenter.getId(), tests).size());
	}
	
	@org.junit.jupiter.api.Test
	public void getTestsOfDiagnosticCenterTest() throws Exception {
		when(centerRepository.findById(diagnosticCenter.getId())).thenReturn(Optional.of(diagnosticCenter));
		assertEquals(2,adminService.getTestsOfDiagnosticCenter(diagnosticCenter.getId()).size());
	}
	
	/*
	 * Ayush Gupta's code ends
	 */

}
