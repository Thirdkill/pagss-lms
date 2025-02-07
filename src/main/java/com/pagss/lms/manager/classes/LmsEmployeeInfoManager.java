package com.pagss.lms.manager.classes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.dao.interfaces.EmployeeInfoDao;
import com.pagss.lms.domains.EmployeeInfo;
import com.pagss.lms.manager.interfaces.EmployeeInfoManager;

@Component
public class LmsEmployeeInfoManager implements EmployeeInfoManager{
	
	@Autowired
	private EmployeeInfoDao employeeInfoDao;

	@Override
	public EmployeeInfo fetchEmployeeInfo(EmployeeInfo empinfo) {
		return this.employeeInfoDao.fetchEmployeeInfo(empinfo);
	}

	@Override
	public int countEmployeeCode(EmployeeInfo empinfo) {
		return this.employeeInfoDao.countEmployeeCode(empinfo);
	}
	
	@Override
	public List<EmployeeInfo> fetchTrainers() {
		return this.employeeInfoDao.fetchTrainers();
	}
	
	@Override
	public EmployeeInfo fetchEmployeeInfoByUserId(int userId) {
		return this.employeeInfoDao.fetchEmployeeInfoByUserId(userId);
	}
	
	@Override
	public List<EmployeeInfo> fetchTrainersByStatus(int status) {
		return this.employeeInfoDao.fetchTrainersByStatus(status);
	}

	@Override
	public List<EmployeeInfo> fetchEmployeeInfoWithoutClass(int classId,int pageNumber,int pageSize) {
		int calculatedPageNo = pageSize * (pageNumber - 1);
		return this.employeeInfoDao.fetchEmployeeInfoWithoutClass(classId, calculatedPageNo, pageSize);
	}

	@Override
	public int countEmployeeInfoWithoutClass(int classId) {
		return this.employeeInfoDao.countEmployeeInfoWithoutClass(classId);
	}

	@Override
	public List<EmployeeInfo> searchEmployeeInfoWithoutClass(TableCommand tableCommand) {
		tableCommand.setPageNumber(tableCommand.getPageSize() * (tableCommand.getPageNumber() - 1));
		tableCommand.setWhereClause(getWhereClause(tableCommand));
		List<EmployeeInfo> employeeInfos = new ArrayList<>();
		if(tableCommand.getJobRoleId() != 0) {
			employeeInfos = this.employeeInfoDao.searchEmployeeInfoByJobRoleId(tableCommand);
		} else if(tableCommand.getUserGroupId() != 0){
			employeeInfos = this.employeeInfoDao.searchEmployeeInfoByUserGroupId(tableCommand);
		} else {
			employeeInfos = this.employeeInfoDao.searchEmployeeInfo(tableCommand);
		}
		return employeeInfos;
	}

	@Override
	public int countEmployeeInfoWithoutClass(TableCommand tableCommand) {
		tableCommand.setWhereClause(getWhereClause(tableCommand));
		int countEmployeeInfos = 0;
		if(tableCommand.getJobRoleId() != 0) {
			countEmployeeInfos = this.employeeInfoDao.countSearchEmployeeInfoByJobRoleId(tableCommand);
		} else if(tableCommand.getUserGroupId() != 0){
			countEmployeeInfos = this.employeeInfoDao.countSearchEmployeeInfoByUserGroupId(tableCommand);
		} else {
			countEmployeeInfos = this.employeeInfoDao.countSearchEmployeeInfo(tableCommand);
		}
		return countEmployeeInfos;
	}
	/********************************************************************************************************
	 * 	Private Methods
	 ********************************************************************************************************/
	private String getWhereClause(TableCommand tableCommand) {
		StringBuffer whereClause = new StringBuffer();
		if(tableCommand.getKeyword() != "") {
			whereClause.append(" AND ei.userId IN(")
				.append("SELECT ei3.userId FROM employeeinfo ei3 ")
				.append("LEFT JOIN user u3 ON u3.userId=ei3.userId ")
				.append("LEFT JOIN jobrole jr3 ON jr3.jobRoleId=ei3.jobRoleId ")
				.append("WHERE u3.status = :status AND (ei3.lastName like :keyword ")
				.append("OR ei3.firstName like :keyword ")
				.append("OR ei3.fullName like :keyword ")
				.append("OR ei3.employeeCode like :keyword ")
				.append("OR jr3.jobName like :keyword)) ")
				.append("OR ei.userId IN (")
				.append("SELECT ugm.userId FROM usergroupmember ugm ")
				.append("LEFT JOIN usergroup ug ON ug.userGroupId=ugm.userGroupId ")
				.append("WHERE ug.userGroupName like :keyword)");
		}
		return whereClause.toString();
	}
}
