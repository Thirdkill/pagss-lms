package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.constants.LmsTrainingFlowData;
import com.pagss.lms.dao.interfaces.TrainingFlowDao;
import com.pagss.lms.domains.CourseInfo;
import com.pagss.lms.domains.TrainingFlow;
import com.pagss.lms.manager.interfaces.TrainingFlowManager;

@Component
public class LmsTrainingFlowManager implements TrainingFlowManager {
	
	private TrainingFlowDao trainingFlowDao;
	/*******************************************************************************************************
	 * Start: Autowired Setters	
	 ******************************************************************************************************/
	@Autowired
	public void setTrainingFlowDao(TrainingFlowDao trainingFlowDao) {
		this.trainingFlowDao = trainingFlowDao;
	}
	/*******************************************************************************************************
	 * End: Autowired Setters	
	 ******************************************************************************************************/
	@Override
	public List<TrainingFlow> fetchTrainingFlowsPage(int pageSize, int pageNo,TrainingFlow trainingFlow) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		if(trainingFlow.getAssignmentType() == LmsTrainingFlowData.JOBROLE) {
			trainingFlow.setWhereClause("AND tf.jobRoleId = :jobRoleId ");
		} else {
			trainingFlow.setWhereClause("AND tf.userGroupId = :userGroupId ");
		}
		return this.trainingFlowDao.fetchTrainingFlowsPage(pageSize, calculatedPageNo, trainingFlow);
	}
	
	@Override
	public int countFetchTrainingFlows(int assignmentType) {
		return this.trainingFlowDao.countFetchTrainingFlows(assignmentType);
	}
	
	@Override
	public List<TrainingFlow> fetchTrainingFlows(TrainingFlow trainingFlow) {
		if(trainingFlow.getAssignmentType() == LmsTrainingFlowData.JOBROLE) {
			trainingFlow.setWhereClause("AND tf.jobRoleId = :jobRoleId ");
		} else {
			trainingFlow.setWhereClause("AND tf.userGroupId = :userGroupId ");
		}
		return this.trainingFlowDao.fetchTrainingFlows(trainingFlow);
	}
	
	@Override
	public void updateTrainingFlow(TrainingFlow trainingFlow) {
		this.trainingFlowDao.updateTrainingFlow(trainingFlow);
	}
	
	@Override
	public void updatePreCourseIds(int trainingFlowId) {
		this.trainingFlowDao.updatePreCourseIds(trainingFlowId);
	}

	@Override
	public void createTrainingFlows(List<CourseInfo> courseInfos) {
		this.trainingFlowDao.createTrainingFlows(courseInfos);
	}
	
	@Override
	public List<TrainingFlow> fetchTrainingFlowList(TrainingFlow trainingFlow) {
		return this.trainingFlowDao.fetchTrainingFlowList(trainingFlow);
	}
	
	@Override
	public List<TrainingFlow> fetchTrainingFlowUserGroupList(TrainingFlow trainingFlow) {
		return this.trainingFlowDao.fetchTrainingFlowUserGroupList(trainingFlow);
	}
	
	@Override
	public void updateTrainingFLowOrderNos(List<TrainingFlow> trainingFlows) {
		this.trainingFlowDao.updateTrainingFlowPreCourseIds(trainingFlows);
		this.trainingFlowDao.updateTrainingFLowOrderNos(trainingFlows);	
	}
}
