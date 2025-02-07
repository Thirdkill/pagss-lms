$(function() {
	initDeliveryMethodDropdown();
	initClassInfoTbl();
	initTrainerDropdown();
	initCourseDropdown();
	attachListenerToSearchBtn();
	attachListenerToMyClassesFilter();
	attachListenerToInProgressFilter();
	attachListenerToUpcomingFilter();
	attachListenerToCompletedFilter();
	attachListenerToCancelledFilter();
	attachListenerToDeleteLink();
	attachListenerToResetBtn();
});

function initDeliveryMethodDropdown() {
	_.each(DeliveryMethod.LIST,function(deliveryMethod) {
		$("#deliveryMethodDropdown").append($("<option/>").attr({"value":deliveryMethod.value})
			.html(deliveryMethod.name))
	});
}

function initClassInfoTbl() {
	handleFilterDisplay("myclass","off");
	searchClassInfo({
 		deliveryMethod:parseInt($("#deliveryMethodDropdown").val()),
 		employeeId:getEmployeeIdFilter(),
 		courseId:parseInt($("#courseInfoDropdown").val()),
 		startDate:$("#searchStartDate").val(),
 		endDate:$("#searchEndDate").val(),
 		keyword:$("#searchTxtBox").val(),
 		searchInProgressClasses:(($(this).data("#progressFilter")=="on")?1:0),
 		searchUpcomingClasses:(($("#upcomingFilter").data("status")=="on")?1:0),
 		searchCompletedClasses:(($("#completedFilter").data("status")=="on")?1:0),
 		searchCancelledClasses:(($("#cancelledFilter").data("status")=="on")?1:0)
 	});
}

function createClassInfoRow(classInfo) {
	if($("#myClassesFilter").data("status") == "on") {
		createClassInfoRowWithoutTrainer(classInfo);
	} else {
		createClassInfoRowWithTrainer(classInfo);
	}
	
}

function createClassInfoRowWithTrainer(classInfo) {
	$("#classInfoTblHeader").html("");
	$("#classInfoTblHeader").append($("<tr/>")
			.append($("<th/>").html("Class"))
			.append($("<th/>").html("Course"))
			.append($("<th/>").html("Deliver Method"))
			.append($("<th/>").html("Trainer"))
			.append($("<th/>").html("Start Date"))
			.append($("<th/>").html("End Date"))
			.append($("<th/>").html("Available Slots")));
	$("#classInfoTblBody").append($("<tr/>")
			.append($("<td/>").html(classInfo.className))
			.append($("<td/>").html(classInfo.courseName))
			.append($("<td/>").html(getDeliveryMethodValue(classInfo.deliveryMethod)))
			.append($("<td/>").html(classInfo.fullName))
			.append($("<td/>").html(classInfo.startDate))
			.append($("<td/>").html(classInfo.endDate))
			.append($("<td/>").html(getAvailableSlots(classInfo.maxAttendee,classInfo.totalEnrolledTrainees)))
			.append($("<td/>")
				.append($("<a/>").attr({
					"href":"admin.classdashboard?classId="+classInfo.classId,
					"data-id":classInfo.classId})
				.addClass("waves-effect waves-light")
					.append($("<i/>").addClass("material-icons").html("edit"))))
			.append($("<td/>")
				.append($("<a/>").attr({"href":"#!","data-id":classInfo.classId})
					.addClass("waves-effect waves-light deleteLink")
						.append($("<i/>").addClass("material-icons").html("delete")))));
}

function createClassInfoRowWithoutTrainer(classInfo) {
	$("#classInfoTblHeader").html("");
	$("#classInfoTblHeader").append($("<tr/>")
			.append($("<th/>").html("Class"))
			.append($("<th/>").html("Course"))
			.append($("<th/>").html("Deliver Method"))
			.append($("<th/>").html("Start Date"))
			.append($("<th/>").html("End Date"))
			.append($("<th/>").html("Available Slots")));
	$("#classInfoTblBody").append($("<tr/>")
			.append($("<td/>").html(classInfo.classCode))
			.append($("<td/>").html(classInfo.courseName))
			.append($("<td/>").html(getDeliveryMethodValue(classInfo.deliveryMethod)))
			.append($("<td/>").html(classInfo.startDate))
			.append($("<td/>").html(classInfo.endDate))
			.append($("<td/>").html(getAvailableSlots(classInfo.maxAttendee,classInfo.totalEnrolledTrainees)))
			.append($("<td/>")
				.append($("<a/>").attr({
					"href":"admin.classdashboard?classId="+classInfo.classId,
					"data-id":classInfo.classId})
				.addClass("waves-effect waves-light")
					.append($("<i/>").addClass("material-icons").html("edit"))))
			.append($("<td/>")
					.append($("<a/>").attr({"href":"#!","data-id":classInfo.classId})
						.addClass("waves-effect waves-light deleteLink")
							.append($("<i/>").addClass("material-icons").html("delete")))));
}

function getAvailableSlots(maxAttendees,noOfEnrolledTrainees) {
	var availableSlots=maxAttendees-noOfEnrolledTrainees;
	if(availableSlots<0) {
		availableSlots=0;
	}
	return availableSlots;
}

function getDeliveryMethodValue(deliveryMethod) {
	var result = "";
	switch(deliveryMethod) {
		case DeliveryMethod.INDIVIDUAL:
			result = "CBT (Individual)";
			break;
		case DeliveryMethod.MODULAR:
			result = "CBT (Modular)";
			break;
		case DeliveryMethod.CLASSTRAINING:
			result = "Classroom Training";
			break;
	}
	return result;
}

function initTrainerDropdown() {
	$.when(ajax.fetch("employeeinfo/trainers")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				populateTrainerDropdown(response.employeeInfos);
				break;
		}
	})
}

function populateTrainerDropdown(employeeInfos) {
	_.each(employeeInfos,function(employeeInfo) {
		$("#trainerDropdown").append($("<option/>").attr({"value":employeeInfo.employeeId})
			.html(employeeInfo.fullName))
	});
}

function initCourseDropdown() {
	$.when(ajax.fetch("courseinfo/active/list")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				populateCourseInfoDropdown(response.courseInfos);
				break;
		}
	})
}

function populateCourseInfoDropdown(courseInfos) {
	_.each(courseInfos,function(courseInfo) {
		$("#courseInfoDropdown").append($("<option/>").attr({"value":courseInfo.courseId})
			.html(courseInfo.courseName));
	});
}

function attachListenerToSearchBtn() {
	$("#searchBtn").on("click",function() {
		if(validateSearchFields()) {
			searchClassInfo({
	    		deliveryMethod:parseInt($("#deliveryMethodDropdown").val()),
	    		employeeId:getEmployeeIdFilter(),
	    		courseId:parseInt($("#courseInfoDropdown").val()),
	    		startDate:$("#searchStartDate").val(),
	    		endDate:$("#searchEndDate").val(),
	    		keyword:$("#searchTxtBox").val(),
	    		searchInProgressClasses:(($(this).data("#progressFilter")=="on")?1:0),
	    		searchUpcomingClasses:(($("#upcomingFilter").data("status")=="on")?1:0),
	    		searchCompletedClasses:(($("#completedFilter").data("status")=="on")?1:0),
	    		searchCancelledClasses:(($("#cancelledFilter").data("status")=="on")?1:0)
	    	});
		}
	});
} 

function getEmployeeIdFilter() {
	return ($("#myClassesFilter").data("status")=="on")?
			parseInt($("#employeeId").val()):parseInt($("#trainerDropdown").val());
}

function searchClassInfo(data,filterId) {
	$("#classInfoTblPagination").pagination({     
        dataSource: "classinfo/search",
        locator: "classInfos",
        totalNumberLocator: function(response) {
        	initClassCounter(filterId,response.totalRecords);
            return response.totalRecords;
        },
        ajax: {data:data,type:"POST"},
        className: "paginationjs-theme-blue",
        pageSize: 20,    
        callback: function(data) {                      
            $("#classInfoTblBody").html("");
            _.each(data, function(classInfo) {
            	createClassInfoRow(classInfo);
            });
        }
    });
}

function initClassCounter(filterId,totalRecords) {
	$(filterId).html("");
	$(filterId).html("<b>"+totalRecords+"</b>");
	
}

function validateSearchFields() {
	var result = true;
	if($("#searchStartDate").val()>$("#searchEndDate").val()) {
		alertify.warning("Start date cannot be higher than end date.");
		result = false;
	}
	return result;
}

function attachListenerToMyClassesFilter() {
	$("#myClassesFilter").on("click",function(e) {
		e.preventDefault();
		handleFilterDisplay($(this).data("filter"),$(this).data("status"));
		searchClassInfo({
    		deliveryMethod:parseInt($("#deliveryMethodDropdown").val()),
    		employeeId:getEmployeeIdFilter(),
    		courseId:parseInt($("#courseInfoDropdown").val()),
    		startDate:$("#searchStartDate").val(),
    		endDate:$("#searchEndDate").val(),
    		keyword:$("#searchTxtBox").val(),
    		searchInProgressClasses:(($(this).data("#progressFilter")=="on")?1:0),
    		searchUpcomingClasses:(($("#upcomingFilter").data("status")=="on")?1:0),
    		searchCompletedClasses:(($("#completedFilter").data("status")=="on")?1:0),
    		searchCancelledClasses:(($("#cancelledFilter").data("status")=="on")?1:0)
    	});
	});
}

function attachListenerToInProgressFilter() {
	$("#progressFilter").on("click",function(e) {
		e.preventDefault();
		handleFilterDisplay($(this).data("filter"),$(this).data("status"));
		searchClassInfo({
    		deliveryMethod:parseInt($("#deliveryMethodDropdown").val()),
    		employeeId:getEmployeeIdFilter(),
    		courseId:parseInt($("#courseInfoDropdown").val()),
    		startDate:$("#searchStartDate").val(),
    		endDate:$("#searchEndDate").val(),
    		keyword:$("#searchTxtBox").val(),
    		searchInProgressClasses:(($(this).data("status")=="on")?1:0),
    		searchUpcomingClasses:(($("#upcomingFilter").data("status")=="on")?1:0),
    		searchCompletedClasses:(($("#completedFilter").data("status")=="on")?1:0),
    		searchCancelledClasses:(($("#cancelledFilter").data("status")=="on")?1:0)
    	},"#inProgressClassesCounter");
	});
}

function attachListenerToUpcomingFilter() {
	$("#upcomingFilter").on("click",function(e) {
		e.preventDefault();
		handleFilterDisplay($(this).data("filter"),$(this).data("status"));
		searchClassInfo({
    		deliveryMethod:parseInt($("#deliveryMethodDropdown").val()),
    		employeeId:getEmployeeIdFilter(),
    		courseId:parseInt($("#courseInfoDropdown").val()),
    		startDate:$("#searchStartDate").val(),
    		endDate:$("#searchEndDate").val(),
    		keyword:$("#searchTxtBox").val(),
    		searchInProgressClasses:(($(this).data("#progressFilter")=="on")?1:0),
    		searchUpcomingClasses:(($("#upcomingFilter").data("status")=="on")?1:0),
    		searchCompletedClasses:(($("#completedFilter").data("status")=="on")?1:0),
    		searchCancelledClasses:(($("#cancelledFilter").data("status")=="on")?1:0)
    	},"#upcomingClassesCounter");
	});
}

function attachListenerToCompletedFilter() {
	$("#completedFilter").on("click",function(e) {
		e.preventDefault();
		handleFilterDisplay($(this).data("filter"),$(this).data("status"));
		searchClassInfo({
    		deliveryMethod:parseInt($("#deliveryMethodDropdown").val()),
    		employeeId:getEmployeeIdFilter(),
    		courseId:parseInt($("#courseInfoDropdown").val()),
    		startDate:$("#searchStartDate").val(),
    		endDate:$("#searchEndDate").val(),
    		keyword:$("#searchTxtBox").val(),
    		searchInProgressClasses:(($(this).data("#progressFilter")=="on")?1:0),
    		searchUpcomingClasses:(($("#upcomingFilter").data("status")=="on")?1:0),
    		searchCompletedClasses:(($("#completedFilter").data("status")=="on")?1:0),
    		searchCancelledClasses:(($("#cancelledFilter").data("status")=="on")?1:0)
    	},"#completedClassesCounter");
	});
}

function attachListenerToCancelledFilter() {
	$("#cancelledFilter").on("click",function(e) {
		e.preventDefault();
		handleFilterDisplay($(this).data("filter"),$(this).data("status"));
		searchClassInfo({
    		deliveryMethod:parseInt($("#deliveryMethodDropdown").val()),
    		employeeId:getEmployeeIdFilter(),
    		courseId:parseInt($("#courseInfoDropdown").val()),
    		startDate:$("#searchStartDate").val(),
    		endDate:$("#searchEndDate").val(),
    		keyword:$("#searchTxtBox").val(),
    		searchInProgressClasses:(($(this).data("#progressFilter")=="on")?1:0),
    		searchUpcomingClasses:(($("#upcomingFilter").data("status")=="on")?1:0),
    		searchCompletedClasses:(($("#completedFilter").data("status")=="on")?1:0),
    		searchCancelledClasses:(($("#cancelledFilter").data("status")=="on")?1:0)
    	},"#cancelledClassesCounter");
	});
}

function handleFilterDisplay(filterName,status) {
	switch(filterName) {
		case "myclass":
			changeClassInfoCounters(status);
			changeDivStatus("#myClassesFilter","#myClassesFilterDiv",status);
			break;
		case "progress":
			changeDivStatus("#progressFilter","#progressFilterDiv",status);
			changeDivStatus("#upcomingFilter","#upcomingFilterDiv", "on");
			changeDivStatus("#completedFilter","#completedFilterDiv", "on");
			changeDivStatus("#cancelledFilter","#cancelledFilterDiv", "on");
			break;
		case "upcoming":
			changeDivStatus("#upcomingFilter","#upcomingFilterDiv", status);
			changeDivStatus("#progressFilter","#progressFilterDiv","on");
			changeDivStatus("#completedFilter","#completedFilterDiv", "on");
			changeDivStatus("#cancelledFilter","#cancelledFilterDiv", "on");
			break;
		case "completed":
			changeDivStatus("#completedFilter","#completedFilterDiv", status);
			changeDivStatus("#progressFilter","#progressFilterDiv","on");
			changeDivStatus("#upcomingFilter","#upcomingFilterDiv", "on");
			changeDivStatus("#cancelledFilter","#cancelledFilterDiv", "on");
			break;
		case "cancelled":
			changeDivStatus("#cancelledFilter","#cancelledFilterDiv", status);
			changeDivStatus("#completedFilter","#completedFilterDiv", "on");
			changeDivStatus("#progressFilter","#progressFilterDiv","on");
			changeDivStatus("#upcomingFilter","#upcomingFilterDiv", "on");
			break;
	}
}

function changeClassInfoCounters(status) {
	if(status=="on") {
		$.when(ajax.fetch("classinfos/statistics")).done(function(response) {
			$("#inProgressClassesCounter").html("");
			$("#upcomingClassesCounter").html("");
			$("#completedClassesCounter").html("");
			$("#cancelledClassesCounter").html("");
			$("#inProgressClassesCounter").html("<b>"+response.totalInProgressClassInfos+"</b>");
			$("#upcomingClassesCounter").html("<b>"+response.totalUpcomingClassInfos+"</b>");
			$("#completedClassesCounter").html("<b>"+response.totalCompletedClassInfos+"</b>");
			$("#cancelledClassesCounter").html("<b>"+response.totalCancelledClassInfos+"</b>");
		});
	} else {
		$.when(ajax.fetch("user/classinfos/statistics")).done(function(response) {
			$("#inProgressClassesCounter").html("");
			$("#upcomingClassesCounter").html("");
			$("#completedClassesCounter").html("");
			$("#cancelledClassesCounter").html("");
			$("#inProgressClassesCounter").html("<b>"+response.totalInProgressClassInfos+"</b>");
			$("#upcomingClassesCounter").html("<b>"+response.totalUpcomingClassInfos+"</b>");
			$("#completedClassesCounter").html("<b>"+response.totalCompletedClassInfos+"</b>");
			$("#cancelledClassesCounter").html("<b>"+response.totalCancelledClassInfos+"</b>");
		});
	}
}

function changeDivStatus(linkDOM,dom,status) {
	if(status=="off") {
		$(dom).css({"background-color":"#1e88e5","color":"white"});
		$(dom).find("h5").css("color","white");
		$(linkDOM).data("status","on");
	} else {
		$(dom).css({"background-color":"white","color":"#1e88e5"});
		$(dom).find("h5").css("color","#1e88e5");
		$(linkDOM).data("status","off");
	}
}

function attachListenerToDeleteLink() {
	$("body").on("click",".deleteLink",function() {
		var classId = $(this).data("id");
		var message = "Are you sure you want to remove this class?";
		alertify.confirm("Delete Class", message, function(){ 
			removeClassInfo(classId);
		}, function(){}).set("labels",{ok:"Yes", cancel:"No"});
	});
}

function removeClassInfo(classId) {
	$.when(ajax.remove("classinfo/",classId)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				initClassInfoTbl();
				break;
		}
	});
}

function attachListenerToResetBtn() {
	$("#resetBtn").on("click",function() {
		clearSearchFields();
	});
}

function clearSearchFields() {
	$("#deliveryMethodDropdown").val("0").trigger("change");
	$("#trainerDropdown").val("0").trigger("change");
	$("#courseInfoDropdown").val("0").trigger("change");
	$("#searchStartDate").val(null);
	$("#searchEndDate").val(null);
	$("#searchTxtBox").val("");
}