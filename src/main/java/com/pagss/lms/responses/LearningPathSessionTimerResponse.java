package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.LearningPathSessionTimer;

import lombok.Getter;
import lombok.Setter;

public class LearningPathSessionTimerResponse {

	@Getter @Setter private int status;
	@Getter @Setter private LearningPathSessionTimer learningPathSessionTimer;
	@Getter @Setter private List<LearningPathSessionTimer> learningPathSessionTimers;
	
	public LearningPathSessionTimerResponse(int status) {
		setStatus(status);
	}
	
	public LearningPathSessionTimerResponse(int status, LearningPathSessionTimer learningPathSessionTimer) {
		setStatus(status);
		setLearningPathSessionTimer(learningPathSessionTimer);
	}
	
	public LearningPathSessionTimerResponse(int status, List<LearningPathSessionTimer> learningPathSessionTimers) {
		setStatus(status);
		setLearningPathSessionTimers(learningPathSessionTimers);
	}
}
