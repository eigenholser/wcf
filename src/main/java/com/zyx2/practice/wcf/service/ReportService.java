package com.zyx2.practice.wcf.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
	public LocalDate startDate(LocalDate endDate) {
		return endDate.minusMonths(REPORT_RANGE).withDayOfMonth(1);
	}

	public Set<Long> usageEmployeeId(Stream<Usage> usages) {
		return usages //
				.map(usage -> usage.getEmployeeId()) //
				.collect(Collectors.toSet());
	}

	public List<Phone> getActivePhones(Set<Long> activeEmployeeIds) {
		List<Phone> phones = new ArrayList<Phone>();
		for (Long employeeId : activeEmployeeIds) {
			Phone phone = phoneRepository.find(employeeId);
			if (phone != null) {
				phones.add(phone);
			}
		}
		
		Collections.sort(phones, (a, b) -> {
			if (a.getEmployeeId() > b.getEmployeeId())
				return 1;
			else
				return -1;
		});
		return phones;
	}

	public Integer computeTotalMinutes(Stream<Usage> usages) {
		return usages //
				.map(usage -> usage.getTotalMinutes()) //
				.reduce(0, (a, b) -> a + b);
	}

	public Float computeTotalData(Stream<Usage> usages) {
		return usages  //
				.map(usage -> usage.getTotalData()) //
				.reduce(0f, (a, b) -> a + b);
	}
}
