package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.CourseExamDao;
import com.pagss.lms.domains.CourseExam;
import com.pagss.lms.manager.interfaces.CourseExamManager;

@Component
public class LmsCourseExamManager implements CourseExamManager {

	@Autowired
	private CourseExamDao courseExamDao;
	
	@Override
	public List<CourseExam> fetchCourseExamPages(int pageSize, int pageNumber, int courseId) {
		int calculatedPageNo = pageSize * (pageNumber - 1);
		return this.courseExamDao.fetchCourseExamPages(pageSize, calculatedPageNo, courseId);
	}
	
	@Override
	public int countTotalCourseExams(int courseId) {
		return this.courseExamDao.countTotalCourseExams(courseId);
	}

	@Override
	public int createCourseExam(CourseExam courseExam) {
		return this.courseExamDao.createCourseExam(courseExam);
	}
	
	@Override
	public void createCourseExams(List<CourseExam> courseExams) {
		this.courseExamDao.createCourseExams(courseExams);
	}
	
	@Override
	public void deleteCourseExam(int courseExamId) {
		this.courseExamDao.deleteCourseExam(courseExamId);
	}

	@Override
	public List<CourseExam> fetchCourseExamsWithExamType(int courseId,int examType) {
		return this.courseExamDao.fetchCourseExamsWithExamType(courseId, examType);
	}

	@Override
	public void updateCourseExam(CourseExam courseExam) {
		this.courseExamDao.updateCourseExam(courseExam);
	}

	@Override
	public List<CourseExam> fetchCourseExams(int courseId,int classId,int employeeId) {
		return this.courseExamDao.fetchCourseExams(courseId,classId,employeeId);
	}
}
