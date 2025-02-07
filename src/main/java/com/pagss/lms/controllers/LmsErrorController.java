package com.pagss.lms.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Gerald
 *
 */
@RestController
public class LmsErrorController implements ErrorController{
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@GetMapping(value="/error")
	public ModelAndView handleError(HttpServletRequest request) throws Exception {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("portal/errorpage");
		if(status != null) {
			int statusCode = Integer.valueOf(status.toString());
			if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				mav.addObject("message","Session has Expired");
			} else if(statusCode == HttpStatus.NOT_FOUND.value()) {
				mav.addObject("message","Page not Found");
			}
		}
		return mav;
	}
}
