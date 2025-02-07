package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.UserGroup;
import com.pagss.lms.domains.UserGroupMember;

public interface UserGroupDao {
	
	public List<UserGroup> fetchUserGroup(int pageSize, int pageNo);

	public int countTotalUserGroups();
	
	public List<UserGroup> searchUserGroup(String keyword, int pageSize, int pageNo);

	public int countSearchedUserGroups(String keyword);
	
	public int countUserGroupMember(int userId);

	public void deleteMember(int userId);
	
	public void addMember(List<UserGroupMember> usergroupmember);

	public List<UserGroup> fetchUserGroupAssignment(int userId);

	public int fetchLatestUserGroupId();

	public void addUserGroup(UserGroup usergroup);

	public List<UserGroup> fetchUserGroupList();

	public int countUserGroupMemberById(int userGroupId);

	public void deleteMemberByUserGroupId(int userGroupId);

	public UserGroup fetchUserGroupInfo(UserGroup usergroup);

	public List<UserGroupMember> fetchUserGroupMembers(int userGroupId);

	public void updateUserGroup(UserGroup usergroup);

	public int countUserGroupCode(UserGroup usergroups);

	public int getUserGroupId(String userGroupCode);
}
