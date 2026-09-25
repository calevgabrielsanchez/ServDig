var dialogoConfirmarCancelar;
var dialogoConfirmar;
var dialogoConfirmarSalida;
var index=-1;

$(document).ready(function(){
	
	$('#btnObtenerReporte').click(function(){
		obtenerReporte();
	});

	if($("#dialog-confirm-cancelar").length > 0) {
		dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false,
			buttons: {
				"ACEPTAR": function() {
					$( this ).dialog( "close" );
					cancelarSolicitud();
				},
				"CANCELAR": function() {
					$( this ).dialog( "close" );
				}
			}
		});

		$('#btnIniciocancelarYContinuar').click(function(){
			dialogoConfirmarCancelar.dialog( "open" );
		});	
	}
	
	if($("#dialog-confirm-salir").length > 0) {
		dialogoConfirmarSalida = $( "#dialog-confirm-salir" ).dialog({
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false,
			buttons: {
				"CONTINUAR": function() {
					$( this ).dialog( "close" );
					parent.WizardComprobanteVigenciaCtrl.cerrar();
				}
			}
		});
	}

	$('#btnInicioCancelarTramite').click(function(){
		cancelar();
	});
	
	
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				parent.WizardRegistroDerechohabienteCtrl.cerrar();
		 	}
		 }
	 });
});

function obtenerReporte() {
	$('#asignacionNSS').submit();	
}

function cancelar() {
	parent.WizardComprobanteVigenciaCtrl.cerrar();
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