var objDialogoMensajesGenericos;
var operacion;
var idSolicitante;
function evaluarBotones(){
	$("#dialogoRechazo").hide();
	switch(estadoSolicitud){
		case idEstatusParaEdicionBackOffice: 
			$("#btnEditar").hide();
			$("#btnEditarBackOffice").show();
			$("#btnRechazar").show();
			$("#btnConcluirPresencialmente").hide();
			$("#btnConcluirBackOffice").show();
			$("#btnEnviarVentanilla").show();
			
//			$("#btnEditar").removeAttr("disabled");
//			$("#btnRechazar").removeAttr("disabled");
//			$("#btnConcluirBackOffice").removeAttr("disabled");
//			$("#btnEnviarVentanilla").removeAttr("disabled");
//			$("#btnConcluirPresencialmente").attr("disabled", true);
//			$("#btnConcluirPresencialmente").hide();
//			$("#btnConcluirBackOffice").show();
//			$("#btnEnviarVentanilla").show();
			break;
		case idEstatusParaEdicionVentanilla:
			$("#btnEditar").show();
			$("#btnEditarBackOffice").hide();
			$("#btnRechazar").show();
			$("#btnConcluirBackOffice").hide();
			$("#btnEnviarVentanilla").hide();
			$("#btnConcluirPresencialmente").show();
			
//			$("#btnEditar").removeAttr("disabled");
//			$("#btnRechazar").removeAttr("disabled");
//			$("#btnConcluirPresencialmente").removeAttr("disabled");
//			$("#btnConcluirBackOffice").attr("disabled", true);
//			$("#btnEnviarVentanilla").attr("disabled", true);
//			$("#btnConcluirBackOffice").hide();
//			$("#btnEnviarVentanilla").hide();
//			$("#btnConcluirPresencialmente").show();
			break;
		default:
			$("#btnEditar").hide();
			$("#btnRechazar").hide();
			$("#btnEditarBackOffice").hide();
			$("#btnConcluirPresencialmente").hide();
			$("#btnConcluirBackOffice").hide();
			$("#btnEnviarVentanilla").hide();
			
//			$("#btnEditar").attr("disabled", true);
//			$("#btnRechazar").attr("disabled", true);
//			$("#btnConcluirPresencialmente").attr("disabled", true);
//			$("#btnConcluirBackOffice").attr("disabled", true);
//			$("#btnEnviarVentanilla").attr("disabled", true);
//			$("#btnConcluirBackOffice").show();
//			$("#btnEnviarVentanilla").show();
//			$("#btnConcluirPresencialmente").show();
			break;
	}
	if(!isOperadorIMSS){
		$("#btnEditar").hide();
		$("#btnRechazar").hide();
		$("#btnConcluirBackOffice").hide();
		$("#btnEnviarVentanilla").hide();
		$("#btnConcluirPresencialmente").hide();
		$("#btnConcluirPresencialmente").hide();
		$("#btnConcluirBackOffice").hide();
		$("#btnEnviarVentanilla").hide();
	}
}

function despliegaMensajeConfirmacion(estatusPorAplicar){
	operacion = estatusPorAplicar;
	$("#embebedPDF").hide();
	var oDialogo;
	construirDialogoGenericoDeConfirmacion("#dialogoMensajes", oDialogo, "Confirmaci\u00F3n", "\u00BFEst\u00E1 seguro que desea rechazar la solicitud?", false, 
			despliegaDialogoRazonRechazo);

}

function despliegaDialogoRazonRechazo(response){
	var oDialogo;
	construirDialogoParaPagina("#dialogoRechazo", oDialogo, "Raz\u00F3n de rechazo", false, 
			validaSeleccionRechazo);
}

function validaSeleccionRechazo(){
	var razonCancelacion = $("#idRazonCancelacion").val();
	if(razonCancelacion=='-1'){
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Debe seleccionar la raz\u00F3n del rechazo.", 
				true, despliegaDialogoRazonRechazo, undefined, 150, 450);
	}else{
		actualizarSolicitud(idEstatusRechazada);
	}
}

function validaDetalleSolicitudTramiteActivo(estatus){
	$.blockUI();
	$("#embebedPDF").hide();
	operacion = estatus;
	var solicitud = new Object();
	solicitud.estadoSolicitud= new Object();
	solicitud.estadoSolicitud.idEstadoSolicitud=operacion;
	solicitud.solicitudId=$("#solicitudId").val();
	$("#estadoSolicitud\\.idEstadoSolicitud").val(operacion);
	sendToServer('/solicitud/validarEdicionTramite',solicitud,callbackFiltroSolicitudTramiteActivo, true);
}

function callbackFiltroSolicitudTramiteActivo(response){
	procesarRespuestaServer(response, actualizarSolicitud, 200, 550, callbackForXButtonConfirmacion, true);
	$.unblockUI();
}

function especificarSolicitante(){
	var oDialogo;
	construirDialogoParaPagina("#formaSolicitadoPor", oDialogo, "Especificar Solicitante", false, 
			callbackSolicitadoPor, undefined, 250, 600);
}

function callbackSolicitadoPor(response){
	var selectedRadio = getSelectedRadioButton(document.getElementsByName("radioSolicitadoPor"));
	idSolicitante=$("#solicitadoPorForm #idRepresentante").val();
	var validacion=true;
	if(selectedRadio.value==rolRepresentante){
		if(idSolicitante==-1){
			var oDialogo;
			construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Debe seleccionar el nombre del representante legal que se ha presentado en ventanilla y solicitado la conclusi\u00F3n.", true, callbackValidacionSeleccionadoPor, undefined, 150, 450);
			validacion = false;
		}
	}
	
	if(validacion){
		$.blockUI();
		actualizarSolicitud(idEstatusProcesadaEnVentanilla);
	}
	
}



function actualizarSolicitud(estatus){
	if(estatus != undefined)
		operacion = estatus;	
	
	if(idSolicitudPreviaActiva!=undefined){
		var solicitud = new Object();
		sendToServer('/solicitud/actualizarEstatusAPendienteAsignar?idSolicitud='+idSolicitudPreviaActiva,solicitud,ejecutarOperacionRequerida, true);
	}else{
		$("#embebedPDF").hide();
		ejecutarOperacionRequerida();
	}
}

function callbackForXButtonConfirmacion(){
	$("#embebedPDF").show();
}

function ejecutarOperacionRequerida(){
	$.blockUI();
	$("#estadoSolicitud\\.idEstadoSolicitud").val(operacion);
	if(operacion == idEstatusProcesadaEnVentanilla || operacion == idEstatusProcesadaEnBackoffice){
		var solicitud = $("#solicitudForm").toObject(true);
		var rolSolicitanteSeleccionado = getSelectedRadioButton(document.getElementsByName("radioSolicitadoPor"));
		var idPerfil = 0;
		if(rolSolicitanteSeleccionado.value = rolRepresentante)
			idPerfil = 8;
		else if(rolSolicitanteSeleccionado.value = rolPatron)
			idPerfil = 2;
			
		solicitud.solicitante = new Object();
		solicitud.solicitante.perfilUsuario= new Object();
		solicitud.solicitante.perfilUsuario.idPerfilUsuario=idPerfil;
		solicitud.solicitante.fisica=new Object();
		solicitud.solicitante.fisica.idPersona = idSolicitante;
		sendToServer('/solicitud/concluirSolicitud?idSolicitud='+idSolicitudPreviaActiva,solicitud,callbackConcluirSolicitud, true);	
	}else{
		//La razon de cancelación solo tiene valor cuandos e rechaza una solicitud
		var razonCancelacion = $("#idRazonCancelacion").val();
		$("#razonCancelacion\\.idRazonCancelacion").val(razonCancelacion);
		document.getElementById('solicitudForm').action=context+'/solicitud/actualizarEstatus';
		document.getElementById('solicitudForm').action.target='_self';
		$("#solicitudForm").submit();
	}
}

function callbackConcluirSolicitud(response){
	$.unblockUI();
//	document.getElementById('formReporteModificacionPatronal').method = 'POST';
//	document.getElementById('formReporteModificacionPatronal').target = '_blank';
//	document.getElementById('formReporteModificacionPatronal').action = context+'/afiliacion/procesarInformacionAcuseAfiliacion?origen=COMPROBANTE';
//	document.getElementById('formReporteModificacionPatronal').submit();
	
	if(response.imprimirAvisoClasificacion){
		document.getElementById('formReporteClasificacion').method = 'POST';
		document.getElementById('formReporteClasificacion').target = '_self';
		document.getElementById('formReporteClasificacion').action = context+'/clasificacion/presentarAcuse';
		document.getElementById('formReporteClasificacion').submit();
	}
	
	var oDialogo;
	construirDialogoGenerico("#dialogoMensajes", oDialogo, "Confirmaci\u00F3n", "Su solicitud ha sido conclu\u00EDda satisfactoriamente.", false, 
			callbackConfirmacionConcluirDetalleSolicitud, callbackConfirmacionConcluirDetalleSolicitud);
}

function callbackConfirmacionConcluirDetalleSolicitud(response){
	$.blockUI();
	document.getElementById('solicitudForm').action=context+'/solicitud/mostrarBusquedaRfc';
	document.getElementById('solicitudForm').submit();
}

function abrirDocumentoDeTramite(solicitud,folio,tipoDocumento){
	document.getElementById('solicitudDocumentoForm').action=context+'/solicitud/mostrarDocumento?idSolicitud='+solicitud+'&noFolio='+folio+'&tipoDocumento='+tipoDocumento;
//	alert(document.getElementById('solicitudDocumentoForm').action);
	document.getElementById('solicitudDocumentoForm').submit();
}

$(function() {	
	evaluarBotones();
});



