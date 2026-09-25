$.getScript("/gestionCobranza-web/static/resources/js/wizard/cartaNoAdeudo/cartaNoAdeudoCtrl.js");

$(document).ready(function() {
	$.post("/portal-web/utility/menu/opciones/2/9", null, function(data) {
		$("#accionesWidgetDatosFiscales").html(data);
	});
});

var identidadFiscalWidget = {

	init : function() {
	},

	refresh : function() {
	},

	solicitarCartaNoAdeudo : function() {
		var idPersona = AtributosPersonaCtrl.personaPortal.idPersona;
		var idTipoPersona = AtributosPersonaCtrl.personaPortal.idTipoPersona;
		var rfc = AtributosPersonaCtrl.personaPortal.rfc;
		var curp = AtributosPersonaCtrl.personaFirmada.curp;
		var _tituloDialogoError = 'Tr&aacute;mite improcedente';
		var _idDivDialogoGeneral = '#dialogoMensajesGeneral';

		if ($.trim(rfc).length == 0) {
			construirDialogoFiscalGeneral(
					_idDivDialogoGeneral,
					_tituloDialogoError,
					'Es necesario contar con RFC para realizar este tr&aacute;mite.',
					true, undefined, undefined, 250, 600);
		} else {
			
			if (indShowMsgPat == true) {
				construirDialogoFiscalGeneral(_idDivDialogoGeneral, 'Aviso',
						msgPat, false, function() {
							ejecutarCartaNoAdeudo(idPersona, idTipoPersona,
									rfc, curp);
						}, undefined, 'auto', 450);
			} else {
				ejecutarCartaNoAdeudo(idPersona, idTipoPersona, rfc, curp);
			}
		}
	}
};

$("#solicitudCartaNoAdeudo").live('click', function() {
	identidadFiscalWidget.solicitarCartaNoAdeudo();
});

function ejecutarCartaNoAdeudo(idPersona, idTipoPersona, rfc, curp) {
	WizardCartaNoAdeudoCtrl.init('datosFiscalesComponent', idPersona,
			idTipoPersona, rfc, curp);
	WizardCartaNoAdeudoCtrl.abrir();
}

function construirDialogoFiscalGeneral(divId, titulo, mensaje, error, callback,
		callbackForXButton, height, width) {

	$('#textoMensajeGeneral').html(mensaje);
	$('#textoMensajeGeneral').removeAttr("style");
	
	if (error) {
		$('#textoMensajeGeneral').attr("style", "color: red;");
	}

	if (height == undefined) {
		height = 150;
	}
	if (width == undefined) {
		width = 400;
	}

	var objDialogo = $(divId).dialog(
			{
				autoOpen : false,
				resizable : false,
				modal : true,
				height : height,
				width : width,
				title : titulo,
				close : function(event, ui) {
					if (event.originalEvent
							&& $(event.originalEvent.target).closest(
									".ui-dialog-titlebar-close").length) {
						if (callbackForXButton != undefined
								&& jQuery.isFunction(callbackForXButton)) {
							callbackForXButton();
						}
					}
				},
				buttons : {
					"Aceptar" : function() {
						if (jQuery.isFunction(callback)) {
							callback();
						}
						$(this).dialog("close");
					}
				}
			});
	objDialogo.dialog('open');
};