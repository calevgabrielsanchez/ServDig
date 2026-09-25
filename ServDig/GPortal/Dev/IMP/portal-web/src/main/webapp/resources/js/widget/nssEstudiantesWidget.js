/*
 * JS de control del Widget de Asignación de NSS.
 */

$.getScript("/gestionAsegurados-web/static/resources/js/delta/asignacion/wizard/estudiantes/asignacionNSSEstudiantesWizard.js");

var asignacionNSSEstudiantesWidget = {
	asignar : function() {
		var nrp = AtributosPersonaCtrl.personaPortal.registroPatronal;				  
				
		WizardAsignacionNSSEstudiantesCtrl.init('asignacionNSSEstudiantesComponent', nrp);
		WizardAsignacionNSSEstudiantesCtrl.asignarNSS();
	}
};

$("#initAsignarNSS").live('click', function() {
	asignacionNSSEstudiantesWidget.asignar();
});