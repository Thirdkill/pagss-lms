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
      <script src="static/assets/js/portal-trainingcatalog.js"></script>
      <!-- END: JS-HEADER -->
   </head>
  
   <body >
     <!-- Header -->
	<jsp:include page="../portal/common/portal-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../portal/common/portal-sidenav.jsp" />
            
        <div class="container" style="height: 100vh !important;">
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
              <li><a href="mytraining-inprogress" id="inProgressHref"><u>In Progress</u></a></li>
              <li style="font-size: 18px;">|</li>
              <li><a href="mytraining-completed" id="completeHref"><u>Completed</u></a></li>
              <li style="font-size: 18px;">|</li>
              <li><a href="mytraining-catalog">Training Catalog</a></li>
            </ul>
          </div>

          <div class="row multiple-slides">
            <h6 style="margin-bottom: 20px;"><b>Recommended</b></h6>
            <div id="recommendedSlider">
            </div>
            <!--<div class="col s12 m4">
              <div class="divcard">
                <h6 style="margin: 0;"><b>Passenger Handling</b></h6>
                <p><label><span>Class 101</span></label></p>
                <p><label><span>Classroom Training</span></label></p>
                <a href="#!" class="btn bttn waves-effect waves-light" type="submit" style="width: 100%;">View</a>
              </div>
            </div>
            
            <div class="col s12 m4">
              <div class="divcard">
                <h6 style="margin: 0;"><b>Passenger Handling</b></h6>
                <p><label><span>Class 101</span></label></p>
                <p><label><span>CBT (Individual)</span></label></p>
                <a href="#!" class="btn bttn waves-effect waves-light" type="submit" style="width: 100%;visibility: hidden;">View</a>
              </div>
            </div>

            <div class="col s12 m4">
              <div class="divcard">
                <h6 style="margin: 0;"><b>Passenger Handling</b></h6>
                <p><label><span>Class 101</span></label></p>
                <p><label><span>CBT (Modular)</span></label></p>
                <a href="#!" class="btn bttn waves-effect waves-light" type="submit" style="width: 100%;">View</a>
              </div>
            </div>-->
          </div>

          <div class="row multiple-slides">
            <h6 style="margin-bottom: 20px;"><b>Browse Public Trainings</b></h6>
            <div id="publicTrainingSlider">
            </div>
            <!--<div class="col s12 m4">
              <div class="divcard">
                <h6 style="margin: 0;"><b>Passenger Handling</b></h6>
                <p><label><span>Class 101</span></label></p>
                <p><label><span>Classroom Training</span></label></p>
                <a href="#!" class="btn bttn waves-effect waves-light" type="submit" style="width: 100%;">View</a>
              </div>
            </div>
            
            <div class="col s12 m4">
              <div class="divcard">
                <h6 style="margin: 0;"><b>Passenger Handling</b></h6>
                <p><label><span>Class 101</span></label></p>
                <p><label><span>CBT (Individual)</span></label></p>
                <a href="#!" class="btn bttn waves-effect waves-light" type="submit" style="width: 100%;visibility: hidden;">View</a>
              </div>
            </div>

            <div class="col s12 m4">
              <div class="divcard">
                <h6 style="margin: 0;"><b>Passenger Handling</b></h6>
                <p><label><span>Class 101</span></label></p>
                <p><label><span>CBT (Modular)</span></label></p>
                <a href="#!" class="btn bttn waves-effect waves-light" type="submit" style="width: 100%;">View</a>
              </div>
            </div>-->
          </div>
          <!--<div class="multiple-items" style="background-color: #aeaeae;">
            <div>
              <div class="divcard" style="margin: 5px;">
                <h6 style="margin: 0;"><b>Passenger Handling</b></h6>
                <p><label><span>Class 101</span></label></p>
                <p><label><span>Classroom Training</span></label></p>
                <a href="#!" class="btn bttn waves-effect waves-light" type="submit" style="width: 100%;">View</a>
              </div>
            </div>
            
            <div>
              <div class="divcard" style="margin: 5px;">
                <h6 style="margin: 0;"><b>Passenger Handling</b></h6>
                <p><label><span>Class 101</span></label></p>
                <p><label><span>CBT (Individual)</span></label></p>
                <a href="#!" class="btn bttn waves-effect waves-light" type="submit" style="width: 100%;visibility: hidden;">View</a>
              </div>
            </div>

            <div>
              <div class="divcard" style="margin: 5px;">
                <h6 style="margin: 0;"><b>Passenger Handling</b></h6>
                <p><label><span>Class 101</span></label></p>
                <p><label><span>CBT (Modular)</span></label></p>
                <a href="#!" class="btn bttn waves-effect waves-light" type="submit" style="width: 100%;">View</a>
              </div>
            </div>
            <div>7</div>
            <div>8</div>
            <div>9</div>
          </div>-->
            
        </div>

      </div>
	<input type="hidden" id="userId" value="${user.userId}"/>
   </body>
</html>