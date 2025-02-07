<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - Topic And Difficulty</title>
	<!-- START: CSS-HEADER -->  
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	 <jsp:include page="../admin/common/admin-js-header.jsp" />
	 <script src="static/assets/js/admin-topicsanddifficulties.js"></script>
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
               <h4>Topic and Difficulty Level</h4>
            </div>
            <div style="padding-top:5px;" class="nav-wrapper col s12 m6 row">
              <div class="row">
                 <div style="margin-top:19px;" class="input-field col s6 m7">
                    <input id="searchTopicDifficultyTxtBox" class="search" type="search" name="" placeholder="Search Topic and Difficulty Level">
                    <label class="label-icon" for="search"><i style="margin-top:4px;" class="material-icons">search</i></label>
                    <i id="clearSearhField" style="margin-top:1px;" class="material-icons">close</i>
                 </div>
                 <div class="input-field col s6 m5">
                    <a id="addTopicDifficultyButton" data-target="modal1" id="add-location" style="width:100%;text-transform:capitalize;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" ><i class="material-icons left">add_circle</i>Add</a>
                 </div>
              </div>
            </div>
          </div>

          <div class="row">
            <hr style="width:100%;">
          </div>

          <div class="row">
            <div class="divcard">
              <table class="highlight striped responsive-table">
                <thead>
                  <tr>
                      <th>Actions</th>
                       <th>Topic<a href="#!" class="sort-by"></a></th>
                      <th>Status<a href="#!" class="sort-by"></a></th>
                  </tr>
                </thead>
                <tbody id="topicsTblBody">
                </tbody>
              </table>
              <div id="pagealign">
	              <div id="topicsPagination">
	              </div>
              </div>
            </div>
          </div>


          <div class="row">
            <div class="divcard">
              <table class="highlight striped responsive-table">
                <thead>
                  <tr>
                      <th>Actions</th>
                      <th>Difficulty Level<a href="#!" id="restable"><i class="material-icons down">arrow_drop_down</i></a><a href="#" id="restable"><i class="material-icons up">arrow_drop_up</i></a></th>
                      <th>Status<a href="#!" id="restable"><i class="material-icons down">arrow_drop_down</i></a><a href="#" id="restable"><i class="material-icons up">arrow_drop_up</i></a></th>
                  </tr>
                </thead>

                <tbody id="difficultyLevelsTblBody">
                </tbody>
              </table>
              <div id="pagealign">
	              <div id="difficultyLevelsPagination">
	              </div>
              </div>
            </div>
          </div>
            
        </div>


        <!--Modal starts here-->

        
        <div id="modal1" class="TDmodal modal modal-fixed-footer">
          <div class="modal-header">
            <h5 id="modalTitle">Add Topic/Difficulty Level</h5>
          </div>
          <div class="modal-content">
            <div class="content-row">
              <div>
                <b id="typeLabel">Type*</b>
                  <select id="topicDifficultyDropDown">
                    <option value="" disabled selected>Please Specify...</option>
                    <option value="1">Topic</option>
                    <option value="2">Difficulty level</option>
                  </select>
                <b id="fieldName"></b>
                <input id="topicDifficultyNameTxtBox" type="text" name="machine" value="" placeholder="Ex: Machine">
                <p><b>Status</b>
                  <label style="margin-left:10px !important;">
                    <input id="statusCheckBox" type="checkbox" checked />
                    <span style="color: #1e1e1e">Active</span>
                  </label>
                </p>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <a id="cancelButton" class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
            <a id="submitButton" class="btn waves-effect waves-light bttn" type="submit" name="action"><i class="material-icons left">save</i>Save</a>
          </div>
        </div>

        <!--Modal ends here-->
</body>
</html>