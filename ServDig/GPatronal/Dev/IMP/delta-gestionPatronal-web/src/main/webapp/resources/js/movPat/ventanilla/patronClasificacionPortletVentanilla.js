/**
 * 
 */
$.getScript("/delta-gestionPatronal-web/static/resources/js/wizard/modificacion/patron/clasificacion/modificacionPatronClasificacionWizard.js");
$.getScript("/gestionBeneficio-web/static/resources/js/delta/wizard/riss/solicitarRifWizard.js");

var patronClasificacionPortlet = {
	editar : function() {
		//var numeroRegistroPatronal = AtributosPersonaCtrl.personaPortal.registroPatronal;
		var numeroRegistroPatronal = '84018548101';
		//var tipoTramite = $('#hdnTipoActualizacionClasif').val();
		var tipoTramite = 11;
		//var esRPC = AtributosPersonaCtrl.personaPortal.isRPC;
		var esRPC = '0';
		
		if(esRPC=='1'){
			notificarRPC();
			return;
		}
		
		WizardModificacionPatronClasificacionCtrl.init('wizardModificacionClasificacion', numeroRegistroPatronal,
				tipoTramite, "Modificaciones en el Seguro de Riesgo de Trabajo");
		WizardModificacionPatronClasificacionCtrl.abrir();
	}
};



/*$("#modificarClasificacionPatron").live('click', function() {
	patronClasificacionPortlet.editar();
});*/

$("#btnTestWidget").live('click', function() {
	patronClasificacionPortlet.editar();
});
