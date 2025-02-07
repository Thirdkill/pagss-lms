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
@Table(name="employeeexamsummary")
public class EmployeeExamSummary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int examSummaryId;
	@Getter @Setter private int classId;
	@Getter @Setter private int examId;
	@Getter @Setter private int employeeId;
	@Getter @Setter private BigDecimal score;
	@Getter @Setter private int noOfTimesTaken;
	@Getter @Setter private String remarks;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
	
	@Transient @Getter @Setter private String title;
	@Transient @Getter @Setter private String employeeCode;
	@Transient @Getter @Setter private String jobName;
	@Transient @Getter @Setter private String completedDate;
	@Transient @Getter @Setter private String fullName;
	@Transient @Getter @Setter private int trainingStatus;
}
