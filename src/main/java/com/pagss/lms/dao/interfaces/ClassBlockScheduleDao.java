package com.pagss.lms.dao.interfaces;

import com.pagss.lms.domains.ClassBlockSchedule;

public interface ClassBlockScheduleDao {
	
	public ClassBlockSchedule fetchClassBlockScheduleByClassId(int classId);
	
	public int countClassBlockSchedule(ClassBlockSchedule classBlockSchedule);
}
