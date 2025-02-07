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
@Table(name="courseexam")
public class CourseExam {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int courseExamId;
	@Getter @Setter private int examId;
	@Getter @Setter private int courseId;
	@Getter @Setter private int duration;
	@Getter @Setter private BigDecimal passingScore;
	@Getter @Setter private int examRetake;
	@Getter @Setter private int noOfRetake;
	@Getter @Setter private int isSafeBrowser;
	@Getter @Setter private int isShowCorrectAnswer;
	@Getter @Setter private int isShowScore;
	@Getter @Setter private int isShowBreakDown;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
	
	@Transient @Getter @Setter private int examType;
	@Transient @Getter @Setter private String description;
	@Transient @Getter @Setter private String title;
	@Transient @Getter @Setter private String examCode;
	@Transient @Getter @Setter private String remarks;
	@Transient @Getter @Setter private int noOfTimesTaken;
	@Transient @Getter @Setter private BigDecimal score;
}
