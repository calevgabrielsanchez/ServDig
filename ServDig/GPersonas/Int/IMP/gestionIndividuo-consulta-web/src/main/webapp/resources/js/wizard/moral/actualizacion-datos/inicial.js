var urlWizardTramite	= context_path + '/wizard/tramite/actualizar/datos/';
var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function(){
	$('#btnInciaTramite').click(function(){
		iniciarTramiteCambioDatos();
	});
	
	$('#btnRetomarTramite').click(function(){
		retomarTramiteCambioDatos();
	});
	
	$('#btnCancelarTramite').click(function(){
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	$('#btnInicioCancelarTramite').click(function(){
		cancelarInicioTramtieCambioDatos();
	});
	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				$( this ).dialog( "close" );
				cancelarTramiteCambioDatos();
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
				cancelarInicioTramtieCambioDatos();
		 	}
		 }
	 });
});

function iniciarTramiteCambioDatos() {
	
	fnHideErrores('#formAuxDatosComp');
	
	// Se valida si se deben captura datos complementarios
	var capturaRFC = $('#rfcInputTmp').length > 0 ? true : false;
	
	// Se deben capturar los datos complementarios
	var msg = 'Campo requerido';
	var huboError = false;
		
	if (capturaRFC) {
		var rfc = $('#rfcInputTmp').val();
		if (rfc == null || rfc == "") {
			huboError = true;
			fnShowError('#rfcInputTmpError' , msg);
		} else {
			$('#rfc').val(rfc);
		}
		
		if (!huboError) {
			$('#icaDatosEntradaForm').submit();
		} 
		
	} else {
		$('#icaDatosEntradaForm').submit();
	}
}

function retomarTramiteCambioDatos() {
	var url = urlWizardTramite + 'moral/retomar/solicitud';
	
	$('#solicitudForm').attr('action',url);
	$('#solicitudForm').submit();
}

function cancelarTramiteCambioDatos() {
	var idSolicitudPendiente = $('#idSolicitudPendiente').val();
	var url = urlWizardTramite + 'cancelar/solicitud/' + idSolicitudPendiente;
	
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

function cancelarInicioTramtieCambioDatos() {
	parent.WizardActualizacionDatosCtrl.cerrar();
}
