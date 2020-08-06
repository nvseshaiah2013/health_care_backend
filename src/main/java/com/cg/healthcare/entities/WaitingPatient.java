package com.cg.healthcare.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="WAITING_PATIENTS")
@SequenceGenerator(name="waiting_no", initialValue = 1, allocationSize = 1)
public class WaitingPatient implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "waiting_no")
	private int id;
	
	@OneToOne
	@JoinColumn(name = "APP_ID", nullable = false)
	private Appointment appointment;
	
	@Column(name="REQUEST_DATE", nullable = false)

	private Timestamp requestedOn;
	
	public WaitingPatient() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Timestamp getRequestedOn() {
		return requestedOn;
	}

	public void setRequestedOn(Timestamp requestedOn) {
		this.requestedOn = requestedOn;
	}	
	
}
