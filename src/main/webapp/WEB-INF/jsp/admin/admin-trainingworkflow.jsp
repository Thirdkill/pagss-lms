<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - Training Flow Setup</title>
	<!-- START: CSS-HEADER -->  
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	 <jsp:include page="../admin/common/admin-js-header.jsp" />
	 <script src="static/assets/js/admin-trainingworkflow.js"></script>
	<!-- END: JS-HEADER -->
</head>
<body>
<!-- Header -->
	<jsp:include page="../admin/common/admin-header.jsp" />
	
<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-sidebar.jsp" />
<div class="container2">
          <div class="row">
            <div class="col s12 m12">
               <h4>Training Flow Setup</h4>
            </div>
          </div>

          <div class="row">
            <hr style="width:100%;">
          </div>

          <div class="row">
            <div id="left-option" class="col s12">
              <form style="margin-left: -3px;margin-top: -4px;">
                <div id="left-option" class="col s6" style="padding-left: 0;">
                  <div class="col s2 m1" style="padding: 0;margin-top: 10px;">
                    <label>
                     <input type="radio" name="tfs" class="with-gap" value="jobrole" id="jobRoleRadio" checked>
                     <span></span>
                   </label>
                  </div>
                  <div class="col s10 m11 input-field" style="padding-left:0;margin: 0;" id="jobRoleDropDownCon">
	                  <h6>Job Role</h6>
	                  <select id="jobRoleDropDown">
	                    <option value="" disabled selected>Choose your option</option>
	                  </select>
	                  <input type="hidden" id="assignmentType">
                </div>
                </div>
                <div id="right-option" class="col s6">
                  <div class="col s2 m1" style="padding-left:0;margin-top: -5px;">
                    <p>
                      <label>
                        <input type="radio" name="tfs" class="with-gap" value="usergrp" id="userGroupRadio">
                        <span></span>
                      </label>
                    </p>
                  </div>
                  <div class="col s10 m11 input-field" style="padding-left:0;margin: 0;" id="userGroupCon">
                    <h6>User Group</h6>
                   <select id="userGroupDropDown">
                    <option value="" disabled selected>Choose your option</option>
                  </select>
                  </div>
                </div>
              </form>
              <div class="right buttonright" style="margin-bottom:20px;">
                  <a data-target="addCourseModal" id="btnAddTrainingStageBtn" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" disabled>
                <i class="material-icons left">add_circle</i>Add training stage</a>
                </div>
            </div>
           </div>




           <!--Option starts here-->
           
           <div id="container" class="row opt1 showOpt">
            <table class="highlight responsive-table">
              <thead>
                <tr>
                    <th>Order No.</th>
                    <th>Category</th>
                    <th>Course</th>
                    <th>Code</th>
                    <th>Prerequisite</th>
                    <th>Required</th>
                    <th>Recurring</th>
                    <th class="min">Interval</th>
                    <th>Action</th>
                </tr>
              </thead>
              <tbody id="trainingFlowTblBody">
                
              </tbody>
            </table>
            <div id="trainingFlowTblPagination" style="display:none;">
            </div>
         </div>
           <!--Option ends here-->
            
        </div>

         <!--Modal Starts here-->
         <div id="addCourseModal" class="modal modal-fixed-footer">
            <div class="modal-header">
              <h5>Add Training Stage</h5>
            </div>
            <div class="modal-content">
              <div style="padding-top:5px;" class="content-row nav-wrapper col s12 m7 row">
                    <div class="row">
                      <div class="col m6 hide-on-med-and-down">
                      </div>
                      <div class="input-field col s12 m6">
                        <input style="padding-right: 0 !important;" id="searchCourse" type="search" name="" placeholder="search">
                          <label class="label-icon" for="search"><i style="margin-top:4px;" class="material-icons">search</i></label>
                        <i style="margin-top:1px;" id="clearSearch" class="material-icons">close</i>
                      </div>
                    </div>
              </div>
              <div class="content-row">
                <table class="highlight responsive-table">
                  <thead>
                    <tr>
                        <th>
                          <label style="margin-left:15px !important;">
                            <input type="checkbox" id="checkAllChkBox"/>
                            <span></span>
                          </label>
                        </th>
                        <th>Course Code<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
                        <th>Course<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
                        <th>Category<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
                        <th>Delivery Method<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
                    </tr>
                  </thead>

                  <tbody id="courseInfoTblBody">
					<!-- Dynamically Populated -->
                  </tbody>
                </table>
                <div id="pagealign">
	                <div id="courseInfoPagination">
	                </div>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" id="btnCancel" type="button" ><i class="material-icons left">cancel</i>Cancel</a>
              <button class="btn waves-effect waves-light bttn" id="btnSaveCourse" type="submit" name="action"><i class="material-icons left">save</i>Save</button>
            </div>
        </div>
        <!--Modal ends here-->
</body>
</html>