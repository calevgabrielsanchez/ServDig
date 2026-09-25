/*
 * JS de control del Widget de Comprobante Fiscal.
 */
$.getScript("/gestionCobranza-web/static/resources/js/wizard/comprobanteFiscal/comprobanteFiscalWizardRFC.js");

var comprobanteFiscalWidget = {
		
		obtener : function() {
			var rfc = $("#rfc").val();
			WizardComprobanteFiscalCtrlRFC.init('wizardObtencionComprobanteFiscal', rfc);
			WizardComprobanteFiscalCtrlRFC.abrir();
		}
	};


$("#obtenerComprobanteFiscalPorRFC").live('click', function() {
	var rfc = [parent.AtributosPersonaCtrl.personaPortal.rfc];
	//console.log(' OBTENER CFDI POR RFC: '+rfc);
	comprobanteFiscalWidget.obtener();
	
});

$(document).ready(function() {
		
		$.post("/portal-web/utility/menu/opciones/2/9", null, function(data) {
			$("#opcionesWidgetComprobanteFiscalRFC").html(data);
		});
	
});

function mensajeError() {

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

	$decision.html('Es necesario contar con RFC para realizar este tr&aacute;mite');
	$decision.dialog('open');
	
}