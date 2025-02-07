package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.constants.LmsCourseMaterialData;
import com.pagss.lms.dao.interfaces.CourseMaterialDao;
import com.pagss.lms.domains.CourseMaterial;
import com.pagss.lms.manager.interfaces.CourseMaterialManager;
import com.pagss.lms.utilities.FileServerOperator;

@Component
public class LmsCourseMaterialManager implements CourseMaterialManager {

	@Autowired
	private CourseMaterialDao courseMaterialDao;
	@Autowired
	private FileServerOperator fileServerOperator;
	
	@Override
	public int createCourseMaterial(CourseMaterial courseMaterial) {
		return this.courseMaterialDao.createCourseMaterial(courseMaterial);
	}
	
	@Override
	public CourseMaterial fetchCourseMaterial(int courseMaterialId) {
		return this.courseMaterialDao.fetchCourseMaterial(courseMaterialId);
	}
	
	@Override
	public void updateContentUrl(CourseMaterial courseMaterial) {
		this.courseMaterialDao.updateContentUrl(courseMaterial);
	}

	@Override
	public List<CourseMaterial> fetchCourseMaterialsPages(int courseId,int pageSize, int PageNo) {
		int calculatedPageNo = pageSize * (PageNo - 1);
		return this.courseMaterialDao.fetchCourseMaterialsPages(courseId,pageSize, calculatedPageNo);
	}

	@Override
	public int countTotalCourseMaterials(int courseId) {
		return this.courseMaterialDao.countTotalCourseMaterials(courseId);
	}
	
	@Override
	public void deleteCourseMaterials(List<CourseMaterial> courseMaterials) {
		try {
			for(CourseMaterial courseMaterial : courseMaterials) {
				if(courseMaterial.getContentType() != LmsCourseMaterialData.CONTENTTYPE_URL) {
					fileServerOperator.deleteFile(courseMaterial.getContentUrl());
				}
			}
			this.courseMaterialDao.deleteCourseMaterials(courseMaterials);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteCourseMaterial(int courseMaterialId) {
		this.courseMaterialDao.deleteCourseMaterial(courseMaterialId);
	}

	@Override
	public void updateViewStatus(CourseMaterial courseMaterial) {
		this.courseMaterialDao.updateViewStatus(courseMaterial);
	}
	
	@Override
	public List<CourseMaterial> fetchCourseMaterialsWithViewStatus(int courseId,int viewStatus) {
		return this.courseMaterialDao.fetchCourseMaterialsWithViewStatus(courseId, viewStatus);
	}

	@Override
	public int insertCourseMaterial(CourseMaterial courseMaterial) {
		return this.courseMaterialDao.insertCourseMaterial(courseMaterial);
	}

	@Override
	public int fetchLastInsertedCourseMaterialId() {
		return this.courseMaterialDao.fetchLastInsertedCourseMaterialId();
	}

	@Override
	public void updateCourseMaterial(CourseMaterial courseMaterial) {
		this.courseMaterialDao.updateCourseMaterial(courseMaterial);
	}
}
