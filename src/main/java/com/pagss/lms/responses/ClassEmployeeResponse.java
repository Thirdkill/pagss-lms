package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.ClassEmployee;

import lombok.Getter;
import lombok.Setter;

public class ClassEmployeeResponse {

	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private ClassEmployee classEmployee;
	@Getter @Setter private List<ClassEmployee> classEmployees;
	
	public ClassEmployeeResponse(int status) {
		setStatus(status);
	}
	
	public ClassEmployeeResponse(int status,ClassEmployee classEmployee) {
		setStatus(status);
		setClassEmployee(classEmployee);
	}
	
	public ClassEmployeeResponse(int status,int totalRecords,List<ClassEmployee> classEmployees) {
		setStatus(status);
		setTotalRecords(totalRecords);
		setClassEmployees(classEmployees);
	}
	
	public ClassEmployeeResponse(int status,List<ClassEmployee> classEmployees) {
		setStatus(status);
		setClassEmployees(classEmployees);
	}
	
	public ClassEmployeeResponse(int status,int totalRecords) {
		setStatus(status);
		setTotalRecords(totalRecords);
	}
}
