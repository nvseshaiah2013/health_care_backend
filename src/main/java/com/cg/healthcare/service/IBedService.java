package com.cg.healthcare.service;

public interface IBedService {
	//assigning bed to a patient
	public boolean admittedSuccessfully(int appointmentId);
	
	//assigning general bed to a patient
	public boolean admittedGeneralBedSuccessfully(int appointmentId);

	//assigning Intensive Care Bed to patient
	public boolean admittedIntensiveCareBedSuccessfully(int appointmentId);

	//assigning Intensive critical care bed to patient
	public boolean admittedIntensiveCriticalCareBedSuccessfully(int appointmentId);

	//assigning ventilator bed to patient
	public boolean admittedVentilatorBedSuccessfully(int appointmentId);
	
	//discharging patient
	public boolean dischargePatient(int appointmentId) throws Exception;
}
