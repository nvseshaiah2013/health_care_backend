package com.cg.healthcare.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="APPOINTMENTS")
@SequenceGenerator(name = "appoint_id",initialValue = 1000, allocationSize = 1)
public class Appointment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "appoint_id")
	private int id;
	
	@Column(name="APPOINT_DATE", nullable = false)
	private Timestamp appointmentDate;
	
	@Column(name="STATUS")
	@ColumnDefault(value = "0")
	private int approvalStatus; // 0 - Pending 1 - Approved 2 - Rejected
	
	@Column(name="DIAGNOSIS")
	private String diagnosis;
	
	@Column(name="SYMPTOMS")
	private String symptoms;
	
	@ManyToMany
	@JoinTable(name = "APPOINT_TESTS",
			   joinColumns = @JoinColumn(name = "APP_ID"),
			   inverseJoinColumns = @JoinColumn(name = "TEST_ID")
			)
	@JsonIgnore
	private Set<Test> assignedTests = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name = "PATIENT_ID", nullable = false)
	private Patient patient;
	
	@ManyToOne
	@JoinColumn(name="D_CENTER_ID", nullable = false)
	private DiagnosticCenter diagnosticCenter;
	
	

	public Appointment(int id, Timestamp appointmentDate, int approvalStatus, String diagnosis, String symptoms, Patient patient, DiagnosticCenter diagnosticCenter) {
		this.id = id;
		this.appointmentDate = appointmentDate;
		this.approvalStatus = approvalStatus;
		this.diagnosis = diagnosis;
		this.symptoms = symptoms;
		this.patient = patient;
		this.diagnosticCenter = diagnosticCenter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Timestamp appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public int getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(int approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Set<Test> getAssignedTests() {
		return assignedTests;
	}

	public void setAssignedTests(Set<Test> assignedTests) {
		this.assignedTests = assignedTests;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	public DiagnosticCenter getDiagnosticCenter() {
		return diagnosticCenter;
	}

	public void setDiagnosticCenter(DiagnosticCenter diagnosticCenter) {
		this.diagnosticCenter = diagnosticCenter;
	}
	
}
