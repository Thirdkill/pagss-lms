package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.EmployeeInfoDao;
import com.pagss.lms.dao.interfaces.UserGroupDao;
import com.pagss.lms.domains.EmployeeInfo;
import com.pagss.lms.domains.User;
import com.pagss.lms.domains.UserGroup;
import com.pagss.lms.domains.UserGroupMember;
import com.pagss.lms.manager.interfaces.UserGroupManager;

@Component
public class LmsUserGroupManager implements UserGroupManager{
	
	private UserGroupDao userGroupDao;
	private EmployeeInfoDao employeeInfoDao;
	/*******************************************************************************************************
	 * Start: Autowired Setters	
	 ******************************************************************************************************/
	@Autowired
	public void setUserGroupManager(UserGroupDao userGroupDao) {
		this.userGroupDao = userGroupDao;
	}
	@Autowired
	public void setEmployeeInfoManager(EmployeeInfoDao employeeInfoDao) {
		this.employeeInfoDao = employeeInfoDao;
	}
	/*******************************************************************************************************
	 * End: Autowired Setters	
	 ******************************************************************************************************/
	
	@Override
	public List<UserGroup> fetchUserGroup(int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.userGroupDao.fetchUserGroup(pageSize, calculatedPageNo);
	}
	
	@Override
	public int countTotalUserGroups() {
		return this.userGroupDao.countTotalUserGroups();
	}
	
	@Override
	public List<UserGroup> searchUserGroup(String keyword, int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.userGroupDao.searchUserGroup(keyword, pageSize, calculatedPageNo);
	}
	
	@Override
	public int countSearchedUserGroups(String keyword) {
		return this.userGroupDao.countSearchedUserGroups(keyword);
	}
	@Override
	public void usergroupMember(List<UserGroupMember> usergroupmember, int userId) {
		int userCount = userGroupDao.countUserGroupMember(userId);
		if(userCount > 0) {
			this.userGroupDao.deleteMember(userId);
			this.userGroupDao.addMember(usergroupmember);
		} else if(userCount == 0) {
			this.userGroupDao.addMember(usergroupmember);
		}
	}
	@Override
	public List<UserGroup> fetchUserGroupAssignment(int userId) {
		return this.userGroupDao.fetchUserGroupAssignment(userId);
	}
	@Override
	public UserGroup generateUserGroupCode() {
		UserGroup usergroup = new UserGroup();
		usergroup.setUserGroupCode(generateUserGroupCode(this.userGroupDao.fetchLatestUserGroupId()));
		usergroup.setUserGroupId(this.userGroupDao.fetchLatestUserGroupId());
		return usergroup;
	}
	private String generateUserGroupCode(int userGroupId) {
		String ugNo = Integer.toString(100000 + userGroupId).substring(1);
		return "UGC" + ugNo;
	}
	@Override
	public void addUserGroup(UserGroup usergroup, User users) {
		EmployeeInfo empinfo = new EmployeeInfo();
		empinfo.setUserId(users.getUserId());
		EmployeeInfo empinfos = this.employeeInfoDao.fetchEmployeeInfo(empinfo);
		String fullName = empinfos.getFullName().toString();
		usergroup.setModifiedBy(fullName);
		this.userGroupDao.addUserGroup(usergroup);
	}
	@Override
	public List<UserGroup> fetchUserGroupList() {
		return this.userGroupDao.fetchUserGroupList();
	}
	@Override
	public void usergroupMembers(List<UserGroupMember> usergroupmember, int userGroupId) {
		int memberCount = this.userGroupDao.countUserGroupMemberById(userGroupId);
		if(memberCount > 0) {
			this.userGroupDao.deleteMemberByUserGroupId(userGroupId);
			this.userGroupDao.addMember(usergroupmember);
		} else if(memberCount == 0) {
			this.userGroupDao.addMember(usergroupmember);
		}
	}
	@Override
	public UserGroup fetchUserGroupInfo(UserGroup usergroup) {
		return this.userGroupDao.fetchUserGroupInfo(usergroup);
	}
	@Override
	public List<UserGroupMember> fetchUserGroupMembers(int userGroupId) {
		return this.userGroupDao.fetchUserGroupMembers(userGroupId);
	}
	@Override
	public void updateUserGroup(UserGroup usergroup, User users) {
		EmployeeInfo empinfo = new EmployeeInfo();
		empinfo.setUserId(users.getUserId());
		EmployeeInfo empinfos = this.employeeInfoDao.fetchEmployeeInfo(empinfo);
		String fullName = empinfos.getFullName().toString();
		usergroup.setModifiedBy(fullName);
		this.userGroupDao.updateUserGroup(usergroup);
	}
	@Override
	public int countUserGroupCode(UserGroup usergroups) {
		return this.userGroupDao.countUserGroupCode(usergroups);
	}
	@Override
	public int getUserGroupId(String userGroupCode) {
		return this.userGroupDao.getUserGroupId(userGroupCode);
	}
}
