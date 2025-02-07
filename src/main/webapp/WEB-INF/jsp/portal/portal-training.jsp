<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
   <head>
       <title>PAGSS</title>
      <meta name = "viewport" content = "width = device-width, initial-scale = 1">
      <!-- START: CSS-HEADER -->  
	  <jsp:include page="../portal/common/css-header.jsp" />
      <!-- END: CSS-HEADER -->
       
      <!-- START: JS-HEADER -->
      <jsp:include page="../portal/common/js-header.jsp" />
      <script src="static/assets/js/portal-training.js"></script>
      <!-- END: JS-HEADER -->
   </head>
  
   <body >
     <!-- Header -->
	<jsp:include page="../portal/common/portal-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../portal/common/portal-sidenav.jsp" />
        <div class="container">
          <div class="row">
            <div class="col s12 m12">
               <h4>My Trainings</h4>
            </div>
          </div>

          <div class="row">
            <hr style="width:100%;">
          </div>

          <div class="row" style="margin-bottom: 20px;">
            <ul class="pagination hide-on-med-and-down">
              <li><a href="mytraining-calendar"><u>Calendar</u></a></li>
              <li style="font-size: 18px;">|</li>
              <li><a href="mytraining-inprogress" id="inProgressHref"></a></li>
              <li style="font-size: 18px;">|</li>
              <li><a href="mytraining-completed" id="completeHref"></a></li>
              <li style="font-size: 18px;">|</li>
              <li><a href="mytraining-catalog"><u>Training Catalog</u></a></li>
            </ul>
          </div>

          <div class="row" id="classDiv">
            <!--<div class="col s12 m4">
              <div class="divcard">
                <h6 style="margin: 0;"><b>Passenger Handling</b></h6>
                <p><label><span>Class 101</span></label></p>
                <p><label><span>Classroom Training</span></label></p>
                <a href="#!" class="btn bttn waves-effect waves-light" type="submit" style="width: 100%;">Start</a>
              </div>
            </div>
            
            <div class="col s12 m4">
              <div class="divcard">
                <h6 style="margin: 0;"><b>Passenger Handling</b></h6>
                <p><label><span>Class 101</span></label></p>
                <p><label><span>CBT (Individual)</span></label></p>
                <a href="#!" class="btn bttn waves-effect waves-light" type="submit" style="width: 100%;">Resume</a>
              </div>
            </div>

            <div class="col s12 m4">
              <div class="divcard">
                <h6 style="margin: 0;"><b>Passenger Handling</b></h6>
                <p><label><span>Class 101</span></label></p>
                <p><label><span>CBT (Modular)</span></label></p>
                <a href="#!" class="btn bttn waves-effect waves-light" type="submit" style="width: 100%;">Resume</a>
              </div>
            </div>
          </div>-->
            
        </div>
	<input type="hidden" id="userId" value="${user.userId}"/>
	<input type="hidden" id="status" value="${status}"/>
   </body>
</html>