package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.constants.LmsClassMaterialData;
import com.pagss.lms.dao.interfaces.ClassMaterialDao;
import com.pagss.lms.domains.ClassMaterial;
import com.pagss.lms.manager.interfaces.ClassMaterialManager;
import com.pagss.lms.utilities.FileServerOperator;

@Component
public class LmsClassMaterialManager implements ClassMaterialManager {

	@Autowired
	private ClassMaterialDao classMaterialDao;
	@Autowired
	private FileServerOperator fileServerOperator;
	
	@Override
	public List<ClassMaterial> fetchClassMaterials(int classId) {
		return assignGeneratedUrl(this.classMaterialDao.fetchClassMaterials(classId));
	}

	@Override
	public List<ClassMaterial> fetchClassMaterialPages(int classId, int pageNumber, int pageSize) {
		int calculatedPageNo = pageSize * (pageNumber - 1);
		return this.classMaterialDao.fetchClassMaterialPages(classId, calculatedPageNo, pageSize);
	}

	@Override
	public int countClassMaterials(int classId) {
		return this.classMaterialDao.countClassMaterials(classId);
	}

	@Override
	public void updateClassMaterialViewStatus(ClassMaterial classMaterial) {
		this.classMaterialDao.updateClassMaterialViewStatus(classMaterial);
	}
	
	@Override
	public void deleteClassMaterials(List<ClassMaterial> classMaterials) {
		try {
			for(ClassMaterial classMaterial : classMaterials) {
				if(classMaterial.getContentType() != LmsClassMaterialData.CONTENTTYPE_URL) {
					fileServerOperator.deleteFile(classMaterial.getContentUrl());
				}
			}
			this.classMaterialDao.deleteClassMaterials(classMaterials);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int createClassMaterial(ClassMaterial classMaterial) {
		return this.classMaterialDao.createClassMaterial(classMaterial);
	}

	@Override
	public ClassMaterial fetchClassMaterial(int classMaterialId) {
		return this.classMaterialDao.fetchClassMaterial(classMaterialId);
	}

	@Override
	public void updateContentUrl(ClassMaterial classMaterial) {
		this.classMaterialDao.updateContentUrl(classMaterial);
	}

	@Override
	public void deleteClassMaterial(int classMaterialId) {
		this.classMaterialDao.deleteClassMaterial(classMaterialId);
	}

	@Override
	public int fetchLatestClassMaterialId() {
		return this.classMaterialDao.fetchLatestClassMaterialId();
	}

	/***********************************************************************
	 * 				Start: PRIVATE CLASSES									*
	 **********************************************************************/
	private List<ClassMaterial> assignGeneratedUrl(List<ClassMaterial> classMaterials) {
		for(ClassMaterial classMaterial : classMaterials) {
			if(classMaterial.getContentType() != LmsClassMaterialData.CONTENTTYPE_URL) {
				classMaterial.setContentUrl(fileServerOperator.generatePresignedUrl(classMaterial.getContentUrl()));
			}
		}
		return classMaterials;
	}
	/***********************************************************************
	 * 				End: PRIVATE CLASSES									*
	 **********************************************************************/
}
