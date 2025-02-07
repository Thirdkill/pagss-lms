package com.pagss.lms.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pagss.lms.constants.LmsStatus;
import com.pagss.lms.domains.EmployeeInfo;
import com.pagss.lms.domains.IpAddress;
import com.pagss.lms.domains.User;
import com.pagss.lms.exceptions.SessionExpiredException;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.handlers.StackTraceHandler;
import com.pagss.lms.manager.interfaces.EmployeeInfoManager;
import com.pagss.lms.manager.interfaces.IPAddressManager;
import com.pagss.lms.responses.IpAddressResponse;
import com.pagss.lms.spring.data.repositories.IpAddressRepository;

/*
 * @author	Billy
 */

@RestController
public class IPAddressController {
	
	@Autowired
	private IpAddressRepository ipAddressRepository;
	@Autowired
	public IPAddressManager ipAddressManager;
	@Autowired
	public EmployeeInfoManager employeeInfoManager;

	/***************************************************************************************************************************/
	/* START: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	@GetMapping(value="admin.ipaddress")
	public ModelAndView goToIPAddressPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-ipaddress");
		
		return mav;
	}
	/***************************************************************************************************************************/
	/* END: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	
	/***************************************************************************************************************************/
	/* START: RESTFUL API																					 **/
	/**************************************************************************************************************************/
	@GetMapping(value="ipaddresses/pages")
	public @ResponseBody IpAddressResponse fetchIpAddress(HttpServletRequest request,
		@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo) {
			try {
				SessionHandler.getSessionUser(request);
				SessionHandler.checkSessionUserAccess(request);
				List<IpAddress> ipAddressService = this.ipAddressManager.fetchIpAddress(pageSize,pageNo);
				int totalRecords = this.ipAddressManager.countTotalIpAddresses();
				return new IpAddressResponse(LmsStatus.SUCCESS,ipAddressService,totalRecords);
			} catch (SessionExpiredException see) {
				StackTraceHandler.printStackTrace(see);
				return new IpAddressResponse(LmsStatus.SESSION_EXPIRED);	
			} catch (DataAccessException dae) {
				StackTraceHandler.printStackTrace(dae);
				return new IpAddressResponse(LmsStatus.QUERY_FAILED);
			} catch (Exception e) {
				StackTraceHandler.printStackTrace(e);
				return new IpAddressResponse(LmsStatus.UNHANDLED_ERROR);
			}
		}
	
	@GetMapping(value="ipaddresses/check/{ipAddress}")
	public @ResponseBody IpAddressResponse checkIpAddress(HttpServletRequest request,
			@PathVariable("ipAddress") String ipAddress) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			IpAddress ipaddress = new IpAddress();
			ipaddress.setIpAddress(ipAddress);
			int totalRecords = this.ipAddressManager.countIpAddress(ipaddress);
			return new IpAddressResponse(LmsStatus.SUCCESS,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new IpAddressResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new IpAddressResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new IpAddressResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="ipaddresses/search")
	public @ResponseBody IpAddressResponse searchIpAddress(HttpServletRequest request,
		@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo,
		@RequestParam("keyword") String keyword) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<IpAddress> ipAddressService = this.ipAddressManager.searchIpAddress(keyword,pageSize,pageNo);
			int totalRecords = this.ipAddressManager.countSearchedIpAddresses(keyword);
			return new IpAddressResponse(LmsStatus.SUCCESS,ipAddressService,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new IpAddressResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new IpAddressResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new IpAddressResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="ipaddresses")
	public @ResponseBody IpAddressResponse addIpAddress(HttpServletRequest request,
			@RequestBody IpAddress ipaddress) {
		try {
			User user = SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			EmployeeInfo empinfo = new EmployeeInfo();
			empinfo.setUserId(user.getUserId());
			EmployeeInfo empinfos = this.employeeInfoManager.fetchEmployeeInfo(empinfo);
			ipaddress.setModifiedBy(empinfos.getFullName().toString());
			this.ipAddressRepository.save(ipaddress);
			return new IpAddressResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new IpAddressResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new IpAddressResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new IpAddressResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@DeleteMapping(value="ipaddresses/{ipAddressId}")
	public @ResponseBody IpAddressResponse deleteIpAddress(HttpServletRequest request,
			@PathVariable("ipAddressId") int ipAddressId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.ipAddressRepository.deleteById(ipAddressId);
			return new IpAddressResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new IpAddressResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new IpAddressResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new IpAddressResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="ipaddresses/pages/class/{classId}")
	public @ResponseBody IpAddressResponse fetchIpAddressOnClass(HttpServletRequest request,
	@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo,
	@PathVariable("classId") int classId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<IpAddress> ipAddressService = this.ipAddressManager.fetchIpAddressOnClass(classId,pageSize,pageNo);
			int totalRecords = this.ipAddressManager.countTotalIpAddressOnClass(classId);
			return new IpAddressResponse(LmsStatus.SUCCESS,ipAddressService,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new IpAddressResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new IpAddressResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new IpAddressResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	/***************************************************************************************************************************/
	/* END: RESTFUL API																					 **/
	/**************************************************************************************************************************/
}
