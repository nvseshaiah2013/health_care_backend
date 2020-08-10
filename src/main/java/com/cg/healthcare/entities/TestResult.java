package com.cg.healthcare.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;


@Entity
@Table(name="TEST_RESULTS")
public class TestResult implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private TestResultId id;

	@Column(name="TEST_READ")
	private double testReading;
	
	@Column(name="P_CONDITION")
	@Pattern(regexp ="(Below Normal)|(Normal)|(Above Normal)")
	private String condition;
	
	@ManyToOne
	@MapsId("APP_ID")
	@JoinColumn(name = "APP_ID")
	private Appointment appointment;
	
	@ManyToOne
	@MapsId("TEST_ID")
	@JoinColumn(name ="TEST_ID")
	private DiagnosticTest test;

	public TestResult() {
	}
	public TestResult(TestResultId id, double testReading,
			@Pattern(regexp = "(Below Normal)|(Normal)|(Above Normal)") String condition, Appointment appointment,
			DiagnosticTest test) {
		super();
		this.id = id;
		this.testReading = testReading;
		this.condition = condition;
		this.appointment = appointment;
		this.test = test;
	}



	public double getTestReading() {
		return testReading;
	}

	public void setTestReading(double testReading) {
		this.testReading = testReading;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public TestResultId getId() {
		return id;
	}

	public void setId(TestResultId id) {
		this.id = id;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public DiagnosticTest getTest() {
		return test;
	}

	public void setTest(DiagnosticTest test) {
		this.test = test;
	}	
	
	
	
}
