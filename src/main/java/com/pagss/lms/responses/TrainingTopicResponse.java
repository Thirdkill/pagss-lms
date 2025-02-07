package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.TrainingTopic;

import lombok.Getter;
import lombok.Setter;

public class TrainingTopicResponse {

	@Getter @Setter private int status;
	@Getter @Setter private TrainingTopic trainingTopic;
	@Getter @Setter private List<TrainingTopic> trainingTopics;
	@Getter @Setter private int totalRecords;
	
	public TrainingTopicResponse(int status) {
		setStatus(status);
	}
	
	public TrainingTopicResponse(int status,TrainingTopic trainingTopic) {
		setStatus(status);
		setTrainingTopic(trainingTopic);
	}
	
	public TrainingTopicResponse(int status,List<TrainingTopic> trainingTopics) {
		setStatus(status);
		setTrainingTopics(trainingTopics);
	}
	
	public TrainingTopicResponse(int status,List<TrainingTopic> trainingTopics, int totalRecords) {
		setStatus(status);
		setTrainingTopics(trainingTopics);
		setTotalRecords(totalRecords);
	}

	public TrainingTopicResponse(int status, int totalRecords) {
		setStatus(status);
		setTotalRecords(totalRecords);
	}
}
