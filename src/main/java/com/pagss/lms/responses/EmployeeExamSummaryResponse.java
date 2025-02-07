package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.EmployeeExamSummary;

import lombok.Getter;
import lombok.Setter;

public class EmployeeExamSummaryResponse {
	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private EmployeeExamSummary employeeExamSummary;
	@Getter @Setter private List<EmployeeExamSummary> employeeExamSummaries;
	
	public EmployeeExamSummaryResponse (int status) {
		setStatus(status);
	}
	
	public EmployeeExamSummaryResponse (int status, int totalRecords) {
		setStatus(status);
		setTotalRecords(totalRecords);
	}
	
	public EmployeeExamSummaryResponse (int status, EmployeeExamSummary employeeExamSummary) {
		setStatus(status);
		setEmployeeExamSummary(employeeExamSummary);
	}

	public EmployeeExamSummaryResponse (int status, List<EmployeeExamSummary> employeeExamSummaries) {
		setStatus(status);
		setEmployeeExamSummaries(employeeExamSummaries);
	}
	public EmployeeExamSummaryResponse (int status, int totalRecords, List<EmployeeExamSummary> employeeExamSummaries) {
		setStatus(status);
		setTotalRecords(totalRecords);
		setEmployeeExamSummaries(employeeExamSummaries);
	}
}
