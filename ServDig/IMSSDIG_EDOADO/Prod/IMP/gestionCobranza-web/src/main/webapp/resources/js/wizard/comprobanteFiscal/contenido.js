
$(document).ready(function() {
	$('#cerrarWizard').click(function() {
		cerrarWizard();
	});
});

function cerrarWizard() {	
	parent.WizardComprobanteFiscalCtrl.cerrar();
}
