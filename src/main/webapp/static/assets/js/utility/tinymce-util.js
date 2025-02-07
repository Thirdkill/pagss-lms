$(function() {
	defineEditorProperties();
});

function defineEditorProperties() {
	Editor = {
		TOOLBAR: "bold italic underline strikethrough alignleft aligncenter alignright " +
			"alignjustify | styleselect formatselect | fontselect fontsizeselect forecolor backcolor " + 
			"|numlist bullist| wordcount |",
		PLUGINS: "lists table code paste help hr wordcount",
		MENUBAR: "format edit insert help custom"
	}
}

/**
 * Description: Initialize TinyMCE Editor with default toolbars 
 * @param dom
 * @returns
 */
function initTinyMceEditor(dom,height) {
	return tinymce.init({
		  selector: dom,
		  plugins: Editor.PLUGINS,
		  toolbar: Editor.TOOLBAR,
		  theme: 'silver',
		  menubar: Editor.MENUBAR,
		  paste_as_text: true,
		  height: height,
		  branding:false
	});
}

function initTinyMceEditorReadOnly(dom,height) {
	return tinymce.init({
		  selector: dom,
		  plugins: Editor.PLUGINS,
		  toolbar: Editor.TOOLBAR,
		  theme: 'silver',
		  menubar: Editor.MENUBAR,
		  paste_as_text: true,
		  height: height,
		  branding:false,
		  readonly: 1
	});
}

/**
 * Description: Initialize TinyMCE Editor with default and customized buttons
 * @param dom
 * @param additionalOptions = an object with a list of custom button's properties
 * @param callbackFunction
 * @returns
 */
function initCustomEditor(dom,additionalOptions,callbackFunction) {
	return tinymce.init({
		  selector: dom,
		  plugins: Editor.PLUGINS,
		  toolbar: Editor.TOOLBAR,
		  theme: "silver",
		  paste_as_text: true,
		  height: 500,
		  branding:false,
		  menubar: Editor.MENUBAR,
		  menu: additionalOptions,
		  setup: callbackFunction
	});
}

/**
 * Description: Set HTML content to the editor
 * @param dom
 * @param string
 * @returns
 */
function setContent(dom,string) {
	tinymce.get(dom).setContent(string);
}

/**
 * Description: Returns the TinyMCE Editor content as Text
 * @param dom
 * @param string
 * @returns
 */
function getContent(dom) {
	return tinymce.get(dom).getContent();
}

/**
 * Description: Clears Html inside the TinyMCE Editor
 * @param dom
 * @returns
 */
function clearContent(dom) {
	tinymce.get(dom).setContent("");
}