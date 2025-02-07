<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/portal/common/include.jsp"%>
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
      <script src="static/assets/js/portal-modulartraining.js"></script>
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
              <div class="col s12 m6">
                <h5>Learning Path</h5>
              </div>
              <div class="col s12 m6">
                <a href="#!" class="bttn btn waves-light waves-effect right" id="finishBtn">Finish Class</a>
              </div>
            </div>

            <div class="row">
              <hr>
              <div id="learningPathCon">
              </div>
              <!-- <table class="striped highlight">
                <thead>
                  <tr>
                    <th colspan="4"><span class="left">Section Header</span></th>
                  </tr>
                </thead>
                <tbody>
                 <tr>
                    <td>Content 1 (Content Type)</td>
                    <td>Last Accessed: May 31, 2019</td>
                    <td>Total Time Spent: 15 minutes</td>
                    <td>Done</td>
                    <td><i class="material-icons">play_arrow</i></td>
                  </tr>
                  <tr>
                    <td>Content 2 (Content Type)</td>
                    <td> </td>
                    <td> </td>
                    <td>Not yet Started</td>
                    <td><a href="#!" class="bttn btn waves-effect waves-light">Start</a></td>
                  </tr>
                  <tr>
                    <td>Exam (Exam Type)</td>
                    <td>Duration: 30 min</td>
                    <td>Passing Score: 8%</td>
                    <td>No. Retakes left: 3</td>
                    <td>Not taken</td>
                  </tr>
                </tbody>
              </table>-->
            </div>


            <!-- first step starts here -->
            <div class="row" style="margin-top: 20px;">
              <div class="row">
                <div class="col s12 m6">
                  <h5 style="margin-top: 5px;">Other Learning Materials</h5>
                </div>
              </div>
              <hr style="margin-bottom: 20px;">

              <div class="multiple-items" style="background-color: #aeaeae;margin-bottom: 20px;" id="learningMaterialsSlider">
              </div>
            </div>
            <!-- first step ends here -->

            <!-- second step starts here -->
            <div class="row" style="margin-top: 20px;">
              <div class="row">
                <div class="col s12 m6">
                  <h5 style="margin-top: 5px;">Other Examinations</h5>
                </div>
              </div>
              <hr style="margin-bottom: 20px;">

              <div class="multiple-items" style="background-color: #aeaeae;margin-bottom: 20px;" id="classExamSlider">
              </div>
            </div>
            <!-- second step ends here -->

            <!-- third step starts here -->
            <div class="row" style="margin-top: 20px;" >
              <div class="row">
                <div class="col s12 m6">
                  <h5 style="margin-top: 5px;">Evaluations</h5>
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