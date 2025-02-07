package com.pagss.lms.manager.classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.ClassEvaluationDao;
import com.pagss.lms.domains.ClassEvaluation;
import com.pagss.lms.domains.EmployeeEvaluation;
import com.pagss.lms.manager.interfaces.ClassEvaluationManager;
import com.pagss.lms.spring.data.repositories.EmployeeEvaluationRepository;

@Component
public class LmsClassEvaluationManager implements ClassEvaluationManager{
	
	@Autowired
	private ClassEvaluationDao classEvaluationDao;
	@Autowired
	private EmployeeEvaluationRepository employeeEvaluationRepository;

	@Override
	public List<ClassEvaluation> fetchClassEvaluationList(int classId, int pageNumber, int pageSize) {
		int calculatedPageNo = pageSize * (pageNumber - 1);
		return this.classEvaluationDao.fetchClassEvaluationList(classId,calculatedPageNo,pageSize);
	}

	@Override
	public int countTotalClassEvaluation(int classId) {
		return this.classEvaluationDao.countTotalClassEvaluation(classId);
	}

	@Override
	public void addClassEvaluation(List<ClassEvaluation> classEvaluation) {
		this.classEvaluationDao.addClassEvaluation(classEvaluation);
	}

	@Override
	public void deleteClassEvaluation(int classEvaluationId) {
		this.classEvaluationDao.deleteClassEvaluation(classEvaluationId);
	}
	
	@Override
	public List<ClassEvaluation> fetchEmployeeClassEvaluation(int classId, int employeeId) {
		return this.classEvaluationDao.fetchEmployeeClassEvaluationList(classId, employeeId);
	}
	
	public ClassEvaluation fetchEmployeeClassPath(int classId, int evaluationId) {
		return this.classEvaluationDao.fetchEmployeeClassPath(classId, evaluationId);
	}

	@Override
	public List<EmployeeEvaluation> insertEmployeeAnswer(List<EmployeeEvaluation> empEvaluation, int employeeId) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		
		for(EmployeeEvaluation emp: empEvaluation) {
			emp.setEmployeeId(employeeId);
			emp.setDateCompleted(dateFormat.format(now));
		}
		empEvaluation = (List<EmployeeEvaluation>) this.employeeEvaluationRepository.saveAll(empEvaluation);
		return empEvaluation;
	}

	@Override
	public List<EmployeeEvaluation> fetchEmployeeAnswers(int classId, int employeeId, int evaluationId) {
		return this.classEvaluationDao.fetchEmployeeAnswers(classId, employeeId, evaluationId);
	}
}
