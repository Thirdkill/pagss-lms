package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.dao.interfaces.JobRoleDao;
import com.pagss.lms.domains.JobRole;
import com.pagss.lms.manager.interfaces.JobRoleManager;

@Component
public class LmsJobRoleManager implements JobRoleManager {

	private JobRoleDao jobRoleDao;
	
	/*******************************************************************************************************
	 * Start: Autowired Setters	
	 ******************************************************************************************************/
	@Autowired
	public void setJobRoleDao(JobRoleDao jobRoleDao) {
		this.jobRoleDao = jobRoleDao;
	}
	/*******************************************************************************************************
	 * END: Autowired Setters	
	******************************************************************************************************/
	@Override
	public List<JobRole> fetchJobRoles(int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.jobRoleDao.fetchJobRoles(pageSize, calculatedPageNo);
	}

	@Override
	public int countFetchJobRoles() {
		return this.jobRoleDao.countFetchJobRoles();
	}
	
	@Override
	public List<JobRole> searchJobRole(TableCommand tableCommand) {
		int calculatedPageNo = tableCommand.getPageSize() * (tableCommand.getPageNumber() - 1);
		tableCommand.setPageNumber(calculatedPageNo);
		
		if(tableCommand.getKeyword() != "") {
			tableCommand.setWhereClause("WHERE jr.jobCode like :keyword OR jr.jobName like :keyword OR jr.jobDesc like :keyword ");
		} else {
			tableCommand.setWhereClause("");
		}
		
		switch(tableCommand.getSortColumnName()) {
			case "jobCode":
				tableCommand.setOrderClause("ORDER BY jr.jobCode " + tableCommand.getSortDirection() + " ");
				break;
			case "jobName":
				tableCommand.setOrderClause("ORDER BY jr.jobName " + tableCommand.getSortDirection() + " ");
				break;
			case "jobDesc":
				tableCommand.setOrderClause("ORDER BY jr.jobDesc " + tableCommand.getSortDirection() + " ");
				break;
			case "status":
				tableCommand.setOrderClause("ORDER BY jr.status " + tableCommand.getSortDirection() + " ");
				break;
		}
		return this.jobRoleDao.searchJobRole(tableCommand);
	}
	
	@Override
	public int countSearchJobRole(String keyword) {
		return this.jobRoleDao.countSearchJobRole(keyword);
	}
	
	@Override
	public JobRole generateJobCode() {
		JobRole newJobRole = new JobRole();
		newJobRole.setJobCode(generateJobCode(this.jobRoleDao.fetchLatestJobRoleId()));
		return newJobRole;
	}
	
	@Override
	public int countJobCode(String jobCode) {
		return this.jobRoleDao.countJobCode(jobCode);
	}
	
	@Override
	public int getJobRoleId(String jobCode) {
		return this.jobRoleDao.getRoleId(jobCode);
	}
	/**************************************************************************************************
	*								Start: Private Classes												*
	****************************************************************************************************/
	private String generateJobCode(int jobRoleId) {
		String jobNo = Integer.toString(10000 + jobRoleId).substring(1);
		return "JC" + jobNo;
	}
	/**************************************************************************************************
	*								End: Private Classes												*
	****************************************************************************************************/
}
