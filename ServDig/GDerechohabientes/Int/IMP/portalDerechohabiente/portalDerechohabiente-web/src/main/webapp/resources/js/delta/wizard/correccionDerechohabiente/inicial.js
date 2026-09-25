var dialogoConfirmarCancelar;
var dialogoConfirmar;
CONTEXT_PATH_APLICACION = '/${mvn.web.app.root}';

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
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				parent.WizardCorreccionDerechohabienteCtrl.cerrar();
		 	}
		 }
	 });
});

function iniciarTramite() {
	if ($('#formIniciaTramite').valid()){
		$('#formIniciaTramite').submit();
	}
}


function retomar() {
	var url = CONTEXT_PATH_APLICACION + '/wizard/correccion/retomar';
	
	$('#solicitudForm').attr('action',url);
	$('#solicitudForm').submit();
}


function cancelarSolicitud() {
	var idSolicitudPendiente = $('#idSolicitudPendiente').val();
	var url = CONTEXT_PATH_APLICACION + '/wizard/correccion/solicitud/cancelar';
	
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
	parent.WizardCorreccionDerechohabienteCtrl.cerrar();
}