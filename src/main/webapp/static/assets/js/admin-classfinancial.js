/**
 * 
 */

$(function(){
	defineVariables();
	allowNumbersOnly();
	initClassInfo();
	initTrainerList();
	attachListenerToCreateClassFinancial();
	attachListenerToSaveFinancial();
	initFinancialList();
});

function defineVariables() {
	financialLists = [];
	financialNum = 1;
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

function initTrainerList() {
	$.when(ajax.fetch("employeeinfo/trainers")).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var trainers = response.employeeInfos;
			populateTrainerList(trainers);
		}
	}); 
}

function initFinancialList() {
	var classId = parseInt($("#classId").val());
	$.when(ajax.fetch("classinfo/classfinancial/"+classId+"/trainers/list")).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var trainers = response.trainerFinances;
			_.each(trainers,function(trainer) {
				populateExistingClassFinancial(trainer,"Trainer");
			});
			$.when(ajax.fetch("classinfo/classfinancial/"+classId+"/materials/list")).done(function(response) {
				if(response.status == HttpStatus.SUCCESS){
					var materials = response.materialFinances;
					_.each(materials,function(material) {
						populateExistingClassFinancial(material,"Material");
					});
					$.when(ajax.fetch("classinfo/classfinancial/"+classId+"/others/list")).done(function(response) {
						if(response.status == HttpStatus.SUCCESS){
							var others = response.otherFinances;
							_.each(others,function(other) {
								populateExistingClassFinancial(other,"Other");
							});
						}
					});
				}
			}); 
		}
	});
}

function populateTrainerList(trainers) {
	$("#trainerDropdown").html("");
	_.each(trainers,function(trainer) {
		$("#trainerDropdown").append($("<option/>").attr("value",trainer.employeeId).html(trainer.fullName));
	})
	$("#trainerDropdown").trigger("contentChanged");
}

function populateAddedTrainerList(financialNum,trainers) {
	$("#trainerDropdown_"+financialNum).html("");
	_.each(trainers,function(trainer) {
		$("#trainerDropdown_"+financialNum).append($("<option/>").attr("value",trainer.employeeId).html(trainer.fullName));
	})
	$("#trainerDropdown_"+financialNum).select2({placeholder: "Choose An Option"});
}

function attachFunctionToCalculateTotalCost(price, quantity) {
	var totalCost = parseFloat(price) * quantity;
	return totalCost.toFixed(2);
}

function attachListenerToCreateClassFinancial(){
	$("#addTrainerBtn").on("click", function(){
		financialList = {};
		financialList.financialId = financialNum;
		financialList.type = "Trainer";
		financialLists.push(financialList);
		createClassFinantialsRow(financialList);
	});
	$("#addMaterialBtn").on("click", function(){
		financialList = {};
		financialList.financialId = financialNum;
		financialList.type = "Material";
		financialLists.push(financialList);
		createClassFinantialsRow(financialList);
	});
	$("#addOtherBtn").on("click", function(){
		financialList = {};
		financialList.financialId = financialNum;
		financialList.type = "Other";
		financialLists.push(financialList);
		createClassFinantialsRow(financialList);
	});
}

function populateExistingClassFinancial(financialList, financialType){
	financialList.financialId = financialNum;
	if(financialType == "Trainer"){
		financialList.type = "Trainer";
	}else if(financialType == "Material"){
		financialList.type = "Material";
	}else if(financialType == "Other"){
		financialList.type = "Other";
	}
	financialLists.push(financialList);
	createExistingClassFinantialsRow(financialList);
	addTotalCost();
}

function createExistingClassFinantialsRow(financialList) {
	if(financialList.type == "Trainer"){
		$("#trainerTblBody").prepend($("<tr/>").attr("id","trainerFinancial_"+financialList.financialId)
			.append($("<td/>")
			.append($("<select/>").attr({"id":"trainerDropdown_"+financialList.financialId})
					.append($("<option/>"))))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbTrainerRate_"+financialList.financialId,
					"data-id":financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":financialList.trainerRate.toFixed(2),
					"step":"0.01",
					"min":"0"})
				.bind("keyup", function () {
					var currentNo = $(this).attr("data-id");
					var price = ($("#tbTrainerRate_"+currentNo).val() != "") ? $("#tbTrainerRate_"+currentNo).val() : 0;
					var quantity = parseInt($("#tbNoOfDays_"+currentNo).val());
					$("#tbTotalCost_"+currentNo).val(attachFunctionToCalculateTotalCost(price, quantity));
					addTotalCost();
				})))
			.append($("<td/>").html("x"))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbNoOfDays_"+financialList.financialId,
					"data-id":financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":financialList.noOfDays,
					"min":"1"})
				.bind("keyup", function () {
					var currentNo = $(this).attr("data-id");
					var price = ($("#tbTrainerRate_"+currentNo).val() != "") ? $("#tbTrainerRate_"+currentNo).val() : 0;
					var quantity = parseInt($("#tbNoOfDays_"+currentNo).val());
					$("#tbTotalCost_"+currentNo).val(attachFunctionToCalculateTotalCost(price, quantity));
					addTotalCost();
				})))
			.append($("<td/>").html("="))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbTotalCost_"+financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":financialList.totalCost.toFixed(2),
					"step":"0.01",
					"min":"0",
					"disabled":"disabled"})))
			.append($("<td/>").attr({"data-id":financialList.financialId}).html("<a href='#!'><i class='material-icons'>delete</i></a>")
					.bind("click", function(e) {
						var currentNo = $(this).attr("data-id");
						deleteFinancialFromList(currentNo);
						addTotalCost();
						$("#trainerFinancial_"+currentNo).remove();
					})));
		$.when(ajax.fetch("employeeinfo/trainers")).done(function(response) {
			if(response.status == HttpStatus.SUCCESS){
				var trainers = response.employeeInfos;
				populateAddedTrainerList(financialList.financialId,trainers);
				$("#trainerDropdown_"+financialList.financialId).val(financialList.employeeId).trigger("change");
			}
		});
		
	} else if(financialList.type == "Material"){
		$("#materialTblBody").prepend($("<tr/>").attr("id","materialFinancial_"+financialList.financialId)
			.append($("<td/>")
			.append($("<input/>").attr({"id":"tbMaterialName_"+financialList.financialId,"type":"text","value":financialList.name})))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbMaterialPrice_"+financialList.financialId,
					"data-id":financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":financialList.price.toFixed(2),
					"step":"0.01",
					"min":"0"})
				.bind("keyup", function () {
					var currentNo = $(this).attr("data-id");
					var price = ($("#tbMaterialPrice_"+currentNo).val() != "") ? $("#tbMaterialPrice_"+currentNo).val() : 0;
					var quantity = parseInt($("#tbMaterialQuantity_"+currentNo).val());
					$("#tbTotalCost_"+currentNo).val(attachFunctionToCalculateTotalCost(price, quantity));
					addTotalCost();
				})))
			.append($("<td/>").html("x"))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbMaterialQuantity_"+financialList.financialId,
					"data-id":financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":financialList.quantity,
					"min":"1"})
				.bind("keyup", function () {
					var currentNo = $(this).attr("data-id");
					var price = ($("#tbMaterialPrice_"+currentNo).val() != "") ? $("#tbMaterialPrice_"+currentNo).val() : 0;
					var quantity = parseInt($("#tbMaterialQuantity_"+currentNo).val());
					$("#tbTotalCost_"+currentNo).val(attachFunctionToCalculateTotalCost(price, quantity));
					addTotalCost();
				})))
			.append($("<td/>").html("="))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbTotalCost_"+financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":financialList.totalCost.toFixed(2),
					"step":"0.01",
					"min":"0",
					"disabled":"disabled"})))
			.append($("<td/>").attr({"data-id":financialList.financialId}).html("<a href='#!'><i class='material-icons'>delete</i></a>")
				.bind("click", function(e) {
					var currentNo = $(this).attr("data-id");
					deleteFinancialFromList(currentNo);
					addTotalCost();
					$("#materialFinancial_"+currentNo).remove();
				})));
	} else if(financialList.type == "Other"){
		$("#otherTblBody").prepend($("<tr/>").attr("id","otherFinancial_"+financialList.financialId)
			.append($("<td/>")
			.append($("<input/>").attr({"id":"tbOthersName_"+financialList.financialId,"type":"text","value":financialList.name})))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbOthersPrice_"+financialList.financialId,
					"data-id":financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":financialList.price.toFixed(2),
					"step":"0.01",
					"min":"0"})
				.bind("keyup", function () {
					var currentNo = $(this).attr("data-id");
					var price = ($("#tbOthersPrice_"+currentNo).val() != "") ? $("#tbOthersPrice_"+currentNo).val() : 0;
					var quantity = parseInt($("#tbOthersQuantity_"+currentNo).val());
					$("#tbTotalCost_"+currentNo).val(attachFunctionToCalculateTotalCost(price, quantity));
					addTotalCost();
				})))
					.append($("<td/>").html("x"))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbOthersQuantity_"+financialList.financialId,
					"data-id":financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":financialList.quantity,
					"min":"1"})
				.bind("keyup", function () {
					var currentNo = $(this).attr("data-id");
					var price = ($("#tbOthersPrice_"+currentNo).val() != "") ? $("#tbOthersPrice_"+currentNo).val() : 0;
					var quantity = parseInt($("#tbOthersQuantity_"+currentNo).val());
					$("#tbTotalCost_"+currentNo).val(attachFunctionToCalculateTotalCost(price, quantity));
					addTotalCost();
				})))
			.append($("<td/>").html("="))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbTotalCost_"+financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":financialList.totalCost.toFixed(2),
					"step":"0.01",
					"min":"0",
					"disabled":"disabled"})
				.bind("keyup", function () {
					var currentNo = $(this).attr("data-id");
					var price = ($("#tbTrainerRate_"+currentNo).val() != "") ? $("#tbTrainerRate_"+currentNo).val() : 0;
					var quantity = parseInt($("#tbNoOfDays_"+currentNo).val());
					$("#tbTotalCost_"+currentNo).val(attachFunctionToCalculateTotalCost(price, quantity));
					addTotalCost();
				})))
			.append($("<td/>").attr({"data-id":financialList.financialId}).html("<a href='#!'><i class='material-icons'>delete</i></a>")
				.bind("click", function(e) {
					var currentNo = $(this).attr("data-id");
					deleteFinancialFromList(currentNo);
					addTotalCost();
					$("#otherFinancial_"+currentNo).remove();
				})));
	}
	financialNum++;
}

function createClassFinantialsRow(financialList) {
	if(financialList.type == "Trainer"){
		$("#trainerTblBody").prepend($("<tr/>").attr("id","trainerFinancial_"+financialList.financialId)
			.append($("<td/>")
			.append($("<select/>").attr({"id":"trainerDropdown_"+financialList.financialId})
					.append($("<option/>"))))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbTrainerRate_"+financialList.financialId,
					"data-id":financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":"0.00",
					"step":"0.01",
					"min":"0"})
				.bind("keyup", function () {
					var currentNo = $(this).attr("data-id");
					var price = ($("#tbTrainerRate_"+currentNo).val() != "") ? $("#tbTrainerRate_"+currentNo).val() : 0;
					var quantity = parseInt($("#tbNoOfDays_"+currentNo).val());
					$("#tbTotalCost_"+currentNo).val(attachFunctionToCalculateTotalCost(price, quantity));
					addTotalCost();
				})))
			.append($("<td/>").html("x"))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbNoOfDays_"+financialList.financialId,
					"data-id":financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":"1",
					"min":"1"})
				.bind("keyup", function () {
					var currentNo = $(this).attr("data-id");
					var price = ($("#tbTrainerRate_"+currentNo).val() != "") ? $("#tbTrainerRate_"+currentNo).val() : 0;
					var quantity = parseInt($("#tbNoOfDays_"+currentNo).val());
					$("#tbTotalCost_"+currentNo).val(attachFunctionToCalculateTotalCost(price, quantity));
					addTotalCost();
				})))
			.append($("<td/>").html("="))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbTotalCost_"+financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":"0.00",
					"step":"0.01",
					"min":"0",
					"disabled":"disabled"})))
			.append($("<td/>").attr({"data-id":financialList.financialId}).html("<a href='#!'><i class='material-icons'>delete</i></a>")
					.bind("click", function(e) {
						var currentNo = $(this).attr("data-id");
						deleteFinancialFromList(currentNo);
						addTotalCost();
						$("#trainerFinancial_"+currentNo).remove();
					})));
		$.when(ajax.fetch("employeeinfo/trainers")).done(function(response) {
			if(response.status == HttpStatus.SUCCESS){
				var trainers = response.employeeInfos;
				populateAddedTrainerList(financialList.financialId,trainers);
			}
		});
		
	} else if(financialList.type == "Material"){
		$("#materialTblBody").prepend($("<tr/>").attr("id","materialFinancial_"+financialList.financialId)
			.append($("<td/>")
			.append($("<input/>").attr({"id":"tbMaterialName_"+financialList.financialId,"type":"text"})))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbMaterialPrice_"+financialList.financialId,
					"data-id":financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":"0.00",
					"step":"0.01",
					"min":"0"})
				.bind("keyup", function () {
					var currentNo = $(this).attr("data-id");
					var price = ($("#tbMaterialPrice_"+currentNo).val() != "") ? $("#tbMaterialPrice_"+currentNo).val() : 0;
					var quantity = parseInt($("#tbMaterialQuantity_"+currentNo).val());
					$("#tbTotalCost_"+currentNo).val(attachFunctionToCalculateTotalCost(price, quantity));
					addTotalCost();
				})))
			.append($("<td/>").html("x"))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbMaterialQuantity_"+financialList.financialId,
					"data-id":financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":"1",
					"min":"1"})
				.bind("keyup", function () {
					var currentNo = $(this).attr("data-id");
					var price = ($("#tbMaterialPrice_"+currentNo).val() != "") ? $("#tbMaterialPrice_"+currentNo).val() : 0;
					var quantity = parseInt($("#tbMaterialQuantity_"+currentNo).val());
					$("#tbTotalCost_"+currentNo).val(attachFunctionToCalculateTotalCost(price, quantity));
					addTotalCost();
				})))
			.append($("<td/>").html("="))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbTotalCost_"+financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":"0.00",
					"step":"0.01",
					"min":"0",
					"disabled":"disabled"})))
			.append($("<td/>").attr({"data-id":financialList.financialId}).html("<a href='#!'><i class='material-icons'>delete</i></a>")
				.bind("click", function(e) {
					var currentNo = $(this).attr("data-id");
					deleteFinancialFromList(currentNo);
					addTotalCost();
					$("#materialFinancial_"+currentNo).remove();
				})));
	} else if(financialList.type == "Other"){
		$("#otherTblBody").prepend($("<tr/>").attr("id","otherFinancial_"+financialList.financialId)
			.append($("<td/>")
			.append($("<input/>").attr({"id":"tbOthersName_"+financialList.financialId,"type":"text"})))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbOthersPrice_"+financialList.financialId,
					"data-id":financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":"0.00",
					"step":"0.01",
					"min":"0"})
				.bind("keyup", function () {
					var currentNo = $(this).attr("data-id");
					var price = ($("#tbOthersPrice_"+currentNo).val() != "") ? $("#tbOthersPrice_"+currentNo).val() : 0;
					var quantity = parseInt($("#tbOthersQuantity_"+currentNo).val());
					$("#tbTotalCost_"+currentNo).val(attachFunctionToCalculateTotalCost(price, quantity));
					addTotalCost();
				})))
					.append($("<td/>").html("x"))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbOthersQuantity_"+financialList.financialId,
					"data-id":financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":"1",
					"min":"1"})
				.bind("keyup", function () {
					var currentNo = $(this).attr("data-id");
					var price = ($("#tbOthersPrice_"+currentNo).val() != "") ? $("#tbOthersPrice_"+currentNo).val() : 0;
					var quantity = parseInt($("#tbOthersQuantity_"+currentNo).val());
					$("#tbTotalCost_"+currentNo).val(attachFunctionToCalculateTotalCost(price, quantity));
					addTotalCost();
				})))
			.append($("<td/>").html("="))
			.append($("<td/>")
			.append($("<input/>")
				.attr({"id":"tbTotalCost_"+financialList.financialId,
					"class":"numbersOnly",
					"type":"number",
					"value":"0.00",
					"step":"0.01",
					"min":"0",
					"disabled":"disabled"})
				.bind("keyup", function () {
					var currentNo = $(this).attr("data-id");
					var price = ($("#tbTrainerRate_"+currentNo).val() != "") ? $("#tbTrainerRate_"+currentNo).val() : 0;
					var quantity = parseInt($("#tbNoOfDays_"+currentNo).val());
					$("#tbTotalCost_"+currentNo).val(attachFunctionToCalculateTotalCost(price, quantity));
					addTotalCost();
				})))
			.append($("<td/>").attr({"data-id":financialList.financialId}).html("<a href='#!'><i class='material-icons'>delete</i></a>")
				.bind("click", function(e) {
					var currentNo = $(this).attr("data-id");
					deleteFinancialFromList(currentNo);
					addTotalCost();
					$("#otherFinancial_"+currentNo).remove();
				})));
	}
	financialNum++;
}

function addTotalCost() {
	totalCost = 0;
	_.each(financialLists,function(financialList) {
		totalCost = totalCost + parseFloat($("#tbTotalCost_"+financialList.financialId).val());
	});
	$("#tbFinalTotalCost").val(totalCost.toFixed(2));
}

function deleteFinancialFromList(financialId) {
	var result = false;
	_.each(financialLists,function(finObj) {
		if(finObj.financialId == financialId) {
			financialLists = deleteFinancial(financialId);
		}
	});
	return result;
}

function deleteFinancial(financialId) {
	var resultArray = [];
	_.each(financialLists,function(finObj) {
		if(finObj.financialId != financialId) {
			resultArray.push(finObj);
		}
	});
	return resultArray;
}

function attachListenerToSaveFinancial() {
	$("#btnSaveClassFinancial").unbind("click").click(function() {
		$("#btnSaveClassFinancial").attr("disabled","disabled");
		$("#btnSaveClassFinancial").html("Saving...");
		validateClassFinancialFields();
	});
}

function validateClassFinancialFields() {
	submitClassFinancial();
}

function submitClassFinancial() {
	var classId = parseInt($("#classId").val());
	var trainerFinancial = getFinancialFields("Trainer", financialLists);
	var materialFinancial = getFinancialFields("Material", financialLists);
	var otherFinancial = getFinancialFields("Other", financialLists);
	
	$.when(ajax.customUpdate("classinfo/classfinancial/"+classId+"/trainers",trainerFinancial)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				$.when(ajax.customUpdate("classinfo/classfinancial/"+classId+"/materials",materialFinancial)).done(function(response) {
					switch(response.status) {
						case HttpStatus.SUCCESS:
							$.when(ajax.customUpdate("classinfo/classfinancial/"+classId+"/others",otherFinancial)).done(function(response) {
								switch(response.status) {
									case HttpStatus.SUCCESS:
										defineVariables();
										$("#trainerTblBody").empty();
										$("#materialTblBody").empty();
										$("#otherTblBody").empty();
										initFinancialList();
										alertify.success("Successfully Updated.");
										$("#btnSaveClassFinancial").removeAttr("disabled");
										$("#btnSaveClassFinancial").html("<i class='material-icons left'>save</i>Save");
								}
							});
							break;
					}
				});
				break;
		}
	});
}

function getFinancialFields(financialType, financialLists) {
	finalFinancialLists = [];
	_.each(financialLists,function(finObj) {
		if(finObj.type == "Trainer" && financialType == "Trainer"){
			financialList = {};
			financialList.classId = parseInt($("#classId").val());
			financialList.employeeId = parseInt($("#trainerDropdown_"+finObj.financialId).val());
			financialList.noOfDays = parseInt($("#tbNoOfDays_"+finObj.financialId).val());
			financialList.trainerRate = parseFloat($("#tbTrainerRate_"+finObj.financialId).val());
			financialList.totalCost = parseFloat($("#tbTotalCost_"+finObj.financialId).val());
			finalFinancialLists.push(financialList);

		} else if(finObj.type == "Material" && financialType == "Material"){
			financialList = {};
			financialList.classId = parseInt($("#classId").val());
			financialList.name = $("#tbMaterialName_"+finObj.financialId).val();
			financialList.price = parseFloat($("#tbMaterialPrice_"+finObj.financialId).val());
			financialList.quantity = parseInt($("#tbMaterialQuantity_"+finObj.financialId).val());
			financialList.totalCost = parseFloat($("#tbTotalCost_"+finObj.financialId).val());
			finalFinancialLists.push(financialList);

		} else if(finObj.type == "Other" && financialType == "Other"){
			financialList = {};
			financialList.classId = parseInt($("#classId").val());
			financialList.name = $("#tbOthersName_"+finObj.financialId).val();
			financialList.price = parseFloat($("#tbOthersPrice_"+finObj.financialId).val());
			financialList.quantity = parseInt($("#tbOthersQuantity_"+finObj.financialId).val());
			financialList.totalCost = parseFloat($("#tbTotalCost_"+finObj.financialId).val());
			finalFinancialLists.push(financialList);
		}
	});
	return finalFinancialLists;
}