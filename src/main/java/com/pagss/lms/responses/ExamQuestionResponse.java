package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.ExamQuestion;

import lombok.Getter;
import lombok.Setter;

public class ExamQuestionResponse {

	@Getter @Setter private int status;
	@Getter @Setter private ExamQuestion examQuestion;
	@Getter @Setter private List<ExamQuestion> examQuestions;
	
	public ExamQuestionResponse(int status) {
		setStatus(status);
	}
	
	public ExamQuestionResponse(int status,ExamQuestion examQuestion) {
		setStatus(status);
		setExamQuestion(examQuestion);
	}
	
	public ExamQuestionResponse(int status,List<ExamQuestion> examQuestions) {
		setStatus(status);
		setExamQuestions(examQuestions);
	}
	
	
}
