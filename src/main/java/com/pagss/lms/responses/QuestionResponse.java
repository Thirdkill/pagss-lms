package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.Question;

import lombok.Getter;
import lombok.Setter;

public class QuestionResponse {

	@Getter @Setter private int status;
	@Getter @Setter private int questionId;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private Question question;
	@Getter @Setter private List<Question> questions;
	
	public QuestionResponse(int status) {
		setStatus(status);
	}
	
	public QuestionResponse(int status,int questionId) {
		setStatus(status);
		setQuestionId(questionId);
	}
	
	public QuestionResponse(int status,Question question) {
		setStatus(status);
		setQuestion(question);
	}
	
	public QuestionResponse(int status,List<Question> questions) {
		setStatus(status);
		setQuestions(questions);
	}
	
	public QuestionResponse(int status,int totalRecords,List<Question> questions) {
		setStatus(status);
		setQuestions(questions);
		setTotalRecords(totalRecords);
	}
}
