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
	 <script src="static/assets/js/admin-classexam.js"></script>
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
                  <li><a href="admin.classexam?classId=${classId}">Exams</a></li>
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
                    <a href="admin.class" class="btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">arrow_back</i>Back</a>
                    <a href="admin.examination?action=create&module=classexam&classId=${classId}" class="btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">add_circle</i>Create New Exams</a>
                  </div>
                </div>

                <div class="row" style="margin-top: 20px;padding: 10px;">
                  <span><b>Class Exams</b></span>
                </div>

                <div class="row">
                  <table class="striped highlight">
                    <thead>
                      <tr>
                        <th>Exam Title</th>
                        <th>Exam Type</th>
                        <th>Description</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody id="classExamTblBody">
                    </tbody>
                  </table>
                  	<div id="pagealign">
	              		<div id="classExamTblPagination">
	                  	<!--Pagination-->
	               		</div>
              		</div>
                </div>

                <div class="row" style="margin-top: 20px;padding: 10px;">
                  <span><b>Course Exams</b></span>
                </div>

                <div class="row">
                  <table class="striped highlight">
                    <thead>
                      <tr>
                        <th>Exam Title</th>
                        <th>Exam Type</th>
                        <th>Description</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
	                    <tbody id="courseExamTblBody">
	                      <tr>
	                        <td>asd</td>
	                        <td>Passenger Handling Basics</td>
	                        <td>Document</td>
	                        <td>
	                          <a href="#!"><i class="material-icons">edit</i></a>
	                        </td>
	                      </tr>
	                    </tbody>
                  	</table>
                  	<div id="pagealign">
	              		<div id="courseExamTblPagination">
	                  	<!--Pagination-->
	               		</div>
              		</div>
                </div>

                <div class="row" style="margin-top: 20px;">
                  <div class="col s12 m4" style="padding-top: 25px;">
                     <span><b>Avalable Exams</b></span>
                  </div>
                  <div style="padding-top:5px;" class="nav-wrapper col s12 m8 row">
                     <div class="col s12">
                        <div class="row">
	                    <div style="margin-top:3px;" class="input-field col s6 m7">
	                       <input id="searchExamTxtBox" type="search" name="" placeholder="Search Exam">
	                       <label class="label-icon" for="search" ><i style="margin-top:4px;" class="material-icons">search</i></label>
	                       <i id="clearSearch" style="margin-top:1px;" class="material-icons" id="clearSearch">close</i>
	                    </div>
	                    <div style="margin:0;" class="input-field col s6 m5">
	                       <a id="addClassExamBtn" style="width:100%;text-transform:capitalize;" class="btn modal-trigger waves-effect waves-light bttn" name="" ><i class="material-icons left">add_circle</i>Add</a>
	                    </div>
	                 </div>
                     </div>
                  </div>
                </div>

                <div class="row">
                  <table class="striped highlight">
                    <thead>
                      <tr>
                        <th></th>
                        <th>Exam Title</th>
                        <th>Exam Type</th>
                        <th>Description</th>
                        <th></th>
                      </tr>
                    </thead>
                    <tbody id="examTblBody">
                    </tbody>
                  </table>
                  	<div id="pagealign">
	              		<div id="examTblPagination">
	                  	<!--Pagination-->
	               		</div>
              		</div>
                </div>

                <div class="row" style="margin-top: 20px;">
                  <div class="col s12 m8" style="padding-top: 15px;">
                     <span><b>Trainees' Exam Score</b></span>
                  </div>
                  <div style="padding-top:5px;" class="col s12 m4">
                     <div class="col s12 m10">
                       <select id="examScoreViewByDropdown">
                       </select>
                     </div>
                     <div class="col s12 m2">
                       <a href="#!"><i class="material-icons" style="padding-top: 10px;">print</i></a>
                     </div>
                  </div>
                </div>

                <div class="row">
                  <table class="striped highlight">
                    <thead>
                      <tr>
                        <th></th>
                        <th>Full Name</th>
                        <th>Employee Number</th>
                        <th>Job Role</th>
                        <th>Date Finished</th>
                        <th>Total Score</th>
                        <th>Status</th>
                      </tr>
                    </thead>
                    <tbody id="traineeExamScoreTblBody">
                    </tbody>
                  </table>
                  	<div id="pagealign">
	              		<div id="traineeExamScoreTblPagination">
	                  	<!--Pagination-->
	               		</div>
              		</div>
                </div>

              </div>
              <!-- content ends here -->
            </div>
          </div>
        </div>

        <!--Modal exam settings starts here-->

        <div id="examSettingsModal" class="modal modal-fixed-footer">
            <div class="modal-header">
              <h5>Exam Settings</h5>
            </div>
            <div class="modal-content" style="overflow-y:scroll;height:73%;">
              <div class="content-row">
                <div>
                  <b>Duration</b>
                    <input id="tbClassDuration" type="text" name="">
                  <b>Passing Score</b>
                    <input id="tbPassingScore" type="text" name="">
                  <b>Exam Retake</b>
                  <div>
                    <label>
                      <input id="chOnlyFailed" type="checkbox" name="">
                      <span style="color: #1e1e1e">Only When Failed</span>
                    </label><br>
                    <label>
                      <input id="chIfPassed" type="checkbox" name="">
                      <span style="color: #1e1e1e">Even if passed (The system will take the highest score)</span>
                    </label><br>
                    <span style="padding-right: 10px;">No. of Retake </span><input id="tbExamRetake" type="text" name="" style="width: 100px;">
                  </div>
                  <div class="switch">
                    <b>Take exam in safe Exam Browser</b>
                    <label>
                      <input id="chIsSafeBrowser" type="checkbox">
                      <span class="lever"></span>
                    </label>
                  </div>
                  <div style="margin-top: 20px;">
                    <b>Completion</b><br>
                    <label>
                      <input id="chShowCorrectAnswer" type="checkbox" name="">
                      <span style="color: #1e1e1e">Show Correct Answer</span>
                    </label><br>
                    <label>
                      <input id="chShowScore" type="checkbox" name="">
                      <span style="color: #1e1e1e">Show Score</span>
                    </label><br>
                    <label>
                      <input id="chShowBreakdown" type="checkbox" name="">
                      <span style="color: #1e1e1e">Show Score Breakdown</span>
                    </label>
                  </div>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <a id="btnCancelExamSettings" class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
              <a id="btnSaveExamSettings" class="btn waves-effect waves-light bttn" type="submit" name="action" id="saveLocationBtn"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>
		
		<input type="hidden" id="classId" value="${classInfo.classId}">
		<input type="hidden" id="classExamId" value="">
        <input type="hidden" id="tbCourseId" value="${classInfo.courseId}">
        <input type="hidden" id="deliveryMethod" value="${classInfo.deliveryMethod}">
        <input type="hidden" id="scheduleType" value="${classInfo.scheduleType}">
        <!--Modal exam settings ends here-->
        
        
</body>
</html>