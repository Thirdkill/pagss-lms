<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title></title>
<!-- START: CSS-HEADER -->
<jsp:include page="../admin/common/admin-css-header.jsp" />
<link rel="stylesheet" type="text/css" href="static/assets/css/page.css">
<!-- END: CSS-HEADER -->
<!-- START: JS-HEADER -->
<jsp:include page="../admin/common/admin-js-header.jsp" />
<script src="static/assets/js/admin-userprofilefield.js"></script>
<!-- END: JS-HEADER -->
</head>
<body>
	<div>
		<!-- Header -->
		<jsp:include page="../admin/common/admin-header.jsp" />

		<!-- Left Side bar -->
		<jsp:include page="../admin/common/admin-sidebar.jsp" />

		<!-- Content Page -->
		<div class="container2">
			<div class="row">
				<div class="col s12 m8">
					<h4 id="fieldPage"></h4>
				</div>
				<div id="navpad" class="nav-wrapper col s12 m4 row">
					<div class="row">
						<div class="input-field col s6 m6">
							<a href="admin.user" id="add-location" style="width: 100%;"
								class="btn modal-trigger waves-effect waves-light bttn"
								type="submit" name=""><i class="material-icons left">not_interested</i>Cancel</a>
						</div>
						<div class="input-field col s6 m6">
							<a id="userSubmit" style="width: 100%;"
								class="btn modal-trigger waves-effect waves-light bttn"
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
						<div class="col s12 m3">
							<div class="card">
								<div class="card-image">
								
									<img id="userProfilePicture" src="static/assets/images/usersimage/user.png"> <a
										id="uploadPhotoLink"
										class="btn-floating btn-large halfway-fab waves-effect waves-light red bttn"><i
										class="material-icons">add</i></a>
								</div>
								<form id="uploadPhotoFrm">
                					<input type="file" style="visibility:hidden;" id="fileUpload" name="fileUpload">
                				</form>
							</div>
						</div>
						<div class="col s12 m9" style="padding: 10px;">
							<div class="row">
								<div class="col s12 m5 input-field">
									<input type="text" id="employeeNoTxtbox"
										class="validate text-input" name="" placeholder=" "> <label
										id="empLabel" for="employeeNoTxtbox" class="active">Employee Number*</label>
								</div>
								<div class="col s10 m5 input-field">
									<select id="userTypeDropDown">
									</select> <label id="uTypeLabel" for="userTypeDropDown" class="active">User
										Type*</label>
								</div>
								<div class="col s2 m1 switch left">
									<b>Status</b> <label> <input id="statusCheckBox"
										type="checkbox"> <span style="" class="lever"></span>
									</label>
								</div>
							</div>
							<div class="row">
								<div class="col s12 m5 input-field">
									<input type="text" id="lnameTxtbox" class="validate text-input"
										name="" placeholder=" " maxlength="25"> <label
										id="lnLabel" for="lnameTxtbox" class="active">Last Name*</label>
								</div>
								<div class="col s12 m5 input-field">
									<input type="text" id="fnameTxtbox" class="validate text-input"
										name="" placeholder=" " maxlength="50"> <label
										id="fnLabel" for="fnameTxtbox" class="active">First Name*</label>
								</div>
								<div class="col s12 m2 input-field">
									<input type="text" id="miTxtbox" class="validate text-input"
										name="" placeholder=" " maxlength="5"> <label
										for="miTxtbox" class="active">Middle Initial</label>
								</div>
							</div>
							<div class="row">
								<div class="col s12 m3 input-field">
									<select id="jobRoleDropdown">
									</select> <label id="jrLabel" for="jobRoleDropdown" class="active">Job Role*</label>
								</div>
								<div class="col s12 m3 input-field">
									<input id="dateHiredTxtbox" type="date" class="datepicker"
										name=""> <label id="dhLabel" for="dateHiredTxtbox" class="active">Date
										Hired*</label>
								</div>
								<div class="col s12 m6 input-field">
									<select id="userGroupDropDown">
									</select> <label for="userGroupDropDown" class="active">User
										Group</label>
								</div>
							</div>
							<div class="row">
								<div class="col s12 m6 input-field">
									<input id="mobileNoTxtbox" type="number" name=""
										placeholder="+63-915-555-5555" maxlength="15"> <label
										for="mobileNoTxtbox" class="active">Mobile Number</label>
								</div>
								<div class="col s12 m6 input-field">
									<input id="emailTxtbox" type="email"
										class="validate text-input" name=""
										placeholder="example@example.com"> <label
										id="emailLabel" for="emailTxtbox" class="active">Email Address*</label>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>