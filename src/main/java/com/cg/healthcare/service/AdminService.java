 package com.cg.healthcare.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.TestRepository;
import com.cg.healthcare.dao.UserRepository;
import com.cg.healthcare.dao.WaitingPatientRepository;
import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.DiagnosticTest;
import com.cg.healthcare.entities.GeneralBed;
import com.cg.healthcare.entities.IntensiveCareBed;
import com.cg.healthcare.entities.IntensiveCriticalCareBed;
import com.cg.healthcare.entities.User;
import com.cg.healthcare.entities.VentilatorBed;
import com.cg.healthcare.entities.WaitingPatient;
import com.cg.healthcare.exception.AppointmentNotApprovedException;
import com.cg.healthcare.exception.DiagnosticCenterNotFoundException;
import com.cg.healthcare.exception.DiagnosticCenterNotPresentException;
import com.cg.healthcare.exception.InvalidBedAllocationException;
import com.cg.healthcare.exception.InvalidBedTypeException;
import com.cg.healthcare.exception.NoBedAvailableException;
import com.cg.healthcare.exception.NoTestFoundAtThisCenterException;
import com.cg.healthcare.exception.PartialBedsAllocationException;
import com.cg.healthcare.exception.TestAlreadyFoundException;
import com.cg.healthcare.exception.TestNotPresentInCenter;
import com.cg.healthcare.exception.UsernameAlreadyExistsException;
import com.cg.healthcare.requests.DiagnosticCenterSignUpRequest;

@Service
@Transactional
public class AdminService implements IAdminService {

	/*
	 * Sachin Kumar (Starts)
	 */

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

	@Autowired
	private DiagnosticCenterRepository diagnosticCenterRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WaitingPatientRepository waitingPatientRepository;

	// Add diagnostic center
	@Override
	public DiagnosticCenter addDiagnosticCenter(DiagnosticCenterSignUpRequest diagnosticCenter) throws Exception {
		User toFindUser = userRepository.findByUsername(diagnosticCenter.getUserName());
		if (toFindUser != null) {
			throw new UsernameAlreadyExistsException("Username Exception", "Username Exists Exception");
		}
		String salt = BCrypt.gensalt(10);
		User user = new User(diagnosticCenter.getUserName(), BCrypt.hashpw(diagnosticCenter.getPassword(), salt),
				"ROLE_CENTER");
		User newUser = userRepository.save(user);
		DiagnosticCenter newDiagnosticCenter = new DiagnosticCenter(newUser.getId(), diagnosticCenter.getName(),
				diagnosticCenter.getContactNo(), diagnosticCenter.getAddress(), diagnosticCenter.getContactEmail(),
				diagnosticCenter.getServicesOffered());
		DiagnosticCenter addedCenter = diagnosticCenterRepository.save(newDiagnosticCenter);
		LOGGER.info("Diagnostic center added successfully...");
		return addedCenter;
	}

	// Get diagnostic center by Id
	@Override
	public DiagnosticCenter getDiagnosticCenterById(int diagnosticCenterId){
		DiagnosticCenter center = diagnosticCenterRepository.findById(diagnosticCenterId).get();
		LOGGER.info("Fetched diagnostic center successfully...");
		return center;
	}

	// Remove diagnostic center by Id
	@Override
	public List<DiagnosticCenter> removeDiagnosticCenter(int diagnosticCenterId) throws Exception{
		
		DiagnosticCenter center = getDiagnosticCenterById(diagnosticCenterId);
		
		if(center==null)
		{
			throw new DiagnosticCenterNotPresentException("Diagnostic center not present");
		}
		diagnosticCenterRepository.delete(center);
		userRepository.deleteById(center.getId());
		LOGGER.info("Diagnostic center removed successfully...");
		return getAllDiagnosticCenter();
	}

	// Update diagnostic center
	@Override
	public DiagnosticCenter updateDiagnosticCenter(DiagnosticCenter diagnosticCenter) {
		DiagnosticCenter center = getDiagnosticCenterById(diagnosticCenter.getId());
		center.setId(diagnosticCenter.getId());
		center.setName(diagnosticCenter.getName());
		center.setContactNo(diagnosticCenter.getContactNo());
		center.setAddress(diagnosticCenter.getAddress());
		center.setContactEmail(diagnosticCenter.getContactEmail());
		center.setServicesOffered(diagnosticCenter.getServicesOffered());
		DiagnosticCenter updatedCenter = diagnosticCenterRepository.save(center);
		LOGGER.info("Diagnostic center updated successfully...");
		return updatedCenter;
	}

	// Get all diagnostic center
	@Override
	public List<DiagnosticCenter> getAllDiagnosticCenter(){
		List<DiagnosticCenter> centers = diagnosticCenterRepository.findAll();
		LOGGER.info("Fetched all diagnostic centers successfully...");
		return centers;
	}

	/*
	 * Sachin Kumar (Ends)
	 */

	// Venkat Starts

	@Override
	public void allocateBeds(int diagnosticCenterId, List<Integer> waitingPatientIds, String type) throws Exception {

		DiagnosticCenter diagnosticCenter = diagnosticCenterRepository.getOne(diagnosticCenterId);

		if (diagnosticCenter == null) {

			LOGGER.error("Diagnostic Center not Found");

			throw new DiagnosticCenterNotFoundException("Diagnostic Center Exception", "Diagnostic Center not Found");

		}
		
		Class<?> bedType = getBedType(type);

		Collections.sort(waitingPatientIds);

		int allocations = 0;

		int waitingIndex = 0;

		int waitingPatientsLength = waitingPatientIds.size();

		List<WaitingPatient> waitingPatients = waitingPatientIds.stream()

				.map((waitingId) -> waitingPatientRepository.getOne(waitingId))

				.filter((waitingPatient) -> waitingPatient.getAppointment().getApprovalStatus() == 1)

				.collect(Collectors.toList());

		int approvedWaitingPatientsLength = waitingPatients.size();

		boolean differentDiagnosticCenterException = false;

		for (Bed bed : diagnosticCenter.getBeds()) {

			if (waitingIndex < approvedWaitingPatientsLength) {

				if (!bed.isOccupied() && bedType.isInstance(bed) && type.equals(waitingPatients.get(waitingIndex).getType())) {

					WaitingPatient patient = waitingPatients.get(waitingIndex);

					if (patient.getAppointment().getDiagnosticCenter().getId() == diagnosticCenterId) {
						++allocations;

						bed.setOccupied(true);

						bed.setAppointment(patient.getAppointment());

						waitingPatientRepository.delete(patient);
					} else {
						differentDiagnosticCenterException = true;
					}

					++waitingIndex;
				}
			} else
				break;
		}

		diagnosticCenterRepository.save(diagnosticCenter);

		if (differentDiagnosticCenterException) {

			LOGGER.error("Appointment taken is from a different diagnostic Center");

			throw new InvalidBedAllocationException("Bed Allocation",
					"Appointment taken is from a different diagnostic Center");

		}

		if (approvedWaitingPatientsLength != waitingPatientsLength) {

			LOGGER.error("Appointment Not Approved Exception");

			throw new AppointmentNotApprovedException("Bed Allocation", "Appointment Not Approved Exception");

		}

		if (allocations > 0 && allocations < approvedWaitingPatientsLength) {

			LOGGER.error("All waiting patients not be able to allocate beds");

			throw new PartialBedsAllocationException("Bed Allocation",
					"All waiting patients not be able to allocate beds");

		}

		if (allocations == 0) {

			LOGGER.error("No Beds Vacant");

			throw new NoBedAvailableException("Bed Allocation", "No Beds Vacant");

		}
	}

	@Override
	public Class<?> getBedType(String bedType) throws Exception {
		
		if(bedType.equals("General")) {
			return new GeneralBed().getClass();
		}
		else if(bedType.equals("Ventilator")){
			return new VentilatorBed().getClass();
		}
		else if(bedType.equals("ICU"))
		{
			return new IntensiveCareBed().getClass();
		}
		else if(bedType.equals("ICCU")) {
			return new IntensiveCriticalCareBed().getClass();
		}
		else {
			LOGGER.error( bedType + " is not a valid kind of Bed");
			throw new InvalidBedTypeException("Bed Type Exception", bedType + " is not a valid kind of Bed");
		}
	}
	
	
	
	@Override
	public Set<Bed> getBeds(int diagnosticCenterId) throws Exception {

		Optional<DiagnosticCenter> diagnosticCenter = diagnosticCenterRepository.findById(diagnosticCenterId);

		if (diagnosticCenter.isPresent()) {

			return diagnosticCenter.get().getBeds();

		}

		else {
			LOGGER.error("Diagnostic Center not Found");

			throw new DiagnosticCenterNotFoundException("Diagnostic Center Exception", "Diagnostic Center not Found");
		}
	}
	
	List<WaitingPatient> getWaitingPatients() throws Exception {
		return this.waitingPatientRepository.findAll();
	}

	// Venkat Ends
	/*
	 * 
	 * Ayush Gupta's code starts
	 */

	@Autowired
	private TestRepository testRepository;
	
	@Autowired
	private DiagnosticCenterRepository centerRepository;
	
	@Override
	public List<DiagnosticTest> getAllTest(){
		List<DiagnosticTest> tests=testRepository.findAll();
		return tests;
	}
	
	@Override
	public List<DiagnosticCenter> getAllDiagnosticCenters(){
		List<DiagnosticCenter> centers=centerRepository.findAll();
		return centers;
	}
	@Override
	public DiagnosticTest addNewTest(DiagnosticTest test) {
		DiagnosticTest addedTest=testRepository.save(test);
		LOGGER.info("test added successfully...");
		return addedTest;
	}
	@Override
	public DiagnosticTest updateTestDetail(DiagnosticTest test) {
		DiagnosticTest updatedTest=testRepository.save(test);
		LOGGER.info("test updated successfully...");
		return updatedTest;
	}
	
	@Override
	public DiagnosticCenter getDiagnosticCentersById(int diagnosticCenterId) {
		DiagnosticCenter center = diagnosticCenterRepository.getOne(diagnosticCenterId);
		return center;
	}
	
	@Override
	public List<DiagnosticTest> getTestsOfDiagnosticCenter(int centerId){
		DiagnosticCenter center=centerRepository.getOne(centerId);
		List<DiagnosticTest> testList=new LinkedList<>(center.getTests());
		if(testList.size()==0) {
			LOGGER.error("No Test Found At This Center");
			throw new NoTestFoundAtThisCenterException("No Test Found At This Center");
		}
		return testList;
	}
	@Override
	public List<DiagnosticTest> addTestToDiagnosticCenter(int centerId,List<DiagnosticTest> tests) throws Exception{
		DiagnosticCenter center=centerRepository.getOne(centerId);
		List<DiagnosticTest> centerTest=new LinkedList<DiagnosticTest>(center.getTests());
		for(int i=0;i<tests.size();i++) {
			if(centerTest.contains(tests.get(i))) {
				LOGGER.error("Some Tests are already Found in Diagnostic Center");
				throw new TestAlreadyFoundException("Some Tests are already Found in Diagnostic Center");
			}
		}
		center.getTests().addAll(tests);
		centerRepository.save(center);
		List<DiagnosticTest> updatedTestList=new LinkedList<DiagnosticTest>(center.getTests());
		return updatedTestList;
	}
	@Override
	public List<DiagnosticTest> removeTestFromDiagnosticCenter(int centerId,List<DiagnosticTest> tests) throws Exception{
		DiagnosticCenter center=centerRepository.getOne(centerId);
		List<DiagnosticTest> centerTest=new LinkedList<DiagnosticTest>(center.getTests());
		for(int i=0;i<tests.size();i++) {
			if(!centerTest.contains(tests.get(i))) {
				LOGGER.error("Some Tests are not present in Diagnostic Center");
				throw new TestNotPresentInCenter("Some Tests are not present in Diagnostic Center");
			}
		}
		center.getTests().removeAll(tests);
		centerRepository.save(center);
		LOGGER.info("Tests are removed successfully....");
		List<DiagnosticTest> updatedTestList=new LinkedList<DiagnosticTest>(center.getTests());
		return updatedTestList;
	}

	/*
	 * 
	 * Ayush Gupta's code ends
	 */

}
