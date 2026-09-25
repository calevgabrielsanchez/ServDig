/**
 * JS para la vista de la caputa libre de la persona 
 */

var objTipoSerie;

$(document).ready(function(){
	
	//Se deshabilita el formulario
	$('div#datosBasicosDiv').deshabilitarContenido(false);
	$('div#datosCurp').deshabilitarContenido(false);

	$('#regresar').click(function(){
		$('form#formIniciarTramite').submit();
	});
		
	$('button#btnInicioTramite').click(function(){
		$('form#formIniciarTramite').submit();
	});
	
	$('button#btnVerReporte').click(function(){
		$('form#formVerReporte').submit();
		$.unblockUI();
	});
});