$(function(){
	defineHttpStatus();
	defineStatus();
	defineMessages();
	defineAssignmentType();
	defineAjaxRequest();
	definePagination();
	defineDeliveryMethod();
	defineCheckedValue();
	defineQuestionType();
	defineGlobalVariables();
	defineExamType();
	defineScheduleType();
	defineComponentType();
	defineContentType();
	defineModuleCode();
	defineItemType();
	defineSourceType();
	defineExamRetake();
	defineCompletionStatus();
	defineApprovalStatus();
	defineTrainingStatus();
	defineClassRole();
	defineValidFileType();
	defineValidFileSize();
	defineViewRestrictionType();
	defineIsSelfRegister();
	defineFillInTheBlankChoiceType();
	defineSessionStatus();
	defineIsRequired();
	defineIsAnonymousSender();
	defineWithCertificate();
	defineCertificateDownloadable();
	defineCertificateType();
	defineSelfRegisterType();
	defineViewingRestrictions();
	defineAccessRestrictionType();
});

function defineHttpStatus() {
	HttpStatus = {
		SUCCESS:									200,
		SESSION_EXPIRED:							1000,
		QUERY_FAILED:								1001,
		UNHANDLED_ERROR:							1999,
		CATEGORY_CODE_EXIST:						2000,
		JOBROLE_CODE_EXIST:							2010,
		LOCATION_CODE_EXIST:						2020,
		FILE_FORMAT_IS_INVALID:						2030,
		FILE_SIZE_IS_INVALID:						2031,
		INSUFFICIENT_NO_OF_QUESTIONS:				2040,
		EXAMCODE_EXISTS:							2050,
		COURSECODE_EXISTS:							2060,
		COMPONENT_EXISTS:							2070,
		LEARNINGPATH_ALREADY_EXIST:					2080,
		CLASSSCHEDULE_HAS_CONFLICTS:				2090,
		TIME_SPENT_IS_NOT_ENOUGH:					3000};
}

function defineMessages() { 
	var tryAgainClause = "\n Please try again. \n If the problem persists, \n please contact info@lms.com.";
	
	Message = {
		SESSION_EXPIRED_MESSAGE:
			"Your session has expired due to inactivity. Please login again.",
		QUERY_FAILED_MESSAGE:
			"An error has occurred \n while connecting to the database. " + tryAgainClause,	
		UNHANDLED_ERROR_MESSAGE:
			"A server error has occurred. " + tryAgainClause,
		CATEGORY_CODE_EXIST:
			"Training Category Code already exists. Please try another one.",
		JOBROLE_CODE_EXIST:
			"Job Role Code already exists. Please try another one.",
		LOCATION_CODE_EXIST:
			"Location Code already exists. Please try another one",
		FILE_FORMAT_IS_INVALID:
			"Media file format is invalid. Please try another one.",
		FILE_SIZE_IS_INVALID:
			"File size is too large. Minimum file size is 10mb. Please try another one",
		INSUFFICIENT_NO_OF_QUESTIONS:
			"Insufficient no of available questions.",
		EXAMCODE_EXISTS:
			"Exam Code Already Exists. Please try another one.",
		COURSECODE_EXISTS:
			"Course code already exist.Please try another one.",
		COMPONENT_EXISTS:
			"Component already exists",
		LEARNINGPATH_ALREADY_EXIST:
			"Course Exam Already Exist",
		CLASSSCHEDULE_HAS_CONFLICTS:
			"You cannot join this class. You have another class that coincides with this class schedule.",
		TIME_SPENT_IS_NOT_ENOUGH:
			"You cannot finish the class yet. You haven’t spent the minimum required duration for this class"
	}
}

function defineStatus() {
	Status = {
		INACTIVE:										0,
		ACTIVE:											1
	}
}

function defineAssignmentType() {
	AssignmentType = {
		JOBROLE:										1,
		USERGROUP:										2
	}
}

function defineAjaxRequest() {
	ajax = {
		/**
		 * Description: Generalized Ajax Request For List
		 * @Param url 
		 * @return
		 */	
		fetch : function(url) {
			return $.ajax({
				url: url,
				type: "GET",
				dataType: "json",
				contentType: "application/json",
				mimeType: "application/json"
			}).done(function(response) {
				switch(response.status) {
					case HttpStatus.SESSION_EXPIRED:
						alertify.error(Message.SESSION_EXPIRED_MESSAGE);
						break;
					case HttpStatus.QUERY_FAILED:
						alertify.error(Message.QUERY_FAILED_MESSAGE);
						break;
					case HttpStatus.UNHANDLED_ERROR:
						alertify.error(Message.UNHANDLED_ERROR_MESSAGE);
						break;
				}
			});
		},
		/**
		 * Description: Generalized Ajax Request For Object
		 * @Param url
		 * @Param id
		 * @return
		 */
		fetchObj : function(url,id) {
			return $.ajax({
				url: url + id,
				type: "GET",
				dataType: "json",
				contentType: "application/json",
				mimeType: "application/json"
			}).done(function(response) {
				switch(response.status) {
					case HttpStatus.SESSION_EXPIRED:
						alertify.error(Message.SESSION_EXPIRED_MESSAGE);
						break;
					case HttpStatus.QUERY_FAILED:
						alertify.error(Message.QUERY_FAILED_MESSAGE);
						break;
					case HttpStatus.UNHANDLED_ERROR:
						alertify.error(Message.UNHANDLED_ERROR_MESSAGE);
						break;
				}
			});
		},
		/**
		 * Description: ajax request with custom data
		 * @Param
		 * @url
		 * @data
		 */
		fetchWithData : function(url,data) {
			return $.ajax({
				url: url,
				type: "GET",
				data: data,
				dataType: "json",
				contentType: "application/json",
				mimeType: "application/json"
			}).done(function(response) {
				switch(response.status) {
					case HttpStatus.SESSION_EXPIRED:
						alertify.error(Message.SESSION_EXPIRED_MESSAGE);
						break;
					case HttpStatus.QUERY_FAILED:
						alertify.error(Message.QUERY_FAILED_MESSAGE);
						break;
					case HttpStatus.UNHANDLED_ERROR:
						alertify.error(Message.UNHANDLED_ERROR_MESSAGE);
						break;
				}
			});
		},
		update : function(url,id,data) {
			return $.ajax({
				url: url + id,
				type: "PUT",
				data: JSON.stringify(data),
				dataType: "json",
				contentType: "application/json",
				mimeType: "application/json"
			}).done(function(response) {
				switch(response.status) {
					case HttpStatus.SESSION_EXPIRED:
						alertify.error(Message.SESSION_EXPIRED_MESSAGE);
						break;
					case HttpStatus.QUERY_FAILED:
						alertify.error(Message.QUERY_FAILED_MESSAGE);
						break;
					case HttpStatus.UNHANDLED_ERROR:
						alertify.error(Message.UNHANDLED_ERROR_MESSAGE);
						break;
				}
			});
		},
		remove :function(url,id) {
			return $.ajax({
				url: url + id,
				type: "DELETE",
				dataType: "json",
				contentType: "application/json",
				mimeType: "application/json"
			}).done(function(response) {
				switch(response.status) {
					case HttpStatus.SUCCESS:
						alertify.success("Successfully Removed!");
						break;
					case HttpStatus.SESSION_EXPIRED:
						alertify.error(Message.SESSION_EXPIRED_MESSAGE);
						break;
					case HttpStatus.QUERY_FAILED:
						alertify.error(Message.QUERY_FAILED_MESSAGE);
						break;
					case HttpStatus.UNHANDLED_ERROR:
						alertify.error(Message.UNHANDLED_ERROR_MESSAGE);
						break;
				}
			});
		},
		create : function(url,object) {
			return $.ajax({
				url: url,
				type: "POST",
				dataType: "json",
				data: JSON.stringify(object),
				contentType: "application/json",
				mimeType: "application/json"
			}).done(function(response) {
				switch(response.status) {
					case HttpStatus.SUCCESS:
						break;
					case HttpStatus.SESSION_EXPIRED:
						alertify.error(Message.SESSION_EXPIRED_MESSAGE);
						break;
					case HttpStatus.QUERY_FAILED:
						alertify.error(Message.QUERY_FAILED_MESSAGE);
						break;
					case HttpStatus.UNHANDLED_ERROR:
						alertify.error(Message.UNHANDLED_ERROR_MESSAGE);
						break;
				}
			});
		},
		/**
		 * Default ajax file upload
		 * @Parameters
		 * @url
		 * @dom
		 */
		upload : function(url,dom) {
			return $.ajax({
				url: url,
		        type: "POST",
		        data: new FormData(document.getElementById(dom)),
		        enctype: 'multipart/form-data',
		        processData: false,
		        contentType: false
			}).done(function(response){
				switch(response.status) {
					case HttpStatus.SESSION_EXPIRED:
						alertify.error(Message.SESSION_EXPIRED_MESSAGE);
						break;
					case HttpStatus.QUERY_FAILED:
						alertify.error(Message.QUERY_FAILED_MESSAGE);
						break;
					case HttpStatus.UNHANDLED_ERROR:
						alertify.error(Message.UNHANDLED_ERROR_MESSAGE);
						break;
				}
			});
		},
		/**
		 * Still Not Finalize
		 */
		uploadWithProgressHandler : function(url,dom) {
			return $.ajax({
				url: url,
		        type: "POST",
		        data: new FormData(document.getElementById(dom)),
		        enctype: 'multipart/form-data',
		        processData: false,
		        contentType: false,
		        xhr : function() {
		        	 var xhr = $.ajaxSettings.xhr();
		        	 xhr.upload.onloadstart = function(event) {
			        		var percentComplete = Math.round(event.loaded / event.total * 40);
			               	$("#progressBar").css("width",percentComplete + "%");
			               	$("#percentValue").html(percentComplete+"%");
			             };
		        	 xhr.upload.onprogress = function(event) {
		        		var percentComplete = Math.round(event.loaded / event.total * 70);
		               	$("#progressBar").css("width",percentComplete + "%");
		               	$("#percentValue").html(percentComplete+"%");
		             };
		             xhr.upload.onloadend = function(event) {
			        		var percentComplete = Math.round(event.loaded / event.total * 90);
			               	$("#progressBar").css("width",percentComplete + "%");
			               	$("#percentValue").html(percentComplete+"%");
			             };
		             return xhr;
		        	}
			}).success(function(response) {
				switch(response.status) {
					case HttpStatus.SUCCESS:
						$("#progressBar").css("width","100%");
		               	$("#percentValue").html("100%");
						break;
				}
			}).done(function(response){
				switch(response.status) {
					case HttpStatus.SESSION_EXPIRED:
						alertify.error(Message.SESSION_EXPIRED_MESSAGE);
						break;
					case HttpStatus.QUERY_FAILED:
						alertify.error(Message.QUERY_FAILED_MESSAGE);
						break;
					case HttpStatus.UNHANDLED_ERROR:
						alertify.error(Message.UNHANDLED_ERROR_MESSAGE);
						break;
				}
			});
		},
		removeWithData : function(url,data) {
			return $.ajax({
				url: url,
		        type: "DELETE",
		        data: JSON.stringify(data),
		        contentType: "application/json",
				mimeType: "application/json"
			}).done(function(response){
				switch(response.status) {
					case HttpStatus.SESSION_EXPIRED:
						alertify.error(Message.SESSION_EXPIRED_MESSAGE);
						break;
					case HttpStatus.QUERY_FAILED:
						alertify.error(Message.QUERY_FAILED_MESSAGE);
						break;
					case HttpStatus.UNHANDLED_ERROR:
						alertify.error(Message.UNHANDLED_ERROR_MESSAGE);
						break;
				}
			});
		},
		customUpdate : function(url,data) {
			return $.ajax({
				url: url,
				type: "PUT",
				data: JSON.stringify(data),
				dataType: "json",
				contentType: "application/json",
				mimeType: "application/json"
			}).done(function(response) {
				switch(response.status) {
					case HttpStatus.SESSION_EXPIRED:
						alertify.error(Message.SESSION_EXPIRED_MESSAGE);
						break;
					case HttpStatus.QUERY_FAILED:
						alertify.error(Message.QUERY_FAILED_MESSAGE);
						break;
					case HttpStatus.UNHANDLED_ERROR:
						alertify.error(Message.UNHANDLED_ERROR_MESSAGE);
						break;
				}
			});
		},
	}
}

function defineGlobalVariables() {
	//Used For Dynamic population of html
	globalList = [];
	//Used As a counter for limiting dynamic population
	count = 0;
}

/**
 * Description: Initializes table pagination. 
 * Response must include an integer named totalRecords.
 * 	
 * @param paginationProperties = {
 * 	paginationDom = e.g Id of pagination
 *  tableBodyDom = e.g Id of Table Body
 * 	url = ajax request url
 * 	locator = response data Source
 *  ajax = ajax request for searching of data
 *  className = custom class
 *  pageSize = desired page size
 *  functionName = method Name to be called when executed
 * }
 */
function definePagination() {
	pagination = {
		/**
		 * Description: Initiates pagination table with no custom ajax request data
		 */	
		initiate : function(paginationProperties) {
			$(paginationProperties.paginationDom).pagination({     
		        dataSource: paginationProperties.url,
		        locator: paginationProperties.locator,
		        totalNumberLocator: function(response) {            
		            return response.totalRecords;
		        },
		        ajax: paginationProperties.ajax,
		        className: paginationProperties.className,
		        pageSize: paginationProperties.pageSize,     
		        callback: function(data) {                      
		            $(paginationProperties.tableBodyDom).html("");
		            globalList = data;
		            var functionName = eval()
		            _.each(data, function(object) {
		            	var functionName = paginationProperties.functionName + "(object)";
		            	eval(functionName);
		            });
		        }
		    });}
	}
}

function defineDeliveryMethod() {
	DeliveryMethod = {
		INDIVIDUAL:										1,
		MODULAR:										2,
		CLASSTRAINING:									3,
		//Delivery Method List
		LIST: 	[{name:"CBT (Individual)",	value:1},
				{name:"CBT (Modular)",		value:2},
				{name:"Classroom Training",	value:3}]
	}
}

function defineCheckedValue() {
	CheckedValue = {
		CHECKED:										1,
		UNCHECKED:										0
	}
}

function defineQuestionType() {
	QuestionType = {
		MULTIPLE_CHOICE:								1,
		FILL_IN_THE_BLANK:								2,
		TRUE_OR_FALSE:									3,
		ESSAY:											4,
		IDENTIFICATION:									5,
		ENUMERATION:									6,
		ORDERING:										7,
		MATCHING:										8,
		FORM:											9,
		ASSESSMENT:										10
	}
}

function defineExamType() {
	ExamTypes = [{
		description:									"Final Exam",
		id:												1
	},{
		description:									"Quiz",
		id:												2
	},{
		description:									"Assignment",
		id:												3
	},{
		description:									"Regular Exam",
		id:												4
	},{
		description:									"Checkout",
		id:												5
	},{
		description:									"Exercise",
		id:												6
	}]
}

function defineScheduleType() {
	var scheduleTypeList = [{
		id:												1,
		description:									"BLOCK"
	},{
		id:												2,
		description:									"SET"
	},{
		id:												3,
		description:									"SERIES"
	}]
	ScheduleType = {
		BLOCK:											1,
		SET:											2,
		SERIES:											3,
		LIST:											scheduleTypeList
	}
}

function defineComponentType() {
	var componentTypesList = ["Final Exam","Regular Exam","Exercise","Quiz",
			"Attendance","Checkout","Assignment","Others"];
	ComponentType = {
		FINAL_EXAM:										"Final Exam",
		REGULAR_EXAM:									"Regular Exam",
		EXERCISE:										"Exercise",
		QUIZ:											"Quiz",
		ATTENDANCE:										"Attendance",
		OTHERS:											"Others",
		CHECKOUT:										"Checkout",
		ASSIGNMENT:										"Assignment",
		LIST:											componentTypesList
	}
}

function defineContentType() {
	var contentTypeList = [{
		id:												1,
		description:									"Document"
		},{
		id:												2,
		description:									"Video"
		},{
		id:												3,
		description:									"Audio"
		},{
		id:												4,
		description:									"Image"
		},{
		id:												6,
		description:									"URL"
		}];
	ContentType = {
		DOCUMENT:										1,
		VIDEO:											2,
		AUDIO:											3,
		IMAGE:											4,
		FLASH:											5,
		URL:											6,
		COURSE_MATERIAL:								7,
		LIST:											contentTypeList
	}
}

function defineModuleCode() {
	Module = {
		COURSEEXAM:										"courseexam",
		CLASSEXAM:										"classexam"	
	}
}

function defineItemType() {
	ItemType = {
		EXAM:											"EXAM",
		MATERIAL:										"MATERIAL"
	}
}

function defineSourceType() {
	var sourceList = [{
		id:												1,
		description:									"Course Examination Library"
	},{
		id:												2,
		description:									"List of available exams"
	}]
	SourceType = {
		COURSE_EXAMINATION:								1,
		AVAILABLE_EXAMS:								2,
		LIST:											sourceList
	}
}

function defineExamRetake() {
	ExamRetake = {
		ONLY_WHEN_FAILED:								1,
		EVEN_IF_PASSED:									2
	}
}

function defineCompletionStatus() {
	CompletionStatus = {
		CANCELLED:										0,
		COMPLETED:										1,
		ONGOING:										2					
	}
}

function defineApprovalStatus() {
	ApprovalStatus = {
		ALL_APPROVAL_STATUS:							0,
		APPROVED:										1,
		CANCELLED:										2,
		ONGOING:										3,
		DISAPPROVED:									4
	}
}

function defineTrainingStatus() {
	var trainingStatusList = [{
		id:												1,
		description:									"Not Yet Started"
	},{
		id:												2,
		description:									"Completed"
	},{
		id:												3,
		description:									"Dismissed"
	},{
		id:												4,
		description:									"Cancelled"
	},{
		id:												5,
		description:									"Incomplete"
	}];
	TrainingStatus = {
		NOT_YET_STARTED:								1,
		COMPLETED:										2,
		DISMISSED:										3,
		CANCELLED:										4,
		INCOMPLETE:										5,
		LIST:											trainingStatusList
	}
}

function defineClassRole() {
	Role = {
		TRAINER:										1,
		TRAINEE:										2,
		SELF_REGISTERED:								3
	}
}

function defineValidFileType() {
	ValidFileType = {
		IMAGE : ["image/png", "image/jpeg", "image/gif","image/bmp","image/tiff"],
		VIDEO : ["video/avi","video/mpeg","video/mpeg","application/octet-stream",
			"video/mp4","video/ogg","video/x-ms-wmv","application/ogg","video/x-matroska",
			"video/quicktime","	video/x-flv"],
		DOCUMENT : [
			//Power point formats
			"application/mspowerpoint","application/vnd.ms-powerpoint","application/powerpoint",
			"application/x-mspowerpoint","application/mspowerpoint", "application/vnd.oasis.opendocument.presentation",
			"application/vnd.oasis.opendocument.text","application/vnd.openxmlformats-officedocument.presentationml.presentation",
			//Spread sheets
			"application/vnd.oasis.opendocument.spreadsheet",
			"application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
			//Pdf formats
			"application/pdf",
			//MS WORD DOCUMENT
			"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document"],
		AUDIO : ["audio/wav","audio/mpeg3","audio/mpeg","audio/mp3","audio/ogg"]
	}
}

function defineValidFileSize() {
	ValidFileSize = {
		VIDEO_SIZE_LIMIT:									524288000,
		AUDIO_SIZE_LIMIT:									10485760,
		IMAGE_SIZE_LIMIT:									10485760,
		DOCUMENT_SIZE_LIMIT:								15728640
	}
}

function defineViewRestrictionType() {
	ViewRestrictionType = {
		ACCESSIBLE_BY_TRAINING_DURATION:					1,
		ACCESSIBLE_INDEFINITELY:							2,
		ACCESSIBLE_BY_SPECIFIED_DATE:						3
	}
}

function defineWithCertificate() {
	WithCertificate = {
		TRUE:												1,
		FALSE:												0
	}
}

function defineCertificateType() {
	CertificateType = {
		SYSTEMTEMPLATE:												1,
		CUSTOMTEMPLATE:												2
	}
}

function defineCertificateDownloadable() {
	CertificateDownloadable = {
		TRUE:												1,
		FALSE:												0
	}
}

function defineIsSelfRegister() {
	IsSelfRegister = {
		TRUE:												1,
		FALSE:												0
	}
}

function defineSelfRegisterType() {
	SelfRegisterType = {
		OPENINDEFINITELY:									1,
		SPECIFIEDPERIOD:									0
	}
}

function defineIsRequired() {
	IsSelfRegister = {
		TRUE:												1,
		FALSE:												2
	}
}

function defineViewingRestrictions() {
	ViewRestrictions = {
		TRAININGDURATION:										1,
		INDEFINITE:												2,
		SPECIFIEDPERIOD:										3
	}
}

function defineAccessRestrictionType() {
	AccessRestrictionType = {
		PUBLIC:												1,
		PRIVATE:											2
	}
}

function defineIsAnonymousSender() {
	IsAnonymousSender = {
		TRUE:												1,
		FALSE:												2
	}
}

function defineFillInTheBlankChoiceType() {
	ChoiceType={
		MULTIPLE_CHOICE:									1,
		DIRECT_ANSWER:										2
	}
}

function defineSessionStatus() {
	SessionStatus = {
		IN_PROGRESS:										2,
		FINISHED:											1,
		NOT_YET_TAKEN:										0	
	}
}