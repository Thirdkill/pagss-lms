/**
 * 
 */

$(function(){
	initializeIPAddressTbl();
	initializeButtons();
	checkIPTxtbox();
	attachListenerToSearchBox();
});

function checkIPTxtbox(){
	$("#ipaddress").keyup(function() {
		if ($("#ipaddress").val().length != 0) {
			$("#newIpSubmit").removeAttr("disabled");
		} else if ($("#ipaddress").val().length == 0){
			$("#newIpSubmit").attr("disabled","disabled");
		}
	});

}

function initializeButtons(){
	$("#newIpSubmit").unbind("click").click(function(){
		var ip = {};
		ip.ipAddress = $("#ipaddress").val();
		if(ip.ipAddress.length != 0){
			$.when(ajax.create("ipaddresses",ip)).done(function(response) {
				if(response.status == HttpStatus.SUCCESS){
					initializeIPAddressTbl();
					clrFields();
					$("newIpSubmit").addClass("modal-close");
					alertify.success("Successfully Created.");
				}
			}); 
		} else {
			alertify.alert('Add IP Address Whitelist', 'Please enter an IP Address.', clrFields());
		} 
	});
	$("#clearSearhField").unbind("click").click(function(){
		$("#searchIPAddress").val("");
		initializeIPAddressTbl();
	});
}

function clrFields(){
	$("#ipaddress").val("");
	$("#newIpSubmit").attr("disabled","disabled");
}

function deleteAction(specid){
	var ip = {};
	ip.ipAddressId = $(specid).attr("data-id");
	ip.ipAddress = $(specid).attr("data-ip");
	alertify.confirm("Delete Confirmation","Do you really want to delete "+ip.ipAddress+" to your whitelist?",function() {deleteIpAddress(ip.ipAddressId);},null)
	.set("labels", {ok:"DELETE", cancel:"CANCEL"});
}

function deleteIpAddress(ip){
	$.when(ajax.remove("ipaddresses/",ip)).done(function(response) {
		if(response.status == HttpStatus.SUCCESS){
			initializeIPAddressTbl();
		}
	}); 
}

function initializeIPAddressTbl() {
	var pageSize = 10;
	$("#ipaddressPagination").pagination({     
		dataSource: "ipaddresses/pages",
		locator: 'ipaddresses',
		totalNumberLocator: function(response) {            
			return response.totalRecords;
		},
		className: 'paginationjs-theme-blue',
		pageSize: pageSize,     
		callback: function(data) {                      
			$("#ipaddressTblBody").empty(); 
			_.each(data, function(ipaddresses) {
				createIpAddressRow(ipaddresses);
			});         
		}
	});
}

function createIpAddressRow(ipaddresses) {
	$("#ipaddressTblBody").append($("<tr/>")
	.append($("<td/>").attr({"style" : "width:60px;","class":"deleteAction","data-id":ipaddresses.ipAddressId,"data-ip":ipaddresses.ipAddress,"onclick":"deleteAction(this);"})
	.append($("<a/>").addClass("waves-effect waves-light")
	.append($("<i/>").addClass("material-icons").html("delete"))))
	.append($("<td/>").html(ipaddresses.ipAddress))
	.append($("<td/>").html(ipaddresses.createdAt))
	.append($("<td/>").html(ipaddresses.modifiedBy)));
}

function checkStatus(status) {
	return (status != 0)? "ACTIVE" : "INACTIVE"; 
}

function attachListenerToSearchBox() {

	$("#searchIPAddress").on("keyup",function(e) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == 13) {
			var keyword = $(this).val();
			searchIpAddress(keyword);
		}
		
		if(keycode == 8 && $("#searchIPAddress").val().length == 0) {
			initializeIPAddressTbl();
		}
		return true;
	});
}

function searchIpAddress(keyword) {
	var pageSize = 10;
	$("#ipaddressPagination").pagination({     
		dataSource: "ipaddresses/search",
		locator: 'ipaddresses',
		totalNumberLocator: function(response) {            
			return response.totalRecords;
		},
		ajax: {data: {keyword : keyword}, type: "POST"},
		className: 'paginationjs-theme-blue',
		pageSize: pageSize,     
		callback: function(data) {                      
			$("#ipaddressTblBody").empty();            
			_.each(data, function(ipaddresses) {
				createIpAddressRow(ipaddresses);
			});         
		}
	});
}