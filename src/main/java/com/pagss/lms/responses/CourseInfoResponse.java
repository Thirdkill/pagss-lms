package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.CourseInfo;

import lombok.Getter;
import lombok.Setter;

public class CourseInfoResponse {

	@Getter @Setter private int status;
	@Getter @Setter private int courseId;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private CourseInfo courseInfo;
	@Getter @Setter private List<CourseInfo> courseInfos;
	
	public CourseInfoResponse(int status) {
		setStatus(status);
	}
	
	public CourseInfoResponse(int status,int courseId) {
		setStatus(status);
		setCourseId(courseId);
	}
	
	public CourseInfoResponse(int status,CourseInfo courseInfo) {
		setStatus(status);
		setCourseInfo(courseInfo);
	}
	
	public CourseInfoResponse(int status,List<CourseInfo> courseInfos) {
		setStatus(status);
		setCourseInfo(courseInfo);
		setCourseInfos(courseInfos);
	}
	
	public CourseInfoResponse(int status,int totalRecords, List<CourseInfo> courseInfos) {
		setStatus(status);
		setTotalRecords(totalRecords);
		setCourseInfos(courseInfos);
	}
}
