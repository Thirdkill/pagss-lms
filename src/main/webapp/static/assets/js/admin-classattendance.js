/**
 * 
 */

$(function(){
	initClassInfo();
	defineVariables();
});

function defineVariables() {
	checkedLists = [];
}

function initClassInfo() {
	if($("#deliveryMethod").val() == DeliveryMethod.INDIVIDUAL){
		$("#deliveryMethodLabel").html("Individual");
	} else if($("#deliveryMethod").val() == DeliveryMethod.MODULAR){
		$("#deliveryMethodLabel").html("Modular");
	} else if($("#deliveryMethod").val() == DeliveryMethod.CLASSTRAINING){
		$("#deliveryMethodLabel").html("Class Training");
	}
}

