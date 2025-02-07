package com.pagss.lms.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pagss.lms.constants.LmsStatus;
import com.pagss.lms.domains.QuestionType;
import com.pagss.lms.exceptions.SessionExpiredException;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.handlers.StackTraceHandler;
import com.pagss.lms.manager.interfaces.QuestionTypeManager;
import com.pagss.lms.responses.QuestionTypeResponse;
import com.pagss.lms.spring.data.repositories.QuestionTypeRepository;

@RestController
public class QuestionTypeController {

	@Autowired
	public QuestionTypeRepository questionTypeRepository;
	@Autowired
	public QuestionTypeManager questionTypeManager;
	/***************************************************************************************************************************/
	/* START: RESTFUL API																						 **/
	/**************************************************************************************************************************/
	@GetMapping(value="questiontypes")
	public @ResponseBody QuestionTypeResponse fetchQuestionTypes(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<QuestionType> questionTypes = this.questionTypeManager.fetchQuestionTypes();
			return new QuestionTypeResponse(LmsStatus.SUCCESS,questionTypes);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new QuestionTypeResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new QuestionTypeResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new QuestionTypeResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	/***************************************************************************************************************************/
	/* END: RESTFUL API																						 **/
	/**************************************************************************************************************************/
	
}
