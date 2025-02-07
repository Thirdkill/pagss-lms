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
import com.pagss.lms.domains.EmployeeEvaluation;
import com.pagss.lms.domains.EmployeeInfo;
import com.pagss.lms.domains.Evaluation;
import com.pagss.lms.domains.EvaluationQuestion;
import com.pagss.lms.domains.User;
import com.pagss.lms.exceptions.SessionExpiredException;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.handlers.StackTraceHandler;
import com.pagss.lms.manager.interfaces.EvaluationManager;
import com.pagss.lms.responses.EmployeeEvaluationResponse;
import com.pagss.lms.responses.EvaluationQuestionResponse;
import com.pagss.lms.responses.EvaluationResponse;
import com.pagss.lms.spring.data.repositories.EvaluationRepository;

@RestController
public class EvaluationController {
	
	@Autowired
	private EvaluationManager evaluationManager;
	@Autowired
	private EvaluationRepository evaluationRepository;

	/***************************************************************************************************************************/
	/* START: NORMAL HTTPREQUEST	 																						 **/
	/**************************************************************************************************************************/
	@GetMapping(value="admin.evaluation")
	public ModelAndView gotoEvaluationsPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		return new ModelAndView("admin/admin-evaluation");
	}
	
	@GetMapping(value="admin.createevaluation")
	public ModelAndView gotoCreateEvaluationsPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		return new ModelAndView("admin/admin-createevaluation");
	}
	
	@GetMapping(value="admin.updateevaluation")
	public ModelAndView gotoEditEvaluationsPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		return new ModelAndView("admin/admin-editevaluation");
	}
	/***************************************************************************************************************************/
	/* END: NORMAL HTTPREQUEST	 																						 **/
	/**************************************************************************************************************************/
	
	/***************************************************************************************************************************/
	/* START: RESTFUL API																					 **/
	/**************************************************************************************************************************/
	@GetMapping(value="evaluations/pages")
	public @ResponseBody EvaluationResponse fetchEvaluations(HttpServletRequest request,
	@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<Evaluation> evaluationService = this.evaluationManager.fetchEvaluationTable(pageSize,pageNo);
			int totalRecords = this.evaluationManager.countTotalEvaluations();
			return new EvaluationResponse(LmsStatus.SUCCESS,evaluationService,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EvaluationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="evaluations/pages/courseevaluation/{courseId}")
	public @ResponseBody EvaluationResponse fetchEvaluationsOnCourse(HttpServletRequest request,
	@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo,
	@PathVariable("courseId") int courseId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<Evaluation> evaluationService = this.evaluationManager.fetchEvaluationOnCourseTable(courseId,pageSize,pageNo);
			int totalRecords = this.evaluationManager.countTotalEvaluationsOnCourse(courseId);
			return new EvaluationResponse(LmsStatus.SUCCESS,evaluationService,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EvaluationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="evaluations/info/{evaluationId}")
	public @ResponseBody EvaluationResponse fetchEvaluationInfo(HttpServletRequest request,
			@PathVariable("evaluationId") int evaluationId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			Evaluation evaluation = this.evaluationManager.fetchEvaluationInfo(evaluationId);
			return new EvaluationResponse(LmsStatus.SUCCESS,evaluation);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EvaluationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="evaluations/search/courseevaluation/{courseId}")
	public @ResponseBody EvaluationResponse fetchSearchedEvaluationsOnCourse(HttpServletRequest request,
	@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo,
	@PathVariable("courseId") int courseId, @RequestParam("keyword") String keyword) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<Evaluation> evaluationService = this.evaluationManager.fetchSearchedEvaluationOnCourseTable(courseId,keyword,pageSize,pageNo);
			int totalRecords = this.evaluationManager.countTotalSearchedEvaluationsOnCourse(courseId,keyword);
			return new EvaluationResponse(LmsStatus.SUCCESS,evaluationService,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EvaluationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="evaluations/search")
	public @ResponseBody EvaluationResponse fetchSearchedEvaluations(HttpServletRequest request,
	@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo,
	@RequestParam("keyword") String keyword) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<Evaluation> evaluationService = this.evaluationManager.fetchSearchedEvaluationTable(keyword,pageSize,pageNo);
			int totalRecords = this.evaluationManager.countTotalSearchedEvaluations(keyword);
			return new EvaluationResponse(LmsStatus.SUCCESS,evaluationService,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EvaluationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="evaluations/generate-evaluationcode")
	public @ResponseBody EvaluationResponse generateEvaluationCode(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new EvaluationResponse(LmsStatus.SUCCESS,this.evaluationManager.generateEvaluationCode());
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EvaluationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="evaluations/{evaluationCode}")
	public @ResponseBody EvaluationResponse checkEvaluationCode(HttpServletRequest request,
			@PathVariable("evaluationCode") String evaluationCode) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new EvaluationResponse(LmsStatus.SUCCESS,this.evaluationManager.checkEvaluationCode(evaluationCode));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EvaluationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="evaluations/questions/{evaluationId}")
	public @ResponseBody EvaluationQuestionResponse fetchEvaluationList(HttpServletRequest request,
			@PathVariable("evaluationId") int evaluationId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<EvaluationQuestion> evaluationQuestions = (List<EvaluationQuestion>) this.evaluationManager.fetchEvaluationQuestions(evaluationId);
			return new EvaluationQuestionResponse(LmsStatus.SUCCESS,evaluationQuestions);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EvaluationQuestionResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EvaluationQuestionResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EvaluationQuestionResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="evaluations")
	public @ResponseBody EvaluationResponse addEvaluation(HttpServletRequest request,
			@RequestBody Evaluation evaluation) {
		try {
			User users = SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			EmployeeInfo empinfo = new EmployeeInfo();
			empinfo.setUserId(users.getUserId());
			EmployeeInfo empinfos = this.evaluationManager.fetchEmployeeInfo(empinfo);
			evaluation.setModifiedBy(empinfos.getFullName().toString());
			this.evaluationRepository.save(evaluation);
			return new EvaluationResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EvaluationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="evaluations/questions/{evaluationId}")
	public @ResponseBody EvaluationResponse updateEvaluationList(HttpServletRequest request,
			@PathVariable("evaluationId") int evaluationId,
			@RequestBody List<EvaluationQuestion> evaluationquestion) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.evaluationManager.evaluationQuestions(evaluationquestion,evaluationId);
			return new EvaluationResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EvaluationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="evaluations/{evaluationId}")
	public @ResponseBody EvaluationResponse updateEvaluation(HttpServletRequest request,
			@RequestBody Evaluation evaluation, @PathVariable("evaluationId") int evaluationId) {
		try {
			User users = SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			EmployeeInfo empinfo = new EmployeeInfo();
			empinfo.setUserId(users.getUserId());
			EmployeeInfo empinfos = this.evaluationManager.fetchEmployeeInfo(empinfo);
			evaluation.setModifiedBy(empinfos.getFullName().toString());
			this.evaluationManager.updateEvaluationInfo(evaluation, evaluationId);
			return new EvaluationResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EvaluationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="evaluations/active/pages/classevaluation")
	public @ResponseBody EvaluationResponse fetchEvaluationsOnClass(HttpServletRequest request,
	@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo,
	@RequestParam("classId") int classId, @RequestParam("courseId") int courseId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TableCommand tableCommand = new TableCommand();
			tableCommand.setClassId(classId);
			tableCommand.setCourseId(courseId);
			List<Evaluation> evaluationService = this.evaluationManager.fetchEvaluationsOnClass(tableCommand,pageSize,pageNo);
			int totalRecords = this.evaluationManager.countTotalEvaluationsOnClass(tableCommand);
			return new EvaluationResponse(LmsStatus.SUCCESS,evaluationService,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EvaluationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="evaluations/active/search/classevaluation")
	public @ResponseBody EvaluationResponse searchEvaluationsOnClass(HttpServletRequest request,
	@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo,
	@RequestParam("classId") int classId, @RequestParam("courseId") int courseId,
	@RequestParam("keyword") String keyword) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TableCommand tableCommand = new TableCommand();
			tableCommand.setKeyword(keyword);
			tableCommand.setClassId(classId);
			tableCommand.setCourseId(courseId);
			List<Evaluation> evaluationService = this.evaluationManager.searchEvaluationsOnClass(tableCommand,pageSize,pageNo);
			int totalRecords = this.evaluationManager.countTotalSearchEvaluationsOnClass(tableCommand);
			return new EvaluationResponse(LmsStatus.SUCCESS,evaluationService,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EvaluationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="evaluations/employee-evaluations")
	public @ResponseBody EmployeeEvaluationResponse fetchEmployeeEvaluations(HttpServletRequest request,
	@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo,
	@RequestParam("classEvaluationId") int classEvaluationId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<EmployeeEvaluation> evaluationService = this.evaluationManager.fetchEmployeeEvaluations(classEvaluationId,pageSize,pageNo);
			int totalRecords = this.evaluationManager.countTotalEmployeeEvaluations(classEvaluationId);
			return new EmployeeEvaluationResponse(LmsStatus.SUCCESS,evaluationService,totalRecords);
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
	/***************************************************************************************************************************/
	/* END: RESTFUL API																					 **/
	/**************************************************************************************************************************/
}
