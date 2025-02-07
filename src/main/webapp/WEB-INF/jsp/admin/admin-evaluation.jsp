<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - EVALUATION</title>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
	<script src="static/assets/js/admin-evaluation.js"></script>
	<!-- END: JS-HEADER -->
</head>
<body>
	<!-- Header -->
	<jsp:include page="../admin/common/admin-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-sidebar.jsp" />
         <div class="container2">
          <div class="row">
            <div class="col s12 m5">
               <h4>Evaluation</h4>
            </div>
            <div style="padding-top:5px;" class="nav-wrapper col s12 m7 row">
                  <div class="row">
                     <div style="margin-top:19px;" class="input-field col s6 m7">
                        <input class="search" id="searchEvalTxtbox" style="padding-right: 0 !important;"
                         type="search" name="" placeholder="Search Evaluation">
                        <label class="label-icon" for="search">
                        <i style="margin-top:4px;" class="material-icons">search</i></label>
                        <i id="btnReset" style="margin-top:1px;" class="material-icons">close</i>
                     </div>
                     <div class="input-field col s6 m5">
                        <a href="admin.createevaluation" style="width:100%;text-transform:capitalize;" class="btn waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">add_circle</i>Create New Evaluation</a>
                     </div>
                  </div>
            </div>
          </div>
          
          <div class="row">
            <hr style="width:100%;">
          </div>  

          <div class="row">
            <div class="divcard">
              <table class="highlight striped">
                <thead>
                  <tr>
                      <th>Actions</th>
                      <th>Evaluation Title<a href="#!" class="sort-by"></a></th>
                      <th>Status<a href="#!" class="sort-by"></a></th>
                      <th>Date Modified<a href="#!" class="sort-by"></a></th>
                      <th>Modified by<a href="#!" class="sort-by"></a></th>
                  </tr>
                </thead>

                <tbody id="evaluationTblBody">
                </tbody>
              </table>
              <div id="pagealign">
	              <div id="evaluationPagination" class="center">
	                <!-- Pagination -->
	              </div>
              </div>
            </div>
           </div>

        </div>

         


         


        <!--Modal save location starts here-->

        <div id="locationModal" class="modal modal-fixed-footer">
            <div class="modal-header">
              <h5>Add Location</h5>
            </div>
            <div class="modal-content">
              <div class="content-row">
                <div>
                  <b>Code*</b>
                    <input type="text" name="" id="locationCode" class="validate">
                  <b>Location*</b>
                    <input type="text" name="" id="locationName" class="validate">
                  <b>Description</b>
                    <textarea style="resize:none;" id="description" class="materialize-textarea"></textarea>
                  <b>Status</b>
                  <label style="margin-left:10px !important;">
                    <input type="checkbox" id="locationStatus"/>
                    <span>Active</span>
                  </label>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" type="submit" name="action" id="saveLocationBtn"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>

        <!--Modal save location ends here-->

        <!--Modal edit location starts here-->

        <div id="editLocationModal" class="modal modal-fixed-footer">
            <div class="modal-header">
              <h5>Add Location</h5>
            </div>
            <div class="modal-content">
              <input type="hidden" id="locationId"/>
              <div class="content-row">
                <div>
                  <b>Code*</b>
                    <input type="text" name="" id="editLocationCode" class="validate">
                  <b>Location*</b>
                    <input type="text" name="" id="editLocationName" class="validate">
                  <b>Description</b>
                    <textarea style="resize:none;" id="editDescription" class="materialize-textarea"></textarea>
                  <b>Status</b>
                  <label style="margin-left:10px !important;">
                    <input type="checkbox" id="editLocationStatus"/>
                    <span>Active</span>
                  </label>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" id="updateLocationBtn" type="submit" name="action"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>

        <!--Modal edit location ends here-->
</body>
</html>