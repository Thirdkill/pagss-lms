<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - COURSE EVALUATION</title>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
	<script src="static/assets/js/admin-courseevaluation.js"></script>
	<!-- END: JS-HEADER -->
</head>
<body>
	<!-- Header -->
	<jsp:include page="../admin/common/admin-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-sidebar.jsp" />
        <div class="container2">
          <div class="row">
            <div class="col s12 m8">
               <h4>${courseInfo.courseName}</h4>
            </div>
          </div>

          <div class="row">
            <hr style="width:100%;">
          </div>
          
          <div class="row">
            <div class="divcard">
              <div class="row" style="margin-bottom: 20px;">
                <div>
                  <ul class="pagination">
                    <li><a href="admin.coursedetails?courseId=${courseId}"><u>Details</u></a></li>
                    <li style="font-size: 18px;">|</li>
                    <li><a href="admin.coursematerials?courseId=${courseId}"><u>Learning Materials</u></a></li>
                    <li style="font-size: 18px;">|</li>
                    <c:choose><c:when test="${courseInfo.deliveryMethod == 2 }">
					     <li><a href="admin.coursepath?courseId=${courseId}"><u>Learning Path</u></a></li>
                    <li style="font-size: 18px;">|</li>
                    </c:when></c:choose>
                    <li><a href="admin.courseexamination?courseId=${courseId}"><u>Examinations</u></a></li>
                    <li style="font-size: 18px;">|</li>
                    <li><a href="admin.courseevaluation?courseId=${courseId}">Evaluation</a></li>
                  </ul>
                </div>
              </div>
              <div class="row">
                <div class="right">
                  <a href="admin.trainingcourse" class="btn waves-light waves-effect bttn" type="submit" style="margin-right: 5px;"><i class="material-icons left">arrow_back</i>Back</a>
                  <a id="createEvaluationBtn" class="modal-trigger btn waves-light waves-effect bttn" type="submit"><i class="material-icons left">add_circle</i>Create New Evaluation</a>
                </div>
              </div>
              <div class="row">
                <div style="margin: 20px 0;">
                  <table class="highlight striped">
                    <tbody id="savedEvaluationTblBody">
                    </tbody>
                  </table>
                </div>
                <div>
                  <hr style="width:100%;">
                </div>
              </div>



              <div class="row" style="margin-top: 20px;">
                <div class="col s12 m6" style="padding-top: 13px;">
                  <b>Avalable Evaluation</b>
                </div>
                <div style="padding-top:5px;" class="nav-wrapper col s12 m6 row">
                  <div class="row">
                     <div style="margin-top:3px;" class="input-field col s6 m7">
                        <input id="searchEvalTxtbox" type="search" name="" placeholder="Search Evaluation">
                        <label class="label-icon" for="search"><i style="margin-top:4px;" class="material-icons">search</i></label>
                        <i id="btnReset" style="margin-top:1px;" class="material-icons">close</i>
                     </div>
                     <div style="margin:0;" class="input-field col s6 m5">
                        <a id="addEvaluationBtn" style="width:100%;text-transform:capitalize;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">add_circle</i>ADD</a>
                     </div>
                  </div>
                </div>
              </div>

              <div class="row">
                <table class="highlight striped">
                  <tbody id="evaluationTblBody">
                  </tbody>
                </table>
                <div id="pagealign">
                	<div id="evaluationPagination" class="center"></div>
                </div>
              </div>
            </div>
          </div>
        </div>
        
</body>
</html>