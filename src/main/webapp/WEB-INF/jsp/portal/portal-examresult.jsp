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
	<div class="container" style="margin-top: 20px;">
          <div class="divcard">

            <!-- Breadcrumbs starts here -->
            <div class="row">
              <nav>
                <div>
                  <div class="col s12">
                    <a href="#!" class="breadcrumb">Passenger Handling</a>
                    <a href="#!" class="breadcrumb">Class 101</a>
                    <a href="#!" class="breadcrumb">Exam 101</a>
                  </div>
                </div>
              </nav>
            </div>
            <!-- Breadcrumbs ends here -->

            <div class="row" style="margin-top: 20px;">
              <div class="col s12 m6">
                <div class="row">
                  <h6><b>Juan Dela Cruz (status) (exam title)</b></h6>
                </div>
                <div class="row" style="margin-top: 25px;">
                  <p>Score: <span>no. of correct/total items (percentage)</span></p>
                  <div class="divcard">
                    <legend style="margin-bottom: 10px;"><b>Score Breakdown</b></legend>
                    <table class="striped highlight">
                      <thead>
                        <tr>
                          <th>Section</th>
                          <th>Total Items</th>
                          <th>Score</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td>asd</td>
                          <td>asd</td>
                          <td>asd</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
                <div class="row" style="margin-top: 15px;">
                  <p>Official Score: <span>no. of correct/total items (percentage)</span></p>
                  <p>Official Exam Status:</p>
                  <div class="divcard">
                    <legend style="margin-bottom: 10px;"><b>Score Breakdown</b></legend>
                    <table class="striped highlight">
                      <thead>
                        <tr>
                          <th>Section</th>
                          <th>Total Items</th>
                          <th>Score</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td>asd</td>
                          <td>asd</td>
                          <td>asd</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <div class="col s12 m6">
                <div class="row">
                  <div class="col s6 m6">
                    <a href="#!" class="bttn btn waves-light waves-effect" type="submit" style="width: 100%;">Review</a>
                  </div>
                  <div class="col s6 m6">
                    <a href="#!" class="bttn btn waves-light waves-effect" type="submit" style="width: 100%;">Retake Exam</a>
                  </div>
                </div>
                <div class="row" style="padding-left: 10px;margin-top: 10px;">
                  <span>Retake:</span><br>
                  <span>Condition:</span><br>
                  <span>No. of Retakes Left:</span><br>
                </div>
                <div class="row" style="padding-left: 10px;margin-top: 10px;">
                  <div class="divcard">
                    <legend style="margin-bottom: 10px;"><b>Attempt History</b></legend>
                    <table class="striped highlight">
                      <thead>
                        <tr>
                          <th>Date of Attempt</th>
                          <th>Score</th>
                          <th>Percentage</th>
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
                </div>
              </div>
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

   </body>
</html>