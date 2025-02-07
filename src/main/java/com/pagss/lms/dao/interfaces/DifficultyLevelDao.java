package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.DifficultyLevel;

public interface DifficultyLevelDao {

	public List<DifficultyLevel> fetchDifficultyLevels();

	public List<DifficultyLevel> fetchDifficultyLevelsTable(int pageSize, int PageNo);

	public int countTotalDificultyLevels();

	public List<DifficultyLevel> fetchSearchedDifficultyLevelsTable(String keyword, int pageSize, int PageNo);

	public int countTotalSearchedDificultyLevels(String keyword);

	public void addDifficultyLevel(DifficultyLevel difficultylevel);

	public void updateDifficultyLevel(DifficultyLevel difficultylevel);

	public int countDifficultyName(DifficultyLevel difficultylevel);
}
