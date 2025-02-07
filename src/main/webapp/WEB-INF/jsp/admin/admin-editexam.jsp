<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/admin/common/admin-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PAGSS - EDIT EXAM</title>
	<!-- START: CSS-HEADER -->
	<jsp:include page="../admin/common/admin-css-header.jsp" />
	<!-- END: CSS-HEADER -->
	<!-- START: JS-HEADER -->
	<jsp:include page="../admin/common/admin-js-header.jsp" />
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
               <h4>Edit Exam</h4>
            </div>
            <div id="navpad" class="nav-wrapper col s12 m4 row">
              <div class="row">
                 <div class="input-field col s6 m6">
                    <a href="admin-examinations.html" style="width:100%;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">not_interested</i>Cancel</a>
                 </div>
                 <div class="input-field col s6 m6">
                    <a style="width:100%;" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="" ><i class="material-icons left">save</i>Save</a>
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
                  <b>Exam Code</b>
                  <input type="text" name="" class="validate">
                </div>
                <div class="col s10 m6">
                  <b>Exam Title</b>
                  <input type="text" name="" class="validate">
                </div>
                <div class="col s2 m1 switch left">
                  <b>Status</b>
                  <label>
                    <input type="checkbox">
                    <span style="" class="lever left"></span>
                  </label>
                </div>
              </div>
              <div style="padding-top: 10px;" class="row">
                <div class="col s12 m5 switch">
                  <b>Exam Type</b>
                  <select>
                    <option>Final Exam</option>
                    <option>asd</option>
                  </select>
                  <b class="left" style="margin:10px 15px 0 0;">Allow Attachment</b>
                  <label>
                    <input type="checkbox">
                    <span class="lever left"></span>
                  </label>
                </div>
                <div class="col s12 m7">
                  <div>
                    <b>Exam Description</b>
                    <div class="editorgroup">
                      <a class="custombtn waves-effect waves-light" title="Bold"><i class="material-icons">format_bold</i></a>
                      <a class="custombtn waves-effect waves-light" title="Italic"><i class="material-icons">format_italic</i></a>
                      <a class="custombtn waves-effect waves-light" title="Underline"><i class="material-icons">format_underline</i></a>
                      <a class="custombtn waves-effect waves-light" title="Strikethrough"><i class="material-icons">format_strikethrough</i></a>
                      <a class="custombtn waves-effect waves-light" title="Align Left"><i class="material-icons">format_align_left</i></a>
                      <a class="custombtn waves-effect waves-light" title="Align Center"><i class="material-icons">format_align_center</i></a>
                      <a class="custombtn waves-effect waves-light" title="Align Right"><i class="material-icons">format_align_right</i></a>
                      <a class="custombtn waves-effect waves-light" title="Justify"><i class="material-icons">format_align_justify</i></a>
                      <a class="custombtn waves-effect waves-light" title="Bullet"><i class="material-icons">format_list_bulleted</i></a>
                      <a class="custombtn waves-effect waves-light" title="Number"><i class="material-icons">format_list_numbered</i></a>
                      <a class="custombtn waves-effect waves-light" title="Undo"><i class="material-icons">undo</i></a>
                      <a class="custombtn waves-effect waves-light" title="Redo"><i class="material-icons">redo</i></a>
                      <a class="custombtn waves-effect waves-light" title="Insert Image"><i class="material-icons">image</i></a>
                    </div>
                    <div>
                      <textarea id="editorarea" placeholder="Enter text here"></textarea>
                    </div>
                  </div>
                </div>
                <div class="right buttonright">
                  <a data-target="addSectionModal" class="btn modal-trigger waves-effect waves-light bttn" type="submit" name="action"><i class="material-icons left">add_circle</i>Add Section</a>
               </div>
              </div>
            </div>
          </div>

          <!-- Section for randomize new exam starts here -->
          <div class="row">
            <div class="divcard">
              <div class="row">
                <ul class="collapsible">
                  <li>
                    <div class="collapsible-header" style="padding: 10px;">
                      <span style="padding-top: 5px;">Section 1</span>
                      <div class="collapsibleIcon">
                        <i class="material-icons rotate arrowRotate">arrow_drop_down</i>
                        <a href="#!"><i class="material-icons">delete</i></a>
                      </div>
                    </div>
                    <div class="collapsible-body bodyContent">
                      <div class="row">
                        <div class="col s12 m6" style="padding: 0;">
                          <input type="text" name="" placeholder="Section Name">
                        </div>
                        <div class="col s12 m3" style="padding: 18px 0;">
                          <div class="switch center">
                            <span style="margin-right: 5px;">Randomized</span>
                            <label>
                              <input type="checkbox" value="randomExam">
                              <span style="margin-top: 0 !important;" class="lever"></span>
                            </label>
                          </div>
                        </div>
                        <div class="col s12 m3" style="padding: 18px 0;">
                          <div class="switch center">
                            <span style="margin-right: 5px;">Shuffle Questions</span>
                            <label>
                              <input type="checkbox" value="shuffleExam">
                              <span style="margin-top: 0 !important;" class="lever"></span>
                            </label>
                          </div>
                        </div>
                      </div>
                      <div class="row">
                        <div>
                          <div class="editorgroup">
                            <a class="custombtn waves-effect waves-light" title="Bold"><i class="material-icons">format_bold</i></a>
                            <a class="custombtn waves-effect waves-light" title="Italic"><i class="material-icons">format_italic</i></a>
                            <a class="custombtn waves-effect waves-light" title="Underline"><i class="material-icons">format_underline</i></a>
                            <a class="custombtn waves-effect waves-light" title="Strikethrough"><i class="material-icons">format_strikethrough</i></a>
                            <a class="custombtn waves-effect waves-light" title="Align Left"><i class="material-icons">format_align_left</i></a>
                            <a class="custombtn waves-effect waves-light" title="Align Center"><i class="material-icons">format_align_center</i></a>
                            <a class="custombtn waves-effect waves-light" title="Align Right"><i class="material-icons">format_align_right</i></a>
                            <a class="custombtn waves-effect waves-light" title="Justify"><i class="material-icons">format_align_justify</i></a>
                            <a class="custombtn waves-effect waves-light" title="Bullet"><i class="material-icons">format_list_bulleted</i></a>
                            <a class="custombtn waves-effect waves-light" title="Number"><i class="material-icons">format_list_numbered</i></a>
                            <a class="custombtn waves-effect waves-light" title="Undo"><i class="material-icons">undo</i></a>
                            <a class="custombtn waves-effect waves-light" title="Redo"><i class="material-icons">redo</i></a>
                            <a class="custombtn waves-effect waves-light" title="Insert Image"><i class="material-icons">image</i></a>
                          </div>
                          <div>
                            <textarea id="editorarea" placeholder="Enter text here"></textarea>
                          </div>
                        </div>
                      </div>
                      <div class="row">
                        <div>
                          <div class="col s6 m6" style="padding:8px 0 0 0;">
                            <span><b>Question Type:</b> Multiple Choice</span>
                          </div>
                          <div class="col s6 m6" style="padding-right: 0;">
                            <a data-target="partModal" class="modal-trigger bttn btn waves-effect waves-light right" type="submit"><i class="material-icons left">add_circle</i>Add Part</a>
                          </div>
                        </div>
                      </div>
                      <div>
                        <table class="highlight striped">
                          <thead>
                            <tr>
                                <th class="min">Actions</th>
                                <th class="mid">Topic</th>
                                <th class="mid">Difficulty Level</th>
                                <th class="mid">No. of Questions</th>
                                <th class="mins">Weight</th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <td class="min">
                                <a data-target="partPreviewModal" class="modal-trigger waves-effect waves-light"><i class="material-icons">pageview</i></a>
                                <a href="#!" class="waves-effect waves-light"><i class="material-icons">edit</i></a>
                              </td>
                              <td class="mid">Eclair</td>
                              <td class="mid">$0.87</td>
                              <td class="mid">Eclair</td>
                              <td class="mins"><input type="text" name="" style="text-align: center;"></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                      <div style="padding:8px 0 0 0;">
                        <b>Total Score:</b> <span>0</span>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
            </div>          
          </div>
          <!-- Section for randomize new exam ends here -->

          <!-- Section for not-randomize new exam starts here -->
          <div class="row">
            <div class="divcard">
              <div class="row">
                <ul class="collapsible">
                  <li>
                    <div class="collapsible-header" style="padding: 10px;">
                      <span style="padding-top: 5px;">Section 1</span>
                      <div class="collapsibleIcon">
                        <i class="material-icons rotate arrowRotate">arrow_drop_down</i>
                        <a href="#!"><i class="material-icons">delete</i></a>
                      </div>
                    </div>
                    <div class="collapsible-body bodyContent">
                      <div class="row">
                        <div class="col s12 m6" style="padding: 0;">
                          <input type="text" name="" placeholder="Section Name">
                        </div>
                        <div class="col s12 m3" style="padding: 18px 0;">
                          <div class="switch center">
                            <span style="margin-right: 5px;">Randomized</span>
                            <label>
                              <input type="checkbox" value="randomExam">
                              <span style="margin-top: 0 !important;" class="lever"></span>
                            </label>
                          </div>
                        </div>
                        <div class="col s12 m3" style="padding: 18px 0;">
                          <div class="switch center">
                            <span style="margin-right: 5px;">Shuffle Questions</span>
                            <label>
                              <input type="checkbox" value="shuffleExam">
                              <span style="margin-top: 0 !important;" class="lever"></span>
                            </label>
                          </div>
                        </div>
                      </div>
                      <div class="row">
                        <div>
                          <div class="editorgroup">
                            <a class="custombtn waves-effect waves-light" title="Bold"><i class="material-icons">format_bold</i></a>
                            <a class="custombtn waves-effect waves-light" title="Italic"><i class="material-icons">format_italic</i></a>
                            <a class="custombtn waves-effect waves-light" title="Underline"><i class="material-icons">format_underline</i></a>
                            <a class="custombtn waves-effect waves-light" title="Strikethrough"><i class="material-icons">format_strikethrough</i></a>
                            <a class="custombtn waves-effect waves-light" title="Align Left"><i class="material-icons">format_align_left</i></a>
                            <a class="custombtn waves-effect waves-light" title="Align Center"><i class="material-icons">format_align_center</i></a>
                            <a class="custombtn waves-effect waves-light" title="Align Right"><i class="material-icons">format_align_right</i></a>
                            <a class="custombtn waves-effect waves-light" title="Justify"><i class="material-icons">format_align_justify</i></a>
                            <a class="custombtn waves-effect waves-light" title="Bullet"><i class="material-icons">format_list_bulleted</i></a>
                            <a class="custombtn waves-effect waves-light" title="Number"><i class="material-icons">format_list_numbered</i></a>
                            <a class="custombtn waves-effect waves-light" title="Undo"><i class="material-icons">undo</i></a>
                            <a class="custombtn waves-effect waves-light" title="Redo"><i class="material-icons">redo</i></a>
                            <a class="custombtn waves-effect waves-light" title="Insert Image"><i class="material-icons">image</i></a>
                          </div>
                          <div>
                            <textarea id="editorarea" placeholder="Enter text here"></textarea>
                          </div>
                        </div>
                      </div>
                      <div class="row">
                        <div>
                          <div class="col s6 m6" style="padding:8px 0 0 0;">
                            <span><b>Questions:</b></span>
                          </div>
                          <div class="col s6 m6" style="padding-right: 0;">
                              <div class="right">
                                 <a href="#!" class="bttn btn waves-effect waves-light" type="submit" style="margin-right: 15px;"><i class="material-icons left">add_circle</i>Add New</a>
                                 <a data-target="addexistingModal" class="modal-trigger bttn btn waves-effect waves-light right" type="submit"><i class="material-icons left">add_circle</i>Add Existing</a>
                               </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <table class="highlight striped">
                          <thead>
                            <tr>
                                <th class="min">Actions</th>
                                <th class="large">Question</th>
                                <th class="mid">Type</th>
                                <th class="mins">Weight</th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <td class="min">
                                <a data-target="partPreviewModal" class="modal-trigger waves-effect waves-light"><i class="material-icons">pageview</i></a>
                                <a href="#!" class="waves-effect waves-light"><i class="material-icons">edit</i></a>
                              </td>
                              <td class="mid">Eclair</td>
                              <td class="mid">$0.87</td>
                              <td class="mid">Eclair</td>
                              <td class="mins"><input type="text" name="" style="text-align: center;"></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                      <div style="padding:8px 0 0 0;">
                        <b>Total Score:</b> <span>0</span>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
            </div>          
          </div>
          <!-- Section for not-randomize new exam starts here -->

        </div>
        
        <!--Modal add section starts here-->

        <div id="addSectionModal" class="modal modal-fixed-footer">
            <div class="modal-header">
              <h5>Add Section</h5>
            </div>
            <div class="modal-content">
              <div class="content-row">
                <div class="switch">
                  <b>Name*</b>
                    <input type="text" name="" id="locationCode" class="validate">
                </div>
              </div>
              <div class="content-row row">
                <div class="col s12 m6">
                  <div class="switch right">
                    <span style="margin-right: 5px;">Randomized</span>
                    <label>
                      <input type="checkbox" value="randomExam">
                      <span style="margin-top: 0 !important;" class="lever"></span>
                    </label>
                  </div>
                </div>
                <div class="col s12 m6">
                  <div class="switch">
                    <span style="margin-right: 5px;">Shuffle Questions</span>
                    <label>
                      <input type="checkbox" value="shuffleExam">
                      <span style="margin-top: 0 !important;" class="lever"></span>
                    </label>
                  </div>
                </div>
              </div>
              <div class="content-row">
                <div class="col s12 m12">
                  <b>Type*</b>
                    <select>
                      <option>Multiple Choice</option>
                      <option>Multiple Choice</option>
                      <option>Multiple Choice</option>
                    </select>
                </div>
              </div>
              <div class="content-row center">
                <p>Note: Randomization settings cannot be undone once saved</p>
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" type="submit" name="action" id="saveLocationBtn"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>
        <!--Modal add section ends here-->

        <!--Modal add part starts here-->

        <div id="partModal" class="modal modal-fixed-footer" style="height: 400px;">
            <div class="modal-header">
              <h5>Add Part</h5>
            </div>
            <div class="modal-content">
              <div class="content-row">
                <div>
                  <b>Topic*</b>
                    <select>
                      <option>Clarity</option>
                      <option>asd</option>
                    </select>
                  <b>Difficulty Level*</b>
                    <select>
                      <option>Easy</option>
                      <option>Medium</option>
                      <option>Hard</option>
                    </select>
                  <b>No. of Questions*</b>
                    <input type="text" name="" id="">
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" type="submit" name="action" id="saveLocationBtn"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>
        <!--Modal add part ends here-->

        <!-- Part preview Modal starts here  -->
        <div id="partPreviewModal" class="modal modal-fixed-footer" style="height: 400px;">
          <div class="modal-content">
            <div class="content-row">
              <div class="carousel carousel-slider center">
                <div class="carousel-fixed-item center">
                  <a class="btn waves-effect white grey-text darken-text-2">button</a>
                  <h2>First Panel</h2>
                  <p class="white-text">This is your first panel</p>
                </div>
                <div class="carousel-item red white-text" href="#one!">
                  <h2>First Panel</h2>
                  <p class="white-text">This is your first panel</p>
                </div>
                <div class="carousel-item amber white-text" href="#two!">
                  <h2>Second Panel</h2>
                  <p class="white-text">This is your second panel</p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- Part preview Modal ends here  -->
        <!--Modal add existing starts here-->
        <div id="addexistingModal" class="modal modal-fixed-footer">
            <div class="modal-header">
              <h5>Add Existing Question</h5>
            </div>
            <div class="modal-content">
              <b>Filter</b>
              <div class="content-row">
                <div>
                  <b>Type</b>
                    <select>
                      <option>All</option>
                      <option>asd</option>
                    </select>
                  <b>Topic</b>
                    <select>
                      <option>All</option>
                      <option>asd</option>
                      <option>asd</option>
                    </select>
                   <b>Difficulty Level</b>
                    <select>
                      <option>All</option>
                      <option>asd</option>
                    </select>
                </div>
              </div>
              <div class="content-row">
                <div class="right">
                  <a class="btn waves-effect waves-light bttn" type="submit" name="action"><i class="material-icons left">search</i>Search</a>
                </div>
              </div>
              <div class="content-row">
                <table class="highlight responsive-table">
                  <thead>
                    <tr>
                        <th>
                          <label style="margin-left:15px !important;">
                            <input type="checkbox" />
                            <span></span>
                          </label>
                        </th>
                        <th>Question<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
                        <th>Topic<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
                        <th>Difficulty level<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
                        <th>Type<a href="#!"><i class="material-icons down">arrow_drop_down</i></a><a href="#"><i class="material-icons up">arrow_drop_up</i></a></th>
                    </tr>
                  </thead>

                  <tbody>
                    <tr>
                      <td>
                        <label style="margin-left:15px !important;">
                          <input type="checkbox" />
                          <span></span>
                        </label>
                      </td>
                      <td>Eclair</td>
                      <td>$0.87</td>
                      <td>Eclair</td>
                      <td>$0.87</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
            <div class="modal-footer">
              <a class="modal-close btn waves-effect waves-light bttn" type="button" name="action"><i class="material-icons left">cancel</i>Cancel</a>
              <a class="btn waves-effect waves-light bttn" type="submit" name="action" id="saveLocationBtn"><i class="material-icons left">save</i>Save</a>
            </div>
        </div>
        <!--Modal add existing ends here-->
</body>
</html>