<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - CREATE CLASS</title>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
	<script src="static/assets/js/admin-createclass.js"></script>
	<!-- END: JS-HEADER -->
</head>
<body>
	<!-- Header -->
	<jsp:include page="../admin/common/admin-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-sidebar.jsp" />
	<div class="container2">
          <div class="row">
            <div class="col s12 m8">
               <h4>Add Class</h4>
            </div>
            <div id="navpad" class="nav-wrapper col s12 m4 row">
              <div class="row">
                 <div class="input-field col s6 m6">
                    <a href="admin.class" style="width:100%;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">not_interested</i>Cancel</a>
                 </div>
                 <div class="input-field col s6 m6">
                    <a id="btnClassSave" style="width:100%;" class="btn waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">save</i>Save</a>
                 </div>
              </div>
            </div>
          </div>

          <div class="row">
            <hr style="width:100%;">
          </div>

          <div class="row">
            <div class="divcard">

              <div class="row">
                <div class="col s12 m6">
                  <b id="courseLabel">Course</b>
                    <select id="courseDropdown">
                    </select>
                </div>
                <div class="col s12 m6">
                  <b id="codeLabel">Class Code</b>
                    <input id="classCodeTxtbox" type="text" name="">
                </div>
              </div>

              <div class="row">
                <div class="col s12 m6">
                  <b id="nameLabel">Class Name</b>
                    <input id="classNameTxtbox" type="text" name="">
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
                      <b>Self Registration</b><br>
                      <label>
                        <input id="selfRegCheckBox" type="checkbox">
                        <span class="lever"></span>
                      </label>
                    </div>
                    <div class="col s4 m4" style="text-align: center;">
                      <b>Send Certificate</b><br>
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
                    <input class="numbersOnly" id="minAttendees" type="number" name="">
                </div>
                <div class="col s12 m6">
                  <b id="maxLabel">Maximum Attendees</b>
                    <input class="numbersOnly" id="maxAttendees" type="number" name="">
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
            </div>
          </div>

          <!-- Schedule type starts here -->
          <div id="blockSchedule" class="row blockSchedule showOpt" style="display:none;">
            <div class="divcard">
              <div class="row">
                <legend style="margin-bottom: 10px;"><b>Block Schedule</b></legend>

                <div class="row">
                  <div class="col s12 m6">
                    <b>Start Date*</b>
                    <input id="blockDateStartPicker" type="date"  name="">
                    <label></label>
                  </div>
                  <div class="col s12 m6">
                    <b>End Date*</b>
                    <input id="blockDateEndPicker" type="date"  name="">
                    <label></label>
                  </div>
                </div>

                <div class="row">
                  <div class="col s12 m6">
                    <b>Start Time*</b><br>
                    <div class="row">
                      <div style="padding-right: 5px;">
                        <i class="material-icons" style="vertical-align: middle;">access_time</i>
                        <input id="blockStartHr" class="numberOnly" value="12" min="1" max="12" type="number" name="" style="width: 50px;">
                        <span>:</span>
                        <input id="blockStartMin" class="numberOnly" value="00" min="0" max="59" type="number" name="" style="width: 50px;">
                      	<span class="selectWidth">
                      		<select id="blockMeridiemFrom">
	                            <option disabled selected></option>
	                            <option selected value="AM">AM</option>
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
                        <input id="blockEndHr" class="numberOnly" value="12" min="1" max="12" type="number" name="" style="width: 50px;">
                        <span>:</span>
                        <input id="blockEndMin" class="numberOnly" value="00" min="0" max="59" type="number" name="" style="width: 50px;">
                        <span class="selectWidth">
                        	<select id="blockMeridiemTo">
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
                  <select id="endTypeDropDown">
                    <option disabled selected></option>
                    <option value="endAfter">End after</option>
                    <option value="endBy">End By</option>
                  </select>
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
              </div>
              <!-- weekly option ends here -->
              <!-- monthly option starts here -->
              	<div id="monthlyDiv" class="row" style="padding:10px 10px 20px 10px; ">
              		<div class="col s12 m3">
              			<label>
		                    <input id="dateRadio" type="radio" name="monthlyType" class="with-gap" value="Date">
		                    <span style="color: #1e1e1e"><b>Date</b></span>
	                    </label><br>
	                    <label>
		                    <input id="dayRadio" type="radio" name="monthlyType" class="with-gap" value="Day">
		                    <span style="color: #1e1e1e;margin-top:18px;"><b>Day</b></span>
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
        </div>
</body>
</html>