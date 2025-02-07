package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.ExamQuestionDao;
import com.pagss.lms.domains.ExamQuestion;
import com.pagss.lms.manager.interfaces.ExamQuestionManager;

@Component
public class LmsExamQuestionManager implements ExamQuestionManager {

	@Autowired
	private ExamQuestionDao examQuestionDao;
	
	@Override
	public List<ExamQuestion> fetchExamQuestions(int examId) {
		return this.examQuestionDao.fetchExamQuestions(examId);
	}

}
