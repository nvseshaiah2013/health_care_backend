package com.cg.healthcare.requests;

public class DiagnosticCenterSignUpRequest {

	private String name;

	private String contactNo;

	private String address;

	private String contactEmail;
	
	private String servicesOffered;
	
	private String userName;
	
	private String password;

	public DiagnosticCenterSignUpRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DiagnosticCenterSignUpRequest(String name, String contactNo, String address, String contactEmail,
			String servicesOffered, String userName, String password) {
		super();
		this.name = name;
		this.contactNo = contactNo;
		this.address = address;
		this.contactEmail = contactEmail;
		this.servicesOffered = servicesOffered;
		this.userName = userName;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getServicesOffered() {
		return servicesOffered;
	}

	public void setServicesOffered(String servicesOffered) {
		this.servicesOffered = servicesOffered;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
