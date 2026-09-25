var dialogoConfirmarCancelar;
var dialogoConfirmar;
var urlContacto 			= '/delta-gestionPatronal-web/wizard/tramite/centroTrabajo/medios';
var idFormaContacto			= 'soForm';

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
		height:'auto',
		modal: true,
		autoOpen: false,
		dialogClass: "no-close",
	    closeOnEscape: false
	 });
});

function inicarTramite() {	
	var url = urlContacto + '/crear/solicitud';
	$.blockUI();	
	$('form#'+idFormaContacto).attr('action',url);
	$('form#'+idFormaContacto).submit();
}

function retomarTramite() {
	var idSolicitudPendiente = $('#hdnIdSolicitud').val();
	var url = urlContacto + '/retomar/solicitud/' + idSolicitudPendiente;
	$.blockUI();
	$('form#'+idFormaContacto).attr('action',url);
	$('form#'+idFormaContacto).submit();
}

function cancelarTramite() {	
	var idSolicitudPendiente = $('#hdnIdSolicitud').val();
	var url = urlContacto + '/cancelar/solicitud/' + idSolicitudPendiente;
	
	$.blockUI();
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	$.postJSON(url, null, function(data) {
		mostrarDialogo(data.mensaje);
	}).error(function(data){
		mostrarDialogo(data.mensaje);
	}).done(function(data){
		$.unblockUI();
	});
}

function mostrarDialogo (mensaje) {
	$('#mensajeDialogo').text(mensaje);
	dialogoConfirmar.dialog('open');
}

function cerrarWizard() {	
	parent.WizardModificacionContactoCentroTrabajoCtrl.cerrar();
}

function cancelarInicioTramite() {
	parent.WizardModificacionContactoCentroTrabajoCtrl.cerrar();
}