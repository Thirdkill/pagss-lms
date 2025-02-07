$(function(){
	defineCounters();
	defineGlobalList();
	removeTabIndex();
	initTextEditor();
	initExamTypeDropdown();
	initQuestionTypeDropDown();
	initTopicDropDown();
	initDifficultyLevelDropDown();
	initExamCode();
	initQuestionsTbl();
	initExamInfo();
	initExamSections();
	attachListenerToAddSectionBtn(); 
	attachListenerToAddPartBtn();
	attachListenerToSaveExamBtn();
	attachListenerToRandomizeSwitch();
	attachListenerToShuffleSwitch();
	attachListenerToAddQuestionsBtn();
	attachListenerToSearchQuestionsTxtBox();
	attachListenerToAddExistingBtn();
	attachListenerToSelectAllChkBox();
	attachListenerToNonRandomizeDelete();
	attachListenerToNonRandomizeWeightTxt();
	attachListenerToBtnCancel();
	attachListenerToCancelExamBtn();
	attachListenerToDeletePartLink();
});

function defineCounters() {
	sectionCounter = 1;
	partCounter = 1;
	totalScore = 0;
}

function defineGlobalList() {
	randomizedQuestionsList = [];
	deletedItems = [];
	deletedPartItems = [];
	deletedSections=[];
	nonRandomizeQuestionList = [];
	nonRandomizeCheckedList = [];
	questionList = [];
	sectionList=[];
}

function removeTabIndex() {
	$("#addSectionModal").removeAttr("tabindex");
	$("#partModal").removeAttr("tabindex");
	$("#addExistingModal").removeAttr("tabindex");
}

function initTextEditor() {
	initTinyMceEditor("#texteditor",300);
}

function initExamTypeDropdown() {
	_.each(ExamTypes,function(examType) {
		$("#examTypeDropdown").append($("<option/>").attr({"value" : examType.id})
			.html(examType.description));
	});
}

function initQuestionTypeDropDown() {
	$.when(ajax.fetch("questiontypes")).done(function(response) {
		_.each(response.questionTypes,function(questionType) {
			$("#questionTypeDropDown,#existQuestionTypeDropdown").append($("<option/>")
				.attr({"value" : questionType.questionTypeId})
				.html(questionType.questionTypeDesc));
		});
	});
}

function initTopicDropDown() {
	$.when(ajax.fetch("trainingtopics")).done(function(response) {
		_.each(response.trainingTopics,function(trainingTopic) {
			$("#partTopicDropDown,#existTopicDropDown").append($("<option/>").attr({"value" : trainingTopic.topicId})
				.html(trainingTopic.topicDesc));
		});
	});
}

function initDifficultyLevelDropDown() {
	$.when(ajax.fetch("difficultylevels")).done(function(response) {
		_.each(response.difficultyLevels,function(difficultyLevel) {
			$("#partDifficultyDropDown, #existDifficultyDropdown").append($("<option/>").attr({"value" : difficultyLevel.difficultyId})
				.html(difficultyLevel.difficultyName));
		});
	});
}

function initExamCode() {
	if($("#action").val()=="create") {
		$.when(ajax.fetch("examinfo/generate-examcode")).done(function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					var examInfo = response.examInfo;
					$("#examCode").val(examInfo.examCode);
					break;
			}
		});
	}
}

function initQuestionsTbl() {
	$("#questionTblPagination").pagination({     
        dataSource: "questions/pages",
        locator: "questions",
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        ajax: {data:{
    		questionType:$("#existQuestionTypeDropdown").val(),
    		topic:$("#existTopicDropDown").val(),
    		difficultyLevel:$("#existDifficultyDropdown").val(),
    		status: Status.ACTIVE,
    		sortName:"createdAt",
    		sortDir:"ASC"
    		},type:"POST"},
        className:"paginationjs-theme-blue",
        pageSize: 5,     
        callback: function(data) {                      
            $("#questionTblBody").html("");
            questionList = data;
            _.each(data, function(question) {
            	createQuestionsRow(question);
            });
        }
    });
}

function createQuestionsRow(question) {
	var sectionId=$("#existingSectionValue").val();
	$("#selectAllCheckBox").prop("checked",false);
	$("#questionTblBody").append($("<tr/>")
		.append($("<td/>").append($("<label/>").css("margin-left","15px !important")
			.append($("<input/>").addClass("checkbox_"+sectionId).attr({"type":"checkbox",
				"data-questionid":question.questionId,"data-sectionid":sectionId})
			.prop("checked",checkIfExistInCheckedList(sectionId,question.questionId))
			.bind("change",function() {
				if($(this).prop("checked")) {
					nonRandomizeCheckedList.push(getQuestionObj($(this).data("sectionid"),$(this).data("questionid")));
				} else {
					nonRandomizeCheckedList = removeNonRandomizeCheckedListItem($(this).data("questionid"));
				}
			}))
			.append($("<span/>"))))
		.append($("<td/>").html(question.label))
		.append($("<td/>").html(question.topicDesc))
		.append($("<td/>").html(question.difficultyName))
		.append($("<td/>").html(question.questionTypeDesc)));
}

function checkIfExistInCheckedList(sectionId,questionId) {
	var result = false;
	_.each(nonRandomizeCheckedList,function(question) {
		if(question.questionId==questionId && question.sectionId==sectionId) {
			result = true;
		}
	});
	return result;
}

function getQuestionObj(sectionId,questionId) {
	var questionObj = {};
	_.each(questionList,function(question) {
		if(question.questionId == questionId) {
			question.sectionId = sectionId;
			questionObj = question;
		}
	});
	return questionObj;
}

function removeNonRandomizeCheckedListItem(questionId) {
	var tempArray = [];
	_.each(nonRandomizeCheckedList,function(question) {
		if(question.questionId != questionId) {
			tempArray.push(question);
		}
	});
	return tempArray;
}

function initExamInfo() {
	if($("#action").val() == "update") {
		var examId = $("#examId").val();
		$.when(ajax.fetchObj("examinfo/",examId)).done(function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					populateExamDetails(response.examInfo);
					break;
			}
		});
	}
}

function initExamSections() {
	if($("#action").val() == "update") {
		var examId = $("#examId").val();
		$.when(ajax.fetch("examinfo/"+examId+"/questions")).done(function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					populateExamSections(response.examQuestions);
					break;
			}
		});
	}
}

function populateExamSections(examQuestions) {
	_.each(examQuestions,function(examQuestion) {
		sectionCounter = examQuestion.sectionOrder;
		examQuestion.sectionId = examQuestion.sectionOrder;
		examQuestion.partId = partCounter++;
		if(examQuestion.isRandomized == 1) {
			if(!isSectionExist(examQuestion.sectionOrder)) {populateRandomizedSection(examQuestion);} 
			examQuestion.questionTypeDesc = examQuestion.randomizeQuestionDesc;
			randomizedQuestionsList.push(examQuestion);
			populatePartTable(examQuestion);
			
		} else {
			if(!isSectionExist(examQuestion.sectionOrder)) {populateNonRandomSection(examQuestion);}
			nonRandomizeQuestionList.push(examQuestion);
			populateNonRandomizeSectionTbl(examQuestion);
		}
	});
	reComputeTotalScore(examQuestions);
}

function reComputeTotalScore(examQuestions) {
	var randomizetotalScore = 0;
	var nonRandomizetotalScore = 0;
	_.each(examQuestions,function(examQuestion) {
		if(examQuestion.isRandomized != 1) {
			randomizetotalScore = randomizetotalScore + examQuestion.weight;
			$("#hiddenTotalScore_"+examQuestion.sectionId).val(randomizetotalScore);
			$("#totalScore_"+examQuestion.sectionId).html("").html(randomizetotalScore);
		} 
		else {
			nonRandomizetotalScore = nonRandomizetotalScore+(examQuestion.noOfQuestions*examQuestion.weight);
			$("#hiddenTotalScore_"+examQuestion.sectionId).val(nonRandomizetotalScore);
			$("#totalScore_"+examQuestion.sectionId).html("").html(nonRandomizetotalScore);
		}
	});
}

function isSectionExist(sectionOrder) {
	var result = false;
	_.each(randomizedQuestionsList,function(question) {
		if(question.sectionOrder==sectionOrder) {
			result=true;
		}
	});
	_.each(nonRandomizeQuestionList,function(question) {
		if(question.sectionOrder==sectionOrder) {
			result=true;
		}
	})
	return result;
}

function populateRandomizedSection(examQuestion) {
	$("#sectionsCon").append($("<div/>").attr("id","section_"+examQuestion.sectionId).addClass("row")
		.append($("<div/>").addClass("divcard")
			.append($("<div/>").addClass("row")
				.append($("<ul/>").addClass("collapsible")
					.append($("<li/>")
						.append($("<div/>").addClass("collapsible-header").css("padding","10px").attr("tabindex","0")
							.append($("<span/>").attr("id","sectionNameLbl_"+examQuestion.sectionId)
								.html(examQuestion.sectionName).css("padding-top","5px")
								.append("<span style='color:red;visibility: hidden;' id='required_"+sectionCounter+"' " +
									"class='requiredLabel'>*</span>"))
							.append($("<div/>").addClass("collapsibleIcon")
								.append($("<i/>").addClass("material-icons rotate arrowRotate").html("arrow_drop_down"))
								.append($("<a/>").attr({"href":"#!","data-sectionid":examQuestion.sectionId})
										.bind("click",function() {
											$("#section_"+$(this).data("sectionid")).html("");
											deletedItems.push($(this).data("sectionid"));
									}).append($("<i/>").addClass("material-icons")
										.html("delete")))))
						.append($("<div/>").addClass("collapsible-body bodyContent")
							.append($("<div/>").addClass("row")
								.append($("<div/>").addClass("col s12 m6").css("padding","0")
									.append($("<input/>").attr({
										"type":"text","placeholder":"Section Name","data-sectionid":examQuestion.sectionId,
										"value":examQuestion.sectionName,"id":"sectionName_" + examQuestion.sectionId})
									.bind("keyup",function() {
										$("#sectionNameLbl_"+$(this).data("sectionid")).html($(this).val());
									})))
							.append($("<div/>").addClass("col s12 m3").css("padding","18px 0")
								.append($("<div/>").addClass("switch center")
									.append($("<span/>").css("margin-right","5px").html("Randomized Questions"))
									.append($("<label/>")
										.append($("<input>").prop("disabled",true).attr({
											"type":"checkbox","id":"sectionIsRandomized_"+sectionCounter})
										.prop("checked",(examQuestion.isRandomized==1)?"checked":""))
										.append($("<span/>").addClass("lever").css("margin-top","0 !important")))))
							.append($("<div/>").addClass("col s12 m3").css("padding","18px 0")
								.append($("<div/>").addClass("switch center")
									.append($("<span/>").css("margin-right","5px").html("Shuffle Questions"))
									.append($("<label/>")
										.append($("<input>").prop("disabled",true).attr({
											"type":"checkbox","id":"sectionIsShuffle_"+examQuestion.sectionId})
										.prop("checked",(examQuestion.isShuffle==1)?"checked":""))
										.append($("<span/>").addClass("lever").css("margin-top","0 !important")))))
							.append($("<div/>").addClass("row")
								.append($("<div>").addClass("sectioneditor").attr("id","sectioneditor_"+examQuestion.sectionId)))
							.append($("<div/>").addClass("row").css("margin","10px 0 10px 0")
								.append($("<div/>")
									.append($("<div/>").addClass("col s6 m6").css("padding","8px 0 0 0")
										.append($("<span/>").attr("id","questionTypeLbl_"+examQuestion.sectionId)
											.append($("<b>Question Type: </b>"))
												.append(examQuestion.randomizeQuestionTypeDesc)))
									.append($("<div>").addClass("col s6 m6").css("padding-right","0")
										.append($("<a/>").attr({
												"href":"#partModal",
												"data-target":"partModal",
												"data-sectionid":examQuestion.sectionId})
											.addClass("modal-trigger bttn btn waves-effect waves-light right")
											.html("Add Part").bind("click",function() {
												$("#partSectionValue").val($(this).data("sectionid"));
											})
										.append($("<i/>").addClass("material-icons left").html("add_circle"))))))
							.append($("<div/>")
								.append($("<table/>").addClass("highlight striped")
									.append($("<thead/>")
										.append($("<tr/>")
											.append($("<th/>").addClass("min").html("Action"))
											.append($("<th/>").addClass("min").html("Topic"))
											.append($("<th/>").addClass("min").html("Difficulty Level"))
											.append($("<th/>").addClass("min").html("No. of Questions"))
											.append($("<th/>").addClass("min").html("Weight"))))
									.append($("<tbody/>").attr("id","sectionTblBody_"+sectionCounter))))
							.append($("<div/>").css("padding","8px 0 0 0;")
								.append($("<b/>").html("Total Score: "))
								.append($("<span/>").attr("id","totalScore_"+examQuestion.sectionId).html(examQuestion.sectionId)))
							.append($("<input>").attr({"id":"hiddenTotalScore_"+examQuestion.sectionId,"value":"0","type":"hidden"}))
							.append($("<input>").attr({"id":"hiddenQuestionTypeId_"+examQuestion.sectionId,
								"value":examQuestion.randomizedQuestionTypeId,"type":"hidden"}))
									)))))));
	$(".collapsible").collapsible();
	initTinyMceEditor(".sectioneditor",300);
	setContent("sectioneditor_"+examQuestion.sectionId,examQuestion.instruction);
	$("#hiddenQuestionTypeId_"+examQuestion.sectionId).val(examQuestion.randomizedQuestionTypeId);
}

function populateNonRandomSection(examQuestion) {
	$("#sectionsCon").append($("<div/>").attr("id","section_"+examQuestion.sectionId).addClass("row")
		.append($("<div/>").addClass("divcard")
			.append($("<div/>").addClass("row")
				.append($("<ul/>").addClass("collapsible")
					.append($("<li/>")
						.append($("<div/>").addClass("collapsible-header").css("padding","10px").attr("tabindex","0")
							.append($("<span/>").attr("id","sectionNameLbl_"+sectionCounter).html(examQuestion.sectionName)
								.css("padding-top","5px").append("<span style='color:red;visibility: hidden;'" +
										" id='required_"+sectionCounter+"' class='requiredLabel'>*</span>"))
							.append($("<div/>").addClass("collapsibleIcon")
								.append($("<i/>").addClass("material-icons rotate arrowRotate").html("arrow_drop_down"))
								.append($("<a/>").attr({"href":"#!","data-sectionid":examQuestion.sectionId})
										.bind("click",function() {
											$("#section_"+$(this).data("sectionid")).html("");
											deletedItems.push($(this).data("sectionid"));
									}).append($("<i/>").addClass("material-icons")
										.html("delete")))))
						.append($("<div/>").addClass("collapsible-body bodyContent")
							.append($("<div/>").addClass("row")
								.append($("<div/>").addClass("col s12 m6").css("padding","0")
									.append($("<input/>").attr({
										"type":"text","placeholder":"Section Name","data-sectionid":examQuestion.sectionId,
										"value":examQuestion.sectionName,"id":"sectionName_" + examQuestion.sectionId})
									.bind("keyup",function() {
										$("#sectionNameLbl_"+$(this).data("sectionid")).html($(this).val());})))
							.append($("<div/>").addClass("col s12 m3").css("padding","18px 0")
								.append($("<div/>").addClass("switch center")
									.append($("<span/>").css("margin-right","5px").html("Randomized Questions"))
									.append($("<label/>")
										.append($("<input>").prop("disabled",true).attr({
											"type":"checkbox","id":"sectionIsRandomized_"+examQuestion.sectionId}))
										.append($("<span/>").addClass("lever").css("margin-top","0 !important")))))
							.append($("<div/>").addClass("col s12 m3").css("padding","18px 0")
								.append($("<div/>").addClass("switch center")
									.append($("<span/>").css("margin-right","5px").html("Shuffle Questions"))
									.append($("<label/>")
										.append($("<input>").prop("disabled",true).attr({
											"type":"checkbox","id":"sectionIsShuffle_"+examQuestion.sectionId})
											.prop("checked",(examQuestion.isShuffled!=1)?false:true))
										.append($("<span/>").addClass("lever").css("margin-top","0 !important")))))
							.append($("<div/>").addClass("row")
								.append($("<div>").addClass("sectioneditor").attr("id","sectioneditor_"+examQuestion.sectionId)))
							.append($("<div/>").addClass("row").css("margin","10px 0 10px 0")
								.append($("<div/>")
									.append($("<div/>").addClass("col s6 m6").css("padding","8px 0 0 0")
										.append($("<span/>").attr("id","questionTypeLbl_"+examQuestion.sectionId)
											.append($("<b/>").html("Questions:"))))
									.append($("<div/>").addClass("col s6 m6").css("padding-right","0")
										.append($("<div>").addClass("right")
											.append($("<a/>").attr({"target":"_blank","href":"admin.question?action=create","type":"submit",})
												.css("margin-right","15px").addClass("bttn btn waves-effect waves-light")
												.html("Add new")
												.append($("<i/>").addClass("material-icons left").html("add_circle")))
											.append($("<a/>")
												.attr({"href":"#!","type":"submit","data-sectionid":examQuestion.sectionId,
													"data-target":"addExistingModal"}).addClass("addExisting")
												.bind("click",function() {
													$("#existingSectionValue").val($(this).data("sectionid"));})
												.css("margin-right","15px")
												.addClass("modal-trigger bttn btn waves-effect waves-light right")
												.html("Add Existing")
												.append($("<i/>").addClass("material-icons left").html("add_circle")))))))
							.append($("<div/>")
								.append($("<table/>").addClass("highlight striped")
									.append($("<thead/>")
										.append($("<tr/>")
											.append($("<th/>").addClass("min").html("Action"))
											.append($("<th/>").addClass("large").html("Question"))
											.append($("<th/>").addClass("mid").html("Type"))
											.append($("<th/>").addClass("mins").html("Weight"))))
									.append($("<tbody/>").attr("id","sectionTblBody_"+sectionCounter))))
							.append($("<div/>").css("padding","8px 0 0 0;")
								.append($("<b/>").html("Total Score: "))
								.append($("<span/>").attr("id","totalScore_"+examQuestion.sectionId).html(examQuestion.sectionId)))
							.append($("<input>").attr({"id":"hiddenTotalScore_" + examQuestion.sectionId,"value":"0","type":"hidden"})))))))));
		$(".collapsible").collapsible();
		initTinyMceEditor(".sectioneditor",300);
		setContent("sectioneditor_"+examQuestion.sectionId,examQuestion.instruction);
}

function populateExamDetails(examInfo) {
	$("#examCode").val(examInfo.examCode).prop("disabled",true);
	$("#examTitle").val(examInfo.title);
	$("#examTypeDropdown").val(examInfo.examType).trigger("change");
	setCheckBoxValue("#examStatus",examInfo.status);
	setCheckBoxValue("#allowAttachment",examInfo.allowAttachment);
	setContent("texteditor",examInfo.description);
}

function attachListenerToAddSectionBtn() {
	$("#addSectionBtn").on("click",function() {
		if(validateAddSectionFields()) {
			var section = {};
			section.name = $("#sectionName").val();
			section.isRandomized = getCheckBoxValue("#isRandomized");
			section.isShuffled = getCheckBoxValue("#isShuffle");
			section.questionTypeId = $("#questionTypeDropDown").val();
			section.questionTypeDesc = $("#questionTypeDropDown option:selected").text();
			if(getCheckBoxValue("#isRandomized")==1){addRandomizedSection(section)}
			else {addNonRandomSection(section);};
			$("#addSectionModal").modal("close");
			clearSectionModalFields();
		}
	});
}

function validateAddSectionFields() {
	var result="true";
	if(getCheckBoxValue("#isRandomized")==1 && $("#questionTypeDropDown").val()==null) {
		alertify.warning("Please choose a Question Type.");
		result = false;
	}
	return result;
}

function clearSectionModalFields() {
	$("#sectionName").val("");
	$("#isRandomized").prop("checked",false);
	$("#isShuffle").prop("checked",false);
	$("#questionTypeDropDown").val("");
	$("#questionTypeDiv").css("display","none");	
}

function addRandomizedSection(section) {
	sectionCounter++;
	$("#sectionsCon").append($("<div/>").attr("id","section_"+sectionCounter).addClass("row")
		.append($("<div/>").addClass("divcard")
			.append($("<div/>").addClass("row")
				.append($("<ul/>").addClass("collapsible")
					.append($("<li/>")
						.append($("<div/>").addClass("collapsible-header").css("padding","10px").attr("tabindex","0")
							.append($("<span/>").attr("id","sectionNameLbl_"+sectionCounter).html(section.name)
								.css("padding-top","5px").append("<span style='color:red;visibility:hidden;' " +
										"id='required_"+sectionCounter+"' class='requiredLabel'>*</span>"))
							.append($("<div/>").addClass("collapsibleIcon")
								.append($("<i/>").addClass("material-icons rotate arrowRotate").html("arrow_drop_down"))
								.append($("<a/>").attr({"href":"#!","data-sectionid":sectionCounter})
										.bind("click",function() {
											$("#section_"+$(this).data("sectionid")).html("");
											deletedItems.push($(this).data("sectionid"));
											deletedSections.push($(this).data("sectionid"));
									}).append($("<i/>").addClass("material-icons")
										.html("delete")))))
						.append($("<div/>").addClass("collapsible-body bodyContent")
							.append($("<div/>").addClass("row")
								.append($("<div/>").addClass("col s12 m6").css("padding","10px 0 0 0")
									.append($("<input/>").attr({
										"type":"text","placeholder":"Section Name","data-sectionid":sectionCounter,
										"value":section.name,"id":"sectionName_" + sectionCounter})
									.bind("keyup",function() {
										$("#sectionNameLbl_"+$(this).data("sectionid")).html($(this).val());
									})))
							.append($("<div/>").addClass("col s12 m3").css("padding","0 0 18px 0")
								.append($("<div/>").addClass("switch center")
									.append($("<span/>").css("margin-right","5px").html("Randomized Questions")
											.append($("<br>")))
									.append($("<label/>")
										.append($("<input>").prop("disabled",true).attr({
											"type":"checkbox","id":"sectionIsRandomized_"+sectionCounter}))
										.append($("<span/>").addClass("lever").css("margin-top","0 !important")))))
							.append($("<div/>").addClass("col s12 m3").css("padding","0 0 18px 0")
								.append($("<div/>").addClass("switch center")
									.append($("<span/>").css("margin-right","5px").html("Shuffle Questions")
											.append($("<br>")))
									.append($("<label/>")
										.append($("<input>").prop("disabled",true).attr({
											"type":"checkbox","id":"sectionIsShuffle_"+sectionCounter}))
										.append($("<span/>").addClass("lever").css("margin-top","0 !important")))))
							.append($("<div/>").addClass("row").css("margin-bottom","10px")
								.append($("<div>").addClass("sectioneditor").attr("id","sectioneditor_"+sectionCounter)))
							.append($("<div/>").addClass("row").css("margin","10px 0 10px 0")
								.append($("<div/>")
									.append($("<div/>").addClass("col s6 m6").css("padding","8px 0 0 0")
										.append($("<span/>").attr("id","questionTypeLbl_"+sectionCounter)
											.append($("<b>Question Type: </b>"))
												.append(section.questionTypeDesc)))
									.append($("<div>").addClass("col s6 m6").css("padding-right","0")
										.append($("<a/>").attr({
												"href":"#partModal",
												"data-target":"partModal",
												"data-sectionid":sectionCounter})
											.addClass("modal-trigger bttn btn waves-effect waves-light right")
											.html("Add Part").bind("click",function() {
												$("#partSectionValue").val($(this).data("sectionid"));
											})
										.append($("<i/>").addClass("material-icons left").html("add_circle"))))))
							.append($("<div/>").css("margin-bottom","10px")
								.append($("<table/>").addClass("highlight striped")
									.append($("<thead/>")
										.append($("<tr/>")
											.append($("<th/>").addClass("min").html("Action"))
											.append($("<th/>").addClass("min").html("Topic"))
											.append($("<th/>").addClass("min").html("Difficulty Level"))
											.append($("<th/>").addClass("min").html("No. of Questions"))
											.append($("<th/>").addClass("min").html("Weight"))))
									.append($("<tbody/>").attr("id","sectionTblBody_"+sectionCounter))))
							.append($("<div/>").css("padding","8px 0 0 0;")
								.append($("<b/>").html("Total Score: "))
								.append($("<span/>").attr("id","totalScore_"+sectionCounter).html("0")))
							.append($("<input>").attr({"id":"hiddenTotalScore_" + sectionCounter,"value":"0","type":"hidden"}))
							.append($("<input>").attr({"id":"hiddenQuestionTypeId_" + sectionCounter,"value":section.questionTypeId,
								"type":"hidden"}))
									)))))));
	section.sectionId=sectionCounter;
	sectionList.push(section);
	$(".collapsible").collapsible();
	initTinyMceEditor(".sectioneditor",300);
	setCheckBoxValue("#sectionIsRandomized_"+sectionCounter,Status.ACTIVE);
}

function addNonRandomSection(section) {
	sectionCounter++;
	$("#sectionsCon").append($("<div/>").attr("id","section_"+sectionCounter).addClass("row")
		.append($("<div/>").addClass("divcard")
			.append($("<div/>").addClass("row")
				.append($("<ul/>").addClass("collapsible")
					.append($("<li/>")
						.append($("<div/>").addClass("collapsible-header").css("padding","10px").attr("tabindex","0")
							.append($("<span/>").attr("id","sectionNameLbl_"+sectionCounter).html(section.name)
								.css("padding-top","5px").append("<span style='color:red;visibility: hidden;'" +
										" id='required_"+sectionCounter+"' class='requiredLabel'>*</span>"))
							.append($("<div/>").addClass("collapsibleIcon")
								.append($("<i/>").addClass("material-icons rotate arrowRotate").html("arrow_drop_down"))
								.append($("<a/>").attr({"href":"#!","data-sectionid":sectionCounter})
										.bind("click",function() {
											$("#section_"+$(this).data("sectionid")).html("");
											deletedItems.push($(this).data("sectionid"));
											deletedSections.push($(this).data("sectionid"));
									}).append($("<i/>").addClass("material-icons")
										.html("delete")))))
						.append($("<div/>").addClass("collapsible-body bodyContent")
							.append($("<div/>").addClass("row")
								.append($("<div/>").addClass("col s12 m6").css("padding","10px 0 0 0")
									.append($("<input/>").attr({
										"type":"text","placeholder":"Section Name","data-sectionid":sectionCounter,
										"value":section.name,"id":"sectionName_" + sectionCounter})
									.bind("keyup",function() {
										$("#sectionNameLbl_"+$(this).data("sectionid")).html($(this).val());})))
							.append($("<div/>").addClass("col s12 m3").css("padding","0 0 18px 0")
								.append($("<div/>").addClass("switch center")
									.append($("<span/>").css("margin-right","5px").html("Randomized Questions")
											.append($("<br>")))
									.append($("<label/>")
										.append($("<input>").prop("disabled",true).attr({
											"type":"checkbox","id":"sectionIsRandomized_"+sectionCounter}))
										.append($("<span/>").addClass("lever").css("margin-top","0 !important")))))
							.append($("<div/>").addClass("col s12 m3").css("padding","0 0 18px 0")
								.append($("<div/>").addClass("switch center")
									.append($("<span/>").css("margin-right","5px").html("Shuffle Questions")
											.append($("<br>")))
									.append($("<label/>")
										.append($("<input>").prop("disabled",true).attr({
											"type":"checkbox","id":"sectionIsShuffle_"+sectionCounter})
											.prop("checked",(section.isShuffled!=1)?false:true))
										.append($("<span/>").addClass("lever").css("margin-top","0 !important")))))
							.append($("<div/>").addClass("row")
								.append($("<div>").addClass("sectioneditor").attr("id","sectioneditor_"+sectionCounter)))
							.append($("<div/>").addClass("row").css("margin","10px 0 10px 0")
								.append($("<div/>")
									.append($("<div/>").addClass("col s6 m6").css("padding","8px 0 0 0")
										.append($("<span/>").attr("id","questionTypeLbl_"+sectionCounter)
											.append($("<b/>").html("Questions:"))))
									.append($("<div/>").addClass("col s6 m6").css("padding-right","0")
										.append($("<div>").addClass("right")
											.append($("<a/>").attr({"target":"_blank","href":"admin.question?action=create","type":"submit",})
												.css("margin-right","15px").addClass("bttn btn waves-effect waves-light")
												.html("Add new")
												.append($("<i/>").addClass("material-icons left").html("add_circle")))
											.append($("<a/>")
												.attr({"href":"#!","type":"submit","data-sectionid":sectionCounter,
													"data-target":"addExistingModal"}).addClass("addExisting")
												.bind("click",function() {
													$("#existingSectionValue").val($(this).data("sectionid"));})
												.css("margin-right","15px")
												.addClass("modal-trigger bttn btn waves-effect waves-light right")
												.html("Add Existing")
												.append($("<i/>").addClass("material-icons left").html("add_circle")))))))
							.append($("<div/>").css("margin-bottom","10px")
								.append($("<table/>").addClass("highlight striped")
									.append($("<thead/>")
										.append($("<tr/>")
											.append($("<th/>").addClass("min").html("Action"))
											.append($("<th/>").addClass("large").html("Question"))
											.append($("<th/>").addClass("mid").html("Type"))
											.append($("<th/>").addClass("mins").html("Weight"))))
									.append($("<tbody/>").attr("id","sectionTblBody_"+sectionCounter))))
							.append($("<div/>").css("padding","8px 0 0 0;")
								.append($("<b/>").html("Total Score: "))
								.append($("<span/>").attr("id","totalScore_"+sectionCounter).html("0")))
							.append($("<input>").attr({"id":"hiddenTotalScore_" + sectionCounter,"value":"0","type":"hidden"})))))))));
	$(".collapsible").collapsible();
	initTinyMceEditor(".sectioneditor",300);
	section.sectionId=sectionCounter;
	sectionList.push(section);
}

function attachListenerToAddPartBtn() {
	$("#addPartBtn").on("click",function() {
		if(!isPartFieldsEmpty()) {
			var question = getPartModalFieldsData();
			var totalScore = parseInt($("#hiddenTotalScore_"+question.sectionId).val())+(parseInt(question.noOfQuestions)*1);
			populatePartTable(question);
			randomizedQuestionsList.push(question);
			deletedSections.push(parseInt(question.sectionId));
			$("#partModal").modal("close");
			partCounter++;
			$("#hiddenTotalScore_"+$("#partSectionValue").val()).val(totalScore);
			$("#totalScore_"+question.sectionId).html("").html(totalScore);
			clearPartModalFields();
		}
	});
}

function getPartModalFieldsData() {
	var sectionId = $("#partSectionValue").val();
	var totalScore = parseInt($("#hiddenTotalScore_"+sectionId).val()) + (parseInt($("#partNoOfQuestion").val()) * 1);
	var question = {};
	question.randomizedTopicDesc = $("#partTopicDropDown option:selected").text();
	question.randomizedTopicId= $("#partTopicDropDown").val();
	question.randomizedTopicId = $("#partTopicDropDown").val();
	question.randomizedDifficultyDesc = $("#partDifficultyDropDown option:selected").text();
	question.randomizedDifficultyId = $("#partDifficultyDropDown").val();
	question.noOfQuestions = $("#partNoOfQuestion").val();
	question.questionTypeId = $("#hiddenQuestionTypeId_"+sectionId).val();
	question.randomizedQuestionTypeId = $("#hiddenQuestionTypeId_"+sectionId).val();
	question.sectionId = sectionId;
	question.partId = partCounter;
	question.totalScore = totalScore;
	return question;
}

function isPartFieldsEmpty() {
	result = false;
	if($("#partTopicDropDown").val() == null) {
		alertify.warning("Please choose a topic.");
		result = true;
	}
	if($("#partDifficultyDropDown").val() == null) {
		alertify.warning("Please choose a difficulty level.");
		result = true;
	}
	if($("#partNoOfQuestion").val() == "") {
		alertify.warning("Please choose specify a number of questions to be randomize.");
		result = true;
	}
	return result;
}

function clearPartModalFields() {
	$("#partTopicDropDown").val("0").trigger("change");
	$("#partDifficultyDropDown").val("0").trigger("change");
	$("#partNoOfQuestion").val("");
}

function attachListenerToSaveExamBtn() {
	$("#saveExamBtn").on("click",function() {
		var examInfo = getExamInfoDetails();
		examInfo.examQuestions = getExamQuestions();
		if(validateExamFields()) {
			if($("#action").val() == "create") {
				createExam(examInfo);
			} else {
				updateExam(examInfo);
			}
		}
	});
}

function createExam(examInfo) {
	$.when(ajax.create("examinfo",examInfo)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully Created!");
				if($("#courseId").val() == "" && $("#classId").val() == "") {
					setTimeout(function() {redirect("admin.examinations");},3000);
				} else {
					var module=$("#module").val();
					var courseId=$("#courseId").val();
					var classId=$("#classId").val();
					redirectToPage(module,courseId,classId);
				}
				break;
			case HttpStatus.EXAMCODE_EXISTS:
				alertify.warning(Message.EXAMCODE_EXISTS);
				break;
		}
	});
}

function updateExam(examInfo) {
	var examId=$("#examId").val();
	examInfo.examId = examId;
	$.when(ajax.update("examinfo/",examId,examInfo)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully updated exam.");
				if($("#courseId").val() != "" || $("#classId").val() != "") {
					var module=$("#module").val();
					var courseId=$("#courseId").val();
					var classId=$("#classId").val();
					redirectToPage(module,courseId,classId);
				}
				break;
			case HttpStatus.EXAMCODE_EXISTS:
				alertify.warning(Message.EXAMCODE_EXISTS);
				break;
		}
	});
}

function validateExamFields() {
	var finalRandomizeList=(deletedItems.length != 0)?filterSelectedItems(randomizedQuestionsList):
		randomizedQuestionsList;
	var finalNonRandomizeList=(deletedItems.length != 0)?filterSelectedItems(nonRandomizeQuestionList):
		nonRandomizeQuestionList;
	var finalSectionList=filterCreatedSections(sectionList);
	var result = true;
	if($("#examCode").val()=="") {
		alertify.warning("Exam code cannot be empty.");
		result = false;
	}
	if($("#examTitle").val()=="") {
		alertify.warning("Exam title cannot be empty.");
		result=false;
	}
	if($("#examTypeDropdown").val()==null) {
		alertify.warning("Please choose an exam type");
		result=false;
	}
	if(finalRandomizeList.length==0 && finalNonRandomizeList==0) {
		alertify.warning("Exam must contain atleast 1 section with 1 question.");
		result=false;
	}
	if(finalSectionList.length != 0) {
		alertify.warning("Please add at least 1 question on highlighted sections.");
		result=false;
	}
	$(".requiredLabel").css("visibility","hidden");
	highlightEmptySection(finalSectionList);
	return result;
}

function highlightEmptySection(finalSectionList) {
	_.each(finalSectionList,function(section) {
		$("#required_"+section.sectionId).css("visibility","visible");
		highlightDomValidation("#sectionNameLbl_"+section.sectionId);
		highlightDomValidation("#questionTypeLbl_"+section.sectionId);
	});
}

function getExamInfoDetails() {
	var examInfo = {};
	examInfo.examCode = $("#examCode").val();
	examInfo.examType = $("#examTypeDropdown").val();
	examInfo.title = $("#examTitle").val();
	examInfo.description = getContent("texteditor");
	examInfo.status = getCheckBoxValue("#examStatus");
	examInfo.allowAttachment = getCheckBoxValue("#allowAttachment");
	examInfo.courseId = $("#courseId").val();
	examInfo.classId = $("#classId").val();
	return examInfo;
}

function getExamQuestions() {
	var examQuestions = [];
	var finalRandomizeList=(deletedItems.length != 0)?filterSelectedItems(randomizedQuestionsList):randomizedQuestionsList;
	_.each(finalRandomizeList,function(question) {
		var examQuestion = {};
		var sectionId = question.sectionId;
		examQuestion.sectionName = $("#sectionName_"+sectionId).val();
		examQuestion.sectionOrder = sectionId;
		examQuestion.instruction = getContent("sectioneditor_"+sectionId);
		examQuestion.isRandomized = getCheckBoxValue("#sectionIsRandomized_"+sectionId);
		examQuestion.randomizedQuestionTypeId = parseInt(question.randomizedQuestionTypeId);
		examQuestion.questionTypeId = parseInt(question.questionTypeId);
		examQuestion.isShuffle = getCheckBoxValue("#sectionIsShuffle_"+sectionId);
		examQuestion.randomizedTopicId = parseInt(question.randomizedTopicId);
		examQuestion.randomizedDifficultyId = parseInt(question.randomizedDifficultyId);
		examQuestion.noOfQuestions = question.noOfQuestions;
		examQuestion.weight = $("#partWeight_"+question.partId).val();
		examQuestions.push(examQuestion);
	});
	var finalNonRandomizeList=(deletedItems.length != 0)?filterSelectedItems(nonRandomizeQuestionList):
		nonRandomizeQuestionList;
	_.each(finalNonRandomizeList,function(question) {
		var examQuestion = {};
		var sectionId = question.sectionId;
		examQuestion.sectionName = $("#sectionName_"+sectionId).val();
		examQuestion.sectionOrder = sectionId;
		examQuestion.instruction = getContent("sectioneditor_"+sectionId);
		examQuestion.isRandomized = getCheckBoxValue("#sectionIsRandomized_"+sectionId);
		examQuestion.isShuffle = getCheckBoxValue("#sectionIsShuffle_"+sectionId);
		examQuestion.questionId = question.questionId;
		examQuestion.weight = $("#partWeight_"+question.partId).val();
		examQuestions.push(examQuestion);
	});
	return examQuestions;
}

function filterSelectedItems(ArrayList) {
	var resultArray = [];
	_.each(ArrayList,function(questionObj) {
		if($.inArray(questionObj.sectionId,deletedItems)<0) {
			resultArray.push(questionObj);
		}
	});
	return resultArray;
}

function filterCreatedSections(ArrayList) {
	var resultArray = [];
	_.each(ArrayList,function(sectionObj) {
		if($.inArray(sectionObj.sectionId,deletedSections)<0) {
			resultArray.push(sectionObj);
		}
	});
	return resultArray;
}

function populatePartTable(question) {
	$("#sectionTblBody_"+question.sectionId).append($("<tr/>")
		.append($("<td/>").addClass("min")
			.append($("<a/>").attr({"data-target":"partPreviewModal"}).addClass("modal-trigger waves-effect waves-light")
				.append($("<i/>").addClass("material-icons").html("pageview")))
			.append($("<a/>").attr({"href":"#!","data-partid":question.partId,"data-sectionid":question.sectionId})
			.addClass("waves-effect waves-light removePartLink")
				.append($("<i/>").addClass("material-icons").html("delete"))))
		.append($("<td/>").addClass("mid").html(question.randomizedTopicDesc))
		.append($("<td/>").addClass("mid").html(question.randomizedDifficultyDesc))
		.append($("<td/>").addClass("mid").html(question.noOfQuestions))
		.append($("<td/>").addClass("mins").append($("<input/>").addClass("numbersOnly").css({"height":"22px","margin-bottom":"0","text-align":"center"})
			.attr({"type":"text",
				"id":"partWeight_"+question.partId,
				"value":(question.weight == undefined)?"1":question.weight,
				"data-partid":question.partId,
				"data-sectionid":question.sectionId,
				"data-noofquestions":question.noOfQuestions})
			.bind("keyup",function() {
				if($(this).val()=="") {$(this).val("0");}
				var oldTotalScore = computeOldTotalScore($(this).data("sectionid"),$(this).data("partid"));
				computeTotalScore(oldTotalScore,$(this).data("sectionid"),$(this).data("noofquestions"),$(this).val());
			}))));
	allowNumbersOnly();
}

function rePopulatePartTbl(sectionId,partId) {
	randomizedQuestionsList = deleteSelectedItem(sectionId,partId);
	$("#sectionTblBody_"+sectionId).html("");
	_.each(randomizedQuestionsList,function(question) {
		if(question.sectionId == sectionId) {
			populatePartTable(question);
		}
	}); 
}

function deleteSelectedItem(sectionId,partId) {
	var resultArray = [];
	_.each(randomizedQuestionsList,function(questionObj) {
		if(partId != questionObj.partId) {
			questionObj.weight = $("#partWeight_"+questionObj.partId).val();
			resultArray.push(questionObj);
		}
	});
	return resultArray;
}

function reComputeTotalScoreUponDelete(sectionId) {
	var tempTotalScore = 0;
	_.each(randomizedQuestionsList,function(questionObj) {
		if(questionObj.sectionId == sectionId) {
			tempTotalScore = tempTotalScore + (questionObj.noOfQuestions*parseInt($("#partWeight_"+questionObj.partId).val()));
		}
	});
	$("#totalScore_"+sectionId).html("");
	$("#totalScore_"+sectionId).html(tempTotalScore);
	$("#hiddenTotalScore_"+sectionId).val(tempTotalScore);
}

function computeTotalScore(totalScore,sectionId,noOfQuestion,weight) {
	totalScore = totalScore + (noOfQuestion*weight);
	$("#totalScore_"+sectionId).html("");
	$("#totalScore_"+sectionId).html(totalScore);
	$("#hiddenTotalScore_"+sectionId).val(totalScore);
}

function computeOldTotalScore(sectionId,partId) {
	var tempTotalScore = 0;
	_.each(randomizedQuestionsList,function(question) {
		if(question.sectionId == sectionId && question.partId != partId) {
			tempTotalScore = tempTotalScore + (question.noOfQuestions*parseInt($("#partWeight_"+question.partId).val()));
		}
	});
	return tempTotalScore;
}

function attachListenerToRandomizeSwitch() {
	$("#isRandomized").on("change",function() {
		if($(this).prop("checked") == true) {
			if($("#isShuffle").prop("checked") == true) {
				$("#isShuffle").prop("checked",false);
			}
			$("#questionTypeDiv").removeAttr("style");
		} else {
			$("#questionTypeDiv").css("display","none");
		}
	});
}

function attachListenerToShuffleSwitch() {
	$("#isShuffle").on("change",function() {
		if($("#isRandomized").prop("checked") == true) {
			$("#isRandomized").prop("checked",false);
		}
		$("#questionTypeDiv").css("display","none");
	});
}

function attachListenerToAddQuestionsBtn() {
	$("#AddQuestionBtn").on("click",function() {
		if(nonRandomizeCheckedList.length != 0) {
			deletedSections.push(parseInt($("#existingSectionValue").val()));
			nonRandomizeQuestionList = deleteSectionItemsToList($("#existingSectionValue").val());
			_.each(nonRandomizeCheckedList,function(question) {
				question.sectionId=$("#existingSectionValue").val();
				question.partId=partCounter++;
				nonRandomizeQuestionList.push(question);
			});
			nonRandomizeCheckedList = [];
			$("#sectionTblBody_"+$("#existingSectionValue").val()).html("");
			_.each(nonRandomizeQuestionList,function(question) {
				if(question.sectionId == $("#existingSectionValue").val()) {
					populateNonRandomizeSectionTbl(question);
				}
			});
			computeNonRandomizeTotalScore($("#existingSectionValue").val());
			$("#addExistingModal").modal("close");
		} else {
			alertify.warning("Please select at least one question before saving.");
		}
		
	});
}
function deleteSectionItemsToList(sectionId) {
	var tempArray = [];
	_.each(nonRandomizeQuestionList,function(question) {
		if(question.sectionId != sectionId) {
			tempArray.push(question);
		}
	});
	return tempArray;
}

function computeNonRandomizeTotalScore(sectionId) {
	var totalScore = 0;
	_.each(nonRandomizeQuestionList,function(question) {
		if(question.sectionId==sectionId) {
			totalScore = totalScore + parseInt($("#partWeight_"+question.partId).val());
		}
	});
	$("#totalScore_"+sectionId).html("");
	$("#totalScore_"+sectionId).html(totalScore);
}

function populateNonRandomizeSectionTbl(question) {
	$("#sectionTblBody_"+question.sectionId).append($("<tr/>")
		.append($("<td/>").addClass("min")
			.append($("<a/>").attr("data-target","partPreviewModal").addClass("modal-trigger waves-effect waves-light")
				.append($("<i/>").addClass("material-icons").html("pageview")))
			.append($("<a/>").attr({"href":"#!","data-sectionid":question.sectionId,"data-partid":question.partId})
					.addClass("modal-trigger waves-effect waves-light deleteNonRandomize")
				.append($("<i/>").addClass("material-icons").html("delete "))))
		.append($("<td/>").addClass("mid").html(question.label))
		.append($("<td/>").addClass("mid").html(question.questionTypeDesc))
		.append($("<td/>").addClass("mins").append($("<input/>").addClass("nonRandomizeWeight numbersOnly").css({"height":"22px","margin-bottom":"0","text-align":"center"})
			.attr({"type":"text","data-partid":question.partId,"id":"partWeight_"+question.partId,
				"data-sectionid":question.sectionId,"value":(question.weight!=undefined)?question.weight:"1"})
				.css("text-align","center"))));
	allowNumbersOnly();
}

function attachListenerToSearchQuestionsTxtBox() {
	$("#searchQuestionTxtBox").on("click",function() {
		initQuestionsTbl();
	})
}

function attachListenerToAddExistingBtn() {
	$("body").on("click",".addExisting",function() {
		var sectionId = $(this).data("sectionid");
		_.each(nonRandomizeQuestionList,function(question) {
			if(sectionId == question.sectionId) {
				nonRandomizeCheckedList.push(question);
			}
		});
		initQuestionsTbl();
	});
}

function attachListenerToSelectAllChkBox() {
	$("#selectAllCheckBox").on("change",function() {
		var sectionId = $("#existingSectionValue").val();
		if($(this).prop("checked")) {
			$(".checkbox_"+sectionId).prop("checked",true).trigger('change');
		} else {
			$(".checkbox_"+sectionId).prop("checked",false).trigger('change');
		}
	});
}

function attachListenerToNonRandomizeDelete() {
	$("body").on("click",".deleteNonRandomize",function() {
		var sectionId=$(this).data("sectionid");
		var partId=$(this).data("partid");
		nonRandomizeQuestionList=deleteItemFromNonRandomizeList(sectionId,partId);
		computeNonRandomizeTotalScore(sectionId);
		deletedSections=removeItemFromDeleteSectionList(sectionId);
		$("#sectionTblBody_"+sectionId).html("");
		_.each(nonRandomizeQuestionList,function(question) {
			if(question.sectionId == sectionId) {
				populateNonRandomizeSectionTbl(question);
			}
		});
	});
}

function deleteItemFromNonRandomizeList(sectionId,partId) {
	var tempArray = [];
	_.each(nonRandomizeQuestionList,function(question) {
		if(question.partId != partId) {
			tempArray.push(question);
		}
	});
	return tempArray;
}

function attachListenerToNonRandomizeWeightTxt() {
	$("body").on("keyup",".nonRandomizeWeight",function() {
		var sectionId=$(this).data("sectionid");
		var partId=$(this).data("partid");
		updateNonRandomizeListWeightValue(sectionId,partId,$(this).val());
		computeNonRandomizeTotalScore(sectionId);
	});
}

function updateNonRandomizeListWeightValue(sectionId,partId,value) {
	_.each(nonRandomizeQuestionList,function(question) {
		if(question.sectionId==sectionId && question.partId==partId) {
			question.weight = value;
		}
	});
}

function attachListenerToBtnCancel() {
	$("#btnCancel").on("click",function() {
		nonRandomizeCheckedList = [];
	});
}

function attachListenerToCancelExamBtn() {
	$("#cancelExamBtn").on("click",function() {
		var module=$("#module").val();
		var courseId=$("#courseId").val();
		var classId=$("#classId").val();
		redirectToPage(module,courseId,classId);
	});
}

function redirectToPage(module,courseId,classId) {
	switch(module) {
		case Module.COURSEEXAM:
			redirect("admin.courseexamination?courseId="+courseId,500);
			break;
		case Module.CLASSEXAM:
			redirect("admin.classexam?classId="+classId,500);
			break;
		default:
			redirect("admin.examinations",500);
			break;
	}
}

function attachListenerToDeletePartLink() {
	$("body").on("click",".removePartLink",function() {
		rePopulatePartTbl($(this).data("sectionid"),$(this).data("partid"));
		reComputeTotalScoreUponDelete($(this).data("sectionid"));
		deletedSections=removeItemFromDeleteSectionList($(this).data("sectionid"));
	});
}

function removeItemFromDeleteSectionList(sectionId) {
	var tempArray=[];
	_.each(deletedSections,function(sectionObjId) {
		if(sectionObjId!=sectionId) {
			tempArray.push(sectionObjId);
		}
	});
	return tempArray;
}