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

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.constants.LmsStatus;
import com.pagss.lms.domains.TrainingCategory;
import com.pagss.lms.exceptions.SessionExpiredException;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.handlers.StackTraceHandler;
import com.pagss.lms.manager.interfaces.TrainingCategoryManager;
import com.pagss.lms.responses.TrainingCategoryResponse;
import com.pagss.lms.spring.data.repositories.TrainingCategoryRepository;

/*
 * @author Billy
 */

@RestController
public class TrainingCategoryController {
	
	@Autowired
	private TrainingCategoryRepository trainingCategoryRepository;
	@Autowired
	private TrainingCategoryManager trainingCategoryManager;
	/***************************************************************************************************************************/
	/* START: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	@GetMapping(value="admin.trainingcategory")
	public ModelAndView goToTrainingCategoryPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-trainingcategory");
		
		return mav;
	}
	/***************************************************************************************************************************/
	/* END: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	/***************************************************************************************************************************/
	/* START: RESTFUL API																						 **/
	/**************************************************************************************************************************/
	@GetMapping(value="trainingcategories/pages")
	public @ResponseBody TrainingCategoryResponse fetchTrainingCategories(HttpServletRequest request, 
			@RequestParam("pageNumber") int pageNo, @RequestParam("pageSize") int pageSize) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<TrainingCategory> trainingCategories=this.trainingCategoryManager.fetchTrainingCategoriesPages(pageSize, pageNo);
			int totalRecords = this.trainingCategoryManager.countTotalCategories();
			return new TrainingCategoryResponse(LmsStatus.SUCCESS,totalRecords,trainingCategories);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingCategoryResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingCategoryResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingCategoryResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="trainingcategories/search")
	public @ResponseBody TrainingCategoryResponse searchTrainingCategories(HttpServletRequest request, 
			@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo, 
			@RequestParam("sortDirection") String sortDirection,@RequestParam("sortName") String sortColumnName,
			@RequestParam("keyword") String keyword) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TableCommand tableCommand = new TableCommand();
			tableCommand.setKeyword(keyword);
			tableCommand.setSortDirection(sortDirection);
			tableCommand.setSortColumnName(sortColumnName);
			tableCommand.setPageSize(pageSize);
			tableCommand.setPageNumber(pageNo);
			tableCommand.setKeyword(keyword);
			List<TrainingCategory> trainingCategories = this.trainingCategoryManager.searchTrainingCategories(tableCommand);
			int totalCategories = this.trainingCategoryManager.countSearchedTrainingCategories(keyword);
			return new TrainingCategoryResponse(LmsStatus.SUCCESS,totalCategories,trainingCategories);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingCategoryResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingCategoryResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingCategoryResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="trainingcategory")
	public @ResponseBody TrainingCategoryResponse createTrainingCategory(HttpServletRequest request, 
			@RequestBody TrainingCategory trainingCategory) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			if(this.trainingCategoryManager.countTrainingCategoryCode(trainingCategory.getCategoryCode()) > 0) {
				return new TrainingCategoryResponse(LmsStatus.CATEGORY_CODE_EXIST);	
			} else {
				this.trainingCategoryRepository.save(trainingCategory);
				return new TrainingCategoryResponse(LmsStatus.SUCCESS);	
			}
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingCategoryResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingCategoryResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingCategoryResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="trainingcategory/{trainingCategoryId}")
	public @ResponseBody TrainingCategoryResponse fetchTrainingCategory(HttpServletRequest request, 
			@PathVariable("trainingCategoryId") int trainingCategoryId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TrainingCategory trainingCategory = this.trainingCategoryRepository.findById(trainingCategoryId).get();
			return new TrainingCategoryResponse(LmsStatus.SUCCESS,trainingCategory);	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingCategoryResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingCategoryResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingCategoryResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="trainingcategory/{trainingCategoryId}")
	public @ResponseBody TrainingCategoryResponse updateTrainingCategory(HttpServletRequest request, 
			@PathVariable("trainingCategoryId") int categoryId,@RequestBody TrainingCategory trainingCategory) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			trainingCategory.setCategoryId(categoryId);
			this.trainingCategoryRepository.save(trainingCategory);
			return new TrainingCategoryResponse(LmsStatus.SUCCESS);	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingCategoryResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingCategoryResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingCategoryResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="trainingcategory/generate-trainingcategoryCode")
	public @ResponseBody TrainingCategoryResponse generateTrainingCategoryCode(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new TrainingCategoryResponse(LmsStatus.SUCCESS,this.trainingCategoryManager.generateCategoryCode());	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingCategoryResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingCategoryResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingCategoryResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="trainingcategories")
	public @ResponseBody TrainingCategoryResponse fetchTrainingCategories(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new TrainingCategoryResponse(LmsStatus.SUCCESS,this.trainingCategoryManager.fetchTrainingCategories());	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingCategoryResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingCategoryResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingCategoryResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="trainingcategories/status/{status}")
	public @ResponseBody TrainingCategoryResponse fetchTrainingCategories(HttpServletRequest request,
			@PathVariable("status") int status) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<TrainingCategory> categories = this.trainingCategoryManager.fetchTrainingCategoriesByStatus(status);
			return new TrainingCategoryResponse(LmsStatus.SUCCESS,categories);	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new TrainingCategoryResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new TrainingCategoryResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new TrainingCategoryResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	/***************************************************************************************************************************/
	/* END: RESTFUL API																						 **/
	/**************************************************************************************************************************/
	
}
