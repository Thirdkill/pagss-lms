package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.User;

public interface UserDao {

	public int countUser(User user);
	
	public User fetchUser(User user);
	
	public List<User> fetchUsers(int pageSize, int pageNo);
	
	public int countTotalUsers();
	
	public List<User> searchUsers(User user, int pageSize, int pageNo);

	public int countTotalSearchedUsers(User user);
	
	public User fetchUserInfo(User user);

	public int fetchLatestUserId();

	public int fetchLatestEmployeeId();

	public int updateUserInfo(User user);

	public int addUserInfo(User user);

	public int updateUserPassword(User user);

	public void saveNewPassword(User user);

	public List<User> fetchUserlist();

	public List<User> fetchSearchedUserListOnUserGroup(User user, int pageSize, int pageNo);

	int countTotalSearchedUserListOnUserGroup(User user);

	public List<User> fetchUserListOnUserGroup(int pageSize, int calculatedPageNo);

	public int countTotalUserListOnUserGroup();

	public int countUsername(String genUsername);

	public int countTotalRecords();

	public int countTotalActiveRecords();

	public int countTotalInactiveRecords();

	public int getUserTypeId(String userTypeDesc);

}
