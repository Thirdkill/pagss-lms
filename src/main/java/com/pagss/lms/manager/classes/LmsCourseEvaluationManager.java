package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.CourseEvaluationDao;
import com.pagss.lms.domains.CourseEvaluation;
import com.pagss.lms.domains.EmployeeEvaluation;
import com.pagss.lms.manager.interfaces.CourseEvaluationManager;

@Component
public class LmsCourseEvaluationManager implements CourseEvaluationManager {
	
	@Autowired
	private CourseEvaluationDao courseEvaluationDao;

	@Override
	public List<CourseEvaluation> fetchCourseEvaluations(int courseId, int employeeId) {
		return this.courseEvaluationDao.fetchCourseEvaluations(courseId, employeeId);
	}

	@Override
	public void deleteCourseEvaluation(int courseEvaluationId) {
		this.courseEvaluationDao.deleteCourseEvaluation(courseEvaluationId);
	}

	@Override
	public void addCourseEvaluations(List<CourseEvaluation> courseEvaluations) {
		this.courseEvaluationDao.addCourseEvaluations(courseEvaluations);
	}

	@Override
	public List<CourseEvaluation> fetchCourseEvaluationsTable(int courseId, int pageNo, int pageSize) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.courseEvaluationDao.fetchCourseEvaluationsTable(courseId,calculatedPageNo,pageSize);
	}

	@Override
	public int countTotalCourseEvaluations(int courseId) {
		return this.courseEvaluationDao.countTotalCourseEvaluations(courseId);
	}

	@Override
	public CourseEvaluation fetchEmployeeCoursePath(int classId, int evaluationId) {
		return this.courseEvaluationDao.fetchEmployeeCoursePath(classId, evaluationId);
	}
	
	@Override
	
	public List<EmployeeEvaluation> fetchEmployeeEvaluation(int classId,
			int employeeId, int evaluationId, int courseId) {
		return this.courseEvaluationDao.fetchEmployeeEvaluation(classId, employeeId, evaluationId, courseId);
	}

}
