/**
 * JS para la vista de la caputa libre de la persona 
 */

var objTipoSerie;

$(document).ready(function(){

	//Se deshabilita el formulario
	$('div#datosBasicosDiv').deshabilitarContenido(false);
	$('div#datosCurp').deshabilitarContenido(false);

	$('#regresar').click(function(){
		var url = context_path + "/tramite/iniciar";
		var form = $('form#registroPersonaFisicaForm').attr('action' , url);
		form.attr('method' , 'get');
		form.submit();
	});
	
	$('button#btnImprimirNSSQR').click(function(){
		var url = context_path + "/reporte/imprimeNSS";
		var form = $('form#formImprimeDocto').attr('action' , url);
		form.submit();
		$.unblockUI();
	});
	
	$('#registrar').click(function(){
		
		$('div#datosBasicosDiv').habilitarContenido(false);
		$('div#datosCurp').habilitarContenido(false);
		
		$('form#registroPersonaFisicaForm').submit();
	});
		
	$('button#btnInicioTramite').click(function(){
		$('form#formIniciarTramite').submit();
	});
	
});
