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
      <!-- END: CSS-HEADER -->
       
      <!-- START: JS-HEADER -->
      <jsp:include page="../portal/common/js-header.jsp" />
      <script src="static/assets/js/portal-training.js"></script>
      <!-- END: JS-HEADER -->
   </head>
  
   <body >
     <!-- Header -->
	<jsp:include page="../portal/common/portal-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../portal/common/portal-sidenav.jsp" />
	<div class="container1">
          <div class="divcard">

            <!-- Breadcrumbs starts here -->
            <div class="row" style="margin-bottom: 20px;">
              <nav>
                <div class="col s12">
                  <a href="#!" class="breadcrumb">Passenger Handling</a>
                  <a href="#!" class="breadcrumb">Class 101</a>
                  <a href="#!" class="breadcrumb">Exam 101</a>
                  <a href="#!" class="breadcrumb">Section Name</a>
                </div>
              </nav>
            </div>
            <!-- Breadcrumbs ends here -->

            <div class="row">
              <div class="col s12 m3">
                <div>
                  <div class="divcard" style="margin-bottom: 20px;">
                    <div class="left">
                      <h3 style="margin-top: 10px;margin-bottom: 0;"><i class="material-icons" style="font-size: 40px;">schedule</i></h3>
                    </div>
                    <h5 style="text-align: center;vertical-align: middle;">Time Remaining</h5>
                    <div style="text-align: center;">
                      <span style="font-size: 26px;font-weight: bold;">00:29:59</span>
                    </div>
                  </div>
                  <div class="divcard" style="margin-bottom: 20px;">
                    <table>
                      <tbody>
                        <tr>
                          <td><a href="#!">1</a></td>
                          <td><a href="#!">2</a></td>
                          <td><a href="#!">3</a></td>
                          <td><a href="#!">4</a></td>
                          <td><a href="#!">5</a></td>
                        </tr>
                        <tr>
                          <td><a href="#!">6</a></td>
                          <td><a href="#!">7</a></td>
                          <td><a href="#!">8</a></td>
                          <td><a href="#!">9</a></td>
                          <td><a href="#!">10</a></td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                  <div>
                    <a href="#!" class="btn bttn waves-effect waves-light" style="width: 100%;">Finish</a>
                    
                  </div>
                </div>
              </div>
              <div class="col s12 m9">
                <div class="divcontrol">
                  <div>
                    <a href="#!" name="prev" class="btn bttn waves-effect waves-effect"><i class="material-icons left">keyboard_arrow_left</i>Previous Question</a>
                  </div>
                  <div style="padding:10px;">
                    <span><b>Question 1 out of 18</b></span>
                  </div>
                  <div>
                    <a href="#!" name="next" class="btn bttn waves-effect waves-effect"><i class="material-icons right">keyboard_arrow_right</i>Next Question</a>
                  </div>
                </div>
                <div class="mydivs">
                  <!-- Multiple choice -->
                  <div>
                    <span>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</span>
                    <div class="row">
                      <div class="col s12 m7">
                        <form class="formstyle">
                          <label>
                            <input type="radio" name="choices" value="a">
                            <span>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</span>
                          </label><br>
                          <label>
                            <input type="radio" name="choices" value="b">
                            <span>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</span>
                          </label><br>
                          <label>
                            <input type="radio" name="choices" value="c">
                            <span>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</span>
                          </label><br>
                          <label>
                            <input type="radio" name="choices" value="d">
                            <span>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</span>
                          </label><br>
                          <!-- <input type="radio" name="choices" value="b">
                          <span>b</span><br>
                          <input type="radio" name="choices" value="c">
                          <span>c</span><br> -->
                        </form>                        
                      </div>
                      <div class="col s12 m5 center">
                        <img src="../assets/images/usersimage/users.png">
                      </div>
                    </div>
                  </div>

                  <!-- Fill in the blanks -->
                  <div>
                    <div class="row">
                      <div class="col s12 m7">
                        <span>
                          Lorem ipsum dolor sit amet, <input style="height: 25px;width: 150px;" type="text" name=""> elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                        </span>
                      </div>
                      <div class="col s12 m5 center">
                        <img src="../assets/images/usersimage/users.png">
                      </div>
                    </div>
                  </div>

                  <!-- True or False -->
                  <div>
                    <span>
                      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                    </span>
                    <div class="row">
                      <div class="col s12 m7">
                        <form class="formstyle">
                          <label>
                            <input type="radio" name="choices" value="a">
                            <span>True</span>
                          </label><br>
                          <label>
                            <input type="radio" name="choices" value="b">
                            <span>False</span>
                          </label>
                        </form>
                      </div>
                      <div class="col s12 m5 center">
                        <img src="../assets/images/usersimage/users.png">
                      </div>
                    </div>
                  </div>

                  <!-- Essay -->
                  <div>
                    <div class="row">
                      <div class="col s12 m7">
                        <span>
                          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                        </span>
                      </div>
                      <div class="col s12 m5 center">
                        <img src="../assets/images/usersimage/users.png">
                      </div>
                    </div>
                  </div>

                  <!-- Identification -->
                  <div>
                    <span>
                      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                    </span>
                    <div class="row">
                      <div class="col s12 m7">
                        <div class="row" style="padding-top: 20px;">
                          <div class="col s12 m2" style="padding-top: 15px;">
                            <span><b>Answer:</b></span> 
                          </div>
                          <div class="col s12 m10">
                            <input type="text" name="">
                          </div>
                        </div>
                      </div>
                      <div class="col s12 m5 center">
                        <img src="../assets/images/usersimage/users.png">
                      </div>
                    </div>
                  </div>

                  <!-- Enumeration -->
                  <div>
                    <span>
                      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                    </span>
                    <div class="row">
                      <div class="col s12 m7">
                        <div class="row" style="padding-top: 20px;">
                          <div class="col s12 m2" style="padding-top: 15px;">
                            <span><b>Answer:</b></span> 
                          </div>
                          <div class="col s12 m10">
                            <input type="text" name="">
                            <input type="text" name="">
                            <input type="text" name="">
                          </div>
                        </div>
                      </div>
                      <div class="col s12 m5 center">
                        <img src="../assets/images/usersimage/users.png">
                      </div>
                    </div>
                  </div>

                  <!-- Ordering -->
                  <div>
                    <span>
                      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                    </span>
                    <div class="row">
                      <div class="col s12 m7">
                        <ul id="sortable">
                          <li><span>Item 1</span></li>
                          <li><span>Item 2</span></li>
                          <li><span>Item 3</span></li>
                          <li><span>Item 4</span></li>
                          <li><span>Item 5</span></li>
                          <li><span>Item 6</span></li>
                          <li><span>Item 7</span></li>
                        </ul>
                      </div>
                      <div class="col s12 m5 center">
                        <img src="../assets/images/usersimage/users.png">
                      </div>
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