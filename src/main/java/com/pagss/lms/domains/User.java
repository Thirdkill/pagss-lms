package com.pagss.lms.domains;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int userId;
	@Getter @Setter private String username;
	@Getter @Setter private String password;
	@Getter @Setter private int isPasswordReset;
	@Getter @Setter private int userTypeId;
	@Getter @Setter private int status;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
	
	@Transient @Getter @Setter private int employeeId;
	@Transient @Getter @Setter private int jobroleId;
	@Transient @Getter @Setter private int userGroupId;
	
	@Transient @Getter @Setter private String employeeCode;
	@Transient @Getter @Setter private String lastName;
	@Transient @Getter @Setter private String firstName;
	@Transient @Getter @Setter private String middleInitial;
	@Transient @Getter @Setter private String userGroupName;
	@Transient @Getter @Setter private String dateHired;
	@Transient @Getter @Setter private String email;
	@Transient @Getter @Setter private String jobName;
	@Transient @Getter @Setter private String UserTypeDesc;
	@Transient @Getter @Setter private String whereClause;
	@Transient @Getter @Setter private String mobileNo;
	@Transient @Getter @Setter private String fullName;
	@Transient @Getter @Setter private String modifiedBy;
	@Transient @Getter @Setter private String errorMessage;
}
