/**
 * 
 */

$(function(){
	initEvaluationTbl();
	attachListenerToSearchBox();
});

function initEvaluationTbl() {
	var pageSize = 10;
    $("#evaluationPagination").pagination({     
        dataSource: "evaluations/pages",
        locator: 'evaluations',
        totalNumberLocator: function(response) {            
            return response.totalRecords;
        },
        className: 'paginationjs-theme-blue',
        pageSize: pageSize,     
        callback: function(data) {            
            $("#evaluationTblBody").empty(); 
            _.each(data, function(evaluations) {
            	createEvaluationRow(evaluations);
            });         
        }
    });
}

function createEvaluationRow(evaluations) {
	var evalStatus = checkStatus(evaluations.status);
	
	$("#evaluationTblBody").append($("<tr/>")
		.append($("<td/>").attr({"data-id":evaluations.evaluationId})
		.append($("<a/>").addClass("waves-effect waves-light")
		.append($("<i/>").addClass("material-icons").html("file_download")))
		.append($("<a/>").addClass("waves-effect waves-light")
			.attr("href","admin.updateevaluation?action=EDIT&id="+evaluations.evaluationId)
		.append($("<i/>").addClass("material-icons").html("edit"))))
		.append($("<td/>").html(evaluations.title))
		.append($("<td/>").html(evalStatus))
		.append($("<td/>").html(evaluations.lastModifiedAt))
		.append($("<td/>").html(evaluations.modifiedBy)));
}

function checkStatus(status) {
	return (status != 0)? "ACTIVE" : "INACTIVE"; 
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

function searchEvaluationTbl(keyword) {
	var pageSize = 10;
	$("#evaluationPagination").pagination({     
		dataSource: "evaluations/search",
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
            	createEvaluationRow(evaluations);
            });           
		}
	});
}