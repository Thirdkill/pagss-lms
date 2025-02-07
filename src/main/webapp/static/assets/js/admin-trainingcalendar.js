$(function() {
	initCalendar();
});

function initCalendar() {
	$("#calendar").fullCalendar({
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