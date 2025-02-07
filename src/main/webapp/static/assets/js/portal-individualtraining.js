$(function() {
	handleLoadEvents();
	defineVariables();
	initPageSettings();
	initClassSchedule();
	initLearningMaterialsSlickSettings();
	initLearningMaterialsSlider();
	attachListenerToViewMaterialsLink();
	attachListenerToMaterialsNxtStepBtn();
	attachListenerToExamNxtStepBtn();
});
function handleLoadEvents() {
	$(window).unload(function() {
		updateTimeSpent();
	});
}

function updateTimeSpent() {
	var classId = $("#classId").val();
	$.when(ajax.customUpdate("classsessiontimer/"+classId+"/timeSpent")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				break;
		}
	});
}

function defineVariables() {
	noOfLearningMaterials = 0;
	noOfExams = 0;
	noOfEvaluations = 0;
}

function initPageSettings() {
	displaySlider("#learningMaterialsSlider");
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

function initEvaluationSlider() {
	var classId = $("#classId").val();
	var result=$.Deferred();
	$.when(createCourseEvaluationSlides()).then(function(status) {
		//Todo
		$.when(ajax.fetch("courseinfo/classevaluations/list?classId="
			+ classId)).done(function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					var classEvalutaions=response.classEvalutaions;
					populateEvaluationSlider(classEvalutaions);
					noOfEvaluations+=classEvalutaions.length;
					result.resolve("Success");
					displayNoSlideText("#evaluationSlider","There is no evaluation for this class.",noOfEvaluations);
					break;
			}
		});
	});
	return result.promise();
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
	var classId=$("#classId").val();
	var courseId=$("#courseId").val();
	htmlDiv.push("<div>");
	if (evaluationObj.courseEvaluationId > 0 ) {
		htmlDiv.push(`<a href='class-evaluation-test?classId=${classId}&courseEvaluationId=${evaluationObj.evaluationId}&courseId=${courseId}'>`)
	
	} else {
		htmlDiv.push(`<a href='class-evaluation-test?classId=${classId}&classEvaluationId=${evaluationObj.evaluationId}'>`)
	}
	htmlDiv.push("<div class='divcard' style='margin: 5px;''>");
	htmlDiv.push("<h6 style='margin: 0;''><b>"+evaluationObj.title+"</b></h6>");
	htmlDiv.push("<p><label><span>Status: "+((evaluationObj.employeeEvaluationId>0)?"SUBMITTED":"NOT YET STARTED")+"</span></label></p>");
	htmlDiv.push("</div></div>");
	return htmlDiv.join(" ");
}

function initLearningMaterialsSlider() {
	$.when(createClassMaterialSlides()).then(function(status) {
		createCourseMaterialSlides();
	})
}

function createClassMaterialSlides() {
	var result = $.Deferred();
	var classId=$("#classId").val();
	$.when(ajax.fetch("classinfo/"+classId+"/classmaterials")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				var classMaterials = response.classMaterials;
				populateLearningMaterialsSlider(classMaterials);
				noOfLearningMaterials+=classMaterials.length;
				result.resolve("Success");
				break;
		}
	});
	return result.promise();
}

function createCourseMaterialSlides() {
	var courseId=$("#courseId").val();
	$.when(ajax.fetchWithData("courseinfo/"+courseId+"/coursematerials/viewstatus",
		{viewStatus:Status.ACTIVE})).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				var courseMaterials = response.courseMaterials;
				populateLearningMaterialsSlider(courseMaterials);
				noOfLearningMaterials+=courseMaterials.length;
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
	var result="";
	if(material.contentType != ContentType.URL) {
		result="<a href='#!' class='viewMaterialsLink' data-url="+material.contentUrl+" data-type="+material.contentType+">"
	} else {
		result="<a href='"+material.contentUrl+"' class='viewMaterialsLink' target='_blank'>";
	}
	return result;
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

function displaySlider(dom) {
	switch(dom) {
		case "#learningMaterialsSlider":
			$("#examCon").hide();
			$("#evaluationsCon").hide();
			$("#learningMaterialsCon").show();
			$("#documentViewerCon").show();
			setActiveStepLink("#classProperLink");
			break;
		case "#classExamSlider":
			$("#examCon").show();
			$("#evaluationsCon").hide();
			$("#learningMaterialsCon").hide();
			$("#documentViewerCon").hide();
			setActiveStepLink("#takeExamLink");
			initExamSlickSettings();
			initExamSlider();
			break;
		case "#evaluationSlider":
			$("#examCon").hide();
			$("#evaluationsCon").show();
			$("#learningMaterialsCon").hide();
			$("#documentViewerCon").hide();
			initEvaluationSlickSettings();
			initEvaluationSlider();
			setActiveStepLink("#evaluateClassLink");
			break;
	}
}

function setActiveStepLink(dom) {
	switch(dom) {
		case "#classProperLink":
			$("#classProperLink").addClass("bttn btn waves-light");
			$("#takeExamLink").removeClass("bttn btn waves-light");
			$("#evaluateClassLink").removeClass("bttn btn waves-light");
			break;
		case "#takeExamLink":
			$("#takeExamLink").addClass("bttn btn waves-light");
			$("#classProperLink").removeClass("bttn btn waves-light");
			$("#evaluateClassLink").removeClass("bttn btn waves-light");
			break;
		case "#evaluateClassLink":
			$("#evaluateClassLink").addClass("bttn btn waves-light");
			$("#classProperLink").removeClass("bttn btn waves-light");
			$("#takeExamLink").removeClass("bttn btn waves-light");
			break;
	}
}

function initExamSlider() {
	$.when(createClassExamSlides()).then(function(status) {
		createCourseExamSlider();
	});
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
				break;
		}
	});
	return result.promise();
}

function createCourseExamSlider() {
	var courseId = $("#courseId").val();
	var classId = $("#classId").val();
	$.when(ajax.fetchWithData("courseinfo/"+courseId+"/courseexam",{classId:classId})).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				var courseExams=response.courseExams;
				populateExamSlider(courseExams);
				noOfExams+=courseExams.length;
				displayNoSlideText("#classExamSlider","There is no exam for this class.",
						noOfExams);
				break;
		}
	});
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

function attachListenerToViewMaterialsLink() {
	$("body").on("click",".viewMaterialsLink",function() {
		$("#documentViewerFrame").attr("src",$(this).data("url"));
	});
}

function attachListenerToExamNxtStepBtn() {
	$("#examNxtStepBtn").on("click",function() {
		displaySlider("#evaluationSlider");
	});
}

function attachListenerToMaterialsNxtStepBtn() {
	$("#materialsNextStepBtn").on("click",function() {
		validateSessionTime();
	});
}

function validateSessionTime() {
	var classId = $("#classId").val();
	$.when(ajax.fetchWithData("validate-classsession",{classId:classId})).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				triggerNextStepAction(response.result,response.sessionStatus);
				break;
		}
	})
}

function triggerNextStepAction(result,sessionStatus) {
	if(result && sessionStatus == SessionStatus.FINISHED) {
		displaySlider("#classExamSlider");
	} else {
		if(sessionStatus == SessionStatus.FINISHED) {
			displaySlider("#classExamSlider");
		} else {
			var warnMessage=[];
			warnMessage.push("You cannot access the next step.");
			warnMessage.push("You haven't spent yet the minimum required duration for the class proper.");
			alertify.warning(warnMessage.join(" "));
		}
	}
}