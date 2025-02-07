/**
 * 
 */

$(function(){
	initUserDetails();
	initButtons();
});

function initButtons(){
	$("#userResetPassword").unbind("click").click(function(){
		var email = $("#emailLabel").html();
		var userId = GetURLParameter('id');
		var user = {};
		user.userId = userId;
		$.when(ajax.update("users/password/",userId, user)).done(function(response) {
			if(response.status == HttpStatus.SUCCESS){
				alertify.alert('Reset Password', 'Password has successfully changed and it was sent to '+email+".");
			}
		});
	});
}

function initUserDetails(){
	var userId = GetURLParameter('id');
	$.when(ajax.fetchObj("users/",userId)).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var userDetails = response.user;
			populateUserDetails(userDetails);
		}
	}); 
}

function populateUserDetails(userDetails) {
	var userId = GetURLParameter('id');
	var utid = userDetails.userTypeId;
	var hired = new Date(userDetails.dateHired).toDateString("MM-dd-yyyy");
	var created = new Date(userDetails.createdAt).toDateString("MM-dd-yyyy hh:MM:ss TT");
	var lastModidied = new Date(userDetails.lastModifiedAt).toDateString("MM-dd-yyyy hh:MM:ss TT");
	$("#employeeNoLabel").html(userDetails.employeeCode);
	$("#fullNameLabel").html(userDetails.fullName);
	$("#userTypeLabel").html(userDetails.userTypeDesc);
	$("#usernameLabel").html(userDetails.username);
	$("#jobRoleLabel").html(userDetails.jobName);
	$("#hireDateLabel").html(userDetails.dateHired);
	$("#mobileNoLabel").html(userDetails.mobileNo);
	$("#emailLabel").html(userDetails.email);
	$("#dateCreatedLabel").html(created);
	$("#dateModifiedLabel").html(lastModidied);
	$("#modifiedByLabel").html(userDetails.modifiedBy);
	$("#editUserProfile").attr("href", "admin.userpage?action=EDIT&id="+userId);
	$("#viewUserLink").attr("href", "admin.viewuser?action=EDIT&id="+userId);
	$("#tfJobroleLink").attr("href", "admin.viewuser.jobrole?id="+userId);
	$("#tfUsergroupLink").attr("href", "admin.viewuser.usergroup?id="+userId);
	
	if(userDetails.status == "1"){
		$("#statusLabel").html("Active");
	} else if(userDetails.status == "2"){
		$("#statusLabel").html("Inactive");
	}
}
