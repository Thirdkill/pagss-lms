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
	 <script src="static/assets/js/admin-classattendance.js"></script>
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
                  <li><a href="admin.classdetails?classId=${classId}"><u>Details</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classtrainee?classId=${classId}"><u>Trainees</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classattendance?classId=${classId}">Attendance</a></li>
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
                  <div class="right">
                    <a href="#!" class="btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">arrow_back</i>Back</a>
                    <a href="#!" class="btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">save</i>Save Attendance</a>
                  </div>
                </div>

                <div class="row">
                  <span>Schedule List</span>
                </div>

                <div class="row">
                  <div class="col s6 m6">
                    <select>
                      <option>asd</option>
                      <option>asd</option>
                      <option>asd</option>
                    </select>
                  </div>
                  <div class="col s6 m6" style="padding-top: 15px;">
                    <a href="#!" class="right"><i class="material-icons">print</i></a>
                  </div>
                </div>

                <div class="row">
                  <table class="highlight striped">
                    <thead>
                      <tr>
                        <th>Name</th>
                        <th>Attendance Status</th>
                        <th>Remarks</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>Juan Dela Cruz</td>
                        <td>
                          <select>
                            <option>asd</option>
                            <option>asd</option>
                            <option>asd</option>
                          </select>
                        </td>
                        <td>
                          <input type="text" name="">
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                
                <!-- Show only on cbt modular and indiviual attendance -->
                <div >
                  <table class="highlight striped">
                    <thead>
                      <tr>
                        <th>Name</th>
                        <th>Job Role</th>
                        <th>Date Accessed</th>
                        <th>Time Spent</th>
                        <th>Total Time Spent</th>
                        <th>Status</th>
                        <th>Remarks</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>Juan Dela Cruz</td>
                        <td>Juan Dela Cruz</td>
                        <td>Juan Dela Cruz</td>
                        <td>Juan Dela Cruz</td>
                        <td>Juan Dela Cruz</td>
                        <td>Juan Dela Cruz</td>
                        <td>
                          <input type="text" name="">
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <!-- up to here -->

              </div>
              <!-- content ends here -->
            </div>
          </div>
        </div>
        
        <input type="hidden" id="classId" value="${classInfo.classId}">
        <input type="hidden" id="courseId" value="${classInfo.courseId}">
        <input type="hidden" id="deliveryMethod" value="${classInfo.deliveryMethod}">
        <input type="hidden" id="scheduleType" value="${classInfo.scheduleType}">
        
        
</body>
</html>