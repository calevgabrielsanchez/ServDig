$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/seguroDomestico/comunes/common.js');

$(document).ready(function() {
	$('#btnCancelarSolicitudRenovacion').click(function() {
		if(ventanilla)
            parent.WizardIVROVentanillaSeguroDomesticoCtrl.abrir();
		else
			closeWizard();
	});
	$('#btnIniciarSolicitudRenovacion').click(function() {
		$('#capturarDatosSolicitudRenovacionForm').submit();
	});
});