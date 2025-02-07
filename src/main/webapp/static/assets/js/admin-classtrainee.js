$(function() {
	removeTabIndex();
	defineGlobalVariables();
	initUserGroupDropdown();
	initJobRoleDropdown();
	initTraineeTbl();
	initApprovedTraineeTbl();
	initPendingTraineeTbl();
	initGradeSheetHeader();
	initClassEmployeeAssessmentsList();
	attachListenerToFilterGroup();
	attachListenerToJobRoleDropdown();
	attachListenerToUserGroupDropdown();
	attachListenerToSearchTxtBox();
	attachListenerToCheckAll();
	attachListenerToAddTraineeBtn();
	attachListenerToCancelTraineeBtn();
	attachListenerToCompleteChkBox();
	attachListenerToSetAsCompletedBtn();
	attachListenerToUploadPhotoLink();
	attachListenerToUploadPhoto();
});

function removeTabIndex() {
	$("#addTraineesModal").removeAttr("tabindex");
}

function defineGlobalVariables() {
	employeeInfoCheckedList = [];
	employeeInfoList = [];
	approvedTraineeList = [];
	pendingTraineeList = [];
	completedTraineeList = [];
	gradingComponentsList = [];
	classEmployeeAssessmentList = [];
}

function initUserGroupDropdown() {
	$.when(ajax.fetch("usergroups")).done(function(response) {
		var usergroups = response.usergroups;
		populateUserGroupDropdown(usergroups);
	});
}

function populateUserGroupDropdown(userGroups) {
	$("#userGroupDropdown").html("");
	$("#userGroupDropdown").append($("<option/>").prop({
		"disabled":true,"selected":true,"value":"0"}).html("Choose an option"));
	_.each(userGroups,function(userGroup) {
		$("#userGroupDropdown").append($("<option/>").attr({"value":userGroup.userGroupId})
			.html(userGroup.userGroupName));
	});
}

function initJobRoleDropdown() {
	$.when(ajax.fetch("jobroles")).done(function(response) {
		var jobRoles = response.jobRoles;
		populateJobRoleDropdown(jobRoles);
	});
}

function populateJobRoleDropdown(jobRoles) {
	$("#jobRoleDropdown").html("");
	$("#jobRoleDropdown").append($("<option/>").prop({
		"disabled":true,"selected":true,"value":"0"}).html("Choose an option"));
	_.each(jobRoles,function(jobRole) {
		$("#jobRoleDropdown").append($("<option/>").attr({"value":jobRole.jobRoleId}).html(jobRole.jobName));
	})
}

function initTraineeTbl() {
	var classId = $("#classId").val();
	$("#traineePagination").pagination({     
        dataSource: "employeeinfo/trainees/pages",
        locator: "employeeInfos",
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        ajax: {data:{classId:classId},type:"POST"},
        className: "paginationjs-theme-blue",
        pageSize: 5,     
        callback: function(data) { 
            $("#traineeTblBody").html("");
            employeeInfoList = data;
            createTraineeHeader("jobrole");
            _.each(data, function(employeeInfo) {
            	createTraineeRow(employeeInfo,"jobrole");
            });
        }
    });
}

function createTraineeHeader(filterType) {
	$("#traineeTblHeader").html("");
	$("#traineeTblHeader").append($("<tr/>")
		.append($("<th/>")
			.append($("<label/>").css("margin-left","15px !important")
				.append($("<input/>").addClass("checkAll").attr({"type":"checkbox"})
					.prop("checked",(countNoOfCheckedObj(employeeInfoList)==employeeInfoList.length)?true:false))
				.append($("<span/>"))))
		.append($("<th/>").html("Last Name")
			.append($("<a/>").attr({"href":"#!"}).addClass("sort-by")))
		.append($("<th/>").html("First Name")
			.append($("<a/>").attr({"href":"#!"}).addClass("sort-by")))
		.append($("<th/>").html("Employee Code")
			.append($("<a/>").attr({"href":"#!"}).addClass("sort-by")))
		.append($("<th/>").html((filterType=="jobrole")?"Job Role":"User Group")
			.append($("<a/>").attr({"href":"#!"}).addClass("sort-by"))));
}

function countNoOfCheckedObj(employeeInfos) {
	var count = 0;
	_.each(employeeInfos,function(employeeInfo) {
		_.each(employeeInfoCheckedList,function(checkedEmployeeInfo) {
			if(checkedEmployeeInfo.employeeId==employeeInfo.employeeId) {
				count++;
			}
		})
	});
	return count;
}

function createTraineeRow(employeeInfo,filterType) {
	$("#traineeTblBody").append($("<tr/>")
		.append($("<td/>")
			.append($("<label/>").css("margin-left","15px !important")
				.append($("<input/>").addClass("employeeChkBox").attr({
					"type":"checkbox",
					"data-id":employeeInfo.employeeId})
				.prop("checked",checkIfObjExist(employeeInfo.employeeId)).bind("change",function() {
					if($(this).prop("checked")) {
						employeeInfoCheckedList.push(getEmployeeInfoObj($(this).data("id")));
					} else {
						employeeInfoCheckedList = removeEmployeeInfoObj($(this).data("id"));
						$(".checkAll").prop("checked",false);
					}
				})).append($("<span/>"))))
		.append($("<td/>").html(employeeInfo.lastName))
		.append($("<td/>").html(employeeInfo.firstName))
		.append($("<td/>").html(employeeInfo.employeeCode))
		.append($("<td/>").html((filterType=="jobrole")?employeeInfo.jobName:employeeInfo.userGroupName)));
}


function getEmployeeInfoObj(employeeInfoId) {
	var employeeInfoObj = {};
	_.each(employeeInfoList,function(employeeInfo) {
		if(employeeInfo.employeeId == employeeInfoId) {
			employeeInfo.classId = $("#classId").val();
			employeeInfo.role=Role.TRAINEE;
			employeeInfoObj = employeeInfo;
		}
	});
	return employeeInfoObj;
}

function removeEmployeeInfoObj(employeeId) {
	var tempEmployeeInfoArray = [];
	_.each(employeeInfoCheckedList,function(employeeInfo) {
		if(employeeInfo.employeeId!=employeeId) {
			tempEmployeeInfoArray.push(employeeInfo);
		}
	});
	return tempEmployeeInfoArray;
}

function checkIfObjExist(employeeId) {
	var result = false;
	_.each(employeeInfoCheckedList,function(employeeInfo) {
		if(employeeInfo.employeeId==employeeId) {
			result = true;
		}
	});
	return result;
}

function initApprovedTraineeTbl() {
	var classId = $("#classId").val();
	$("#AppprovedTraineePagination").pagination({     
        dataSource: "classinfo/"+classId+"/classemployee/trainee/approvalstatus/"+ApprovalStatus.APPROVED,
        locator: "classEmployees",
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        className: "paginationjs-theme-blue",
        pageSize: 5,     
        callback: function(data) { 
            $("#ApprovedTraineeTblBody").html("");
            approvedTraineeList = data;
            _.each(data, function(classEmployee) {
            	createApprovedTraineeRow(classEmployee);
            });
        }
    });
}

function createApprovedTraineeRow(classEmployee) {
	var deliveryMethod=$("#deliveryMethod").val();
	$("#ApprovedTraineeTblBody").append($("<tr/>")
		.append($("<td/>")
			.append($("<label/>").css("margin-left","10px !important")
				.append((deliveryMethod==DeliveryMethod.CLASSTRAINING)?
					$("<input/>").addClass("completeChkBox").attr({"type":"checkbox","data-id":classEmployee.classEmployeeId})
					.prop("checked",checkIfExistInCompletedTraineeList(classEmployee.classEmployeeId)):"")
				.append($("<span/>")))
			.append($("<a/>").attr({"href":"#!","data-id":classEmployee.classEmployeeId})
				.append($("<i/>").addClass("material-icons")
				.bind("click",function() {
					var message = "Are you sure you want to remove this trainee in this class?"
					alertify.confirm("Delete Trainee", message, function(){ 
						removeClassEmployee(classEmployee.classEmployeeId);
					}, function(){}).set("labels",{ok:"Yes", cancel:"No"});
				}).html("delete"))))
		.append($("<td/>").html(classEmployee.fullName))
		.append($("<td/>").html(classEmployee.jobName))
		.append($("<td/>").html((classEmployee.userGroupName==null)?"NONE":classEmployee.userGroupName))
		.append($("<td/>").html(classEmployee.enrolledDate))
		.append($("<td/>").html((classEmployee.completedDate=="")?"N/A":classEmployee.completedDate))
		.append($("<td/>")
			.append($("<select/>").addClass("trainingStatus")
				.attr({"id":"trainingStatus_"+classEmployee.classEmployeeId,"data-id":classEmployee.classEmployeeId})
				.bind("change",function() {
					classEmployee.trainingStatus = $(this).val();
					classEmployee.comment = $("#comment_"+classEmployee.classEmployeeId).val();
					updateClassEmployee(classEmployee);
				}).append($("<option/>").attr("value","0").prop({"selected":true,"disabled":true})
					.html("Choose an option"))))
		.append($("<td/>")
			.append($("<input/>").addClass("commentTxtBox").attr({
				"type":"text",
				"value":classEmployee.comment,
				"data-id":classEmployee.classEmployeeId,
				"id":"comment_"+classEmployee.classEmployeeId})
			.bind("keyup",function() {
				classEmployee.trainingStatus = $("#trainingStatus_"+classEmployee.classEmployeeId).val();
				classEmployee.comment = $(this).val();
				updateClassEmployee(classEmployee);
			}))));
	appendTrainingStatusOptions(classEmployee.classEmployeeId,classEmployee.trainingStatus);
}

function checkIfExistInCompletedTraineeList(classEmployeeId) {
	var result = false;
	_.each(completedTraineeList,function(classEmployee) {
		if(classEmployee.classEmployeeId == classEmployeeId) {
			result = true;
		}
	});
	return result;
}

function updateClassEmployee(classEmployee) {
	var classId = $("#classId").val();
	$.when(ajax.customUpdate("classinfo/"+classId+"/classemployee/"+classEmployee.classEmployeeId,classEmployee))
		.done(function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					break;
			}
	});
}

function removeClassEmployee(classEmployeeId) {
	var classId = $("#classId").val();
	$.when(ajax.remove("classinfo/"+classId+"/classemployee/",classEmployeeId))
		.done(function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					initApprovedTraineeTbl();
					initTraineeTbl();
					break;
			}
	});
}

function appendTrainingStatusOptions(classEmployeeId,trainingStatus) {
	_.each(TrainingStatus.LIST,function(trainingStatusObj) {
		$("#trainingStatus_"+classEmployeeId)
			.append($("<option/>").attr("value",trainingStatusObj.id)
				.prop("selected",(trainingStatus==trainingStatusObj.id)?true:false)
				.html(trainingStatusObj.description));
	});
	$("#trainingStatus_"+classEmployeeId).trigger("change");
	$("#trainingStatus_"+classEmployeeId).select2();
}

function initPendingTraineeTbl() {
	var classId = $("#classId").val();
	$("#pendingTraineePagination").pagination({     
        dataSource: "classinfo/"+classId+"/classemployee/trainee/approvalstatus/"+ApprovalStatus.ALL_APPROVAL_STATUS,
        locator: "classEmployees",
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        className: "paginationjs-theme-blue",
        pageSize: 5,     
        callback: function(data) { 
            $("#pendingTraineeTblBody").html("");
            pendingTraineeList = data;
            _.each(data, function(classEmployee) {
            	createPendingTraineeRow(classEmployee);
            });
        }
    });
}

function createPendingTraineeRow(classEmployee) {
	$("#pendingTraineeTblBody").append($("<tr/>")
		.append($("<td/>")
			.append($("<a/>").attr("href","#!")
				.bind("click",function() {
					updateApprovalStatus(classEmployee.classEmployeeId,ApprovalStatus.APPROVED);
				}).append($("<i/>").addClass("material-icons")
					.html("thumb_up")))
		.append($("<a/>").attr("href","#!")
				.bind("click",function() {
					updateApprovalStatus(classEmployee.classEmployeeId,ApprovalStatus.DISAPPROVED);
				}).append($("<i/>").addClass("material-icons")
					.html("thumb_down"))))
		.append($("<td/>").html(classEmployee.fullName))
		.append($("<td/>").html(classEmployee.jobName))
		.append($("<td/>").html((classEmployee.userGroupName==null)?"NONE":classEmployee.userGroupName))
		.append($("<td/>").html(classEmployee.requestDate))
		.append($("<td/>").html(classEmployee.approvedDate))
		.append($("<td/>").html(getApprovalStatusDesc(classEmployee.approvalStatus))));
}

function updateApprovalStatus(classEmployeeId,approvalStatus) {
	var classId = $("#classId").val();
	$.when(ajax.customUpdate("classinfo/"+classId+"/classemployee/"+classEmployeeId+"/approvalstatus/"+approvalStatus))
		.done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				initApprovedTraineeTbl();
				initPendingTraineeTbl();
				break;
		}
	});
}

function getApprovalStatusDesc(approvalStatus) {
	var result = "";
	switch(approvalStatus) {
		case ApprovalStatus.APPROVED:
			result = "APPROVED";
			break;
		case ApprovalStatus.DISAPPROVED:
			result = "DISAPPROVED";
			break;
		case ApprovalStatus.ONGOING:
			result = "PENDING";
			break;
	}
	return result;
}

function initGradeSheetHeader() {
	var courseId = $("#courseId").val();
	$.when(ajax.fetch("courseinfo/"+courseId+"/gradingcomponents")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:  
				gradingComponentsList=response.gradingComponents;
				populateGradingSheetHeader(response.gradingComponents);
				break;
		}
	});
}

function populateGradingSheetHeader(gradingComponents) {
	gradingComponents.push(setGradingComponentObj("Job"));
	gradingComponents.push(setGradingComponentObj("Name"));
	_.each(gradingComponents.reverse(),function(gradingComponent) {
		$("#gradingSheetTblHeader").append($("<th/>").html(gradingComponent.componentDesc));
	});
	$("#gradingSheetTblHeader").append($("<th/>").html("Final Grade"))
		.append($("<th/>").html("Status"));
	initGradingSheetBody();
}

function setGradingComponentObj(componentDesc) {
	var gradingComponent = {};
	gradingComponent.gradingComponentId = 0;
	gradingComponent.componentDesc = componentDesc;
	return gradingComponent;
}

function initGradingSheetBody() {
	var classId = $("#classId").val();
	$("#gradingSheetPagination").pagination({     
        dataSource: "classinfo/"+classId+"/classemployee/trainee/approvalstatus/"+ApprovalStatus.APPROVED,
        locator: "classEmployees",
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        className: "paginationjs-theme-blue",
        pageSize: 5,     
        callback: function(data) { 
            $("#gradingSheetTblBody").html("");
            _.each(data, function(classEmployee) {
            	createGradingSheetRow(classEmployee);
            });
        }
    });
}

function createGradingSheetRow(classEmployee) {
	var passingGrade = 0;
	$("#gradingSheetTblBody").append($("<tr/>").attr("id","employeeNo_"+classEmployee.classEmployeeId)
		.append($("<td/>").html(classEmployee.fullName))
		.append($("<td/>").html(classEmployee.jobName)));
	_.each(gradingComponentsList,function(gradingComponent) {
		if(gradingComponent.gradingComponentId != 0) {
			passingGrade = gradingComponent.passingGrade;
			$("#employeeNo_"+classEmployee.classEmployeeId).append($("<td/>")
				.append($("<input/>").addClass("numbersOnly").attr({
					"type":"text",
					"id":"componentEmp"+classEmployee.classEmployeeId+"Comp"+gradingComponent.gradingComponentId,
					"data-employeeid":classEmployee.classEmployeeId,
					"data-componentid":gradingComponent.gradingComponentId,
					"data-percentage":gradingComponent.percentage,
					"value":getTraineeGrade(classEmployee.classEmployeeId,gradingComponent.gradingComponentId)})
				.bind("keyup",function(e) {
					var $this = $(this);
					var value=$this.val();
					var employeeId = $(this).data("employeeid");
					var componenId = $(this).data("componentid");
					setTimeout(function() {
						calculatedGrade = computeGrade(value,gradingComponent.percentage);
						$this.val(calculatedGrade);
						updateClassEmployeeAssessment(employeeId,componenId,calculatedGrade);
						$("#finalGrade_"+classEmployee.classEmployeeId).val(
							calculateFinalGrade(classEmployee.classEmployeeId));
						$("#gradeStatus_"+classEmployee.classEmployeeId).val(
							checkIfPassOrFailed(classEmployee.classEmployeeId,passingGrade));
					},1000);
				})));
		}
	});
	$("#employeeNo_"+classEmployee.classEmployeeId).append($("<td/>")
		.append($("<input/>").attr({
			"type":"text",
			"data-employeeid":classEmployee.classEmployeeId,
			"id":"finalGrade_"+classEmployee.classEmployeeId,
			"value":calculateFinalGrade(classEmployee.classEmployeeId)})
			.prop("disabled",true)));
	$("#employeeNo_"+classEmployee.classEmployeeId).append($("<td/>")
			.append($("<input/>").attr({
				"type":"text",
				"data-employeeid":classEmployee.classEmployeeId,
				"id":"gradeStatus_"+classEmployee.classEmployeeId,
				"value":checkIfPassOrFailed(classEmployee.classEmployeeId,passingGrade)})
				.prop("disabled",true)));
}

function checkIfPassOrFailed(classEmployeeId,passingGrade) {
	var finalGrade = parseFloat($("#finalGrade_"+classEmployeeId).val());
	var result = "Failed";
	if(finalGrade != 0) {
		if(finalGrade >= passingGrade) {
			result = "Passed";
		}
	} else {
		result = "";
	}
	return result;
}

function calculateFinalGrade(employeeId) {
	var finalGrade=0;
	_.each(gradingComponentsList,function(gradingComponent) {
		if(gradingComponent.gradingComponentId != 0) {
			var employeeGrade = $("#componentEmp"+employeeId+"Comp"+gradingComponent.gradingComponentId).val();
			finalGrade=finalGrade+parseFloat(employeeGrade);
		}
	});
	return finalGrade;
}

function computeGrade(value,percentage) {
	var calculatedGrade = value * parseFloat(percentage);
	return calculatedGrade;
} 

function updateClassEmployeeAssessment(employeeId,gradingComponentId,score) {
	var classId = $("#classId").val();
	var classEmployeeAssessment = {};
	classEmployeeAssessment.classId = classId;
	classEmployeeAssessment.employeeId = employeeId;
	classEmployeeAssessment.gradingComponentId = gradingComponentId;
	classEmployeeAssessment.score = score;
	$.when(ajax.create("classinfo/"+classId+"/classemployeeassessment",classEmployeeAssessment)).done(
		function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					break;
			}
	});
}

function initClassEmployeeAssessmentsList() {
	var classId = $("#classId").val();
	$.when(ajax.fetch("classinfo/"+classId+"/classemployeeassessments")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				classEmployeeAssessmentList = response.classEmployeeAssessments;
				break;
		}
	})
}

function getTraineeGrade(employeeId,gradingComponentId) {
	var grade = 0;
	_.each(classEmployeeAssessmentList,function(classEmployeeAssessment) {
		if(classEmployeeAssessment.employeeId==employeeId && 
			classEmployeeAssessment.gradingComponentId==gradingComponentId) {
			grade = classEmployeeAssessment.score;
		}
	});
	return grade;
}

function attachListenerToFilterGroup() {
	$("input[name='filterGroup']").on("change",function() {
		if($("#jobRoleRdBtn").prop("checked")) {
			$("#jobRoleDropdown").prop("disabled",false);
			$("#userGroupDropdown").prop("disabled",true);
			$("#userGroupDropdown").val("0").trigger("change");
		} else {
			$("#userGroupDropdown").prop("disabled",false);
			$("#jobRoleDropdown").prop("disabled",true);
			$("#jobRoleDropdown").val("0").trigger("change");
		}
	});
}

function attachListenerToJobRoleDropdown() {
	$("#jobRoleDropdown").on("change",function() {
		if($("#jobRoleDropdown").val()==null && $("#userGroupDropdown").val()==null) {
			initTraineeTbl();
		} else {
			searchEmployeeInfo();
		}
	});
}

function attachListenerToUserGroupDropdown() {
	$("#userGroupDropdown").on("change",function() {
		if($("#jobRoleDropdown").val()==null && $("#userGroupDropdown").val()==null) {
			initTraineeTbl();
		} else {
			searchEmployeeInfo();
		}
	});
}

function attachListenerToSearchTxtBox() {
	$("#searchTrainee").on("keyup",function() {
		searchEmployeeInfo();
	});
} 

function searchEmployeeInfo() {
	var classId = $("#classId").val();
	$("#traineePagination").pagination({     
        dataSource: "employeeinfo/trainees/search",
        locator: "employeeInfos",
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        ajax: {data:{
        	classId:classId,
        	keyword:$("#searchTrainee").val(),
        	jobRoleId:($("#jobRoleDropdown").val()==null)?0:$("#jobRoleDropdown").val(),
        	userGroupId:($("#userGroupDropdown").val()==null)?0:$("#userGroupDropdown").val()},
        	type:"POST"},
        className: "paginationjs-theme-blue",
        pageSize: 10,     
        callback: function(data) {            
            var filterType=($("#jobRoleRdBtn").prop("checked")?"jobrole":"usergroup");
            $("#traineeTblBody").html("");
            createTraineeHeader(filterType);
            _.each(data, function(employeeInfo) {
            	createTraineeRow(employeeInfo,filterType);
            });
        }
    });
}

function attachListenerToCheckAll() {
	$("body").on("change",".checkAll",function() {
		if($(this).prop("checked")) {
			$("#traineeTblBody").find("input[type=checkbox]").prop("checked",true).trigger("change");
		} else {
			$("#traineeTblBody").find("input[type=checkbox]").prop("checked",false).trigger("change");
		}
	})
}

function attachListenerToAddTraineeBtn() {
	$("#addTraineeBtn").on("click",function() {
		if(employeeInfoCheckedList.length != 0) {
			createClassEmployees();
		} else {
			alertify.warning("Please Select atleast 1 employee.")
		}
	});
}

function createClassEmployees() {
	var classId = $("#classId").val();
	$.when(ajax.create("classinfo/"+classId+"/classemployee/trainee",employeeInfoCheckedList))
		.done(function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					alertify.success("Successfully Added!");
					employeeInfoCheckedList = [];
					$("#addTraineesModal").modal("close");
					initTraineeTbl();
					initApprovedTraineeTbl();
					initGradeSheetHeader();
					break;
			}
		});
}

function attachListenerToCancelTraineeBtn() {
	$("#cancelTraineeBtn").on("click",function() {
		employeeInfoCheckedList = [];
		$("#searchTrainee").val("");
	});
}

function attachListenerToCompleteChkBox() {
	$("body").on("change",".completeChkBox",function() {
		if($(this).prop("checked")) {
			var classEmployee = {};
			classEmployee.classEmployeeId=$(this).data("id");
			classEmployee.trainingStatus = TrainingStatus.COMPLETED;
			completedTraineeList.push(classEmployee);
		} else {
			completedTraineeList = removeFromCompleteTraineeList($(this).data("id"));
		}
	});
}

function removeFromCompleteTraineeList(classEmployeeId) {
	var tempTraineeList = [];
	_.each(completedTraineeList,function(classEmployee) {
		if(classEmployee.classEmployeeId != classEmployeeId) {
			tempTraineeList.push(classEmployee);
		}
	});
	return tempTraineeList;
}

function attachListenerToSetAsCompletedBtn() {
	$("#setAsCompletedBtn").on("click",function() {
		updateTrainingStatus(completedTraineeList);
	});
}

function updateTrainingStatus(classEmployees) {
	var classId = $("#classId").val();
	$.when(ajax.customUpdate("classinfo/"+classId+"/trainingstatus",classEmployees)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully set as completed!");
				initApprovedTraineeTbl();
				completedTraineeList = [];
				break;
		}
	})
}

function attachListenerToUploadPhotoLink() {
	$("#uploadPhotoLink").on("click",function() {
		$("#fileUpload").trigger("click");
	});
}

function attachListenerToUploadPhoto() {
	$("#fileUpload").on("change",function(e) {
		if(validateFile()) {
			handleRefreshEvent();
			updateClassPhoto();
		}
	});
}

function validateFile() {
	var result = true;
	if($("#fileUpload").get(0).files.length==0) {
		result = false;
	}
	if(!validateFileType("#fileUpload","IMAGE")) {
		alertify.warning("Invalid File Type. Please Try another one.");
		result = false;
	}if(!validateFileSize("#fileUpload","IMAGE")) {
		result = false;
	}
	return result;
}

function updateClassPhoto() {
	blockUI("Uploading Media File..");
	var classId = $("#classId").val();
	$.when(ajax.upload("classinfo/"+classId+"/classPhotoUrl","uploadPhotoFrm")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully Updated!");
				$("#classPhoto").attr("src",response.classInfo.classPhotoUrl);
				$(window).unbind('beforeunload');
				$.unblockUI();
				break;
		}
	});
}