package com.pagss.lms.dao.interfaces;


import com.pagss.lms.domains.ClassSessionTimer;

public interface ClassSessionTimerDao {

	public int countClassSessionTimerByEmployeeId(int classId,int employeeId);
	
	public void createClassSessionTimer(ClassSessionTimer classSessionTimer);
	
	public void updateClassSessionTimer(ClassSessionTimer classSessionTimer);
	
	public ClassSessionTimer fetchClassSessionTimer(int classId, int employeeId);
	
	public void updateTimeSpent(ClassSessionTimer classSessionTimer);
}
