$(function() {
	defineVariables();
	initPageSettings();
	initCourseDetails();
	initContTypeDropdown();
	initCourseMaterialsTbl();
	attachListenerToSaveMaterialsBtn();
	attachListenerToContentTypeDropdown();
	attachListenerToCheckBox();
	attachListenerToDeleteBtn();
	attachListenerToCancelBtn();
});
function defineVariables() {
	checkedList = [];
}

function initPageSettings() {
	$("#urlDiv").hide();
	$("#preloader").hide();
}

function initCourseDetails() {
	var courseId = parseInt($("#courseId").val());
	$.when(ajax.fetchObj("courseinfo/",courseId)).done(function(response) {
		var courseInfo = response.courseInfo;
		$("#courseNameHeader").text(courseInfo.courseName);
	});
}

function initContTypeDropdown() {
	_.each(ContentType.LIST,function(contentType) {
		$("#contentTypeDropdown").append($("<option/>").attr({"value" : contentType.id})
			.html(contentType.description));
	});
}

function initCourseMaterialsTbl() {
	var courseId = parseInt($("#courseId").val());
	pagination.initiate({
    	url: "courseinfo/coursematerial/pages",
    	locator: "courseMaterials",
    	paginationDom: "#courseMaterialsPagination",
    	tableBodyDom: "#courseMaterialsTblBody",
    	className: "paginationjs-theme-blue",
    	pageSize: 5,
    	ajax:{data:{courseId:courseId},type:"GET"},
    	functionName: "createCourseMaterialsRow"
    });
}

function createCourseMaterialsRow(courseMaterial) {
	$("#courseMaterialsTblBody").append($("<tr/>")
		.append($("<td/>")
			.append($("<label/>")
				.append($("<input/>").addClass("materialChkBox").attr({
					"type":"checkbox","data-id":courseMaterial.courseMaterialId})
				.prop("checked",checkIfExist(courseMaterial.courseMaterialId)))
				.append($("<span/>").addClass("checkStyle").css("padding-left","20px !important")))
			.append($("<a/>").attr({"href":"#!"}).addClass("waves-effect waves-light")
				.append($("<i/>").addClass("material-icons").html("file_download"))))
		.append($("<td/>").html(courseMaterial.fileLabel))
		.append($("<td/>").html(getContentTypeDesc(courseMaterial.contentType)))
		.append($("<td/>").html((courseMaterial.contentType!=ContentType.URL)?courseMaterial.fileName:courseMaterial.contentUrl))
		.append($("<a/>").attr({"href":"#!","data-id":courseMaterial.courseMaterialId,"data-status":courseMaterial.viewStatus})
			.addClass("waves-effect waves-light").bind("click",function() {
				updateStatus($(this).data("id"),$(this).data("status"));
			}).append($("<i/>").addClass("material-icons")
				.html((courseMaterial.viewStatus==0)?"visibility_off":"remove_red_eye").css("margin-top","10px"))));
}

function updateStatus(courseMaterialId,viewStatus) {
	var courseMaterial = {};
	courseMaterial.courseMaterialId = parseInt(courseMaterialId);
	courseMaterial.viewStatus = (viewStatus==Status.ACTIVE)?Status.INACTIVE:Status.ACTIVE;
	$.when(ajax.customUpdate("courseinfo/coursematerial/"+courseMaterialId+"/viewstatus",courseMaterial)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				initCourseMaterialsTbl();
				break;
		}
	});
}

function checkIfExist(courseMaterialId) {
	var result=false;
	_.each(checkedList,function(courseMaterial) {
		if(courseMaterial.courseMaterialId==courseMaterialId) {
			result=true;
		}
	});
	return result;
}

function getCourseMaterialObj(courseMaterialId) {
	var courseMaterial = {};
	_.each(globalList,function(object) {
		if(object.courseMaterialId==courseMaterialId) {
			courseMaterial=object;
		}
	});
	return courseMaterial;
}

function removeCheckedListObject(courseMaterialId) {
	var tempCheckedList=[];
	_.each(checkedList,function(courseMaterial) {
		if(courseMaterial.courseMaterialId != courseMaterialId) {
			tempCheckedList.push(courseMaterial);
		}
	});
	return tempCheckedList;
}

function getContentTypeDesc(contentTypeValue) {
	var result = "";
	_.each(ContentType.LIST,function(contentType) {
		if(contentTypeValue==contentType.id) {
			result=contentType.description;
		}
	});
	return result;
}

function attachListenerToSaveMaterialsBtn() {
	$("#saveCourseMaterials").on("click",function() {
		handleRefreshEvent();
		var courseMaterial = getCourseMaterialsData();
		if(validateCourseMaterialFields()) {
			if(courseMaterial.contentType!=ContentType.URL) {
				createCourseMaterialFile(courseMaterial);
			} else {
				createCourseMaterial(courseMaterial);
			}
		}
	});
}

function getCourseMaterialsData() {
	var courseMaterial = {};
	courseMaterial.courseId = $("#courseId").val();
	courseMaterial.contentType = $("#contentTypeDropdown").val();
	courseMaterial.contentUrl = $("#urlTextBox").val();
	courseMaterial.viewStatus = getCheckBoxValue("#viewStatus");
	courseMaterial.fileLabel = $("#fileLabel").val();
	return courseMaterial;
}

function createCourseMaterial(courseMaterial) {
	$.when(ajax.create("courseinfo/coursematerial/contenturl",courseMaterial)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully created!");
				setTimeout(function(){$("#learningMaterialModal").modal("close");},1000);
				initCourseMaterialsTbl();
				clearMaterialModalFields();
				$(window).unbind('beforeunload');
				break;
		}
	});
}

function createCourseMaterialFile(courseMaterial) {
	var courseId=$("#courseId").val();
	initPreloader("show");
	$.when(ajax.uploadWithProgressHandler("courseinfo/"+courseId+"/coursematerial/file","mediaUploadFrm")
		.done(function(response) {
			switch(response.status) {
				case HttpStatus.SUCCESS:
					courseMaterial.courseMaterialId=response.courseMaterialId;
					updateCourseMaterial(courseMaterial);
					initPreloader("hide");
					break;
			}
	}));
}

function updateCourseMaterial(courseMaterial) {
	$.when(ajax.customUpdate(
		"courseinfo/"+courseMaterial.courseId+"/coursematerial/"+courseMaterial.courseMaterialId,courseMaterial)
			.done(function(response) {
				switch(response.status) {
					case HttpStatus.SUCCESS:
							alertify.success("Successfully created!");
							setTimeout(function(){$("#learningMaterialModal").modal("close");},1000);
							initCourseMaterialsTbl();
							clearMaterialModalFields();
							$(window).unbind('beforeunload');
						break;
				}
	}));
}

function validateCourseMaterialFields() {
	var result = true;
	if($("#contentTypeDropdown").val()==null) {
		alertify.warning("Please choose a content type.");
		result = false;
	}
	if($("#contentTypeDropdown").val()!=ContentType.URL && $("#fileupload").val()=="") {
		alertify.warning("Please choose a file to upload.");
		result = false;
	}
	if($("#contentTypeDropdown").val()==ContentType.URL && $("#urlTextBox").val()=="") {
		alertify.warning("Please specify a url.");
		result = false;
	}
	if($("#fileLabel").val() == "") {
		alertify.warning("Please specify a file label.");
		result = false;
	}
	if(!validateFileType("#mediaFileUpload",getTargetFileType($("#contentTypeDropdown").val()))) {
		alertify.warning("Invalid file type. Please try another one");
		result=false;
	}
	if(!validateFileSize("#mediaFileUpload",getTargetFileType($("#contentTypeDropdown").val()))) {
		result=false;
	}
	return result;
}

function getTargetFileType(contentType) {
	var targetFileType="";
	if(contentType==ContentType.DOCUMENT) {
		targetFileType="DOCUMENT";
	} else if(contentType==ContentType.IMAGE) {
		targetFileType="IMAGE";
	} else if(contentType==ContentType.VIDEO) {
		targetFileType="VIDEO";
	} else if(contentType==ContentType.AUDIO) {
		targetFileType="AUDIO";
	}
	return targetFileType;
}

function attachListenerToContentTypeDropdown() {
	$("#contentTypeDropdown").on("change",function() {
		if($(this).val()==ContentType.URL) {
			$("#urlDiv").show();
			$("#mediaUploadDiv").hide();
		} else {
			$("#urlDiv").hide();
			$("#mediaUploadDiv").show();
		}
	});
}

function initPreloader(status) {
	if(status=="show") {
		$("#materialsModalBtn").hide();
//		$("#preloader").show();
		$("#progressCon").show();
	} else {
		$("#materialsModalBtn").show();
		$("#preloader").hide();
		$("#progressCon").hide();
	}
}

function clearMaterialModalFields() {
	$("#contentTypeDropdown").val(0).trigger("change");
	$("#urlTextBox").val("");
	$("#viewStatus").prop("checked","");
	$("#fileLabel").val("");
	$("#fileupload").val("");
}

function attachListenerToCheckBox() {
	$("body").on("change",".materialChkBox",function() {
		var courseMaterialId=$(this).data("id");
		if($(this).prop("checked")){checkedList.push(getCourseMaterialObj(courseMaterialId))}
		else{checkedList=removeCheckedListObject(courseMaterialId)}
	});
}

function attachListenerToDeleteBtn() {	
	$("#deleteBtn").on("click",function() {
		if(checkedList.length!=0) {
			var message = "Are you sure you want to delete these learning materials?";
			alertify.confirm("Delete Learning Materials", message, function(){ 
				deleteCourseMaterials();
				checkedList = [];
			}, function(){}).set("labels",{ok:"Yes", cancel:"No"});
		}
	});
}

function deleteCourseMaterials() {
	var courseInfo = {};
	courseInfo.courseMaterials=checkedList;
	blockUI("Removing Files..");
	$.when(ajax.removeWithData("courseinfo/coursematerials",courseInfo))
		.done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully removed!");
				initCourseMaterialsTbl();
				$.unblockUI();
				break;
		}
	});
}

function attachListenerToCancelBtn(){
	$("#cancelBtn").on("click",function() {
		$("#contentTypeDropdown").val(0).trigger("change");
		$("#fileLabel").val("");
		$("#fileupload").val("");
		$("#urlTextBox").val("");
		$("#viewStatus").prop("checked","");
	});
}