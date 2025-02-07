<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>PAGSS - IP Address</title>
<!-- START: CSS-HEADER -->
<jsp:include page="../admin/common/admin-css-header.jsp" />
<!-- END: CSS-HEADER -->
<!-- START: JS-HEADER -->
<jsp:include page="../admin/common/admin-js-header.jsp" />
<script src="static/assets/js/admin-ipaddress.js"></script>
<!-- END: JS-HEADER -->
</head>
<body>
	<div>
		<!-- Header -->
		<jsp:include page="../admin/common/admin-header.jsp" />

		<!-- Left Side bar -->
		<jsp:include page="../admin/common/admin-sidebar.jsp" />

		<!-- Content Page -->
		<div class="container2">
			<div class="row">
				<div class="col s12 m6">
					<h4>IP Address</h4>
				</div>
				<div style="padding-top: 5px;" class="nav-wrapper col s12 m6 row">
					<div class="row">
						<div style="margin-top: 19px;" class="input-field col s6 m6">
							<input style="padding-right: 0 !important;" id="searchIPAddress"
								class="search" type="search" name=""
								placeholder="Search IP Address"> <label
								class="label-icon" for="search"><i
								style="margin-top: 4px;" class="material-icons">search</i></label> 
								<i id="clearSearhField" style="margin-top: 1px;" class="material-icons">close</i>
						</div>
						<div class="input-field col s6 m6">
							<a data-target=modal1
								style="width: 100%; text-transform: capitalize;"
								class="btn modal-trigger waves-effect waves-light bttn"
								type="submit" name=""><i class="material-icons left">add_circle</i>Whitelist</a>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<hr style="width: 100%;">
			</div>

			<div class="row">
				<div class="divcard">
					<table class="highlight ">
						<thead>
							<tr>
								<th></th>
								<th>IP Address<a href="#!" class="sort-by"></a><a href="#"></a></th>
								<th>Date Added<a href="#!" class="sort-by"></a><a href="#"></a></th>
								<th>Added By<a href="#!" class="sort-by"></a><a
									href="#"></a></th>
							</tr>
						</thead>
						<tbody id="ipaddressTblBody">
						</tbody>
					</table>
					<div id="pagealign">
						<div id="ipaddressPagination">
						</div>
					</div>
				</div>
			</div>

		</div>






		<!--Modal starts here-->

		<div id="modal1" class="ipmodal modal modal-fixed-footer">
			<div class="modal-header">
				<h5>Whitelist IP Address</h5>
			</div>
			<div class="modal-content">
				<div class="content-row">
					<div>
						<p><h6>
							<b>IP Address*</b>
						</h6>
						</p>
		            <input type="text" name="" maxlength="20" id="ipaddress"
							class="validate">
		        </div>
		      </div>
		    </div>
		    <div class="modal-footer">
		      <a class="modal-close btn waves-effect waves-light bttn"
					type="button" name="action" onclick="clrFields();"><i
					class="material-icons left">cancel</i>Cancel</a>
		      <a id="newIpSubmit"
					class="modal-close btn waves-effect waves-light bttn" disabled
					type="submit"><i class="material-icons left">save</i>Save</a>
		    </div>
          </div>

        <!--Modal ends here-->
	</div>

<!--Modal ends here-->
</body>
</html>