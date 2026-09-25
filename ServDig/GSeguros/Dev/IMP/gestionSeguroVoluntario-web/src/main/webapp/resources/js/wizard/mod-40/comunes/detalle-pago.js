$(document).ready(function() {
	$('#cerrarWizard').click(function() {
		parent.WizardDetalleSeguroCtrl.cerrar();
	});


	$('#regresar').click(function(e){
		e.preventDefault();
		parent.WizardDetalleSeguroCtrl.abrir();
	});
	
});
