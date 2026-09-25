var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function(){
	$('#btnInciaTramite').click(function(){
		inicarTramite();
	});
	
	$('#btnRetomarTramite').click(function(){
		retomarTramite();
	});
	
	$('#btnCancelarTramite').click(function(){
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	$('#btnInicioCancelarTramite').click(function(){
		cancelarInicioTramite();
	});

	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"SI": function() {
				$( this ).dialog( "close" );
				cancelarTramite();
		 	},
		 	"NO": function() {
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
				parent.WizardDummyCtrl.cerrar();
		 	}
		 }
	 });
});

function inicarTramite() {
//	var urlAction = '/delta-gestionPatronal-web/wizard/tramite/registro/patronal/crear/solicitud';
//
//	$.blockUI();
//
//	document.getElementById('personaForm').action = urlAction;
//	document.getElementById('personaForm').submit();
}

function retomarTramite() {
//	var idSolicitudPendiente = $('#hdnIdSolicitud').val();
//	var urlAction = '/delta-gestionPatronal-web/wizard/tramite/registro/patronal/retomar/solicitud/'
//			+ idSolicitudPendiente;
//
//	$.blockUI();
//	
//	document.getElementById('personaForm').action = urlAction;
//	document.getElementById('personaForm').submit();
}

function cancelarTramite() {
//	var idSolicitudPendiente = $('#hdnIdSolicitud').val();
//	var url = '/delta-gestionPatronal-web/wizard/tramite/registro/patronal/cancelar/solicitud/'
//				+ idSolicitudPendiente;
//	
//	$.blockUI();
//	
//	$.postJSON(url, null, function(data) {
//		$('#mensajeDialogo').text(data.mensaje);
//		dialogoConfirmar.dialog( "open" );
//	}).error(function(data){
//		$('#mensajeDialogo').text(data.mensaje);
//		dialogoConfirmar.open();
//	}).done(function(data){
//		$.unblockUI();
//	});
}

function cancelarInicioTramite() {
	parent.WizardDummyCtrl.cerrar();
}