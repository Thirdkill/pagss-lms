package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.CourseInfo;
import com.pagss.lms.domains.TrainingFlow;

public interface TrainingFlowDao {

	public List<TrainingFlow> fetchTrainingFlowsPage(int pageSize, int pageNo,TrainingFlow trainingFlow);
	
	public int countFetchTrainingFlows(int assignmentType);
	
	public List<TrainingFlow> fetchTrainingFlows(TrainingFlow trainingFlow);
	
	public void updateTrainingFlow(TrainingFlow trainingFlow);
	
	public void updatePreCourseIds(int trainingFlowId);
	
	public void createTrainingFlows(List<CourseInfo> courseInfo);

	public List<TrainingFlow> fetchTrainingFlowList(TrainingFlow trainingFlow);

	public List<TrainingFlow> fetchTrainingFlowUserGroupList(TrainingFlow trainingFlow);
	
	public void updateTrainingFLowOrderNos(List<TrainingFlow> trainingFlows);
	
	public void updateTrainingFlowPreCourseIds(List<TrainingFlow> trainingFlows);
}
