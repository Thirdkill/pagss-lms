package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.domains.JobRole;

public interface JobRoleDao {

	public List<JobRole> fetchJobRoles(int pageSize, int pageNo);
	
	public int countFetchJobRoles();
	
	public List<JobRole> searchJobRole(TableCommand tableCommand);
	
	public int countSearchJobRole(String keyword);
	
	public int fetchLatestJobRoleId();
	
	public int countJobCode(String jobCode);

	public int getRoleId(String jobCode);
}
