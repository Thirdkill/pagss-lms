package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.EmployeeInfo;
import com.pagss.lms.domains.User;
import com.pagss.lms.domains.UserGroup;

import lombok.Getter;
import lombok.Setter;

public class MassUserUploadResponse {
	@Getter @Setter private int status;
	@Getter @Setter private int rowId;
	@Getter @Setter private List<User> savedUploadedUser;
	@Getter @Setter private List<User> errorUploadedUser;
	@Getter @Setter private User user;
	public MassUserUploadResponse(int status) {
		setStatus(status);
	}
	
	public MassUserUploadResponse(int status, int rowId) {
		setStatus(status);
		setRowId(rowId);
	}
	
	public MassUserUploadResponse(int status, List<User> errorList, List<User> savedList) {
		setStatus(status);
		setSavedUploadedUser(savedList);
		setErrorUploadedUser(errorList);
	}
	
	public MassUserUploadResponse(int status, User user) {
		setStatus(status);
		setUser(user);
	}
}
