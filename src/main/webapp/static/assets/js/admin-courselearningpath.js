$(function() {
	defineVariables();
	initPageSettings();
	initSectionDropdown();
	initContentTypeDropdown();
	initCourseMaterialsDropdown();
	initSourceDropdown();
	initExamTypeDropdown();
	initLearningPathTbl();
	attachListenerToAddSectionBtn();
	attachListenerToContentTypeDropdown();
	attachListenerToMaterialsDropdown();
	attachListenerToBtnCancelSection();
	attachListenerToSaveContentBtn();
	attachListenerToSaveExamBtn();
	attachListenerToBtnCancelExam();
	attachListenerToSectionLinkUp();
	attachListenerToSectionLinkDown();
	attachListenerToSubItemLinkUp();
	attachListenerToSubItemLinkDown();
	attachListenerToEditSectionBtn();
	attachListenerToContentCancelBtn();
});
function defineVariables() {
	courseMaterials = [];
	learningPathSectionList=[];
	learningPathList=[];
}

function initPageSettings() {
	initPreloader("hide");
	$("#addContentModal").removeAttr("tabindex");
}

function initSectionDropdown() {
	var courseId=$("#courseId").val();
	$.when(ajax.fetch("courseinfo/"+courseId+"/learningpathsections")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				populateSectionDropdown(response.learningPathSections);
				break;
		}
	});
}

function populateSectionDropdown(learningPathSections) {
	$("#sectionDropdown,#examSectionDropdown").html("");
	$("#sectionDropdown").append($("<option/>").attr("value","0").prop({"disabled":true,"selected":true})
			.html("Choose a section"));
	$("#examSectionDropdown").append($("<option/>").attr("value","0").prop({"disabled":true,"selected":true})
			.html("Choose a section"));
	_.each(learningPathSections,function(section) {
		$("#sectionDropdown,#examSectionDropdown").append($("<option/>").attr("value",section.learningPathSectionId)
				.html(section.sectionTitle));
		$("#sectionDropdown,#examSectionDropdown").trigger("change");
	});
}

function initContentTypeDropdown() {
	_.each(ContentType.LIST,function(contentType) {
		$("#contentTypeDropdown").append($("<option/>").attr("value",contentType.id)
				.html(contentType.description));
	});
	$("#contentTypeDropdown").append($("<option/>").attr("value",ContentType.COURSE_MATERIAL)
			.html("From Course Learning Material Library"));
}

function initCourseMaterialsDropdown() {
	var courseId = $("#courseId").val();
	$.when(ajax.fetchWithData("courseinfo/"+courseId+"/coursematerials/viewstatus",
			{viewStatus:Status.ACTIVE})).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				courseMaterials = response.courseMaterials;
				populateCourseMaterialsDropdown(response.courseMaterials);
				break;
		}
	});
}

function populateCourseMaterialsDropdown(courseMaterials) {
	_.each(courseMaterials,function(courseMaterial) {
		$("#courseMaterialsDropdown").append($("<option/>").attr("value",courseMaterial.courseMaterialId)
			.html(courseMaterial.fileLabel));
	})
}

function initSourceDropdown() {
	_.each(SourceType.LIST,function(sourceType) {
		$("#sourceDropdown").append($("<option/>").attr("value",sourceType.id)
			.html(sourceType.description)).bind("change",function() {
				var examType=$("#examTypeDropdown").val();
				var sourceType=$(this).val();
				if(sourceType!=null && examType!=null) {
					if(sourceType==SourceType.COURSE_EXAMINATION) {
						initExamTitleDropdown(examType,null);
					} 
					if(sourceType==SourceType.AVAILABLE_EXAMS) {
						initExamTitleDropdown(examType,Status.ACTIVE);
					}
				}
			});
	});
}

function initExamTypeDropdown() {
	$("#examTypeDropdown").html("");
	$("#examTypeDropdown").append($("<option/>").attr("value","0").prop({"disabled":true,"selected":true})
			.html("Choose an exam type"));
	_.each(ExamTypes,function(examType) {
		$("#examTypeDropdown").append($("<option/>").attr("value",examType.id)
			.html(examType.description)).bind("change",function() {
				var examType=$(this).val();
				var sourceType=$("#sourceDropdown").val();
				if(sourceType!=null && examType!=null) {
					if(sourceType==SourceType.COURSE_EXAMINATION) {
						initExamTitleDropdown(examType,null);
					} 
					if(sourceType==SourceType.AVAILABLE_EXAMS) {
						initExamTitleDropdown(examType,Status.ACTIVE);
					}
				}
			});
	});
}

function initExamTitleDropdown(examType,status) {
	var courseId = $("#courseId").val();
	if(status != null) {
		$.when(ajax.fetchWithData("examinfos/active",{courseId:courseId,status:status,examType:examType}))
			.done(function(response) {
				switch(response.status) {
					case HttpStatus.SUCCESS:
						populateExamTitleDropdown(response.examInfos);
						break;
				}
		});
	} else {
		$.when(ajax.fetchWithData("courseinfo/"+courseId+"/courseexams",{examType:examType}))
			.done(function(response) {
				switch(response.status) {
					case HttpStatus.SUCCESS:
						populateCourseExamTitleDropdown(response.courseExams);
						break;
				}
		});
	}
}

function populateExamTitleDropdown(examInfos) {
	$("#examTitleDropdown").html("");
	_.each(examInfos,function(examInfo){
		$("#examTitleDropdown").append($("<option/>").attr("value",examInfo.examId)
				.html(examInfo.title));
	});
	$("#examTitleDropdown").trigger("change");
}

function populateCourseExamTitleDropdown(courseExams) {
	$("#examTitleDropdown").html("");
	$("#examTitleDropdown").append($("<option/>").attr("value","0").prop({"disabled":true,"selected":true})
			.html("Choose an exam title"));
	_.each(courseExams,function(courseExam){
		$("#examTitleDropdown").append($("<option/>").attr("value",courseExam.courseExamId)
				.html(courseExam.title));
	});
	$("#examTitleDropdown").trigger("change");
}

function initLearningPathTbl() {
	$.when(initLearningPathSections()).then(function(status) {
		initLearningPathSubItems();
	});
}

function initLearningPathSections() {
	var result = $.Deferred();
	$.when(fetchLearningPathSection()).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				populateLearningPathSection(response.learningPathSections);
				learningPathSectionList = response.learningPathSections;
				result.resolve("Success");
				break;
		}
	});
	return result.promise();
}

function fetchLearningPathSection() {
	var courseId=$("#courseId").val();
	return $.ajax({
		url: "courseinfo/"+courseId+"/learningpathsections",
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
}

function populateLearningPathSection(learningPathSections) {
	$("#learningPathCon").html("");
	_.each(learningPathSections,function(learningPathSection) {
		$("#learningPathCon").append($("<div/>").css("margin-top","20px")
			.append($("<table/>").addClass("highlight striped")
				.append($("<thead>")
					.append($("<tr/>")
						.append($("<th/>").addClass("large").attr("colspan","2").css("text-align","left")
							.html(learningPathSection.sectionTitle))
						.append($("<th/>").addClass("min")
							.append($("<a/>").addClass("sectionDown").attr({"href":"#",
								"data-id":learningPathSection.learningPathSectionId,
								"data-orderno":learningPathSection.sectionOrderNo,
								"data-maxorderno":learningPathSection.maxSectionOrderNo})
								.append($("<i/>").addClass("material-icons").css("margin-top","6px")
									.html("arrow_downward")))
							.append($("<a/>").addClass("sectionUp").attr({"href":"#",
								"data-id":learningPathSection.learningPathSectionId,
								"data-orderno":learningPathSection.sectionOrderNo})
								.append($("<i/>").addClass("material-icons").css("margin-top","6px")
									.html("arrow_upward")))
							.append($("<a/>").addClass("modal-trigger").attr({
								"href":"#!","data-id":learningPathSection.learningPathSectionId,
								"data-target":"editSectionModal"})
								.bind("click",function(e) {
									populateSectionModal($(this).data("id"));
								e.preventDefault();
							}).append($("<i/>").addClass("material-icons").css("margin-top","6px")
									.html("edit")))
							.append($("<a/>").attr({"href":"#!","data-id":learningPathSection.learningPathSectionId})
								.bind("click",function(e) {
									e.preventDefault();
									var message = [];
									message.push("Are you sure you want to remove this section header? \n ")
									message.push("Contents and exams under this section will be deleted also.")
									alertify.confirm("Delete Confirmation",message.join(""),
										function() {removeSection(learningPathSection.learningPathSectionId);},null)
										.set("labels", {ok:"DELETE", cancel:"CANCEL"});
							}).append($("<i/>").addClass("material-icons").css("margin-top","6px").html("delete"))))))
			.append($("<tbody>").attr("id","section_"+learningPathSection.learningPathSectionId))));
	});
}

function removeSection(learningPathSectionId) {
	var courseId=$("#courseId").val();
	$.when(ajax.remove("courseinfo/"+courseId+"/learningpathsections/",learningPathSectionId))
		.done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				reArrangeSectionOrderNo(learningPathSectionId,learningPathSectionList);
				initSectionDropdown();
				break;
		}
	});
	
}

function reArrangeSectionOrderNo(learningPathSectionId,learningPathSectionList) {
	var ctr=1;
	var tempSectionArray=[];
	_.each(learningPathSectionList,function(learningPathSection) {
		if(learningPathSection.learningPathSectionId!=learningPathSectionId) {
			learningPathSection.sectionOrderNo=ctr;
			tempSectionArray.push(learningPathSection);
			ctr++;
		}
	});
	var courseInfo = {};
	courseInfo.learningPathSections=tempSectionArray;
	updateLearningPathSectionOrder(courseInfo);
}

function populateSectionModal(learningPathSectionId) {
	var learningPathSection = getLearningPathSectionObj(learningPathSectionId);
	$("#editSectionTitle").val(learningPathSection.sectionTitle);
	$("#editSectionDescription").val(learningPathSection.description);
	$("#learningPathSectionId").val(learningPathSection.learningPathSectionId);
}

function getLearningPathSectionObj(learningPathSectionId) {
	var tempSectionObj = {};
	_.each(learningPathSectionList,function(learningPathSection) {
		if(learningPathSection.learningPathSectionId==learningPathSectionId) {
			tempSectionObj = learningPathSection;
		}
	});
	return tempSectionObj;
}

function initLearningPathSubItems() {
	$.when(fetchLearningPathSubItems()).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				learningPathList=response.learningPaths;
				populateLearningPath(response.learningPaths);
				break;
		}
	});
}

function fetchLearningPathSubItems() {
	var courseId=$("#courseId").val();
	return $.ajax({
		url: "courseinfo/"+courseId+"/learningpaths",
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
}

function populateLearningPath(learningPaths) {
	_.each(learningPaths,function(learningPath) {
			$("#section_"+learningPath.learningPathSectionId)
			.append($("<tr/>")
				.append($("<td/>").addClass("min").css("text-align","left").html(
					(learningPath.itemType==ItemType.EXAM)?learningPath.title:learningPath.fileLabel))
				.append($("<td/>").html(
					(learningPath.itemType==ItemType.EXAM)?
						getExamTypeDesc(learningPath.examType):getContentTypeDesc(learningPath.contentType)))
				.append($("<td/>")
					.append($("<a/>").addClass("subItemDown").attr({"href":"#!",
						"data-id":learningPath.learningPathId,"data-orderno":learningPath.subOrderNo,
						"data-maxorderno":learningPath.maxOrderNo})
						.append($("<i/>").addClass("material-icons").css("margin-top","6px").html("arrow_downward")))
					.append($("<a/>").addClass("subItemUp").attr({"href":"#!",
						"data-id":learningPath.learningPathId,"data-orderno":learningPath.subOrderNo,
						"data-maxorderno":learningPath.maxOrderNo})
						.append($("<i/>").addClass("material-icons").css("margin-top","6px").html("arrow_upward")))
					.append($("<a/>").attr({"href":"#!","data-id":learningPath.learningPathId})
							.bind("click",function(e) {
								e.preventDefault();
								removeLearningPathItem(learningPath.learningPathId);
							})
						.append($("<i/>").addClass("material-icons").css("margin-top","6px").html("delete")))));
	});
}

function removeLearningPathItem(learningPathId) {
	var courseId=$("#courseId").val();
	$.when(ajax.remove("courseinfo/"+courseId+"/learningpath/",learningPathId)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				updateLearningPathItemOrderNo(learningPathId,learningPathList);
				break;
		}
	});
}

function updateLearningPathItemOrderNo(learningPathId,learningPathList) {
	var ctr=1;
	var tempSubItemArray=[];
	_.each(learningPathList,function(learningPath) {
		if(learningPath.learningPathId!=learningPathId) {
			tempSubItemArray.subOrderNo=ctr;
			tempSubItemArray.push(learningPath);
			ctr++;
		}
	});
	var courseInfo = {};
	courseInfo.learningPaths=tempSubItemArray;
	updateLearningPathSubOrderNo(courseInfo);
}

function getLearningPathObj(learningPathId) {
	var tempLearningPathObj={};
	_.each(learningPathList,function(learningPath) {
		if(learningPath.learningPathId==learningPathId) {
			tempLearningPathObj == learningPath;
		}
	});
	return tempLearningPathObj;
}

function getExamTypeDesc(examTypeValue) {
	var examTypeDesc="";
	_.each(ExamTypes,function(examType) {
		if(examType.id==examTypeValue) {
			examTypeDesc=examType.description;
		}
	});
	return examTypeDesc;
}

function getContentTypeDesc(contentTypeValue) {
	var contentTypeDesc="";
	_.each(ContentType.LIST,function(contentType) {
		if(contentType.id==contentTypeValue) {
			contentTypeDesc=contentType.description;
		}
	});
	return contentTypeDesc;
}

function attachListenerToAddSectionBtn() {
	$("#saveSectionBtn").on("click",function() {
		if(validateSectionModalFields()) {
			var learningPathSection=getLearningPathSectionData();
			createLearningPathSection(learningPathSection);
			clearAddSectionModalFields();
		}
	});
}

function validateSectionModalFields() {
	var result=true;
	if($("#sectionTitle").val()=="") {
		alertify.warning("Section title cannot be empty.")
		result=false;
	}
	return result;
}

function getLearningPathSectionData() {
	var learningPathSection={};
	learningPathSection.sectionTitle=$("#sectionTitle").val();
	learningPathSection.description=$("#sectionDescription").val();
	learningPathSection.courseId = $("#courseId").val();
	return learningPathSection;
}

function createLearningPathSection(learningPathSection) {
	var courseId=$("#courseId").val();
	$.when(ajax.create("courseinfo/"+courseId+"/learningpathsection",learningPathSection)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully created!");
				clearAddSectionModalFields();
				$("#addSectionModal").modal("close");
				initSectionDropdown();
				initLearningPathTbl();
				break;
		}
	});
}

function clearAddSectionModalFields() {
	$("#sectionTitle").val("");
	$("#sectionDescription").val("");
} 

function attachListenerToContentTypeDropdown() {
	$("#contentTypeDropdown").on("change",function() {
		var contentType=$(this).val();
		$("#fileLabel").val("");
		$("#fileName").val("");
		changeDropdownDisplayed(contentType);
	});
}

function changeDropdownDisplayed(contentType) {
	if(contentType==ContentType.COURSE_MATERIAL) {
		$("#courseMaterialsDropdownCon").show();
		$("#urlCon").hide();
		$("#mediaFileUploadCon").hide();
		$("#fileLabelCon").hide();
	} else if(contentType==ContentType.URL) {
		$("#courseMaterialsDropdownCon").hide();
		$("#urlCon").show();
		$("#mediaFileUploadCon").hide();
	} else {
		$("#urlCon").hide();
		$("#courseMaterialsDropdownCon").hide();
		$("#mediaFileUploadCon").show();
		$("#fileLabelCon").show();
	}
}

function attachListenerToMaterialsDropdown() {
	$("#courseMaterialsDropdown").on("change",function() {
		var courseMaterial = getCourseMaterialData($(this).val());
		if(courseMaterial.contentType==ContentType.URL) {
			$("#urlCon").show();
			$("#mediaFileUpload").hide();
			$("#urlTxtBox").val(courseMaterial.contentUrl);
			$("#fileLabel").val(courseMaterial.fileLabel);
		} else {
			$("#urlCon").hide();
			$("#mediaFileUpload").show();
			$("#fileLabel").val(courseMaterial.fileLabel);
			$("#fileName").val(courseMaterial.fileName);
		}
	});
}

function getCourseMaterialData(courseMaterialId) {
	var tempCourseMaterial = {};
	_.each(courseMaterials,function(courseMaterial) {
		if(courseMaterial.courseMaterialId==courseMaterialId) {
			tempCourseMaterial=courseMaterial;
		}
	});
	return tempCourseMaterial;
}

function attachListenerToBtnCancelSection() {
	$("#cancelSectionBtn").on("click",function() {
		clearAddSectionModalFields();
	});
}

function attachListenerToSaveContentBtn() {
	$("#saveContentBtn").on("click",function() {
		handleRefreshEvent();
		var courseMaterial = getCourseMaterialContentData();
		if(validateCourseMaterialFields()) {
			if(courseMaterial.contentType==ContentType.COURSE_MATERIAL) {
				createLearningPathCourseMaterial(courseMaterial);
			} else if(courseMaterial.contentType==ContentType.URL) {
				courseMaterial.itemType=ItemType.MATERIAL;
				createCourseMaterialUrl(courseMaterial);
			} else {
				createCourseMaterialMedia(courseMaterial);
			}
		}
	});
}

function createLearningPathCourseMaterial(courseMaterial) {
	courseMaterial.courseMaterialId=$("#courseMaterialsDropdown").val();
	courseMaterial.itemType=ItemType.MATERIAL;
	createLearningPath(courseMaterial);
	setTimeout(function(){$("#addContentModal").modal("close");},1000);
	alertify.success("Successfully created!");
	clearAddContentModalFields();
}

function createCourseMaterialUrl(courseMaterial) {
	$.when(ajax.create("courseinfo/coursematerial/contenturl",courseMaterial)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				$(window).unbind('beforeunload');
				courseMaterial.courseMaterialId=response.courseMaterialId;
				createLearningPath(courseMaterial);
				clearAddContentModalFields();
				alertify.success("Successfully created!");
				setTimeout(function(){$("#addContentModal").modal("close");},1000);
				break;
		}
	});
}

function createCourseMaterialMedia(courseMaterial) {
	var courseId=$("#courseId").val();
	initPreloader("show");
	$.when(ajax.uploadWithProgressHandler("courseinfo/"+courseId+"/coursematerial/file","contentUploadFrm")
		.done(function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					courseMaterial.courseMaterialId=response.courseMaterialId;
					updateCourseMaterial(courseMaterial);
					initPreloader("hide");
					break;
			}
	}));
}

function updateCourseMaterial(courseMaterial) {
	$.when(ajax.customUpdate(
		"courseinfo/"+courseMaterial.courseId+"/coursematerial/"+courseMaterial.courseMaterialId,courseMaterial)
			.done(function(response) {
				switch(response.status) {
					case HttpStatus.SUCCESS:
						courseMaterial.itemType=ItemType.MATERIAL;
						createLearningPath(courseMaterial);
						alertify.success("Successfully created");
						setTimeout(function(){
							initPreloader("hide");
							$("#addContentModal").modal("close");},2000);
						$(window).unbind('beforeunload');
						clearAddContentModalFields();
						break;
				}
	}));
}
//
//function uploadMediaFile(courseMaterialId) {
//	initPreloader("show");
//	$.when(ajax.uploadWithProgressHandler("courseinfo/coursematerial/"+courseMaterialId+"/mediaFile","contentUploadFrm"))
//		.done(function(response) {
//		switch(response.status) {
//			case HttpStatus.SUCCESS:
//				alertify.success("Successfully created");
//				setTimeout(function(){
//					initPreloader("hide");
//					$("#addContentModal").modal("close");},2000);
//				$(window).unbind('beforeunload');
//				clearAddContentModalFields();
//				break;
//			case HttpStatus.FILE_FORMAT_IS_INVALID:
//				initPreloader("hide");
//				alertify.warning("File format is invalid. Please try another one.");
//				break;
//			case HttpStatus.FILE_SIZE_IS_INVALID:
//				initPreloader("hide");
//				alertify.warning("File size is invalid.");
//				break;
//		}
//	});
//}

function validateCourseMaterialFields() {
	var result = true;
	if($("#sectionDropdown").val()==null) {
		alertify.warning("Please choose a section");
		result = false;
	}
	if($("#contentTypeDropdown").val()==null) {
		alertify.warning("Please choose a content type.");
		result = false;
	}
	if($("#contentTypeDropdown").val()!=ContentType.URL && $("#fileName").val()=="") {
		alertify.warning("Please choose a file to upload.");
		result = false;
	}
	if($("#contentTypeDropdown").val()==ContentType.URL && $("#urlTxtBox").val()=="") {
		alertify.warning("Please specify a url.");
		result = false;
	}
	if($("#fileLabel").val() == "") {
		alertify.warning("Please specify a file label.");
		result = false;
	}
	if($("#contentTypeDropdown").val()==ContentType.COURSE_MATERIAL && $("#courseMaterialsDropdown").val()==null) {
		alertify.warning("Please choose a course material.");
		result = false;
	}
	if($("#contentTypeDropdown").val()!=ContentType.COURSE_MATERIAL &&
		$("#contentTypeDropdown").val()!=ContentType.URL) {
		if(!validateFileType("#mediaFileUpload",getTargetFileType($("#contentTypeDropdown").val()))) {
			alertify.warning("Invalid file type. Please try another one");
			result=false;
		}
	}
	if($("#contentTypeDropdown").val()!=ContentType.COURSE_MATERIAL &&
		$("#contentTypeDropdown").val()!=ContentType.URL) {
		if(!validateFileSize("#mediaFileUpload",getTargetFileType($("#contentTypeDropdown").val()))) {
			result=false;
		}
	}
	return result;
}

function getTargetFileType(contentType) {
	var targetFileType="";
	if(contentType==ContentType.DOCUMENT) {
		targetFileType="DOCUMENT";
	} else if(contentType==ContentType.IMAGE) {
		targetFileType="IMAGE";
	} else if(contentType==ContentType.VIDEO) {
		targetFileType="VIDEO";
	} else if(contentType==ContentType.AUDIO) {
		targetFileType="AUDIO";
	}
	return targetFileType;
}


function createLearningPath(learningPath) {
	$.when(ajax.create("courseinfo/learningpath",learningPath)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				$("#addExamModal").modal("close");
				clearExamModalFields();
				initLearningPathTbl();
				break;
			case HttpStatus.LEARNINGPATH_ALREADY_EXIST:
				alertify.warning(Message.LEARNINGPATH_ALREADY_EXIST);
				break;
		}
	});
}

function initPreloader(status) {
	if(status=="show") {
		$("#modalContentBtn").hide();
		$("#progressCon").show();
	} else {
		$("#modalContentBtn").show();
		$("#progressCon").hide();
	}
}

function getCourseMaterialContentData() {
	var tempCourseInfo={};
	tempCourseInfo.learningPathSectionId=$("#sectionDropdown").val();
	tempCourseInfo.contentType=$("#contentTypeDropdown").val();
	tempCourseInfo.courseMaterialId=$("#courseMaterialsDropdown").val();
	tempCourseInfo.fileLabel=$("#fileLabel").val();
	tempCourseInfo.contentUrl=$("#urlTxtBox").val();
	tempCourseInfo.courseId=$("#courseId").val();
	tempCourseInfo.viewStatus=Status.ACTIVE;
	return tempCourseInfo;
}

function clearAddContentModalFields() {
	$("#sectionDropdown").val(0).trigger("change");
	$("#contentTypeDropdown").val(0).trigger("change");;
	$("#courseMaterialsDropdown").val(0).trigger("change");;
	$("#fileLabel").val("");
	$("#fileName").val("");
	$("#urlTxtBox").val("");
}

function attachListenerToSaveExamBtn() {
	$("#saveExamBtn").on("click",function() {
		var courseExam=getCourseExamData();
		var courseId=$("#courseId").val();
		if(validateExamModalFields()) {
			if(courseExam.sourceType==SourceType.COURSE_EXAMINATION) {
				var learningPath = {};
				learningPath.courseExamId=courseExam.courseExamId;
				learningPath.learningPathSectionId=$("#examSectionDropdown").val();
				learningPath.itemType=ItemType.EXAM;
				learningPath.courseId=courseId;
				createLearningPath(learningPath);
			} else {
				$.when(ajax.create("courseinfo/"+courseId+"/courseexam",courseExam)).done(function(response) {
					switch(response.status) {
						case HttpStatus.SUCCESS:
							var learningPath = {};
							learningPath.courseExamId=response.courseExamId;
							learningPath.learningPathSectionId=$("#examSectionDropdown").val();
							learningPath.itemType=ItemType.EXAM;
							learningPath.courseId=courseId;
							createLearningPath(learningPath);
							break;
					}
				});
			}
		}
	});
}

function getCourseExamData() {
	var courseExam = {};
	courseExam.sourceType = $("#sourceDropdown").val();
	courseExam.examId=$("#examTitleDropdown").val();
	courseExam.courseExamId=$("#examTitleDropdown").val();
	courseExam.courseId=$("#courseId").val();
	return courseExam;
}

function validateExamModalFields() {
	var result = true;
	if($("#examSectionDropdown").val() == null) {
		alertify.warning("Please choose a section.");
		result = false;
	}
	if($("#examTitleDropdown").val() == null) {
		alertify.warning("Please choose an exam.");
		result = false;
	}
	if($("#examTypeDropdown").val() == null) {
		alertify.warning("Please specify an exam type.");
		result = false;
	}
	return result;
}

function clearExamModalFields() {
	$("#sourceDropdown").val(1).trigger("change");
	$("#examTypeDropdown").val(0).trigger("change");
	$("#examSectionDropdown").val(0).trigger("change");
	$("#examTitleDropdown").val(0).trigger("change");
}

function attachListenerToBtnCancelExam() {
	$("#btnCancelExam").on("click",function() {
		clearExamModalFields();
	});
}

function attachListenerToSectionLinkUp() {
	$("body").on("click",".sectionUp",function() {
		learningPathSectionList=changeOrderNo("up",$(this).data("id"),
				$(this).data("orderno"),$(this).data("maxorderno"));
		var courseInfo = {};
		courseInfo.learningPathSections=learningPathSectionList;
		updateLearningPathSectionOrder(courseInfo);
	});
} 

function attachListenerToSectionLinkDown() {
	$("body").on("click",".sectionDown",function() {
		learningPathSectionList=changeOrderNo("down",$(this).data("id"),
				$(this).data("orderno"),$(this).data("maxorderno"));
		var courseInfo = {};
		courseInfo.learningPathSections=learningPathSectionList;
		updateLearningPathSectionOrder(courseInfo);
	});
} 

function changeOrderNo(direction,learningPathSectionId,sectionOrderNo,maxOrderNo) {
	var tempLearningPathSectionList=learningPathSectionList;
	if(direction=="up") {
		var calculatedOrderNo =sectionOrderNo-1;
		newOrderNo = (calculatedOrderNo<=0)?1:calculatedOrderNo;
		_.each(learningPathSectionList,function(learningPathSection) {
			if(learningPathSection.learningPathSectionId==learningPathSectionId) {
				tempLearningPathSectionList=changeOtherSectionOrderNo(sectionOrderNo,newOrderNo,tempLearningPathSectionList);
				learningPathSection.sectionOrderNo = newOrderNo;
			}
		});
	} else {
		var calculatedOrderNo=sectionOrderNo+1;
		newOrderNo = (calculatedOrderNo>maxOrderNo)?maxOrderNo:calculatedOrderNo;
		_.each(learningPathSectionList,function(learningPathSection) {
			if(learningPathSection.learningPathSectionId==learningPathSectionId) {
				tempLearningPathSectionList=changeOtherSectionOrderNo(sectionOrderNo,newOrderNo,tempLearningPathSectionList);
				learningPathSection.sectionOrderNo = newOrderNo;
			}
		});
	}
	return tempLearningPathSectionList;
}

function changeOtherSectionOrderNo(sectionOrderNo,newOrderNo,tempSectionList) {
	var tempLearningPathSectionOrder=tempSectionList;
	_.each(tempLearningPathSectionOrder,function(learningPathSection) {
		if(learningPathSection.sectionOrderNo==newOrderNo) {
			learningPathSection.sectionOrderNo = sectionOrderNo;
		}
	});
	return tempLearningPathSectionOrder;
}

function updateLearningPathSectionOrder(courseInfo) {
	var courseId=$("#courseId").val();
	$.when(ajax.customUpdate("courseinfo/"+courseId+"/learningpathsection/sectionorderno",courseInfo))
		.done(function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					initLearningPathTbl();
					break;
			}
	});
}

function attachListenerToSubItemLinkUp() {
	$("body").on("click",".subItemUp",function() {
		learningPathList=changeSubItemOrderNo("up",$(this).data("id"),
				$(this).data("orderno"),$(this).data("maxorderno"));
		var courseInfo = {};
		courseInfo.learningPaths=learningPathList;
		updateLearningPathSubOrderNo(courseInfo);
	});
}

function attachListenerToSubItemLinkDown() {
	$("body").on("click",".subItemDown",function() {
		learningPathList=changeSubItemOrderNo("down",$(this).data("id"),
				$(this).data("orderno"),$(this).data("maxorderno"));
		var courseInfo = {};
		courseInfo.learningPaths=learningPathList;
		updateLearningPathSubOrderNo(courseInfo);
	});
}

function updateLearningPathSubOrderNo(courseInfo) {
	var courseId=$("#courseId").val();
	$.when(ajax.customUpdate("courseinfo/"+courseId+"/learningpath/suborderno",courseInfo)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				initLearningPathTbl();
				break;
		}
	});
}

function changeSubItemOrderNo(direction,learningPathId,subOrderNo,maxOrderNo) {
	var tempLearningPathList=learningPathList;
	if(direction=="up") {
		var calculatedOrderNo =subOrderNo-1;
		newOrderNo = (calculatedOrderNo<=0)?1:calculatedOrderNo;
		_.each(learningPathList,function(learningPath) {
			if(learningPath.learningPathId==learningPathId) {
				tempLearningPathList=changeOtherSubItemOrderNo(subOrderNo,newOrderNo,learningPath.learningPathSectionId,
						tempLearningPathList);
				learningPath.subOrderNo = newOrderNo;
			}
		});
	} else {
		var calculatedOrderNo=subOrderNo+1;
		newOrderNo = (calculatedOrderNo>maxOrderNo)?maxOrderNo:calculatedOrderNo;
		_.each(learningPathList,function(learningPath) {
			if(learningPath.learningPathId==learningPathId) {
				tempLearningPathList=changeOtherSubItemOrderNo(subOrderNo,newOrderNo,learningPath.learningPathSectionId,
						tempLearningPathList);
				learningPath.subOrderNo = newOrderNo;
			}
		});
	}
	return tempLearningPathList;
}

function changeOtherSubItemOrderNo(subOrderNo,newOrderNo,learningSectionId,tempSubItemList) {
	var tempLearningPathList=tempSubItemList;
	_.each(tempLearningPathList,function(learningPath) {
		if(learningPath.subOrderNo==newOrderNo && learningPath.learningPathSectionId==learningSectionId) {
			learningPath.subOrderNo = subOrderNo;
		}
	});
	return tempLearningPathList;
}

function attachListenerToEditSectionBtn() {
	$("#editSectionBtn").on("click",function() {
		if(validateEditSectionModalFields()) {
			var learningPathSection=getEditSectionModalFields();
			updateLearningPathSection(learningPathSection);
		}
	});
}

function validateEditSectionModalFields() {
	var result=true;
	if($("#editSectionTitle").val() == "") {
		alertify.warning("Section title cannot be empty.");
		result=false;
	}
	return result
}

function updateLearningPathSection(learningPathSection) { 
	var courseId=$("#courseId").val();
	$.when(ajax.customUpdate("courseinfo/"+courseId+"/learningpathsections",learningPathSection))
		.done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully updated!");
				initLearningPathTbl();
				initSectionDropdown();
				$("#editSectionModal").modal("close");
				break;
		}
	});
}

function getEditSectionModalFields() {
	var learningPathSection={};
	learningPathSection.learningPathSectionId=$("#learningPathSectionId").val();
	learningPathSection.sectionTitle=$("#editSectionTitle").val();
	learningPathSection.description=$("#editSectionDescription").val();
	return learningPathSection;
}

function attachListenerToContentCancelBtn() {
	$("#ContentCancelBtn").on("click",function() {
		clearAddContentModalFields();
	});
}