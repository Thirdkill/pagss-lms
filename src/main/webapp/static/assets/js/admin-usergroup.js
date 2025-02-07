/**
 * 
 */
$(function(){
	initializeButtons();
	initializeUserGroupTbl();
	attachListenerToSearchBox();
});

function initializeButtons() {
	$("#clearSearchBox").unbind("click").on("click", function() {
		$("#searchUserGroup").val("");
		initializeUserGroupTbl();
	});
}

function initializeUserGroupTbl() {
	var pageSize = 10;
    $("#usergroupPagination").pagination({     
        dataSource: "usergroups/pages",
        locator: 'usergroups',
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        className: 'paginationjs-theme-blue',
        pageSize: pageSize,     
        callback: function(data) {                      
            $("#usergroupTblBody").empty(); 
            _.each(data, function(usergroups) {
            	createUserGroupRow(usergroups);
            });         
        }
    });
}

function createUserGroupRow(usergroups) {
	var usergroupStatus = checkStatus(usergroups.status);
	
	$("#usergroupTblBody").append($("<tr/>")
		.append($("<td/>").addClass("min")
		.append($("<a/>").attr("href","admin.usergrouppage?action=EDIT&id="+usergroups.userGroupId).addClass("waves-effect waves-light")
		.append($("<i/>").addClass("material-icons").html("edit"))))
		.append($("<td/>").html(usergroups.userGroupCode))
		.append($("<td/>").html(usergroups.userGroupName))
		.append($("<td/>").html(usergroups.description))
		.append($("<td/>").html(usergroups.fullName))
		.append($("<td/>").html(usergroupStatus))
		.append($("<td/>").html(usergroups.lastModifiedAt))
		.append($("<td/>").html(usergroups.modifiedBy)));
}

function checkStatus(status) {
	return (status != 0)? "ACTIVE" : "INACTIVE"; 
}

function attachListenerToSearchBox() {
	$("#searchUserGroup").on("keyup",function(e) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == 13) {
			var keyword = $(this).val();
			searchUserGroup(keyword);
		}
		
		if(keycode == 8 && $("#searchUserGroup").val().length == 0) {
			initializeUserGroupTbl();
		}
		return true;
	})
}

function searchUserGroup(keyword) {
	var pageSize = 10;
    $("#usergroupPagination").pagination({     
        dataSource: "usergroups/search",
        locator: 'usergroups',
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        ajax: {data: {keyword : keyword}, type: "POST"},
        className: 'paginationjs-theme-blue',
        pageSize: pageSize,     
        callback: function(data) {                      
            $("#usergroupTblBody").empty();            
            _.each(data, function(usergroups) {
            	createUserGroupRow(usergroups);
            });         
        }
    });
}