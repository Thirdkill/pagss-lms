/**
 * 
 */
$(function(){
	initUserDetails();
});

function initUserDetails(){
	var userId = GetURLParameter('id');
	$.when(ajax.fetchObj("users/",userId)).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var userDetails = response.user;
			initTrainingFlowList(userDetails);
		}
	}); 
}

function initTrainingFlowList(userDetails) {
	var userId = GetURLParameter('id');
	$.when(ajax.fetchObj("usergroups/",userId)).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var userGroupLists = response.usergroups;
			_.each(userGroupLists,function(userGroupList) {
				$("#userDetailsContainer").append($("<div/>").attr("class","hello row")
				 .append($("<div/>").attr("class","divcard")
				  .append($("<div/>").attr({"class":"row","style":"margin: 15px 0"})
					.append($("<div/>").attr("class","left")
					 .append($("<b/>").html("User Group: "))
					 .append($("<span/>").html(userGroupList.userGroupName))))
				  .append($("<div/>").attr("class","row")
				   .append($("<table/>").attr("class","highlight centered")
				    .append($("<thead/>")
					 .append($("<tr/>")
				      .append($("<th/>").html("Order No."))
					  .append($("<th/>").html("Category"))
					  .append($("<th/>").html("Course"))
					  .append($("<th/>").html("Code"))
					  .append($("<th/>").html("Prerequisite"))
					  .append($("<th/>").html("Required"))
					  .append($("<th/>").html("Recurring"))
					  .append($("<th/>").html("Interval"))))
					 .append($("<tbody/>").attr({"id":"trainingFlowTblBody_"+userGroupList.userGroupId,"style":"padding: 5px !important;"}))))));
				
				$.when(ajax.fetchObj("trainingflowlist/usergroup/",userGroupList.userGroupId)).done(function(response) {
					if(response.status == HttpStatus.SUCCESS){
						var trainingFlowDetails = response.trainingFlows;
						populateTrainingFlowList(trainingFlowDetails);
					}
				});
			});
		}
	});

}

function populateTrainingFlowList(trainingFlowDetails) {
	var userId = GetURLParameter('id');
	console.log("Dumaan");
	$("#viewUserLink").attr("href", "admin.viewuser?action=EDIT&id="+userId);
	$("#tfJobroleLink").attr("href", "admin.viewuser.jobrole?id="+userId);
	$("#editUserProfile").attr("href", "admin.userpage?action=EDIT&id="+userId);
	_.each(trainingFlowDetails,function(trainingFlowDetail) {
		var checkIndicator1 = "";
		var checkIndicator22 = "";
		var checkIndicator2 = "";
		var checkIndicator22 = "";
		if(trainingFlowDetail.isRequired == 1){
			checkIndicator1 = $("<input/>").attr({"type":"checkbox","checked":"checked","disabled":"disabled"});
			checkIndicator12 = $("<span/>").css("display","");
		} else if(trainingFlowDetail.isRequired == 0){
			checkIndicator1 = $("<input/>").attr({"type":"checkbox","disabled":"disabled"});
			checkIndicator12 = $("<span/>").css("display","none");
		}
		if(trainingFlowDetail.isRecurring == 1){
			checkIndicator2 = $("<input/>").attr({"type":"checkbox","checked":"checked","disabled":"disabled"});
			checkIndicator22 = $("<span/>").css("display","");
		} else if(trainingFlowDetail.isRequired == 0){
			checkIndicator2 = $("<input/>").attr({"type":"checkbox","disabled":"disabled"});
			checkIndicator22 = $("<span/>").css("display","none");
		}
		
		$("#trainingFlowTblBody_"+trainingFlowDetail.userGroupId).append($("<tr/>")
		.append($("<td/>").html(trainingFlowDetail.orderNo))
		.append($("<td/>").html(trainingFlowDetail.categoryName))
		.append($("<td/>").html(trainingFlowDetail.courseName))
		.append($("<td/>").html(trainingFlowDetail.courseCode))
		.append($("<td/>").html(trainingFlowDetail.preCourseName))
		.append($("<td/>").css("padding-top","10px")
		.append($("<label/>").css("margin-left","20px")
		.append(checkIndicator1)
		.append(checkIndicator12)))
		.append($("<td/>").css("padding-top","10px")
		.append(checkIndicator2)
		.append(checkIndicator22))
		.append($("<td/>").html(trainingFlowDetail.interval+" Days")));
	});
	
}