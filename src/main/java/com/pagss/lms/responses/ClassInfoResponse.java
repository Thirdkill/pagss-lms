package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.ClassInfo;

import lombok.Getter;
import lombok.Setter;

public class ClassInfoResponse {
	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private ClassInfo classInfo;
	@Getter @Setter private List<ClassInfo> classInfos;
	@Getter @Setter private int totalInProgressClassInfos;
	@Getter @Setter private int totalUpcomingClassInfos;
	@Getter @Setter private int totalCompletedClassInfos;
	@Getter @Setter private int totalCancelledClassInfos;
	
	public ClassInfoResponse (int status) {
		setStatus(status);
	}
	
	public ClassInfoResponse (int status, int totalRecords) {
		setStatus(status);
		setTotalRecords(totalRecords);
	}
	
	public ClassInfoResponse (int status, ClassInfo classInfo) {
		setStatus(status);
		setClassInfo(classInfo);
	}

	public ClassInfoResponse (int status, List<ClassInfo> classInfos) {
		setStatus(status);
		setClassInfos(classInfos);
	}
	
	public ClassInfoResponse (int status,int totalRecords, List<ClassInfo> classInfos) {
		setStatus(status);
		setClassInfos(classInfos);
		setTotalRecords(totalRecords);
	}

	public ClassInfoResponse (int status, int totalInProgressClassInfos, int totalUpcomingClassInfos,
		int totalCompletedClassInfos, int totalCancelledClassInfos) {
		setStatus(status);
		setTotalInProgressClassInfos(totalInProgressClassInfos);
		setTotalCompletedClassInfos(totalCompletedClassInfos);
		setTotalCancelledClassInfos(totalCancelledClassInfos);
		setTotalUpcomingClassInfos(totalUpcomingClassInfos);
	}
}
