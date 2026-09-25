var urlWizardRegRep				= context_path + '/wizard/tramite/representado/registro/';
var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function(){
	$('#btnInciaTramite').click(function(){
		iniciarTramiteRegistroRepresentanteLegal();
	});
	
	$('#btnRetomarTramite').click(function(){
		retomarRegistroRepresentado();
	});
	
	$('#btnCancelarTramite').click(function(){
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	$('#btnInicioCancelarTramite').click(function(){
		cancelarInicioTramtieRegistroRepresentante();
	});
	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				$( this ).dialog( "close" );
				cancelarRegistroRepresentado();
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
				cancelarInicioTramtieRegistroRepresentante();
		 	}
		 }
	 });
});

function iniciarTramiteRegistroRepresentanteLegal() {
	$('#icaDatosEntradaForm').submit();	
}


function retomarRegistroRepresentado() {
	var url = urlWizardRegRep + 'retomar/solicitud';
	
	$('#solicitudForm').attr('action',url);
	$('#solicitudForm').submit();
}


function cancelarRegistroRepresentado() {
	var idSolicitudPendiente = $('#idSolicitudPendiente').val();
	var url = urlWizardRegRep + 'cancelar/solicitud/'+idSolicitudPendiente;
	
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

function cancelarInicioTramtieRegistroRepresentante() {
	parent.WizardRegistroRepresentadoLegalCtrl.cerrar();
}