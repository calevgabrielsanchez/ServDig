var dialogoConfirmarCancelar;
var dialogoConfirmar;
var dialogoConfirmarSalida;
var index=-1;

$(document).ready(function(){
	
	$('#btnObtenerReporte').click(function(){
		obtenerReporte();
	});

	$('#btnInicioCancelarTramite').click(function(){
		cancelar();
	});
	
	
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		autoOpen: false,
		buttons: {
			"Aceptar": function() {
				parent.WizardComprobanteVigenciaCtrl.cerrar();
		 	}
		 }
	 });
	
	verificarErroresRegreso();
});

function obtenerReporte() {
	$('#asignacionNSS').submit();	
}

function cancelar() {
	parent.WizardComprobanteVigenciaCtrl.cerrar();
}

function verificarErroresRegreso() {
	
	if($("#asignacionNSS").length) {
		marcarCamposConErroresFromSpanErrors('asignacionNSS',".error");
	}
}

/**
 * Metodo para cerrar un dialogo
 * @param $dialogo - dialogo que se cerrara
 */
function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}