package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.LearningPathDao;
import com.pagss.lms.dao.interfaces.LearningPathSectionDao;
import com.pagss.lms.domains.LearningPathSection;
import com.pagss.lms.manager.interfaces.LearningPathSectionManager;

@Component
public class LmsLearningPathSectionManager implements LearningPathSectionManager {
	
	@Autowired
	private LearningPathSectionDao learningPathSectionDao;
	@Autowired
	private LearningPathDao learningPathDao;
	
	@Override
	public void createLearningPathSection(LearningPathSection learningPathSection) {
		this.learningPathSectionDao.createLearningPathSection(learningPathSection);
	}

	@Override
	public List<LearningPathSection> fetchLearningPathSections(int courseId) {
		return this.learningPathSectionDao.fetchLearningPathSections(courseId);
	}

	@Override
	public void updateSectionOrderNo(List<LearningPathSection> LearningPathSections) {
		this.learningPathSectionDao.updateSectionOrderNo(LearningPathSections);
	}
	
	@Override
	public void updateLearningPathSection(LearningPathSection learningPathSection) {
		this.learningPathSectionDao.updateLearningPathSection(learningPathSection);
	}

	@Override
	public void deleteLearningPathSection(int learningPathSectionId) {
		this.learningPathDao.deleteLearningPathByLearningPathSectionId(learningPathSectionId);
		this.learningPathSectionDao.deleteLearningPathSection(learningPathSectionId);
	}
}
