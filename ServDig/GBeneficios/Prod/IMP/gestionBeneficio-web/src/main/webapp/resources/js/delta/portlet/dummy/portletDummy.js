/*
 * JS de control del Widget dummy
 */
$.getScript("/gestionExpediente-web/static/resources/js/delta/wizard/dummy/wizardDummy.js");

var portletDummy = {
	ejecutarEjemplo : function() {
//		var idDummy = AtributosPersonaCtrl.personaPortal.idPersona;
		var idDummy = 1;
		
		WizardDummyCtrl.init('wizardContainer', idDummy);
		WizardDummyCtrl.abrir();
	}
};

$("#abrirWizardDummyPortlet").live('click', function(e) {
	e.preventDefault();
	portletDummy.ejecutarEjemplo();
});