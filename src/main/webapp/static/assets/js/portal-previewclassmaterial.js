$(function() {
	handleUnloadEvent();
	initClassSchedule();
	attachListenerToFinishBtn();
});
function handleUnloadEvent() {
	$(window).unload(function() {
		var learningPathId=$("#learningPathId").val();
		var classId=$("#classId").val();
		ajax.customUpdate("classinfo/"+classId+"/learningpathsessiontimer/"+learningPathId+"/timeSpent");
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

function attachListenerToFinishBtn() {
	$("#finishBtn").on("click",function() {
		var classId=$("#classId").val();
		var learningPathId=$("#learningPathId").val();
		if(learningPathId != "") {
			updateSessionStatus();
		} else {
			redirect("preview-class?classId="+classId,2000);
		}
	});
}

function updateSessionStatus() {
	var classId=$("#classId").val();
	var learningPathId=$("#learningPathId").val();
	$.when(ajax.customUpdate("learningpathsessiontimer/"+learningPathId,getLearningPathSessionTimerData())).done(
		function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					alertify.success("Successfully finished class material.")
					redirect("preview-class?classId="+classId,2000);
					break;
			}	
	});
}

function getLearningPathSessionTimerData() {
	var learningPathSessionTimer={};
	learningPathSessionTimer.classId=$("#classId").val();
	learningPathSessionTimer.learningPathId=$("#learningPathId").val();
	learningPathSessionTimer.sessionStatus=SessionStatus.FINISHED;
	return learningPathSessionTimer;
}