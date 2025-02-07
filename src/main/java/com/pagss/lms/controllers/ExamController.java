package com.pagss.lms.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.constants.LmsStatus;
import com.pagss.lms.domains.EmployeeExamSummary;
import com.pagss.lms.domains.EmployeeInfo;
import com.pagss.lms.domains.ExamInfo;
import com.pagss.lms.domains.User;
import com.pagss.lms.exceptions.SessionExpiredException;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.handlers.StackTraceHandler;
import com.pagss.lms.manager.interfaces.EmployeeExamSummaryManager;
import com.pagss.lms.manager.interfaces.EmployeeInfoManager;
import com.pagss.lms.manager.interfaces.ExamInfoManager;
import com.pagss.lms.manager.interfaces.ExamQuestionManager;
import com.pagss.lms.responses.EmployeeExamSummaryResponse;
import com.pagss.lms.responses.ExamInfoResponse;
import com.pagss.lms.responses.ExamQuestionResponse;

/**
 * 
 * @author Gerald
 *
 */
@RestController
public class ExamController {
	
	@Autowired
	private ExamInfoManager examInfoManager;
	@Autowired
	private EmployeeInfoManager employeeInfoManager;
	@Autowired
	private EmployeeExamSummaryManager employeeExamSummaryManager;
	@Autowired
	private ExamQuestionManager examQuestionManager;
	/***************************************************************************************************************************/
	/* START: NORMAL HTTPREQUEST	 																						 **/
	/**************************************************************************************************************************/
	@GetMapping(value="admin.examinations")
	public ModelAndView gotoExaminationsPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		return new ModelAndView("admin/admin-examinations");
	}
	
	@GetMapping(value="admin.examination")
	public ModelAndView gotoCreateExamPage(HttpServletRequest request,
		@RequestParam(value="action",required=false) String action,
		@RequestParam(value="module",required=false) String module,
		@RequestParam(value="classId",required=false) String classId,
		@RequestParam(value="courseId",required=false) String courseId,
		@RequestParam(value="examId",required=false) Integer examId) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		if(action.equals("create") && module == null) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("admin/admin-createexam");
			mav.addObject("action","create");
			return mav;
		} else if(action.equals("create") && module != null) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("admin/admin-createexam");
			mav.addObject("action","create");
			mav.addObject("module",module);
			mav.addObject("courseId",courseId);
			mav.addObject("classId",classId);
			return mav;
		} else if(action.equals("update") && module != null) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("admin/admin-createexam");
			mav.addObject("action","update");
			mav.addObject("examId",examId);
			mav.addObject("classId",classId);
			mav.addObject("courseId",courseId);
			mav.addObject("module",module);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("admin/admin-createexam");
			mav.addObject("action","update");
			mav.addObject("examId",examId);
			return mav;
		}
	}
	
	@GetMapping(value="exam-result")
	public ModelAndView gotoPortalExamResultPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("portal/portal-exam");
		return mav;
	}
	
	@GetMapping(value="examination")
	public ModelAndView gotoPortalExamPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("portal/portal-mainexam");
		return mav;
	}
	
	@GetMapping(value="exam-startingpage")
	public ModelAndView gotoExamStartingPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("portal/portal-examstartingpage");
		return mav;
	}
	/***************************************************************************************************************************/
	/* END: NORMAL HTTPREQUEST	 																						 **/
	/**************************************************************************************************************************/
	/***************************************************************************************************************************/
	/* START: Restful API	 																						 **/
	/**************************************************************************************************************************/
	@GetMapping(value="examinfos/pages")
	public @ResponseBody ExamInfoResponse fetchExamInfosPage(HttpServletRequest request,
		@RequestParam("pageNumber") int pageNo, @RequestParam("pageSize") int pageSize) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TableCommand tableCommand = new TableCommand();
			tableCommand.setPageNumber(pageNo);
			tableCommand.setPageSize(pageSize);
			List<ExamInfo> examInfos = this.examInfoManager.fetchExamInfoPages(tableCommand);
			int totalRecords = this.examInfoManager.countExamInfos(tableCommand);
			return new ExamInfoResponse(LmsStatus.SUCCESS,totalRecords,examInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ExamInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ExamInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ExamInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="examinfos/search")
	public @ResponseBody ExamInfoResponse searchExamInfos(HttpServletRequest request,
		@RequestParam("pageNumber") int pageNo, @RequestParam("pageSize") int pageSize,
		@RequestParam("keyword") String keyword) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<ExamInfo> examInfos = this.examInfoManager.searchExamInfo(pageSize, pageNo, keyword);
			int totalRecords = this.examInfoManager.countSearchExamInfo(keyword);
			return new ExamInfoResponse(LmsStatus.SUCCESS,totalRecords,examInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ExamInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ExamInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ExamInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="examinfo")
	public @ResponseBody ExamInfoResponse createExamInfo(HttpServletRequest request,@RequestBody ExamInfo examInfo) {
		try {
			User user = SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			if(this.examInfoManager.countExamCode(examInfo.getExamCode()) != 0) {
				return new ExamInfoResponse(LmsStatus.EXAMCODE_EXISTS);
			} else {
				EmployeeInfo employeeInfo = new EmployeeInfo();
				employeeInfo.setUserId(user.getUserId());
				examInfo.setModifiedBy(this.employeeInfoManager.fetchEmployeeInfo(employeeInfo).getFullName());
				this.examInfoManager.createExamInfo(examInfo);
				return new ExamInfoResponse(LmsStatus.SUCCESS);
			}
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ExamInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ExamInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ExamInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="examinfo/generate-examcode")
	public @ResponseBody ExamInfoResponse generateExamCode(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new ExamInfoResponse(LmsStatus.SUCCESS,this.examInfoManager.generateExamCode());
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ExamInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ExamInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ExamInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="examinfo/{examId}")
	public @ResponseBody ExamInfoResponse fetchExamInfo(HttpServletRequest request, 
		@PathVariable("examId") int examId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new ExamInfoResponse(LmsStatus.SUCCESS,this.examInfoManager.fetchExamInfo(examId));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ExamInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ExamInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ExamInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="examinfo/{examId}/questions")
	public @ResponseBody ExamQuestionResponse fetchExamQuestions(HttpServletRequest request,
		@PathVariable("examId") int examId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new ExamQuestionResponse(LmsStatus.SUCCESS,this.examQuestionManager.fetchExamQuestions(examId));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ExamQuestionResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ExamQuestionResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ExamQuestionResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="examinfo/{examId}")
	public @ResponseBody ExamQuestionResponse updateExamInfo(HttpServletRequest request,
		@RequestBody ExamInfo examInfo) {
		try {
			User user=SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			EmployeeInfo employeeInfo = new EmployeeInfo();
			employeeInfo.setUserId(user.getUserId());
			examInfo.setModifiedBy(this.employeeInfoManager.fetchEmployeeInfo(employeeInfo).getFullName());
			this.examInfoManager.updateExamInfo(examInfo);
			return new ExamQuestionResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ExamQuestionResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ExamQuestionResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ExamQuestionResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="examinfos/active/pages")
	public @ResponseBody ExamInfoResponse fetchExamInfosPageWithStatus(HttpServletRequest request,
		@RequestParam("pageNumber") int pageNo, @RequestParam("pageSize") int pageSize,
		@RequestParam("status") int status,@RequestParam("courseId") int courseId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TableCommand tableCommand = new TableCommand();
			tableCommand.setPageNumber(pageNo);
			tableCommand.setPageSize(pageSize);
			tableCommand.setSearchByExamStatus(status);
			tableCommand.setSearchByExamStatus(status);
			tableCommand.setCourseId(courseId);
			List<ExamInfo> examInfos = this.examInfoManager.fetchExamInfoPagesWithStatus(tableCommand);
			int totalRecords = this.examInfoManager.countExamPagesWithStatus(courseId,status);
			return new ExamInfoResponse(LmsStatus.SUCCESS,totalRecords,examInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ExamInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ExamInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ExamInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="examinfos/active/pages/class")
	public @ResponseBody ExamInfoResponse fetchExamInfosPageOnClassWithStatus(HttpServletRequest request,
		@RequestParam("pageNumber") int pageNo, @RequestParam("pageSize") int pageSize,
		@RequestParam("status") int status,@RequestParam("classId") int classId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TableCommand tableCommand = new TableCommand();
			tableCommand.setPageNumber(pageNo);
			tableCommand.setPageSize(pageSize);
			tableCommand.setSearchByExamStatus(status);
			tableCommand.setSearchByExamStatus(status);
			tableCommand.setClassId(classId);
			List<ExamInfo> examInfos = this.examInfoManager.fetchExamInfosPageOnClassWithStatus(tableCommand);
			int totalRecords = this.examInfoManager.countxamInfosPageOnClassWithStatus(tableCommand);
			return new ExamInfoResponse(LmsStatus.SUCCESS,totalRecords,examInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ExamInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ExamInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ExamInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="examinfos/active/search")
	public @ResponseBody ExamInfoResponse searchExamInfosPageWithStatus(HttpServletRequest request,
		@RequestParam("pageNumber") int pageNo, @RequestParam("pageSize") int pageSize,
		@RequestParam("status") int status,@RequestParam("keyword") String keyword,
		@RequestParam("examType") int examType,@RequestParam("courseId") int courseId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TableCommand tableCommand = new TableCommand();
			tableCommand.setKeyword(keyword);
			tableCommand.setPageSize(pageSize);
			tableCommand.setPageNumber(pageNo);
			tableCommand.setSearchByExamStatus(status);
			tableCommand.setSearchByExamType(examType);
			tableCommand.setCourseId(courseId);
			List<ExamInfo> examInfos = this.examInfoManager.searchExamInfoPagesWithStatus(tableCommand);
			int totalRecords = this.examInfoManager.countSearchExamInfoPagesWithStatus(tableCommand);
			return new ExamInfoResponse(LmsStatus.SUCCESS,totalRecords,examInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ExamInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ExamInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ExamInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="examinfos/active/search/class")
	public @ResponseBody ExamInfoResponse searchExamInfosPageOnClassWithStatus(HttpServletRequest request,
		@RequestParam("pageNumber") int pageNo, @RequestParam("pageSize") int pageSize,
		@RequestParam("status") int status,@RequestParam("keyword") String keyword,
		@RequestParam("examType") int examType,@RequestParam("classId") int classId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TableCommand tableCommand = new TableCommand();
			tableCommand.setKeyword(keyword);
			tableCommand.setPageSize(pageSize);
			tableCommand.setPageNumber(pageNo);
			tableCommand.setSearchByExamStatus(status);
			tableCommand.setSearchByExamType(examType);
			tableCommand.setClassId(classId);
			List<ExamInfo> examInfos = this.examInfoManager.searchExamInfosPageOnClassWithStatus(tableCommand);
			int totalRecords = this.examInfoManager.countSearchExamInfosPageOnClassWithStatus(tableCommand);
			return new ExamInfoResponse(LmsStatus.SUCCESS,totalRecords,examInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ExamInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ExamInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ExamInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="examinfos/active")
	public @ResponseBody ExamInfoResponse fetchExamInfosPageWithStatus(HttpServletRequest request,
		@RequestParam("courseId") int courseId, @RequestParam("status") int status,@RequestParam("examType") int examType) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			ExamInfo examInfo = new ExamInfo();
			examInfo.setCourseId(courseId);
			examInfo.setStatus(status);
			examInfo.setExamType(examType);
			List<ExamInfo> examInfos = this.examInfoManager.fetchExamInfosWithStatus(examInfo);
			return new ExamInfoResponse(LmsStatus.SUCCESS,examInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ExamInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ExamInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ExamInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="examinfos/scores")
	public @ResponseBody EmployeeExamSummaryResponse fetchExamScores(HttpServletRequest request,
			@RequestParam("pageNumber") int pageNo, @RequestParam("pageSize") int pageSize,
			@RequestParam("examId") int examId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<EmployeeExamSummary> examScores = this.employeeExamSummaryManager.fetchExamScores(examId, pageNo, pageSize);
			int totalRecords = this.employeeExamSummaryManager.countExamScores(examId);
			return new EmployeeExamSummaryResponse(LmsStatus.SUCCESS,totalRecords,examScores);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EmployeeExamSummaryResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EmployeeExamSummaryResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EmployeeExamSummaryResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	/***************************************************************************************************************************/
	/* END: Restful API	 																						 **/
	/**************************************************************************************************************************/

}
