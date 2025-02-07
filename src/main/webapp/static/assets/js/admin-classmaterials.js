$(function(){
	defineVariables();
	initPageSettings();
	initContTypeDropdown();
	initClassMaterialsTbl();
	initCourseMaterialsTbl();
	initClassInfo();
	attachListenerToCancelBtn();
	attachListenerToContentTypeDropdown();
	attachListenerToDeleteMaterialsButton();
	attachListenerToSaveMaterialButton();
});

function defineVariables() {
	checkedLists = [];
}

function initContTypeDropdown() {
	_.each(ContentType.LIST,function(contentType) {
		$("#contentTypeDropdown").append($("<option/>").attr({"value" : contentType.id})
			.html(contentType.description));
	});
}

function initClassInfo() {
	if($("#deliveryMethod").val() == DeliveryMethod.INDIVIDUAL){
		$("#deliveryMethodLabel").html("Individual");
	} else if($("#deliveryMethod").val() == DeliveryMethod.MODULAR){
		$("#deliveryMethodLabel").html("Modular");
	} else if($("#deliveryMethod").val() == DeliveryMethod.CLASSTRAINING){
		$("#deliveryMethodLabel").html("Class Training");
	}
}

function initClassMaterialsTbl() {
	var classId = parseInt(GetURLParameter('classId'));
	pagination.initiate({
    	url: "classinfo/classmaterial/pages",
    	locator: "classMaterials",
    	paginationDom: "#classMaterialTblPagination",
    	tableBodyDom: "#classMaterialTblBody",
    	className: "paginationjs-theme-blue",
    	pageSize: 5,
    	ajax:{data:{classId:classId},type:"GET"},
    	functionName: "createClassMaterialsRow"
    });
}

function initCourseMaterialsTbl() {
	var courseId = $("#tbCourseId").val();
	console.log(courseId);
	pagination.initiate({
    	url: "courseinfo/coursematerial/pages",
    	locator: "courseMaterials",
    	paginationDom: "#courseMaterialTblPagination",
    	tableBodyDom: "#courseMaterialTblBody",
    	className: "paginationjs-theme-blue",
    	pageSize: 5,
    	ajax:{data:{courseId:courseId},type:"GET"},
    	functionName: "createCourseMaterialsRow"
    });
}

function initPreloader(status) {
	if(status=="show") {
		$("#materialsModalBtn").hide();
		$("#progressCon").show();
	} else {
		$("#materialsModalBtn").show();
		$("#preloader").hide();
		$("#progressCon").hide();
	}
}

function initPageSettings() {
	$("#urlDiv").hide();
	$("#preloader").hide();
}

function createCourseMaterialsRow(courseMaterial) {
	var visibility = "";
	if(courseMaterial.viewStatus == Status.ACTIVE){
		visibility = $("<i/>").attr("class","material-icons").html("visibility");
	} else if(courseMaterial.viewStatus == Status.INACTIVE){
		visibility = $("<i/>").attr("class","material-icons").html("visibility_off");
	}
	
	$("#courseMaterialTblBody").append($("<tr/>")
		.append($("<td/>")
					.append($("<span/>").attr("style","padding:10px;"))
					.append($("<a/>").attr("href","#!")
						.append($("<i/>").attr("class","material-icons").html("file_download"))))
		.append($("<td/>").html(courseMaterial.fileLabel))
		.append($("<td/>").html(getContentTypeDesc(courseMaterial.contentType)))
		.append($("<td/>").html(courseMaterial.fileName))
		.append($("<td/>")
		.append(visibility)));
}

function createClassMaterialsRow(classMaterial) {
	console.log(classMaterial);
	var visibility = "";
	if(classMaterial.viewStatus == Status.ACTIVE){
		visibility = $("<i/>").attr("class","material-icons").html("visibility");
	} else if(classMaterial.viewStatus == Status.INACTIVE){
		visibility = $("<i/>").attr("class","material-icons").html("visibility_off");
	}
	
	$("#classMaterialTblBody").append($("<tr/>")
		.append($("<td/>")
			.append($("<label/>").attr("style","margin-left:10px !important;")
				.append($("<input/>").attr({"type":"checkbox"})
				.prop("checked",checkIfClassMaterialExist(checkedLists,classMaterial))
				.bind("change",function() {
					if($(this).prop("checked") != true) {
						if(checkIfClassMaterialExist(checkedLists,classMaterial)) {
							deleteClassMaterialFromCheckList(classMaterial);
						}
					} else {
						if(!checkIfClassMaterialExist(checkedLists,classMaterial)) {
							checkedLists.push(classMaterial);
						}
					}
					console.log(checkedLists);
				}))
				.append($("<span/>").attr("style","padding:10px;")))
				.append($("<a/>").attr("href","#!")
					.append($("<i/>").attr("class","material-icons").html("file_download"))))
		.append($("<td/>").html(classMaterial.fileLabel))
		.append($("<td/>").html(getContentTypeDesc(classMaterial.contentType)))
		.append($("<td/>").html(classMaterial.fileName))
		.append($("<td/>")
			.append($("<a/>").attr({"data-id":classMaterial.classMaterialId,"data-status":classMaterial.viewStatus})
			.bind("click",function(){updateStatus($(this).data("id"),$(this).data("status"));})
				.append(visibility))));
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

function checkIfClassMaterialExist(classMaterials,classMaterial) {
	var result = false;
	_.each(classMaterials,function(cmObj) {
		if(cmObj.classMaterialId == classMaterial.classMaterialId) {
			result = true;
		}
	});
	return result;
}

function deleteClassMaterialFromCheckList(classMaterial) {
	var result = false;
	_.each(checkedLists,function(cmObj) {
		if(cmObj.classMaterialId == classMaterial.classMaterialId) {
			checkedLists = deleteItem(classMaterial);
		}
	});
	return result;
}

function deleteItem(classMaterial) {
	var resultArray = [];
	_.each(checkedLists,function(cmObj) {
		if(cmObj.classMaterialId != classMaterial.classMaterialId) {
			resultArray.push(cmObj);
		}
	});
	return resultArray;
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

function attachListenerToDeleteMaterialsButton() {
	$("#btnDeleteMaterials").on("click",function() {
		$("#btnDeleteMaterials").attr("disabled","disabled");
		$("#btnDeleteMaterials").html("Deleting Materials...");
		if(checkedLists.length == 0){
			alertify.dismissAll();
			alertify.warning("Please select at least 1 class material to delete.");
			$("#btnDeleteMaterials").removeAttr("disabled");
			$("#btnDeleteMaterials").html("<i class='material-icons left'>delete</i>Delete Materials");
		} else {
			alertify.dismissAll();
			deleteClassMaterials();
		}
	});
}

function attachListenerToSaveMaterialButton() {
	$("#saveClassMaterials").on("click",function() {
		$("#saveClassMaterials").attr("disabled","disabled");
		$("#saveClassMaterials").html("Saving...");
		var classMaterials = getClassMaterialsData();
		alertify.dismissAll();
		if(validateClassMaterialFields()) {
			createClassMaterial(classMaterials);
		} else {
			$("#saveClassMaterials").removeAttr("disabled");
			$("#saveClassMaterials").html("<i class='material-icons left'>save</i>Save");
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

function getClassMaterialsData() {
	var classMaterials = {};
	classMaterials.classId = parseInt($("#classId").val());
	classMaterials.contentType = parseInt($("#contentTypeDropdown").val());
	classMaterials.contentUrl = $("#urlTextBox").val();
	classMaterials.viewStatus = getCheckBoxValue("#viewStatus");
	classMaterials.fileLabel = $("#fileLabel").val();
	return classMaterials;
}

function validateClassMaterialFields() {
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
		alertify.warning("Please specify a URL.");
		result = false;
	}
	if($("#fileLabel").val() == "") {
		alertify.warning("Please specify a file label.");
		result = false;
	}
	return result;
}

function clearMaterialModalFields() {
	$("#contentTypeDropdown").val(0).trigger("change");
	$("#urlTextBox").val("");
	$("#viewStatus").prop("checked","");
	$("#fileLabel").val("");
	$("#fileupload").val("");
}

function createClassMaterial(classMaterial) {
	console.log(classMaterial);
	$.when(ajax.create("classinfo/classmaterial",classMaterial)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				if(classMaterial.contentType!=ContentType.URL) {
					uploadMediaFile(response.classMaterialId);
				} else{
					alertify.success("Successfully created!");
					setTimeout(function(){$("#learningMaterialModal").modal("close");},1000);
					initClassMaterialsTbl();
					clearMaterialModalFields();
					$(window).unbind('beforeunload');
				}
				break;
		}
	});
}

function uploadMediaFile(classMaterialId) {
	initPreloader("show");
	$.when(ajax.uploadWithProgressHandler("classinfo/classmaterial/"+classMaterialId+"/mediaFile","mediaUploadFrm"))
		.done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully created!");
				setTimeout(function(){
					initPreloader("hide");
					$("#learningMaterialModal").modal("close");},3000);
				initClassMaterialsTbl();
				clearMaterialModalFields();
				$(window).unbind('beforeunload');
				break;
			case HttpStatus.FILE_FORMAT_IS_INVALID:
				initPreloader("hide");
				alertify.warning("File format is invalid. Please try another one.");
				break;
			case HttpStatus.FILE_SIZE_IS_INVALID:
				initPreloader("hide");
				alertify.warning("File size is invalid.");
				break;
		}
	});
}

function updateStatus(classMaterialId,viewStatus) {
	console.log(classMaterialId+"--"+viewStatus);
	var classMaterial = {};
	classMaterial.classMaterialId = parseInt(classMaterialId);
	classMaterial.viewStatus = (viewStatus==Status.ACTIVE)?Status.INACTIVE:Status.ACTIVE;
	$.when(ajax.customUpdate("classinfo/classmaterial/"+classMaterialId+"/viewstatus",classMaterial)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				initClassMaterialsTbl();
				
				break;
		}
	});
}

function deleteClassMaterials() {
	$.when(ajax.customUpdate("classinfo/classmaterial/deleteclassmaterials",checkedLists)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully Deleted.");
				$("#btnDeleteMaterials").removeAttr("disabled");
				$("#btnDeleteMaterials").html("<i class='material-icons left'>delete</i>Delete Materials");
				initClassMaterialsTbl();
				break;
		}
	});
}

