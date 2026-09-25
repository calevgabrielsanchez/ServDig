var dialogTerminosCondiciones;

$(document).ready(function() {
	
	dialogTerminosCondiciones = $('#divTC').dialog({
		title: 'T&eacute;rminos y Condiciones',
		appendTo: parent.document,
		resizable: false,
		height: 550,
		width: 940,
		modal: true,
		autoOpen: false,
	    closeOnEscape: false
	 });
	
	$('#cerrarTC').click(function() {
		dialogTerminosCondiciones.dialog('close');
	});
	
    $('#linkTC').click(function() {
    	dialogTerminosCondiciones.dialog('open');
    });
	


});