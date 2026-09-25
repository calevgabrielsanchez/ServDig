

/**
 * JS para el control de la funcionalidad de la vista de personas encontradas
 * en el Instituto.
 */

var dgPersonas;
var dtPersona;
$(document).ready(function() {
	//Configuracion del dialogo de las personas
	dgPersonas = $('#dgPersonas').dialog({
		autoOpen : false,
		show : "blind",
		width : 1100,
		modal : true
	});

	//Configuracion del datable
	dtPersona = $('#personasFisicasFoundIMSSTable').dataTable({
		bFilter : false,
		bInfo : false,
		bSort : false,
		"bPaginate" : false,
		"bAutoWidth" : false
	});
	
});

function verRegistros() {
	
	dgPersonas.dialog('open');
}
