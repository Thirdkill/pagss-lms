package com.pagss.lms.manager.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.constants.LmsClassSessionTimerData;
import com.pagss.lms.dao.interfaces.ClassInfoDao;
import com.pagss.lms.dao.interfaces.ClassSessionTimerDao;
import com.pagss.lms.domains.ClassInfo;
import com.pagss.lms.domains.ClassSessionTimer;
import com.pagss.lms.manager.interfaces.ClassSessionTimerManager;

@Component
public class LmsClassSessionTimerManager implements ClassSessionTimerManager {

	@Autowired
	private ClassSessionTimerDao classSessionTimerDao;
	@Autowired
	private ClassInfoDao classInfoDao;
	
	@Override
	public int countClassSessionTimerByEmployeeId(int classId, int employeeId) {
		return this.classSessionTimerDao.countClassSessionTimerByEmployeeId(classId, employeeId);
	}

	@Override
	public void createClassSessionTimer(int classId, int employeeId) {
		String timeStarted = new SimpleDateFormat("HH:mm").format(new Date());
		ClassInfo classInfo = classInfoDao.fetchClassInfo(classId);
		ClassSessionTimer classSessionTimer = new ClassSessionTimer();
		classSessionTimer.setClassId(classId);
		classSessionTimer.setEmployeeId(employeeId);
		classSessionTimer.setTimeStarted(timeStarted);
		if(classSessionTimerDao.countClassSessionTimerByEmployeeId(classId, employeeId) > 0) {
			int timeSpent = this.updateTimeSpent(classId, employeeId);
			ClassSessionTimer oldClassSessionTimer=this.fetchClassSessionTimer(classId, employeeId);
			int timeDurationLeft = classInfo.getClassDuration()-oldClassSessionTimer.getTimeSpent();
			classSessionTimer.setSessionStatus((timeSpent<classInfo.getClassDuration()?
					oldClassSessionTimer.getSessionStatus():LmsClassSessionTimerData.FINISHED));
			classSessionTimer.setTimeEnded(getTimeEnded(timeStarted, (timeDurationLeft>0)?timeDurationLeft:0));
			classSessionTimerDao.updateClassSessionTimer(classSessionTimer);
		} else {
			classSessionTimer.setSessionStatus(LmsClassSessionTimerData.IN_PROGRESS);
			classSessionTimer.setTimeEnded(getTimeEnded(timeStarted, classInfo.getClassDuration()));
			classSessionTimerDao.createClassSessionTimer(classSessionTimer);
		}
	}
	
	@Override
	public ClassSessionTimer fetchClassSessionTimer(int classId, int employeeId) {
		return this.classSessionTimerDao.fetchClassSessionTimer(classId, employeeId);
	}

	@Override
	public int updateTimeSpent(int classId,int employeeId) {
		ClassSessionTimer classSessionTimer=this.classSessionTimerDao.fetchClassSessionTimer(classId, employeeId);
		int timeSpent = getTimeSpent(classSessionTimer.getTimeStarted())+classSessionTimer.getTimeSpent();
		classSessionTimer.setTimeSpent(timeSpent);
		classSessionTimer.setClassId(classId);
		classSessionTimer.setEmployeeId(employeeId);
		this.classSessionTimerDao.updateTimeSpent(classSessionTimer);
		return timeSpent;
	}
	
	@Override
	public void updateClassSessionTimer(ClassSessionTimer classSessionTimer) {
		this.classSessionTimerDao.updateClassSessionTimer(classSessionTimer);
	}
	/**********************************************************************
	 * 			Private Methods
	 ***************************************************************/
	private String getTimeEnded(String timeStarted,int classDuration) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new SimpleDateFormat("HH:mm").parse(timeStarted));
			calendar.add(Calendar.MINUTE, classDuration);
			return new SimpleDateFormat("HH:mm").format(calendar.getTime());
		} catch(ParseException pe) {
			pe.printStackTrace();
			return null;
		}
	}
	
	private int getTimeSpent(String timeStarted) {
		try {
			DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm:ss");
			DateTime timeStartedValue = dtf.parseDateTime(timeStarted);
			DateTime timeEnded = new DateTime();
			Period timeSpent = new Period(timeStartedValue,timeEnded);
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
