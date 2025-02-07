/**
 * 
 */

$(function(){
	defineGlobalList();
	initClassFields();
	allowNumbersOnly();
	initCourseInfoFields();
	populateClassFields();
	attachListenerToScheduleTypeDropdownChanged();
	attachListenerToMonthlyRadioButtonChanged();
	attachListenerToRecurrenceDropdownChanged();
	attachListenerToClassSchedule();
	attachListenerToAttendeeScale();
	attachListenerToAddSetSchedule();
	attachListenerToDaysCheckBox();
	attachListenerToSaveButton();
	
});

function defineGlobalList() {
	tempClassSchedules = [];
	tempDays = [];
	tempCheckedDays = [];
}


function initClassFields() {
	$.when(ajax.fetch("locations/status/" + Status.ACTIVE)).done(function(response) {
		populateLocationDropdown(response.locations);
	});
	$.when(ajax.fetch("employeeinfo/trainers/status/"+Status.ACTIVE)).done(function(response) {
		console.log(response);
		populateEmployeeDropdown(response.employeeInfos);
	})
}

function initCourseInfoFields() {
	var courseId = $("#courseId").val();
	$.when(ajax.fetch("courseinfo/" + courseId)).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			var courseInfo = response.courseInfo;
			$("#divCourseDescription").html(courseInfo.description);
			$("#divCourseObjective").html(courseInfo.objective);
			$("#courseId").val();
		}
	});
}

function populateLocationDropdown(locations) {
	$("#locationDropdown").html("");
	$("#locationDropdown").append($("<option/>").attr({"selected":"","disabled":"disabled"}));
	_.each(locations,function(location) {
	$("#locationDropdown").append($("<option/>").attr("value",location.locationId).html(location.locationName));
	})
	$("#locationDropdown").val($("#locationId").val());
}

function populateEmployeeDropdown(employeeInfos) {
	$("#trainerDropdown").html("");
	$("#trainerDropdown").append($("<option/>").attr({"selected":"","disabled":"disabled"}));
	_.each(employeeInfos,function(employeeInfo) {
	$("#trainerDropdown").append($("<option/>").attr("value",employeeInfo.employeeId).html(employeeInfo.fullName));
	})
}

function populateClassFields() {
	
	$("#scheduleTypeDropdown").val($("#scheduleType").val()).trigger("change");
	
	if($("#deliveryMethod").val() == DeliveryMethod.INDIVIDUAL){
		$("#mainLearningPath").css("display","none");
		$("#mainChecklist").css("display","none");
		$("#deliveryMethodLabel").html("Individual");
	} else if($("#deliveryMethod").val() == DeliveryMethod.MODULAR){
		$("#mainChecklist").css("display","none");
		$("#deliveryMethodLabel").html("Modular");
	} else if($("#deliveryMethod").val() == DeliveryMethod.CLASSTRAINING){
		$("#mainLearningPath").css("display","none");
		$("#deliveryMethodLabel").html("Class Training");
	}
	
	if($("#isSelfRegister").val() == "1"){
		$("#selfRegCheckBox").prop("checked",true).trigger('change');
	} else if($("#isSelfRegister").val() == "0"){
		$("#selfRegCheckBox").prop("checked",false).trigger('change');
	}
	if($("#withCertificate").val() == "1"){
		$("#certificateCheckBox").prop("checked",true).trigger('change');
	} else if($("#withCertificate").val() == "0"){
		$("#certificateCheckBox").prop("checked",false).trigger('change');
	}
	if($("#exam").val() == "1"){
		$("#examCheckbox").prop("checked",true).trigger('change');
	} else if($("#exam").val() == "0"){
		$("#examCheckbox").prop("checked",false).trigger('change');
	}
	
	if($("#scheduleTypeDropdown").val() == "1"){
		$("#blockSchedule").css("display","");
		$("#setSchedule").css("display","none");
		$("#seriesSchedule").css("display","none");
	}else if($("#scheduleTypeDropdown").val() == "2"){
		$("#blockSchedule").css("display","none");
		$("#setSchedule").css("display","");
		$("#seriesSchedule").css("display","none");
	}else if($("#scheduleTypeDropdown").val() == "3"){
		$("#blockSchedule").css("display","none");
		$("#setSchedule").css("display","none");
		$("#seriesSchedule").css("display","");
	}

}

function attachListenerToAddSetSchedule() {
	$("#addSetScheduleSubmit").on("click",function(e) {
		var incField = 0;
		if($("#setDateStartPicker").val().length == 0){
			$("#setScheduleStartLabel").css("color","red");
			incField = 1;
		}else{$("#setScheduleStartLabel").css("color","");}
		
		if($("#setDateEndPicker").val().length == 0){
			$("#setScheduleEndLabel").css("color","red");
			incField = 1;
		}else{$("#setScheduleEndLabel").css("color","");}
		if(incField == 1){
			alertify.dismissAll();
			alertify.warning("Please fill up the required fields.");
			$("#addSetScheduleSubmit").removeAttr("disabled");
			$("#addSetScheduleSubmit").html("<i class='material-icons left'>save</i>Save");
		}else{
			setSchedule = {};
			setSchedule.ScheduleDateStart = $("#setDateStartPicker").val();
			setSchedule.ScheduleDateEnd = $("#setDateEndPicker").val();
			setSchedule.ScheduleHrStart = $("#setStartHr").val();
			setSchedule.ScheduleMinStart = $("#setStartMin").val();
			setSchedule.ScheduleMeridiemStart = $("#setMeridiemFrom").val();
			setSchedule.ScheduleHrEnd = $("#setEndHr").val();
			setSchedule.ScheduleMinEnd = $("#setEndMin").val();
			setSchedule.ScheduleMeridiemEnd = $("#setMeridiemTo").val();
			setClassSchedule(setSchedule);
			createSetScheduleRow(setSchedule);
			
			$("#addScheduleModal").modal('close');
		}
	});
}

function attachListenerToScheduleTypeDropdownChanged() {
	$("#scheduleTypeDropdown").on("change",function(e) {
		if($(this).val() == "1"){
			$("#blockSchedule").css("display","");
			$("#setSchedule").css("display","none");
			$("#seriesSchedule").css("display","none");
		}else if($(this).val() == "2"){
			$("#blockSchedule").css("display","none");
			$("#setSchedule").css("display","");
			$("#seriesSchedule").css("display","none");
		}else if($(this).val() == "3"){
			$("#blockSchedule").css("display","none");
			$("#setSchedule").css("display","none");
			$("#seriesSchedule").css("display","");
		}
	});
}

function attachListenerToMonthlyRadioButtonChanged() {
	 $("input:radio[name=monthlyType]").change(function () {
         if (this.value == "Date") {
        	 $("#dateDiv").css("display","");
        	 $("#dayDiv").css("display","none");
         }
         if (this.value == "Day") {
        	 $("#dateDiv").css("display","none");
        	 $("#dayDiv").css("display","");
         }
     });
}

function attachListenerToRecurrenceDropdownChanged() {
	$("#recurrenceDropdown").on("change",function(e) {
		if($(this).val() == "1"){
			$("#weeklyDiv").css("display","");
			$("#monthlyDiv").css("display","none");
			$("#dateDiv").css("display","none");
			$("#dayDiv").css("display","none");
			$("#dateRadio").prop("checked",false);
			$("#dayRadio").prop("checked",false);
		}else if($(this).val() == "2"){
			$("#weeklyDiv").css("display","none");
			$("#monthlyDiv").css("display","");
			$("#dateDiv").css("display","none");
			$("#dayDiv").css("display","none");
			$("#dateRadio").prop("checked",false);
			$("#dayRadio").prop("checked",false);
		}
	});
}

function attachListenerToAttendeeScale() {
	$("#minAttendees").on("keyup",function(e) {
		var numOnly = e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57);
		var minScale = parseInt($("#minAttendees").val());
		var maxScale = parseInt($("#maxAttendees").val());
		if(minScale > maxScale || minScale == maxScale || minScale < 0){
			$("#minAttendees").val(maxScale-1);
		}
	});
	
	$("#maxAttendees").on("keyup",function(e) {
		var numOnly = e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57);
		var minScale = parseInt($("#minAttendees").val());
		var maxScale = parseInt($("#maxAttendees").val());
		if(minScale > maxScale || minScale == maxScale || minScale < 0){
			$("#maxAttendees").val(minScale+1);
		}
	});
}

function attachListenerToClassSchedule() {
	var date1 = new Date($("#blockDateStartPicker").val());
	var date2 = new Date($("#blockDateEndPicker").val());
	
	$("#blockDateStartPicker").on("change",function(e) {
//		setDateTimeSchedules();
	});
	$("#blockDateEndPicker").on("change",function(e) {
//		setDateTimeSchedules();
	});
	$("#blockStartHr").on("change",function(e) {
//		setDateTimeSchedules();
	});
	$("#blockStartMin").on("change",function(e) {
//		setDateTimeSchedules();
	});
	$("#blockEndHr").on("change",function(e) {
//		setDateTimeSchedules();
	});
	$("#blockEndMin").on("change",function(e) {
//		setDateTimeSchedules();
	});
}

function createSetScheduleRow(setSchedule) {	
	$("#setScheduleTblBody").append($("<tr/>")
		.append($("<td/>").attr({"class":"mid"}).html(setSchedule.ScheduleDateStart))
		.append($("<td/>").attr({"class":"mid"}).html(setSchedule.ScheduleDateEnd))
		.append($("<td/>").attr({"class":"mins"})
		 .append($("<a/>").attr("href","#!")
		  .append($("<i/>").attr("class","material-icons").html("edit")))
		 .append($("<a/>").attr("href","#!")
		  .append($("<i/>").attr("class","material-icons").html("delete")))));
}

function setClassSchedule(setSchedule) {
	var blockDate = {};
	var classId = parseInt($("#btnClassSave").attr("data-id"));
	var dateStart = setSchedule.ScheduleDateStart;
	var hrStart = parseInt(setSchedule.ScheduleHrStart);
	var minStart = setSchedule.ScheduleMinStart;
	var meridiemStart = setSchedule.ScheduleMeridiemStart;
	
	var dateEnd = setSchedule.ScheduleDateEnd;
	var hrEnd = parseInt(setSchedule.ScheduleHrEnd);
	var minEnd = setSchedule.ScheduleMinEnd;
	var meridiemEnd = setSchedule.ScheduleMeridiemEnd;
	
	if(meridiemStart == "PM" && hrStart < 12)
	{
		hrStart = hrStart + 12;
	}
	else if(meridiemStart == "AM" && hrStart == 12)
	{
		hrStart = hrStart - 12;
	}
	
	if(meridiemEnd == "PM" && hrEnd < 12)
	{
		hrEnd = hrEnd + 12;
	}
	else if(meridiemEnd == "AM" && hrEnd == 12)
	{
		hrEnd = hrEnd - 12;
	}
	
	console.log("Start Date: "+dateStart+" "+hrStart+":"+minStart+":00");
	console.log("End Date: "+dateEnd+" "+hrEnd+":"+minEnd+":00");
	var timeStart = hrStart+":"+minStart+":00";
	var timeEnd = hrEnd+":"+minEnd+":00";
	blockDate.classId = classId;
	blockDate.startDate = dateStart;
	blockDate.endDate = dateEnd;
	blockDate.startTime = timeStart;
	blockDate.endTime = timeEnd;
	
	tempClassSchedules.push(blockDate);
	
	console.log(tempClassSchedules);
}

function attachListenerToSaveButton() {
	$("#btnClassSave").on("click",function(e) {
		$("#btnClassSave").attr("disabled","disabled");
		$("#btnClassSave").html("Saving...");
		
		validateClassInfoFields();
	});
}

function convertCourseNameToInitials(name) {
	var matches = name.match(/\b(\w)/g);
	var acronym = matches.join('');
	console.log(acronym);
	return acronym.toUpperCase();
}

function validateClassInfoFields() {
	var incField = 0;
	
	if($("#courseDropdown").val() == 0 || $("#courseDropdown").val() == null){
		$("#courseLabel").css("color","red");
		incField = 1;
	}else{$("#courseLabel").css("color","");}
	
	if($("#classCodeTxtbox").val().length == 0){
		$("#codeLabel").css("color","red");
		incField = 1;
	}else{$("#codeLabel").css("color","");}
	
	if($("#classNameTxtbox").val().length == 0){
		$("#nameLabel").css("color","red");
		incField = 1;
	}else{$("#nameLabel").css("color","");}
	
	if($("#locationDropdown").val() == 0 || $("#locationDropdown").val() == null){
		$("#locationLabel").css("color","red");
		incField = 1;
	}else{$("#locationLabel").css("color","");}
	
	if($("#trainerDropdown").val() == 0 || $("#trainerDropdown").val() == null){
		$("#trainerLabel").css("color","red");
		incField = 1;
	}else{$("#trainerLabel").css("color","");}
	
	if($("#minAttendees").val().length == 0){
		$("#minLabel").css("color","red");
		incField = 1;
	}else{$("#minLabel").css("color","");}
	
	if($("#maxAttendees").val().length == 0){
		$("#maxLabel").css("color","red");
		incField = 1;
	}else{$("#maxLabel").css("color","");}
	
	if($("#scheduleTypeDropdown").val() == 0 || $("#scheduleTypeDropdown").val() == null){
		$("#schedLabel").css("color","red");
		incField = 1;
	}else{$("#schedLabel").css("color","");}
	
	if(incField != 1){
		var minScale = parseInt($("#minAttendees").val());
		var maxScale = parseInt($("#maxAttendees").val());
		if(minScale > maxScale){
			$("#minLabel").css("color","red");
			$("#maxLabel").css("color","red");
			$("#minAttendees").val("");
			$("#maxAttendees").val("");
			alertify.dismissAll();
			alertify.warning("Minimum value cannot be greater than Maximum value.");
			$("#btnClassSave").removeAttr("disabled");
			$("#btnClassSave").html("<i class='material-icons left'>save</i>Save");
		} else if(minScale == maxScale){
			$("#minLabel").css("color","red");
			$("#maxLabel").css("color","red");
			$("#minAttendees").val("");
			$("#maxAttendees").val("");
			alertify.dismissAll();
			alertify.warning("Minimum and Maximum value cannot be equal.");
			$("#btnClassSave").removeAttr("disabled");
			$("#btnClassSave").html("<i class='material-icons left'>save</i>Save");
		} else if(minScale < 0 || maxScale < 0){
			$("#minLabel").css("color","red");
			$("#maxLabel").css("color","red");
			$("#minAttendees").val("");
			$("#maxAttendees").val("");
			alertify.dismissAll();
			alertify.warning("Minimum and Maximum value must be a positive number.");
			$("#btnClassSave").removeAttr("disabled");
			$("#btnClassSave").html("<i class='material-icons left'>save</i>Save");
		}else {
			if($("#scheduleTypeDropdown").val() == "1"){
				setSchedule = {};
				setSchedule.ScheduleDateStart = $("#blockDateStartPicker").val();
				setSchedule.ScheduleDateEnd = $("#blockDateEndPicker").val();
				setSchedule.ScheduleHrStart = $("#blockStartHr").val();
				setSchedule.ScheduleMinStart = $("#blockStartMin").val();
				setSchedule.ScheduleMeridiemStart = $("#blockMeridiemFrom").val();
				setSchedule.ScheduleHrEnd = $("#blockEndHr").val();
				setSchedule.ScheduleMinEnd = $("#blockEndMin").val();
				setSchedule.ScheduleMeridiemEnd = $("#blockMeridiemTo").val();
				setClassSchedule(setSchedule);
			} else if($("#scheduleTypeDropdown").val() == "3"){
				setSchedule = {};
				setSchedule.ScheduleDateStart = $("#seriesStartDate").val();
				setSchedule.ScheduleEndType = $("#endTypeDropDown").val();
				setSchedule.ScheduleDateEnd = $("#seriesEndDate").val();
				setSchedule.ScheduleDateAfter = $("#seriesEndAfter").val();
				setSchedule.ScheduleHrStart = $("#seriesStartHr").val();
				setSchedule.ScheduleMinStart = $("#seriesStartMin").val();
				setSchedule.ScheduleMeridiemStart = $("#seriesMeridiemStartDropDown").val();
				setSchedule.ScheduleHrEnd = $("#seriesEndHr").val();
				setSchedule.ScheduleMinEnd = $("#seriesEndMin").val();
				setSchedule.ScheduleMeridiemEnd = $("#seriesMeridiemEndDropDown").val();
				console.log(setSchedule);
				setClassSeries(setSchedule);
			}
			submitClassDetails();
		}
		
	} else{
		alertify.dismissAll();
		alertify.warning("Please fill up the required fields.");
		$("#btnClassSave").removeAttr("disabled");
		$("#btnClassSave").html("<i class='material-icons left'>save</i>Save");
	}
}

function setClassInfo() {
	var classinfo = {};
	classinfo.classCode = $("#classCodeTxtbox").val();
	classinfo.courseId = parseInt($("#courseDropdown").val());
	classinfo.locationId = parseInt($("#locationDropdown").val());
	classinfo.className = $("#classNameTxtbox").val();
	var scheduleType = $("#scheduleTypeDropdown").val();
	
	if ($('#selfRegCheckBox').is(':checked')) {
		classinfo.isSelfRegister = 1;
	} else if(!$('#selfRegCheckBox').is(':checked')){
		classinfo.isSelfRegister = 0;
	}
	if ($('#certificateCheckBox').is(':checked')) {
		classinfo.withCertificate = 1;
	} else if(!$('#certificateCheckBox').is(':checked')){
		classinfo.withCertificate = 0;
	}
	if ($('#examCheckbox').is(':checked')) {
		classinfo.withExam = 1;
	} else if(!$('#examCheckbox').is(':checked')){
		classinfo.withExam = 0;
	}
	classinfo.minAttendee = parseInt($("#minAttendees").val());
	classinfo.maxAttendee = parseInt($("#maxAttendees").val());
	classinfo.scheduleType = scheduleType;

	return classinfo;
}

function setClassEmployee() {
	var classEmployee = {};
	classEmployee.classId = parseInt($("#btnClassSave").attr("data-id"));
	classEmployee.employeeId = parseInt($("#trainerDropdown").val());
	classEmployee.role = 1;
	return classEmployee;
}

function submitClassDetails() {
	var classId = parseInt($("#btnClassSave").attr("data-id"));
	var classInfo = setClassInfo();
	var classEmployee = setClassEmployee();
	var classSchedules = tempClassSchedules;
	var scheduleType = "";
	$.when(ajax.fetch("classinfo/check-classcode/"+classInfo.classCode)).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			console.log(response);
			if(response.totalRecords > 0){
				$("#btnClassSave").removeAttr("disabled");
				$("#codeLabel").css("color","red");
				$("#btnClassSave").html("<i class='material-icons left'>save</i>Save");
				alertify.dismissAll();
				alertify.warning("Class Code already exists. Please enter a different Class Code.");
			} else {
				console.log("class schedules");
				console.log(classSchedules);
				$.when(ajax.create("classinfo",classInfo)).done(function(response) {
					if(response.status == HttpStatus.SUCCESS){
						$.when(ajax.create("classemployee",classEmployee)).done(function(response) {
							if(response.status == HttpStatus.SUCCESS){
								console.log(classInfo.scheduleType);
								if(classInfo.scheduleType == "1"){
									scheduleType = "block";
								}
								if(classInfo.scheduleType == "2"){
									scheduleType = "set";
								}else if(classInfo.scheduleType == "3"){
									scheduleType = "series";
								}
								$.when(ajax.customUpdate("classinfo/"+classId+"/classschedules/"+scheduleType,classSchedules)).done(function(response) {
									if(response.status == HttpStatus.SUCCESS){
										alertify.dismissAll();
										alertify.success("Successfully Created.");
										setTimeout(function(){redirect("admin.classdetails?classId="+classId);}, 3000);
									}
								});
							}
						})
					}
				});
			}
		}
	});
}

function checkIfDayExist(days,day) {
	var result = false;
	_.each(days,function(dayObj) {
		if(dayObj.typeOfDay == day) {
			result = true;
		}
	});
	return result;
}

function deleteDayFromCheckList(day) {
	var result = false;
	tempDays = tempCheckedDays;
	_.each(tempDays,function(dayObj) {
		if(dayObj.typeOfDay == day) {
			tempCheckedDays = deleteItem(day);
		}
	});
	return result;
}

function deleteItem(day) {
	var resultArray = [];
	_.each(tempCheckedDays,function(dayObj) {
		if(dayObj.typeOfDay != day) {
			resultArray.push(dayObj);
		}
	});
	return resultArray;
}

function attachListenerToDaysCheckBox(){
	$("#chMonSeries").on("change",function(e) {
		var checkedDay = "Monday";
		console.log("Monday");
		checkDays(this,checkedDay);
	});
	$("#chTueSeries").on("change",function(e) {
		var checkedDay = "Tuesday";
		console.log("Tuesday");
		checkDays(this,checkedDay);
	});
	$("#chWedSeries").on("change",function(e) {
		var checkedDay = "Wednesday";
		console.log("Wednesday");
		checkDays(this,checkedDay);
	});
	$("#chThuSeries").on("change",function(e) {
		var checkedDay = "Thursday";
		console.log("Thursday");
		checkDays(this,checkedDay);
	});
	$("#chFriSeries").on("change",function(e) {
		var checkedDay = "Friday";
		console.log("Friday");
		checkDays(this,checkedDay);
	});
	$("#chSatSeries").on("change",function(e) {
		var checkedDay = "Saturday";
		console.log("Saturday");
		checkDays(this,checkedDay);
	});
	$("#chSunSeries").on("change",function(e) {
		var checkedDay = "Sunday";
		console.log("Sunday");
		checkDays(this,checkedDay);
	});

}

function checkDays(dom,day) {
	if($(dom).prop("checked") != true) {
		if(checkIfDayExist(tempCheckedDays,day)) {
			deleteDayFromCheckList(day);
		}
	} else if(!checkIfDayExist(tempCheckedDays,day)) {
		tempcheckDay = {};
		tempcheckDay.typeOfDay = day;
		tempCheckedDays.push(tempcheckDay);
	}
}

function setClassSeries(setSchedule) {
	var date1 = new Date(setSchedule.ScheduleDateStart);
	var date2 = new Date(setSchedule.ScheduleDateEnd);
	
	var endType = "";

	//Weekly Recurrence
	if($("#recurrenceDropdown").val() == "1"){
		
		for (var d = date1; d <= date2; d.setDate(d.getDate() + 1)) {
			var weekday=new Array(7);
			weekday[0]="Sunday";
			weekday[1]="Monday";
			weekday[2]="Tuesday";
			weekday[3]="Wednesday";
			weekday[4]="Thursday";
			weekday[5]="Friday";
			weekday[6]="Saturday";
			
			tempDays = tempCheckedDays;
			
			_.each(tempDays,function(dayObj) {
				
				if(weekday[d.getDay()] == dayObj.typeOfDay) {
					console.log(d+" "+ d.getDay());
					var blockDate = {};
					var monthNumber = d.getMonth()+1;
					var dateNumber = d.getDate();
					var classId = parseInt($("#btnClassSave").attr("data-id"));
					var dateStart = d.getFullYear()+"-"+monthNumber+"-"+dateNumber;
					var hrStart = parseInt(setSchedule.ScheduleHrStart);
					var minStart = setSchedule.ScheduleMinStart;
					var meridiemStart = setSchedule.ScheduleMeridiemStart;
					
					var dateEnd = setSchedule.ScheduleDateEnd;
					var hrEnd = parseInt(setSchedule.ScheduleHrEnd);
					var minEnd = setSchedule.ScheduleMinEnd;
					var meridiemEnd = setSchedule.ScheduleMeridiemEnd;
					
					if(meridiemStart == "PM" && hrStart < 12)
					{
						hrStart = hrStart + 12;
					}
					else if(meridiemStart == "AM" && hrStart == 12)
					{
						hrStart = hrStart - 12;
					}
					
					if(meridiemEnd == "PM" && hrEnd < 12)
					{
						hrEnd = hrEnd + 12;
					}
					else if(meridiemEnd == "AM" && hrEnd == 12)
					{
						hrEnd = hrEnd - 12;
					}
					
					var timeStart = hrStart+":"+minStart+":00";
					var timeEnd = hrEnd+":"+minEnd+":00";
					
					blockDate.classId = classId;
					blockDate.startDate = dateStart;
					blockDate.endDate = dateStart;
					blockDate.startTime = timeStart;
					blockDate.endTime = timeEnd;
					
					tempClassSchedules.push(blockDate);
				}
			});
		} 
	} else if($("#recurrenceDropdown").val() == "2"){
		//Monthly Recurrence
		for (var d = date1; d <= date2; d.setDate(d.getMonth() + 1)) {
			var weekday=new Array(7);
			weekday[0]="Sunday";
			weekday[1]="Monday";
			weekday[2]="Tuesday";
			weekday[3]="Wednesday";
			weekday[4]="Thursday";
			weekday[5]="Friday";
			weekday[6]="Saturday";
			
			tempDays = tempCheckedDays;
			
			_.each(tempDays,function(dayObj) {
				
				if(weekday[d.getDay()] == dayObj.typeOfDay) {
					console.log(d+" "+ d.getDay());
					var blockDate = {};
					var monthNumber = d.getMonth()+1;
					var dateNumber = d.getDate();
					var classId = parseInt($("#btnClassSave").attr("data-id"));
					var dateStart = d.getFullYear()+"-"+monthNumber+"-"+dateNumber;
					var hrStart = parseInt(setSchedule.ScheduleHrStart);
					var minStart = setSchedule.ScheduleMinStart;
					var meridiemStart = setSchedule.ScheduleMeridiemStart;
					
					var dateEnd = setSchedule.ScheduleDateEnd;
					var hrEnd = parseInt(setSchedule.ScheduleHrEnd);
					var minEnd = setSchedule.ScheduleMinEnd;
					var meridiemEnd = setSchedule.ScheduleMeridiemEnd;
					
					if(meridiemStart == "PM" && hrStart < 12)
					{
						hrStart = hrStart + 12;
					}
					else if(meridiemStart == "AM" && hrStart == 12)
					{
						hrStart = hrStart - 12;
					}
					
					if(meridiemEnd == "PM" && hrEnd < 12)
					{
						hrEnd = hrEnd + 12;
					}
					else if(meridiemEnd == "AM" && hrEnd == 12)
					{
						hrEnd = hrEnd - 12;
					}
					
					var timeStart = hrStart+":"+minStart+":00";
					var timeEnd = hrEnd+":"+minEnd+":00";
					
					blockDate.classId = classId;
					blockDate.startDate = dateStart;
					blockDate.endDate = dateStart;
					blockDate.startTime = timeStart;
					blockDate.endTime = timeEnd;
					
					tempClassSchedules.push(blockDate);
				}
			});
		} 
	}
}