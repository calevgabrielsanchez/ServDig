var sIdDialogMensajeCambioTabEC= "#mensajeCambioTab";
var oDialogMensajeCambioTabEC;

var sIdDialogMsgDescartarNumEscritura= "#msgDescartarNumEscritura";
var oDialogMsgDescartarNumEscritura;

var sIdDialogMsgDescartarSeccion = "#msgDescartarSeccion";
var oDialogMsgDescartarSeccion;



$(function() {	
	
	construirDialogoMsgDescartarSeccion();
	construirDialogoMsgDescartarNumEscritura();
		
	habilitarTabs(true);
	evaluarBotonesTramiteEscrituraActivo();
	evaluarBotonesTramiteEscrituraRatificado();	
	$("#txtFechaRegistroEdicionEC").datepicker({
		maxDate: "+0D",
		showOn: "button",
		buttonImage: context_path + "/static/resources/imagenes/calendar.gif",
		buttonImageOnly: true,
		dateFormat: "dd/mm/yy",
		changeMonth: true,
		changeYear: true,
		yearRange: '-112:+0'
	});	
	$("#txtFechaRegistroEdicionEC").val($("#moral\\.escrituraConstitutiva\\.fechaExpedicion").val());
	var dateVigenteEC="";
	if ($("#labelfechaEC").html()!=""){
		dateVigenteEC=$("#labelfechaEC").html();		
		$("#labelfechaEC").html((dateVigenteEC.substr(8,2)+"/"+dateVigenteEC.substr(5,2)+"/"+dateVigenteEC.substr(0,4)));
	}		
});


function evaluarBotonesTramiteEscrituraActivo(){
	if(tramiteEscrituraConstitutivaActivo){
		$("#divActualizarEscrituraConstitutiva").show();
		$("#btnGuardarEscrituraConstitutiva").hide();
		$("#btnCancelarEscrituraConstitutiva").hide();
		$("#mensajeEscrituraRatificacion").hide();
	}else if(!tramiteEscrituraConstitutivaActivo){
		$("#divActualizarEscrituraConstitutiva").hide();
		$("#mensajeEscrituraRatificacion").hide();
	}
}
	
function evaluarBotonesTramiteEscrituraRatificado(){
	if(tramiteEscrituraConstitutivaRatificado){
		$("#divActualizarEscrituraConstitutiva").hide();
		$("#btnGuardarEscrituraConstitutiva").hide();
		$("#btnCancelarEscrituraConstitutiva").hide();
		$("#chkRatificaEC").attr("checked","checked");
		$("#mensajeEscrituraRatificacion").show();
	}
}

function actualizarEC(){
	$("#btnGuardarEscrituraConstitutiva").hide();
	$("#divActualizarEscrituraConstitutiva").show();
}

function cancelarEC(){
	$("#btnGuardarEscrituraConstitutiva").show();
	$("#divActualizarEscrituraConstitutiva").hide();
}

function guardarEC(){
	construirDialogoConfirmar('Guardar',guardarEscrituraConstitutiva);
	$("#btnActualizarEscrituraConstitutiva").show();
	$("#btnGuardarEscrituraConstitutiva").hide();
}

function guardarEscrituraConstitutiva() {
	construirSujetoObligadoEscrituraConstitutiva();
	sendToServer('/afiliacion/actualizarTramite?tipoTramite='+escrituraConstitutiva+'&idSolicitud='+idSolicitud, sujetoObigadoTramite,
			callbackEscrituraTramite, false);

}

function construirSujetoObligadoEscrituraConstitutiva(){
	if (sujetoObigadoTramite == undefined || sujetoObigadoTramite == null) {
		sujetoObigadoTramite = new Object();
	}
	sujetoObigadoTramite.cveIdSujetoObligado=$("#cveIdSujetoObligado").val();
	sujetoObigadoTramite.tipoPersonaFiscal = $("#tipoPersonaFiscal").val();
	sujetoObigadoTramite.moral = new Object();
	sujetoObigadoTramite.moral.nombreComercial=$("#moral\\.nombreComercial").val();
	sujetoObigadoTramite.moral.idPersona=$("#moral\\.idPersona").val();
	
	sujetoObigadoTramite.moral.escrituraConstitutiva=new Object();
	sujetoObigadoTramite.moral.escrituraConstitutiva.lugarExpedicion=new Object();
	sujetoObigadoTramite.moral.escrituraConstitutiva.lugarExpedicion.entidadFederativa=new Object();
	
	sujetoObigadoTramite.moral.escrituraConstitutiva.lugarExpedicion.entidadFederativa.clave=$("#moral\\.escrituraConstitutiva\\.lugarExpedicion\\.entidadFederativa\\.clave").val();
	sujetoObigadoTramite.moral.escrituraConstitutiva.lugarExpedicion.entidadFederativa.nombre=$("#moral\\.escrituraConstitutiva\\.lugarExpedicion\\.entidadFederativa\\.clave option:selected").text();
	sujetoObigadoTramite.moral.escrituraConstitutiva.lugarExpedicion.clave=$("#moral\\.escrituraConstitutiva\\.lugarExpedicion\\.clave").val();
	sujetoObigadoTramite.moral.escrituraConstitutiva.lugarExpedicion.nombre=$("#moral\\.escrituraConstitutiva\\.lugarExpedicion\\.clave option:selected").text();
	sujetoObigadoTramite.moral.escrituraConstitutiva.numEscritura=$("#moral\\.escrituraConstitutiva\\.numEscritura").val();
	sujetoObigadoTramite.moral.escrituraConstitutiva.numNotaria=$("#moral\\.escrituraConstitutiva\\.numNotaria").val();
	sujetoObigadoTramite.moral.escrituraConstitutiva.folioMercantil=$("#moral\\.escrituraConstitutiva\\.folioMercantil").val();
	sujetoObigadoTramite.moral.escrituraConstitutiva.seccion=
		$("#moral\\.escrituraConstitutiva\\.seccion").val() != undefined ?
		$("#moral\\.escrituraConstitutiva\\.seccion").val().toUpperCase() : "";
	sujetoObigadoTramite.moral.escrituraConstitutiva.partida=
		$("#moral\\.escrituraConstitutiva\\.partida").val() != undefined ? 
		$("#moral\\.escrituraConstitutiva\\.partida").val().toUpperCase() : "";
	sujetoObigadoTramite.moral.escrituraConstitutiva.volumen=
		$("#moral\\.escrituraConstitutiva\\.volumen").val() != undefined ?
		$("#moral\\.escrituraConstitutiva\\.volumen").val().toUpperCase() : "";
	sujetoObigadoTramite.moral.escrituraConstitutiva.foja=
		$("#moral\\.escrituraConstitutiva\\.foja").val()!=undefined ? 
		$("#moral\\.escrituraConstitutiva\\.foja").val().toUpperCase() : "";
	sujetoObigadoTramite.moral.escrituraConstitutiva.fechaExpedicion=$("#txtFechaRegistroEdicionEC").val();
	sujetoObigadoTramite.moral.escrituraConstitutiva.cveEscrituraConstitutiva=$("#moral\\.escrituraConstitutiva\\.cveEscrituraConstitutiva").val();
}

function callbackEscrituraTramite(response){
	callbackEnviarTramite(response, 150, 750);
	$("#btnCancelarEscrituraConstitutiva").hide();
	evaluarBotonesSolicitud();
	//	oTableTramites.fnDraw();
}

function callbackActualizacionEscrituraTramite(response){
	if(isOpRatificacion){
		checkedObject = $('#chkRatificaEC');//Se asigna a la variable global checkedObject el objeto check que se deseleccionara en caso de presionar cancelar en la pantalla de confirmación
		callbackValidacionTramiteActivo(response, ratificaTramiteEscritura, deseleccionarCheck);
		isOpRatificacion=false;
	}else{
		callbackValidacionTramiteActivo(response, guardarEscrituraConstitutiva);
	}
}

function ratificaEC(){
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
	$.blockUI();
	sendToServer('/afiliacion/ratificarTramite?tipoTramite='+tipoTramiteEscrituraConstitutiva+'&idSolicitud='+idSolicitud,sujetoObigadoTramite,
			callbackRatificarEscrituraConstitutivaTramite, false);
}


function ratificaCheckEC(){
	var esRatificado = $("#chkRatificaEC").attr("checked");
	if(esRatificado == undefined){//Esto significa que el elemento no esta checado
		tramiteEscrituraConstitutivaRatificado=false;
		evaluarBotonesTramiteEscrituraActivo();
	}else{//Si esta checado
		isOpRatificacion=true;
		var rfcEnviarValidacion;
		if (tipoPersonaFiscal == "FISICA") {
			rfcEnviarValidacion=$("#fisica\\.rfc").val();
		}else{
			rfcEnviarValidacion=$("#moral\\.rfc").val();
		}
		validaSolicitudTramiteActivo('/afiliacion/validarTramiteActivo?tipoTramite='+escrituraConstitutiva+'&idSolicitud='+idSolicitud+'&rfc='+rfcEnviarValidacion,callbackActualizacionEscrituraTramite);
	}
}

function ratificaTramiteEscritura(){
	construirSujetoObligadoEscrituraConstitutiva();
	sendToServer('/afiliacion/ratificarTramite?tipoTramite='+tipoTramiteEscrituraConstitutiva+'&idSolicitud='+idSolicitud,sujetoObigadoTramite,
			callbackRatificarEscrituraConstitutivaTramite, false);
	
}

function callbackRatificarEscrituraConstitutivaTramite(response){
	$.unblockUI();
	callbackEnviarTramite(response, 200, 550);
	if(response.mensajeError!=undefined && response.mensajeError!=null){
		deseleccionarCheck();
		tramiteEscrituraConstitutivaRatificado=false;
	}else{
		$("#btnGuardarEscrituraConstitutiva").hide();
		$("#btnRatificarTramiteEscrituraConstitutiva").hide();
		$("#mensajeEscrituraRatificacion").show();
		tramiteEscrituraConstitutivaRatificado=true;
		tramiteEscrituraConstitutivaActivo=true;
		evaluarBotonesSolicitud();
	}
	
	evaluarBotonesTramiteEscrituraActivo();
	evaluarBotonesTramiteEscrituraRatificado();
}



/**
 * Al ser invocado inhabilita y  limpia los campos seccion,	partida, volumen y foja
 */
function inhabilitarCapura1(){
	// limpamos
	$("#escrituraConstitutivaForm #seccion").val("");
	$("#escrituraConstitutivaForm #partida").val("");
	$("#escrituraConstitutivaForm #volumen").val("");
	$("#escrituraConstitutivaForm #foja").val("");
}

/**
 * Al ser invocado inhabilita y  limpia el campo folioMercantil
 */
function inhabilitarCapura2(){
	// limpiamos folioMercantil
	$("#escrituraConstitutivaForm #folioMercantil").val("");
}

function mensajeCambioTabEC(){
oDialogMensajeCambioTabEC = $(
		sIdDialogMensajeCambioTabEC).dialog( {
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 150,
		width : 350,
		buttons : {
			"Aceptar" : function() {
				tramiteRegistroSindicatoActivo=true;
				tramiteEscrituraConstitutivaActivo=false;
				habilitarTabs(false);
				$(this).dialog('close');
			},
			"Cancelar": function(){
				$(this).dialog('close');
			}			
		}
	});
	oDialogMensajeCambioTabEC.dialog("open");
}

function verificar1(){
	if (document.getElementById("moral\.escrituraConstitutiva\.seccion").value != ""
		|| document.getElementById("moral\.escrituraConstitutiva\.partida").value != ""
		|| document.getElementById("moral\.escrituraConstitutiva\.volumen").value != ""
		|| document.getElementById("moral\.escrituraConstitutiva\.foja").value != ""){
		oDialogMsgDescartarSeccion.dialog('open');
	}
}

function verificar2(){
	if (document.getElementById("moral\.escrituraConstitutiva\.folioMercantil").value != "") {
		oDialogMsgDescartarNumEscritura.dialog('open');
	}
}

function descartarNumEscritura(){
	document.getElementById("moral\.escrituraConstitutiva\.folioMercantil").value = "";
//	document.getElementById("moral\.escrituraConstitutiva\.folioMercantil").readOnly = true;
//	
//	document.getElementById("moral\.escrituraConstitutiva\.seccion").readOnly = false;
//	document.getElementById("moral\.escrituraConstitutiva\.partida").readOnly = false;
//	document.getElementById("moral\.escrituraConstitutiva\.volumen").readOnly = false;
//	document.getElementById("moral\.escrituraConstitutiva\.foja").readOnly = false;
	oDialogMsgDescartarNumEscritura.dialog('close');
	$("#moral\\.escrituraConstitutiva\\.seccion").focus();
}

function descartarSeccion(){
	document.getElementById("moral\.escrituraConstitutiva\.seccion").value = "";
	document.getElementById("moral\.escrituraConstitutiva\.partida").value = "";
	document.getElementById("moral\.escrituraConstitutiva\.volumen").value = "";
	document.getElementById("moral\.escrituraConstitutiva\.foja").value = "";
	
//	document.getElementById("moral\.escrituraConstitutiva\.seccion").readOnly = true;
//	document.getElementById("moral\.escrituraConstitutiva\.partida").readOnly = true;
//	document.getElementById("moral\.escrituraConstitutiva\.volumen").readOnly = true;
//	document.getElementById("moral\.escrituraConstitutiva\.foja").readOnly = true;
//	
//	document.getElementById("moral\.escrituraConstitutiva\.numEscritura").readOnly = false;
	oDialogMsgDescartarSeccion.dialog('close');
	$('#moral\\.escrituraConstitutiva\\.folioMercantil').focus();
}

function construirDialogoMsgDescartarNumEscritura(){	
	
	oDialogMsgDescartarNumEscritura =	$( sIdDialogMsgDescartarNumEscritura).dialog({
		autoOpen:false,
		resizable: false,
		width:720,
		height:150,
		modal: true,		
		buttons: {
			"Proceder": function(){
				descartarNumEscritura();
			},
			'Cancelar': function(){
					$( this ).dialog( "close" );
			}
		}
	});
}

function construirDialogoMsgDescartarSeccion(){	
	
	oDialogMsgDescartarSeccion =	$( sIdDialogMsgDescartarSeccion).dialog({
		autoOpen:false,
		resizable: false,
		width:720,
		height:150,
		modal: true,		
		buttons: {
			"Proceder": function(){
				descartarSeccion();
			},
			'Cancelar': function(){
					$( this ).dialog( "close" );
			}
		}
	});
}