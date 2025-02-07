package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.LearningPathSessionTimer;

public interface LearningPathSessionTimerDao {

	public List<LearningPathSessionTimer> fetchLearningPathSessionTimers(int classId, int employeeId);
	
	public int countLearningPathSessionTimer(int classId,int employeeId,int learningPathId);
	
	public void createLearningPathSessionTimer(LearningPathSessionTimer learningPathSessionTimer);
	
	public void updateLearningPathSessionTimer(LearningPathSessionTimer learningPathSessionTimer);
	
	public LearningPathSessionTimer fetchLearningPathSessionTimer(int classId,int employeeId, int learningPathId);
	
	public void updateTimeSpent(LearningPathSessionTimer learningPathSessionTimer);
	
	public int calculateTotalTimeSpent(int classId,int employeeId);
}
