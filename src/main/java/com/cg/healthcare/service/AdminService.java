package com.cg.healthcare.service;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.TestRepository;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.DiagnosticTest;
import com.cg.healthcare.exception.NoTestFoundAtThisCenterException;
import com.cg.healthcare.exception.TestAlreadyFoundException;
import com.cg.healthcare.exception.TestNotPresentInCenter;

@Service
@Transactional
public class AdminService {

	/*
	 * 
	 * Ayush Gupta's code starts
	 */
	@Autowired
	private TestRepository testRepository;
	
	@Autowired
	private DiagnosticCenterRepository centerRepository;
	
	public List<DiagnosticTest> getAllTest(){
		List<DiagnosticTest> tests=testRepository.findAll();
		return tests;
	}
	
	public List<DiagnosticCenter> getAllDiagnosticCenter(){
		List<DiagnosticCenter> centers=centerRepository.findAll();
		return centers;
	}
	public DiagnosticTest addNewTest(DiagnosticTest test) {
		DiagnosticTest addedTest=testRepository.save(test);
		return addedTest;
	}
	public DiagnosticTest updateTestDetail(DiagnosticTest test) {
		DiagnosticTest updatedTest=testRepository.save(test);
		return updatedTest;
	}
	
	public DiagnosticCenter getDiagnosticCenterById(int centerId) {
		DiagnosticCenter center=centerRepository.findById(centerId).get();
		return center;
		
	}
	
	public List<DiagnosticTest> getTestsOfDiagnosticCenter(int centerId){
		DiagnosticCenter center=getDiagnosticCenterById(centerId);
		List<DiagnosticTest> testList=new LinkedList<>(center.getTests());
		if(testList.size()==0) {
			throw new NoTestFoundAtThisCenterException("No Test Found At This Center");
		}
		return testList;
	}
	public List<DiagnosticTest> addTestToDiagnosticCenter(int centerId,List<DiagnosticTest> tests) throws Exception{
		DiagnosticCenter center=getDiagnosticCenterById(centerId);
		List<DiagnosticTest> centerTest=new LinkedList<DiagnosticTest>(center.getTests());
		for(int i=0;i<tests.size();i++) {
			if(centerTest.contains(tests.get(i))) {
				throw new TestAlreadyFoundException("Some Tests are already Found in Diagnostic Center");
			}
		}
		center.getTests().addAll(tests);
		centerRepository.save(center);
		List<DiagnosticTest> updatedTestList=new LinkedList<DiagnosticTest>(center.getTests());
		return updatedTestList;
	}
	public List<DiagnosticTest> removeTestFromDiagnosticCenter(int centerId,List<DiagnosticTest> tests) throws Exception{
		DiagnosticCenter center=getDiagnosticCenterById(centerId);
		List<DiagnosticTest> centerTest=new LinkedList<DiagnosticTest>(center.getTests());
		for(int i=0;i<tests.size();i++) {
			if(!centerTest.contains(tests.get(i))) {
				throw new TestNotPresentInCenter("Some Tests are not present in Diagnostic Center");
			}
		}
		center.getTests().removeAll(tests);
		centerRepository.save(center);
		List<DiagnosticTest> updatedTestList=new LinkedList<DiagnosticTest>(center.getTests());
		return updatedTestList;
	}
	
	/*
	 * 
	 * Ayush Gupta's code ends
	 */
}
