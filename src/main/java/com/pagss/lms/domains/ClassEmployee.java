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
@Table(name="classemployee")
public class ClassEmployee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int classEmployeeId;
	@Getter @Setter private int classId;
	@Getter @Setter private int employeeId;
	@Getter @Setter private int role;
	@Getter @Setter private int trainingStatus;
	@Getter @Setter private int approvalStatus;
	@Getter @Setter private String comment;
	@Getter @Setter private String requestDate;
	@Getter @Setter private String enrolledDate;
	@Getter @Setter private String approvedDate;
	@Getter @Setter private String completedDate;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
	
	@Transient @Getter @Setter private String fullName;
	@Transient @Getter @Setter private String jobName;
	@Transient @Getter @Setter private int jobRoleId;
	@Transient @Getter @Setter private String userGroupName;
}
