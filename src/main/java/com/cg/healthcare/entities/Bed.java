package com.cg.healthcare.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="BEDS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SequenceGenerator(name = "bed_id",initialValue = 1000, allocationSize = 1)
public class Bed implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bed_id")
	private int id;
	
	@Column(name="IS_OCCUPIED")
	private boolean isOccupied = false;
	
	@OneToOne
	@JoinColumn(name = "APPOINTMENT_ID")
	private Appointment appointment;
	
	@Column(name="PRICE",nullable =  false)
	private double pricePerDay;
	
	@ManyToOne
	@JoinColumn(name = "D_CENTER_ID")
	private DiagnosticCenter diagnosticCenter;
	
	public Bed(int id, boolean isOccupied, Appointment appointment, double pricePerDay,
			DiagnosticCenter diagnosticCenter) {
		super();
		this.id = id;
		this.isOccupied = isOccupied;
		this.appointment = appointment;
		this.pricePerDay = pricePerDay;
		this.diagnosticCenter = diagnosticCenter;
	}
	
	public DiagnosticCenter getDiagnosticCenter() {
		return diagnosticCenter;
	}

	public void setDiagnosticCenter(DiagnosticCenter diagnosticCenter) {
		this.diagnosticCenter = diagnosticCenter;
	}

	public Bed()
	{
		this.isOccupied = false;
	}
	
	public Bed(double pricePerDay)
	{
		this();
		this.pricePerDay = pricePerDay;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean isOccupied() {
		return isOccupied;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}
	
	public void addAppointment(Appointment appointment) {
		this.setAppointment(appointment);
	}
	
	public void removeAppointment() {
		this.setAppointment(null);
	}
}
