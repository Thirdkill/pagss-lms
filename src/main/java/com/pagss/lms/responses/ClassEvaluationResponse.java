package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.ClassEvaluation;

import lombok.Getter;
import lombok.Setter;

public class ClassEvaluationResponse {
	@Getter @Setter private int status;
	@Getter @Setter private ClassEvaluation classevaluation;
	@Getter @Setter private List<ClassEvaluation> classevaluations;
	@Getter @Setter private int totalRecords;
	
	public ClassEvaluationResponse (int status) {
		setStatus(status);
	}
	
	public ClassEvaluationResponse (int status, ClassEvaluation classevaluation) {
		setStatus(status);
		setClassevaluation(classevaluation);
	}
	
	public ClassEvaluationResponse (int status, List<ClassEvaluation> classevaluations) {
		setStatus(status);
		setClassevaluations(classevaluations);
	}
	
	public ClassEvaluationResponse (int status, List<ClassEvaluation> classevaluations, int totalRecords) {
		setStatus(status);
		setClassevaluations(classevaluations);
		setTotalRecords(totalRecords);
	}
}
