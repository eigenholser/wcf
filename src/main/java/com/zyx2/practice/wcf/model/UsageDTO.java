package com.zyx2.practice.wcf.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	private List<BigDecimal> dataUsage = new ArrayList<>();
	private List<String> monthHeaders = new ArrayList<>();

	public UsageDTO(Phone phone, List<Usage> usages) {
		employeeId = phone.getEmployeeId();
		employeeName = phone.getEmployeeName();
		phoneModel = phone.getModel();
		purchaseDate = phone.getPurchaseDate();

		// Extract all usages for given phone (since we are assuming one phone per
		// employee)
		List<Usage> phoneUsage = usages.stream() //
				.filter(usage -> (usage.getEmployeeId().compareTo(employeeId) == 0)) //
				.collect(Collectors.toList());

		// Sort descending by date
		Collections.sort(phoneUsage, (a, b) -> {
			if (a.getDate().isBefore(b.getDate()))
				return 1;
			else
				return -1;
		});

		// Generate month name headers
		monthHeaders = phoneUsage.stream().map(pUsage -> {
			String month = pUsage.getDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.US).toString();
			int year = pUsage.getDate().getYear();
			return month + "_" + year;
		}) //
				.distinct() //
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
				dataUsage.add(BigDecimal.ZERO.setScale((2)));
			}

			if (currentMonth == referenceMonth) {
				Integer newMinute = minuteUsage.get(minuteUsage.size() - 1) + usage.getTotalMinutes();
				minuteUsage.set(minuteUsage.size() - 1, newMinute);

				BigDecimal newData = dataUsage.get(dataUsage.size() - 1)
						.add(new BigDecimal(usage.getTotalData().toString()));
				newData.setScale(2, RoundingMode.HALF_UP);
				dataUsage.set(dataUsage.size() - 1, newData);
			} else {
				minuteUsage.add(usage.getTotalMinutes());
				dataUsage.add(new BigDecimal(usage.getTotalData().toString()).setScale(2, RoundingMode.HALF_UP));
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

	public List<BigDecimal> getDataUsage() {
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
