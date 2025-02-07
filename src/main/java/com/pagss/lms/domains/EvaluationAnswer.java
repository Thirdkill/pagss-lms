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
@Table(name="evaluationanswer")
public class EvaluationAnswer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int evaluationAnswerId;
	@Getter @Setter private int evaluationQuestionId;
	@Getter @Setter private int userId;
	@Getter @Setter private String answer;
	@Getter @Setter private String createdAt;
	
	@Transient @Getter @Setter private String sectionName;
	@Transient @Getter @Setter private String instruction;
	@Transient @Getter @Setter private String descripton;
	@Transient @Getter @Setter private String evaluationType;
	@Transient @Getter @Setter private Integer scaleMin;
	@Transient @Getter @Setter private Integer scaleMax;
	
}
