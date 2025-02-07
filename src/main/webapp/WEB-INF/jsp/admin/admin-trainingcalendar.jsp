<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - TRAINING CALENDAR</title>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
	<script src="static/assets/js/admin-trainingcalendar.js"></script>
	<!-- END: JS-HEADER -->
</head>
<body>
	<!-- Header -->
	<jsp:include page="../admin/common/admin-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-sidebar.jsp" />
	<div class="container2">
          <div class="row">
            <div class="col s12 m12">
               <h4>Training Calendar</h4>
            </div>
          </div>

          <div class="row">
            <hr style="width:100%;">
          </div>

          <!--Search Filter starts here-->
          <div class="row">
            <div class="divcard">
              <div class="row">
                <div class="col s6 m4">
                  <b>Trainer</b>
                  <select>
                    <option selected="">All</option>
                    <option>asd</option>
                    <option>asd</option>
                  </select>
                </div>
                <div class="col s6 m4">
                  <b>Delivery Method</b>
                  <select>
                    <option selected="">All</option>
                    <option>asd</option>
                    <option>asd</option>
                  </select>
                </div>
                <div class="col s10 m4">
                  <b>Location</b>
                  <select>
                    <option selected="">All</option>
                    <option>asd</option>
                    <option>asd</option>
                  </select>
                </div>
              </div>
              <div class="row">
                <div class="right buttonright">
                  <a class="btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">refresh</i>Reset</a>
                  <a class="btn waves-effect waves-light bttn" type="submit" name="action"><i class="material-icons left">search</i>Search</a>
                </div>
              </div>
            </div>
          </div>
          <!--Search Filter ends here-->

          <div class="row">
            <div class="divcard">
              <!-- Calendar starts here -->
                <div class="row">
	                <div class="col s12 m3">
	                  <div class="divcard" style="padding: 5px;">
	                    <legend style="margin-bottom: 10px;"><b>Class Details</b></legend>
	                  </div>
	                </div>
	                <div class="col s12 m9">
	                  <div id='calendar' class="fc fc-unthemed fc-ltr"></div>
	                </div>
	              </div>
              <!-- Calendar ends here -->
            </div>
          </div>
            
        </div>
</body>
</html>