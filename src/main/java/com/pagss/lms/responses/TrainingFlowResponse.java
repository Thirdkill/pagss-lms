package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.TrainingFlow;

import lombok.Getter;
import lombok.Setter;

public class TrainingFlowResponse {

	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private TrainingFlow trainingFlow;
	@Getter @Setter private List<TrainingFlow> trainingFlows; 
	
	public TrainingFlowResponse(int status) {
		setStatus(status);
	}
	
	public TrainingFlowResponse(int status,TrainingFlow trainingFlow) {
		setStatus(status);
		setTrainingFlow(trainingFlow);
	}
	
	public TrainingFlowResponse(int status,List<TrainingFlow> trainingFlows) {
		setStatus(status);
		setTrainingFlow(trainingFlow);
		setTrainingFlows(trainingFlows);
	}
	
	public TrainingFlowResponse(int status,List<TrainingFlow> trainingFlows,int totalRecords) {
		setStatus(status);
		setTrainingFlow(trainingFlow);
		setTrainingFlows(trainingFlows);
		setTotalRecords(totalRecords);
	}
}
