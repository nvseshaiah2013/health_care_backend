package com.cg.healthcare.entities;

import java.io.Serializable;
import java.sql.Timestamp;

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
	
	@ManyToOne
	@JoinColumn(name = "TEST_ID", nullable = false)
	private DiagnosticTest diagnosticTest;
	
	@ManyToOne
	@JoinColumn(name = "PATIENT_ID", nullable = false)
	private Patient patient;
	
	@ManyToOne
	@JoinColumn(name="D_CENTER_ID", nullable = false)
	private DiagnosticCenter diagnosticCenter;
	
	@OneToOne
	@JoinColumn(name="T_RESULT_ID")
	private TestResult testResult;
	
	public Appointment()
	{
		
	}
	

	public Appointment(int id, Timestamp appointmentDate, int approvalStatus, String diagnosis, String symptoms, Patient patient, DiagnosticCenter diagnosticCenter) {
		this.id = id;
		this.appointmentDate = appointmentDate;
		this.approvalStatus = approvalStatus;
		this.diagnosis = diagnosis;
		this.symptoms = symptoms;
		this.patient = patient;
		this.diagnosticCenter = diagnosticCenter;
	}

	
	public Appointment(int id, Timestamp appointmentDate, String diagnosis, String symptoms, DiagnosticTest diagnosticTest,
			Patient patient, DiagnosticCenter diagnosticCenter) {
		super();
		this.id = id;
		this.appointmentDate = appointmentDate;
		this.diagnosis = diagnosis;
		this.symptoms = symptoms;
		this.diagnosticTest = diagnosticTest;
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


	public DiagnosticTest getDiagnosticTest() {
		return diagnosticTest;
	}


	public void setDiagnosticTest(DiagnosticTest diagnosticTest) {
		this.diagnosticTest = diagnosticTest;
	}

	@JsonIgnore
	public TestResult getTestResult() {
		return testResult;
	}


	public void setTestResult(TestResult testResult) {
		this.testResult = testResult;
	}


	@Override
	public String toString() {
		return "Appointment [id=" + id + ", appointmentDate=" + appointmentDate + ", approvalStatus=" + approvalStatus
				+ ", diagnosis=" + diagnosis + ", symptoms=" + symptoms + ", patient=" + patient + "]";
	}
	
	
	
	
}
