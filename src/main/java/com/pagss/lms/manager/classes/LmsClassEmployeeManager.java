package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.constants.LmsClassEmployeeData;
import com.pagss.lms.constants.LmsClassInfoData;
import com.pagss.lms.constants.LmsStatus;
import com.pagss.lms.dao.interfaces.ClassBlockScheduleDao;
import com.pagss.lms.dao.interfaces.ClassEmployeeDao;
import com.pagss.lms.dao.interfaces.ClassSetScheduleDao;
import com.pagss.lms.domains.ClassBlockSchedule;
import com.pagss.lms.domains.ClassEmployee;
import com.pagss.lms.domains.ClassInfo;
import com.pagss.lms.domains.ClassSetSchedule;
import com.pagss.lms.manager.interfaces.ClassEmployeeManager;

@Component
public class LmsClassEmployeeManager implements ClassEmployeeManager {

	@Autowired
	private ClassEmployeeDao classEmployeeDao;
	@Autowired
	private ClassSetScheduleDao classSetScheduleDao;
	@Autowired
	private ClassBlockScheduleDao classBlockScheduleDao;
	
	@Override
	public void createClassEmployees(List<ClassEmployee> classEmployees) {
		this.classEmployeeDao.createClassEmployees(classEmployees);
	}

	@Override
	public List<ClassEmployee> fetchClassEmployeesByApprovalStatus(TableCommand tableCommand) {
		StringBuffer whereClause = new StringBuffer();
		StringBuffer orderByClause = new StringBuffer();
		if(tableCommand.getSearchByApprovalStatus()==LmsClassEmployeeData.ALL_APPROVAL_STATUS) {
			whereClause.append("AND ce.role IN (3) ");
			orderByClause.append("ORDER BY FIELD(approvalStatus,3,1,2,4) ");
		} else {
			whereClause.append("AND ce.approvalStatus = :approvalStatus AND ce.role IN (2,3) ");
			orderByClause.append("ORDER BY ei.fullName ASC ");
		}
		tableCommand.setPageNumber(tableCommand.getPageSize() * (tableCommand.getPageNumber()-1));
		tableCommand.setOrderClause(orderByClause.toString());
		tableCommand.setWhereClause(whereClause.toString());
		return this.classEmployeeDao.fetchClassEmployeesByApprovalStatus(tableCommand);
	}

	@Override
	public int countClassEmployeesByApprovalStatus(TableCommand tableCommand) {
		StringBuffer whereClause = new StringBuffer();
		if(tableCommand.getSearchByApprovalStatus()==LmsClassEmployeeData.ALL_APPROVAL_STATUS) {
			whereClause.append("AND ce.role=:role ");
		} else {
			whereClause.append("AND ce.approvalStatus = :approvalStatus AND ce.role=:role ");
		}
		return this.classEmployeeDao.countClassEmployeesByApprovalStatus(tableCommand);
	}
	
	@Override
	public void updateClassEmployee(ClassEmployee classEmployee) {
		this.classEmployeeDao.updateClassEmployee(classEmployee);
	}

	@Override
	public void updateClassInfoTrainingStatus(List<ClassEmployee> classEmployees) {
		this.classEmployeeDao.updateClassInfoTrainingStatus(classEmployees);
	}

	@Override
	public void updateApprovalStatus(ClassEmployee classEmployee) {
		this.classEmployeeDao.updateApprovalStatus(classEmployee);
	}
	
	@Override
	public int createClassEmployee(ClassInfo classInfo) {
		int totalConflictSchedules=0;
		if(classInfo.getScheduleType()==LmsClassInfoData.SET) {
			for(ClassSetSchedule classSetSchedule : classInfo.getClassSetSchedules()) {
				classSetSchedule.setEmployeeId(classInfo.getEmployeeId());
				totalConflictSchedules+=this.classSetScheduleDao.countClassSetSchedule(classSetSchedule);
			}
			for(ClassSetSchedule classSetScheduleForBlock : classInfo.getClassSetSchedules()) {
				ClassBlockSchedule classBlockSchedule = new ClassBlockSchedule();
				classBlockSchedule.setStartDate(classSetScheduleForBlock.getStartDate());
				classBlockSchedule.setEndDate(classSetScheduleForBlock.getEndDate());
				classBlockSchedule.setEmployeeId(classInfo.getEmployeeId());
				totalConflictSchedules+=this.classBlockScheduleDao.countClassBlockSchedule(classBlockSchedule);
			}
		} else if(classInfo.getScheduleType()==LmsClassInfoData.BLOCK) {
			ClassBlockSchedule classBlockSchedule=classInfo.getClassBlockSchedule();
			ClassSetSchedule classBlockScheduleForSet = new ClassSetSchedule();
			classBlockScheduleForSet.setEmployeeId(classInfo.getEmployeeId());
			classBlockScheduleForSet.setStartDate(classBlockSchedule.getStartDate());
			classBlockScheduleForSet.setEndDate(classBlockSchedule.getEndDate());
			totalConflictSchedules+=this.classSetScheduleDao.countClassSetSchedule(classBlockScheduleForSet);
			classBlockSchedule.setEmployeeId(classInfo.getEmployeeId());
			totalConflictSchedules+=this.classBlockScheduleDao.countClassBlockSchedule(classBlockSchedule);
		} else {
			//ToDo
		}
		if(totalConflictSchedules != 0) {
			return LmsStatus.CLASSSCHEDULE_HAS_CONFLICTS;
		} else {
			this.classEmployeeDao.createClassEmployee(classInfo.getClassEmployee());
			return LmsStatus.SUCCESS;
		}
	}
	
	@Override
	public int countClassEmployeeByEmployeeId(int classId,int employeeId) {
		return this.classEmployeeDao.countClassEmployeeByEmployeeId(classId, employeeId);
	}
	
	@Override
	public int countClassEmployeeWithOngoingClasses(int classId, int employeeId) {
		return this.classEmployeeDao.countClassEmployeeWithOngoingClasses(classId, employeeId);
	}
	
	@Override
	public void updateTrainingStatus(int classId, int employeeId, int trainingStatus) {
		this.classEmployeeDao.updateTrainingStatus(classId, employeeId, trainingStatus);
	}
}
