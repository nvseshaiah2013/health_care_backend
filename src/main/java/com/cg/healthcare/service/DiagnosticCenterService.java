package com.cg.healthcare.service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.healthcare.dao.AppointmentRepository;
import com.cg.healthcare.dao.BedRepository;
import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.TestRepository;
import com.cg.healthcare.dao.TestResultRepository;
import com.cg.healthcare.dao.UserRepository;
import com.cg.healthcare.entities.Appointment;
import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.DiagnosticTest;
import com.cg.healthcare.entities.GeneralBed;
import com.cg.healthcare.entities.IntensiveCareBed;
import com.cg.healthcare.entities.IntensiveCriticalCareBed;
import com.cg.healthcare.entities.TestResult;
import com.cg.healthcare.entities.User;
import com.cg.healthcare.entities.VentilatorBed;
import com.cg.healthcare.exception.BedNotFoundException;
import com.cg.healthcare.exception.InvalidGeneralBedException;
import com.cg.healthcare.exception.InvalidICCUBedException;
import com.cg.healthcare.exception.InvalidICUBedException;
import com.cg.healthcare.exception.InvalidVentilatorBedException;
import com.cg.healthcare.exception.NoAppointmentException;
import com.cg.healthcare.exception.OccupiedBedException;
import com.cg.healthcare.requests.TestResultForm;


@Service
@Transactional
public class DiagnosticCenterService implements IDiagnosticCenterService {
	

	private static final Logger LOGGER  = LoggerFactory.getLogger(DiagnosticCenterService.class);

	@Autowired
	private DiagnosticCenterRepository diagnosticCenterRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestRepository testRepository;
	
	@Autowired
	private BedRepository bedRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	
	@Autowired
	private TestResultRepository testResultRepository;
	
	
	@Override
	public DiagnosticTest getTestInfo(String testName)
	{
		DiagnosticTest test = testRepository.findBytestName(testName);
		return test;
	}
	

	@Override
	public List<Bed> listOfVacantBeds()
	{
		List<Bed> allBeds = bedRepository.findAll();
		List<Bed> vacantBedsList =new ArrayList<Bed>(); 
		for(Bed bed : allBeds) {
			if( !(bed.isOccupied())) vacantBedsList.add(bed) ;
		}
		return vacantBedsList;
		
	}

	private static Validator validator;
	static {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	
	// Venkat Starts

	@Override
	public DiagnosticCenter getDiagnosticCenterByUsername(String diagnosticCenterUsername) {
		User user = userRepository.findByUsername(diagnosticCenterUsername);
		DiagnosticCenter diagnosticCenter = diagnosticCenterRepo.getOne(user.getId());
		return diagnosticCenter;
	}

	/**
	 * Method to Add ICU Beds
	 * @author Venkat
	 * @return void
	 * @exception InvalidICUBedException
	 */
	@Override
	public void addICUBeds(String diagnosticCenterUsername, int noOfBeds, double bedPrice, boolean isKneeTiltAvailable,
			boolean isHeadTiltAvailable, boolean isElectric, int noOfFunctions) throws Exception {

		DiagnosticCenter diagnosticCenter = getDiagnosticCenterByUsername(diagnosticCenterUsername);

		Set<ConstraintViolation<IntensiveCareBed>> violations = validator.validate(
				new IntensiveCareBed(bedPrice, isKneeTiltAvailable, isHeadTiltAvailable, isElectric, noOfFunctions));

		if (violations.size() != 0) {

			String errorMessage = violations.stream().map((violation) -> violation.getMessage()).reduce("",
					String::concat);
			LOGGER.error("Error While Adding a New Intensive Care Bed");
			throw new InvalidICUBedException("Intensive Care Bed Exception", errorMessage);
		}

		Set<Bed> newBeds = new HashSet<>();

		for (int bedIndex = 0; bedIndex < noOfBeds; ++bedIndex) {
			Bed bed = new IntensiveCareBed(bedPrice, isKneeTiltAvailable, isHeadTiltAvailable, isElectric,
					noOfFunctions);
			bed.setAppointment(null);
			bed.setDiagnosticCenter(diagnosticCenter);			
			Bed newBed = bedRepository.save(bed);
			
			newBeds.add(newBed);
		}
		diagnosticCenter.getBeds().addAll(newBeds);

		diagnosticCenterRepo.save(diagnosticCenter);
	}

	/**
	 * Method To add ICCU Beds
	 * @author Venkat
	 * @return void
	 * @throws InvalidICCUBeds Exception
	 */
	@Override
	public void addICCUBeds(String diagnosticCenterUsername, int noOfBeds, double bedPrice, boolean batteryBackUp,
			boolean hasABS, boolean remoteOperated, String type) throws Exception {

		DiagnosticCenter diagnosticCenter = getDiagnosticCenterByUsername(diagnosticCenterUsername);

		Set<ConstraintViolation<IntensiveCriticalCareBed>> violations = validator
				.validate(new IntensiveCriticalCareBed(bedPrice, batteryBackUp, hasABS, remoteOperated,type));

		if (violations.size() != 0) {

			String errorMessage = violations.stream().map((violation) -> violation.getMessage()).reduce("",
					String::concat);
			LOGGER.error("Error While Adding a New Intensive Critical Care Bed");
			throw new InvalidICCUBedException("Intensive Critical Care Bed Exception", errorMessage);
		}

		Set<Bed> newBeds = new HashSet<>();

		for (int bedIndex = 0; bedIndex < noOfBeds; ++bedIndex) {
			Bed bed = new IntensiveCriticalCareBed(bedPrice, batteryBackUp, hasABS, remoteOperated,type);
			bed.setAppointment(null);
			bed.setDiagnosticCenter(diagnosticCenter);
			Bed newBed = bedRepository.save(bed); 
			newBeds.add(newBed);
		}
		diagnosticCenter.getBeds().addAll(newBeds);
		
		diagnosticCenterRepo.save(diagnosticCenter);
	}

	/**
	 * Method to Add General Beds
	 * @author Venkat
	 * @return void
	 * @throws Invalid GeneralBedException
	 */
	@Override
	public void addGeneralBeds(String diagnosticCenterUsername, int noOfBeds, double bedPrice, boolean isMovable,
			String frameMaterial) throws Exception {

		DiagnosticCenter diagnosticCenter = getDiagnosticCenterByUsername(diagnosticCenterUsername);

		Set<ConstraintViolation<GeneralBed>> violations = validator
				.validate(new GeneralBed(bedPrice, isMovable, frameMaterial));

		if (violations.size() != 0) {

			String errorMessage = violations.stream().map((violation) -> violation.getMessage()).reduce("",
					String::concat);
			LOGGER.error("Error While Adding a General Bed");
			throw new InvalidGeneralBedException("General Bed Exception", errorMessage);
		}
		Set<Bed> newBeds = new HashSet<>();
		for (int bedIndex = 0; bedIndex < noOfBeds; ++bedIndex) {
			Bed bed = new GeneralBed(bedPrice, isMovable, frameMaterial);
			bed.setAppointment(null);
			bed.setDiagnosticCenter(diagnosticCenter);
			Bed newBed = bedRepository.save(bed);
			newBeds.add(newBed);
		}
		diagnosticCenter.getBeds().addAll(newBeds);
		
		diagnosticCenterRepo.save(diagnosticCenter);
	}

	/**
	 * Method to Add Ventilator Beds
	 * @author Venkat
	 * @return void
	 * @throws InvalidVentilatorBedException
	 */
	@Override
	public void addVentilatorBeds(String diagnosticCenterUsername, int noOfBeds, double bedPrice, int respiratoryRate,
			String type) throws Exception {

		DiagnosticCenter diagnosticCenter = getDiagnosticCenterByUsername(diagnosticCenterUsername);

		Set<ConstraintViolation<VentilatorBed>> violations = validator
				.validate(new VentilatorBed(bedPrice, respiratoryRate, type));

		if (violations.size() != 0) {

			String errorMessage = violations.stream().map((violation) -> violation.getMessage()).reduce("",
					String::concat);
			LOGGER.error("Error While Adding a Ventilator Bed");
			throw new InvalidVentilatorBedException("Ventilator Bed Exception", errorMessage);

		}
		Set<Bed> newBeds = new HashSet<>();

		for (int bedIndex = 0; bedIndex < noOfBeds; ++bedIndex) {
			Bed bed = new VentilatorBed(bedPrice, respiratoryRate, type);
			bed.setAppointment(null);
			bed.setDiagnosticCenter(diagnosticCenter);
			Bed newBed = bedRepository.save(bed);
			newBeds.add(newBed);
		}
		diagnosticCenter.getBeds().addAll(newBeds);
		
		diagnosticCenterRepo.save(diagnosticCenter);
	}

	/**
	 * Method to Get the List Of Beds
	 * @author Venkat
	 * @return Set<Bed>
	 * @exception Exception
	 */
	@Override
	public Set<Bed> getBeds(String diagnosticCenterUsername) throws Exception {
		DiagnosticCenter diagnosticCenter = getDiagnosticCenterByUsername(diagnosticCenterUsername);
		return diagnosticCenter.getBeds();
	}

	/**
	 * Method To Remove the Bed From Diagnostic Center
	 * @author Venkat
	 * @return void
	 * @exception OccupiedBedException
	 */
	@Override
	public void removeBed(String diagnosticCenterUsername, Integer bedId) throws Exception {

		DiagnosticCenter diagnosticCenter = getDiagnosticCenterByUsername(diagnosticCenterUsername);

		Set<Bed> beds = diagnosticCenter.getBeds();

		Optional<Bed> toBeDeletedBed = beds.stream().filter((bed) -> bed.getId() == bedId).findFirst();

		if (toBeDeletedBed.isPresent()) {
			
			if (toBeDeletedBed.get().isOccupied()) {
				LOGGER.error("Error While Deleting the bed as bed is occupied");
				throw new OccupiedBedException("Bed Exception",
						"The bed cannot be deleted as the bed is already occupied");
			} else {
				diagnosticCenter.getBeds().remove(toBeDeletedBed.get());
				bedRepository.delete(toBeDeletedBed.get());
				diagnosticCenterRepo.save(diagnosticCenter);
				LOGGER.info("Bed Deleted");
			}
		} else {
			LOGGER.error("Error while trying to delete the bed");
			throw new BedNotFoundException("Bed Exception",
					"The requested resource does not exist or unauthorized operation");
		}
	}
	
	// Venkat Ends

	// Madhu Starts
	
	@Override
	public String updateTestResult(TestResultForm testResult) throws Exception {
		TestResult result =new TestResult();
		if (appointmentRepository.existsById(testResult.getAppointmentId()))
		{
			Appointment appointment = appointmentRepository.getOne(testResult.getAppointmentId());
			result.setCondition(testResult.getCondition());
			result.setTestReading(testResult.getTestReading());
			result.setAppointment(appointment);
			testResultRepository.save(result);
			return "Test results of "+testResult.getAppointmentId()+"Updated";
		} 
		else
		{
			LOGGER.error("Error while Updating the Test Result");
			throw new NoAppointmentException("Appointment Exception",
					"Entered appointment id does not xists");
		}
	}
	
	// Madhu Ends
	
}
