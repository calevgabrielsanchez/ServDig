var urlWizardSociosB		= context_path + '/wizard/tramite/bajaSocios/';
var identificadorForma	= 'socio';

var dialogoConfirmarCancelar;
var dialogoConfirmarCommon;

$(document).ready(function(){
	$('#btnInciaTramite').click(function(){
		inicarTramite();
	});
		
	$('#btnInicioCancelarTramite').click(function(){
		cerrarWizard();
	});
	
	$('#btnRetomarTramite').click(function(){
		retomarTramite();
	});
	
	$('#btnCancelarTramite').click(function(){
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				cancelarTramite();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });
	
	dialogoConfirmarCommon = $( "#dialog-confirm-common" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false
	 });

	setSizeWithinIframe(document, 900);
});

function inicarTramite() {
	var url = urlWizardSociosB+'generarBajaSocio';	
	$('form#'+identificadorForma).attr('action',url);
	$('form#'+identificadorForma).submit();
}


function retomarTramite() {
	var url = urlWizardSociosB+'solicitud/retomar';
	$('#solicitudForm').attr('action',url);
	$('#solicitudForm').submit();
}

function cancelarTramite() {
	var idSolicitudPendiente = $('#idSolicitudPendiente').val();
	var url = urlWizardSociosB+'cancelar/solicitud/'+ idSolicitudPendiente;
	
	inicializarDialogoCancelacion();
	
	$.postJSON(url, null, function(data) {
		mostrarDialogo(data.mensaje);
	}).error(function(data){
		mostrarDialogo(data.mensaje);
	});
}

function cerrarWizard() {	
	parent.WizardBajaSociosCtrl.cerrar();
}

function inicializarDialogoCancelacion() {
	dialogoConfirmarCommon.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
}

function mostrarDialogo (mensaje) {
	$('#mensajeDialogo').text(mensaje);
	dialogoConfirmarCommon.dialog('open');
}