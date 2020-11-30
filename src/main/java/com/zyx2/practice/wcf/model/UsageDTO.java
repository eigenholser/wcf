package com.zyx2.practice.wcf.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class UsageDTO {
	private Long employeeId;
	private String employeeName;
	private String phoneModel;
	private LocalDate purchaseDate;
	private List<Integer> minuteUsage = new ArrayList<>();
	private List<Float> dataUsage = new ArrayList<>();
	private List<String> monthHeaders = new ArrayList<>();

	public UsageDTO(Phone phone, List<Usage> usages) {
		employeeId = phone.getEmployeeId();
		employeeName = phone.getEmployeeName();
		phoneModel = phone.getModel();
		purchaseDate = phone.getPurchaseDate();

		// Extract all usages for given phone (since we are assuming one phone per
		// employee)
		List<Usage> phoneUsage = usages.stream().filter(usage -> (usage.getEmployeeId().compareTo(employeeId) == 0))
				.collect(Collectors.toList());

		// Sort descending by date
		Collections.sort(phoneUsage, (c1, c2) -> {
			if (c1.getDate().isBefore(c2.getDate()))
				return 1;
			else
				return -1;
		});
		
		// Generate month name headers
		monthHeaders = phoneUsage.stream().map(pUsage -> {
			String month = pUsage.getDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.US).toString();
			int year = pUsage.getDate().getYear();
			return month + "_" + year;
		})
			.distinct()
			.collect(Collectors.toList());

		// Extract minutes/data combining possible non-distinct monthly objects.
		LocalDate referenceDate = null;
		for (Usage usage : phoneUsage) {
			Month currentMonth = usage.getDate().getMonth();

			if (referenceDate == null) {
				referenceDate = usage.getDate();
			}

			Month referenceMonth = referenceDate.getMonth();

			if (dataUsage.isEmpty() && minuteUsage.isEmpty()) {
				minuteUsage.add(0);
				dataUsage.add(0f);
			}

			if (currentMonth == referenceMonth) {
				Integer newMinute = minuteUsage.get(minuteUsage.size() - 1) + usage.getTotalMinutes();
				minuteUsage.set(minuteUsage.size() - 1, newMinute);

				Float newData = dataUsage.get(dataUsage.size() - 1) + usage.getTotalData();
				dataUsage.set(dataUsage.size() - 1, newData);
			} else {
				minuteUsage.add(usage.getTotalMinutes());
				dataUsage.add(usage.getTotalData());
			}

			referenceDate = usage.getDate();
		}
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public String getPhoneModel() {
		return phoneModel;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public List<Integer> getMinuteUsage() {
		return minuteUsage;
	}

	public List<Float> getDataUsage() {
		return dataUsage;
	}

	public List<String> getMonthHeaders() {
		return monthHeaders;
	}

	@Override
	public String toString() {
		return "UsageDTO [employeeId=" + employeeId + ", employeeName=" + employeeName + ", phoneModel=" + phoneModel
				+ ", purchaseDate=" + purchaseDate + ", minuteUsage=" + minuteUsage + ", dataUsage=" + dataUsage
				+ ", monthHeaders=" + monthHeaders + "]";
	}
}
