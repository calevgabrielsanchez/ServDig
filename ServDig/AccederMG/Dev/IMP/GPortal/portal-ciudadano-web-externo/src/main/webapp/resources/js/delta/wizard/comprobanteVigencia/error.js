var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function() {
	$('#cerrarWizardVigencia').click(function() {
		cerrarWizardComprobante();
	});
});

function cerrarWizardComprobante() {	
	parent.WizardComprobanteVigenciaCtrl.cerrar();
}