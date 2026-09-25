/**
 * 
 */
$.getScript("/delta-gestionPatronal-web/static/resources/js/wizard/modificacion/patron/clasificacion/modificacionPatronClasificacionWizard.js");
$.getScript("/gestionBeneficio-web/static/resources/js/delta/wizard/riss/solicitarRifWizard.js");

var patronClasificacionPortlet = {
	editar : function() {
		var numeroRegistroPatronal = AtributosPersonaCtrl.personaPortal.registroPatronal;
		var tipoTramite = $('#hdnTipoActualizacionClasif').val();
		var esRPC = AtributosPersonaCtrl.personaPortal.isRPC;
		
		if(esRPC=='1'){
			notificarRPC();
			return;
		}
		
		WizardModificacionPatronClasificacionCtrl.init('wizardModificacionClasificacion', numeroRegistroPatronal,
				tipoTramite, "Modificaciones en el Seguro de Riesgo de Trabajo");
		WizardModificacionPatronClasificacionCtrl.abrir();
	},
	solicitarRIF: function () {
		var rfc 		= AtributosPersonaCtrl.personaPortal.rfc;
		var idPersona 	= AtributosPersonaCtrl.personaPortal.idPersona;
		
		WizardSolicitarRifCtrl.init('wizardModificacionClasificacion', rfc, idPersona);
		WizardSolicitarRifCtrl.abrir();
	}
};



$("#modificarClasificacionPatron").live('click', function() {
	patronClasificacionPortlet.editar();
});

$("#solicitarRif").live('click', function() {
	patronClasificacionPortlet.solicitarRIF();
});

$(document).ready(
	function() {
		var context_p = $("#context_p").val();
		$.post("/portal-web/utility/menu/opciones/1/4",null,function(data) {
			$("#opcionesClasificacion").html(data);
		});
	}
);
