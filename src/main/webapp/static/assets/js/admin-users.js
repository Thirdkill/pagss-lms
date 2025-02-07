	

$(function(){
	initializeUserTbl();
	initializeJobRole();
	initializeUserType();
	attachListenerToSearchBox();
});

//---------------------LOAD USER TABLE--------------------

function initializeUserTbl() {
	var pageSize = 10;
    $("#userTblPagination").pagination({     
        dataSource: "users",
        locator: 'users',
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        className: 'paginationjs-theme-blue',
        pageSize: pageSize,     
        callback: function(data) {            
        	
            $("#userTblBody").empty(); 
            _.each(data, function(users) {
            	createUserRow(users);
            });         
        }
    });
    
    $.when(ajax.fetch("users/totalUser")).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			console.log(response);
			$("#activeCount").html(response.totalActiveRecords);
			$("#inactiveCount").html(response.totalInactiveRecords);
			$("#totalCount").html(response.totalRecords);
		}
	}); 
}
function createUserRow(users) {
	var userStatus = checkStatus(users.status);

	$("#userTblBody").append($("<tr/>")
		.append($("<td/>").addClass("min")
		.append($("<a/>").attr("href","admin.viewuser?id="+users.userId).addClass("waves-effect waves-light")
		.append($("<i/>").addClass("material-icons").html("remove_red_eye")))
		.append($("<a/>").attr("href","admin.userpage?action=EDIT&id="+users.userId).addClass("waves-effect waves-light")
		.append($("<i/>").addClass("material-icons").html("edit"))))
		.append($("<td/>").html(users.lastName))
		.append($("<td/>").html(users.firstName))
		.append($("<td/>").html(users.employeeCode))
		.append($("<td/>").html(users.jobName))
		.append($("<td/>").html(users.username))
		.append($("<td/>").html(users.userTypeDesc))
		.append($("<td/>").html(userStatus)));
}

function checkStatus(status) {
	return (status != 0)? "ACTIVE" : "INACTIVE"; 
}

//------------------SEARCH FILTER------------------

function attachListenerToSearchBox() {
	
	$("#btnReset").unbind("click").on("click", function() {
		$("#lastNameTxt").val(null);
		$("#firstNameTxt").val(null);
		$("#emplyoyeeNoTxt").val(null);
		initializeUserTbl();
		initializeJobRole();
		initializeUserType();
		$("#statusDropdown").val("All");

	});
	
	$("#btnSubmit").unbind("click").on("click", function() {
		var user = {};
		user.lastName = $("#lastNameTxt").val();
		user.firstName = $("#firstNameTxt").val();
		user.employeeNo = $("#emplyoyeeNoTxt").val();
		user.jobRoleId = ($("#jobRoleDropdown").val()=="All")? 0 : parseInt($("#jobRoleDropdown").val());
		user.userTypeId = ($("#userTypeDropdown").val()=="All")? 0 : parseInt($("#userTypeDropdown").val());
		user.status = ($("#statusDropdown").val()=="All")? 2 : parseInt($("#statusDropdown").val());
		searchUser(user);
	});
}

function searchUser(user) {
	
	var pageSize = 10;
    $("#userTblPagination").pagination({     
        dataSource: "users/search",
        locator: 'users',
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        ajax: {data: user, type: "POST"},
        className: 'paginationjs-theme-blue',
        pageSize: pageSize,     
        callback: function(data) {                      
            $("#userTblBody").empty();            
            _.each(data, function(users) {
            	createUserRow(users);
            });         
        }
    });
}

//----------------POPULATE SEARCH FILTER FIELDS---------------------
function initializeUserType() {
	$.when(ajax.fetch("users/usertypes")).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var userTypeFields = response.usertypes;
			populateUserTypeFields(userTypeFields);
		}
	}); 
}

function initializeJobRole() {
	$.when(ajax.fetch("jobroles")).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var jobRoleFields = response.jobRoles;
			populateJobRoleFields(jobRoleFields);
		}
	}); 
}

function populateJobRoleFields(jobRoleFields) {
	$("#jobRoleDropdown").html("");
	$("#jobRoleDropdown").append($("<option/>").attr("selected","").html("All"));
	_.each(jobRoleFields,function(jobRoleField) {
	$("#jobRoleDropdown").append($("<option/>").attr("value",jobRoleField.jobRoleId).html(jobRoleField.jobName));
	})
	$("#jobRoleDropdown").trigger("contentChanged");
}

function populateUserTypeFields(userTypeFields) {
	$("#userTypeDropdown").html("");
	$("#userTypeDropdown").append($("<option/>").attr("selected","").html("All"));
	_.each(userTypeFields,function(userTypeField) {
		$("#userTypeDropdown").append($("<option/>").attr("value",userTypeField.userTypeId).html(userTypeField.userTypeDesc));
	})
	$("#userTypeDropdown").trigger("contentChanged");
}