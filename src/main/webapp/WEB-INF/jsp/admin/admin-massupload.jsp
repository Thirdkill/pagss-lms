<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<title id="actionTitle">PAGSS - Mass User Upload</title>
<!-- START: CSS-HEADER -->
<jsp:include page="../admin/common/admin-css-header.jsp" />
<!-- END: CSS-HEADER -->
<!-- START: JS-HEADER -->
<jsp:include page="../admin/common/admin-js-header.jsp" />
<jsp:include page="../admin/common/admin-tinymce-header.jsp" />
<script src="static/assets/js/utility/tinymce-util.js"></script>
<script src="static/assets/js/admin-massupload.js"></script>
<!-- END: JS-HEADER -->
</head>
<body>
	<!-- Header -->
	<jsp:include page="../admin/common/admin-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-sidebar.jsp" />
	<div class="container2">
		<div class="row">
			<h4 id="actionLabel">User Mass Upload</h4>
		</div>
		<div class="row" style="margin-bottom: 20px;">
			<hr style="width: 100%;">
		</div>
		<div class="row">
			<div class="divcard">
				<div class="row" style="margin-bottom: 20px;">
					<div class="col s12 m6">
						<a id="fileUploadTabBtn" class="bttn btn waves-light waves-effect"
							type="submit" style="width: 100% !important">File Upload</a>
					</div>
					<div class="col s12 m6">
						<a id="saveRecordTabBtn" class="bttn btn waves-light waves-effect"
							type="submit" style="width: 100% !important">Save Records</a>
					</div>
				</div>

				<!-- File Upload option starts here -->
				<div class="row" id="fileUploadOptionForm">
					<div class="divcard">
						<div class="row">
							<a id="resetBtn" class="btn bttn waves-effect waves-light right"><i
								class="material-icons left">refresh</i>Reset</a>
						</div>

						<div class="row">
							<div class="col s12 m6">
								<span><b>Default Job Role</b></span> <select
									id="jobRoleDropdown">

								</select>
							</div>
							<div class="col s12 m6">
								<span><b>Default User Type</b></span> <select
									id="userTypeDropdown">
								</select>
							</div>
						</div>

						<div class="row">
							<div class="col s12 m6">
								<span><b>Default User Group</b></span> <select
									id="userGroupsDropdown">
								</select>
							</div>
							<div class="col s12 m6 hide-on-med-and-down"></div>
						</div>

						<div class="row" style="margin-top: 20px;">
							<div class="col s12 m6">
								<div class="row">
									<div class="col s12 m6">
										<a id="generateTemplate"
											class="btn bttn waves-light waves-effect"
											style="width: 100% !important">Generate Template</a>
									</div>
									<div class="col s12 m6">


										<a id="fileUploadLabel"
											class="btn bttn waves-light waves-effect"
											style="width: 100% !important">Choose File</a>
										<form id="massUploadFrm">
											<input type="file" style="visibility: hidden;"
												id="fileUpload" name="fileUpload">
										</form>
									</div>
								</div>
							</div>
							<div class="col s12 m6">
								<div class="row">
									<div class="col s12 m6 hide-on-med-and-down"></div>

									<div class="col s12 m6">
										<a id="uploadTemplate"
											class="btn bttn waves-light waves-effect"
											style="width: 100% !important">Upload</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- File Upload option ends here -->

				<!-- Save records option starts here -->
				<div class="row" style="margin-bottom: 20px;" id="recordsTab">
					<div class="col s12 m6">
						<div class="row">
							<div class="col s12 m6">
								<a id="showErrorRecordsBtn"
									class="bttn btn waves-effect waves-light"
									style="width: 100% !important">Records in Error (2)</a>
							</div>
							<div class="col s12 m6">
								<a id="showSaveRecordsBtn"
									class="bttn btn waves-effect waves-light"
									style="width: 100% !important">Saved Records (2)</a>
							</div>
						</div>
					</div>
					<div class="col s12 m6 hide-on-med-and-down"></div>
				</div>
				<!-- RECORDS IN ERROR STARTS HERE -->
				<form id="error-form">
					<div class="row">
						<div class="divcard" style="height: 500px; overflow-y: scroll;" id="errorRecordValues">
							<div class="row" style="margin-bottom: 20px;">
								<div class="col s12 m6 hide-on-med-and-down"></div>
								<div class="col s12 m6">
									<div class="row">
										<div class="col s12 m6">
											<a class="bttn btn waves-light waves-effect" id="deleteAllBtn"
												style="width: 100% !important" type="submit"><i
												class="material-icons left">delete</i>Delete All</a>
										</div>
										<div class="col s12 m6">
											<a class="bttn btn waves-light waves-effect" id="saveAllBtn"
												style="width: 100% !important" type="submit"><i
												class="material-icons left">save</i>Save All</a>
										</div>
									</div>
								</div>
							</div>
							<div class="" id="errorFormCard">
							</div>
							<div id="pagealign">
								<div id="errorUploadUserpagination"></div>
							</div>
						</div>
					</div>
					<!-- RECORDS IN ERROR ENDS HERE -->
				</form>

				<!-- SAVED RECORDS STARTS HERE -->
				<div class="row" id="saveRecordsPane">
					<div class="divcard" style="height: auto; overflow-y: scroll;"id="saveRecordsValues">
					</div>
					<div id="pagealign">
						<div id="savedUploadUserpagination"></div>
					</div>	
				</div>
				
				<!-- SAVED RECORDS ENDS HERE -->

				<!-- Save records option ends here -->

			</div>
		</div>
	</div>
</body>
</html>