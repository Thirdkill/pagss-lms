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
@Table(name="employeeevaluation")
public class EmployeeEvaluation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int employeeEvaluationId;
	@Getter @Setter private int employeeId;
	@Getter @Setter private int classId;
	@Getter @Setter private int evaluationId;
	@Getter @Setter private int evaluationQuestionId;
	@Getter @Setter private String answer;
	@Getter @Setter private String dateCompleted;
	
	@Transient @Getter @Setter private String fullName;
	@Transient @Getter @Setter private String employeeCode;
	@Transient @Getter @Setter private String jobName;
	@Transient @Getter @Setter private String evaluationType;
	
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
}
