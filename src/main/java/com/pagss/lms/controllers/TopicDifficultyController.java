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

import com.pagss.lms.constants.LmsStatus;
import com.pagss.lms.domains.DifficultyLevel;
import com.pagss.lms.domains.TrainingTopic;
import com.pagss.lms.exceptions.SessionExpiredException;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.handlers.StackTraceHandler;
import com.pagss.lms.manager.interfaces.DifficultyLevelManager;
import com.pagss.lms.manager.interfaces.TrainingTopicManager;
import com.pagss.lms.responses.DifficultyLevelResponse;
import com.pagss.lms.responses.TrainingTopicResponse;

@RestController
public class TopicDifficultyController {

	@Autowired 
	private TrainingTopicManager trainingTopicManager;
	@Autowired 
	private DifficultyLevelManager difficultyLevelManager;
	/***************************************************************************************************************************/
	/* START: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	@GetMapping(value="admin.topicdifficulty")
	public ModelAndView gotoTopicDifficultyPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		return new ModelAndView("admin/admin-topicdifficulty");
	}
	/***************************************************************************************************************************/
	/* END: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	/***************************************************************************************************************************/
	/* START: TOPIC RESTFUL API																							 **/
	/**************************************************************************************************************************/
	@GetMapping("trainingtopics")
	public @ResponseBody TrainingTopicResponse fetchTopic(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new TrainingTopicResponse(LmsStatus.SUCCESS,this.trainingTopicManager.fetchTrainingTopics());
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingTopicResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingTopicResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingTopicResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	@GetMapping("trainingtopics/pages")
	public @ResponseBody TrainingTopicResponse fetchTopicTable(HttpServletRequest request,
			@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List <TrainingTopic> trainingtopics = this.trainingTopicManager.fetchTopicsTable(pageSize,pageNo);
			int totalRecords = this.trainingTopicManager.countTotalTrainingTopics();
			return new TrainingTopicResponse(LmsStatus.SUCCESS,trainingtopics,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingTopicResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingTopicResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingTopicResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping("trainingtopics/check/{topicDesc}")
	public @ResponseBody TrainingTopicResponse checkTopicDesc(HttpServletRequest request,
			@PathVariable("topicDesc") String topicDesc) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TrainingTopic trainingtopic = new TrainingTopic();
			trainingtopic.setTopicDesc(topicDesc);
			int totalRecords = this.trainingTopicManager.countTopicDesc(trainingtopic);
			return new TrainingTopicResponse(LmsStatus.SUCCESS,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingTopicResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingTopicResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingTopicResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping("trainingtopics/search")
	public @ResponseBody TrainingTopicResponse fetchSearchTopicTable(HttpServletRequest request,
			@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo,
			@RequestParam("keyword") String keyword) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List <TrainingTopic> trainingtopics = this.trainingTopicManager.fetchSearchedTopicsTable(keyword,pageSize,pageNo);
			int totalRecords = this.trainingTopicManager.countTotalSearchedTrainingTopics(keyword);
			return new TrainingTopicResponse(LmsStatus.SUCCESS,trainingtopics,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingTopicResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingTopicResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingTopicResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping("trainingtopics")
	public @ResponseBody TrainingTopicResponse addSearchTopic(HttpServletRequest request,
			@RequestBody TrainingTopic trainingtopic) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.trainingTopicManager.addTrainingTopic(trainingtopic);
			return new TrainingTopicResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingTopicResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingTopicResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingTopicResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping("trainingtopics/{topicId}")
	public @ResponseBody TrainingTopicResponse updateSearchTopic(HttpServletRequest request,
			@PathVariable("topicId") int topicId, 
			@RequestBody TrainingTopic trainingtopic) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			trainingtopic.setTopicId(topicId);
			this.trainingTopicManager.updateTrainingTopic(trainingtopic);
			return new TrainingTopicResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingTopicResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingTopicResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingTopicResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	/***************************************************************************************************************************/
	/* END: TOPIC RESTFUL API																							 **/
	/**************************************************************************************************************************/
	/***************************************************************************************************************************/
	/* START: Difficulty Level RESTFUL API																							 **/
	/**************************************************************************************************************************/
	@GetMapping(value="difficultylevels")
	public @ResponseBody DifficultyLevelResponse fetchDifficultyLevels(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new DifficultyLevelResponse(LmsStatus.SUCCESS,this.difficultyLevelManager.fetchDifficultyLevels());
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new DifficultyLevelResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new DifficultyLevelResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new DifficultyLevelResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="difficultylevels/pages")
	public @ResponseBody DifficultyLevelResponse fetchDifficultyTable(HttpServletRequest request,
			@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List <DifficultyLevel> difficultyLevels = this.difficultyLevelManager.fetchDifficultyLevelsTable(pageSize,pageNo);
			int totalRecords = this.difficultyLevelManager.countTotalDifficultyLevels();
			return new DifficultyLevelResponse(LmsStatus.SUCCESS,difficultyLevels,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new DifficultyLevelResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new DifficultyLevelResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new DifficultyLevelResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping("difficultylevels/check/{difficultyName}")
	public @ResponseBody DifficultyLevelResponse checkDifficulty(HttpServletRequest request,
			@PathVariable("difficultyName") String difficultyName) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			DifficultyLevel difficultylevel = new DifficultyLevel();
			difficultylevel.setDifficultyName(difficultyName);
			int totalRecords = this.difficultyLevelManager.countDifficultyName(difficultylevel);
			return new DifficultyLevelResponse(LmsStatus.SUCCESS,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new DifficultyLevelResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new DifficultyLevelResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new DifficultyLevelResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="difficultylevels/search")
	public @ResponseBody DifficultyLevelResponse fetchSearchedDifficultyTable(HttpServletRequest request,
			@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo,
			@RequestParam("keyword") String keyword) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List <DifficultyLevel> difficultyLevels = this.difficultyLevelManager.fetchSearchedDifficultyLevelsTable(keyword,pageSize,pageNo);
			int totalRecords = this.difficultyLevelManager.countTotalSearchedDifficultyLevels(keyword);
			return new DifficultyLevelResponse(LmsStatus.SUCCESS,difficultyLevels,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new DifficultyLevelResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new DifficultyLevelResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new DifficultyLevelResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="difficultylevels")
	public @ResponseBody DifficultyLevelResponse addDifficultyLevel(HttpServletRequest request,
			@RequestBody DifficultyLevel difficultylevel) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.difficultyLevelManager.addDifficultyLevel(difficultylevel);
			return new DifficultyLevelResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new DifficultyLevelResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new DifficultyLevelResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new DifficultyLevelResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="difficultylevels/{difficultyId}")
	public @ResponseBody DifficultyLevelResponse updateDifficultyLevel(HttpServletRequest request,
			@PathVariable("difficultyId") int difficultyId, 
			@RequestBody DifficultyLevel difficultylevel) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			difficultylevel.setDifficultyId(difficultyId);
			this.difficultyLevelManager.updateDifficultyLevel(difficultylevel);
			return new DifficultyLevelResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new DifficultyLevelResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new DifficultyLevelResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new DifficultyLevelResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	/***************************************************************************************************************************/
	/* END: Difficulty Level RESTFUL API																							 **/
	/**************************************************************************************************************************/
	
}
