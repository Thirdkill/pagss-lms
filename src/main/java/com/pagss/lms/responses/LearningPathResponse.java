package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.LearningPath;
import com.pagss.lms.domains.LearningPathSection;

import lombok.Getter;
import lombok.Setter;

public class LearningPathResponse {

	@Getter @Setter private int status;
	@Getter @Setter private LearningPath learningPath;
	@Getter @Setter private List<LearningPath> learningPaths;
	@Getter @Setter private List<LearningPathSection> learningPathSections;
	
	public LearningPathResponse(int status) {
		setStatus(status);
	}
	
	public LearningPathResponse(int status,LearningPath learningPath) {
		setStatus(status);
		setLearningPath(learningPath);
	}
	
	public LearningPathResponse(int status,List<LearningPath> learningPaths) {
		setStatus(status);
		setLearningPaths(learningPaths);
	}
	
	public LearningPathResponse(int status,List<LearningPath> learningPaths,List<LearningPathSection> learningPathSections) {
		setStatus(status);
		setLearningPaths(learningPaths);
		setLearningPathSections(learningPathSections);
	}
	
	
}
