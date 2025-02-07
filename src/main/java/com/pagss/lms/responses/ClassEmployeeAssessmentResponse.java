package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.ClassEmployeeAssessment;

import lombok.Getter;
import lombok.Setter;

public class ClassEmployeeAssessmentResponse {

	@Getter @Setter private int status;
	@Getter @Setter private ClassEmployeeAssessment classEmployeeAssessment;
	@Getter @Setter private List<ClassEmployeeAssessment> classEmployeeAssessments;
	
	public ClassEmployeeAssessmentResponse(int status) {
		setStatus(status);
	}
	
	public ClassEmployeeAssessmentResponse(int status,ClassEmployeeAssessment classEmployeeAssessment) {
		setStatus(status);
		setClassEmployeeAssessment(classEmployeeAssessment);
	}
	
	public ClassEmployeeAssessmentResponse(int status,List<ClassEmployeeAssessment> classEmployeeAssessments) {
		setStatus(status);
		setClassEmployeeAssessments(classEmployeeAssessments);
	}
}
