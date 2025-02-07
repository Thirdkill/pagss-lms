package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.EnumerationAnswerDao;
import com.pagss.lms.domains.EnumerationAnswer;
import com.pagss.lms.manager.interfaces.EnumerationAnswerManager;

@Component
public class LmsEnumerationAnswerManager implements EnumerationAnswerManager{

	@Autowired
	private EnumerationAnswerDao enumerationAnswerDao;
	
	@Override
	public List<EnumerationAnswer> fetchEnumerationAnswers(int questionId) {
		return this.enumerationAnswerDao.fetchEnumerationAnswers(questionId);
	}
}
