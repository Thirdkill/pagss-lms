package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.EmployeeEvaluation;

import lombok.Getter;
import lombok.Setter;

public class EmployeeEvaluationResponse {
	@Getter @Setter private int status;
	@Getter @Setter private EmployeeEvaluation employeeevaluation;
	@Getter @Setter private List<EmployeeEvaluation> employeeevaluations;
	@Getter @Setter private int totalRecords;
	
	public EmployeeEvaluationResponse (int status) {
		setStatus(status);
	}
	
	public EmployeeEvaluationResponse (int status, int totalRecords) {
		setStatus(status);
		setTotalRecords(totalRecords);
	}
	
	public EmployeeEvaluationResponse (int status, EmployeeEvaluation employeeevaluation) {
		setStatus(status);
		setEmployeeevaluation(employeeevaluation);
	}
	
	public EmployeeEvaluationResponse (int status, List<EmployeeEvaluation> employeeevaluations) {
		setStatus(status);
		setEmployeeevaluations(employeeevaluations);
	}
	
	public EmployeeEvaluationResponse (int status, List<EmployeeEvaluation> employeeevaluations, int totalRecords) {
		setStatus(status);
		setEmployeeevaluations(employeeevaluations);
		setTotalRecords(totalRecords);
	}
}
