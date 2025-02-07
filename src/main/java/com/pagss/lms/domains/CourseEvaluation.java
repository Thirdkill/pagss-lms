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
@Table(name="courseevaluation")
public class CourseEvaluation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int courseEvaluationId;
	@Getter @Setter private int courseId;
	@Getter @Setter private int evaluationId;
	@Transient @Getter @Setter private String title;
	@Transient @Getter @Setter private int employeeEvaluationId;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
	@Transient @Getter @Setter private String className;
	@Transient @Getter @Setter private String courseName;
	@Transient @Getter @Setter private int status;
}
