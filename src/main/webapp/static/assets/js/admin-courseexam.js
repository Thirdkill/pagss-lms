$(function() {
	defineCheckedList();
	initCourseExamTbl();
	initExamTbl();
	attachListenerToExamCheckBox();
	attachListenerToSearchBox();
	attachListenerToClearIcon();
	attachListenerToAddCourseExamBtn();
	attachListenerToSaveExamSettings();
	attachListenerToOnlyWhenFailedChkBox();
	attachListenerToEvenIfPassedCheckBox();
	attachListenerToExamSettingsCancel();
	attachListenerToPassingScoreTxtBox();
});

function defineCheckedList() {
	availableExamList = [];
	checkedList = [];
	courseExamList=[];
}

function initCourseExamTbl() {
	var courseId = parseInt($("#courseId").val());
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
        }
    });
}

function createCourseExamRow(courseExam) {
	$("#courseExamTblBody").append($("<tr/>")
		.append($("<td/>").addClass("mid").html(courseExam.title))
		.append($("<td/>").addClass("mid").html(getExamTypeDesc(courseExam.examType)))
		.append($("<td/>").addClass("mid").html(courseExam.description))
		.append($("<td/>").addClass("min")
			.append($("<a/>").addClass("waves-light waves-effect modal-trigger").attr({
				"href":"#!",
				"data-target":"examSettingsModal",
				"data-courseexamid":courseExam.courseExamId,
				"data-courseid":courseExam.courseId
				}).bind("click",function() {
					populateExamSettings($(this).data("courseexamid"));
				}).append($("<i/>").addClass("material-icons").html("settings")))
			.append($("<a/>").addClass("waves-light waves-effect").attr({
				"href":"admin.examination?action=update&module=courseexam&courseId="+courseExam.courseId+
					"&examId="+courseExam.examId,
				"data-courseexamid":courseExam.courseExamId,
				"data-courseid":courseExam.courseId
				}).append($("<i/>").addClass("material-icons").html("edit")))
			.append($("<a/>").addClass("waves-light waves-effect").attr({
				"href":"#!",
				"data-courseexamid":courseExam.courseExamId,
				"data-courseid":courseExam.courseId
				}).bind("click",function() {
					deleteCourseExam($(this).data("courseexamid"));
				}).append($("<i/>").addClass("material-icons").html("delete")))));
}

function populateExamSettings(courseExamId) {
	var courseExam = getCourseExamObj(courseExamId);
	$("#courseExamId").val(courseExam.courseExamId);
	$("#durationTxtBox").val((courseExam.duration!=0)?courseExam.duration:"30");
	$("#passingScoreTxtBox").val((courseExam.passingScore!=null)?courseExam.passingScore:"75");
	$("#noOfRetakeTxtBox").val(courseExam.noOfRetake);
	setCheckBoxValue("#isSafeBrowserChkBox",courseExam.isSafeBrowser);
	setCheckBoxValue("#isShowCorrectAnswerChkBox",courseExam.isShowCorrectAnswer);
	setCheckBoxValue("#isShowScoreChkBox",courseExam.isShowScore);
	setCheckBoxValue("#isShowBreakdownChkBox",courseExam.isShowBreakDown);
	$("#onlyWhenFailedChkBox").trigger("change");
	$("#evenIfPassedChkBox").trigger("change");
	if(courseExam.examRetake==ExamRetake.ONLY_WHEN_FAILED) {
		setCheckBoxValue("#onlyWhenFailedChkBox",Status.ACTIVE);
		$("#evenIfPassedChkBox").prop("disabled",true);
		$("#noOfRetakeTxtBox").prop("disabled",false);
	}
	if(courseExam.examRetake==ExamRetake.EVEN_IF_PASSED) {
		setCheckBoxValue("#evenIfPassedChkBox",Status.ACTIVE);
		$("#onlyWhenFailedChkBox").prop("disabled",true);
		$("#noOfRetakeTxtBox").prop("disabled",false);
	}	
}

function getCourseExamObj(courseExamId) {
	var tempCourseExam={};
	_.each(courseExamList,function(courseExam) {
		if(courseExam.courseExamId==courseExamId) {
			tempCourseExam=courseExam;
		}
	});
	return tempCourseExam;
}	

function deleteCourseExam(courseExamId) {
	var courseId = parseInt($("#courseId").val());
	$.when(ajax.remove("courseinfo/"+courseId+"/courseexam/",courseExamId)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				initCourseExamTbl();
				initExamTbl();
				break;
		}
	});
}

function initExamTbl() {
	var courseId=$("#courseId").val();
	$("#examTblPagination").pagination({     
        dataSource: "examinfos/active/pages",
        locator: "examInfos",
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        ajax: {data:{status:Status.ACTIVE,courseId:courseId},type:"GET"},
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
	$("#examTblBody").append($("<tr/>")
		.append($("<td/>").addClass("mins")
			.append($("<label/>")
				.append($("<input/>").attr({
					"type":"checkbox",
					"data-examid":examInfo.examId
				}).addClass("examCheckBox").prop("checked",checkIfItemExist(examInfo.examId)))
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

function checkIfItemExist(examId) {
	var result = false;
	_.each(checkedList,function(examInfo) {
		if(examInfo.examId==examId) {
			result=true;
		}
	});
	return result;
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


function attachListenerToExamCheckBox() {
	$("body").on("change",".examCheckBox",function() {
		var examId=$(this).data("examid");
		if($(this).prop("checked")) {
			checkedList.push(getExamInfoObject(examId));
		} else {
			checkedList = removeCheckedListObj(examId);
		}
	});
}

function getExamInfoObject(examId) {
	var tempExamInfo = {};
	_.each(availableExamList,function(examInfo) {
		if(examInfo.examId==examId) {
			examInfo.courseId=$("#courseId").val();
			tempExamInfo=examInfo;
		}
	});
	return tempExamInfo;
}

function removeCheckedListObj(examId) {
	var tempArray = [];
	_.each(checkedList,function(examInfo) {
		if(examInfo.examId != examId) {
			tempArray.push(examInfo);
		}
	});
	return tempArray;
}

function attachListenerToSearchBox() {
	$("#searchExamTxtBox").on("keypress",function() {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == 13) {
			var keyword = $(this).val();
			searchExam(keyword);
		}
	});
}

function searchExam(keyword) {
	var courseId=$("#courseId").val(); 
	$("#examTblPagination").pagination({     
        dataSource: "examinfos/active/search",
        locator: "examInfos",
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        ajax: {data:{
        	status:Status.ACTIVE,
        	keyword:keyword,
        	examType:getSearchExamTypeDesc(keyword),
        	courseId:courseId},
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

function attachListenerToAddCourseExamBtn() {
	$("#addCourseExamBtn").on("click",function() {
		var courseInfo = {};
		courseInfo.courseExams = checkedList;
		if(checkedList.length==0) {
			alertify.warning("Please select at least one exam from the table.");
		} else {
			createCourseExam(courseInfo);
		}
	});
}

function createCourseExam(courseInfo) {
	var courseId=parseInt($("#courseId").val());
	$.when(ajax.create("courseinfo/"+courseId+"/courseexams",courseInfo)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully added!");
				initCourseExamTbl();
				checkedList = [];
				initExamTbl();
				break;
		}
	});
}

function attachListenerToSaveExamSettings() {
	$("#saveExamSettingsBtn").on("click",function() {
		var courseExam=getExamSettingsData();
		if(validateExamSettings()) {
			updateCourseExam(courseExam);
		}
	});
}

function updateCourseExam(courseExam) {
	var courseId=$("#courseId").val();
	var courseExamId=courseExam.courseExamId;
	$.when(ajax.update("courseinfo/"+courseId+"/courseexam/",courseExamId,courseExam))
		.done(function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					$("#examSettingsModal").modal("close");
					alertify.success("Successfully created!");
					initCourseExamTbl();
					clearExamSettings();
					break;
			}
	});
}

function getExamSettingsData() {
	var courseExam={};
	courseExam.courseExamId=$("#courseExamId").val();
	courseExam.duration=$("#durationTxtBox").val();
	courseExam.passingScore=$("#passingScoreTxtBox").val();
	courseExam.examRetake=getExamRetakeValue();
	courseExam.noOfRetake=$("#noOfRetakeTxtBox").val();
	courseExam.isSafeBrowser=getCheckBoxValue("#isSafeBrowserChkBox");
	courseExam.isShowCorrectAnswer=getCheckBoxValue("#isShowCorrectAnswerChkBox");
	courseExam.isShowScore=getCheckBoxValue("#isShowScoreChkBox");
	courseExam.isShowBreakDown=getCheckBoxValue("#isShowBreakdownChkBox");
	return courseExam;
}

function getExamRetakeValue() {
	var result=0;
	if(getCheckBoxValue("#onlyWhenFailedChkBox")==1) {
		result=1;
	} else if(getCheckBoxValue("#evenIfPassedChkBox")==1) {
		result=2;
	}
	return result;
}

function validateExamSettings() {
	var result=true;
	if(getExamRetakeValue()>0 && $("#noOfRetakeTxtBox").val()=="") {
		alertify.warning("Please specify a number of retake");
		result=false;
	}
	return result;
}

function attachListenerToOnlyWhenFailedChkBox() {
	$("#onlyWhenFailedChkBox").on("change",function() {
		if($(this).prop("checked")) {
			$("#evenIfPassedChkBox").prop("checked","");
			$("#evenIfPassedChkBox").prop("disabled",true);
			$("#noOfRetakeTxtBox").prop("disabled",false);
		} else {
			$("#evenIfPassedChkBox").prop("disabled",false);
			$("#noOfRetakeTxtBox").prop("disabled",true);
		}
	});
}

function attachListenerToEvenIfPassedCheckBox() {
	$("#evenIfPassedChkBox").on("change",function() {
		if($(this).prop("checked")) {
			$("#onlyWhenFailedChkBox").prop("checked","");
			$("#onlyWhenFailedChkBox").prop("disabled",false);
		} else {
			$("#onlyWhenFailedChkBox").prop("disabled",false);
			$("#noOfRetakeTxtBox").prop("disabled",true);
		}
	});
}

function attachListenerToExamSettingsCancel() {
	$("#examSettingsCancelBtn").on("click",function() {
		clearExamSettings();
	});
}

function clearExamSettings() {
	$("#courseExamId").val("");
	$("#noOfRetakeTxtBox").val("");
	$("#isSafeBrowserChkBox").prop("checked","");
	$("#isShowCorrectAnswerChkBox").prop("checked","");
	$("#isShowScoreChkBox").prop("checked","");
	$("#isShowBreakdownChkBox").prop("checked","");
	$("#onlyWhenFailedChkBox").prop("checked","");
	$("#onlyWhenFailedChkBox").prop("disabled",false);
	$("#evenIfPassedChkBox").prop("checked","");
	$("#evenIfPassedChkBox").prop("disabled",false);
}

function attachListenerToPassingScoreTxtBox() {
	$("#passingScoreTxtBox").on("keyup",function() {
		var input=$(this).val();
		if(input>100) {
			$(this).val("");
		}
	});
}