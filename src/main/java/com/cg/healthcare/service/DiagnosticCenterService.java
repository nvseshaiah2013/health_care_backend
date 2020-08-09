package com.cg.healthcare.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.healthcare.dao.BedRepository;
import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.TestRepository;
import com.cg.healthcare.dao.UserRepository;
import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.GeneralBed;
import com.cg.healthcare.entities.IntensiveCareBed;
import com.cg.healthcare.entities.IntensiveCriticalCareBed;
import com.cg.healthcare.entities.DiagnosticTest;
import com.cg.healthcare.entities.User;
import com.cg.healthcare.entities.VentilatorBed;
import com.cg.healthcare.exception.BedNotFoundException;
import com.cg.healthcare.exception.OccupiedBedException;

@Service
@Transactional
public class DiagnosticCenterService { 

	@Autowired
	private DiagnosticCenterRepository diagnosticCenterRepo;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestRepository testRepository;
	
	@Autowired
	private BedRepository bedRepository;
	
	
	
	public DiagnosticTest getTestInfo(String testName)
	{
		DiagnosticTest test = testRepository.findBytestName(testName);
		return test;
	}
	

	public List<Bed> listOfVacantBeds()
	{
		List<Bed> allBeds = bedRepository.findAll();
		List<Bed> vacantBedsList =new ArrayList<Bed>(); 
		for(Bed bed : allBeds) {
			if( !(bed.isOccupied())) vacantBedsList.add(bed) ;
		}
		return vacantBedsList;
		
	}

	public DiagnosticCenter getDiagnosticCenterByUsername(String diagnosticCenterUsername) {
		User user = userRepository.findByUsername(diagnosticCenterUsername);
		DiagnosticCenter diagnosticCenter = diagnosticCenterRepo.getOne(user.getId());
		return diagnosticCenter;
	}

	public void addICUBeds(String diagnosticCenterUsername, int noOfBeds, double bedPrice) throws Exception {
		DiagnosticCenter diagnosticCenter = getDiagnosticCenterByUsername(diagnosticCenterUsername);
		Set<Bed> newBeds = new HashSet<>();
		for (int bedIndex = 0; bedIndex < noOfBeds; ++bedIndex) {
			newBeds.add(new IntensiveCareBed(bedPrice));
		}
		diagnosticCenter.getBeds().addAll(newBeds);
		diagnosticCenterRepo.save(diagnosticCenter);
	}

	public void addICCUBeds(String diagnosticCenterUsername, int noOfBeds, double bedPrice) throws Exception {
		DiagnosticCenter diagnosticCenter = getDiagnosticCenterByUsername(diagnosticCenterUsername);
		Set<Bed> newBeds = new HashSet<>();
		for (int bedIndex = 0; bedIndex < noOfBeds; ++bedIndex) {
			newBeds.add(new IntensiveCriticalCareBed(bedPrice));
		}
		diagnosticCenter.getBeds().addAll(newBeds);
	}

	public void addGeneralBeds(String diagnosticCenterUsername, int noOfBeds, double bedPrice) throws Exception {
		DiagnosticCenter diagnosticCenter = getDiagnosticCenterByUsername(diagnosticCenterUsername);
		Set<Bed> newBeds = new HashSet<>();
		for (int bedIndex = 0; bedIndex < noOfBeds; ++bedIndex) {
			newBeds.add(new GeneralBed(bedPrice));
		}
		diagnosticCenter.getBeds().addAll(newBeds);
	}

	public void addVentilatorBeds(String diagnosticCenterUsername, int noOfBeds, double bedPrice) throws Exception {
		DiagnosticCenter diagnosticCenter = getDiagnosticCenterByUsername(diagnosticCenterUsername);
		Set<Bed> newBeds = new HashSet<>();
		for (int bedIndex = 0; bedIndex < noOfBeds; ++bedIndex) {
			newBeds.add(new VentilatorBed(bedPrice));
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
		if(toBeDeletedBed.isPresent()) {
			if(toBeDeletedBed.get().isOccupied()) {
				throw new OccupiedBedException("Bed Exception", "The bed cannot be deleted as the bed is already occupied");
			} else {
				beds.remove(toBeDeletedBed.get());			}
		}
		else {
			throw new BedNotFoundException("Bed Exception","The requested resource does not exist or unauthorized operation");
		}
	}

}
