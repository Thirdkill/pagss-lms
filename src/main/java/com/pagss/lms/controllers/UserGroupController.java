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
import com.pagss.lms.domains.UserGroup;
import com.pagss.lms.domains.UserGroupMember;
import com.pagss.lms.exceptions.SessionExpiredException;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.handlers.StackTraceHandler;
import com.pagss.lms.manager.interfaces.UserGroupManager;
import com.pagss.lms.responses.UserGroupMemberResponse;
import com.pagss.lms.responses.UserGroupResponse;
import com.pagss.lms.spring.data.repositories.UserGroupRepository;
/*
 * @author	Billy
 */


@RestController
public class UserGroupController {
	
	@Autowired
	public UserGroupRepository userGroupRepository;
	@Autowired
	public UserGroupManager userGroupManager;	
	
	/***************************************************************************************************************************/
	/* START: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	@GetMapping(value="admin.usergroup")
	public ModelAndView goToUserGroupPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-usergroup");
		
		return mav;
	}
	
	@GetMapping(value="admin.usergrouppage")
	public ModelAndView displayUserPage(HttpServletRequest request,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "id", required = false) String userId) 
					throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		if (action.equals("ADD")) {
			return new ModelAndView("admin/admin-usergroupprofile");
		}else if (action.equals("EDIT")) {
			return new ModelAndView("admin/admin-usergroupprofile");
		} else {
			return new ModelAndView(new RedirectView("admin.user"));
		}
	}
	/***************************************************************************************************************************/
	/* END: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/

	/***************************************************************************************************************************/
	/* START: RESTFUL API																					 **/
	/**************************************************************************************************************************/
	@GetMapping(value="usergroups/pages")
	public @ResponseBody UserGroupResponse fetchUserGroupsTbl(HttpServletRequest request,
		@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo) {
			try {
				SessionHandler.getSessionUser(request);
				List<UserGroup> userGroupService = this.userGroupManager.fetchUserGroup(pageSize,pageNo);
				int totalRecords = this.userGroupManager.countTotalUserGroups();
				return new UserGroupResponse(LmsStatus.SUCCESS,userGroupService,totalRecords);
			} catch (SessionExpiredException see) {
				StackTraceHandler.printStackTrace(see);
				return new UserGroupResponse(LmsStatus.SESSION_EXPIRED);	
			} catch (DataAccessException dae) {
				StackTraceHandler.printStackTrace(dae);
				return new UserGroupResponse(LmsStatus.QUERY_FAILED);
			} catch (Exception e) {
				StackTraceHandler.printStackTrace(e);
				return new UserGroupResponse(LmsStatus.UNHANDLED_ERROR);
			}
	}
	
	@GetMapping(value="usergroups")
	public @ResponseBody UserGroupResponse fetchUserGroups(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<UserGroup> usergroups = this.userGroupManager.fetchUserGroupList();
			return new UserGroupResponse(LmsStatus.SUCCESS,usergroups);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserGroupResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserGroupResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserGroupResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="usergroups/generate-usergroupcode")
	public @ResponseBody UserGroupResponse generateUserGroupCode(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new UserGroupResponse(LmsStatus.SUCCESS,this.userGroupManager.generateUserGroupCode());
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserGroupResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserGroupResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserGroupResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="usergroups/{userId}")
	public @ResponseBody UserGroupResponse fetchUserGroupAssignment(HttpServletRequest request,
			@PathVariable("userId") int userId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<UserGroup> userGroups = (List<UserGroup>) this.userGroupManager.fetchUserGroupAssignment(userId);
			return new UserGroupResponse(LmsStatus.SUCCESS,userGroups);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserGroupResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserGroupResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserGroupResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="usergroups/check/{userGroupCode}")
	public @ResponseBody UserGroupResponse checkUserGroupCode(HttpServletRequest request,
			@PathVariable("userGroupCode") String userGroupCode) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			UserGroup usergroups = new UserGroup();
			usergroups.setUserGroupCode(userGroupCode);
			int totalRecords = this.userGroupManager.countUserGroupCode(usergroups);
			return new UserGroupResponse(LmsStatus.SUCCESS,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserGroupResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserGroupResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserGroupResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="usergroups/members/{userGroupId}")
	public @ResponseBody UserGroupMemberResponse fetchUserGroupMembers(HttpServletRequest request,
			@PathVariable("userGroupId") int userGroupId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<UserGroupMember> userGroupMembers = (List<UserGroupMember>) this.userGroupManager.fetchUserGroupMembers(userGroupId);
			return new UserGroupMemberResponse(LmsStatus.SUCCESS,userGroupMembers);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserGroupMemberResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserGroupMemberResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserGroupMemberResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="usergroups/info/{userGroupId}")
	public @ResponseBody UserGroupResponse fetchUserGroupInfo(HttpServletRequest request,
			@PathVariable("userGroupId") int userGroupId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			UserGroup usergroup = new UserGroup();
			usergroup.setUserGroupId(userGroupId);
			UserGroup fetchedInfo = this.userGroupManager.fetchUserGroupInfo(usergroup);
			return new UserGroupResponse(LmsStatus.SUCCESS,fetchedInfo);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserGroupResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserGroupResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserGroupResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="usergroups")
	public @ResponseBody UserGroupResponse addUserGroup(HttpServletRequest request,
			@RequestBody UserGroup usergroup) {
		try {
			User users = SessionHandler.getSessionUser(request);
			this.userGroupManager.addUserGroup(usergroup, users);
			return new UserGroupResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserGroupResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserGroupResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserGroupResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="usergroups/info/{userGroupId}")
	public @ResponseBody UserGroupResponse updateUserGroup(HttpServletRequest request,
			@PathVariable("userGroupId") int userGroupId, @RequestBody UserGroup usergroup) {
		try {
			User users = SessionHandler.getSessionUser(request);
			usergroup.setUserGroupId(userGroupId);
			this.userGroupManager.updateUserGroup(usergroup, users);
			return new UserGroupResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserGroupResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserGroupResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserGroupResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="usergroups/search")
	public @ResponseBody UserGroupResponse searchUserGroup(HttpServletRequest request,
		@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo,
		@RequestParam("keyword") String keyword) {
		try {
			SessionHandler.getSessionUser(request);
			List<UserGroup> userGroupService = this.userGroupManager.searchUserGroup(keyword, pageSize,pageNo);
			int totalRecords = this.userGroupManager.countSearchedUserGroups(keyword);
			return new UserGroupResponse(LmsStatus.SUCCESS,userGroupService,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserGroupResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserGroupResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserGroupResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="usergroups/{userId}")
	public @ResponseBody UserGroupResponse updateUserGroupMember(HttpServletRequest request,
			@PathVariable("userId") int userId, @RequestBody List<UserGroupMember> usergroupmember) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.userGroupManager.usergroupMember(usergroupmember, userId);
			return new UserGroupResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserGroupResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserGroupResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserGroupResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="usergroups/members/{userGroupId}")
	public @ResponseBody UserGroupResponse updateUserGroupList(HttpServletRequest request,
			@PathVariable("userGroupId") int userGroupId, @RequestBody List<UserGroupMember> usergroupmember) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.userGroupManager.usergroupMembers(usergroupmember, userGroupId);
			return new UserGroupResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new UserGroupResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new UserGroupResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new UserGroupResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	/***************************************************************************************************************************/
	/* END: RESTFUL API																					 **/
	/**************************************************************************************************************************/

}
