/**
 * 
 */
$(function(){
	defineList();
	initClassInfo();
	attachListenerOnCertificate();
	attachListenerOnSelfRegistration();
	attachListenerOnViewingRestrictions();
	attachListenerOnAccessRestrictions();
	attachListenerToAddClassIpAddressBtn();
	attachListenerToAddClassIpAddressModalBtn();
	attachListenerToSaveSettingsBtn();
});

function defineList() {
	checkIpAddress = [];
}

function initClassInfo() {
	if($("#deliveryMethod").val() == DeliveryMethod.INDIVIDUAL){
		$("#modularDisplay").css("display","");
		$("#deliveryMethodLabel").html("Individual");
	} else if($("#deliveryMethod").val() == DeliveryMethod.MODULAR){
		$("#modularDisplay").css("display","");
		$("#deliveryMethodLabel").html("Modular");
	} else if($("#deliveryMethod").val() == DeliveryMethod.CLASSTRAINING){
		$("#modularDisplay").css("display","none");
		$("#deliveryMethodLabel").html("Class Training");
	}
	
	if($("#deliveryMethod").val() == DeliveryMethod.INDIVIDUAL){
		$("#modularDisplay").css("display","");
		$("#deliveryMethodLabel").html("Individual");
	} else if($("#deliveryMethod").val() == DeliveryMethod.MODULAR){
		$("#modularDisplay").css("display","");
		$("#deliveryMethodLabel").html("Modular");
	}
	
	if($("#chWithCertificate").attr("data-withcertificate") == WithCertificate.TRUE){
		$("#chWithCertificate").prop("checked",true).trigger("change");
		$("#withCertificate").css("display","");
		if($("#withCertificate").attr("data-template") == CertificateType.SYSTEMTEMPLATE) {
			$("#raSystemTemplate").prop("checked",true);
			$("#customUploadDiv").css("display","none");
		} else if($("#withCertificate").attr("data-template") == CertificateType.CUSTOMTEMPLATE) {
			$("#raCustomTemplate").prop("checked",true);
			$("#customUploadDiv").css("display","");
		}
		
		if($("#isDownloadable").attr("data-downloadable") == CertificateDownloadable.TRUE){
			$("#chIsDownloadable").prop("checked",true).trigger("change");
		} else if($("#isDownloadable").attr("data-downloadable") == CertificateDownloadable.FALSE){
			$("#chIsDownloadable").prop("checked",false).trigger("change");
		}
	} else {
		$("#withCertificate").css("display","none");
		$("#customUploadDiv").css("display","none");
	}
	
	if($("#chIsSelfRegister").attr("data-selfregister") == IsSelfRegister.TRUE){
		$("#chIsSelfRegister").prop("checked",true).trigger("change");
		if($("#selfRegistrationDiv").attr("data-selfregistertype") == SelfRegisterType.OPENINDEFINITELY) {
			$("#raOpenIndefinite").prop("checked",true);
			$("#specifiedPeriodDiv").css("display","none");
		} else if($("#selfRegistrationDiv").attr("data-selfregistertype") == SelfRegisterType.SPECIFIEDPERIOD) {
			$("#raOpenSpecifiedPeriod").prop("checked",true);
			$("#specifiedPeriodDiv").css("display","");
		}
	} else {
		$("#selfRegistrationDiv").css("display","none");
		$("#specifiedPeriodDiv").css("display","none");
	}
	
	if($("#viewRestrictionDiv").attr("data-viewrestriction") == ViewRestrictions.TRAININGDURATION) {
		$("#raAccessTrainingDurationOnly").prop("checked",true);
		$("#specifiedRestrictionsDiv").css("display","none");
	} else if($("#viewRestrictionDiv").attr("data-viewrestriction") == ViewRestrictions.INDEFINITE) {
		$("#raAccessIndefinitely").prop("checked",true);
		$("#specifiedRestrictionsDiv").css("display","none");
	} else if($("#viewRestrictionDiv").attr("data-viewrestriction") == ViewRestrictions.SPECIFIEDPERIOD) {
		$("#raAccessSpecifiedPeriod").prop("checked",true);
		$("#specifiedRestrictionsDiv").css("display","");
	}
	
	if($("#accessRestrictionTypeDiv").attr("data-accesstype") == AccessRestrictionType.PUBLIC) {
		$("#raIpPublic").prop("checked",true);
		$("#btnClassIPModal").attr("disabled","disabled");
	} else if($("#accessRestrictionTypeDiv").attr("data-accesstype") == AccessRestrictionType.PRIVATE) {
		$("#raIpPrivate").prop("checked",true);
		$("#btnClassIPModal").removeAttr("disabled");
	}
}

function attachListenerOnCertificate() {
	$("#chWithCertificate").on("change",function() {
		if($(this).prop("checked") == true) {
			$("#chWithCertificate").attr("data-withcertificate",WithCertificate.TRUE);
			$("#withCertificate").css("display","");
			if($("#raSystemTemplate").prop("checked") != true) {
				$("#customUploadDiv").css("display","");
			} else {
				$("#customUploadDiv").css("display","none");
			}
		} else {
			$("#chWithCertificate").attr("data-withcertificate",WithCertificate.FALSE);
			$("#customUploadDiv").css("display","none");
			$("#withCertificate").css("display","none");
		}
	});
	
	$("#raSystemTemplate").on("change", function () {
		$("#withCertificate").attr("data-template", CertificateType.SYSTEMTEMPLATE);
		$("#customUploadDiv").css("display","none");
	});
	
	$("#raCustomTemplate").on("change", function () {
		$("#withCertificate").attr("data-template",CertificateType.CUSTOMTEMPLATE);
		$("#customUploadDiv").css("display","");
	});
	
	$("#chIsDownloadable").on("change", function () {
		if($("#chIsDownloadable").prop("checked") == true) {
			$("#isDownloadable").attr("data-downloadable",CertificateDownloadable.TRUE);
		} else {
			$("#isDownloadable").attr("data-downloadable",CertificateDownloadable.FALSE);
		}
	});
}

function attachListenerOnSelfRegistration() {
	$("#chIsSelfRegister").on("change",function() {
		if($(this).prop("checked") == true) {
			$("#chIsSelfRegister").attr("data-selfregister",IsSelfRegister.TRUE);
			$("#selfRegistrationDiv").css("display","");
			if($("#raOpenIndefinite").prop("checked") == true) {
				$("#specifiedPeriodDiv").css("display","none");
			} else if($("#raOpenSpecifiedPeriod").prop("checked") == true) {
				$("#specifiedPeriodDiv").css("display","");
			}
		} else {
			$("#chIsSelfRegister").attr("data-selfregister",IsSelfRegister.FALSE);
			$("#selfRegistrationDiv").css("display","none");
			$("#specifiedPeriodDiv").css("display","none");
		}
	});
	
	$("#raOpenIndefinite").on("change", function () {
		$("#selfRegistrationDiv").attr("data-selfregistertype",SelfRegisterType.OPENINDEFINITELY);
		$("#specifiedPeriodDiv").css("display","none");
	});
	
	$("#raOpenSpecifiedPeriod").on("change", function () {
		$("#selfRegistrationDiv").attr("data-selfregistertype",SelfRegisterType.SPECIFIEDPERIOD);
		$("#specifiedPeriodDiv").css("display","");
	});
}

function attachListenerOnViewingRestrictions() {
	$("#raAccessTrainingDurationOnly").on("change", function () {
		$("#viewRestrictionDiv").attr("data-viewrestriction",ViewRestrictions.TRAININGDURATION);
		$("#specifiedRestrictionsDiv").css("display","none");
	});
	
	$("#raAccessIndefinitely").on("change", function () {
		$("#viewRestrictionDiv").attr("data-viewrestriction",ViewRestrictions.INDEFINITE);
		$("#specifiedRestrictionsDiv").css("display","none");
	});
	
	$("#raAccessSpecifiedPeriod").on("change", function () {
		$("#viewRestrictionDiv").attr("data-viewrestriction",ViewRestrictions.SPECIFIEDPERIOD);
		$("#specifiedRestrictionsDiv").css("display","");
	});
}

function attachListenerOnAccessRestrictions() {
	$("#raIpPublic").on("change", function () {
		$("#accessRestrictionTypeDiv").attr("data-accesstype",AccessRestrictionType.PUBLIC);
		$("#btnClassIPModal").attr("disabled","disabled");
	});
	
	$("#raIpPrivate").on("change", function () {
		$("#accessRestrictionTypeDiv").attr("data-accesstype",AccessRestrictionType.PRIVATE);
		$("#btnClassIPModal").removeAttr("disabled");
	});
}

function initClassIPAddressTbl() {
	var classId = parseInt(GetURLParameter('classId'));
	var pageSize = 5;
	$("#classIpTblPagination").pagination({     
		dataSource: "classinfo/"+classId+"/classipaddress/pages",
		locator: 'classIpAddresses',
		totalNumberLocator: function(response) {            
			return response.totalRecords;
		},
		className: 'paginationjs-theme-blue',
		pageSize: pageSize,     
		callback: function(data) {                      
			$("#classIpTblBody").empty(); 
			_.each(data, function(classipaddress) {
				createClassIpAddressRow(classipaddress);
			});         
		}
	});
}

function initIPAddressTbl() {
	var classId = parseInt(GetURLParameter('classId'));
	var pageSize = 5;
	$("#ipaddressPagination").pagination({     
		dataSource: "ipaddresses/pages/class/"+classId,
		locator: 'ipaddresses',
		totalNumberLocator: function(response) {            
			return response.totalRecords;
		},
		className: 'paginationjs-theme-blue',
		pageSize: pageSize,     
		callback: function(data) {                      
			$("#ipaddressTblBody").empty(); 
			_.each(data, function(ipaddress) {
				createIpAddressRow(ipaddress);
			});         
		}
	});
}
function createClassIpAddressRow(classipaddress) {
	$("#classIpTblBody").append($("<tr/>")
		.append($("<td/>").addClass("mins")
			.append($("<a/>").addClass("waves-light waves-effect").attr({
				"href":"#!",
				"data-classipaddressid":classipaddress.classIpId,
				"data-classid":classipaddress.classId})
				.bind("click",function() {
					deleteClassIpAddress($(this).data("classipaddressid"));
				})
				.append($("<i/>").addClass("material-icons").html("delete"))))
		.append($("<td/>").attr("style","text-align:left;")
		.html(classipaddress.ipAddress)));
}

function createIpAddressRow(ipaddress) {
	var classId = parseInt(GetURLParameter('classId'));
	$("#ipaddressTblBody").append($("<tr/>")
		.append($("<td/>").addClass("mins")
			.append($("<label/>")
				.append($("<input/>").attr({"type":"checkbox"})
				.prop("checked",checkIfItemExist(ipaddress.ipAddressId))
				.bind("change",function() {
					if($(this).prop("checked") != true) {
						if(checkIfItemExist(ipaddress.ipAddressId)) {
							deleteClassIpFromCheckList(ipaddress);
						}
					} else {
						if(!checkIfItemExist(ipaddress.ipAddressId)) {
							ipaddress.classId = classId;
							checkIpAddress.push(ipaddress);
						}
					}
				}))
				.append($("<span/>"))))
		.append($("<td/>").attr("style","text-align:left;")
		.html(ipaddress.ipAddress)));
}

function checkIfItemExist(ipAddressId) {
	var result = false;
	_.each(checkIpAddress,function(ipAddressInfo) {
		if(ipAddressInfo.ipAddressId==ipAddressId) {
			result=true;
		}
	});
	return result;
}

function deleteClassIpFromCheckList(ipAddressInfo) {
	var result = false;
	_.each(checkIpAddress,function(ipObj) {
		if(ipObj.ipAddressId == ipAddressInfo.ipAddressId) {
			checkIpAddress = deleteItem(ipAddressInfo);
		}
	});
	return result;
}

function deleteItem(ipAddressInfo) {
	var resultArray = [];
	_.each(checkIpAddress,function(ipObj) {
		if(ipObj.ipAddressId != ipAddressInfo.ipAddressId) {
			resultArray.push(ipObj);
		}
	});
	return resultArray;
}

function attachListenerToAddClassIpAddressBtn() {
	$("#addClassIPAddressBtn").on("click",function() {
		if(checkIpAddress.length == 0) {
			alertify.warning("Please select at least one IP Address from the table.");
		} else {
			createClassIpAddress(checkIpAddress);
		}
	});
}

function attachListenerToSaveSettingsBtn() {
	$("#btnSaveSettings").on("click",function() {
		$("#btnSaveSettings").attr("disabled","disabled");
		$("#btnSaveSettings").html("Saving...");
		updateClassSettings();
	});
}

function populateClassSettings() {
	classinfo = {};
	
	classinfo.classId = parseInt(GetURLParameter('classId'));
	classinfo.classDuration = $("#tbClassDuration").val();
	classinfo.isSelfRegister = $("#chIsSelfRegister").attr("data-selfregister");
	classinfo.selfRegisterType = $("#selfRegistrationDiv").attr("data-selfregistertype");
	classinfo.selfRegisterStartDate = $("#tbSpecifiedStart").val();
	classinfo.selfRegisterEndDate = $("#tbSpecifiedEnd").val();
	classinfo.withCertificate = $("#chWithCertificate").attr("data-withcertificate");
	classinfo.certificateTemplateType = $("#withCertificate").attr("data-template");
	classinfo.certificateUrl = $("#fuCustomCertificate").val();
	classinfo.isCertificateDownloadable = $("#isDownloadable").attr("data-downloadable");
	classinfo.viewRestrictionType = $("#viewRestrictionDiv").attr("data-viewrestriction");
	classinfo.accessRestrictionType = $("#accessRestrictionTypeDiv").attr("data-accesstype");
	classinfo.accessStartDate = $("#tbAccessSpecifiedStart").val();
	classinfo.accessEndDate = $("#tbAccessSpecifiedEnd").val();
	
	return classinfo;
}

function attachListenerToAddClassIpAddressModalBtn() {
	$("#btnClassIPModal").on("click",function() {
		initClassIPAddressTbl();
		initIPAddressTbl();
	});
}

function updateClassSettings() {
	var classinfo = populateClassSettings();
	var classId = parseInt(GetURLParameter('classId'));

	$.when(ajax.customUpdate("classinfo/"+classinfo.classId+"/classsettings",classinfo)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully saved!");
				setTimeout(function(){ redirect("admin.classsettings?classId="+classinfo.classId); }, 3000);
		}
	});
}

function createClassIpAddress(classIpAddress) {
	var classId = parseInt(GetURLParameter('classId'));
	$.when(ajax.customUpdate("classinfo/"+classId+"/classipaddresses",classIpAddress)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				alertify.success("Successfully added!");
				defineList();
				initClassIPAddressTbl();
				initIPAddressTbl();
				break;
		}
	});
}

function deleteClassIpAddress(classIpId) {
	var classId = parseInt(GetURLParameter('classId'));
	$.when(ajax.remove("classinfo/"+classId+"/classipaddress/",classIpId)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				defineList();
				initClassIPAddressTbl();
				initIPAddressTbl();
				break;
		}
	});
}