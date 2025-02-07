<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>PAGSS - USER GROUP</title>
<!-- START: CSS-HEADER -->
<jsp:include page="../admin/common/admin-css-header.jsp" />
<!-- END: CSS-HEADER -->
<!-- START: JS-HEADER -->
<jsp:include page="../admin/common/admin-js-header.jsp" />
<script src="static/assets/js/admin-usergroup.js"></script>
<!-- END: JS-HEADER -->
</head>
<body>
	<!-- Header -->
	<jsp:include page="../admin/common/admin-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-sidebar.jsp" />

	<!-- Content Page -->
	<div class="container2">
		<div class="row">
			<div class="col s12 m6">
				<h4>User Group</h4>
			</div>
			<div style="padding-top: 5px;" class="nav-wrapper col s12 m6 row">
				<div class="row">
					<div style="margin-top: 19px;" class="input-field col s6 m6">
						<input id="searchUserGroup" style="padding-right: 0 !important;" 
							type="search" name="" placeholder="Search User Group"> <label
							class="label-icon" for="search"><i
							style="margin-top: 4px;" class="material-icons">search</i></label> <i id="clearSearchBox" style="margin-top: 1px;" class="material-icons">close</i>
					</div>
					<div class="input-field col s6 m6">
						<a href="admin.usergrouppage?action=ADD" data-target="modal1" id="addUserGroupBtn"
							style="width: 100%; text-transform: capitalize;"
							class="btn modal-trigger waves-effect waves-light bttn"
							type="submit" name="">
							<i class="material-icons left">add_circle</i>Add User Group
						</a>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<hr style="width: 100%;">
		</div>

		<div class="row">
			<div class="divcard">
				<table class="striped highlight">
					<thead>
						<tr>
							<th id="restable">Action</th>
							<th>Code<a href="#!" class="sort-by"></a></th>
								<th>Name<a href="#!" class="sort-by"></a></th>
								<th>Description<a href="#!" class="sort-by"></a></th>
								<th>Group Supervisor<a href="#!" class="sort-by"></a></th>
								<th>Status<a href="#!" class="sort-by"></a></th>
								<th>Modified Date<a href="#!" class="sort-by"></a></th>
								<th>Modified By<a href="#!" class="sort-by"></a></th>
						</tr>
					</thead>

					<tbody id="usergroupTblBody">
						<!--Dynamic table-->
					</tbody>
				</table>
				<div id="pagealign">
					<div id="usergroupPagination">
						<!--Pagination-->
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>