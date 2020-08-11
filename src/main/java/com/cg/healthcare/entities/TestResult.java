package com.cg.healthcare.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;


@Entity
@Table(name="TEST_RESULTS")
@SequenceGenerator(name = "TEST_R_SEQ",allocationSize = 1, initialValue = 1001)
public class TestResult implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEST_R_SEQ")
	private int id;

	@Column(name="TEST_READ")
	private double testReading;
	
	@Column(name="P_CONDITION")
	@Pattern(regexp ="(Below Normal)|(Normal)|(Above Normal)")
	private String condition;
	
	@OneToOne(mappedBy="TEST_RESULTS")
	private Appointment appointment;

	public TestResult() {
		
	}


	public TestResult(int id, double testReading,
			@Pattern(regexp = "(Below Normal)|(Normal)|(Above Normal)") String condition, Appointment appointment) {
		this.id = id;
		this.testReading = testReading;
		this.condition = condition;
		this.appointment = appointment;
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

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}	
	
}
