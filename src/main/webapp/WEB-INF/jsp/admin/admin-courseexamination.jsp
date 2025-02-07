<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - COURSE EXAMINATION</title>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
	<script src="static/assets/js/admin-courseexam.js"></script>
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
               <h4>${courseInfo.courseName}</h4>
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
                    <li><a href="admin.coursematerials?courseId=${courseId}" ><u>Learning Materials</u></a></li>
                    <li style="font-size: 18px;">|</li>
                    <c:choose><c:when test="${courseInfo.deliveryMethod == 2 }">
					     <li><a href="admin.coursepath?courseId=${courseId}"><u>Learning Path</u></a></li>
                    <li style="font-size: 18px;">|</li>
                    </c:when></c:choose>
                    <li><a href="admin.courseexamination?courseId=${courseId}" class="active">Examinations</a></li>
                    <li style="font-size: 18px;">|</li>
                    <li><a href="admin.courseevaluation?courseId=${courseId}"><u>Evaluation</u></a></li>
                  </ul>
                </div>
              </div>
              <div class="row">
                <div class="right">
                  <a href="admin.trainingcourse" class="btn waves-light waves-effect bttn" type="submit" style="margin-right: 5px;"><i class="material-icons left">arrow_back</i>Back</a>
                  <a href="admin.examination?action=create&module=courseexam&courseId=${courseId}" class="modal-trigger btn waves-light waves-effect bttn"><i class="material-icons left">add_circle</i>Create Exam</a>
                </div>
              </div>
              <div class="row">
                <div style="margin-top: 20px;">
                  <table class="highlight striped">
                    <thead>
                      <tr>
                        <th class="mid">Exam Title</th>
                        <th class="mid">Exam Type</th>
                        <th class="large">Description</th>
                        <th class="min">Action</th>
                      </tr>
                    </thead>
                    <tbody id="courseExamTblBody">
                    </tbody>
                  </table>
                  <div id="pagealign">
		              <div id="courseExamTblPagination">
		              </div>
	              </div>
                </div>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="divcard">
              <div class="row">
                <div class="col s12 m6" style="padding-top: 13px;">
                  <b>Avalable Exams</b>
                </div>
                <div style="padding-top:5px;" class="nav-wrapper col s12 m6 row">
                  <div class="row">
                     <div style="margin-top:3px;" class="input-field col s6 m7">
                        <input id="searchExamTxtBox" type="search" name="" placeholder="search">
                        <label class="label-icon" for="search" ><i style="margin-top:4px;" class="material-icons">search</i></label>
                        <i style="margin-top:1px;" class="material-icons" id="clearSearch">close</i>
                     </div>
                     <div style="margin:0;" class="input-field col s6 m5">
                        <a id="addCourseExamBtn" style="width:100%;text-transform:capitalize;" class="btn modal-trigger waves-effect waves-light bttn" name="" ><i class="material-icons left">add_circle</i>Add</a>
                     </div>
                  </div>
                </div>
              </div>

              <div class="row">
                <div style="margin-top: 20px;">
                  <table class="highlight striped">
                    <thead>
                      <tr>
                        <th class="mins"></th>
                        <th class="mid">Exam Title</th>
                        <th class="mid">Exam Type</th>
                        <th class="mid">Description</th>
                        <th class="mins"></th>
                      </tr>
                    </thead>
                    <tbody id="examTblBody">
                    </tbody>
                    
                  </table>
                  <div id="pagealign">
		              <div id="examTblPagination">
		              </div>
	              </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <input type="hidden" id="courseId" value="${courseId}">
        
        <!--Modal exam settings starts here-->

        <div id="examSettingsModal" class="modal modal-fixed-footer fixed-modal">
            <div class="modal-header">
              <h5>Exam Settings</h5>
            </div>
            <div class="modal-content">
              <div class="content-row">
                <div>
                  <b>Duration (In Minutes)</b>
                    <input type="text" id="durationTxtBox" class="numbersOnly" value="30">
                  <b>Passing Score (In Percentage)</b>
                    <input type="text" id="passingScoreTxtBox" class="numbersOnly" value="75">
                  <b>Exam Retake</b>
                  <div>
                    <label>
                      <input type="checkbox" id="onlyWhenFailedChkBox">
                      <span style="color: #1e1e1e">Only When Failed</span>
                    </label><br>
                    <label>
                      <input type="checkbox" id="evenIfPassedChkBox">
                      <span style="color: #1e1e1e">Even if passed (The system will take the highest score)</span>
                    </label><br>
                    <span style="padding-right: 10px;">No. of Retake </span><input type="text" id="noOfRetakeTxtBox" disabled
                    style="width: 100px;" class="numbersOnly">
                  </div>
                  <div class="switch">
                    <b>Take exam in safe Exam Browser</b>
                    <label >
                      <input type="checkbox" id="isSafeBrowserChkBox">
                      <span class="lever" style="margin-left: 10px !important;margin-top: 0 !important;"></span>
                    </label>
                  </div>
                  <div style="margin-top: 20px;">
                    <b>Completion</b><br>
                    <label>
                      <input type="checkbox" id="isShowCorrectAnswerChkBox">
                      <span style="color: #1e1e1e">Show Correct Answer</span>
                    </label><br>
                    <label>
                      <input type="checkbox" id="isShowScoreChkBox">
                      <span style="color: #1e1e1e">Show Score</span>
                    </label><br>
                    <label>
                      <input type="checkbox" id="isShowBreakdownChkBox">
                      <span style="color: #1e1e1e">Show Score Breakdown</span>
                    </label>
                    <input type="hidden" id="courseExamId">
                  </div>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" id="examSettingsCancelBtn"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" type="submit" name="action" id="saveExamSettingsBtn"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>

        <!--Modal exam settings ends here-->
</body>
</html>