package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.UserType;

import lombok.Getter;
import lombok.Setter;

public class UserTypeResponse {
	@Getter @Setter private int status;
	@Getter @Setter private UserType usertype;
	@Getter @Setter private List<UserType> usertypes;
	
	public UserTypeResponse(int status) {
		setStatus(status);
	}
	
	public UserTypeResponse(int status,UserType userType) {
		setStatus(status);
		setUsertype(userType);
	}
	
	public UserTypeResponse(int status,List<UserType> userTypes) {
		setStatus(status);
		setUsertypes(userTypes);
	}
}
