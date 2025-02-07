<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>PAGSS - USERS</title>
<!-- START: CSS-HEADER -->
<jsp:include page="../admin/common/admin-css-header.jsp" />
<!-- END: CSS-HEADER -->
<!-- START: JS-HEADER -->
<jsp:include page="../admin/common/admin-js-header.jsp" />
<script src="static/assets/js/admin-users.js"></script>
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
               <h4>Users</h4>
            </div>
            <div id="navpad" class="nav-wrapper col s12 m3 row">
              <div class="row">
                <div class="input-field col s12 m12">
					<a href="admin.userpage?action=ADD" id="add-location"
						style="width: 100%; text-transform: capitalize;"
						class="btn modal-trigger waves-effect waves-light bttn"
						type="submit" name=""><i class="material-icons left">add_circle</i>Create
						New User</a>
				</div>
              </div>
            </div>
          </div>

          <div class="row">
            <hr style="width:100%;">
          </div>

          <div class="row">
            <div class="divcard">
                 <legend style="margin-bottom: 10px;"><b>&nbspSearch Filter&nbsp</b></legend>
                 <div class="row">
                   <div class="col s12 m4">
                     <b>Job Role</b>
                     <select id="jobRoleDropdown">
                       <option selected="">All</option>
                     </select>
                   </div>
                   <div class="col s12 m4">
                     <b>User Type</b>
                     <select id="userTypeDropdown">
                       <option selected="">All</option>
                     </select>
                   </div>
                   <div class="col s12 m4">
                     <b>Status</b>
                     <select id="statusDropdown">
                       <option selected="">All</option>
                       <option value="1">Active</option>
                       <option value="0">Inactive</option>
                     </select>
                   </div>
                 </div>
                 <div class="row">
					<div class="col s12 m4">
						<b>Last Name</b> <input id="lastNameTxt" type="text" name="">
					</div>
					<div class="col s12 m4">
						<b>First Name</b> <input id="firstNameTxt" type="text" name="">
					</div>
					<div class="col s12 m4">
						<b>Employee Number</b> <input id="emplyoyeeNoTxt" type="text"
							name="employee_number">
					</div>
				</div>
                 <div class="row">
				<div class="right buttonright">
					<a id="btnReset" class="btn waves-effect waves-light bttn"
						type="button" name="action"><i class="material-icons left">refresh</i>Reset</a>
					<a id="btnSubmit" class="btn waves-effect waves-light bttn"
						type="submit" name="action"><i class="material-icons left">search</i>Search</a>
				</div>
			</div>
           </div>
           </div>



           <div id="container" class="row">
            <div class="divcard">
              <table class="highlight striped responsive-table">
                <thead>
                  <tr>
                      <th>Actions</th>
                      <th>Last Name<a href="#!" class="sort-by"></a></th>
                      <th>First Name<a href="#!" class="sort-by"></a></th>
                      <th>Employee Number<a href="#!" class="sort-by"></a></th>
                      <th>Job Role<a href="#!" class="sort-by"></a></th>
                      <th>Username<a href="#!" class="sort-by"></a></th>
                      <th>User Type<a href="#!" class="sort-by"></a></th>
                      <th>Status<a href="#!" class="sort-by"></a></th>
                   </tr>
                </thead>
                <tbody id="userTblBody">
                  <!--responsive table content-->
                </tbody>
              </table>
              <div id="pagealign">
	               <div id="userTblPagination">
	                  <!--Pagination-->
	               </div>
              </div>
              
              <div class="row" style="margin-top:20px;display:flex;justify-content:left;">
	               <b>Active: </b>&nbsp<span id="activeCount">0</span>&nbsp&nbsp
	               <b>Inactive: </b>&nbsp<span id="inactiveCount">0</span>&nbsp&nbsp
	               <b>Count: </b>&nbsp<span id="totalCount">0</span>
              </div>
              </div>
            </div>
          </div>
            
        </div>
</body>
</html>