

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
		width : 1100,
		title : 'Listado de personas localizadas en el Instituto',
		modal : true,
		position : {
			my : "top",
			at : "top",
			of : window,
			offset : "0 10"
		}
	});

	dgPersonas.dialog({
		close : function(event, ui) {
			var urlAction = context_path + '/tramite/consultaDatosBasicos';
			document.getElementById('registroAseguradoDatosBasicosForm').action = urlAction;
		}
	});

	//Configuracion del datable
	dtPersona = $('#personasFisicasFoundIMSSTable').dataTable({
		bFilter : false,
		bInfo : false,
		bSort : false,
		"bPaginate" : false,
		"bAutoWidth" : false
	});
	
	
	//Configuracion del boton de seleccionar NSS
	
	
	$("button#btnSeleccionarPersonaRegistrada").click(function(event){
		
		var datosPersona = $(this).attr("idPersona");
		var tmpDatos = datosPersona.split('|');
		var numNSS;
		var idPersona = tmpDatos[0];
		$("input#hiddenIdPersona").val(idPersona);
		if (tmpDatos[1] != ""){
			numNSS = tmpDatos[1];
			$("input#hiddenNSSPersona").val(numNSS);
		}
		
		$("form#formaComplementarSeleccion").submit();
		
	});
	

	$("button#btnRegistrarPersonaNueva").click(function(event) {
		var urlAction = context_path + '/tramite/complementar/personaNueva';

		document.getElementById('registroAseguradoDatosBasicosForm').action = urlAction;
		document.getElementById('registroAseguradoDatosBasicosForm').submit();
	});
	
});

function verRegistros() {
	
	dgPersonas.dialog('open');
}
