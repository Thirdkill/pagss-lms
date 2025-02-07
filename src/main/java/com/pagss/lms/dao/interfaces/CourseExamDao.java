package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.CourseExam;

public interface CourseExamDao {

	public List<CourseExam> fetchCourseExamPages(int pageSize,int pageNumber,int courseId);
	
	public int countTotalCourseExams(int courseId);
	
	public int createCourseExam(CourseExam courseExam);
	
	public void createCourseExams(List<CourseExam> courseExams);
	
	public void deleteCourseExam(int courseExamId);
	
	public List<CourseExam> fetchCourseExamsWithExamType(int courseId,int examType);
	
	public void updateCourseExam(CourseExam courseExam);
	
	public List<CourseExam> fetchCourseExams(int courseId,int classId,int employeeId);
}
