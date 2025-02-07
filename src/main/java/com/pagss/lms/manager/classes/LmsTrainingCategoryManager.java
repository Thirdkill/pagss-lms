package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.dao.interfaces.TrainingCategoryDao;
import com.pagss.lms.domains.TrainingCategory;
import com.pagss.lms.manager.interfaces.TrainingCategoryManager;

@Component
public class LmsTrainingCategoryManager implements TrainingCategoryManager {

	@Autowired 
	private TrainingCategoryDao trainingCategoryDao;
	
	/*******************************************************************************************************
	 * Start: Autowired Setters	
	 ******************************************************************************************************/
	@Autowired
	public void setTrainingCategoryDao(TrainingCategoryDao trainingCategoryDao) {
		this.trainingCategoryDao = trainingCategoryDao;
	}
	/*******************************************************************************************************
	 * End: Autowired Setters	
	******************************************************************************************************/
	
	@Override
	public List<TrainingCategory> fetchTrainingCategoriesPages(int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.trainingCategoryDao.fetchTrainingCategoriesPages(pageSize, calculatedPageNo);
	}

	@Override
	public int countTotalCategories() { 
		return this.trainingCategoryDao.countTotalCategories();
	}
	
	@Override
	public List<TrainingCategory> searchTrainingCategories(TableCommand tableCommand) {
		int calculatedPageNo = tableCommand.getPageSize() * (tableCommand.getPageNumber() - 1);
		tableCommand.setPageNumber(calculatedPageNo);
		if(tableCommand.getKeyword() != "") {
			tableCommand.setWhereClause("WHERE tc.categoryCode like :keyword OR tc.categoryName like :keyword OR tc.description like :keyword ");
		} else {
			tableCommand.setWhereClause("");
		}
		
		switch(tableCommand.getSortColumnName()) {
			case "categoryCode":
				tableCommand.setOrderClause("ORDER BY tc.categoryCode " + tableCommand.getSortDirection() + " ");
				break;
			case "categoryName":
				tableCommand.setOrderClause("ORDER BY tc.categoryName " + tableCommand.getSortDirection() + " ");
				break;
			case "description":
				tableCommand.setOrderClause("ORDER BY tc.description " + tableCommand.getSortDirection() + " ");
				break;
			case "status":
				tableCommand.setOrderClause("ORDER BY tc.status " + tableCommand.getSortDirection() + " ");
				break;
		}
		return this.trainingCategoryDao.searchTrainingCategories(tableCommand);
	}

	@Override
	public int countSearchedTrainingCategories(String keyword) {
		return this.trainingCategoryDao.countSearchedTrainingCategories(keyword);
	}
	
	@Override
	public TrainingCategory generateCategoryCode() {
		TrainingCategory trainingCategory = new TrainingCategory();
		trainingCategory.setCategoryCode(generateCategoryCode(trainingCategoryDao.fetchLatestCategoryId()));
		return trainingCategory;
	}
	
	@Override
	public int countTrainingCategoryCode(String categoryCode) {
		return this.trainingCategoryDao.countTrainingCategoryCode(categoryCode);
	}
	
	@Override
	public List<TrainingCategory> fetchTrainingCategories() {
		return this.trainingCategoryDao.fetchTrainingCategories();
	}
	
	@Override
	public List<TrainingCategory> fetchTrainingCategoriesByStatus(int status) {
		return this.trainingCategoryDao.fetchTrainingCategoriesByStatus(status);
	}
	/**************************************************************************************************
	*								Start: Private Classes												*
	****************************************************************************************************/
	private String generateCategoryCode(int categoryId) {
		String categoryNo = Integer.toString(10000 + categoryId).substring(1);
		return "TCC" + categoryNo;
	}
	/**************************************************************************************************
	*								End: Private Classes												*
	****************************************************************************************************/
}
