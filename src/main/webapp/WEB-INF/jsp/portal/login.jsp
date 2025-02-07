<%@ page isELIgnored="false" %>
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
      <script src="static/assets/js/portal-login.js"></script>
      <!-- END: JS-HEADER -->
      
   </head>
  
   <body>
      <div class="mother-container">

        <div class="subContainer">
          <div class="container">
          <form action="login" method="post" role="form">
            <div id="container-2">
              <div id="small">

                <img style="width:290px;height:80px;margin-bottom:20px;" src="static/assets/images/login/PAGSS.jpg">

                 <input name="user" type="text" class="validate text-input" placeholder="Enter Username">
                    <input name="pass" type="password" class="validate text-input" placeholder="Password" required>
                 <button id="fontcolor" class="btn waves-effect waves-light bttn" type="submit" name="action">Submit</button>
                <p>
                  <a href="/" style="color:#0e0e0e;text-decoration:underline;" class="right">Forgot Password</a><br>
                </p>
              </div>
            </div>
            </form>
          </div>
        </div>

      </div>
   </body>
</html>