/**
 * 
 */

/*
 * JS de control del Widget de Persona Fisica.
 */

$.getScript("/gestionCobranza-web/static/resources/js/wizard/impresionReporteCobranzaWizard.js");

var impresionReportesCobranzaObject = {
	reporteSituacion : function(nrp) {
		WizardImpresionReportesCobranzaCtrl.init('wizardImpresionReportesCobranza',nrp,1);
		WizardImpresionReportesCobranzaCtrl.abrir();
	},
	reporteSituacionRCV: function(nrp) {
		WizardImpresionReportesCobranzaCtrl.init('wizardImpresionReportesCobranza',nrp,2);
		WizardImpresionReportesCobranzaCtrl.abrir();
	},
	reporteMotivo: function(nrp) {
		WizardImpresionReportesCobranzaCtrl.init('wizardImpresionReportesCobranza',nrp,3);
		WizardImpresionReportesCobranzaCtrl.abrir();
	}, 
	reporteMotivoRCV: function(nrp) {
		WizardImpresionReportesCobranzaCtrl.init('wizardImpresionReportesCobranza',nrp,4);
		WizardImpresionReportesCobranzaCtrl.abrir();
	}
};