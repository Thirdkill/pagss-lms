$(function() {
	initCourseTbl();
	attachListenerToSearchBox();
	attachListenerToClearIcon();
});
function initCourseTbl() {
	pagination.initiate({
		paginationDom:"#courseTblPagination",
		tableBodyDom: "#courseTblBody",
		url:"courseinfo/pages",
		locator:"courseInfos",
		className: "paginationjs-theme-blue",
    	pageSize: 10,
    	functionName: "createCoursesRow"
	});
}

function createCoursesRow(courseInfo) {
	$("#courseTblBody").append($("<tr/>")
		.append($("<td/>").attr({"style" : "width:60px;"})
			.append($("<a/>").attr({
				"href":"admin.coursedetails?courseId="+courseInfo.courseId,
				"data-id":courseInfo.courseId,"id":"editCourseLink"})
				.addClass("waves-effect waves-light")
				.append($("<i/>").addClass("material-icons").html("edit"))))
		.append($("<td/>").html(courseInfo.courseCode))
		.append($("<td/>").html(courseInfo.courseName))
		.append($("<td/>").html(courseInfo.categoryName))
		.append($("<td/>").html(getDeliveryMethodValue(courseInfo.deliveryMethod)))
		.append($("<td/>").html((courseInfo.status==Status.ACTIVE)?"ACTIVE":"INACTIVE"))
		.append($("<td/>").html(courseInfo.lastModifiedAt))
		.append($("<td/>").html(courseInfo.modifiedBy)))
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

function attachListenerToSearchBox() {
	$("#searchCourseInfoTbl").on("keypress",function(e) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == 13) {
			var keyword = $(this).val();
			searchCourseInfo(keyword);
		}
	})
}

function searchCourseInfo(keyword) {
	pagination.initiate({
    	url: "courseinfo/search",
    	locator: "courseInfos",
    	paginationDom: "#courseTblPagination",
    	tableBodyDom: "#courseTblBody",
    	ajax: {data:{keyword:keyword},type:"POST"},
    	className: "paginationjs-theme-blue",
    	pageSize: 20,
    	functionName: "createCoursesRow"
    });
}

function attachListenerToClearIcon() {
	$("#clearSearch").bind("click",function() {
		$("input[type='search']").val("");
		initCourseTbl();
	});
}