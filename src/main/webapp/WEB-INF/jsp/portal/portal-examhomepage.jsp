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
      <script src="static/assets/js/portal-examhomepage.js"></script>
      <!-- END: JS-HEADER -->
   </head>
  
   <body >
     <!-- Header -->
	<jsp:include page="../portal/common/portal-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../portal/common/portal-sidenav.jsp" />
	<div class="container1" style="margin-top: 20px;">
          <div class="divcard">

            <!-- Breadcrumbs starts here -->
            <div class="row">
              <nav>
                <div>
                  <div class="col s12">
                    <a href="#!" style="color:black;" class="breadcrumb">${classInfo.courseName}</a> 
                     <a href="#!" style="color:black;" class="breadcrumb">></a> 
                    <a href="#!" style="color:black;" class="breadcrumb">${classInfo.className}</a>
                    <a href="#!" style="color:black;" class="breadcrumb">></a> 
                    <a href="#!" style="color:black;" class="breadcrumb">${classExam.title}</a>
                  </div>
                </div>
              </nav>
            </div>
            <!-- Breadcrumbs ends here -->

            <div class="row">
              <div class="col s12 m6">
                <span><b style="font-size: 20px">${classExam.title}</b>&nbsp(<span id="examType"></span>)</span>
              </div>
              <div class="col s12 m6">
                <span class="right"><i class="material-icons left">schedule</i>&nbsp${classExam.duration} Minutes</span>
              </div>
            </div>

            <div>
              <hr>
              <p>${classExam.description}</p>
              <table class="striped highlights">
                <thead>
                  <tr>
                    <th>Section</th>
                    <th>Instruction</th>
                    <th>Total Points</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>asd</td>
                    <td>asd</td>
                    <td>asd</td>
                    <td>asd</td>
                  </tr>
                </tbody>
              </table>
            </div>

            <div class="row">
              <div class="file-field input-field" style="margin-top: 0 !important;">
                <div class="btn bttn right" style="margin-top: 20px;">
                  <span>Browse</span>
                  <input type="file">
                </div>
                <div class="file-path-wrapper" style="padding-top: 0 !important;">
                  <span>&nbsp</span>
                  <input class="file-path validate" type="text" id="fileupload">
                </div>
              </div>
            </div>

            <div class="row">
              <a href="#!" class="bttn btn waves-effect waves-light" type="submit" style="width: 100%;">Back to Class</a>
            </div>

          </div>
        </div>

      </div>
	<input type="hidden" id="hdnExamType" value="${classExam.examType}">
	<input type="hidden" id="hdnClassId" value="${classInfo.classId}">
	<input type="hidden" id="hdnExamId"  value="${classExam.examId}">
   </body>
</html>