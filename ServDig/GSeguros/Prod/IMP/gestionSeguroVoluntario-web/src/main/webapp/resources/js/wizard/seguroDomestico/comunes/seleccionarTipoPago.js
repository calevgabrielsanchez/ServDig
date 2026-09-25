$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/seguroDomestico/comunes/common.js');

$(document).ready(function() {
	$('#listTipoPago').selectable();
	
	$('#cancelarTramite').click(function() {
		if(ventanilla && tieneSeguros)
            parent.WizardIVROVentanillaSeguroDomesticoCtrl.abrir();
		else
			closeWizard();
	});
	$('#siguientePaso').click(function(event) {
		event.preventDefault();
		$('#validacion').hide();
		var tipoPago = $('.ui-selected').attr('value');
		if(tipoPago !== undefined) {
			$('#inputTipoPago').val(tipoPago == '0' ? false : true);
			$('#nextStepForm').submit();
		}
		else {
			$('#validacion').show();
			setSizeWithinIframe(document);
		}
	});
});