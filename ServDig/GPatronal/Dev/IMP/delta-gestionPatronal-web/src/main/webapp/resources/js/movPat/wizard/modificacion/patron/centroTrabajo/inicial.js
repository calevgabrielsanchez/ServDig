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

		 	"No": function() {
		 		$( this ).dialog( "close" );
		 	},
			"Si": function() {
				$( this ).dialog( "close" );
				cancelarTramite();
		 	}
		 }
	 });
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		autoOpen: false,
		buttons: {
			"Aceptar": function() {
				parent.WizardModificacionPatronClasificacionCtrl.limpiarElementosSesion();
				parent.WizardModificacionPatronClasificacionCtrl.abrir();
		 	}
		 }
	 });
});

function inicarTramite() {
	var numeroRegistroPatronal = parent.WizardModificacionPatronClasificacionCtrl.config.numeroRegistroPatronal;
	var idTipoTramite = $("#hdnIdTramiteActualizacionCT").val();
	var urlAction = '/delta-gestionPatronal-web/wizard/tramite/modificar/movpat/clasificacion/generarSolicitud/' + idTipoTramite;

	$("#modificacionCentroTrabajoForm #hdnClasifNumeroRegistroPatronal").val(numeroRegistroPatronal);
	$.blockUI();

	document.getElementById('modificacionCentroTrabajoForm').action = urlAction;
	document.getElementById('modificacionCentroTrabajoForm').submit();
}

function retomarTramite() {
	var numeroRegistroPatronal = parent.WizardModificacionPatronClasificacionCtrl.config.numeroRegistroPatronal;
	var idTipoTramite = $('#hdnIdTipoTramite').val();
	var idSolicitudPendiente = $('#hdnIdSolicitud').val();

	$("#modificacionCentroTrabajoForm #hdnClasifNumeroRegistroPatronal").val(numeroRegistroPatronal);
	var urlAction = '/delta-gestionPatronal-web/wizard/tramite/modificar/movpat/clasificacion/retomar/solicitud/'
			+ idTipoTramite + '/' + idSolicitudPendiente;

	$.blockUI();
	
	document.getElementById('modificacionCentroTrabajoForm').action = urlAction;
	document.getElementById('modificacionCentroTrabajoForm').submit();
}

function cancelarTramite() {
	var idSolicitudPendiente = $('#hdnIdSolicitud').val();
	var url = '/delta-gestionPatronal-web/wizard/tramite/modificar/patron/clasificacion/cancelar/solicitud/'
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
	parent.WizardModificacionPatronClasificacionCtrl.cerrar();
}