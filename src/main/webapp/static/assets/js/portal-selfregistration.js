$(function() {
	initJoinBtn();
	initClassDetails();
	initClassSchedule();
	attachListenerToJoinBtn();
});
function initJoinBtn() {
	var classId=$("#classId").val();
	var employeeId=$("#employeeIdHdn").val();
	$.when(ajax.fetch("classinfo/"+classId+"/classemployee/"+employeeId+"/countapprovedemployee")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				disableJoinBtn(response.totalRecords);
				break;
		}
	});
}

function disableJoinBtn(totalRecord) {
	if(totalRecord > 0) {
		$("#joinBtn").removeClass("bttn").removeAttr("href").css("background-color","grey");
		$("#joinBtn").html("").html("Waiting For Approval");
	}
}

function initClassDetails() {
	console.log(getDeliveryMethodValue($("#deliveryMethodHdn").val()));
	$("#deliveryMethod").html(getDeliveryMethodValue(parseInt($("#deliveryMethodHdn").val())));
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

function attachListenerToJoinBtn() {
	$("#joinBtn").on("click",function() {
		createClassEmployee();
	});
}

function createClassEmployee() {
	var classId=$("#classId").val();
	$.when(ajax.create("classinfo/"+classId+"/classemployee",getClassInfoData())).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				$("#joinBtn").removeClass("bttn").removeAttr("href").css("background-color","grey");
				$("#joinBtn").html("").html("Waiting For Approval");
				break;
			case HttpStatus.CLASSSCHEDULE_HAS_CONFLICTS:
				alertify.warning(Message.CLASSSCHEDULE_HAS_CONFLICTS);
				break;
		}
	})
}

function getClassInfoData() {
	var classInfo = {};
	var classEmployee={};
	var classId=$("#classId").val();
	classEmployee.classId=classId;
	classEmployee.employeeId=$("#employeeIdHdn").val();
	classInfo.classEmployee=classEmployee;
	classInfo.employeeId=$("#employeeIdHdn").val();
	return classInfo;
}