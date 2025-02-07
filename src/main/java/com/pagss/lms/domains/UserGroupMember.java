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
@Table(name="usergroupmember")
public class UserGroupMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int memberId;
	@Getter @Setter private int userGroupId;
	@Getter @Setter private int userId;
	@Transient @Getter @Setter private String lastName;
	@Transient @Getter @Setter private String firstName;
	@Transient @Getter @Setter private String employeeCode;
	@Transient @Getter @Setter private String jobName;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
}
