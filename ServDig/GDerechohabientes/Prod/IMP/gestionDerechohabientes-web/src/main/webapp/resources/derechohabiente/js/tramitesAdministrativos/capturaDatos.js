/**
 * 
 */

$(document).ready(function(){
	$("#tramite").validate({ 
		rules: {
		/*	matricula: {
				required:true,
				number: true,
				maxlength: 15
			},*/
			motivo: {
				required: true,
				maxlength: 500
			},
			fundamentoLegal: {
				required: true,
				maxlength: 500
			}
		}, 
		errorLabelContainer: "#warning", 
		messages: { 
		//	matricula: {required:"Obligatorio", number : "El campo debe ser num&eacute;rico", maxlength : "Deber ser m&aacute;ximo 15 caracteres"},
			motivo:{required: "Obligatorio", maxlength: "Debe ser m&aacute;ximo de 400 caracteres(tomando en cuenta espacios)"},
			fundamentoLegal:{required: "Obligatorio", maxlength: "Debe ser m&aacute;ximo de 500 caracteres(tomando en cuenta espacios)"}
		} 
	}); 
	
	$("#aceptar").on('click', function() {
		validaDatos();
	});
	
	$("#cancelarTramiteAdmin").on("click", function() {
		cancelarSolicitud();
	});
	
	var opciones = {maxCharacters: 500}
	
	asignartextAreaLimites("motivo",opciones);
	asignartextAreaLimites("fundamentoLegal",opciones);
});

var validaDatos = function() {
	var datosValidos = $("#tramite").valid();
	//si los datos estan bien ejecutamos el callback
	if(datosValidos) {
		mostrarMensajeConfirmacion();
	}
}

var mostrarMensajeConfirmacion = function() {
	var tramiteRealizado = $("#tituloTramite").html().toLowerCase();
	var mensaje = "&iquest;Est&aacute; seguro que desea realizar el tr&aacute;mite de <strong>"+tramiteRealizado+"</strong> para el integrante del grupo familiar?";
	var $dialogo = $('<div></div');
	$dialogo.html(mensaje);
	$dialogo.dialog({
		autoOpen : false,
		title: "Confirmaci\u00F3n",
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Aceptar" : function() {
				$(this).dialog('close');
				finalizarTramite();
				$.blockUI();
			}, 
			"Cancelar" : function() {
				$(this).dialog('close');
			}
			
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$dialogo.dialog('open');
}

var cancelarSolicitud = function() {
	var tramiteRealizado = $("#tituloTramite").html().toLowerCase();
	var mensaje = "&iquest;Est&aacute; seguro que desea cancelar el tramite de <strong>"+tramiteRealizado+"</strong>? La informaci&oacute;n capturada se perder&aacute;";
	var $dialogo = $('<div></div');
	$dialogo.html(mensaje);
	$dialogo.dialog({
		autoOpen : false,
		title: "Cancelaci\u00F3n",
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Si" : function() {
				$(this).dialog('close');
				$.blockUI();
				location.href = context_path +"/inicio/grupoFamiliar";
			}, 
			"No" : function() {
				$(this).dialog('close');
			}
			
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$dialogo.dialog('open');
}

var finalizarTramite = function() {
	$formTramite = $("#tramite");
	$formTramite.attr("action",context_path + "/tramites/admin/finalizar");
	$formTramite.submit();
};
