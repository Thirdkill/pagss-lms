package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.ClassExam;

public interface ClassExamDao {

	public List<ClassExam> fetchClassExamList(int classId, int calculatedPageNo, int pageSize);

	public int countClassExamList(int classId);

	public void createClassExam(List<ClassExam> classExams);

	public void updateClassExam(ClassExam classExam);

	public void addClassExam(ClassExam classExam);

	public void deleteClassExam(int classExamId);
	
	public List<ClassExam> fetchClassExams(int classId,int employeeId);
	
	public ClassExam fetchClassExam(int classId,int examId);
}
