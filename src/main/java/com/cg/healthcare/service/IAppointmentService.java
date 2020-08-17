package com.cg.healthcare.service;

import java.util.Set;

import com.cg.healthcare.entities.Appointment;

public interface IAppointmentService {
	Appointment makeAppointment(Appointment appointment, String username, int testId, int diagnosticCenterId)
			throws Exception;
	Set<Appointment> viewAppointments(String username) throws Exception;
	Appointment viewMyAppointment(int appointmentId, String username) throws Exception;

}
