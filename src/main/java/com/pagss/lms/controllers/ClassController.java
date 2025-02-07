package com.pagss.lms.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.pagss.lms.constants.LmsClassEmployeeData;
import com.pagss.lms.constants.LmsClassInfoData;
import com.pagss.lms.constants.LmsClassSessionTimerData;
import com.pagss.lms.constants.LmsCourseInfoData;
import com.pagss.lms.constants.LmsFileServer;
import com.pagss.lms.constants.LmsLearningPathSessionTimerData;
import com.pagss.lms.constants.LmsStatus;
import com.pagss.lms.domains.ClassEmployee;
import com.pagss.lms.domains.ClassEmployeeAssessment;
import com.pagss.lms.domains.ClassEvaluation;
import com.pagss.lms.domains.ClassExam;
import com.pagss.lms.domains.ClassInfo;
import com.pagss.lms.domains.ClassIpAddress;
import com.pagss.lms.domains.ClassMaterial;
import com.pagss.lms.domains.ClassSeriesSchedule;
import com.pagss.lms.domains.ClassSessionTimer;
import com.pagss.lms.domains.ClassBlockSchedule;
import com.pagss.lms.domains.ClassSetSchedule;
import com.pagss.lms.domains.EmployeeInfo;
import com.pagss.lms.domains.LearningPath;
import com.pagss.lms.domains.LearningPathSection;
import com.pagss.lms.domains.LearningPathSessionTimer;
import com.pagss.lms.domains.MaterialFinance;
import com.pagss.lms.domains.OtherFinance;
import com.pagss.lms.domains.TrainerFinance;
import com.pagss.lms.domains.User;
import com.pagss.lms.exceptions.SessionExpiredException;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.handlers.StackTraceHandler;
import com.pagss.lms.manager.interfaces.ClassDefaultManager;
import com.pagss.lms.manager.interfaces.ClassEmployeeAssessmentManager;
import com.pagss.lms.manager.interfaces.ClassEmployeeManager;
import com.pagss.lms.manager.interfaces.ClassEvaluationManager;
import com.pagss.lms.manager.interfaces.ClassExamManager;
import com.pagss.lms.manager.interfaces.ClassInfoManager;
import com.pagss.lms.manager.interfaces.ClassIpAddressManager;
import com.pagss.lms.manager.interfaces.ClassMaterialFinancialManager;
import com.pagss.lms.manager.interfaces.ClassMaterialManager;
import com.pagss.lms.manager.interfaces.ClassOtherFinancialManager;
import com.pagss.lms.manager.interfaces.ClassSessionTimerManager;
import com.pagss.lms.manager.interfaces.ClassTrainerFinancialManager;
import com.pagss.lms.manager.interfaces.EmployeeInfoManager;
import com.pagss.lms.manager.interfaces.LearningPathManager;
import com.pagss.lms.manager.interfaces.LearningPathSectionManager;
import com.pagss.lms.manager.interfaces.LearningPathSessionTimerManager;
import com.pagss.lms.responses.ClassDefaultResponse;
import com.pagss.lms.responses.ClassEmployeeAssessmentResponse;
import com.pagss.lms.responses.ClassEmployeeResponse;
import com.pagss.lms.responses.ClassEvaluationResponse;
import com.pagss.lms.responses.ClassExamResponse;
import com.pagss.lms.responses.ClassInfoResponse;
import com.pagss.lms.responses.ClassIpAddressResponse;
import com.pagss.lms.responses.ClassMaterialFinancialResponse;
import com.pagss.lms.responses.ClassMaterialResponse;
import com.pagss.lms.responses.ClassOtherFinancialResponse;
import com.pagss.lms.responses.ClassScheduleResponse;
import com.pagss.lms.responses.ClassSessionTimerResponse;
import com.pagss.lms.responses.ClassTrainerFinancialResponse;
import com.pagss.lms.responses.LearningPathResponse;
import com.pagss.lms.responses.LearningPathSessionTimerResponse;
import com.pagss.lms.spring.data.repositories.ClassEmployeeRepository;
import com.pagss.lms.utilities.FileServerOperator;

@RestController
public class ClassController {
	
	@Autowired
	private ClassDefaultManager classDefaultManager;
	@Autowired
	private ClassInfoManager classInfoManager;
	@Autowired
	private ClassEmployeeRepository classEmployeeRepository;
	@Autowired
	private ClassEmployeeManager classEmployeeManager;
	@Autowired
	private ClassTrainerFinancialManager classTrainerFinancialManager;
	@Autowired
	private ClassMaterialFinancialManager classMaterialFinancialManager;
	@Autowired
	private ClassOtherFinancialManager classOtherFinancialManager;
	@Autowired
	private ClassExamManager classExamManager;
	@Autowired
	private FileServerOperator fileServerOperator;
	@Autowired
	private ClassEmployeeAssessmentManager classEmployeeAssessmentManager;
	@Autowired
	private EmployeeInfoManager employeeInfoManager;
	@Autowired
	private ClassMaterialManager classMaterialManager;
	@Autowired
	private ClassSessionTimerManager classSessionTimerManager;
	@Autowired
	private ClassEvaluationManager classEvaluationManager;
	@Autowired
	private ClassIpAddressManager classIpAddressManager;
	@Autowired
	private LearningPathManager learningPathManager;
	@Autowired
	private LearningPathSectionManager learningPathSectionManager;
	@Autowired
	private LearningPathSessionTimerManager learningPathSessionTimerManager;

	/***************************************************************************************************************************/
	/* START: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	@GetMapping(value="admin.class")
	public ModelAndView goToClassInfoPage(HttpServletRequest request) throws Exception {
		User user = SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-class");
		mav.addObject("user",user);
		mav.addObject("totalUserClasses",this.classInfoManager.countClassInfoByUserId(user.getUserId()));
		mav.addObject("totalInProgressClasses",this.classInfoManager.countInProgressClassInfos(user.getUserId()));
		mav.addObject("totalUpcomingClasses",this.classInfoManager.countUpcomingClassInfos(user.getUserId()));
		mav.addObject("totalCompletedClasses",this.classInfoManager.countCompletedClassInfos(user.getUserId()));
		mav.addObject("totalCancelledClasses",this.classInfoManager.countCancelledClassInfos(user.getUserId()));
		return mav;
	}
	
	@GetMapping(value="admin.createclass")
	public ModelAndView goToCreateClassInfoPage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		return new ModelAndView("admin/admin-createclass");
	}
	
	@GetMapping(value="admin.classdashboard")
	public ModelAndView goToClassDashboardPage(HttpServletRequest request,
		@RequestParam("classId") int classId) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-classdashboard");
		mav.addObject("classId",classId);
		return mav;
	}
	
	@GetMapping(value="admin.classdetails")
	public ModelAndView goToClassTrainingDetailsPage(HttpServletRequest request,
		@RequestParam("classId") int classId) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ClassInfo classInfo = this.classInfoManager.fetchClassInfo(classId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-classdetails");
		mav.addObject("classInfo",classInfo);
		mav.addObject("classId",classId);
		return mav;
	}
	
	@GetMapping(value="admin.classtrainee")
	public ModelAndView goToClassTraineePage(HttpServletRequest request,
		@RequestParam("classId") int classId) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ClassInfo classInfo = this.classInfoManager.fetchClassInfo(classId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-classtrainee");
		mav.addObject("classId",classId);
		mav.addObject("classInfo",classInfo);
		mav.addObject("photoUrl",fileServerOperator.generatePresignedUrl(classInfo.getClassPhotoUrl()));
		return mav;
	}
	
	@GetMapping(value="admin.classattendance")
	public ModelAndView goToClassAttendancePage(HttpServletRequest request,
		@RequestParam("classId") int classId) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ClassInfo classInfo = this.classInfoManager.fetchClassInfo(classId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-classattendance");
		mav.addObject("classId",classId);
		mav.addObject("classInfo",classInfo);
		mav.addObject("photoUrl",fileServerOperator.generatePresignedUrl(classInfo.getClassPhotoUrl()));
		return mav;
	}
	
	@GetMapping(value="admin.classsettings")
	public ModelAndView goToClassSettingsPage(HttpServletRequest request,
		@RequestParam("classId") int classId) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ClassInfo classInfo = this.classInfoManager.fetchClassInfo(classId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-classsettings");
		mav.addObject("classId",classId);
		mav.addObject("classInfo",classInfo);
		mav.addObject("photoUrl",fileServerOperator.generatePresignedUrl(classInfo.getClassPhotoUrl()));
		return mav;
	}
	
	@GetMapping(value="admin.classmaterials")
	public ModelAndView goToClassMaterialsPage(HttpServletRequest request,
		@RequestParam("classId") int classId) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ClassInfo classInfo = this.classInfoManager.fetchClassInfo(classId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-classmaterials");
		mav.addObject("classId",classId);
		mav.addObject("classInfo",classInfo);
		mav.addObject("photoUrl",fileServerOperator.generatePresignedUrl(classInfo.getClassPhotoUrl()));
		return mav;
	}
	
	@GetMapping(value="admin.classexam")
	public ModelAndView goToClassExamPage(HttpServletRequest request,
		@RequestParam("classId") int classId) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ClassInfo classInfo = this.classInfoManager.fetchClassInfo(classId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-classexam");
		mav.addObject("classId",classId);
		mav.addObject("classInfo",classInfo);
		mav.addObject("photoUrl",fileServerOperator.generatePresignedUrl(classInfo.getClassPhotoUrl()));
		return mav;
	}
	
	@GetMapping(value="admin.classfinancial")
	public ModelAndView goToClassFinancialPage(HttpServletRequest request,
		@RequestParam("classId") int classId) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ClassInfo classInfo = this.classInfoManager.fetchClassInfo(classId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-classfinancial");
		mav.addObject("classId",classId);
		mav.addObject("classInfo",classInfo);
		mav.addObject("photoUrl",fileServerOperator.generatePresignedUrl(classInfo.getClassPhotoUrl()));
		return mav;
	}
	
	@GetMapping(value="admin.classevaluation")
	public ModelAndView goToClassEvaluationPage(HttpServletRequest request,
		@RequestParam("classId") int classId) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ClassInfo classInfo = this.classInfoManager.fetchClassInfo(classId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-classevaluation");
		mav.addObject("classId",classId);
		mav.addObject("classInfo",classInfo);
		mav.addObject("photoUrl",fileServerOperator.generatePresignedUrl(classInfo.getClassPhotoUrl()));
		return mav;
	}
	
	@GetMapping(value="mytraining-inprogress")
	public ModelAndView gotoInProgressTrainingPage(HttpServletRequest request) throws Exception {
		User user = SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("portal/portal-training");
		mav.addObject("user",user);
		mav.addObject("status", LmsClassInfoData.ONGOING);
		return mav;
	}
	
	@GetMapping(value="mytraining-completed")
	public ModelAndView gotoCompletedTrainingPage(HttpServletRequest request) throws Exception {
		User user = SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("portal/portal-training");
		mav.addObject("user",user);
		mav.addObject("status", LmsClassInfoData.COMPLETE);
		return mav;
	}
	
	@GetMapping(value="mytraining-catalog")
	public ModelAndView gotoTrainingCatalogPage(HttpServletRequest request) throws Exception {
		User user = SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("portal/portal-trainingcatalog");
		mav.addObject("user",user);
		mav.addObject("user",user);
		return mav;
	}
	
	@GetMapping(value="mytraining-calendar")
	public ModelAndView gotoTrainingCalendarPage(HttpServletRequest request) throws Exception {
		User user = SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("portal/portal-trainingcalendar");
		mav.addObject("user",user);
		return mav;
	}
	
	@GetMapping(value="mytraining-classinfo")
	public ModelAndView gotoClassInfoRegistrationPage(HttpServletRequest request,
		@RequestParam("classId") int classId) throws Exception {
		User user = SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ClassInfo classInfo = this.classInfoManager.fetchClassInfo(classId);
		EmployeeInfo employeeInfo = this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("portal/portal-selfregistration");
		mav.addObject("classInfo",classInfo);
		mav.addObject("classPhotoUrl",fileServerOperator.generatePresignedUrl(classInfo.getClassPhotoUrl()));
		mav.addObject("employeeId",employeeInfo.getEmployeeId());
		return mav;
	}
	
	@GetMapping(value="preview-class")
	public ModelAndView gotoViewClass(HttpServletRequest request, @RequestParam("classId") int classId)
		throws Exception {
		User user = SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		EmployeeInfo employeeInfo = this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
		if(this.classEmployeeManager.countClassEmployeeWithOngoingClasses(classId, employeeInfo.getEmployeeId()) > 0) {
			ClassInfo classInfo = this.classInfoManager.fetchClassInfo(classId);
			mav.addObject("classInfo",classInfo);
			mav.addObject("classPhotoUrl",fileServerOperator.generatePresignedUrl(classInfo.getClassPhotoUrl()));
			this.classEmployeeManager.updateTrainingStatus(classId,employeeInfo.getEmployeeId(),
				LmsClassEmployeeData.TRAININGSTATUS_INCOMPLETE);
			if(classInfo.getDeliveryMethod()==LmsCourseInfoData.INDIVIDUAL) {
				this.classSessionTimerManager.createClassSessionTimer(classId, employeeInfo.getEmployeeId());
				mav.setViewName("portal/portal-individualtraining");
			} else if(classInfo.getDeliveryMethod()==LmsCourseInfoData.MODULAR) {
				mav.setViewName("portal/portal-modulartraining");
			} else {
				mav.setViewName("portal/portal-classroomtraining");
			}
		} else {
			mav.setViewName("portal/errorpage");
			mav.addObject("message","This page is forbidden.");
		}
		return mav;
	}
	
	@GetMapping(value="preview-material")
	public ModelAndView gotoPreviewClassMaterialPage(HttpServletRequest request,
		@RequestParam(value="classId",required=true) Integer classId,
		@RequestParam(value="learningPathId",required=false) Integer learningPathId,
		@RequestParam(value="classMaterialId",required=false) Integer classMaterialId) throws Exception {
		User user = SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ClassInfo classInfo = this.classInfoManager.fetchClassInfo(classId);
		EmployeeInfo employeeInfo = this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
		ModelAndView mav = new ModelAndView();
		if(learningPathId!=null) {
			LearningPath learningPath=this.learningPathManager.fetchLearningPath(learningPathId);
			this.learningPathSessionTimerManager.createLearningPathSessionTimerWithTimeSpent(
					classId,employeeInfo.getEmployeeId(),learningPathId);
			mav.addObject("contentUrl",learningPath.getContentUrl());
			mav.addObject("learningPathId",learningPathId);
		} else {
			ClassMaterial classMaterial=this.classMaterialManager.fetchClassMaterial(classMaterialId);
			System.out.print(classMaterial.getContentUrl());
			mav.addObject("contentUrl",fileServerOperator.generatePresignedUrl(classMaterial.getContentUrl()));
		}
		mav.setViewName("portal/portal-previewclassmaterial");
		mav.addObject("classInfo",classInfo);
		mav.addObject("classPhotoUrl",fileServerOperator.generatePresignedUrl(classInfo.getClassPhotoUrl()));
		return mav;
	}
	/***************************************************************************************************************************/
	/* END: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	@GetMapping(value="classinfo/generate-classcode/{courseName}")
	public @ResponseBody ClassInfoResponse generateClassCode(HttpServletRequest request,
			@PathVariable("courseName") String courseName) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new ClassInfoResponse(LmsStatus.SUCCESS,this.classInfoManager.generateClassCode(courseName));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/check-classcode/{classCode}")
	public @ResponseBody ClassInfoResponse checkClassCode(HttpServletRequest request,
			@PathVariable("classCode") String classCode) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new ClassInfoResponse(LmsStatus.SUCCESS,this.classInfoManager.checkClassCode(classCode));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/classdefault/{courseId}")
	public @ResponseBody ClassDefaultResponse fetchClassDefault(HttpServletRequest request,
			@PathVariable("courseId") int courseId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new ClassDefaultResponse(LmsStatus.SUCCESS,this.classDefaultManager.fetchClassDefault(courseId));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassDefaultResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassDefaultResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassDefaultResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="classinfo")
	public @ResponseBody ClassInfoResponse addClassInfo(HttpServletRequest request, @RequestBody ClassInfo classinfo) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classInfoManager.addClassInfo(classinfo);
			return new ClassInfoResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="classemployee")
	public @ResponseBody ClassInfoResponse addClassEmployee(HttpServletRequest request,
			@RequestBody ClassEmployee classEmployee) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classEmployeeRepository.save(classEmployee);
			return new ClassInfoResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/pages")
	public @ResponseBody ClassInfoResponse fetchClassInfoPages(HttpServletRequest request,
		@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<ClassInfo> classInfos = this.classInfoManager.fetchClassInfoPages(pageNumber, pageSize);
			int totalRecords = this.classInfoManager.countClassInfos();
			return new ClassInfoResponse(LmsStatus.SUCCESS,totalRecords,classInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfos")
	public @ResponseBody ClassInfoResponse fetchClassInfos(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<ClassInfo> classInfos = this.classInfoManager.fetchClassInfos();
			return new ClassInfoResponse(LmsStatus.SUCCESS,classInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/classmaterial/pages")
	public @ResponseBody ClassMaterialResponse fetchClassMaterials(HttpServletRequest request,
	@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize,
	@RequestParam("classId") int classId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<ClassMaterial> classMaterials = this.classMaterialManager.fetchClassMaterialPages(classId, pageNumber, pageSize);
			int totalRecords = this.classMaterialManager.countClassMaterials(classId);
			return new ClassMaterialResponse(LmsStatus.SUCCESS,totalRecords,classMaterials);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassMaterialResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassMaterialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassMaterialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="classinfo/search")
	public @ResponseBody ClassInfoResponse searchClassInfoPages(HttpServletRequest request,
		@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize,
		@RequestParam("deliveryMethod") int deliveryMethod,@RequestParam("employeeId") int employeeId,
		@RequestParam("courseId") int courseId,@RequestParam("startDate") String startDate,
		@RequestParam("endDate") String endDate,@RequestParam("keyword") String keyword,
		@RequestParam("searchInProgressClasses") int inProgress, @RequestParam("searchUpcomingClasses") int upcoming,
		@RequestParam("searchCompletedClasses") int completed,@RequestParam("searchCancelledClasses") int cancelled) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TableCommand tableCommand = new TableCommand();
			tableCommand.setDeliveryMethod(deliveryMethod);
			tableCommand.setSearchByEmployeeId(employeeId);
			tableCommand.setCourseId(courseId);
			tableCommand.setStartDate(startDate);
			tableCommand.setEndDate(endDate);
			tableCommand.setKeyword(keyword);
			tableCommand.setPageNumber(pageNumber);
			tableCommand.setPageSize(pageSize);
			tableCommand.setSearchInProgressClasses(inProgress);
			tableCommand.setSearchUpcomingClasses(upcoming);
			tableCommand.setSearchCompletedClasses(completed);
			tableCommand.setSearchCancelledClasses(cancelled);
			List<ClassInfo> classInfos = this.classInfoManager.searchClassInfo(tableCommand);
			int totalRecords = this.classInfoManager.countSearchClassInfo(tableCommand);
			return new ClassInfoResponse(LmsStatus.SUCCESS,totalRecords,classInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@DeleteMapping(value="classinfo/{classId}")
	public @ResponseBody ClassInfoResponse deleteClassInfo(HttpServletRequest request,
		@PathVariable("classId") int classId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classInfoManager.deleteClassInfo(classId);
			return new ClassInfoResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/{classId}/classschedules/block")
	public @ResponseBody ClassScheduleResponse updateBlockClassSchedules(HttpServletRequest request,
			@PathVariable("classId") int classId,
			@RequestBody List<ClassBlockSchedule> classSchedules) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classInfoManager.updateBlockClassSchedules(classSchedules, classId);
			return new ClassScheduleResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassScheduleResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassScheduleResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassScheduleResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/{classId}/classschedules/set")
	public @ResponseBody ClassScheduleResponse updateSetClassSchedules(HttpServletRequest request,
			@PathVariable("classId") int classId,
			@RequestBody List<ClassSetSchedule> classSchedules) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classInfoManager.updateSetClassSchedules(classSchedules, classId);
			return new ClassScheduleResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassScheduleResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassScheduleResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassScheduleResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/{classId}/classschedules/series")
	public @ResponseBody ClassScheduleResponse updateSeriesClassSchedules(HttpServletRequest request,
			@PathVariable("classId") int classId,
			@RequestBody List<ClassSeriesSchedule> classSchedules) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classInfoManager.updateSeriesClassSchedules(classSchedules, classId);
			return new ClassScheduleResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassScheduleResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassScheduleResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassScheduleResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
			
	@PostMapping(value="classinfo/{classId}/classemployee/trainee")
	public @ResponseBody ClassEmployeeResponse createClassTrainees(HttpServletRequest request,
		@PathVariable("classId") int classId,@RequestBody List<ClassEmployee> classEmployees) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classEmployeeManager.createClassEmployees(classEmployees);
			return new ClassEmployeeResponse(LmsStatus.SUCCESS);	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassEmployeeResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassEmployeeResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassEmployeeResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/{classId}/classemployee/trainee/approvalstatus/{approvalStatus}")
	public @ResponseBody ClassEmployeeResponse createClassTrainees(HttpServletRequest request,
		@PathVariable("classId") int classId,@PathVariable("approvalStatus") int approvalStatus,
		@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TableCommand tblCommand = new TableCommand();
			tblCommand.setClassId(classId);
			tblCommand.setSearchByApprovalStatus(approvalStatus);
			tblCommand.setPageNumber(pageNumber);
			tblCommand.setPageSize(pageSize);
			List<ClassEmployee> classEmployees = this.classEmployeeManager.fetchClassEmployeesByApprovalStatus(tblCommand);
			int totalRecords = this.classEmployeeManager.countClassEmployeesByApprovalStatus(tblCommand);
			return new ClassEmployeeResponse(LmsStatus.SUCCESS,totalRecords,classEmployees);	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassEmployeeResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassEmployeeResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassEmployeeResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@DeleteMapping(value="classinfo/{classId}/classemployee/{classEmployeeId}")
	public @ResponseBody ClassEmployeeResponse deleteClassEmployee(HttpServletRequest request,
			@PathVariable("classId") int classId,@PathVariable("classEmployeeId") int classEmployeeId) {
			try {
				SessionHandler.getSessionUser(request);
				SessionHandler.checkSessionUserAccess(request);
				ClassEmployee classEmployee = new ClassEmployee();
				classEmployee.setClassEmployeeId(classEmployeeId);
				this.classEmployeeRepository.delete(classEmployee);
				return new ClassEmployeeResponse(LmsStatus.SUCCESS);	
			} catch (SessionExpiredException see) {
				StackTraceHandler.printStackTrace(see);
				return new ClassEmployeeResponse(LmsStatus.SESSION_EXPIRED);	
			} catch (DataAccessException dae) {
				StackTraceHandler.printStackTrace(dae);
				return new ClassEmployeeResponse(LmsStatus.QUERY_FAILED);
			} catch (Exception e) {
				StackTraceHandler.printStackTrace(e);
				return new ClassEmployeeResponse(LmsStatus.UNHANDLED_ERROR);
			}
	}
	
	@PutMapping(value="classinfo/{classId}/classemployee/{classEmployeeId}")
	public @ResponseBody ClassEmployeeResponse updateClassEmployee(HttpServletRequest request,
		@PathVariable("classId") int classId,@PathVariable("classEmployeeId") int classEmployeeId,
		@RequestBody ClassEmployee classEmployee) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classEmployeeManager.updateClassEmployee(classEmployee);
			return new ClassEmployeeResponse(LmsStatus.SUCCESS);	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassEmployeeResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassEmployeeResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassEmployeeResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/{classId}/trainingstatus")
	public @ResponseBody ClassEmployeeResponse updateClassTrainingStatus(HttpServletRequest request,
		@PathVariable("classId") int classId,@RequestBody List<ClassEmployee> classEmployees) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classEmployeeManager.updateClassInfoTrainingStatus(classEmployees);
			return new ClassEmployeeResponse(LmsStatus.SUCCESS);	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassEmployeeResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassEmployeeResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassEmployeeResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="classinfo/{classId}/classPhotoUrl")
	public @ResponseBody ClassInfoResponse updatePhotoUrl(MultipartHttpServletRequest request,
		@PathVariable("classId") int classId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			MultipartFile file = request.getFile("fileUpload");
			FileUploadCommand uploadCmd = new FileUploadCommand();
			uploadCmd.setFileName(new StringBuffer()
					.append(classId).append("_classPhoto").toString());
			uploadCmd.setFileDir(new StringBuffer()
					.append(LmsFileServer.CLASS_FOLDER)
					.append(LmsFileServer.CLASS_PHOTO).append("/")
					.append(classId).toString());
			uploadCmd.setContentType(file.getContentType());
			String url = fileServerOperator.uploadFile(file, uploadCmd);
			ClassInfo classInfo = new ClassInfo();
			classInfo.setClassId(classId);
			classInfo.setClassPhotoUrl(url);
			this.classInfoManager.updateClassPhotoUrl(classInfo);
			classInfo.setClassPhotoUrl(fileServerOperator.generatePresignedUrl(url));
			return new ClassInfoResponse(LmsStatus.SUCCESS,classInfo);	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/{classId}/classemployee/{classEmployeeId}/approvalstatus/{approvalStatus}")
	public @ResponseBody ClassEmployeeResponse updateApprovalStatus(HttpServletRequest request,
			@PathVariable("classId") int classId, @PathVariable("classEmployeeId") int classEmployeeId,
			@PathVariable("approvalStatus") int approvalStatus) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			ClassEmployee classEmployee = new ClassEmployee();
			classEmployee.setClassId(classId);
			classEmployee.setClassEmployeeId(classEmployeeId);
			classEmployee.setApprovalStatus(approvalStatus);
			this.classEmployeeManager.updateApprovalStatus(classEmployee);
			return new ClassEmployeeResponse(LmsStatus.SUCCESS);	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassEmployeeResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassEmployeeResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassEmployeeResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="classinfo/{classId}/classemployeeassessment")
	public @ResponseBody ClassEmployeeAssessmentResponse updateClassEmployeeAssessment(HttpServletRequest request,
		@PathVariable("classId") int classId, @RequestBody ClassEmployeeAssessment classEmployeeAssessment) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			if(this.classEmployeeAssessmentManager.countEmployeeGradingComponent(classEmployeeAssessment)
				!= 0) {
				this.classEmployeeAssessmentManager.updateClassEmployeeAssessment(classEmployeeAssessment);
			} else {
				this.classEmployeeAssessmentManager.createClassEmployeeAssessment(classEmployeeAssessment);
			}
			return new ClassEmployeeAssessmentResponse(LmsStatus.SUCCESS);	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassEmployeeAssessmentResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassEmployeeAssessmentResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassEmployeeAssessmentResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/{classId}/classemployeeassessments")
	public @ResponseBody ClassEmployeeAssessmentResponse fetchClassEmployeeAssessment(HttpServletRequest request,
		@PathVariable("classId") int classId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new ClassEmployeeAssessmentResponse(LmsStatus.SUCCESS,
				this.classEmployeeAssessmentManager.fetchClassEmployeeAssessment(classId));	
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassEmployeeAssessmentResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassEmployeeAssessmentResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassEmployeeAssessmentResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/classmaterial/fetchlatestid")
	public @ResponseBody ClassMaterialResponse fetchLatestClassMaterialId(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			int totalRecords = this.classMaterialManager.fetchLatestClassMaterialId();
			return new ClassMaterialResponse(LmsStatus.SUCCESS,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassMaterialResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassMaterialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassMaterialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/classmaterial/{classMaterialId}/viewstatus")
	public @ResponseBody ClassMaterialResponse updateClassMaterialViewStatus(HttpServletRequest request,
			@PathVariable("classMaterialId") int classMaterialId,
			@RequestBody ClassMaterial classMaterial) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classMaterialManager.updateClassMaterialViewStatus(classMaterial);
			return new ClassMaterialResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassMaterialResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassMaterialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassMaterialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/classmaterial/deleteclassmaterials")
	public @ResponseBody ClassMaterialResponse deleteClassMaterials(HttpServletRequest request,
			@RequestBody List <ClassMaterial> classMaterials) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classMaterialManager.deleteClassMaterials(classMaterials);
			return new ClassMaterialResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassMaterialResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassMaterialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassMaterialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="classinfo/classmaterial")
	public @ResponseBody ClassMaterialResponse createClassMaterial(HttpServletRequest request,
			@RequestBody ClassMaterial classMaterial) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new ClassMaterialResponse(LmsStatus.SUCCESS,this.classMaterialManager.createClassMaterial(classMaterial));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassMaterialResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassMaterialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassMaterialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="classinfo/classmaterial/{classMaterialId}/mediaFile")
	public @ResponseBody ClassMaterialResponse addClassMaterial(MultipartHttpServletRequest request,
			@PathVariable("classMaterialId") int classMaterialId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			MultipartFile file = request.getFile("mediaFileUpload");
			ClassMaterial classMaterial = this.classMaterialManager.fetchClassMaterial(classMaterialId);
			if(fileServerOperator.validateCustomFileFormat(classMaterial.getContentType(),file)) {
				if(fileServerOperator.validateCustomeFileSize(classMaterial.getContentType(),file)) {
					FileUploadCommand uploadCmd = new FileUploadCommand();
					uploadCmd.setFileName(new StringBuffer()
							.append(classMaterialId).append("_classMaterial").toString());
					uploadCmd.setFileDir(new StringBuffer()
							.append(LmsFileServer.CLASS_FOLDER)
							.append(LmsFileServer.CLASS_MATERIALS).append("/")
							.append(classMaterialId).toString());
					uploadCmd.setContentType(file.getContentType());
					classMaterial.setClassMaterialId(classMaterialId);
					classMaterial.setContentUrl(fileServerOperator.uploadFile(file, uploadCmd));
					classMaterial.setFileName(file.getOriginalFilename());
					this.classMaterialManager.updateContentUrl(classMaterial);
					return new ClassMaterialResponse(LmsStatus.SUCCESS);
				} else {
					this.classMaterialManager.deleteClassMaterial(classMaterialId);
					return new ClassMaterialResponse(LmsStatus.FILE_SIZE_IS_INVALID);
				}
			} else {
				this.classMaterialManager.deleteClassMaterial(classMaterialId);
				return new ClassMaterialResponse(LmsStatus.FILE_FORMAT_IS_INVALID);
			}
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassMaterialResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassMaterialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassMaterialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}

	@GetMapping("classinfos/inprogress")
	public @ResponseBody ClassInfoResponse fetchInProgressClassInfos(HttpServletRequest request) {
		try {
			User user = SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<ClassInfo> classInfos = this.classInfoManager.fetchInProgressClassInfosByUserId(user.getUserId());
			return new ClassInfoResponse(LmsStatus.SUCCESS,classInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping("classinfos/viewrestrictions")
	public @ResponseBody ClassInfoResponse fetchClassInfosWithViewRestrictions(HttpServletRequest request) {
		try {
			User user = SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<ClassInfo> classInfos=this.classInfoManager.fetchClassInfoWithViewRestriction(user.getUserId());
			return new ClassInfoResponse(LmsStatus.SUCCESS,classInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping("classinfos/completed")
	public @ResponseBody ClassInfoResponse fetchCompletedClassInfos(HttpServletRequest request) {
		try {
			User user = SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<ClassInfo> classInfos = this.classInfoManager.fetchCompletedClassInfosByUserId(user.getUserId());
			return new ClassInfoResponse(LmsStatus.SUCCESS,classInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfos/recommended")
	public @ResponseBody ClassInfoResponse fetchRecommendedClassInfos(HttpServletRequest request) {
		try {
			User user = SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<ClassInfo> classInfos = this.classInfoManager.fetchRecommendedClassInfos(
					user.getUserId(), user.getJobroleId());
			return new ClassInfoResponse(LmsStatus.SUCCESS,classInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfos/isselfregister/{isSelfRegister}")
	public @ResponseBody ClassInfoResponse fetchClassInfoByIsSelfRegister(HttpServletRequest request,
		@PathVariable("isSelfRegister") int isSelfRegister) {
		try {
			User user=SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<ClassInfo> classInfos = this.classInfoManager.fetchClassInfoByIsSelfRegister(user.getUserId(),isSelfRegister);
			return new ClassInfoResponse(LmsStatus.SUCCESS,classInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfos/statistics")
	public @ResponseBody ClassInfoResponse fetchNoOfClassInfos(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			int totalInProgressClassInfos=this.classInfoManager.countInProgressClassInfos(0);
			int totalUpcomingClassInfos=this.classInfoManager.countUpcomingClassInfos(0);
			int totalCompletedClassInfos=this.classInfoManager.countCompletedClassInfos(0);
			int totalCancelledClassInfos=this.classInfoManager.countCancelledClassInfos(0);
			return new ClassInfoResponse(LmsStatus.SUCCESS,totalInProgressClassInfos,totalUpcomingClassInfos,
				totalCompletedClassInfos,totalCancelledClassInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="user/classinfos/statistics")
	public @ResponseBody ClassInfoResponse fetchNoOfUserClassInfos(HttpServletRequest request) {
		try {
			User user = SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			int totalInProgressClassInfos=this.classInfoManager.countInProgressClassInfos(user.getUserId());
			int totalUpcomingClassInfos=this.classInfoManager.countUpcomingClassInfos(user.getUserId());
			int totalCompletedClassInfos=this.classInfoManager.countCompletedClassInfos(user.getUserId());
			int totalCancelledClassInfos=this.classInfoManager.countCancelledClassInfos(user.getUserId());
			return new ClassInfoResponse(LmsStatus.SUCCESS,totalInProgressClassInfos,totalUpcomingClassInfos,
				totalCompletedClassInfos,totalCancelledClassInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/{classId}/classschedule")
	public @ResponseBody ClassInfoResponse fetchClassSchedule(HttpServletRequest request,
		@PathVariable("classId") int classId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			ClassInfo classInfo = this.classInfoManager.fetchClassInfoSchedule(classId);
			return new ClassInfoResponse(LmsStatus.SUCCESS,classInfo);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="classinfo/{classId}/classemployee")
	public @ResponseBody ClassEmployeeResponse createClassEmployee(HttpServletRequest request,
		@PathVariable("classId") int classId, @RequestBody ClassInfo classInfo) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			ClassInfo classInfoSchedule=this.classInfoManager.fetchClassInfoSchedule(classId);
			classInfoSchedule.setEmployeeId(classInfo.getEmployeeId());
			classInfoSchedule.setClassEmployee(classInfo.getClassEmployee());
			return new ClassEmployeeResponse(this.classEmployeeManager.createClassEmployee(classInfoSchedule));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassEmployeeResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassEmployeeResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassEmployeeResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/{classId}/classemployee/{employeeId}/countapprovedemployee")
	public @ResponseBody ClassEmployeeResponse createClassEmployee(HttpServletRequest request,
		@PathVariable("classId") int classId, @PathVariable("employeeId") int employeeId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new ClassEmployeeResponse(LmsStatus.SUCCESS,
				this.classEmployeeManager.countClassEmployeeByEmployeeId(classId, employeeId));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassEmployeeResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassEmployeeResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassEmployeeResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/validateclassschedule")
	public @ResponseBody ClassInfoResponse validateClassInfoSchedule(HttpServletRequest request,
		@RequestBody ClassInfo classInfo) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new ClassInfoResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/classfinancial/{classId}/trainers")
	public @ResponseBody ClassTrainerFinancialResponse updateClassTrainerFinancials(HttpServletRequest request,
			@PathVariable("classId") int classId,
			@RequestBody List<TrainerFinance> trainerFinancials) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classTrainerFinancialManager.updateClassTrainerFinancials(classId, trainerFinancials);
			return new ClassTrainerFinancialResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassTrainerFinancialResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassTrainerFinancialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassTrainerFinancialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/classfinancial/{classId}/materials")
	public @ResponseBody ClassMaterialFinancialResponse updateClassMaterialFinancials(HttpServletRequest request,
			@PathVariable("classId") int classId,
			@RequestBody List<MaterialFinance> materialFinancials) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classMaterialFinancialManager.updateClassMaterialFinancials(classId, materialFinancials);
			return new ClassMaterialFinancialResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassMaterialFinancialResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassMaterialFinancialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassMaterialFinancialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/classfinancial/{classId}/others")
	public @ResponseBody ClassOtherFinancialResponse updateClassOtherFinancials(HttpServletRequest request,
			@PathVariable("classId") int classId,
			@RequestBody List<OtherFinance> otherFinancials) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classOtherFinancialManager.updateClassOtherFinancials(classId, otherFinancials);
			return new ClassOtherFinancialResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassOtherFinancialResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassOtherFinancialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassOtherFinancialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/classfinancial/{classId}/trainers/list")
	public @ResponseBody ClassTrainerFinancialResponse fetchClassTrainerFinancials(HttpServletRequest request,
			@PathVariable("classId") int classId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<TrainerFinance> trainerFinancials = this.classTrainerFinancialManager.fetchClassTrainerFinancials(classId);
			return new ClassTrainerFinancialResponse(LmsStatus.SUCCESS,trainerFinancials);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassTrainerFinancialResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassTrainerFinancialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassTrainerFinancialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/classfinancial/{classId}/materials/list")
	public @ResponseBody ClassMaterialFinancialResponse fetchClassMaterialFinancials(HttpServletRequest request,
			@PathVariable("classId") int classId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<MaterialFinance> materialFinancials = this.classMaterialFinancialManager.fetchClassMaterialFinancials(classId);
			return new ClassMaterialFinancialResponse(LmsStatus.SUCCESS,materialFinancials);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassMaterialFinancialResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassMaterialFinancialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassMaterialFinancialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/classfinancial/{classId}/others/list")
	public @ResponseBody ClassOtherFinancialResponse fetchClassOtherFinancials(HttpServletRequest request,
			@PathVariable("classId") int classId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<OtherFinance> otherFinancials = this.classOtherFinancialManager.fetchClassOtherFinancials(classId);
			return new ClassOtherFinancialResponse(LmsStatus.SUCCESS,otherFinancials);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassOtherFinancialResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassOtherFinancialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassOtherFinancialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/{classId}/classexam/pages")
	public @ResponseBody ClassExamResponse fetchClassExamList(HttpServletRequest request,
			@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize,
			@PathVariable("classId") int classId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<ClassExam> trainerFinancials = this.classExamManager.fetchClassExamList(classId,pageNumber,pageSize);
			int totalRecords = this.classExamManager.countClassExamList(classId);
			return new ClassExamResponse(LmsStatus.SUCCESS,trainerFinancials,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassExamResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassExamResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassExamResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/{classId}/classexams")
	public @ResponseBody ClassExamResponse createClassExam(HttpServletRequest request,
		@PathVariable("classId") int classId, @RequestBody List<ClassExam> classExams) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classExamManager.createClassExam(classExams);
			return new ClassExamResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassExamResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassExamResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassExamResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/{classId}/classexam/settings")
	public @ResponseBody ClassExamResponse updateClassExam(HttpServletRequest request,
		@PathVariable("classId") int classId, @RequestBody ClassExam classExam) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classExamManager.updateClassExam(classExam);
			return new ClassExamResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassExamResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassExamResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassExamResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@DeleteMapping(value="classinfo/{classId}/classexam/{classExamId}")
	public @ResponseBody ClassExamResponse deleteClassExam(HttpServletRequest request,
		@PathVariable("classId") int classId, @PathVariable("classExamId") int classExamId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classExamManager.deleteClassExam(classExamId);
			return new ClassExamResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassExamResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassExamResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassExamResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/{classId}/classmaterials")
	public @ResponseBody ClassMaterialResponse fetchClassMaterials(HttpServletRequest request,
		@PathVariable("classId") int classId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<ClassMaterial> classMaterials = this.classMaterialManager.fetchClassMaterials(classId);
			return new ClassMaterialResponse(LmsStatus.SUCCESS,classMaterials);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassMaterialResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassMaterialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassMaterialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/{classId}/classexams")
	public @ResponseBody ClassExamResponse fetchClassExams(HttpServletRequest request,
		@PathVariable("classId") int classId) {
		try {
			User user = SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			EmployeeInfo employeeInfo = this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
			List<ClassExam> classExams=this.classExamManager.fetchClassExams(classId, employeeInfo.getEmployeeId());
			return new ClassExamResponse(LmsStatus.SUCCESS,classExams);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassExamResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassExamResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassExamResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="validate-classsession")
	public @ResponseBody ClassSessionTimerResponse validateClassSessionTime(HttpServletRequest request,
		@RequestParam("classId") int classId) {
		try {
			User user = SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			EmployeeInfo employeeInfo = this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
			ClassSessionTimer classSessionTimer=this.classSessionTimerManager.fetchClassSessionTimer(classId,
					employeeInfo.getEmployeeId());
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			Date timeEnded = sdf.parse(sdf.format(new Date()));
			Date specifiedTimeEnded = sdf.parse(classSessionTimer.getTimeEnded());
			if(timeEnded.compareTo(specifiedTimeEnded) > 0 && 
				classSessionTimer.getSessionStatus() != LmsClassSessionTimerData.FINISHED) {
				classSessionTimer.setSessionStatus(LmsClassSessionTimerData.FINISHED);
				this.classSessionTimerManager.updateClassSessionTimer(classSessionTimer);
				return new ClassSessionTimerResponse(LmsStatus.SUCCESS,classSessionTimer.getSessionStatus(),true);
			} else {
				return new ClassSessionTimerResponse(LmsStatus.SUCCESS,classSessionTimer.getSessionStatus(),false);
			}
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassSessionTimerResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassSessionTimerResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassSessionTimerResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classsessiontimer/{classId}/timeSpent")
	public @ResponseBody ClassSessionTimerResponse updateClassSessionTimeSpent(HttpServletRequest request,
		@PathVariable("classId") int classId) {
		try {
			User user = SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			EmployeeInfo employeeInfo = this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
			this.classSessionTimerManager.updateTimeSpent(classId, employeeInfo.getEmployeeId());
			return new ClassSessionTimerResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassSessionTimerResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassSessionTimerResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassSessionTimerResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/{classId}/classevaluation/pages")
	public @ResponseBody ClassEvaluationResponse fetchClassEvaluationList(HttpServletRequest request,
			@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize,
			@PathVariable("classId") int classId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<ClassEvaluation> classEvaluations = this.classEvaluationManager.fetchClassEvaluationList(classId,pageNumber,pageSize);
			int totalRecords = this.classEvaluationManager.countTotalClassEvaluation(classId);
			return new ClassEvaluationResponse(LmsStatus.SUCCESS,classEvaluations,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassEvaluationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassEvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassEvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/{classId}/classevaluations/settings")
	public @ResponseBody ClassInfoResponse updateClassEvaluationSettings(HttpServletRequest request,
		@PathVariable("classId") int classId, @RequestBody ClassInfo classInfo) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classInfoManager.updateClassEvaluationSettings(classInfo);
			return new ClassInfoResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/{classId}/classevaluations")
	public @ResponseBody ClassEvaluationResponse addClassEvaluation(HttpServletRequest request,
		@PathVariable("classId") int classId, @RequestBody List<ClassEvaluation> classEvaluation) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classEvaluationManager.addClassEvaluation(classEvaluation);
			return new ClassEvaluationResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassEvaluationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassEvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassEvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@DeleteMapping(value="classinfo/{classId}/classevaluation/{classEvaluationId}")
	public @ResponseBody ClassEvaluationResponse deleteClassEvaluation(HttpServletRequest request,
		@PathVariable("classId") int classId, @PathVariable("classEvaluationId") int classEvaluationId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classEvaluationManager.deleteClassEvaluation(classEvaluationId);
			return new ClassEvaluationResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassEvaluationResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassEvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassEvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/{classId}/learningpaths")
	public @ResponseBody LearningPathResponse fecthClassLearningPaths(HttpServletRequest request,
		@PathVariable("classId") int classId) {
		try {
			User user=SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			EmployeeInfo employeeInfo=this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
			ClassInfo classInfo=this.classInfoManager.fetchClassInfo(classId);
			List<LearningPath> learningPaths=this.learningPathManager.fetchLearningPathWithTimeSpent(classId, 
				employeeInfo.getEmployeeId(), classInfo.getCourseId());
			List<LearningPathSection> learningPathSections=this.learningPathSectionManager.fetchLearningPathSections(
					classInfo.getCourseId());
			return new LearningPathResponse(LmsStatus.SUCCESS,learningPaths,learningPathSections);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new LearningPathResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new LearningPathResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new LearningPathResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/{classId}/learningpathsessiontimer/{learningPathId}/timeSpent")
	public @ResponseBody LearningPathSessionTimerResponse updateLearningPathTimeSpent(HttpServletRequest request, 
		@PathVariable("learningPathId") int learningPathId,@PathVariable("classId") int classId) {
		try {
			User user=SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			EmployeeInfo employeeInfo=this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
			this.learningPathSessionTimerManager.updateTimeSpent(classId,employeeInfo.getEmployeeId(),learningPathId);
			return new LearningPathSessionTimerResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new LearningPathSessionTimerResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new LearningPathSessionTimerResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new LearningPathSessionTimerResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/{classId}/learningpathsessiontimer/{learningPathId}")
	public @ResponseBody LearningPathSessionTimerResponse createLearningPathSessionTimer(HttpServletRequest request, 
		@PathVariable("learningPathId") int learningPathId,@PathVariable("classId") int classId) {
		try {
			User user=SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			EmployeeInfo employeeInfo=this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
			LearningPathSessionTimer learningPathSessionTimer=new LearningPathSessionTimer();
			learningPathSessionTimer.setClassId(classId);
			learningPathSessionTimer.setEmployeeId(employeeInfo.getEmployeeId());
			learningPathSessionTimer.setLearningPathId(learningPathId);
			learningPathSessionTimer.setLastAccessed(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			learningPathSessionTimer.setSessionStatus(LmsLearningPathSessionTimerData.FINISHED);
			this.learningPathSessionTimerManager.createLearningPathSessionTimer(learningPathSessionTimer);
			return new LearningPathSessionTimerResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new LearningPathSessionTimerResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new LearningPathSessionTimerResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new LearningPathSessionTimerResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="learningpathsessiontimer/{learningPathId}")
	public @ResponseBody LearningPathSessionTimerResponse updateLearningMaterialsSessionStatus(HttpServletRequest request,
		@RequestBody LearningPathSessionTimer learningPathSessionTimer,@PathVariable("learningPathId") int learningPathId) {
		try {
			User user=SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			EmployeeInfo employeeInfo=this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
			this.learningPathSessionTimerManager.updateTimeSpent(learningPathSessionTimer.getClassId(), 
					employeeInfo.getEmployeeId(), learningPathId);
			learningPathSessionTimer.setEmployeeId(employeeInfo.getEmployeeId());
			learningPathSessionTimer.setLastAccessed(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			this.learningPathSessionTimerManager.updateLearningPathSessionTimer(learningPathSessionTimer);
			return new LearningPathSessionTimerResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new LearningPathSessionTimerResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new LearningPathSessionTimerResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new LearningPathSessionTimerResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/{classId}/classemployee/trainingStatus")
	public @ResponseBody ClassEmployeeResponse updateClassEmployeeTrainingStatus(HttpServletRequest request,
		@PathVariable("classId") int classId) {
		try {
			User user=SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			EmployeeInfo empInfo=this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
			int totalTimeSpent=this.learningPathSessionTimerManager.calculateTotalTimeSpent(classId,empInfo.getEmployeeId());
			ClassInfo classInfo=this.classInfoManager.fetchClassInfo(classId);
			System.out.println(classInfo.getClassDuration()+"<="+totalTimeSpent);
			if(classInfo.getClassDuration() <= totalTimeSpent) {
				this.classEmployeeManager.updateTrainingStatus(classId,empInfo.getEmployeeId(),
					LmsClassEmployeeData.TRAININGSTATUS_COMPLETED);
				return new ClassEmployeeResponse(LmsStatus.SUCCESS);
			} else {
				return new ClassEmployeeResponse(LmsStatus.TIME_SPENT_IS_NOT_ENOUGH);
			}
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassEmployeeResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassEmployeeResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassEmployeeResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/{classId}/classipaddress/pages")
	public @ResponseBody ClassIpAddressResponse fetchClassIpAddressList(HttpServletRequest request,
			@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize,
			@PathVariable("classId") int classId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<ClassIpAddress> classIpAddresses = this.classIpAddressManager.fetchClassIpAddressList(classId,pageNumber,pageSize);
			int totalRecords = this.classIpAddressManager.countTotalClassIpAddress(classId);
			return new ClassIpAddressResponse(LmsStatus.SUCCESS,totalRecords,classIpAddresses);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassIpAddressResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassIpAddressResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassIpAddressResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/{classId}/classipaddresses")
	public @ResponseBody ClassIpAddressResponse updateClassIpAddress(HttpServletRequest request,
	@PathVariable("classId") int classId, @RequestBody List<ClassIpAddress> classIpAddresses) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classIpAddressManager.addClassIpAddresses(classIpAddresses);
			return new ClassIpAddressResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassIpAddressResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassIpAddressResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassIpAddressResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@DeleteMapping(value="classinfo/{classId}/classipaddress/{classIpId}")
	public @ResponseBody ClassIpAddressResponse deleteClassIpAddress(HttpServletRequest request,
		@PathVariable("classId") int classId, @PathVariable("classIpId") int classIpId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classIpAddressManager.deleteClassIpAddress(classIpId);
			return new ClassIpAddressResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassIpAddressResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassIpAddressResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassIpAddressResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="classinfo/{classId}/classsettings")
	public @ResponseBody ClassInfoResponse updateClassInfoSettings(HttpServletRequest request,
	@PathVariable("classId") int classId, @RequestBody ClassInfo classInfo) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.classInfoManager.updateClassInfoSettings(classInfo);
			return new ClassInfoResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new ClassInfoResponse(LmsStatus.SESSION_EXPIRED);	
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new ClassInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new ClassInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
}

