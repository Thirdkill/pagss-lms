<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="ISO-8859-1">
	<title id="actionTitle">PAGSS - Add New Question</title>
	<!-- START: CSS-HEADER -->  
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	 <jsp:include page="../admin/common/admin-js-header.jsp" />
	 <jsp:include page="../admin/common/admin-tinymce-header.jsp" />
	 <script src="static/assets/js/utility/tinymce-util.js"></script>
	 <script src="static/assets/js/admin-createquestion.js"></script>
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
               <h4 id="actionLabel">Add New Question</h4>
            </div>
            <div id="navpad" class="nav-wrapper col s12 m4 row">
              <div class="row">
                 <div class="input-field col s6 m6">
                    <a href="admin.questions" style="width:100%;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">not_interested</i>Cancel</a>
                 </div>
                 <div class="input-field col s6 m6">
                    <a style="width:100%;" id="saveQuestionBtn" class="btn waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">save</i>
                    <c:choose>
					    <c:when test="${action != 'create' }">
					        UPDATE
					    </c:when>
					    <c:otherwise>
					        SAVE
					    </c:otherwise>
					</c:choose>
                    </a>
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
                <div class="col s6 m4">
                  <b>Type</b>
                  <select id="questionTypeDropDown">
                  <option value="0" disabled selected>Choose your option</option>
                  <!-- Dynamically populated -->
                </select>
                </div>
                <div class="col s6 m3">
                  <b>Topic</b>
                  <select id="topicDropDown">
	                 <option value="0" disabled selected>Choose your option</option>
	                  <!-- Dynamically populated -->
	                </select>
                </div>
                <div class="col s10 m4">
                  <b>Difficulty Level</b>
                  <select id="difficultyDropDown">
	                  <option value="0" disabled selected>Choose your option</option>
	                  <!-- Dynamically populated -->
	                </select>
                </div>
                <div class="col s2 m1 switch left">
                  <b>Status</b>
                  <label>
                    <input type="checkbox" id="questionStatus" checked>
                  <span style="" class="lever left"></span>
                  </label>
                </div>
              </div>
              <!--Hide parts on enumeration and ordering type-->
              <div style="padding-top: 10px;" class="matching fillInTheBlank multipleChoice trueorfalse essay identification matching choiceType directType showOpt row">
                <div class="col s12 m6">
                  <b>Question Label</b>
                  <input type="text" name="" class="validate" id="questionLabel">
                </div>
                <div class="col s12 m6">
                  <form id="uploadMediaForm">
                    <div class="file-field input-field" style="margin-top: 0 !important;">
                      <div class="btn bttn right" style="margin-top: 20px;">
                        <span>Browse</span>
					     <input type="file" id="fileUpload" name="fileUpload"/>
                      </div>
                      <div class="file-path-wrapper" style="padding-top: 0 !important;">
                        <b>Media Display</b>
                        <input class="file-path validate" type="text" id="mediaDisplayTxt">
                      </div>
                    </div>
                  </form>
                </div>
              </div>
              <!--up to here-->
            </div>
          </div>

         <!--Text editor WYSIWYG for matching type starts here-->
        <div  class="row multipleChoice fillInTheBlank trueorfalse essay identification enumeration ordering matching assessment scores choiceType directType showOpt">
			<div style="margin-bottom: 20px;">
			<form>
				<textarea id="texteditor"></textarea>
			</form>	
			</div>
        </div>
        <!--Text editor WYSIWYG for matching type ends here-->

          <!--multiple choice option starts here-->
          <div class="row multipleChoice showOpt">
           <div id="multipleChoiceCon" style="display:none;" class="answerCon divcard">
              <div class="row">
                <div>
                  <b>Choices</b>
                </div>
              </div>
              <div id="multipleChoiceDiv" class="row" style="margin-bottom: 10px;">
				<!-- Dynamically Populated -->
              </div>
              <div class="row">
                <div>
                  <a class="btn waves-effect waves-light bttn" type="submit" name="action" id="addMultipleChoiceBtn"><i class="material-icons left">add_circle</i>Add a choice</a>
                </div>
              </div>
            </div>
          </div>
          <!--multiple choice option ends here-->



          <!--true or false option starts here-->
          <div class="row trueorfalse showOpt">
            <div id="trueOrFalseCon" style="display:none;" class="answerCon divcard">
              <div class="row">
                <div>
                  <p><b>Answer</b></p>
                    <p>
                      <label style="margin-left:10px !important;">

                        <input type="checkbox" id="trueChckBox"/>
                        <span style="color: #1e1e1e !important">True</span>

                      </label>
                    </p>
                    <p>
                      <label style="margin-left:10px !important;">

                        <input type="checkbox" id="falseChckBox"/>
                        <span style="color: #1e1e1e !important">False</span>

                      </label>
                    </p>
                </div>
              </div>
            </div>
          </div>
          <!--true or false option ends here-->



          <!--Identification option starts here-->
          <div class="row identification showOpt" >
            <div id="identificationCon" style="display:none;" class="answerCon divcard">
              <div class="row">
                <div class="input-field">
                  <b>Answer</b>
                  <input type="text" id="identificationAnswer" class="validate text-input" name="" >
                  <p>
                    <label style="margin-left:10px !important;">
                      <input type="checkbox" id="identificationMatchCaseChkBox"/>
                      <span>Match Case</span>
                    </label>
                  </p>
                </div>
              </div>
            </div>
          </div>
          <!--Identification option ends here-->


          <!--Enumeration option starts here-->
          <div class="row enumeration showOpt" >
             <div id="enumerationCon" style="display:none;" class="answerCon divcard">
              <div class="row">
                <div>
                  <b>Answer</b>
                </div>
                <div class="row" id="enumerationAnswersCon">
                  <div class="col s10 m11" id="enumChoice_0">
                    <input type="text" placeholder="Option" class="enumChoice">
                  </div>
                  <div style="padding-top: 15px;" id="enumChoiceDeleteCon_0" class="col s2 m1">
                    <a class="waves-effect waves-light deleteEnumChoiceLink" data-id="0"><i class="material-icons">delete</i></a>
                  </div>
                </div>
                <div class="row">
                  <div>
                    <a class="btn waves-effect waves-light bttn" id="btnAddEnumeration"><i class="material-icons left">add_circle</i>Add</a>
                    <p>
                      <label style="margin-left:10px !important;">
                        <input type="checkbox" id="enumMatchCaseChkBox"/>
                        <span>Match Case</span>
                      </label>
                    </p>
                    <p>
                      <label style="margin-left:10px !important;">
                        <input type="checkbox" id="enumIgnoreOrder" checked/>
                        <span>Ignore Order</span>
                      </label>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!--Enumeration option ends here-->



          <!--Ordering option starts here-->
          <div class="row ordering showOpt">
            <div id="OrderingCon" style="display:none;" class="answerCon divcard">
              <div class="row">
                <div>
                  <b>Answer</b>
                </div>
                <div class="row" id="orderingAnswerCon">
	              	<div id="orderingRow">
		                  <div class="col s10 m10 sortable" id="orderingAnswerRow_0">
		                  	<a href="#!" data-id="0"><i class="material-icons">delete</i></a>
		                  	<label>MOVE ME</label>
		                    <input type="text" data-id="0" class="orderingAnswerTxtBox">
		                  </div>
	                </div>
                </div>
                <div>
                  <a class="btn waves-effect waves-light bttn" id="addOrderingBtn"><i class="material-icons left">add_circle</i>Add</a>
                </div>
              </div>
            </div>
          </div>
          <!--Ordering option ends here-->


          <!--Matching option starts here-->
          <div class="row matching showOpt">
            <div id="matchingCon" style="display:none;" class="answerCon divcard">
              <div class="row">
                <div>
                  <b>Answer</b>
                </div>
                <div class="row" id="pairDiv">
                	<div id="pairSection_1">
		                <div class="col s12 m6">
		                    <input type="text" class="pairA" id="pairA_1" placeholder="Pair 1" value="">
		                  </div>
		                  <div class="col s11 m5">
		                    <input type="text" class="pairB" id="pairB_1" placeholder="Pair 1" value="">
		                  </div>
		                  <div class="col s1 m1 orderingicons">
		                    <a href="#!" class="removeAnalogy" data-id=1><i class="material-icons">delete</i></a>
		                  </div>
                	</div>
                	<div id="pairSection_2">
		                <div class="col s12 m6">
		                    <input type="text" class="pairA" id="pairA_2" placeholder="Pair 2">
		                  </div>
		                  <div class="col s11 m5">
		                    <input type="text" class="pairB" id="pairB_2" placeholder="Pair 2">
		                  </div>
		                  <div class="col s1 m1 orderingicons">
		                    <a href="#!" class="removeAnalogy" data-id=2><i class="material-icons">delete</i></a>
		                  </div>
                	</div>
                </div>
                <div>
                  <a class="btn waves-effect waves-light bttn" id="addPair"><i class="material-icons left">add_circle</i>Add Pair</a>
                </div>
              </div>
            </div>
          </div>
          <!--Matching option ends here-->

          <!--Assessment option starts here-->
          <div class="row assessment scores showOpt">
           <div id="AssessmentCon" style="display:none;" class="answerCon divcard">
              <div class="row">
                <a data-target="addCriteriaModal" class="modal-trigger bttn btn waves-effect waves-light right" type="submit"><i class="material-icons left">add_circle</i>Add Criteria</a>
              </div>
              <div class="row">
                <table class="highlight striped">
                  <thead>
                    <tr>
                        <th class="min">Actions</th>
                        <th class="midl">Criteria</th>
                        <th class="mins">Min Score</th>
                        <th class="mins">Nax Score</th>
                        <th class="min">Comment</th>
                        <th class="mid">Pass Benchmark</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td class="min">
                        <a data-target="addCriteriaModal" class="modal-trigger waves-effect waves-light"><i class="material-icons">pageview</i></a>
                        <a href="#!" class="waves-effect waves-light"><i class="material-icons">edit</i></a>
                      </td>
                      <td class="midl">Eclair</td>
                      <td class="mins">$0.87</td>
                      <td class="mins">Eclair</td>
                      <td class="min">$0.87</td>
                      <td class="mid">$0.87</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
          <!--Assessment option ends here-->
            
        </div>
		 <div id="essayCon" style="display:none" class="answerCon">
        </div>

        <!-- Add answer modal for fill in the blanks starts here -->
        <div id="addFillInTheBlanksDropDownModal" class="modal modal-fixed-footer fixed-modal">
          <div class="modal-header">
            <h5>Add Answer</h5>
          </div>
          <div class="modal-content">
            <div class="content-row" style="height: 200px;">
              <!-- Choice type answer starts here -->
              <div class="choiceType showOpt">
                <b>Choices</b>
                <div id="fitbChoiceDiv">
	                <div id="fitbChoiceDiv_0" class="row" style="margin-bottom: 10px;">
	                  <div class="col s7 m9">
	                    <input type="text" class="fitb_choice" data-id="0">
	                  </div>
	                  <div class="col s3 m2">
	                    <p class="pchck">
	                      <label style="margin: 5px 0 0 10px;">
	                        <input type="checkbox" class="fitb_isanswer" data-id="0"/>
	                        <span>Correct</span>
	                      </label>
	                    </p> 
	                  </div>
	                  <div style="padding-top: 15px;" class="col s2 m1">
	                    <a class="waves-effect waves-light deleteFITBChoiceBtn" data-id="0"><i class="material-icons">delete</i></a>
	                  </div>
	                </div>
                </div>
                <div>
                  <a id="fitbAddChoiceBtn" class="btn waves-effect waves-light bttn"><i class="material-icons left">add_circle</i>Add a choice</a>
                </div>
              </div>
              <!-- Choice type answer Ends here -->
            </div>
          </div>
          <div class="modal-footer">
            <a class="modal-close btn waves-effect waves-light bttn" id="fitbCancelBtn"><i class="material-icons left">cancel</i>Cancel</a>
            <a class="btn waves-effect waves-light bttn" type="submit" name="action" id="saveFITBDropDownBtn"><i class="material-icons left">save</i>Save</a>
          </div>
        </div>
        <!-- Add answer modal for fill in the blanks starts here -->

        <!-- Add answer modal for fill in the blanks starts here -->
        <div id="addFillInTheBlanksTextBoxModal" class="modal modal-fixed-footer fixed-modal">
          <div class="modal-header">
            <h5>Add Answer</h5>
          </div>
          <div class="modal-content">
            <div class="content-row" style="height: 200px;">
              <!-- Direct type answer starts here -->
              <div class="directType showOpt">
                <b>Answer</b>
                <div>
                  <div>
                    <input type="text" id="fitbDirectAnswerTxtBox">
                  </div>
                  <label style="margin-left:10px !important;">
                    <input type="checkbox" id="fitbDirectAnswerChkBox"/>
                    <span>Match Case</span>
                  </label>
                </div>
              </div>
              <!-- Direct type answer ends here -->
            </div>
          </div>
          <div class="modal-footer">
            <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
            <a class="btn waves-effect waves-light bttn" id="saveFITBAnswerTxtBoxBtn"><i class="material-icons left">save</i>Save</a>
          </div>
        </div>
        <!-- Add answer modal for fill in the blanks starts here -->

        <!-- Assessment add criteria modal starts here-->
        <div id="addCriteriaModal" class="modal modal-fixed-footer">
            <div class="modal-header">
              <h5>Add Location</h5>
            </div>
            <div class="modal-content">
              <div class="content-row">
                <div>
                  <b>Criteria*</b>
                    <input type="text" name="" id="editLocationCode" class="validate">
                </div>
                <div style="height: 250px;">
                  <b>Comment*</b>
                    <select>
                      <option disabled selected></option>
                      <option value="scores">With Comment</option>
                      <option>asd</option>
                      <option>asd</option>
                    </select>
                  <div class="row scores  showOpt">
                    <div class="col s12 m4">
                      <span style="text-align: center !important;font-weight: bold;">Minimum Score:*</span>
                      <input type="text" name="" placeholder="">
                    </div>
                    <div class="col s12 m4">
                      <span style="text-align: center !important;font-weight: bold;">Maximum Score:*</span>
                      <input type="text" name="" placeholder="">
                    </div>
                    <div class="col s12 m4">
                      <span style="text-align: center !important;font-weight: bold;">Pass Benchmark:*</span>
                      <input type="text" name="" placeholder="">
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" id="updateLocationBtn" type="submit" name="action"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>
        <!-- Assessment add criteria modal ends here-->
	<input type="hidden" id="action" value="${action}"/>
	<input type="hidden" id="questionId" value="${questionId}"/>
	<input type="hidden" id="contentHdn">
</body>
</html>