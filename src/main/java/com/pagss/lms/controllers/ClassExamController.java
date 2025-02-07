package com.pagss.lms.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pagss.lms.domains.ClassExam;
import com.pagss.lms.domains.ClassInfo;
import com.pagss.lms.domains.EmployeeInfo;
import com.pagss.lms.domains.User;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.manager.interfaces.ClassExamManager;
import com.pagss.lms.manager.interfaces.ClassInfoManager;
import com.pagss.lms.manager.interfaces.EmployeeInfoManager;

@RestController
public class ClassExamController {

	@Autowired
	private ClassInfoManager classInfoManager;
	@Autowired
	private ClassExamManager classExamManager;
	@Autowired
	private EmployeeInfoManager employeeInfoManager;
	/*****************************************************************************************************
	 * START:NORMAL HTTPREQUEST
	 *****************************************************************************************************/
	@GetMapping(value="exam-home")
	public ModelAndView gotoExamHomePage(HttpServletRequest request,
		@RequestParam(value="classId",required=false) Integer classId,
		@RequestParam(value="examId",required=false) Integer examId,
		@RequestParam(value="coursexam",required=false) Integer courseExam) throws Exception {
		User user = SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ClassInfo classInfo = this.classInfoManager.fetchClassInfo(classId);
		EmployeeInfo employeeInfo = this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
		ClassExam classExam = this.classExamManager.fetchClassExam(classId, examId);
		ModelAndView mav = new ModelAndView();
		mav.addObject("classInfo",classInfo);
		mav.addObject("employeeId",employeeInfo.getEmployeeId());
		mav.addObject("classExam",classExam);
		mav.setViewName("portal/portal-examhomepage");
		return mav;
	}
	/*****************************************************************************************************
	 * END:HTTPREQUEST
	 *****************************************************************************************************/
}
