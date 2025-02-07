/**
 * 
 */
$(function(){
	defineVariables();
	initializeJobRole();
	initializeUserType();
	initalizeUserGroups();
	generateTemplateListener();
	attachListenerToFileUpload();
	uploadFileListener();
	resetButtonListener();
	fileUploadTabListener();
	$("#recordsTab").hide();
	$("#saveRecordsPane").hide();
	$("#error-form").hide();
	$("#saveRecordTabBtn").hide();
	saveTabListener();
	showErrorTabPaneListener();
	showSaveTabPaneListener();
	attachSaveAllListener();
	attachDeleteListener();
	attachdeleteAllListener();
	attachSaveListener();
});

function defineVariables() {
	userTypeList=[];
	jobRoleList=[];
	userGroupList=[];
	uploadedErrorList=[];
	uploadedSavedList=[];
    validatedUsers = [];
}

function generateTemplateListener() {
	$("#generateTemplate").on("click",function() {
	
		var parameters = {
				jobRoleId: $("#jobRoleDropdown :selected").val(),
				userTypeId: $("#userTypeDropdown :selected").val(),
				userGroupId: $("#userGroupsDropdown :selected").val()
		};
		
		window.location.href = "usermassupload/generateexcel?" + $.param(parameters);
	});
}

function resetButtonListener() {
	$("#resetBtn").on("click",function() {
		$("#jobRoleDropdown").val("0").change();
		$("#userTypeDropdown").val("0").change();
		$("#userGroupsDropdown").val("0").change();

	});
}

function fileUploadTabListener() {
	var showUploadedUsersTab = false;
	$("#fileUploadTabBtn").on("click",function() {
		$("#fileUploadOptionForm").show();
		$("#recordsTab").hide();
		if(isUploaded) {
			$("#saveRecordTabBtn").show();
		} else {
			$("#saveRecordTabBtn").hide();
		}
		$("#saveRecordsPane").hide();
		$("#error-form").hide();
	});
}

function saveTabListener() {
	var showUploadedUsersTab = false;
	$("#saveRecordTabBtn").on("click",function() {
		$("#fileUploadOptionForm").hide();
		$("#recordsTab").show();
		$("#saveRecordsPane").hide();
		$("#error-form").hide();
		$("#saveRecordTabBtn").show();
	});
}

function attachListenerToFileUpload() {
	$("#fileUploadLabel").on("click",function() {
		$("#fileUpload").trigger("click");
	});
	
	$("#fileUpload").on("change",function(e) {
		if(validateFile()) {
			$("#fileUploadLabel").text("File is Loaded");
		}
	});
}

function validateFile() {
	var result = true;
	
	if($("#fileUpload").get(0).files.length==0) {
		result = false;
		alertify.warning("Please enter a file.");
		return;
	}

	if(!validateFileType("#fileUpload","DOCUMENT")) {

		alertify.warning("Please enter a xlsx file type only.");
		result = false;

	} if(!validateFileSize("#fileUpload","DOCUMENT")) {
		result = false;
	}
	return result;
}

function showErrorTabPaneListener() {
	$("#showErrorRecordsBtn").on("click",function() {
		$("#error-form").show();

		$("#errorFormCard").show();
		$("#saveRecordsPane").hide();

	});
}

function showSaveTabPaneListener() {
	$("#showSaveRecordsBtn").on("click",function() {
		$("#error-form").hide();
		$("#errorFormCard").hide();
		$("#saveRecordsPane").show();
	});
}

function initializeSavedRecordView() {
	$.when(ajax.fetch("users/usertypes")).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var userTypeFields = response.usertypes;
			populateUserTypeFields(userTypeFields);
		}
	}); 
}

function attachSaveListener() {
	$("body").on("click", ".saveBtn",function() {
		var result = $(this).data("id");
		$("#saveBtn_"+result).attr("disabled","disabled");
		$("#savebtn_"+result).html("Saving...");
		validateUser(result, false);
	});
}

function attachSaveAllListener() {
	$("#saveAllBtn").on("click",function() {
		validatedUsers = [];
		_.each(uploadedErrorList, function(user) {
			validateUser(user.userId, true);
		});
		submitUsers(validatedUsers);
	});
}

function attachdeleteAllListener() {
	$("#deleteAllBtn").on("click",function() {
		userGroupList=[];
		users = [];
		uploadedErrorList = [];
		$("#errorFormCard").empty();
		initErrorUploadList();
		$("#showErrorRecordsBtn").html("Error Records("+uploadedErrorList.length + ")" );
	});
}

function attachDeleteListener() {
	$("body").on("click", ".deleteBtn",function() {
		var result = $(this).data("id");

		uploadedErrorList = $.grep(uploadedErrorList, function(user, index) {
			return user.userId != result;
		});
		$("#errorFormCardId_"+result).remove();
		$("#showErrorRecordsBtn").html("Error Records("+uploadedErrorList.length + ")" );
	});

}

function submitUser(id) {
	var user = populateUserDetails(id);

	$.when(ajax.create("usermassupload/upload-user",user)).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			user = response.user;

			if (!user.errorMessage) {
				uploadedSavedList.push(user);
				$("#errorFormCardId_"+id).remove();
				//addSaveRecordValues(user);
				initSaveUploadList();
				uploadedErrorList = uploadedErrorList.filter(function(errorUser){
					return errorUser.userId !== id;
				});
				alertify.success("User Successfully Saved.");
				$("#showSaveRecordsBtn").html("Save Records("+uploadedSavedList.length + ")" );
				$("#showErrorRecordsBtn").html("Error Records("+uploadedErrorList.length + ")" );
				
			} else {
				alertify.error(user.errorMessage);
				$("#errorMessageLabel_" + id).html(user.errorMessage);
			}
		} 
	});
}

function submitUsers(users) {

	$.when(ajax.create("usermassupload/upload-users",users)).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var errorList = response.errorUploadedUser;
			var savedList = response.savedUploadedUser;
			_.each(errorList, function(errorUser){
				$("#errorMessageLabel_" + errorUser.userId).html(errorUser.errorMessage);
			});	
			//delete users without error message
			_.each(users, function(checkUsers){
				if (!checkUsers.errorMessage) {
					$("#errorFormCardId_"+checkUsers.userId).remove();
					uploadedErrorList = $.grep(uploadedErrorList, function(user, index) {
						return user.userId != checkUsers.userId;
					});
				}
			});
			_.each(savedList, function(savedUsers){
				uploadedSavedList.push(savedUsers);
			});
			
			initSaveUploadList();
			$("#showSaveRecordsBtn").html("Save Records("+uploadedSavedList.length + ")" );
			$("#showErrorRecordsBtn").html("Error Records("+uploadedErrorList.length + ")" );
		}
	});
}


function populateUserDetails(id) {
	var user = {};
	user.userId = id;
	user.employeeCode = $("#empNum_" + id).val();
	user.userTypeId = parseInt($("#userType_" + id).val());
	user.jobroleId = parseInt($("#jobRole_"+id).val());
	user.userGroupId = parseInt($("#userGroup_"+id).val());
	user.firstName = $("#lname_" + id).val();
	user.lastName = $("#fname_" + id).val();
	user.middleInitial = $("#midInitial_"+id).val();
	user.fullName = user.firstName + " " + user.lastName;
	user.password = "password";
	user.email = $("#email_" + id).val();
	user.dateHired = $("#hiredDate_" + id).val();
	user.mobileNo = $("#phone_" + id).val();
	user.status = 1;
	user.isPasswordReset = 1;
	
	return user;
}

function initSaveUploadList() {
	var pageSize = 10;
    $("#savedUploadUserpagination").pagination({     
        dataSource: uploadedSavedList,
        className: 'paginationjs-theme-blue',
        pageSize: pageSize,     
        callback: function(data) {            
            $(".saveCard").empty();
        	addSavedRecordValues(data);                                
        }
    });
}

function initErrorUploadList() {
	var pageSize = 10;
    $("#errorUploadUserpagination").pagination({     
        dataSource: uploadedErrorList,
        className: 'paginationjs-theme-blue',
        pageSize: pageSize,     
        callback: function(data) {       
			$("#errorFormCard").empty();
			initializeErrorRecordView(data);       
			$(window).scrollTop(0);      
			$('#errorRecordValues').scrollTop(0);                    
        }
    });
} 

function initializeErrorRecordView(users) {
	_.each(users, function(user){
		$("#errorFormCard")
		.append($("<div/>").addClass("divcard errorCard").attr({"id": "errorFormCardId_"+user.userId})
			.append($("<span/>").attr({"id":"errorMessageSpan_" + user.userId}).css({"color":"red"})
				.append($("<b/>").attr({"id":"errorMessageLabel_" + user.userId}).html(user.errorMessage))
			)
			.append($("<div/>").addClass("row").css({"margin-top": "20px"})
				.append($("<div/>").addClass("col s12 m3 input-field")
					.append($("<input/>").attr({
							"type": "text",
							"id":"empNum_" + user.userId,
							"name": "",
							"placeholder": " "
						}).addClass("validate text-input").val(user.employeeCode)
					)
					.append($("<label/>").attr({"for":"empNum_" + user.userId})
						.addClass("active").html("Employee Number"))
				)
				.append($("<div/>").addClass("col s12 m3 input-field")
					.append($("<select/>").attr({
						"id":"userType_" + user.userId, 
						"tabindex": 0,
						"aria-hidden":false
						}).addClass("browser-default").val(user.userTypeId).change())
					.append($("<label/>").attr({
						"id":"userTypeLabel_"+ user.userId,
						"for": "userType_" + user.userId, 
					}).addClass("active").html("User Type*"))
				)
				.append($("<div/>").addClass("col s12 m3 input-field")
					.append($("<select/>").attr({
							"id":"jobRole_" + user.userId, 
							"tabindex": 0,
							"aria-hidden":false
						}).addClass("browser-default").val(user.jobroleId).change())
					.append($("<label/>").attr({
						"id":"jobRoleLabel_"+ user.userId,
						"for": "jobRole_" + user.userId
					}).addClass("active").html("Job Role*"))
				)
				
				.append($("<div/>").addClass("col s12 m3 input-field")
					.append($("<select/>").attr({
						"id":"userGroup_" + user.userId, 
						"tabindex": 0,
						"aria-hidden":false
					}).addClass("browser-default").val(2).change())
					.append($("<label/>").attr({
						"id":"uGroupLabel"+ user.userId,
						"for": "userGroup_" + user.userId
					}).addClass("active").html("User Group"))
				)
			)
			.append($("<div/>").addClass("row").css({"margin-top": "20px"})
				.append($("<div/>").addClass("col s12 m4 input-field")
					.append($("<input/>").addClass("validate text-input").attr({
						"type":"text",
						"name":"",
						"placeholder": "  ",
						"id": "lname_" + user.userId}).val(user.lastName))
					.append($("<label/>").addClass("active").attr({
						"for":"lname_" + user.userId, "id":"lnameLabel_"+user.userId}).html("Last Name"))
				)
				.append($("<div/>").addClass("col s12 m4 input-field")
					.append($("<input/>").addClass("validate text-input").attr({
						"type":"text",
						"placeholder": " ",
						"name":"",
						"id": "fname_" + user.userId}).val(user.firstName))
					.append($("<label/>").addClass("active").attr(
						{"for":"fname" + user.userId, "id": "fnameLabel_"+user.userId}).html("First Name"))
				)
				.append($("<div/>").addClass("col s12 m4 input-field")
					.append($("<input/>").addClass("validate text-input").attr({
						"type":"text", 
						"placeholder": " ", 
						"name":"",
						"id": "midInitial_" + user.userId}).val(user.middleInitial))
						.append($("<label/>").addClass("active").attr({
							"for":"midInitial_" + user.userId}).html("Middle Initial"))
				)
			)
			.append($("<div/>").addClass("row").css({"margin-top":"20px"}) 
				.append($("<div/>").addClass("col s12 m4 input-field")
					.append($("<input/>").addClass("datepicker").attr({"type":"date", "id":"hiredDate_" + user.userId}).val(user.dateHired))
					.append($("<label/>").addClass("active").attr({"for":"hiredDate_" + user.userId, "id":"hiredDateLbl_"+user.userId}).html("Hire Date*"))
				)
				.append($("<div/>").addClass("col s12 m4 input-field")
					.append($("<input/>").addClass("validate text-input").attr({"type":"text", "id":"email_" + user.userId}).val(user.email))
					.append($("<label/>").addClass("active").attr({"for":"email_" + user.userId, "id":"emailLabel_"+user.userId}).html("Email Address"))
				)
				.append($("<div/>").addClass("col s12 m4 input-field")
					.append($("<input/>").addClass("validate text-input").attr({"type":"text", "id":"phone_" + user.userId}).val(user.mobileNo))
					.append($("<label/>").addClass("active").attr({"for":"phone_" + user.userId}).html("Mobile Phone Number"))
				)
			)
			.append($("<div/>").addClass("row")
				.append($("<div/>").addClass("col s12 m6"))
				.append($("<div/>").addClass("col s12 m6")
					.append($("<div/>").addClass("row")
						.append($("<div/>").addClass("col s12 m6")
							.append($("<a/>").addClass("deleteBtn bttn btn waves-effect waves-light").attr({
								"type":"submit",
								"data-id":user.userId,
								"style": "width: 100% !important"})
								.append($("<i/>").addClass("material-icons left").html("delete"))
								.append("Delete")
							)
						)
						.append($("<div/>").addClass("col s12 m6")
							.append($("<a/>").addClass("saveBtn bttn btn waves-effect waves-light").attr({
								"type":"submit",
								"data-id":user.userId,
								"id": "saveBtn_" + user.userId,
								"style": "width: 100% !important"})
								.append($("<i/>").addClass("material-icons left").html("save"))
								.append("Save")
							)
						)
					)
				)
			)
		);
		$("#userType_"+user.userId).append($("<option/>").attr({"value": 0, "disabled":""}).html("No User Type Selected"));
		_.each(userTypeList,function(userTypeField) {
			$("#userType_" + user.userId).append($("<option/>").attr("value",userTypeField.userTypeId).html(userTypeField.userTypeDesc));
		});
		$("#userType_"+ user.userId).trigger("contentChanged");
		
		$("#jobRole_"+user.userId).append($("<option/>").attr({"value": 0, "disabled":""}).html("No Job Role Selected"));
		
		_.each(jobRoleList,function(jobRoleField) {
			$("#jobRole_" + user.userId).append($("<option/>").attr("value",jobRoleField.jobRoleId).html(jobRoleField.jobName));
		});
		$("#jobRole_"+ user.userId).trigger("contentChanged");
		
		$("#userGroup_"+user.userId).append($("<option/>").attr("value", 0).html("No User Group"));
		_.each(userGroupList,function(groupTypeField) {
			$("#userGroup_" + user.userId).append($("<option/>").attr("value",groupTypeField.userGroupId).html(groupTypeField.userGroupName));
		});
		$("#userGroup_").trigger("contentChanged");
		
		$("#userGroup_"+user.userId).val(user.userGroupId).change();
		$("#userType_"+user.userId).val(user.userTypeId).change();
		$("#jobRole_"+user.userId).val(user.jobroleId).change();
	});
}


function addSavedRecordValues(users) {
	_.each(users, function(user) {
		$("#saveRecordsValues")
		.append($("<div/>").addClass("row saveCard")
			.append($("<div/>").addClass("divcard")
				.append($("<div/>").addClass("row")
					.append($("<div/>").addClass("col s12 m6")
						.append($(`<span> <b>Employee Code: </b> ${user.employeeCode}</span> <br/>`))
						.append($(`<span> <b>Full Name: </b> ${user.fullName}</span> <br/>`))
						.append($(`<span> <b>User Type: </b> ${user.userTypeDesc}</span> <br/>`))
						.append($(`<span> <b>Username: </b> ${user.username}</span> <br/>`))
						.append($(`<span> <b>Job Role: </b> ${user.jobName}</span> <br/>`))
					)
					.append($("<div/>").addClass("col s12 m6")
						.append($(`<span> <b>User Group: </b> ${user.userGroupName}</span> <br/>`))
						.append($(`<span> <b>Hire Date: </b> ${user.dateHired}</span> <br/>`))
						.append($(`<span> <b>Mobile Phone Number: </b> ${user.mobileNo}</span> <br/>`))
						.append($(`<span> <b>Email Address: </b> ${user.email}</span> <br/>`))
					)
				)
			)
		)
	});
}

function uploadFileListener() {
	$("#uploadTemplate").on("click", function(){
		$("#uploadTemplate").attr("disabled","disabled");
		$("#uploadTemplate").html("Uploading...");
		$(".errorCard").remove();
		$(".saveCard").remove();
		if(validateFile()) {
			$.when(ajax.upload("usermassupload/upload-excel", "massUploadFrm")).done(function(response) {
				var savedUserList = response.savedUploadedUser;
				var errorUserList = response.errorUploadedUser;
				uploadedSavedList = savedUserList;
				uploadedErrorList = errorUserList;
				if(response.status == HttpStatus.SUCCESS){
					if((Array.isArray(savedUserList) && savedUserList.length)
							|| (Array.isArray(errorUserList) && errorUserList)) {
						$("#uploadTemplate").removeAttr("disabled");
						$("#uploadTemplate").html("Upload");
						$("#fileUploadOptionForm").hide();
						$("#recordsTab").show();
						$("#saveRecordsPane").hide();
						$("#error-form").hide();
						$("#saveRecordTabBtn").show();
						$("#fileUploadLabel").text("Choose File");
						isUploaded = true;
						validatedUsers = [];
						initSaveUploadList();
						initErrorUploadList();
						$("#showSaveRecordsBtn").html("Save Records("+uploadedSavedList.length + ")" );
						$("#showErrorRecordsBtn").html("Error Records("+uploadedErrorList.length + ")" );
					} else {
						alertify.warning("File is Empty");
						$("#uploadTemplate").removeAttr("disabled");
						$("#uploadTemplate").html("Upload");
						$("#fileUploadLabel").text("Choose File");
					}
				} else {
					$("#uploadTemplate").removeAttr("disabled");
					$("#uploadTemplate").html("Upload");
					$("#fileUploadLabel").text("File is Loaded");
				}
			});
		}
	});
}

function validateEmail(email) {
    var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    if (filter.test(email)) {
        return true;
    }
    else {
        return false;
    }
}

function validateUser(id, isList) {
	var incField = 0;
	if (($("#userType_"+id).val() || 0) == 0) {
		$("#userTypeLabel_"+id).css("color","red");
		incField = 1;
	}else { $("#userTypeLabel_"+id).css("color","");}
	
	if (($("#jobRole_"+id).val() || 0) == 0) {
		$("#jobRoleLabel_"+id).css("color","red");
		incField = 1;
	}else { $("#jobRoleLabel_"+id).css("color","");}
	
	if (($("#lname_"+id).val() || 0) == 0) {
		$("#lnameLabel_"+id).css("color","red");
		incField = 1;
	} else { $("#lnameLabel_"+id).css("color","");}
	
	if (($("#fname_"+id).val() || 0) == 0){
		$("#fnameLabel_"+id).css("color","red");
		incField = 1;
		
	} else { $("#fnameLabel_"+id).css("color","");}

	if (!$("#hiredDate_"+id).val()) {
		$("#hiredDateLbl_"+id).css("color","red");
		incField = 1;
		
	} else{ $("#hiredDateLbl_"+id).css("color","");}

	if (incField != 1) {
		var email = $("#email_"+id).val();

		if (!email) {
			if (isList) {
				$("#errorMessageLabel_"+id).html("");
				var validatedUser = populateUserDetails(id);
				validatedUsers.push(validatedUser);
			} else {
				submitUser(id);
				$("#errorMessageLabel_"+id).html("");
			}
			$("#saveBtn_"+id).removeAttr("disabled");
			$("#saveBtn_"+id).html("<i class='material-icons left'>save</i>Save");
		} else {
			var validEmail = validateEmail(email);
			
			if(validEmail == true){
				if (isList) {
					$("#errorMessageLabel_"+id).html("");
					var validatedUser = populateUserDetails(id);
					validatedUsers.push(validatedUser);
				} else {
					submitUser(id);
					
					$("#errorMessageLabel_"+id).html("");
				}
				$("#saveBtn_"+id).removeAttr("disabled");
				$("#saveBtn_"+id).html("<i class='material-icons left'>save</i>Save");
			} else {
				$("#emailLabel_"+id).css("color","red");
				$("#saveBtn_"+id).removeAttr("disabled");
				$("#saveBtn_"+id).html("<i class='material-icons left'>save</i>Save");
			}
		}
		

		
	} else{
		$("#errorMessageLabel_"+id).html("Please Fill up the Required Fields");
		$("#saveBtn_"+id).removeAttr("disabled");
		$("#saveBtn_"+id).html("<i class='material-icons left'>save</i>Save");
	}
}

// ----------------POPULATE SEARCH FILTER FIELDS---------------------

function initializeUserType() {
	$.when(ajax.fetch("users/usertypes")).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var userTypeFields = response.usertypes;
			populateUserTypeFields(userTypeFields);
			userTypeList = response.usertypes;
		}
	}); 
}

function initializeJobRole() {
	$.when(ajax.fetch("jobroles")).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var jobRoleFields = response.jobRoles;
			populateJobRoleFields(jobRoleFields);
			jobRoleList = response.jobRoles;
		}
	}); 
}

function initalizeUserGroups() {
	$.when(ajax.fetch("usergroups")).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var userGroupsFields = response.usergroups;
			userGroupList = response.usergroups;
			populateUserGroupsFields(userGroupsFields);
		}
	}); 
}

function populateJobRoleFields(jobRoleFields) {
	$("#jobRoleDropdown").html("");
	$("#jobRoleDropdown").append($("<option/>").attr("value", 0).html("No Default Job Role"));
	
	_.each(jobRoleFields,function(jobRoleField) {
	$("#jobRoleDropdown").append($("<option/>").attr("value",jobRoleField.jobRoleId).html(jobRoleField.jobName));
	})
	$("#jobRoleDropdown").trigger("contentChanged");
	
	$("#jobRole").html("");
}

function populateUserTypeFields(userTypeFields) {
	$("#userTypeDropdown").html("");
	$("#userTypeDropdown").append($("<option/>").attr("value", 0).html("No Default User Type"));
	_.each(userTypeFields,function(userTypeField) {
		$("#userTypeDropdown").append($("<option/>").attr("value",userTypeField.userTypeId).html(userTypeField.userTypeDesc));
	})
	$("#userTypeDropdown").trigger("contentChanged");
}

function populateUserGroupsFields(groupTypeFields) {
	$("#userGroupsDropdown").html("");

	$("#userGroupsDropdown").append($("<option/>").attr("value", 0).html("No Default User Group"));
	_.each(groupTypeFields,function(groupTypeField) {
		$("#userGroupsDropdown").append($("<option/>").attr("value",groupTypeField.userGroupId).html(groupTypeField.userGroupName));
	})
	$("#userGroupsDropdown").trigger("contentChanged");
}