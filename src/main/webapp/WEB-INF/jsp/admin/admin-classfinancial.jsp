<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - VIEW USER (USER GROUP)</title>
	<!-- START: CSS-HEADER -->  
	<jsp:include page="../admin/common/admin-trainee-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	 <jsp:include page="../admin/common/admin-js-header.jsp" />
	 <script src="static/assets/js/admin-classfinancial.js"></script>
	<!-- END: JS-HEADER -->
</head>
<body>
	<jsp:include page="../admin/common/admin-collapsibleheader.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-collapsiblesidebar.jsp" />
	
	<div class="container1">
          <div class="row">
            <div class="col s12 m3" style="padding-top: 20px;">
              <div class="card">
                <div class="card-image">
                  <img src="static/assets/images/usersimage/users.png" style="clip-path: circle(40% at 50% 50%);">
                  <a class="btn-floating btn-large halfway-fab waves-effect waves-light bttn"><i class="material-icons">add</i></a>
                </div>
              </div>
              <div>
                <h6><b>${classInfo.courseName}</b></h6>
                <p><span>${classInfo.className}</span></p>
                <p><span id="deliveryMethodLabel"></span></p>
                <p><span>Status</span></p>
              </div>
            </div>
            <div class="col s12 m9" style="padding: 5px;">
              <div class="row" style="margin-bottom: 20px;">
                <ul class="pagination hide-on-med-and-down">
                  <li><a href="admin.classdashboard?classId=${classId}"><u>Dashboard</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classdetails?classId=${classId}"><u>Details</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classtrainee?classId=${classId}"><u>Trainees</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classattendance?classId=${classId}"><u>Attendance</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classsettings?classId=${classId}"><u>Settings</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classmaterials?classId=${classId}"><u>Materials</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classexam?classId=${classId}"><u>Exams</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classfinancial?classId=${classId}">Financial</a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classevaluation?classId=${classId}"><u>Evaluation</u></a></li>
                </ul>
              </div>
              <!-- content starts here -->
              <div class="divcard">

                <div class="row" style="margin-bottom: 40px;">
                  <div class="right">
                    <a href="#!" class="btn bttn waves-light waves-effect" style="margin-right: 5px;"><i class="material-icons left">arrow_back</i>Back</a>
                    <a id="btnSaveClassFinancial" class="btn bttn waves-light waves-effect" style="margin-right: 5px;"><i class="material-icons left">save</i>Save</a>
                  </div>
                </div>

                <!-- Trainers section starts here -->                
                <div class="row" style="margin-bottom:10px;">
                	<div class="col s12 m6">
                		<h6 style="margin-top: 10px;"><b>Trainers</b></h6>
                	</div>
                	<div class="col s12 m6">
                		<a id="addTrainerBtn" href="#!" class="btn bttn waves-light waves-effect right"><i class="material-icons left">add_circle</i>Add</a>
                	</div>
                </div>
				
                <div class="row" style="padding: 10px;margin-bottom:50px;">
                  <table>
                    <thead>
                      <tr>
                        <th>Trainer</th>
                        <th>Rate</th>
                        <th></th>
                        <th>No. of Days</th>
                        <th></th>
                        <th>Total Cost</th>
                        <th></th>
                      </tr>
                    </thead>

                    <tbody id="trainerTblBody">
                    </tbody>
                  </table>
                </div>
                <!-- trainers section ends here -->

                <!-- Materials section starts here -->
                <div class="row" style="margin-bottom:10px;">
                	<div class="col s12 m6">
                		<h6 style="margin-top: 10px;"><b>Materials</b></h6>
                	</div>
                	<div class="col s12 m6">
                		<a id="addMaterialBtn" href="#!" class="btn bttn waves-light waves-effect right"><i class="material-icons left">add_circle</i>Add</a>
                	</div>
                </div>

                <div class="row" style="padding: 10px;margin-bottom:50px;">
                  <table>
                    <thead>
                      <tr>
                        <th>Material</th>
                        <th>Price</th>
                        <th></th>
                        <th>Quantity</th>
                        <th></th>
                        <th>Total Cost</th>
                        <th></th>
                      </tr>
                    </thead>

                    <tbody id="materialTblBody">
                    </tbody>
                  </table>
                </div>
                <!-- Materials section ends here -->

                <!-- Others section starts here -->
                <div class="row" style="margin-bottom:10px;">
                	<div class="col s12 m6">
                		<h6 style="margin-top: 10px;"><b>Others</b></h6>
                	</div>
                	<div class="col s12 m6">
                		<a id="addOtherBtn" href="#!" class="btn bttn waves-light waves-effect right"><i class="material-icons left">add_circle</i>Add</a>
                	</div>
                </div>
                

                <div class="row" style="padding: 10px;">
                  <table>
                    <thead>
                      <tr>
                        <th>Name</th>
                        <th>Price</th>
                        <th></th>
                        <th>Quantity</th>
                        <th></th>
                        <th>Total Cost</th>
                        <th></th>
                      </tr>
                    </thead>

                    <tbody id="otherTblBody">
                    </tbody>
                  </table>
                </div>
                <!-- Others section ends here -->

                <div class="row" style="padding: 10px;">
                  <div class="right">
                    <b>Grand Total Cost:</b>
                    <input id="tbFinalTotalCost" type="text" name="" value="0.00" disabled style="font-weight: bolder;">
                  </div>
                </div>

              </div>
              <!-- content ends here -->
            </div>
          </div>
        </div>
        
        <input type="hidden" id="classId" value="${classInfo.classId}">
        <input type="hidden" id="courseId" value="${classInfo.courseId}">
        <input type="hidden" id="deliveryMethod" value="${classInfo.deliveryMethod}">
        <input type="hidden" id="scheduleType" value="${classInfo.scheduleType}">
</body>
</html>