package com.cg.healthcare.service;

import java.util.List;

import com.cg.healthcare.entities.Appointment;

public interface IAppointmentService {

	public List<Appointment> viewAppointments();
	public Appointment viewMyAppointment(int appointmentId) throws Exception;
	Appointment makeAppointment(Appointment appointment, String username, int testId, int diagnosticCenterId)
			throws Exception;

}
