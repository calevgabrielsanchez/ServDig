$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/seguroDomestico/comunes/common.js');

$(document).ready(function() {
	$('#listCentroTrabajo').selectable();
	
	$('#agregarDomicilio').click(function() {
		$('#agregarCentroTrabajoForm').submit();
	});

	$('#cancelarTramite').click(function() {
		if(ventanilla && tieneSeguros)
            parent.WizardIVROVentanillaSeguroDomesticoCtrl.abrir();
		else
			closeWizard();
	});
	$('#siguientePaso').click(function(event) {
		event.preventDefault();
		$('#validacion').hide();
		var idCentroTrabajo = $('.ui-selected').attr('idCentroTrabajo');
		var numeroRegistroPatronal = $('.ui-selected').attr('numeroRegistroPatronal');
		var idModalidad = $('.ui-selected').attr('idModalidad');
		var numModalidad = $('.ui-selected').attr('numModalidad');
		var digitoVerificador = $('.ui-selected').attr('digitoVerificador');
		var claveAsentamiento = $('.ui-selected').attr('claveAsentamiento');
		var claveLocalidad = $('.ui-selected').attr('claveLocalidad');
		var claveMunicipio = $('.ui-selected').attr('claveMunicipio');
		var claveEntidadFederativa = $('.ui-selected').attr('claveEntidadFederativa');
		if(idCentroTrabajo !== undefined) {
			$('#inputIdCentroTrabajo').val(idCentroTrabajo);
			$('#inputNumeroRegistroPatronal').val(numeroRegistroPatronal);
			$('#inputIdModalidad').val(idModalidad);
			$('#inputNumModalidad').val(numModalidad);
			$('#inputDigitoVerificador').val(digitoVerificador);
			$('#inputClaveAsentamiento').val(claveAsentamiento);
			$('#inputClaveLocalidad').val(claveLocalidad);
			$('#inputClaveMunicipio').val(claveMunicipio);
			$('#inputClaveEntidadFederativa').val(claveEntidadFederativa);
			$('#nextStepForm').submit();
		}
		else {
			$('#validacion').show();
			setSizeWithinIframe(document);
		}
	});
});