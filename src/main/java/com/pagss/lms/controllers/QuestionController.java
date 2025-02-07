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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.pagss.lms.commands.FileUploadCommand;
import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.constants.LmsFileServer;
import com.pagss.lms.constants.LmsStatus;
import com.pagss.lms.domains.Question;
import com.pagss.lms.exceptions.SessionExpiredException;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.handlers.StackTraceHandler;
import com.pagss.lms.manager.interfaces.EnumerationAnswerManager;
import com.pagss.lms.manager.interfaces.QuestionManager;
import com.pagss.lms.responses.EnumerationAnswerResponse;
import com.pagss.lms.responses.QuestionResponse;
import com.pagss.lms.utilities.FileServerOperator;

@RestController
public class QuestionController {

	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private FileServerOperator fileServerOperator;
	@Autowired
	private EnumerationAnswerManager enumerationManager;
	/***************************************************************************************************************************/
	/* START: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	@GetMapping(value="admin.questions")
	public ModelAndView gotoQuestionPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		return new ModelAndView("admin/admin-questions");
	}
	
	@GetMapping(value="admin.question")
	public ModelAndView gotoAddQuestionPage(HttpServletRequest request, 
		@RequestParam(value="action",required=false) String action,
		@RequestParam(value="questionId",required=false) Integer questionId) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-createquestion");
		mav.addObject("action",action);
		mav.addObject("questionId",questionId);
		return mav;
	}
	/***************************************************************************************************************************/
	/* END: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	/***************************************************************************************************************************/
	/* START: Question Restful API																						 **/
	/**************************************************************************************************************************/
	@PostMapping(value="question")
	public @ResponseBody QuestionResponse createQuestion(HttpServletRequest request, @RequestBody Question question) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new QuestionResponse(LmsStatus.SUCCESS,this.questionManager.createQuestion(question));	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new QuestionResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new QuestionResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new QuestionResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="question/{questionId}/media")
	public @ResponseBody QuestionResponse createQuestion(MultipartHttpServletRequest request, 
			@PathVariable("questionId") int questionId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			MultipartFile file = request.getFile("fileUpload");
			if(fileServerOperator.validateFileFormat(file)) {
				if(file.getSize() > 10978680) {
					return new QuestionResponse(LmsStatus.FILE_SIZE_IS_INVALID);	
				} else {
					FileUploadCommand uploadCmd = new FileUploadCommand();
					uploadCmd.setFileName(new StringBuffer()
							.append(questionId).append("_media").toString());
					uploadCmd.setFileDir(new StringBuffer()
							.append(LmsFileServer.MEDIA_FOLDER)
							.append(LmsFileServer.QUESTION).append("/")
							.append(questionId).toString());
					uploadCmd.setContentType(file.getContentType());
					Question question = new Question();
					question.setQuestionId(questionId);
					question.setMediaUrl(fileServerOperator.uploadFile(file, uploadCmd));
					question.setMediaFileName(file.getOriginalFilename());
					questionManager.updateMediaUrl(question);
					return new QuestionResponse(LmsStatus.SUCCESS);	
				}
			} else {
				return new QuestionResponse(LmsStatus.FILE_FORMAT_IS_INVALID);
			}
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new QuestionResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new QuestionResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new QuestionResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="questions/pages")
	public @ResponseBody QuestionResponse fetchQuestions(HttpServletRequest request,
		@RequestParam("pageNumber") int pageNumber,@RequestParam("pageSize") int pageSize, 
		@RequestParam("questionType") int questionTypeId, @RequestParam("topic") int topicId, 
		@RequestParam("difficultyLevel") int difficultyLevel,@RequestParam("status") int status,
		@RequestParam("sortName") String sortName, @RequestParam("sortDir") String sortDirection) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TableCommand tableCommand = new TableCommand();
			tableCommand.setPageNumber(pageNumber);
			tableCommand.setPageSize(pageSize);
			tableCommand.setSortColumnName(sortName);
			tableCommand.setSortDirection(sortDirection);
			tableCommand.setSearchByDifficultyLevel(difficultyLevel);
			tableCommand.setSearchByQuestionType(questionTypeId);
			tableCommand.setSearchByTopic(topicId);
			tableCommand.setSearchByStatus(status);
			List<Question> questions = this.questionManager.fetchQuestions(tableCommand);
			int totalRecords = this.questionManager.countFetchQuestions(tableCommand);
			return new QuestionResponse(LmsStatus.SUCCESS,totalRecords,questions);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new QuestionResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new QuestionResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new QuestionResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="question/{questionId}")
	public @ResponseBody QuestionResponse fetchQuestion(HttpServletRequest request,
		@PathVariable("questionId") int questionId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new QuestionResponse(LmsStatus.SUCCESS, this.questionManager.fetchQuestion(questionId));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new QuestionResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new QuestionResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new QuestionResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="question/{questionId}")
	public @ResponseBody QuestionResponse updateQuestion(HttpServletRequest request,
		@PathVariable("questionId") int questionId,@RequestBody Question question) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			question.setQuestionId(questionId);
			this.questionManager.updateQuestion(question);
			return new QuestionResponse(LmsStatus.SUCCESS,questionId);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new QuestionResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new QuestionResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new QuestionResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="questions/random")
	public @ResponseBody QuestionResponse fetchRandomQuestions(HttpServletRequest request,
		@RequestParam("topicId") int topicId, @RequestParam("topicId") int difficultyId,
		@RequestParam("noOfQuestions") int noOfQuestions,@RequestParam("questionTypeId") int questionTypeId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			Question question = new Question();
			question.setTopicId(topicId);
			question.setDifficultyId(difficultyId);
			question.setNoOfQuestions(noOfQuestions);
			question.setQuestionTypeId(questionTypeId);
			List<Question> questions = this.questionManager.fetchRandomQuestions(question);
			if(this.questionManager.countTotalQuestions(question) < question.getNoOfQuestions()) {
				return new QuestionResponse(LmsStatus.INSUFFICIENT_NO_OF_QUESTIONS);
			} else {
				return new QuestionResponse(LmsStatus.SUCCESS,questions);
			}
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new QuestionResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new QuestionResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new QuestionResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="question/{questionId}/enumerationanswers")
	public @ResponseBody EnumerationAnswerResponse fetchEnumerationAnswers(HttpServletRequest request,
		@PathVariable("questionId") int questionId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new EnumerationAnswerResponse(LmsStatus.SUCCESS,
					this.enumerationManager.fetchEnumerationAnswers(questionId));	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EnumerationAnswerResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EnumerationAnswerResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EnumerationAnswerResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	/***************************************************************************************************************************/
	/* END: Question Restful API																						 **/
	/**************************************************************************************************************************/
	
}