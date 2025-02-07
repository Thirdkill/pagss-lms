package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.GradingComponent;

import lombok.Getter;
import lombok.Setter;

public class GradingComponentResponse {

	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private GradingComponent gradingComponent;
	@Getter @Setter private List<GradingComponent> gradingComponents;
	
	public GradingComponentResponse(int status) {
		setStatus(status);
	}
	
	public GradingComponentResponse(int status,GradingComponent gradingComponent) {
		setStatus(status);
		setGradingComponent(gradingComponent);
	}	
	
	public GradingComponentResponse(int status,List<GradingComponent> gradingComponents) {
		setStatus(status);
		setGradingComponents(gradingComponents);
	}
	
	public GradingComponentResponse(int status,int totalRecords,List<GradingComponent> gradingComponents) {
		setStatus(status);
		setGradingComponents(gradingComponents);
		setTotalRecords(totalRecords);
	}
}
