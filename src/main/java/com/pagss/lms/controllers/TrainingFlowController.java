package com.pagss.lms.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pagss.lms.constants.LmsStatus;
import com.pagss.lms.domains.CourseInfo;
import com.pagss.lms.domains.TrainingFlow;
import com.pagss.lms.exceptions.SessionExpiredException;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.handlers.StackTraceHandler;
import com.pagss.lms.manager.interfaces.TrainingFlowManager;
import com.pagss.lms.responses.TrainingFlowResponse;
import com.pagss.lms.spring.data.repositories.TrainingFlowRepository;

@RestController
public class TrainingFlowController {

	@Autowired
	private TrainingFlowRepository trainingFlowRepository;
	@Autowired
	private TrainingFlowManager trainingFlowManager;
	
	/***************************************************************************************************************************/
	/* START: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	@GetMapping(value="admin.trainingworkflow")
	public ModelAndView gotoTrainingWorkFlowPage(HttpServletRequest request) throws Exception{
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		return new ModelAndView("admin/admin-trainingworkflow");
	}
	/***************************************************************************************************************************/
	/* END: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	/***************************************************************************************************************************/
	/* START: RESTFUL API																							 **/
	/**************************************************************************************************************************/
	@GetMapping(value="trainingflow/pages")
	public @ResponseBody TrainingFlowResponse fetchTrainingFlowPages(HttpServletRequest request,@RequestParam("pageNumber") int pageNo, 
		@RequestParam("pageSize") int pageSize, @RequestParam("assignmentType") int assignmentType, 
		@RequestParam("assignmentTypeId") int assignmentTypeId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TrainingFlow trainingFlow = new TrainingFlow();
			trainingFlow.setAssignmentType(assignmentType);
			trainingFlow.setAssignmentTypeId(assignmentTypeId);
			List<TrainingFlow> trainingFlows = this.trainingFlowManager.fetchTrainingFlowsPage(pageSize, pageNo, trainingFlow);
			int totalRecords = this.trainingFlowManager.countFetchTrainingFlows(assignmentType);
			return new TrainingFlowResponse(LmsStatus.SUCCESS,trainingFlows,totalRecords); 
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingFlowResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingFlowResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingFlowResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="trainingflowlist/jobrole/{jobRoleId}")
	public @ResponseBody TrainingFlowResponse fetchTrainingFlowList(HttpServletRequest request,
			@PathVariable("jobRoleId") int jobRoleId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TrainingFlow trainingFlow = new TrainingFlow();
			trainingFlow.setJobRoleId(jobRoleId);
			List<TrainingFlow> trainingFlows = this.trainingFlowManager.fetchTrainingFlowList(trainingFlow);
			return new TrainingFlowResponse(LmsStatus.SUCCESS,trainingFlows); 
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingFlowResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingFlowResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingFlowResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="trainingflowlist/usergroup/{userGroupId}")
	public @ResponseBody TrainingFlowResponse fetchTrainingFlowUserGroupList(HttpServletRequest request,
			@PathVariable("userGroupId") int userGroupId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TrainingFlow trainingFlow = new TrainingFlow();
			trainingFlow.setUserGroupId(userGroupId);
			List<TrainingFlow> trainingFlows = this.trainingFlowManager.fetchTrainingFlowUserGroupList(trainingFlow);
			return new TrainingFlowResponse(LmsStatus.SUCCESS,trainingFlows); 
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingFlowResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingFlowResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingFlowResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="trainingflows")
	public @ResponseBody TrainingFlowResponse fetchTrainingFlows(HttpServletRequest request,
		@RequestParam("assignmentType") int assignmentType,@RequestParam("assignmentTypeId") int assignmentTypeId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			
			TrainingFlow trainingFlow = new TrainingFlow();
			trainingFlow.setAssignmentType(assignmentType);
			trainingFlow.setAssignmentTypeId(assignmentTypeId);
			List<TrainingFlow> trainingFlows = this.trainingFlowManager.fetchTrainingFlows(trainingFlow);
			return new TrainingFlowResponse(LmsStatus.SUCCESS,trainingFlows); 
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingFlowResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingFlowResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingFlowResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="trainingFlow/{trainingFlowId}")
	public @ResponseBody TrainingFlowResponse updateTrainingFlow(HttpServletRequest request,
			@RequestBody TrainingFlow trainingFlow, @PathVariable("trainingFlowId") int trainingFlowId ) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			
			this.trainingFlowManager.updateTrainingFlow(trainingFlow);
			return new TrainingFlowResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingFlowResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingFlowResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingFlowResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@DeleteMapping(value="trainingFlow/{trainingFlowId}")
	public @ResponseBody TrainingFlowResponse removeTrainingFlow(HttpServletRequest request,
		@PathVariable("trainingFlowId") int trainingFlowId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.trainingFlowManager.updatePreCourseIds(trainingFlowId);
			this.trainingFlowRepository.deleteById(trainingFlowId);
			return new TrainingFlowResponse(LmsStatus.SUCCESS);	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingFlowResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingFlowResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingFlowResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="trainingflows")
	public @ResponseBody TrainingFlowResponse createTrainingFlow(HttpServletRequest request,
		@RequestBody List<CourseInfo> courseInfos) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.trainingFlowManager.createTrainingFlows(courseInfos);
			return new TrainingFlowResponse(LmsStatus.SUCCESS);	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingFlowResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingFlowResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingFlowResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="trainingflow/trainingflows")
	public @ResponseBody TrainingFlowResponse updateTrainingFlowOrderNos(HttpServletRequest request,
		@RequestBody List<TrainingFlow> trainingFlows) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.trainingFlowManager.updateTrainingFLowOrderNos(trainingFlows);
			return new TrainingFlowResponse(LmsStatus.SUCCESS);	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingFlowResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingFlowResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingFlowResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	/***************************************************************************************************************************/
	/* END: RESTFUL API																							 **/
	/**************************************************************************************************************************/
	
}
