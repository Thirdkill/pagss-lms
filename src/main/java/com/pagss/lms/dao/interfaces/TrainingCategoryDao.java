package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.domains.TrainingCategory;

public interface TrainingCategoryDao {
	
	public List<TrainingCategory> fetchTrainingCategoriesPages(int pageSize,int pageNo);
	
	public int countTotalCategories();
	
	public List<TrainingCategory> searchTrainingCategories(TableCommand tableCommand);
	
	public int countSearchedTrainingCategories(String keyword);
	
	public int fetchLatestCategoryId();
	
	public int countTrainingCategoryCode(String categoryCode);
	
	public List<TrainingCategory> fetchTrainingCategories();
	
	public List<TrainingCategory> fetchTrainingCategoriesByStatus(int status);
}
