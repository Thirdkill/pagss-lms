<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - Add Course</title>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
	<jsp:include page="../admin/common/admin-tinymce-header.jsp" />
	 <script src="static/assets/js/utility/tinymce-util.js"></script>
	<script src="static/assets/js/admin-createtrainingcourse.js"></script>
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
               <h4>Add Course</h4>
            </div>
            <div id="navpad" class="nav-wrapper col s12 m4 row">
              <div class="row">
                 <div class="input-field col s6 m6">
                    <a href="admin.trainingcourse" style="width:100%;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">not_interested</i>Cancel</a>
                 </div>
                 <div class="input-field col s6 m6">
                    <a id="saveCourseBtn" style="width:100%;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">save</i>Save</a>
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
                <div class="col s12 m5">
                  <b>Training Category</b>
                  <select id="trainingCategoryDropDown">
                    <option disabled selected>Choose an option</option>
                  </select>
                </div>
                <div class="col s10 m6">
                  <b>Delivery Method</b>
                  <select id="deliveryMethodDropdown">
                    <option disabled selected>Choose an option</option>
                  </select>
                </div>
                <div class="col s2 m1 switch left">
                  <b>Status</b>
                  <label>
                    <input type="checkbox" id="courseStatus" checked>
                    <span style="" class="lever left"></span>
                  </label>
                </div>
              </div>
              <div class="row">
                <div class="col s12 m5">
                  <b>Course Code</b>
                  <input type="text" name="" id="courseCode">
                </div>
                <div class="col s10 m7">
                  <b>Course Name</b>
                  <input type="text" name="" id="courseName">
                </div>
              </div>
              <div style="padding-top: 10px;" class="row">
                <div class="col s12 m6">
                  <div>
                    <b>Course Description</b>
                    <div id="courseDescriptionEditor">
                    </div>
                  </div>
                </div>
                <div class="col s12 m6">
                  <div>
                    <b>Course Objectives</b>
                    <div id="courseObjectivesEditor">
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="divcard">
              <legend style="margin-bottom: 10px;"><b>Class Defaults</b></legend>
              <div class="row">
                <div class="col s12 m5">
                  <b>Location</b>
                  <select id="locationDropdown">
                    <option disabled selected>Choose an option</option>
                  </select>
                </div>
                <div class="col s2 m7 switch left">
                  <div class="row">
                    <div class="col s4 m4" style="text-align: center;">
                      <b>Self Registration</b><br>
                      <label>
                        <input type="checkbox" id="isSelfRegistration">
                        <span class="lever"></span>
                      </label>
                    </div>
                    <div class="col s4 m4" style="text-align: center;">
                      <b>Send Certificate</b><br>
                      <label>
                        <input type="checkbox" id="withCertificate">
                        <span class="lever"></span>
                      </label>
                    </div>
                    <div class="col s4 m4" style="text-align: center;">
                      <b>Exam</b><br>
                      <label>
                        <input type="checkbox" id="withExam" checked>
                        <span class="lever" style="margin-left: 15px !important;"></span>
                      </label>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col s12 m5">
                  <b>Minimum Attendees</b>
                  <input type="text" name="" id="minAttendees" class="numbersOnly">
                </div>
                <div class="col s10 m7">
                  <b>Maximum Attendees</b>
                  <input type="text" name="" id="maxAttendees" class="numbersOnly">
                </div>
              </div>
              <div class="row">
                <div class="col s12 m5">
                  <b>Trainer</b>
                  <select id="trainerDropdown">
                    <option disabled selected>Choose an option</option>
                  </select>
                </div>
                <div class="col s10 m7">
                  <b>Schedule Type</b>
                  <select id="scheduleTypeDropdown">
                  </select>
                </div>
              </div>
              <div class="row" style="margin-top: 10px;padding: 10px;">
              	<form id="frmUploadPhoto">
                <div class="file-field input-field" style="margin-top: 0 !important;">
                  <div class="btn bttn right" style="margin-top: 20px;">
                    <span>Browse</span>
                    <input type="file" name="fileUpload" id="fileUpload">
                  </div>
                  <div class="file-path-wrapper" style="padding-top: 0 !important;">
                    <b>Display Photo</b>
                    <input class="file-path validate" name="uploadPhoto" id="uploadPhoto" type="text">
                  </div>
                </div>
                </form>
              </div>
            </div>
          </div>
        </div>
</body>
</html>