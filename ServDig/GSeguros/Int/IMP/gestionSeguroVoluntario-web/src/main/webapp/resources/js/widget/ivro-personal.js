
$.getScript("/${mvn.web.app.root}/static/resources/js/wizard/individual/WizardSeguroIvroIndivCtrl.js");

$("#iniciaTramite").live('click', function(event) { 
	event.preventDefault();
	var datos = {contenedor: 'wizardModalidadIvroPersonalComponent',
		idPersona: AtributosPersonaCtrl.personaPortal.idPersona,
		rfc: AtributosPersonaCtrl.personaPortal.rfc};
	WizardSeguroIvroIndivCtrl.init(datos);
	WizardSeguroIvroIndivCtrl.abrir();
});
