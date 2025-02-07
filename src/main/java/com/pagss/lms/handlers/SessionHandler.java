package com.pagss.lms.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.pagss.lms.domains.User;
import com.pagss.lms.exceptions.ForbiddenAccessException;
import com.pagss.lms.exceptions.SessionExpiredException;


public class SessionHandler {

	private static class Session {
		public static final String USER = "user";
	}
	
	public static final String SESSION_EXPIRED_MESSAGE = "Session has expired.";	
	public static final String FORBIDDEN_MESSAGE = "Access is forbidden.";
	
	
	/**
	 * Purpose: Use to create a session, example during login.
	 * @param request
	 * @param user
	 */
	public static void createSession (HttpServletRequest request, User user) {
		request.getSession().setAttribute(Session.USER, user);
	}
	
	/**
	 * Purpose: Use to get the session attributes that contains a User object.
	 * @param request
	 * @return
	 * @throws SessionExpiredException
	 */
	public static User getSessionUser (HttpServletRequest request) throws SessionExpiredException {
		HttpSession session = request.getSession();
		if (session.getAttribute(Session.USER) != null) {
			return (User) session.getAttribute(Session.USER);
		} else {			
			throw new SessionExpiredException(SESSION_EXPIRED_MESSAGE);
		}
	}
	
	/**
	 * Purpose: Check the access level of the logged-in user. Returns an exception
	 * if the accesslevel did not match for each of the methods.
	 * @param request
	 * @param accessLevel
	 * @return
	 * @throws ForbiddenAccessException
	 */
	public static void checkSessionUserAccess (HttpServletRequest request) throws ForbiddenAccessException {		
		HttpSession session = request.getSession();
		User sessionUser = (User)session.getAttribute(Session.USER); 	
		if(sessionUser.getUserTypeId() == 0) {throw new ForbiddenAccessException(FORBIDDEN_MESSAGE);}
	}
} 
