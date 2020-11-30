package com.zyx2.practice.wcf.model;

import java.time.LocalDate;
import java.util.List;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Phone {
	@Id
	private Long employeeId;

	private String employeeName;

	@JsonbDateFormat(value = "yyyyMMdd")
	private LocalDate purchaseDate;

	private String model;
	
//	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinColumn(name="employeeId")
//	private List<Usage> usages;

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

//	public List<Usage> getUsages() {
//		return usages;
//	}
//
//	public void setUsages(List<Usage> usages) {
//		this.usages = usages;
//	}


	

}
