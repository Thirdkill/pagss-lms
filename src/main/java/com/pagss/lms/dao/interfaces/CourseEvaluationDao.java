package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.CourseEvaluation;
import com.pagss.lms.domains.EmployeeEvaluation;

public interface CourseEvaluationDao {

	public List<CourseEvaluation> fetchCourseEvaluations(int courseId, int employeeId);

	public void deleteCourseEvaluation(int courseEvaluationId);

	public void addCourseEvaluations(List<CourseEvaluation> courseEvaluations);

	public List<CourseEvaluation> fetchCourseEvaluationsTable(int courseId, int calculatedPageNo, int pageSize);

	public int countTotalCourseEvaluations(int courseId);

	public CourseEvaluation fetchEmployeeCoursePath(int classId, int evaluationId);
	
	public List<EmployeeEvaluation>fetchEmployeeEvaluation(int classId, int employeeId,
			int evaluationId, int courseId);
}
