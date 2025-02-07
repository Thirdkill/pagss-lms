package com.pagss.lms.manager.classes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.LearningPathDao;
import com.pagss.lms.dao.interfaces.LearningPathSessionTimerDao;
import com.pagss.lms.domains.LearningPath;
import com.pagss.lms.domains.LearningPathSessionTimer;
import com.pagss.lms.manager.interfaces.LearningPathManager;
import com.pagss.lms.utilities.FileServerOperator;

@Component
public class LmsLearningPathManager implements LearningPathManager {

	@Autowired
	private LearningPathDao learningPathDao;
	@Autowired
	private LearningPathSessionTimerDao learningPathSessionTimerDao;
	@Autowired
	private FileServerOperator fileServerOperator;
	
	@Override
	public void createLearningPath(LearningPath learningPath) {
		this.learningPathDao.createLearningPath(learningPath);
	}
	
	@Override
	public List<LearningPath> fetchLearningPaths(int courseId) {
		return this.learningPathDao.fetchLearningPaths(courseId);
	}

	@Override
	public int checkIfCourseExamExist(LearningPath learningPath) {
		return this.learningPathDao.checkIfCourseExamExist(learningPath);
	}
	
	@Override
	public void updateSubOrderNo(List<LearningPath> learningPaths) {
		this.learningPathDao.updateSubOrderNo(learningPaths);
	}

	@Override
	public void deleteLearningPath(int learningPathId) {
		this.learningPathDao.deleteLearningPath(learningPathId);
	}
	
	@Override
	public List<LearningPath> fetchLearningPathWithTimeSpent(int classId,int employeeId,int courseId) {
		List<LearningPath> learningPaths=this.learningPathDao.fetchLearningPaths(courseId);
		List<LearningPathSessionTimer> learningPathSessionTimers=this.learningPathSessionTimerDao.fetchLearningPathSessionTimers(
				classId, employeeId);
		return setTimeSpentDetails(learningPaths,learningPathSessionTimers);
	}
	
	@Override
	public LearningPath fetchLearningPath(int learningPathId) {
		LearningPath learningPath=this.learningPathDao.fetchLearningPath(learningPathId);
		learningPath.setContentUrl(fileServerOperator.generatePresignedUrl(learningPath.getContentUrl()));
		return learningPath;
	}
	/***************************************************
	 * 				Private	Methods					***
	 *************************************************/
	private List<LearningPath> setTimeSpentDetails(List<LearningPath> learningPaths,
			List<LearningPathSessionTimer> learningPathSessionTimers) {
		List<LearningPath> tempLearningPaths=new ArrayList<>();
		for(LearningPath learningPath:learningPaths) {
			for(LearningPathSessionTimer learningPathSessionTimer:learningPathSessionTimers) {
				if(learningPathSessionTimer.getLearningPathId()==learningPath.getLearningPathId()) {
					learningPath.setLastAccessed(learningPathSessionTimer.getLastAccessed());
					learningPath.setTimeSpent(learningPathSessionTimer.getTimeSpent());
					learningPath.setSessionStatus(learningPathSessionTimer.getSessionStatus());
				}
			}
			tempLearningPaths.add(learningPath);
		}
		return tempLearningPaths;
	}
	/***************************************************
	 * 				Private	Methods					***
	 *************************************************/
}
