package com.cg.healthcare.requests;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

public class MakeAppointmentRequest {

	
	@NotNull(message = "Appointment Date should not be null")
	private Timestamp appointmentDate;
	
	@ColumnDefault(value = "0")
	private int approvalStatus; // 0 - Pending 1 - Approved 2 - Rejected
	
	@NotNull(message = "Test Id Cannot Be Null")
	private int testId;
	
	@NotNull(message = "Diagnostic Center Id cannot be null")
	private int diagnosticCenterId;
	
	@NotNull(message = "Symptoms Should not be Null")
	private String symptoms;

	
	
	public MakeAppointmentRequest(@NotNull(message = "Appointment Date should not be null") Timestamp appointmentDate,
			int approvalStatus, @NotNull(message = "Test Id Cannot Be Null") int testId,
			@NotNull(message = "Diagnostic Center Id cannot be null") int diagnosticCenterId,
			@NotNull(message = "Symptoms Should not be Null") String symptoms) {
		this.appointmentDate = appointmentDate;
		this.approvalStatus = approvalStatus;
		this.testId = testId;
		this.diagnosticCenterId = diagnosticCenterId;
		this.symptoms = symptoms;
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

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public int getDiagnosticCenterId() {
		return diagnosticCenterId;
	}

	public void setDiagnosticCenterId(int diagnosticCenterId) {
		this.diagnosticCenterId = diagnosticCenterId;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}
	
	
}
