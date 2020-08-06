package com.cg.healthcare.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

public class PatientSignUpRequest {
	
	@NotNull(message = "Username Cannot be Null")
	@NotEmpty(message = "Username Cannot be Empty")
	@Email(message = "Username must be Email")
	private String username;
	
	@NotNull(message="Name Cannot be Null")
	@NotEmpty(message = "Name Cannot be Empty")
	@Pattern(regexp = "([a-zA-Z]+[ ]?)+", message = "Name cannot contain other characters other than alphabets")
	private String name;
	
	@NotNull(message = "Password Cannot be Null")
	@NotEmpty(message = "Password Cannot be Left Blank")
	private String password;
	
	@NotNull(message = "Age cannot be null")
	@Range(min = 1, message = "Age Cannot be null or Empty")
	private int age;
	
	@NotNull(message = "Gender Cannot be Null")
	@NotEmpty(message = "Gender Cannot be Left Blank")
	@Pattern(regexp = "Male|Female|Other")
	private String gender;
	
	@NotNull(message = "Phone No Cannot be Null")
	@NotEmpty(message = "Phone No Cannot be left Blank")
	@Pattern(regexp = "[1-9][0-9]{9}")
	private String phoneNo;
	
	public PatientSignUpRequest(
			@NotNull(message = "Username Cannot be Null") @NotEmpty(message = "Username Cannot be Empty") @Email(message = "Username must be Email") String username,
			@NotNull(message = "Name Cannot be Null") @NotEmpty(message = "Name Cannot be Empty") @Pattern(regexp = "([a-zA-Z]+[ ]?)+", message = "Name cannot contain other characters other than alphabets") String name,
			@NotNull(message = "Password Cannot be Null") @NotEmpty(message = "Password Cannot be Left Blank") String password,
			@NotNull(message = "Age cannot be null") @Range(min = 1, message = "Age Cannot be null or Empty") int age,
			@NotNull(message = "Gender Cannot be Null") @NotEmpty(message = "Gender Cannot be Left Blank") @Pattern(regexp = "Male|Female|Other") String gender,
			@NotNull(message = "Phone No Cannot be Null") @NotEmpty(message = "Phone No Cannot be left Blank") @Pattern(regexp = "[1-9][0-9]{9}") String phoneNo) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.age = age;
		this.gender = gender;
		this.phoneNo = phoneNo;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
