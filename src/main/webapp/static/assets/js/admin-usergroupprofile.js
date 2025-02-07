/**
 * 
 */

$(function(){
	defineGlobalList();
	initJobRoleList();
	initUserGroupDetails();
	attachListenerToSubmitUserGroupBtn();
	attachListenerToSaveMemberBtn();
	attachListenerToAddMemberBtn();
	attachListenerToPopulateEmployeeList();
});

function defineGlobalList() {
	usergroupMembers = [];
	checkedUserGroupMembers = [];
}

function initUserGroupDetails(){
	$.when(initUserGroupList(), initUserList()).done(function(response1, response2) {
		if(GetURLParameter('action') == "EDIT"){
			$("#fieldPage").text("Edit User Group");
			$(document).attr("title", "PAGSS - Edit User Group");
			var userGroupId = GetURLParameter('id');
			$.when(ajax.fetchObj("usergroups/info/",userGroupId)).done(function(response) {
				if(response.status == HttpStatus.SUCCESS){
					var userGroupDetails = response.usergroup;
					populateUserGroupDetails(userGroupDetails);
					initUserGroupMemberList(userGroupId);
					$(".q").attr("class","active");
				} 
			}); 
		} else if(GetURLParameter('action') == "ADD"){
			$("#fieldPage").text("Create User Group");
			$(document).attr("title", "PAGSS - Create User Group");
			$.when(ajax.fetch("usergroups/generate-usergroupcode")).done(function(response) {
				var userGroupDetails = response.usergroup;
				$("#userGroupSubmit").attr("data-id",userGroupDetails.userGroupId);
				$("#userGroupCodeTxtBox").val(userGroupDetails.userGroupCode);
				$(".q").attr("class","active");
				$("#userGroupStatus").prop('checked', true);
				validateIsSubGroupDropDown();
			});
		}
		
			
		$('#isSubGroupDropDown').on("select2:select", function(e) { 
			validateIsSubGroupDropDown();
		});
	});
	
}

function initUserGroupList() {
	return $.when(ajax.fetch("usergroups")).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var userGroupFields = response.usergroups;
			populateUserGroupFields(userGroupFields);
		}
	}); 
}

function initUserGroupMemberList(userGroupId) {
	var userGroupId = GetURLParameter('id');
	$.when(ajax.fetchObj("usergroups/members/",userGroupId)).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var userGroupMembers = response.usergroupmembers;
			usergroupMembers = userGroupMembers;
			initMemberList();
		}
	}); 
}

function initUserList() {
	return $.when(ajax.fetch("users/userlist")).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var userFields = response.users;
			populateUserFields(userFields);
		}
	}); 
}

function initJobRoleList() {
	$.when(ajax.fetch("jobroles")).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var jobRoleFields = response.jobRoles;
			populateJobRoleFields(jobRoleFields);
		}
	}); 
}

function initEmployeeList() {
	var pageSize = 10;
    $("#employeeTblPagination").pagination({     
        dataSource: "users/fetch-employee",
        locator: 'users',
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        className: 'paginationjs-theme-blue',
        pageSize: pageSize,     
        callback: function(data) {            
            $("#employeeTblBody").empty();
            _.each(data, function(user) {
            	createEmployeeRow(user);
            });         
        }
    });
}

function searchEmployeeList() {
	var users = {};
	users.lastName = $("#userSearchTxt").val();
	users.firstName = $("#userSearchTxt").val();
	users.employeeNo = $("#userSearchTxt").val();
	users.jobRoleId = ($("#jobRoleDropdown").val()=="All")? 0 : parseInt($("#jobRoleDropdown").val());
	initSearchEmployeeList(users);
}

function initSearchEmployeeList(user) {
	var pageSize = 10;
    $("#employeeTblPagination").pagination({     
        dataSource: "users/search-employee",
        locator: "users",
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        ajax: {data: user, type: "POST"},
        className: 'paginationjs-theme-blue',
        pageSize: pageSize,     
        callback: function(data) {                      
            $("#employeeTblBody").empty();            
            _.each(data, function(users) {
            	createEmployeeRow(users);
            });         
        }
    });
}

function createEmployeeRow(user) {
	var usergroupId = $("#userGroupSubmit").attr("data-id");
	$("#checkAllChkBox").prop("checked",false);
	$("#employeeTblBody").append($("<tr/>").attr("id","emprow_"+user.userId)
	.append($("<td/>").addClass("min")
	.append($("<label/>").css("margin-left","15px")
	.append($("<input/>").attr({"class":"emplist chkbox","type":"checkbox","data-uid":user.userId})
		.prop("checked",checkIfUserExist(checkedUserGroupMembers,user))
		.bind("change",function() {
			if($(this).prop("checked") != true) {
				if(checkIfUserExist(checkedUserGroupMembers,user)) {
					deleteUserFromCheckList(user);
					$("#checkAllChkBox").prop("checked",false);
				}
			} else {
				if(!checkIfUserExist(checkedUserGroupMembers, user)) {
					user.userGroupId = parseInt(usergroupId);
					checkedUserGroupMembers.push(user);
				}
			}
		}))
	.append($("<span/>"))))
	.append($("<td/>").attr("id","lname_"+user.userId).html(user.lastName))    
	.append($("<td/>").attr("id","fname_"+user.userId).html(user.firstName))
	.append($("<td/>").attr("id","ecode_"+user.userId).html(user.employeeCode))
	.append($("<td/>").attr("id","jrole_"+user.userId).html(user.jobName)));
}

function deleteUserFromCheckList(user) {
	var result = false;
	var ugMember = [];
	ugMembers = checkedUserGroupMembers;
	_.each(ugMembers,function(userObj) {
		if(userObj.userId == user.userId) {
			checkedUserGroupMembers = deleteItem(user);
		}
	});
	return result;
}

function deleteUserFromList(user) {
	var result = false;
	var ugMember = [];
	ugMembers = usergroupMembers;
	_.each(ugMembers,function(userObj) {
		if(userObj.userId == user.userId) {
			usergroupMembers = deleteMembers(user);
		}
	});
	return result;
}

function deleteMembers(user) {
	var resultArray = [];
	_.each(usergroupMembers,function(userObj) {
		if(userObj.userId != user.userId) {
			resultArray.push(userObj);
		}
	});
	return resultArray;
}

function deleteItem(user) {
	var resultArray = [];
	_.each(checkedUserGroupMembers,function(userObj) {
		if(userObj.userId != user.userId) {
			resultArray.push(userObj);
		}
	});
	return resultArray;
}

function checkIfUserExist(users,user) {
	var result = false;
	_.each(users,function(userObj) {
		if(userObj.userId == user.userId) {
			result = true;
		}
	});
	return result;
}

function createUserGroupMemberRow(userGroupMembers) {
	$("#userGroupMembersTb").html("");
	_.each(userGroupMembers,function(userGroupMember) {
		$("#userGroupMembersTb").append($("<tr/>")
		.append($("<td/>")
			/*Billy pasingit ako dito ng checkbox hindi ko kasi mainsert
			 * ito yung code ko
			 * .append($("<label/>").css("margin-left","15px")
						.append($("<input/>").attr({"class":"emplist chkbox","type":"checkbox","data-uid":user.userId}))
						.append($("<span/>")))
			 * pakicheck kung bakit ayaw gumana Thanks.*/
			.append($("<a/>")
			.append($("<i/>").attr({"class":"material-icons iconfont","style":"cursor:pointer;"}).html("delete")
				.bind("click",function() {
					deleteUserFromList(userGroupMember);
					initMemberList();
				}))))
		.append($("<td/>").html(userGroupMember.lastName))    
		.append($("<td/>").html(userGroupMember.firstName))
		.append($("<td/>").html(userGroupMember.employeeCode))
		.append($("<td/>").html(userGroupMember.jobName)));
	});
}

function populateUserGroupDetails(userGroupDetails) {
	$("#userGroupCodeTxtBox").val(userGroupDetails.userGroupCode);
	$("#userGroupCodeTxtBox").attr("disabled","disabled");
	$("#userGroupNameTxtBox").val(userGroupDetails.userGroupName);
	$("#userGroupDesc").val(userGroupDetails.description);
	$("#isSubGroupDropDown").val(userGroupDetails.isSubGroup.toString()).trigger("change");
	$("#mainUserGroupDropDown").val(userGroupDetails.mainUserGroupCode).trigger("change");
	$("#groupSupervisorDropDown").val(userGroupDetails.supervisorId).trigger("change");
	$("#userGroupSubmit").attr("data-id",userGroupDetails.userGroupId);
	if (userGroupDetails.status == 1) {
		$("#userGroupStatus").prop('checked', true);
	} else if (userGroupDetails.status != 1) {
		$("#userGroupStatus").prop('checked', false);
	}
	if (userGroupDetails.mainUserGroupCode != 0) {
		$('#mainUserGroupDropDown').removeAttr("disabled");
	}
	validateIsSubGroupDropDown();
}

function populateJobRoleFields(jobRoleFields) {
	$("#jobRoleDropdown").html("");
	$("#jobRoleDropdown").append($("<option/>").attr("selected","").html("All"));
	_.each(jobRoleFields,function(jobRoleField) {
	$("#jobRoleDropdown").append($("<option/>").attr("value",jobRoleField.jobRoleId).html(jobRoleField.jobName));
	})
	$("#jobRoleDropdown").trigger("contentChanged");
}

function populateUserGroupFields(userGroupFields) {
	var userGroupId = GetURLParameter('id');
	$("#mainUserGroupDropDown").html("");
	$("#mainUserGroupDropDown").append($("<option/>").html("Please Specify...").attr("value","0"));
	_.each(userGroupFields,function(userGroupField) {
		if(userGroupField.userGroupId != userGroupId){
			$("#mainUserGroupDropDown").append($("<option/>").attr("value",userGroupField.userGroupCode).html(userGroupField.userGroupName));
		}
		
	})
	$("#mainUserGroupDropDown").trigger("contentChanged");
}

function populateUserFields(userFields) {
	$("#groupSupervisorDropDown").html("");
	$("#groupSupervisorDropDown").append($("<option/>").html("Please Specify...").attr("value","0"));
	_.each(userFields,function(userField) {
		$("#groupSupervisorDropDown").append($("<option/>").attr("value",userField.userId).html(userField.fullName));
	})
	$("#groupSupervisorDropDown").trigger("contentChanged");
}

function validateFields() {
	var incField = 0;
	if($("#userGroupCodeTxtBox").val().length == 0){
		$("#ugcLabel").css("color","red");
		incField = 1;
	}else{$("#ugcLabel").css("color","");}
	
	if($("#userGroupNameTxtBox").val().length == 0){
		$("#ugnLabel").css("color","red");
		incField = 1;
	}else{$("#ugnLabel").css("color","");}
	
	if(incField != 1){
		submitUserGroupDetails();
	} else{
		alertify.warning("Please fill up the required fields.");
		$("#userGroupSubmit").removeAttr("disabled");
		$("#userGroupSubmit").html("<i class='material-icons left'>save</i>Save");
	}
}

function validateIsSubGroupDropDown() {
	if($("#isSubGroupDropDown").val() == 2){
		$("#mainUserGroupDropDown").attr("disabled","disabled");
		$("#mainUserGroupDropDown").val(0).trigger("contentChanged");
	} else if($("#isSubGroupDropDown").val() == 1){
		$("#mainUserGroupDropDown").removeAttr("disabled");
	}
}

function submitUserGroupDetails() {
	var usergroupId = $("#userGroupSubmit").attr("data-id");
	var usergroup = getUserGroupDetails();
	
	if(GetURLParameter('action') == "EDIT"){
		$.when(ajax.update("usergroups/info/",usergroupId,usergroup)).done(function(response) {
			if(response.status == HttpStatus.SUCCESS){
				if(usergroupMembers.length == 0){
					alertify.success("Successfully Updated.");
					setTimeout(function(){ redirect("admin.usergroup"); }, 3000);
				} else if(usergroupMembers.length > 0){
					$.when(ajax.update("usergroups/members/",usergroupId,usergroupMembers)).done(function(response) {
						if(response.status == HttpStatus.SUCCESS){
							alertify.success("Successfully Updated.");
							setTimeout(function(){ redirect("admin.usergroup"); }, 3000);
						}
					});
				}
			}
		});
	} else if(GetURLParameter('action') == "ADD"){
		$.when(ajax.fetch("usergroups/check/"+usergroup.userGroupCode)).done(function(response) {
			if(response.status == HttpStatus.SUCCESS){
				if(response.totalRecords > 0){
					alertify.warning("User Group Code already exists. Please enter a different User Group Code.");
					$("#ugcLabel").css("color","red");
					$("#userGroupSubmit").removeAttr("disabled");
					$("#userGroupSubmit").html("<i class='material-icons left'>save</i>Save");
				} else {
					$.when(ajax.create("usergroups",usergroup)).done(function(response) {
						if(response.status == HttpStatus.SUCCESS){
							if(usergroupMembers.length == 0){
								alertify.success("Successfully Created.");
								setTimeout(function(){ redirect("admin.usergroup"); }, 3000);
							} else if(usergroupMembers.length > 0){
								$.when(ajax.update("usergroups/members/",usergroupId,usergroupMembers)).done(function(response) {
									if(response.status == HttpStatus.SUCCESS){
										alertify.success("Successfully Created.");
										setTimeout(function(){ redirect("admin.usergroup"); }, 3000);
									}
								});
							}
						}
					});
				}
			}
		});
		

	}
}

function getUserGroupDetails() {
	var usergroup = {};
	
	usergroup.userGroupCode = $("#userGroupCodeTxtBox").val();
	usergroup.userGroupName = $("#userGroupNameTxtBox").val();
	usergroup.description = $("#userGroupDesc").val();
	usergroup.isSubGroup = parseInt($("#isSubGroupDropDown").val());
	usergroup.mainUserGroupCode = $("#mainUserGroupDropDown").val();
	usergroup.supervisorId = parseInt($("#groupSupervisorDropDown").val());
	
	if ($('#userGroupStatus').is(':checked')) {
		usergroup.status = 1;
	} else if(!$('#userGroupStatus').is(':checked')){
		usergroup.status = 0;
	}
	
	return usergroup;
}

function attachListenerToPopulateEmployeeList() {
	$("#jobRoleDropdown").change(function() {
		if($("#userSearchTxt").val().length != 0 || $("#jobRoleDropdown").val() != null){
			searchEmployeeList();
		} else {
			initEmployeeList();
		}
	});
}

function attachListenerToSaveMemberBtn() {
	$("#userGroupMemberSubmit").on("click",function() {
		usergroupMembers = pushCheckedListToMemberList();
		if(usergroupMembers.length == 0){
			alertify.warning("Please select at least one user for this user group.");
		} else{
			alertify.dismissAll();
			$("#userGroupMemberSubmit").addClass("modal-close");
			initMemberList();
		}
	});
}

function pushCheckedListToMemberList() {
	var tempusergroupMembers = [];
	_.each(checkedUserGroupMembers,function(user) {
		tempusergroupMembers.push(user);
	});
	return tempusergroupMembers;
}

function initMemberList() {
	var pageSize = 10;
    $("#userGroupMembersPagination").pagination({     
        dataSource: usergroupMembers,
        className: 'paginationjs-theme-blue',
        pageSize: pageSize,     
        callback: function(data) {            
            $("#userGroupMembersTb").empty();
        	createUserGroupMemberRow(data);                                
        }
    });
} 

function attachListenerToSubmitUserGroupBtn() {
	$("#userGroupSubmit").unbind("click").click(function(){
		$("#userGroupSubmit").attr("disabled","disabled");
		$("#userGroupSubmit").html("Saving...");
		validateFields();
	});
}

function attachListenerToAddMemberBtn() {
	$("#addMemberModal").unbind("click").click(function(){
		$("#userSearchTxt").val("");
		$("#jobRoleDropdown").val("All").trigger("change");
		$("#userGroupMemberSubmit").removeClass("modal-close");
		initEmployeeList();
		attachListenerToCheckAll();
		attachListenerToSearchBox();
		checkedUserGroupMembers = usergroupMembers;
	});
}

function attachListenerToSearchBox() {
	
	$("#btnSearchReset").unbind("click").on("click", function() {
		$("#userSearchTxt").val(null);
		initEmployeeList();
		initJobRoleList();
		$("#jobRoleDropdown").val("All");
	});
	
	$("#userSearchTxt").on("keyup",function(e) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == 13) {
			searchEmployeeList();
		}
		if(keycode == 8 && $("#userSearchTxt").val().length == 0) {
			searchEmployeeList();
		}
		return true;
	});
	
}

function attachListenerToCheckAll() {
	$("#checkAllChkBox").on("change",function() {
		var sectionId = $("#existingSectionValue").val();
		if($(this).prop("checked")) {
			$(".chkbox").prop("checked",true).trigger('change');
		} else {
			$(".chkbox").prop("checked",false).trigger('change');
		}
	});
}