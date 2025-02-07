package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.ClassBlockSchedule;

import lombok.Getter;
import lombok.Setter;

public class ClassScheduleResponse {
	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private ClassBlockSchedule classSchedule;
	@Getter @Setter private List<ClassBlockSchedule> classSchedules;
	
	public ClassScheduleResponse (int status) {
		setStatus(status);
	}
	
	public ClassScheduleResponse (int status, int totalRecords) {
		setStatus(status);
		setTotalRecords(totalRecords);
	}
	
	public ClassScheduleResponse (int status, ClassBlockSchedule classchedule) {
		setStatus(status);
		setClassSchedule(classchedule);
	}

	public ClassScheduleResponse (int status, List<ClassBlockSchedule> classchedules) {
		setStatus(status);
		setClassSchedules(classchedules);
	}
}
