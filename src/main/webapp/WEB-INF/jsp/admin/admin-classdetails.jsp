<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - VIEW USER (USER GROUP)</title>
	<!-- START: CSS-HEADER -->  
	<jsp:include page="../admin/common/admin-trainee-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	 <jsp:include page="../admin/common/admin-js-header.jsp" />
	 <script src="static/assets/js/admin-classdetails.js"></script>
	<!-- END: JS-HEADER -->
</head>
<body>
	<!-- Header -->
	<jsp:include page="../admin/common/admin-collapsibleheader.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-collapsiblesidebar.jsp" />
	
	<div class="container1">
          <div class="row">
            <div class="col s12 m3" style="padding-top: 20px;">
              <div class="card">
                <div class="card-image">
                  <img src="static/assets/images/usersimage/users.png"style="clip-path: circle(40% at 50% 50%);">
                  <a class="btn-floating btn-large halfway-fab waves-effect waves-light bttn"><i class="material-icons">add</i></a>
                </div>
              </div>
              <div>
                <h6><b>${classInfo.courseName}</b></h6>
                <p><span>${classInfo.className}</span></p>
                <p><span id="deliveryMethodLabel"></span></p>
                <p><span>Status</span></p>
              </div>
            </div>
            <div class="col s12 m9" style="padding: 5px;">
              <div class="row" style="margin-bottom: 20px;">
                <ul class="pagination hide-on-med-and-down">
                  <li><a href="admin.classdashboard?classId=${classId}"><u>Dashboard</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classdetails?classId=${classId}">Details</a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classtrainee?classId=${classId}"><u>Trainees</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classattendance?classId=${classId}"><u>Attendance</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classsettings?classId=${classId}"><u>Settings</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classmaterials?classId=${classId}"><u>Materials</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classexam?classId=${classId}"><u>Exams</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classfinancial?classId=${classId}"><u>Financials</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classevaluation?classId=${classId}"><u>Evaluation</u></a></li>
                </ul>         </div>
              <!-- content starts here -->
              <div class="divcard">
                <div class="row">

                  <div class="right">
                    <a href="admin.class" class="btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">arrow_back</i>Back</a>
                    <a href="#!" class="btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">not_interested</i>Cancel Class</a>
                    <a id="btnClassSave" class="btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">save</i>Save</a>
                  </div>
                </div>

                <div class="row" style="margin-top: 10px;">
                  <div class="col s12 m6">
                    <b>Course</b>
                      <input id="courseTxtbox" type="text" disabled name="" value="${classInfo.courseName}">
                  </div>
                  <div class="col s12 m6">
                    <b>Class Code</b>
                      <input id="classCodeTxtbox" type="text" disabled name="" value="${classInfo.classCode}">
                  </div>
                </div>

                <div class="row">
                  <div class="col s12 m6">
                    <b id="nameLabel">Class Name</b>
                      <input id="classNameTxtbox" type="text" name="" value="${classInfo.className}">
                  </div>
                  <div class="col s12 m6">
                    <b id="locationLabel">Location</b>
                      <select id="locationDropdown">
                      </select>
                  </div>
                </div>

                <div class="row">
                  <div class="col s12 m6">
                    <b id="trainerLabel">Trainer</b>
                      <select id="trainerDropdown">
                      </select>
                  </div>
                  <div class="col s12 m6">
                    <div class="row switch">
                      <div class="col s4 m4" style="text-align: center;">
                        <b>Self Registration</b>
                        <label>
                          <input id="selfRegCheckBox"  type="checkbox">
                          <span class="lever"></span>
                        </label>
                      </div>
                      <div class="col s4 m4" style="text-align: center;">
                        <b>Send Certificate</b>
                        <label>
                          <input id="certificateCheckBox" type="checkbox">
                          <span class="lever"></span>
                        </label>
                      </div>
                      <div class="col s4 m4" style="text-align: center;">
                        <b>Exam</b><br>
                        <label>
                          <input id="examCheckbox" type="checkbox">
                          <span class="lever" style="margin-left: 15px !important;"></span>
                        </label>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="row">
                  <div class="col s12 m6">
                    <b id="minLabel">Minimum Attendees</b>
                      <input class="numbersOnly" id="minAttendees" type="number" value="${classInfo.minAttendee}">
                  </div>
                  <div class="col s12 m6">
                    <b id="maxLabel">Maximum Attendees</b>
                      <input class="numbersOnly" id="maxAttendees" type="number" value="${classInfo.maxAttendee}">
                  </div>
                </div>

                <div class="row">
                  <div class="col s12 m6">
                    <b id="schedLabel">Schedule Type</b>
                     <select id="scheduleTypeDropdown">
                      	<option disabled selected></option>
                      	<option value="1">Block</option>
                      	<option value="2">Set Schedule</option>
                      	<option value="3">Series Schedule</option>
                     </select>
                  </div>
                  <div class="col m6 hide-on-small-only">
                  </div>
                </div>
                
                <!-- Schedule type starts here -->
		          <div id="blockSchedule" class="row blockSchedule showOpt" style="margin:0 5px;display:none;">
		            <div class="divcard">
		                <legend style="margin-bottom: 10px;"><b>Block Schedule</b></legend>
		
		                <div class="row">
		              		<div class="col s12 m6">
		              			<div>
				                  <b id="setScheduleStartLabel">Schedule Start*</b>
				                    <input id="setDateStartPicker" type="date" class="datepicker" name="">
				                    <label></label>
				                </div>
				                <div>
				                	<b>Start Time</b><br>
				                   	<i class="material-icons" style="vertical-align: middle;">access_time</i>
				                    <input id="setStartHr" class="numberOnly" value="12" min="1" max="12" type="number" name="" style="width: 50px;">
				                    <span>:</span>
				                    <input id="setStartMin" class="numberOnly" value="00" min="0" max="59" type="number" name="" style="width: 50px;">
			                    	<select id="setMeridiemFrom">
				                      <option disabled selected></option>
				                      <option selected value="AM">AM</option>
				                      <option value="PM">PM</option>
				                    </select>
				            	</div>
		              		</div>
		              		<div class="col s12 m6">
		              			<div>
				                  <b id="setScheduleEndLabel">Schedule End*</b>
				                    <input id="setDateEndPicker" type="date" class="datepicker" name="">
				                    <label></label>
				                </div>
				                <div>
				                	<b>End Time</b><br>
				                   	<i class="material-icons" style="vertical-align: middle;">access_time</i>
				                    <input id="setEndHr" class="numberOnly" value="12" min="1" max="12" type="number" name="" style="width: 50px;">
				                    <span>:</span>
				                    <input id="setEndMin" class="numberOnly" value="00" min="0" max="59" type="number" name="" style="width: 50px;">
			            			<select id="setMeridiemTo">
				                      <option disabled selected></option>
				                      <option selected value="AM">AM</option>
				                      <option value="PM">PM</option>
				                    </select>
				            	</div>
		              		</div>
			            </div>
		            </div>
		          </div>
		          <!-- Schedule type ends here -->
		
		          <!-- setSchedule type starts here -->
		          <div id="setSchedule" class="row setSchedule showOpt" style="display:none">
		            <div class="divcard">
		              <legend style="margin-bottom: 10px;"><b>Set Schedule</b></legend>
		              <div class="row">
		                <a data-target="addScheduleModal" class="modal-trigger bttn btn waves-effect waves-light right" type="submit"><i class="material-icons left">add_circle</i>Add Schedule</a>
		              </div>
		              <div class="row" style="margin-top: 20px;">
		                <table class="highlight striped">
		                  <thead>
		                    <tr>
		                      <th class="mid">Event Schedule Start</th>
		                      <th class="mid">Event Schedule End</th>
		                      <th class="mins"></th>
		                    </tr>
		                  </thead>
		                  <tbody id="setScheduleTblBody">
		                  </tbody>
		                </table>
		              </div>
		            </div>
		          </div>
		          <!-- setSchedule type ends here -->
		
		          <!-- seriesSchedule type starts here -->
		          <div id="seriesSchedule" class="row seriesSchedule endAfter endBy showOpt" style="display:none;">
		            <div class="divcard">
		              <legend style="margin-bottom: 10px;"><b>Series Schedule</b></legend>
		              <div class="row">
		                <div class="col s12 m3">
			                <b>Schedule Start</b>
			                <input type="date" class="datepicker" name="">
			                <label></label>
		                </div>
		
		                <div class="col s12 m3">
		                	<span>&nbsp</span>	
		                	<input type="date" class="datepicker" name="">
		                    <label></label>
		                </div>
		                <div class="col s12 m3">
			                <span><b>Recurrence Pattern</b></span>
			                <select id="recurrenceDropdown">
		                      <option selected value="1">Weekly</option>
		                      <option value="2">Monthly</option>
			                 </select>
		                </div>
		              </div>
		              <!-- weekly option starts here -->
		              <div id="weeklyDiv" class="row" style="padding:10px 10px 20px 10px;'">
		              <div id="pagealign" class="row" style="padding:10px 10px 20px 10px;">
		              	<label>
		                  <input type="checkbox" id="chMonSeries">
		                  <span style="color: #1e1e1e">Monday</span>
		                </label>
		                <label style="margin-left: 15px;">
		                  <input type="checkbox" id="chTueSeries">
		                  <span style="color: #1e1e1e">Tuesday</span>
		                </label>
		                <label style="margin-left: 15px;">
		                  <input type="checkbox" id="chWedSeries">
		                  <span style="color: #1e1e1e">Wednesday</span>
		                </label>
		                <label style="margin-left: 15px;">
		                  <input type="checkbox" id="chThuSeries">
		                  <span style="color: #1e1e1e">Thursday</span>
		                </label>
		                <label style="margin-left: 15px;">
		                  <input type="checkbox" id="chFriSeries">
		                  <span style="color: #1e1e1e">Friday</span>
		                </label>
		                <label style="margin-left: 15px;">
		                  <input type="checkbox" id="chSatSeries">
		                  <span style="color: #1e1e1e">Saturday</span>
		                </label>
		                <label style="margin-left: 15px;">
		                  <input type="checkbox" id="chSunSeries">
		                  <span style="color: #1e1e1e">Sunday</span>
		                </label>
		              </div>
		              <!-- weekly option ends here -->
		              <!-- monthly option starts here -->
		              	<div id="monthlyDiv" class="row" style="padding:10px 10px 20px 10px; display:none;'">
		              		<div class="col s12 m3">
		              			<label>
				                    <input id="dateRadio" type="radio" name="monthlyType" class="with-gap" value="Date">
				                    <span style="color: #1e1e1e"><b>Date</b></span>
			                    </label><br>
			                    <label>
				                    <input id="dayRadio" type="radio" name="monthlyType" class="with-gap" value="Day">
				                    <span style="color: #1e1e1e"><b>Day</b></span>
			                    </label>
		              		</div>
		              		<div class="col s12 m9">
		              			<!-- When date is chosen starts here -->
		              			<span id="dateDiv">Day <input type="text" style="width:20px;height:20px;"> of every <input type="text" style="width:20px;height:20px;"> month(s)</span><br>
		              			<!-- When day is chosen starts here -->
		              			<span id="dayDiv" class="selectWidth">The 
			              			<select>
			              				<option>1st</option>
			              			</select> 
			              			<select>
			              				<option>Monday</option>
			              			</select>
			              			of every <input type="text" style="width:20px;height:20px;"> month(s)
		              			</span>
		              		</div>
		              	</div>
		              <!-- monthly option ends here -->
		              <div class="row">
		                <div class="col s12 m6">
		                  <b>Schedule Start</b>
		                    <input id="seriesStartDate" type="date" class="datepicker" name="">
		                    <label></label>
		                </div>
		                <div class="col s12 m6">
		                  <div class="row">
		                    <div class="col s4 m4">
		                      <span>&nbsp</span>
		                      <select id="endTypeDropDown">
		                        <option disabled selected></option>
		                        <option value="endAfter">End after</option>
		                        <option value="endBy">End By</option>
		                      </select>
		                    </div>
		                    <div class="col s8 m8">
		                      <div class="endAfter showOpt">
		                        <span>&nbsp</span>
		                        <input id="seriesEndDate" type="date" class="datepicker" name="">
		                        <label></label>
		                      </div>
		                      <div class="endBy showOpt">
		                        <span>&nbsp</span>
		                        <input id="seriesEndAfter" type="text" name="" placeholder="Number of occurences">
		                      </div>
		                    </div>
		                  </div>
		                </div>
		              </div>
		              <div class="row">
		                  <div class="col s12 m6">
		                    <b>Start Time*</b><br>
		                    <div class="row">
		                      <div style="padding-right: 5px;">
		                        <i class="material-icons" style="vertical-align: middle;">access_time</i>
		                        <input id="seriesStartHr" class="numberOnly" value="12" min="1" max="12" type="number" name="" style="width: 50px;">
		                        <span>:</span>
		                        <input id="seriesStartMin" class="numberOnly" value="00" min="0" max="59" type="number" name="" style="width: 50px;">
		                        <span class="selectMargin"> 
			              			<select id="seriesMeridiemStartDropDown">
			                            <option disabled selected></option>
			                            <option value="AM">AM</option>
			                            <option value="PM">PM</option>
			              			</select> 
			              		</span>
		                      </div>
		                    </div>
		                  </div>
		                  <div class="col s12 m6">
		                    <b>End Time*</b><br>
		                    <div class="row">
		                      <div style="padding-right: 5px;">
		                        <i class="material-icons" style="vertical-align: middle;">access_time</i>
		                        <input id="seriesEndHr" class="numberOnly" value="12" min="1" max="12" type="number" name="" style="width: 50px;">
		                        <span>:</span>
		                        <input id="seriesEndMin" class="numberOnly" value="00" min="0" max="59" type="number" name="" style="width: 50px;">
		                        <span class="selectMargin"> 
			              			<select id="seriesMeridiemEndDropDown">
			                            <option disabled selected></option>
			                            <option value="AM">AM</option>
			                            <option value="PM">PM</option>
			              			</select> 
			              		</span>
		                      </div>
		                    </div>
		                  </div>
		                </div>
		              </div>
		            </div>
		          <!-- seriesSchedule type ends here -->
		        </div>
        <!-- addScheduleModal starts here -->
          <div id="addScheduleModal" class="modal modal-fixed-footer" style="height: 320px;" style="display:none;">
            <div class="modal-header">
              <h5>Add Schedule</h5>
            </div>
            <div class="modal-content">
              <div class="content-row">
              	<div class="row">
              		<div class="col s12 m6">
              			<div>
		                  <b id="setScheduleStartLabel">Schedule Start*</b>
		                    <input id="setDateStartPicker" type="date" class="datepicker" name="">
		                    <label></label>
		                </div>
		                <div>
		                	<b>Start Time</b><br>
		                   	<i class="material-icons" style="vertical-align: middle;">access_time</i>
		                    <input id="setStartHr" class="numberOnly" value="12" min="1" max="12" type="number" name="" style="width: 50px;">
		                    <span>:</span>
		                    <input id="setStartMin" class="numberOnly" value="00" min="0" max="59" type="number" name="" style="width: 50px;">
		                    <span class="selectWidth">
		                    	<select id="setMeridiemFrom">
			                      <option disabled selected></option>
			                      <option selected value="AM">AM</option>
			                      <option value="PM">PM</option>
			                    </select>
		                    </span>
		            	</div>
              		</div>
              		<div class="col s12 m6">
              			<div>
		                  <b id="setScheduleEndLabel">Schedule End*</b>
		                    <input id="setDateEndPicker" type="date" class="datepicker" name="">
		                    <label></label>
		                </div>
		                <div>
		                	<b>End Time</b><br>
		                   	<i class="material-icons" style="vertical-align: middle;">access_time</i>
		                    <input id="setEndHr" class="numberOnly" value="12" min="1" max="12" type="number" name="" style="width: 50px;">
		                    <span>:</span>
		                    <input id="setEndMin" class="numberOnly" value="00" min="0" max="59" type="number" name="" style="width: 50px;">
		            		<span class="selectWidth">
		            			<select id="setMeridiemTo">
			                      <option disabled selected></option>
			                      <option selected value="AM">AM</option>
			                      <option value="PM">PM</option>
			                    </select>
		            		</span>
		            	</div>
              		</div>
	            </div>
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
              <a id="addSetScheduleSubmit" class="btn waves-effect waves-light bttn" type="submit" name="action" id="saveLocationBtn"><i class="material-icons left">save</i>Save</a>
            </div>
          </div>
          <!-- addScheduleModal ends here -->

                <div class="row" style="margin: 5px 5px 0 5px;">
                  <div class="divcard">
                    <div class="row" style="padding: 0 15px;">
                      <legend style="margin-bottom: 10px;"><b>Schedule</b></legend>
                    </div>
                    <div class="row" style="padding: 0 15px;">
                      <a href="#!" class="right bttn btn waves-effect waves-light"><i class="material-icons left">add_circle</i>Add</a>
                    </div>
                    <div class="row">
                    	<table class="highlight striped">
                    		<thead>
	                    		<tr>
	                    			<th>Date</th>
	                    			<th>Start Time</th>
	                    			<th>End Time</th>
	                    		</tr>
                    		</thead>
                    		<tbody>
                    			<tr>
                    				<td>ad</td>
                    				<td>ad</td>
                    				<td>ad</td>
                    			</tr>
                    		</tbody>
                    	</table>
                    </div>
                  </div>
                </div>

                <div id="mainChecklist" class="row" style="margin: 5px 5px 0 5px;">
                  <div class="divcard">
                    <div class="row" style="padding: 0 15px;">
                      <legend style="margin-bottom: 10px;"><b>Checklist</b></legend>
                    </div>
                    <div class="row" style="padding: 0 15px;">
                      <div class="col s10 m10">
                        <input type="text" name="">
                      </div>
                      <div class="col s2 m2" style="padding-top: 10px;">
                        <a href="#!"><i class="material-icons">delete</i></a>
                        <a href="#!"><i class="material-icons">add_circle</i></a>
                      </div>
                    </div>
                  </div>
                </div>
                
                <div id="mainLearningPath" class="row" style="margin: 5px 5px 0 5px;">
                  <div class="divcard">
                    <div class="row" style="padding: 0 15px;">
                      <legend style="margin-bottom: 10px;"><b>Learning Path</b></legend>
                    </div>
                    <div class="row" style="padding: 0 15px;">
                      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                    </div>
                  </div>
                </div>

                <!-- only shows on modular and hide in individual -->
                <div class="row" style="margin: 5px 5px 0 5px;">
                  <div class="divcard">
                    <div class="row" style="padding: 0 15px;">
                      <legend style="margin-bottom: 10px;"><b>Course Description</b></legend>
                    </div>
                    <div id="divCourseDescription" class="row" style="padding: 0 15px;">
                      
                    </div>
                  </div>
                </div>
                <!-- up to here -->

                <div class="row" style="margin: 5px 5px 0 5px;">
                  <div class="divcard">
                    <div class="row" style="padding: 0 15px;">
                      <legend style="margin-bottom: 10px;"><b>Course Objectives</b></legend>
                    </div>
                    <div id="divCourseObjective" class="row" style="padding: 0 15px;">
                      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                    </div>
                  </div>
                </div>

              </div>
              <!-- content ends here -->
            </div>
          </div>
        </div>
        
        <input type="hidden" id="classId" value="${classInfo.classId}">
        <input type="hidden" id="locationId" value="${classInfo.locationId}">
        <input type="hidden" id="courseId" value="${classInfo.courseId}">
        <input type="hidden" id="trainerId" value="${classInfo.courseId}">
        <input type="hidden" id="isSelfRegister" value="${classInfo.isSelfRegister}">
        <input type="hidden" id="withCertificate" value="${classInfo.withCertificate}">
        <input type="hidden" id="exam" value="${classInfo.withExam}">
        <input type="hidden" id="deliveryMethod" value="${classInfo.deliveryMethod}">
        <input type="hidden" id="scheduleType" value="${classInfo.scheduleType}">
</body>
</html>