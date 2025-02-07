<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - EVALUATION</title>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
	<jsp:include page="../admin/common/admin-tinymce-header.jsp" />
	<script src="static/assets/js/utility/tinymce-util.js"></script>
	<script src="static/assets/js/admin-evaluationprofile.js"></script>
	<!-- END: JS-HEADER -->
</head>
<body>
	<!-- Header -->
	<jsp:include page="../admin/common/admin-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-sidebar.jsp" />
         <div id="evalProfileBody" class="container2">
          <div class="row">
            <div class="col s12 m8">
               <h4>Create New Evaluation</h4>
            </div>
            <div id="navpad" class="nav-wrapper col s12 m4 row">
              <div class="row">
                 <div class="input-field col s6 m6">
                    <a id="evaluationCancel" style="width:100%;" class="btn waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">not_interested</i>Cancel</a>
                 </div>
                 <div class="input-field col s6 m6">
                    <a id="evaluationSubmit" style="width:100%;" class="btn waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">save</i>Save</a>
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
                <div class="col s12 m5">
                  <b id="evalCodeLabel">Evaluation Code*</b>
                  <input id="evaluationCodeTxtbox" type="text" name="" class="validate">
                </div>
                <div class="col s10 m6">
                  <b id="evalTitleLabel">Evaluation Title*</b>
                  <input id="evaluationTitleTxtbox" maxlength="50" type="text" name="" class="validate">
                </div>
                <div class="col s2 m1 switch left">
                  <b>Status</b> 	
                  <label>
                    <input id="statusCheckBox" type="checkbox">
                    <span style="" class="lever left"></span>
                  </label>
                </div>
              </div>
              <div style="padding-top: 10px;" class="row">
                <div class="right">
                  <a data-target="addSectionModal" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="action"><i class="material-icons left">add_circle</i>Add Section</a>
               </div>
              </div>
            </div>
          </div>
         </div>

        <!--Modal add section starts here-->

        <div id="addSectionModal" class="addSection modal modal-fixed-footer fixed-modal">
            <div class="modal-header">
              <h5>Add Section</h5>
            </div>
            <div class="modal-content">
              <div>
                <div class="switch">
                  <b id="nameLabel">Name*</b>
                    <input type="text" name="" id="sectionNameTxtBox" class="validate">
                </div>
              </div>
              <div>
                <div class="col s12 m12">
                  <b id="typeLabel">Type*</b>
                    <select id="sectionTypeDropdown">
                      <option disabled selected>Choose Type</option>
                      <option value="Scale">Scale</option>
                      <option value="Free Form">Free Form</option>
                    </select>
                </div>
              </div>
              <!-- Scale type starts here -->
              <div id="scaleFields" class="scale showOpt" style="display:none;">
                <div class="row" style="margin-bottom: 10px;">
                  <div class="col s3" style="padding-top: 19px;text-align: center;">
                    <b id="minScaleLabel">Minimum*</b>
                  </div>
                  <div class="col s3">
                    <input id="minScaleTxtBox" class="numbersOnly" type="number">
                  </div>
                  <div class="col s3" style="padding-top: 19px;text-align: center;">
                    <b id="maxScaleLabel">Maximum*</b>
                  </div>
                  <div class="col s3">
                    <input id="maxScaleTxtBox" class="numbersOnly" type="number">
                  </div>
                </div>
              </div>
              <!-- Scale type Ends here -->
            </div>
            <div class="modal-footer">
              <a id="sectionCancel" class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
              <a id="sectionSubmit" class="btn waves-effect waves-light bttn" type="submit" name="action" id="saveLocationBtn"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>

        <!--Modal add section ends here-->

        

        <!--Modal add part starts here-->
        <div id="partModal" class="modal modal-fixed-footer" style="height: 400px;">
            <div class="modal-header">
              <h5>Add Question/Criteria</h5>
            </div>
            <div class="modal-content">
              <div>
                <b id="questionLabel">Question/Criteria</b>
                <div style="margin-top:10px;">
                  <textarea id="questionTextArea"></textarea>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <a id="questionCancel" class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
              <a id="questionSubmit" class="btn waves-effect waves-light bttn" type="submit" name="action" id="saveLocationBtn"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>
        <!--Modal add part ends here-->

</body>
</html>