/*
 * JS de control del Widget del Domicilio Centro de Trabajo
 */
$.getScript("/delta-gestionPatronal-web/static/resources/js/wizard/modificacion/patron/clasificacion/modificacionPatronClasificacionWizard.js");
$.getScript("/delta-gestionPatronal-web/static/resources/js/wizard/modificacion/patron/centroTrabajo/contacto/medioContactoWizard.js");

var patronDomicilioCentroTrabajoWidget = {
	editar : function() {
		var numeroRegistroPatronal = AtributosPersonaCtrl.personaPortal.registroPatronal;
		var tipoTramite = $('#hdnTipoActualizacionCentroTrab').val();
		var esRPC = AtributosPersonaCtrl.personaPortal.isRPC;
		var tipoRegPatron = AtributosPersonaCtrl.personaPortal.idTipoRegPatron;
		
		if(esRPC=='1'){
			notificarRPC();
			return;
		}
		
		if(tipoRegPatron == '2' || tipoRegPatron == '3'){
			construirDialogo("#dialogoMensajes", "Tr&aacute;mite improcedente", "El registro patronal seleccionado tiene la caracter&iacute;stica de ser un Registro Patronal Unico (RPU) por lo cual no puede realizar este movimiento. Tramitar en ventanilla.", true, undefined, undefined, 200, 600);
			return;
		}
		
		WizardModificacionPatronClasificacionCtrl.init('wizardModificacionCentroTrabajo', numeroRegistroPatronal,
			tipoTramite, "Cambio de Domicilio del Centro de Trabajo");
		WizardModificacionPatronClasificacionCtrl.abrir();			
		
	}
};


var patronContactoDomicilioCentroTrabajoWidget = {
		editar : function() {
			var numeroRegistroPatronal = AtributosPersonaCtrl.personaPortal.registroPatronal;
			var idPersona = AtributosPersonaCtrl.personaPortal.idPersona;
			var idTipoPersona = AtributosPersonaCtrl.personaPortal.idTipoPersona;
			
			var tipoTramite = $('#hdnTipoActualizacionCentroTrab').val();
			var esRPC = AtributosPersonaCtrl.personaPortal.isRPC;
			
			if(esRPC=='1'){
				notificarRPC();
				return;
			}
			
			WizardModificacionContactoCentroTrabajoCtrl.init('wizardModificacionContactoCentroTrabajo', numeroRegistroPatronal, idPersona, idTipoPersona,
					tipoTramite, "Modificaci&oacute;n de Datos de Contacto del Centro de Trabajo");
			WizardModificacionContactoCentroTrabajoCtrl.abrir();
		}
	};


$("#editarDomicilioCentroTrabajo").live('click', function() {
	patronDomicilioCentroTrabajoWidget.editar();
});

$("#editarDatosContactoCentroTrabajo").live('click', function() {
	 patronContactoDomicilioCentroTrabajoWidget.editar();
});

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
}

$(document).ready(
		function() {
			var context_p = $("#context_p").val();
			$.post("/portal-web/utility/menu/opciones/2/3",null,function(data) {
				$("#accionesWidgetPatronGeneral").html(data);
			});
		}
	);
