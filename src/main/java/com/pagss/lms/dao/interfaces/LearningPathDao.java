package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.LearningPath;

public interface LearningPathDao {

	public void createLearningPath(LearningPath learningPath);
	
	public List<LearningPath> fetchLearningPaths(int courseId);
	
	public int checkIfCourseExamExist(LearningPath learningPath);
	
	public void updateSubOrderNo(List<LearningPath> learningPaths);
	
	public void deleteLearningPath(int learningPathId);
	
	public void deleteLearningPathByLearningPathSectionId(int learningPathSectionId);
	
	public LearningPath fetchLearningPath(int learningPathId);
}
