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
import com.pagss.lms.domains.Location;
import com.pagss.lms.exceptions.LmsException;
import com.pagss.lms.exceptions.SessionExpiredException;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.handlers.StackTraceHandler;
import com.pagss.lms.manager.interfaces.LocationManager;
import com.pagss.lms.responses.LocationResponse;
import com.pagss.lms.spring.data.repositories.LocationRepository;

/*
 * @author	Billy
 */
@RestController
public class LocationController {
	
	@Autowired
	public LocationManager locationManager;
	@Autowired
	public LocationRepository locationRepository;
	/***************************************************************************************************************************/
	/* START: NORMAL HTTPREQUEST																							 **/
	/********************************************************************************************************************/
	@GetMapping(value="admin.location")
	public ModelAndView goToLocationPage(HttpServletRequest request) throws LmsException {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-location");
		return mav;
	}
	/***************************************************************************************************************************/
	/* END: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	/***************************************************************************************************************************/
	/* START: RESTFUL API																					 **/
	/**************************************************************************************************************************/
	@GetMapping(value="locations/pages")
	public @ResponseBody LocationResponse fetchLocations(HttpServletRequest request, 
			@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			
			List<Location> locations = this.locationManager.fetchLocationPages(pageSize,pageNo);
			int totalLocations = this.locationManager.countTotalLocations();
			return new LocationResponse(LmsStatus.SUCCESS,locations,totalLocations);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new LocationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new LocationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new LocationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="locations/search")
	public @ResponseBody LocationResponse searchLocations(HttpServletRequest request,@RequestParam("pageSize") int pageSize, 
			@RequestParam("pageNumber") int pageNo, @RequestParam("sortDirection") String sortDirection,
			@RequestParam("sortName") String sortColumnName,@RequestParam("keyword") String keyword) {
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
			List<Location> searchedLocations = this.locationManager.searchLocations(tableCommand);
			int totalRecords = this.locationManager.countTotalSearchedLocations(keyword);
			return new LocationResponse(LmsStatus.SUCCESS,searchedLocations,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new LocationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new LocationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new LocationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="location")
	public @ResponseBody LocationResponse createLocation(HttpServletRequest request, @RequestBody Location location) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			if(this.locationManager.countLocationCode(location.getLocationCode()) != 0) {
				return new LocationResponse(LmsStatus.LOCATION_CODE_EXIST);
			} else {
				this.locationRepository.save(location);
				return new LocationResponse(LmsStatus.SUCCESS);
			}
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new LocationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new LocationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new LocationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="location/{locationId}")
	public @ResponseBody LocationResponse fetchLocation(HttpServletRequest request, @PathVariable("locationId") int locationId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			Location location = this.locationRepository.findById(locationId).get();
			return new LocationResponse(LmsStatus.SUCCESS,location);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new LocationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new LocationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new LocationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="location/{locationId}")
	public @ResponseBody LocationResponse createLocation(HttpServletRequest request, 
			@PathVariable("locationId") int locationId, @RequestBody Location location) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			location.setLocationId(locationId);
			this.locationRepository.save(location);
			return new LocationResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new LocationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new LocationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new LocationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="location/locationcode")
	public @ResponseBody LocationResponse generateLocationCode(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new LocationResponse(LmsStatus.SUCCESS,this.locationManager.fetchGeneratedLocationCode());
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new LocationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new LocationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new LocationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="locations")
	public @ResponseBody LocationResponse fetchLocations(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new LocationResponse(LmsStatus.SUCCESS,this.locationManager.fetchLocations());
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new LocationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new LocationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new LocationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="locations/status/{status}")
	public @ResponseBody LocationResponse fetchActiveLocations(HttpServletRequest request,
			@PathVariable("status") int status) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new LocationResponse(LmsStatus.SUCCESS,this.locationManager.fetchLocationsByStatus(status));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new LocationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new LocationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new LocationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	/***************************************************************************************************************************/
	/* END: RESTFUL API																					 **/
	/**************************************************************************************************************************/
	
}
