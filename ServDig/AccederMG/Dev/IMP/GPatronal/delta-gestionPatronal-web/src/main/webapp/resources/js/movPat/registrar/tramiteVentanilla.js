
//$.getScript("/delta-gestionPatronal-web-ventanilla-dev/static/resources/js/wizard/modificacion/patron/clasificacion/modificacionPatronClasificacionWizard.js");

var dialogoListaTramitesClasificacion;
var index=-1;
var oTableRFC;
var oTableTramites;
var dialogoError;

$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
});


function navegarADetalleDeRP(){
	var valorCampoRegistroPatronal = $("#numRegistroPatronal").val().toUpperCase();
	
	if(valorCampoRegistroPatronal.length==10 || valorCampoRegistroPatronal.length==11){
		$("#solicitudClasificacionForm #numeroRegistroPatronal").val(valorCampoRegistroPatronal);
		var sujeto = new Object();
		sujeto.numeroRegistroPatronal = valorCampoRegistroPatronal;
		$.blockUI;
		sendToServer('/movPat/ventanilla/tramite/validaRegistroPatronalExistente', 
				sujeto, callbackValidaRPDetalle, false);
		
	}else{
		var oDialogoGenerico;
		var mensaje = "El registro patronal proporcionado es inv\u00E1lido. <br>Proporcione un registro v\u00E1lido a 10 u 11 posiciones.";
		
		if (valorCampoRegistroPatronal == null || valorCampoRegistroPatronal.length == 0) {
			mensaje = "Campo Requerido."
		}
		
		construirDialogoGenerico(
				"#dialogoMensajes",
				oDialogoGenerico,
				"Aviso",
				mensaje, 
				true, undefined, undefined, 200, 400);
		return false;
	}
}

function callbackValidaRPDetalle(response) {
	$.unblockUI
	if (response.mensajeError != undefined && response.mensajeError != null) {
		titulo = "Operaci&oacute;n Err&oacute;nea";
		error = true;
		var height=200;
		var width=400;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}
		
		if(response.mensajeError.length > 100){
			height=200;
			width=550;
		}
		
		var oDialogoGenerico;
		construirDialogoGenerico("#dialogoMensajes", oDialogoGenerico, titulo,
				mensaje, error, undefined, undefined, height, width);
	} else {
		/*$('#registrosPatronalesForm #numeroRegistroPatronal').val(
				$('#numRegistroPatronal').val());
		navegarTo('/afiliacion/mostrarDetalleRegistroPatronal',
				'registrosPatronalesForm');*/
		patronClasificacionPortlet.editar();
		$("#numRegistroPatronal").val("")
	}
}

var patronClasificacionPortlet = {
	editar : function() {
		var numeroRegistroPatronal = $("#numRegistroPatronal").val();
		var tipoTramite = 11;// Actualizacion de clasificacion
		/*var esRPC = AtributosPersonaCtrl.personaPortal.isRPC;
		
		if(esRPC=='1'){
			notificarRPC();
			return;
		}*/
		
		WizardModificacionPatronClasificacionCtrl.init('wizardModificacionClasificacionVentanilla', numeroRegistroPatronal,
				tipoTramite, "Modificaciones en el Seguro de Riesgo de Trabajo", context);
		WizardModificacionPatronClasificacionCtrl.abrir();
	},
	solicitarRIF: function () {
		var rfc 		= AtributosPersonaCtrl.personaPortal.rfc;
		var idPersona 	= AtributosPersonaCtrl.personaPortal.idPersona;
		
		WizardSolicitarRifCtrl.init('wizardModificacionClasificacionVentanilla', rfc, idPersona);
		WizardSolicitarRifCtrl.abrir();
	}
};



$("#modificarClasificacionPatron").live('click', function() {
	patronClasificacionPortlet.editar();
});

$("#btnCancelarRp").live('click', function() {
	$("#numRegistroPatronal").val("");
});

