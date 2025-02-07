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
@Table(name="employeeinfo")
public class EmployeeInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int employeeId;
	@Getter @Setter private int userId;
	@Getter @Setter private int jobRoleId;
	@Getter @Setter private String employeeCode;
	@Getter @Setter private String lastName;
	@Getter @Setter private String firstName;
	@Getter @Setter private String middleInitial;
	@Getter @Setter private String fullName;
	@Getter @Setter private String dateHired;
	@Getter @Setter private String mobileNo;
	@Getter @Setter private String email;
	@Getter @Setter private String modifiedBy;
	@Transient @Getter @Setter private int userTypeId;
	@Transient @Getter @Setter private int status;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
	@Transient @Getter @Setter private String userTypeDesc;
	@Transient @Getter @Setter private String jobName;
	@Transient @Getter @Setter private String userGroupName;
	@Transient @Getter @Setter private int userGroupId;
	@Transient @Getter @Setter private String errorMessage;
	@Transient @Getter @Setter private String userGroupCode;
	@Transient @Getter @Setter private String jobRoleCode;
	
}
