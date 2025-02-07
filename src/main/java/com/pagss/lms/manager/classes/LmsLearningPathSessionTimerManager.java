package com.pagss.lms.manager.classes;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.constants.LmsLearningPathSessionTimerData;
import com.pagss.lms.dao.interfaces.LearningPathSessionTimerDao;
import com.pagss.lms.domains.LearningPathSessionTimer;
import com.pagss.lms.manager.interfaces.LearningPathSessionTimerManager;

@Component
public class LmsLearningPathSessionTimerManager implements LearningPathSessionTimerManager {

	@Autowired
	private LearningPathSessionTimerDao learningPathSessionTimerDao;
	
	@Override
	public void createLearningPathSessionTimerWithTimeSpent(int classId, int employeeId, int learningPathId) {
		int noOfRecords=this.learningPathSessionTimerDao.countLearningPathSessionTimer(classId, employeeId, learningPathId);
		LearningPathSessionTimer learningPathSessionTimer = new LearningPathSessionTimer();
		learningPathSessionTimer.setClassId(classId);
		learningPathSessionTimer.setEmployeeId(employeeId);
		learningPathSessionTimer.setLearningPathId(learningPathId);
		learningPathSessionTimer.setTimeStarted(new SimpleDateFormat("HH:mm").format(new Date()));
		learningPathSessionTimer.setLastAccessed(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		if(noOfRecords==0) {
			learningPathSessionTimer.setSessionStatus(LmsLearningPathSessionTimerData.IN_PROGRESS);
			this.learningPathSessionTimerDao.createLearningPathSessionTimer(learningPathSessionTimer);
		} else {
			this.updateTimeSpent(classId, employeeId, learningPathId);
			LearningPathSessionTimer oldLearningPathSessionTimer=this.learningPathSessionTimerDao.fetchLearningPathSessionTimer(
				classId, employeeId, learningPathId);
			learningPathSessionTimer.setSessionStatus(oldLearningPathSessionTimer.getSessionStatus());
			learningPathSessionTimer.setTimeEnded(oldLearningPathSessionTimer.getTimeEnded());
			this.learningPathSessionTimerDao.updateLearningPathSessionTimer(learningPathSessionTimer);
		}
	}
	
	@Override
	public void updateTimeSpent(int classId, int employeeId, int learningPathId) {
		LearningPathSessionTimer oldLearningPathSessionTimer=this.learningPathSessionTimerDao.fetchLearningPathSessionTimer(
				classId, employeeId, learningPathId);
		LearningPathSessionTimer learningPathSessionTimer = new LearningPathSessionTimer();
		learningPathSessionTimer.setClassId(classId);
		learningPathSessionTimer.setEmployeeId(employeeId);
		learningPathSessionTimer.setLearningPathId(learningPathId);
		learningPathSessionTimer.setTimeSpent(oldLearningPathSessionTimer.getTimeSpent()+
				getTimeSpent(oldLearningPathSessionTimer.getTimeStarted()));
		this.learningPathSessionTimerDao.updateTimeSpent(learningPathSessionTimer);
	}
	
	@Override
	public void updateLearningPathSessionTimer(LearningPathSessionTimer learningPathSessionTimer) {
		this.learningPathSessionTimerDao.updateLearningPathSessionTimer(learningPathSessionTimer);
	}
	
	@Override
	public void createLearningPathSessionTimer(LearningPathSessionTimer learningPathSessionTimer) {
		int noOfRecords=this.learningPathSessionTimerDao.countLearningPathSessionTimer(learningPathSessionTimer.getClassId(),
			learningPathSessionTimer.getEmployeeId(),learningPathSessionTimer.getLearningPathId());
		if(noOfRecords==0) {
			this.learningPathSessionTimerDao.createLearningPathSessionTimer(learningPathSessionTimer);
		} else {
			this.learningPathSessionTimerDao.updateLearningPathSessionTimer(learningPathSessionTimer);
		}
	}
	
	@Override
	public int calculateTotalTimeSpent(int classId, int employeeId) {
		return this.learningPathSessionTimerDao.calculateTotalTimeSpent(classId, employeeId);
	}
	/**********************************************************************
	 * 			Private Methods
	 ***************************************************************/
	private int getTimeSpent(String timeStarted) {
		try {
			DateTime timeEnded = new DateTime();
			DateTime dtfTimeStart = DateTimeFormat.forPattern("HH:mm:ss").parseDateTime(timeStarted);
			Period timeSpent = new Period(dtfTimeStart,timeEnded);
			return timeSpent.getMinutes();
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**********************************************************************
	 * 			Private Methods
	 ***************************************************************/
}
