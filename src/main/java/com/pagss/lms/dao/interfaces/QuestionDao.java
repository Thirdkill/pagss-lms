package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.domains.Question;

public interface QuestionDao {
	
	public int createQuestion(Question question);
	
	public void updateMediaUrl(Question question);
	
	public List<Question> fetchQuestions(TableCommand tableCommand);
	
	public int countFetchQuestions(TableCommand tableCommand);
	
	public Question fetchQuestion(int questionId);
	
	public void updateQuestion(Question question);
	
	public List<Question> fetchRandomQuestions(Question question);
	
	public int countTotalQuestions(Question question);
}
