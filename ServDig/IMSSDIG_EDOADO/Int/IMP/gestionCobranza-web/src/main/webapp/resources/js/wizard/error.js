var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function() {
	$('#cerrarWizard').click(function() {
		cerrarWizard();
	});
});

function cerrarWizard() {	
	parent.WizardImpresionReportesCobranzaCtrl.cerrar();
}