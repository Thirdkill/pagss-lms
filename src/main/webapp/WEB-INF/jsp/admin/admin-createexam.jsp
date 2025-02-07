<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	 <c:choose>
        <c:when test="${action != 'create' }">
	       <title>PAGSS - EDIT EXAM</title>
	    </c:when>
	    <c:otherwise>
	     <title>PAGSS - CREATE EXAM</title>
	    </c:otherwise>
	</c:choose>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
	<jsp:include page="../admin/common/admin-tinymce-header.jsp" />
	<script src="static/assets/js/utility/tinymce-util.js"></script>
	<script src="static/assets/js/admin-createexam.js"></script>
	<!-- END: JS-HEADER -->
</head>
<body>
<div>
	<!-- Header -->
	<jsp:include page="../admin/common/admin-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-sidebar.jsp" />
	<div class="container2">
          <div class="row">
            <div class="col s12 m8">
            <c:choose>
               <c:when test="${action != 'create' }">
			       <h4>Edit Exam</h4>
			    </c:when>
			    <c:otherwise>
			      <h4> Create New Exam</h4>
			    </c:otherwise>
			</c:choose>
            </div>
            <div id="navpad" class="nav-wrapper col s12 m4 row">
              <div class="row">
                 <div class="input-field col s6 m6">
                    <a href="#!" id="cancelExamBtn" style="width:100%;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">not_interested</i>Cancel</a>
                 </div>
                 <div class="input-field col s6 m6">
                    <a style="width:100%;" class="btn modal-trigger waves-effect waves-light bttn" id="saveExamBtn" name="" ><i class="material-icons left">save</i>Save</a>
                 </div>
              </div>
            </div>
          </div>
            
          <div class="row">
            <hr style="width:100%;">
          </div>

          <div class="row">
            <div class="divcard">
              <div class="row">
                <div class="col s12 m5">
                  <b>Exam Code</b>
                  <input type="text" class="numbersOnly" name="" id="examCode" class="validate">
                </div>
                <div class="col s10 m6">
                  <b>Exam Title</b>
                  <input type="text" name="" id="examTitle" class="validate">
                </div>
                <div class="col s2 m1 switch left">
                  <b>Status</b>
                  <label>
                    <input type="checkbox" id="examStatus" checked>
                    <span style="" class="lever left"></span>
                  </label>
                </div>
              </div>
              <div style="padding-top: 10px;" class="row">
                <div class="col s12 m5 switch">
                  <b>Exam Type</b>
                  <select id="examTypeDropdown">
                    <option selected disabled value="0">Choose an option</option>
                  </select>
                  <b class="left" style="margin:10px 15px 0 0;">Allow Attachment</b>
                  <label>
                    <input type="checkbox" id="allowAttachment">
                    <span class="lever left"></span>
                  </label>
                </div>
                <div class="col s12 m7">
                  <div>
                    <b>Exam Description</b>
                    <div id="texteditor">
                    </div>
                  </div>
                </div>
                <div class="right buttonright">
                  <a href="#addSectionModal" data-target="addSectionModal" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="action"><i class="material-icons left">add_circle</i>Add Section</a>
               </div>
              </div>
            </div>
          </div>

		<div id="sectionsCon">
        </div>
        </div>
        <!--Modal add section starts here-->

        <div id="addSectionModal" class="modal modal-fixed-footer" style="overflow:hidden;">
            <div class="modal-header">
              <h5>Add Section</h5>
            </div>
            <div class="modal-content">
              <div class="content-row" style="margin-bottom: 10px;">
                <div class="switch">
                  <b>Name*</b>
                    <input type="text" id="sectionName" class="validate">
                </div>
              </div>
              <div class="content-row row" style="margin-bottom: 10px;">
                <div class="col s12 m6">
                  <div class="switch right">
                    <span style="margin-right: 5px;font-weight:bold;">Randomized</span>
                    <label>
                      <input type="checkbox" id="isRandomized" value="randomExam">
                      <span style="margin-top: 0 !important;" class="lever"></span>
                    </label>
                  </div>
                </div>
                <div class="col s12 m6">
                  <div class="switch">
                    <span style="margin-right: 5px;font-weight:bold;">Shuffle Questions</span>
                    <label>
                      <input type="checkbox" id="isShuffle" value="shuffleExam">
                      <span style="margin-top: 0 !important;" class="lever"></span>
                    </label>
                  </div>
                </div>
              </div>
              <div class="content-row">
                <div class="col s12 m12" id="questionTypeDiv" style="display:none">
                  <b>Type*</b>
                    <select id="questionTypeDropDown">
                    	<option disabled selected value="">CHOOSE AN OPTION</option>
                    </select>
                </div>
              </div>
              <div class="content-row center">
                <p><b>Note:</b> Randomization settings cannot be undone once saved</p>
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" type="submit" name="action" id="addSectionBtn"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>

        <!--Modal add section ends here-->

        <!--Modal add part starts here-->

        <div id="partModal" class="modal modal-fixed-footer" style="height: 400px;">
            <div class="modal-header">
              <h5>Add Part</h5>
            </div>
            <div class="modal-content">
            	<input type="hidden" id="hiddenQuestionTypeId">
              <div class="content-row">
              <input type="hidden" id="partSectionValue">
                <div>
                  <b>Topic*</b>
                    <select id="partTopicDropDown">
                      <option disabled value="0" selected>Choose an option</option>
                    </select>
                  <b>Difficulty Level*</b>
                    <select id="partDifficultyDropDown">
                      <option value="0" disabled selected>Choose an option</option>
                    </select>
                  <b>No. of Questions*</b>
                    <input type="text" class="numbersOnly" id="partNoOfQuestion">
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" type="submit" name="action" id="addPartBtn"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>

        <!--Modal add part ends here-->

        <!-- Part preview Modal starts here  -->
        <div id="partPreviewModal" class="modal modal-fixed-footer" style="height: 400px;">
          <div class="modal-content">
            <div class="content-row">
              <div class="carousel carousel-slider center">
                <div class="carousel-fixed-item center">
                  <a class="btn waves-effect white grey-text darken-text-2">button</a>
                  <h2>First Panel</h2>
                  <p class="white-text">This is your first panel</p>
                </div>
                <div class="carousel-item red white-text" href="#one!">
                  <h2>First Panel</h2>
                  <p class="white-text">This is your first panel</p>
                </div>
                <div class="carousel-item amber white-text" href="#two!">
                  <h2>Second Panel</h2>
                  <p class="white-text">This is your second panel</p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- Part preview Modal ends here  -->

        <!--Modal add existing starts here-->
        <div id="addExistingModal" class="modal modal-fixed-footer">
            <div class="modal-header">
              <h5>Add Existing Question</h5>
            </div>
            <div class="modal-content">
              <b>Filter</b>
              <div class="content-row">
               <input type="hidden" id="existingSectionValue">
               	<div class="row">
	               	<div class="col s6 m6">
	                  <b>Type</b>
	                    <select id="existQuestionTypeDropdown">
	                      <option value="0">All</option>
	                    </select>
	                </div>
	                <div class="col s6 m6">
	                  <b>Topic</b>
	                    <select id="existTopicDropDown">
	                      <option value="0">All</option>
	                    </select>
	                </div>
                 </div>
                 <div class="nav-wrapper row">
                 	<div class="col s6 m6">
	                   <b>Difficulty Level</b>
	                    <select id="existDifficultyDropdown">
	                      <option value="0">All</option>
	                    </select>
                    </div>
                    <div style="margin-top:19px;" class="input-field col s6 m6">
	                    <input style="padding-right: 0 !important;" id="searchexistingexam" class="search" type="search" name="" placeholder="Search Existing Exam">
	                    <label class="label-icon" for="search"><i style="margin-top:4px;" class="material-icons">search</i></label>
	                    <i style="margin-top:1px;" class="material-icons" id="clearSearch">close</i>
                    </div>
                 </div>
              </div>
              <div class="content-row">
                <div class="right" style="margin-bottom:20px;">
                  <a class="btn waves-effect waves-light bttn" id="searchQuestionTxtBox" type="submit" name="action"><i class="material-icons left">search</i>Search</a>
                </div>
              </div>
              <div class="content-row">
                <table class="highlight responsive-table">
                  <thead>
                    <tr>
                        <th>
                          <label>
                            <input type="checkbox" id="selectAllCheckBox"/>
                            <span></span>
                          </label>
                        </th>
                        <th>Question<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
                        <th>Topic<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
                        <th>Difficulty level<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
                        <th>Type<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
                    </tr>
                  </thead>

                  <tbody id="questionTblBody">
                    <!--<tr>
                      <td>
                        <label style="margin-left:15px !important;">
                          <input type="checkbox" />
                          <span></span>
                        </label>
                      </td>
                      <td>Eclair</td>
                      <td>$0.87</td>
                      <td>Eclair</td>
                      <td>$0.87</td>
                    </tr>-->
                  </tbody>
                </table>
                <div id="pagealign">
                  <div id="questionTblPagination">
                  </div>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action" id="btnCancel"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" type="submit" name="action" id="AddQuestionBtn"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>
        <!--Modal add existing ends here-->
        <input type="hidden" id="hiddenQuestionTypeId">
        <input type="hidden" id="action" value="${action}">
        <input type="hidden" id="examId" value="${examId}">
        <input type="hidden" id="module" value="${module}">
        <input type="hidden" id="courseId" value="${courseId}">
        <input type="hidden" id="classId" value="${classId}"> 
</div>
</body>
</html>