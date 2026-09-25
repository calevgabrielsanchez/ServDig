
var sujetoObigadoTramite;

function ejecutarEnvioDeSolicitudconFirma(){
	inicializaSujetoTramiteGeneral();	
	sendToServer('../afiliacion/enviarSolicitud', sujetoObigadoTramite,
			callbackEnvioSolicitud, false);
}

function callbackEnvioSolicitud(response) {
	$.unblockUI();
	procesarRespuestaServer(response, callbackConfirmarEnvioSolicitud);
}

function callbackConfirmarEnvioSolicitud(response){
	if (!error) {		
		$("#btnEnviarSolicitud").hide();
		//if (isOperadorIMSS) {
//			$("#btnConcluirSolicitud").show();
		//}

		var rfcParam;
		if ($("#tipoPersonaFiscal").val() == "FISICA") {
			rfcParam = $("#fisica\\.rfc").val();
		} else {
			rfcParam = $("#moral\\.rfc").val();
		}		
		navegarTo('../sujetoObligado/recargarTramites?rfcParam=' + rfcParam,
				'formExitoFirma');
	}
}

function inicializaSujetoTramiteGeneral(){
	if (sujetoObigadoTramite == undefined || sujetoObigadoTramite == null) {
		sujetoObigadoTramite = new Object();
	}
	sujetoObigadoTramite.cveIdSujetoObligado = $("#cveIdSujetoObligado").val();
	sujetoObigadoTramite.tipoPersonaFiscal = $("#tipoPersonaFiscal").val();
	if (tipoPersonaFiscal == "FISICA") {
		sujetoObigadoTramite.fisica = new Object();
		sujetoObigadoTramite.fisica.idPersona = $("#fisica\\.idPersona").val();
		sujetoObigadoTramite.fisica.rfc = $("#fisica\\.rfc").val();
	} else {
		sujetoObigadoTramite.moral = new Object();
		sujetoObigadoTramite.moral.idPersona = $("#moral\\.idPersona").val();
		sujetoObigadoTramite.moral.rfc = $("#moral\\.rfc").val();
	}
}

