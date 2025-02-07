<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - TRAINING COURSE</title>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
	<script src="static/assets/js/admin-trainingcourse.js"></script>
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
               <h4>Course</h4>
            </div>
            <div style="padding-top:5px;" class="nav-wrapper col s12 m6 row">
              <div class="row">
                 <div style="margin-top:19px;" class="input-field col s6 m6">
                    <input id="searchCourseInfoTbl" type="search" name="" placeholder="Search Course">
                    <label class="label-icon" for="searchCourseInfoTbl"><i style="margin-top:4px;" class="material-icons">search</i></label>
                    <i style="margin-top:1px;" id="clearSearch" class="material-icons">close</i>
                 </div>
                 <div class="input-field col s6 m6">
                    <a href="admin.createtrainingcourse" style="width:100%;text-transform:capitalize;" class="btn waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">add_circle</i>Add Course</a>
                 </div>
              </div>
            </div>
          </div>

          <div class="row">
            <hr style="width:100%;">
          </div>

          <div class="row">
            <div class="divcard">
              <table class="striped highlight">
                <thead>
                  <tr>
                      <th>Action</th>
                      <th>Code<a href="#!" class="sort-by"></a></th>
                      <th>Course<a href="#!" class="sort-by"></a></th>
                      <th>Training Category<a href="#!" class="sort-by"></a></th>
                      <th>Delivery Method<a href="#!" class="sort-by"></a></th>
                      <th>Status<a href="#!" class="sort-by"></a></th>
                      <th>Modified Date<a href="#!" class="sort-by"></a></th>
                      <th>Modified By<a href="#!" class="sort-by"></a></th>
                  </tr>
                </thead>

                <tbody id="courseTblBody">
                  <!--Dynamic table-->
                </tbody>
              </table>
              <div id="pagealign">
              	<div id="courseTblPagination">
                	<!--Pagination-->
              	</div>
             </div>
            </div>
          </div>
            
        </div>

</body>
</html>