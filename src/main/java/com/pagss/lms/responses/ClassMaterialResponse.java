package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.ClassMaterial;

import lombok.Getter;
import lombok.Setter;

public class ClassMaterialResponse {
	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private int classMaterialId;
	@Getter @Setter private ClassMaterial classMaterial;
	@Getter @Setter private List<ClassMaterial> classMaterials;
	
	public ClassMaterialResponse (int status) {
		setStatus(status);
	}
	
	public ClassMaterialResponse (int status, int classMaterialId) {
		setStatus(status);
		setClassMaterialId(classMaterialId);
	}
	
	public ClassMaterialResponse (int status, ClassMaterial classMaterial) {
		setStatus(status);
		setClassMaterial(classMaterial);
	}

	public ClassMaterialResponse (int status, List<ClassMaterial> classMaterials) {
		setStatus(status);
		setClassMaterials(classMaterials);
	}
	
	public ClassMaterialResponse (int status,int totalRecords, List<ClassMaterial> classMaterials) {
		setStatus(status);
		setClassMaterials(classMaterials);
		setTotalRecords(totalRecords);
	}
}
