package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.EmployeeExamSummary;

public interface EmployeeExamSummaryDao {

	public List<EmployeeExamSummary> fetchExamScores(int examId, int calculatedPageNo, int pageSize);

	public int countExamScores(int examId);

}
