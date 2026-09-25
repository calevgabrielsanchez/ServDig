/**
 * 
 */

$.getScript("/delta-gestionPatronal-web/static/resources/js/delta/wizard/representanteLegal/representado/registro/registroRepresentadoLegalWizard.js");
$.getScript("/delta-gestionPatronal-web/static/resources/js/delta/wizard/representanteLegal/representado/baja/bajaRepresentadoLegalWizard.js");

var representadoLegalPortlet = {
	registrar : function() {		
		var rfcPersona = AtributosPersonaCtrl.personaPortal.rfc;
		var curpPersona = AtributosPersonaCtrl.personaPortal.curp;
		var idPersona = AtributosPersonaCtrl.personaPortal.idPersona;
				
		if($.trim(rfcPersona).length == 0) {
			mensajeError('Es necesario contar con RFC para realizar este tr&aacute;mite');
		} else {
			if($.trim(curpPersona).length == 0) {
				mensajeError('Es necesario contar con CURP para realizar este tr&aacute;mite');
			} else {				
				WizardRegistroRepresentadoLegalCtrl.init('wizardRegistroRepresentado', 
					idPersona, curpPersona, rfcPersona);
				WizardRegistroRepresentadoLegalCtrl.abrir();
			}
		}
	},
	baja: function() {
		var rfcPersona = AtributosPersonaCtrl.personaPortal.rfc;
		var idPersona = AtributosPersonaCtrl.personaPortal.idPersona;
				
		if($.trim(rfcPersona).length == 0) {
			mensajeError('Es necesario contar con RFC para realizar este tr&aacute;mite');
		} else {
			WizardBajaRepresentadoLegalCtrl.init('wizardRegistroRepresentado', 
				idPersona,1, rfcPersona);
			WizardBajaRepresentadoLegalCtrl.abrir();
		}
	}
};

$("#registroEmpresaRepresentada").live('click', function() {
	representadoLegalPortlet.registrar();
	
});

$("#registrarRPL").live('click', function() {
	representadoLegalPortlet.registrar();
});

$("#bajaRPL").live('click', function() {
	representadoLegalPortlet.baja();
});

function mensajeError(msg) {

	$decision = $('<div title="Mensaje"></div');

	$decision.dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		dialogClass: "no-close",
	    closeOnEscape: false,
	    buttons: {
		 	"ACEPTAR": function() {
		 		$( this ).dialog( "close" );
		 		$( this ).dialog( "destroy" );
		 	}
		 }
	});

	$decision.html(msg);
	$decision.dialog('open');
	
}

$(document).ready(
		function() {
			$.post("/portal-web/utility/menu/opciones/1/3",null,function(data) {
				$("#opcionesRepresentado").html(data);
			});
		}
	);

