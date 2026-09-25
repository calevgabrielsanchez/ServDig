/*
 * JS de control del Widget de Comprobante Fiscal.
 */
$.getScript("/gestionCobranza-web/static/resources/js/wizard/comprobanteFiscal/comprobanteFiscalWizard.js");

var comprobanteFiscalWidget = {
	obtener : function() {
		var nrp = $("#nrp").val();
		var rfc = $("#rfc").val();

		WizardComprobanteFiscalCtrl.init('wizardObtencionComprobanteFiscal', nrp, rfc);
		WizardComprobanteFiscalCtrl.abrir();
	}
};

$("#obtenerComprobanteFiscal").live('click', function() {
	comprobanteFiscalWidget.obtener();
});

$(document).ready(function() {
	$.post("/portal-web/utility/menu/opciones/2/7", null, function(data) {
		$("#opcionesWidgetComprobanteFiscal").html(data);
	});
});
