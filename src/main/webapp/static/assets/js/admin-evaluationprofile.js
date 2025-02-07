/**
 * 
 */

$(function(){
	defineGlobalList();
	initEvaluationDetails();
	allowNumbersOnly();
	cancelButtonListener();
	submitButtonListener();
	submitSectionButtonListener();
	submitQuestionButtonListener();
});

function defineGlobalList() {
	evalSections = [];
	evalQuestions = [];
	evaluationQuestions = []; 	
	sectionCount = 1;
	questionCount = 1;
}

function initEvaluationDetails() {
	$("#statusCheckBox").attr('checked', true);
	if(GetURLParameter('action') == "EDIT"){
		var evaluationId = GetURLParameter('id');
		$.when(ajax.fetchObj("evaluations/info/",evaluationId)).done(function(response) {
			if(response.status == HttpStatus.SUCCESS){
				var evaluationDetails = response.evaluation;
				populateEvaluationDetails(evaluationDetails);
				initEvaluationSectionList(evaluationId);
			} 
		}); 
	} else {
		$.when(ajax.fetch("evaluations/generate-evaluationcode")).done(function(response) {
			var evalDetails = response.evaluation;
			$("#evaluationSubmit").attr("data-id",evalDetails.evaluationId);
			$("#evaluationCodeTxtbox").val(evalDetails.evaluationCode);
		});
	}
}

function populateEvaluationDetails(evaluationDetails) {
	$("#evaluationCodeTxtbox").val(evaluationDetails.evaluationCode);
	$("#evaluationTitleTxtbox").val(evaluationDetails.title);
	$("#evaluationSubmit").attr("data-id",evaluationDetails.evaluationId);
	
	if(evaluationDetails.status == "1"){
		$("#statusCheckBox").prop("checked",true).trigger('change');
	} else if(evaluationDetails.status == "0"){
		$("#statusCheckBox").prop("checked",false).trigger('change');
	}
}

function initEvaluationSectionList(evaluationId) {
	$.when(ajax.fetchObj("evaluations/questions/",evaluationId)).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var evQuestions = response.evaluationquestions;
			_.each(evQuestions,function(evalSec) {
				var evalSection = {};
				if(sectionCount == evalSec.sectionOrder){
					evalSection.sectionOrder = evalSec.sectionOrder;
					evalSection.sectionName = evalSec.sectionName;
					evalSection.evaluationType = evalSec.evaluationType;
					evalSection.instruction = evalSec.instruction;
					
					if(evalSec.evaluationType == "Scale"){
						evalSection.scaleMin = parseInt(evalSec.scaleMin);
						evalSection.scaleMax = parseInt(evalSec.scaleMax);
					} else {
						evalSection.scaleMin = 0;
						evalSection.scaleMax = 0;
					}
					
					evalSections.push(evalSection);
					
					$.when(createSection(evalSection)).done(function(response) {
						setContent("evalInstructions_"+evalSec.sectionOrder,evalSec.instruction);
						
						if(evalSections.evaluationType == "Scale"){
							$("#minimumTxtBox_"+evalSec.sectionOrder).val(evalSec.scaleMin);
							$("#maximumTxtBox_"+evalSec.sectionOrder).val(evalSec.scaleMax);
						}
						initEvaluationQuestionList(evQuestions, evalSec.sectionOrder);
						sectionCount++;
					});	
					
				}
			});
		}
	}); 
}

function initEvaluationQuestionList(evQuestions, sectionOrder) {
	_.each(evQuestions,function(evalQue) {
		if(sectionOrder == evalQue.sectionOrder) {
			evalQue.questionOrder = questionCount;
			evalQuestions.push(evalQue);
			
			createQuestion(evalQue);
			questionCount++;
		}
	});
}

function validateFields() {
	var incField = 0;
	if($("#evaluationCodeTxtbox").val().length == 0){
		$("#evalCodeLabel").css("color","red");
		incField = 1;
	}else{$("#evalCodeLabel").css("color","");}
	
	if($("#evaluationTitleTxtbox").val().length == 0){
		$("#evalTitleLabel").css("color","red");
		incField = 1;
	}else{$("#evalTitleLabel").css("color","");}
	
	if(incField != 1){
		if($(".valSection").length == 0){
			alertify.dismissAll();
			alertify.warning("Please add at least 1 section.");
			$("#evaluationSubmit").removeAttr("disabled");
			$("#evaluationSubmit").html("<i class='material-icons left'>save</i>Save");
		} else {
			validateQuestionTable();
		}
	} else{
		alertify.dismissAll();
		alertify.warning("Please fill up the required fields.");
		$("#evaluationSubmit").removeAttr("disabled");
		$("#evaluationSubmit").html("<i class='material-icons left'>save</i>Save");
	}
}

function validateSectionFields() {
	var incField = 0;
	var scaleError = 0;
	if($("#sectionNameTxtBox").val().length == 0){
		$("#nameLabel").css("color","red");
		incField = 1;
	}else{$("#nameLabel").css("color","");}
	
	if($("#sectionTypeDropdown").val() == 0 || $("#sectionTypeDropdown").val() == null){
		$("#typeLabel").css("color","red");
		incField = 1;
	}else{$("#typeLabel").css("color","");}
	
	if($("#scaleFields").is(":visible")){
		if($("#minScaleTxtBox").val().length == 0){
			$("#minScaleLabel").css("color","red");
			incField = 1;
		}else{$("#minScaleLabel").css("color","");}
		
		if($("#maxScaleTxtBox").val().length == 0){
			$("#maxScaleLabel").css("color","red");
			incField = 1;
		}else{$("#maxScaleLabel").css("color","");}
		
		
	} else {
		$("#minScaleLabel").css("color","");
		$("#maxScaleLabel").css("color","");
	}
	
	if(incField != 1){
		var minScale = parseInt($("#minScaleTxtBox").val());
		var maxScale = parseInt($("#maxScaleTxtBox").val());
		if(minScale > maxScale){
			$("#minScaleLabel").css("color","red");
			$("#maxScaleLabel").css("color","red");
			$("#minScaleTxtBox").val("");
			$("#maxScaleTxtBox").val("");
			alertify.dismissAll();
			alertify.warning("Minimum value cannot be greater than Maximum value.");
			$("#sectionSubmit").removeAttr("disabled");
			$("#sectionSubmit").html("<i class='material-icons left'>save</i>Save");
		} else if(minScale == maxScale){
			$("#minScaleLabel").css("color","red");
			$("#maxScaleLabel").css("color","red");
			$("#minScaleTxtBox").val("");
			$("#maxScaleTxtBox").val("");
			alertify.dismissAll();
			alertify.warning("Minimum and Maximum value cannot be equal.");
			$("#sectionSubmit").removeAttr("disabled");
			$("#sectionSubmit").html("<i class='material-icons left'>save</i>Save");
		} else if(minScale < 0 || maxScale < 0){
			$("#minScaleLabel").css("color","red");
			$("#maxScaleLabel").css("color","red");
			$("#minScaleTxtBox").val("");
			$("#maxScaleTxtBox").val("");
			alertify.dismissAll();
			alertify.warning("Minimum and Maximum value must be a positive number.");
			$("#sectionSubmit").removeAttr("disabled");
			$("#sectionSubmit").html("<i class='material-icons left'>save</i>Save");
		}else {
			submitSectionDetails();
		}
		
	} else{
		alertify.dismissAll();
		alertify.warning("Please fill up the required fields.");
		$("#sectionSubmit").removeAttr("disabled");
		$("#sectionSubmit").html("<i class='material-icons left'>save</i>Save");
	}
}

function validateQuestionFields() {
	var incField = 0;
	if($("#questionTextArea").val().length == 0){
		$("#questionLabel").css("color","red");
		incField = 1;
	}else{$("#questionLabel").css("color","");}
	
	if(incField != 1){
		submitQuestionDetails();
	} else{
		alertify.dismissAll();
		alertify.warning("You cannot save blanked question.");
		$("#questionSubmit").removeAttr("disabled");
		$("#questionSubmit").html("<i class='material-icons left'>save</i>Save");
	}
}

function validateQuestionTable() {
	var incQuestionTable = 0;
	_.each(evalSections,function(evalSec) {
		var questionTableCount = 0;
		questionTableCount = parseInt($("#questionTblBody_"+evalSec.sectionOrder).children().length);
		if(questionTableCount == 0) {
			$("#evalSectionLabel_"+evalSec.sectionOrder).css("color","red");
			$("#hlQuestionLabel_"+evalSec.sectionOrder).css("color","red");
			incQuestionTable = 1;
		} else {
			$("#evalSectionLabel_"+evalSec.sectionOrder).css("color","");
			$("#hlQuestionLabel_"+evalSec.sectionOrder).css("color","");
		}
	});
	if(incQuestionTable != 1){
		submitEvaluationDetails();
	} else {
		alertify.dismissAll();
		alertify.warning("Please add at least 1 question on highlighted sections.");
		$("#evaluationSubmit").removeAttr("disabled");
		$("#evaluationSubmit").html("<i class='material-icons left'>save</i>Save");
	}
}

function submitSectionDetails(){
	evalSection = {};
	evalSection.sectionOrder = sectionCount;
	evalSection.sectionName = $("#sectionNameTxtBox").val();
	evalSection.evaluationType = $("#sectionTypeDropdown").val();
	evalSection.scaleMin = parseInt(($("#minScaleTxtBox").val()=="")? 0 : parseInt($("#minScaleTxtBox").val()));
	evalSection.scaleMax = parseInt(($("#maxScaleTxtBox").val()=="")? 0 : parseInt($("#maxScaleTxtBox").val()));
	
	evalSections.push(evalSection);
	
	$("#addSectionModal").modal('close');
	$("#sectionSubmit").removeAttr("disabled");
	$("#sectionSubmit").html("<i class='material-icons left'>save</i>Save");
	createSection(evalSection);
	
	clearSectionFields();
	sectionCount++;
}

function submitQuestionDetails(){
	var questionAction = $("#questionSubmit").attr("data-questionAction");
	var currentQuestionOrder = $("#questionSubmit").attr("data-questionOrder");
	evalQuestion = {};
	var sectionOrder = parseInt($("#questionSubmit").attr("data-sectionOrder"));
	if(questionAction == "ADD"){
		evalQuestion.questionOrder = questionCount;
	} else if(questionAction == "EDIT"){
		evalQuestion.questionOrder = parseInt(currentQuestionOrder);
	} 
	
	evalQuestion.sectionOrder = sectionOrder;
	evalQuestion.questionDesc = $("#questionTextArea").val();
	
	if(questionAction == "ADD"){
		evalQuestions.push(evalQuestion);
		questionCount++;
	} else if(questionAction == "EDIT"){
		deleteQuestionFromList(evalQuestion);
		evalQuestions.push(evalQuestion);
		$("#question_"+currentQuestionOrder).remove();
	} 
	
	$("#partModal").modal('close');
	$("#questionSubmit").removeAttr("disabled");
	$("#questionSubmit").html("<i class='material-icons left'>save</i>Save");
	$.when(createQuestion(evalQuestion)).done(function(response) {
		$("#questionTextArea").val("");
	});
}

function validateSectionType() {
	if($("#sectionTypeDropdown").val() == "Free Form"){
		$("#scaleFields").css("display","none");
	} else if($("#sectionTypeDropdown").val() == "Scale"){
		$("#scaleFields").css("display","");
	}
	$("#minScaleTxtBox").val("");
	$("#minScaleLabel").css("color","");
	$("#maxScaleTxtBox").val("");
	$("#maxScaleLabel").css("color","");
}

function getEvaluationDetails() {
	var evaluation = {};
	
	evaluation.evaluationCode = $("#evaluationCodeTxtbox").val();
	evaluation.title = $("#evaluationTitleTxtbox").val();
	
	if ($('#statusCheckBox').is(':checked')) {
		evaluation.status = 1;
	} else if(!$('#statusCheckBox').is(':checked')){
		evaluation.status = 0;
	}
	return evaluation;
}

function deleteSectionFromList(evalSection) {
	var result = false;
	evalSecs = evalSections;
	_.each(evalSecs,function(evalObj) {
		if(evalObj.sectionOrder == evalSection.sectionOrder) {
			evalSections = deleteSections(evalSection);
		}
	});
	return result;
	
}

function deleteSections(evalSection) {
	var resultArray = [];
	_.each(evalSections,function(evalObj) {
		if(evalObj.sectionOrder != evalSection.sectionOrder) {
			resultArray.push(evalObj);
		}
	});
	return resultArray;
}

function deleteQuestionFromList(evalQuestion) {
	var result = false;
	evalQues = evalQuestions;
	_.each(evalQues,function(evalObj) {
		if(evalObj.questionOrder == evalQuestion.questionOrder) {
			evalQuestions = deleteQuestions(evalQuestion);
		}
	});
	return result;
	
}

function deleteQuestions(evalQuestion) {
	var resultArray = [];
	_.each(evalQuestions,function(evalObj) {
		if(evalObj.questionOrder != evalQuestion.questionOrder) {
			resultArray.push(evalObj);
		}
	});
	return resultArray;
}

function getEvaluationQuestions(evalId) {
	
	_.each(evalSections,function(evalSec) {
		_.each(evalQuestions,function(evalQue) {
			if(evalSec.sectionOrder == evalQue.sectionOrder) {
				var eQuestion = {};
				eQuestion.evaluationId = evalId;
				eQuestion.sectionOrder = evalSec.sectionOrder;
				eQuestion.sectionName = $("#sectionName_"+evalSec.sectionOrder).val();
				eQuestion.evaluationType = $("#evalTypeLabel_"+evalSec.sectionOrder).html();
				eQuestion.instruction = getContent("evalInstructions_"+evalSec.sectionOrder);
				eQuestion.questionDesc = evalQue.questionDesc;
				
				if(evalSec.evaluationType == "Scale"){
					eQuestion.scaleMin = parseInt($("#minimumTxtBox_"+evalSec.sectionOrder).val());
					eQuestion.scaleMax = parseInt($("#maximumTxtBox_"+evalSec.sectionOrder).val());
				} else {
					eQuestion.scaleMin = 0;
					eQuestion.scaleMax = 0;
				}
				evaluationQuestions.push(eQuestion);
			}
		});
	});
	return evaluationQuestions;
}

function cancelButtonListener() {
	$("#evaluationCancel").unbind("click").click(function(){
		var courseId = (!GetURLParameter("courseId"))? 0 : parseInt(GetURLParameter("courseId"));
		var classId = (!GetURLParameter("classId"))? 0 : parseInt(GetURLParameter("classId"));
		if(courseId != 0){
			redirect("admin.courseevaluation?courseId="+courseId,0);
		} else if(classId != 0){
			redirect("admin.classevaluation?classId="+classId,0);
		} else {
			redirect("admin.evaluation",0);
		}
	});
}

function submitButtonListener() {
	$("#evaluationSubmit").unbind("click").click(function(){
		$("#evaluationSubmit").attr("disabled","disabled");
		$("#evaluationSubmit").html("Saving...");
		validateFields();
	});
} 

function submitSectionButtonListener() {
	$('#sectionTypeDropdown').on("select2:select", function(e) { 
		validateSectionType();
	});
	
	$("#sectionSubmit").unbind("click").click(function(){
		$("#sectionSubmit").attr("disabled","disabled");
		$("#sectionSubmit").html("Saving...");
		validateSectionFields();
	});
	$("#sectionCancel").on("click",function() {
		clearSectionFields();
	});
}

function submitQuestionButtonListener() {
	$("#questionSubmit").unbind("click").click(function(){
		$("#questionSubmit").attr("disabled","disabled");
		$("#questionSubmit").html("Saving...");
		validateQuestionFields();
	});
	$("#questionCancel").on("click",function() {
		clearQuestionFields();
	});
}

function clearSectionFields() {
	$("#sectionNameTxtBox").val("");
	$("#sectionTypeDropdown").val("Choose Type").trigger("change");
	$("#minScaleTxtBox").val("");
	$("#maxScaleTxtBox").val("");
	$("#nameLabel").css("color","");
	$("#typeLabel").css("color","");
	$("#minScaleLabel").css("color","");
	$("#maxScaleLabel").css("color","");
	$("#scaleFields").css("display","none");
}

function clearQuestionFields() {
	$("#questionTextArea").val("");
}

function createSection(evalSection){
	
	var appendScale = "";
	if(evalSection.evaluationType == "Scale"){
		appendScale = $("<div/>").attr("class","col s12 m8")
		.append($("<div/>").attr("class","row")
				.append($("<div/>")
				.attr({"class":"col s3","style":"padding-top:19px;text-align:center;"})
					.append($("<b/>").html("Minimum*")))
				.append($("<div/>").attr("class","col s3")
					.append($("<input/>").val(evalSection.scaleMin)
					.attr({"type":"number",
						"id":"minimumTxtBox_"+evalSection.sectionOrder,
						"data-sectionOrder":evalSection.sectionOrder,
						"class":"numbersOnly"})
					.bind("blur",function(e) {
						var numOnly = e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57);
						var currentsectionOrder = $(this).attr("data-sectionOrder");
						var minScale = parseInt($("#minimumTxtBox_"+currentsectionOrder).val());
						var maxScale = parseInt($("#maximumTxtBox_"+currentsectionOrder).val());
						if(minScale > maxScale || minScale == maxScale || minScale < 0){
							$(this).val(maxScale-1);
						}
					})))
				.append($("<div/>")
				.attr({"class":"col s3","style":"padding-top:19px;text-align:center;"})
					.append($("<b/>").html("Maximum*")))
				.append($("<div/>").attr("class","col s3")
					.append($("<input/>").val(evalSection.scaleMax)
					.attr({"type":"number",
						"id":"maximumTxtBox_"+evalSection.sectionOrder,
						"data-sectionOrder":evalSection.sectionOrder,
						"class":"numbersOnly"})
					.bind("blur",function() {
						var currentsectionOrder = $(this).attr("data-sectionOrder");
						var minScale = parseInt($("#minimumTxtBox_"+currentsectionOrder).val());
						var maxScale = parseInt($("#maximumTxtBox_"+currentsectionOrder).val());
						if(minScale > maxScale || minScale == maxScale || maxScale < 0){
							$(this).val(minScale+1);
						}
						
					}))));
	}
	
	$("#evalProfileBody").append($("<div/>").attr({"id":"section_"+evalSection.sectionOrder,"class":"row valSection"})
		.append($("<div/>").attr("class","divcard")
			.append($("<div/>").attr("class","row")
				.append($("<ul/>").attr("class","collapsible")
					.append($("<li/>")
						.append($("<div/>")
						.attr({"class":"collapsible-header","style":"padding:10px;","tabindex":"0"})
							.append($("<span/>").attr({"id":"evalSectionLabel_"+evalSection.sectionOrder,"style":"padding-top:5px"})
							.html(evalSection.sectionName))
							.append($("<div/>").attr("class","collapsibleIcon")
								.append($("<i/>")
								.attr("class","material-icons rotate arrowRotate")
								.html("arrow_drop_down"))
								.append($("<a/>").attr({"data-sectionOrder":evalSection.sectionOrder})
								.bind("click",function() {
									var currentsectionOrder = $(this).attr("data-sectionOrder");
									deleteSectionFromList(evalSection);
									$("#section_"+currentsectionOrder).remove();
								})
									.append($("<i/>")
									.attr("class","material-icons").html("delete")
									))))
						.append($("<div/>").attr({"class":"collapsible-body bodyContent"})
							.append($("<div/>").attr("class","row")
								.append($("<div/>").attr("style","padding:0;")
									.append($("<input/>")
									.attr({"id":"sectionName_"+evalSection.sectionOrder,"type":"text","placeholder":"Section Name",
										"value":evalSection.sectionName})
									.bind("keyup",function() {
										var currentsectionOrder = evalSection.sectionOrder;
										$("#evalSectionLabel_"+currentsectionOrder).html($(this).val());
									}))))
							.append($("<div/>").attr("class","row")
								.append($("<div/>").attr({"id":"evalInstructions_"+evalSection.sectionOrder,"class":"txtarea"})))
							.append($("<div/>").attr("class","row")
								.append($("<div/>")
								.attr({"class":"col s12 m4","style":"padding:19px 0 0 0;"})
									.append($("<span/>").html("Type: ")
										.append($("<b/>").attr("id","evalTypeLabel_"+evalSection.sectionOrder)
										.html(evalSection.evaluationType))))
								.append(appendScale))
							.append($("<div/>").attr({"class":"row","style":"margin:10px 0;"})
								.append($("<div/>")
									.append($("<div/>")
									.attr({"class":"col s6 m6","style":"padding:8px 0 0 0;"})
										.append($("<b/>").attr("id","hlQuestionLabel_"+evalSection.sectionOrder)
										.html("Questions:")))
									.append($("<div/>")
									.attr({"class":"col s6 m6","style":"padding-right:0;"})
										.append($("<a/>").attr("id","questionModalBtn_"+evalSection.sectionOrder)
										.attr({"data-target":"partModal","type":"submit","data-sectionOrder":evalSection.sectionOrder})
										.addClass("modal-trigger bttn btn waves-effect waves-light right")
										.html("<i class='material-icons left'>add_circle</i>Add Question/Criteria")
										.bind("click",function() {
											$("#questionSubmit").attr("data-sectionOrder",$(this).attr("data-sectionOrder"));
											$("#questionSubmit").removeAttr("data-questionOrder");
											$("#questionSubmit").attr("data-questionAction","ADD");
										})))))
							.append($("<div/>")
								.append($("<table/>").attr("class","highlight striped")
									.append($("<thead/>")
										.append($("<tr/>")
											.append($("<th/>")
											.attr("class","min").html("Actions"))
											.append($("<th/>")
											.html("Question"))))
									.append($("<tbody/>").attr("id","questionTblBody_"+evalSection.sectionOrder))))))))));
	$(".collapsible").collapsible();
	initTinyMceEditor(".txtarea",300);
}

function createQuestion(evalQuestion) {
	$("#questionTblBody_"+evalQuestion.sectionOrder).append($("<tr/>")
		.attr("id","question_"+evalQuestion.questionOrder)
		.append($("<td/>").attr({"class":"min"})
			.append($("<a/>").attr({"class":"modal-trigger waves-effect waves-light",
				"data-target":"partModal","data-sectionOrder":evalQuestion.sectionOrder,
				"data-questionOrder":evalQuestion.questionOrder})
			.bind("click",function() {
				$("#questionTextArea").val(evalQuestion.questionDesc);
				$("#questionSubmit").attr("data-sectionOrder",$(this).attr("data-sectionOrder"));
				$("#questionSubmit").attr("data-questionOrder",$(this).attr("data-questionOrder"));
				$("#questionSubmit").attr("data-questionAction","EDIT");
			})
			.html("<i class='material-icons'>edit</i>"))
			.append($("<a/>").attr("class","waves-effect waves-light")
			.bind("click",function() {
				deleteQuestionFromList(evalQuestion);
				$("#question_"+evalQuestion.questionOrder).remove();
			})
			.html("<i class='material-icons'>delete</i>")))
		.append($("<td/>").html(evalQuestion.questionDesc)));
}

function setCourseEvaluation(evalId,courseId) {
	var courseEvaluations = [];
	var courseEvaluation = {};
	courseEvaluation.courseId = courseId;
	courseEvaluation.evaluationId = evalId;
	courseEvaluations.push(courseEvaluation);
	
	return courseEvaluations;
}

function setClassEvaluation(evalId,classId) {
	var classEvaluations = [];
	var classEvaluation = {};
	classEvaluation.classId = classId;
	classEvaluation.evaluationId = evalId;
	classEvaluations.push(classEvaluation);
	
	return classEvaluations;
}

function submitEvaluationDetails() {
	var courseId = (!GetURLParameter("courseId"))? 0 : parseInt(GetURLParameter("courseId"));
	var classId = (!GetURLParameter("classId"))? 0 : parseInt(GetURLParameter("classId"));
	var evalId = parseInt($("#evaluationSubmit").attr("data-id"));
	var evaluation = getEvaluationDetails();
	evaluationQuestions = getEvaluationQuestions(evalId);
	if(GetURLParameter('action') == "EDIT"){
		$.when(ajax.update("evaluations/",evalId,evaluation)).done(function(response) {
			if(response.status == HttpStatus.SUCCESS){
				if(evalSections.length > 0) {
					$.when(ajax.update("evaluations/questions/",evalId,evaluationQuestions)).done(function(response) {
						if(response.status == HttpStatus.SUCCESS){
							alertify.dismissAll();
							alertify.success("Successfully Updated.");
							if(courseId != 0){
								setTimeout(function(){redirect("admin.courseevaluation?courseId="+courseId,0);}, 3000);
							} else if(classId != 0){
								setTimeout(function(){redirect("admin.classevaluation?classId="+classId,0);}, 3000);
							} else {
								setTimeout(function(){redirect("admin.evaluation",0);}, 3000);
							}
						}
					});
				} else {
					alertify.dismissAll();
					alertify.success("Successfully Updated.");
					if(courseId != 0){
						setTimeout(function(){redirect("admin.courseevaluation?courseId="+courseId,0);}, 3000);
					} else if(classId != 0){
						setTimeout(function(){redirect("admin.classevaluation?classId="+classId,0);}, 3000);
					} else {
						setTimeout(function(){redirect("admin.evaluation",0);}, 3000);
					}
					
				}
			}
		});
	} else {
		$.when(ajax.fetch("evaluations/"+evaluation.evaluationCode)).done(function(response) {
			if(response.status == HttpStatus.SUCCESS){
				if(response.totalRecords > 0){
					$("#evaluationSubmit").removeAttr("disabled");
					$("#evalCodeLabel").css("color","red");
					$("#evaluationSubmit").html("<i class='material-icons left'>save</i>Save");
					alertify.dismissAll();
					alertify.warning("Evaluation Code already exists. Please enter a different Evaluation Code.");
				} else {
					$.when(ajax.create("evaluations",evaluation)).done(function(response) {
						if(response.status == HttpStatus.SUCCESS){
							if(evalSections.length > 0) {
								$.when(ajax.update("evaluations/questions/",evalId,evaluationQuestions)).done(function(response) {
									if(response.status == HttpStatus.SUCCESS){
										if(courseId != 0){
											var courseEvaluations = setCourseEvaluation(evalId,courseId);
											$.when(ajax.update("courseinfo/courseevaluations/",courseId,courseEvaluations)).done(function(response) {
												if(response.status == HttpStatus.SUCCESS){
													alertify.dismissAll();
													alertify.success("Successfully Created.");
													setTimeout(function(){redirect("admin.courseevaluation?courseId="+courseId);}, 3000);
												}
											});
										} else if(classId != 0){
											var classEvaluations = setClassEvaluation(evalId,classId);
											$.when(ajax.customUpdate("classinfo/"+classId+"/classevaluations",classEvaluations)).done(function(response) {
												if(response.status == HttpStatus.SUCCESS){
													alertify.dismissAll();
													alertify.success("Successfully Created.");
													setTimeout(function(){redirect("admin.classevaluation?classId="+classId,0);}, 3000);
												}
											});
										} else {
											alertify.dismissAll();
											alertify.success("Successfully Created.");
											setTimeout(function(){redirect("admin.evaluation");}, 3000);
										}
									}
								});
							} else {
								if(courseId != 0){
									var courseEvaluations = setCourseEvaluation(evalId,courseId);
									$.when(ajax.update("courseinfo/courseevaluations/",courseId,courseEvaluations)).done(function(response) {
										if(response.status == HttpStatus.SUCCESS){
											alertify.dismissAll();
											alertify.success("Successfully Created.");
											setTimeout(function(){redirect("admin.courseevaluation?courseId="+courseId);}, 3000);
										}
									});
								} else if(classId != 0){
									var classEvaluations = setClassEvaluation(evalId,classId);
									$.when(ajax.customUpdate("classinfo/"+classId+"/classevaluations",classEvaluations)).done(function(response) {
										if(response.status == HttpStatus.SUCCESS){
											alertify.dismissAll();
											alertify.success("Successfully Created." );
											setTimeout(function(){redirect("admin.classevaluation?classId="+classId,0);}, 3000);
										}
									});
								} else {
									alertify.dismissAll();
									alertify.success("Successfully Created.");
									setTimeout(function(){redirect("admin.evaluation");}, 3000);
								}
							}
						}
					});
				}
			}
		});
	}
}