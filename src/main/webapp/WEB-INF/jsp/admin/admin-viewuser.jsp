<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - VIEW USER</title>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
	<script src="static/assets/js/admin-users-viewuser.js"></script>
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
               <h4>User Details</h4>
            </div>
            <div id="navpad" class="nav-wrapper col s12 m3 row">
              <div class="row">
                     <div class="input-field col s12 m12">
                        <a id="editUserProfile" style="width:100%;text-transform:capitalize;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">edit</i>Edit User Details</a>
                     </div>
                  </div>
            </div>
          </div>
          <div class="row">
            <hr style="width:100%;">
         </div>

        <div id="container">
          <div class="divcard">
            <div class="row">
              <div>
                <ul class="pagination">
                  <li><a id="viewUserLink" class="active">Details</a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a id="tfJobroleLink"><u>Training Flow (Job Role)</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a id="tfUsergroupLink"><u>Training Flow (User Group)</u></a></li>
                </ul>
              </div>
            </div>
            <div class="row">
              <div class="right">
                <a id="userResetPassword" style="width:100%;text-transform:capitalize;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" >Reset Password</a>
              </div>
            </div>
            <div class="row">
              <div class="col s12 m3">
                <div class="card">
                  <div class="card-image">
                    <img src="static/assets/images/usersimage/user.png">
                  </div>
                </div>
              </div>
              <div class="col s12 m5">
                <table class="contentLeft tableNoBorder">
                  <tbody>
                    <tr>
                      <td><b>Employee Number:</b></td>
                      <td id="employeeNoLabel"></td>
                    </tr>
                    <tr>
                      <td><b>Full Name:</b></td>
                      <td id="fullNameLabel"></td>
                    </tr>
                    <tr>
                      <td><b>User Type:</b></td>
                      <td id="userTypeLabel"></td>
                    </tr>
                    <tr>
                      <td><b>Username:</b></td>
                      <td id="usernameLabel"></td>
                    </tr>
                    <tr>
                      <td><b>Job Role:</b></td>
                      <td id="jobRoleLabel"></td>
                    </tr>
                    <tr>
                      <td><b>Hire Date:</b></td>
                      <td id="hireDateLabel"></td>
                    </tr>
                    <tr>
                      <td><b>Mobile Number:</b></td>
                      <td id="mobileNoLabel"></td>
                    </tr>
                    <tr>
                      <td><b>Email Address:</b></td>
                      <td id="emailLabel"></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div class="col s12 m4">
                <table class="contentLeft tableNoBorder">
                  <tbody>
                    <tr>
                      <td><b>Status:</b></td>
                      <td id="statusLabel"></td>
                    </tr>
                    <tr>
                      <td><b>Date Created:</b></td>
                      <td id="dateCreatedLabel"></td>
                    </tr>
                    <tr>
                      <td><b>Last Modified Date:</b></td>
                      <td id="dateModifiedLabel"></td>
                    </tr>
                    <tr>
                      <td><b>Modified By:</b></td>
                      <td id="modifiedByLabel"></td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>

        <div id="container">
          <div class="divcard">
            <div class="row">
              <legend style="margin-bottom: 10px;"><b>Attachments</b></legend>
            </div>
            <div class="row">
              <div class="right">
                <a data-target="userModal" style="width:100%;text-transform:capitalize;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">add_circle</i>Add Attachments</a>
              </div>
            </div>
            <div class="row">
              <table class="highlight ">
                <thead>
                  <tr>
                      <th>Actions</th>
                      <th>File<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
                      <th>Description<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
                      <th>File Name<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td class="min">
                      <a href="#!" class="waves-effect waves-light"><i class="material-icons">file_download</i></a>
                      <a href="#!" class="waves-effect waves-light"><i class="material-icons">edit</i></a>
                      <a href="#!" class="waves-effect waves-light"><i class="material-icons">delete</i></a>
                    </td>
                    <td class="mid">Eclair</td>
                    <td class="mid">$0.87</td>
                    <td class="mid">Eclair</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        </div>
        
        <!-- user details modal ends here -->
        <div id="userModal" class="modal modal-fixed-footer">
			<div class="modal-header">
				<h5>Add Attachment</h5>
			</div>
			<div class="modal-content">
				<div class="content-row">
					<div>
						<b>File*</b> 
							<input type="text" name="" id=""
							class="validate"> 
						<b>Description</b>
							<textarea style="resize: none;" id="description"
							class="materialize-textarea"></textarea>
						<form id="uploadMediaForm">
		                    <div class="file-field input-field" style="margin-top: 0 !important;">
		                      <div class="btn bttn right" style="margin-top: 20px;">
		                        <span>Browse</span>
							     <input type="file" id="fileUpload" name="fileUpload"/>
		                      </div>
		                      <div class="file-path-wrapper" style="padding-top: 0 !important;">
		                        <b>Browse File*</b>
		                        <input class="file-path validate" type="text" id="mediaDisplayTxt">
		                      </div>
		                    </div>
		                  </form>
					</div>
					
				</div>
			</div>
			<div class="modal-footer">
				<a class="modal-close btn waves-effect waves-light bttn"
					type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
				<a class="btn waves-effect waves-light bttn" type="submit"
					name="action" id="saveLocationBtn"><i
					class="material-icons left">save</i>Save</a>
			</div>
		</div>
		<!-- user details modal ends here -->
        
</body>
</html>