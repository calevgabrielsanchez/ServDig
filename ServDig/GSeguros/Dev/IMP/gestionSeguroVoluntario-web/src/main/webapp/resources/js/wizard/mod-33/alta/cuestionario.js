$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/mod-33/comunes/common.js');

$(document).ready(function() {
	var _cuestionario = $('#cuestionarioMedicoContainer').cuestionario({
		idCuestionario: 3,
		formId: 'nextStepForm',
		onSuccess: function () {
		}
	});
	
	$('#siguientePaso').click(function(e) {
		e.preventDefault();
		_cuestionario.cuestionario('validar');
	});
	
	$('#cerrar').click(function() {
                //En caso de renovacion regresa a lista de integrantes y elimina el ultimo de la lista en controler
		closeWizard();
	});
	
	$('#cerrarRenovaion').click(function() {
                //En caso de renovacion regresa a lista de integrantes y elimina el ultimo de la lista en controler
		$('form#quitarUltimoIntegranteForm').submit();
        closeWizard();
	});
		
});