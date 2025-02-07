package com.pagss.lms.controllers;

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
import com.pagss.lms.constants.LmsFileServer;
import com.pagss.lms.constants.LmsStatus;
import com.pagss.lms.domains.ClassDefault;
import com.pagss.lms.domains.ClassEvaluation;
import com.pagss.lms.domains.CourseEvaluation;
import com.pagss.lms.domains.CourseExam;
import com.pagss.lms.domains.CourseInfo;
import com.pagss.lms.domains.CourseMaterial;
import com.pagss.lms.domains.EmployeeEvaluation;
import com.pagss.lms.domains.EmployeeInfo;
import com.pagss.lms.domains.GradingComponent;
import com.pagss.lms.domains.LearningPath;
import com.pagss.lms.domains.LearningPathSection;
import com.pagss.lms.domains.User;
import com.pagss.lms.exceptions.SessionExpiredException;
import com.pagss.lms.handlers.SessionHandler;
import com.pagss.lms.handlers.StackTraceHandler;
import com.pagss.lms.manager.interfaces.ClassDefaultManager;
import com.pagss.lms.manager.interfaces.ClassEvaluationManager;
import com.pagss.lms.manager.interfaces.CourseEvaluationManager;
import com.pagss.lms.manager.interfaces.CourseExamManager;
import com.pagss.lms.manager.interfaces.CourseInfoManager;
import com.pagss.lms.manager.interfaces.CourseMaterialManager;
import com.pagss.lms.manager.interfaces.EmployeeInfoManager;
import com.pagss.lms.manager.interfaces.GradingComponentManager;
import com.pagss.lms.manager.interfaces.LearningPathManager;
import com.pagss.lms.manager.interfaces.LearningPathSectionManager;
import com.pagss.lms.responses.ClassEvaluationResponse;
import com.pagss.lms.responses.CourseEvaluationResponse;
import com.pagss.lms.responses.CourseExamResponse;
import com.pagss.lms.responses.CourseInfoResponse;
import com.pagss.lms.responses.CourseMaterialResponse;
import com.pagss.lms.responses.EmployeeEvaluationResponse;
import com.pagss.lms.responses.GradingComponentResponse;
import com.pagss.lms.responses.LearningPathResponse;
import com.pagss.lms.responses.LearningPathSectionResponse;
import com.pagss.lms.utilities.FileServerOperator;

@RestController
public class CourseController {

	@Autowired
	private CourseInfoManager courseInfoManager;
	@Autowired
	private EmployeeInfoManager employeeInfoManager;
	@Autowired
	private FileServerOperator fileServerOperator;
	@Autowired
	private ClassDefaultManager classDefaultManager;
	@Autowired
	private GradingComponentManager gradingComponentManager;
	@Autowired
	private CourseMaterialManager courseMaterialManager;
	@Autowired
	private CourseExamManager courseExamManager;
	@Autowired
	private LearningPathSectionManager learningPathSectionManager;
	@Autowired
	private LearningPathManager learningPathManager;
	@Autowired
	private CourseEvaluationManager courseEvaluationManager;
	@Autowired
	private ClassEvaluationManager classEvaluationManager;
	/***************************************************************************************************************************/
	/* START: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	@GetMapping(value="admin.trainingcourse")
	public ModelAndView goToTrainingCoursePage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		return new ModelAndView("admin/admin-trainingcourse");
	}
	
	@GetMapping(value="admin.createtrainingcourse")
	public ModelAndView goToCreateTrainingCoursePage(HttpServletRequest request) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		return new ModelAndView("admin/admin-createtrainingcourse");
	}
	
	@GetMapping(value="admin.courseevaluation")
	public ModelAndView goToCourseEvaluationPage(HttpServletRequest request,
		@RequestParam("courseId") Integer courseId) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-courseevaluation");
		mav.addObject("courseId",courseId);
		mav.addObject("courseInfo",this.courseInfoManager.fetchCourseInfo(courseId));
		return mav;
	}
	
	@GetMapping(value="admin.courseexamination")
	public ModelAndView goToCourseExaminationPage(HttpServletRequest request,
		@RequestParam("courseId") Integer courseId) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-courseexamination");
		mav.addObject("courseId",courseId);
		mav.addObject("courseInfo",this.courseInfoManager.fetchCourseInfo(courseId));
		return mav;
	}
	
	@GetMapping(value="admin.coursematerials")
	public ModelAndView goToCourseMaterialsPage(HttpServletRequest request,
		@RequestParam("courseId") Integer courseId) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-courselearningmaterials");
		mav.addObject("courseId",courseId);
		mav.addObject("courseInfo",this.courseInfoManager.fetchCourseInfo(courseId));
		return mav;
	}
	
	@GetMapping(value="admin.coursepath")
	public ModelAndView goToCoursePathPage(HttpServletRequest request,
		@RequestParam("courseId") Integer courseId) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-courselearningpath");
		mav.addObject("courseId",courseId);
		mav.addObject("courseInfo",this.courseInfoManager.fetchCourseInfo(courseId));
		return mav;
	}
	
	@GetMapping(value="admin.coursedetails")
	public ModelAndView gotoCourseDetails(HttpServletRequest request, 
		@RequestParam("courseId") Integer courseId) throws Exception {
		SessionHandler.getSessionUser(request);
		SessionHandler.checkSessionUserAccess(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin-viewtrainingcoursedetails");
		mav.addObject("courseId",courseId);
		mav.addObject("courseInfo",this.courseInfoManager.fetchCourseInfo(courseId));
		return mav;
	}
	/***************************************************************************************************************************/
	/* END: NORMAL HTTPREQUEST																							 **/
	/**************************************************************************************************************************/
	/***************************************************************************************************************************/
	/* START: RESTFUL API																						 **/
	/**************************************************************************************************************************/
	@GetMapping(value="courseinfo/pages")
	public @ResponseBody CourseInfoResponse fetchCoursesPage(HttpServletRequest request, @RequestParam("pageNumber") int pageNumber,
		@RequestParam("pageSize") int pageSize) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<CourseInfo> courseInfos = this.courseInfoManager.fetchCourseInfoPages(pageNumber, pageSize);
			int totalRecords = this.courseInfoManager.countFetchCourseInfos();
			return new CourseInfoResponse(LmsStatus.SUCCESS,totalRecords,courseInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="courseinfo/active/pages")
	public @ResponseBody CourseInfoResponse fetchCoursesPage(HttpServletRequest request, 
			@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize,
			@RequestParam("status") int status,@RequestParam("assignmentType") int assignmentType,
			@RequestParam("assignmentTypeId")int assignmentTypeId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TableCommand tableCommand=new TableCommand();
			tableCommand.setPageSize(pageSize);
			tableCommand.setPageNumber(pageNumber);
			tableCommand.setSearchByStatus(status);
			tableCommand.setAssignmentType(assignmentType);
			tableCommand.setAssignmentTypeId(assignmentTypeId);
			List<CourseInfo> courseInfos=this.courseInfoManager.fetchCourseInfoPagesWithStatus(
					tableCommand);
			int totalRecords = this.courseInfoManager.countFetchCourseInfosWithStatus(tableCommand);
			return new CourseInfoResponse(LmsStatus.SUCCESS,totalRecords,courseInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="courseinfo/active/list")
	public @ResponseBody CourseInfoResponse fetchCourseList(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<CourseInfo> courseInfos = this.courseInfoManager.fetchActiveCourseInfoList();
			return new CourseInfoResponse(LmsStatus.SUCCESS,courseInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="courseinfo/search")
	public @ResponseBody CourseInfoResponse searchCoursesPage(HttpServletRequest request,
		@RequestParam("pageNumber") int pageNumber,@RequestParam("pageSize") int pageSize,
		@RequestParam("keyword") String keyword) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<CourseInfo> courseInfos = this.courseInfoManager.searchCourseInfos(pageNumber, pageSize,keyword);
			int totalRecords = this.courseInfoManager.countSearchCourseInfos(keyword);
			return new CourseInfoResponse(LmsStatus.SUCCESS,totalRecords,courseInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="courseinfo/search/status")
	public @ResponseBody CourseInfoResponse searchCoursesPage(HttpServletRequest request,
		@RequestParam("pageNumber") int pageNumber,@RequestParam("pageSize") int pageSize,
		@RequestParam("keyword") String keyword,@RequestParam("status") int status,
		@RequestParam("assignmentType") int assignmentType,
		@RequestParam("assignmentTypeId") int assignmentTypeId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			TableCommand tableCommand = new TableCommand();
			tableCommand.setPageNumber(pageNumber);
			tableCommand.setPageSize(pageSize);
			tableCommand.setKeyword(keyword);
			tableCommand.setSearchByStatus(status);
			tableCommand.setAssignmentType(assignmentType);
			tableCommand.setAssignmentTypeId(assignmentTypeId);
			List<CourseInfo> courseInfos = this.courseInfoManager.searchTrainingFlowCourseInfos(tableCommand);
			int totalRecords = this.courseInfoManager.countTrainingFlowCourseInfos(tableCommand);
			return new CourseInfoResponse(LmsStatus.SUCCESS,totalRecords,courseInfos);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="courseinfo")
	public @ResponseBody CourseInfoResponse createCourseInfo(HttpServletRequest request, 
			@RequestBody CourseInfo courseInfo) {
		try {
			User user = SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			courseInfo.setModifiedBy(this.employeeInfoManager.fetchEmployeeInfoByUserId(
				user.getUserId()).getFullName());
			if(this.courseInfoManager.countCourseCode(courseInfo.getCourseCode()) != 0) {
				return new CourseInfoResponse(LmsStatus.COURSECODE_EXISTS);
			} else {
				return new CourseInfoResponse(LmsStatus.SUCCESS,
						this.courseInfoManager.createCourseInfo(courseInfo));
			}
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="courseinfo/{courseId}/photo")
	public @ResponseBody CourseInfoResponse createCourseInfo(MultipartHttpServletRequest request, 
			@PathVariable("courseId") int courseId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			MultipartFile file = request.getFile("fileUpload");
			if(fileServerOperator.validateImageFormat(file)) {
				if(file.getSize() > 5242880 ) {
					return new CourseInfoResponse(LmsStatus.FILE_SIZE_IS_INVALID);	
				} else {
					FileUploadCommand uploadCmd = new FileUploadCommand();
					uploadCmd.setFileName(new StringBuffer()
							.append(courseId).append("_photo").toString());
					uploadCmd.setFileDir(new StringBuffer()
							.append(LmsFileServer.COURSE_FOLDER)
							.append(LmsFileServer.COURSE_PHOTO_FOLDER).append("/")
							.append(courseId).toString());
					uploadCmd.setContentType(file.getContentType());
					ClassDefault classDefault = new ClassDefault();
					classDefault.setCourseId(courseId);
					classDefault.setClassPhotoUrl(fileServerOperator.uploadFile(file, uploadCmd));
					classDefault.setPhotoFileName(file.getOriginalFilename());
					this.classDefaultManager.updatePhotoUrl(classDefault);
					return new CourseInfoResponse(LmsStatus.SUCCESS);
				}
			} else {
				return new CourseInfoResponse(LmsStatus.FILE_FORMAT_IS_INVALID);
			}
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="courseInfo/courseId/latest")
	public @ResponseBody CourseInfoResponse createCourseInfo(HttpServletRequest request) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new CourseInfoResponse(LmsStatus.SUCCESS,this.courseInfoManager.fetchLatestId());
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="courseinfo/{courseId}")
	public @ResponseBody CourseInfoResponse fetchCourseInfo(HttpServletRequest request,
			@PathVariable("courseId") int courseId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new CourseInfoResponse(LmsStatus.SUCCESS,this.courseInfoManager.fetchCourseInfo(courseId));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="courseinfo/{courseId}")
	public @ResponseBody CourseInfoResponse updateCourseInfo(HttpServletRequest request,
			@RequestBody CourseInfo courseInfo,@PathVariable("courseId") int courseId) {
		try {
			User user = SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			String modifiedBy = this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId()).getFullName();
			courseInfo.setModifiedBy(modifiedBy);
			this.courseInfoManager.updateCourseInfo(courseInfo);
			return new CourseInfoResponse(LmsStatus.SUCCESS,courseId);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="courseinfo/{courseId}/gradingcomponent")
	public @ResponseBody CourseInfoResponse createGradingComponent(HttpServletRequest request,
			@RequestBody GradingComponent gradingComponent,@PathVariable("courseId") int courseId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			if(this.gradingComponentManager.checkIfExistComponentExist(gradingComponent)!=0) {
				return new CourseInfoResponse(LmsStatus.COMPONENT_EXISTS);
			} else {
				this.gradingComponentManager.createGradingComponent(gradingComponent);
				return new CourseInfoResponse(LmsStatus.SUCCESS);
			}
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="courseinfo/{courseId}/gradingcomponents/pages")
	public @ResponseBody GradingComponentResponse fetchGradingComponentsPages(HttpServletRequest request,
			@PathVariable("courseId") int courseId, @RequestParam("pageSize") int pageSize,
			@RequestParam("pageNumber") int pageNumber) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<GradingComponent> gradingComponents=this.gradingComponentManager.fetchGradingComponentPages(pageSize, 
					pageNumber, courseId);
			int totalRecords = this.gradingComponentManager.countTotalGradingComponents(courseId);
			return new GradingComponentResponse(LmsStatus.SUCCESS,totalRecords,gradingComponents);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new GradingComponentResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new GradingComponentResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new GradingComponentResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="courseinfo/coursematerial/contenturl")
	public @ResponseBody CourseMaterialResponse createCourseMaterial(HttpServletRequest request,
			@RequestBody CourseMaterial courseMaterial) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new CourseMaterialResponse(LmsStatus.SUCCESS,this.courseMaterialManager.createCourseMaterial(courseMaterial));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseMaterialResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseMaterialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseMaterialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}

	@PostMapping(value="courseinfo/coursematerial/{courseMaterialId}/mediaFile")
	public @ResponseBody CourseMaterialResponse uploadCourseMaterialsMediFile(MultipartHttpServletRequest request,
			@PathVariable("courseMaterialId") int courseMaterialId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			MultipartFile file = request.getFile("mediaFileUpload");
			CourseMaterial courseMaterial = this.courseMaterialManager.fetchCourseMaterial(courseMaterialId);
			if(fileServerOperator.validateCustomFileFormat(courseMaterial.getContentType(),file)) {
				if(fileServerOperator.validateCustomeFileSize(courseMaterial.getContentType(),file)) {
					FileUploadCommand uploadCmd = new FileUploadCommand();
					uploadCmd.setFileName(new StringBuffer()
							.append(courseMaterialId).append("_courseMaterial").toString());
					uploadCmd.setFileDir(new StringBuffer()
							.append(LmsFileServer.COURSE_FOLDER)
							.append(LmsFileServer.COURSE_MATERIALS).append("/")
							.append(courseMaterialId).toString());
					uploadCmd.setContentType(file.getContentType());
					courseMaterial.setCourseMaterialId(courseMaterialId);
					courseMaterial.setContentUrl(fileServerOperator.uploadFile(file, uploadCmd));
					courseMaterial.setFileName(file.getOriginalFilename());
					this.courseMaterialManager.updateContentUrl(courseMaterial);
					return new CourseMaterialResponse(LmsStatus.SUCCESS);
				} else {
					this.courseMaterialManager.deleteCourseMaterial(courseMaterialId);
					return new CourseMaterialResponse(LmsStatus.FILE_SIZE_IS_INVALID);
				}
			} else {
				this.courseMaterialManager.deleteCourseMaterial(courseMaterialId);
				return new CourseMaterialResponse(LmsStatus.FILE_FORMAT_IS_INVALID);
			}
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseMaterialResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseMaterialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseMaterialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="courseinfo/{courseId}/coursematerial/file")
	public @ResponseBody CourseMaterialResponse uploadCourseMaterialsFile(MultipartHttpServletRequest request,
			@PathVariable("courseId") int courseId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			MultipartFile file = request.getFile("mediaFileUpload");
			int courseMaterialId=this.courseMaterialManager.fetchLastInsertedCourseMaterialId()+1;
			FileUploadCommand uploadCmd = new FileUploadCommand();
			uploadCmd.setFileName(new StringBuffer()
					.append(courseMaterialId).append("_courseMaterial").toString());
			uploadCmd.setFileDir(new StringBuffer()
					.append(LmsFileServer.COURSE_FOLDER)
					.append(LmsFileServer.COURSE_MATERIALS).append("/")
					.append(courseMaterialId).toString());
			uploadCmd.setContentType(file.getContentType());
			
			CourseMaterial courseMaterial = new CourseMaterial();
			courseMaterial.setCourseId(courseId);
			courseMaterial.setContentUrl(fileServerOperator.uploadFile(file, uploadCmd));
			courseMaterial.setFileName(file.getOriginalFilename());
			return new CourseMaterialResponse(LmsStatus.SUCCESS,
				this.courseMaterialManager.insertCourseMaterial(courseMaterial));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseMaterialResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseMaterialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseMaterialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="courseinfo/{courseId}/coursematerial/{courseMaterialId}")
	public @ResponseBody CourseMaterialResponse updateCourseMaterial(HttpServletRequest request,
		@PathVariable("courseId") int courseId,@PathVariable("courseMaterialId") int courseMaterialId,
		@RequestBody CourseMaterial courseMaterial) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.courseMaterialManager.updateCourseMaterial(courseMaterial);
			return new CourseMaterialResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseMaterialResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseMaterialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseMaterialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="courseinfo/coursematerial/pages")
	public @ResponseBody CourseMaterialResponse fetchCourseMaterialsPages(HttpServletRequest request,
		@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize,
		@RequestParam("courseId") int courseId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<CourseMaterial> courseMaterials=this.courseMaterialManager.fetchCourseMaterialsPages(
					courseId,pageSize, pageNumber);
			int totalRecords=this.courseMaterialManager.countTotalCourseMaterials(courseId);
			return new CourseMaterialResponse(LmsStatus.SUCCESS,totalRecords,courseMaterials);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseMaterialResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseMaterialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseMaterialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@DeleteMapping(value="courseinfo/coursematerials")
	public @ResponseBody CourseMaterialResponse deleteCourseMaterials(HttpServletRequest request,
		@RequestBody CourseInfo courseInfo) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<CourseMaterial> courseMaterials = courseInfo.getCourseMaterials();
			this.courseMaterialManager.deleteCourseMaterials(courseMaterials);
			return new CourseMaterialResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseMaterialResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseMaterialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseMaterialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="courseinfo/coursematerial/{courseMaterialId}/viewstatus")
	public @ResponseBody CourseMaterialResponse updateViewStatus(HttpServletRequest request,
		@RequestBody CourseMaterial courseMaterial,@PathVariable("courseMaterialId")int courseMaterialId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.courseMaterialManager.updateViewStatus(courseMaterial);
			return new CourseMaterialResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseMaterialResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseMaterialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseMaterialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="courseinfo/{courseId}/coursematerials/viewstatus")
	public @ResponseBody CourseMaterialResponse fetchCourseMaterialsWithViewStatus(HttpServletRequest request,
		@PathVariable("courseId") int courseId,@RequestParam("viewStatus") int viewStatus) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<CourseMaterial> courseMaterials=this.courseMaterialManager.fetchCourseMaterialsWithViewStatus(
					courseId, viewStatus);
			return new CourseMaterialResponse(LmsStatus.SUCCESS,courseMaterials);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseMaterialResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseMaterialResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseMaterialResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="courseinfo/{courseId}/courseexam/pages")
	public @ResponseBody CourseExamResponse fetchCourseExamPages(HttpServletRequest request,
		@PathVariable("courseId") int courseId,@RequestParam("pageNumber") int pageNumber,
		@RequestParam("pageSize") int pageSize) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<CourseExam> courseExams = this.courseExamManager.fetchCourseExamPages(pageSize, pageNumber, courseId);
			int totalRecords = this.courseExamManager.countTotalCourseExams(courseId);
			return new CourseExamResponse(LmsStatus.SUCCESS,totalRecords,courseExams);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseExamResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseExamResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseExamResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="courseinfo/{courseId}/courseexams")
	public @ResponseBody CourseExamResponse fetchCourseExamPages(HttpServletRequest request,
		@PathVariable("courseId") int courseId,@RequestParam("examType") int examType) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<CourseExam> courseExams = this.courseExamManager.fetchCourseExamsWithExamType(courseId, examType);
			return new CourseExamResponse(LmsStatus.SUCCESS,courseExams);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseExamResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseExamResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseExamResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="courseinfo/{courseId}/courseexam")
	public @ResponseBody CourseExamResponse createCourseExam(HttpServletRequest request,
		@PathVariable("courseId") int courseId, @RequestBody CourseExam courseExam) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new CourseExamResponse(LmsStatus.SUCCESS,this.courseExamManager.createCourseExam(courseExam));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseExamResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseExamResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseExamResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="courseinfo/{courseId}/courseexams")
	public @ResponseBody CourseExamResponse createCourseExams(HttpServletRequest request,
		@PathVariable("courseId") int courseId, @RequestBody CourseInfo courseInfo) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.courseExamManager.createCourseExams(courseInfo.getCourseExams());
			return new CourseExamResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseExamResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseExamResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseExamResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@DeleteMapping(value="courseinfo/{courseId}/courseexam/{courseExamId}")
	public @ResponseBody CourseExamResponse deleteCourseExam(HttpServletRequest request,
		@PathVariable("courseId") int courseId,@PathVariable("courseExamId") int courseExamId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.courseExamManager.deleteCourseExam(courseExamId);
			return new CourseExamResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseExamResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseExamResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseExamResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="courseinfo/{courseId}/courseexam/{courseExamId}")
	public @ResponseBody CourseExamResponse updateCourseExam(HttpServletRequest request,
		@PathVariable("courseId") int courseId,@PathVariable("courseExamId") int courseExamId,
		@RequestBody CourseExam courseExam) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.courseExamManager.updateCourseExam(courseExam);
			return new CourseExamResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseExamResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseExamResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseExamResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="courseinfo/{courseId}/gradingcomponent/{gradingComponentId}")
	public @ResponseBody CourseInfoResponse updateGradingComponent(HttpServletRequest request,
			@RequestBody GradingComponent gradingComponent,@PathVariable("courseId") int courseId,
			@PathVariable("gradingComponentId") int gradingComponentId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			GradingComponent component=this.gradingComponentManager.fetchGradingComponent(gradingComponentId);
			if(component.getComponentDesc().equals(gradingComponent.getComponentDesc())) {
				this.gradingComponentManager.updateGradingComponent(gradingComponent);
				return new CourseInfoResponse(LmsStatus.SUCCESS);
			} else {
				if(this.gradingComponentManager.checkIfExistComponentExist(gradingComponent)!=0) {
					return new CourseInfoResponse(LmsStatus.COMPONENT_EXISTS);
				} else {
					this.gradingComponentManager.updateGradingComponent(gradingComponent);
					return new CourseInfoResponse(LmsStatus.SUCCESS);
				}
			}
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseInfoResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseInfoResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseInfoResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="courseinfo/{courseId}/gradingcomponents")
	public @ResponseBody GradingComponentResponse fetchGradingComponents(HttpServletRequest request,
			@PathVariable("courseId") int courseId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<GradingComponent> gradingComponents=this.gradingComponentManager.fetchGradingComponents(courseId);
			int totalRecords = this.gradingComponentManager.countTotalGradingComponents(courseId);
			return new GradingComponentResponse(LmsStatus.SUCCESS,totalRecords,gradingComponents);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new GradingComponentResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new GradingComponentResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new GradingComponentResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@DeleteMapping(value="courseinfo/{courseId}/gradingcomponent/{gradingComponentId}")
	public @ResponseBody GradingComponentResponse deleteGradingComponent(HttpServletRequest request,
		@PathVariable("courseId") int courseId, @PathVariable("gradingComponentId") int gradingComponentId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.gradingComponentManager.deleteGradingComponent(gradingComponentId);
			return new GradingComponentResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new GradingComponentResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new GradingComponentResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new GradingComponentResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="courseinfo/learningpath")
	public @ResponseBody LearningPathResponse createLearningPath(HttpServletRequest request,
		@RequestBody LearningPath learningPath) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			if(this.learningPathManager.checkIfCourseExamExist(learningPath)!=0) {
				return new LearningPathResponse(LmsStatus.LEARNINGPATH_ALREADY_EXIST);
			} else {
				this.learningPathManager.createLearningPath(learningPath);
				return new LearningPathResponse(LmsStatus.SUCCESS);
			}
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
	
	@GetMapping(value="courseinfo/{courseId}/learningpaths")
	public @ResponseBody LearningPathResponse fetchLearningPaths(HttpServletRequest request,
		@PathVariable("courseId") int courseId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new LearningPathResponse(LmsStatus.SUCCESS,
					this.learningPathManager.fetchLearningPaths(courseId));
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
	
	@PutMapping(value="courseinfo/{courseId}/learningpath/suborderno")
	public @ResponseBody LearningPathResponse fetchLearningPaths(HttpServletRequest request,
			@PathVariable("courseId") int courseId,@RequestBody CourseInfo courseInfo) {
			try {
				SessionHandler.getSessionUser(request);
				SessionHandler.checkSessionUserAccess(request);
				this.learningPathManager.updateSubOrderNo(courseInfo.getLearningPaths());
				return new LearningPathResponse(LmsStatus.SUCCESS);
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
	
	@DeleteMapping(value="courseinfo/{courseId}/learningpath/{learningPathId}")
	public @ResponseBody LearningPathResponse fetchLearningPaths(HttpServletRequest request,
			@PathVariable("courseId") int courseId,@PathVariable("learningPathId") int learningPathId) {
			try {
				SessionHandler.getSessionUser(request);
				SessionHandler.checkSessionUserAccess(request);
				this.learningPathManager.deleteLearningPath(learningPathId);
				return new LearningPathResponse(LmsStatus.SUCCESS);
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
	
	@PutMapping(value="courseinfo/{courseId}/learningpathsection/sectionorderno")
	public @ResponseBody LearningPathSectionResponse updateLearningPathSectionOrderNo(HttpServletRequest request,
			@PathVariable("courseId") int courseId,@RequestBody CourseInfo courseInfo) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.learningPathSectionManager.updateSectionOrderNo(courseInfo.getLearningPathSections());
			return new LearningPathSectionResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new LearningPathSectionResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new LearningPathSectionResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new LearningPathSectionResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PostMapping(value="courseinfo/{courseId}/learningpathsection")
	public @ResponseBody LearningPathSectionResponse createLearningPathSection(HttpServletRequest request,
		@PathVariable("courseId") int courseId, @RequestBody LearningPathSection learningPathSection) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.learningPathSectionManager.createLearningPathSection(learningPathSection);
			return new LearningPathSectionResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new LearningPathSectionResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new LearningPathSectionResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new LearningPathSectionResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="courseinfo/{courseId}/learningpathsections")
	public @ResponseBody LearningPathSectionResponse fetchLearningPathSections(HttpServletRequest request,
		@PathVariable("courseId") int courseId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			return new LearningPathSectionResponse(LmsStatus.SUCCESS,
					this.learningPathSectionManager.fetchLearningPathSections(courseId));
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new LearningPathSectionResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new LearningPathSectionResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new LearningPathSectionResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="courseinfo/{courseId}/learningpathsections")
	public @ResponseBody LearningPathSectionResponse updateLearningPathSections(HttpServletRequest request,
		@PathVariable("courseId") int courseId,@RequestBody LearningPathSection learningPathSection) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.learningPathSectionManager.updateLearningPathSection(learningPathSection);
			return new LearningPathSectionResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new LearningPathSectionResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new LearningPathSectionResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new LearningPathSectionResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@DeleteMapping(value="courseinfo/{courseId}/learningpathsections/{learningPathSectionId}")
	public @ResponseBody LearningPathSectionResponse deleteLearningPathSection(HttpServletRequest request,
		@PathVariable("courseId") int courseId, @PathVariable("learningPathSectionId") int learningPathSectionId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.learningPathSectionManager.deleteLearningPathSection(learningPathSectionId);
			return new LearningPathSectionResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new LearningPathSectionResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new LearningPathSectionResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new LearningPathSectionResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="courseinfo/{courseId}/courseevaluations/list")
	public @ResponseBody CourseEvaluationResponse fetchCourseEvaluations(HttpServletRequest request,
			@PathVariable("courseId") int courseId) {
		try {
			User user = SessionHandler.getSessionUser(request);
			EmployeeInfo employee = this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
			SessionHandler.checkSessionUserAccess(request);
			List<CourseEvaluation> courseEvaluations = this.courseEvaluationManager.fetchCourseEvaluations(courseId, employee.getEmployeeId());
			return new CourseEvaluationResponse(LmsStatus.SUCCESS,courseEvaluations);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseEvaluationResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseEvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseEvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="courseinfo/{courseId}/courseevaluations/pages")
	public @ResponseBody CourseEvaluationResponse fetchCourseEvaluationsTable(HttpServletRequest request,
			@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNo,
			@PathVariable("courseId") int courseId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			List<CourseEvaluation> courseEvaluations = this.courseEvaluationManager.fetchCourseEvaluationsTable(courseId,pageNo,pageSize);
			int totalRecords = this.courseEvaluationManager.countTotalCourseEvaluations(courseId);
			return new CourseEvaluationResponse(LmsStatus.SUCCESS,courseEvaluations,totalRecords);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseEvaluationResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseEvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseEvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@PutMapping(value="courseinfo/courseevaluations/{courseId}")
	public @ResponseBody CourseEvaluationResponse addCourseEvaluations(HttpServletRequest request,
		@PathVariable("courseId") int courseId, 
		@RequestBody List<CourseEvaluation> courseEvaluations) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.courseEvaluationManager.addCourseEvaluations(courseEvaluations);
			return new CourseEvaluationResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseEvaluationResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseEvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseEvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@DeleteMapping(value="courseinfo/courseevaluation/{courseEvaluationId}")
	public @ResponseBody CourseEvaluationResponse deleteCourseEvaluation(HttpServletRequest request,
		@PathVariable("courseEvaluationId") int courseEvaluationId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			this.courseEvaluationManager.deleteCourseEvaluation(courseEvaluationId);
			return new CourseEvaluationResponse(LmsStatus.SUCCESS);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseEvaluationResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseEvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseEvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="courseinfo/{courseId}/courseexam")
	public @ResponseBody CourseExamResponse fetchCourseExamByClassId(HttpServletRequest request,
		@PathVariable("courseId") int courseId,@RequestParam("classId") int classId) {
		try {
			User user=SessionHandler.getSessionUser(request);
			EmployeeInfo employeeInfo=this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
			SessionHandler.checkSessionUserAccess(request);
			List<CourseExam> courseExams=this.courseExamManager.fetchCourseExams(courseId,classId,employeeInfo.getEmployeeId());
			return new CourseExamResponse(LmsStatus.SUCCESS,courseExams);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseExamResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseExamResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseExamResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	@GetMapping(value="classinfo/fetchcourseevaluationpath")
	public @ResponseBody CourseEvaluationResponse fetchEvaluationClassPath(HttpServletRequest request,
			@RequestParam("classId") Integer classId,
			@RequestParam("courseEvaluationId") Integer evaluationId) {
		try {
			SessionHandler.getSessionUser(request);
			SessionHandler.checkSessionUserAccess(request);
			CourseEvaluation courseEvaluation = new CourseEvaluation();
			courseEvaluation = this.courseEvaluationManager.fetchEmployeeCoursePath(classId, evaluationId);
			return new CourseEvaluationResponse(LmsStatus.SUCCESS, courseEvaluation);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new CourseEvaluationResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new CourseEvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new CourseEvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	
	
	@GetMapping(value="courseinfo/employeeAnswer/list")
	public @ResponseBody EmployeeEvaluationResponse fetchEmployeeAnswers(HttpServletRequest request,
			@RequestParam("classId") Integer classId,
			@RequestParam("evaluationId") Integer courseEvaluationId,
			@RequestParam("courseId") Integer courseId) {
		try {
			SessionHandler.checkSessionUserAccess(request);
			User user = SessionHandler.getSessionUser(request);
			EmployeeInfo employee = this.employeeInfoManager.fetchEmployeeInfoByUserId(user.getUserId());
			SessionHandler.checkSessionUserAccess(request);
			List<EmployeeEvaluation> employeeAnswerList = this.courseEvaluationManager
					.fetchEmployeeEvaluation(classId, employee.getEmployeeId(), courseEvaluationId, courseId);
			return new EmployeeEvaluationResponse(LmsStatus.SUCCESS,employeeAnswerList);
		} catch (SessionExpiredException see) {
			StackTraceHandler.printStackTrace(see);
			return new EmployeeEvaluationResponse(LmsStatus.SESSION_EXPIRED);
		} catch (DataAccessException dae) {
			StackTraceHandler.printStackTrace(dae);
			return new EmployeeEvaluationResponse(LmsStatus.QUERY_FAILED);
		} catch (Exception e) {
			StackTraceHandler.printStackTrace(e);
			return new EmployeeEvaluationResponse(LmsStatus.UNHANDLED_ERROR);
		}
	}
	/***************************************************************************************************************************/
	/* END: RESTFUL API																							 **/
	/**************************************************************************************************************************/
}
