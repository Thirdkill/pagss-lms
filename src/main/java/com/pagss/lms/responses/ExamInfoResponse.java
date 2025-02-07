package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.ExamInfo;

import lombok.Getter;
import lombok.Setter;

public class ExamInfoResponse {

	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private ExamInfo examInfo;
	@Getter @Setter private List<ExamInfo> examInfos;
	
	public ExamInfoResponse(int status) {
		setStatus(status);
	}
	
	public ExamInfoResponse(int status,ExamInfo examInfo) {
		setStatus(status);
		setExamInfo(examInfo);
	}
	
	public ExamInfoResponse(int status,List<ExamInfo> examInfos) {
		setStatus(status);
		setExamInfos(examInfos);
	}
	
	public ExamInfoResponse(int status,int totalRecords,List<ExamInfo> examInfos) {
		setStatus(status);
		setExamInfos(examInfos);
		setTotalRecords(totalRecords);
	}
}
