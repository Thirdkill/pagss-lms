$(function() {
	initRecomendedSlickSettings();
	initRecomendedClassInfoSlides();
	initPublicTrainingSlickSettings();
	initPublicTrainingClassInfoSlides();
});
function initRecomendedSlickSettings() {
	$("#recommendedSlider").slick({
		infinite:true,
		slidesToShow:3,
		slidesToScroll:3
	});
}

function initPublicTrainingSlickSettings() {
	$("#publicTrainingSlider").slick({
		infinite:true,
		slidesToShow:3,
		slidesToScroll:3
	});
}

function initUserClassInfoList() {
	var userId = $("#userId").val();
	$.when(ajax.fetch("user/"+userId+"/classInfos")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				userClassInfoList = response.classInfos;
				break;
		}
	});
}

function initRecomendedClassInfoSlides() {
	$.when(ajax.fetch("classinfos/recommended")).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				populateRecommendedClassInfoSlides(response.classInfos);
				break;
		}
	});
}

function checkIfUserHasClass(classId) {
	var result=false;
	console.log(userClassInfoList);
	_.each(userClassInfoList,function(classInfo) {
		if(classInfo.classId==classId) {
			result=true;
		}
	});
	return result;
}

function populateRecommendedClassInfoSlides(classInfos) {
	_.each(classInfos,function(classInfo) {
		$("#recommendedSlider").slick("slickAdd",createSlideDiv(classInfo));
	});
}

function initPublicTrainingClassInfoSlides() {
	$.when(ajax.fetch("classinfos/isselfregister/"+IsSelfRegister.TRUE)).done(function(response) {
		switch(response.status) {
			case HttpStatus.SUCCESS:
				populatePublicTrainingSlides(response.classInfos);
				break;
		}
	});
}

function populatePublicTrainingSlides(classInfos) {
	_.each(classInfos,function(classInfo) {
		$("#publicTrainingSlider").slick("slickAdd",createSlideDiv(classInfo));
	})
}

function createSlideDiv(classInfo) {
	var divHtml = [];
	divHtml.push("<div class='col s12 m4'>");
	divHtml.push("<div class='divcard'>");
	divHtml.push("<h6 style='margin: 0;'><b>"+classInfo.courseName+"</b></h6>");
	divHtml.push("<p><label><span>"+classInfo.className+"</span></label></p>");
	divHtml.push("<p><label><span>"+getDeliveryMethodValue(classInfo.deliveryMethod)+"</span></label></p>");
	divHtml.push("<a href='mytraining-classinfo?classId="+classInfo.classId+"' class='btn bttn waves-effect waves-light' style='width: 100%;'>View</a>");
	divHtml.push("</div></div>");
	return divHtml.join(" ");
}

function getDeliveryMethodValue(deliveryMethod) {
	var result = "";
	switch(deliveryMethod) {
		case DeliveryMethod.INDIVIDUAL:
			result = "CBT (Individual)";
			break;
		case DeliveryMethod.MODULAR:
			result = "CBT (Modular)";
			break;
		case DeliveryMethod.CLASSTRAINING:
			result = "Classroom Training";
			break;
	}
	return result;
}