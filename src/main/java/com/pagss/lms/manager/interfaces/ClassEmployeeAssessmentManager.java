package com.pagss.lms.manager.interfaces;

import java.util.List;

import com.pagss.lms.domains.ClassEmployeeAssessment;

public interface ClassEmployeeAssessmentManager {
	
	public int countEmployeeGradingComponent(ClassEmployeeAssessment classEmployeeAssessment);
	
	public void createClassEmployeeAssessment(ClassEmployeeAssessment classEmployeeAssessment);
	
	public void updateClassEmployeeAssessment(ClassEmployeeAssessment classEmployeeAssessment);
	
	public List<ClassEmployeeAssessment> fetchClassEmployeeAssessment(int classId);
}
