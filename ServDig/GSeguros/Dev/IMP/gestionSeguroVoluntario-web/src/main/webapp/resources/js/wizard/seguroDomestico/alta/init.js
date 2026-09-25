$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/seguroDomestico/comunes/common.js');

$(document).ready(function() {
	$('#btnCancelarSolicitudAlta').click(function() {
		if(ventanilla && tieneSeguros)
            parent.WizardIVROVentanillaSeguroDomesticoCtrl.abrir();
		else
			closeWizard();
	});
	$('#btnIniciarSolicitudAlta').click(function() {
		$('#capturarDatosSolicitudAltaForm').submit();
	});
});