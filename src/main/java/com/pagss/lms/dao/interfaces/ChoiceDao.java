package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.Choice;

public interface ChoiceDao {

	public void createChoices(List<Choice> choices);
	
	public List<Choice> fetchChoices(int questionId);
	
	public void deleteChoices(int questionId);
}
