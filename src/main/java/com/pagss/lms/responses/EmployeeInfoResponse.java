package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.EmployeeInfo;

import lombok.Getter;
import lombok.Setter;

public class EmployeeInfoResponse {
	
	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private EmployeeInfo employeeInfo;
	@Getter @Setter private List<EmployeeInfo> employeeInfos;
	
	public EmployeeInfoResponse(int status) {
		setStatus(status);
	}
	
	public EmployeeInfoResponse(int status, EmployeeInfo employeeInfo) {
		setStatus(status);
		setEmployeeInfo(employeeInfo);
	}
	
	public EmployeeInfoResponse(int status, List<EmployeeInfo> employeeInfos) {
		setStatus(status);
		setEmployeeInfos(employeeInfos);
	}
	
	public EmployeeInfoResponse(int status, List<EmployeeInfo> employeeInfos, int noEmployeeInfo) {
		setStatus(status);
		setEmployeeInfos(employeeInfos);
	}

	public EmployeeInfoResponse(int status, int totalRecords) {
		setStatus(status);
		setTotalRecords(totalRecords);
	}
	
	public EmployeeInfoResponse(int status, int totalRecords,List<EmployeeInfo> employeeInfos) {
		setStatus(status);
		setEmployeeInfos(employeeInfos);
		setTotalRecords(totalRecords);
	}
}
