/*
 * JS de control del Widget de Persona Ivro Individual Seguro.
 */

var isReadOnly = $('div#idPersonaIvroIndivWidget').attr('is-readOnly'); 


	$.getScript("/gestionSeguroVoluntario-web/static/resources/js/wizard/persona/ivro/indiv/modalidad/check-modalidad-ivro-indiv.js");
	


var seguroIvroPersonalWidget = {
		checar : function() {
			var rfcPersona = $("#rfcPerson").val();
			var idPersona = AtributosPersonaCtrl.personaPortal.idPersona;
			WizardCheckModalidadIvroIndivCtrl.init('wizardModalidadIvroPersonalComponent', 1, idPersona, rfcPersona);
			WizardCheckModalidadIvroIndivCtrl.abrir();
		}
	};

$("#reviewModalidadIvroIndiv").live('click', function() {

	seguroIvroPersonalWidget.checar();
});