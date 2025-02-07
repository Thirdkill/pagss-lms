package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.ClassEvaluation;
import com.pagss.lms.domains.EmployeeEvaluation;

public interface ClassEvaluationDao {

	public List<ClassEvaluation> fetchClassEvaluationList(int classId, int calculatedPageNo, int pageSize);

	public int countTotalClassEvaluation(int classId);

	public void addClassEvaluation(List<ClassEvaluation> classEvaluation);

	public void deleteClassEvaluation(int classEvaluationId);

	public List<ClassEvaluation> fetchEmployeeClassEvaluationList(int classId, int employeeId);
	
	public ClassEvaluation fetchEmployeeClassPath(int classId, int evaluationId);
	
	public List<EmployeeEvaluation> fetchEmployeeAnswers(int classId, int employeeId, int evaluationId);

}
