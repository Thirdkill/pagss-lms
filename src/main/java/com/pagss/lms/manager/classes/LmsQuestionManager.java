package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.constants.LmsQuestionTypeData;
import com.pagss.lms.dao.interfaces.AnalogyAnswerDao;
import com.pagss.lms.dao.interfaces.ChoiceDao;
import com.pagss.lms.dao.interfaces.EnumerationAnswerDao;
import com.pagss.lms.dao.interfaces.FillInTheBlanksAnswerDao;
import com.pagss.lms.dao.interfaces.OrderingAnswerDao;
import com.pagss.lms.dao.interfaces.QuestionDao;
import com.pagss.lms.domains.AnalogyAnswer;
import com.pagss.lms.domains.Choice;
import com.pagss.lms.domains.EnumerationAnswer;
import com.pagss.lms.domains.FillInTheBlanksAnswer;
import com.pagss.lms.domains.OrderingAnswer;
import com.pagss.lms.domains.Question;
import com.pagss.lms.manager.interfaces.QuestionManager;

@Component
public class LmsQuestionManager implements QuestionManager {

	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private ChoiceDao choiceDao;
	@Autowired
	private EnumerationAnswerDao enumerationAnswerDao;
	@Autowired
	private AnalogyAnswerDao analogyAnswerDao;
	@Autowired
	private FillInTheBlanksAnswerDao fillInTheBlanksAnswerDao;
	@Autowired
	private OrderingAnswerDao orderingAnswerDao;
	
	@Override
	public int createQuestion(Question question) {
		int questionId = 0;
		switch(question.getQuestionTypeId()) {
			case LmsQuestionTypeData.ESSAY:
				questionId = this.questionDao.createQuestion(question);
				break;	
			case LmsQuestionTypeData.IDENTIFICATION:
				questionId = this.questionDao.createQuestion(question);
				break;
			case LmsQuestionTypeData.MULTIPLE_CHOICE:
				questionId = this.questionDao.createQuestion(question);
				List<Choice> newChoices = assignQuestionId(questionId,question.getChoices());
				this.choiceDao.createChoices(newChoices);
				break;
			case LmsQuestionTypeData.TRUE_OR_FALSE:
				questionId = this.questionDao.createQuestion(question);
				break;
			case LmsQuestionTypeData.ENUMERATION:
				questionId = this.questionDao.createQuestion(question);
				List<EnumerationAnswer> enumerationAnswers=assignEnumerationQuestionId(questionId,
					question.getEnumerationAnswers());
				this.enumerationAnswerDao.createEnumerationAnswers(enumerationAnswers);
				break;
			case LmsQuestionTypeData.MATCHING:
				questionId = this.questionDao.createQuestion(question);
				List<AnalogyAnswer> analogyAnswers = assignAnalogyAnswerQuestionId(questionId,question.getAnalogyAnswers());
				this.analogyAnswerDao.createAnalogyAnswers(analogyAnswers);
				break;
			case LmsQuestionTypeData.FILL_IN_THE_BLANK:
				questionId = this.questionDao.createQuestion(question);
				List<FillInTheBlanksAnswer> fillInTheBlanksAnswers=assignFillInTheBlanksAnswerQuestionId(questionId,
					question.getFillInTheBlanksAnswers());
				this.fillInTheBlanksAnswerDao.createFillInTheBlanksAnswers(fillInTheBlanksAnswers);
				break;
			case LmsQuestionTypeData.ORDERING:
				questionId = this.questionDao.createQuestion(question);
				List<OrderingAnswer> orderingAnswers=assignOrderingAnswerQuestionId(questionId,question.getOrderingAnswers());
				this.orderingAnswerDao.createOrderingAnswer(orderingAnswers);
				break;
		}	
		return questionId;
	}

	@Override
	public void updateMediaUrl(Question question) {
		this.questionDao.updateMediaUrl(question);
	}
	
	@Override
	public List<Question> fetchQuestions(TableCommand tableCommand) {
		int calculatedPageNo = tableCommand.getPageSize() * (tableCommand.getPageNumber() - 1);
		tableCommand.setPageNumber(calculatedPageNo);
		StringBuffer whereClause = new StringBuffer().append("WHERE q.questionTypeId != 0 ");
		if(tableCommand.getSearchByQuestionType() != 0) {
			whereClause.append("AND q.questionTypeId = :questionTypeId ");
		} 
		if(tableCommand.getSearchByDifficultyLevel() != 0) {
			whereClause.append("AND q.difficultyId = :difficultyId ");
		} 
		if(tableCommand.getSearchByTopic() != 0) {
			whereClause.append("AND q.topicId = :topicId ");
		} 
		if(tableCommand.getSearchByStatus() <= 1) {
			whereClause.append("AND q.status = :status ");
		}
		tableCommand.setWhereClause(whereClause.toString());
		switch(tableCommand.getSortColumnName()) {
			case "questionTypeDesc":
				tableCommand.setOrderClause("ORDER BY qt.questionTypeDesc "+tableCommand.getSortDirection()+" ");
				break;
			case "topicDesc":
				tableCommand.setOrderClause("ORDER BY tt.topicDesc "+tableCommand.getSortDirection()+" ");
				break;
			case "difficultyName":
				tableCommand.setOrderClause("ORDER BY dl.difficultyName "+tableCommand.getSortDirection()+" ");
				break;
			case "status":
				tableCommand.setOrderClause("ORDER BY q.status "+tableCommand.getSortDirection()+" ");
				break;
			case "label":
				tableCommand.setOrderClause("ORDER BY q.label "+tableCommand.getSortDirection()+" ");
				break;
			default:
				tableCommand.setOrderClause("ORDER BY q.createdAt "+tableCommand.getSortDirection()+" ");
				break;
		}
		return  this.questionDao.fetchQuestions(tableCommand);
	}
	
	@Override
	public int countFetchQuestions(TableCommand tableCommand) {
		StringBuffer whereClause = new StringBuffer().append("WHERE q.questionTypeId != 0 ");
		if(tableCommand.getSearchByQuestionType() != 0) {
			whereClause.append("AND q.questionTypeId = :questionTypeId ");
		} else if(tableCommand.getSearchByDifficultyLevel() != 0) {
			whereClause.append("AND q.difficultyId = :difficultyId ");
		} else if(tableCommand.getSearchByTopic() != 0) {
			whereClause.append("AND q.topicId = :topicId ");
		} else if(tableCommand.getSearchByStatus() <= 1) {
			whereClause.append("AND q.status = :status ");
		}
		tableCommand.setWhereClause(whereClause.toString());
		return this.questionDao.countFetchQuestions(tableCommand);
	}
	
	@Override
	public Question fetchQuestion(int questionId) {
		Question question = this.questionDao.fetchQuestion(questionId);
		question.setChoices(this.choiceDao.fetchChoices(questionId));
		question.setEnumerationAnswers(this.enumerationAnswerDao.fetchEnumerationAnswers(questionId));
		question.setAnalogyAnswers(this.analogyAnswerDao.fetchAnalogyAnswers(questionId));
		question.setFillInTheBlanksAnswers(this.fillInTheBlanksAnswerDao.fetchFillInTheBlanksAnswers(questionId));
		question.setOrderingAnswers(this.orderingAnswerDao.fetchOrderingAnswers(questionId));
		return question;
	}
	
	@Override
	public void updateQuestion(Question question) {
		this.questionDao.updateQuestion(question);
		switch(question.getQuestionTypeId()) {
			case LmsQuestionTypeData.MULTIPLE_CHOICE:
				this.choiceDao.deleteChoices(question.getQuestionId());
				List<Choice> newChoices = assignQuestionId(question.getQuestionId(),question.getChoices());
				this.choiceDao.createChoices(newChoices);
				break;
			case LmsQuestionTypeData.ENUMERATION:
				this.enumerationAnswerDao.deleteEnumerationAnswers(question.getQuestionId());
				List<EnumerationAnswer> enumerationAnswers=assignEnumerationQuestionId(question.getQuestionId(),
						question.getEnumerationAnswers());
				this.enumerationAnswerDao.createEnumerationAnswers(enumerationAnswers);
				break;
			case LmsQuestionTypeData.MATCHING:
				this.analogyAnswerDao.deleteAnalogyAnswers(question.getQuestionId());
				List<AnalogyAnswer> analogyAnswers = assignAnalogyAnswerQuestionId(question.getQuestionId(),
					question.getAnalogyAnswers());
				this.analogyAnswerDao.createAnalogyAnswers(analogyAnswers);
				break;
			case LmsQuestionTypeData.FILL_IN_THE_BLANK:
				this.fillInTheBlanksAnswerDao.deleteFillInTheBlanksAnswer(question.getQuestionId());
				List<FillInTheBlanksAnswer> fillInTheBlanksAnswers=assignFillInTheBlanksAnswerQuestionId(question.getQuestionId(),
					question.getFillInTheBlanksAnswers());
				this.fillInTheBlanksAnswerDao.createFillInTheBlanksAnswers(fillInTheBlanksAnswers);
				break;
			case LmsQuestionTypeData.ORDERING:
				this.orderingAnswerDao.deleteOrderingAnswers(question.getQuestionId());
				List<OrderingAnswer> orderingAnswers = assignOrderingAnswerQuestionId(question.getQuestionId(),
					question.getOrderingAnswers());
				this.orderingAnswerDao.createOrderingAnswer(orderingAnswers);
				break;
				
		}	
	}
	
	@Override
	public List<Question> fetchRandomQuestions(Question question) {
		return this.questionDao.fetchRandomQuestions(question);
	}
	
	@Override
	public int countTotalQuestions(Question question) {
		return this.questionDao.countTotalQuestions(question);
	}
	/**************************************************************************************************
	*								Start: Private Classes												*
	****************************************************************************************************/
	private List<Choice> assignQuestionId(int questionId,List<Choice> choices) {
		for(Choice choice : choices) {
			choice.setQuestionId(questionId);
		}
		return choices;
	}
	
	private List<EnumerationAnswer> assignEnumerationQuestionId(int questionId,List<EnumerationAnswer> enumerationAnswers) {
		for(EnumerationAnswer enumerationAnswer : enumerationAnswers) {
			enumerationAnswer.setQuestionId(questionId);
		}
		return enumerationAnswers;
	}
	
	private List<AnalogyAnswer> assignAnalogyAnswerQuestionId(int questionId,List<AnalogyAnswer> analogyAnswers) {
		for(AnalogyAnswer analogyAnswer : analogyAnswers) {
			analogyAnswer.setQuestionId(questionId);
		}
		return analogyAnswers;
	}
	
	private List<FillInTheBlanksAnswer> assignFillInTheBlanksAnswerQuestionId(int questionId, 
		List<FillInTheBlanksAnswer> fillInTheBlanksAnswers) {
		for(FillInTheBlanksAnswer fillInTheBlanksAnswer:fillInTheBlanksAnswers) {
			fillInTheBlanksAnswer.setQuestionId(questionId);
		}
		return fillInTheBlanksAnswers;
	}
	
	private List<OrderingAnswer> assignOrderingAnswerQuestionId(int questionId,
		List<OrderingAnswer> orderingAnswers) {
		for(OrderingAnswer orderingAnswer:orderingAnswers) {
			orderingAnswer.setQuestionId(questionId);
		}
		return orderingAnswers;
	}
	/**************************************************************************************************
	*								END: Private Classes												*
	****************************************************************************************************/
}
