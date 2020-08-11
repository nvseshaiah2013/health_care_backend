package com.cg.healthcare.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class DiagnosticCenterSignUpRequest {

	@NotNull(message = "Name Cannot be Null")
	@NotEmpty(message = "Name Cannot be left Blank")
	private String name;

	@NotNull(message = "Phone No Cannot be Null")
	@NotEmpty(message = "Phone No Cannot be left Blank")
	@Pattern(regexp = "[1-9][0-9]{9}")
	private String contactNo;

	@NotNull(message = "Address Cannot be Null")
	@NotEmpty(message = "Address Cannot be left Blank")
	private String address;

	@NotNull(message = "Email Cannot be Null")
	@NotEmpty(message = "Email Cannot be Empty")
	@Email
	private String contactEmail;
	
	@NotNull(message = "Services offered Cannot be Null")
	@NotEmpty(message = "Services Offered Cannot be Empty")
	private String servicesOffered;
	
	@NotNull(message = "Username Cannot be Null")
	@NotEmpty(message = "Username Cannot be Empty")
	@Email(message = "Username must be Email")
	private String userName;
	
	@NotNull(message = "Password Cannot be Null")
	@NotEmpty(message = "Password Cannot be Left Blank")
	private String password;

	public DiagnosticCenterSignUpRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DiagnosticCenterSignUpRequest(
			@NotNull(message = "Name Cannot be Null") @NotEmpty(message = "Name Cannot be left Blank")String name, 
			@NotNull(message = "Phone No Cannot be Null") @NotEmpty(message = "Phone No Cannot be left Blank") @Pattern(regexp = "[1-9][0-9]{9}")String contactNo,
			@NotNull(message = "Address Cannot be Null") @NotEmpty(message = "Address Cannot be left Blank") String address, 
			@NotNull(message = "Email Cannot be Null") @NotEmpty(message = "Email Cannot be Empty")@Email String contactEmail,
			@NotNull(message = "Services offered Cannot be Null") @NotEmpty(message = "Services Offered Cannot be Empty") String servicesOffered, 
			@NotNull(message = "Username Cannot be Null") @NotEmpty(message = "Username Cannot be Empty") @Email(message = "Username must be Email") String userName, 
			@NotNull(message = "Password Cannot be Null") @NotEmpty(message = "Password Cannot be Left Blank") String password) {
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
