package com.cg.healthcare.service;

import java.util.List;
import java.util.Set;

import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.DiagnosticTest;
import com.cg.healthcare.requests.TestResultForm;

public interface IDiagnosticCenterService {

	DiagnosticTest getTestInfo(String testName);

	List<Bed> listOfVacantBeds();

	DiagnosticCenter getDiagnosticCenterByUsername(String diagnosticCenterUsername);

	void addICUBeds(String diagnosticCenterUsername, int noOfBeds, double bedPrice, boolean isKneeTiltAvailable,
			boolean isHeadTiltAvailable, boolean isElectric, int noOfFunctions) throws Exception;

	void addICCUBeds(String diagnosticCenterUsername, int noOfBeds, double bedPrice, boolean batteryBackUp,
			boolean hasABS, boolean remoteOperated, String type) throws Exception;

	void addGeneralBeds(String diagnosticCenterUsername, int noOfBeds, double bedPrice, boolean isMovable,
			String frameMaterial) throws Exception;

	void addVentilatorBeds(String diagnosticCenterUsername, int noOfBeds, double bedPrice, int respiratoryRate,
			String type) throws Exception;

	Set<Bed> getBeds(String diagnosticCenterUsername) throws Exception;

	void removeBed(String diagnosticCenterUsername, Integer bedId) throws Exception;

	String updateTestResult(TestResultForm testResult) throws Exception;

	

}