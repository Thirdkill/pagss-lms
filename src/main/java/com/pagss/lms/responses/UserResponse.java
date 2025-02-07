package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.User;

import lombok.Getter;
import lombok.Setter;

public class UserResponse {
	@Getter @Setter private int status;
	@Getter @Setter private User user;
	@Getter @Setter private List<User> users;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private int totalActiveRecords;
	@Getter @Setter private int totalInactiveRecords;
	
	public UserResponse(int status) {
		setStatus(status);
	}
	
	public UserResponse(int status,User user) {
		setStatus(status);
		setUser(user);
	}
	
	public UserResponse(int status,List<User> users) {
		setStatus(status);
		setUsers(users);
	}

	public UserResponse(int status,List<User> users, int totalRecords) {
		setStatus(status);
		setUsers(users);
		setTotalRecords(totalRecords);
	}
	
	public UserResponse(int status, int totalRecords, int totalActiveRecords, int totalInactiveRecords) {
		setStatus(status);
		setTotalRecords(totalRecords);
		setTotalActiveRecords(totalActiveRecords);
		setTotalInactiveRecords(totalInactiveRecords);
	}
}
