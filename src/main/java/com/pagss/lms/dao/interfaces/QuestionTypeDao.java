package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.QuestionType;

public interface QuestionTypeDao {
	
	public List<QuestionType> fetchQuestionTypes();
}
