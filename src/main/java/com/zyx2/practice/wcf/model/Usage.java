package com.zyx2.practice.wcf.model;

import java.time.LocalDate;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Past;

@Entity
public class Usage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long employeeId;

	@JsonbDateFormat(value = "M/d/yyyy")
	@Past
	private LocalDate date;

	private int totalMinutes;

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
