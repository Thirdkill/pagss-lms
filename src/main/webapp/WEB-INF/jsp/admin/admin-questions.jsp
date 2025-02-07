<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - QUESTIONS</title>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
	<script src="static/assets/js/admin-questions.js"></script>
	<!-- END: JS-HEADER -->
</head>
<body>
	<!-- Header -->
	<jsp:include page="../admin/common/admin-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-sidebar.jsp" />
	<div class="container2">
          <div class="row">
            <div class="col s12 m9">
               <h4>Questions</h4>
            </div>
            <div id="navpad" class="nav-wrapper col s12 m3 row">
              <div class="row">
                 <div class="input-field col s12 m12">
                    <a href="admin.question?action=create" id="add-location" style="width:100%;text-transform:capitalize;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">add_circle</i>Add Question</a>
                 </div>
              </div>
            </div>
          </div>

          <div class="row">
            <hr style="width:100%;">
          </div>
            
          <div class="row">
            <div class="divcard">
               <legend style="margin-bottom: 10px;"><b>Search Filter</b></legend>
               <div class="row">
                 <div class="col s12 m6">
                   <b>Type</b>
                   <select id="questionTypeDropDown">
                     <option selected value="0">All</option>
                   </select>
                 </div>
                 <div class="col s12 m6">
                   <b>Topic</b>
                   <select id="topicDropDown">
                     <option selected value="0">All</option>
                   </select>
                 </div>
                 <div class="col s12 m6">
                   <b>Difficulty Level</b>
                   <select id="difficultyDropDown">
                     <option selected value="0">All</option>
                   </select>
                 </div>
                 <div class="col s12 m6">
                   <b>Status</b>
                   <select id="statusDropDown">
                     <option value="2">All</option>
                     <option value="1" selected>Active</option>
                     <option value="0">Inactive</option>
                   </select>
                 </div>
                 <div class="right buttonright">
                    <a class="btn waves-effect waves-light bttn" id= "resetBtn" type="button" name="action"><i class="material-icons left">refresh</i>Reset</a>
                    <a id="searchQuestionBtn" class="btn waves-effect waves-light bttn"><i class="material-icons left">search</i>Search</a>
                 </div>
               </div>
            </div>
          </div>

          <div class="row">
            <div class="divcard">
              <table class="highlight ">
                <thead>
                  <tr>
                      <th>Actions</th>
                      <th>Question<a href="" class="sort sort-by" data-sortname="label" data-sortdir="DESC"></a><a href="#" class="sort" data-sortname="label" data-sortdir="ASC"></a></th>
                      <th>Topic<a href="" class="sort sort-by" data-sortname="topicDesc" data-sortdir="DESC"></a><a href="#" class="sort" data-sortname="topicDesc" data-sortdir="ASC"></a></th>
                      <th>Difficulty Level<a href="" class="sort sort-by" data-sortname="difficultyName" data-sortdir="DESC"></a><a href="#" class="sort" data-sortname="difficultyName" data-sortdir="ASC"></a></th>
                      <th>Type<a href="" class="sort sort-by" data-sortname="questionTypeDesc" data-sortdir="DESC"></a><a href="#" class="sort" data-sortname="questionTypeDesc" data-sortdir="ASC"></a></th>
                      <th>Status<a name="sort"  class="sort sort-by" data-sortname="status" data-sortdir="DESC"></a><a href="#" class="sort" data-sortname="status" data-sortdir="ASC"></a></th>
                  </tr>
                </thead>
                <tbody id="questionsTblBody">
                  <!-- Dynamically Populated -->
                </tbody>
                
              </table>
              <div id="pagealign">
	              <div id="questionsPagination">
	              </div>
              </div>
            </div>
          </div>
          
           <!-- Question Preview with image -->
          <div id="viewQuestionModalwithPicture" class="modal modal-fixed-footer">
              <div class="modal-header">
                <h5>Question</h5>
              </div>
              <div class="modal-content modal-contentSize">
                <div class="content-row">
                  <div class="row">
                    <div class="col s12 m8">
                      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                      <div class="divcard">
                        asd
                      </div>
                    </div>
                    <div class="col s12 m4">
                      <img src="asd.jpg">
                    </div>
                  </div>
                </div>
              </div>
          </div>
          <!-- Question preview without Image -->
          <div id="viewQuestionModalwithoutPicture" class="modal modal-fixed-footer">
              <div class="modal-header">
                <h5>Question</h5>
              </div>
              <div class="modal-content modal-contentSize">
                <div class="content-row">
                  <div class="row">
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                    <div class="divcard">
                      asd
                    </div>
                  </div>
                </div>
              </div>
          </div>
        </div>
</body>
</html>