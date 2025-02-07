/**
 * 
 */

$(function(){
	defineVariables();
	initClassInfo();
	initClassExamTbl();
	initCourseExamsTbl();
	initAvailableExamsTbl();
	attachListenerToSearchBox();
	attachListenerToAddClassExamBtn();
	attachListenerToSaveExamSettings();
	attachListenerToPassingScoreTxtBox();
	attachListenerToExamSettingsCancel();
	attachListenerToOnlyWhenFailedChkBox();
	attachListenerToEvenIfPassedCheckBox();
	attachListenerToFetchTraineesExamScoresTbl();
});

function defineVariables() {
	checkedList = [];
	classList = [];
	courseList = [];
}

function initClassInfo() {
	if($("#deliveryMethod").val() == DeliveryMethod.INDIVIDUAL){
		$("#deliveryMethodLabel").html("Individual");
	} else if($("#deliveryMethod").val() == DeliveryMethod.MODULAR){
		$("#deliveryMethodLabel").html("Modular");
	} else if($("#deliveryMethod").val() == DeliveryMethod.CLASSTRAINING){
		$("#deliveryMethodLabel").html("Class Training");
	}
}

function initClassExamTbl() {
	var classId = parseInt(GetURLParameter('classId'));
	$("#classExamTblPagination").pagination({     
        dataSource: "classinfo/"+classId+"/classexam/pages",
        locator: "classExams",
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
    	ajax:{data:{classId:classId},type:"GET"},
        className: "paginationjs-theme-blue",
        pageSize: 5,     
        callback: function(data) {                      
            $("#classExamTblBody").html("");
            classExamList = data;
            var functionName = eval()
            _.each(data, function(classExam) {
            	createClassExamRow(classExam);
            });
            populateExamScoreViewByClassDropdown(data);
            classList = data;
        }
    });
}

function createClassExamRow(classExam) {
	$("#classExamTblBody").append($("<tr/>")
		.append($("<td/>").addClass("mid").html(classExam.title))
		.append($("<td/>").addClass("mid").html(getExamTypeDesc(classExam.examType)))
		.append($("<td/>").addClass("mid").html(classExam.description))
		.append($("<td/>").addClass("min")
			.append($("<a/>").addClass("waves-light waves-effect modal-trigger").attr({
				"href":"#!",
				"data-target":"examSettingsModal",
				"data-classexamid":classExam.classExamId,
				"data-classid":classExam.classId
				}).bind("click",function() {
					var currectClassExam = classExam;
					populateExamSettings(currectClassExam);
				}).append($("<i/>").addClass("material-icons").html("settings")))
			.append($("<a/>").addClass("waves-light waves-effect").attr({
				"href":"admin.examination?action=update&module=classexam&classId="+classExam.classId+
					"&examId="+classExam.examId,
				"data-classexamid":classExam.classExamId,
				"data-classid":classExam.classId
				}).append($("<i/>").addClass("material-icons").html("edit")))
			.append($("<a/>").addClass("waves-light waves-effect").attr({
				"href":"#!",
				"data-classexamid":classExam.classExamId,
				"data-classid":classExam.classId
				}).bind("click",function() {
					deleteClassExam($(this).data("classexamid"));
				}).append($("<i/>").addClass("material-icons").html("delete")))));
}

function initCourseExamsTbl() {
	var courseId = $("#tbCourseId").val();
	$("#courseExamTblPagination").pagination({     
        dataSource: "courseinfo/"+courseId+"/courseexam/pages",
        locator: "courseExams",
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
    	ajax:{data:{courseId:courseId},type:"GET"},
        className: "paginationjs-theme-blue",
        pageSize: 5,     
        callback: function(data) {                      
            $("#courseExamTblBody").html("");
            courseExamList = data;
            var functionName = eval()
            _.each(data, function(courseExam) {
            	createCourseExamRow(courseExam);
            });
            courseList = data;
            populateExamScoreViewByCourseDropdown(data);
        }
    });
}

function populateExamScoreViewByClassDropdown(classExams) {
	$("#examScoreViewByDropdown").empty();
	$("#examScoreViewByDropdown").append($("<option/>")
			.attr({"disabled":"disabled","selected":"selected"})
			.html("Choose An Option"));
	_.each(classExams, function(classExam) {
		$("#examScoreViewByDropdown").append($("<option/>")
			.attr({"value":classExam.examId,"data-examtype":"class"})
			.html(classExam.title));
	});
}

function populateExamScoreViewByCourseDropdown(courseExams) {
	_.each(courseExams, function(courseExam) {
		$("#examScoreViewByDropdown").append($("<option/>")
			.attr({"value":courseExam.examId,"data-examtype":"course"})
			.html(courseExam.title));
	});
}

function createCourseExamRow(courseExam) {
	var classId = parseInt(GetURLParameter('classId'));
	$("#courseExamTblBody").append($("<tr/>")
		.append($("<td/>").addClass("mid").html(courseExam.title))
		.append($("<td/>").addClass("mid").html(getExamTypeDesc(courseExam.examType)))
		.append($("<td/>").addClass("mid").html(courseExam.description))
		.append($("<td/>").addClass("min")
			.append($("<a/>").addClass("waves-light waves-effect").attr({
				"href":"admin.examination?action=update&module=classexam&courseId="+courseExam.courseId+
					"&examId="+courseExam.examId+
					"&classId="+classId,
				"data-courseexamid":courseExam.courseExamId,
				"data-courseid":courseExam.courseId
				}).append($("<i/>").addClass("material-icons").html("edit")))));
}

function initAvailableExamsTbl() {
	var classId = parseInt(GetURLParameter('classId'));
	$("#examTblPagination").pagination({     
        dataSource: "examinfos/active/pages/class",
        locator: "examInfos",
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        ajax: {data:{status:Status.ACTIVE,classId:classId},type:"GET"},
        className: "paginationjs-theme-blue",
        pageSize: 5,     
        callback: function(data) {                      
            $("#examTblBody").html("");
            availableExamList = data;
            _.each(data, function(examInfo) {
            	createExamRow(examInfo);
            });
        }
    });
}

function createExamRow(examInfo) {
	var classId = parseInt(GetURLParameter('classId'));
	$("#examTblBody").append($("<tr/>")
		.append($("<td/>").addClass("mins")
			.append($("<label/>")
				.append($("<input/>").attr({
					"type":"checkbox",
					"data-examid":examInfo.examId
				}).addClass("examCheckBox")
				.prop("checked",checkIfItemExist(examInfo.examId))
				.bind("change",function() {
					if($(this).prop("checked") != true) {
						if(checkIfItemExist(examInfo.examId)) {
							deleteClassExamFromCheckList(examInfo);
						}
					} else {
						if(!checkIfItemExist(examInfo.examId)) {
							examInfo.classId = classId;
							checkedList.push(examInfo);
						}
					}
				}))
				.append($("<span/>").addClass("checkStyle").css("padding-left","20px !important;"))))
		.append($("<td/>").addClass("mid").html(examInfo.title))
		.append($("<td/>").addClass("mid").html(getExamTypeDesc(examInfo.examType)))
		.append($("<td/>").addClass("mid").html(examInfo.description))
		.append($("<td/>").addClass("mins")
			.append($("<a/>").attr({
				"href":"#!",
				"data-examid":examInfo.examId
			}).addClass("waves-light waves-effect")
			.append($("<i/>").addClass("material-icons").html("pageview")))));
}

function populateExamSettings(classExam) {
	$("#classExamId").val(classExam.classExamId);
	$("#tbClassDuration").val((classExam.duration!=0)?classExam.duration:"30");
	$("#tbPassingScore").val((classExam.passingScore!=0)?classExam.passingScore:"75");
	$("#tbExamRetake").val(classExam.noOfRetake);
	setCheckBoxValue("#chIsSafeBrowser",classExam.isSafeBrowser);
	setCheckBoxValue("#chShowCorrectAnswer",classExam.isShowCorrectAnswer);
	setCheckBoxValue("#chShowScore",classExam.isShowScore);
	setCheckBoxValue("#chShowBreakdown",classExam.isShowBreakDown);
	$("#chOnlyFailed").trigger("change");
	$("#chIfPassed").trigger("change");
	if(classExam.examRetake==ExamRetake.ONLY_WHEN_FAILED) {
		setCheckBoxValue("#chOnlyFailed",Status.ACTIVE);
		$("#chIfPassed").prop("checked","");
		$("#tbExamRetake").prop("disabled",true);
		$("#tbExamRetake").val("");
	}
	if(classExam.examRetake==ExamRetake.EVEN_IF_PASSED) {
		setCheckBoxValue("#chIfPassed",Status.ACTIVE);
		$("#chOnlyFailed").prop("checked","");
		$("#tbExamRetake").prop("disabled",false);
	}	
}

function attachListenerToOnlyWhenFailedChkBox() {
	$("#chOnlyFailed").on("change",function() {
		if($(this).prop("checked")) {
			$("#chIfPassed").prop("checked","");
			$("#tbExamRetake").prop("disabled",true);
			$("#tbExamRetake").val("");
		} else {
			$("#chIfPassed").prop("disabled",false);
			$("#tbExamRetake").prop("disabled",true);
		}
	});
}

function attachListenerToEvenIfPassedCheckBox() {
	$("#chIfPassed").on("change",function() {
		if($(this).prop("checked")) {
			$("#chOnlyFailed").prop("checked","");
			$("#tbExamRetake").prop("disabled",false);
		} else {
			$("#chOnlyFailed").prop("disabled",false);
			$("#tbExamRetake").prop("disabled",true);
		}
	});
}

function attachListenerToFetchTraineesExamScoresTbl() {
	$("#examScoreViewByDropdown").on("change",function() {
		var examId = parseInt($(this).val());
		$("#traineeExamScoreTblPagination").pagination({     
	        dataSource: "examinfos/scores",
	        locator: "employeeExamSummaries",
	        totalNumberLocator: function(response) {            
	            return response.totalRecords;
	        },
	    	ajax:{data:{examId:examId},type:"POST"},
	        className: "paginationjs-theme-blue",
	        pageSize: 10,     
	        callback: function(data) {                      
	            $("#traineeExamScoreTblBody").empty();
	            courseExamList = data;
	            var functionName = eval()
	            _.each(data, function(examScore) {
	            	createExamScoreRow(examScore);
	            });
	        }
	    });
	});
}

function createExamScoreRow(examScore) {
	$("#traineeExamScoreTblBody").append($("<tr/>")
		.append($("<td/>")
			.append($("<a/>").addClass("material-icons")
			.html("<i class='material-icons'>remove_red_eye</i>"))
			.append($("<a/>").addClass("material-icons")
			.html("<i class='material-icons'>insert_drive_file</i>"))
			.append($("<a/>").addClass("material-icons")
			.html("<i class='material-icons'>file_download</i>")))
		.append($("<td/>").html(examScore.fullName))
		.append($("<td/>").html(examScore.employeeCode))
		.append($("<td/>").html(examScore.jobName))
		.append($("<td/>").html(examScore.completedDate))
		.append($("<td/>").html(examScore.score))
		.append($("<td/>").html(validateClassEmployeeScoreStatus(examScore))))
}

function validateClassEmployeeScoreStatus(examScore) {
	var examType = $("#examScoreViewByDropdown option:selected").data("examtype");
	var status = "";
	if(examType == "class"){
		_.each(classList,function(ceObj) {
			if(ceObj.examId == examScore.examId) {
				if(examScore.score >= ceObj.passingScore) {
					status = "Passed";
				} else {
					status = "Failed";
				}
			}
		});
	}else if(examType == "course"){
		_.each(courseList,function(ceObj) {
			if(ceObj.examId == examScore.examId) {
				if(examScore.score >= ceObj.passingScore) {
					status = "Passed";
				} else {
					status = "Failed";
				}
			}
		});
	}
	return status;
}

function getClassExamObj(classExamId) {
	var tempCourseExam={};
	_.each(courseExamList,function(courseExam) {
		if(courseExam.classExamId==classExamId) {
			tempCourseExam=courseExam;
		}
	});
	return tempCourseExam;
}	

function getExamTypeDesc(examTypevalue) {
	var examTypeResult="";
	_.each(ExamTypes,function(examType) {
		if(examType.id==examTypevalue) {
			examTypeResult = examType.description;
		}
	});
	return examTypeResult;
}

function checkIfItemExist(examId) {
	var result = false;
	_.each(checkedList,function(examInfo) {
		if(examInfo.examId==examId) {
			result=true;
		}
	});
	return result;
}

function deleteClassExamFromCheckList(examInfo) {
	var result = false;
	_.each(checkedList,function(ceObj) {
		if(ceObj.examId == examInfo.examId) {
			checkedList = deleteItem(examInfo);
		}
	});
	return result;
}

function deleteItem(examInfo) {
	var resultArray = [];
	_.each(checkedList,function(ceObj) {
		if(ceObj.examId != examInfo.examId) {
			resultArray.push(ceObj);
		}
	});
	return resultArray;
}

function deleteClassExam(classExamId) {
	var classId = parseInt(GetURLParameter('classId'));
	$.when(ajax.remove("classinfo/"+classId+"/classexam/",classExamId)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				initClassExamTbl();
				defineVariables();
				initAvailableExamsTbl();
				break;
		}
	});
}

function attachListenerToSearchBox() {
	$("#searchExamTxtBox").on("keyup",function() {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == 13) {
			var keyword = $(this).val();
			searchExam(keyword);
		}
		
		if(keycode == 8 && $("#searchExamTxtBox").val().length == 0) {
			initAvailableExamsTbl();
		}
	});
	
	$("#clearSearch").unbind("click").click(function(){
		$("#searchExamTxtBox").val("");
		initAvailableExamsTbl();
	});
}

function searchExam(keyword) {
	var classId = parseInt(GetURLParameter('classId'));
	$("#examTblPagination").pagination({     
        dataSource: "examinfos/active/search/class",
        locator: "examInfos",
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        ajax: {data:{
        	status:Status.ACTIVE,
        	keyword:keyword,
        	examType:getSearchExamTypeDesc(keyword),
        	classId:classId},
        	type:"POST"},
        className: "paginationjs-theme-blue",
        pageSize: 5,     
        callback: function(data) {                      
            $("#examTblBody").html("");
            availableExamList = data;
            _.each(data, function(examInfo) {
            	createExamRow(examInfo);
            });
        }
    });
}

function getSearchExamTypeDesc(keyword) {
	var result = 0;
	_.each(ExamTypes,function(examType) {
		if(examType.description.toLowerCase()==keyword.toLowerCase()) {
			result = examType.id;
		}
	});
	return result;
}

function attachListenerToClearIcon() {
	$("#clearSearch").bind("click",function() {
		$("input[type='search']").val("");
		initExamTbl();
	});
}

function attachListenerToAddClassExamBtn() {
	$("#addClassExamBtn").on("click",function() {
		if(checkedList.length==0) {
			alertify.warning("Please select at least one exam from the table.");
		} else {
			createClassExam(checkedList);
		}
	});
}

function createClassExam(classExam) {
	var classId = parseInt(GetURLParameter('classId'));
	$.when(ajax.customUpdate("classinfo/"+classId+"/classexams",classExam)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully added!");
				initClassExamTbl();
				defineVariables();
				initAvailableExamsTbl();
				break;
		}
	});
}

function attachListenerToSaveExamSettings() {
	$("#btnSaveExamSettings").on("click",function() {
		var classExam=getExamSettingsData();
		if(validateExamSettings()) {
			updateClassExam(classExam);
		}
	});
}

function updateClassExam(classExam) {
	var classId = parseInt(GetURLParameter('classId'));
	$.when(ajax.customUpdate("classinfo/"+classExam.classExamId+"/classexam/settings",classExam))
		.done(function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					$("#examSettingsModal").modal("close");
					alertify.success("Successfully updated!");
					initClassExamTbl();
					defineVariables();
					initAvailableExamsTbl();
					clearExamSettings();
					break;
			}
	});
}

function getExamSettingsData() {
	var classExam={};
	classExam.classExamId=parseInt($("#classExamId").val());
	classExam.duration=parseInt($("#tbClassDuration").val());
	classExam.passingScore=parseFloat($("#tbPassingScore").val());
	classExam.examRetake=getExamRetakeValue();
	classExam.noOfRetake=parseInt($("#tbExamRetake").val());
	classExam.isSafeBrowser=getCheckBoxValue("#chIsSafeBrowser");
	classExam.isShowCorrectAnswer=getCheckBoxValue("#chShowCorrectAnswer");
	classExam.isShowScore=getCheckBoxValue("#chShowScore");
	classExam.isShowBreakDown=getCheckBoxValue("#chShowBreakdown");
	return classExam;
}

function getExamRetakeValue() {
	var result=0;
	if(getCheckBoxValue("#chOnlyFailed")==1) {
		result=1;
	} else if(getCheckBoxValue("#chIfPassed")==1) {
		result=2;
	}
	return result;
}

function validateExamSettings() {
	var result=true;
	if(getExamRetakeValue()>0 && $("#tbExamRetake").val()=="") {
		alertify.warning("Please specify a number of retake");
		result=false;
	}
	return result;
}

function attachListenerToExamSettingsCancel() {
	$("#btnCancelExamSettings").on("click",function() {
		clearExamSettings();
	});
}

function clearExamSettings() {
	$("#courseExamId").val("");
	$("#tbExamRetake").val("");
	$("#chIsSafeBrowser").prop("checked","");
	$("#chShowCorrectAnswer").prop("checked","");
	$("#chShowScore").prop("checked","");
	$("#chShowBreakdown").prop("checked","");
	$("#chOnlyFailed").prop("checked","");
	$("#chOnlyFailed").prop("disabled",false);
	$("#chIfPassed").prop("checked","");
	$("#chIfPassed").prop("disabled",false);
}

function attachListenerToPassingScoreTxtBox() {
	$("#tbPassingScore").on("keyup",function() {
		var input=$(this).val();
		if(input>100) {
			$(this).val("");
		}
	});
}