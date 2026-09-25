/*
 * JS de control del Widget de Medios de Contacto Fiscales.
 */

$.getScript("/gestionIndividuo-consulta-web/static/resources/js/wizard/fisica/modificacion/medios/fiscales/modificacionMediosFiscalesWizard.js");


var mediosContactoFiscalesWidget = {
	editar : function() {
		
		var idPersona = $('#idPersonaWidgetCtrl').val();
		
		WizardModificacionMediosFiscalesCtrl.init('wizardDatosActualizacion', 1, idPersona);
		WizardModificacionMediosFiscalesCtrl.abrir();
	}
};

$("#editarMediosFiscales").live('click', function() {
	mediosContactoFiscalesWidget.editar();
});