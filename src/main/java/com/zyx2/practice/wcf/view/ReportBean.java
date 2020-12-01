package com.zyx2.practice.wcf.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.zyx2.practice.wcf.model.Phone;
import com.zyx2.practice.wcf.model.Usage;
import com.zyx2.practice.wcf.model.UsageDTO;
import com.zyx2.practice.wcf.repository.UsageRepository;
import com.zyx2.practice.wcf.service.ReportService;

@Named
@RequestScoped
public class ReportBean {

	@Inject
	UsageRepository usageRepository;
	
	@Inject
	ReportService reportService;

	private List<UsageDTO> usageDetail = new ArrayList<>();
	private List<String> monthHeaders = new ArrayList<>();
	private int phoneCount;
	private List<Phone> phones;
	private List<Usage> usages;
	private String reportDate;
	private Float averageMinutes;
	private BigDecimal averageData;
	private Integer totalMinutes;
	private BigDecimal totalData;
	
	@PostConstruct
	public void init() {
		// Start date and end date for report.
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate endDate = usageRepository.maxDate().plusDays(1L); // XXX: Add one day for the query
		LocalDate startDate = reportService.startDate(endDate);
		usages = usageRepository.findByDate(startDate, endDate);
		
		// Associated phones
		Set<Long> activeEmployeeIds = reportService.usageEmployeeId(usages.stream());
		phones = reportService.getActivePhones(activeEmployeeIds);
		
		// Header summary
		LocalDateTime reportDate = LocalDateTime.now();
		this.reportDate = df.format(reportDate);
		phoneCount = phones.size();
		totalMinutes = reportService.computeTotalMinutes(usages.stream());
		totalData = new BigDecimal(reportService.computeTotalData(usages.stream()).toString()).setScale(2, RoundingMode.HALF_UP);
		averageMinutes = (float) (totalMinutes / phones.size());
		averageData = totalData
				.divide(new BigDecimal(new Integer(phones.size()).toString()), 2, RoundingMode.HALF_UP);
		
		// Initialize UsageDTO for all phones
		phones.stream().forEach(phone -> usageDetail.add(new UsageDTO(phone, usages)));
		
		// Squash header list
		for (UsageDTO ud : usageDetail) {
			ud.getMonthHeaders().stream()
				.filter(month -> !monthHeaders.contains(month))
				.forEach(month -> monthHeaders.add(month));
		}
				
	}
	
	public ReportService getReportService() {
		return reportService;
	}

	public List<UsageDTO> getUsageDetail() {
		return usageDetail;
	}

	public int getPhoneCount() {
		return phoneCount;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public List<Usage> getUsages() {
		return usages;
	}

	public String getReportDate() {
		return reportDate;
	}

	public Float getAverageMinutes() {
		return averageMinutes;
	}

	public BigDecimal getAverageData() {
		return averageData;
	}

	public Integer getTotalMinutes() {
		return totalMinutes;
	}

	public BigDecimal getTotalData() {
		return totalData;
	}

	public List<String> getMonthHeaders() {
		return monthHeaders;
	}
}
