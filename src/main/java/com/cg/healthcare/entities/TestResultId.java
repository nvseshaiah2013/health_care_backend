package com.cg.healthcare.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TestResultId implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="APP_ID")
	private int appointmentId;
	
	@Column(name="TEST_ID")
	private int testId;
	
	
	
	public TestResultId(int appointmentId, int testId) {
		super();
		this.appointmentId = appointmentId;
		this.testId = testId;
	}
	public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	public int getTestId() {
		return testId;
	}
	public void setTestId(int testId) {
		this.testId = testId;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) 
			return true;
		if(!(o instanceof TestResultId)) 
			return false;
		TestResultId that = (TestResultId)o;
		return Objects.equals(this.getAppointmentId(), that.getAppointmentId()) && 
				Objects.equals(this.getAppointmentId(), that.getAppointmentId());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getAppointmentId(),this.getTestId());
	}
	
}
