package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.domains.ClassEmployee;

public interface ClassEmployeeDao {

	public void createClassEmployees(List<ClassEmployee> classEmployees);
	
	public List<ClassEmployee> fetchClassEmployeesByApprovalStatus(TableCommand tableCommand);
	
	public int countClassEmployeesByApprovalStatus(TableCommand tableCommand);
	
	public void updateClassEmployee(ClassEmployee classEmployee);
	
	public void updateClassInfoTrainingStatus(List<ClassEmployee> classEmployee);
	
	public void updateApprovalStatus(ClassEmployee classEmployee);
	
	public void createClassEmployee(ClassEmployee classEmployee);
	
	public int countClassEmployeeByEmployeeId(int classId,int employeeId);
	
	public int countClassEmployeeWithOngoingClasses(int classId,int employeeId);
	
	public void updateTrainingStatus(int classId,int employeeId,int trainingStatus);
}
