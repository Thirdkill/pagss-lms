package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.JobRole;

import lombok.Getter;
import lombok.Setter;

public class JobRoleResponse {

	
	@Getter @Setter private int status;
	@Getter @Setter private JobRole jobRole;
	@Getter @Setter private List<JobRole> jobRoles;
	@Getter @Setter private int totalRecords;

	
	public JobRoleResponse(int status) {
		setStatus(status);
	}
	

	public JobRoleResponse(int status,JobRole jobRole) {
		setStatus(status);
		setJobRole(jobRole);
	}
	
	public JobRoleResponse(int status,List<JobRole> jobRoles) {
		setStatus(status);
		setJobRoles(jobRoles);
	}
	
	public JobRoleResponse(int status,List<JobRole> jobRoles,int totalRecords) {
		setStatus(status);
		setJobRoles(jobRoles);
		setTotalRecords(totalRecords);

	}
}
