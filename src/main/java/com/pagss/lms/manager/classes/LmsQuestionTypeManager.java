package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.QuestionTypeDao;
import com.pagss.lms.domains.QuestionType;
import com.pagss.lms.manager.interfaces.QuestionTypeManager;

@Component
public class LmsQuestionTypeManager implements QuestionTypeManager {

	@Autowired
	private QuestionTypeDao questionTypeDao;
	
	@Override
	public List<QuestionType> fetchQuestionTypes() {
		return questionTypeDao.fetchQuestionTypes();
	}

}
