package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.EvaluationQuestion;

import lombok.Getter;
import lombok.Setter;

public class EvaluationQuestionResponse {
	@Getter @Setter private int status;
	@Getter @Setter private int evaluationQuestionId;
	@Getter @Setter private EvaluationQuestion evaluationquestion;
	@Getter @Setter private List<EvaluationQuestion> evaluationquestions;
	
	public EvaluationQuestionResponse (int status) {
		setStatus(status);
	}
	
	public EvaluationQuestionResponse (int status, int evaluationQuestionId) {
		setStatus(status);
		setEvaluationQuestionId(evaluationQuestionId);
	}
	
	public EvaluationQuestionResponse (int status, EvaluationQuestion evaluationquestion) {
		setStatus(status);
		setEvaluationquestion(evaluationquestion);
	}
	
	public EvaluationQuestionResponse (int status, List<EvaluationQuestion> evaluationquestions) {
		setStatus(status);
		setEvaluationquestions(evaluationquestions);
	}
}
