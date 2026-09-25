/**
 * Script para controlar el portlet de persona autorizada
 */

$.getScript("/delta-gestionPatronal-web/static/resources/js/delta/wizard/socios/alta/wizardAltaSocios.js");
$.getScript("/delta-gestionPatronal-web/static/resources/js/delta/wizard/socios/baja/wizardBajaSocios.js");

var sociosPortlet = {
		registrar : function() {
			//Obtener Id y RFC de la empresa.
			var idPersonaRep = $("#hdnIdPersonaRepresentada").val();
			var rfcRep = $("#hdnRfcPersonaRep").val();
			var url = '/delta-gestionPatronal-web/wizard/tramite/socios/validaciones/SocioSindicato/' + idPersonaRep;
						
			$.getJSON(url, function(data) {
				var respuesta = data.respuesta;
		        if (respuesta == 1) {
		        	construirDialogo("#dialogoMensajes", "Tr&aacute;mite improcedente", 
		        		data.msgError, true, undefined, undefined, 250, 600);
					return;
				} else {
					altaDeSocios(idPersonaRep, rfcRep);
				}		        
		    });
						
		},
		baja: function() {
			//Obtener Id y RFC de la empresa.
			var idPersonaRep = $("#hdnIdPersonaRepresentada").val();
			var rfcRep = $("#hdnRfcPersonaRep").val();
						
			bajaDeSocios(idPersonaRep, rfcRep);
			
		}
};

function altaDeSocios(idPersonaRep, rfcRep){
	WizardAltaSociosCtrl.init('wizardPortletSocios', idPersonaRep, rfcRep);
	WizardAltaSociosCtrl.abrir();
}

function bajaDeSocios(idPersonaRep, rfcRep){
	WizardBajaSociosCtrl.init('wizardPortletSocios', idPersonaRep, rfcRep);
	WizardBajaSociosCtrl.abrir();
}

function construirDialogo(divId, titulo, mensaje, error, callback, callbackForXButton, height, width) {
	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if (error) {
		$("#textoMensaje").attr("style", "color: red;");
	} else {
		$("#textoMensaje").attr("style", "color: blue;");
	}
	
	if(height == undefined){
		height=150;
	}
	if(width == undefined){
		width=400;
	}
	
	var objDialogo = $(divId).dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : height,
		width : width,
		title : titulo,
		close: function(event, ui) {
				    if ( event.originalEvent && $(event.originalEvent.target).closest(".ui-dialog-titlebar-close").length ) {
				    	if ( callbackForXButton != undefined && jQuery.isFunction(callbackForXButton)) {
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


$("#registrarSocio").live('click', function() {
	sociosPortlet.registrar();
});

$("#eliminarSocios").live('click', function() {
	sociosPortlet.baja();
});

$(document).ready(function() {
	
	$.post("/portal-web/utility/menu/opciones/1/9",null,function(data) {
			$("#opcionesSociosPortlet").html(data);
	});
	
});
