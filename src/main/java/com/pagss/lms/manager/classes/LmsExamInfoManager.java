package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.dao.interfaces.ClassExamDao;
import com.pagss.lms.dao.interfaces.CourseExamDao;
import com.pagss.lms.dao.interfaces.ExamInfoDao;
import com.pagss.lms.dao.interfaces.ExamQuestionDao;
import com.pagss.lms.domains.ClassExam;
import com.pagss.lms.domains.CourseExam;
import com.pagss.lms.domains.ExamInfo;
import com.pagss.lms.domains.ExamQuestion;
import com.pagss.lms.manager.interfaces.ExamInfoManager;

@Component
public class LmsExamInfoManager implements ExamInfoManager {
	
	@Autowired
	private ExamInfoDao examInfoDao;
	@Autowired
	private ExamQuestionDao examQuestionDao;
	@Autowired
	private CourseExamDao courseExamDao;
	@Autowired
	private ClassExamDao classExamDao;
	
	@Override
	public List<ExamInfo> fetchExamInfoPages(TableCommand tableCommand) {
		int calculatedPageNo = tableCommand.getPageSize() * (tableCommand.getPageNumber()- 1);
		tableCommand.setPageNumber(calculatedPageNo);
		return this.examInfoDao.fetchExamInfoPages(tableCommand);
	}
	
	@Override
	public int countExamInfos(TableCommand tableCommand) {
		return this.examInfoDao.countExamInfos(tableCommand);
	}

	@Override
	public List<ExamInfo> searchExamInfo(int pageSize, int pageNo, String keyword) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.examInfoDao.searchExamInfo(pageSize, calculatedPageNo, keyword);
	}

	@Override
	public int countSearchExamInfo(String keyword) {
		return this.examInfoDao.countSearchExamInfo(keyword);
	}
	
	@Override
	public void createExamInfo(ExamInfo examInfo) {
		int examId = this.examInfoDao.createExamInfo(examInfo);
		List<ExamQuestion> examQuestions = setExamIdToList(examInfo.getExamQuestions(),examId);
		this.examQuestionDao.createExamQuestions(examQuestions);
		if(examInfo.getCourseId() != 0) {
			ExamInfo newExamInfo = this.examInfoDao.fetchExamInfo(examId);
			CourseExam courseExam = new CourseExam();
			courseExam.setExamType(newExamInfo.getExamType());
			courseExam.setDescription(newExamInfo.getDescription());
			courseExam.setTitle(newExamInfo.getTitle());
			courseExam.setCourseId(examInfo.getCourseId());
			courseExam.setExamId(newExamInfo.getExamId());
			this.courseExamDao.createCourseExam(courseExam);
		}else if(examInfo.getClassId() != 0) {
			ExamInfo newExamInfo = this.examInfoDao.fetchExamInfo(examId);
			ClassExam classExam = new ClassExam();
			classExam.setClassId(examInfo.getClassId());
			classExam.setExamId(newExamInfo.getExamId());
			this.classExamDao.addClassExam(classExam);
		}
	}
	
	@Override
	public int countExamCode(String examCode) {
		return this.examInfoDao.countExamCode(examCode);
	}

	@Override
	public ExamInfo generateExamCode() {
		ExamInfo examInfo = new ExamInfo();
		examInfo.setExamCode(generateExamCode(this.examInfoDao.fetchLatestExamId()));
		return examInfo;
	}
	
	@Override
	public ExamInfo fetchExamInfo(int examId) {
		return this.examInfoDao.fetchExamInfo(examId);
	}
	
	@Override
	public void updateExamInfo(ExamInfo examInfo) {
		this.examInfoDao.updateExamInfo(examInfo);
		this.examQuestionDao.deleteExamQuestions(examInfo.getExamId());
		List<ExamQuestion> examQuestions = setExamIdToList(examInfo.getExamQuestions(),examInfo.getExamId());
		this.examQuestionDao.createExamQuestions(examQuestions);
	}
	
	@Override
	public List<ExamInfo> fetchExamInfoPagesWithStatus(TableCommand tableCommand) {
		int calculatedPageNo = tableCommand.getPageSize() * (tableCommand.getPageNumber() - 1);
		tableCommand.setPageNumber(calculatedPageNo);
		return this.examInfoDao.fetchExamInfoPagesWithStatus(tableCommand);
	}
	
	@Override
	public int countExamPagesWithStatus(int courseId,int status) {
		return this.examInfoDao.countExamPagesWithStatus(courseId,status);
	}
	
	@Override
	public List<ExamInfo> searchExamInfoPagesWithStatus(TableCommand tableCommand) {
		int calculatedPageNo = tableCommand.getPageSize() * (tableCommand.getPageNumber() - 1);
		tableCommand.setPageNumber(calculatedPageNo);
		return this.examInfoDao.searchExamInfoPagesWithStatus(tableCommand);
	}
	
	@Override
	public int countSearchExamInfoPagesWithStatus(TableCommand tableCommand) {
		return this.examInfoDao.countSearchExamInfoPagesWithStatus(tableCommand);
	}

	@Override
	public List<ExamInfo> fetchExamInfosWithStatus(ExamInfo examInfo) {
		return this.examInfoDao.fetchExamInfosWithStatus(examInfo);
	}
	/**************************************************************************************************
	*								Start: Private Classes												*
	****************************************************************************************************/
	private String generateExamCode(int examId) {
		String examNo = Integer.toString(10000 + examId).substring(1);
		return "EC" + examNo;
	}
	
	private List<ExamQuestion> setExamIdToList(List<ExamQuestion> examQuestions,int examId) {
		for(ExamQuestion examQuestion: examQuestions) {
			examQuestion.setExamId(examId);
		}
		return examQuestions;
	}
	/**************************************************************************************************
	*								End: Private Classes												*
	****************************************************************************************************/

	@Override
	public List<ExamInfo> searchExamInfosPageOnClassWithStatus(TableCommand tableCommand) {
		int calculatedPageNo = tableCommand.getPageSize() * (tableCommand.getPageNumber() - 1);
		tableCommand.setPageNumber(calculatedPageNo);
		return this.examInfoDao.searchExamInfosPageOnClassWithStatus(tableCommand);
	}

	@Override
	public int countSearchExamInfosPageOnClassWithStatus(TableCommand tableCommand) {
		return this.examInfoDao.countSearchExamInfosPageOnClassWithStatus(tableCommand);
	}

	@Override
	public List<ExamInfo> fetchExamInfosPageOnClassWithStatus(TableCommand tableCommand) {
		int calculatedPageNo = tableCommand.getPageSize() * (tableCommand.getPageNumber() - 1);
		tableCommand.setPageNumber(calculatedPageNo);
		return this.examInfoDao.fetchExamInfosPageOnClassWithStatus(tableCommand);
	}

	@Override
	public int countxamInfosPageOnClassWithStatus(TableCommand tableCommand) {
		return this.examInfoDao.countxamInfosPageOnClassWithStatus(tableCommand);
	}
}
