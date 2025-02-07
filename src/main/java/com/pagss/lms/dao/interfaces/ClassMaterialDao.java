package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.ClassMaterial;

public interface ClassMaterialDao {

	public List<ClassMaterial> fetchClassMaterials(int classId);

	public List<ClassMaterial> fetchClassMaterialPages(int classId, int calculatedPageNo, int pageSize);

	public int countClassMaterials(int classId);

	public void updateClassMaterialViewStatus(ClassMaterial classMaterial);

	public void deleteClassMaterials(List<ClassMaterial> classMaterials);

	public int createClassMaterial(ClassMaterial classMaterial);

	public ClassMaterial fetchClassMaterial(int classMaterialId);

	public void updateContentUrl(ClassMaterial classMaterial);

	public void deleteClassMaterial(int classMaterialId);

	public int fetchLatestClassMaterialId();
}
