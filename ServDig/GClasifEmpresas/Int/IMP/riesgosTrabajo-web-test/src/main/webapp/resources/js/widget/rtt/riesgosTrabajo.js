
$.getScript("/${mvn.web.app.root}/static/resources/js/wizard/rtt/wizardRttCtrl.js");

$("#entrarRiesgoTrabajo").live('click', function(event) { 
	event.preventDefault();
	var datos = {contenedor: 'riesgoTrabajoDiv',
		rp: parent.AtributosPersonaCtrl.personaPortal.registroPatronal,
		razonSocial: parent.AtributosPersonaCtrl.personaPortal.nombreRazonSocial,
	    rfc: parent.AtributosPersonaCtrl.personaPortal.rfc};
	WizardRttCtrl.init(datos);
	WizardRttCtrl.open();
});
