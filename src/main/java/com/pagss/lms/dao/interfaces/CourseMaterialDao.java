package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.CourseMaterial;

public interface CourseMaterialDao {

	public int createCourseMaterial(CourseMaterial courseMaterial);
	
	public CourseMaterial fetchCourseMaterial(int courseMaterialId);
	
	public void updateContentUrl(CourseMaterial courseMaterial);
	
	public List<CourseMaterial> fetchCourseMaterialsPages(int courseId,int pageSize,int PageNo);
	
	public int countTotalCourseMaterials(int courseId);
	
	public void deleteCourseMaterials(List<CourseMaterial> courseMaterials);
	
	public void deleteCourseMaterial(int courseMaterialId);
	
	public void updateViewStatus(CourseMaterial courseMaterial);
	
	public List<CourseMaterial> fetchCourseMaterialsWithViewStatus(int courseId,int viewStatus);
	
	public int insertCourseMaterial(CourseMaterial courseMaterial); //Change to createMaterials after reimplementing all file uploads
	
	public int fetchLastInsertedCourseMaterialId();
	
	public void updateCourseMaterial(CourseMaterial courseMaterial);
}
