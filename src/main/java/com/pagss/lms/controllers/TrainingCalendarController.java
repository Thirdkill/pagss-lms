package com.pagss.lms.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pagss.lms.handlers.SessionHandler;

@RestController
public class TrainingCalendarController {

	/***************************************************************************************************************************/
	/* START: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	@GetMapping(value="admin.trainingcalendar")
	public ModelAndView goToTrainingCalendarPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		return new ModelAndView("admin/admin-trainingcalendar");
	}
	/***************************************************************************************************************************/
	/* END: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	
}
