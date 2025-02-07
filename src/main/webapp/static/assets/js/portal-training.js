$(function() {
	fetchViewRestrictionClassInfos();
	initClassInfos();
});

function defineVariables() {
	viewRestrictionCLassInfos=[];
}

function initClassInfos() {
	var status=$("#status").val();
	if(status==CompletionStatus.ONGOING) {
		initInProgressClasses();
		$("#inProgressHref").append("In Progress</u>");
		$("#completeHref").append($("<u>Completed</u>"));
	} else {
		initCompletedClassInfos();
		$("#completeHref").append("Completed");
		$("#inProgressHref").append($("<u>In Progress</u>"));
	}
}

function initInProgressClasses() {
	$.when(ajax.fetch("classinfos/inprogress")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				populateInProgressClassInfoDivs(response.classInfos);
				break;
		}
	});
}

function populateInProgressClassInfoDivs(classInfos) {
	_.each(classInfos,function(classInfo) {
		$("#classDiv").append($("<div/>").addClass("col s12 m4")
			.append($("<div/>").addClass("divcard")
				.append($("<h6/>").css("margin","0")
					.append($("<b/>").html(classInfo.courseName)))
				.append($("<p/>").append($("<label/>").append($("<span/>").html(classInfo.className))))
				.append($("<p/>").append($("<label/>").append($("<span/>").html(
						getDeliveryMethodValue(classInfo.deliveryMethod)))))
				.append($("<a/>").addClass("btn bttn waves-effect waves-light")
						.attr({"href":"preview-class?classId="+classInfo.classId}).css("width","100%")
					.html((classInfo.trainingStatus==TrainingStatus.NOT_YET_STARTED)?"Start":"Resume"))));
	});
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

function initCompletedClassInfos() {
	$.when(ajax.fetch("classinfos/completed")).done(
		function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					populateCompletedClassInfoDivs(response.classInfos);
					break;
			}
	})
}

function populateCompletedClassInfoDivs(classInfos) {
	_.each(classInfos,function(classInfo) {
		$("#classDiv").append($("<div/>").addClass("col s12 m4")
			.append($("<div/>").addClass("divcard")
				.append($("<h6/>").css("margin","0").append($("<b/>").html(classInfo.courseName)))
				.append($("<p/>").append($("<label/>").append($("<span/>").html(classInfo.className))))
				.append($("<p/>").append($("<label/>").append($("<span/>").html(
						getDeliveryMethodValue(classInfo.deliveryMethod)))))
				.append((checkIfViewable(classInfo.classId))?
					$("<a/>").addClass("btn bttn waves-effect waves-light").attr({"href":"#!"}).css("width","100%")
						.html("View"):"")));
	});
}

function checkIfViewable(classId) {
	var result=false;
	_.each(viewRestrictionCLassInfos,function(classInfo) {
		if(classInfo.classId==classId) {
			result=true;
		}
	});
	return result;
}

function fetchViewRestrictionClassInfos() {
	viewRestrictionCLassInfos=[];
	$.ajax({
		url: "classinfos/viewrestrictions",
		type: "GET",
		dataType: "json",
		contentType: "application/json",
		mimeType: "application/json"
	}).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				viewRestrictionCLassInfos=response.classInfos;
				break;
			case HttpStatus.SESSION_EXPIRED:
				alertify.error(Message.SESSION_EXPIRED_MESSAGE);
				break;
			case HttpStatus.QUERY_FAILED:
				alertify.error(Message.QUERY_FAILED_MESSAGE);
				break;
			case HttpStatus.UNHANDLED_ERROR:
				alertify.error(Message.UNHANDLED_ERROR_MESSAGE);
				break;
		}
	});
}