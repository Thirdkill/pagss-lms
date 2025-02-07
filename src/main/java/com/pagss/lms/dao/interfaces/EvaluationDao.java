package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.domains.EmployeeEvaluation;
import com.pagss.lms.domains.Evaluation;
import com.pagss.lms.domains.EvaluationQuestion;

public interface EvaluationDao {

	public List<Evaluation> fetchEvaluationTable(int pageSize, int calculatedPageNo);

	public int countTotalEvaluations();

	public List<Evaluation> fetchSearchedEvaluationTable(Evaluation evaluation, int pageSize, int calculatedPageNo);

	public int countTotalSearchedEvaluations(Evaluation evaluation);

	public int fetchLatestEvaluationId();

	public int countEvaluationCode(Evaluation eval);

	public int countEvaluationQuestionsById(int evaluationId);

	public void deleteEvaluationQuestionById(int evaluationId);

	public void addEvaluationQuestion(List<EvaluationQuestion> evaluationquestion);

	public void updateEvaluationInfo(Evaluation evaluation);

	public Evaluation fetchEvaluationInfo(int evaluationId);

	public List<EvaluationQuestion> fetchEvaluationQuestions(int evaluationId);

	public List<Evaluation> fetchEvaluationOnCourseTable(int courseId,int pageSize, int pageNo);

	public List<Evaluation> fetchSearchedEvaluationOnCourseTable(int courseId, Evaluation evaluation, int pageSize, int pageNo);

	public int countTotalEvaluationsOnCourse(int courseId);

	public int countTotalSearchedEvaluationsOnCourse(int courseId, Evaluation evaluation);

	public List<Evaluation> fetchEvaluationsOnClass(TableCommand tableCommand, int pageSize, int calculatedPageNo);

	public int countTotalEvaluationsOnClass(TableCommand tableCommand);

	public List<Evaluation> searchEvaluationsOnClass(TableCommand tableCommand, int pageSize, int calculatedPageNo);

	public int countTotalSearchEvaluationsOnClass(TableCommand tableCommand);

	public List<EmployeeEvaluation> fetchEmployeeEvaluations(int classEvaluationId, int pageSize, int calculatedPageNo);

	public int countTotalEmployeeEvaluations(int classEvaluationId);

}
