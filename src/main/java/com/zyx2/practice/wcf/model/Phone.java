package com.zyx2.practice.wcf.model;

import java.time.LocalDate;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;

@Entity
public class Phone {
	@Id
	@Digits(integer=4, fraction=0) @Positive
	private Long employeeId;

	@NotEmpty
	private String employeeName;

	@JsonbDateFormat(value = "yyyyMMdd")
	@Past
	private LocalDate purchaseDate;

	@NotEmpty
	private String model;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return "Phone [employeeId=" + employeeId + ", employeeName=" + employeeName + ", purchaseDate=" + purchaseDate
				+ ", model=" + model + "]";
	}

}
