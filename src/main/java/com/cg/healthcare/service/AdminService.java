package com.cg.healthcare.service;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.TestRepository;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.Test;

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
	
	public List<Test> getAllTest(){
		List<Test> tests=testRepository.findAll();
		return tests;
	}
	
	public List<DiagnosticCenter> getAllDiagnosticCenter(){
		List<DiagnosticCenter> centers=centerRepository.findAll();
		return centers;
	}
	public Test addNewTest(Test test) {
		Test addedTest=testRepository.save(test);
		return addedTest;
	}
	public Test updateTestDetail(Test test) {
		Test updatedTest=testRepository.save(test);
		return updatedTest;
	}
	
	public DiagnosticCenter getDiagnosticCenterById(int centerId) {
		DiagnosticCenter center=centerRepository.findById(centerId).get();
		return center;
		
	}
	
	public List<Test> getTestsOfDiagnosticCenter(int centerId){
		DiagnosticCenter center=getDiagnosticCenterById(centerId);
		List<Test> testList=new LinkedList<>(center.getTests());
		return testList;
	}
	public List<Test> addTestToDiagnosticCenter(int centerId,List<Test> tests){
		DiagnosticCenter center=getDiagnosticCenterById(centerId);
		center.getTests().addAll(tests);
		centerRepository.save(center);
		List<Test> updatedTestList=new LinkedList<Test>(center.getTests());
		return updatedTestList;
	}
	public List<Test> removeTestFromDiagnosticCenter(int centerId,List<Test> tests){
		DiagnosticCenter center=getDiagnosticCenterById(centerId);
		center.getTests().removeAll(tests);
		centerRepository.save(center);
		List<Test> updatedTestList=new LinkedList<Test>(center.getTests());
		return updatedTestList;
	}
	
	/*
	 * 
	 * Ayush Gupta's code ends
	 */
}
