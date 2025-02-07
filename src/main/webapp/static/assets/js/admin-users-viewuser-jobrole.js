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
	$.when(ajax.fetchObj("trainingflowlist/jobrole/",userDetails.jobroleId)).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var trainingFlowDetails = response.trainingFlows;
			populateTrainingFlowList(trainingFlowDetails);
			$("#jobRoleName").html(userDetails.jobName);
		}
	}); 
}

function populateTrainingFlowList(trainingFlowDetails) {
	var userId = GetURLParameter('id');
	$("#viewUserLink").attr("href", "admin.viewuser?action=EDIT&id="+userId);
	$("#tfUsergroupLink").attr("href", "admin.viewuser.usergroup?id="+userId);
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
		
		$("#trainingFlowTblBody").append($("<tr/>")
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