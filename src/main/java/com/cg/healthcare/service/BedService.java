package com.cg.healthcare.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.healthcare.dao.AppointmentRepository;
import com.cg.healthcare.dao.BedRepository;
import com.cg.healthcare.dao.WaitingPatientRepository;
import com.cg.healthcare.entities.Appointment;
import com.cg.healthcare.entities.Bed;
import com.cg.healthcare.entities.DiagnosticCenter;
import com.cg.healthcare.entities.GeneralBed;
import com.cg.healthcare.entities.IntensiveCareBed;
import com.cg.healthcare.entities.IntensiveCriticalCareBed;
import com.cg.healthcare.entities.VentilatorBed;
import com.cg.healthcare.entities.WaitingPatient;
import com.cg.healthcare.exception.PatientNotAdmittedException;
@Service
@Transactional
public class BedService implements IBedService{
	private static final Logger LOGGER  = LoggerFactory.getLogger(BedService.class);

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private WaitingPatientRepository waitPatientRepository;
	
	@Autowired
	private BedRepository bedRepository;

	//assigning bed to patient
	@Override
	public boolean admittedSuccessfully(int appointmentId) {
		
		Appointment appointment= appointmentRepository.getOne(appointmentId);
		
		DiagnosticCenter diagnosticCenter=appointment.getDiagnosticCenter();
		
		Optional<Bed> bed = diagnosticCenter.getBeds().stream().filter(b->b.isOccupied()==false).findFirst();
		
		if(!bed.isPresent()) {
			
			LOGGER.info("No Bed Available adding Bed Request to Waiting Bed List");
			
			WaitingPatient waitingPatient=new WaitingPatient();
			waitingPatient.setAppointment(appointment);
			return false;
			}
		else {
			LOGGER.info("Successfully Applied for Bed");
			bed.get().setOccupied(true);
			bed.get().setAppointment(appointment);
			bedRepository.save(bed.get());
			return true;
			}
	}
	
	//assigning general bed to patient
	@Override
	public boolean admittedGeneralBedSuccessfully(int appointmentId) {
		Appointment appointment= appointmentRepository.getOne(appointmentId);
		
		DiagnosticCenter diagnosticCenter=appointment.getDiagnosticCenter();
		
		Optional<Bed> bed = diagnosticCenter.getBeds().stream().filter(b -> b instanceof GeneralBed).filter(b->b.isOccupied()==false).findFirst();
		
		if(!bed.isPresent()) {
			
			LOGGER.info("No Bed Available adding Bed Request to Waiting Bed List");			
			WaitingPatient waitingPatient=new WaitingPatient();
			waitingPatient.setAppointment(appointment);
			waitingPatient.setRequestedOn(Timestamp.valueOf(LocalDateTime.now()));
			waitingPatient.setType("General");
			waitPatientRepository.save(waitingPatient);
			return false;
			}
		else {
			LOGGER.info("Successfully Applied for Bed");
			bed.get().setOccupied(true);
			bed.get().setAppointment(appointment);
			bedRepository.save(bed.get());
			return true;
			}
	}
	
	//assigning Intensive Care Bed to patient
	@Override
	public boolean admittedIntensiveCareBedSuccessfully(int appointmentId) {
		Appointment appointment= appointmentRepository.getOne(appointmentId);
		
		DiagnosticCenter diagnosticCenter=appointment.getDiagnosticCenter();
		
		Optional<Bed> bed = diagnosticCenter.getBeds().stream().filter(b -> b instanceof IntensiveCareBed).filter(b->b.isOccupied()==false).findFirst();
		
		if(!bed.isPresent()) {
			
			LOGGER.info("No Bed Available adding Bed Request to Waiting Bed List");
			
			WaitingPatient waitingPatient=new WaitingPatient();
			waitingPatient.setAppointment(appointment);
			waitingPatient.setRequestedOn(Timestamp.valueOf(LocalDateTime.now()));
			waitingPatient.setType("ICU");
			waitPatientRepository.save(waitingPatient);
			return false;
			}
		else {
			LOGGER.info("Successfully Applied for Bed");
			bed.get().setOccupied(true);
			bed.get().setAppointment(appointment);
			bedRepository.save(bed.get());
			return true;
			}
	}
	
	//assigning Intensive critical care bed to patient
	@Override
	public boolean admittedIntensiveCriticalCareBedSuccessfully(int appointmentId) {
		Appointment appointment= appointmentRepository.getOne(appointmentId);

		DiagnosticCenter diagnosticCenter=appointment.getDiagnosticCenter();
		
		Optional<Bed> bed = diagnosticCenter.getBeds().stream().filter(b -> b instanceof IntensiveCriticalCareBed).filter(b->b.isOccupied()==false).findFirst();
		
		if(!bed.isPresent()) {
			
			LOGGER.info("No Bed Available adding Bed Request to Waiting Bed List");
			
			WaitingPatient waitingPatient=new WaitingPatient();
			waitingPatient.setAppointment(appointment);
			waitingPatient.setRequestedOn(Timestamp.valueOf(LocalDateTime.now()));
			waitingPatient.setType("ICCU");
			waitPatientRepository.save(waitingPatient);
			return false;
			}
		else {
			LOGGER.info("Successfully Applied for Bed");
			bed.get().setOccupied(true);
			bed.get().setAppointment(appointment);
			bedRepository.save(bed.get());
			return true;
			}
	}
	
	//assigning ventilator bed to patient
	@Override
	public boolean admittedVentilatorBedSuccessfully(int appointmentId) {
		Appointment appointment= appointmentRepository.getOne(appointmentId);
		
		DiagnosticCenter diagnosticCenter=appointment.getDiagnosticCenter();
		
		Optional<Bed> bed = diagnosticCenter.getBeds().stream().filter(b -> b instanceof VentilatorBed).filter(b->b.isOccupied()==false).findFirst();
		
		if(!bed.isPresent()) {
			
			LOGGER.info("No Bed Available adding Bed Request to Waiting Bed List");
			
			WaitingPatient waitingPatient=new WaitingPatient();
			waitingPatient.setAppointment(appointment);
			waitingPatient.setRequestedOn(Timestamp.valueOf(LocalDateTime.now()));
			waitingPatient.setType("Ventilator");
			waitPatientRepository.save(waitingPatient);
			return false;
			}
		else {
			LOGGER.info("Successfully Applied for Bed");
			bed.get().setOccupied(true);
			bed.get().setAppointment(appointment);
			bedRepository.save(bed.get());
			return true;
			}
	}
	
	//discharging patient
	@Override
	public boolean dischargePatient(int appointmentId) throws Exception {
		
		Appointment appointment= appointmentRepository.findById(appointmentId).get();

		DiagnosticCenter diagnosticCenter=appointment.getDiagnosticCenter();
		
		Optional<Bed> bed = diagnosticCenter.getBeds().stream().filter(b->b.getAppointment().getId()==appointmentId).findFirst();
		if(!bed.isPresent()) {
			LOGGER.error("No Patient Available for Discharges");
			throw new PatientNotAdmittedException("Patient not found", "Patient not admitted");
			}
		else {
			LOGGER.info("Patient Successfully Discharged");
			bed.get().setOccupied(false);
			bed.get().setAppointment(null);
			bedRepository.save(bed.get());
			return true;
			}
	}


}
