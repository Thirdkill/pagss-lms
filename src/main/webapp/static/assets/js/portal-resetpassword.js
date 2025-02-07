/**
 * 
 */

$(function() {
	loginFailedCheck();
});

function loginFailedCheck() {
	if(GetURLParameter('errcode') == "notmatched"){
		alertify.alert('Reset Password Failed', 'Password does not match. Please try again.');
	}
}