$(function() {
	defineVariables();
	initUserGroupDropDown();
	initJobRoleDropDown();
	attachListenerToJobRoleRadioBtn();
	attachListenerToUserGroupRadioBtn();
	attachListenerToJobRoleDropDown();
	attachListenerToUserGroupDropDown();
	attachListenerToDeleteHref();
	attachListenerToAddTrainingStage();
	attachListenerToSearchCourseBox();
	attachListenerToBtnSaveCourse();
	attachListenerToSelectAll();
	attachListenerToClearIcon();
	attachListenerToBtnCancel();
	attachListenerToItemLinkUp();
	attachListenerToItemLinkDown();
});
function defineVariables() {
	trainingFlowList=[];
}

function initUserGroupDropDown() {
	$.when(ajax.fetch("usergroups")).done(function(response) {
		var usergroups = response.usergroups;
		$("#userGroupDropDown").prop("disabled",true);
		populateUserGroupDropDown(usergroups);
	});
}

function populateUserGroupDropDown(userGroups) {
	$("#userGroupDropDown").html("");
	$("#userGroupDropDown").append($("<option/>"));
	_.each(userGroups,function(userGroup) {
		if(userGroup.status == Status.ACTIVE) {
			$("#userGroupDropDown").append($("<option/>").attr({"value":userGroup.userGroupId})
				.html(userGroup.userGroupName));
		}
	});
}

function initJobRoleDropDown() {
	$.when(ajax.fetch("jobroles")).done(function(response) {
		var jobRoles = response.jobRoles;
		$("#assignmentType").val(AssignmentType.JOBROLE);
		populateJobRoleDropDown(jobRoles);
	});
}

function populateJobRoleDropDown(jobRoles) {
	$("#jobRoleDropDown").html("");
	$("#jobRoleDropDown").append($("<option/>"));
	_.each(jobRoles,function(jobRole) {
		if(jobRole.status == Status.ACTIVE) {
			$("#jobRoleDropDown").append($("<option/>").attr({"value":jobRole.jobRoleId}).html(jobRole.jobName));
		}
	})
}

function initTrainingFlowTbl(AssignmentType,assignmentTypeId) {
	if(assignmentTypeId != null) {
		$("#trainingFlowTblPagination").pagination({     
	        dataSource: "trainingflow/pages",
	        locator: "trainingFlows",
	        totalNumberLocator: function(response) {            
	            return response.totalRecords;
	        },
	        ajax: {data: {assignmentType:AssignmentType, assignmentTypeId:assignmentTypeId}},
	        className: "paginationjs-theme-blue",
	        pageSize: 10,     
	        callback: function(data) {                      
	            $("#trainingFlowTblBody").html("");  
	            trainingFlowList = data;
	            _.each(trainingFlowList, function(trainingFlow) {
	            	createTrainingFlowRow(trainingFlow)
	            });
	        }
	    });
	}   
}

function createTrainingFlowRow(trainingFlow) {
	var assignmentTypeId = (trainingFlow.assignmentType == AssignmentType.JOBROLE)?trainingFlow.jobRoleId:trainingFlow.userGroupId;
	$("#trainingFlowTblBody").append($("<tr/>")
		.append($("<input/>").attr({"id":"hidden_"+trainingFlow.trainingFlowId,"type":"hidden","value":trainingFlow.orderNo}))	
		.append($("<td/>").attr({"id":"orderNo_" + trainingFlow.trainingFlowId})
			.css("width","60px").html(trainingFlow.orderNo))
		.append($("<td/>").html(trainingFlow.categoryName))	
		.append($("<td/>").html(trainingFlow.courseName))	
		.append($("<td/>").html(trainingFlow.courseCode))
		.append($("<td/>").append($("<select/>").attr({"id":"preRequisite_"+trainingFlow.trainingFlowId})
			.bind("change",function() {
				updateTrainingFlow(trainingFlow,false);
			})))	
		.append($("<td/>").append($("<label/>").attr({"style":"margin-left:-20px !important;"})
			.append($("<input/>").attr({"type":"checkbox","id":"isRequired_" + trainingFlow.trainingFlowId})
					.prop("checked",setCheckBoxValue(trainingFlow.isRequired))
					.bind("change",function() {updateTrainingFlow(trainingFlow,false);}))
				.append($("<span/>").attr({"style":"padding:0 !important;"}))))	
		.append($("<td/>").append($("<label/>").attr({"style":"margin-left:-20px !important;"})
			.append($("<input/>").attr({"type":"checkbox","id":"isRecurring_" + trainingFlow.trainingFlowId})
					.prop("checked",setCheckBoxValue(trainingFlow.isRecurring))
					.bind("change",function() {
						updateTrainingFlow(trainingFlow,false);
						if($(this).prop("checked")) {
							$("#interval_"+trainingFlow.trainingFlowId).prop("disabled",false);
						} else {
							$("#interval_"+trainingFlow.trainingFlowId).prop("disabled",true);
						}
					}))
				.append($("<span/>").attr({"style":"padding: 0 !important;"}))))
		.append($("<td/>").append($("<input/>").addClass("numberOnly")
				.attr({"type":"number","id":"interval_"+trainingFlow.trainingFlowId,"value":trainingFlow.interval})
				.prop("disabled",(trainingFlow.isRecurring==Status.INACTIVE)?true:false)
				.bind("keypress",function(e) {
					setTimeout(function(){updateTrainingFlow(trainingFlow,false);},1000);
					return event.keyCode === 8 || event.keyCode === 46 ? true : !isNaN(Number(event.key));
					})).append($("<label/>").attr({"for":"interval_" + trainingFlow.trainingFlowId}).html("Days")))
		.append($("<td/>")
				.append($("<a/>").attr({
					"href":"#!","data-id":trainingFlow.trainingFlowId,
					"data-orderno":trainingFlow.orderNo})
					.addClass("itemLinkUp")
				.append($("<i/>").addClass("material-icons").html("keyboard_arrow_up")))
			.append($("<a/>")
				.attr({"href":"#!","data-id":trainingFlow.trainingFlowId,
					"data-orderno":trainingFlow.orderNo})
					.addClass("itemLinkDown")
				.append($("<i/>").addClass("material-icons").html("keyboard_arrow_down")))
			.append($("<a/>").attr({
					"href":"#!",
					"data-id":trainingFlow.trainingFlowId,
					"data-type":trainingFlow.assignmentType,
					"data-typeId": assignmentTypeId,
					"id": "deleteHref"})
				.append($("<i/>").addClass("material-icons").html("delete")))));
	appendPrerequisites(trainingFlow);
}

function updateTrainingFlow(trainingFlowObj,isReloadTable) {
	var trainingFlow = getTrainingFlowRowData(trainingFlowObj.trainingFlowId);
	$.when(ajax.update("trainingFlow/",trainingFlowObj.trainingFlowId,trainingFlow)).done(function() {
		if(isReloadTable){
			var assignmentTypeId = (trainingFlowObj.assignmentType==AssignmentType.JOBROLE)?
				trainingFlowObj.jobRoleId:trainingFlowObj.userGroupId;
			initTrainingFlowTbl(trainingFlowObj.assignmentType,assignmentTypeId);
		}
	});
}

function setCheckBoxValue(value) {
	return (value == 1)? true : false ;
}

function appendPrerequisites(trainingFlow) {
	$("#preRequisite_"+trainingFlow.trainingFlowId).append($("<option/>").attr({"value":"0"}).html("Prerequisite")
			.prop({"disabled":true,"selected":true}));
	trainingFlow.assignmentTypeId=(trainingFlow.assignmentType==AssignmentType.JOBROLE)?
			trainingFlow.jobRoleId:trainingFlow.userGroupId;
	$.when(ajax.fetchWithData("trainingflows",{
		assignmentType:trainingFlow.assignmentType,
		assignmentTypeId: trainingFlow.assignmentTypeId})).done(function(response) {
		var trainingFlows = removeEarlierCourse(trainingFlow.orderNo,response.trainingFlows);
		_.each(trainingFlows,function(trainingFlowObj) {
			$("#preRequisite_"+trainingFlow.trainingFlowId).select2();
//			var result = (trainingFlow.preCourseId==trainingFlowObj.preCourseId)? true : false;
			var newOption = new Option(trainingFlowObj.courseCode, trainingFlowObj.courseId, false,
					(trainingFlow.preCourseId==trainingFlowObj.courseId)? true : false);
			$("#preRequisite_"+trainingFlow.trainingFlowId).append(newOption);		
		});
	});
}

function getTrainingFlowRowData(trainingFlowId) {
	var trainingFlow = {};
	trainingFlow.orderNo = $("#hidden_" + trainingFlowId).val();
	trainingFlow.trainingFlowId = trainingFlowId;
	trainingFlow.preCourseId = $("#preRequisite_" + trainingFlowId).val();
	trainingFlow.isRequired = getCheckBoxValue("#isRequired_" + trainingFlowId);
	trainingFlow.isRecurring = getCheckBoxValue("#isRecurring_" + trainingFlowId);
	trainingFlow.interval = $("#interval_" + trainingFlowId).val();
	return trainingFlow;
}

function removeEarlierCourse(orderNo,trainingFlows) {
	var finalTrainingFlows = [];
	_.each(trainingFlows,function(trainingFlow) {
		if(orderNo > trainingFlow.orderNo) {
			finalTrainingFlows.push(trainingFlow);
		}
	});
	return finalTrainingFlows;
}

function attachListenerToJobRoleRadioBtn() {
	$("#jobRoleRadio").on("change",function() {
		if($(this).is(":checked")) {
			$("#userGroupDropDown").prop("disabled",true);
			$("#userGroupDropDown").find("option:first").prop("selected",true).trigger('change.select2');;
			$("#jobRoleDropDown").prop("disabled",false);
			$("#assignmentType").val(AssignmentType.JOBROLE);
		}
	});
}

function attachListenerToUserGroupRadioBtn() {
	$("#userGroupRadio").on("change",function() {
		if($(this).is(":checked")) {
			$("#userGroupDropDown").prop("disabled",false);
			$("#jobRoleDropDown").prop("disabled",true);
			$("#jobRoleDropDown").find("option:first").prop("selected",true).trigger('change.select2');
			$("#assignmentType").val(AssignmentType.USERGROUP);
		}
	});
}

function attachListenerToJobRoleDropDown() {
	$("#jobRoleDropDown").on("change",function() {
		var jobRoleId = $(this).val();
		initTrainingFlowTbl(AssignmentType.JOBROLE,jobRoleId);
		$("#btnAddTrainingStageBtn").removeAttr("disabled");
	});
}

function attachListenerToUserGroupDropDown() {
	$("#userGroupDropDown").on("change",function() {
		var userGroupId = $("#userGroupDropDown").val();
		initTrainingFlowTbl(AssignmentType.USERGROUP,userGroupId);
		$("#btnAddTrainingStageBtn").removeAttr("disabled");
	});
}

function attachListenerToDeleteHref() {
	$("body").on("click","#deleteHref",function() {
		var alertMessage = [];
		var trainingFlow = {};
		trainingFlow.assignmentType = $(this).data("type");
		trainingFlow.assignmentTypeId = $(this).data("typeid");
		trainingFlow.trainingFlowId = $(this).data("id");
		alertMessage.push("Are you sure you want to remove this training course in the flow? \n");
		alertMessage.push("It will also be removed as prerequisite course, if any \n");
		alertify.confirm("Delete Confirmation",alertMessage.join(""),
			function() {
			deleteTrainingFlow(trainingFlow);
			trainingFlowList=removeTrainingFlowItem(trainingFlow.trainingFlowId);
			updateOrderNo(trainingFlowList);
		},null)
			.set("labels", {ok:"DELETE", cancel:"CANCEL"});
	});
}

function removeTrainingFlowItem(trainingFlowId) {
	var tempTrainingFlowList=[];
	var ctr=1;
	_.each(trainingFlowList,function(trainingFlow) {
		if(trainingFlow.trainingFlowId!=trainingFlowId) {
			trainingFlow.orderNo=ctr;
			tempTrainingFlowList.push(trainingFlow);
			ctr++;
		}
	});
	return tempTrainingFlowList;
}

function deleteTrainingFlow(trainingFlow) {
	$.when(ajax.remove("trainingFlow/",trainingFlow.trainingFlowId)).done(function() {
		initTrainingFlowTbl(trainingFlow.assignmentType,trainingFlow.assignmentTypeId);
	});
}

function attachListenerToAddTrainingStage() {
	$("body").on("click","#btnAddTrainingStageBtn",function() {
		initCourseInfoTbl();
	});
}

function initCourseInfoTbl() {
	var jobRoleId=$("#jobRoleDropDown").val();
	var userGroupId=$("#userGroupDropDown").val();
	var assignmentType=$("#assignmentType").val();
	var assignmentTypeId=($("#assignmentType").val()==AssignmentType.JOBROLE)?jobRoleId:userGroupId;
	$("#courseInfoPagination").pagination({     
        dataSource: "courseinfo/active/pages",
        locator: "courseInfos",
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        ajax:{data:{status:Status.ACTIVE,assignmentType:assignmentType,
        	assignmentTypeId:assignmentTypeId}},
        className: "paginationjs-theme-blue",
        pageSize: 10,     
        callback: function(data) {                      
            $("#courseInfoTblBody").html("");  
            var courseInfos = checkSelectedData(data);
            _.each(courseInfos, function(courseInfo) {
            	createCourseInfoRow(courseInfo)
            });
        }
    });
}

function createCourseInfoRow(courseInfo) {
	courseInfo.assignmentType = $("#assignmentType").val();
	courseInfo.jobRoleId = $("#jobRoleDropDown").val();
	courseInfo.userGroupId = $("#userGroupDropDown").val();
	$("#courseInfoTblBody").append($("<tr/>")
		.append($("<td/>").append($("<label/>").attr({"margin-left":"15px !important"})
			.append($("<input/>").attr({"id": "courseInfo_" + courseInfo.courseId,"type":"checkbox","data-id":courseInfo.courseId})
				.prop("checked",setCheckBoxValue(courseInfo.isSelected))
				.bind("change",function() {
					courseInfo.isSelected = getCheckBoxValue("#courseInfo_" + courseInfo.courseId);
					updateSelectedCourse(courseInfo);
				}))
			.append($("<span/>"))))
		.append($("<td/>").html(courseInfo.courseCode))
		.append($("<td/>").html(courseInfo.courseName))
		.append($("<td/>").html(courseInfo.categoryName))
		.append($("<td/>").html(getDeliveryMethod(courseInfo.deliveryMethod))));
}

function getDeliveryMethod(deliveryMethodValue) {
	var deliveryMethodString = "";
	_.each(DeliveryMethod.LIST,function(deliveryMethod) {
		if(deliveryMethod.value==deliveryMethodValue) {
			deliveryMethodString = deliveryMethod.name;
		}
	})
	return deliveryMethodString;
}

function attachListenerToSearchCourseBox() {
	$("#searchCourse").on("keypress",function(event) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		var keyword = $(this).val();
		var jobRoleId=$("#jobRoleDropDown").val();
		var userGroupId=$("#userGroupDropDown").val();
		var assignmentType=$("#assignmentType").val();
		var assignmentTypeId=($("#assignmentType").val()==AssignmentType.JOBROLE)?jobRoleId:userGroupId;
		if(keycode == 13) {
			$("#courseInfoPagination").pagination({     
		        dataSource: "courseinfo/search/status",
		        locator: "courseInfos",
		        totalNumberLocator: function(response) {            
		            return response.totalRecords;
		        },
		        ajax: {data: {
		        	keyword :keyword,status:Status.ACTIVE,
		        	assignmentTypeId:assignmentTypeId,
		        	assignmentType:assignmentType},
		        	type:"POST"},
		        className: "paginationjs-theme-blue",
		        pageSize: 15,     
		        callback: function(data) {                      
		            $("#courseInfoTblBody").html("");  
		            var courseInfos = checkSelectedData(data);
		            _.each(courseInfos, function(courseInfo) {
		            	createCourseInfoRow(courseInfo)
		            });
		        }
		    });
		}
		return true;
	});
}

function checkSelectedData(courseInfos) {
	var finalList = [];
	if(globalList.length > 0) {
		_.each(globalList,function(selectedCourse) {
			_.each(courseInfos,function(courseInfo) {
				if(selectedCourse.courseId == courseInfo.courseId && selectedCourse.isSelected == 1) {
					courseInfo.isSelected = 1;
				}
			});
		});
	}
	finalList = courseInfos;
	return finalList;
}

function updateSelectedCourse(courseInfo) {
	if(checkCourseIfExist(courseInfo)) {
		_.each(globalList,function(courseObj) {
			if(courseInfo.courseId == courseObj.courseId) {
				courseObj.isSelected = courseInfo.isSelected;
			}
		});	
	} else {
		globalList.push(courseInfo);
	}
}

function checkCourseIfExist(courseInfo) {
	var result = false;
	_.each(globalList,function(courseObj) {
		if(courseInfo.courseId == courseObj.courseId) {
			result = true;
		}
	});
	return result;
}

function attachListenerToBtnSaveCourse() {
	$("#btnSaveCourse").on("click",function() {
		var finalCourseInfo=filterSelectedCourse(globalList);
		if(finalCourseInfo.length>0) {
			$.when(ajax.create("trainingflows",finalCourseInfo)).done(function() {
				alertify.success("Successfully Created");
				$("#addCourseModal").modal("close");
				var assignmentType = $("#assignmentType").val();
				var assignmentTypeId = (assignmentType==AssignmentType.JOBROLE)? $("#jobRoleDropDown").val() : $("#userGroupDropDown").val();
				globalList = [];
				initTrainingFlowTbl(assignmentType,assignmentTypeId);
				initCourseInfoTbl();
				$("#checkAllChkBox").prop("checked",false);
			});
		} else {
			alertify.warning("Please select at least one course before saving.");
		}
	});
}

function attachListenerToSelectAll() {
	$("#checkAllChkBox").on("change",function() {
		var value = (getCheckBoxValue("#checkAllChkBox") != 1)? false : true;
		$("#courseInfoTblBody").find("input:checkbox").prop("checked",value).trigger('change');
	});
}

function filterSelectedCourse(globalList) {
	var finalSelectedCourse = [];
	_.each(globalList,function(courseInfo) {
		if(courseInfo.isSelected == CheckedValue.CHECKED) {
			finalSelectedCourse.push(courseInfo);
		}
	});
	return finalSelectedCourse;
}

function attachListenerToClearIcon() {
	$("#clearSearch").bind("click",function() {
		$("input[type='search']").val("");
		initCourseInfoTbl();
	});
}


function attachListenerToBtnCancel() {
	$("#btnCancel").on("click",function() {
		$("input[type='search']").val("");
	});
}

function attachListenerToItemLinkUp() {
	$("body").on("click",".itemLinkUp",function() {
		var trainingFlowId=$(this).data("id");
		var orderNo=$(this).data("orderno");
		var message = [];
		message.push("Are you sure you want to continue? \n ");
		message.push("Selected prerequisite course will be removed");
		alertify.confirm("Item Arrangement Confirmation",message.join(""),
			function() {
			trainingFlowList=changeOrderNo("up",trainingFlowId,orderNo);
			updateOrderNo(trainingFlowList);
		},null).set("labels", {ok:"MOVE", cancel:"CANCEL"});
	});
}

function attachListenerToItemLinkDown() {
	$("body").on("click",".itemLinkDown",function() {
		var trainingFlowId=$(this).data("id");
		var orderNo=$(this).data("orderno");
		var message = [];
		message.push("Are you sure you want to continue? \n ");
		message.push("Selected prerequisite course will be removed");
		alertify.confirm("Delete Confirmation",message.join(""),
			function() {
			trainingFlowList=changeOrderNo("down",trainingFlowId,orderNo);
			updateOrderNo(trainingFlowList);
		},null).set("labels", {ok:"MOVE", cancel:"CANCEL"});
	});
}

function changeOrderNo(direction,trainingFlowId,orderNo) {
	var tempTrainingFlowList=trainingFlowList;
	if(direction=="up") {
		var calculatedOrderNo=orderNo-1;
		newOrderNo = (calculatedOrderNo<=0)?1:calculatedOrderNo;
		_.each(tempTrainingFlowList,function(trainingFlow) {
			if(trainingFlow.trainingFlowId==trainingFlowId) {
				tempTrainingFlowList=changeOtherOrderNo(orderNo,newOrderNo,
						tempTrainingFlowList);
				trainingFlow.orderNo = newOrderNo;
			}
		});
	} else {
		var calculatedOrderNo=orderNo+1;
		newOrderNo = (calculatedOrderNo>trainingFlowList.length)?trainingFlowList.length:calculatedOrderNo;
		_.each(tempTrainingFlowList,function(trainingFlow) {
			if(trainingFlow.trainingFlowId==trainingFlowId) {
				tempLearningPathList=changeOtherOrderNo(orderNo,newOrderNo,
						tempTrainingFlowList);
				trainingFlow.orderNo = newOrderNo;
			}
		});
	}
	return tempTrainingFlowList;
}

function changeOtherOrderNo(orderNo,newOrderNo,tempTrainingFlowList) {
	_.each(tempTrainingFlowList,function(trainingFlow) {
		if(trainingFlow.orderNo==newOrderNo) {
			trainingFlow.orderNo = orderNo;
		}
	});
	return tempTrainingFlowList;
}

function updateOrderNo(trainingFlowList) {
	var jobRoleId=$("#jobRoleDropDown").val();
	var userGroupId=$("#userGroupDropDown").val();
	var assignmentType=$("#assignmentType").val();
	var assignmentTypeId=(assignmentType==AssignmentType.JOBROLE)?jobRoleId:userGroupId;
	$.when(ajax.customUpdate("trainingflow/trainingflows",trainingFlowList)).done(function(response) {
		switch(response.status) { 
			case HttpStatus.SUCCESS:
				initTrainingFlowTbl(assignmentType,assignmentTypeId);
				break;
		}
	})
}