<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - JOB ROLES</title>
	<!-- START: CSS-HEADER -->  
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	 <jsp:include page="../admin/common/admin-js-header.jsp" />
	 <script src="static/assets/js/admin-jobrole.js"></script>
	<!-- END: JS-HEADER -->
</head>
<body>
<!-- Header -->
	<jsp:include page="../admin/common/admin-header.jsp" />
	
<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-sidebar.jsp" />
	<div class="container2">
          <div class="row">
            <div class="col s12 m6">
               <h4>Job Roles</h4>
            </div>
            <div style="padding-top:5px;" class="nav-wrapper col s12 m6 row">
              <div class="row">
                 <div style="margin-top:19px;" class="input-field col s6 m6">
                    <input style="padding-right: 0 !important;" id="searchJobRole" class="search" type="search" name="" placeholder="Search Job Role">
                     <label class="label-icon" for="search"><i style="margin-top:4px;" class="material-icons">search</i></label>
                     <i style="margin-top:1px;" class="material-icons" id="clearSearch">close</i>
                 </div>
                 <div class="input-field col s6 m6">
                    <a data-target="saveJobRoleModal" style="width:100%;text-transform:capitalize;" class="btn modal-trigger waves-effect waves-light bttn" id="addJobRoleLink" ><i class="material-icons left">add_circle</i>Add Job Role</a>
                 </div>
              </div>
            </div>
          </div>

          <div class="row">
            <hr style="width:100%;">
          </div>

          <div class="row">
            <div class="divcard">
              <table class="highlight responsive-table">
                <thead>
	                <tr>
	                    <th></th>
	                    <th>Job Code
	                    	<a href="#!" class="sortLink sort-by" data-sortdir = "DESC" data-sortname="jobCode"></a><a href="#" class="sortLink" data-sortdir = "ASC" data-sortname="jobCode"></a></th>
	                    <th>Job Role
	                    	<a href="#!" class="sortLink sort-by" data-sortdir = "DESC" data-sortname="jobName"></a><a href="#" class="sortLink" data-sortdir = "ASC" data-sortname="jobName"></a></th>
	                    <th>Description<a href="#!" class="sortLink sort-by" data-sortdir = "DESC" data-sortname="jobDesc"></a><a href="#"  class="sortLink" data-sortdir = "ASC" data-sortname="jobDesc"></a></th>
	                    <th>Status<a href="#!" class="sortLink sort-by" data-sortdir = "DESC" data-sortname="status"></a><a href="#" class="sortLink" data-sortdir = "ASC" data-sortname="status"></a></th>
	                </tr>
	              </thead>
                <tbody id="jobRoleTblBody">
                  <!-- Dynamically Populated -->
                </tbody>
              </table>
              <div id="pagealign">
	              <div id="jobRolePagination">
	                <!-- Pagination -->
	              </div>
              </div>
            </div>
           </div>
            
        </div>



        <!--Modal for save starts here-->

        <div id="saveJobRoleModal" class="modal modal-fixed-footer">
          <div class="modal-header">
            <h5>Add Job Role</h5>
          </div>
          <div class="modal-content">
            <div class="content-row">
              <div>
                <b>Code*</b>
                  <input type="text" name="" id="saveJobCode" class="validate">
                <b>Job Role*</b>
                  <input type="text" name="" id="saveJobName" class="validate">
                <b>Description</b>
                  <textarea style="resize:none;" id="saveDescription" class="materialize-textarea"></textarea>
                <b>Status</b>
                <label style="margin-left:10px !important;">
                  <input type="checkbox" id="saveJobStatus" checked/>
                  <span>Active</span>
                </label>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
            <a class="btn waves-effect waves-light bttn" id="saveJobRoleBtn" type="submit" name="action"><i class="material-icons left">save</i>Save</a>
          </div>
        </div>

        <!--Modal for save ends here-->

        <!--Modal for edit starts here-->

        <div id="editJobRoleModal" class="modal modal-fixed-footer">
          <div class="modal-header">
            <h5>Edit Job Role</h5>
          </div>
          <div class="modal-content">
            <div class="content-row">
            	<input type="hidden" id="editJobRoleId"/>
              <div>
                <b>Code*</b>
                  <input type="text" name="" id="editJobCode" class="validate" disabled>
                <b>Job Role*</b>
                  <input type="text" name="" id="editJobName" class="validate">
                <b>Description</b>
                  <textarea style="resize:none;" id="editDescription" class="materialize-textarea"></textarea>
                <b>Status</b>
                <label style="margin-left:10px !important;">
                  <input type="checkbox" id="editStatus" />
                  <span>Active</span>
                </label>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
            <a class="btn waves-effect waves-light bttn" id="updateJobRoleBtn" type="submit" name="action"><i class="material-icons left">save</i>Save</a>
          </div>
        </div>

        <!--Modal for edit ends here-->
</body>
</html>