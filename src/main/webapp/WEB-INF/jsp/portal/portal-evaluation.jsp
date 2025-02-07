<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>

<head>
  <title>PAGSS</title>
  <meta name="viewport" content="width = device-width, initial-scale = 1">
  <!-- START: CSS-HEADER -->
  <jsp:include page="../portal/common/css-header.jsp" />
  <!-- END: CSS-HEADER -->
  <link rel="stylesheet" href="static/assets/css/evaluationportalpage.css">
  <!-- START: JS-HEADER -->
  <jsp:include page="../portal/common/js-header.jsp" />
  <jsp:include page="../admin/common/admin-tinymce-header.jsp" />
  <script src="static/assets/js/utility/tinymce-util.js"></script>
  <script src="static/assets/js/portal-evaluation.js"></script>
  <!-- END: JS-HEADER -->
</head>

<body>
  <!-- Header -->
  <jsp:include page="../portal/common/portal-header.jsp" />

  <!-- Left Side bar -->
  <jsp:include page="../portal/common/portal-sidenav.jsp" />
  <div class="container" style="margin-top: 20px;">
    <div class="divcard">

      <!-- Breadcrumbs starts here -->
      <div class="row">
        <nav>
          <div>
            <div class="col s12">
              <label class="breadcrumbs" id="courseName"></label>
              <label class="breadcrumbs" id="className"></label>
              <label class="breadcrumbs" id="evaluationName"></label>
            </div>
          </div>
        </nav>
      </div>
      <!-- Breadcrumbs ends here -->
       <!-- Modal starts here -->
       <div id="evalSubmit" class="evalSize modal modal-fixed-footer">
        <div class="modal-header"> 
          <h5>Submit</h5>
        </div>
        <div id="TraineesModal" class="modal-content">

          <div>
            <span>Are you sure you already want to submit your evaluation?</span>
          </div>

        </div>
        <div class="modal-footer">
          <a class="modal-close btn waves-effect waves-light bttn" type="button"><i class="material-icons left">cancel</i>No</a>
          <a class="btn waves-effect waves-light bttn" type="submit" name="action" id="submitForm"><i class="material-icons left">save</i>Yes</a>
        </div>
    </div>
  
  <!-- Modal ends here -->
      <div class="row" style="margin-top: 25px;">
        <div class="col s7 m9">
          <h6 id="evaluationName1"></h6>
        </div>
        <div class="col s5 m3">
          <a data-target="evalSubmit" class="modal-trigger bttn btn waves-light waves-effect" style="width: 100%;">Submit</a>
        </div>
      </div>

      <div class="row" style="margin-top:10px;">
        <hr style="width: 100%;">
      </div>
      <div id="questionList">

      </div>
    </div>
  </div>
  <input type="hidden" id="classId" value="${classId}">
  <input type="hidden" id="courseEvaluationId" value="${courseEvaluationId}">
  <input type="hidden" id="courseId" value="${courseId}">
  <input type="hidden" id="classEvaluationId" value="${classEvaluationId}">
</body>

</html>