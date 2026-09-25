var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(function() {
	$('#btnInciaTramite').click(function() {
		iniciarTramite();
	});

	$('#btnInicioCancelarTramite').click(function() {
		cancelarInicioTramite();
	});

});

function iniciarTramite() {
	$('#nssEstudiantesForm').submit();
}

function cancelarInicioTramite() {
	parent.WizardAsignacionNSSEstudiantesCtrl.cerrar();
}
