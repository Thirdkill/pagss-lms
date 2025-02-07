<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
   <head>
      <title>PAGSS</title>
      <meta name = "viewport" content = "width = device-width, initial-scale = 1">
      <!-- START: CSS-HEADER -->  
	  <jsp:include page="../portal/common/css-header.jsp" />
	  <link rel="stylesheet" type="text/css" href="static/assets/css/adminlogin.css"> 
      <!-- END: CSS-HEADER -->
       
      <!-- START: JS-HEADER -->
      <jsp:include page="../portal/common/js-header.jsp" />
      <script src="static/assets/js/portal-resetpassword.js"></script>
      <!-- END: JS-HEADER -->
      </head>
<body>
<div class="mother-container">

        <div class="subContainer">
          <div class="container">
            <div id="container-2">
              <div id="small">
                <img style="width:150px;height:150px;" src="static/assets/images/usersimage/user.png">
                <h5>Change Password</h5>
                <form action="save-password" method="post" role="form">
<!--                 <input type="password" class="validate text-input" placeholder="Old Password"> -->
                <input name="newPassword" type="password" class="validate text-input" placeholder="New Password">
                <input name="confirmPassword" type="password" class="validate text-input" placeholder="Confirm Password" required>
                <input class="btn waves-effect waves-light bttn" type="submit" name="action" style="margin-top: 10px;" />
            	</form>
            </div>
          </div>
        </div>

      </div>
   </div>   
</body>
</html>