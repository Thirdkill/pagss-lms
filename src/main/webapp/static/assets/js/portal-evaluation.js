$(function(){
    initializeEvaluationBreadCrumbs();
    defineVariables();
    initializeEvaluationQuestions();
    submitActionListener();
});

function defineVariables() {
    var employeeAnswerList = [];
    var questionList = [];
}

function initializeEvaluationBreadCrumbs() {
    var classId = $("#classId").val();
    var courseId = $("#courseId").val();
    var courseEvaluationId = $("#courseEvaluationId").val();
    var classEvaluationId = $("#classEvaluationId").val();
    
    if (!courseEvaluationId) {
        $.when(ajax.fetch(`classinfo/fetchevaluationpath?classId=${classId}&classEvaluationId=${classEvaluationId}`))
            .done(function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
                    var classEvaluation = response.classevaluation;
                    $("#courseName").text(classEvaluation.courseName);
                    $("#className").text(classEvaluation.className);
                    $("#evaluationName").text(classEvaluation.title);
                    $("#evaluationName1").html(`<b>${classEvaluation.title}</b>`)
                    break;
			}
		});
    } else if (!classEvaluationId) {
        $.when(ajax.fetch(`classinfo/fetchcourseevaluationpath?classId=${classId}&courseEvaluationId=${courseEvaluationId}`))
            .done(function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
                    var courseEvaluation = response.coursevaluation;
                    $("#courseName").text(courseEvaluation.courseName);
                    $("#className").text(courseEvaluation.className);
                    $("#evaluationName").text(courseEvaluation.title);
                    $("#evaluationName1").html(`<b>${courseEvaluation.title}</b>`)
                    break;
			}
		});
    } else {
        alertify("not working");
    }
}

function initializeEvaluationQuestions() {
    var courseEvaluationId = $("#courseEvaluationId").val();
    var classEvaluationId = $("#classEvaluationId").val();
    var evaluationId = !courseEvaluationId ? classEvaluationId : courseEvaluationId;
    
    $.when(ajax.fetch(`evaluations/questions/${evaluationId}`)).done(function(response) {
        switch(response.status) {
            case HttpStatus.SUCCESS:
                questionList = response.evaluationquestions;
                createQuestions(questionList);
                break;
        }
    });
}

function createQuestions(questions) {
    var lastSectionNo = 0;
    var scales = [];
    var classId = $("#classId").val();

    var lastIndexNo = 1;

    _.each(questions, function(qt, index){
        if (qt.sectionOrder !== lastSectionNo) {
            scales = [];
            lastIndexNo = 1;
            if (qt.evaluationType === "Scale") {
                for (var i = qt.scaleMin; i <= qt.scaleMax; i++){
                    scales.push(i);
                }
                scales =scales.reverse();
                $("#questionList").append($("<div/>").addClass("row")
                    .append($("<input/>").attr({"type":"radio", "name":"asd"}))
	                .append($("<div/>").addClass("divcard")
	                		.append($(`<legend style='margin-bottomL: 10px'><b>${qt.sectionName}</b></legend>`))
	                        .append($("<span/>").text("Instructions"))
                            .append($($.parseHTML(qt.instruction)).attr({"id":"instruction_"+index}))
                            .append($("<table/>").addClass("striped highlight").attr({"id":"scaleTbl_"+qt.sectionOrder})
                                .append($("<thead/>")
                                    .append($("<tr/>")
                                        .append($(`<th colspan='6' id='tableColSpan_${index}' class='large'></th>`).html("Questions"))
                                    )
                                )
                                .append($("<tbody/>")
                                    .append($("<tr/>")
                                        .append($(`<td colspan='6' id='questionStarts_${index}' class='large' style='text-align: left;'>${lastIndexNo}. ${qt.questionDesc}</td>`))
                                    )
                                )
                            )
	                )
                )
                lastIndexNo++;
                _.each(scales, function(scale){
                    $(`#tableColSpan_${index}`).after($("<th/>").addClass("min").text(scale))
                });
                _.each(scales, function(scale){
                    $(`#questionStarts_${index}`).after($("<td/>").addClass("min")
                        .append($("<label/>")
                            .append($("<input/>").attr({
                                "type":"radio",
                                "name":"question_"+qt.evaluationQuestionId,
                                "value":scale
                            }))
                            .append($("<span/>"))
                        )
                    )
                });
            } else if (qt.evaluationType === "Free Form") {
                $("#questionList").append($("<div/>").addClass("row")
	                .append($("<div/>").addClass("divcard")
                        .append($(`<legend style='margin-bottom: 10px'><b>${qt.sectionName}</b></legend>`))
                        .append($("<span/>").text("Instructions"))
                        .append($($.parseHTML(qt.instruction)))
                        .append($("<div/>").addClass("row").css({"padding": "15px"})
                            .append($("<b/>").text("Questions"))
                            .append($("<p/>").text(qt.questionDesc))
                            .append($("<div/>").attr({"id":"evalAnswer_"+qt.evaluationQuestionId,"class":"txtarea"}))
                            .append($("<div/>").attr({"id":"nextValue_"+index}))
                        )
	                )
	            )
            }
        } else {
            if (qt.evaluationType === "Scale") {
                $("#scaleTbl_"+qt.sectionOrder).find('tbody')
                .append($("<tr/>")
                    .append($(`<td colspan='6' id='questionStarts_${index}' class='large' style='text-align: left;'>${lastIndexNo}. ${qt.questionDesc}</td>`))
                    
                )
                _.each(scales, function(scale){
                    $(`#questionStarts_${index}`).after($("<td/>").addClass("min")
                        .append($("<label/>")
                            .append($("<input/>").attr({
                                "type":"radio",
                                "name":"question_"+qt.evaluationQuestionId,
                                "value":scale
                            })) 
                            .append($("<span/>"))
                        )
                    )
                });
            } else if (qt.evaluationType === "Free Form") {
                var lastIndex = index - 1;
                $("#nextValue_"+lastIndex).append($("<b>Question</b>"))
                .append($("<p/>").text(qt.questionDesc))
                .append($("<div/>").attr({"id":"evalAnswer_"+qt.evaluationQuestionId,"class":"txtarea"}))
                .append($("<div/>").attr({"id":"nextValue_"+index}))
            }
        }


        lastSectionNo=qt.sectionOrder;
    });

    var courseId = $("#courseId").val();
    var courseEvaluationId = $("#courseEvaluationId").val();
    var classEvaluationId = $("#classEvaluationId").val();

    if (!classEvaluationId) {
        $.when(ajax.fetch(`courseinfo/employeeAnswer/list?classId=${classId}&evaluationId=${courseEvaluationId}&courseId=${courseId}`)).done(function(response) {
            switch(response.status) {
                case HttpStatus.SUCCESS:
                    var userAnswer = response.employeeevaluations
                        if (userAnswer.length > 0) {
                            _.each(userAnswer, function(user){
                                if ($.isNumeric(user.answer)) {
                                    $(`input[name=question_${user.evaluationQuestionId}][value='${user.answer}']`).prop("checked",true);
                                    $(`input[name=question_${user.evaluationQuestionId}]`).attr("disabled", "disabled");
                                } else if (user.evaluationType === "Free Form") {
                                    initTinyMceEditorReadOnly(".txtarea",300);
                                    setContent("evalAnswer_" + user.evaluationQuestionId, user.answer);
                                }
                            });
                        } else {
                            initTinyMceEditor(".txtarea" , 300);
                        }
                    break;
            }
        });
    } else if (!courseEvaluationId) {
        $.when(ajax.fetch(`classInfo/employeeAnswer/list?classId=${classId}&evaluationId=${classEvaluationId}`)).done(function(response) {
            switch(response.status) {
                case HttpStatus.SUCCESS:
                    var userAnswer = response.employeeevaluations
                    if (userAnswer.length > 0) {
                        _.each(userAnswer, function(user){
                            if ($.isNumeric(user.answer)) {
                                $(`input[name=question_${user.evaluationQuestionId}][value='${user.answer}']`).prop("checked",true);
                                $(`input[name=question_${user.evaluationQuestionId}]`).attr("disabled", "disabled");
                            } else if (user.evaluationType === "Free Form") {
                                initTinyMceEditorReadOnly(".txtarea",300);
                                $("#evalAnswer_" + user.evaluationQuestionId).attr("disabled", "disabled")
                                setContent("evalAnswer_" + user.evaluationQuestionId, user.answer);
                                 
                            }
                        });
                    } else {
                        initTinyMceEditor(".txtarea" , 300);
                    }
                break;
            }
        });
    } else {
        
    }

    
}

function submitActionListener(){
    
    $("#submitForm").on("click", function(){

        var classId = $("#classId").val();
        employeeAnswerList = [];
       _.each(questionList, function(question){
            if (question.evaluationType === "Scale") {
                var answerValue = $(`input[name='question_${question.evaluationQuestionId}']:checked`).val();
                var employeeAnswer = populateEmployeeAnswerData(question, answerValue);
               employeeAnswerList.push(employeeAnswer);
            } else if (question.evaluationType === "Free Form") {
                var answerValue = getContent("evalAnswer_"+question.evaluationQuestionId);
                var employeeAnswer = populateEmployeeAnswerData(question, answerValue);
                employeeAnswerList.push(employeeAnswer);
            }
            lastSectionNo = question.sectionOrder;
       });
       $.when(ajax.create("classinfo/insertemployeeanswer",employeeAnswerList)).done(function(response) {
            if (response.status === HttpStatus.SUCCESS) {
                alertify.success("Submitted Evaluation!");
                setTimeout(function(){redirect("preview-class?classId="+classId,0);}, 3000);
            }
       });
    });
}

function populateEmployeeAnswerData(employeeQuestion, answerValue) {
    
    var requestClassId = $("#classId").val();
    var employeeAnswer =  {};
    employeeAnswer.classId = requestClassId;
    employeeAnswer.answer = answerValue;
    employeeAnswer.evaluationId = employeeQuestion.evaluationId;
    employeeAnswer.evaluationQuestionId = employeeQuestion.evaluationQuestionId;

    return employeeAnswer;
}
