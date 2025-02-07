package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.DifficultyLevel;

import lombok.Getter;
import lombok.Setter;

public class DifficultyLevelResponse {

	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private DifficultyLevel difficultyLevel;
	@Getter @Setter private List<DifficultyLevel> difficultyLevels;
	
	public DifficultyLevelResponse(int status) {
		setStatus(status);
	}
	
	public DifficultyLevelResponse(int status,DifficultyLevel difficultyLevel) {
		setStatus(status);
		setDifficultyLevel(difficultyLevel);
	}
	
	public DifficultyLevelResponse(int status,List<DifficultyLevel> difficultyLevels) {
		setStatus(status);
		setDifficultyLevels(difficultyLevels);
	}
	
	public DifficultyLevelResponse(int status,List<DifficultyLevel> difficultyLevels, int totalRecords) {
		setStatus(status);
		setTotalRecords(totalRecords);
		setDifficultyLevels(difficultyLevels);
	}

	public DifficultyLevelResponse(int status, int totalRecords) {
		setStatus(status);
		setTotalRecords(totalRecords);
	}
}
