package com.pagss.lms.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pagss.lms.constants.LmsFileType;
import com.pagss.lms.constants.LmsStatus;
import com.pagss.lms.domains.EmployeeInfo;
import com.pagss.lms.domains.User;
import com.pagss.lms.exceptions.SessionExpiredException;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.handlers.StackTraceHandler;
import com.pagss.lms.manager.interfaces.EmployeeInfoManager;
import com.pagss.lms.manager.interfaces.UserManager;
import com.pagss.lms.responses.MassUserUploadResponse;
import com.pagss.lms.utilities.ExcelGenerator;
import com.pagss.lms.utilities.InputValidators;

@RestController
public class UserMassUploadController {
	@Autowired 
	private UserManager userManager;
	@Autowired
	private EmployeeInfoManager employeeInfoManager;
	@Autowired
	private ExcelGenerator excelGenerator;
	@Autowired
	private InputValidators inputValidator;
	
	@GetMapping(value="usermassupload/generateexcel")
	public ResponseEntity<Object> fetchGeneratedExcel(HttpServletRequest request, 
		@RequestParam("jobRoleId") Integer jobRoleId,
		@RequestParam("userTypeId") Integer userTypeId,
		@RequestParam("userGroupId") Integer userGroupId) {
		File file = null;
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			file = excelGenerator.generateMassUserTemplate(userTypeId, jobRoleId, userGroupId);
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			
			return ResponseEntity
				.status(HttpStatus.OK)
				.contentType(MediaType.parseMediaType(
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.contentLength(file.length())
				.header("Content-Disposition", "attachment; filename=\"massuseruploadtemplate.xlsx\"")
				.body(resource);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} finally {
			if (file != null) {
				file.delete();
			}
		}
	}

	@PostMapping(value="usermassupload/upload-excel")
	public @ResponseBody MassUserUploadResponse fetchUploadedExcel(MultipartHttpServletRequest request) {
		try {
			List<User> uploadedSavedListUser = new ArrayList<>();
			List<User> uploadedErrorListUser = new ArrayList<>();
			List<User> uploadedUser = new ArrayList<>();
			User admin = SessionHandler.getSessionUser(request);
			EmployeeInfo adminInfo = new EmployeeInfo();
			adminInfo = this.employeeInfoManager.fetchEmployeeInfoByUserId(admin.getUserId());
			String modifiedBy = adminInfo.getFullName();
			SessionHandler.checkSessionUserAccess(request);
			MultipartFile file = request.getFile("fileUpload");
			String contentType = file.getContentType();
			if (LmsFileType.documentTypes.contains(contentType)) {
				File tempFile = this.inputValidator.multipartToFile(file, file.getName());
				uploadedUser = excelGenerator.uploadUserTemplate(tempFile);
				this.userManager.createUploadedUsers(uploadedErrorListUser, uploadedSavedListUser, uploadedUser, modifiedBy);
				tempFile.delete();
				
			} else {
				return new MassUserUploadResponse(LmsStatus.FILE_FORMAT_IS_INVALID);
			}
			
			return new MassUserUploadResponse(LmsStatus.SUCCESS
					, uploadedErrorListUser, uploadedSavedListUser);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new MassUserUploadResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new MassUserUploadResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new MassUserUploadResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="usermassupload/upload-user")
	public @ResponseBody MassUserUploadResponse addUploadedUser(HttpServletRequest request
			,@RequestBody User user) {
		try {
			User uploadedUser = new User();
			User admin = SessionHandler.getSessionUser(request);
			EmployeeInfo adminInfo = new EmployeeInfo();
			adminInfo = this.employeeInfoManager.fetchEmployeeInfoByUserId(admin.getUserId());
			String modifiedBy = adminInfo.getFullName();
			user.setModifiedBy(modifiedBy);
			SessionHandler.checkSessionUserAccess(request);
			uploadedUser = this.userManager.createUploadedUser(user);
			
			return new MassUserUploadResponse(LmsStatus.SUCCESS, uploadedUser);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new MassUserUploadResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new MassUserUploadResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new MassUserUploadResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="usermassupload/upload-users")
	public @ResponseBody MassUserUploadResponse addUploadedUsers(HttpServletRequest request
			,@RequestBody List<User> uploadedUsers) {
		try {
			List<User> uploadedSavedListUser = new ArrayList<>();
			List<User> uploadedErrorListUser = new ArrayList<>();
			User admin = SessionHandler.getSessionUser(request);
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			EmployeeInfo adminInfo = new EmployeeInfo();
			adminInfo = this.employeeInfoManager.fetchEmployeeInfoByUserId(admin.getUserId());
			String modifiedBy = adminInfo.getFullName();
			this.userManager.createUploadedUsers(uploadedErrorListUser, uploadedSavedListUser, uploadedUsers, modifiedBy);
			
			return new MassUserUploadResponse(LmsStatus.SUCCESS, uploadedErrorListUser, uploadedSavedListUser);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new MassUserUploadResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new MassUserUploadResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new MassUserUploadResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
}
