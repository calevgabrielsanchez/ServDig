
//$.getScript("/delta-gestionPatronal-web-ventanilla-dev/static/resources/js/wizard/modificacion/patron/clasificacion/modificacionPatronClasificacionWizard.js");

var dialogoListaTramitesClasificacion;
var index=-1;
var oTableRFC;
var oTableTramites;
var dialogoError;

$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
});


function navegarADetalleDeRP(){
		patronClasificacionPortlet.editar();
		$("#numRegistroPatronal").val("")
}


var patronClasificacionPortlet = {
	editar : function() {
		var numeroRegistroPatronal = registroPatronal;
		var tipoTramite = 11;// Actualizacion de clasificacion
		/*var esRPC = AtributosPersonaCtrl.personaPortal.isRPC;
		
		if(esRPC=='1'){
			notificarRPC();
			return;
		}*/
		
		WizardModificacionPatronClasificacionCtrl.init('wizardModificacionClasificacionInternet', numeroRegistroPatronal,
				tipoTramite, "Modificaciones en el Seguro de Riesgo de Trabajo", context);
		WizardModificacionPatronClasificacionCtrl.abrir();
	},
	solicitarRIF: function () {
		var rfc 		= AtributosPersonaCtrl.personaPortal.rfc;
		var idPersona 	= AtributosPersonaCtrl.personaPortal.idPersona;
		
		WizardSolicitarRifCtrl.init('wizardModificacionClasificacionInternet', rfc, idPersona);
		WizardSolicitarRifCtrl.abrir();
	}
};



$("#modificarClasificacionPatron").live('click', function() {
	patronClasificacionPortlet.editar();
});

$("#btnCancelarRp").live('click', function() {
	$("#numRegistroPatronal").val("");
});

