package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.GradingComponent;

public interface GradingComponentDao {

	public void createGradingComponent(GradingComponent gradingComponent);
	
	public List<GradingComponent> fetchGradingComponentPages(int pagesize, int pageNumber,int courseId);
	
	public int countTotalGradingComponents(int courseId);
	
	public void updateGradingComponent(GradingComponent gradingComponent);
	
	public int checkIfExistComponentExist(GradingComponent gradingComponent);
	
	public void deleteGradingComponent(int gradingComponentId);
	
	public GradingComponent fetchGradingComponent(int gradingComponentId);
	
	public List<GradingComponent> fetchGradingComponents(int courseId);
}
