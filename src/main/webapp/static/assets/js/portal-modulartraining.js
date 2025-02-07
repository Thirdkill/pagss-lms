$(function() {
	defineVariables();
	initClassSchedule();
	initLearningMaterialsSlickSettings();
	initLearningMaterialsSlider();
	initExamSlickSettings();
	initExamSlider();
	initEvaluationSlickSettings();
	initEvaluationSlider();
	initLearningPaths();
	attachListenerToContentUrlLink();
	attachListenerToFinishBtn();
});
function defineVariables() {
	noOfLearningMaterials = 0;
	noOfExams = 0;
	noOfEvaluations = 0;
	learningPathList=[];
}

function initClassSchedule() {
	var classId=$("#classId").val();
	$.when(ajax.fetch("classinfo/"+classId+"/classschedule")).done(function(response) {
		var classInfo = response.classInfo;
		switch(classInfo.scheduleType) {
			case ScheduleType.BLOCK:
				populateClassBlockSchedule(classInfo.classBlockSchedule);
				break;
			case ScheduleType.SET:
				populateClassSetSchedule(classInfo.classSetSchedules);
				break;
		}
	});
}

function populateClassBlockSchedule(classBlockSchedule) {
	$("#classScheduleDiv").append($("<p>").html(classBlockSchedule.startDate+" "+classBlockSchedule.startTime
			+" to "+classBlockSchedule.endDate+" "+classBlockSchedule.endTime));
}

function populateClassSetSchedule(classSetSchedules) {
	_.each(classSetSchedules, function(classSetSchedule) {
		$("#classScheduleDiv").append($("<p>").html(classSetSchedule.startDate + " " + classSetSchedule.startTime
		+ " to " + classSetSchedule.endDate + " " + classSetSchedule.endTime));
	});
}

function initLearningMaterialsSlickSettings() {
	$("#learningMaterialsSlider").slick({
		infinite:true,
		slidesToShow:5,
		slidesToScroll:3
	});
}

function initExamSlickSettings() {
	$("#classExamSlider").slick({
		infinite:true,
		slidesToShow:5,
		slidesToScroll:3
	});
}

function initEvaluationSlickSettings() {
	$("#evaluationSlider").slick({
		infinite:true,
		slidesToShow:5,
		slidesToScroll:3
	});
}

function initLearningMaterialsSlider() {
	createClassMaterialSlides();
}

function createClassMaterialSlides() {
	var classId=$("#classId").val();
	$.when(ajax.fetch("classinfo/"+classId+"/classmaterials")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				var classMaterials = response.classMaterials;
				populateLearningMaterialsSlider(classMaterials);
				noOfLearningMaterials+=classMaterials.length;
				displayNoSlideText("#learningMaterialsSlider","There is no learning material for this class.",
					noOfLearningMaterials);
				break;
		}
	});
}

function displayNoSlideText(dom,message,listSize) {
	if(listSize==0) {
		$(dom).append("<center><p>"+message+"</p></center>");
	}
}

function populateLearningMaterialsSlider(materialsList) {
	_.each(materialsList,function(materialObject) {
		$("#learningMaterialsSlider").slick("slickAdd",createLearningMaterialSlideDiv(materialObject));
	});
}

function createLearningMaterialSlideDiv(material) {
	var divHtml = [];
	divHtml.push("<div>")
	divHtml.push(createDivLink(material));
    divHtml.push("<div class='divcard' style='margin: 5px;''>")
    divHtml.push("<h6 style='margin: 0;'><b>"+material.fileLabel+"</b></h6>")
    divHtml.push("<p><label><span>"+getContentType(material.contentType)+"</span></label></p>");
	divHtml.push("</div></a>");
	divHtml.push("</div>");
	return divHtml.join(" ");
}

function createDivLink(material) {
	var html = [];
	var classId=$("#classId").val();
	if(material.contentType != ContentType.URL) {
		html.push("<a href='preview-material?classId="+classId+"&classMaterialId="+material.classMaterialId+"' ");
		html.push("class='viewMaterialsLink' data-type='"+material.contentType+"'>");
	} else {
		html.push("<a href='"+material.contentUrl+"' class='viewMaterialsLink' target='_blank'>");
	}
	return html.join(" ");
}

function getContentType(contentType) {
	var contentTypeDesc="";
	switch(contentType) {
		case ContentType.DOCUMENT:
			contentTypeDesc="Document";
			break;
		case ContentType.VIDEO:
			contentTypeDesc="Video";
			break;
		case ContentType.AUDIO:
			contentTypeDesc="Audio";
			break;
		case ContentType.IMAGE:
			contentTypeDesc="Image";
			break;
		case ContentType.FLASH:
			contentTypeDesc="Flash";
			break;
		case ContentType.URL:
			contentTypeDesc="URL";
			break;									
	}
	return contentTypeDesc;
}

function initExamSlider() {
	createClassExamSlides()
}

function createClassExamSlides() {
	var result = $.Deferred();
	var classId = $("#classId").val();
	$.when(ajax.fetch("classinfo/"+classId+"/classexams")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				var classExams = response.classExams;
				populateExamSlider(classExams);
				result.resolve("Success");
				noOfExams+=classExams.length;
				displayNoSlideText("#classExamSlider","There is no exam for this class.",noOfExams);
				break;
		}
	});
	return result.promise();
}

function populateExamSlider(examList) {
	_.each(examList,function(examObj) {
		$("#classExamSlider").slick("slickAdd",createExamSlideDiv(examObj));
	});
}

function createExamSlideDiv(examObj) {
	var examHtml = [];
	var noOfRetake=examObj.noOfRetake-examObj.noOfTimesTaken;
	examHtml.push("<div>");
	examHtml.push("<div class='divcard' style='margin: 5px;''>");
	examHtml.push("<h6 style='margin: 0;''><b>"+examObj.title+"</b></h6>("+getExamTypeDesc(examObj.examType)+")");
	examHtml.push("<p><label><span>Duration: "+examObj.duration+" minutes</span></label></p>");
	examHtml.push("<p><label><span>Passing Score: "+examObj.passingScore+"</span></label></p>");
	examHtml.push("<p><label><span>No Of Retake Left: "+noOfRetake+"</span></label></p>");
	examHtml.push("<p><label><span>Status: "+examObj.remarks+"</span></label></p>");
	return examHtml.join(" ");
}

function getExamTypeDesc(examType) {
	var examTypeDesc="";
	_.each(ExamTypes,function(examTypeObj) {
		if(examTypeObj.id==examType) {
			examTypeDesc=examTypeObj.description;
		}
	});
	return examTypeDesc;
}

function initEvaluationSlider() {
	$.when(createCourseEvaluationSlides()).then(function(status) {
		//Todo
	});
}

function createCourseEvaluationSlides() {
	var courseId = $("#courseId").val();
	var result=$.Deferred();
	$.when(ajax.fetch("courseinfo/"+courseId+"/courseevaluations/list")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				var courseEvaluations=response.courseevaluations;
				populateEvaluationSlider(courseEvaluations);
				noOfEvaluations+=courseEvaluations.length;
				result.resolve("Success");
				displayNoSlideText("#evaluationSlider","There is no evaluation for this class.",noOfEvaluations);
				break;
		}
	});
	return result.promise();
}

function populateEvaluationSlider(evaluationList) {
	_.each(evaluationList,function(evaluationObj) {
		$("#evaluationSlider").slick("slickAdd",createEvaluationSlideDiv(evaluationObj));
	});
}

function createEvaluationSlideDiv(evaluationObj) {
	var htmlDiv=[];
	htmlDiv.push("<div>");
	htmlDiv.push("<div class='divcard' style='margin: 5px;''>");
	htmlDiv.push("<h6 style='margin: 0;''><b>"+evaluationObj.title+"</b></h6>");
	htmlDiv.push("<p><label><span>"+((evaluationObj.status==Status.ACTIVE)?"ACTIVE":"INACTIVE")+"</span></label></p>");
	htmlDiv.push("</div></div>");
	return htmlDiv.join(" ");
}

function initLearningPaths() {
	var classId=$("#classId").val();
	$.when(ajax.fetch("classinfo/"+classId+"/learningpaths")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				learningPathList=response.learningPaths;
				populateLearningPathSection(response.learningPathSections,response.learningPaths);
				break;
		}
	})
}

function populateLearningPathSection(learningPathSections,learningPaths) {
	_.each(learningPathSections,function(learningPathSection) {
		$("#learningPathCon").append($("<table/>").addClass("striped highlight")
			.append($("<thead/>").append($("<tr/>")
			.append($("<th>").attr("colspan","4")
				.append($("<span/>").addClass("left").html(learningPathSection.sectionTitle)))))
			.append($("<tbody/>").attr("id","sectionNo_"+learningPathSection.learningPathSectionId)));
	});
	populateLearningPath(learningPaths);
}

function populateLearningPath(learningPaths) {
	
	_.each(learningPaths,function(learningPath) {
		console.log(learningPath.itemType+"=="+ItemType.MATERIAL);
		$("#sectionNo_"+learningPath.learningPathSectionId)
			.append((learningPath.itemType==ItemType.MATERIAL)?
				getLearningPathMaterialsRow(learningPath):getLearningPathExamRow(learningPath));
	});
}

function getLearningPathMaterialsRow(learningPath) {
	var html=[];
	console.log(learningPath.sessionStatus+"=="+SessionStatus.NOT_YET_TAKEN);
	html.push("<tr>");
	html.push("<td>"+learningPath.fileLabel+"("+getContentType(learningPath.contentType)+")"+"</td>");
	html.push("<td>"+((learningPath.lastAccessed!=null)?"Last Accessed:"+learningPath.lastAccessed:"")+"</td/>");
	html.push("<td>"+((learningPath.sessionStatus!=SessionStatus.NOT_YET_TAKEN)?
		"Total Time Spent:"+learningPath.timeSpent+" minutes":"")+"</td>");
	html.push("<td>"+getSessionStatusValue(learningPath.sessionStatus)+"</td>");
	html.push("<td>"+getLearningPathActionBtn(learningPath)+"</td>");
	html.push("<tr/>");
	return html.join(" ");
}

function getSessionStatusValue(sessionStatus) {
	var sessionStatusDesc="";
	if(sessionStatus==SessionStatus.FINISHED) {
		sessionStatusDesc="DONE";
	} else if(sessionStatus==SessionStatus.IN_PROGRESS) {
		sessionStatusDesc="In Progress";
	} else {
		sessionStatusDesc="Not Yet Taken";
	}
	return sessionStatusDesc;
}

function getLearningPathExamRow(learningPath) {
	var passingScore = (learningPath.passingScore/learningPath.totalItems)*100;
	var html=[];
	html.push("<tr>");
	html.push("<td>"+learningPath.title+"("+getExamType(learningPath.examType)+")</td>");
	html.push("<td>Duration: "+learningPath.duration + " minutes</td>");
	html.push("<td>Passing Score: "+passingScore+"%</td>");
	html.push("<td>No. Of Retake :"+learningPath.noOfRetake+"</td>");
	html.push("<td>"+getLearningPathActionBtn(learningPath)+"</td>");
	html.push("<tr/>");
	return html.join(" ");
}

function getExamType(examType) {
	var result = "";
	_.each(ExamTypes,function(examTypeObj) {
		if(examTypeObj.id==examType) {
			result=examTypeObj.description;
		}
	});
	return result;
}

function getLearningPathActionBtn(learningPath) {
	var html = [];
	var classId=$("#classId").val();
	var lastSubOrderNo=getLastItemSubOrderNo(learningPath.subOrderNo);
	var lastLearningPathItem=getLearningPathObj(learningPath.learningPathSectionId,lastSubOrderNo);
	if(learningPath.sessionStatus==SessionStatus.FINISHED) {
		if(learningPath.contentType ==ContentType.URL && learningPath.sessionStatus==SessionStatus.FINISHED) {
			html.push("<a href='"+learningPath.contentUrl+"' data-id="+learningPath.learningPathId);
			html.push("class='contentUrlLink' ><i class='material-icons'>play_arrow</i></a>");
		} else {
			html.push("<a href='preview-material?classId="+classId+"&learningPathId="+learningPath.learningPathId+"'");
			html.push("data-id="+learningPath.learningPathId+">")
			html.push("<i class='material-icons'>play_arrow</i></a>");
		}
	} else if(lastLearningPathItem.sessionStatus==SessionStatus.FINISHED || learningPath.subOrderNo==1) {
		if(learningPath.contentType!=ContentType.URL) {
			html.push("<a href='preview-material?classId="+classId+"&learningPathId="+learningPath.learningPathId+"'");
			html.push("class='bttn btn waves-effect waves-light'>Start</a>");
		} else {
			html.push("<a href='"+learningPath.contentUrl+"' data-id="+learningPath.learningPathId);
			html.push("class='contentUrlLink bttn btn waves-effect waves-light'>Start</a>");
		}
	} 
	return html.join(" ");	
}

function getLastItemSubOrderNo(subOrderNo) {
	var lastItemSubOrderNo=subOrderNo-1;
	return ((lastItemSubOrderNo)==0)?1:lastItemSubOrderNo;
}

function getLearningPathObj(sectionId,subOrderNo) {
	var learningPathObj={};
	_.each(learningPathList,function(learningPath){
		if(learningPath.subOrderNo==subOrderNo && learningPath.learningPathSectionId==sectionId) {
			learningPathObj=learningPath;
		}
	});
	return learningPathObj;
}

function attachListenerToContentUrlLink() {
	$("body").on("click",".contentUrlLink",function() {
		var learningPathId=parseInt($(this).data("id"));
		createLearningPathSessionTimer(learningPathId);
	});
}

function createLearningPathSessionTimer(learningPathId) {
	var classId=$("#classId").val();
	$.when(ajax.customUpdate("classinfo/"+classId+"/learningpathsessiontimer/"+learningPathId)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
			break;
		}
	})
} 

function attachListenerToFinishBtn() {
	$("#finishBtn").on("click",function() {
		if(validateClassLearningPath()) {
			updateEmployeeTrainingStatus();
		}
	});
}

function updateEmployeeTrainingStatus() {
	var classId=$("#classId").val();
	$.when(ajax.customUpdate("classinfo/"+classId+"/classemployee/trainingStatus")).done(
		function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					alertify.success("Congratulations! You have successfully finished this class");
					break;
				case HttpStatus.TIME_SPENT_IS_NOT_ENOUGH:
					alertify.warning(Message.TIME_SPENT_IS_NOT_ENOUGH);
					break;
			}
	});
}

function validateClassLearningPath() {
	var result=true;
	if(!checkCompletedClassMaterials()) {
		alertify.warning("Please finish all class materials first.");
		result=false;
	}
	console.log(checkCompletedClassMaterials());
	return result;
}

function checkCompletedClassMaterials() {
	var result=true;
	_.each(learningPathList,function(learningPath) {
		if(learningPath.itemType==ItemType.MATERIAL && learningPath.sessionStatus!=SessionStatus.FINISHED) {
			result=false;
		}
	});
	return result;
}