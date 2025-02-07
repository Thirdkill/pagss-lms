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
      <script src="static/assets/js/portal-trainingcalendar.js"></script>
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
              <li><a href="portal-trainingcalendar.html">Calendar</a></li>
              <li style="font-size: 18px;">|</li>
              <li><a href="mytraining-inprogress" id="inProgressHref"><u>In Progress</u></a></li>
              <li style="font-size: 18px;">|</li>
              <li><a href="mytraining-completed" id="completeHref"><u>Completed</u></a></li>
              <li style="font-size: 18px;">|</li>
              <li><a href="mytraining-catalog"><u>Training Catalog</u></a></li>
            </ul>
          </div>

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
                  <div id='trainingCalendar' class="fc fc-unthemed fc-ltr"></div>
                </div>
              </div>
              <!-- Calendar ends here -->
            </div>
          </div>
            
        </div>
	<input type="hidden" id="userId" value="${user.userId}"/>
   </body>
</html>