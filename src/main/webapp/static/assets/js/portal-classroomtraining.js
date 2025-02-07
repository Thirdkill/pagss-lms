$(function() {
	defineVariables();
	initLearningMaterialsSlickSettings();
	initExamSlickSettings();
	initEvaluationSlickSettings();
	initClassSchedule();
	initLearningMaterialsSlider();
	initExamSlider();
	initEvaluationSlider();
	attachListenerToViewMaterialsLink();
});

function defineVariables() {
	noOfLearningMaterials = 0;
	noOfExams = 0;
	noOfEvaluations = 0;
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
	var classId=$("#classId").val();
	var noOfRetake=examObj.noOfRetake-examObj.noOfTimesTaken;
	examHtml.push("<div>");
	examHtml.push("<a href='exam-home?classId="+classId+"&examId="+examObj.examId+"'>")
	examHtml.push("<div class='divcard' style='margin: 5px;''>");
	examHtml.push("<h6 style='margin: 0;''><b>"+examObj.title+"</b></h6>("+getExamTypeDesc(examObj.examType)+")");
	examHtml.push("<p><label><span>Duration: "+examObj.duration+" minutes</span></label></p>");
	examHtml.push("<p><label><span>Passing Score: "+examObj.passingScore+"</span></label></p>");
	examHtml.push("<p><label><span>No Of Retake Left: "+noOfRetake+"</span></label></p>");
	examHtml.push("<p><label><span>Status: "+examObj.remarks+"</span></label></p></div></div>");
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
	var classId = $("#classId").val();
	var result=$.Deferred();
	$.when(createCourseEvaluationSlides()).then(function(status) {
		//Todo
		$.when(ajax.fetch("classInfo/classevaluations/list?classId="
			+ classId)).done(function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					var classEvaluations=response.classevaluations;
					populateEvaluationSlider(classEvaluations);
					noOfEvaluations+=classEvaluations.length;
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

function attachListenerToViewMaterialsLink() {
	$("body").on("click",".viewMaterialsLink",function() {
		$("#documentViewerFrame").attr("src",$(this).data("url"));
	});
}