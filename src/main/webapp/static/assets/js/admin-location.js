$(function(){
	initLocationsTbl();
	attachListenerToSearchBox();
	attachListenerToSortLink();
	attachListenerToAddLocationLink();
	attachListenerToSaveBtn();
	attachListenerToEditBtn();
	attachListenerToUpdateBtn();
	attachListenerToClearIcon();
});

function initLocationsTbl() {
    pagination.initiate({
    	url: "locations/pages",
    	locator: "locations",
    	paginationDom: "#locationPagination",
    	tableBodyDom: "#locationTblBody",
    	className: "paginationjs-theme-blue",
    	pageSize: 10,
    	functionName: "createLocationRow"
    });
}

function createLocationRow(location) {
	$("#locationTblBody").append($("<tr/>")
		.append($("<td/>").attr({"style" : "width:60px;"})
			.append($("<a/>").attr({"data-id":location.locationId,"data-target":"editLocationModal",
				"id":"editLocationBtn"})
				.addClass("modal-trigger waves-effect waves-light")
				.append($("<i/>").addClass("material-icons").html("edit"))))
		.append($("<td/>").html(location.locationCode))
		.append($("<td/>").html(location.locationName))
		.append($("<td/>").html(truncateString(location.description,28)))
		.append($("<td/>").html(checkStatus(location.status))))
}

function checkStatus(status) {
	return (status != 0)? "ACTIVE" : "INACTIVE"; 
}

function attachListenerToSearchBox() {
	$("#searchLocation").on("keypress",function(e) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == 13) {
			var tableCommand = {};
			tableCommand.keyword = $(this).val();
			tableCommand.sortDirection = "ASC";
			tableCommand.sortColumnName = "locationCode";
			searchLocation(tableCommand);
		}
		return true;
	})
}

function attachListenerToSortLink() {
	$("body").on("click",".sortLink",function() {
		var tableCommand = {};
		tableCommand.keyword = $("#searchLocation").val();
		tableCommand.sortDirection = $(this).data("sortdir");
		tableCommand.sortColumnName = $(this).data("sortname");
		searchLocation(tableCommand);
//		showSortLink($(this).data("sortdir"),$(this).data("sortname"));
	});
}

//function showSortLink(sortdir,sortName) {
//	var otherSortLink = "";
//	var clickedSortLink = sortName+sortdir;
//	if(sortdir == "DESC") {
//		otherSortLink = sortName + "ASC";
//	} else {
//		otherSortLink = sortName + "DESC";
//	}
//	$("#"+otherSortLink).removeAttr("style");
//	$("#"+clickedSortLink).css("display","none");
//}

function searchLocation(tableCommand) {
    pagination.initiate({
    	url: "locations/search",
    	locator: "locations",
    	paginationDom: "#locationPagination",
    	tableBodyDom: "#locationTblBody",
    	ajax: {
    		data: {
    			sortDirection: tableCommand.sortDirection,
    			sortName: tableCommand.sortColumnName,
    			keyword: tableCommand.keyword},
    		type: "POST"},
    	className: "paginationjs-theme-blue",
    	pageSize: 10,
    	functionName: "createLocationRow"
    });
}

function attachListenerToAddLocationLink() {
	$("body").on("click","#AddlocationBtn",function() {
		$.when(ajax.fetch("location/locationcode")).done(function(response) {
			var location = response.location;
			$("#locationCode").val(location.locationCode);
		});
	});
}

function attachListenerToSaveBtn() {
	$("#saveLocationBtn").on("click",function() {
		if(validateSaveLocation()) {
			var location = getLocationData();
			$.when(ajax.create("location",location)).done(function(response) {
				switch(response.status) {
					case HttpStatus.SUCCESS:
						alertify.success("Successfully Created!")
						clearFields();
						initLocationsTbl();
						$("#locationModal").modal("close");
						break;
					case HttpStatus.LOCATION_CODE_EXIST:
						alertify.warning(Message.LOCATION_CODE_EXIST);
						break;
				}
			});
		}
	});
}

function validateSaveLocation() {
	if($("#locationCode").val() != "" && $("#locationName").val() != "") {
		return true
	} else {
		alertify.warning("Please fill all required fields!");
		return false;
	}
}

function getLocationData() {
	var location = {}; 
	location.locationCode = $("#locationCode").val();
	location.locationName = $("#locationName").val();
	location.description = $("#description").val();
	location.status = getCheckBoxValue("#locationStatus");
	return location;
}

function clearFields() {
	$("#locationCode").val("");
	$("#locationName").val("");
	$("#description").val("");
	$("#locationStatus").prop("checked","");
}

function attachListenerToEditBtn() {
	$("body").on("click","#editLocationBtn",function() {
		var locationId = $(this).data("id");
		populateLocationModal(findLocationObj(locationId));
	});
}

function findLocationObj(locationId) {
	var locationOutput = {};
	_.each(globalList,function(locationObj) {
		if(locationId == locationObj.locationId) {
			locationOutput = locationObj;
		}
	});
	return locationOutput;
}

function populateLocationModal(location) {
	$("#editLocationCodeLbl").addClass("active");
	$("#editLocationCode").val(location.locationCode);
	$("#editLocationNameLbl").addClass("active");
	$("#editLocationName").val(location.locationName);
	$("#editDescriptionLbl").addClass("active");
	$("#editDescription").val(location.description);
	$("#locationId").val(location.locationId);
	if(location.status != Status.INACTIVE) {
		$("#editLocationStatus").prop("checked","checked");
	} else {
		$("#editLocationStatus").prop("checked","");
	}
}

function attachListenerToUpdateBtn() {
	$("#updateLocationBtn").on("click",function() {
		if(validateEditLocation()) {
			var location = getEditLocationData();
			$.when(ajax.update("location/",location.locationId,location)).done(function(response) {
				if(response.status == HttpStatus.SUCCESS) {
					alertify.success("Successfully Updated!");
					initLocationsTbl();
					$("#editLocationModal").modal("close");
				}
			})
		}
	});
}

function validateEditLocation() {
	if($("#editLocationCode").val() != "" && $("#editLocationName").val() != "") {
		return true
	} else {
		alertify.warning("Please fill all required fields!");
		return false;
	}
}

function getEditLocationData() {
	var location = {}; 
	location.locationCode = $("#editLocationCode").val();
	location.locationName = $("#editLocationName").val();
	location.description = $("#editDescription").val();
	location.status = getCheckBoxValue("#editLocationStatus");
	location.locationId = $("#locationId").val();
	return location;
}

function attachListenerToClearIcon() {
	$("#clearSearch").bind("click",function() {
		$("input[type='search']").val("");
		initLocationsTbl();
	});
}