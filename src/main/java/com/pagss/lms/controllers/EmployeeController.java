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

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.constants.LmsStatus;
import com.pagss.lms.domains.EmployeeInfo;
import com.pagss.lms.domains.User;
import com.pagss.lms.exceptions.SessionExpiredException;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.handlers.StackTraceHandler;
import com.pagss.lms.manager.interfaces.EmployeeInfoManager;
import com.pagss.lms.responses.EmployeeInfoResponse;
import com.pagss.lms.spring.data.repositories.EmployeeInfoRepository;
@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeInfoManager employeeInfoManager;
	@Autowired
	private EmployeeInfoRepository employeeInfoRepository;
	
	@GetMapping(value="employeeinfo/{employeeCode}")
	public @ResponseBody EmployeeInfoResponse checkEmployeeCode(HttpServletRequest request,
			@PathVariable("employeeCode") String employeeCode) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			EmployeeInfo empinfo = new EmployeeInfo();
			empinfo.setEmployeeCode(employeeCode);
			return new EmployeeInfoResponse(LmsStatus.SUCCESS,this.employeeInfoManager.countEmployeeCode(empinfo));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EmployeeInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EmployeeInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EmployeeInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="employeeinfo")
	public @ResponseBody EmployeeInfoResponse addEmployeeInfo(HttpServletRequest request,
			@RequestBody EmployeeInfo employeeinfo) {
		try {
			User users = SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			EmployeeInfo empinfo = new EmployeeInfo();
			empinfo.setUserId(users.getUserId());
			EmployeeInfo empinfos = this.employeeInfoManager.fetchEmployeeInfo(empinfo);
			employeeinfo.setModifiedBy(empinfos.getFullName().toString());
			this.employeeInfoRepository.save(employeeinfo);
			return new EmployeeInfoResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EmployeeInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EmployeeInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EmployeeInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}

	@PutMapping(value="employeeinfo/{employeeId}")
	public @ResponseBody EmployeeInfoResponse updateEmployeeInfo(HttpServletRequest request,
			@PathVariable("employeeId") int empId, @RequestBody EmployeeInfo employeeinfo) {
		try {
			User users = SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			EmployeeInfo empinfo = new EmployeeInfo();
			empinfo.setUserId(users.getUserId());
			EmployeeInfo empinfos = this.employeeInfoManager.fetchEmployeeInfo(empinfo);
			employeeinfo.setEmployeeId(empId);
			employeeinfo.setModifiedBy(empinfos.getFullName().toString());
			this.employeeInfoRepository.save(employeeinfo);
			return new EmployeeInfoResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EmployeeInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EmployeeInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EmployeeInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="employeeinfo/trainers")
	public @ResponseBody EmployeeInfoResponse fetchTrainers(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new EmployeeInfoResponse(LmsStatus.SUCCESS,this.employeeInfoManager.fetchTrainers());
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EmployeeInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EmployeeInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EmployeeInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="employeeinfo/trainers/status/{status}")
	public @ResponseBody EmployeeInfoResponse fetchTrainers(HttpServletRequest request,
			@PathVariable("status") int status) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new EmployeeInfoResponse(LmsStatus.SUCCESS,this.employeeInfoManager.fetchTrainersByStatus(status));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EmployeeInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EmployeeInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EmployeeInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="employeeinfo/trainees/pages")
	public @ResponseBody EmployeeInfoResponse fetchTrainees(HttpServletRequest request,
			@RequestParam("classId") int classId,@RequestParam("pageSize") int pageSize,
			@RequestParam("pageNumber") int pageNumber) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<EmployeeInfo> employeeInfos = this.employeeInfoManager.fetchEmployeeInfoWithoutClass(classId, 
				pageNumber, pageSize);
			int totalRecords = this.employeeInfoManager.countEmployeeInfoWithoutClass(classId);
			return new EmployeeInfoResponse(LmsStatus.SUCCESS,totalRecords,employeeInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EmployeeInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EmployeeInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EmployeeInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="employeeinfo/trainees/search")
	public @ResponseBody EmployeeInfoResponse searchTrainees(HttpServletRequest request,
			@RequestParam("classId") int classId,@RequestParam("pageSize") int pageSize,
			@RequestParam("pageNumber") int pageNumber, @RequestParam("jobRoleId") int jobRoleId,
			@RequestParam("userGroupId") int userGroupId, @RequestParam("keyword") String keyword) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TableCommand tblCommand = new TableCommand();
			tblCommand.setPageSize(pageSize);
			tblCommand.setPageNumber(pageNumber);
			tblCommand.setKeyword(keyword);
			tblCommand.setJobRoleId(jobRoleId);
			tblCommand.setUserGroupId(userGroupId);
			tblCommand.setClassId(classId);
			List<EmployeeInfo> employeeInfos = this.employeeInfoManager.searchEmployeeInfoWithoutClass(tblCommand);
			int totalRecords = this.employeeInfoManager.countEmployeeInfoWithoutClass(tblCommand);
			return new EmployeeInfoResponse(LmsStatus.SUCCESS,totalRecords,employeeInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EmployeeInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EmployeeInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EmployeeInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
}
