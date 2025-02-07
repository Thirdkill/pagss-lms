package com.pagss.lms.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pagss.lms.constants.LmsStatus;
import com.pagss.lms.domains.ClassEvaluation;
import com.pagss.lms.domains.EmployeeEvaluation;
import com.pagss.lms.domains.EmployeeInfo;
import com.pagss.lms.domains.User;
import com.pagss.lms.exceptions.SessionExpiredException;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.handlers.StackTraceHandler;
import com.pagss.lms.manager.interfaces.ClassEvaluationManager;
import com.pagss.lms.manager.interfaces.EmployeeInfoManager;
import com.pagss.lms.responses.ClassEvaluationResponse;
import com.pagss.lms.responses.EmployeeEvaluationResponse;

@RestController
public class ClassEvaluationController {
	
	@Autowired
	private EmployeeInfoManager employeeInfoManager;
	@Autowired
	private ClassEvaluationManager classEvaluationManager;
	
	@GetMapping(value="classInfo/classevaluations/list")
	public @ResponseBody ClassEvaluationResponse fetchCourseEvaluations(HttpServletRequest request,
			@RequestParam("classId") int classId) {
		try {
			SessionHandler.checkSessionUserAccess(request);
			User user = SessionHandler.getSessionUser(request);
			EmployeeInfo employee = this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
			SessionHandler.checkSessionUserAccess(request);
			List<ClassEvaluation> classEvaluations = this.classEvaluationManager
					.fetchEmployeeClassEvaluation(classId, employee.getEmployeeId());
			return new ClassEvaluationResponse(LmsStatus.SUCCESS,classEvaluations);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassEvaluationResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassEvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassEvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classInfo/employeeAnswer/list")
	public @ResponseBody EmployeeEvaluationResponse fetchEmployeeAnswers(HttpServletRequest request,
			@RequestParam(value="classId",required=true) Integer classId,
			@RequestParam(value="evaluationId",required=false) Integer evaluationId) {
		try {

			SessionHandler.checkSessionUserAccess(request);
			User user = SessionHandler.getSessionUser(request);
			EmployeeInfo employee = this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
			SessionHandler.checkSessionUserAccess(request);
			List<EmployeeEvaluation> employeeAnswerList = this.classEvaluationManager
					.fetchEmployeeAnswers(classId, employee.getEmployeeId(), evaluationId);
			return new EmployeeEvaluationResponse(LmsStatus.SUCCESS,employeeAnswerList);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EmployeeEvaluationResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EmployeeEvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EmployeeEvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="class-evaluation-test")
	public @ResponseBody ModelAndView goToEvaluationForm(HttpServletRequest request,
			@RequestParam(value="classId",required=false) Integer classId,
			@RequestParam(value="courseEvaluationId",required=false) Integer courseEvaluationId,
			@RequestParam(value="classEvaluationId",required=false) Integer classEvaluationId,
			@RequestParam(value="courseId", required=false) Integer courseId){
		if (classEvaluationId == null) {
			
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("portal/portal-evaluation");
		mav.addObject("classId",classId);
		if(courseEvaluationId == null) {
			mav.addObject("classEvaluationId",classEvaluationId);
		} else if (classEvaluationId == null) {
			mav.addObject("courseEvaluationId",courseEvaluationId);
			mav.addObject("courseId", courseId);
		}
		return mav;
	}
	
	@GetMapping(value="classinfo/fetchevaluationpath")
	public @ResponseBody ClassEvaluationResponse fetchEvaluationClassPath(HttpServletRequest request,
			@RequestParam("classId") Integer classId,
			@RequestParam("classEvaluationId") Integer evaluationId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			ClassEvaluation classEvaluation = new ClassEvaluation();
			classEvaluation = this.classEvaluationManager.fetchEmployeeClassPath(classId, evaluationId);
			return new ClassEvaluationResponse(LmsStatus.SUCCESS, classEvaluation);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassEvaluationResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassEvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassEvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="classinfo/insertemployeeanswer")
	public @ResponseBody EmployeeEvaluationResponse insertEmployeeAnswer(HttpServletRequest request,
			@RequestBody List<EmployeeEvaluation> employeeEvaluationList) {
		try {
			User user = SessionHandler.getSessionUser(request);
			EmployeeInfo empInfo = this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
			int empId = empInfo.getEmployeeId();
			SessionHandler.checkSessionUserAccess(request);
			employeeEvaluationList = this.classEvaluationManager.insertEmployeeAnswer(employeeEvaluationList, empId);
			return new EmployeeEvaluationResponse(LmsStatus.SUCCESS, employeeEvaluationList);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EmployeeEvaluationResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EmployeeEvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EmployeeEvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
}
