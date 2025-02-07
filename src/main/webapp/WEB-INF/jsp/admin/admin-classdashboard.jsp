<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - CLASS DASHBOARD</title>
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
                  <img src="static/assets/images/usersimage/users.png" style="clip-path: circle(40% at 50% 50%);">
                  <a class="btn-floating btn-large halfway-fab waves-effect waves-light bttn"><i class="material-icons">add</i></a>
                </div>
              </div>
              <div>
                <h6><b>Passenger Handling</b></h6>
                <p><span>Class 101</span></p>
                <p><span>Classroom Training</span></p>
                <p><span>Status</span></p>
              </div>
            </div>
            <div class="col s12 m9" style="padding: 5px;">
              <div class="row" style="margin-bottom: 20px;">
                <ul class="pagination hide-on-med-and-down">
                  <li><a href="admin.classdashboard?classId=${classId}">Dashboard</a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classdetails?classId=${classId}"><u>Details</u></a></li>
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
                </ul>
              </div>
              <!-- content starts here -->
              <div class="divcard">
                <div class="row">
                  <div class="col s12 m8">
                    <div class="divcard">
                      <legend style="margin-bottom: 10px;"><b>Schedule</b></legend>
                      <p>Lorem Ipsum</p>
                    </div>
                    <div style="margin-top: 20px;">
                      Location: <span>Lorem ipsum</span>
                    </div>
                    <div>
                      <p><b>Trainer</b></p>
                    </div>
                    <div class="row" style="margin-bottom: 20px;">
                      <div class="col s12 m4" style="text-align: center;">
                        <img src="static/assets/images/usersimage/user.png" style="height: 150px;width: 150px;">
                      </div>
                      <div class="col s12 m8">
                        <p>Lorem ipsum</p>
                        <p>Lorem ipsum</p>
                        <p>Lorem ipsum</p>
                      </div>
                    </div>
                    <div class="divcard">
                      <legend style="margin-bottom: 10px;"><b>Checklist</b></legend>
                      <p>
                        <label>
                          <input type="checkbox" name="">
                          <span style="color: #1e1e1e">not selected</span>
                        </label>
                      </p>
                      <p>
                        <label>
                          <input type="checkbox" name="">
                          <span style="color: #1e1e1e">selected</span>
                        </label>
                      </p>
                      <p>
                        <label>
                          <input type="checkbox" name="">
                          <span style="color: #1e1e1e">not selected</span>
                        </label>
                      </p>
                      <p>
                        <label>
                          <input type="checkbox" name="">
                          <span style="color: #1e1e1e">selected</span>
                        </label>
                      </p>
                      <p>
                        <label>
                          <input type="checkbox" name="">
                          <span style="color: #1e1e1e">not selected</span>
                        </label>
                      </p>
                      <p>
                        <label>
                          <input type="checkbox" name="">
                          <span style="color: #1e1e1e">selected</span>
                        </label>
                      </p>
                      <p>
                        <label>
                          <input type="checkbox" name="">
                          <span style="color: #1e1e1e">not selected</span>
                        </label>
                      </p>
                      <p>
                        <label>
                          <input type="checkbox" name="">
                          <span style="color: #1e1e1e">selected</span>
                        </label>
                      </p>
                    </div>
                  </div>
                  <div class="col s12 m4">
                    <div class="divcard" style="clear: both;">
                      <h3 class="left" style="margin: 0;">25</h3>
                      <h5 style="text-align: center;vertical-align: middle;">Enrolled Trainees</h5>
                    </div>
                    <div class="divcard" style="clear: both;">
                      <input type="date" class="datepicker" name="">
                      <label></label>
                      <h3 class="left" style="margin: 0;">25</h3>
                      <h5 style="text-align: center;vertical-align: middle;">Present Trainees</h5>
                    </div>
                    <div class="divcard" style="clear: both;">
                      <select>
                        <option>Exam 101</option>
                        <option>Exam 102</option>
                        <option>Exam 103</option>
                      </select>
                      <h3 class="left" style="margin: 0;">25%</h3>
                      <h5 style="text-align: center;vertical-align: middle;">Average Score</h5>
                    </div>
                    <div class="divcard" style="clear: both;">
                      <select>
                        <option>Exam 101</option>
                        <option>Exam 102</option>
                        <option>Exam 103</option>
                      </select>
                      <h3 class="left" style="margin-top: 10px;">25</h3>
                      <h5 style="text-align: center;vertical-align: middle;">Average Exam Time</h5>
                    </div>
                    <div class="divcard" style="clear: both;">
                      <select>
                        <option>Exam 101</option>
                        <option>Exam 102</option>
                        <option>Exam 103</option>
                      </select>
                      <h3 class="left" style="margin: 0;">25%</h3>
                      <h5 style="text-align: center;vertical-align: middle;">Exam Passers</h5>
                    </div>
                  </div>
                </div>
              </div>
              <!-- content ends here -->
            </div>
          </div>
        </div>
      <input type="hidden" id="classId" value="${classId}">  
</body>
</html>