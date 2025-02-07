package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.ClassSetSchedule;

public interface ClassSetScheduleDao {

	public List<ClassSetSchedule> fetchClassSetScheduleByClassId(int classId);
	
	public int countClassSetSchedule(ClassSetSchedule classSetSchedule);
}
