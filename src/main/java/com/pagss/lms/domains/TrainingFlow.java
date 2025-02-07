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
@Table(name="trainingflow")
public class TrainingFlow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private Integer trainingFlowId;
	@Getter @Setter private Integer orderNo;
	@Getter @Setter private Integer courseId;
	@Getter @Setter private Integer categoryId;
	@Getter @Setter private Integer preCourseId;
	@Getter @Setter private Integer assignmentType;
	@Getter @Setter private Integer jobRoleId;
	@Getter @Setter private Integer userGroupId;
	@Getter @Setter private Integer isRequired;
	@Getter @Setter private Integer isRecurring;
	@Getter @Setter private Integer interval;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
	
	@Transient @Getter @Setter private String preCourseName;
	@Transient @Getter @Setter private String courseCode;
	@Transient @Getter @Setter private String courseName;
	@Transient @Getter @Setter private String categoryName;
	@Transient @Getter @Setter private int assignmentTypeId;
	
	@Transient @Getter @Setter private String whereClause;
	
}
