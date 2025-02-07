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
@Table(name="examquestion")
public class ExamQuestion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int examQuestionId;
	@Getter @Setter private int examId;
	@Getter @Setter private int questionId;
	@Getter @Setter private String sectionName;
	@Getter @Setter private int sectionOrder;
	@Getter @Setter private String instruction;
	@Getter @Setter private int isRandomized;
	@Getter @Setter private int isShuffle;
	@Getter @Setter private int randomizedQuestionTypeId;
	@Getter @Setter private int randomizedTopicId;
	@Getter @Setter private int randomizedDifficultyId;
	@Getter @Setter private int noOfQuestions;
	@Getter @Setter private int weight;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
	
	@Transient @Getter @Setter private String questionTypeDesc;
	@Transient @Getter @Setter private String label;
	@Transient @Getter @Setter private String randomizeQuestionTypeDesc;
	@Transient @Getter @Setter private String randomizedTopicDesc;
	@Transient @Getter @Setter private String randomizedDifficultyDesc;
}
