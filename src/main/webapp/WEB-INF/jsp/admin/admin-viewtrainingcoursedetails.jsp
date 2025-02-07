<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - ${courseInfo.courseName}</title>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
	<jsp:include page="../admin/common/admin-tinymce-header.jsp" />
	<script src="static/assets/js/utility/tinymce-util.js"></script>
	<script src="static/assets/js/admin-viewtrainingcoursedetails.js"></script>
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
            <div id="navpad" class="nav-wrapper col s12 m4 row">
              <div class="row">
                 <div class="input-field col s6 m6">
                    <a href="admin.trainingcourse" style="width:100%;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">not_interested</i>Cancel</a>
                 </div>
                 <div class="input-field col s6 m6">
                    <a id="saveCourseBtn" style="width:100%;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">save</i>Save</a>
                 </div>
              </div>
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
                    <li><a href="admin.coursedetails?courseId=${courseId}">Details</a></li>
                    <li style="font-size: 18px;">|</li>
                    <li><a href="admin.coursematerials?courseId=${courseId}"><u>Learning Materials</u></a></li>
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
                <div class="col s12 m5">
                  <b>Training Category</b>
                  <select id="trainingCategoryDropdown" disabled>
                    <option disabled selected>Choose an option</option>
                  </select>
                </div>
                <div class="col s10 m6">
                  <b>Delivery Method</b>
                  <select id="deliveryMethodDropdown" disabled>
                     <option disabled selected>Choose an option</option>
                  </select>
                </div>
                <div class="col s2 m1 switch left">
                  <b>Status</b>
                  <label>
                    <input type="checkbox" id="courseStatus">
                    <span style="" class="lever left"></span>
                  </label>
                </div>
              </div>
              <div class="row">
                <div class="col s12 m5">
                  <b>Course Code</b>
                  <input type="text" id="courseCode" disabled>
                </div>
                <div class="col s10 m7">
                  <b>Course Name</b>
                  <input type="text" id="courseName">
                </div>
              </div>
              <div style="padding-top: 10px;" class="row">
                <div class="col s12 m6">
                  <div>
                    <b>Course Description</b>
                    <div id="courseDescriptionEditor">
                    </div>
                  </div>
                </div>
                <div class="col s12 m6">
                  <div>
                    <b>Course Objectives</b>
                    <div id="courseObjectivesEditor">
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="divcard">
              <legend style="margin-bottom: 10px;"><b>Grading System</b></legend>
              <div class="row" style="margin-bottom: 10px;">
                <a data-target="addComponentModal" class="btn modal-trigger waves-effect waves-light bttn right" type="submit" name="" ><i class="material-icons left">add_circle</i>Add Component</a>
              </div>
              <div class="row">
                <table class="highlight striped">
                  <thead>
                    <tr>
                        <th class="min">Actions</th>
                        <th class="large">Component<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
                        <th class="min">Percentage<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
                    </tr>
                  </thead>
                  <tbody id="tblComponentBody">
                    <!-- Dynamically Populated -->
                  </tbody>
                </table>
                <div id="pagealign">
	              <div id="gradingComponentPagination">
	                <!-- Pagination -->
	              </div>
              </div>
              </div>
            </div>
          </div>
			
          <div class="row">
            <div class="divcard">
              <legend style="margin-bottom: 10px;"><b>Class Defaults</b></legend>
              <div class="row">
                <div class="col s12 m5">
                  <b>Location</b>
                  <select id="locationDropdown">
                    <option disabled selected>Choose an option</option>
                  </select>
                </div>
                <div class="col s2 m7 switch left">
                  <div class="row">
                    <div class="col s4 m4" style="text-align: center;">
                      <b>Self Registration</b><br>
                      <label>
                        <input type="checkbox" id="isSelfRegister">
                        <span class="lever"></span>
                      </label>
                    </div>
                    <div class="col s4 m4" style="text-align: center;">
                      <b>Send Certificate</b><br>
                      <label>
                        <input type="checkbox" id="withCertificate">
                        <span class="lever"></span>
                      </label>
                    </div>
                    <div class="col s4 m4" style="text-align: center;">
                      <b>Exam</b><br>
                      <label>
                        <input type="checkbox" id="withExam">
                        <span class="lever" style="margin-left: 15px !important;"></span>
                      </label>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col s12 m5">
                  <b>Minimum Attendees</b>
                  <input type="text" id="minAttendees" class="numbersOnly">
                </div>
                <div class="col s10 m7">
                  <b>Maximum Attendees</b>
                  <input type="text" id="maxAttendees" class="numbersOnly">
                </div>
              </div>
              <div class="row">
                <div class="col s12 m5">
                  <b>Trainer</b>
                  <select id="trainerDropdown">
                    <option disabled selected>Choose an option</option>
                  </select>
                </div>
                <div class="col s10 m7">
                  <b>Schedule Type</b>
                  <select id="scheduleTypeDropdown">
                  </select>
                </div>
              </div>
              <div class="row" style="margin-top: 10px;padding: 10px;">
              <form id="frmUploadPhoto">
                <div class="file-field input-field" style="margin-top: 0 !important;">
                  <div class="btn bttn right" style="margin-top: 20px;">
                    <span>Browse</span>
                    <input type="file" name="fileUpload" id="fileUpload">
                  </div>
                  <div class="file-path-wrapper" style="padding-top: 0 !important;">
                    <b>Display Photo</b>
                    <input class="file-path validate" type="text" name="uploadPhoto" id="uploadPhoto" type="text">
                  </div>
                </div>
               </form> 
              </div>
            </div>
          </div>
            
        </div>


        <!-- Add Component Modal starts here -->
        <div id="addComponentModal" class="modal modal-fixed-footer">
            <div class="modal-header">
              <h5>Add Component</h5>
            </div>
            <div class="modal-content">
              <div class="content-row">
                <b>Component*</b>
                <select id="componentTypeDropdown">
                  <option disabled selected value="0">Choose an option</option>
                </select>
               	<input type="text" id="othersTxtBox" style="display:none">
              </div>
              <div class="content-row">
                <b>Percentage*</b>
                <input class="numbersOnly" type="text" id="percentageTxtBox">
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" type="submit" name="action" id="saveComponentBtn"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>
        <!-- Edit Component Modal ends here -->
         <!-- Add Component Modal starts here -->
        <div id="editComponentModal" class="modal modal-fixed-footer">
            <div class="modal-header">
              <h5>Update Component</h5>
            </div>
            <div class="modal-content">
              <div class="content-row">
                <b>Component*</b>
                <select id="editComponentTypeDropdown">
                  <option disabled selected>Choose an option</option>
                </select>
               	<input type="text" id="editOthersTxtBox" style="visibility :hidden;">
              </div>
              <div class="content-row">
                <b>Percentage*</b>
                <input class="numbersOnly" type="text" id="editPercentageTxtBox">
              </div>
            </div>
             <input type="hidden" id="hiddenGradingComponentId">
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" type="submit" name="action" id="editComponentBtn"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>
        <!-- Edit Component Modal ends here -->
   <input type="hidden" id="courseId" value="${courseId}">
   <input type="hidden" id="hdnTotalMarks">
</body>
</html>