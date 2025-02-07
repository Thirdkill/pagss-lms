<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - EXAMINATIONS</title>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
	<script src="static/assets/js/admin-examinations.js"></script>
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
               <h4>Examinations</h4>
            </div>
            <div style="padding-top:5px;" class="nav-wrapper col s12 m7 row">
              <div class="row">
                 <div style="margin-top:19px;" class="input-field col s6 m7">
                    <input id="searchExam" type="search" name="" placeholder="Search Examination">
                    <label class="label-icon" for="search"><i style="margin-top:4px;" class="material-icons">search</i></label>
                    <i style="margin-top:1px;" class="material-icons" id="clearSearch">close</i>
                 </div>
                 <div class="input-field col s6 m5">
                    <a href="admin.examination?action=create" style="width:100%;text-transform:capitalize;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">add_circle</i>Create New Exam</a>
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
                      <th>Exam Title<a href="#!" class="sort-by"></a></th>
                      <th>Status<a href="#!" class="sort-by"></a></th>
                      <th>Date Modified<a href="#!" class="sort-by"></a></th>
                      <th>Modified by<a href="#!" class="sort-by"></a></th>
                  </tr>
                </thead>
                <tbody id="tblExamBody">
                </tbody>
              </table>
              <div id="pagealign">
	              <div id="tblExamPagination" class="center">
	                <!-- Pagination -->
	              </div>
	          </div>
            </div>
          </div>

        </div>
</body>
</html>