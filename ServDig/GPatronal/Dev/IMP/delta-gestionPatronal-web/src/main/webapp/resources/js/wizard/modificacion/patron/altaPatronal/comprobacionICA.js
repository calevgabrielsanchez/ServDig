var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

	$('#acordeon').accordion({
		autoHeight : false,
		collapsible : true
	});

	// se renderea la tabla de resultados del renapo como dataTable
	$('#tabla-imss-renapo').dataTable({
		bInfo : false,
		sPaginationType : null,
		bJQueryUI : false,
		bSort : false,
		bFilter : false,
		bPaginate : false,
		bAutoWidth : true

	});

	// se renderea la tabla de resultados del sat como dataTable
	$('#tabla-imss-sat').dataTable({
		bInfo : false,
		sPaginationType : null,
		bJQueryUI : false,
		bSort : false,
		bFilter : false,
		bPaginate : false,
		bAutoWidth : true

	});

	$('#siguientePaso').click(function() {
		siguientePaso();
	});

	$('#guardarTramite').click(function() {
		guardarTramite();
	});

	$('#guardarCerrarTramite').click(function() {
		guardarCerrarTramite();
	});

	$('#cancelarTramite').click(function() {
		dialogoConfirmarCancelar.dialog( "open" );
	});

	$('#cerrarWizard').click(function() {
		cerrarWizard();
	});
	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				cancelarTramite();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });

	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				cerrarWizard();
		 	}
		 }
	 });
});

function siguientePaso() {
	var urlAction = context_path + '/wizard/tramite/registro/patronal/captura/centroTrabajo/domicilio';

	$.blockUI();

	document.getElementById('comprobacionIcaForm').action = urlAction;
	document.getElementById('comprobacionIcaForm').submit();
}

function guardarTramite() {
	construirDialogoConfirmarComun('\u00BFEst\u00E1 seguro que desea Guardar la solicitud?', guardarSolicitudTramite);
}

function guardarSolicitudTramite() {
	var url = context_path + '/wizard/tramite/registro/patronal/guardar/solicitud/datosIca/'
		+ idSolicitud;
	prepararRequest(url, null, true, procesarRespuestaServidor);
}

function guardarCerrarTramite() {
	construirDialogoConfirmarComun('\u00BFEst\u00E1 seguro que desea Guardar antes de cerrar la solicitud?', guardarCerrarSolicitud);
}

function guardarCerrarSolicitud(){
	var url = context_path + '/wizard/tramite/registro/patronal/guardar/solicitud/datosIca/'
		+ idSolicitud;
	prepararRequest(url, null, true, callbackGuardarCerrarSolicitud);
}

function callbackGuardarCerrarSolicitud(response) {
	procesarRespuestaServidor(response, cerrarWizard);
}

function cancelarTramite() {
	var url = context_path + '/wizard/tramite/registro/patronal/cancelar/solicitud/'
		+ idSolicitud;

	$.postJSON(url, null, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	});
}

function cerrarWizard() {	
	parent.WizardAltaPatronalCtrl.cerrar();
}

function procesarRespuestaServidor(response, callback) {
	var mensaje = "";
	var titulo = "";
	var error = false;
	if (response.mensajeExito != undefined && response.mensajeExito != null) {
		titulo = "Operaci&oacute;n Exitosa";
		error = false;
		if (response.mensajeExito == "") {
			mensaje = "Operaci&oacute;n realizada con &eacute;xito.";
		} else {
			mensaje = response.mensajeExito;
			construirDialogoMensajes(titulo, mensaje, error, callback);
		}
		return true;
	} else if (response.mensajeError != undefined
			&& response.mensajeError != null) {
		titulo = "Operaci&oacute;n Erronea";
		error = true;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}
		construirDialogoMensajes(titulo, mensaje, error, callback);
	}
	return false;
}

function construirDialogoMensajes(titulo, mensaje, error, callback) {
	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if (error) {
		$("#textoMensaje").attr("style", "color: red;");
	} else {
		$("#textoMensaje").attr("style", "color: black;");
	}
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 'auto',
		width : 400,
		title : titulo,
		buttons : {
			"Aceptar" : function() {
				if (jQuery.isFunction(callback)) {
					callback();
				}
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}
