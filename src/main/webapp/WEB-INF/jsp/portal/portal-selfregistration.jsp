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
      <script src="static/assets/js/portal-selfregistration.js"></script>
      <!-- END: JS-HEADER -->
   </head>
  
   <body >
     <!-- Header -->
	<jsp:include page="../portal/common/portal-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../portal/common/portal-sidenav.jsp" />
	<div class="container1">
            <div class="divcard">

               <div class="row">
                  <div class="col s12 m3">
                     <c:choose>
                     	<c:when test="${classPhotoUrl != ''}">
                     	 <img src="${classPhotoUrl}">
                     	</c:when>
                     	<c:otherwise>
                     	 <img src="static/assets/images/usersimage/users.png">
                     	</c:otherwise>
                     </c:choose>
                  </div>
                  <div class="col s12 m5">
                     <div>
                        <span style="color: #1e88e5;font-family: 'Staatliches', cursive;font-size: 1.64rem;margin-right: 10px;">${classInfo.courseName}</span>(${classInfo.courseName})
                        <hr>
                        <div class="row">
                           <div class="col s12 m6 mySpan">
                              <span id=deliveryMethod></span><br>
                              <span id="trainingCategory">${classInfo.categoryName}</span><br>
                              <span id="className">${classInfo.className}</span><br>
                              <span id="location">Location: ${classInfo.locationName}</span><br>
                              <span id="trainer">Trainer: ${classInfo.trainerName}</span>
                           </div>
                           <div class="col s12 m6">
                              <a id="joinBtn" href="#!" class="bttn btn waves-light waves-effect right">Join Class</a>
                           </div>
                        </div>
                     </div>
                  </div>
                  <div class="col s12 m4">
                     <div class="divcard">
                        <legend style="margin-bottom: 10px;"><b>Class Schedule</b></legend>
                        <div id="classScheduleDiv">
                        </div>
                     </div>
                  </div>
               </div>

               <div class="row">
                  <div class="col s12 m6">
                     <div class="divcard">
                        <legend style="margin-bottom: 10px;"><b>Course Description</b></legend>
                        <p>${classInfo.description}</p>
                     </div>
                  </div>
                  <div class="col s12 m6">
                     <div class="divcard">
                        <legend style="margin-bottom: 10px;"><b>Course Objectives</b></legend>
                        <p>${classInfo.objective}</p>
                     </div>
                  </div>
               </div>

            </div>
         </div>

      </div>
      <input type="hidden" id="classId" value="${classInfo.classId}">
      <input type="hidden" id="deliveryMethodHdn" value="${classInfo.deliveryMethod}">
      <input type="hidden" id="employeeIdHdn" value="${employeeId}">
   </body>
</html>  