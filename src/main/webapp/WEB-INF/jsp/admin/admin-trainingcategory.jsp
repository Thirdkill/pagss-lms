<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - Training Category</title>
	<!-- START: CSS-HEADER -->  
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	 <jsp:include page="../admin/common/admin-js-header.jsp" />
	 <script src="static/assets/js/admin-trainingcategory.js"></script>
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
               <h4>Training Category</h4>
            </div>
            <div style="padding-top:5px;" class="nav-wrapper col s12 m6 row">
              <div class="row">
                 <div style="margin-top:19px;" class="input-field col s6 m6">
                    <input style="padding-right: 0 !important;" id="searchTrainingCategory" type="search" name="" placeholder="Search Training Category">
					<label class="label-icon" for="search"><i style="margin-top:4px;" class="material-icons">search</i></label>
					<i style="margin-top:1px;" class="material-icons" id="clearSearch">close</i>
                 </div>
                 <div class="input-field col s6 m6">
                    <button data-target="saveCategoryModal" id="addCategoryBtn" style="width:100%;text-transform:capitalize;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">add_circle</i>Add Training Category</button>
                 </div>
              </div>
            </div>
          </div>

          <div class="row">
            <hr style="width:100%;">
          </div>

          <div class="row row-1">
            <div class="divcard">
              <table class="highlight responsive-table">
                <thead>
                <tr>
                    <th id="restable"></th>
                    <th>Code<a href="#!" class="sortLink sort-by" data-sortdir = "DESC" data-sortname="categoryCode"></a><a href="#" class="sortLink" data-sortdir = "ASC" data-sortname="categoryCode"></a></th>
                    <th>Training Category<a href="#!" class="sortLink sort-by" data-sortdir = "DESC" data-sortname="categoryName"></a><a href="#" class="sortLink" data-sortdir = "ASC" data-sortname="categoryName"></a></th>
                    <th>Description<a href="#!" class="sortLink sort-by" data-sortdir = "DESC" data-sortname="description"></a><a href="#" class="sortLink" data-sortdir = "ASC" data-sortname="description"></a></th>
                    <th>Status<a href="#!" class="sortLink sort-by" data-sortdir = "DESC" data-sortname="status"></a><a href="#" class="sortLink" data-sortdir = "ASC" data-sortname="status"></a></th>
                </tr>
              </thead>
                <tbody id="trainingCategoryTblBody">
                  <!-- Dynamically populated -->
                </tbody>
              </table>
              <div id="pagealign">
	              <div id="trainingCategoryPagination">
	                <!--Pagination-->
	              </div>
              </div>
            </div>
          </div>
            
        </div>


         <div id="saveCategoryModal" class="modal modal-fixed-footer">
            <div class="modal-header">
              <h5>Add Training Category</h5>
            </div>
            <div class="modal-content">
              <div class="content-row">
                <div>
                  <b>Code*</b>
                    <input type="text" name="" id="saveCategoryCode" class="validate">
                  <b>Training Category*</b>
                    <input type="text" name="" id="saveCategoryName" class="validate">
                  <b>Description</b>
                    <textarea style="resize:none;" id="saveDescription" class="materialize-textarea"></textarea>
                  <b>Status</b>
                  <label style="margin-left:10px !important;">
                    <input type="checkbox" id="saveStatus" checked/>
                    <span>Active</span>
                  </label>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" id="saveCategoryBtn" type="submit" name="action"><i class="material-icons left">save</i>Save</a>
            </div>
          </div>

        <!--Modal save category starts here-->

        <!--Modal edit category starts here-->

          <div id="editCategoryModal" class="modal modal-fixed-footer">
            <div class="modal-header">
              <h5>Add Training Category</h5>
            </div>
            <div class="modal-content">
              <div class="content-row">
              <input type="hidden" id="editCategoryId">
                <div>
                  <b>Code*</b>
                    <input type="text" name="" id="editCategoryCode" class="validate" disabled>
                  <b>Training Category*</b>
                    <input type="text" name="" id="editCategoryName" class="validate">
                  <b>Description</b>
                    <textarea style="resize:none;" id="editDescription" class="materialize-textarea"></textarea>
                  <b>Status</b>
                  <label style="margin-left:10px !important;">
                    <input type="checkbox" id="editStatus"/>
                    <span>Active</span>
                  </label>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" id="updateCategoryBtn" type="submit" name="action"><i class="material-icons left">save</i>Save</a>
            </div>
          </div>

        <!--Modal edit caregory starts here-->
</body>
</html>