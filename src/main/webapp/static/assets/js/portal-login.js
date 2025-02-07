/**
 * 
 */

$(function() {
	loginFailedCheck();
});

function loginFailedCheck() {
	if(GetURLParameter("logincode") == "incorrect"){
		alertify.alert("Login Failed", "Login Credentials are incorrect. Please try again.");
	}
}