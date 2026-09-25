//$.getScript("/${mvn.web.app.root}/static/resources/js/wizard/individual/WizardSeguroIvroIndivCtrl.js");

$("#btnInciaTramite").live('click', function(event) {
	event.preventDefault();
	$('#mdmDatosEntrada').submit();
	
});
$("#btnInciaTramiteRenovacion").live('click', function(event) {
	event.preventDefault();
	$('#mdmDatosEntradaRenovacion').submit();
	
});

$("#btnInicioCancelarTramite").live('click', function(event) {
	event.preventDefault();
	parent.WizardSeguroIvroIndivCtrl.cerrar();
});