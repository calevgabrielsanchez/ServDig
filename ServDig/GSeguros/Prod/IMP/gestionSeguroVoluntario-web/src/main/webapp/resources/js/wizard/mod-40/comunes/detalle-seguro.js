$(document).ready(function() {
	$('#cerrarWizard').click(function() {
		parent.WizardDetalleSeguroCtrl.cerrar();
	});

	$('#btnImpComprob').click(function() {
		imprSegPerIvro();
	});
	
	$('#mostrarBancos').click(mostrarBancosPagos);
});

var imprimePago = function(idPago) {
	var liga = '/${mvn.web.app.root}/wizard/detalle/seguro/lc/pago/' + idPago;
	window.open(liga, '_blank');
};

var imprSegPerIvro = function(id) {
	//var idCifrado = id.getAttribute('data-id');
	//var liga = '/${mvn.web.app.root}/wizard/detalle/seguro/reporteComp/' + idCifrado;
	//window.open(liga, '_blank');
	
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


var enviaComprobanteRenvacionCVRO = function(id,correo) {
	var liga = '/${mvn.web.app.root}/wizard/detalle/seguro/sendEmailComprobante/';
		$.ajax({
			url : liga + id + '/' + correo + '/',
			dataType : 'json',
			success : function(response) {
				$.unblockUI();
			},
			error : function(error) {
				$.unblockUI();
				var msgError = error.msgError;
				if (typeof msgError === 'undefined') {
					msgError = 'Ocurri\u00f3 un error inesperado al enviar correo.';
				}
			}
		});
};
var mostrarBancosPagos = function() {
	var $dialogBancos = $( "<div></div>" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		width: "350px",
		title: 'Bancos',
		autoOpen: false,
		buttons: {
			"Aceptar": function() {
				var liga = 'http://www.imss.gob.mx/patrones/sipare/entidades-receptoras';
				window.open(liga, '_blank');
				$(this).dialog('close');
		 	}
		 }
	});
	
	$dialogBancos.html("Est&aacute; saliendo del portal IMSS digital para visualizar los bancos en donde puede realizar su pago. Al concluir regresar&aacute; al portal IMSS digital.");
	$dialogBancos.dialog('open');
}