package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.LearningPathSection;

public interface LearningPathSectionDao {

	public void createLearningPathSection(LearningPathSection learningPathSection);
	
	public List<LearningPathSection> fetchLearningPathSections(int courseId);
	
	public void updateSectionOrderNo(List<LearningPathSection> LearningPathSections);
	
	public void updateLearningPathSection(LearningPathSection learningPathSection);
	
	public void deleteLearningPathSection(int learningPathSectionId);
}
