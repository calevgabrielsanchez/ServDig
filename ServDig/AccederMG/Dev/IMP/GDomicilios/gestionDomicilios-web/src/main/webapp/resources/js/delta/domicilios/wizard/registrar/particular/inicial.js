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
			"ACEPTAR": function() {
				$( this ).dialog( "close" );
				cancelarTramite();
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
				parent.WizardRegistrarDomicilioParticularCtrl.cerrar();
		 	}
		 }
	 });
});

function inicarTramite() {
	$('#mdmForm').submit();	
}

function retomarTramite() {
	var url = '/${mvn.web.app.root}'+'/wizard/tramite/registrar/domicilio/particular/retomar/solicitud';
	
	$('#solicitudForm').attr('action',url);
	$('#solicitudForm').submit();
}

function cancelarTramite() {
	var idSolicitudPendiente = $('#idSolicitudPendiente').val();
	var url = '/${mvn.web.app.root}'+'/wizard/tramite/registrar/domicilio/particular/cancelar/solicitud/'
				+ idSolicitudPendiente;
	
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

function cancelarInicioTramite() {
	parent.WizardRegistrarDomicilioParticularCtrl.cerrar();
}