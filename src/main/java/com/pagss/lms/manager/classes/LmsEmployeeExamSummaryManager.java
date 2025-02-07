package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.EmployeeExamSummaryDao;
import com.pagss.lms.domains.EmployeeExamSummary;
import com.pagss.lms.manager.interfaces.EmployeeExamSummaryManager;

@Component
public class LmsEmployeeExamSummaryManager implements EmployeeExamSummaryManager {
	
	@Autowired
	private EmployeeExamSummaryDao employeeScoreExamDao;

	@Override
	public List<EmployeeExamSummary> fetchExamScores(int examId, int pageNo, int pageSize) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.employeeScoreExamDao.fetchExamScores(examId, calculatedPageNo, pageSize);
	}

	@Override
	public int countExamScores(int examId) {
		return this.employeeScoreExamDao.countExamScores(examId);
	}

}
