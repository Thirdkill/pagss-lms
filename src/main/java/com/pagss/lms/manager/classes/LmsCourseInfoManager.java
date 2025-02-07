package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.constants.LmsCourseInfoData;
import com.pagss.lms.constants.LmsTrainingFlowData;
import com.pagss.lms.dao.interfaces.ClassDefaultDao;
import com.pagss.lms.dao.interfaces.CourseInfoDao;
import com.pagss.lms.domains.CourseInfo;
import com.pagss.lms.manager.interfaces.CourseInfoManager;

@Component
public class LmsCourseInfoManager implements CourseInfoManager {

	@Autowired
	private CourseInfoDao courseInfoDao;
	@Autowired
	private ClassDefaultDao classDefaultDao;
	
	@Override
	public List<CourseInfo> fetchCourseInfoPages(int pageNumber, int pageSize) {
		int calculatedPageNo = pageSize * (pageNumber - 1);
		return this.courseInfoDao.fetchCourseInfoPages(calculatedPageNo, pageSize);
	}	
	
	@Override
	public int countFetchCourseInfos() {
		return this.courseInfoDao.countFetchCourseInfos();
	}

	@Override
	public List<CourseInfo> searchCourseInfos(int pageNumber, int pageSize, String keyword) {
		int calculatedPageNo = pageSize * (pageNumber - 1);
		int deliveryMethod = 0;
		if(LmsCourseInfoData.INDIVIDUAL_STRING.toLowerCase().contains(keyword.toLowerCase())) {
			deliveryMethod = 1;
		} else if(LmsCourseInfoData.MODULAR_STRING.toLowerCase().contains(keyword.toLowerCase())) {
			deliveryMethod = 2;
		} else if(LmsCourseInfoData.CLASSTRAINING_STRING.toLowerCase().contains(keyword.toLowerCase())) {
			deliveryMethod = 3;
		} else {
			deliveryMethod = 0;
		}
		return this.courseInfoDao.searchCourseInfos(calculatedPageNo, pageSize, keyword,deliveryMethod);
	}

	@Override
	public int countSearchCourseInfos(String keyword) {
		return this.courseInfoDao.countSearchCourseInfos(keyword);
	}
	
	@Override
	public int createCourseInfo(CourseInfo courseInfo) {
		int courseId = this.courseInfoDao.createCourseInfo(courseInfo);
		courseInfo.setCourseId(courseId);
		this.classDefaultDao.createClassDefault(courseInfo);
		return courseId;
	}

	@Override
	public int fetchLatestId() {
		return this.courseInfoDao.fetchLatestId();
	}
	
	@Override
	public int countCourseCode(String courseCode) {
		return this.courseInfoDao.countCourseCode(courseCode);
	}

	@Override
	public CourseInfo fetchCourseInfo(int courseId) {
		return this.courseInfoDao.fetchCourseInfo(courseId);
	}
	
	@Override
	public void updateCourseInfo(CourseInfo courseInfo) {
		this.courseInfoDao.updateCourseInfo(courseInfo);
		this.classDefaultDao.updateClassDefault(courseInfo);
	}

	@Override
	public List<CourseInfo> fetchCourseInfoPagesWithStatus(TableCommand tableCommand) {
		int calculatedPageNo = tableCommand.getPageSize() * (tableCommand.getPageNumber()-1);
		if(tableCommand.getAssignmentType() == LmsTrainingFlowData.JOBROLE) {
			tableCommand.setWhereClause("AND tf.jobroleId = :jobRoleId)");
		} else {
			tableCommand.setWhereClause("AND tf.userGroupId = :userGroupId)");
		}
		tableCommand.setPageNumber(calculatedPageNo);
		return this.courseInfoDao.fetchCourseInfoPagesWithStatus(tableCommand);
	}
	
	@Override
	public int countFetchCourseInfosWithStatus(TableCommand tableCommand) {
		if(tableCommand.getAssignmentType() == LmsTrainingFlowData.JOBROLE) {
			tableCommand.setWhereClause("AND tf.jobroleId = :jobRoleId)");
		} else {
			tableCommand.setWhereClause("AND tf.userGroupId = :userGroupId)");
		}
		return this.courseInfoDao.countFetchCourseInfosWithStatus(tableCommand);
	}
	
	@Override
	public List<CourseInfo> searchTrainingFlowCourseInfos(TableCommand tableCommand) {
		int calculatedPageNo = tableCommand.getPageSize() * (tableCommand.getPageNumber() - 1);
		int deliveryMethod = 0;
		if(LmsCourseInfoData.INDIVIDUAL_STRING.toLowerCase().contains(tableCommand.getKeyword().toLowerCase())) {
			deliveryMethod = 1;
		} else if(LmsCourseInfoData.MODULAR_STRING.toLowerCase().contains(tableCommand.getKeyword().toLowerCase())) {
			deliveryMethod = 2;
		} else if(LmsCourseInfoData.CLASSTRAINING_STRING.toLowerCase().contains(tableCommand.getKeyword().toLowerCase())) {
			deliveryMethod = 3;
		} else {
			deliveryMethod = 0;
		}
		if(tableCommand.getAssignmentType() == LmsTrainingFlowData.JOBROLE) {
			tableCommand.setWhereClause("AND tf.jobRoleId = :jobRoleId ");
		} else {
			tableCommand.setWhereClause("AND tf.userGroupId = :userGroupId ");
		}
		tableCommand.setPageNumber(calculatedPageNo);
		tableCommand.setDeliveryMethod(deliveryMethod);
		return this.courseInfoDao.searchTrainingFlowCourseInfos(tableCommand);
	}

	@Override
	public int countTrainingFlowCourseInfos(TableCommand tableCommand) {
		int deliveryMethod = 0;
		if(LmsCourseInfoData.INDIVIDUAL_STRING.toLowerCase().contains(tableCommand.getKeyword().toLowerCase())) {
			deliveryMethod = 1;
		} else if(LmsCourseInfoData.MODULAR_STRING.toLowerCase().contains(tableCommand.getKeyword().toLowerCase())) {
			deliveryMethod = 2;
		} else if(LmsCourseInfoData.CLASSTRAINING_STRING.toLowerCase().contains(tableCommand.getKeyword().toLowerCase())) {
			deliveryMethod = 3;
		} else {
			deliveryMethod = 0;
		}
		if(tableCommand.getAssignmentType() == LmsTrainingFlowData.JOBROLE) {
			tableCommand.setWhereClause("AND tf.jobRoleId = :jobRoleId)");
		} else {
			tableCommand.setWhereClause("AND tf.userGroupId = :userGroupId)");
		}
		tableCommand.setDeliveryMethod(deliveryMethod);
		return this.courseInfoDao.countTrainingFlowCourseInfos(tableCommand);
	}

	@Override
	public List<CourseInfo> fetchActiveCourseInfoList() {
		return this.courseInfoDao.fetchActiveCourseInfoList();
	}
	
	@Override
	public int countFetchCourseInfosWithStatus(int status) {
		return this.courseInfoDao.countFetchCourseInfosWithStatus(status);
	}
}
