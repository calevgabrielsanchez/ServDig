$(document).ready(function() {
	
	if ($('#btnCerrarWizard').length > 0) {
		$('#btnCerrarWizard').click(function(){
			parent.WizardAsignacionNSSEstudiantesCtrl.cerrar();
		});
	}
	
});
