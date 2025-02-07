/**
 * 
 */

$(function(){
	initUserDetails();
	submitButtonListener();
	attachListenerToUploadPhotoLink();
});

function submitButtonListener(){
	$("#userSubmit").unbind("click").click(function(){
		$("#userSubmit").attr("disabled","disabled");
		$("#userSubmit").html("Saving...");
		validateFields();
	});
}

function getEmployeeDetailsData() {
	var empinfo = {};
	var empId = $("#userSubmit").attr("data-id");
	var userId = $("#userSubmit").attr("data-uid");
	empinfo.userId = userId;
	empinfo.employeeCode = $("#employeeNoTxtbox").val();
	empinfo.jobRoleId = $("#jobRoleDropdown").val();
	empinfo.lastName = $("#lnameTxtbox").val();
	empinfo.firstName = $("#fnameTxtbox").val();
	empinfo.middleInitial = $("#miTxtbox").val();
	empinfo.fullName = empinfo.firstName+" "+empinfo.lastName;
	empinfo.dateHired = $("#dateHiredTxtbox").val();
	empinfo.mobileNo = $("#mobileNoTxtbox").val();
	empinfo.email = $("#emailTxtbox").val();
	if(GetURLParameter('action') == "EDIT"){
		empinfo.employeeId = empId;
	}
	return empinfo;
}

function getUserData() {
	var user = {};
	var userId = $("#userSubmit").attr("data-uid");
	user.firstName = $("#fnameTxtbox").val();
	user.lastName = $("#lnameTxtbox").val();
	user.userTypeId = parseInt($("#userTypeDropDown").val());
	
	if ($('#statusCheckBox').is(':checked')) {
		user.status = 1;
	} else if(!$('#statusCheckBox').is(':checked')){
		user.status = 0;
	}
	
	if(GetURLParameter('action') == "EDIT"){
		user.userId = userId;
	}
	return user;
}

function getUserGroupsData() {
	var userGroups = [];
	$(".select2-selection__rendered li").each(function(i) {
		var userGroup = {};
		userGroup.userId = $("#userSubmit").attr("data-uid");
		var userGroupName = $(this).attr("title");
		if($(this).attr("title") != undefined) {
			userGroup.userGroupName = $(this).attr("title");
		}
		$("#userGroupDropDown option").each(function() {
			if($(this).html() == userGroupName) {
				userGroup.userGroupId = $(this).val();
				userGroups.push(userGroup);
			}
		});
	});
	return userGroups;
}

function initUserType() {
	return $.when(ajax.fetch("users/usertypes")).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var userTypeFields = response.usertypes;
			populateUserTypeFields(userTypeFields);
		}
	}); 
}

function initUserGroup() {
	$.when(ajax.fetch("usergroups")).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var userGroupFields = response.usergroups;
			populateUserGroupFields(userGroupFields);
		}
	}); 
}

function initJobRole() {
	return $.when(ajax.fetch("jobroles")).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var jobRoleFields = response.jobRoles;
			populateJobRoleFields(jobRoleFields);
		}
	}); 
}

function initUserDetails() {
	$("#statusCheckBox").attr('checked', true);
	$.when(initUserType(), initJobRole()).done(function(response1, response2) {
		var userId = GetURLParameter('id');
		if(GetURLParameter('action') == "EDIT"){
			$("#fieldPage").text("Edit User Details");
			$(document).attr("title", "PAGSS - Edit User Details");
			var userId = GetURLParameter('id');
			$.when(ajax.fetchObj("users/",userId)).done(function(response) {
				if(response.status == HttpStatus.SUCCESS){
					var userDetails = response.user;
					populateUserDetails(userDetails);
					initUserGroup();
				}
			}); 
		} else if(GetURLParameter('action') == "ADD"){
			$("#fieldPage").text("Create New User");
			$(document).attr("title", "PAGSS - Create New User");
			$.when(ajax.fetch("users/generate-usercode")).done(function(response) {
				var userDetails = response.user;
				$("#userSubmit").attr("data-id",userDetails.employeeId+1);
				$("#userSubmit").attr("data-uid",userDetails.userId+1);
				$("#employeeNoTxtbox").val(userDetails.employeeCode);
				initUserGroup();
			});
		}
	});
}

function populateUserTypeFields(userTypeFields) {
	$("#userTypeDropDown").html(""); 
	$("#userTypeDropDown").append($("<option/>").html("Please Specify...").attr({"value":"0","disabled":"disabled","selected":""}));
	_.each(userTypeFields,function(userTypeField) {
		$("#userTypeDropDown").append($("<option/>").attr("value",userTypeField.userTypeId).html(userTypeField.userTypeDesc));
	});
	$("#userTypeDropDown").trigger("contentChanged");
}

function populateJobRoleFields(jobRoleFields) {
	$("#jobRoleDropdown").html("");
	$("#jobRoleDropdown").append($("<option/>").html("Please Specify...").attr({"value":"0","disabled":"disabled","selected":""}));
	_.each(jobRoleFields,function(jobRoleField) {
		if(jobRoleField.status == 1){
			$("#jobRoleDropdown").append($("<option/>").attr("value",jobRoleField.jobRoleId).html(jobRoleField.jobName));
		}
	})
	$("#jobRoleDropdown").trigger("contentChanged");
}

function populateUserGroupFields(userGroupFields) {
	if(GetURLParameter('action') == "EDIT"){
		var userId = GetURLParameter('id');
		$.when(ajax.fetchObj("usergroups/",userId)).done(function(response) {
			if(response.status == HttpStatus.SUCCESS){
				populateUserGroups(response.usergroups);
			}
		});
	}
	populateMultipleSelect2("#userGroupDropDown",getUserGroupNames(userGroupFields),[]);
}

function populateUserGroups(editUserGroups) {
	var editUserGroupNames = getUserGroupNames(editUserGroups);
	$.when(ajax.fetch("usergroups")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				var userGroupList = getUserGroupNames(response.usergroups);
				var userGroupNames = [];
				_.each(editUserGroupNames,function(editUserGroupName){
					userGroupNames.push(editUserGroupName.id);
				});
				populateMultipleSelect2("#userGroupDropDown",userGroupList,userGroupNames);
				break;
		}
	});	
}

function getUserGroupNames(userGroups) {
	var userGroupNames = [];
	var userGroupIdList = []; var userGroupList = [];
	_.each(userGroups,function(userGroup){
		var object = {};
		object.id = userGroup.userGroupId;
		object.text = userGroup.userGroupName;
		userGroupList.push(object);
	});
	return userGroupList;
}

function populateUserDetails(userDetails) {
	$("#userSubmit").attr("data-id",userDetails.employeeId);
	$("#userSubmit").attr("data-uid",GetURLParameter('id'));
	$("#employeeNoTxtbox").val(userDetails.employeeCode);
	$("#employeeNoTxtbox").attr("disabled","disabled");
	$("#userTypeDropDown").val(userDetails.userTypeId);
	$("#userTypeDropDown").trigger("change");
	$("#lnameTxtbox").val(userDetails.lastName);
	$("#fnameTxtbox").val(userDetails.firstName);
	$("#miTxtbox").val(userDetails.middleInitial);
	$("#jobRoleDropdown").val(userDetails.jobroleId);
	$("#jobRoleDropdown").trigger("change");
	$("#dateHiredTxtbox").val(userDetails.dateHired);
	$("#mobileNoTxtbox").val(userDetails.mobileNo);
	$("#emailTxtbox").val(userDetails.email);
	
	if(userDetails.status == "1"){
		$("#statusCheckBox").attr("checked", "checked");
	} else if(userDetails.status == "0"){
		$("#statusCheckBox").removeAttr("checked");
	}
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

function validateFields() {
	var incField = 0;
	if($("#employeeNoTxtbox").val().length == 0){
		$("#empLabel").css("color","red");
		incField = 1;
	}else{$("#empLabel").css("color","");}
	
	if($("#userTypeDropDown").val() == 0 || $("#userTypeDropDown").val() == null){
		$("#uTypeLabel").css("color","red");
		incField = 1;
	}else{$("#uTypeLabel").css("color","");}
	
	if($("#lnameTxtbox").val().length == 0){
		$("#lnLabel").css("color","red");
		incField = 1;
	}else{$("#lnLabel").css("color","");}
	
	if($("#fnameTxtbox").val().length == 0){
		$("#fnLabel").css("color","red");
		incField = 1;
	}else{$("#fnLabel").css("color","");}
	
	if($("#jobRoleDropdown").val() == 0 || $("#jobRoleDropdown").val() == null){
		$("#jrLabel").css("color","red");
		incField = 1;
	}else{$("#jrLabel").css("color","");}
	
	if($("#dateHiredTxtbox").val().length == 0){
		$("#dhLabel").css("color","red");
		incField = 1;
	}else{$("#dhLabel").css("color","");}
	
	if($("#emailTxtbox").val().length == 0){
		$("#emailLabel").css("color","red");
		incField = 1;
	}else{$("#emailLabel").css("color","");}
	
	if(incField != 1){
		var email = $("#emailTxtbox").val();
		var validEmail = validateEmail(email);
		if(validEmail == true){
			submitUserDetails();
		}
		else if(validEmail == false){
			$("#emailLabel").css("color","red");
			alertify.warning("Please enter a valid email address."); 
			$("#userSubmit").removeAttr("disabled");
			$("#userSubmit").html("<i class='material-icons left'>save</i>Save");
		}
			
		
	} else{
		alertify.warning("Please fill up the required fields.");
		$("#userSubmit").removeAttr("disabled");
		$("#userSubmit").html("<i class='material-icons left'>save</i>Save");
	}
}

function attachListenerToUploadPhotoLink() {
	$("#uploadPhotoLink").on("click",function() {
		$("#fileUpload").trigger("click");
	});
	
	$("#fileUpload").on("change",function(e) {
		if(validateFile()) {
			$("#uploadPhotoLink").html("<i class='material-icons'>check</i>");
			console.log($("#fileUpload").val());
//			handleRefreshEvent();
//			updateClassPhoto();
		}
	});
}

function validateFile() {
	var result = true;
	console.log($("#fileUpload").get(0).files);
	if($("#fileUpload").get(0).files.length==0) {
		result = false;
		
	}
	if(!validateFileType("#fileUpload","IMAGE")) {
		result = false;
	}if(!validateFileSize("#fileUpload","IMAGE")) {
		result = false;
	}
	return result;
}

function updateClassPhoto() {
	blockUI("Uploading Profile Picture...");
	var classId = $("#classId").val();
	$.when(ajax.upload("classinfo/"+classId+"/classPhotoUrl","uploadPhotoFrm")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully Updated!");
				$("#classPhoto").attr("src",response.classInfo.classPhotoUrl);
				$(window).unbind('beforeunload');
				$.unblockUI();
				break;
		}
	});
}

function submitUserDetails() {
	var empinfo = getEmployeeDetailsData();
	var user = getUserData();
	var userGroups = getUserGroupsData();
	
	if(GetURLParameter('action') == "EDIT"){
		var userId = $("#userSubmit").attr("data-uid");
		$.when(ajax.update("users/",user.userId,user)).done(function(response) {
			if(response.status == HttpStatus.SUCCESS){
				$.when(ajax.update("employeeinfo/",empinfo.employeeId,empinfo)).done(function(response) {
					if(response.status == HttpStatus.SUCCESS){
						if($("#fileUpload").val().length != 0){
							handleRefreshEvent();
							updateClassPhoto();
							$.when(ajax.update("usergroups/",userId,userGroups)).done(function(response) {
								if(response.status == HttpStatus.SUCCESS){
									alertify.success("Successfully Updated.");
									setTimeout(function(){redirect('admin.viewuser?id='+userId);}, 3000);
								}
							});
						} else {
							$.when(ajax.update("usergroups/",userId,userGroups)).done(function(response) {
								if(response.status == HttpStatus.SUCCESS){
									alertify.success("Successfully Updated.");
									setTimeout(function(){redirect('admin.viewuser?id='+userId);}, 3000);
								}
							});
						}
						
					}
				});
			}
		});
	} else if(GetURLParameter('action') == "ADD"){
		var userId = parseInt($("#userSubmit").attr("data-uid"));
		
		$.when(ajax.fetch("employeeinfo/"+empinfo.employeeCode)).done(function(response) {
			if(response.status == HttpStatus.SUCCESS){
				if(response.totalRecords > 0){
					$("#userSubmit").removeAttr("disabled");
					$("#empLabel").css("color","red");
					$("#userSubmit").html("<i class='material-icons left'>save</i>Save");
					alertify.warning("Employee Number already exists. Please enter a different Employee Number.");
				} else {
					$.when(ajax.create("users",user)).done(function(response) {
						if(response.status == HttpStatus.SUCCESS){
							$.when(ajax.create("employeeinfo",empinfo)).done(function(response) {
								if(response.status == HttpStatus.SUCCESS){
									if(userGroups.length > 0) {
										$.when(ajax.update("usergroups/",userId,userGroups)).done(function(response) {
											if(response.status == HttpStatus.SUCCESS){
												alertify.success("Successfully Created.");
												setTimeout(function(){redirect('admin.viewuser?id='+userId);}, 3000);
											}
										});
									} else {
										alertify.success("Successfully Created.");
										setTimeout(function(){redirect('admin.viewuser?id='+userId);}, 3000);
									}
								}
							});
						}
					});
				}
			}
		});
	}
}