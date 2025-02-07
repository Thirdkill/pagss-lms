<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - User</title>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
	<script src="static/assets/js/admin-questions.js"></script>
	<!-- END: JS-HEADER -->
</head>
<body>
	<!-- Header -->
	<jsp:include page="../admin/common/admin-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-sidebar.jsp" />
	
	<div class="container2">
          <div class="row">
            <div class="col s12 m9">
               <h4>User Details</h4>
            </div>
          </div>

          <div class="row">
            <hr style="width:100%;">
          </div>

          <div class="row">
            <div class="divcard">
              <div class="row">
                <div>
                  <ul class="pagination">
                    <li><a href="admin-viewuser.html"><u>Details</u></a></li>
                    <li style="font-size: 18px;">|</li>
                    <li><a href="admin-viewuserjobRole.html"><u>Training Flow (Job Role)</u></a></li>
                    <li style="font-size: 18px;">|</li>
                    <li><a href="admin-view-user-userGroup.html"><u>Training Flow (User Group)</u></a></li>
                    <li style="font-size: 18px;">|</li>
                    <li><a href="admin-traininghistory.html" class="active">Training History</a></li>
                  </ul>
                </div>
              </div>
              <div class="row nav-wrapper" style="margin: 15px 0;">
                <div class="input-field col s6 m7">
                </div>
                <div style="margin-top:19px;" class="input-field col s6 m5">
                  <input id="searchLocation" type="search" name="" placeholder="search">
                  <label class="label-icon" for="search"><i style="margin-top:4px;" class="material-icons">search</i></label>
                  <i style="margin-top:1px;" class="material-icons">close</i>
                </div>
              </div>
              <div class="row">
                <table class="highlight centered">
                  <thead>
                    <tr>
                        <th>Certificate</th>
                        <th>Course</th>
                        <th>Delivery Method</th>
                        <th>Training Category</th>
                        <th>Class</th>
                        <th>Trainer</th>
                        <th>Date Completed</th>
                    </tr>
                  </thead>
                  <tbody style="padding: 5px !important;">
                    <tr>
                      <td>
                        <a href="#!"><i class="material-icons">file_download</i></a>
                      </td>
                      <td>Refresher</td>
                      <td>Basic Cargo Skills</td>
                      <td>CC001</td>
                      <td>N/A</td>
                      <td>asd</td>
                      <td>asd</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
            
        </div>
</body>
</html>