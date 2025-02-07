/**
 * 
 */

$(function(){
	initEvaluationTbl();
	initSavedEvaluationTbl();
	defineGlobalList();
	attachListenerToSearchBox();
	attachListenerToAddEvaluation();
	attachListenerToAddCourseEvaluation();
});

function defineGlobalList() {
	evalAvailable = [];
}

function initEvaluationTbl() {
	var pageSize = 5;
	var courseId = GetURLParameter('courseId');
    $("#evaluationPagination").pagination({     
        dataSource: "evaluations/pages/courseevaluation/"+courseId,
        locator: 'evaluations',
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        className: 'paginationjs-theme-blue',
        pageSize: pageSize,     
        callback: function(data) {            
            $("#evaluationTblBody").empty(); 
			_.each(data, function(evaluations) {
				createEvaluationRow(evaluations, courseId);
            });   
        }
    });
}

function initSavedEvaluationTbl() {
	var courseId = GetURLParameter('courseId');
	$.when(ajax.fetch("courseinfo/"+courseId+"/courseevaluations/list")).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var courseEvaluations = response.courseevaluations;
			$("#savedEvaluationTblBody").empty();
			_.each(courseEvaluations, function(courseEvaluation) {
				createSavedEvaluationRow(courseEvaluation);
            }); 
		}
	}); 
}

function searchEvaluationTbl(keyword) {
	var pageSize = 5;
	var courseId = GetURLParameter('courseId');
	$("#evaluationPagination").pagination({
		dataSource: "evaluations/search/courseevaluation/"+courseId,
		locator: 'evaluations',
		totalNumberLocator: function(response) {            
			return response.totalRecords;
		},
		ajax: {data: {keyword : keyword}, type: "POST"},
		className: 'paginationjs-theme-blue',
		pageSize: pageSize,     
		callback: function(data) {         
			$("#evaluationTblBody").empty(); 
			_.each(data, function(evaluations) {
				createEvaluationRow(evaluations, courseId);
            }); 
		}
	});
}

function createEvaluationRow(evaluation, courseId) {
	$("#evaluationTblBody").append($("<tr/>")
		.append($("<td/>").attr({"class":"min","data-id":evaluation.evaluationId})
		.append($("<label/>")
		.append($("<input/>").attr("type","checkbox")
		.prop("checked",checkIfEvaluationExist(evalAvailable,evaluation))
		.bind("change",function() {
			if($(this).prop("checked") != true) {
				if(checkIfEvaluationExist(evalAvailable,evaluation)) {
					deleteEvaluationFromCheckList(evaluation);
				}
			} else if(!checkIfEvaluationExist(evalAvailable, evaluation)) {
				evaluation.courseId = parseInt(courseId);
				evalAvailable.push(evaluation);
			}
		}))
		.append($("<span/>").attr({"class":"checkStyle","style":"padding-left: 20px !important;"}))))
		.append($("<td/>").attr({"style":"text-align: left;"}).html(evaluation.title)));
}

function createSavedEvaluationRow(evaluation) {
	$("#savedEvaluationTblBody").append($("<tr/>").attr("id","savedEval_"+evaluation.evaluationId)
		.append($("<td/>").attr({"class":"min","data-id":evaluation.evaluationId})
		.append($("<a/>").attr({"class":"preventEdit","href":"admin.updateevaluation?action=EDIT&id="+evaluation.evaluationId+"&courseId="+evaluation.courseId})
		.append($("<i/>").addClass("material-icons").html("edit")))
		.append($("<a/>").attr({"style":"cursor:pointer;","data-id":evaluation.courseEvaluationId})
		.bind("click",function() {
			var currectEvalId = parseInt($(this).data("id"));
			$(this).css("pointer-events","none");
			$(".preventEdit").css("pointer-events","none");
			alertify.message("Removing "+evaluation.title+"...");
			setTimeout(function(){
				$.when(ajax.remove("courseinfo/courseevaluation/",currectEvalId)).done(function(response) {
					if(response.status == HttpStatus.SUCCESS){
						$("#savedEval_"+evaluation.evaluationId).remove();
						$(".preventEdit").css("pointer-events","");
						$("#searchEvalTxtbox").val(null);
						initEvaluationTbl();
						initSavedEvaluationTbl();
						evalAvailable = [];
					}
				});
			}, 1500);
		})
		.append($("<i/>").addClass("material-icons").html("delete"))))
		.append($("<td/>").attr({"style":"text-align:left;"}).html(evaluation.title)));
}


function attachListenerToSearchBox() {
	$("#btnReset").unbind("click").on("click", function() {
		$("#searchEvalTxtbox").val(null);
		initEvaluationTbl();
	});
	
	$("#searchEvalTxtbox").on("keyup",function(e) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == 13) {
			var keyword = $(this).val();
			searchEvaluationTbl(keyword);
		}
		
		if(keycode == 8 && $("#searchEvalTxtbox").val().length == 0) {
			initEvaluationTbl();
		}
		return true;
	});
}

function attachListenerToAddEvaluation() {
	var courseId = GetURLParameter('courseId');
	$("#createEvaluationBtn").unbind("click").on("click", function() {
		redirect("admin.createevaluation?courseId="+courseId,0);
	});
}

function attachListenerToAddCourseEvaluation() {
	var courseId = GetURLParameter('courseId');
	$("#addEvaluationBtn").unbind("click").on("click", function() {
		$("#addEvaluationBtn").attr("disabled","disabled");
		$("#addEvaluationBtn").html("ADDING...");
		if(evalAvailable.length == 0){
			alertify.dismissAll();
			alertify.warning("Please add at least 1 evaluation.");
			$("#addEvaluationBtn").removeAttr("disabled");
			$("#addEvaluationBtn").html("<i class='material-icons left'>add_circle</i>ADD");
		} else {
			$.when(ajax.update("courseinfo/courseevaluations/",courseId,evalAvailable)).done(function(response) {
				if(response.status == HttpStatus.SUCCESS){
					$("#searchEvalTxtbox").val(null);
					initEvaluationTbl();
					initSavedEvaluationTbl();
					evalAvailable = [];
					alertify.dismissAll();
					alertify.success("Successfully Added.");
					$("#addEvaluationBtn").removeAttr("disabled");
					$("#addEvaluationBtn").html("<i class='material-icons left'>add_circle</i>ADD");
				}
			});
		}
	});
}

function checkIfEvaluationExist(evals,evaluation) {
	var result = false;
	_.each(evals,function(evalObj) {
		if(evalObj.evaluationId == evaluation.evaluationId) {
			result = true;
		}
	});
	return result;
}

function deleteEvaluationFromCheckList(evaluation) {
	var result = false;
	evList = evalAvailable;
	_.each(evList,function(evalObj) {
		if(evalObj.evaluationId == evaluation.evaluationId) {
			evalAvailable = deleteItem(evaluation);
		}
	});
	return result;
}

function deleteItem(evaluation) {
	var resultArray = [];
	_.each(evalAvailable,function(evalObj) {
		if(evalObj.evaluationId != evaluation.evaluationId) {
			resultArray.push(evalObj);
		}
	});
	return resultArray;
}