package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.ClassEmployeeAssessmentDao;
import com.pagss.lms.domains.ClassEmployeeAssessment;
import com.pagss.lms.manager.interfaces.ClassEmployeeAssessmentManager;

@Component
public class LmsClassEmployeeAssessmentManager implements ClassEmployeeAssessmentManager {

	@Autowired
	private ClassEmployeeAssessmentDao classEmployeeAssessmentDao;
	
	@Override
	public int countEmployeeGradingComponent(ClassEmployeeAssessment classEmployeeAssessment) {
		return this.classEmployeeAssessmentDao.countEmployeeGradingComponent(classEmployeeAssessment);
	}

	@Override
	public void createClassEmployeeAssessment(ClassEmployeeAssessment classEmployeeAssessment) {
		this.classEmployeeAssessmentDao.createClassEmployeeAssessment(classEmployeeAssessment);
	}

	@Override
	public void updateClassEmployeeAssessment(ClassEmployeeAssessment classEmployeeAssessment) {
		this.classEmployeeAssessmentDao.updateClassEmployeeAssessment(classEmployeeAssessment);
	}

	@Override
	public List<ClassEmployeeAssessment> fetchClassEmployeeAssessment(int classId) {
		return this.classEmployeeAssessmentDao.fetchClassEmployeeAssessment(classId);
	}
}
