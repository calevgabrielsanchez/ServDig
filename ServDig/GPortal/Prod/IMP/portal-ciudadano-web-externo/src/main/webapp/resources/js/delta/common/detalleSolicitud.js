/**
 * 
 */

$(document).ready(function() {
	$("#cerrarDetalle").on('click',cerrarDetalleSolicitud);
	$(".salir").on('click',cerrarDetalleSolicitud);
	blockBackButton();
	//obtenemos la homoclave del tramite
	var homoclaveTramite = $("#keyHomoclaveTramite").val();
	if(homoclaveTramite.length > 0) {
	//lanzamos la encuesta una vez que se carga la pagina
	startEncuestaHC(500,homoclaveTramite);
	}
});

function imprimirAcuse() {
	var urlAction = context_path + '/solicitud/mostrarComprobante';
	abrirPestanaDocumentos(urlAction,"","");
}

function cerrarDetalleSolicitud() {
	var url = '/portal-ciudadano-web-externo/solicitud/limpiar-sesion';
	$.blockUI();
	$.postJSON(url, '', function(data) {
		location.href =  "http://www.imss.gob.mx/derechoH/escritorio-virtual";
	}).error(function(data) {
		location.href =  "http://www.imss.gob.mx/derechoH/escritorio-virtual";
	});
}

function abrirDocumentoResultante(idTramite, idDocumentoPorTipo) {
	var urlAction = context_path + '/solicitud/mostrarDocumentoResultante';
	abrirPestanaDocumentos(urlAction,idTramite,idDocumentoPorTipo);
}

function abrirPestanaDocumentos(url, idTramite, idDocumento) {
	var $formularioDocumento = $('#solicitudDocumentoForm');
	$formularioDocumento.attr('action',url);

	$formularioDocumento.find('#idTramite').val(idTramite);
	$formularioDocumento.find('#tipoDocumento').val(idDocumento);
	$formularioDocumento.submit();
}

function reenviarDocumentoResultante(idTramiteHashed, tipoDocumentoHashed) {
	var urlAction = context_path + '/solicitud/reenviarDocumento';
	var oForm = {
		'idTramiteHashed' : idTramiteHashed,
		'tipoDocumentoHashed' : tipoDocumentoHashed
	};

	$.getJSON(urlAction, oForm, function(data) {
		mostrarMensajeInformacion("Correo enviado", data.mensaje);
	}).error(function(data) {
		mostrarMensajeError("El documento no pudo ser reenviado. Intente nuevamente");
	});
}

function localizarUmf(latitud, longitud) {

	if (latitud != 'SIN_UBICACION' && longitud != 'SIN_UBICACION') {
		var urlAction = 'http://maps.google.com/maps?q=' + latitud + ','
				+ longitud + '&z=15&iwloc=near&addr';
		$("#btnUbicarClinica").attr("href", urlAction);
		return true
	} else {
		mostrarMensajeError("No es posible ubicar la UMF");
		$("#btnUbicarClinica").attr("href", "#");
		return false
	}
}

function mostrarMensajeError(mensaje) {
	var mensajeError =  $( "#dialog-error" );
	mensajeError.html(mensaje);
	mensajeError.dialog({
		autoOpen : false,
		title: 'Error',
		resizable: false,
		closeOnEscape: false,
		modal: true,
		heigth: 'auto',
		width: 'auto',
		buttons: {
			"Aceptar" : function() {
	          $(this).dialog("close");
	        }
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	mensajeError.dialog('open');
}

function mostrarMensajeInformacion(title, mensaje) {
	var mensajeInfo =  $( "#dialog-info" );
	mensajeInfo.html(mensaje);
	mensajeInfo.dialog({
		autoOpen : false,
		title: title,
		resizable: false,
		closeOnEscape: false,
		modal: true,
		heigth: 'auto',
		width: 'auto',
		buttons: {
			"Aceptar" : function() {
	          $(this).dialog("close");
	        }
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	mensajeInfo.dialog('open');
}


