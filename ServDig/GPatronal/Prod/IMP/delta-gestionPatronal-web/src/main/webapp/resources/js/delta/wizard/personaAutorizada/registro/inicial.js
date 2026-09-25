var urlWizardRegistroPA				= context_path + '/wizard/tramite/personaAutorizada/';
var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function(){
	$('#btnInciaTramite').click(function(){
		iniciarTramiteRegistroPersonaAutorizada();
	});
	
	$('#btnRetomarTramite').click(function(){
		retomarRegistroPersonaAutorizada();
	});
	
	$('#btnCancelarTramite').click(function(){
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	$('#btnInicioCancelarTramite').click(function(){
		cerrarWizard();
	});
	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				$( this ).dialog( "close" );
				cancelarRegistroPersonaAutorizada();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				cerrarWizard();
		 	}
		 }
	 });
});

function iniciarTramiteRegistroPersonaAutorizada() {
	$('#formIniciaTramite').submit();	
}


function retomarRegistroPersonaAutorizada() {
	var url = urlWizardRegistroPA + '/registro/solicitud/retomar';
	
	$('#solicitudForm').attr('action',url);
	$('#solicitudForm').submit();
}


function cancelarRegistroPersonaAutorizada() {
	var idSolicitudPendiente = $('#idSolicitudPendiente').val();
	var url = urlWizardRegistroPA + '/registro/solicitud/cancelar';
	
	$.blockUI();
	
	$.postJSON(url, {solicitudId : idSolicitudPendiente}, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	}).done(function(data){
		$.unblockUI();
	});

}

function cerrarWizard() {
	parent.WizardRegistroPersonaAutorizadaCtrl.cerrar();
}