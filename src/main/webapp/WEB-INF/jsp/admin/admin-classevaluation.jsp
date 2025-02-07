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
	 <script src="static/assets/js/admin-classevaluation.js"></script>
	<!-- END: JS-HEADER -->
</head>
<body>
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
                  <li><a href="admin.classevaluation?classId=${classId}">Evaluation</a></li>
                </ul>
              </div>
              <!-- content starts here -->
              <div class="divcard">

                <div class="row">
                  <div class="right">
                    <a href="admin.class" class="btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">arrow_back</i>Back</a>
                    <a id="btnSaveEvalSettings" href="#!" class="btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">save</i>Save Settings</a>
                    <a href="admin.createevaluation?classId=${classInfo.classId}" class="btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">add_circle</i>Create New Evaluation</a>
                  </div>
                </div>

                <div class="row" style="padding: 10px;margin-top: 30px;">
                  <h6><b>Evaluation Settings</b></h6>
                  <hr>
                  <div>
                    <p>
                      <label>
                        <input id="chIsRequired" type="checkbox" name="">
                        <span style="color: #1e1e1e">Required</span>
                      </label>
                    </p>
                    <p>
                      <label>
                        <input id="chIsAnonymousSender" type="checkbox" name="">
                        <span style="color: #1e1e1e">Anonymous sender</span>
                      </label>
                    </p>
                  </div>
                </div>

                <div class="row" style="padding: 10px;">
                  <h6><b>Class Evaluation</b></h6>
                  <hr>
                  <table>
                    <tbody id="classEvalTblBody">
                      <tr>
                        <td class="mins">
                          <a href="#!"><i class="material-icons">edit</i></a>
                          <a href="#!"><i class="material-icons">delete</i></a>
                          <td style="text-align: left">Evaluation Sheet for Mandatory Trainings</td>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                  	<div id="pagealign">
	              		<div id="classEvalTblPagination">
	                  	<!--Pagination-->
	               		</div>
              		</div>
                </div>

                <div class="row" style="padding: 10px;">
                  <h6><b>Course Evaluation</b></h6>
                  <hr>
                  <table>
                    <tbody id="courseEvalTblBody">
                      <tr>
                        <td class="mins">
                          <a href="#!"><i class="material-icons">edit</i></a>
                          <td style="text-align: left">Evaluation Sheet for Mandatory Trainings</td>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                  	<div id="pagealign">
	              		<div id="courseEvalTblPagination">
	                  	<!--Pagination-->
	               		</div>
              		</div>
                </div>

                <div class="row">
                  <div class="col s12 m4">
                     <h6 style="margin-top: 30px;"><b>Available Evaluation</b></h6>
                  </div>
                  <div style="padding-top:5px;" class="nav-wrapper col s12 m8">
                    <div class="row">
                       <div style="margin-top:19px;" class="input-field col s6 m7">
                          <input id="searchEvalTxtBox" type="search" name="" placeholder="Search Evaluation">
                          <label class="label-icon" for="search"><i style="margin-top:4px;" class="material-icons">search</i></label>
                          <i id="clearSearch" style="margin-top:1px;" class="material-icons">close</i>
                       </div>
                       <div class="input-field col s6 m5">
                          <a id="addClassEvalBtn" href="#!" style="width:100%;text-transform:capitalize;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">add_circle</i>Add as Class Evaluation</a>
                       </div>
                    </div>
                  </div>
                </div>

                <div>
                  <hr style="width:100%;margin-top: 0;">
                  <table>
                   <tbody id="evalTblBody">
                     <tr>
                        <td class="mins">
                          <label>
                            <input type="checkbox" name="">
                            <span></span>
                          </label>
                        </td>
                        <td style="text-align: left;">
                         asd
                        </td>
                      </tr>
                    </tbody>
                  </table>
                  	<div id="pagealign">
	              		<div id="evalTblPagination">
	                  	<!--Pagination-->
	               		</div>
              		</div>
                </div>

                <div class="row" style="margin-top: 20px;">
                  <div class="col s12 m8" style="padding-top: 20px;">
                     <span><b>Trainees' Evaluation</b></span>
                  </div>
                  <div style="padding-top:5px;" class="col s12 m4">
                     <div class="col s12 m10">
                       <select id="employeeEvalViewByDropdown">
                         <option>Class/Course Evaluation</option>
                         <option>asd</option>
                         <option>asd</option>
                       </select>
                     </div>
                     <div class="col s12 m2">
                       <a href="#!"><i class="material-icons" style="padding-top: 10px;">print</i></a>
                     </div>
                  </div>
                </div>

                <div class="row" style="padding: 10px;">
                  <hr style="width:100%;margin-top: 0;">
                  <table>
                    <thead>
                      <tr>
                        <th></th>
                        <th>Full Name</th>
                        <th>Employee Number</th>
                        <th>Job Role</th>
                        <th>Date Finished</th>
                      </tr>
                    </thead>
                    <tbody id="traineeEvalTblBody">
                      <tr>
                        <td>
                        <!-- Hide this part on trainee evaluation -->
                        <a href="#!"><i class="material-icons">remove_red_eye</i></a>
                        <!-- up to here -->
                        <a href="#!"><i class="material-icons">file_download</i></a>
                        </td>
                        <td>asd</td>
                        <td>asd</td>
                        <td>asd</td>
                        <td>asd</td>
                      </tr>
                    </tbody>
                  </table>
                  	<div id="pagealign">
	              		<div id="traineeEvalTblPagination">
	                  	<!--Pagination-->
	               		</div>
              		</div>
                </div>
               

              </div>
              <!-- content ends here -->
            </div>
          </div>
        </div>
        
        <input type="hidden" id="classId" value="${classInfo.classId}">
        <input type="hidden" id="tbCourseId" value="${classInfo.courseId}">
        <input type="hidden" id="deliveryMethod" value="${classInfo.deliveryMethod}">
        <input type="hidden" id="scheduleType" value="${classInfo.scheduleType}">
        
        <input type="hidden" id="isRequired" value="${classInfo.isEvaluationRequired}">
        <input type="hidden" id="isAnonymousSender" value="${classInfo.isAnonymousSender}">
        
</body>
</html>