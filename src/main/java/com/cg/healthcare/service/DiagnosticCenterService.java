package com.cg.healthcare.service;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import java.util.List;


import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.healthcare.dao.BedRepository;

import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.UserRepository;
import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.GeneralBed;
import com.cg.healthcare.entities.IntensiveCareBed;
import com.cg.healthcare.entities.IntensiveCriticalCareBed;
import com.cg.healthcare.entities.User;
import com.cg.healthcare.entities.VentilatorBed;
import com.cg.healthcare.exception.BedNotFoundException;
import com.cg.healthcare.exception.InvalidGeneralBedException;
import com.cg.healthcare.exception.InvalidICCUBedException;
import com.cg.healthcare.exception.InvalidICUBedException;
import com.cg.healthcare.exception.InvalidVentilatorBedException;
import com.cg.healthcare.exception.OccupiedBedException;

import com.cg.healthcare.entities.Bed;


@Service
@Transactional


public class DiagnosticCenterService {
	

	private static final Logger LOGGER  = LoggerFactory.getLogger(DiagnosticCenterService.class);

	@Autowired
	private DiagnosticCenterRepository diagnosticCenterRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BedRepository bedRepository;

	private static Validator validator;
	static {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	
	// Venkat Starts

	public DiagnosticCenter getDiagnosticCenterByUsername(String diagnosticCenterUsername) {
		User user = userRepository.findByUsername(diagnosticCenterUsername);
		DiagnosticCenter diagnosticCenter = diagnosticCenterRepo.getOne(user.getId());
		return diagnosticCenter;
	}

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

			newBeds.add(new IntensiveCareBed(bedPrice, isKneeTiltAvailable, isHeadTiltAvailable, isElectric,
					noOfFunctions));
		}
		diagnosticCenter.getBeds().addAll(newBeds);

		diagnosticCenterRepo.save(diagnosticCenter);
	}

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
			newBeds.add(new IntensiveCriticalCareBed(bedPrice, batteryBackUp, hasABS, remoteOperated,type));
		}
		diagnosticCenter.getBeds().addAll(newBeds);
	}

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
			newBeds.add(new GeneralBed(bedPrice, isMovable, frameMaterial));
		}
		diagnosticCenter.getBeds().addAll(newBeds);
	}

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
			newBeds.add(new VentilatorBed(bedPrice, respiratoryRate, type));
		}
		diagnosticCenter.getBeds().addAll(newBeds);
	}

	public Set<Bed> getBeds(String diagnosticCenterUsername) throws Exception {
		DiagnosticCenter diagnosticCenter = getDiagnosticCenterByUsername(diagnosticCenterUsername);
		return diagnosticCenter.getBeds();
	}

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
				beds.remove(toBeDeletedBed.get());
			}
		} else {
			LOGGER.error("Error while trying to delete the bed");
			throw new BedNotFoundException("Bed Exception",
					"The requested resource does not exist or unauthorized operation");
		}
	}
	
	// Venkat Ends


}
