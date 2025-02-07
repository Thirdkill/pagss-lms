package com.pagss.lms.domains;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="classemployeeassessment")
public class ClassEmployeeAssessment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int classEmploymentAssessmentId;
	@Getter @Setter private int classId;
	@Getter @Setter private int employeeId;
	@Getter @Setter private int gradingComponentId;
	@Getter @Setter private BigDecimal score;
	@Getter @Setter private String dateFinished;
	@Getter @Setter private int status;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
}
