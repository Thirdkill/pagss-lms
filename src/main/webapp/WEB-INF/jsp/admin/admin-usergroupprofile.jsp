<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>PAGSS - CREATE USERGROUP</title>
<!-- START: CSS-HEADER -->
<jsp:include page="../admin/common/admin-css-header.jsp" />
<!-- END: CSS-HEADER -->
<!-- START: JS-HEADER -->
<jsp:include page="../admin/common/admin-js-header.jsp" />
<script src="static/assets/js/admin-usergroupprofile.js"></script>
<!-- END: JS-HEADER -->
</head>
<body>
	<!-- Header -->
	<jsp:include page="../admin/common/admin-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-sidebar.jsp" />

	<div class="container2">
		<div class="row">
			<div class="col s12 m7">
				<h4 id="fieldPage"></h4>
			</div>
			<div id="navpad" class="nav-wrapper col s12 m5 row">
				<div class="row">
					<div class="input-field col s6 m6">
						<a href="admin.usergroup" style="width: 100%;"
							class="btn waves-effect waves-light bttn" type="submit" name=""><i
							class="material-icons left">not_interested</i>Cancel</a>
					</div>
					<div class="input-field col s6 m6">
						<a id="userGroupSubmit" style="width: 100%;" class="btn waves-effect waves-light bttn"
							type="submit" name=""><i class="material-icons left">save</i>Save</a>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<hr style="width: 100%;">
		</div>

		<div class="row">
			<div class="divcard">
				<div class="row">
					<div class="col s12 m6 input-field">
						<input type="text" id="userGroupCodeTxtBox" class="validate text-input">
						<label id="ugcLabel" class="active q" for="userGroupCodeTxtBox" >User Group Code*</label>
					</div>
					<div class="col s12 m6 input-field">
						<input type="text" id="userGroupNameTxtBox" class="validate text-input">
						<label id="ugnLabel" class="active q" for="userGroupNameTxtBox" >Name*</label>
					</div>
				</div>
				<div class="row">
					<div class="col s12 m12 input-field">
						<textarea style="resize: none;" id="userGroupDesc" name=""
							class="materialize-textarea"></textarea>
						<label id="ugdLabel" for="userGroupDesc" class="active q">Description</label>
					</div>
				</div>
				<div class="row">
					<div class="col s12 m5 input-field">
						<select id="isSubGroupDropDown">
							<option value="1">Yes</option>
							<option value="2" selected>No</option>
						</select> <label class="active">Subgroup?</label>
					</div>
					<div class="col s12 m7 input-field">
						<select id="mainUserGroupDropDown">
							<option selected="" disabled selected></option>
							<option>Yes</option>
							<option>No</option>
						</select> <label class="active">Main Group</label>
					</div>
				</div>
				<div class="row">
					<div class="col s9 m10 input-field">
						<select id="groupSupervisorDropDown">
						</select> <label class="active">Group Supervisor</label>
					</div>
					<div id="createusergrpstatus" class="col s3 m2 ">
						<b style="margin-right: 10px;">Status:</b> <label> <input
							type="checkbox" id="userGroupStatus" /> <span>Active</span>
						</label>
					</div>
				</div>
			</div>
		</div>

		<!--Text editor WYSIWYG starts here-->

		<div id="container">
			<div class="divcard">
				<div class="row">
					<div class="col s4 m3" style="margin-bottom: 15px;">
						<h6 style="font-weight: bold;">Members:</h6>
					</div>
					<div class="col s8 m9" style="text-align: right">
						<a id="" data-target="moveMembers" class="modal-trigger bttn btn waves-effect waves-light"
						type="submit"><i class="material-icons left">person_add</i>Move Members</a>
						<a id="addMemberModal" data-target="modal1"
							class="btn modal-trigger waves-effect waves-light bttn"
							type="submit" name=""><i class="material-icons left">person_add</i>Add
							Member</a>
					</div>
				</div>
				<div class="row">
					<div class="col s12 m12">
						<table class="highlight striped responsive-table">
							<thead style="background-color: #cccccc;">
								<th>Actions</th>
								<th>Last Name<a href="#!" class="sort-by"></a></th>
								<th>First Name<a href="#!" class="sort-by"></a></th>
								<th>Employee Code<a href="#!" class="sort-by"></a></th>
								<th>Job Role<a href="#!" class="sort-by"></a></th>
							</thead>
							<tbody id="userGroupMembersTb">
							</tbody>
						</table>
						<div id="pagealign">
							<div id="userGroupMembersPagination"></div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!--Text editor WYSIWYG ends here-->
	</div>
	<!--Modal starts here-->

	<div id="modal1" class="modal modal-fixed-footer fixed-modal">
		<div class="modal-header">
			<h5>Add User Group Members</h5>
		</div>
		<div class="modal-content">
			<div class="content-row">
				<div>
					<h6 style="font-weight: bold; margin-bottom: 25px;">Filter</h6>
				</div>
			</div>
			<div class="content-row">
				<div class="row">
					<div style="padding-top: 5px;" class="nav-wrapper col s12 m6 row">
					
							<div class="row">
								<div class="input-field col s4 hide-on-large-only"></div>
								<div class="input-field col s8 m12">
									<input id="userSearchTxt" style="padding-right: 0 !important;" id="search"
										type="search" name="" placeholder="Search Employee"> <label
										class="label-icon" for="search"><i
										 style="margin-top: 4px;" class="material-icons">search</i></label> <i
										 id="btnSearchReset" style="margin-top: 1px;" class="material-icons">close</i>
								</div>
							</div>
					</div>
					<div class="input-field col hide-on-small-only m6">
						<select id="jobRoleDropdown">
						</select>
						<label for="jobRoleDropdown" class="active" style="font-size: 18px;font-weight: bold;color:#1e1e1e">Job Role</label>
					</div>
				</div>
			</div>
			<div class="content-row">
				<table class="highlight responsive-table">
					<thead>
						<tr>
							<th><label style="margin-left: 15px !important;"> <input
									id="checkAllChkBox" type="checkbox" /> <span></span>
							</label></th>
							<th>Last Name<a href="#!"><i
									class="material-icons down">arrow_drop_down</i></a><a href="#"><i
									class="material-icons up">arrow_drop_up</i></a></th>
							<th>First Name<a href="#!"><i
									class="material-icons down">arrow_drop_down</i></a><a href="#"><i
									class="material-icons up">arrow_drop_up</i></a></th>
							<th>Employee Code<a href="#!"><i
									class="material-icons down">arrow_drop_down</i></a><a href="#"><i
									class="material-icons up">arrow_drop_up</i></a></th>
							<th>Job Role<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a
								href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
						</tr>
					</thead>

					<tbody id="employeeTblBody">
					</tbody>
				</table>
				<div id="pagealign">
					<div id="employeeTblPagination">
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<a class="modal-close btn waves-effect waves-light bttn"
				type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
			<a id="userGroupMemberSubmit" class="btn waves-effect waves-light bttn" type="submit"
				name="action"><i class="material-icons left">save</i>Save</a>
		</div>
	</div>

	<!--Modal ends here-->
	
	<!-- Move member modal starts here -->
	
	<div id="moveMembers" class="moveModal modal modal-fixed-footer fixed-modal">
		<div class="modal-header">
			<h5>Move Users to Another User Group</h5>
		</div>
		<div class="modal-content">
			<div>
				<b>User Group:*</b>
				<select>
					<option>asd</option>
					<option>asd</option>
					<option>asd</option>
				</select>
			</div>
		</div>
		<div class="modal-footer">
			<a class="modal-close btn waves-effect waves-light bttn"
				type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
			<a id="userGroupMemberSubmit" class="btn waves-effect waves-light bttn" type="submit"
				name="action"><i class="material-icons left">save</i>Move</a>
		</div>
	</div>
	
	<!-- Move member modal ends here -->
</body>
</html>