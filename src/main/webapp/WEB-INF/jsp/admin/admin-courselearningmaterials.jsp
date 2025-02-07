<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - COURSE LEARNING MATERIALS</title>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
	<script src="static/assets/js/admin-courselearningmaterials.js"></script>
	<!-- END: JS-HEADER -->
</head>
<body>
	<!-- Header -->
	<jsp:include page="../admin/common/admin-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-sidebar.jsp" />
	<div class="container2">
          <div class="row">
            <div class="col s12 m8">
              <h4 id="courseNameHeader">${courseInfo.courseName}</h4>
            </div>
          </div>

          <div class="row">
            <hr style="width:100%;">
          </div>
          
          <div class="row">
            <div class="divcard">
              <div class="row" style="margin-bottom: 20px;">
                <div>
                   <ul class="pagination">
                    <li><a href="admin.coursedetails?courseId=${courseId}"><u>Details</u></a></li>
                    <li style="font-size: 18px;">|</li>
                    <li><a href="admin.coursematerials?courseId=${courseId}">Learning Materials</a></li>
                    <li style="font-size: 18px;">|</li>
                  	<c:choose><c:when test="${courseInfo.deliveryMethod == 2 }">
					     <li><a href="admin.coursepath?courseId=${courseId}"><u>Learning Path</u></a></li>
                    <li style="font-size: 18px;">|</li>
                    </c:when></c:choose>
                    <li><a href="admin.courseexamination?courseId=${courseId}"><u>Examinations</u></a></li>
                    <li style="font-size: 18px;">|</li>
                    <li><a href="admin.courseevaluation?courseId=${courseId}"><u>Evaluation</u></a></li>
                  </ul>
                </div>
              </div>
              <div class="row">
                <div class="right">
                  <a href="admin.trainingcourse" class="btn waves-light waves-effect bttn" type="submit" style="margin-right: 5px;"><i class="material-icons left">arrow_back</i>Back</a>
                  <a data-target="learningMaterialModal" class="modal-trigger btn waves-light waves-effect bttn" type="submit" style="margin-right: 5px;"><i class="material-icons left">add_circle</i>Add Materials</a>
                  <a href="#!" id="deleteBtn" class="btn waves-light waves-effect bttn" type="submit"><i class="material-icons left">delete</i>Delete Materials</a>
                </div>
              </div>
              <div class="row">
                <div style="margin-top: 20px;">
                  <table class="highlight striped">
                    <tbody id="courseMaterialsTblBody">
                     <!-- Dynamically populated -->
                    </tbody>
                  </table>
                  <div id="pagealign">
		              <div id="courseMaterialsPagination">
		              </div>
	              </div>
                </div>
              </div>
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
                    <input type="file" name="mediaFileUpload" id="mediaFileUpload" accept="video/*,.mkv">
                  </div>
                  <div class="file-path-wrapper" style="padding-top: 0 !important;">
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
              <a class="btn waves-effect waves-light bttn" type="submit" name="action" id="saveCourseMaterials"><i class="material-icons left">save</i>Save</a>
            </div>
            <div id="progressCon">
	            <b id="percentValue">0%</b>
	            <div class="progress">
	                <div id="progressBar" class="determinate"></div>
	            </div>
            </div>
            <input type="hidden" id="courseId" value="${courseId}">
        </div>
			
        <!--Modal learningMaterialModal ends here-->
</body>
</html>