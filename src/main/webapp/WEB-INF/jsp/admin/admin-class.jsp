<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - CLASS</title>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
	<script src="static/assets/js/admin-class.js"></script>
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
               <h4>Class</h4>
            </div>
            <div id="navpad" class="nav-wrapper col s12 m3 row">
              <div class="row">
                 <div class="input-field col s12 m12">
                    <a href="admin.createclass" id="add-location" style="width:100%;text-transform:capitalize;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">add_circle</i>Add Class</a>
                 </div>
              </div>
            </div>
          </div>

          <div class="row">
            <hr style="width:100%;">
          </div>

          <div class="row">
              <div class="row">
                <div class="col s12 m2" style="padding: 0 15px 0 10px;">
                  <div class="row">
                    <a class="sideFilters" data-status="off" data-filter="myclass" href="#!" id="myClassesFilter">
                    <div class="divcard" id="myClassesFilterDiv" style="text-align: center;">
                      <h5>MY CLASSES</h5>
                      <p style="font-size: 21px;margin-top: 10px;"><b>(${totalUserClasses})</b></p>
                    </div></a>
                    <a class="sideFilters" data-status="off" data-filter="progress" href="#!" id="progressFilter">
                    <div class="divcard" id="progressFilterDiv" style="text-align: center;">
                      <h5>IN PROGRESS</h5>
                      <p id="inProgressClassesCounter" style="font-size: 21px;margin-top: 10px;"><b>(${totalInProgressClasses})</b></p>
                    </div>
                    </a>
                    <a class="sideFilters" data-status="off" data-filter="upcoming" href="#!" id="upcomingFilter">
                    <div class="divcard" id="upcomingFilterDiv" style="text-align: center;">
                      <h5>UPCOMING</h5>
                      <p id="upcomingClassesCounter" style="font-size: 21px;margin-top: 10px;"><b>(${totalUpcomingClasses})</b></p>
                    </div>
                    </a>
                    <a class="sideFilters" data-status="off" data-filter="completed" href="#!" id="completedFilter">
                    <div class="divcard" id="completedFilterDiv" style="text-align: center;">
                      <h5>COMPLETED</h5>
                      <p id="completedClassesCounter" style="font-size: 21px;margin-top: 10px;"><b>(${totalCompletedClasses})</b></p>
                    </div></a>
                    <a class="sideFilters" data-status="off" data-filter="cancelled" href="#!" id="cancelledFilter">
                    <div class="divcard" id="cancelledFilterDiv" style="text-align: center;">
                      <h5>CANCELLED</h5>
                      <p id="cancelledClassesCounter" style="font-size: 21px;margin-top: 10px;"><b>(${totalCancelledClasses})</b></p>
                    </div></a>
                  </div>
                </div>
                <div class="col s12 m10">
                <div class="divcard">
                  <div class="row" style="padding: 0 10px">
                    <legend style="margin-bottom: 10px;"><b>Search Filter</b></legend>
                    <div class="row">
                      <div class="col s12 m4">
                        <label>Delivery Method</label>
                        <select id="deliveryMethodDropdown">
                          <option selected value="0">All</option>
                        </select>
                      </div>
                      <div class="col s12 m4">
                        <label>Trainer</label>
                        <select id="trainerDropdown">
                          <option selected value="0">All</option>
                        </select>
                      </div>
                      <div class="col s12 m4">
                        <label>Course</label>
                        <select id="courseInfoDropdown">
                          <option selected value="0">All</option>
                        </select>
                      </div>
                    </div>
                    <div class="row nav-wrapper">
                      <div class="col s6 m4 input-field">
                        <input type="date" class="datepicker" id="searchStartDate">
                        <label>Start Date</label>
                      </div>
                      <div class="col s6 m4 input-field">
                        <input type="date" class="datepicker" id="searchEndDate">
                        <label>End Date</label>
                      </div>
                      <div class="input-field col s12 m4">
                      	<input type="text" id="searchTxtBox" class="className" placeholder="&nbsp">
                      	<label>Class Name</label>
	                      <!-- 
	                      <input id="searchTxtBox" type="search" name="" placeholder="Class Name">
	                      <label class="label-icon" for="search"><i style="margin-top:4px;" class="material-icons">search</i></label>
	                      <i style="margin-top:1px;" class="material-icons">close</i>
	                       -->
	                  </div>
                    </div>
                    <div class="row" style="padding-right: 10px;">
                      <div class="right buttonright">
                        <a class="btn waves-effect waves-light bttn" type="button" id="resetBtn"><i class="material-icons left">refresh</i>Reset</a>
                        <a class="btn waves-effect waves-light bttn" type="submit" id="searchBtn"><i class="material-icons left">search</i>Search</a>
                      </div>
                    </div>
                  </div>
                  <div class="row" style="padding: 0 10px">
                    <div class="col m5 hide-on-med-and-down">
                    </div>
                    
                    <div class="row">
                      <table class="highlight striped">
                        <thead id="classInfoTblHeader">
                        </thead>
                        <tbody id="classInfoTblBody">
                          <!--<tr>
                            <td>
                              asd
                            </td>
                            <td>Eclair</td>
                            <td>$0.87</td>
                            <td>Eclair</td>
                            <td>$0.87</td>
                            <td>asd</td>
                            <td>
                              <a href="#!" class="waves-effect waves-light"><i class="material-icons">edit</i></a>
                              <a href="#!" class="waves-effect waves-light"><i class="material-icons">delete</i></a>
                            </td>
                          </tr>-->
                        </tbody>
                      </table>
                      <div id="pagealign">
		              	<div id="classInfoTblPagination">
		                	<!--Pagination-->
		              	</div>
		             </div>
                    </div>
                  </div>
                </div>
              </div>  
            </div>
          </div>
        </div>
    <input type="hidden" id="employeeId" value="${user.employeeId}">
</body>
</html>