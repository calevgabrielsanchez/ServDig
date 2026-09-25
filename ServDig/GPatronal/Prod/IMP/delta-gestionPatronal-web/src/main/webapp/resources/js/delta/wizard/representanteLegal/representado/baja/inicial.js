var urlWizardBajaRepresentado 	= context_path + '/wizard/tramite/representado/baja/';
var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function(){
	
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	$('#btnInciaTramite').click(function(){
		iniciarTramiteBajaRepresentadoLegal();
	});
	
	$('#btnRetomarTramite').click(function(){
		retomarBajaRepresentadoLegal();
	});
	
	$('#btnCancelarTramite').click(function(){
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	$('#btnInicioCancelarTramite').click(function(){
		cancelarInicioTramiteBajaRepLegal();
	});
	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				$( this ).dialog( "close" );
				cancelarBajaRepresentadoLegal();
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
				cancelarInicioTramiteBajaRepLegal();
		 	}
		 }
	 });
});

function iniciarTramiteBajaRepresentadoLegal() {
	$('#formIniciaTramite').submit();	
}


function retomarBajaRepresentadoLegal() {
	var url = urlWizardBajaRepresentado + 'solicitud/retomar';
	
	$('#solicitudForm').attr('action',url);
	$('#solicitudForm').submit();
}


function cancelarBajaRepresentadoLegal() {
	var idSolicitudPendiente = $('#idSolicitudPendiente').val();
	var url = urlWizardBajaRepresentado + 'solicitud/cancelar/'+idSolicitudPendiente;

	$.blockUI();
	
	$.postJSON(url, null, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	}).done(function(data){
		$.unblockUI();
	});

}

function cancelarInicioTramiteBajaRepLegal() {
	parent.WizardBajaRepresentadoLegalCtrl.cerrar();
}