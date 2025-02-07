package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.UserGroup;

import lombok.Getter;
import lombok.Setter;

public class UserGroupResponse {
	@Getter @Setter private int status;
	@Getter @Setter private int userGroupId;
	@Getter @Setter private UserGroup usergroup;
	@Getter @Setter private List<UserGroup> usergroups;
	@Getter @Setter private int totalRecords;
	
	public UserGroupResponse(int status) {
		setStatus(status);
	}
	
	public UserGroupResponse(int status, int totalRecords) {
		setStatus(status);
		setTotalRecords(totalRecords);
	}
	
	public UserGroupResponse(int status,UserGroup usergroup) {
		setStatus(status);
		setUsergroup(usergroup);
	}

	public UserGroupResponse(int status,List<UserGroup> usergroups) {
		setStatus(status);
		setUsergroups(usergroups);
	}
	
	public UserGroupResponse(int status,List<UserGroup> usergroups, int totalRecords) {
		setStatus(status);
		setUsergroups(usergroups);
		setTotalRecords(totalRecords);
	}
}
