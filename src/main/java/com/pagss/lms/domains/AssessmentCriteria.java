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
@Table(name="assessmentcriteria")
public class AssessmentCriteria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int assessmentCriteriaId;
	@Getter @Setter private int questionId;
	@Getter @Setter private String criteriaDesc;
	@Getter @Setter private int withComment;
	@Getter @Setter private int minScore;
	@Getter @Setter private int maxScore;
	@Getter @Setter private int passBenchMark;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
}
