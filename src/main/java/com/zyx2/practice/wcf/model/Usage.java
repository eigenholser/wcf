package com.zyx2.practice.wcf.model;

import java.time.LocalDate;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;

@Entity
public class Usage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonbProperty("emplyeeId")
	@Digits(integer=4, fraction=0) @Positive
	private Long employeeId;

	@JsonbDateFormat(value = "M/d/yyyy")
	@Past
	private LocalDate date;

	@Digits(integer=5, fraction=0) @Positive
	private int totalMinutes;

	@Digits(integer=5, fraction=2) @Positive
	private Float totalData;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getTotalMinutes() {
		return totalMinutes;
	}

	public void setTotalMinutes(int totalMinutes) {
		this.totalMinutes = totalMinutes;
	}

	public Float getTotalData() {
		return totalData;
	}

	public void setTotalData(Float totalData) {
		this.totalData = totalData;
	}

	@Override
	public String toString() {
		return "Usage [id=" + id + ", employeeId=" + employeeId + ", date=" + date + ", totalMinutes=" + totalMinutes
				+ ", totalData=" + totalData + "]";
	}



}
