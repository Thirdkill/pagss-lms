$(function() {
	initPageSettings();
});
function initPageSettings() {
	$("#examType").html(getExamTypeDesc($("#hdnExamType").val()));
}

function getExamTypeDesc(examType) {
	var examTypeDesc="";
	_.each(ExamTypes,function(examTypeObj) {
		if(examTypeObj.id==examType) {
			examTypeDesc=examTypeObj.description;
		}
	});
	return examTypeDesc;
}