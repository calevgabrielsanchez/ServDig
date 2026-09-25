/*
 * JS de control del Widget de beneficios
 */
$.getScript("/gestionBeneficio-web/static/resources/js/delta/wizard/riss/solicitarRifWizard.js");

var beneficiosWidget = {
	solicitarRiss : function() {
		var rfc 		= AtributosPersonaCtrl.personaPortal.rfc;
		var idPersona	= AtributosPersonaCtrl.personaPortal.idPersona;
		
		WizardSolicitarRifCtrl.init('wizardBeneficioRiss', rfc, idPersona);
		WizardSolicitarRifCtrl.abrir();
	}
};

$("#solicitarRif").live('click', function(e) {
	e.preventDefault();
	beneficiosWidget.solicitarRiss();
});