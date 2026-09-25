/**
 * 
 */
$.getScript("/delta-gestionPatronal-web/static/resources/js/wizard/modificacion/patron/altaPatronal/altaPatronalWizard.js");
$.getScript("/portal-web/static/resources/js/wizard/recuperacionRP/recuperacionPatronWizard.js");
$.getScript("/portal-web/static/resources/js/portlet/nmps.js");
$.getScript("/gestionCobranza-web/static/resources/js/wizard/graficas/estadoAdeudoWizard.js");
$.getScript("/delta-gestionPatronal-web/static/resources/js/wizard/modificacion/patron/clasificacion/modificacionPatronClasificacionWizard.js");
$.getScript("/delta-gestionPatronal-web/static/resources/js/wizard/modificacion/patron/centroTrabajo/contacto/medioContactoWizard.js");
$.getScript("/gestionBeneficio-web/static/resources/js/delta/wizard/riss/solicitarRifWizard.js");
$.getScript("/gestionCobranza-web/static/resources/js/wizard/comprobanteFiscal/comprobanteFiscalWizard.js");
$.getScript("/delta-gestionPatronal-web/static/resources/js/delta/registroMovimientosAfiliatorios/wizardRegistroMovAfiliatorios.js");
$.getScript("/riesgosTrabajo-web/static/resources/js/wizard/rtt/wizardRttCtrl.js");
$.getScript("/escritoDesacuerdo-web/static/resources/js/wizard/desacuerdo/wizardEscritoCtrl.js");


var patronAsociadoPortlet = {
	 
	editar : function() {
		var cveIdPersona = AtributosPersonaCtrl.personaPortal.idPersona;
		var cveTipoPersona = AtributosPersonaCtrl.personaPortal.idTipoPersona;
		
		var url = '/delta-gestionPatronal-web/wizard/tramite/registro/patronal/validarPersonaParaAlta/' 
			+ cveIdPersona + '/' + cveTipoPersona;
		
		$.blockUI();
		
		$.getJSON(url, function(data) {
			
			$.unblockUI();
			
			var respuesta = data.respuesta;
	        if (respuesta == 1) {
				construirDialogo("#dialogoMensajes", "Importante", data.msgError, true, undefined, undefined, 250, 600);
				return;
			} else {
				WizardAltaPatronalCtrl.init('wizardAltaPatronal', cveIdPersona, cveTipoPersona);
				WizardAltaPatronalCtrl.abrir();
			}
	    });
	}, 
	recuperarRP: function() {
		var cveIdPersona = AtributosPersonaCtrl.personaPortal.idPersona;
		var cveTipoPersona = AtributosPersonaCtrl.personaPortal.idTipoPersona;
		var rfcPersona = AtributosPersonaCtrl.personaPortal.rfc;
				
		if($.trim(rfcPersona).length == 0) {
			mensajeError();
		} else {
			WizardRecuperacionPatronCtrl.init("wizardRecuperacionPatron",
				cveIdPersona, cveTipoPersona, rfcPersona);
			WizardRecuperacionPatronCtrl.abrir();
		}
	},
	modificarClasificacion : function(numeroRegistroPatronal){
		var tipoTramite = tipoTramiteModificacionSRT;

		WizardModificacionPatronClasificacionCtrl.init('wizardModificacionClasificacion', numeroRegistroPatronal,
				tipoTramite, "Modificaciones en el seguro de riesgo de trabajo");
		WizardModificacionPatronClasificacionCtrl.abrir();
	},
	
	modificarCentroTrabajo : function(numeroRegistroPatronal, esRPC, idTipoRegPatron){
		var tipoTramite = tipoTramiteModificacionCentroTrabajo;
		
		if(esRPC=='1'){
			construirDialogo("#dialogoMensajes", "Tr&aacute;mite improcedente", "El registro patronal seleccionado tiene la caracter&iacute;stica de ser un Registro Patronal por Clase (RPC) por lo cual no puede realizar este movimiento.", true, undefined, undefined, 150, 600);
			return;
		}
		
		if(idTipoRegPatron == '2' || idTipoRegPatron == '3'){
			construirDialogo("#dialogoMensajes", "Tr&aacute;mite improcedente", "El registro patronal seleccionado tiene la caracter&iacute;stica de ser un Registro Patronal Unico (RPU) por lo cual no puede realizar este movimiento. Tramitar en ventanilla.", true, undefined, undefined, 200, 600);
			return;
		}
		
		WizardModificacionPatronClasificacionCtrl.init('wizardModificacionCentroTrabajo', numeroRegistroPatronal,
				tipoTramite, "Cambio de domicilio del centro de trabajo");
		WizardModificacionPatronClasificacionCtrl.abrir();	
	},
	
	registroDeMovimientosAfiliatorios: function(numeroRegistroPatronal){
		
		var idPersona = AtributosPersonaCtrl.personaPortal.idPersona;
		var cveTipoPersona = AtributosPersonaCtrl.personaPortal.idTipoPersona;
		wizardRegistroMovAfiliatorios.init("wizardRegistroMovimientos", numeroRegistroPatronal, idPersona, cveTipoPersona);
		wizardRegistroMovAfiliatorios.abrir();
	

	
	},
	
	mostrarEstadoDeAdeudo : function(numeroRegistroPatronal){
		WizardEstadoAdeudoCtrl.init("wizardEstadoAdeudoDiv", numeroRegistroPatronal);
		WizardEstadoAdeudoCtrl.abrir();
	},
	
	modificarContactoCentroTrabajo : function(numeroRegistroPatronal, esRPC) {
		
		var cveIdPersona = AtributosPersonaCtrl.personaPortal.idPersona;
		var cveTipoPersona = AtributosPersonaCtrl.personaPortal.idTipoPersona;
		
		if(esRPC=='1'){
			construirDialogo("#dialogoMensajes", "Tr&aacute;mite improcedente", "El registro patronal tiene marca RPC por lo cual no puede realizar este movimiento", true, undefined, undefined, 150, 450);
			return;
		}
		
		WizardModificacionContactoCentroTrabajoCtrl.init('wizardModificacionContactoCentroTrabajo', numeroRegistroPatronal,
				cveIdPersona, cveTipoPersona);
		WizardModificacionContactoCentroTrabajoCtrl.abrir();
			
	},

	
	verDetalle : function(numeroRegistroPatronal, modalidadPatron, indRegPatClase){
			$.blockUI();

			$('#hdnRegistroPatronal').val(numeroRegistroPatronal);
			$('#hdnModalidadPatron').val(modalidadPatron);
			$('#hdnIndRegPatClase').val(indRegPatClase);
			
			/* 
			 * Se realiza esta validaci�n para saber si estamos en el portal
			 * empresa y poder mandar la informaci�n necesaria de la 
			 * empresa para mostrarlo en la barra de navegaci�n
			 */
			if (portalContext == portletEnumEmpresaId){
				if ($('input#hdnCveTipoPersona').val() == 1) {
					$('input#hdnIdEmpresaFisica').val($('input#hdnIdPersonaRepresentada').val());
					$('input#hdnRFCEmpresaFisica').val($('input#hdnRfcPersonaRep').val());
					$('input#hdnIdFiscalEmpresaFisica').val($('input#idPersonaFisicaMoral').val());
				} else {
					$('input#hdnIdEmpresaMoral').val($('input#hdnIdPersonaRepresentada').val());
					$('input#hdnRFCEmpresaMoral').val($('input#hdnRfcPersonaRep').val());
					$('input#hdnIdFiscalEmpresaMoral').val($('input#idPersonaFisicaMoral').val());
				}
			}
			
			document.getElementById('formPatronAsociado').action =  context_path + '/portal/patron/ingresar/';
			document.getElementById('formPatronAsociado').submit();
	},
	solicitarRIF: function (numeroRegistroPatronal) {
		var rfc 		= AtributosPersonaCtrl.personaPortal.rfc;
		var idPersona 	= AtributosPersonaCtrl.personaPortal.idPersona;
		
		WizardSolicitarRifCtrl.init('wizardModificacionClasificacion', rfc, idPersona);
		WizardSolicitarRifCtrl.abrir();
	},
	obtenerComprobanteFiscal: function (numeroRegistroPatronal) {
		var rfc 		= AtributosPersonaCtrl.personaPortal.rfc;

		WizardComprobanteFiscalCtrl.init('wizardObtencionComprobanteFiscal', numeroRegistroPatronal, rfc);
		WizardComprobanteFiscalCtrl.abrir();
	},
	registroObra: function (numeroRegistroPatronal) {
		var rfc = AtributosPersonaCtrl.personaPortal.rfc;
		var cveIdPersona = AtributosPersonaCtrl.personaPortal.idPersona;
		var cveTipoPersona = AtributosPersonaCtrl.personaPortal.idTipoPersona;
		var rfcEscapado=rfc.replace("&", "%26");				
		WizardRegistroObraCtrl.init('wizardRegistroObra', rfcEscapado, numeroRegistroPatronal, cveIdPersona, cveTipoPersona);
		WizardRegistroObraCtrl.abrir();
	},
	riesgoTrabajo : function(numeroRegistroPatronal) {
	    var datos = {contenedor: 'wizardRiesgoTrabajoDiv',
        		rp: numeroRegistroPatronal,
				razonSocial: AtributosPersonaCtrl.personaPortal.nombreRazonSocial,
			    id: AtributosPersonaCtrl.personaPortal.idPersona};
	    WizardRttCtrl.init(datos);
		WizardRttCtrl.abrir();  	
	},
	riesgoTrabajoRfc : function(cveRfc) {
	    var datos = {contenedor: 'wizardRiesgoTrabajoDiv',
        		rfc: cveRfc,
				razonSocial: AtributosPersonaCtrl.personaPortal.nombreRazonSocial,
			    id: AtributosPersonaCtrl.personaPortal.idPersona};
	    WizardRttCtrl.init(datos);
		WizardRttCtrl.abrir();  	
	},
	escrito : function(numeroRegistroPatronal) {
		 var datos = {contenedor: 'wizardRiesgoTrabajoDiv',
	        		rp: numeroRegistroPatronal};
		    WizardEscritoCtrl.init(datos);
			WizardEscritoCtrl.abrir();  	
	},
	folioIncapacidades:function(numeroRegistroPatronal){		
		WizardNMPSCtrl.init('wizardNMPSDiv',AtributosPersonaCtrl.personaPortal.rfc,numeroRegistroPatronal,AtributosPersonaCtrl.personaFirmada.nssCifrado,'/nmps-patron-consulta/incapacidades/consultarFolio',true);
		WizardNMPSCtrl.abrir();  	
	},
	rangoFechas:function(numeroRegistroPatronal){
		WizardNMPSCtrl.init('wizardNMPSDiv',AtributosPersonaCtrl.personaPortal.rfc,numeroRegistroPatronal,AtributosPersonaCtrl.personaFirmada.nssCifrado,'/nmps-patron-consulta/incapacidades/consultarFechas',true);
		WizardNMPSCtrl.abrir(); 	
	},
	nss:function(numeroRegistroPatronal){
		WizardNMPSCtrl.init('wizardNMPSDiv',AtributosPersonaCtrl.personaPortal.rfc,numeroRegistroPatronal,AtributosPersonaCtrl.personaFirmada.nssCifrado,'/nmps-patron-consulta/incapacidades/consultarNss',true);
		WizardNMPSCtrl.abrir(); 		
	},
	reembolsoSubsidios:function(numeroRegistroPatronal ){
		WizardNMPSCtrl.init('wizardNMPSDiv',AtributosPersonaCtrl.personaPortal.rfc,numeroRegistroPatronal,AtributosPersonaCtrl.personaFirmada.nssCifrado,'/nmps-patron-consulta/incapacidades/consultarFactura',true);
		WizardNMPSCtrl.abrir(); 		
	},abrirnmpsClabe:function(){		
		WizardNMPSCtrl.init('wizardNMPSDivAsegurado',AtributosPersonaCtrl.personaPortal.rfcEncriptado,null,AtributosPersonaCtrl.personaFirmada.nssEncriptado,'/nmps-asegurado-cuenta/FIEL/consultar',false);
		WizardNMPSCtrl.abrir(); 		
	},abrirnmpsMovimientos:function(){			
		WizardNMPSCtrl.init('wizardNMPSDivAsegurado',AtributosPersonaCtrl.personaPortal.rfc,null,AtributosPersonaCtrl.personaFirmada.nssCifrado,'/nmps-asegurado-consulta/FIEL/incapacidad/historial',false);
		WizardNMPSCtrl.abrir(); 
	},abrirnmpsEstatusPago:function(){		
		WizardNMPSCtrl.init('wizardNMPSDivAsegurado',AtributosPersonaCtrl.personaPortal.rfc,null,AtributosPersonaCtrl.personaFirmada.nssCifrado,'/nmps-asegurado-consulta/FIEL/pago/estatusPago',false);
		WizardNMPSCtrl.abrir(); 
	},
    bandeja:function(numeroRegistroPatronal){
        WizardNMPSCtrl.init('wizardNMPSDiv',AtributosPersonaCtrl.personaPortal.rfc,numeroRegistroPatronal,AtributosPersonaCtrl.personaFirmada.nssCifrado,'/nmps-patron-consulta/incapacidades/bandeja',true);
        WizardNMPSCtrl.abrir();
    },
    validaCuenta:function(numeroRegistroPatronal){
        WizardNMPSCtrl.init('wizardNMPSDiv',AtributosPersonaCtrl.personaPortal.rfc,numeroRegistroPatronal,AtributosPersonaCtrl.personaFirmada.nssCifrado,'/nmps-patron-consulta/empresaConConvenio/cuentaClabe',true);
        WizardNMPSCtrl.abrir();
    }
};

$("#registroAltaPatronal").live('click', function() {
	patronAsociadoPortlet.editar();
	
});

$("#recuperarPatron").live('click', function() {
	patronAsociadoPortlet.recuperarRP();
	
});

$("#registroMovimientosAfiliatorios").live('click',function (){
	patronAsociadoPortlet.registroDeMovimientosAfiliatorios("1");
});





$("#abrirnmpsClabe").live('click', function() {
	patronAsociadoPortlet.abrirnmpsClabe();	
});


$("#abrirnmpsMovimientos").live('click', function() {
	patronAsociadoPortlet.abrirnmpsMovimientos();	
});


$("#abrirnmpsEstatusPago").live('click', function() {
	patronAsociadoPortlet.abrirnmpsEstatusPago();	
});



function mensajeError() {

	$decision = $('<div title="Mensaje"></div');

	$decision.dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		dialogClass: "no-close",
	    closeOnEscape: false,
	    buttons: {
		 	"ACEPTAR": function() {
		 		$( this ).dialog( "close" );
		 		$( this ).dialog( "destroy" );
		 	}
		 }
	});

	$decision.html('Es necesario contar con RFC para realizar este tr&aacute;mite');
	$decision.dialog('open');
	
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
}

$(document).ready(
	function() {
		$.post("/portal-web/utility/menu/opciones/1/1",null,function(data) {
			$("#opcionesPortletPatrones").html(data);
		});
	}
);
