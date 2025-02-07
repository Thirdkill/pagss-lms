package com.pagss.lms.manager.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.constants.LmsClassInfoData;
import com.pagss.lms.dao.interfaces.ClassBlockScheduleDao;
import com.pagss.lms.dao.interfaces.ClassInfoDao;
import com.pagss.lms.dao.interfaces.ClassSetScheduleDao;
import com.pagss.lms.domains.ClassInfo;
import com.pagss.lms.domains.ClassSeriesSchedule;
import com.pagss.lms.domains.ClassBlockSchedule;
import com.pagss.lms.domains.ClassSetSchedule;
import com.pagss.lms.manager.interfaces.ClassInfoManager;

@Component
public class LmsClassInfoManager implements ClassInfoManager {
	
	@Autowired
	private ClassInfoDao classInfoDao;
	@Autowired
	private ClassBlockScheduleDao classBlockScheduleDao;
	@Autowired
	private ClassSetScheduleDao classSetScheduleDao;
	
	@Override
	public ClassInfo generateClassCode(String courseName) {
		ClassInfo clInfo = new ClassInfo();
		
		clInfo.setClassCode(generateCourseClassCode(courseName,this.classInfoDao.fetchLatestClassId()));
		clInfo.setClassId(this.classInfoDao.fetchLatestClassId());
		
		return clInfo;
	}
	
	private String generateCourseClassCode(String courseName, int classId) {
		String courseInitials = courseName.replaceAll("\\s","");
		String evalNo = Integer.toString(10000 + classId).substring(1);
		return courseInitials + "C" + evalNo;
	}

	@Override
	public int checkClassCode(String classCode) {
		ClassInfo clInfo = new ClassInfo();
		clInfo.setClassCode(classCode);
		return this.classInfoDao.checkClassCode(clInfo);
	}

	@Override
	public void addClassInfo(ClassInfo classinfo) {
		this.classInfoDao.addClassInfo(classinfo);
	}
	
	@Override
	public List<ClassInfo> fetchClassInfoPages(int pageNumber,int pageSize) {
		int calculatedPageNo = pageSize * (pageNumber - 1);
		return this.classInfoDao.fetchClassInfoPages(calculatedPageNo,pageSize);
	}
	
	@Override
	public int countClassInfos() {
		return this.classInfoDao.countClassInfos();
	}
	
	@Override
	public List<ClassInfo> fetchClassInfos() {
		return this.classInfoDao.fetchClassInfos();
	}

	@Override
	public List<ClassInfo> searchClassInfo(TableCommand tableCommand) {
		int calculatedPageNo = tableCommand.getPageSize() * (tableCommand.getPageNumber() - 1);
		StringBuffer whereClause = new StringBuffer();
		if(tableCommand.getDeliveryMethod() != 0) {
			whereClause.append("AND coi.deliveryMethod = :deliveryMethod ");
		}
		if(tableCommand.getSearchByEmployeeId() != 0) {
			whereClause.append("AND ei.employeeId = :employeeId ");
		}
		if(tableCommand.getCourseId() != 0) {
			whereClause.append("AND ci.courseId = :courseId ");
		}
		if(tableCommand.getKeyword() != "") {
			whereClause.append("AND ci.className like :keyword ");
		}
		if(tableCommand.getStartDate() != "" && tableCommand.getEndDate() != "") {
			whereClause.append("AND (CASE WHEN ci.scheduleType = 1 THEN ");
			whereClause.append("(SELECT cbs.startDate FROM classblockschedule cbs ");
			whereClause.append("WHERE cbs.classId = ci.classId) ");
			whereClause.append("WHEN ci.scheduleType = 2 THEN ");
			whereClause.append("(SELECT MIN(css.startDate) FROM classsetschedule css ");
			whereClause.append("WHERE css.classId = ci.classId) ");
			whereClause.append("END) BETWEEN :startDate AND :endDate AND ");
			whereClause.append("(CASE WHEN ci.scheduleType = 1 THEN ");
			whereClause.append("(SELECT cbs.endDate FROM classblockschedule cbs ");
			whereClause.append("WHERE cbs.classId = ci.classId) ");
			whereClause.append("WHEN ci.scheduleType = 2 THEN ");
			whereClause.append("(SELECT MAX(css.endDate) FROM classsetschedule css ");
			whereClause.append("WHERE css.classId = ci.classId) ");
			whereClause.append("END) BETWEEN :startDate AND :endDate ");
		}
		if(tableCommand.getSearchInProgressClasses() != 0) {
			whereClause.append("AND NOW() BETWEEN (CASE WHEN ci.scheduleType = 1 THEN ");
			whereClause.append("(SELECT cbs.startDate FROM classblockschedule cbs ");
			whereClause.append("WHERE cbs.classId = ci.classId) ");
			whereClause.append("WHEN ci.scheduleType = 2 THEN ");
			whereClause.append("(SELECT MIN(css.startDate) FROM classsetschedule css ");
			whereClause.append("WHERE css.classId = ci.classId) ");
			whereClause.append("END) AND ");
			whereClause.append("(CASE WHEN ci.scheduleType = 1 THEN ");
			whereClause.append("(SELECT cbs.endDate FROM classblockschedule cbs ");
			whereClause.append("WHERE cbs.classId = ci.classId) ");
			whereClause.append("WHEN ci.scheduleType = 2 THEN ");
			whereClause.append("(SELECT MAX(css.endDate) FROM classsetschedule css ");
			whereClause.append("WHERE css.classId = ci.classId) ");
			whereClause.append("END)");
		}
		if(tableCommand.getSearchUpcomingClasses() != 0) {
			whereClause.append("AND (CASE WHEN ci.scheduleType = 1 THEN ");
			whereClause.append("(SELECT cbs.startDate FROM classblockschedule cbs ");
			whereClause.append("WHERE cbs.classId = ci.classId) ");
			whereClause.append("WHEN ci.scheduleType = 2 THEN ");
			whereClause.append("(SELECT MIN(css.startDate) FROM classsetschedule css ");
			whereClause.append("WHERE css.classId = ci.classId) ");
			whereClause.append("END) > NOW() ");
		}
		if(tableCommand.getSearchCompletedClasses() != 0) {
			whereClause.append("AND (CASE WHEN ci.scheduleType = 1 THEN ");
			whereClause.append("(SELECT cbs.endDate FROM classblockschedule cbs ");
			whereClause.append("WHERE cbs.classId = ci.classId) ");
			whereClause.append("WHEN ci.scheduleType = 2 THEN ");
			whereClause.append("(SELECT MAX(css.endDate) FROM classsetschedule css ");
			whereClause.append("WHERE css.classId = ci.classId) ");
			whereClause.append("END) < NOW() ");
		}
		if(tableCommand.getSearchCancelledClasses() != 0) {
			whereClause.append("AND ci.completionStatus = 0 ");
		}
		if(tableCommand.getSearchInProgressClasses() != 0 || tableCommand.getSearchUpcomingClasses() != 0) {
			whereClause.append("AND ci.completionStatus > 0 ");
		}
		tableCommand.setPageNumber(calculatedPageNo);
		tableCommand.setWhereClause(whereClause.toString());
		return this.classInfoDao.searchClassInfo(tableCommand);
	}

	@Override
	public int countSearchClassInfo(TableCommand tableCommand) {
		StringBuffer whereClause = new StringBuffer();
		if(tableCommand.getDeliveryMethod() != 0) {
			whereClause.append("AND coi.deliveryMethod = :deliveryMethod ");
		}
		if(tableCommand.getSearchByEmployeeId() != 0) {
			whereClause.append("AND ei.employeeId = :employeeId ");
		}
		if(tableCommand.getCourseId() != 0) {
			whereClause.append("AND ci.courseId = :courseId ");
		}
		if(tableCommand.getKeyword() != "") {
			whereClause.append("AND ci.className like :keyword ");
		}
		if(tableCommand.getStartDate() != "" && tableCommand.getEndDate() != "") {
			whereClause.append("AND (CASE WHEN ci.scheduleType = 1 THEN ");
			whereClause.append("(SELECT cbs.startDate FROM classblockschedule cbs ");
			whereClause.append("WHERE cbs.classId = ci.classId) ");
			whereClause.append("WHEN ci.scheduleType = 2 THEN ");
			whereClause.append("(SELECT MIN(css.startDate) FROM classsetschedule css ");
			whereClause.append("WHERE css.classId = ci.classId) ");
			whereClause.append("END) BETWEEN :startDate AND :endDate AND ");
			whereClause.append("(CASE WHEN ci.scheduleType = 1 THEN ");
			whereClause.append("(SELECT cbs.endDate FROM classblockschedule cbs ");
			whereClause.append("WHERE cbs.classId = ci.classId) ");
			whereClause.append("WHEN ci.scheduleType = 2 THEN ");
			whereClause.append("(SELECT MAX(css.endDate) FROM classsetschedule css ");
			whereClause.append("WHERE css.classId = ci.classId) ");
			whereClause.append("END) BETWEEN :startDate AND :endDate ");
		}
		if(tableCommand.getSearchInProgressClasses() != 0) {
			whereClause.append("AND NOW() BETWEEN (CASE WHEN ci.scheduleType = 1 THEN ");
			whereClause.append("(SELECT cbs.startDate FROM classblockschedule cbs ");
			whereClause.append("WHERE cbs.classId = ci.classId) ");
			whereClause.append("WHEN ci.scheduleType = 2 THEN ");
			whereClause.append("(SELECT MIN(css.startDate) FROM classsetschedule css ");
			whereClause.append("WHERE css.classId = ci.classId) ");
			whereClause.append("END) AND ");
			whereClause.append("(CASE WHEN ci.scheduleType = 1 THEN ");
			whereClause.append("(SELECT cbs.endDate FROM classblockschedule cbs ");
			whereClause.append("WHERE cbs.classId = ci.classId) ");
			whereClause.append("WHEN ci.scheduleType = 2 THEN ");
			whereClause.append("(SELECT MAX(css.endDate) FROM classsetschedule css ");
			whereClause.append("WHERE css.classId = ci.classId) ");
			whereClause.append("END)");
		}
		if(tableCommand.getSearchUpcomingClasses() != 0) {
			whereClause.append("AND (CASE WHEN ci.scheduleType = 1 THEN ");
			whereClause.append("(SELECT cbs.startDate FROM classblockschedule cbs ");
			whereClause.append("WHERE cbs.classId = ci.classId) ");
			whereClause.append("WHEN ci.scheduleType = 2 THEN ");
			whereClause.append("(SELECT MIN(css.startDate) FROM classsetschedule css ");
			whereClause.append("WHERE css.classId = ci.classId) ");
			whereClause.append("END) > NOW() ");
		}
		if(tableCommand.getSearchCompletedClasses() != 0) {
			whereClause.append("AND (CASE WHEN ci.scheduleType = 1 THEN ");
			whereClause.append("(SELECT cbs.endDate FROM classblockschedule cbs ");
			whereClause.append("WHERE cbs.classId = ci.classId) ");
			whereClause.append("WHEN ci.scheduleType = 2 THEN ");
			whereClause.append("(SELECT MAX(css.endDate) FROM classsetschedule css ");
			whereClause.append("WHERE css.classId = ci.classId) ");
			whereClause.append("END) < NOW() ");
		}
		if(tableCommand.getSearchCancelledClasses() != 0) {
			whereClause.append("AND ci.completionStatus = 0 ");
		}
		if(tableCommand.getSearchInProgressClasses() != 0 || tableCommand.getSearchUpcomingClasses() != 0) {
			whereClause.append("AND ci.completionStatus > 0 ");
		}
		tableCommand.setWhereClause(whereClause.toString());
		return this.classInfoDao.countSearchClassInfo(tableCommand);
	}

	@Override
	public int countClassInfoByUserId(int userId) {
		return this.classInfoDao.countClassInfoByUserId(userId);
	}

	@Override
	public int countInProgressClassInfos(int userId) {
		StringBuffer whereClause = new StringBuffer();
		if(userId != 0) {
			whereClause.append("AND u.userId = :userId ");
		}
		TableCommand tblCommand = new TableCommand();
		tblCommand.setWhereClause(whereClause.toString());
		tblCommand.setUserId(userId);
		return this.classInfoDao.countInProgressClassInfos(tblCommand);
	}

	@Override
	public int countUpcomingClassInfos(int userId) {
		StringBuffer whereClause = new StringBuffer();
		if(userId != 0) {
			whereClause.append("AND u.userId = :userId ");
		}
		TableCommand tblCommand = new TableCommand();
		tblCommand.setWhereClause(whereClause.toString());
		tblCommand.setUserId(userId);
		return this.classInfoDao.countUpcomingClassInfos(tblCommand);
	}

	@Override
	public int countCompletedClassInfos(int userId) {
		StringBuffer whereClause = new StringBuffer();
		if(userId != 0) {
			whereClause.append("AND u.userId = :userId ");
		}
		TableCommand tblCommand = new TableCommand();
		tblCommand.setWhereClause(whereClause.toString());
		tblCommand.setUserId(userId);
		return this.classInfoDao.countCompletedClassInfos(tblCommand);
	}
	
	@Override
	public int countCancelledClassInfos(int userId) {
		StringBuffer whereClause = new StringBuffer();
		if(userId != 0) {
			whereClause.append("AND u.userId = :userId ");
		}
		TableCommand tblCommand = new TableCommand();
		tblCommand.setWhereClause(whereClause.toString());
		tblCommand.setUserId(userId);
		return this.classInfoDao.countCancelledClassInfos(tblCommand);
	}

	@Override
	public void deleteClassInfo(int classId) {
		this.classInfoDao.deleteClassInfo(classId);
	}

	@Override

	public void updateBlockClassSchedules(List<ClassBlockSchedule> classSchedules, int classId) {
		this.classInfoDao.addBlockClassSchedules(classSchedules);
	}

	@Override
	public void updateSetClassSchedules(List<ClassSetSchedule> classSchedules, int classId) {
		this.classInfoDao.addSetClassSchedules(classSchedules);
	}

	@Override
	public ClassInfo fetchClassInfo(int classId) {
		return this.classInfoDao.fetchClassInfo(classId);
	}

	@Override
	public void updateClassPhotoUrl(ClassInfo classInfo) {
		this.classInfoDao.updateClassPhotoUrl(classInfo);
	}

	@Override
	public void updateSeriesClassSchedules(List<ClassSeriesSchedule> classSchedules, int classId) {
		this.classInfoDao.addSeriesClassSchedules(classSchedules);
	}

	@Override
	public List<ClassInfo> fetchInProgressClassInfosByUserId(int userId) {
		return this.classInfoDao.fetchInProgressClassInfosByUserId(userId);
	}

	@Override
	public List<ClassInfo> fetchClassInfoWithViewRestriction(int userId) {
		List<ClassInfo> accessibleByTrainingDuration=this.classInfoDao.fetchAccessTrainingDurationClassInfos(userId);
		List<ClassInfo> accessibleBySpecifiedDate=this.classInfoDao.fetchAccessSpecifiedClassInfos(userId);
		List<ClassInfo> accessibleIndefinitely=this.classInfoDao.fetchAccessIndefinitlyClassInfos(userId);
		List<ClassInfo> tempClassInfos=Stream.concat(accessibleByTrainingDuration.stream(),accessibleBySpecifiedDate.stream())
				.collect(Collectors.toList());
		List<ClassInfo> finalList=Stream.concat(tempClassInfos.stream(),accessibleIndefinitely.stream())
				.collect(Collectors.toList());
		return finalList;
	}
	
	@Override
	public List<ClassInfo> fetchCompletedClassInfosByUserId(int userId) {
		return this.classInfoDao.fetchCompletedClassInfosByUserId(userId);
	}

	@Override
	public List<ClassInfo> fetchRecommendedClassInfos(int userId, int jobRoleId) {
		List<ClassInfo> classInfos=this.classInfoDao.fetchRecommendedClassInfos(userId, jobRoleId);
		return filterUserClassInfo(classInfos,userId);
	}

	@Override
	public List<ClassInfo> fetchClassInfoByIsSelfRegister(int userId,int isSelfRegister) {
		List<ClassInfo> classInfos=this.classInfoDao.fetchClassInfoByIsSelfRegister(isSelfRegister);
		return classInfos;
	}

	@Override
	public ClassInfo fetchClassInfoSchedule(int classId) {
		ClassInfo classInfo = this.classInfoDao.fetchClassInfo(classId);
		if(classInfo.getScheduleType()==LmsClassInfoData.BLOCK) {
			classInfo.setClassBlockSchedule(this.classBlockScheduleDao.fetchClassBlockScheduleByClassId(classId));
		} else if(classInfo.getScheduleType()==LmsClassInfoData.SET) {
			classInfo.setClassSetSchedules(this.classSetScheduleDao.fetchClassSetScheduleByClassId(classId));
		}
		return classInfo;
	}
	
	@Override
	public void updateClassEvaluationSettings(ClassInfo classInfo) {
		this.classInfoDao.updateClassEvaluationSettings(classInfo);
	}
	
	@Override
	public void updateClassInfoSettings(ClassInfo classInfo) {
		String customTemplateCommand = "";
		String selfRegisterDateCommand = "";
		String viewRestrictionDateCommand = "";
		if(classInfo.getCertificateTemplateType() == LmsClassInfoData.CERTIFICATETYPE_CUSTOMTEMPLATE) {
			customTemplateCommand = ", certificateUrl = :certificateUrl";
		}
		
		if(classInfo.getSelfRegisterType() == LmsClassInfoData.SELFREGISTERTYPE_SPECIFIEDPERIOD) {
			selfRegisterDateCommand = ", selfRegisterStartDate = :selfRegisterStartDate, selfRegisterEndDate = :selfRegisterEndDate";
		}
		
		if(classInfo.getViewRestrictionType() == LmsClassInfoData.ACCESSIBLE_BY_SPECIFIED_DATE) {
			viewRestrictionDateCommand = ", accessStartDate = :accessStartDate, accessEndDate = :accessEndDate";
		}
		
		TableCommand tableCommand = new TableCommand();
		tableCommand.setUpdateSetClause(customTemplateCommand+""+selfRegisterDateCommand+""+viewRestrictionDateCommand+" ");
		this.classInfoDao.updateClassInfoSettings(classInfo,tableCommand);
	}
	
	/*******************************************************************************
	 * START: Private Classes														*
	 * ******************************************************************************/
	private List<ClassInfo> filterUserClassInfo(List<ClassInfo> classInfos,int userId) {
		List<ClassInfo> tempClassInfos = new ArrayList<>();
		List<ClassInfo> userClassInfos = this.classInfoDao.fetchUserClassInfos(userId);
		for(ClassInfo classInfo:classInfos) {
			if(!checkIfUserHasClassInfo(userClassInfos,classInfo.getClassId())) {
				tempClassInfos.add(classInfo);
			}
		}
		return tempClassInfos;
	}
	
	private Boolean checkIfUserHasClassInfo(List<ClassInfo> userClassInfos,int classId) {
		Boolean result = false;
		for(ClassInfo classInfo: userClassInfos) {
			if(classId==classInfo.getClassId()) {
				result=true;
			}
		}
		return result;
	}
	/*******************************************************************************
	 * END: Private Classes														*
	 * ******************************************************************************/
}
