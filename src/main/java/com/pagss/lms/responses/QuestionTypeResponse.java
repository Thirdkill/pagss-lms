package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.QuestionType;

import lombok.Getter;
import lombok.Setter;

public class QuestionTypeResponse {

	@Getter @Setter private int status;
	@Getter @Setter private QuestionType questionType;
	@Getter @Setter private List<QuestionType> questionTypes;
	
	public QuestionTypeResponse(int status) {
		setStatus(status);
	}
	
	public QuestionTypeResponse(int status,QuestionType questionType) {
		setStatus(status);
		setQuestionType(questionType);
	}
	
	public QuestionTypeResponse(int status,List<QuestionType> questionTypes) {
		setStatus(status);
		setQuestionTypes(questionTypes);
	}
}
