package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.domains.ExamInfo;

public interface ExamInfoDao {

	public List<ExamInfo> fetchExamInfoPages(TableCommand tableCommand);
	
	public int countExamInfos(TableCommand tableCommand);
	
	public List<ExamInfo> searchExamInfo(int pageSize, int pageNo,String keyword);
	
	public int countSearchExamInfo(String keyword);
	
	public int createExamInfo(ExamInfo examInfo);
	
	public int countExamCode(String examCode);
	
	public int fetchLatestExamId();
	
	public ExamInfo fetchExamInfo(int examId);
	
	public void updateExamInfo(ExamInfo examInfo);
	
	public List<ExamInfo> fetchExamInfoPagesWithStatus(TableCommand tableCommand);
	
	public int countExamPagesWithStatus(int courseId,int status);
	
	public List<ExamInfo> searchExamInfoPagesWithStatus(TableCommand tableCommand);
	
	public int countSearchExamInfoPagesWithStatus(TableCommand tableCommand);
	
	public List<ExamInfo> fetchExamInfosWithStatus(ExamInfo examInfo);

	public List<ExamInfo> searchExamInfosPageOnClassWithStatus(TableCommand tableCommand);

	public int countSearchExamInfosPageOnClassWithStatus(TableCommand tableCommand);

	public List<ExamInfo> fetchExamInfosPageOnClassWithStatus(TableCommand tableCommand);

	public int countxamInfosPageOnClassWithStatus(TableCommand tableCommand);
}
