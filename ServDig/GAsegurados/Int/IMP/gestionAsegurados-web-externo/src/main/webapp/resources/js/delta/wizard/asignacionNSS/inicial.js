var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function(){
	$('#btnInciaTramite').click(function(){
		iniciarTramiteCambioDatos();
	});
	
	$('#btnInicioCancelarTramite').click(function(){
		cancelarInicioTramtieCambioDatos();
	});
	
});

function iniciarTramiteCambioDatos() {
	$('#initCapturaNSSForm').submit();
}

function cancelarInicioTramtieCambioDatos() {
	parent.WizardAsignacionNSSCtrl.cerrar();
}
