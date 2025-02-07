$(function() {
	defineGlobalVariables();
	removeTabIndex();
	initCourseDetails();
	initTextEditor();
	initComponentDropdown();
	initGradingComponentTbl();
	attachListenerToSaveCourseBtn();
	attachListenerToSaveComponentBtn();
	attachListenerToEditGradingComponent();
});

function defineGlobalVariables() {
	componentList = [];	
}

function removeTabIndex() {
	$("#addComponentModal").removeAttr("tabindex");
	$("#editComponentModal").removeAttr("tabindex");
}

function initCourseDetails() {
	var courseId = parseInt($("#courseId").val());
	$.when(ajax.fetchObj("courseinfo/",courseId)).done(function(response) {
		var courseInfo = response.courseInfo;
		populateCourseDetails(courseInfo);
	});
}

function populateCourseDetails(courseInfo) {
	initTrainingCategoryDropdown(courseInfo.categoryId);
	initDeliveryMethodDropdown(courseInfo.deliveryMethod);
	initLocationDropdown(courseInfo.locationId);
	initEmployeeDropdown(courseInfo.employeeId);
	initScheduleTypeDropdown(courseInfo.scheduleType);
	setCheckBoxValue("#courseStatus",courseInfo.status);
	setCheckBoxValue("#isSelfRegister",courseInfo.isSelfRegister);
	setCheckBoxValue("#withCertificate",courseInfo.withCertificate);
	setCheckBoxValue("#withExam",courseInfo.withExam);
	$("#courseCode").val(courseInfo.courseCode);
	$("#courseName").val(courseInfo.courseName);
	$("#minAttendees").val(courseInfo.minAttendee);
	$("#maxAttendees").val(courseInfo.maxAttendee);
	$("#uploadPhoto").val(courseInfo.photoFileName);
	setContent("courseDescriptionEditor",courseInfo.description);
	setContent("courseObjectivesEditor",courseInfo.objective);
}

function initTrainingCategoryDropdown(value) {
	$.when(ajax.fetch("trainingcategories/status/" + Status.ACTIVE)).done(function(response) {
		populateTrainingCategoryDropdown(response.trainingCategories,value);
	});
}

function populateTrainingCategoryDropdown(trainingCategories,value) {
	_.each(trainingCategories,function(trainingCategory) {
		$("#trainingCategoryDropdown").append($("<option/>").attr({"value" : trainingCategory.categoryId})
				.prop("selected",(value==trainingCategory.categoryId)?true:false).html(trainingCategory.categoryName));
	});
}

function initDeliveryMethodDropdown(value) {
	_.each(DeliveryMethod.LIST,function(deliveryMethodObj) {
		$("#deliveryMethodDropdown").append($("<option/>").attr({"value" : deliveryMethodObj.value})
			.prop("selected",(value==deliveryMethodObj.value)?true:false).html(deliveryMethodObj.name));
	})
}

function initTextEditor() {
	initTinyMceEditor("#courseDescriptionEditor",400);
	initTinyMceEditor("#courseObjectivesEditor",400);
}

function initLocationDropdown(value) {
	$.when(ajax.fetch("locations/status/"+Status.ACTIVE)).done(function(response) {
		populateLocationDropdown(response.locations,value)
	});
}

function populateLocationDropdown(locations,value) {
	_.each(locations,function(location) {
		$("#locationDropdown").append($("<option/>").attr({"value" : location.locationId})
				.prop("selected",(value==location.locationId)?true:false).html(location.locationName));
	});
}

function initEmployeeDropdown(value) {
	$.when(ajax.fetch("employeeinfo/trainers/status/"+Status.ACTIVE)).done(function(response) {
		populateEmployeeDropdown(response.employeeInfos,value);
	})
}

function populateEmployeeDropdown(employeeInfos,value) {
	_.each(employeeInfos,function(employeeInfo) {
		$("#trainerDropdown").append($("<option/>").attr({"value" : employeeInfo.employeeId})
				.prop("selected",(value==employeeInfo.employeeId)?true:false).html(employeeInfo.fullName));
	});
}

function initScheduleTypeDropdown(value) {
	_.each(ScheduleType.LIST,function(scheduleType) {
		$("#scheduleTypeDropdown").append($("<option/>").attr({"value" : scheduleType.id})
			.prop("selected",(value==scheduleType.id)?true:false).html(scheduleType.description));
	});
}

function initComponentDropdown() {
	_.each(ComponentType.LIST,function(componentType) {
		$("#componentTypeDropdown,#editComponentTypeDropdown").append($("<option/>").attr({
			"value" : componentType}).html(componentType));
	});
	$("#componentTypeDropdown,#editComponentTypeDropdown").bind("change",function() {
		if($(this).val()==ComponentType.OTHERS) {
			$("#othersTxtBox").removeAttr("style");
			$("#editOthersTxtBox").removeAttr("style");
		} else {
			$("#othersTxtBox").attr("style","display:none");
			$("#editOthersTxtBox").attr("style","display:none");
		}
	});
}

function initGradingComponentTbl() {
	var courseId=$("#courseId").val();
	$.when(ajax.fetch("courseinfo/"+courseId+"/gradingcomponents")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				componentList = response.gradingComponents;
				populateGradingComponentTbl(response.gradingComponents);
				break;
		}
	});
}

function populateGradingComponentTbl(gradingComponents) {
	$("#tblComponentBody").html("");
	var gradingComponentTemp = {};
	_.each(gradingComponents,function(gradingComponent) {
		gradingComponentTemp = gradingComponent;
		createGradingComponentRow(gradingComponent);
	});
	createGradingComponentFooter(gradingComponentTemp);
}

function createGradingComponentRow(gradingComponent) {
	$("#tblComponentBody").append($("<tr/>")
		.append($("<td/>").addClass("min")
			.append($("<a/>").addClass("modal-trigger waves-effect waves-light").attr({
				"href":"#!",
				"data-target":"editComponentModal",
				"data-componentid":gradingComponent.gradingComponentId
			}).bind("click",function() {
				populateEditComponentModal($(this).data("componentid"));
				$("#hiddenGradingComponentId").val($(this).data("componentid"));
			}).append($("<i/>").addClass("material-icons").html("edit")))
		.append($("<a/>").addClass("waves-effect waves-light").attr({
			"href":"#!",
			"data-id":gradingComponent.gradingComponentId
		}).bind("click",function() {
			deleteComponent($(this).data("id"));
		}).append($("<i/>").addClass("material-icons").html("delete"))))
		.append($("<td/>").addClass("large").html(gradingComponent.componentDesc))
		.append($("<td/>").addClass("min").html(gradingComponent.percentage)))
}

function createGradingComponentFooter(gradingComponent) {
	$("#tblComponentBody").append($("<tr/>")
		.append($("<td/>").attr("colspan","2")
				.append($("<b>").addClass("right").html("Total Marks: ")))
			.append($("<td/>").addClass("center").html(gradingComponent.totalMarks)))
		.append($("<tr>")
			.append($("<td/>").attr("colspan",2)
				.append($("<b/>").addClass("right").html("Passing Grade: ")))
			.append($("<td/>").addClass("center")
				.append($("<input/>").attr({
					"type":"text",
					"id":"passingGradeTxtBox",
					"value":gradingComponent.passingGrade})
				.addClass("center numbersOnly"))));
	$("#hdnTotalMarks").val(gradingComponent.totalMarks);
	allowNumbersOnly();
}

function deleteComponent(gradingComponentId) {
	var courseId=parseInt($("#courseId").val());
	$.when(ajax.remove("courseinfo/"+courseId+"/gradingcomponent/",gradingComponentId)).done(
		function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					initGradingComponentTbl();
					break;
			}
		});
}

function populateEditComponentModal(gradingComponentId) {
	var gradingComponent=getComponentData(gradingComponentId);
	var componentList = ComponentType.LIST;
	if(componentList.includes(gradingComponent.componentDesc)) {
		$("#editOthersTxtBox").attr("style","visibility:hidden");
		$("#editComponentTypeDropdown").val(gradingComponent.componentDesc).trigger("change");
	} else {
		$("#editComponentTypeDropdown").val(ComponentType.OTHERS).trigger("change");
		$("#editOthersTxtBox").val(gradingComponent.componentDesc);
		$("#editOthersTxtBox").removeAttr("style");
	}
	$("#editPercentageTxtBox").val(gradingComponent.percentage);
}

function getComponentData(gradingComponentId) {
	var gradingComponent = {};
	_.each(componentList,function(object) {
		if(object.gradingComponentId==gradingComponentId) {
			gradingComponent = object;
		}
	});
	return gradingComponent;
}

function attachListenerToSaveCourseBtn() {
	$("#saveCourseBtn").on("click",function() {
		var courseId = parseInt($("#courseId").val());
		if(validateCourseFields()) {
			var courseInfo = getCourseDetails();
			$.when(ajax.update("courseinfo/",courseId,courseInfo)).done(function(response) {
				switch (response.status) {
					case HttpStatus.SUCCESS:
						if(validateFileUpload("#frmUploadPhoto")) {
							$.when(uploadPhoto(response.courseId,"frmUploadPhoto")).then(function(status) {
								redirect("admin.coursedetails?courseId="+response.courseId,2000);
							});
						} else {
							alertify.success("Successfully Updated!");
							redirect("admin.coursedetails?courseId="+response.courseId,2000);
						}
						break;
					case HttpStatus.COURSECODE_EXISTS:
						alertify.warning(Message.COURSECODE_EXISTS);
						break;
				}
			})
		}
	})
}

function uploadPhoto(courseId,formDom) {
	var result = $.Deferred();
	blockUI("Uploading Media File..");
	$.when(ajax.upload("courseinfo/"+courseId+"/photo",formDom)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Image successfully saved");
				$.unblockUI();
				result.resolve("Yehey");
				break;
			case HttpStatus.FILE_SIZE_IS_INVALID:
				alertify.warning(Message.FILE_SIZE_IS_INVALID);
				$.unblockUI();
				break;
			case HttpStatus.FILE_FORMAT_IS_INVALID:
				alertify.warning(Message.FILE_FORMAT_IS_INVALID);
				$.unblockUI();
				break;
		}
	});
	return result.promise();
}

function getCourseDetails() {
	var courseInfo = {};
	courseInfo.courseId = $("#courseId").val();
	courseInfo.categoryId = $("#trainingCategoryDropdown").val();
	courseInfo.deliveryMethod = $("#deliveryMethodDropdown").val();
	courseInfo.status = getCheckBoxValue("#courseStatus");
	courseInfo.courseCode = $("#courseCode").val();
	courseInfo.courseName = $("#courseName").val();
	courseInfo.description = getContent("courseDescriptionEditor");
	courseInfo.objective = getContent("courseObjectivesEditor");
	courseInfo.locationId = $("#locationDropdown").val();
	courseInfo.isSelfRegister = getCheckBoxValue("#isSelfRegister");
	courseInfo.withCertificate = getCheckBoxValue("#withCertificate");
	courseInfo.withExam = getCheckBoxValue("#withExam");
	courseInfo.minAttendee = $("#minAttendees").val();
	courseInfo.maxAttendee = $("#maxAttendees").val();
	courseInfo.employeeId = $("#trainerDropdown").val();
	courseInfo.scheduleType = $("#scheduleTypeDropdown").val();
	courseInfo.passingGrade = $("#passingGradeTxtBox").val();
	return courseInfo;
}

function validateCourseFields() {
	var result = true;
	if($("#courseCode").val()=="") {
		alertify.warning("Course code cannot be empty.")
		result = false;
	}
	if($("#courseName").val()=="") {
		alertify.warning("Course name cannot be empty.")
		result = false;
	}
	if($("#trainingCategoryDropdown").val()==null) {
		alertify.warning("Please choose a training category.")
		result = false;
	}
	if($("#deliveryMethodDropdown").val()==null) {
		alertify.warning("Please choose a delivery method.")
		result = false;
	}
	if($("#passingGradeTxtBox").val()=="") {
		alertify.warning("Please specify a passing grade.")
		result = false;
	}
	if(parseInt($("#passingGradeTxtBox").val()) > parseInt($("#hdnTotalMarks").val())) {
		alertify.warning("Passing Grade Cannot be higher than total marks.");
		result = false;
	}
	return result;
}

function attachListenerToSaveComponentBtn() {
	$("#saveComponentBtn").on("click",function() {
		var gradingComponent=getGradingComponent();
		if(validateGradingComponentFields()) {
			createGradingComponent(gradingComponent);
		}
	});
}

function createGradingComponent(gradingComponent) {
	var courseId = parseInt(gradingComponent.courseId);
	$.when(ajax.create("courseinfo/"+courseId+"/gradingcomponent",gradingComponent)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully added!");
				initGradingComponentTbl();
				clearComponentModalFields();
				$("#addComponentModal").modal("close");
				break;
			case HttpStatus.COMPONENT_EXISTS:
				alertify.warning(Message.COMPONENT_EXISTS);
				break;
		}
	});
}

function clearComponentModalFields() {
	$("#componentTypeDropdown").val(0).trigger("change");
	$("#percentageTxtBox").val("");
}

function getGradingComponent() {
	var gradingComponent = {};
	gradingComponent.courseId=parseInt($("#courseId").val());
	gradingComponent.componentDesc=($("#componentTypeDropdown").val()!=ComponentType.OTHERS)?
		$("#componentTypeDropdown").val():$("#othersTxtBox").val();
	gradingComponent.percentage=$("#percentageTxtBox").val();
	return gradingComponent;
}

function validateGradingComponentFields() {
	var result=true;
	var totalMarks=parseInt($("#hdnTotalMarks").val())+parseInt($("#percentageTxtBox").val());
	if($("#componentTypeDropdown").val()==ComponentType.OTHERS && 
		$("#othersTxtBox").val()=="") {
		alertify.warning("Please specify a component.");
		result=false;
	}
	if($("#componentTypeDropdown").val()==null) {
		alertify.warning("Please choose a component.");
		result=false;
	}
	if($("#percentageTxtBox").val()=="") {
		alertify.warning("Please specify a percentage.");
		result=false;
	}
	if(totalMarks>100) {
		alertify.warning("Total Marks cannot exceed 100 above.");
		result=false;
	}
	return result;
}

function attachListenerToEditGradingComponent() {
	$("#editComponentBtn").on("click",function() {
		var gradingComponent = getEditGradingComponentData();
		if(validateEditGradingComponentFields()) {
			updateGradingComponent(gradingComponent);
		}
	});
}

function getEditGradingComponentData() {
	var gradingComponent = {};
	gradingComponent.gradingComponentId=parseInt($("#hiddenGradingComponentId").val());
	gradingComponent.componentDesc=($("#editComponentTypeDropdown").val()!=ComponentType.OTHERS)?
		$("#editComponentTypeDropdown").val():$("#editOthersTxtBox").val();
	gradingComponent.percentage=$("#editPercentageTxtBox").val();
	gradingComponent.courseId=$("#courseId").val();
	return gradingComponent;
}

function updateGradingComponent(gradingComponent) {
	var courseId = parseInt($("#courseId").val());
	var gradingComponentId= parseInt(gradingComponent.gradingComponentId);
	$.when(ajax.update("courseinfo/"+courseId+"/gradingcomponent/",gradingComponentId,gradingComponent))
		.done(function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					alertify.success("Successfully updated!");
					initGradingComponentTbl();
					$("#editComponentModal").modal("close");
					break;
				case HttpStatus.COMPONENT_EXISTS:
					alertify.warning(Message.COMPONENT_EXISTS);
					break;
			}
	});
}

function validateEditGradingComponentFields() {
	var result=true;
	var totalMarks=parseInt($("#hdnTotalMarks").val())+parseInt($("#percentageTxtBox").val());
	if($("#editComponentTypeDropdown").val()==ComponentType.OTHERS && 
		$("#editOthersTxtBox").val()=="") {
		alertify.warning("Please specify a component.");
		result=false;
	}
	if($("#editComponentTypeDropdown").val()==null) {
		alertify.warning("Please choose a component.");
		result=false;
	}
	if($("#editPercentageTxtBox").val()=="") {
		alertify.warning("Please specify a percentage.");
		result=false;
	}
	if(totalMarks<100) {
		alertify.warning("Total Marks cannot exceed 100 above.");
		result=false;
	}
	return result;
}