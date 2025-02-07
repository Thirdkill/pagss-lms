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
import org.springframework.web.servlet.view.RedirectView;

import com.pagss.lms.constants.LmsStatus;
import com.pagss.lms.domains.User;
import com.pagss.lms.domains.UserType;
import com.pagss.lms.exceptions.SessionExpiredException;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.handlers.StackTraceHandler;
import com.pagss.lms.manager.interfaces.EmployeeInfoManager;
import com.pagss.lms.manager.interfaces.UserManager;
import com.pagss.lms.responses.UserResponse;

import com.pagss.lms.responses.UserTypeResponse;
import com.pagss.lms.spring.data.repositories.UserTypeRepository;

/**
 * 
 * @author Gerald, Billy
 *
 */
@RestController
public class UserController {

	@Autowired
	private UserTypeRepository userTypeRepository;
	@Autowired
	private UserManager userManager;
	@Autowired
	public EmployeeInfoManager employeeInfoManager;

	/***************************************************************************************************************************/
	/* START: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	@GetMapping(value="admin.user")
	public ModelAndView gotoUsersPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		return new ModelAndView("admin/admin-users");
	}
	
	@GetMapping(value="admin.viewuser")
	public ModelAndView displayViewUsersPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		return new ModelAndView("admin/admin-viewuser");
	}
	@GetMapping(value="admin.viewuser.jobrole")
	public ModelAndView displayViewUserTFJobRolePage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		return new ModelAndView("admin/admin-viewuserjobrole");
	}
	
	@GetMapping(value="admin.viewuser.usergroup")
	public ModelAndView displayViewUserTFUserGroupPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		return new ModelAndView("admin/admin-viewuserusergroup");
	}

	@GetMapping(value="admin.userpage")
	public ModelAndView displayUserPage(HttpServletRequest request,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "id", required = false) String userId) 
					throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		if (action.equals("ADD")) {
			return new ModelAndView("admin/admin-userprofilefield");
		}else if (action.equals("EDIT")) {
			return new ModelAndView("admin/admin-userprofilefield");
		} else {
			return new ModelAndView(new RedirectView("admin.user"));
		}
	}
	
	@GetMapping(value="admin.massupload")
	public ModelAndView gotoUserMassUploadPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		return new ModelAndView("admin/admin-massupload");
	}
	
	/***************************************************************************************************************************/
	/* END: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	
	/***************************************************************************************************************************/
	/* START: RESTFUL API																					 **/
	/**************************************************************************************************************************/
	@GetMapping(value="users")
	public @ResponseBody UserResponse fetchUsers(HttpServletRequest request,
	@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<User> userService = this.userManager.fetchUsers(pageSize,pageNo);
			int totalRecords = this.userManager.countTotalUsers();
			return new UserResponse(LmsStatus.SUCCESS,userService,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	@GetMapping(value="users/userlist")
	public @ResponseBody UserResponse fetchUserlist(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<User> userService = this.userManager.fetchUserlist();
			return new UserResponse(LmsStatus.SUCCESS,userService);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	@GetMapping(value="users/totalUser")
	public @ResponseBody UserResponse countUserlist(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			int totalRecords = this.userManager.countTotalRecords();
			int totalActiveRecords = this.userManager.countTotalActiveRecords();
			int totalInactiveRecords = this.userManager.countTotalInactiveRecords();
			return new UserResponse(LmsStatus.SUCCESS,totalRecords,totalActiveRecords,totalInactiveRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	@GetMapping(value="users/usertypes")
	public @ResponseBody UserTypeResponse fetchUserType(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<UserType> userTypes = (List<UserType>) this.userTypeRepository.findAll();
			return new UserTypeResponse(LmsStatus.SUCCESS,userTypes);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserTypeResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserTypeResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserTypeResponse(LmsStatus.UNHANDLED_ERROR);
		}
		
	}
	@PostMapping(value="users/search")
	public @ResponseBody UserResponse fetchUser(HttpServletRequest request,
			@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			User user = new User();
			user.setJobroleId(Integer.parseInt(request.getParameter("jobRoleId")));
			user.setUserTypeId(Integer.parseInt(request.getParameter("userTypeId")));
			user.setStatus(Integer.parseInt(request.getParameter("status")));
			user.setLastName(request.getParameter("lastName").toString());
			user.setFirstName(request.getParameter("firstName").toString());
			user.setEmployeeCode(request.getParameter("employeeNo").toString());
			List<User> searchedUsers = this.userManager.searchUsers(user, pageSize, pageNo);
			int totalRecords = this.userManager.countTotalSearchedUsers(user);
			return new UserResponse(LmsStatus.SUCCESS,searchedUsers,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="users/fetch-employee")
	public @ResponseBody UserResponse fetchUserList(HttpServletRequest request,
			@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<User> searchedUsers = this.userManager.fetchUserList(pageSize, pageNo);
			int totalRecords = this.userManager.countTotalUserList();
			return new UserResponse(LmsStatus.SUCCESS,searchedUsers,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="users/search-employee")
	public @ResponseBody UserResponse fetchSearchedUserList(HttpServletRequest request,
			@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			User user = new User();
			user.setJobroleId(Integer.parseInt(request.getParameter("jobRoleId")));
			user.setLastName(request.getParameter("lastName").toString());
			user.setFirstName(request.getParameter("firstName").toString());
			user.setEmployeeCode(request.getParameter("employeeNo").toString());
			List<User> searchedUsers = this.userManager.searchUserList(user, pageSize, pageNo);
			int totalRecords = this.userManager.countTotalSearchedUserList(user);
			return new UserResponse(LmsStatus.SUCCESS,searchedUsers,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="users/generate-usercode")
	public @ResponseBody UserResponse generateUserCode(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new UserResponse(LmsStatus.SUCCESS,this.userManager.generateUserCode());
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="users")
	public @ResponseBody UserResponse addUserInfo(HttpServletRequest request,
			@RequestBody User user) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.userManager.createUserCredentials(user);
			return new UserResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="users/{userId}")
	public @ResponseBody UserResponse updateUserInfo(HttpServletRequest request,
			@PathVariable("userId") int userId, @RequestBody User user) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.userManager.updateUserInfo(user, userId);
			return new UserResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="users/password/{userId}")
	public @ResponseBody UserResponse updateUserPassword(HttpServletRequest request,
			@PathVariable("userId") int userId, @RequestBody User user) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.userManager.generatePassword(user, userId);
			return new UserResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="users/{userId}")
	public @ResponseBody UserResponse fetchUserInfo(HttpServletRequest request,
			@PathVariable("userId") int userId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			User user = new User();
			user.setUserId(userId);
			User fetchedInfo = this.userManager.fetchUserInfo(user);
			return new UserResponse(LmsStatus.SUCCESS,fetchedInfo);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	/***************************************************************************************************************************/
	/* END: RESTFUL API																					 **/
	/**************************************************************************************************************************/
}
