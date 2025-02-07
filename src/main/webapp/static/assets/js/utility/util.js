/**
 * @Author Gerald
 */
$(function(){
	cssSettings();
	triggerDropDownChanges();
	allowNumbersOnly();
});

//$(document).ready(function () {
//    var divs = $('.mydivs>div');
//    var now = 0; // currently shown div
//    divs.hide().first().show();
//    $("a[name=next]").click(function (e) {
//        divs.eq(now).hide();
//        now = (now + 1 < divs.length) ? now + 1 : 0;
//        divs.eq(now).show(); // show next
//    });
//    $("a[name=prev]").click(function (e) {
//        divs.eq(now).hide();
//        now = (now > 0) ? now - 1 : divs.length - 1;
//        divs.eq(now).show(); // or .css('display','block');
//    });
//});

/**
 * Description: Initialize UI related Settings
 */
function cssSettings() {
	$(".sidenav").sidenav();
	$(".dropdown-trigger").dropdown();
	$(".collapsible").collapsible();
	$(".modal").modal();
	$(".fixed-modal").modal({dismissible:false});
	$("select").select2({placeholder: "Choose An Option"});
	$("select").addClass("browser-default");
	$( "#sortable" ).sortable();
	$( "#sortable" ).disableSelection();
}

/**
 * Description: Used to display propt messages
 * @param message
 * @param type
 */
function notify(message,type) {
	alertify.notify(message,type,5);
}

/**
 * Description: get Materialized checkbox value
 * @param dom
 * @returns
 */
function getCheckBoxValue(dom) {
	return ($(dom).prop("checked") != true) ? 0 : 1;
}

/**
 * Description: set Materialized Checkbox Dom Value. This is also applicable in switches.
 * @param dom
 * @param value
 */
function setCheckBoxValue(dom,value) {
	if(value != Status.INACTIVE) {
		$(dom).prop("checked","checked")
	} else {
		$(dom).prop("checked","")
	}
}


/**
 * Description: get Materialized switch value
 * @param dom
 * @returns
 */
function getSwitchValue(dom) {
	return ($(dom).val() != "on") ? 0 : 1;
}

/**
 * Description: Initiates changes to dropdown during dynamic data occurence
 */
function triggerDropDownChanges() {
	$("select").on('contentChanged', function() {
		$('select').select2();
	});
}

/**
 * Description: Allow Numbers only in text fields
 * @Param: input value
 */
function setNumberOnly(input) {
	$(input).on('keypress',function(e){
		if ($.inArray(e.keyCode, [ 46, 8, 9, 27, 13, 110,190 ]) !== -1
			|| (e.keyCode == 65 && (e.ctrlKey === true || e.metaKey === true))
			|| (e.keyCode == 67 && (e.ctrlKey === true || e.metaKey === true))
			|| (e.keyCode == 88 && (e.ctrlKey === true || e.metaKey === true))) {
			return;
		}
		if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57))
			 && (e.keyCode < 96 || e.keyCode > 105)) {
			e.preventDefault();
		}
	});
}

/**
 * Description: Populate list of options using DHTML method or upon calling AJAX.
 * If the arrayObj returns null, it creates a zero value and an 'Empty' description for the option.
 * @param dom - Unique identifier of the <select> tag.
 * @param arrayObj - response  array object.
 * @param value - value of the option
 * @param desc - name of the description of the option
 * @returns - returns an <option> element
 * E.g.: populateOptions("#scopeSelect", response.scopes, "scopeId", "scopeDesc");
 */
function populateOptions(dom, arrayObj, value, desc) {	
	$(dom).empty();	
	if (arrayObj == null) {$(dom).append(new Option("Empty", "0"));
	} else {_.each(arrayObj, function(obj) {$(dom).append(new Option(obj[desc], obj[value]));});}
}

/**
 * Description: Redirects user to the given url
 * @param url
 * @returns
 */
function redirect(url,noOfSeconds) {
	setTimeout(function() {$(location).attr("href", url);},noOfSeconds)
}

/**
 * Description: Truncates string and returns it with elipsis
 * @param string
 * @param limit
 * @returns
 */
function truncateString(string,limit) {
	if(string.length > limit) {
		string = string.substring(0,limit) + "..";
	}
	return string;
}

/**
 * Description: Initialize select2 multiple selection dropdown or set selected data.
 * @param dom
 * @param arrayData
 * @param arrayValue
 * @returns
 */
function populateMultipleSelect2(dom,arrayData,arrayValue) {
	$(dom).html("");
	var select2 = $(dom).select2({
		 placeholder: "Please Select..",
		 multiple: true,
		 data: arrayData
	});
	select2.val(arrayValue);
	select2.trigger("change");
}

function validateFileUpload(dom) {
	var result = false;
	var file = $(dom).find("input[type=file]")[0].files[0];
	if(file != undefined) {
		result = true;
	} 
	return result;
}

/**
 * Description: Blocks UI and displays loading GIF
 * @returns
 */
function blockUI(text) {
	var loadingHtml = [];
	loadingHtml.push("<h4 style='color:white'><img style='height:100px;' src='static/assets/images/loadinggif/snakeloader.gif' />");
	loadingHtml.push(text+"</h4>");
	$.blockUI({
		message: loadingHtml.join(""), 
		css: {backgroundColor:"",border:"",top:"30%",width:"40%"} });
}

/**
 * Validates image file via js.
 * @param file
 * @returns
 */
function validateImageFile (dom) {
	var input = document.getElementById(dom);
	var file = input.files[0];
	var valid = true;
	var fileType = file.type;
	var validImageTypes = ["image/gif", "image/jpeg", "image/png"]
	if ($.inArray(fileType, validImageTypes) < 0){
		alertify.warning("Invalid Image File","warning");
		valid = false;
	}
	return valid;
}

/**
 * Allows numbers only to a specified text box
 * @returns
 */
function allowNumbersOnly() {
	$(".numbersOnly").inputFilter({type:"number"});
}

/*
 * Collects URL Parameter on link.
 */
function GetURLParameter(sParam){
	var sPageURL = window.location.search.substring(1);
		
	var sURLVariables = sPageURL.split("&");
    for (var i = 0; i < sURLVariables.length; i++)
    {
        var sParameterName = sURLVariables[i].split("=");
        if (sParameterName[0] == sParam)
        {
            return sParameterName[1];
        }
    }
}

/**
 *  Displays a confirmation box whenever a user leaves a page
 */
function handleRefreshEvent() {
	$(window).bind('beforeunload', function(event) {
		return "Are you sure you want to refresh this page?";
	});
}

/**
 * Highlights validated dom elements red.
 * @param dom
 * @returns
 */
function highlightDomValidation(dom) {
	$(dom).css("color","red");
}

/**
 * Checks if the file type specified is acceptable.
 * @param dom
 * @param targetfileType
 * @returns
 */
function validateFileType(dom,targetfileType) {
	var fileType = $(dom).get(0).files[0].type;
	var fileTypeArray = [];
	var result = true;
	switch(targetfileType) {
		case "IMAGE":
			fileTypeArray = ValidFileType.IMAGE;
			break;
		case "DOCUMENT":
			fileTypeArray = ValidFileType.DOCUMENT;
			break;
		case "VIDEO":
			fileTypeArray = ValidFileType.VIDEO;
			break;
		case "AUDIO":
			fileTypeArray = ValidFileType.AUDIO;
			break;
	}
	if($.inArray(fileType,fileTypeArray) <= 0) {
		result = false;
	}
	_.each(fileTypeArray,function(validFileType) {
		if(validFileType==fileType) {
			result = true;
		}
	});
	if(fileType=="") {
		//For some unreadable formats;
		result=true;
	}
	return result;
}

/**
 * Checks if the file's size specified is acceptable.
 * @param dom
 * @param targetfileType
 * @returns
 */
function validateFileSize(dom,targetFileType) {
	var fileSize = $(dom).get(0).files[0].size;
	var result = true;
	if(targetFileType=="IMAGE" && fileSize>ValidFileSize.IMAGE_SIZE_LIMIT) {
		alertify.warning("Invalid File Size. Minimum File Size for image is 10mb");
		result = false;
	}
	if(targetFileType=="VIDEO" && fileSize>ValidFileSize.VIDEO_SIZE_LIMIT) {
		alertify.warning("Invalid File Size. Minimum File Size for video is 500mb");
		result = false;
	}
	if(targetFileType=="DOCUMENT" && fileSize>ValidFileSize.DOCUMENT_SIZE_LIMIT) {
		alertify.warning("Invalid File Size. Minimum File Size for document is 15mb");
		result = false;
	}
	if(targetFileType=="AUDIO" && fileSize>ValidFileSize.AUDIO_SIZE_LIMIT) {
		alertify.warning("Invalid File Size. Minimum File Size for audio is 10mb");
		result = false;
	}
	console.log(result);
	return result;
}

/**
 * Generate Randome string with a specfied length
 * @param length
 * @returns
 */
function generateRandomString(length) {
	var result ="";
	var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
	for(var ctr=0;ctr<length;ctr++) {
		result+=characters.charAt(Math.floor(Math.random() * characters.length));
	}
	return result;
}