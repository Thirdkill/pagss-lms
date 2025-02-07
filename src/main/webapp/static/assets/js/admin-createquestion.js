$(function() {
	defineGlobalCounters();
	defineGlobalList();
	initSortableItems();
	initAnalogyAnswerList();
	initQuestionPageLabel();
	initTextEditor();
	initQuestionDetails();
	attachListenerToQuestionTypeDropdown();
	attachListenerToAddMultipleChoice();
	attachListenerToSaveQuestionBtn();
	attachListenerToAddEnumeration();
	attachListenerToDeleteEnumAnswer();
	attachListenerToAddPairBtn();
	attachListenerToRemoveAnalogyLink();
	attachListenerToFiTBAddChoicesBtn();
	attachListenerToSaveFITBDropDownBtn();
	attachListenerToDeleteFITBChoiceBtn();
	attachListenerToFITBCancelBtn();
	attachListenerToFITBSaveDirectAnswerBtn();
	attachListenerToAddOrderingBtn();
});

function defineGlobalCounters() {
	count = 4;
	divMultipleChoiceCount = 4;
	enumerationAnswerCtr=1;
	pairCtr = 3;
	fillInTheBlanksChoicesCtr=1;
	orderingCtr=1;
}

function defineGlobalList() {
	analogyAnswerList = [];
	fillInTheBlanksAnswerList=[];
	orderingAnswerList=[];
}

function initSortableItems() {
	$("#orderingAnswerCon").sortable({
		items:".sortable",
	});
}

function initAnalogyAnswerList() {
	var analogyanswer = {};
	analogyanswer.analogyId = 1;
	analogyanswer.givenA = "";
	analogyanswer.givenB = "";
	analogyAnswerList.push(analogyanswer);
	var analogyanswer = {};
	analogyanswer.analogyId = 2;
	analogyanswer.givenA = "";
	analogyanswer.givenB = "";
	analogyAnswerList.push(analogyanswer);
}

function initQuestionPageLabel() {
	$("#actionLabel").html("");
	$("#actionLabel").html(($("#action").val() != "create")? "Edit Question Details" : "Add New Question");
	$("#actionTitle").html(($("#action").val() != "create")? "Edit Question Details" : "Add New Question");
} 

function initTextEditor() {
	if($("#questionTypeDropDown").val()==QuestionType.FILL_IN_THE_BLANK) {
		initCustomTinyMceEditor();
	} else {
		initTinyMceEditor("#texteditor",500);
	}
}

function initQuestionTypeDropDown(value) {
	$.when(ajax.fetch("questiontypes")).done(function(response) {
		populateQuestionTypeDropDown(response.questionTypes,value);
	});
}

function populateQuestionTypeDropDown(questionTypes,value) {
	if($("#action").val() != "create") {
		$("#questionTypeDropDown").prop("disabled",true);
	}
	_.each(questionTypes,function(questionType) {
		$("#questionTypeDropDown").append($("<option/>").attr({"value" : questionType.questionTypeId})
			.prop("selected",(value==questionType.questionTypeId)?true:false).html(questionType.questionTypeDesc));
	});
	$("#questionTypeDropDown").trigger("change");
}

function initTopicDropDown(value) {
	$.when(ajax.fetch("trainingtopics")).done(function(response) {
		populateTopicDropDown(response.trainingTopics,value);
	});
}

function populateTopicDropDown(trainingTopics,value) {
	_.each(trainingTopics,function(trainingTopic) {
		$("#topicDropDown").append($("<option/>").attr({"value" : trainingTopic.topicId})
				.prop("selected",(value==trainingTopic.topicId)?true:false).html(trainingTopic.topicDesc));
	});
}

function initDifficultyLevelDropDown(value) {
	$.when(ajax.fetch("difficultylevels")).done(function(response) {
		populateDIfficultyDropDown(response.difficultyLevels,value);
	});
}

function populateDIfficultyDropDown(difficultyLevels,value) {
	_.each(difficultyLevels,function(difficultyLevel) {
		$("#difficultyDropDown").append($("<option/>").attr({"value" : difficultyLevel.difficultyId})
				.prop("selected",(value==difficultyLevel.difficultyId)?true:false).html(difficultyLevel.difficultyName));
	});
}

function initQuestionDetails() {
	var questionId = $("#questionId").val();
	if($("#action").val()=="update") {
		$.when(ajax.fetch("question/" + questionId)).done(function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					populateQuestionDetails(response.question);
					break;
			}
		});
	} else {
		initQuestionTypeDropDown(null);
		initTopicDropDown(null);
		initDifficultyLevelDropDown(null)
	}
}

function populateQuestionDetails(question) {
	initQuestionTypeDropDown(question.questionTypeId);
	initTopicDropDown(question.topicId);
	initDifficultyLevelDropDown(question.difficultyId);
	$('select').select2({placeholder: "Choose An Option"});
	$("#questionLabel").val(question.label);
	$("#mediaDisplayTxt").attr({"placeholder":question.mediaFileName});
	$("#contentHdn").val(question.content);
	setContent("texteditor",question.content);
	$("#identificationAnswer").val(question.answer);
	if(question.status!=Status.INACTIVE) {
		$("#questionStatus").prop("checked","checked");
	} else {
		$("#questionStatus").prop("checked","");
	}
	if(question.matchCase==CheckedValue.CHECKED && question.questionTypeId==QuestionType.IDENTIFICATION) {
		$("#identificationMatchCaseChkBox").prop("checked","checked");
	} else {
		$("#identificationMatchCaseChkBox").prop("checked","");
	}
	if(question.matchCase==CheckedValue.CHECKED && question.questionTypeId==QuestionType.ENUMERATION) {
		$("#enumMatchCaseChkBox").prop("checked","checked");
	} else {
		$("#enumMatchCaseChkBox").prop("checked","");
	}
	if(question.ignoreOrder==CheckedValue.CHECKED) {
		$("#enumIgnoreOrder").prop("checked","checked");
	} else {
		$("#enumIgnoreOrder").prop("checked","");
	}
	populateChoices(question.choices);
	populateTrueOrFalse(question.answer);
	populateEnumerationAnswers(question.enumerationAnswers);
	populateAnalogyAnswers(question.analogyAnswers);
	populateOrderingAnswers(question.orderingAnswers);
	analogyAnswerList = question.analogyAnswers;
	fillInTheBlanksAnswerList=question.fillInTheBlanksAnswers;
}

function populateOrderingAnswers(orderingAnswers) {
	$("#orderingRow").html("");
	_.each(orderingAnswers,function(orderingAnswer) {
		$("#orderingRow").append($("<div/>").attr("id","orderingAnswerRow_"+orderingCtr)
			.addClass("col s10 m10 sortable")
			.append($("<a/>").attr({"href":"#!","data-id":orderingCtr})
				.bind("click",function() {
					removeOrderingAnswer($(this).data("id"));
				}).append($("<i/>").addClass("material-icons").html("delete")))
			.append($("<label/>").html("MOVE ME"))
			.append($("<input/>").addClass("orderingAnswerTxtBox").attr({"type":"text","data-id":orderingCtr,
				"value":orderingAnswer.answer})));
		orderingCtr++;
	});
}

function populateAnalogyAnswers(analogyAnswers) {
	$("#pairDiv").html("");
	pairCtr = analogyAnswers.length;
	var ctr = 1;
	_.each(analogyAnswers,function(analogyAnswer) {
		$("#pairDiv").append($("<div/>").attr("id","pairSection_"+analogyAnswer.analogyId)
			.append($("<div/>").addClass("col s12 m6")
			.append($("<input/>").attr({
				"type":"text",
				"placeholder":"Pair "+ctr,
				"id":"pairA_"+analogyAnswer.analogyId,
				"value":analogyAnswer.givenA
				}).addClass("pairA")))
				.append($("<div/>").addClass("col s11 m5")
			.append($("<input/>").attr({
				"type":"text",
				"placeholder":"Pair "+ctr,
				"id":"pairB_"+analogyAnswer.analogyId,
				"value":analogyAnswer.givenB
			}).addClass("pairB")))
			.append($("<div/>").addClass("col s1 m1 orderingicons")
				.append($("<a/>").addClass("removeAnalogy").attr({"href":"#!","data-id":analogyAnswer.analogyId})
					.append($("<i/>").addClass("material-icons").html("delete")))));
		ctr++;
	}) 
}

function populateEnumerationAnswers(enumerationAnswers) {
	$("#enumerationAnswersCon").html("");
	_.each(enumerationAnswers,function(enumerationAnswer) {
		$("#enumerationAnswersCon").append($("<div>").attr({"id":"enumChoice_"+enumerationAnswer.enumerationId}).addClass("col s10 m11")
				.append($("<input/>").addClass("enumChoice")
					.attr({"type":"text","placeholder":"Options","maxlength":"200","value":enumerationAnswer.answer})))
			.append($("<div>").attr({"id":"enumChoiceDeleteCon_"+enumerationAnswer.enumerationId})
				.css("padding-top","15px").addClass("col s2 m1")
				.append($("<a/>").addClass("waves-effect waves-light deleteEnumChoiceLink")
					.attr({"data-id":enumerationAnswer.enumerationId})
					.append($("<i/>").addClass("material-icons").html("delete"))));
	});
}

function populateTrueOrFalse(answer) {
	var trueChkBox = getCheckBoxValue("#trueChckBox");
	var falseChkBox = getCheckBoxValue("#falseChckBox");
	if(answer == "true") {
		$("#trueChckBox").prop("checked","checked");
	} else {
		$("#falseChckBox").prop("checked","checked");
	}
}

function populateChoices(choices) {
	$("#multipleChoiceDiv").html("");
	_.each(choices,function(choice) {
		$("#multipleChoiceDiv")
			.append($("<div/>").addClass("choiceRow").attr("id","mchoice_" + divMultipleChoiceCount)
				.append($("<div/>").addClass("col s7 m9")
					.append($("<input/>").addClass("choiceLabel").attr({
						"type":"text","placeholder":"option","value":choice.choiceDesc})))
				.append($("<div/>").addClass("col s3 m2")
					.append($("<p/>").addClass("pchck")
						.append($("<label/>").css("margin","5px 0 0 10px")
							.append($("<input/>").addClass("isCorrectCheckBox").attr("type","checkbox")
								.prop("checked",(choice.choiceType==0)?"":"checked"))
							.append($("<span/>").html("Correct")))))
				.append($("<div/>").css("padding-top","15px").addClass("col s2 m1")
					.append($("<a/>").addClass("waves-effect waves-light").attr("data-id",divMultipleChoiceCount)
						.bind("click",function() {
							$("#mchoice_"+$(this).data("id")).html("");
							count--;
						}).append($("<i/>").addClass("material-icons").html("delete")))));
		divMultipleChoiceCount++;
	});
	count = choices.length;
}

function attachListenerToQuestionTypeDropdown() {
	$("#questionTypeDropDown").on("change",function() {
		switch(parseInt($(this).val())) {
			case QuestionType.MULTIPLE_CHOICE:
				tinymce.remove();
				initTinyMceEditor("#texteditor",500);
				displayContainer("#multipleChoiceCon");
				if($("#action").val()=="update"){setContent("texteditor",$("#contentHdn").val());}
				if($("#action").val()=="create"){createMultipleChoiceCon(4);}
				break;
			case QuestionType.TRUE_OR_FALSE:
				tinymce.remove();
				initTinyMceEditor("#texteditor",500);
				displayContainer("#trueOrFalseCon");
				if($("#action").val()=="update"){setContent("texteditor",$("#contentHdn").val());}
				break;
			case QuestionType.IDENTIFICATION:
				tinymce.remove();
				initTinyMceEditor("#texteditor",500);
				displayContainer("#identificationCon");
				if($("#action").val()=="update"){setContent("texteditor",$("#contentHdn").val());}
				break;
			case QuestionType.ENUMERATION:
				tinymce.remove();
				initTinyMceEditor("#texteditor",500);
				displayContainer("#enumerationCon");
				if($("#action").val()=="update"){setContent("texteditor",$("#contentHdn").val());}
				break;
			case QuestionType.ORDERING:
				tinymce.remove();
				initTinyMceEditor("#texteditor",500);
				displayContainer("#OrderingCon");
				if($("#action").val()=="update"){setContent("texteditor",$("#contentHdn").val());}
				break;
			case QuestionType.MATCHING:
				tinymce.remove();
				initTinyMceEditor("#texteditor",500);
				displayContainer("#matchingCon");
				setContent("texteditor",$("#contentHdn").val());
				break;
			case QuestionType.ASSESSMENT:
				tinymce.remove();
				initTinyMceEditor("#texteditor",500);
				displayContainer("#AssessmentCon");
				if($("#action").val()=="update"){setContent("texteditor",$("#contentHdn").val());}
				break;
			case QuestionType.ESSAY:
				tinymce.remove();
				initTinyMceEditor("#texteditor",500);
				displayContainer("#essayCon");
				if($("#action").val()=="update"){setContent("texteditor",$("#contentHdn").val());}
				break;
			case QuestionType.FILL_IN_THE_BLANK:
				tinymce.remove();
				initCustomTinyMceEditor();
				if($("#action").val()=="update"){setContent("texteditor",$("#contentHdn").val());}
				break;
			default:
				initTinyMceEditor("#texteditor",500);
				displayContainer("#essayCon");
				if($("#action").val()=="update"){setContent("texteditor",$("#contentHdn").val());}
				break;
		}
	});
}

function initCustomTinyMceEditor() {
	initCustomEditor("#texteditor",{custom: {title: "Others",items:"insertDropdown insertTextBox"}},
		function(editor) {
			  editor.ui.registry.addMenuItem("insertDropdown", {
			      text: "Insert DropDown",
			      icon: "plus",
			      onAction: function () {
			    	$("#addFillInTheBlanksDropDownModal").modal("open");
			      }
			  });
			  editor.ui.registry.addMenuItem("insertTextBox", {
			      text: "Insert TexBox",
			      icon: "plus",
			      onAction: function () {
			    	$("#addFillInTheBlanksTextBoxModal").modal("open");
			      }
			  });
		  });
}

function displayContainer(dom) {
	$(".answerCon").css("display","none");
	$(dom).removeAttr("style");
}

function attachListenerToAddMultipleChoice() {
	$("body").on("click","#addMultipleChoiceBtn",function() {
		if(count <= 9) {
			createMultipleChoiceCon(1);
			count++;	
		} else {
			alertify.warning("You've already reached the maximum number of choices for this question.");
		}
	});
}

function createMultipleChoiceCon(noOfRows) {
	var ctr = 1;
	while(ctr <= noOfRows) {
		$("#multipleChoiceDiv")
			.append($("<div/>").addClass("choiceRow").attr("id","mchoice_" + divMultipleChoiceCount)
				.append($("<div/>").addClass("col s7 m9")
					.append($("<input/>").addClass("choiceLabel").attr({"type":"text","placeholder":"option"})))
				.append($("<div/>").addClass("col s3 m2")
					.append($("<p/>").addClass("pchck")
						.append($("<label/>").css("margin","5px 0 0 10px")
							.append($("<input/>").addClass("isCorrectCheckBox").attr("type","checkbox"))
							.append($("<span/>").html("Correct")))))
				.append($("<div/>").css("padding-top","15px").addClass("col s2 m1")
					.append($("<a/>").addClass("waves-effect waves-light").attr("data-id",divMultipleChoiceCount)
						.bind("click",function() {
							$("#mchoice_"+$(this).data("id")).html("");
							count--;
						}).append($("<i/>").addClass("material-icons").html("delete")))));
		ctr++;
		divMultipleChoiceCount++;
	}
}

function attachListenerToSaveQuestionBtn() {
	$("#saveQuestionBtn").on("click",function() {
		if(validateFormFields()) {
			if($("#action").val() == "create") {
				createQuestion();
			} else {
				updateQuestion();
			}
		}
	});
}

function createQuestion() {
	$.when(ajax.create("question",getQuestionData())).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				if(validateFileUpload("#uploadMediaForm")) {
					$.when(uploadMediaFile(response.questionId)).then(function(status) {
						redirect("admin.questions",3000);
					});
				} else {
					alertify.success("Successfully Created!")
					setTimeout(function(){redirect("admin.questions",1000);},1000);
				}
				break;
		}
	});
}

function updateQuestion() {
	var questionId = $("#questionId").val();
	$.when(ajax.update("question/",questionId,getQuestionData())).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully updated question details!");
				if(validateFileUpload("#uploadMediaForm")) {
					uploadMediaFile(response.questionId);
				}
				break;
		}
	});
}

function validateFormFields() {
	var result = true;
	if($("#questionTypeDropDown").val() == QuestionType.MULTIPLE_CHOICE) {
		result = validateMultipleChoices();
	}
	if($("#questionTypeDropDown").val() == QuestionType.TRUE_OR_FALSE) {
		result = validateTrueOrFalse();
	}
	if($("#questionTypeDropDown").val() == QuestionType.MATCHING) {
		result = validateMatchingFields();
	}
	if($("#questionTypeDropDown").val() == QuestionType.ENUMERATION) {
		result = validateEnumerationFields();
	}
	if($("#questionTypeDropDown").val() == QuestionType.ORDERING &&
		!validateOrderingAnswerEmptyFields()) {
		alertify.warning("All answers must have a value.");
		result = false;
	}
	if($("#questionTypeDropDown").val() == QuestionType.ORDERING &&
			!validateOrderingAnswerLength()) {
		alertify.warning("There should be atleast 3 answers.");
		result = false;
	}
	if($("#questionTypeDropDown").val() == null) {
		alertify.warning("Please choose a question type.");
		result = false;
	} else if($("#topicDropDown").val() == null) {
		alertify.warning("Please choose a topic.");
		result = false;
	} else if($("#difficultyDropDown").val() == null) {
		alertify.warning("Please choose a difficulty.");
		result = false;
	} else if($("#questionLabel").val() == "") {
		alertify.warning("Please fill the Question Label Field.");
		result = false;
	} else if(isEditorEmpty()) {
		alertify.warning("Please fill up the question content.");
		result = false;
	}
	return result;
}

function validateOrderingAnswerEmptyFields() {
	var result=true;
	$(".orderingAnswerTxtBox").each(function() {
		if($(this).val()=="") {
			result=false;
		}
	});
	return result;
}

function validateOrderingAnswerLength() {
	var result=true;
	var ctr=0;
	$(".orderingAnswerTxtBox").each(function() {
		ctr++;
	});
	if(ctr<=2) {
		result=false;
	}
	return result;
}

function validateEnumerationFields() {
	var result = true;
	var enumerationAnswers = getEnumerationChoices();
	if(enumerationAnswers.length<=1) {
		alertify.warning("There should be at least two answers");
		result = false;
	}
	return result;
}

function validateMatchingFields() {
	var result=true;
	var emptyField=false;
	_.each(analogyAnswerList,function(analogyAnswer) {
		if($("#pairA_"+analogyAnswer.analogyId).val() == "" || 
			$("#pairB_"+analogyAnswer.analogyId).val() == "") {
			emptyField=true;
		}
	});
	if(analogyAnswerList.length<=1) {
		alertify.warning("There should be atleast 2 pairs.");
		result=false;
	}
	if(emptyField==true) {
		alertify.warning("Please fill all fields in the matching choices");
		result=false;
	}
	return result;
}

function validateTrueOrFalse() {
	var trueChkBox = getCheckBoxValue("#trueChckBox");
	var falseChkBox = getCheckBoxValue("#falseChckBox");
	if(trueChkBox != 1 && falseChkBox != 1) {
		alertify.warning("Please choose 1 answer.");
		return false;
	} else if(trueChkBox == 1 && falseChkBox == 1) {
		alertify.warning("There should be 1 answer only.");
		return false;
	} else {
		return true;
	}
}

function isEditorEmpty() {
	if(getContent("texteditor") != "") {
		return false;
	} else {
		return true
	}
}

function validateMultipleChoices() {
	var choices = getMultipleChoicesData();
	var correctAnswer = 0;
	var result = true;
	_.each(choices,function(choice) {
		if(choice.choiceType == 1) {
			correctAnswer++;
		}
	});
	if(choices.length < 2 ) {
		alertify.warning("There should be atleast 2 choices");
		result = false;
	}
	if(correctAnswer == 0) {
		alertify.warning("There should be atleast 1 correct answer.");
		result = false;
	}
	var emptyFields=0;
	_.each($(".choiceRow"),function(divCon) {
		if($("#" + divCon.id).find("input[type=text]").val()=="") {
			emptyFields++;
		}
	});
	if(emptyFields>0) {
		alertify.warning("All choices must have a value.");
		result = false;
	}
	return result;
}

function clearFields() {
	$("#questionTypeDropDown").val(0).trigger("change");
	$("#topicDropDown").val(0).trigger("change");
	$("#difficultyDropDown").val(0).trigger("change");
	$("#questionStatus").prop("checked","");
	$("#mediaDisplayTxt").val("");
	$("#fileUpload").val("");
	$("#questionLabel").val("");
	$("#identificationAnswer").val("");
	$("#identificationMatchCaseChkBox").prop("checked","");
	clearContent("texteditor");
}

function uploadMediaFile(questionId) {
	var result = $.Deferred();
	blockUI("Uploading Media File..");
	handleRefreshEvent();
	$.when(ajax.upload("question/"+questionId+"/media","uploadMediaForm")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Media successfully saved");
				$.unblockUI();
				result.resolve("Yehey");
				$(window).unbind('beforeunload');
				break;
			case HttpStatus.FILE_FORMAT_IS_INVALID:
				alertify.warning(Message.FILE_FORMAT_IS_INVALID);
				$.unblockUI
				$(window).unbind('beforeunload');
				break;
			case HttpStatus.FILE_SIZE_IS_INVALID:
				alertify.warning(Message.FILE_SIZE_IS_INVALID);
				$.unblockUI();
				$(window).unbind('beforeunload');
				break;
		}
	});
	return result.promise();
}

function getQuestionData() {
	var question = {};
	question.questionTypeId=$("#questionTypeDropDown").val();
	question.topicId=$("#topicDropDown").val();
	question.difficultyId=$("#difficultyDropDown").val();
	question.label=$("#questionLabel").val();
	question.content=getContent("texteditor");
	question.status=getCheckBoxValue("#questionStatus");
	question.answer=getQuestionAnswer($("#questionTypeDropDown").val());
	question.matchCase=getMatchCase();
	question.ignoreOrder=getCheckBoxValue("#enumIgnoreOrder");
	question.choices=getMultipleChoicesData();
	question.enumerationAnswers=getEnumerationChoices();
	question.analogyAnswers=getAnalogyAnswers();
	question.fillInTheBlanksAnswers=getFillInTheBlanksAnswers();
	question.orderingAnswers=getOrderingAnswers();
	return question;
}

function getOrderingAnswers() {
	var orderingAnswers=[];
	var orderCtr=1;
	$(".orderingAnswerTxtBox").each(function() {
		var orderingAnswer={};
		orderingAnswer.orderNo=orderCtr;
		orderingAnswer.answer=$(this).val();
		orderingAnswers.push(orderingAnswer);
		orderCtr++;
	});
	return orderingAnswers;
}

function getFillInTheBlanksAnswers() {
	var editorHtml=getContent("texteditor");
	var tempFITBList=[];
	_.each(fillInTheBlanksAnswerList,function(fillInTheBlanksAnswerObj) {
		if(editorHtml.includes(fillInTheBlanksAnswerObj.componentCode)) {
			tempFITBList.push(fillInTheBlanksAnswerObj);
		}
	});
	return tempFITBList;
}

function getAnalogyAnswers() {
	var tempAnalogyAnswerList = []; 
	_.each(analogyAnswerList,function(analogyAnswer) {
		var analogyAnswerObj = {};
		analogyAnswerObj.givenA=$("#pairA_"+analogyAnswer.analogyId).val();
		analogyAnswerObj.givenB=$("#pairB_"+analogyAnswer.analogyId).val();
		tempAnalogyAnswerList.push(analogyAnswerObj);
	});
	return tempAnalogyAnswerList;
}

function getMatchCase() {
	var result="";
	if($("#questionTypeDropDown").val()==QuestionType.IDENTIFICATION) {
		result=getCheckBoxValue("#identificationMatchCaseChkBox");
	}
	if($("#questionTypeDropDown").val()==QuestionType.ENUMERATION) {
		result=getCheckBoxValue("#enumMatchCaseChkBox");
	}
	return result;
}

function getEnumerationChoices() {
	var enumeratioAnswers=[];
	$(".enumChoice").each(function(index) {
		if($(this).val()!="") {
			var enumerationAnswer={};
			enumerationAnswer.answer=$(this).val();
			enumerationAnswer.orderNo=index+1;
			enumeratioAnswers.push(enumerationAnswer);
		}
	});
	return enumeratioAnswers;
}

function getMultipleChoicesData() {
	var choices = [];
	var divCons = $(".choiceRow");
	_.each(divCons,function(divCon) {
		if(!$("#" + divCon.id).is(":empty")) {
			var choiceObj = {};
			if($("#" + divCon.id).find("input[type=text]").val() != "") {
				choiceObj.choiceDesc = $("#" + divCon.id).find("input[type=text]").val();
				choiceObj.choiceType = ($("#" + divCon.id).find("input[type=checkbox]").prop("checked")!= true)? 0 : 1;
				choices.push(choiceObj);
			}
		}
	});
	return choices;
}

function getQuestionAnswer(questionTypeId) {
	var answer = "";
	switch(parseInt(questionTypeId)) {
		case QuestionType.IDENTIFICATION:
			answer = $("#identificationAnswer").val();
			break;
		case QuestionType.TRUE_OR_FALSE:
			answer = getTrueOrFalseValue();
			break;
	}
	return answer;
}

function getTrueOrFalseValue() {
	var trueChkBox = getCheckBoxValue("#trueChckBox");
	var falseChkBox = getCheckBoxValue("#falseChckBox");
	if(trueChkBox == 1) {
		return true;
	} else if(falseChkBox == 1) {
		return false;
	}
}

function attachListenerToAddEnumeration() {
	$("#btnAddEnumeration").on("click",function() {
		var enumerationAnswers=getEnumerationChoices();
		if(enumerationAnswers.length<10) {
			createEnumerationAnswer();
		} else {
			alertify.warning("Enumeration answers cannot exceed 10 answers.")
		}
	});
}

function createEnumerationAnswer() {
	$("#enumerationAnswersCon")
		.append($("<div>").attr({"id":"enumChoice_"+enumerationAnswerCtr}).addClass("col s10 m11")
			.append($("<input/>").addClass("enumChoice")
				.attr({"type":"text","placeholder":"Options","maxlength":"200"})))
		.append($("<div>").attr({"id":"enumChoiceDeleteCon_"+enumerationAnswerCtr}).css("padding-top","15px").addClass("col s2 m1")
			.append($("<a/>").addClass("waves-effect waves-light deleteEnumChoiceLink")
				.attr({"data-id":enumerationAnswerCtr})
				.append($("<i/>").addClass("material-icons").html("delete"))));
	enumerationAnswerCtr++;
}

function attachListenerToDeleteEnumAnswer() {
	$("body").on("click",".deleteEnumChoiceLink",function() {
		var enumerationAnswerId=$(this).data("id");
		$("#enumChoice_"+enumerationAnswerId).remove();
		$("#enumChoiceDeleteCon_"+enumerationAnswerId).remove();
	});
}

function attachListenerToAddPairBtn() {
	$("#addPair").on("click",function() {
		var analogyanswer = {};
		analogyanswer.analogyId = pairCtr;
		analogyanswer.givenA = "";
		analogyanswer.givenB = "";
		analogyAnswerList.push(analogyanswer);
		createPairRow();
	});
}

function createPairRow() {
	var ctr = 1;
	$("#pairDiv").append($("<div/>").attr("id","pairSection_"+pairCtr).append($("<div/>").addClass("col s12 m6")
		.append($("<input/>").attr({"type":"text","placeholder":"Pair "+analogyAnswerList.length,"id":"pairA_"+pairCtr})
			.addClass("pairA")))
			.append($("<div/>").addClass("col s11 m5")
		.append($("<input/>").attr({"type":"text","placeholder":"Pair "+analogyAnswerList.length,"id":"pairB_"+pairCtr})
			.addClass("pairB")))
		.append($("<div/>").addClass("col s1 m1 orderingicons")
			.append($("<a/>").addClass("removeAnalogy").attr({"href":"#!","data-id":pairCtr})
				.append($("<i/>").addClass("material-icons").html("delete")))));
	pairCtr++;
}

function attachListenerToRemoveAnalogyLink() {
	$("body").on("click",".removeAnalogy",function() {
		analogyAnswerList = removeAnalogyAnswerObj($(this).data("id"));
		$("#pairSection_"+$(this).data("id")).remove();
		updateMatchingPlaceHolder();
	});
}

function removeAnalogyAnswerObj(analogyId) {
	var tempAnalogyArray=[];
	_.each(analogyAnswerList,function(analogyAnswer) {
		if(analogyAnswer.analogyId != analogyId) {
			tempAnalogyArray.push(analogyAnswer);
		}
	});
	return tempAnalogyArray;
}

function updateMatchingPlaceHolder() {
	var ctr = 1
	_.each(analogyAnswerList,function(analogyAnswer) {
		$("#pairA_"+analogyAnswer.analogyId).attr("placeholder","Pair "+ctr);
		$("#pairB_"+analogyAnswer.analogyId).attr("placeholder","Pair "+ctr);
		ctr++;
	});
}

function attachListenerToFiTBAddChoicesBtn() {
	$("#fitbAddChoiceBtn").on("click",function(){
		createFillInTheBlanksChoiceRow();
	});
}

function createFillInTheBlanksChoiceRow() {
	$("#fitbChoiceDiv").append($("<div/>").attr("id","fitbChoiceDiv_"+fillInTheBlanksChoicesCtr)
		.addClass("row").css("margin-bottom","10px")
			.append($("<div/>").addClass("col s7 m9")
				.append($("<input/>").attr({"type":"text","data-id":fillInTheBlanksChoicesCtr}).addClass("fitb_choice")))
			.append($("<div/>").addClass("col s3 m2")
				.append($("<p/>").addClass("pchck")
					.append($("<label/>").css("margin","5px 0 0 10px")
						.append($("<input/>").attr({"type":"checkbox","data-id":fillInTheBlanksChoicesCtr})
							.addClass("fitb_isanswer"))
						.append($("<span>Correct</span>")))))
			.append($("<div/>").css("padding-top","15px").addClass("col s2 m1")
				.append($("<a/>").attr({"data-id":fillInTheBlanksChoicesCtr})
					.addClass("waves-effect waves-light deleteFITBChoiceBtn")
					.append($("<i/>").addClass("material-icons").html("delete")))));	
	fillInTheBlanksChoicesCtr++;
}

function attachListenerToSaveFITBDropDownBtn() {
	$("#saveFITBDropDownBtn").on("click",function() {
		if(validateFITBChoices()) {
			tinymce.activeEditor.insertContent(createFITBDropdown(getFITBChoiceAnswer(),getFITBChoicesData()));
			$("#addFillInTheBlanksDropDownModal").modal("close");
			fillInTheBlanksChoicesCtr=1;
			reInitFITBChoicesRows();
		}
	});
}

function createFITBDropdown(answer,fitbChoices) {
	var htmlContent = [];
	htmlContent.push("<select id='"+fitbChoices[0].componentCode+"'>")
	_.each(fitbChoices,function(fitbChoiceObj) {
		if(fitbChoiceObj.answer==answer) {
			htmlContent.push("<option selected>"+fitbChoiceObj.choice+"</option>")
		} else {
			htmlContent.push("<option>"+fitbChoiceObj.choice+"</option>")
		}
	});
	htmlContent.push("</select>");
	return htmlContent.join("");
}

function getFITBChoicesData() {
	var fitbChoices=[];
	var answer="";
	var componentCode=generateRandomString(8);
	$(".fitb_choice").each(function(index) {
		var fitbChoiceObj={};
		fitbChoiceObj.componentCode=componentCode;
		fitbChoiceObj.choice=$(this).val();
		if(isFITBSelectedAnswer($(this).data("id"))) {
			fitbChoiceObj.answer=$(this).val();
			answer=$(this).val();
		}
		fitbChoices.push(fitbChoiceObj);
	});
	pushFITBAnswerData(ChoiceType.MULTIPLE_CHOICE,componentCode,answer,Status.INACTIVE);
	return fitbChoices;
}

function getFITBChoiceAnswer() {
	var answer="";
	$(".fitb_choice").each(function(index) {
		if(isFITBSelectedAnswer($(this).data("id"))) {
			answer=$(this).val();
		}
	});
	return answer;
}

function pushFITBAnswerData(choiceType,componentCode,answer,isMatchCase) {
	var fillInTheBlanksAnswer={};
	fillInTheBlanksAnswer.choiceType=choiceType;
	fillInTheBlanksAnswer.componentCode=componentCode;
	fillInTheBlanksAnswer.answer=answer;
	fillInTheBlanksAnswer.isMatchCase=isMatchCase;
	fillInTheBlanksAnswerList.push(fillInTheBlanksAnswer);
}

function isFITBSelectedAnswer(choiceId) {
	var result=false;
	$(".fitb_isanswer").each(function(){
		if($(this).data("id")==choiceId && $(this).prop("checked")) {
			result=true;
		} 
	});
	return result;
}

function validateFITBChoices() {
	var result=true;
	if(isFITBChoiceEmpty()) {
		alertify.warning("All choices must have a value.");
		result=false;
	}
	if(checkFITBNoOfCorrectAnswers()>1) {
		alertify.warning("There should be only one answer.");
		result=false;
	}
	if(checkFITBNoOfCorrectAnswers()==0) {
		alertify.warning("There should be atleast one answer.");
		result=false;
	}
	return result;
}

function isFITBChoiceEmpty() {
	var result=false;
	$(".fitb_choice").each(function(index) {
		if($(this).val() == "") {
			result=true;
		}
	});
	return result;
}

function checkFITBNoOfCorrectAnswers() {
	var ctr=0;
	$(".fitb_isanswer").each(function(index) {
		if($(this).prop("checked")) {
			ctr++;
		}
	});
	return ctr;
}
function attachListenerToDeleteFITBChoiceBtn() {
	$("body").on("click",".deleteFITBChoiceBtn",function() {
		var fillIntheBlankId=$(this).data("id");
		$("#fitbChoiceDiv_"+fillIntheBlankId).remove(); 
	})
}

function attachListenerToFITBCancelBtn() {
	$("#fitbCancelBtn").on("click",function() {
		fillInTheBlanksChoicesCtr=1;
		reInitFITBChoicesRows();
	});
}

function reInitFITBChoicesRows() {
	$("#fitbChoiceDiv").html("");
	$("#fitbChoiceDiv").append($("<div/>").attr("id","fitbChoiceDiv_0")
			.addClass("row").css("margin-bottom","10px")
				.append($("<div/>").addClass("col s7 m9")
					.append($("<input/>").attr({"type":"text","data-id":"0"}).addClass("fitb_choice")))
				.append($("<div/>").addClass("col s3 m2")
					.append($("<p/>").addClass("pchck")
						.append($("<label/>").css("margin","5px 0 0 10px")
							.append($("<input/>").attr({"type":"checkbox","data-id":"0"})
								.addClass("fitb_isanswer"))
							.append($("<span>Correct</span>")))))
				.append($("<div/>").css("padding-top","15px").addClass("col s2 m1")
					.append($("<a/>").attr({"data-id":0})
						.addClass("waves-effect waves-light deleteFITBChoiceBtn")
						.append($("<i/>").addClass("material-icons").html("delete")))));	
}

function attachListenerToFITBSaveDirectAnswerBtn() {
	$("#saveFITBAnswerTxtBoxBtn").on("click",function() {
		var answer=$("#fitbDirectAnswerTxtBox").val();
		var isMatchCase=getCheckBoxValue("#fitbDirectAnswerChkBox");
		var componentCode=generateRandomString(8);
		if(validateFITBDirectAnswer()) {
			pushFITBAnswerData(ChoiceType.DIRECT_ANSWER,componentCode,answer,isMatchCase);
			tinymce.activeEditor.insertContent("<input type='text' id='"+componentCode+"' value='"+answer+"' readonly>");
			$("#addFillInTheBlanksTextBoxModal").modal("close");
			$("#fitbDirectAnswerTxtBox").val("");
			setCheckBoxValue("#fitbDirectAnswerChkBox",Status.INACTIVE);
		}
	});
}

function validateFITBDirectAnswer() {
	var answer=$("#fitbDirectAnswerTxtBox").val();
	var result=true;
	if(answer=="") {
		alertify.warning("Answer cannot be empty.");
		result=false;
	}
	return result;
}

function attachListenerToAddOrderingBtn() {
	$("#addOrderingBtn").on("click",function() {
		if(orderingCtr<20) {
			var orderingAnswer={};
			orderingAnswer.orderingId=orderingCtr;
			orderingAnswer.orderNo=orderingCtr;
			createOrderingRow(orderingAnswer);
		}
	});
}

function createOrderingRow(orderingAnswer) {
	$("#orderingRow").append($("<div/>").attr("id","orderingAnswerRow_"+orderingAnswer.orderingId)
			.addClass("col s10 m10 sortable")
			.append($("<a/>").attr({"href":"#!","data-id":orderingAnswer.orderingId})
				.bind("click",function() {
					removeOrderingAnswer($(this).data("id"));
				}).append($("<i/>").addClass("material-icons").html("delete")))
			.append($("<label/>").html("MOVE ME"))
			.append($("<input/>").addClass("orderingAnswerTxtBox").attr({"type":"text","data-id":orderingAnswer.orderingId})));
	orderingCtr++;
}

function removeOrderingAnswer(orderingId) {
	$("#orderingAnswerRow_"+orderingId).remove();
	orderingCtr=orderingCtr-1;
}