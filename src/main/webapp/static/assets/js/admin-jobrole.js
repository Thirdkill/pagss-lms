$(function(){
	initJobRolesTbl();
	attachListenerToSearchBox();
	attachListenerToSortLink();
	attachListenerToAddJobRoleLink();
	attachListenerToSaveBtn();
	attachListenerToEditBtn();
	attachListenerToUpdateBtn();
	attachListenerToClearIcon();
});

function initJobRolesTbl() {
	pagination.initiate({
    	url: "jobroles/pages",
    	locator: "jobRoles",
    	paginationDom: "#jobRolePagination",
    	tableBodyDom: "#jobRoleTblBody",
    	className: "paginationjs-theme-blue",
    	pageSize: 10,
    	functionName: "createJobRoleRow"
    });
}

function createJobRoleRow(jobRole) {
	$("#jobRoleTblBody").append($("<tr/>")
		.append($("<td/>").attr({"style" : "width:60px;"})
			.append($("<a/>").attr({"data-id":jobRole.jobRoleId,"data-target":"editJobRoleModal",
				"id":"editJobRoleBtn"})
				.addClass("modal-trigger waves-effect waves-light")
				.append($("<i/>").addClass("material-icons").html("edit"))))
		.append($("<td/>").html(jobRole.jobCode))
		.append($("<td/>").html(jobRole.jobName))
		.append($("<td/>").html(truncateString(jobRole.jobDesc,28)))
		.append($("<td/>").html(checkStatus(jobRole.status))));
}

function checkStatus(status) {
	return (status != 0)? "ACTIVE" : "INACTIVE"; 
}

function attachListenerToSearchBox() {
	$("#searchJobRole").on("keypress",function(e) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == 13) {
			var keyword = $(this).val();
			var tableCommand = {};
			tableCommand.keyword = keyword;
			tableCommand.sortColumnName = "jobCode";
			tableCommand.sortDirection = "DESC";
			searchJobRole(tableCommand);
		}
		return true;
	})
}

function attachListenerToSortLink() {
	$("body").on("click",".sortLink",function() {
		var tableCommand = {};
		tableCommand.keyword = $("#searchJobRole").val();
		tableCommand.sortDirection = $(this).data("sortdir");
		tableCommand.sortColumnName = $(this).data("sortname");
		searchJobRole(tableCommand);
	});
}

function searchJobRole(tableCommand) {
    pagination.initiate({
    	url: "jobroles/search",
    	paginationDom: "#jobRolePagination",
    	tableBodyDom: "#jobRoleTblBody",
    	locator: "jobRoles",
    	ajax: {
    		data: {
    			sortDirection: tableCommand.sortDirection,
    			sortName: tableCommand.sortColumnName,
    			keyword: tableCommand.keyword},
    		type: "POST"},
    	className: "paginationjs-theme-blue",
    	pageSize: 10,
    	functionName: "createJobRoleRow"
    	});
}

function attachListenerToAddJobRoleLink() {
	$("body").on("click","#addJobRoleLink",function() {
		$.when(ajax.fetch("jobroles/generate-jobcode")).done(function(response) {
			var jobRole = response.jobRole;
			$("#saveJobCode").val(jobRole.jobCode);
		});
	});
}

function attachListenerToSaveBtn() {
	$("#saveJobRoleBtn").on("click",function() {
		if(validateSaveJobRoleFields()) {
			var jobRole = getJobRoleData();
			$.when(ajax.create("jobrole",jobRole)).done(function(response) {
				switch(response.status) {
					case HttpStatus.SUCCESS:
						alertify.success("Successfully Created!");
						initJobRolesTbl();
						clearFields();
						$("#saveJobRoleModal").modal("close");
						break;
					case HttpStatus.JOBROLE_CODE_EXIST:
						alertify.warning(Message.JOBROLE_CODE_EXIST);
						break;
				}
			});
		}
	});
}

function getJobRoleData() {
	var jobRole = {};
	jobRole.jobCode = $("#saveJobCode").val();
	jobRole.jobName = $("#saveJobName").val();
	jobRole.jobDesc = $("#saveDescription").val();
	jobRole.status = getCheckBoxValue("#saveJobStatus");
	return jobRole;
}

function clearFields() {
	$("#saveJobCode").val("");
	$("#saveJobName").val("");
	$("#saveDescription").val("");
	$("#saveJobStatus").prop("checked","");
}

function validateSaveJobRoleFields() {
	if($("#saveJobCode").val() != "" && $("#saveJobName").val() != "") {
		return true;
	} else {
		alertify.warning("Please fill all required fields!");
		return false;
	}
}

function attachListenerToEditBtn() {
	$("body").on("click","#editJobRoleBtn",function() {
		var jobRoleId = $(this).data("id");
		populateEditModal(findJobRoleObj(jobRoleId));
	});
}

function findJobRoleObj(jobRoleId) {
	var jobRoleOutput = {};
	_.each(globalList,function(jobRoleObj) {
		if(jobRoleId == jobRoleObj.jobRoleId) {
			jobRoleOutput = jobRoleObj;
		}
	});
	return jobRoleOutput;
}

function populateEditModal(jobRole) {
	$("#editJobRoleId").val(jobRole.jobRoleId);
	$("#editJobCode").val(jobRole.jobCode);
	$("#editJobCodeLbl").addClass("active");
	$("#editJobName").val(jobRole.jobName);
	$("#editJobNameLbl").addClass("active");
	$("#editDescription").val(jobRole.jobDesc);
	$("#editDescriptionLbl").addClass("active")
	if(jobRole.status != Status.INACTIVE) {
		$("#editStatus").prop("checked","checked")
	} else {
		$("#editStatus").prop("checked","")
	}
}

function attachListenerToUpdateBtn() {
	$("#updateJobRoleBtn").on("click",function() {
		if(validateEditJobRoleFields()) {
			var jobRole = getEditJobRoleData();
			$.when(ajax.update("jobrole/",jobRole.jobRoleId,jobRole)).done(function(response) {
				if(response.status == HttpStatus.SUCCESS) {
					alertify.success("Successfully Updated!");
					initJobRolesTbl();
					$("#editJobRoleModal").modal("close");
				}
			})
		}
	});
}

function validateEditJobRoleFields() {
	if($("#editJobCode").val() != "" && $("#editJobName").val() != "") {
		return true;
	} else {
		alertify.warning("Please fill all required fields!");
		return false;
	}
}

function getEditJobRoleData() {
	var jobRole = {};
	jobRole.jobRoleId = $("#editJobRoleId").val();
	jobRole.jobCode = $("#editJobCode").val();
	jobRole.jobName = $("#editJobName").val();
	jobRole.jobDesc = $("#editDescription").val();
	jobRole.status = getCheckBoxValue("#editStatus");
	return jobRole;
}

function attachListenerToClearIcon() {
	$("#clearSearch").bind("click",function() {
		$("input[type='search']").val("");
		initJobRolesTbl();
	});
}