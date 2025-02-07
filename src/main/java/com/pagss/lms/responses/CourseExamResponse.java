package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.CourseExam;

import lombok.Getter;
import lombok.Setter;

public class CourseExamResponse {
	
	@Getter @Setter private int status;
	@Getter @Setter private int courseExamId;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private CourseExam courseExam;
	@Getter @Setter private List<CourseExam> courseExams;
	
	public CourseExamResponse(int status) {
		setStatus(status);
	}
	
	public CourseExamResponse(int status,int courseExamId) {
		setStatus(status);
		setCourseExamId(courseExamId);
	}
	
	public CourseExamResponse(int status,CourseExam courseExam) {
		setStatus(status);
		setCourseExam(courseExam);
	}
	
	public CourseExamResponse(int status,int totalRecords,List<CourseExam> courseExams) {
		setStatus(status);
		setTotalRecords(totalRecords);
		setCourseExams(courseExams);
	}
	
	public CourseExamResponse(int status,List<CourseExam> courseExams) {
		setStatus(status);
		setCourseExams(courseExams);
	}
}
