package com.pagss.lms.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.pagss.lms.constants.LmsTrailAction;
import com.pagss.lms.constants.LmsUserTypeData;
import com.pagss.lms.domains.AuditTrail;
import com.pagss.lms.domains.User;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.manager.interfaces.UserManager;
import com.pagss.lms.spring.data.repositories.AuditTrailRepository;

/*
 * @author	Billy
 */

@RestController
public class AuthController {
	
	@Autowired
	private AuditTrailRepository auditTrailRepository;
	@Autowired
	private UserManager userManager;
	/***************************************************************************************************************************/
	/* START: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	@GetMapping(value="login")
	public ModelAndView goToLoginPage(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		ModelAndView mav = new ModelAndView();
		if(session.getAttribute("user") == null) {mav.setViewName("portal/login");
		} else {
			User user = SessionHandler.getSessionUser(request);
			if(userManager.checkPasswordReset(user) == 1) {
				return new ModelAndView(new RedirectView("reset-password"));
			} else if(userManager.checkPasswordReset(user) != 1) {
				if(user.getUserTypeId() == LmsUserTypeData.ADMINISTRATOR) {
					return new ModelAndView(new RedirectView("admin.home"));
				} else if(user.getUserTypeId() == LmsUserTypeData.TRAINER) {
					return new ModelAndView(new RedirectView("admin.home"));
				} else if(user.getUserTypeId() == LmsUserTypeData.TRAINEE) {
					return new ModelAndView(new RedirectView("mytraining-inprogress"));
				}
			}
		}
		return mav;
	}
	
	@PostMapping(value="login")
	public ModelAndView verifyLoginCredentials(HttpServletRequest request) {
		User user = new User();
		ModelAndView mav = new ModelAndView();
		user.setUsername(request.getParameter("user").toString());
		user.setPassword(request.getParameter("pass").toString());
		if(userManager.countUser(user) > 0) {
			user = userManager.fetchUser(user);
			AuditTrail auditTrail = new AuditTrail();
			auditTrail.setUserId(user.getUserId());
			auditTrail.setTrailAction(LmsTrailAction.LOGGED_IN);
			this.auditTrailRepository.save(auditTrail);
			SessionHandler.createSession(request, user);
			if(userManager.checkPasswordReset(user) == 1) {
				return new ModelAndView(new RedirectView("reset-password"));
			} else if(userManager.checkPasswordReset(user) != 1) {
				if(user.getUserTypeId() == LmsUserTypeData.ADMINISTRATOR) {
					return new ModelAndView(new RedirectView("admin.home"));
				} else if(user.getUserTypeId() == LmsUserTypeData.TRAINER) {
					return new ModelAndView(new RedirectView("admin.home"));
				} else if(user.getUserTypeId() == LmsUserTypeData.TRAINEE) {
					return new ModelAndView(new RedirectView("mytraining-inprogress"));
				}
			}
			
		} else {
			mav.addObject("logincode","incorrect");
			mav.setViewName("redirect:/login");
		}
		return mav;
	}
	
	@GetMapping(value="logout")
	public ModelAndView logoutUser(HttpServletRequest request) throws Exception {
		User user = SessionHandler.getSessionUser(request);
		request.getSession().invalidate();
		AuditTrail auditTrail = new AuditTrail();
		auditTrail.setUserId(user.getUserId());
		auditTrail.setTrailAction(LmsTrailAction.LOGGED_OUT);
		this.auditTrailRepository.save(auditTrail);
		return new ModelAndView(new RedirectView("login"));
	}
	
	@GetMapping(value="admin.home")
	public ModelAndView goToAdminPage(HttpServletRequest request) throws Exception {
		User user = SessionHandler.getSessionUser(request);
		ModelAndView mav = new ModelAndView();
		if(userManager.checkPasswordReset(user) == 1) {
			return new ModelAndView(new RedirectView("reset-password"));
		} else if(userManager.checkPasswordReset(user) != 1) {
			if(user.getUserTypeId() == LmsUserTypeData.ADMINISTRATOR) {
				return new ModelAndView("admin/admin-home");
			} else if(user.getUserTypeId() == LmsUserTypeData.TRAINER) {
				return new ModelAndView("admin/admin-home");
			} else if(user.getUserTypeId() == LmsUserTypeData.TRAINEE) {
				return new ModelAndView(new RedirectView("mytraining-inprogress"));
			}
		}
		else {
			mav.setViewName("redirect:/login");
		}
		return mav;
	}
	
	@GetMapping(value="reset-password")
	public ModelAndView goToResetPasswordPage(HttpServletRequest request) throws Exception {
		User user = SessionHandler.getSessionUser(request);
		ModelAndView mav = new ModelAndView();
		if(userManager.checkPasswordReset(user) != 1) {
			if(user.getUserTypeId() == LmsUserTypeData.ADMINISTRATOR) {
				return new ModelAndView(new RedirectView("admin.home"));
			} else if(user.getUserTypeId() == LmsUserTypeData.TRAINER) {
				return new ModelAndView(new RedirectView("admin.home"));
			} else if(user.getUserTypeId() == LmsUserTypeData.TRAINEE) {
				return new ModelAndView(new RedirectView("mytraining-inprogress"));
			}
		}
		else {
			mav.setViewName("admin/admin-resetpassword");
		}
		return mav;
	}
	
	@PostMapping(value="save-password")
	public ModelAndView savePassword(HttpServletRequest request) throws Exception {
		User user = SessionHandler.getSessionUser(request);
		ModelAndView mav = new ModelAndView();
		String pass1 = request.getParameter("newPassword");
		String pass2 = request.getParameter("confirmPassword");
		if(pass1.equals(pass2)) {
			user.setPassword(pass2);
			this.userManager.saveNewPassword(user);
			if(user.getUserTypeId() == LmsUserTypeData.ADMINISTRATOR) {
				return new ModelAndView(new RedirectView("admin.home"));
			} else if(user.getUserTypeId() == LmsUserTypeData.TRAINER) {
				return new ModelAndView(new RedirectView("admin.home"));
			} else if(user.getUserTypeId() == LmsUserTypeData.TRAINEE) {
				return new ModelAndView(new RedirectView("mytraining-inprogress"));
			}
		}
		else {
			mav.addObject("errcode","notmatched");
			mav.setViewName("redirect:/reset-password");
		}
		return mav;
	}
	/***************************************************************************************************************************/
	/* END: NORMAL HTTPREQUEST																							 	 **/
	/**************************************************************************************************************************/
}
