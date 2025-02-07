/**
 * 
 */

$(function(){
	defineVariables();
	initClassInfo();
	initClassEvaluationTbl();
	initCourseEvaluationTbl();
	initAvailableEvaluationTbl();
	attachListenerToSearchBox();
	attachListenerToAddClassEvalBtn();
	attachListenerForUpdateClassEvalSettings();
	attachListenerToFetchTraineesEvalTbl();
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
	
	if($("#deliveryMethod").val() == CheckedValue.CHECKED){
		$("#deliveryMethodLabel").html("Individual");
	} else if($("#deliveryMethod").val() == CheckedValue.UNCHECKED){
		$("#deliveryMethodLabel").html("Modular");
	}
	
	if($("#isRequired").val() == IsSelfRegister.TRUE){
		$("#chIsRequired").prop("checked",true);
	} else if($("#isRequired").val() == IsSelfRegister.FALSE){
		$("#chIsRequired").prop("checked",false);
	}
	if($("#isAnonymousSender").val() == IsAnonymousSender.TRUE){
		$("#chIsAnonymousSender").prop("checked",true);
	} else if($("#isAnonymousSender").val() == IsAnonymousSender.FALSE){
		$("#chIsAnonymousSender").prop("checked",false);
	}
}

function initClassEvaluationTbl() {
	var pageSize = 5;
	var classId = parseInt(GetURLParameter('classId'));
	$("#classEvalTblPagination").pagination({     
        dataSource: "classinfo/"+classId+"/classevaluation/pages",
        locator: 'classevaluations',
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        className: 'paginationjs-theme-blue',
        pageSize: pageSize,     
        callback: function(data) {            
        	$("#classEvalTblBody").empty();
            _.each(data, function(classEval) {
            	createClassEvaluationRow(classEval);
            });
            classList = data;
            populateEvalViewByClassDropdown(data);
        }
    });
}

function createClassEvaluationRow(classEval) {
	$("#classEvalTblBody").append($("<tr/>")
		.append($("<td/>").addClass("mins")
			.append($("<a/>").addClass("waves-light waves-effect").attr({
				"href":"admin.updateevaluation?action=EDIT&id="+classEval.evaluationId+
				"&classId="+classEval.classId,
				"data-classevaluationid":classEval.classEvaluationId,
				"data-classid":classEval.classId
				}).append($("<i/>").addClass("material-icons").html("edit")))
			.append($("<a/>").addClass("waves-light waves-effect").attr({
				"href":"#!",
				"data-classevaluationid":classEval.classEvaluationId,
				"data-classid":classEval.classId
				}).bind("click",function() {
					deleteClassEvaluation($(this).data("classevaluationid"));
				}).append($("<i/>").addClass("material-icons").html("delete"))))
		.append($("<td/>").attr("style","text-align:left;")
		.html(classEval.title)));
}

function initCourseEvaluationTbl() {
	var pageSize = 5;
	var courseId = $("#tbCourseId").val();
	$("#courseEvalTblPagination").pagination({     
        dataSource: "courseinfo/"+courseId+"/courseevaluations/pages",
        locator: 'courseevaluations',
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        className: 'paginationjs-theme-blue',
        pageSize: pageSize,     
        callback: function(data) {            
        	$("#courseEvalTblBody").empty();
            _.each(data, function(courseEval) {
            	createCourseEvaluationRow(courseEval);
            });
            courseList = data;
//            populateEvalViewByCourseDropdown(data);
        }
    });
}

function createCourseEvaluationRow(courseEval) {
	var classId = parseInt(GetURLParameter('classId'));
	$("#courseEvalTblBody").append($("<tr/>")
		.append($("<td/>").addClass("mins")
			.append($("<a/>").addClass("waves-light waves-effect").attr({
				"href":"admin.updateevaluation?action=EDIT&id="+courseEval.evaluationId+
				"&classId="+classId,
				"data-courseevaluationid":courseEval.courseEvaluationId,
				"data-courseid":courseEval.courseId
				}).append($("<i/>").addClass("material-icons").html("edit"))))
		.append($("<td/>").attr("style","text-align:left;")
		.html(courseEval.title)));
}

function attachListenerToClearIcon() {
	$("#clearSearch").bind("click",function() {
		$("input[type='search']").val("");
		initExamTbl();
	});
}

function initAvailableEvaluationTbl() {
	var courseId = $("#tbCourseId").val();
	var classId = parseInt(GetURLParameter('classId'));
	$("#evalTblPagination").pagination({     
        dataSource: "evaluations/active/pages/classevaluation",
        locator: "evaluations",
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        ajax: {data:{
        	classId:classId,
        	courseId:courseId},
        	type:"GET"},
        className: "paginationjs-theme-blue",
        pageSize: 5,     
        callback: function(data) {                      
            $("#evalTblBody").html("");
            _.each(data, function(evalInfo) {
            	createEvaluationRow(evalInfo);
            });
        }
    });
}

function attachListenerToSearchBox() {
	$("#searchEvalTxtBox").on("keyup",function() {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == 13) {
			var keyword = $(this).val();
			searchAvailableEvaluationTbl(keyword);
		}
		
		if(keycode == 8 && $("#searchEvalTxtBox").val().length == 0) {
			initAvailableEvaluationTbl();
		}
	});
	
	$("#clearSearch").unbind("click").click(function(){
		$("#searchEvalTxtBox").val("");
		initAvailableEvaluationTbl();
	});
}

function searchAvailableEvaluationTbl(keyword) {
	var courseId = $("#tbCourseId").val();
	var classId = parseInt(GetURLParameter('classId'));
	$("#evalTblPagination").pagination({     
        dataSource: "evaluations/active/search/classevaluation",
        locator: "evaluations",
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        ajax: {data:{
        	keyword:keyword,
        	classId:classId,
        	courseId:courseId},
        	type:"POST"},
        className: "paginationjs-theme-blue",
        pageSize: 5,     
        callback: function(data) {                      
            $("#evalTblBody").html("");
            _.each(data, function(evalInfo) {
            	createEvaluationRow(evalInfo);
            });
        }
    });
}

function createEvaluationRow(evalInfo) {
	var classId = parseInt(GetURLParameter('classId'));
	$("#evalTblBody").append($("<tr/>")
		.append($("<td/>").addClass("mins")
			.append($("<label/>")
				.append($("<input/>").attr({"type":"checkbox"})
				.prop("checked",checkIfItemExist(evalInfo.evaluationId))
				.bind("change",function() {
					if($(this).prop("checked") != true) {
						if(checkIfItemExist(evalInfo.evaluationId)) {
							deleteClassEvalFromCheckList(evalInfo);
						}
					} else {
						if(!checkIfItemExist(evalInfo.evaluationId)) {
							evalInfo.classId = classId;
							checkedList.push(evalInfo);
						}
					}
				}))
				.append($("<span/>"))))
		.append($("<td/>").attr("style","text-align:left;")
		.html(evalInfo.title)));
}

function populateEvalViewByClassDropdown(classEvals) {
	$("#employeeEvalViewByDropdown").empty();
	$("#employeeEvalViewByDropdown").append($("<option/>")
			.attr({"disabled":"disabled","selected":"selected"})
			.html("Choose An Option"));
	_.each(classEvals, function(classEval) {
		$("#employeeEvalViewByDropdown").append($("<option/>")
			.attr({"value":classEval.classEvaluationId,"data-evaltype":"class"})
			.html(classEval.title));
	});
}

function populateEvalViewByCourseDropdown(courseEvals) {
	_.each(courseEvals, function(courseEval) {
		$("#employeeEvalViewByDropdown").append($("<option/>")
			.attr({"value":courseEval.evaluationId,"data-evaltype":"course"})
			.html(courseEval.title));
	});
}

function checkIfItemExist(evalId) {
	var result = false;
	_.each(checkedList,function(evalInfo) {
		if(evalInfo.evaluationId==evalId) {
			result=true;
		}
	});
	return result;
}

function deleteClassEvalFromCheckList(evalInfo) {
	var result = false;
	_.each(checkedList,function(ceObj) {
		if(ceObj.evaluationId == evalInfo.evaluationId) {
			checkedList = deleteItem(evalInfo);
		}
	});
	return result;
}

function deleteItem(evalInfo) {
	var resultArray = [];
	_.each(checkedList,function(ceObj) {
		if(ceObj.evaluationId != evalInfo.evaluationId) {
			resultArray.push(ceObj);
		}
	});
	return resultArray;
}

function attachListenerForUpdateClassEvalSettings(){
	var classId = parseInt(GetURLParameter('classId'));
	$("#btnSaveEvalSettings").on("click",function() {
		$("#btnSaveEvalSettings").attr("disabled","disabled");
		$("#btnSaveEvalSettings").html("SAVING...");
		classInfo = {};
		classInfo.classId = classId;
		if($("#chIsRequired").prop("checked") == true) {
			classInfo.isEvaluationRequired = IsSelfRegister.TRUE;
		} else if($("#chIsRequired").prop("checked") != true) {
			classInfo.isEvaluationRequired = IsSelfRegister.FALSE;
		}
		
		if($("#chIsAnonymousSender").prop("checked") == true) {
			classInfo.isAnonymousSender = IsAnonymousSender.TRUE;
		} else if($("#chIsAnonymousSender").prop("checked") != true) {
			classInfo.isAnonymousSender = IsAnonymousSender.FALSE;
		}
		
		$.when(ajax.customUpdate("classinfo/"+classId+"/classevaluations/settings",classInfo)).done(function(response) {
			if(response.status == HttpStatus.SUCCESS){
				alertify.success("Successfully Updated.");
				$("#btnSaveEvalSettings").removeAttr("disabled");
				$("#btnSaveEvalSettings").html("<i class='material-icons left'>save</i>SAVE SETTINGS");
			}
		});
	});
}

function attachListenerToAddClassEvalBtn() {
	$("#addClassEvalBtn").on("click",function() {
		if(checkedList.length==0) {
			alertify.warning("Please select at least one evaluation from the table.");
		} else {
			createClassEvaluation(checkedList);
		}
	});
}

function createClassEvaluation(classEval) {
	var classId = parseInt(GetURLParameter('classId'));
	$.when(ajax.customUpdate("classinfo/"+classId+"/classevaluations",classEval)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully added!");
				defineVariables();
				initClassEvaluationTbl();
				initAvailableEvaluationTbl();
				break;
		}
	});
}

function deleteClassEvaluation(classEvaluationId) {
	var classId = parseInt(GetURLParameter('classId'));
	$.when(ajax.remove("classinfo/"+classId+"/classevaluation/",classEvaluationId)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				defineVariables();
				initClassEvaluationTbl();
				initAvailableEvaluationTbl();
				break;
		}
	});
}

function attachListenerToFetchTraineesEvalTbl() {
	$("#employeeEvalViewByDropdown").on("change",function() {
		var classEvaluationId = parseInt($(this).val());
		$("#traineeEvalTblPagination").pagination({     
	        dataSource: "evaluations/employee-evaluations",
	        locator: "employeeevaluations",
	        totalNumberLocator: function(response) {            
	            return response.totalRecords;
	        },
	    	ajax:{data:{classEvaluationId:classEvaluationId},type:"POST"},
	        className: "paginationjs-theme-blue",
	        pageSize: 10,     
	        callback: function(data) {                      
	            $("#traineeEvalTblBody").empty();
	            courseExamList = data;
	            var functionName = eval()
	            _.each(data, function(employeeEval) {
	            	createEmployeeEvalRow(employeeEval);
	            });
	        }
	    });
	});
}

function createEmployeeEvalRow(employeeEval) {
	$("#traineeEvalTblBody").append($("<tr/>")
		.append($("<td/>")
			.append($("<a/>").addClass("material-icons")
			.html("<i class='material-icons'>remove_red_eye</i>"))
			.append($("<a/>").addClass("material-icons")
			.html("<i class='material-icons'>file_download</i>")))
		.append($("<td/>").html(employeeEval.fullName))
		.append($("<td/>").html(employeeEval.employeeCode))
		.append($("<td/>").html(employeeEval.jobName))
		.append($("<td/>").html(employeeEval.dateCompleted)))
}