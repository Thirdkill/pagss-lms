<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - CLASS MATERIALS</title>
	<!-- START: CSS-HEADER -->  
	<jsp:include page="../admin/common/admin-trainee-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	 <jsp:include page="../admin/common/admin-js-header.jsp" />
	 <script src="static/assets/js/admin-classmaterials.js"></script>
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
                  <li><a href="admin.classmaterials?classId=${classId}">Materials</a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classexam?classId=${classId}"><u>Exams</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classfinancial?classId=${classId}"><u>Financials</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classevaluation?classId=${classId}"><u>Evaluation</u></a></li>
                </ul>
              </div>
              <!-- content starts here -->
              <div class="divcard">

                <div class="row">
                  <div class="right">
                    <a href="#!" class="btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">arrow_back</i>Back</a>
                    <a data-target="learningMaterialModal" class="modal-trigger btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">add_circle</i>Add Materials</a>
                    <a id="btnDeleteMaterials" class="btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">delete</i>Delete Materials</a>
                  </div>
                </div>

                <div style="margin-top: 20px;padding: 10px;">
                  <span><b>Class Materials</b></span>
                </div>

                <div>
                  <table class="striped highlight">
                    <tbody id="classMaterialTblBody">
                    </tbody>
                  </table>
                </div>
                <div id="pagealign">
	               <div id="classMaterialTblPagination">
	                  <!--Pagination-->
	               </div>
              	</div>

                <div style="margin-top: 20px;padding: 10px;">
                  <span><b>Course Materials</b></span>
                </div>

                <div>
                  <table class="striped highlight">
                    <tbody id="courseMaterialTblBody">
                    </tbody>
                  </table>
                  <div id="pagealign">
	               <div id="courseMaterialTblPagination">
	                  <!--Pagination-->
	               </div>
              	</div>
                </div>
                

              </div>
              <!-- content ends here -->
            </div>
          </div>
        </div>

        <!--Modal learningMaterialModal starts here-->

        <div id="learningMaterialModal" class="modal modal-fixed-footer fixed-modal">
            <div class="modal-header">
              <h5>Add Learning Material</h5>
            </div>
            <div class="modal-content">
              <div class="content-row">
                <div>
                  <b>Type*</b>
                    <select id="contentTypeDropdown">
                      <option disabled selected value="0">Choose an option</option>
                    </select>
                  <div id="fileName"> 
                  <b>File</b>
                    <input type="text" id="fileLabel" class="validate">
                  </div> 
                </div>
              </div>
              <div class="content-row" id="mediaUploadDiv">
              	<form id="mediaUploadFrm">
                <div class="file-field input-field" style="margin-top: 0 !important;">
                  <div class="btn bttn right" style="margin-top: 20px;">
                    <span>Browse</span>
                    <input type="file" name="mediaFileUpload" id="mediaFileUpload">
                  </div>
                  <div class="file-path-wrapper" style="padding-top: 0 !important;padding-left:0 !important;">
                    <b>Browse File</b>
                    <input class="file-path validate" type="text" id="fileupload">
                  </div>
                </div>
                </form>
              </div>
              <div class="content-row" id="urlDiv">
                <b>URL*</b>
                    <input type="text" id="urlTextBox" class="validate">
              </div>
              <div class="content-row" style="margin: 10px 0 20px 0;">
                <b>Viewable by Trainees</b>
                <label style="margin-left:10px !important;">
                  <input type="checkbox" id="viewStatus"/>
                  <span>Yes</span>
                </label>
              </div>
            </div>
            
            <div class="modal-footer">
            <div id="materialsModalBtn">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" id="cancelBtn" name="action"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" type="submit" name="action" id="saveClassMaterials"><i class="material-icons left">save</i>Save</a>
            </div>
            <div id="progressCon">
	            <b id="percentValue">0%</b>
	            <div class="progress">
	                <div id="progressBar" class="determinate"></div>
	            </div>
            </div>
            <input type="hidden" id="courseId" value="${courseId}">
        </div>
	</div>
	<input type="hidden" id="classId" value="${classInfo.classId}">
        <input type="hidden" id="tbCourseId" value="${classInfo.courseId}">
        <input type="hidden" id="deliveryMethod" value="${classInfo.deliveryMethod}">
        <input type="hidden" id="scheduleType" value="${classInfo.scheduleType}">
        <!--Modal learningMaterialModal ends here-->
        
</body>
</html>