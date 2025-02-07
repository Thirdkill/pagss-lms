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
@Table(name="evaluationquestion")
public class EvaluationQuestion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int evaluationQuestionId;
	@Getter @Setter private int evaluationId;
	@Getter @Setter private String evaluationType;
	@Getter @Setter private String questionDesc;
	@Getter @Setter private int sectionOrder;
	@Getter @Setter private String sectionName;
	@Getter @Setter private String instruction;
	@Getter @Setter private int scaleMin;
	@Getter @Setter private int scaleMax;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
}
