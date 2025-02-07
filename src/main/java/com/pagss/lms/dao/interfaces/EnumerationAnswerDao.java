package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.EnumerationAnswer;

public interface EnumerationAnswerDao {

	public void createEnumerationAnswers(List<EnumerationAnswer> enumerationAnswers);
	
	public void deleteEnumerationAnswers(int questionId);
	
	public List<EnumerationAnswer> fetchEnumerationAnswers(int questionId);
}
