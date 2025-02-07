$(function() {
	initTrainingCategoryDropdown(null);
	initDeliveryMethodDropdown(null);
	initLocationDropdown(null);
	initEmployeeDropdown(null);
	initScheduleTypeDropdown(null);
	initTextEditors();
	attachListenerToSaveCourseBtn();
	attachListenerToCategoryDropdown();
});

function initTrainingCategoryDropdown(value) {
	$.when(ajax.fetch("trainingcategories/status/" + Status.ACTIVE)).done(function(response) {
		populateTrainingCategoryDropdown(response.trainingCategories,value);
	});
}

function populateTrainingCategoryDropdown(trainingCategories,value) {
	_.each(trainingCategories,function(trainingCategory) {
		$("#trainingCategoryDropDown").append($("<option/>").attr({"value" : trainingCategory.categoryId})
				.prop("selected",(value==trainingCategory.categoryId)?true:false).html(trainingCategory.categoryName));
	});
}

function initDeliveryMethodDropdown(value) {
	_.each(DeliveryMethod.LIST,function(deliveryMethodObj) {
		$("#deliveryMethodDropdown").append($("<option/>").attr({"value" : deliveryMethodObj.value})
				.prop("selected",(value==deliveryMethodObj.value)?true:false).html(deliveryMethodObj.name));
	})
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

function initTextEditors() {
	initTinyMceEditor("#courseDescriptionEditor",400);
	initTinyMceEditor("#courseObjectivesEditor",400);
}

function attachListenerToSaveCourseBtn() {
	$("#saveCourseBtn").on("click",function() {
		if(validateCourseFields()) {
			var courseInfo = getCourseDetails();
			$.when(ajax.create("courseinfo",courseInfo)).done(function(response) {
				switch (response.status) {
					case HttpStatus.SUCCESS:
						if(validateFileUpload("#frmUploadPhoto")) {
							$.when(uploadPhoto(response.courseId,"frmUploadPhoto")).then(function(status) {
								redirect("admin.coursedetails?courseId="+response.courseId,2000);
							});
						} else {
							alertify.success("Successfully Created!")
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
	courseInfo.categoryId = $("#trainingCategoryDropDown").val();
	courseInfo.deliveryMethod = $("#deliveryMethodDropdown").val();
	courseInfo.status = getCheckBoxValue("#courseStatus");
	courseInfo.courseCode = $("#courseCode").val();
	courseInfo.courseName = $("#courseName").val();
	courseInfo.description = getContent("courseDescriptionEditor");
	courseInfo.objective = getContent("courseObjectivesEditor");
	courseInfo.locationId = $("#locationDropdown").val();
	courseInfo.isSelfRegister = getCheckBoxValue("#isSelfRegistration");
	courseInfo.withCertificate = getCheckBoxValue("#withCertificate");
	courseInfo.withExam = getCheckBoxValue("#withExam");
	courseInfo.minAttendee = $("#minAttendees").val();
	courseInfo.maxAttendee = $("#maxAttendees").val();
	courseInfo.employeeId = $("#trainerDropdown").val();
	courseInfo.scheduleType = $("#scheduleTypeDropdown").val();
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
	if($("#trainingCategoryDropDown").val()==null) {
		alertify.warning("Please choose a training category.")
		result = false;
	}
	if($("#deliveryMethodDropdown").val()==null) {
		alertify.warning("Please choose a delivery method.")
		result = false;
	}
	return result;
}

function attachListenerToCategoryDropdown() {
	$("#trainingCategoryDropDown").on("change",function() {
		var categoryDesc = $("#trainingCategoryDropDown option:selected").text();
		if($(this).val()!=null) {
			$.when(ajax.fetch("courseInfo/courseId/latest")).done(function(response) {
				var courseId = response.courseId;
				var categoryArray = categoryDesc.split(" ");
				var courseCode = "";
				if(categoryArray.length >=2) {
					_.each(categoryArray,function(value,index) {
						if(index <=1) {
							courseCode = courseCode+value.slice(0,1);
						}
					});
					$("#courseCode").val(courseCode + getCourseCode(courseId));
				} else {
					courseCode = categoryDesc.slice(0,1);
					$("#courseCode").val(courseCode + getCourseCode(courseId));
				}
			});
		}
	});
}

function getCourseCode(courseId) {
	var courseCode = 1000+parseInt(courseId).toString();
	return courseCode.substring(1,courseCode.length);
}