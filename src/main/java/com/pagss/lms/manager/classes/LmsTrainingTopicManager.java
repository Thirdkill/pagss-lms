package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.TrainingTopicDao;
import com.pagss.lms.domains.TrainingTopic;
import com.pagss.lms.manager.interfaces.TrainingTopicManager;

@Component
public class LmsTrainingTopicManager implements TrainingTopicManager {

	@Autowired 
	private TrainingTopicDao trainingTopicDao;
	
	@Override
	public List<TrainingTopic> fetchTrainingTopics() {
		return this.trainingTopicDao.fetchTrainingTopics();
	}

	@Override
	public List<TrainingTopic> fetchTopicsTable(int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.trainingTopicDao.fetchTopicsTable(pageSize, calculatedPageNo);
	}

	@Override
	public int countTotalTrainingTopics() {
		return this.trainingTopicDao.countTotalTrainingTopics();
	}

	@Override
	public List<TrainingTopic> fetchSearchedTopicsTable(String keyword, int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.trainingTopicDao.fetchSearchedTopicsTable(keyword, pageSize, calculatedPageNo);
	}

	@Override
	public int countTotalSearchedTrainingTopics(String keyword) {
		return this.trainingTopicDao.countTotalSearchedTrainingTopics(keyword);
	}

	@Override
	public void addTrainingTopic(TrainingTopic trainingtopic) {
		this.trainingTopicDao.addTrainingTopic(trainingtopic);
	}

	@Override
	public void updateTrainingTopic(TrainingTopic trainingtopic) {
		this.trainingTopicDao.updateTrainingTopic(trainingtopic);
	}

	@Override
	public int countTopicDesc(TrainingTopic trainingtopic) {
		return this.trainingTopicDao.countTopicDesc(trainingtopic);
	}
}
