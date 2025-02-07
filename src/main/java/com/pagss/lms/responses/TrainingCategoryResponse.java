package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.TrainingCategory;

import lombok.Getter;
import lombok.Setter;

public class TrainingCategoryResponse {

	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private TrainingCategory trainingCategory;
	@Getter @Setter private List<TrainingCategory> trainingCategories;
	
	public TrainingCategoryResponse(int status) {
		setStatus(status);
	}
	
	public TrainingCategoryResponse(int status,TrainingCategory trainingCategory) {
		setStatus(status);
		setTrainingCategory(trainingCategory);
	}
	
	public TrainingCategoryResponse(int status,List<TrainingCategory> trainingCategories) {
		setStatus(status);
		setTrainingCategories(trainingCategories);
	}
	
	public TrainingCategoryResponse(int status,int totalRecords,List<TrainingCategory> trainingCategories) {
		setStatus(status);
		setTrainingCategories(trainingCategories);
		setTotalRecords(totalRecords);
	}
}
