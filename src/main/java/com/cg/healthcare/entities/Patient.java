package com.cg.healthcare.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "PATIENTS")
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "PHONE_NO", nullable = false)
	private String phoneNo;

	@Column(name = "AGE", nullable = false)
	private int age;

	@Column(name = "GENDER", nullable = false)
	@Pattern(regexp = "Male|Female|Other")
	private String gender;

	public Patient() {

	}

	public Patient(int id, String name, int age, String gender, String phoneNo) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.phoneNo = phoneNo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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

}
