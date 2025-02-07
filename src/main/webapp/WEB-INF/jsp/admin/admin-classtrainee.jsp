<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - CLASS TRAINEE</title>
	<!-- START: CSS-HEADER -->  
	<jsp:include page="../admin/common/admin-trainee-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	 <jsp:include page="../admin/common/admin-js-header.jsp" />
	<script src="static/assets/js/admin-classtrainee.js"></script>
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
                  <c:choose>
				    <c:when test="${classInfo.classPhotoUrl==null}">
				        <img id="classPhoto" src="static/assets/images/usersimage/users.png" style="clip-path: circle(40% at 50% 50%);">
				    </c:when>  
				    <c:otherwise>
				        <img id="classPhoto" src="${photoUrl}">
				    </c:otherwise>
				</c:choose>
                  <a id="uploadPhotoLink" class="btn-floating btn-large halfway-fab waves-effect waves-light bttn"><i class="material-icons">add</i></a>
                </div>
                <form id="uploadPhotoFrm">
                	<input type="file" style="visibility:hidden;" id="fileUpload" name="fileUpload">
                </form>
              </div>
              <div>
                <h6><b>${classInfo.courseName}</b></h6>
                <p><span>${classInfo.className}</span></p>
                <c:choose>
				    <c:when test="${classInfo.deliveryMethod==1}">
				       <p><span>CBT (Individual)</span></p>
				    </c:when>
				   	<c:when test="${classInfo.deliveryMethod==2}">
				       <p><span>CBT (Modular)</span></p>
				    </c:when>  
				    <c:otherwise>
				       <p><span>Classroom Training</span></p>
				    </c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${classInfo.completionStatus==1}">
				       <p><span>Completed</span></p>
				    </c:when>
				    <c:otherwise>
				       <p><span>Incomplete</span></p>
				    </c:otherwise>
				</c:choose>
              </div>
            </div>
            <div class="col s12 m9" style="padding: 5px;">
              <div class="row" style="margin-bottom: 20px;">
                <ul class="pagination hide-on-med-and-down">
                  <li><a href="admin.classdashboard?classId=${classId}"><u>Dashboard</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classdetails?classId=${classId}"><u>Details</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classtrainee?classId=${classId}">Trainees</a></li>
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
                  <div class="right">
                    <a href="admin.class" class="btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">arrow_back</i>Back</a>
                    <a data-target="addTraineesModal" class="modal-trigger btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">add_circle</i>Add Trainees</a>
                    <c:choose>
					    <c:when test="${classInfo.deliveryMethod==3}">
					       <a href="#!" id="setAsCompletedBtn" class="btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;">Set Status as Completed</a>
					    </c:when>
					</c:choose>
                    <!--<a href="#!" class="btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">save</i>Save</a>-->
                  </div>
                </div>

                <div class="row" style="margin:20px 0 10px 0;">
                  <div class="col s6 m6">
                    <span class="left"><b>Trainee List</b></span>
                  </div>
                  <div class="col s6 m6">
                    <a href="#!"><i class="material-icons right">print</i></a>
                  </div>
                </div>

                <div class="row">
                  <table class="highlight striped">
                    <thead>
                        <tr>
                            <th>Action</th>
                            <th>Name<a href="#!"><a href="#!" class="sort-by"></a></th>
                            <th class="min">Job<a href="#!" class="sort-by"></a></th>
                            <th>User Group<a href="#!" class="sort-by"></a></th>
                            <th>Date Enrolled<a href="#!" class="sort-by"></a></th>
                            <th>Date Completed<a href="#!" class="sort-by"></a></th>
                            <th>Training Status<a href="#!" class="sort-by"></a></th>
                            <th>Comment<a href="#!" class="sort-by"></a></th>
                        </tr>
                      </thead>

                      <tbody id="ApprovedTraineeTblBody">
                        <!--<tr>
                          <td>
                            <!-- hide this checkbox for modular -->
                            <!--<label style="margin-left:10px !important;">
                              <input type="checkbox" id="editLocationStatus"/>
                              <span style="padding:10px;"></span>
                            </label>
                            <!-- upto here -->
                           <!--<a href="#!"><i class="material-icons">delete</i></a>
                          </td>
                          <td>Juan Dela Cruz</td>
                          <td class="mins">asd</td>
                          <td>asd</td>
                          <td>asd</td>
                          <td>asd</td>
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
                        </tr>-->
                      </tbody>
                  </table>
                   <div id="pagealign">
	              <div id="AppprovedTraineePagination">
	                <!-- Pagination -->
	              </div>
              </div>
                </div>

                <div class="row" style="margin-top: 20px;padding: 10px;">
                  <span><b>For Approval</b></span>
                  <table class="highlight striped">
                    <thead>
                        <tr>
                            <th>Action</th>
                            <th>Name<a href="#!" class="sort-by"></a></th>
                            <th class="mins">Job<a href="#!" class="sort-by"></a></th>
                            <th>User Group<a href="#!" class="sort-by"></a></th>
                            <th>Date of Request<a href="#!" class="sort-by"></a></th>
                            <th>Date Approved<a href="#!" class="sort-by"></a></th>
                            <th>Approved Status<a href="#!" class="sort-by"></a></th>
                        </tr>
                      </thead>

                      <tbody id="pendingTraineeTblBody">
                        <!--<tr>
                          <td>asd</td>
                          <td>Juan Dela Cruz</td>
                          <td class="mins">asd</td>
                          <td>asd</td>
                          <td>asd</td>
                          <td>asd</td>
                          <td>asd</td>
                        </tr>-->
                      </tbody>
                  </table>
                  <div id="pagealign">
	                  <div id="pendingTraineePagination">
		                <!-- Pagination -->
		              </div>
	              </div>
                </div>

                <div class="row" style="margin-top: 20px;padding: 10px;">
                  <span><b>Trainees' Grade Sheet</b></span>
                  <a href="#!" class="right"><i class="material-icons">print</i></a>
				<div class="table-wrapper">
                  <table class="highlight striped">
                  	  <thead id="gradingSheetTblHeader">
                  	  	<tr id="gradingSheetTblHeader">
                  	  	</tr>
                  	  </thead>
                      <tbody id="gradingSheetTblBody">
                      </tbody>
                  </table>
                  <div id="pagealign">
	                  <div id="gradingSheetPagination">
		                <!-- Pagination -->
		              </div>
	              </div>
                </div>  
                </div>
              </div>
              <!-- content ends here -->
            </div>
          </div>
        </div>

        <!--Modal addtrainees starts here-->

        <div id="addTraineesModal" class="modal modal-fixed-footer">
            <div class="modal-header">
              <h5>Add Trainees</h5>
            </div>
            <div id="TraineesModal" class="modal-content">

              <div>
                <span><b>Filter</b></span>
              </div>

              <div>
                <div class="row">
                  <div class="col s6 m4" style="padding-top: 15px;">
                    <label style="margin-left:10px !important;">
                      <input name="filterGroup" id="jobRoleRdBtn" type="radio" checked />
                      <span>Job Role</span>
                    </label>
                  </div>
                  <div class="col s6 m8">
                    <select id="jobRoleDropdown">
                    </select>
                  </div>
                </div>
              </div>

              <div>
                <div class="row">
                  <div class="col s6 m4" style="padding-top: 15px;">
                    <label style="margin-left:10px !important;">
                      <input name="filterGroup" id="userGroupRdBtn" type="radio" />
                      <span>User Group</span>
                    </label>
                  </div>
                  <div class="col s6 m8">
                   <select id="userGroupDropdown" disabled>
                    </select>
                  </div>
                </div>
              </div>

              <div style="padding-top:5px;" class="nav-wrapper col s12 m7 row">
                   <div class="row">
                     <div class="col m6 hide-on-med-and-down">
                     </div>
                     <div class="input-field col s12 m6">
                       <input style="padding-right: 0 !important;" id="searchTrainee" type="search" placeholder="search">
                         <label class="label-icon" for="search"><i style="margin-top:4px;" class="material-icons">search</i></label>
                       <i style="margin-top:1px;" class="material-icons">close</i>
                     </div>
                   </div>
              </div>

              <div>
                <table class="highlight responsive-table">
                  <thead id="traineeTblHeader">
                  </thead>
                  <tbody id="traineeTblBody">
                  </tbody>
                </table>
                <div id="pagealign">
	              <div id="traineePagination">
	                <!-- Pagination -->
	              </div>
              </div>
              <br><br>
              </div>

            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" id="cancelTraineeBtn"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" type="submit" name="action" id="addTraineeBtn"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>

        <!--Modal addtrainees ends here-->
        <input type="hidden" id="classId" value="${classId}">
        <input type="hidden" id="courseId" value="${classInfo.courseId}">
        <input type="hidden" id="classPhotoUrl" value="${classInfo.classPhotoUrl}">
        <input type="hidden" id="deliveryMethod" value="${classInfo.deliveryMethod}">
</body>
</html>