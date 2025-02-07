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
      <script src="static/assets/js/portal-individualtraining.js"></script>
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
                <div>
                   <span>Classroom Training</span><br>
                    <span>${classInfo.categoryName}</span><br>
                    <span>${classInfo.className}</span><br>
                    <span>Location: ${classInfo.locationName}</span><br>
                    <span>Trainer: ${classInfo.trainerName}</span>
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

            <div class="row">
              <div class="col s12 m4" style="text-align: center;">
                <a href="#!" id="classProperLink" class="bttn btn waves-light waves-effect" style="border-radius: 50px;height: 50px;width: 50px;padding-top: 8px;"><span style="font-size: 20px;">1</span></a><br>
                <span>Class Proper</span>
              </div>
              <div class="col s12 m4" style="text-align: center;">
                <a href="#!" id="takeExamLink" class="bttn btn waves-light waves-effect" style="border-radius: 50px;height: 50px;width: 50px;padding-top: 8px;"><span style="font-size: 20px;">2</span></a><br>
                <span>Take the exams</span>
              </div>
              <div class="col s12 m4" style="text-align: center;">
                <a href="#!" id="evaluateClassLink" class="bttn btn waves-light waves-effect" style="border-radius: 50px;height: 50px;width: 50px;padding-top: 8px;"><span style="font-size: 20px;">3</span></a><br>
                <span>Evaluate class</span>
              </div>
            </div>

            <!-- Hidden in take the exams and in evaluation class starts here -->
            <div class="row" id="documentViewerCon">
              <center><iframe id="documentViewerFrame" width='1000' height='700' allowfullscreen webkitallowfullscreen></iframe></center>
            </div>
            <!-- Hidden in take the exams ends here -->


            <!-- first step starts here -->
            <div class="row" style="margin-top: 20px;" id="learningMaterialsCon">
              <div class="row">
                <div class="col s12 m6">
                  <h5 style="margin-top: 5px;">Learning Materials</h5>
                </div>
                <div class="col s12 m6">
                  <a href="#!" class="bttn btn waves-effect waves-light right" id="materialsNextStepBtn">Next Step<i class="material-icons right">keyboard_arrow_right</i></a>
                </div>
              </div>
              <hr style="margin-bottom: 20px;">

              <div class="multiple-items" style="background-color: #aeaeae;margin-bottom: 20px;" id="learningMaterialsSlider">
              </div>
            </div>
            <!-- first step ends here -->

            <!-- second step starts here -->
            <div class="row" style="margin-top: 20px;" id="examCon">
              <div class="row">
                <div class="col s12 m6">
                  <h5 style="margin-top: 5px;">Examinations</h5>
                </div>
                <div class="col s12 m6">
                  <a href="#!" class="bttn btn waves-effect waves-light right" id="examNxtStepBtn">Next Step<i class="material-icons right">keyboard_arrow_right</i></a>
                </div>
              </div>
              <hr style="margin-bottom: 20px;">

              <div class="multiple-items" style="background-color: #aeaeae;margin-bottom: 20px;" id="classExamSlider">
              </div>
            </div>
            <!-- second step ends here -->

            <!-- third step starts here -->
            <div class="row" style="margin-top: 20px;" id="evaluationsCon">
              <div class="row">
                <div class="col s12 m6">
                  <h5 style="margin-top: 5px;">Evaluations</h5>
                </div>
                <div class="col s12 m6">
                  <a href="#!" class="bttn btn waves-effect waves-light right" id="finishBtn">Finish Class</a>
                </div>
              </div>
              <hr style="margin-bottom: 20px;">

              <div class="multiple-items" style="background-color: #aeaeae;margin-bottom: 20px;" id="evaluationSlider">
            
              </div>
            </div>
            <!-- third step ends here -->
            

          </div> 
        </div>
        <input type="hidden" id="classId" value="${classInfo.classId}">
        <input type="hidden" id="courseId" value="${classInfo.courseId}">
        </body>
        </html>