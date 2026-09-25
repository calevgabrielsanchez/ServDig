/*
 * JS de control del Widget dummy
 */
$.getScript("/${mvn.web.app.root}/static/resources/js/delta/wizard/dummy/wizardDummy.js");

var widgetDummy = {
	ejecutarEjemplo : function() {
//		var idDummy = AtributosPersonaCtrl.personaPortal.idPersona;
		var idDummy = 1;
		
		WizardDummyCtrl.init('wizardContainer', idDummy);
		WizardDummyCtrl.abrir();
	}
};

$("#abrirWizardDummyWidget").live('click', function(e) {
	e.preventDefault();
	widgetDummy.ejecutarEjemplo();
});
