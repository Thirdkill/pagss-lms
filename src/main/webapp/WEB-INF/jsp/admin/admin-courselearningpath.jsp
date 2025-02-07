<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - LEARNING PATH</title>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
	<script src="static/assets/js/admin-courselearningpath.js"></script>
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
					     <li><a href="admin.coursepath?courseId=${courseId}">Learning Path</a></li>
                    <li style="font-size: 18px;">|</li>
                    </c:when></c:choose>
                    <li><a href="admin.courseexamination?courseId=${courseId}" ><u>Examinations</u></a></li>
                    <li style="font-size: 18px;">|</li>
                    <li><a href="admin.courseevaluation?courseId=${courseId}"><u>Evaluation</u></a></li>
                  </ul>
                </div>
              </div>
              <div class="row">
                <div class="right">
                  <a href="#!" class="btn waves-light waves-effect bttn" type="submit" style="margin-right: 5px;"><i class="material-icons left">arrow_back</i>Back</a>
                  <a data-target="addSectionModal" class="modal-trigger btn waves-light waves-effect bttn" type="submit" style="margin-right: 5px;"><i class="material-icons left">add_circle</i>Add Section</a>
                  <a data-target="addContentModal" class="modal-trigger btn waves-light waves-effect bttn" type="submit" style="margin-right: 5px;"><i class="material-icons left">add_circle</i>Add Content</a>
                  <a data-target="addExamModal" class="modal-trigger btn waves-light waves-effect bttn" type="submit"><i class="material-icons left">add_circle</i>Add Exam</a>
                </div>
              </div>
              <div class="row" id="learningPathCon">
               <!--<div style="margin-top: 20px;">
                  <table class="highlight striped">
                    <thead>
                      <tr>
                        <th class="large" colspan="2" style="text-align: left;">
                          Passenger Handling Basics
                        </th>
                        <th class="min">
                          <a href="#!" ><i class="material-icons" style="margin-top: 6px;">arrow_downward</i></a>
                          <a href="#"><i class="material-icons" style="margin-top: 6px;">arrow_upward</i></a>
                          <a href="#!"><i class="material-icons" style="margin-top: 6px;">edit</i></a>
                          <a href="#!"><i class="material-icons" style="margin-top: 6px;">delete</i></a>
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                          <td style="text-align: left;">Passenger Handling Basics</td>
                          <td>Document</td>
                          <td>
                            <a href="#!" ><i class="material-icons" style="margin-top: 6px;">arrow_downward</i></a>
                            <a href="#"><i class="material-icons" style="margin-top: 6px;">arrow_upward</i></a>
                            <a href="#!"><i class="material-icons" style="margin-top: 6px;">edit</i></a>
                            <a href="#!"><i class="material-icons" style="margin-top: 6px;">delete</i></a>
                          </td>
                      </tr>
                    </tbody>
                  </table>
                  <table class="highlight striped" style="margin-top: 20px;">
                    <thead>
                      <tr>
                        <th class="large" colspan="2" style="text-align: left;">
                          Passenger Handling Basics
                        </th>
                        <th class="min">
                          <a href="#!" ><i class="material-icons" style="margin-top: 6px;">arrow_downward</i></a>
                          <a href="#"><i class="material-icons" style="margin-top: 6px;">arrow_upward</i></a>
                          <a href="#!"><i class="material-icons" style="margin-top: 6px;">edit</i></a>
                          <a href="#!"><i class="material-icons" style="margin-top: 6px;">delete</i></a>
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                          <td style="text-align: left;">Passenger Handling Basics</td>
                          <td>Document</td>
                          <td>
                            <a href="#!" ><i class="material-icons" style="margin-top: 6px;">arrow_downward</i></a>
                            <a href="#"><i class="material-icons" style="margin-top: 6px;">arrow_upward</i></a>
                            <a href="#!"><i class="material-icons" style="margin-top: 6px;">edit</i></a>
                            <a href="#!"><i class="material-icons" style="margin-top: 6px;">delete</i></a>
                          </td>
                      </tr>
                    </tbody>
                  </table>
                </div>-->
              </div>
            </div>
          </div>
        </div>

        <!--Modal addSectionModal starts here-->

        <div id="addSectionModal" class="modal modal-fixed-footer fixed-modal" style="height: 350px;">
            <div class="modal-header">
              <h5>Add Section</h5>
            </div>
            <div class="modal-content">
              <div class="content-row">
                <div>
                  <b>Title*</b>
                    <input type="text" id="sectionTitle" class="validate">
                </div>
              </div>
              <div class="content-row">
                <b>Description</b>
                    <textarea style="resize:none;" id="sectionDescription" class="materialize-textarea"></textarea>
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" id="cancelSectionBtn"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" type="submit" id="saveSectionBtn"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>

        <!--Modal addSectionModal ends here-->

		<!--Modal  editSectionModal starts here-->

        <div id="editSectionModal" class="modal modal-fixed-footer fixed-modal" style="height: 350px;">
            <div class="modal-header">
              <h5>Edit Section</h5>
            </div>
            <div class="modal-content">
              <div class="content-row">
                <div>
                  <b>Title*</b>
                    <input type="text" id="editSectionTitle" class="validate">
                </div>
              </div>
              <div class="content-row">
                <b>Description</b>
                    <textarea style="resize:none;" id="editSectionDescription" class="materialize-textarea"></textarea>
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" id="editCancelSectionBtn"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" type="submit" id="editSectionBtn"><i class="material-icons left">save</i>Save</a>
            </div>
            <input type="hidden" id="learningPathSectionId">
        </div>

        <!--Modal editSectionModal ends here-->
        
        <!--Modal addContentModal starts here-->

        <div id="addContentModal" class="modal modal-fixed-footer fixed-modal">
            <div class="modal-header">
              <h5>Add Content</h5>
            </div>
            <div class="modal-content">
              <div class="content-row">
                <div>
                  <b>Section*</b>
                    <select id="sectionDropdown">
                      <option disabled selected value="0">Choose a section</option>
                    </select>
                </div>
              </div>
              <div class="content-row">
                <div>
                  <b>Content Type*</b>
                    <select id="contentTypeDropdown">
                      <option disabled selected value="0">Choose a content type</option>
                    </select>
                </div>
              </div>
              <div class="content-row" id="courseMaterialsDropdownCon">
                <div>
                  <b>File*</b>
                    <select id="courseMaterialsDropdown">
                       <option disabled selected value="0">Choose a file</option>
                    </select>
                </div>
              </div>
              <div class="content-row" id="fileLabelCon">
                <b>File*</b>
                    <input type="text" id="fileLabel" class="validate">
              </div>
              <div class="content-row" id="mediaFileUploadCon">
                <div class="file-field input-field" style="margin-top: 0 !important;">
                <form id="contentUploadFrm">
                  <div class="btn bttn right" style="margin-top: 20px;">
                    <span>Browse File</span>
                    <input type="file" name="mediaFileUpload" id="mediaFileUpload">
                  </div>
                  <div class="file-path-wrapper" style="padding-top: 0 !important;">
                    <b>Browse File</b>
                    <input class="file-path validate" type="text" id="fileName">
                  </div>
                 </form>
                </div>
              </div>
              <div class="content-row" id="urlCon">
                <b>URL *</b>
                    <input type="text" id="urlTxtBox" class="validate">
              </div>
            </div>
            <div class="modal-footer">
	           	<div id="modalContentBtn">
	              <a class="modal-close btn waves-effect waves-light bttn" type="button" id="ContentCancelBtn"><i class="material-icons left">cancel</i>Cancel</a>
	              <a class="btn waves-effect waves-light bttn" type="submit" id=saveContentBtn><i class="material-icons left">save</i>Save</a>
	            </div>
	            <div id="progressCon">
		            <b id="percentValue">0%</b>
		            <div class="progress">
		                <div id="progressBar" class="determinate"></div>
		            </div>
	            </div>
            </div>
           
        </div>

        <!--Modal addContentModal ends here-->

		<!--Modal edit ContentModal starts here-->

        <div id="editContentModal" class="modal modal-fixed-footer fixed-modal">
            <div class="modal-header">
              <h5>Edit Content</h5>
            </div>
            <div class="modal-content">
              <div class="content-row">
                <div>
                  <b>Section*</b>
                    <select id="editSectionDropdown">
                      <option disabled selected value="0">Choose a section</option>
                    </select>
                </div>
              </div>
              <div class="content-row">
                <div>
                  <b>Content Type*</b>
                    <select id="editContentTypeDropdown">
                      <option disabled selected value="0">Choose a content type</option>
                    </select>
                </div>
              </div>
              <div class="content-row" id="editCourseMaterialsDropdownCon">
                <div>
                  <b>File*</b>
                    <select id="editCourseMaterialsDropdown">
                       <option disabled selected value="0">Choose a file</option>
                    </select>
                </div>
              </div>
              <div class="content-row" id="editFileLabelCon">
                <b>File*</b>
                    <input type="text" id="editFileLabel" class="validate">
              </div>
              <div class="content-row" id="mediaFileUpload">
                <div class="file-field input-field" style="margin-top: 0 !important;">
                <form id="contentUploadFrm">
                  <div class="btn bttn right" style="margin-top: 20px;">
                    <span>Browse File</span>
                    <input type="file" name="mediaFileUpload" id="editMediaFileUpload">
                  </div>
                  <div class="file-path-wrapper" style="padding-top: 0 !important;">
                    <b>Browse File</b>
                    <input class="file-path validate" type="text" id="editFileName">
                  </div>
                 </form>
                </div>
              </div>
              <div class="content-row" id="urlCon">
                <b>URL *</b>
                    <input type="text" id="urlTxtBox" class="validate">
              </div>
            </div>
            <div class="modal-footer">
	           	<div id="modalContentBtn">
	              <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
	              <a class="btn waves-effect waves-light bttn" type="submit" id=saveContentBtn><i class="material-icons left">save</i>Save</a>
	            </div>
	            <div id="progressCon">
		            <b id="percentValue">0%</b>
		            <div class="progress">
		                <div id="progressBar" class="determinate"></div>
		            </div>
	            </div>
            </div>
           
        </div>

        <!--Modal edit ContentModal ends here-->
        <!--Modal addExamModal starts here-->

        <div id="addExamModal" class="modal modal-fixed-footer fixed-modal" style="height: 400px;">
            <div class="modal-header">
              <h5>Add Exam</h5>
            </div>
            <div class="modal-content">
              <div class="content-row">
                <div>
                  <b>Section*</b>
                    <select id="examSectionDropdown">
                    </select>
                </div>
              </div>
              <div class="content-row">
                <div>
                  <b>Source*</b>
                    <select id="sourceDropdown">
                    </select>
                </div>
              </div>
              <div class="content-row">
                <b>Exam Type*</b>
                    <select id="examTypeDropdown">
                      <option disabled selected>Choose an exam type</option>
                    </select>
              </div>
              <div class="content-row" id="examTitleCon">
                <b>Exam Title*</b>
                    <select id="examTitleDropdown">
                      <option disabled selected>Choose an exam</option>
                    </select>
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" id="btnCancelExam"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" type="submit" name="action" id="saveExamBtn"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>

        <!--Modal addExamModal ends here-->
	<input type="hidden" id="courseId" value="${courseId}">
</body>
</html>