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
@Table(name="employeeexamanswer")
public class EmployeeExamAnswer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int employeeExamAnswerId;
	@Getter @Setter private int employeeId;
	@Getter @Setter private int examQuestionId;
	@Getter @Setter private int questionType;
	@Getter @Setter private String answer;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
}
