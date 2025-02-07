package com.pagss.lms.domains;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="examinfo")
public class ExamInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int examId;
	@Getter @Setter private String examCode;
	@Getter @Setter private int examType;
	@Getter @Setter private String title;
	@Getter @Setter private String description;
	@Getter @Setter private int status;
	@Getter @Setter private int allowAttachment;
	@Getter @Setter private String modifiedBy;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
	
	@Transient @Getter @Setter List<ExamQuestion> examQuestions;
	@Transient @Getter @Setter private int courseId;
	@Transient @Getter @Setter private int classId;
}
