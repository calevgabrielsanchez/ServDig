/*
 * JS de control del Widget de Medios de Contacto Particulares.
 */

$.getScript("/gestionIndividuo-consulta-web/static/resources/js/wizard/fisica/modificacion/medios/particulares/modificacionMediosParticularesWizard.js");


var mediosContactoParticularesWidget = {
	editar : function() {
		
		var idPersona = $('#idPersona').val();
		
		WizardModificacionMediosParticularesCtrl.init('wizardDatosActualizacion', 1, idPersona);
		WizardModificacionMediosParticularesCtrl.abrir();
	}
};

$("#editarMediosParticulares").live('click', function() {
	mediosContactoParticularesWidget.editar();
});