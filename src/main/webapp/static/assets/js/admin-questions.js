$(function() {
	initTopicDropDown();
	initDifficultyLevelDropDown();
	initQuestionTypeDropDown();
	initQuestionsTbl();
	attachListenerToSearchQuestionBtn();
	attachListenerToSortLink();
	attachListenerToResetBtn();
});

function initTopicDropDown() {
	$.when(ajax.fetch("trainingtopics")).done(function(response) {
		populateTopicDropDown(response.trainingTopics);
	});
}

function populateTopicDropDown(trainingTopics) {
	_.each(trainingTopics,function(trainingTopic) {
		$("#topicDropDown").append($("<option/>").attr({"value" : trainingTopic.topicId})
				.html(trainingTopic.topicDesc));
	});
}

function initDifficultyLevelDropDown() {
	$.when(ajax.fetch("difficultylevels")).done(function(response) {
		populateDIfficultyDropDown(response.difficultyLevels);
	});
}

function populateDIfficultyDropDown(difficultyLevels) {
	_.each(difficultyLevels,function(difficultyLevel) {
		$("#difficultyDropDown").append($("<option/>").attr({"value" : difficultyLevel.difficultyId})
				.html(difficultyLevel.difficultyName));
	});
}

function initQuestionTypeDropDown() {
	$.when(ajax.fetch("questiontypes")).done(function(response) {
		populateQuestionTypeDropDown(response.questionTypes);
	});
}

function populateQuestionTypeDropDown(questionTypes) {
	_.each(questionTypes,function(questionType) {
		$("#questionTypeDropDown").append($("<option/>").attr({"value" : questionType.questionTypeId})
				.html(questionType.questionTypeDesc));
	});
}

function initQuestionsTbl() {
	pagination.initiate({
    	url: "questions/pages",
    	locator: "questions",
    	paginationDom: "#questionsPagination",
    	tableBodyDom: "#questionsTblBody",
    	className: "paginationjs-theme-blue",
    	pageSize: 10,
    	ajax:{data:{
    		questionType:$("#questionTypeDropDown").val(),
    		topic:$("#topicDropDown").val(),
    		difficultyLevel:$("#difficultyDropDown").val(),
    		status: $("#statusDropDown").val(),
    		sortName:"createdAt",
    		sortDir:"ASC"
    		},type:"POST"},
    	functionName: "createQuestionsRow"
    });
}

function createQuestionsRow(question) {
	$("#questionsTblBody").append($("<tr/>")
		.append($("<td/>").addClass("min")
			.append($("<a/>").addClass("waves-effect waves-light").attr("href","#!")
				.append($("<i/>").addClass("material-icons").html("pageview")))
			.append($("<a/>").addClass("waves-effect waves-light").attr("href","admin.question?action=update&questionId="+question.questionId)
				.append($("<i/>").addClass("material-icons").html("edit"))))
		.append($("<td/>").addClass("mid").html(question.label))
		.append($("<td/>").addClass("mid").html(question.topicDesc))
		.append($("<td/>").addClass("mid").html(question.difficultyName))
		.append($("<td/>").addClass("mid").html(question.questionTypeDesc))
		.append($("<td/>").addClass("mid")
			.append($("<a/>").addClass("waves-effect waves-light")
				.append($("<i/>").addClass("material-icons").html((question.status != 0)? "check":"close"))))
	)
}

function attachListenerToSearchQuestionBtn() {
	$("body").on("click","#searchQuestionBtn",function() {
		pagination.initiate({
	    	url: "questions/pages",
	    	locator: "questions",
	    	paginationDom: "#questionsPagination",
	    	tableBodyDom: "#questionsTblBody",
	    	className: "paginationjs-theme-blue",
	    	pageSize: 10,
	    	ajax:{data:{
	    		questionType:$("#questionTypeDropDown").val(),
	    		topic:$("#topicDropDown").val(),
	    		difficultyLevel:$("#difficultyDropDown").val(),
	    		status: $("#statusDropDown").val(),
	    		sortName:"createdAt",
	    		sortDir:"ASC"
	    		},type:"POST"},
	    	functionName: "createQuestionsRow"
	    });
	});
}

function attachListenerToSortLink() {
	$("body").on("click",".sort",function(e) {
		e.preventDefault();
		pagination.initiate({
	    	url: "questions/pages",
	    	locator: "questions",
	    	paginationDom: "#questionsPagination",
	    	tableBodyDom: "#questionsTblBody",
	    	className: "paginationjs-theme-blue",
	    	pageSize: 10,
	    	ajax:{data:{
	    		questionType:$("#questionTypeDropDown").val(),
	    		topic:$("#topicDropDown").val(),
	    		difficultyLevel:$("#difficultyDropDown").val(),
	    		status: $("#statusDropDown").val(),
	    		sortName:$(this).data("sortname"),
	    		sortDir:$(this).data("sortdir")
	    		},type:"POST"},
	    	functionName: "createQuestionsRow"
	    });
		return false;
	});
}

function attachListenerToResetBtn() {
	$("body").on("click","#resetBtn",function() {
		$("#questionTypeDropDown").val("0").trigger("change");
		$("#topicDropDown").val("0").trigger("change");
		$("#difficultyDropDown").val("0").trigger("change");
		$("#statusDropDown").val("2").trigger("change");
		initQuestionsTbl();
	});
}