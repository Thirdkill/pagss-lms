package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.GradingComponentDao;
import com.pagss.lms.domains.GradingComponent;
import com.pagss.lms.manager.interfaces.GradingComponentManager;

@Component
public class LmsGradingComponentManager implements GradingComponentManager {

	@Autowired
	private GradingComponentDao gradingComponenytDao;
	
	@Override
	public void createGradingComponent(GradingComponent gradingComponent) {
		this.gradingComponenytDao.createGradingComponent(gradingComponent);
	}

	@Override
	public List<GradingComponent> fetchGradingComponentPages(int pageSize, int pageNumber, int courseId) {
		int calculatedPageNo = pageSize * (pageNumber - 1);
		return this.gradingComponenytDao.fetchGradingComponentPages(pageSize, calculatedPageNo, courseId);
	}
	
	@Override
	public int countTotalGradingComponents(int courseId) {
		return this.gradingComponenytDao.countTotalGradingComponents(courseId);
	}
	
	@Override
	public void updateGradingComponent(GradingComponent gradingComponent) {
		this.gradingComponenytDao.updateGradingComponent(gradingComponent);
	}

	@Override
	public int checkIfExistComponentExist(GradingComponent gradingComponent) {
		return this.gradingComponenytDao.checkIfExistComponentExist(gradingComponent);
	}

	@Override
	public void deleteGradingComponent(int gradingComponentId) {
		this.gradingComponenytDao.deleteGradingComponent(gradingComponentId);
	}

	@Override
	public GradingComponent fetchGradingComponent(int gradingComponentId) {
		return this.gradingComponenytDao.fetchGradingComponent(gradingComponentId);
	}
	
	@Override
	public List<GradingComponent> fetchGradingComponents(int courseId) {
		return this.gradingComponenytDao.fetchGradingComponents(courseId);
	}
}
