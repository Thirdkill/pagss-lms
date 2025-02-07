package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.domains.CourseInfo;

public interface CourseInfoDao {

	public List<CourseInfo> fetchCourseInfoPages(int pageNumber,int pageSize);
	
	public int countFetchCourseInfos();
	
	public List<CourseInfo> searchCourseInfos(int pageNumber,int pageSize, String keyword,int deliveryMethod);
	
	public int countSearchCourseInfos(String keyword);
	
	public List<CourseInfo> searchTrainingFlowCourseInfos(TableCommand tableCommand);
	
	public int countTrainingFlowCourseInfos(TableCommand tableCommand);
	
	public int createCourseInfo(CourseInfo courseInfo);
	
	public int fetchLatestId();
	
	public int countCourseCode(String courseCode);
	
	public CourseInfo fetchCourseInfo(int courseId);
	
	public void updateCourseInfo(CourseInfo courseInfo);
	
	public List<CourseInfo> fetchCourseInfoPagesWithStatus(TableCommand tableCommand);

	public int countFetchCourseInfosWithStatus(int status);

	public List<CourseInfo> fetchActiveCourseInfoList();

	public int countFetchCourseInfosWithStatus(TableCommand tableCommand);
}
