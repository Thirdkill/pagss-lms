package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.AnalogyAnswer;

public interface AnalogyAnswerDao {

	public void createAnalogyAnswers(List<AnalogyAnswer> analogyAnswers);
	
	public List<AnalogyAnswer> fetchAnalogyAnswers(int questionId);
	
	public void deleteAnalogyAnswers(int questionId);
}
