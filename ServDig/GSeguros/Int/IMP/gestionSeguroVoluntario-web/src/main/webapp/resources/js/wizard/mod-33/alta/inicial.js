$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/mod-33/comunes/common.js');

$(document).ready(function() {
	$('#btnCancelarSolicitudAlta').click(function() {
		if(ventanilla && tieneSeguros)
			$('#formBackToVentanillaMain').submit();
		else
			closeWizard();
	});
	$('#btnIniciarSolicitudAlta').click(function() {
		$('#capturarDatosSolicitudAltaForm').submit();
	});
});