<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>PAGSS - Location</title>
<!-- START: CSS-HEADER -->
<jsp:include page="../admin/common/admin-css-header.jsp" />
<!-- END: CSS-HEADER -->
<!-- START: JS-HEADER -->
<jsp:include page="../admin/common/admin-js-header.jsp" />
<script src="static/assets/js/admin-location.js"></script>
<!-- END: JS-HEADER -->
</head>
<body>
	<!-- Header -->
	<jsp:include page="../admin/common/admin-header.jsp" />

	<!-- Left Side bar -->
	<jsp:include page="../admin/common/admin-sidebar.jsp" />

	<!-- Content Page -->
	<div class="container2">
          <div class="row">
            <div class="col s12 m5">
               <h4>Location</h4>
            </div>
            <div style="padding-top: 5px;" class="nav-wrapper col s12 m7 row">
				<div class="row">
					<div style="margin-top: 19px;" class="input-field col s6 m7">
						<input id="searchLocation" type="search" name=""
							placeholder="Search Location"> <label class="label-icon"
							for="search"><i style="margin-top: 4px;"
							class="material-icons">search</i></label> <i style="margin-top: 1px;"
							class="material-icons" id="clearSearch">close</i>
					</div>
					<div class="input-field col s6 m5">
						<a data-target="locationModal" id="AddlocationBtn"
							style="width: 100%; text-transform: capitalize;"
							class="btn modal-trigger waves-effect waves-light bttn"
							type="submit"><i class="material-icons left">add_location</i>Add
							Location</a>
					</div>
				</div>
			</div>
          </div>

          <div class="row">
            <hr style="width:100%;">
          </div>

          <div class="row">
            <div class="divcard">
              <table class="highlight">
                <thead>
					<tr>
						<th></th>
						<th>Code<a href="#!" id="locationCodeDESC" class="sortLink sort-by" data-sortdir= "DESC" data-sortname="locationCode"></a><a
							href="#" id="locationCodeASC" class="sortLink" data-sortdir = "ASC" data-sortname="locationCode"></a></th>
						<th>Location<a href="#!" id="locationNameDESC" class="sortLink sort-by" data-sortdir="DESC" data-sortname="locationName"></a><a
							href="#" id="locationNameASC" class="sortLink" data-sortdir="ASC" data-sortname="locationName"></a></th>
						<th>Description<a href="#!" id="descriptionDESC" class="sortLink sort-by" data-sortdir="DESC" data-sortname="description"></a><a id="descriptionASC" href="#" class="sortLink" data-sortdir="ASC" data-sortname="description"></a></th>
						<th>Status<a id="statusDESC" href="#!" class="sortLink sort-by" data-sortdir="DESC" data-sortname="status"></a><a
							href="#" id="statusASC" class="sortLink" data-sortdir="ASC" data-sortname="status"></a></th>
					</tr>
				</thead>

                <tbody id="locationTblBody">
                  <!-- Dynamically populated -->
                </tbody>
              </table>
              <div id="pagealign">
	              <div id="locationPagination">
	                <!-- Pagination -->
	              </div>
              </div>
            </div>
           </div>
        </div>


        <div id="locationModal" class="modal modal-fixed-footer">
		<div class="modal-header">
			<h5>Add Location</h5>
		</div>
		<div class="modal-content">
			<div class="content-row">
				<div>
					<b>Code*</b> <input type="text" name="" id="locationCode"
						class="validate"> <b>Location*</b> <input type="text"
						name="" id="locationName" class="validate"> <b>Description</b>
					<textarea style="resize: none;" id="description"
						class="materialize-textarea"></textarea>
					<b>Status</b> <label style="margin-left: 10px !important;">
						<input type="checkbox" id="locationStatus" checked/> <span>Active</span>
					</label>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<a class="modal-close btn waves-effect waves-light bttn"
				type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
			<a class="btn waves-effect waves-light bttn" type="submit"
				name="action" id="saveLocationBtn"><i
				class="material-icons left">save</i>Save</a>
		</div>
	</div>

	<!--Modal save location ends here-->

	<!--Modal edit location starts here-->

	<div id="editLocationModal" class="modal modal-fixed-footer">
		<div class="modal-header">
			<h5>Edit Location</h5>
		</div>
		<div class="modal-content">
			<input type="hidden" id="locationId" />
			<div class="content-row">
				<div>
					<b>Code*</b> <input type="text" name="" id="editLocationCode" disabled
						class="validate"> <b>Location*</b> <input type="text"
						name="" id="editLocationName" class="validate"> <b>Description</b>
					<textarea style="resize: none;" id="editDescription"
						class="materialize-textarea"></textarea>
					<b>Status</b> <label style="margin-left: 10px !important;">
						<input type="checkbox" id="editLocationStatus" /> <span>Active</span>
					</label>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<a class="modal-close btn waves-effect waves-light bttn"
				type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
			<a class="btn waves-effect waves-light bttn" id="updateLocationBtn"
				type="submit" name="action"><i class="material-icons left">save</i>Save</a>
		</div>
	</div>

	<!--Modal edit location ends here-->
</body>
</html>