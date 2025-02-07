package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.domains.ClassInfo;
import com.pagss.lms.domains.ClassSeriesSchedule;
import com.pagss.lms.domains.ClassBlockSchedule;
import com.pagss.lms.domains.ClassSetSchedule;

public interface ClassInfoDao {

	public int fetchLatestClassId();

	public int checkClassCode(ClassInfo clInfo);

	public void addClassInfo(ClassInfo classinfo);
	
	public List<ClassInfo> fetchClassInfoPages(int pageNumber, int pageSize);
	
	public int countClassInfos();
	
	public List<ClassInfo> fetchClassInfos();
	
	public List<ClassInfo> searchClassInfo(TableCommand tableCommand);
	
	public int countSearchClassInfo(TableCommand tableCommand);
	
	public int countClassInfoByUserId(int userId);
	
	public int countInProgressClassInfos(TableCommand tableCommand);
	
	public int countUpcomingClassInfos(TableCommand tableCommand);
	
	public int countCompletedClassInfos(TableCommand tableCommand);
	
	public int countCancelledClassInfos(TableCommand tableCommand);
	
	public void deleteClassInfo(int classId);

	public void addBlockClassSchedules(List<ClassBlockSchedule> classSchedules);

	public void addSetClassSchedules(List<ClassSetSchedule> classSchedules);
	
	public void addSeriesClassSchedules(List<ClassSeriesSchedule> classSchedules);
	
	public ClassInfo fetchClassInfo(int classId);
	
	public void updateClassPhotoUrl(ClassInfo classInfo);

	public List<ClassInfo> fetchInProgressClassInfosByUserId(int userId);
	
	public List<ClassInfo> fetchAccessTrainingDurationClassInfos(int userId);
	
	public List<ClassInfo> fetchAccessIndefinitlyClassInfos(int userId);
	
	public List<ClassInfo> fetchAccessSpecifiedClassInfos(int userId);
	
	public List<ClassInfo> fetchCompletedClassInfosByUserId(int userId);
	
	public List<ClassInfo> fetchRecommendedClassInfos(int userId,int jobRoleId);
	
	public List<ClassInfo> fetchUserClassInfos(int userId);
	
	public List<ClassInfo> fetchClassInfoByIsSelfRegister(int isSelfRegister);

	public void updateClassEvaluationSettings(ClassInfo classInfo);

	public void updateClassInfoSettings(ClassInfo classInfo, TableCommand tableCommand);
}
