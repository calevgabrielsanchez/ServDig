/*
 * JS de control del Widget de Persona Fisica.
 */

$.getScript("/gestionIndividuo-consulta-web/static/resources/js/wizard/common/actualizacion-datos/actualizacionDatosPersonaWizard.js");

var personaFisicaDatosBasicosWidget = {
	editar : function() {
		
		var idPersonaSesion = $('#idPersonaSesionFM').val();
		var idPersona = $('#idPersona').val();
		var rfcPersona = $('#rfcPersona').val();
		var curpPersona = $('#curpPersona').val();
				
		if(isEmpty(idPersonaSesion))
			idPersonaSesion=0;
		
		// Se valida que se tenga el curp y rfc
		if (rfcPersona == null || rfcPersona == '') {
			rfcPersona = 'SIN_RFC';
		}
		
		if (curpPersona == null || curpPersona == '') {
			curpPersona = 'SIN_CURP';
		}
		
		WizardActualizacionDatosCtrl.init('wizardDatosActualizacion', idPersonaSesion, 1, idPersona,
				curpPersona, rfcPersona);
		WizardActualizacionDatosCtrl.abrir();
	}
};

$("#editar").live('click', function() {
	personaFisicaDatosBasicosWidget.editar();
});

function isEmpty(valor) {
	return (valor == undefined || valor == "");
}