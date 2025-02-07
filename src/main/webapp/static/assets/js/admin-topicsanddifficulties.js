/**
 * 
 */

$(function(){
	initTopicsAndDifficultiesTbl();
	attachListenerToSubmitButton();
	attachListenerToCancelButton();
	attachListenerToAddModal();
	attachListenerToSearchBox();
});

function initTopicsAndDifficultiesTbl(){
	var pageSize = 10;
	$("#topicsPagination").pagination({     
		dataSource: "trainingtopics/pages",
		locator: 'trainingTopics',
		totalNumberLocator: function(response) {
			return response.totalRecords;
		},
		className: 'paginationjs-theme-blue',
		pageSize: pageSize,     
		callback: function(data) {                      
			$("#topicsTblBody").empty(); 
			_.each(data, function(trainingTopics) {
				populateTopicsRow(trainingTopics);
			});       
		}
	});

	$("#difficultyLevelsPagination").pagination({     
		dataSource: "difficultylevels/pages",
		locator: 'difficultyLevels',
		totalNumberLocator: function(response) {            
			return response.totalRecords;
		},
		className: 'paginationjs-theme-blue',
		pageSize: pageSize,     
		callback: function(data) {                      
			$("#difficultyLevelsTblBody").empty(); 
			_.each(data, function(difficultyLevels) {
				populateDifficultiesRow(difficultyLevels);
			});         
		}
	});
}

function searchTopicsAndDifficulties(keyword) {
	var pageSize = 10;
	$("#topicsPagination").pagination({     
		dataSource: "trainingtopics/search",
		locator: 'trainingTopics',
		totalNumberLocator: function(response) {            
			return response.totalRecords;
		},
		ajax: {data: {keyword : keyword}, type: "POST"},
		className: 'paginationjs-theme-blue',
		pageSize: pageSize,     
		callback: function(data) {                      
			$("#topicsTblBody").empty();            
			_.each(data, function(trainingTopics) {
				populateTopicsRow(trainingTopics);
			});         
		}
	});

	$("#difficultyLevelsPagination").pagination({     
		dataSource: "difficultylevels/search",
		locator: 'difficultyLevels',
		totalNumberLocator: function(response) {            
			return response.totalRecords;
		},
		ajax: {data: {keyword : keyword}, type: "POST"},
		className: 'paginationjs-theme-blue',
		pageSize: pageSize,     
		callback: function(data) {                      
			$("#difficultyLevelsTblBody").empty();            
			_.each(data, function(difficultyLevels) {
				populateDifficultiesRow(difficultyLevels);
			});         
		}
	});
}

function populateTopicsRow(trainingTopics) {
	var checkIndicator1 = "";
	var checkIndicator2 = "";
	if(trainingTopics.status == 1){
		checkIndicator1 = $("<input/>").attr({"type":"checkbox","checked":"checked","disabled":"disabled"});
		checkIndicator2 = $("<span/>").css("display","");
	} else if(trainingTopics.status == 2){
		checkIndicator1 = $("<input/>").attr({"type":"checkbox","disabled":"disabled"});
		checkIndicator2 = $("<span/>").css("display","none");
	}
	
	$("#topicsTblBody").append($("<tr/>")
	.append($("<td/>").attr({"data-target":"modal1","style" : "cursor:pointer;","class":"min modal-trigger"})
		.bind("click",function() {
			$("#modalTitle").html("Edit Topic/Difficulty Level");
			$("#fieldName").html("Topic*");
			$("#submitButton").attr({"data-id":trainingTopics.topicId,"data-action":"EDIT","data-saveAction":"TOPIC"});
			$("#topicDifficultyDropDown").val(1).trigger("change");
			$("#topicDifficultyDropDown").attr("disabled","disabled");
			$("#topicDifficultyNameTxtBox").val(trainingTopics.topicDesc);
			$("#topicDifficultyNameTxtBox").attr("data-orig",trainingTopics.topicDesc);
			if(trainingTopics.status == 1){
				$("#statusCheckBox").prop('checked', true);
			} else if(trainingTopics.status == 2){
				$("#statusCheckBox").prop('checked', false);
			}
		})
	.append($("<a/>").addClass("waves-effect waves-light")
	.append($("<i/>").addClass("material-icons").html("edit"))))
	.append($("<td/>").attr("class","large").html(trainingTopics.topicDesc))
	.append($("<td/>").css({"padding-top":"10px"})
	.append($("<label/>").css("margin-left","20px")
	.append(checkIndicator1)
	.append(checkIndicator2))));
}                                                                        

function populateDifficultiesRow(difficultyLevels) {
	var checkIndicator1 = "";
	var checkIndicator2 = "";
	if(difficultyLevels.status == 1){
		checkIndicator1 = $("<input/>").attr({"type":"checkbox","checked":"checked","disabled":"disabled"});
		checkIndicator2 = $("<span/>").css("display","");
	} else if(difficultyLevels.status == 2){
		checkIndicator1 = $("<input/>").attr({"type":"checkbox","disabled":"disabled"});
		checkIndicator2 = $("<span/>").css("display","none");
	}
	
	$("#difficultyLevelsTblBody").append($("<tr/>")
	.append($("<td/>").attr({"style":"cursor:pointer;","data-target":"modal1","style" : "cursor:pointer;","class":"min modal-trigger"})
		.bind("click",function() {
			$("#modalTitle").html("Edit Topic/Difficulty Level");
			$("#fieldName").html("Difficulty level*");
			$("#submitButton").attr({"data-id":difficultyLevels.difficultyId,"data-action":"EDIT","data-saveAction":"DIFFICULTY"});
			$("#topicDifficultyDropDown").val(2).trigger("change");
			$("#topicDifficultyDropDown").attr("disabled","disabled");
			$("#topicDifficultyNameTxtBox").val(difficultyLevels.difficultyName);
			$("#topicDifficultyNameTxtBox").attr("data-orig",difficultyLevels.difficultyName);
			if(difficultyLevels.status == 1){
				$("#statusCheckBox").prop('checked', true);
			} else if(trainingTopics.status == 2){
				$("#statusCheckBox").prop('checked', false);
			}
		})
	.append($("<a/>").addClass("waves-effect waves-light")
	.append($("<i/>").addClass("material-icons").html("edit"))))
	.append($("<td/>").attr("class","large").html(difficultyLevels.difficultyName))
	.append($("<td/>").css({"padding-top":"10px"})
	.append($("<label/>").css("margin-left","20px")
	.append(checkIndicator1)
	.append(checkIndicator2))));
}

function validateFields() {
	var incField = 0;
	if($("#topicDifficultyDropDown").val() == 0 || $("#topicDifficultyDropDown").val() == null){
		$("#typeLabel").css("color","red");
		incField = 1;
	}else{$("#typeLabel").css("color","");}
	
	if($("#topicDifficultyNameTxtBox").val().length == 0){
		$("#fieldName").css("color","red");
		incField = 1;
	}else{$("#fieldName").css("color","");}
	
	if(incField != 1){
		submitTopicDifficultyDetails();
	} else{
		alertify.warning("Please fill up the required fields.");
		$("#submitButton").removeAttr("disabled");
		$("#submitButton").html("<i class='material-icons left'>save</i>Save");
	}
}

function attachListenerToSearchBox() {
	$("#searchTopicDifficultyTxtBox").on("keyup",function(e) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == 13) {
			var keyword = $(this).val();
			searchTopicsAndDifficulties(keyword);
		}
		if(keycode == 8 && $("#searchTopicDifficultyTxtBox").val().length == 0) {
			initTopicsAndDifficultiesTbl();
		}
		return true;
	});
	
	$("#clearSearhField").unbind("click").click(function(){
		$("#searchTopicDifficultyTxtBox").val("");
		initTopicsAndDifficultiesTbl();
	});
}

function attachListenerToAddModal() {
	$("#addTopicDifficultyButton").on("click",function() {
		$("#modalTitle").html("Add Topic/Difficulty Level");
		$("#submitButton").attr({"data-action":"ADD"});
		$("#topicDifficultyDropDown").removeAttr("disabled");
	});
	$("#topicDifficultyDropDown").change(function() {
		if($("#topicDifficultyDropDown").val() == "1"){
			$("#fieldName").html("Topic*");
			$("#submitButton").attr({"data-saveAction":"TOPIC"});
		} else if($("#topicDifficultyDropDown").val() == "2"){
			$("#fieldName").html("Difficulty level*");
			$("#submitButton").attr({"data-saveAction":"DIFFICULTY"});
		}
	});
}

function attachListenerToSubmitButton() {
	$("#submitButton").unbind("click").on("click",function() {
		$("#submitButton").attr("disabled","disabled");
		$("#submitButton").html("Saving...");
		validateFields();
	});
}

function clearFields() {
	$("#topicDifficultyDropDown").val("").trigger("change");
	$("#topicDifficultyNameTxtBox").val("");
	$("#fieldName").css("color","");
	$("#typeLabel").css("color","");
	$("#statusCheckBox").prop('checked', true);
}

function attachListenerToCancelButton() {
	$("#cancelButton").on("click",function() {
		clearFields();
	});
}

function attachListenerToModalClose() {
	$("#modal1").modal('close');
	clearFields();
}

function submitTopicDifficultyDetails() {
	var topic = {};
	var difficulty = {};
	var id = $("#submitButton").attr("data-id");
	var origData = $("#topicDifficultyNameTxtBox").attr("data-orig");
	var action = $("#submitButton").attr("data-action");
	var saveAction = $("#submitButton").attr("data-saveAction");
	
	if(action == "ADD"){
		if(saveAction == "TOPIC"){
			topic.topicDesc = $("#topicDifficultyNameTxtBox").val();
			if($('#statusCheckBox').is(':checked')){
				topic.status = 1;
			}else if(!$('#statusCheckBox').is(':checked')){
				topic.status = 2;
			}
			
			$.when(ajax.fetch("trainingtopics/check/"+topic.topicDesc)).done(function(response) {
				if(response.status == HttpStatus.SUCCESS){
					if(response.totalRecords > 0){
						alertify.warning("Topic already exists. Please enter a different Topic.");
						$("#fieldName").css("color","red");
						$("#submitButton").removeAttr("disabled");
						$("#submitButton").html("<i class='material-icons left'>save</i>Save");
					} else {
						$.when(ajax.create("trainingtopics",topic)).done(function(response) {
							if(response.status == HttpStatus.SUCCESS){
								attachListenerToModalClose();
								alertify.success("Successfully Created.");
								initTopicsAndDifficultiesTbl();
							}
						});
					}
				}
			});
			

		} else if(saveAction == "DIFFICULTY"){
			difficulty.difficultyName = $("#topicDifficultyNameTxtBox").val();
			if($('#statusCheckBox').is(':checked')){
				difficulty.status = 1;
			}else if(!$('#statusCheckBox').is(':checked')){
				difficulty.status = 2;
			}
			
			$.when(ajax.fetch("difficultylevels/check/"+difficulty.difficultyName)).done(function(response) {
				if(response.status == HttpStatus.SUCCESS){
					if(response.totalRecords > 0){
						alertify.warning("Difficulty Level Name already exists. Please enter a different Difficulty Level Name.");
						$("#fieldName").css("color","red");
						$("#submitButton").removeAttr("disabled");
						$("#submitButton").html("<i class='material-icons left'>save</i>Save");
					} else {
						$.when(ajax.create("difficultylevels",difficulty)).done(function(response) {
							if(response.status == HttpStatus.SUCCESS) {
								attachListenerToModalClose();
								alertify.success("Successfully Created.");
								initTopicsAndDifficultiesTbl();
							}
						});
					}
				}
			});
		}
		$("#submitButton").removeAttr("disabled");
		$("#submitButton").html("<i class='material-icons left'>save</i>Save");
		
	} else if(action == "EDIT"){
		if(saveAction == "TOPIC"){
			topic.topicDesc = $("#topicDifficultyNameTxtBox").val();
			if ($('#statusCheckBox').is(':checked')) {
				topic.status = 1;
			} else if(!$('#statusCheckBox').is(':checked')){
				topic.status = 2;
			}
			
			$.when(ajax.fetch("trainingtopics/check/"+topic.topicDesc)).done(function(response) {
				if(response.status == HttpStatus.SUCCESS){
					if(response.totalRecords > 0 && topic.topicDesc != origData){
						alertify.warning("Topic already exists. Please enter a different Topic.");
						$("#fieldName").css("color","red");
						$("#submitButton").removeAttr("disabled");
						$("#submitButton").html("<i class='material-icons left'>save</i>Save");
					} else {
						$.when(ajax.update("trainingtopics/",id,topic)).done(function(response) {
							if(response.status == HttpStatus.SUCCESS){
								attachListenerToModalClose();
								alertify.success("Successfully Updated."); 
								initTopicsAndDifficultiesTbl();
							}
						});
					}
				}
			});
	
		} else if(saveAction == "DIFFICULTY"){
			difficulty.difficultyName = $("#topicDifficultyNameTxtBox").val();
			if($('#statusCheckBox').is(':checked')){
				difficulty.status = 1;
			}else if(!$('#statusCheckBox').is(':checked')){
				difficulty.status = 2;
			}
			
			$.when(ajax.fetch("difficultylevels/check/"+difficulty.difficultyName)).done(function(response) {
				if(response.status == HttpStatus.SUCCESS){
					if(response.totalRecords > 0 && difficulty.difficultyName != origData){
						alertify.warning("Difficulty Level Name already exists. Please enter a different Difficulty Level Name.");
						$("#fieldName").css("color","red");
						$("#submitButton").removeAttr("disabled");
						$("#submitButton").html("<i class='material-icons left'>save</i>Save");
					} else {
						$.when(ajax.update("difficultylevels/",id,difficulty)).done(function(response) {
							if(response.status == HttpStatus.SUCCESS){
								attachListenerToModalClose();
								alertify.success("Successfully Updated.");
								initTopicsAndDifficultiesTbl();
							}
						});
					}
				}
			});
		}
		$("#submitButton").removeAttr("disabled");
		$("#submitButton").html("<i class='material-icons left'>save</i>Save");
	}
}