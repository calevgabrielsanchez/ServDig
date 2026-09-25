/*
 * JS de control del Widget de Asignación de NSS.
 */

$.getScript("/gestionAsegurados-web-externo/static/resources/js/delta/wizard/asignacionNSS/asignacionNSSWizard.js");

var asignacionNSSWidget = {
	asignar : function() {
		var idPersona = $('#idPersonaWidgetCtrl').val();
		var curp = $('#curpPersonaCtrl').val();
		
		if (curp == null || curp == '' || typeof curp === 'undefined'){
			curp = 'SIN_CURP';
		}
		
		WizardAsignacionNSSCtrl.init('wizardDatosActualizacion', idPersona, curp);
		WizardAsignacionNSSCtrl.asignarNSS();

	}
};

$("#initAsignarNSS").live('click', function() {
	asignacionNSSWidget.asignar();
});