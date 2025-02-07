package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.CourseEvaluation;

import lombok.Getter;
import lombok.Setter;

public class CourseEvaluationResponse {
	@Getter @Setter private int status;
	@Getter @Setter private CourseEvaluation coursevaluation;
	@Getter @Setter private List<CourseEvaluation> courseevaluations;
	@Getter @Setter private int totalRecords;
	
	public CourseEvaluationResponse (int status) {
		setStatus(status);
	}
	
	public CourseEvaluationResponse (int status, CourseEvaluation courseevaluation) {
		setStatus(status);
		setCoursevaluation(courseevaluation);
	}
	
	public CourseEvaluationResponse (int status, List<CourseEvaluation> courseevaluations) {
		setStatus(status);
		setCourseevaluations(courseevaluations);
	}
	
	public CourseEvaluationResponse (int status, List<CourseEvaluation> courseevaluations, int totalRecords) {
		setStatus(status);
		setCourseevaluations(courseevaluations);
		setTotalRecords(totalRecords);
	}
	
}
