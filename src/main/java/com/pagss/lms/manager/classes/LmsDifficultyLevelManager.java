package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.DifficultyLevelDao;
import com.pagss.lms.domains.DifficultyLevel;
import com.pagss.lms.manager.interfaces.DifficultyLevelManager;

@Component
public class LmsDifficultyLevelManager implements DifficultyLevelManager {
	
	@Autowired 
	private DifficultyLevelDao difficultyLevelDao;
	
	@Override
	public List<DifficultyLevel> fetchDifficultyLevels() {
		return this.difficultyLevelDao.fetchDifficultyLevels();
	}

	@Override
	public List<DifficultyLevel> fetchDifficultyLevelsTable(int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.difficultyLevelDao.fetchDifficultyLevelsTable(pageSize, calculatedPageNo);
	}

	@Override
	public int countTotalDifficultyLevels() {
		return this.difficultyLevelDao.countTotalDificultyLevels();
	}

	@Override
	public List<DifficultyLevel> fetchSearchedDifficultyLevelsTable(String keyword, int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.difficultyLevelDao.fetchSearchedDifficultyLevelsTable(keyword, pageSize, calculatedPageNo);
	}

	@Override
	public int countTotalSearchedDifficultyLevels(String keyword) {
		return this.difficultyLevelDao.countTotalSearchedDificultyLevels(keyword);
	}

	@Override
	public void addDifficultyLevel(DifficultyLevel difficultylevel) {
		this.difficultyLevelDao.addDifficultyLevel(difficultylevel);
		
	}

	@Override
	public void updateDifficultyLevel(DifficultyLevel difficultylevel) {
		this.difficultyLevelDao.updateDifficultyLevel(difficultylevel);
	}

	@Override
	public int countDifficultyName(DifficultyLevel difficultylevel) {
		return this.difficultyLevelDao.countDifficultyName(difficultylevel);
	}
}
