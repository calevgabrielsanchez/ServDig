var dialogoConfirmarCancelar;
var dialogoConfirmar;
var dialogoConfirmarSalida;
var idOrigenSolicitud = '${mvn.web.app.origin.id}';

$(document).ready(function(){
	$('#btnInciaTramite').click(function(){
		iniciarTramite();
	});
	
	$('#btnRetomarTramite').click(function(){
		retomar();
	});
	
	$('#btnCancelarTramite').click(function(){
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	$('#btnInicioCancelarTramite').click(function(){
		cancelar();
	});
	
	inicializarDialogos();
});

function inicializarDialogos() {
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
					parent.WizardRegistroDerechohabienteCtrl.cerrar();
				}
			}
		});
	}
	

	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				parent.WizardProrrogaDerechohabienteCtrl.cerrar();
		 	}
		 }
	 });
}

function iniciarTramite() {

	if(idOrigenSolicitud == 2) {
		$('#formIniciaTramite').submit();	
	} else if(idOrigenSolicitud == 6){
		inicioCiudadano();
	}

}

function inicioCiudadano() {
	
	FORMULARIO_PRORROGA = $("#prorrogaDerechohabiente");
	var oForm = FORMULARIO_PRORROGA.toObject();
	var url = '/${mvn.web.app.root}'+'/wizard/prorroga/validaInicioCiudadano';
	fnHideErrores("form#prorrogaDerechohabiente");
	
	$.blockUI();
	$.postJSON(url, oForm, function(data2) {
		FORMULARIO_PRORROGA.attr("action",'/${mvn.web.app.root}/wizard/prorroga/iniciarTramiteCiudadano');
		FORMULARIO_PRORROGA.submit();
	}).error(function(data){
		$.unblockUI();
		fnProcesarErrores(data, "form#prorrogaDerechohabiente");
	});
}

function retomar() {
	var url = '/${mvn.web.app.root}'+'/wizard/prorroga/retomar';
	
	$('#solicitudForm').attr('action',url);
	$('#solicitudForm').submit();
}


function cancelarSolicitud() {
	var idSolicitudPendiente = $('#idSolicitudPendiente').val();
	var url = '/${mvn.web.app.root}'+'/wizard/prorroga/solicitud/cancelar';
	
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

function cancelar() {
	parent.WizardProrrogaDerechohabienteCtrl.cerrar();
}