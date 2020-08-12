package com.cg.healthcare.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.healthcare.dao.DiagnosticCenterRepository;
import com.cg.healthcare.dao.UserRepository;
import com.cg.healthcare.dao.WaitingPatientRepository;
import com.cg.healthcare.entities.Appointment;
import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.GeneralBed;
import com.cg.healthcare.entities.IntensiveCareBed;
import com.cg.healthcare.entities.Patient;
import com.cg.healthcare.entities.User;
import com.cg.healthcare.entities.WaitingPatient;
import com.cg.healthcare.exception.AppointmentNotApprovedException;
import com.cg.healthcare.exception.DiagnosticCenterNotFoundException;
import com.cg.healthcare.exception.InvalidBedAllocationException;
import com.cg.healthcare.exception.InvalidBedTypeException;
import com.cg.healthcare.exception.NoBedAvailableException;
import com.cg.healthcare.exception.PartialBedsAllocationException;
import com.cg.healthcare.service.AdminService;

@ExtendWith(MockitoExtension.class)
public class WaitingPatientTest {

	@InjectMocks
	private AdminService adminService;
	
	@Mock
	private UserRepository UserRepository;
		
	@Mock
	private DiagnosticCenterRepository diagnosticCenterRepository;
	
	@Mock
	private WaitingPatientRepository waitingPatientRepository;
	
	private static User mockDiagnosticUser1;
	private static User mockDiagnosticUser2;
	private static DiagnosticCenter mockDiagnosticCenter1;
	private static DiagnosticCenter mockDiagnosticCenter2;
	private static User mockPatientUser1;
	private static Patient mockPatient1;
	private static User mockPatientUser2;
	private static Patient mockPatient2;
	private static User mockPatientUser3;
	private static Patient mockPatient3;
	private static User mockPatientUser4;
	private static Patient mockPatient4;
	private static User mockPatientUser5;
	private static Patient mockPatient5;
	private static User mockPatientUser6;
	private static Patient mockPatient6;
		
	@BeforeEach
	public void init() {
		mockDiagnosticUser1 = new User(10, "center1@gmail.com", "Password@123", "ROLE_CENTER");
		
		mockDiagnosticCenter1 = new DiagnosticCenter(mockDiagnosticUser1.getId(), "Center 1", "9876543210", "Address",
				"email1@gmail.com", "Services 1");
		
		mockPatientUser1 = new User(11, "patient1@gmail.com", "Password@123", "ROLE_PATIENT");
		
		mockPatient1 = new Patient(mockPatientUser1.getId(),"Patient 1",21,"Male","9988776655");
		
		mockDiagnosticUser2 = new User(12, "center2@gmail.com", "Password@123", "ROLE_CENTER");
		
		mockDiagnosticCenter2 = new DiagnosticCenter(mockDiagnosticUser2.getId(), "Center2", "9898989899", "Address", "email2@gmail.com", "Services 2");
	
		mockPatientUser2 = new User(13, "patient2@gmail.com", "Password@123", "ROLE_PATIENT");
		
		mockPatient2 = new Patient(mockPatientUser2.getId(), "Patient 2", 21, "Female", "9797979797");
		
		mockPatientUser3 = new User(14, "patient3@gmail.com", "Password@123", "ROLE_PATIENT");
		
		mockPatient3 = new Patient(mockPatientUser3.getId(), "Patient 3", 21, "Female", "9697979797");
		
		mockPatientUser4 = new User(15, "patient4@gmail.com", "Password@123", "ROLE_PATIENT");
		
		mockPatient4 = new Patient(mockPatientUser4.getId(), "Patient 4", 21, "Female", "9497979797");
		
		mockPatientUser5 = new User(16, "patient5@gmail.com", "Password@123", "ROLE_PATIENT");
		
		mockPatient5 = new Patient(mockPatientUser5.getId(), "Patient 5", 21, "Female", "9696979797");
		
		mockPatientUser6 = new User(17, "patient6@gmail.com", "Password@123", "ROLE_PATIENT");
		
		mockPatient6 = new Patient(mockPatientUser6.getId(), "Patient 6", 21, "Female", "9796979797");
		
		}
	/**
	 * Method to Test the Allocation Of Bed To All waiting Patients
	 * @throws Exception
	 */
	
	@Test
	@DisplayName("Allocation Of Bed To All Waiting Patients")
	public void allocateBedToAll()  throws Exception {
		Appointment appointment1 = new Appointment(101, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient1, mockDiagnosticCenter1);
		Appointment appointment2 = new Appointment(102, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient2, mockDiagnosticCenter1);
		Appointment appointment3 = new Appointment(103, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient3, mockDiagnosticCenter1);
		WaitingPatient waitingPatient1 = new WaitingPatient();
		waitingPatient1.setAppointment(appointment1);
		waitingPatient1.setId(1);
		waitingPatient1.setType("General");
		WaitingPatient waitingPatient2 = new WaitingPatient();
		waitingPatient2.setAppointment(appointment2);
		waitingPatient2.setId(2);
		waitingPatient2.setType("General");
		WaitingPatient waitingPatient3 = new WaitingPatient();
		waitingPatient3.setAppointment(appointment3);
		waitingPatient3.setId(3);
		waitingPatient3.setType("General");
		
		Mockito.when(waitingPatientRepository.getOne(1)).thenReturn(waitingPatient1);
		
		Mockito.when(waitingPatientRepository.getOne(2)).thenReturn(waitingPatient2);
		
		Mockito.when(waitingPatientRepository.getOne(3)).thenReturn(waitingPatient3);
		
		Mockito.when(diagnosticCenterRepository.findById(10)).thenReturn(Optional.of(mockDiagnosticCenter1));
		
		Set<Bed> beds = new HashSet<>();
		for(int bedIndex = 0; bedIndex < 5; ++bedIndex) {
			Bed bed = new GeneralBed(1000.1,true, "Steel");
			bed.setAppointment(null);
			bed.setOccupied(false);
			beds.add(bed);
		}
		mockDiagnosticCenter1.getBeds().addAll(beds);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter1);
		List<Integer> waitingIds = new ArrayList<>();
		waitingIds.add(1);
		waitingIds.add(2);
		waitingIds.add(3);
		adminService.allocateBeds(mockDiagnosticCenter1.getId(), waitingIds, "General");
		Long count = adminService.getBeds(10).stream().filter(bed -> bed.isOccupied() == true ).count();
		assertEquals(3, count);
	}
	
	/**
	 * Method to Test the Allocation of Beds To Waiting Patient
	 * @throws Exception
	 */
	
	@Test
	@DisplayName("Allocation Of Beds to Some Patients")
	public void allocateBedsPartially() throws Exception {
		Appointment appointment1 = new Appointment(101, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient1, mockDiagnosticCenter1);
		Appointment appointment2 = new Appointment(102, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient2, mockDiagnosticCenter1);
		Appointment appointment3 = new Appointment(103, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient3, mockDiagnosticCenter1);
		Appointment appointment4 = new Appointment(104, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient4, mockDiagnosticCenter1);
		Appointment appointment5 = new Appointment(105, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient5, mockDiagnosticCenter1);
		Appointment appointment6 = new Appointment(106, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient6, mockDiagnosticCenter1);
				
		WaitingPatient waitingPatient1 = new WaitingPatient();
		waitingPatient1.setAppointment(appointment1);
		waitingPatient1.setId(1);
		waitingPatient1.setType("General");
		WaitingPatient waitingPatient2 = new WaitingPatient();
		waitingPatient2.setAppointment(appointment2);
		waitingPatient2.setId(2);
		waitingPatient2.setType("General");
		WaitingPatient waitingPatient3 = new WaitingPatient();
		waitingPatient3.setAppointment(appointment3);
		waitingPatient3.setId(3);
		waitingPatient3.setType("General");
		
		Mockito.when(waitingPatientRepository.getOne(1)).thenReturn(waitingPatient1);
		
		Mockito.when(waitingPatientRepository.getOne(2)).thenReturn(waitingPatient2);
		
		Mockito.when(waitingPatientRepository.getOne(3)).thenReturn(waitingPatient3);
		
		Set<Bed> beds = new HashSet<>();
		
		for(int bedIndex = 0; bedIndex < 2; ++bedIndex) {
			Bed bed = new GeneralBed(1000.1,true, "Steel");
			bed.setAppointment(null);
			bed.setOccupied(false);
			beds.add(bed);
		}
		Bed bed1 = new GeneralBed(1000.1,true, "Steel");
			bed1.setAppointment(appointment4);
			bed1.setOccupied(true);
		Bed bed2 = new GeneralBed(1000.1,true, "Steel");
			bed2.setAppointment(appointment5);
			bed2.setOccupied(true);
		Bed bed3 =  new GeneralBed(1000.0,true, "Steel");
			bed3.setAppointment(appointment6);
			bed3.setOccupied(true);
		beds.add(bed1);
		beds.add(bed2);
		beds.add(bed3);
		mockDiagnosticCenter1.getBeds().addAll(beds);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter1);
		List<Integer> waitingIds = new ArrayList<>();
		waitingIds.add(1);
		waitingIds.add(2);
		waitingIds.add(3);
		assertThrows(PartialBedsAllocationException.class, () -> {
			adminService.allocateBeds(mockDiagnosticCenter1.getId(), waitingIds, "General");			
		});		
	}
	
	/**
	 * Method to Test Bed Allocation Failure on non availability of Beds
	 */
	@Test
	@DisplayName("Bed Allocation Failure")
	public void failIfNoBedAvailable() {
		Appointment appointment1 = new Appointment(101, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient1, mockDiagnosticCenter1);
		Appointment appointment2 = new Appointment(102, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient2, mockDiagnosticCenter1);
		Appointment appointment3 = new Appointment(103, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient3, mockDiagnosticCenter1);
		Appointment appointment4 = new Appointment(104, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient4, mockDiagnosticCenter1);
		Appointment appointment5 = new Appointment(105, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient5, mockDiagnosticCenter1);
		Appointment appointment6 = new Appointment(106, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient6, mockDiagnosticCenter1);
				
		WaitingPatient waitingPatient1 = new WaitingPatient();
		waitingPatient1.setAppointment(appointment1);
		waitingPatient1.setId(1);
		waitingPatient1.setType("General");
		WaitingPatient waitingPatient2 = new WaitingPatient();
		waitingPatient2.setAppointment(appointment2);
		waitingPatient2.setId(2);
		waitingPatient2.setType("General");
		WaitingPatient waitingPatient3 = new WaitingPatient();
		waitingPatient3.setAppointment(appointment3);
		waitingPatient3.setId(3);
		waitingPatient3.setType("General");
		Mockito.when(waitingPatientRepository.getOne(1)).thenReturn(waitingPatient1);
		
		Mockito.when(waitingPatientRepository.getOne(2)).thenReturn(waitingPatient2);
		
		Mockito.when(waitingPatientRepository.getOne(3)).thenReturn(waitingPatient3);
		
		Set<Bed> beds = new HashSet<>();
		Bed bed1 = new GeneralBed(1000.1,true, "Steel");
			bed1.setAppointment(appointment4);
			bed1.setOccupied(true);
		Bed bed2 = new GeneralBed(1000.1,true, "Steel");
			bed2.setAppointment(appointment5);
			bed2.setOccupied(true);
		Bed bed3 =  new GeneralBed(1000.0,true, "Steel");
			bed3.setAppointment(appointment6);
			bed3.setOccupied(true);
		beds.add(bed3);
		beds.add(bed2);
		beds.add(bed1);
		mockDiagnosticCenter1.getBeds().addAll(beds);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter1);
		List<Integer> waitingIds = new ArrayList<>();
		waitingIds.add(1);
		waitingIds.add(2);
		waitingIds.add(3);
		assertThrows(NoBedAvailableException.class, () -> {
			adminService.allocateBeds(mockDiagnosticCenter1.getId(), waitingIds, "General");			
		});	
	}
	
	/**
	 * Method to Test the Exception thrown when bed is being tried to 
	 * allocate to patient outside the Diagnostic Center from where he applied the appointment
	 */
	@Test
	@DisplayName("Cannot Allocate a Bed To a Person outside the Appointed Center")
	public void failIfAllocatedBedInDifferentCenter() {
		Appointment appointment1 = new Appointment(101, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient1, mockDiagnosticCenter1);
		Appointment appointment2 = new Appointment(102, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient2, mockDiagnosticCenter2);
		Appointment appointment3 = new Appointment(103, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient3, mockDiagnosticCenter1);
		Appointment appointment4 = new Appointment(104, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient4, mockDiagnosticCenter1);
		Appointment appointment5 = new Appointment(105, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient5, mockDiagnosticCenter1);
		Appointment appointment6 = new Appointment(106, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient6, mockDiagnosticCenter1);
				
		WaitingPatient waitingPatient1 = new WaitingPatient();
		waitingPatient1.setAppointment(appointment1);
		waitingPatient1.setId(1);
		waitingPatient1.setType("General");
		WaitingPatient waitingPatient2 = new WaitingPatient();
		waitingPatient2.setAppointment(appointment2);
		waitingPatient2.setId(2);
		waitingPatient2.setType("General");
		WaitingPatient waitingPatient3 = new WaitingPatient();
		waitingPatient3.setAppointment(appointment3);
		waitingPatient3.setId(3);
		waitingPatient3.setType("General");
		Mockito.when(waitingPatientRepository.getOne(1)).thenReturn(waitingPatient1);
		
		Mockito.when(waitingPatientRepository.getOne(2)).thenReturn(waitingPatient2);
		
		Mockito.when(waitingPatientRepository.getOne(3)).thenReturn(waitingPatient3);
				
		Set<Bed> beds = new HashSet<>();
		for(int bedIndex = 0; bedIndex < 2; ++bedIndex) {
			Bed bed = new GeneralBed(1000.1,true, "Steel");
			bed.setAppointment(null);
			bed.setOccupied(false);
			beds.add(bed);
		}
		Bed bed1 = new GeneralBed(1000.1,true, "Steel");
			bed1.setAppointment(appointment4);
			bed1.setOccupied(true);
		Bed bed2 = new GeneralBed(1000.1,true, "Steel");
			bed2.setAppointment(appointment5);
			bed2.setOccupied(true);
		Bed bed3 =  new GeneralBed(1000.0,true, "Steel");
			bed3.setAppointment(appointment6);
			bed3.setOccupied(true);
		beds.add(bed1);
		beds.add(bed2);
		beds.add(bed3);
		mockDiagnosticCenter1.getBeds().addAll(beds);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter1);
		List<Integer> waitingIds = new ArrayList<>();
		waitingIds.add(1);
		waitingIds.add(2);
		waitingIds.add(3);
		assertThrows(InvalidBedAllocationException.class, () -> {
			adminService.allocateBeds(mockDiagnosticCenter1.getId(), waitingIds, "General");			
		});
	}
	
	/**
	 * Method to test the Exception thrown when bed is being allocated to person with non approval of appointment
	 * 
	 */
	
	@Test
	@DisplayName("Appointment Not Approved then no Bed is Allocated")
	public void failIfAppointmentIsNotApproved() {
		Appointment appointment1 = new Appointment(101, Timestamp.valueOf(LocalDateTime.now()), 0, "Diagnosis", "Symptoms", mockPatient1, mockDiagnosticCenter1);
		Appointment appointment2 = new Appointment(102, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient2, mockDiagnosticCenter1);
		Appointment appointment3 = new Appointment(103, Timestamp.valueOf(LocalDateTime.now()), 2, "Diagnosis", "Symptoms", mockPatient3, mockDiagnosticCenter1);
		Appointment appointment4 = new Appointment(104, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient4, mockDiagnosticCenter1);
		Appointment appointment5 = new Appointment(105, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient5, mockDiagnosticCenter1);
		Appointment appointment6 = new Appointment(106, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient6, mockDiagnosticCenter1);
				
		WaitingPatient waitingPatient1 = new WaitingPatient();
		waitingPatient1.setAppointment(appointment1);
		waitingPatient1.setId(1);
		waitingPatient1.setType("General");
		WaitingPatient waitingPatient2 = new WaitingPatient();
		waitingPatient2.setAppointment(appointment2);
		waitingPatient2.setId(2);
		waitingPatient2.setType("General");
		WaitingPatient waitingPatient3 = new WaitingPatient();
		waitingPatient3.setAppointment(appointment3);
		waitingPatient3.setId(3);
		waitingPatient3.setType("General");
		Mockito.when(waitingPatientRepository.getOne(1)).thenReturn(waitingPatient1);
		
		Mockito.when(waitingPatientRepository.getOne(2)).thenReturn(waitingPatient2);
		
		Mockito.when(waitingPatientRepository.getOne(3)).thenReturn(waitingPatient3);
				
		Set<Bed> beds = new HashSet<>();
		for(int bedIndex = 0; bedIndex < 2; ++bedIndex) {
			Bed bed = new GeneralBed(1000.1,true, "Steel");
			bed.setAppointment(null);
			bed.setOccupied(false);
			beds.add(bed);
		}
		Bed bed1 = new GeneralBed(1000.1,true, "Steel");
			bed1.setAppointment(appointment4);
			bed1.setOccupied(true);
		Bed bed2 = new GeneralBed(1000.1,true, "Steel");
			bed2.setAppointment(appointment5);
			bed2.setOccupied(true);
		Bed bed3 =  new GeneralBed(1000.0,true, "Steel");
			bed3.setAppointment(appointment6);
			bed3.setOccupied(true);
		beds.add(bed1);
		beds.add(bed2);
		beds.add(bed3);
		mockDiagnosticCenter1.getBeds().addAll(beds);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter1);
		List<Integer> waitingIds = new ArrayList<>();
		waitingIds.add(1);
		waitingIds.add(2);
		waitingIds.add(3);
		assertThrows(AppointmentNotApprovedException.class, () -> {
			adminService.allocateBeds(mockDiagnosticCenter1.getId(), waitingIds, "General");			
		});
	}
	
	/**
	 * Method to Test throwing of Exception when Invalid Diagnostic Center is entered 
	 * @throws Exception
	 */
	@Test
	@DisplayName("Throw Invalid Diagnostic Center")
	public void throwInvalidDiagnosticCenterException () throws Exception {
		List<Integer> waitingIds = new ArrayList<>();
		waitingIds.add(1);
		waitingIds.add(2);
		waitingIds.add(3);
		assertThrows(DiagnosticCenterNotFoundException.class, () -> {
			adminService.allocateBeds(99, waitingIds, "General");			
		});
	}
	
	/**
	 * Returns the Bed type class when String name of the type is passed.
	 * @throws Exception
	 */
	
	@Test
	@DisplayName("Checking the Instance According To Bed Type Given")
	public void getBedTypeReturnsAICUBedClass() throws Exception {
		Class<?> bedType = adminService.getBedType("ICU");
		IntensiveCareBed bed = new IntensiveCareBed();
		assertTrue(bedType.isInstance(bed));
	}
	
	/**
	 * Method to Test Whether the Type String passed returns the Required Class Type
	 * @throws Exception
	 */
	@Test
	@DisplayName("Sibling of The Parent Bed Should not match each other")
	public void getBedTypeReturnDoesNotMatchOtherInheritedInstances() throws Exception {
		Class<?> bedType = adminService.getBedType("ICCU");
		IntensiveCareBed bed = new IntensiveCareBed();
		assertFalse(bedType.isInstance(bed));
	}
	
	/**
	 * Method To Test the Throwing of Exception when Some Random String is passed to retreive a Class Type
	 * @throws Exception
	 */
	
	@Test
	@DisplayName("Invalid Bed Type When passed Throws Exception")
	public void getBedTypeThrowsExceptionWhenInvalidTypePassed() throws Exception {
		assertThrows(InvalidBedTypeException.class,() -> {
			adminService.getBedType("Some Random Type");
		} );
	}
}
