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
@Table(name="usergroup")
public class UserGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int userGroupId;
	@Getter @Setter private String userGroupCode;
	@Getter @Setter private String userGroupName;
	@Getter @Setter private String description;
	@Getter @Setter private int supervisorId;
	@Getter @Setter private int status;
	@Getter @Setter private int isSubGroup;
	@Getter @Setter private String mainUserGroupCode;
	@Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String fullName;
	@Transient @Getter @Setter private String lastModifiedAt;
	@Transient @Getter @Setter private String modifiedBy;
}
