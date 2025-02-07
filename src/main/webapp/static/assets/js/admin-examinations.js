$(function() {
	initExamInfoTbl();
	attachListenerToSearchBox();
	attachListenerToClearIcon();
});

function initExamInfoTbl() {
	pagination.initiate({
    	url: "examinfos/pages",
    	locator: "examInfos",
    	paginationDom: "#tblExamPagination",
    	tableBodyDom: "#tblExamBody",
    	className: "paginationjs-theme-blue",
    	pageSize: 10,
    	functionName: "createExamRow"
    });
}

function createExamRow(examInfo) {
	$("#tblExamBody").append($("<tr/>")
		.append($("<td/>")
			.append($("<a/>").addClass("waves-effect waves-light").attr("href","#!")
				.append($("<i/>").addClass("material-icons").css("margin-top","6px").html("file_download"))
				)
			.append($("<a/>").addClass("waves-effect waves-light")
					.attr("href","admin.examination?action=update&examId="+examInfo.examId)
				.append($("<i/>").addClass("material-icons").css("margin-top","6px").html("edit")))
			)
		.append($("<td/>").html(examInfo.title))
		.append($("<td/>").html((examInfo.status!=Status.ACTIVE)?"INACTIVE":"ACTIVE"))
		.append($("<td/>").html(examInfo.lastModifiedAt))
		.append($("<td/>").html(examInfo.modifiedBy)))
}

function attachListenerToSearchBox() {
	$("#searchExam").on("keypress",function() {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == 13) {
			var keyword = $(this).val();
			searchExam(keyword);
		}
	});
}

function searchExam(keyword) {
	pagination.initiate({
    	url: "examinfos/search",
    	locator: "examInfos",
    	paginationDom: "#tblExamPagination",
    	tableBodyDom: "#tblExamBody",
    	ajax: {data:{keyword:keyword},type:"POST"},
    	className: "paginationjs-theme-blue",
    	pageSize: 20,
    	functionName: "createExamRow"
    });
}

function attachListenerToClearIcon() {
	$("#clearSearch").bind("click",function() {
		$("input[type='search']").val("");
		initExamInfoTbl();
	});
}