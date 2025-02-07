package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.domains.EmployeeInfo;

public interface EmployeeInfoDao {
	public EmployeeInfo fetchEmployeeInfo(EmployeeInfo empinfo);

	public int countEmployeeCode(EmployeeInfo empinfo);
	
	public List<EmployeeInfo> fetchTrainers();
	
	public EmployeeInfo fetchEmployeeInfoByUserId(int userId);
	
	public List<EmployeeInfo> fetchTrainersByStatus(int status);
	
	public List<EmployeeInfo> fetchEmployeeInfoWithoutClass(int classId,int pageNumber,int pageSize);
	
	public int countEmployeeInfoWithoutClass(int classId);
	
	public List<EmployeeInfo> searchEmployeeInfoByJobRoleId(TableCommand tableCommand);
	
	public int countSearchEmployeeInfoByJobRoleId(TableCommand tableCommand);
	
	public List<EmployeeInfo> searchEmployeeInfoByUserGroupId(TableCommand tableCommand);
	
	public int countSearchEmployeeInfoByUserGroupId(TableCommand tableCommand);
	
	public List<EmployeeInfo> searchEmployeeInfo(TableCommand tableCommand);
	
	public int countSearchEmployeeInfo(TableCommand tableCommand);
}
