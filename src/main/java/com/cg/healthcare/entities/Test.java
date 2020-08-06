package com.cg.healthcare.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TEST_MASTER")
public class Test implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="TEST_NAME", nullable = false)
	private String testName;
	
	@Column(name="PRICE", nullable = false)
	private double testPrice;
	
	@Column(name="NORMAL_VALUE", nullable = false)
	private String normalValue;
	
	@Column(name="UNITS", nullable = false)
	private String units;
	
	@ManyToMany(mappedBy = "tests")
	@JsonIgnore
	private Set<DiagnosticCenter> diagnosticCenters = new HashSet<>();
	
	public Test()
	{
		
	}
	
	public Test(String testName, double testPrice, String normalValue, String units)
	{
		this.testName = testName;
		this.testPrice = testPrice;
		this.normalValue = normalValue;
		this.units = units;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public double getTestPrice() {
		return testPrice;
	}

	public void setTestPrice(double testPrice) {
		this.testPrice = testPrice;
	}

	public String getNormalValue() {
		return normalValue;
	}

	public void setNormalValue(String normalValue) {
		this.normalValue = normalValue;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public Set<DiagnosticCenter> getDiagnosticCenters() {
		return diagnosticCenters;
	}

	public void setDiagnosticCenters(Set<DiagnosticCenter> diagnosticCenters) {
		this.diagnosticCenters = diagnosticCenters;
	}
	
	public void addDiagnosticCenter(DiagnosticCenter diagnosticCenter) {
		this.diagnosticCenters.add(diagnosticCenter);
	}
	
	public void removeDiagnosticCenter(DiagnosticCenter diagnosticCenter) {
		this.diagnosticCenters.remove(diagnosticCenter);
	}
}
