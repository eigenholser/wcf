package com.zyx2.practice.wcf.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.zyx2.practice.wcf.model.Phone;
import com.zyx2.practice.wcf.model.Usage;
import com.zyx2.practice.wcf.repository.PhoneRepository;
import com.zyx2.practice.wcf.repository.UsageRepository;

@ApplicationScoped
public class ReportService {

	@Inject
	UsageRepository usageRepository;

	@Inject
	PhoneRepository phoneRepository;

	private static final long REPORT_RANGE = 2L; // Not inclusive of current month
	
	/*
	 * Choosing to start reporting in the past by REPORT_RANGE months.
	 */
	public LocalDate startDateTime(LocalDate latestDate) { 
		return latestDate.minusMonths(REPORT_RANGE);
	}
	
	public Set<Long> usageEmployeeId(Stream<Usage> usages) {
		Set<Long> activeEmployeeIds = new HashSet<Long>();
		usages.map(usage -> activeEmployeeIds.add(usage.getEmployeeId()));
		return activeEmployeeIds;
	}
	
	public List<Phone> getActivePhones(Set<Long> activeEmployeeIds) {
		List<Phone> phones = new ArrayList<Phone>();
		for (Long employeeId : activeEmployeeIds) {
			Phone phone = phoneRepository.find(employeeId);
			if (phone != null) {
				phones.add(phone);
			}
		}
		return phones;
	}
	
	public Integer computeTotalMinutes(Stream<Usage> usages) {
		return usages.map(usage -> usage.getTotalMinutes()).reduce(0, (a, b) -> a + b);
	}
	
	public Float computeTotalData(Stream<Usage> usages) {
		return usages.map(usage -> usage.getTotalData()).reduce(0f, (a, b) -> a + b);
	}
}
