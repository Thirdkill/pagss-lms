$(function() {
	initFullCalendar();
});
function initFullCalendar() {
	$("#trainingCalendar").fullCalendar({
		header: {
			left: "prev today",
			center: "title",
			right:"next"
		},
		defaultDate: moment(),
	    editable: true,
	    eventLimit: true,
	});
}