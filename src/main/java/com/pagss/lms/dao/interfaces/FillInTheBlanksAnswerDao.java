package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.FillInTheBlanksAnswer;

public interface FillInTheBlanksAnswerDao {

	public void createFillInTheBlanksAnswers(List<FillInTheBlanksAnswer> fillInTheBlanksAnswers);
	
	public List<FillInTheBlanksAnswer> fetchFillInTheBlanksAnswers(int questionId);
	
	public void deleteFillInTheBlanksAnswer(int questionId);
}
