function cargarSolicitud(){
	navegarTo('/cmp/clasificacion/cargarSolicitudSRT?idTipoTramite='+idTipoTramite+'&idSolicitud='+idSolicitud, 'clasificacionInvokerForm');
}


function cancelarSolicitudModSRT(){
	var sujetoTramite = new Object();
	sujetoTramite.modalidad = new Object();
	sujetoTramite.cveIdPatronSujetoObligado=$("#clasificacionInvokerForm #cveIdSujetoObligado").val();
	sujetoTramite.numeroRegistroPatronal=$("#clasificacionInvokerForm #numeroRegistroPatronal").val();
	sujetoTramite.modalidad.numModalidad=$("#clasificacionInvokerForm #modalidad\\.numModalidad").val();
	sujetoTramite.digVerificador=$("#clasificacionInvokerForm #digVerificador").val();
	sujetoTramite.tipoPersonaFiscal=$("#clasificacionInvokerForm #tipoPersonaFiscal").val();
	if(sujetoTramite.tipoPersonaFiscal=='FISICA'){
		sujetoTramite.fisica = new Object();
		sujetoTramite.fisica.rfc=$("#clasificacionInvokerForm #fisica\\.rfc").val();
		sujetoTramite.fisica.idPersona=$("#clasificacionInvokerForm #fisica\\.idPersona").val();
	}else{
		sujetoTramite.moral = new Object();
		sujetoTramite.moral.rfc=$("#clasificacionInvokerForm #moral\\.rfc").val();
		sujetoTramite.moral.idPersona=$("#clasificacionInvokerForm #moral\\.idPersona").val();
	}
	
	sendToServer('/cmp/clasificacion/cancelarSolicitudClasificacion?idSolicitud='+idSolicitud,
			sujetoTramite,
			callbackCancelarSolicitud, false);
}

function callbackCancelarSolicitud(response){
	procesarRespuestaServer(response,
			callbackConfirmarCancelar, 500, 600);
}

function callbackConfirmarCancelar(response){
	var numeroRegistroPatronalCompleto = 	$("#clasificacionInvokerForm #numeroRegistroPatronal").val() 
										+	$("#clasificacionInvokerForm #modalidad\\.numModalidad").val() ;	
	navegarTo('/cmp/clasificacion/msrt/'+numeroRegistroPatronalCompleto, 'clasificacionInvokerForm');
}