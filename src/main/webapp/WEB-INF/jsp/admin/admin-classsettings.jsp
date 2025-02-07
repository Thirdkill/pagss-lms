<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - Class Settings</title>
	<!-- START: CSS-HEADER -->  
	<jsp:include page="../admin/common/admin-trainee-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	 <jsp:include page="../admin/common/admin-js-header.jsp" />
	 <script src="static/assets/js/admin-classsettings.js"></script>
	<!-- END: JS-HEADER -->
</head>
<body>
	<jsp:include page="../admin/common/admin-collapsibleheader.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-collapsiblesidebar.jsp" />
	
	<div class="container1">
          <div class="row">
            <div class="col s12 m3" style="padding-top: 20px;">
              <div class="card">
                <div class="card-image">
                  <img src="static/assets/images/usersimage/users.png"style="clip-path: circle(40% at 50% 50%);">
                  <a class="btn-floating btn-large halfway-fab waves-effect waves-light bttn"><i class="material-icons">add</i></a>
                </div>
              </div>
              <div>
                <h6><b>${classInfo.courseName}</b></h6>
                <p><span>${classInfo.className}</span></p>
                <p><span id="deliveryMethodLabel"></span></p>
                <p><span>Status</span></p>
              </div>
            </div>
            <div class="col s12 m9" style="padding: 5px;">
              <div class="row" style="margin-bottom: 20px;">
                <ul class="pagination hide-on-med-and-down">
                  <li><a href="admin.classdashboard?classId=${classId}"><u>Dashboard</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classdetails?classId=${classId}"><u>Details</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classtrainee?classId=${classId}"><u>Trainees</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classattendance?classId=${classId}"><u>Attendance</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classsettings?classId=${classId}">Settings</a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classmaterials?classId=${classId}"><u>Materials</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classexam?classId=${classId}"><u>Exams</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classfinancial?classId=${classId}"><u>Financial</u></a></li>
                  <li style="font-size: 18px;">|</li>
                  <li><a href="admin.classevaluation?classId=${classId}"><u>Evaluation</u></a></li>
                </ul>
              </div>
              <!-- content starts here -->
              <div class="divcard">

                <div class="row">
                  <div class="right">
                    <a href="admin.class" class="btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">arrow_back</i>Back</a>
                    <a id="btnSaveSettings" href="#!" class="btn bttn waves-light waves-effect" type="submit" style="margin-right: 5px;"><i class="material-icons left">save</i>Save Settings</a>
                  </div>
                </div>

                <div class="row" style="padding: 10px;">
                	<div id="modularDisplay" style="display:none;">
                		<span><b>Duration (in minutes)</b></span>
                		<input id="tbClassDuration" type="text" value="${classInfo.classDuration}" style="width:100px;">
                	</div>
                  <div class="switch">
                    <b>Certificate</b>
                    <label>
                      <input id="chWithCertificate" type="checkbox" data-withcertificate="${classInfo.withCertificate}">
                      <span class="lever"></span>
                    </label>
                  </div>
                  
                  <hr style="width: 100%;margin-top: 20px;">
                </div>

                <div id="withCertificate" class="row" data-template="${classInfo.certificateTemplateType}">
                    <div class="col s12 m6">
                      <label class="lblcolor">
                        <input id="raSystemTemplate" name="certificate" type="radio" checked />
                        <span>Use System Template</span>
                      </label><br>
                      <label class="lblcolor">
                        <input id="raCustomTemplate" name="certificate" type="radio" />
                        <span>Upload Custom Template</span>
                      </label>
                    </div>
                    <div id="isDownloadable" class="col s12 m6" data-downloadable="${classInfo.isCertificateDownloadable}">
                      <label class="lblcolor">
                        <input id="chIsDownloadable" name="" type="checkbox" />
                        <span>Downloadable</span>
                      </label>
                    </div>
                </div>

                <div class="row">
                  <div class="col s12 m8">
                    <div id="customUploadDiv" class="file-field input-field" >
                      <div class="btn bttn right" style="">
                        <span>Browse</span>
                        <input id="fuCustomCertificate"  type="file">
                      </div>
                      <div class="file-path-wrapper" >
                        <input class="file-path validate" type="text" id="fileupload">
                      </div>
                    </div>
                  </div>
                  <div class="col s12 m4 hide-on-med-and-down"></div>
                </div>

                <div class="row" style="padding: 10px;">
                  <div class="switch">
                    <b>Self Registration</b>
                    <label>
                      <input id="chIsSelfRegister" data-selfregister="${classInfo.isSelfRegister}" type="checkbox">
                      <span class="lever"></span>
                    </label>
                  </div>
                  <hr style="width: 100%;margin-top: 20px;">
                </div>

                <div id="selfRegistrationDiv" class="row" data-selfregistertype="${classInfo.selfRegisterType}">
                   <div class="col s12 m6">
                     <label class="lblcolor">
                       <input id="raOpenIndefinite" name="selfregister" type="radio" checked />
                       <span>Open indefinitely before training start</span>
                     </label><br>
                     <label class="lblcolor">
                       <input id="raOpenSpecifiedPeriod" name="selfregister" type="radio" checked />
                       <span>Open for a specified period only</span>
                     </label>
                   </div>
                   <div class="col s12 m6 hide-on-med-and-down">
                   </div>
                </div>

                <div id="specifiedPeriodDiv" class="row" style="margin-top: 10px;">
                  <div class="col s12 m4">
                    <b>Start Date</b>
                    <input id="tbSpecifiedStart" type="date" class="datepicker" value="${classInfo.selfRegisterStartDate}" name="">
                  </div>
                  <div class="col s12 m4">
                    <b>End Date</b>
                    <input id="tbSpecifiedEnd" type="date" class="datepicker" value="${classInfo.selfRegisterEndDate}" name="">
                  </div>
                  <div class="col s12 m4 hide-on-med-and-down">
                  </div>
                </div>

                <div class="row" style="padding: 10px;margin-top: 20px;">
                  <div class="switch">
                    <b>Viewing Restrictions</b>
                  </div>
                  <hr style="width: 100%;margin-top: 20px;">
                </div>

                <div id="viewRestrictionDiv" class="row" data-viewrestriction="${classInfo.viewRestrictionType}" >
                    <div class="col s12 m8">
                      <label class="lblcolor">
                        <input id="raAccessTrainingDurationOnly" name="viewrestrict" type="radio" />
                        <span>Accessible during training duration only</span>
                      </label><br>
                      <label class="lblcolor">
                        <input id="raAccessIndefinitely" name="viewrestrict" type="radio" />
                        <span>Accessible indefinitely</span>
                      </label>
                      <label class="lblcolor">
                        <input id="raAccessSpecifiedPeriod" name="viewrestrict" type="radio" />
                        <span>Accessible during a specified period only (aside from training duration)</span>
                      </label>
                    </div>
                    <div class="col s12 m4 hide-on-med-and-down">
                    </div>
                </div>

                <div id="specifiedRestrictionsDiv" class="row" style="margin-top: 10px;">
                  <div class="col s12 m4">
                    <b>Start Date</b>
                    <input id="tbAccessSpecifiedStart" type="date" class="datepicker" value="${classInfo.accessStartDate}" name="">
                  </div>
                  <div class="col s12 m4">
                    <b>End Date</b>
                    <input id="tbAccessSpecifiedEnd" type="date" class="datepicker" value="${classInfo.accessEndDate}" name="">
                  </div>
                  <div class="col s12 m4 hide-on-med-and-down">
                  </div>
                </div>

                <div class="row" style="padding: 10px;margin-top: 20px;">
                  <div class="switch">
                    <b>Access Restrictions</b>
                  </div>
                  <hr style="width: 100%;margin-top: 20px;">
                </div>

                <div id="accessRestrictionTypeDiv" class="row" data-accesstype="${classInfo.accessRestrictionType}">
                    <div class="col s12 m8">
                      <label class="lblcolor">
                        <input id="raIpPublic" name="iprestrict" type="radio" checked />
                        <span>Public (Can be access everywhere)</span>
                      </label><br>
                      <label class="lblcolor">
                        <input id="raIpPrivate" name="iprestrict" type="radio" />
                        <span>Private(Selected IP Address)</span>
                      </label>
                    </div>
                    <div class="col s12 m4 hide-on-med-and-down">
                    </div>
                </div>

                <div class="row">
                  <div class="col s12 m4 hide-on-med-and-down">
                    
                  </div>
                  <div class="col s12 m8">
                    <a id="btnClassIPModal" data-target="IPviewmodal" class="bttn btn modal-trigger waves-light waves-effect right">Click here to view enrolled IP Addresses</a>
                  </div>
                </div>

              </div>
              <!-- content ends here -->
            </div>
          </div>
        </div>
        
        <div id="IPviewmodal" class="modal modal-fixed-footer large">
			<div class="modal-header">
				<h5>Class IP Address Whitelist</h5>
			</div>
			<div id="SettingsModal" class="modal-content">
						<div>
						  <b>Class IP Address Whitelist</b>
		                  <table>
		                    <tbody id="classIpTblBody">
		                      <tr>
		                        <td class="mins">
		                          <a href="#!"><i class="material-icons">edit</i></a>
		                          <a href="#!"><i class="material-icons">delete</i></a>
		                         </td>
		                          <td style="text-align: left">Evaluation Sheet for Mandatory Trainings</td>
		                        
		                      </tr>
		                    </tbody>
		                  </table>
		                  	<div id="pagealign">
			              		<div id="classIpTblPagination">
			                  	<!--Pagination-->
			               		</div>
		               		</div>
		               		</div>
		               		
		               	<div>
			               	<div class="row">
			                  <div class="col s12 m4" style="padding: 10px 0 0 !important;">
			                     <b>Available IP Address</b>
			                  </div>
			                  <div style="padding-top:5px;" class="nav-wrapper col s12 m8">
		                          <a id="addClassIPAddressBtn" href="#!" style="width:25%;text-transform:capitalize;z-index:0 !important;" class="btn right waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">add_circle</i>Add</a>
			                  </div>
			                </div>
			                  <table>
			                   <tbody id="ipaddressTblBody">
			                     <tr>
			                        <td class="mins">
			                          <label>
			                            <input type="checkbox" name="">
			                            <span></span>
			                          </label>
			                        </td>
			                        <td style="text-align: left;">
			                         asd
			                        </td>
			                      </tr>
			                    </tbody>
			                  </table>
			                  	<div id="pagealign">
				              		<div id="ipaddressPagination">
				                  	<!--Pagination-->
				               		</div>
			              		</div>
		               	</div>
			</div>
			<div class="modal-footer">
				<a class="modal-close btn waves-effect waves-light bttn"
					type="button" name="action"><i class="material-icons left">cancel</i>Close</a>
			</div>
		</div>
		
		<input type="hidden" id="classId" value="${classInfo.classId}">
        <input type="hidden" id="courseId" value="${classInfo.courseId}">
        <input type="hidden" id="deliveryMethod" value="${classInfo.deliveryMethod}">
        <input type="hidden" id="scheduleType" value="${classInfo.scheduleType}">
        
</body>
</html>