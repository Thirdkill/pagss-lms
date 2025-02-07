package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.ClassExam;

import lombok.Getter;
import lombok.Setter;

public class ClassExamResponse {
	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private ClassExam classExam;
	@Getter @Setter private List<ClassExam> classExams;
	
	public ClassExamResponse (int status) {
		setStatus(status);
	}
	
	public ClassExamResponse (int status, ClassExam classExam) {
		setStatus(status);
		setClassExam(classExam);
	}
	
	public ClassExamResponse (int status, List<ClassExam> classExams) {
		setStatus(status);
		setClassExams(classExams);
	}
	
	public ClassExamResponse (int status, List<ClassExam> classExams, int totalRecords) {
		setStatus(status);
		setClassExams(classExams);
		setTotalRecords(totalRecords);
	}
}
