package com.cg.healthcare.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.cg.healthcare.dao.AppointmentRepository;
import com.cg.healthcare.dao.BedRepository;
import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.TestRepository;
import com.cg.healthcare.dao.UserRepository;
import com.cg.healthcare.dao.WaitingPatientRepository;
import com.cg.healthcare.entities.Appointment;
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
import com.cg.healthcare.exception.DataBaseException;
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

	@Autowired
	private BedRepository bedRepository;

	@Autowired
	private AppointmentRepository appointmentRepo;

	@Autowired
	private TestRepository testRepository;
	
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
	
	public DiagnosticCenter getDiagnosticCenterById(int diagnosticCenterId) {
		DiagnosticCenter center = diagnosticCenterRepository.findById(diagnosticCenterId).get();
		LOGGER.info("Fetched diagnostic center successfully...");
		return center;
	}

	// Remove diagnostic center by Id
	@Override
	public List<DiagnosticCenter> removeDiagnosticCenter(int diagnosticCenterId) throws Exception {

		DiagnosticCenter center = getDiagnosticCenterById(diagnosticCenterId);

		if (center == null) {
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
	public List<DiagnosticCenter> getAllDiagnosticCenter() {
		List<DiagnosticCenter> centers = diagnosticCenterRepository.findAll();
		LOGGER.info("Fetched all diagnostic centers successfully...");
		return centers;
	}

	/*
	 * Sachin Kumar (Ends)
	 */

	// Venkat Starts
	
	/**
	 * @author Venkat
	 * Function to Allocated Beds to the Waiting Patient who have taken appointment
	 * returns void
	 * @throws DiagnosticCenterNotFoundException, InvalidBedAllocationException, 
	 * AppointmentNotApprovedException, PartialBedAllocationException, NoBedAvailableException
	 */

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

				if (!bed.isOccupied() && bedType.isInstance(bed)
						&& type.equals(waitingPatients.get(waitingIndex).getType())) {

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

	/**
	 * @author Venkat
	 * @param bedType - String Type of Bed Category - Ventilator, ICU, ICCU, General
	 * @return Class Type
	 * @throws Exception - InvalidBedTypeException
	 */

	public Class<?> getBedType(String bedType) throws Exception {

		if (bedType.equals("General")) {
			return new GeneralBed().getClass();
		} else if (bedType.equals("Ventilator")) {
			return new VentilatorBed().getClass();
		} else if (bedType.equals("ICU")) {
			return new IntensiveCareBed().getClass();
		} else if (bedType.equals("ICCU")) {
			return new IntensiveCriticalCareBed().getClass();
		} else {
			LOGGER.error(bedType + " is not a valid kind of Bed");
			throw new InvalidBedTypeException("Bed Type Exception", bedType + " is not a valid kind of Bed");
		}
	}

	/**
	 * @author Venkat
	 * @param diagnosticCenterId - Integer
	 * @return - Set<Bed>
	 * @throws DiagnosticCenterNotFoundException
	 */
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

	/**
	 * Returns the list Of Waiting Patients in the HealthCare system
	 * @return - List<WaitingPatient>
	 *
	 */
	@Override
	public List<WaitingPatient> getWaitingPatients() throws Exception {
		return this.waitingPatientRepository.findAll();
	}

	// Venkat Ends
	/*
	 * 
	 * Ayush Gupta's code starts
	 */
	
	/*
	 * this function will return all the testcases
	 */
	@Override
	public List<DiagnosticTest> getAllTest(){
		List<DiagnosticTest> tests=testRepository.findAll();
		LOGGER.info("getting all tests");
		return tests;
	}
	// this methos will return all the diagnostic center available
	@Override
	public List<DiagnosticCenter> getAllDiagnosticCenters(){
		List<DiagnosticCenter> centers=diagnosticCenterRepository.findAll();
		LOGGER.info("getting all diagnostic center");
		return centers;
	}
	
	//this method will add a new test in the database...
	@Override
	public DiagnosticTest addNewTest(DiagnosticTest test) {
		DiagnosticTest addedTest=testRepository.save(test);
		LOGGER.info("test added successfully...");
		return addedTest;
	}
	// the method is updating the test details..
	@Override
	public DiagnosticTest updateTestDetail(DiagnosticTest test) {
		DiagnosticTest updatedTest=testRepository.save(test);
		LOGGER.info("test updated successfully...");
		return updatedTest;
	}
	
	// this function will return a diagnostic center which the function will find it by id...
	@Override
	public DiagnosticCenter getDiagnosticCentersById(int diagnosticCenterId) {
		DiagnosticCenter center = diagnosticCenterRepository.getOne(diagnosticCenterId);
		return center;
	}
	
	/* this function will return a list of all tests available at the diagnostic center 
	 * and in case if no testcase available at the center then it will throw 
	 * no test found at this center exception
	 */
	@Override
	public List<DiagnosticTest> getTestsOfDiagnosticCenter(int centerId){
		DiagnosticCenter center=diagnosticCenterRepository.getOne(centerId);
		List<DiagnosticTest> testList=new LinkedList<>(center.getTests());
		if(testList.size()==0) {
			LOGGER.error("No Test Found At This Center");
			throw new NoTestFoundAtThisCenterException("No Test Found At This Center");
		}
		LOGGER.info("Getting all test available at the center");
		return testList;
	}
	/*this function will add test to a diagnostic center firstly it will check may be 
	 * test will already present in the center then it will throw an exception
	 * test is already found in diagnostic center other wise it will return updated 
	 * list of test available at the diagnostic center....
	 */
	@Override
	public List<DiagnosticTest> addTestToDiagnosticCenter(int centerId,DiagnosticTest test) throws Exception{
		DiagnosticCenter center=diagnosticCenterRepository.getOne(centerId);
		List<DiagnosticTest> centerTests=new LinkedList<>(center.getTests());
		for(int i=0;i<centerTests.size();i++) {
			if(centerTests.get(i).getId()==test.getId()) {
				LOGGER.error("Test is already Found in Diagnostic Center");
				throw new TestAlreadyFoundException("Test is already Found in Diagnostic Center");
			}
		}
		center.getTests().add(test);
		diagnosticCenterRepository.save(center);
		LOGGER.info("test is added to a diagnostic center successfully");
		List<DiagnosticTest> updatedTestList=new LinkedList<DiagnosticTest>(center.getTests());
		return updatedTestList;
	}
	
	/*this function will remove a test from the diagnostic center firstly it will
	 * check that those test is available at the center or not if not then it will throw
	 * exception otherwise it will remove  test from center and returns the
	 * updated list of available test at the center... 
	 */
	@Override
	public List<DiagnosticTest> removeTestFromDiagnosticCenter(int centerId,DiagnosticTest test) throws Exception{
		DiagnosticCenter center=diagnosticCenterRepository.getOne(centerId);
		boolean flag=false;
		List<DiagnosticTest> centerTests=new LinkedList<>(center.getTests());
		for(int i=0;i<centerTests.size();i++) {
			if(centerTests.get(i).getId()==test.getId()) {
				flag=true;
			}
		}
		if(flag==false) {
		LOGGER.error("Test is not present in Diagnostic Center");
		throw new TestNotPresentInCenter("Test is not present in Diagnostic Center");
		}
		for(int i=0;i<centerTests.size();i++) {
			if(centerTests.get(i).getId()==test.getId()) {
				center.getTests().remove(centerTests.get(i));
			}
		}
		diagnosticCenterRepository.save(center);
		LOGGER.info("Test is removed successfully....");
		List<DiagnosticTest> updatedTestList=new LinkedList<DiagnosticTest>(center.getTests());
		return updatedTestList;
	}

	/*
	 * 
	 * Ayush Gupta's code ends
	 */


	/*
	 * Madhu Starts
	 * 
	 */
	@Override
	public List<Bed> listOfVacantBeds() {
		List<Bed> allBeds = bedRepository.findAll();
		List<Bed> vacantBedsList = new ArrayList<Bed>();
		for (Bed bed : allBeds) {
			if (!(bed.isOccupied()))
				vacantBedsList.add(bed);
		}
		return vacantBedsList;

	}
	/*
	 * 
	 * Madhu Ends
	 */

	/**
	 * Sachin Pant Starts
	 * 
	 */
public List<Appointment> getApppointmentList(int centreId,String test,int status){
		
		List<Appointment> allappoints;
		try {
			allappoints=appointmentRepo.findAll();
		}
		catch(Exception e) {
			throw new DataBaseException();
		}
		
		List<Appointment> appointments=new ArrayList<>();
		for(Appointment a:allappoints) {
			int tomorrow =LocalDateTime.now().getDayOfYear()+1;					// admin will see next day's appointment
			int date=a.getAppointmentDate().toLocalDateTime().getDayOfYear();
			
			if(a.getApprovalStatus()==status && test.equals(a.getDiagnosticTest().getTestName()) &&
					centreId==a.getDiagnosticCenter().getId() && tomorrow==date)
				appointments.add(a);
		}
		LOGGER.info(appointments.size() + " appointments has been accessed.");
		return appointments;
	}
	
	
	// bursttime is the time taken to handle one patient
	// seats is the no of test can be done at a time
	public String processAppointment(int centreId,String test,int bursttime,int seats) {
		
		List<Appointment> appointment=this.getApppointmentList(centreId, test, 0);		// 0 means neutral
		for(Appointment a:appointment) {
			int already=checkSeatLeft(bursttime,seats,a,appointment);
			if(already<seats)
			{
				a.setApprovalStatus(1);													// confirm
				LOGGER.info("appointment with id : "+a.getId()+" is approved");
			}
			else 
			{
				a.setApprovalStatus(2);													// rejected
				LOGGER.info("appointment with id : "+a.getId()+" is rejected");
			}
			
			try {
				appointmentRepo.save(a);		//	status is updated
			}
			catch(Exception e) {
				LOGGER.info("appointment not updated for some issue in database for appointment Id: "+a.getId());
				throw new DataBaseException();
			}
		}
		return "done";
	}
	
	public static int checkSeatLeft(int bursttime,int seats,Appointment currApp,List<Appointment> appointments) {
		int already=0;
		for(Appointment a:appointments) {
			
			if(a.getApprovalStatus()!=1 || a.getId()==currApp.getId())		// if not approved or same appointment
			{
				//System.out.println("Same one");
				continue;
			}
			
			int h1=currApp.getAppointmentDate().toLocalDateTime().getHour();
			int m1=currApp.getAppointmentDate().toLocalDateTime().getMinute();
			
			int h2=a.getAppointmentDate().toLocalDateTime().getHour();
			int m2=a.getAppointmentDate().toLocalDateTime().getMinute();
			
			if(coincide(h1,m1,h2,m2,bursttime))						// if time collide with approved request
				already++;
		}
		
		return already;
	}
	
	public static boolean coincide(int hour1,int min1,int hour2,int min2,int bursttime) {
		
		int time1=60*hour1+min1;
		int time2=60*hour2+min2;
		if(Math.min(time2, time1)+bursttime>=Math.max(time2, time1))
			return true;
		else
			return false;
	}

	/**
	 * 
	 * Sachin Pant Ends
	 * 
	 */
}
