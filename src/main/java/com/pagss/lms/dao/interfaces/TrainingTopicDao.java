package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.TrainingTopic;

public interface TrainingTopicDao {

	public List<TrainingTopic> fetchTrainingTopics();

	public List<TrainingTopic> fetchTopicsTable(int pageSize, int pageNo);

	public int countTotalTrainingTopics();

	public int countTotalSearchedTrainingTopics(String keyword);

	public List<TrainingTopic> fetchSearchedTopicsTable(String keyword, int pageSize, int pageNo);

	public void addTrainingTopic(TrainingTopic trainingtopic);

	public void updateTrainingTopic(TrainingTopic trainingtopic);

	public int countTopicDesc(TrainingTopic trainingtopic);
}
