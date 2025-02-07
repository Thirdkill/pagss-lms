package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.LearningPathSection;

import lombok.Getter;
import lombok.Setter;

public class LearningPathSectionResponse {

	@Getter @Setter private int status;
	@Getter @Setter private LearningPathSection learningPathSection;
	@Getter @Setter private List<LearningPathSection> learningPathSections;
	
	public LearningPathSectionResponse(int status) {
		setStatus(status);
	}
	
	public LearningPathSectionResponse(int status,LearningPathSection learningPathSection) {
		setStatus(status);
		setLearningPathSection(learningPathSection);
	}
	
	public LearningPathSectionResponse(int status,List<LearningPathSection> learningPathSections) {
		setStatus(status);
		setLearningPathSections(learningPathSections);
	}
}
