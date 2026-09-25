
$.getScript("/delta-gestionPatronal-web/static/resources/js/delta/wizard/representanteLegal/representante/baja/bajaRepresentanteLegalWizard.js");

var representanteLegalPortlet = {
	baja: function() {
		var idPersonaFM = AtributosPersonaCtrl.personaPortal.idFiscalPersona;
		var tipoPersona = AtributosPersonaCtrl.personaPortal.idTipoPersona;
		var rfcPersona = AtributosPersonaCtrl.personaPortal.rfc;
		var idPersona = AtributosPersonaCtrl.personaPortal.idPersona;
		
		
		if($.trim(rfcPersona).length == 0) {
			mensajeError('Es necesario contar con RFC para realizar este tr&aacute;mite');
		}else{
			WizardBajaRepresentateLegalCtrl.init('wizardRegistroRepresentado', idPersona, idPersonaFM, 
				tipoPersona, rfcPersona.split('#').join('\u00d1'));
			WizardBajaRepresentateLegalCtrl.abrir();			
		}
				
	}
};

$("#bajaRepresentante").live('click', function() {
	representanteLegalPortlet.baja();
});

function parseIndicador( o ) {
	if (o == '1' || o == 1){
		return '<i class="glyphicon glyphicon-ok"></i>';		
		
	}else{
		return '<i class="glyphicon glyphicon-remove"></i>';		
	}
};

function mensajeError(msg) {

	$decision = $('<div title="MensajeRL"></div');

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
			$.post("/portal-web/utility/menu/opciones/1/2",null,function(data) {
				$("#opcionesRepresentante").html(data);
			});
		}
);