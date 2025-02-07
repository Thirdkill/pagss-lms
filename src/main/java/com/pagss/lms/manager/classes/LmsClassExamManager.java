package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.ClassExamDao;
import com.pagss.lms.domains.ClassExam;
import com.pagss.lms.manager.interfaces.ClassExamManager;

@Component
public class LmsClassExamManager implements ClassExamManager{
	
	@Autowired
	private ClassExamDao classExamDao;

	@Override
	public List<ClassExam> fetchClassExamList(int classId, int pageNumber, int pageSize) {
		int calculatedPageNo = pageSize * (pageNumber - 1);
		return this.classExamDao.fetchClassExamList(classId, calculatedPageNo, pageSize);
	}

	@Override
	public int countClassExamList(int classId) {
		return this.classExamDao.countClassExamList(classId);
	}

	@Override
	public void createClassExam(List<ClassExam> classExams) {
		this.classExamDao.createClassExam(classExams);
	}

	@Override
	public void updateClassExam(ClassExam classExam) {
		this.classExamDao.updateClassExam(classExam);
	}

	@Override
	public void deleteClassExam(int classExamId) {
		this.classExamDao.deleteClassExam(classExamId);
	}

	@Override
	public List<ClassExam> fetchClassExams(int classId,int employeeId) {
		return this.classExamDao.fetchClassExams(classId, employeeId);
	}

	@Override
	public ClassExam fetchClassExam(int classId, int examId) {
		return this.classExamDao.fetchClassExam(classId, examId);
	}
}
