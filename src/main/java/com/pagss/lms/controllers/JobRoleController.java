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
import com.pagss.lms.domains.JobRole;
import com.pagss.lms.exceptions.SessionExpiredException;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.handlers.StackTraceHandler;
import com.pagss.lms.manager.interfaces.JobRoleManager;
import com.pagss.lms.responses.JobRoleResponse;
import com.pagss.lms.spring.data.repositories.JobRoleRepository;

/**
 * 
 * @author Gerald
 *
 */
@RestController
public class JobRoleController {
	
	@Autowired
	private JobRoleRepository jobRoleRepository;
	@Autowired
	private JobRoleManager jobRoleManager;
	/***************************************************************************************************************************/
	/* START: NORMAL HTTPREQUEST	 																						 **/
	/**************************************************************************************************************************/
	@GetMapping(value="admin.jobroles")
	public ModelAndView gotoJobRolesPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		return new ModelAndView("admin/admin-jobroles");
	}
	/***************************************************************************************************************************/
	/* END: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	/***************************************************************************************************************************/
	/* START: RESTFUL API																						 **/
	/**************************************************************************************************************************/
	@GetMapping(value="jobroles/pages")
	public @ResponseBody JobRoleResponse fetchJobRoles(HttpServletRequest request,
			@RequestParam("pageSize") int pageSize,@RequestParam("pageNumber") int pageNo) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<JobRole> jobRoles = this.jobRoleManager.fetchJobRoles(pageSize, pageNo);
			int totalRecords = this.jobRoleManager.countFetchJobRoles();
			return new JobRoleResponse(LmsStatus.SUCCESS,jobRoles,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new JobRoleResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new JobRoleResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new JobRoleResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="jobroles/search")
	public @ResponseBody JobRoleResponse searchJobRole(HttpServletRequest request,@RequestParam("pageSize") int pageSize, 
		@RequestParam("pageNumber") int pageNo, @RequestParam("sortDirection") String sortDirection,
		@RequestParam("sortName") String sortColumnName,@RequestParam("keyword") String keyword) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			
			TableCommand tableCommand = new TableCommand();
			tableCommand.setKeyword(keyword);
			tableCommand.setSortDirection(sortDirection);
			tableCommand.setSortColumnName(sortColumnName);
			tableCommand.setPageSize(pageSize);
			tableCommand.setPageNumber(pageNo);
			tableCommand.setKeyword(keyword);
			List<JobRole> searchJobRoles = this.jobRoleManager.searchJobRole(tableCommand);
			int totalRecords = this.jobRoleManager.countSearchJobRole(tableCommand.getKeyword());
			return new JobRoleResponse(LmsStatus.SUCCESS,searchJobRoles,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new JobRoleResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new JobRoleResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new JobRoleResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="jobrole")
	public @ResponseBody JobRoleResponse createJobRole(HttpServletRequest request,@RequestBody JobRole jobRole) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			if(this.jobRoleManager.countJobCode(jobRole.getJobCode()) != 0) {
				return new JobRoleResponse(LmsStatus.JOBROLE_CODE_EXIST);
			} else {
				this.jobRoleRepository.save(jobRole);
				return new JobRoleResponse(LmsStatus.SUCCESS);
			}
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new JobRoleResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new JobRoleResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new JobRoleResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="jobrole/{jobRoleId}")
	public @ResponseBody JobRoleResponse fetchJobRole(HttpServletRequest request,@PathVariable("jobRoleId") int jobRoleId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			JobRole jobRole = this.jobRoleRepository.findById(jobRoleId).get();
			return new JobRoleResponse(LmsStatus.SUCCESS,jobRole);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new JobRoleResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new JobRoleResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new JobRoleResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="jobrole/{jobRoleId}")
	public @ResponseBody JobRoleResponse updateJobRole(HttpServletRequest request,@PathVariable("jobRoleId") int jobRoleId,
			@RequestBody JobRole jobRole) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			jobRole.setJobRoleId(jobRoleId);
			this.jobRoleRepository.save(jobRole);
			return new JobRoleResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new JobRoleResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new JobRoleResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new JobRoleResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="jobroles")
	public @ResponseBody JobRoleResponse fetchJobRoles(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<JobRole> jobRoles = (List<JobRole>) this.jobRoleRepository.findAll();
			return new JobRoleResponse(LmsStatus.SUCCESS,jobRoles);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new JobRoleResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new JobRoleResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new JobRoleResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="jobroles/generate-jobcode")
	public @ResponseBody JobRoleResponse generateJobCode(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new JobRoleResponse(LmsStatus.SUCCESS,this.jobRoleManager.generateJobCode());
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new JobRoleResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new JobRoleResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new JobRoleResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	/***************************************************************************************************************************/
	/* END: RESTFUL API																						 **/
	/**************************************************************************************************************************/
	
}
