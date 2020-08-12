package com.cg.healthcare.requests;

public class TestResultForm {

	int appointmentId;
	String condition;
	double testReading;
	

	public TestResultForm(int appointmentId, String condition, double testReading) {
		super();
		this.appointmentId = appointmentId;
		this.condition = condition;
		this.testReading = testReading;
	}
	
	public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public double getTestReading() {
		return testReading;
	}
	public void setTestReading(double testReading) {
		this.testReading = testReading;
	}
}
