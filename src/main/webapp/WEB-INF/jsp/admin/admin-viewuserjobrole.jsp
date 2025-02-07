<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - VIEW USER JOB ROLE</title>
	<!-- START: CSS-HEADER -->  
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	 <jsp:include page="../admin/common/admin-js-header.jsp" />
	 <script src="static/assets/js/admin-users-viewuser-jobrole.js"></script>
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

          <div class="row">
            <div class="divcard">
              <div class="row">
                <div>
                  <ul class="pagination">
                    <li><a id="viewUserLink"><u>Details</u></a></li>
                    <li style="font-size: 18px;">|</li>
                    <li><a id="tfJobroleLink" class="active">Training Flow (Job Role)</a></li>
                    <li style="font-size: 18px;">|</li>
                    <li><a id="tfUsergroupLink"><u>Training Flow (User Group)</u></a></li>
                  </ul>
                </div>
              </div>
              <div class="row" style="margin: 15px 0;">
                <div class="left">
                  <b>Job Role:</b>
                  <span id="jobRoleName"></span>
                </div>
              </div>
              <div class="row">
                <table class="highlight centered">
                  <thead>
                    <tr>
                        <th>Order No.</th>
                        <th>Category</th>
                        <th>Course</th>
                        <th>Code</th>
                        <th>Prerequisite</th>
                        <th>Required</th>
                        <th>Recurring</th>
                        <th>Interval</th>
                    </tr>
                  </thead>
                  <tbody id="trainingFlowTblBody" style="padding: 5px !important;">
                  </tbody>
                </table>
              </div>
            </div>
          </div>
            
        </div>
</body>
</html>