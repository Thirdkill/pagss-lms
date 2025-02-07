$(function(){
	initTrainingCategoriesTbl();
	attachListenerToSearchBox();
	attachListenerToSortLink();
	attachListenerToAddLocationBtn();
	attachListenerToSaveBtn();
	attachListenerToEditBtn();
	attachListenerToUpdateBtn();
	attachListenerToClearIcon();
});

function initTrainingCategoriesTbl() {
    pagination.initiate({
		paginationDom: "#trainingCategoryPagination",
		tableBodyDom: "#trainingCategoryTblBody",
		url: "trainingcategories/pages",
		locator: "trainingCategories",
		className: "paginationjs-theme-blue",
		pageSize: 10,
		functionName: "createCategoryRow"});
}

function createCategoryRow(trainingCategory) {
	$("#trainingCategoryTblBody").append($("<tr/>")
		.append($("<td/>").attr({"style" : "width:60px;"})
			.append($("<a/>").attr({"data-id":trainingCategory.categoryId,"data-target":"editCategoryModal",
				"id":"editCategoryBtn"})
				.addClass("modal-trigger waves-effect waves-light")
				.append($("<i/>").addClass("material-icons").html("edit"))))
		.append($("<td/>").html(trainingCategory.categoryCode))
		.append($("<td/>").html(trainingCategory.categoryName))
		.append($("<td/>").html(truncateString(trainingCategory.description)))
		.append($("<td/>").html(checkStatus(trainingCategory.status))));
}

function checkStatus(status) {
	return (status != 0)? "ACTIVE" : "INACTIVE"; 
}

function attachListenerToSearchBox() {
	$("#searchTrainingCategory").on("keypress",function(e) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == 13) {
			var tableCommand = {};
			tableCommand.keyword = $(this).val();
			tableCommand.sortDirection = "ASC";
			tableCommand.sortColumnName = "categoryCode";
			searchTrainingCategory(tableCommand);
		}
		return true;
	})
}

function attachListenerToSortLink() {
	$("body").on("click",".sortLink",function() {
		var tableCommand = {};
		tableCommand.keyword = $("#searchTrainingCategory").val();
		tableCommand.sortDirection = $(this).data("sortdir");
		tableCommand.sortColumnName = $(this).data("sortname");
		searchTrainingCategory(tableCommand);
	});
}

function searchTrainingCategory(tableCommand) {
    pagination.initiate({
		paginationDom: "#trainingCategoryPagination",
		tableBodyDom: "#trainingCategoryTblBody",
		url: "trainingcategories/search",
		locator: "trainingCategories",
		ajax: {
    		data: {
    			sortDirection: tableCommand.sortDirection,
    			sortName: tableCommand.sortColumnName,
    			keyword: tableCommand.keyword},
    		type: "POST"},
		className: "paginationjs-theme-blue",
		pageSize: 10,
		functionName: "createCategoryRow"});
}

function attachListenerToAddLocationBtn() {
	$("#addCategoryBtn").on("click",function() {
		$.when(ajax.fetch("trainingcategory/generate-trainingcategoryCode")).done(function(response) {
			var trainingCategory = response.trainingCategory;
			$("#saveCategoryCode").val(trainingCategory.categoryCode);
		})
	});
}

function attachListenerToSaveBtn() {
	$("#saveCategoryBtn").on("click",function() {
		if(validateSaveTrainingCategoryFields()) {
			var trainingCategory = getTrainingCategoryData();
			$.when(ajax.create("trainingcategory",trainingCategory)).done(function(response){
				switch(response.status) {
					case HttpStatus.SUCCESS:
						alertify.success("Successfully Created!");
						initTrainingCategoriesTbl();
						clearCategoryFields();
						$("#saveCategoryModal").modal("close");
						break;
					case HttpStatus.CATEGORY_CODE_EXIST:
						alertify.warning(Message.CATEGORY_CODE_EXIST);
						break;
				}
			})
		}
	});
}

function validateSaveTrainingCategoryFields() {
	if($("#saveCategoryCode").val() != "" && $("#saveCategoryName").val() != "") {
		return true;
	} else {
		alertify.warning("Please fill all required fields!");
		return false;
	}
}

function getTrainingCategoryData() {
	var trainingCategory = {};
	trainingCategory.categoryCode = $("#saveCategoryCode").val();
	trainingCategory.categoryName = $("#saveCategoryName").val();
	trainingCategory.description = $("#saveDescription").val();
	trainingCategory.status = getCheckBoxValue("#saveStatus");
	return trainingCategory;
}

function clearCategoryFields() {
	$("#saveCategoryCode").val("");
	$("#saveCategoryName").val("");
	$("#saveDescription").val("");
	$("#saveStatus").prop("checked","");
}

function attachListenerToEditBtn() {
	$("body").on("click","#editCategoryBtn",function() {
		populateEditCategoryModal(findJobRoleObj($(this).data("id")));
	});
}

function findJobRoleObj(categoryId) {
	var categoryOutput = {};
	_.each(globalList,function(categoryObj) {
		if(categoryId == categoryObj.categoryId) {
			categoryOutput = categoryObj;
		}
	});
	return categoryOutput;
}

function populateEditCategoryModal(trainingCategory) {
	$("#editCategoryCode").val(trainingCategory.categoryCode);
	$("#editCategoryCodeLbl").addClass("active");
	$("#editCategoryName").val(trainingCategory.categoryName);
	$("#editCategoryNameLbl").addClass("active");
	$("#editDescription").val(trainingCategory.description);
	$("#editDescriptionLbl").addClass("active");
	$("#editCategoryId").val(trainingCategory.categoryId);
	if(trainingCategory.status != Status.INACTIVE) {
		$("#editStatus").prop("checked","checked");
	} else {
		$("#editStatus").prop("checked","");
	}
}

function attachListenerToUpdateBtn() {
	$("#updateCategoryBtn").on("click",function() {
		if(validateEditTrainingCategoryFields()) {
			var trainingCategoryData = getEditTrainingCategoryData();
			$.when(ajax.update("trainingcategory/",trainingCategoryData.categoryId,trainingCategoryData)).done(function(response) {
				if(response.status == HttpStatus.SUCCESS) {
					alertify.success("Successfully Updated!");
					initTrainingCategoriesTbl();
					$("#editCategoryModal").modal("close");
				}
			});
		}
	})
}

function validateEditTrainingCategoryFields() {
	if($("#editCategoryCode").val() != "" && $("#editCategoryName").val() != "") {
		return true;
	} else {
		alertify.warning("Please fill all required fields!");
		return false;
	}
}

function getEditTrainingCategoryData() {
	var trainingCategory = {};
	trainingCategory.categoryId = $("#editCategoryId").val();
	trainingCategory.categoryCode = $("#editCategoryCode").val();
	trainingCategory.categoryName = $("#editCategoryName").val();
	trainingCategory.description = $("#editDescription").val();
	trainingCategory.status = getCheckBoxValue("#editStatus");
	return trainingCategory;
}

function attachListenerToClearIcon() {
	$("#clearSearch").bind("click",function() {
		$("input[type='search']").val("");
		initTrainingCategoriesTbl();
	});
}