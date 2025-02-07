package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.CourseMaterial;

import lombok.Getter;
import lombok.Setter;

public class CourseMaterialResponse {

	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private int courseMaterialId;
	@Getter @Setter private CourseMaterial courseMaterial;
	@Getter @Setter private List<CourseMaterial> courseMaterials;
	
	public CourseMaterialResponse(int status) {
		setStatus(status);
	}
	
	public CourseMaterialResponse(int status,int courseMaterialId) {
		setStatus(status);
		setCourseMaterialId(courseMaterialId);
	}
	
	public CourseMaterialResponse(int status,CourseMaterial courseMaterial) {
		setStatus(status);
		setCourseMaterial(courseMaterial);
	}
	
	public CourseMaterialResponse(int status,List<CourseMaterial> courseMaterials) {
		setStatus(status);
		setCourseMaterials(courseMaterials);
	}
	
	public CourseMaterialResponse(int status,int totalRecords,List<CourseMaterial> courseMaterials) {
		setStatus(status);
		setTotalRecords(totalRecords);
		setCourseMaterials(courseMaterials);
	}
}
