/**
 * 
 */

/*
 * JS de control del Widget de Persona Fisica.
 */

$.getScript("/gestionCobranza-web/static/resources/js/wizard/impresionReporteCobranzaWizard.js");

var impresionReportesCobranzaWidget = {
	reporteSituacion : function() {
		
		var nrp = $("#nrp").val();
		
		WizardImpresionReportesCobranzaCtrl.init('wizardImpresionReportesCobranza',nrp,1);
		WizardImpresionReportesCobranzaCtrl.abrir();
	},
	reporteSituacionRCV: function() {
		var nrp = $("#nrp").val();
		
		WizardImpresionReportesCobranzaCtrl.init('wizardImpresionReportesCobranza',nrp,2);
		WizardImpresionReportesCobranzaCtrl.abrir();
	},
	reporteMotivo: function() {
		var nrp = $("#nrp").val();
		
		WizardImpresionReportesCobranzaCtrl.init('wizardImpresionReportesCobranza',nrp,3);
		WizardImpresionReportesCobranzaCtrl.abrir();
	}, 
	reporteMotivoRCV: function() {
		var nrp = $("#nrp").val();
		
		WizardImpresionReportesCobranzaCtrl.init('wizardImpresionReportesCobranza',nrp,4);
		WizardImpresionReportesCobranzaCtrl.abrir();
	}
};

$("#reporteMot").live('click', function() {
	impresionReportesCobranzaWidget.reporteMotivo();
});

$("#reporteMotRCV").live('click', function() {
	impresionReportesCobranzaWidget.reporteMotivoRCV();
});

$(document).ready(
	function() {
		$.post("/portal-web/utility/menu/opciones/2/4",null,function(data) {
				$("#opcionesWidgetEdoAdeudo").html(data);
		});
	}
);
