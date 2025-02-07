package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.UserGroupMember;

import lombok.Getter;
import lombok.Setter;

public class UserGroupMemberResponse {
	@Getter @Setter private int status;
	@Getter @Setter private int usergroupmemberId;
	@Getter @Setter private UserGroupMember usergroupmember;
	@Getter @Setter private List<UserGroupMember> usergroupmembers;
	@Getter @Setter private int totalRecords;
	
	public UserGroupMemberResponse(int status) {
		setStatus(status);
	}
	
	public UserGroupMemberResponse(int status, int userGroupId) {
		setStatus(status);
		setUsergroupmemberId(userGroupId);
	}
	
	public UserGroupMemberResponse(int status,UserGroupMember usergroupmember) {
		setStatus(status);
		setUsergroupmember(usergroupmember);
	}

	public UserGroupMemberResponse(int status,List<UserGroupMember> usergroupmembers) {
		setStatus(status);
		setUsergroupmembers(usergroupmembers);
	}
}
