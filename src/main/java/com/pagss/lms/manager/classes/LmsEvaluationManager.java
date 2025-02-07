package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.dao.interfaces.EmployeeInfoDao;
import com.pagss.lms.dao.interfaces.EvaluationDao;
import com.pagss.lms.domains.EmployeeEvaluation;
import com.pagss.lms.domains.EmployeeInfo;
import com.pagss.lms.domains.Evaluation;
import com.pagss.lms.domains.EvaluationQuestion;
import com.pagss.lms.manager.interfaces.EvaluationManager;

@Component
public class LmsEvaluationManager implements EvaluationManager {
	
	@Autowired
	private EvaluationDao evaluationDao;
	@Autowired
	private EmployeeInfoDao evaluationInfoDao;

	@Override
	public List<Evaluation> fetchEvaluationTable(int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.evaluationDao.fetchEvaluationTable(pageSize, calculatedPageNo);
	}

	@Override
	public int countTotalEvaluations() {
		return this.evaluationDao.countTotalEvaluations();
	}

	@Override
	public List<Evaluation> fetchSearchedEvaluationTable(String keyword, int pageSize, int pageNo) {
		Evaluation evaluation = new Evaluation();
		evaluation.setTitle(keyword);
		evaluation.setModifiedBy(keyword);
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.evaluationDao.fetchSearchedEvaluationTable(evaluation,pageSize, calculatedPageNo);
	}

	@Override
	public int countTotalSearchedEvaluations(String keyword) {
		Evaluation evaluation = new Evaluation();
		evaluation.setTitle(keyword);
		evaluation.setModifiedBy(keyword);
		return this.evaluationDao.countTotalSearchedEvaluations(evaluation);
	}

	@Override
	public Evaluation generateEvaluationCode() {
		Evaluation eval = new Evaluation();
		eval.setEvaluationCode(generateEvaluationCode(this.evaluationDao.fetchLatestEvaluationId()));
		eval.setEvaluationId(this.evaluationDao.fetchLatestEvaluationId());
		
		return eval;
	}
	
	private String generateEvaluationCode(int evaluationId) {
		String evalNo = Integer.toString(10000 + evaluationId).substring(1);
		return "EVC" + evalNo;
	}

	@Override
	public int checkEvaluationCode(String evaluationCode) {
		Evaluation eval = new Evaluation();
		eval.setEvaluationCode(evaluationCode);
		return this.evaluationDao.countEvaluationCode(eval);
	}

	@Override
	public EmployeeInfo fetchEmployeeInfo(EmployeeInfo empinfo) {
		return this.evaluationInfoDao.fetchEmployeeInfo(empinfo);
	}

	@Override
	public void evaluationQuestions(List<EvaluationQuestion> evaluationquestion, int evaluationId) {
		int questionCount = this.evaluationDao.countEvaluationQuestionsById(evaluationId);
		
		if(questionCount > 0) {
			this.evaluationDao.deleteEvaluationQuestionById(evaluationId);
			this.evaluationDao.addEvaluationQuestion(evaluationquestion);
		} else if(questionCount == 0) {
			this.evaluationDao.addEvaluationQuestion(evaluationquestion);
		} 
		
	}

	@Override
	public void updateEvaluationInfo(Evaluation evaluation, int evaluationId) {
		evaluation.setEvaluationId(evaluationId);
		this.evaluationDao.updateEvaluationInfo(evaluation);
	}

	@Override
	public Evaluation fetchEvaluationInfo(int evaluationId) {
		return this.evaluationDao.fetchEvaluationInfo(evaluationId);
	}

	@Override
	public List<EvaluationQuestion> fetchEvaluationQuestions(int evaluationId) {
		return this.evaluationDao.fetchEvaluationQuestions(evaluationId);
	}

	@Override
	public List<Evaluation> fetchEvaluationOnCourseTable(int courseId, int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.evaluationDao.fetchEvaluationOnCourseTable(courseId, pageSize, calculatedPageNo);
	}

	@Override
	public List<Evaluation> fetchSearchedEvaluationOnCourseTable(int courseId, String keyword, int pageSize, int pageNo) {
		Evaluation evaluation = new Evaluation();
		evaluation.setTitle(keyword);
		evaluation.setModifiedBy(keyword);
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.evaluationDao.fetchSearchedEvaluationOnCourseTable(courseId,evaluation, pageSize, calculatedPageNo);
	}

	@Override
	public int countTotalEvaluationsOnCourse(int courseId) {
		return this.evaluationDao.countTotalEvaluationsOnCourse(courseId);
	}

	@Override
	public int countTotalSearchedEvaluationsOnCourse(int courseId,String keyword) {
		Evaluation evaluation = new Evaluation();
		evaluation.setTitle(keyword);
		evaluation.setModifiedBy(keyword);
		return this.evaluationDao.countTotalSearchedEvaluationsOnCourse(courseId,evaluation);
	}

	@Override
	public List<Evaluation> fetchEvaluationsOnClass(TableCommand tableCommand, int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.evaluationDao.fetchEvaluationsOnClass(tableCommand, pageSize, calculatedPageNo);
	}

	@Override
	public int countTotalEvaluationsOnClass(TableCommand tableCommand) {
		return this.evaluationDao.countTotalEvaluationsOnClass(tableCommand);
	}

	@Override
	public List<Evaluation> searchEvaluationsOnClass(TableCommand tableCommand, int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.evaluationDao.searchEvaluationsOnClass(tableCommand, pageSize, calculatedPageNo);
	}

	@Override
	public int countTotalSearchEvaluationsOnClass(TableCommand tableCommand) {
		return this.evaluationDao.countTotalSearchEvaluationsOnClass(tableCommand);
	}

	@Override
	public List<EmployeeEvaluation> fetchEmployeeEvaluations(int classEvaluationId, int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.evaluationDao.fetchEmployeeEvaluations(classEvaluationId, pageSize, calculatedPageNo);
	}

	@Override
	public int countTotalEmployeeEvaluations(int classEvaluationId) {
		return this.evaluationDao.countTotalEmployeeEvaluations(classEvaluationId);
	}
}
