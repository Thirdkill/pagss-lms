package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.ExamQuestion;

public interface ExamQuestionDao {

	public void createExamQuestions(List<ExamQuestion> examQuestion);
	
	public List<ExamQuestion> fetchExamQuestions(int examId);
	
	public void deleteExamQuestions(int examId);
}
