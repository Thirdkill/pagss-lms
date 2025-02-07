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
                  <img src="../../resources/images/usersimage/user.png">
                  <a class="btn-floating btn-large halfway-fab waves-effect waves-light bttn"><i class="material-icons">add</i></a>
                </div>
              </div>
              <div>
                <h6><b>Passenger Handling</b></h6>
                <p><label><span>Class 101</span></label></p>
                <p><label><span>Classroom Training</span></label></p>
                <p><label><span>Status</span></label></p>
              </div>
            </div>
            <div class="col s12 m9" style="padding: 5px;">
              <div class="row" style="margin-bottom: 20px;">
                <ul class="pagination hide-on-med-and-down">
                  <li><a href="individual-dashboard.html">Dashboard</a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="individual-details.html"><u>Details</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="individual-classroomtraining.html"><u>Trainees</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="individual-attendance.html"><u>Attendance</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="individual-settings.html"><u>Settings</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="individual-materials.html"><u>Materials</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="individual-exams.html"><u>Exams</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="individual-financials.html"><u>Financials</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="individual-evaluations.html"><u>Evaluation</u></a></li>
                </ul>
              </div>
              <!-- content starts here -->
              <div class="divcard">
                <div class="row">
                  <div class="col s12 m6">
                    <div class="divcard">
                      <legend style="margin-bottom: 10px;"><b>Schedule</b></legend>
                      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                      </p>
                    </div>
                    <div class="divcard" >
                      <h3 class="left" style="margin: 0;">25<i class="material-icons" style="font-size: 40px;">group</i></h3>
                      <h5 style="text-align: center;vertical-align: middle;">Enrolled Trainees</h5>
                    </div>
                    <div class="divcard">
                      <h3 class="left" style="margin: 0;">25%</h3>
                      <h5 style="text-align: center;vertical-align: middle;">Average Score</h5>
                    </div>
                    <div class="divcard">
                      <div class="left">
                        <h3 style="margin-top: 0;margin-bottom: 0;">25<i class="material-icons" style="font-size: 40px;">schedule</i></h3>
                        <span style="color: #aeaeae">minutes</span>
                      </div>
                      <h5 style="text-align: center;vertical-align: middle;">Average Training Time</h5>
                    </div>
                  </div>
                  <div class="col s12 m6">
                    <div>
                      Duration: <span>Lorem ipsum</span>
                    </div>
                    <div>
                      <p><b>Trainer</b></p>
                    </div>
                    <div class="row" style="margin-bottom: 20px;">
                      <div class="col s12 m4" style="text-align: center;">
                        <img src="../../resources/images/usersimage/user.png" style="height: 150px;width: 150px;">
                      </div>
                      <div class="col s12 m8">
                        <p>Lorem ipsum</p>
                        <p>Lorem ipsum</p>
                        <p>Lorem ipsum</p>
                      </div>
                    </div>
                    <div class="divcard">
                      <legend style="margin-bottom: 10px;"><b>Exam Analysis</b></legend>
                      <div style="margin-bottom: 20px;">
                        <select>
                          <option>Exam 101</option>
                          <option>Exam 101</option>
                          <option>Exam 101</option>
                        </select>
                      </div>
                      <div class="divcard">
                        <h3 class="left" style="margin: 0;">85%</h3>
                        <h5 style="text-align: center;vertical-align: middle;">Exams Passers</h5>
                      </div>
                      <div class="divcard">
                        <h3 class="left" style="margin: 0;">85%</h3>
                        <h5 style="text-align: center;vertical-align: middle;">Average Score</h5>
                      </div>
                      <div class="divcard">
                        <div class="left">
                          <h3 style="margin-top: 0;margin-bottom: 0;">25<i class="material-icons" style="font-size: 40px;">schedule</i></h3>
                          <span style="color: #aeaeae">minutes</span>
                        </div>
                        <h5 style="text-align: center;vertical-align: middle;">Average Training Time</h5>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <!-- content ends here -->
            </div>
          </div>
        </div>
        
</body>
</html>