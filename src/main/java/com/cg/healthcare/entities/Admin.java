package com.cg.healthcare.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ADMIN_MASTER")
public class Admin implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	
	@OneToMany
	@JoinTable(name = "ADM_DIAGS",
			   joinColumns = @JoinColumn(name ="ADM_ID"),
			   inverseJoinColumns = @JoinColumn(name = "DIAG_ID"))
	@JsonIgnore
	private Set<DiagnosticCenter> diagnosticCenters = new HashSet<DiagnosticCenter>();
	
	@OneToMany
	@JoinTable(name = "ADM_WAIT_PTS", 
		       joinColumns = @JoinColumn(name = "ADM_ID"), 
		       inverseJoinColumns = @JoinColumn(name = "WP_ID"))	
	@JsonIgnore
	private Set<WaitingPatient> waitingPatients = new HashSet<WaitingPatient>();
	
	@OneToMany
	@JoinTable(name = "ADM_APTS", 
		joinColumns = @JoinColumn(name = "ADM_ID"), 
		inverseJoinColumns = @JoinColumn(name = "APT_ID"))
	@JsonIgnore
	private Set<Appointment> appointments = new HashSet<Appointment>();
	
	public Admin() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<DiagnosticCenter> getDiagnosticCenters() {
		return diagnosticCenters;
	}

	public void setDiagnosticCenters(Set<DiagnosticCenter> diagnosticCenters) {
		this.diagnosticCenters = diagnosticCenters;
	}

	public Set<WaitingPatient> getWaitingPatients() {
		return waitingPatients;
	}

	public void setWaitingPatients(Set<WaitingPatient> waitingPatients) {
		this.waitingPatients = waitingPatients;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	public void addWaitingPatient(WaitingPatient waitingPatient) {
			this.waitingPatients.add(waitingPatient);
	}
	
	public void removeWaitingPatients(WaitingPatient waitingPatient) {
		this.waitingPatients.remove(waitingPatient);
	}
	
}
