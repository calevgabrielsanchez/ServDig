$(document).ready(function() {
	$('#cerrarWizard').click(function() {
		parent.WizardDetalleSeguroCtrl.cerrar();
	});

	$('#btnImpComprob').click(function() {
		imprSegPerIvro();
	});
	
	$('#regresar').click(function(e){
		e.preventDefault();
		parent.WizardAltaSeguroFamiliarCtrl.openListaSeguros();
	});
	
});

var imprimePago = function(idPago) {
	var liga = '/${mvn.web.app.root}/wizard/detalle/seguro/lc/pago/' + idPago;
	window.open(liga, '_blank');
};

var imprSegPerIvro = function(id) {
//	var idCifrado = id.getAttribute('data-id');
//	var liga = '/${mvn.web.app.root}/wizard/detalle/seguro/reporteComp/' + idCifrado;
//	window.open(liga, '_blank');

	var idCifrado = id.getAttribute('data-id');

	var form = document.createElement("form");
	form.method = "POST";
	form.action = '/${mvn.web.app.root}/wizard/detalle/seguro/reporteComp/' + idCifrado;
	form.target = "_blank";
	 
	
	document.body.appendChild(form);
	form.submit();
	document.body.removeChild(form);
};

var imprCuestionarioIvro = function(id) {
	var liga = '/${mvn.web.app.root}/wizard/detalle/seguro/cuestionario/' + id;
	window.open(liga, '_blank');
};

var enviaCorreo = function(idPago,cveIdSeguroIvro) {
	var liga = '/${mvn.web.app.root}/wizard/detalle/seguro/correoElectronico/' + idPago + '/' + cveIdSeguroIvro;
   $("#auxIframe").attr( "src", liga );
};

