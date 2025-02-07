package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.Evaluation;

import lombok.Getter;
import lombok.Setter;

public class EvaluationResponse {
	@Getter @Setter private int status;
	@Getter @Setter private Evaluation evaluation;
	@Getter @Setter private List<Evaluation> evaluations;
	@Getter @Setter private int totalRecords;
	
	public EvaluationResponse (int status) {
		setStatus(status);
	}
	
	public EvaluationResponse (int status, int totalRecords) {
		setStatus(status);
		setTotalRecords(totalRecords);
	}
	
	public EvaluationResponse (int status, Evaluation evaluation) {
		setStatus(status);
		setEvaluation(evaluation);
	}
	
	public EvaluationResponse (int status, List<Evaluation> evaluations) {
		setStatus(status);
		setEvaluations(evaluations);
	}
	
	public EvaluationResponse (int status, List<Evaluation> evaluations, int totalRecords) {
		setStatus(status);
		setEvaluations(evaluations);
		setTotalRecords(totalRecords);
	}
}
