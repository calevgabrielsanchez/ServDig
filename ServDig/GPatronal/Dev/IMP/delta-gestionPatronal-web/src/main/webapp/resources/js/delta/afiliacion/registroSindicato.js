var sIdDialogMensajeCambioTab= "#mensajeCambioTab";
var oDialogMensajeCambioTab;

$(function() {
	habilitarTabs(true);
	aplicarComportamientoTramiteActivo();
	aplicarComportamientoTramiteRatificado();
	
//	if(!esNuevaSolicitud){
//		if(!isOperadorIMSS){
//			$("#divActualizarRegistroSindicato").hide();
//			$("#btnGuardarRegistroSindicato").hide();
//			$("#btnCancelarRegistroSindicato").hide();
//			$("#grupoRatificarRS").hide();
//			if(tramiteRegistroSindicatoRatificado){
//				$("#mensajeSindicatoRatificacion").show();
//			}else if(!tramiteRegistroSindicatoRatificado){
//				$("#mensajeSindicatoRatificacion").hide();
//			}
//		}else if(isOperadorIMSS){
//			aplicarComportamientoTramiteActivo();
//			aplicarComportamientoTramiteRatificado();
//		}
//	}	
	
	$("#txtFechaRegistroEdicion").datepicker({
		maxDate: "+0D",
		showOn: "button",
		buttonImage: context_path + "/static/resources/imagenes/calendar.gif",
		buttonImageOnly: true,
		dateFormat: "dd/mm/yy",
		changeMonth: true,
		changeYear: true,
		yearRange: '-112:+0'
	});
	$("#txtFechaRegistroEdicion").val($("#moral\\.registroSindicato\\.fechaRegistro").val());	
});


function aplicarComportamientoTramiteActivo(){
	if(tramiteRegistroSindicatoActivo){
		$("#divActualizarRegistroSindicato").show();
		$("#btnGuardarRegistroSindicato").hide();
		$("#btnCancelarRegistroSindicato").hide();
		$("#mensajeSindicatoRatificacion").hide();
	}else if(!tramiteRegistroSindicatoActivo){
		$("#divActualizarRegistroSindicato").hide();
		$("#grupoRatificarRS").show();
		$("#mensajeSindicatoRatificacion").hide();
	}
}


function aplicarComportamientoTramiteRatificado(){
	if(tramiteRegistroSindicatoRatificado){
		$("#divActualizarRegistroSindicato").hide();
		$("#btnGuardarRegistroSindicato").hide();
		$("#btnCancelarRegistroSindicato").hide();
		$("#chkRatificaRS").attr("checked","checked");
		$("#mensajeSindicatoRatificacion").show();
	}
}

function actualizarRS(){
	$("#btnGuardarRegistroSindicato").hide();
	$("#divActualizarRegistroSindicato").show();
	$("#btnRatificarRegistroSindicato").hide();
}

function cancelarRS(){
	$("#btnGuardarRegistroSindicato").show();
	$("#btnRatificarRegistroSindicato").show();
	$("#divActualizarRegistroSindicato").hide();
}


function guardarRS(){
	construirDialogoConfirmar('Guardar',guardarRegistroSindicato);
	$("#btnActualizarRegistroSindicato").show();
	$("#btnGuardarRegistroSindicato").hide();
}

function guardarRegistroSindicato() {
	construirSujetoObligadoRegistroSindicato();
	sendToServer('/afiliacion/actualizarTramite?tipoTramite='+registroSindicato+'&idSolicitud='+idSolicitud, sujetoObigadoTramite,
			callbackRegistroSindicatoTramite, false);

}

function construirSujetoObligadoRegistroSindicato(){
	if (sujetoObigadoTramite == undefined || sujetoObigadoTramite == null) {
		sujetoObigadoTramite = new Object(); // se queda como sujetoObigadoTramite en lugar de sujetoObligadoTramite
	}
	sujetoObigadoTramite.cveIdSujetoObligado=$("#cveIdSujetoObligado").val();
	sujetoObigadoTramite.tipoPersonaFiscal = $("#tipoPersonaFiscal").val();
	sujetoObigadoTramite.moral = new Object();
	sujetoObigadoTramite.moral.nombreComercial=$("#moral\\.nombreComercial").val();
	sujetoObigadoTramite.moral.idPersona=$("#moral\\.idPersona").val();
	
	sujetoObigadoTramite.moral.registroSindicato=new Object();
	
	sujetoObigadoTramite.moral.registroSindicato.numReferenciadocRegistro=$("#moral\\.registroSindicato\\.numReferenciadocRegistro").val();
	$("#moral\\.registroSindicato\\.fechaRegistro").val($("#txtFechaRegistroEdicion").val());
	sujetoObigadoTramite.moral.registroSindicato.fechaRegistro=$("#moral\\.registroSindicato\\.fechaRegistro").val();
	sujetoObigadoTramite.moral.registroSindicato.autoridadLaboral=
		$("#moral\\.registroSindicato\\.autoridadLaboral").val()!= undefined ?
		$("#moral\\.registroSindicato\\.autoridadLaboral").val().toUpperCase() : "";
	sujetoObigadoTramite.moral.registroSindicato.cveRegistroSindicato=$("#moral\\.registroSindicato\\.cveRegistroSindicato").val();
}

function callbackRegistroSindicatoTramite(response){
	$("#btnCancelarRegistroSindicato").hide();
	callbackEnviarTramite(response, 150, 750);
	evaluarBotonesSolicitud();
}

function callbackActualizacionSindicatoTramite(response){
	if(isOpRatificacion){
		checkedObject = $('#chkRatificaRS');//Se asigna a la variable global checkedObject el objeto check que se deseleccionara en caso de presionar cancelar en la pantalla de confirmación
		callbackValidacionTramiteActivo(response, ratificaTramiteSindicato, deseleccionarCheck);
		isOpRatificacion=false;
	}else{
		callbackValidacionTramiteActivo(response, guardarRegistroSindicato);
	}
	
}


function ratificarRS(){
	if (sujetoObigadoTramite == undefined || sujetoObigadoTramite == null) {
		sujetoObigadoTramite = new Object();
	}
	sujetoObigadoTramite.cveIdSujetoObligado=$("#cveIdSujetoObligado").val();
	sujetoObigadoTramite.tipoPersonaFiscal = $("#tipoPersonaFiscal").val();
	if (tipoPersonaFiscal == "FISICA") {
		sujetoObigadoTramite.fisica = new Object();
		sujetoObigadoTramite.fisica.nombreComercial=$("#fisica\\.nombreComercial").val();
		sujetoObigadoTramite.fisica.idPersona=$("#fisica\\.idPersona").val();
	} else {
		sujetoObigadoTramite.moral = new Object();
		sujetoObigadoTramite.moral.nombreComercial=$("#moral\\.nombreComercial").val();
		sujetoObigadoTramite.moral.idPersona=$("#moral\\.idPersona").val();
	}
	
	sendToServer('/afiliacion/ratificarTramite?tipoTramite='+tipoTramiteRegistroSindicato+'&idSolicitud='+idSolicitud,sujetoObigadoTramite,
			callbackRatificarRegistroSindicatoTramite, false);
}

function ratificaCheckRS(){
	
	var esRatificado = $("#chkRatificaRS").attr("checked");
	if(esRatificado == undefined){//Esto significa que el elemento no esta checado
		tramiteRegistroSindicatoRatificado=false;
		aplicarComportamientoTramiteActivo();
	}else{//Si esta checado
		var rfcEnviarValidacion;
		if (tipoPersonaFiscal == "FISICA") {
			rfcEnviarValidacion=$("#fisica\\.rfc").val();
		}else{
			rfcEnviarValidacion=$("#moral\\.rfc").val();
		}
		validaSolicitudTramiteActivo('/afiliacion/validarTramiteActivo?tipoTramite='+registroSindicato+'&idSolicitud='+idSolicitud+'&rfc='+rfcEnviarValidacion,callbackActualizacionSindicatoTramite);
		isOpRatificacion=true;
	}
	
}

function ratificaTramiteSindicato(){
	construirSujetoObligadoRegistroSindicato();
	sendToServer('/afiliacion/ratificarTramite?tipoTramite='+tipoTramiteRegistroSindicato+'&idSolicitud='+idSolicitud,sujetoObigadoTramite,
			callbackRatificarRegistroSindicatoTramite, false);
}

function callbackRatificarRegistroSindicatoTramite(response){
	$.unblockUI();
	callbackEnviarTramite(response, 200, 550);
	if(response.mensajeError!=undefined && response.mensajeError!=null){
		deseleccionarCheck();
		tramiteRegistroSindicatoRatificado=false;
	}else{
		$("#btnGuardarRegistroSindicato").hide();
		$("#btnRatificarRegistroSindicato").hide();
		$("#mensajeSindicatoRatificacion").show();
		tramiteRegistroSindicatoRatificado=true;
		tramiteRegistroSindicatoActivo=true;
		evaluarBotonesSolicitud();
	}
	
	aplicarComportamientoTramiteActivo();
	aplicarComportamientoTramiteRatificado();
}

function mensajeCambioTab(){
oDialogMensajeCambioTab = $(
		sIdDialogMensajeCambioTab).dialog( {
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 150,
		width : 350,
		buttons : {
			"Aceptar" : function() {
				tramiteRegistroSindicatoActivo=false;
				tramiteEscrituraConstitutivaActivo=true;
				habilitarTabs(false);
				$(this).dialog('close');
			},
			"Cancelar": function(){
				$(this).dialog('close');
			}			
		}
	});
	oDialogMensajeCambioTab.dialog("open");
}
