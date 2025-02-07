package com.pagss.lms.constants;

public class LmsStatus {

	public static final int 
		SUCCESS										= 200,
		SESSION_EXPIRED								= 1000,
		QUERY_FAILED								= 1001,
		UNHANDLED_ERROR								= 1999,
		//Training Category
		CATEGORY_CODE_EXIST							= 2000,
		//JobRole
		JOBROLE_CODE_EXIST							= 2010,
		//Location
		LOCATION_CODE_EXIST							= 2020,
		//File Upload
		FILE_FORMAT_IS_INVALID						= 2030,
		FILE_SIZE_IS_INVALID						= 2031,
		//Question
		INSUFFICIENT_NO_OF_QUESTIONS				= 2040,
		//ExamInfo
		EXAMCODE_EXISTS								= 2050,
		//CourseInfo
		COURSECODE_EXISTS							= 2060,
		//Grading Component
		COMPONENT_EXISTS							= 2070,
		//Course Exam
		LEARNINGPATH_ALREADY_EXIST					= 2080,
		//Class schedule
		CLASSSCHEDULE_HAS_CONFLICTS					= 2090,
		//Class Employeee
		TIME_SPENT_IS_NOT_ENOUGH					= 3000;
}
