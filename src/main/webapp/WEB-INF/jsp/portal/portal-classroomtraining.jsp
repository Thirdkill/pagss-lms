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
      <script src="static/assets/js/portal-classroomtraining.js"></script>
      <!-- END: JS-HEADER -->
   </head>
  
   <body >
     <!-- Header -->
	<jsp:include page="../portal/common/portal-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../portal/common/portal-sidenav.jsp" />
	
	<div class="container1" style="margin-top: 20px;">
          <div class="divcard">

            <div class="row" >
              <div class="col s12 m4" style="text-align: center;">
                <c:choose>
                	<c:when test="${classPhotoUrl != ''}">
                		<img src="${classPhotoUrl}">
                	</c:when>
                	<c:otherwise>
                     	 <img src="static/assets/images/usersimage/users.png">
                    </c:otherwise>
                </c:choose>
              </div>
              <div class="col s12 m4">
                <span style="color:#1e88e5;font-family: 'Staatliches', cursive;font-size: 1.64rem; ">${classInfo.courseName}</span> <span>(${classInfo.courseCode})</span>
                <hr>
                <div class="row">
                  <div class="col s12 m6">
                    <span>Classroom Training</span><br>
                    <span>${classInfo.categoryName}</span><br>
                    <span>${classInfo.className}</span><br>
                    <span>Location: ${classInfo.locationName}</span><br>
                    <span>Trainer: ${classInfo.trainerName}</span>
                  </div>
                  <div class="col s12 m6">
                    <a href="#!" class="bttn btn waves-effect waves-light" type="submit" style="width: 100%;">Evaluate Class</a>
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

            <div class="row" style="margin-top: 20px;">
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

            <div class="row" id="documentViewer">
              <!-- White space -->
              <center><iframe id="documentViewerFrame" width='1000' height='700' allowfullscreen webkitallowfullscreen></iframe></center>
            </div>

            <div class="row">
              <h5>Learning Materials</h5>
              <hr>
            </div>

            <div class="multiple-items" style="background-color: #aeaeae;" id="learningMaterialsSlider">
            </div>

            <div class="row" style="margin-top: 20px;">
              <h5>Examinations</h5>
              <hr>
            </div>

            <div class="multiple-items" style="background-color: #aeaeae;margin-bottom: 20px;" id="classExamSlider">
            </div>

            <div class="row">
              <h5>Evaluations</h5>
              <hr>
            </div>

            <div class="multiple-items" style="background-color: #aeaeae;" id="evaluationSlider">
            </div>

          </div> 
        </div>
        <input type="hidden" id="classId" value="${classInfo.classId}">
        <input type="hidden" id="courseId" value="${classInfo.courseId}">
        </body>
        </html>