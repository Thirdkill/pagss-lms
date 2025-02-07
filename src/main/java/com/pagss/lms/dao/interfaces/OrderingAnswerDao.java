package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.OrderingAnswer;

public interface OrderingAnswerDao {

	public void createOrderingAnswer(List<OrderingAnswer> orderingAnswers);
	
	public List<OrderingAnswer> fetchOrderingAnswers(int questionId);
	
	public void deleteOrderingAnswers(int questionId);
}
