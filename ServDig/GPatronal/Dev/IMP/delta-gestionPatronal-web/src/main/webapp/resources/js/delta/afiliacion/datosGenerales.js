var icaFisicaCtrl;
var icaMoralCtrl;
var modManualFisicaCtrl;
var modManualMoralCtrl;

var oTableMediosFiscales;
var columnasMedioContactoFiscales = [ {
	mDataProp : "clave",
	bVisible : false
}, {
	sTitle : "Medio Contacto",
	mDataProp : "tipoMedioContacto.descripcion",
	bVisible : true
}, {
	sTitle : "Descripci\u00F3n",
	mDataProp : "desFormaContacto",
	bVisible : true
} ]; 

/**
 * 
 */

$(function() {
	$("#nombreComercialForm #moral\\.tipoSociedad\\.idTipoSociedad").attr("disabled","disabled");
	evaluarTramiteDenominacionActivo();
	evaluarTramiteDenominacionRatificado();
	inicializaDatosContactoFiscales();
	if(esPatronFisico){
		configurarComponenteModificacionManualFisica();
		configurarBusquedaICAFisica();
	}else{
		configurarComponenteModificacionManualMoral();
		configurarBusquedaICAMoral();
	}
});

function evaluarTramiteDenominacionActivo(){
	if(tramiteDenominacionActivo){
		$("#divNombreComercial").show();
		$("#seccionMediosFiscales").show();
		//$("#btnGuardar").hide();
		$("#btnCancelar").hide();
		$("#mesajeRatificacion").hide();
	}else if(!tramiteDenominacionActivo) {
		$("#divNombreComercial").hide();
		$("#mesajeRatificacion").hide();
	}
}

function evaluarTramiteDenominacionRatificado(){
	if(tramiteDenominacionRatificado){
		$("#divNombreComercial").hide();
		$("#btnGuardar").hide();
		$("#btnCancelar").hide();
		$("#mesajeRatificacion").show();
		$("#chkRatifica").attr("checked","checked");
	}
}

function actualizarNC(){
	if(esPatronFisico){
//		mostrarModManualFisica();
		mostrarBusquedaICAFisica();
	}else{
//		mostrarModManualMoral();
		mostrarBusquedaICAMoral();
	}
	
//	$("#btnGuardar").hide();
//	$("#divNombreComercial").show();
}

function cancelarNC(){
	$("#btnGuardar").show();
	$("#divNombreComercial").hide();
}


function guardarNC(){
	construirDialogoConfirmar('Guardar',guardarNombreComercial);
	$("#btnActualizarNC").show();
	$("#btnGuardarNC").hide();
	$("#divNombreComercial #fisicaNombreComercial").attr("readonly", true);
	$("#divNombreComercial #moralNombreComercial").attr("readonly", true);
}


function guardarNombreComercial() {
	inicializarObjetoTramite();
	sendToServer('/afiliacion/actualizarTramite?tipoTramite='+denominacionSocial+'&idSolicitud='+idSolicitud,sujetoObigadoTramite,
			callbackDenominacionTramite, false);

}


function callbackDenominacionTramite(response){
	callbackEnviarTramite(response, 150, 750);
//	procesarRespuestaServerDialogoParametrizado(response, undefined, 50,500);
	$("#btnCancelar").hide();
	evaluarBotonesSolicitud();
//	oTableTramites.fnDraw();
}

function callbackActualizacionDenominacionTramite(response){
	if(isOpRatificacion){
		checkedObject = $('#chkRatifica');
		callbackValidacionTramiteActivo(response, ratificaTramiteNRS, deseleccionarCheck);
		isOpRatificacion=false;//Se inicializa nuevamente la variable global
	}else{
		callbackValidacionTramiteActivo(response, guardarNombreComercial);
	}
	
}

//function ratificaNC(){
//	inicializarObjetoTramite();
//	sendToServer('/afiliacion/ratificarTramite?tipoTramite='+tipoTramiteDenominacionSocial,sujetoObigadoTramite,
//			callbackRatificarDenominacionTramite, false);
//}

function ratificaCheckNC(){
	var esRatificado = $("#chkRatifica").attr("checked");
	if(esRatificado == undefined){//Esto significa que el elemento no esta checado
		tramiteDenominacionRatificado=false;
		evaluarTramiteDenominacionActivo();
	}else{//Si esta checado
		isOpRatificacion=true;
		var rfcEnviarValidacion;
		if (tipoPersonaFiscal == "FISICA") {
			rfcEnviarValidacion=$("#fisica\\.rfc").val();
		}else{
			rfcEnviarValidacion=$("#moral\\.rfc").val();
		}
		validaSolicitudTramiteActivo('/afiliacion/validarTramiteActivo?tipoTramite='+denominacionSocial+'&idSolicitud='+idSolicitud+'&rfc='+rfcEnviarValidacion,callbackActualizacionDenominacionTramite);
	}
}

function ratificaTramiteNRS(){
	inicializarObjetoTramite();
	sendToServer('/afiliacion/ratificarTramite?tipoTramite='+tipoTramiteDenominacionSocial+'&idSolicitud='+idSolicitud,sujetoObigadoTramite,
			callbackRatificarDenominacionTramite, false);
}

function callbackRatificarDenominacionTramite(response){
	callbackEnviarTramite(response, 200, 550);
	if(response.mensajeError!=undefined && response.mensajeError!=null){
		deseleccionarCheck();
		tramiteDenominacionRatificado=false;;
	}else{
		$("#btnGuardar").hide();
		$("#btnRatificar").hide();
		$("#mesajeRatificacion").show();
		tramiteDenominacionRatificado=true;
		tramiteDenominacionActivo=true;
		evaluarBotonesSolicitud();
	}
	
	evaluarTramiteDenominacionActivo();
	evaluarTramiteDenominacionRatificado();
	
	$.unblockUI();
}

function inicializarObjetoTramite(){
	if (sujetoObigadoTramite == undefined || sujetoObigadoTramite == null) {
		sujetoObigadoTramite = new Object();
	}
	sujetoObigadoTramite.cveIdSujetoObligado=$("#cveIdSujetoObligado").val();
	sujetoObigadoTramite.tipoPersonaFiscal = $("#tipoPersonaFiscal").val();
	
	var domicilioFiscal = obtenerDomicilioFiscal();
	
	if (tipoPersonaFiscal == "FISICA") {
		sujetoObigadoTramite.fisica = new Object();
		
		sujetoObigadoTramite.fisica.primerApellido=$("#fisica\\.primerApellido").val();
		sujetoObigadoTramite.fisica.segundoApellido=$("#fisica\\.segundoApellido").val();
		sujetoObigadoTramite.fisica.nombre=$("#fisica\\.nombre").val();
		sujetoObigadoTramite.fisica.curp=$("#fisica\\.curp").val();
		sujetoObigadoTramite.fisica.rfc=$("#fisica\\.rfc").val();
		
		sujetoObigadoTramite.fisica.idPersona=$("#fisica\\.idPersona").val();
		sujetoObigadoTramite.fisica.domicilioFiscal = domicilioFiscal;
		sujetoObigadoTramite.fisica.mediosContactoFiscales=obtenerListaMediosContactoFiscales();
		
		
	} else {
		sujetoObigadoTramite.moral = new Object();
		sujetoObigadoTramite.moral.tipoSociedad = new Object();
		
		//sujetoObigadoTramite.moral.nombreComercial=$("#moral\\.nombreComercial").val();
		sujetoObigadoTramite.moral.rfc=$("#moral\\.rfc").val();
		sujetoObigadoTramite.moral.razonSocial=$("#moral\\.razonSocial").val();
		//alert($("#moral\\.tipoSociedad\\.idTipoSociedad").val());
		//alert($("#moral\\.tipoSociedad\\.idTipoSociedad option:selected").text());
		sujetoObigadoTramite.moral.tipoSociedad.idTipoSociedad=$("#moral\\.tipoSociedad\\.idTipoSociedad").val();
		sujetoObigadoTramite.moral.tipoSociedad.descripcionAbreviada=$("#moral\\.tipoSociedad\\.idTipoSociedad option:selected").text();
		
		sujetoObigadoTramite.moral.idPersona=$("#moral\\.idPersona").val();
		sujetoObigadoTramite.moral.domicilioFiscal = domicilioFiscal;
		sujetoObigadoTramite.moral.mediosContactoFiscales=obtenerListaMediosContactoFiscales();
	}
	sujetoObigadoTramite.domicilioFiscal=domicilioFiscal;
}

function obtenerDomicilioFiscal(){
	var domicilioFiscal = new Object();
	domicilioFiscal.vialidadPrimaria = new Object();
	domicilioFiscal.vialidadPrimaria.tipoVialidad = new Object();
	domicilioFiscal.asentamiento = new Object();
	domicilioFiscal.asentamiento.codigoPostal = new Object();
	domicilioFiscal.asentamiento.localidad = new Object();
	domicilioFiscal.asentamiento.localidad.municipio = new Object();
	domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa = new Object();
	
	var origenDomicilio = null;
	if(esPatronFisico){
		origenDomicilio ='#fisica\\.domicilioFiscal\\.';
	}else{
		origenDomicilio ='#moral\\.domicilioFiscal\\.';	
	}
	domicilioFiscal.calle = $(origenDomicilio+'calle').val();
	domicilioFiscal.colonia = $(origenDomicilio+'colonia').val();
	domicilioFiscal.descripcion = $(origenDomicilio+'descripcion').val();
	domicilioFiscal.numExterior1 = $(origenDomicilio+'numExterior1').val();
	domicilioFiscal.numExterior2 = $(origenDomicilio+'numExterior2').val();
	domicilioFiscal.numExteriorAlf = $(origenDomicilio+'numExteriorAlf').val();
	domicilioFiscal.numInterior = $(origenDomicilio+'numInterior').val();
	domicilioFiscal.numInteriorAlf = $(origenDomicilio+'numInteriorAlf').val();
	domicilioFiscal.vialidadPrimaria.clave = $(origenDomicilio+'vialidadPrimaria\\.clave').val();
	domicilioFiscal.vialidadPrimaria.nombre = $(origenDomicilio+'vialidadPrimaria\\.nombre').val();
	domicilioFiscal.vialidadPrimaria.tipoVialidad.clave = $(origenDomicilio+'vialidadPrimaria\\.tipoVialidad.clave').val();
	domicilioFiscal.vialidadPrimaria.tipoVialidad.descripcion = $(origenDomicilio+'vialidadPrimaria\\.tipoVialidad.descripcion').val();
	domicilioFiscal.asentamiento.clave = $(origenDomicilio+'asentamiento\\.clave').val();
	domicilioFiscal.asentamiento.nombre = $(origenDomicilio+'asentamiento\\.nombre').val();
	domicilioFiscal.asentamiento.codigoPostal.codigoPostal = $(origenDomicilio+'asentamiento\\.codigoPostal\\.codigoPostal').val();
	domicilioFiscal.asentamiento.localidad.clave = $(origenDomicilio+'asentamiento\\.localidad\\.clave').val();
	domicilioFiscal.asentamiento.localidad.nombre = $(origenDomicilio+'asentamiento\\.localidad\\.nombre').val();
	domicilioFiscal.asentamiento.localidad.municipio.clave = $(origenDomicilio+'asentamiento\\.localidad\\.municipio\\.clave').val();
	domicilioFiscal.asentamiento.localidad.municipio.nombre = $(origenDomicilio+'asentamiento\\.localidad\\.municipio\\.nombre').val();
	domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.clave = $(origenDomicilio+'asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val();
	domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre = $(origenDomicilio+'asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre').val();
	domicilioFiscal.descripcion=$('#textoDomicilio').text();
	
	return domicilioFiscal;
}

function solicitarConfirmacionModificacionManualFisica(){
	var objDialogo;
	
	if(isOperadorIMSS){
		construirDialogoGenericoDeConfirmacion("#dialogoMensajes", objDialogo, 
				"Confirmaci\u00F3n", 
				"No se ha realizado la actualizaci\u00F3n autom\u00E1tica, desea realizar los cambios manualmente?", 
				false, mostrarModManualFisica, undefined, 150, 700);
	}else{
		
		construirDialogoGenerico("#dialogoMensajes", objDialogo, "Notificaci\u00F3n", 
				"Por el momento esta operaci\u00F3n no est\u00E1 disponible por favor int\u00E9ntelo m\u00E1s tarde", 
				false, undefined, undefined, 150, 700);
	}

}

function solicitarConfirmacionModificacionManualMoral(){
	var objDialogo;
	
	if(isOperadorIMSS){
		construirDialogoGenericoDeConfirmacion("#dialogoMensajes", objDialogo, 
				"Confirmaci\u00F3n", 
				"No se ha logrado realizar la actualizaci\u00F3n autom\u00E1tica, desea realizar los cambios manualmente?", 
				false, mostrarModManualMoral, undefined, 150, 700);
	}else{
		construirDialogoGenerico("#dialogoMensajes", objDialogo, "Notificaci\u00F3n", 
				"Por el momento esta operaci\u00F3n no est\u00E1 disponible por favor int\u00E9ntelo m\u00E1s tarde", 
				false, undefined, undefined, 150, 700);

	}
	
}

function configurarBusquedaICAFisica(){
	$.getScript("/gestionIndividuo-consulta-web/static/resources/js/delta/personas/fisica/identificar/cambios-automaticos/identificar-cambios-automaticos.js", function(){
		icaFisicaCtrl = identificarCambiosAutomaticosPersonaFisicaCtrl; 
		icaFisicaCtrl.init('icaFisicaDialog'); 
		 //SETTEO DE PARAMETROS DE ENTRADA 
		icaFisicaCtrl.datosEntrada.indMostrarPantalla = true; 
//		icaFisicaCtrl.datosEntrada.indUsuarioExterno = $('#isUsuarioExterno').is(':checked'); 
		icaFisicaCtrl.datosEntrada.idPersona = $('#fisica\\.idPersona').val(); 
//		icaFisicaCtrl.datosEntrada.curp = $('#busquedaCurp').val(); 
//		icaFisicaCtrl.datosEntrada.rfc = $('#busquedaRfc').val(); 
//		icaFisicaCtrl.datosEntrada.nombrePersona = $('#busquedaNombres').val(); 
//		icaFisicaCtrl.datosEntrada.primerApellido = $('#busquedaPrimerApellido').val(); 
//		icaFisicaCtrl.datosEntrada.segundoApellido = $('#busquedaSegundoApellido').val(); 
		icaFisicaCtrl.datosEntrada.indBusquedaRENAPO = true; 
		icaFisicaCtrl.datosEntrada.indBusquedaSAT = true; 
		icaFisicaCtrl.setOnCloseCallback(fnOnICAFisicaRetorno);
	
	});
	
	  
}

function mostrarBusquedaICAFisica(){
	icaFisicaCtrl.identificarCambios(); 
}

function fnOnICAFisicaRetorno(){
	if(icaFisicaCtrl.getDatosSalida()==undefined){
		solicitarConfirmacionModificacionManualFisica();
		return; // No hubo respuesta.
	}
	
	var objICAF = icaFisicaCtrl.getDatosSalida();
	var fisicaResponse = objICAF.personaFisicaIMSS;
	
	if(fisicaResponse==undefined){
		if(objICAF.traza.COMPARACION_SIN_DIFERENCIAS!=undefined){
			return;
		}else{
			solicitarConfirmacionModificacionManualFisica();
			return; //Hubo error
		}
	}
	$('#fisica\\.rfc').val(fisicaResponse.rfc);
	$('#fisica\\.primerApellido').val(fisicaResponse.primerApellido);
	$('#fisica\\.segundoApellido').val(fisicaResponse.segundoApellido);
	$('#fisica\\.nombre').val(fisicaResponse.nombre);
	$('#fisica\\.curp').val(fisicaResponse.curp);
	
	$('#fisica\\.domicilioFiscal\\.calle').val(fisicaResponse.domicilioFiscal.calle);
	$('#fisica\\.domicilioFiscal\\.colonia').val(fisicaResponse.domicilioFiscal.colonia);
	$('#fisica\\.domicilioFiscal\\.descripcion').val(fisicaResponse.domicilioFiscal.descripcion);
	$('#fisica\\.domicilioFiscal\\.numExterior1').val(fisicaResponse.domicilioFiscal.numExterior1);
	$('#fisica\\.domicilioFiscal\\.numExterior2').val(fisicaResponse.domicilioFiscal.numExterior2);
	$('#fisica\\.domicilioFiscal\\.numExteriorAlf').val(fisicaResponse.domicilioFiscal.numExteriorAlf);
	$('#fisica\\.domicilioFiscal\\.numInterior').val(fisicaResponse.domicilioFiscal.numInterior);
	$('#fisica\\.domicilioFiscal\\.numInteriorAlf').val(fisicaResponse.domicilioFiscal.numInteriorAlf);
	$('#fisica\\.domicilioFiscal\\.vialidadPrimaria\\.clave').val(fisicaResponse.domicilioFiscal.vialidadPrimaria.clave);
	$('#fisica\\.domicilioFiscal\\.vialidadPrimaria\\.nombre').val(fisicaResponse.domicilioFiscal.vialidadPrimaria.nombre);
	if(fisicaResponse.domicilioFiscal.vialidadPrimaria.tipoVialidad!=undefined){
		$('#fisica\\.domicilioFiscal\\.vialidadPrimaria\\.tipoVialidad\\.clave').val(fisicaResponse.domicilioFiscal.vialidadPrimaria.tipoVialidad.clave);
		$('#fisica\\.domicilioFiscal\\.vialidadPrimaria\\.tipoVialidad\\.descripcion').val(fisicaResponse.domicilioFiscal.vialidadPrimaria.tipoVialidad.descripcion);
	}
	$('#fisica\\.domicilioFiscal\\.asentamiento\\.clave').val(fisicaResponse.domicilioFiscal.asentamiento.clave);
	$('#fisica\\.domicilioFiscal\\.asentamiento\\.nombre').val(fisicaResponse.domicilioFiscal.asentamiento.nombre);
	$('#fisica\\.domicilioFiscal\\.asentamiento\\.codigoPostal\\.codigoPostal').val(fisicaResponse.domicilioFiscal.asentamiento.codigoPostal.codigoPostal);
	$('#fisica\\.domicilioFiscal\\.asentamiento\\.localidad\\.clave').val(fisicaResponse.domicilioFiscal.asentamiento.localidad.clave);
	$('#fisica\\.domicilioFiscal\\.asentamiento\\.localidad\\.nombre').val(fisicaResponse.domicilioFiscal.asentamiento.localidad.nombre);
	$('#fisica\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.clave').val(fisicaResponse.domicilioFiscal.asentamiento.localidad.municipio.clave);
	$('#fisica\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.nombre').val(fisicaResponse.domicilioFiscal.asentamiento.localidad.municipio.nombre);
	$('#fisica\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val(fisicaResponse.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.clave);
	$('#fisica\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre').val(fisicaResponse.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
	
	var domicilioCompleto = obtenerDescripcionDomicilio(fisicaResponse.domicilioFiscal);
	$('#textoDomicilio').text(domicilioCompleto);
	
	actualizaDatosFiscales(fisicaResponse.mediosContactoFiscales);
	
	/* ¡ESTO SÓLO ES POR CUESTIÓN DE PRUEBAS!
	 * Se quita el campo documentos probatorios, para que
	 * al momento de que Spring parse el objeto JSON al objeto de modelo 
	 * no truene
	 */
	delete objICAF.personaFisicaIMSS.documentosProbatorios;
	delete objICAF.personaFisicaIMSS.mediosContacto;
	delete objICAF.personaFisicaIMSS.mediosContactoFiscales;
	delete objICAF.personaFisicaIMSS.fechaModificacion;
	delete objICAF.personaFisicaIMSS.fechaRegistro;
	if(objICAF.personaFisicaIMSS.registroSindicato!=undefined)
		delete objICAF.personaFisicaIMSS.registroSindicato.fechaRegistro;
	
	sendToServer('/afiliacion/almacenarTemporalmenteDatosICA',icaFisicaCtrl.getDatosSalida(),
			undefined, false);
	
	$("#divNombreComercial").show();
	$("#seccionMediosFiscales").show();
}

function configurarBusquedaICAMoral(){
	$.getScript("/gestionIndividuo-consulta-web/static/resources/js/delta/personas/moral/identificar/cambios-automaticos/identificar-cambios-automaticos.js", function(){  
		icaMoralCtrl = identificarCambiosAutomaticosPersonaMoralCtrl; 
		icaMoralCtrl.init('icaMoralDialog'); 
		icaMoralCtrl.datosEntrada.indMostrarPantalla = true; 
		icaMoralCtrl.datosEntrada.idPersona = $('#moral\\.idPersona').val();  
		icaMoralCtrl.setOnCloseCallback(fnOnICAMoralRetorno);  
	});
	
}

function mostrarBusquedaICAMoral(){
	icaMoralCtrl.identificarCambios();
}

function fnOnICAMoralRetorno(){
	if(icaMoralCtrl.getDatosSalida()==undefined){
		solicitarConfirmacionModificacionManualMoral();
		return; //No hubo respuesta
	}
	var objICA = icaMoralCtrl.getDatosSalida();
	var moralResponse = objICA.personaMoralIMSS;
	
	if(moralResponse==undefined){
		if(objICA.traza.COMPARACION_SIN_DIFERENCIAS!=undefined){
			return;
		}else{
			solicitarConfirmacionModificacionManualMoral();
			return; //Hubo error
		}
	}
	$('#moral\\.rfc').val(moralResponse.rfcSat);
	$('#moral\\.razonSocial').val(moralResponse.razonSocial);
	$('#moral\\.tipoSociedad\\.idTipoSociedad').val(moralResponse.tipoSociedad.idTipoSociedad);
	$('#moral\\.tipoSociedad\\.descripcion').val(moralResponse.tipoSociedad.descripcion);
	$('#moral\\.tipoSociedad\\.descripcionAbreviada').val(moralResponse.tipoSociedad.descripcionAbreviada);
	
	$('#moral\\.domicilioFiscal\\.calle').val(moralResponse.domicilioFiscal.calle);
	$('#moral\\.domicilioFiscal\\.colonia').val(moralResponse.domicilioFiscal.colonia);
	$('#moral\\.domicilioFiscal\\.descripcion').val(moralResponse.domicilioFiscal.descripcion);
	$('#moral\\.domicilioFiscal\\.numExterior1').val(moralResponse.domicilioFiscal.numExterior1);
	$('#moral\\.domicilioFiscal\\.numExterior2').val(moralResponse.domicilioFiscal.numExterior2);
	$('#moral\\.domicilioFiscal\\.numExteriorAlf').val(moralResponse.domicilioFiscal.numExteriorAlf);
	$('#moral\\.domicilioFiscal\\.numInterior').val(moralResponse.domicilioFiscal.numInterior);
	$('#moral\\.domicilioFiscal\\.numInteriorAlf').val(moralResponse.domicilioFiscal.numInteriorAlf);
	$('#moral\\.domicilioFiscal\\.vialidadPrimaria\\.clave').val(moralResponse.domicilioFiscal.vialidadPrimaria.clave);
	$('#moral\\.domicilioFiscal\\.vialidadPrimaria\\.nombre').val(moralResponse.domicilioFiscal.vialidadPrimaria.nombre);
	if(moralResponse.domicilioFiscal.vialidadPrimaria.tipoVialidad!=undefined){
		$('#moral\\.domicilioFiscal\\.vialidadPrimaria\\.tipoVialidad\\.clave').val(moralResponse.domicilioFiscal.vialidadPrimaria.tipoVialidad.clave);
		$('#moral\\.domicilioFiscal\\.vialidadPrimaria\\.tipoVialidad\\.descripcion').val(moralResponse.domicilioFiscal.vialidadPrimaria.tipoVialidad.descripcion);
	}
	$('#moral\\.domicilioFiscal\\.asentamiento\\.clave').val(moralResponse.domicilioFiscal.asentamiento.clave);
	$('#moral\\.domicilioFiscal\\.asentamiento\\.nombre').val(moralResponse.domicilioFiscal.asentamiento.nombre);
	$('#moral\\.domicilioFiscal\\.asentamiento\\.codigoPostal\\.codigoPostal').val(moralResponse.domicilioFiscal.asentamiento.codigoPostal.codigoPostal);
	$('#moral\\.domicilioFiscal\\.asentamiento\\.localidad\\.clave').val(moralResponse.domicilioFiscal.asentamiento.localidad.clave);
	$('#moral\\.domicilioFiscal\\.asentamiento\\.localidad\\.nombre').val(moralResponse.domicilioFiscal.asentamiento.localidad.nombre);
	$('#moral\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.clave').val(moralResponse.domicilioFiscal.asentamiento.localidad.municipio.clave);
	$('#moral\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.nombre').val(moralResponse.domicilioFiscal.asentamiento.localidad.municipio.nombre);
	$('#moral\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val(moralResponse.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.clave);
	$('#moral\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre').val(moralResponse.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
	
	var domicilioCompleto = obtenerDescripcionDomicilio(moralResponse.domicilioFiscal);
//		moralResponse.domicilioFiscal.calle + ' #' + moralResponse.domicilioFiscal.numExterior1;
//	if(moralResponse.domicilioFiscal.numInterior!= undefined && moralResponse.domicilioFiscal.numInterior!=null && moralResponse.domicilioFiscal.numInterior!=0)
//		domicilioCompleto += ' INTERIOR ' + moralResponse.domicilioFiscal.numInterior;
//	
//	domicilioCompleto += ' COLONIA '+ moralResponse.domicilioFiscal.colonia + ', '+moralResponse.domicilioFiscal.asentamiento.localidad.municipio.nombre;
//	domicilioCompleto += ', '+moralResponse.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre;
//	domicilioCompleto += ', C\u00D3DIGO POSTAL '+moralResponse.domicilioFiscal.asentamiento.codigoPostal.codigoPostal;
	$('#textoDomicilio').text(domicilioCompleto);
	
	actualizaDatosFiscales(moralResponse.mediosContactoFiscales);
	
	/* ¡ESTO SÓLO ES POR CUESTIÓN DE PRUEBAS!
	 * Se quita el campo documentos probatorios, para que
	 * al momento de que Spring parse el objeto JSON al objeto de modelo 
	 * no truene
	 */
	delete objICA.personaMoralIMSS.documentosProbatorios;
	delete objICA.personaMoralIMSS.mediosContacto;
	delete objICA.personaMoralIMSS.mediosContactoFiscales;
	delete objICA.personaMoralIMSS.fechaModificacion;
	delete objICA.personaMoralIMSS.fechaRegistro;
	if(objICA.personaMoralIMSS.registroSindicato!=undefined)
		delete objICA.personaMoralIMSS.registroSindicato.fechaRegistro;
	
//	if(objICA.personaMoralIMSS.registroSindicato!=undefined && objICA.personaMoralIMSS.registroSindicato.fechaRegistro!=undefined)
//		objICA.personaMoralIMSS.registroSindicato.fechaRegistro = 
//			$.datepicker.formatDate('dd/MM/yyyy', objICA.personaMoralIMSS.registroSindicato.fechaRegistro);
	
	sendToServer('/afiliacion/almacenarTemporalmenteDatosICA',objICA,
			undefined, false);
	
	$("#divNombreComercial").show();
	$("#seccionMediosFiscales").show();
}

function configurarComponenteModificacionManualFisica(){
	var objCtrl;
	$.getScript("/gestionIndividuo-consulta-web/static/resources/js/delta/personas/fisica/modificacion/manual/modificacion-manual-datos.js", function(){
	modManualFisicaCtrl = ModificacionManualDatosFisicaCtrl;
	 
	 //ID de un div vacío
	modManualFisicaCtrl.init('mdmFisicaDialog');
	 
	 //Datos de entrada
	modManualFisicaCtrl.datosEntrada.idPersona = $('#fisica\\.idPersona').val();
	modManualFisicaCtrl.datosEntrada.indCapturaNombre = true;
	modManualFisicaCtrl.datosEntrada.indCapturaCURP = true;
	modManualFisicaCtrl.datosEntrada.indCapturaSexo = true;
	modManualFisicaCtrl.datosEntrada.indCapturaFechaNacimiento = true;
	modManualFisicaCtrl.datosEntrada.indCapturaLugarNacimiento = true;
	modManualFisicaCtrl.datosEntrada.indCapturaDocumentoProbatorio = false;
	modManualFisicaCtrl.datosEntrada.indCapturaRFC  = true;
	modManualFisicaCtrl.datosEntrada.indCapturaDomicilioFiscal = true;
	modManualFisicaCtrl.datosEntrada.indCapturaMediosContactoFiscales = true;
	modManualFisicaCtrl.datosEntrada.indCapturaDomicilioParticular = false;
	modManualFisicaCtrl.datosEntrada.indCapturaMediosContactoParticular = false;
	modManualFisicaCtrl.datosEntrada.indAutorizacion = false;
	modManualFisicaCtrl.setOnCloseCallback(fnRetornoModificacionManualFisica);
	});
}

function mostrarModManualFisica(){
	modManualFisicaCtrl.modificacionManual();	
}


function fnRetornoModificacionManualFisica(){
	if(modManualFisicaCtrl.getDatosSalida()==undefined)
		return;
	var objMDM = modManualFisicaCtrl.getDatosSalida().mdmDatosEntrada;
	var fisicaResponse = objMDM.personaFisica;
	
	if(fisicaResponse==undefined)
		return; //Hubo error
	$('#fisica\\.rfc').val(fisicaResponse.rfc);
	$('#fisica\\.primerApellido').val(fisicaResponse.primerApellido);
	$('#fisica\\.segundoApellido').val(fisicaResponse.segundoApellido);
	$('#fisica\\.nombre').val(fisicaResponse.nombre);
	$('#fisica\\.curp').val(fisicaResponse.curp);
	
	$('#fisica\\.domicilioFiscal\\.calle').val(fisicaResponse.domicilioFiscal.calle);
	$('#fisica\\.domicilioFiscal\\.colonia').val(fisicaResponse.domicilioFiscal.colonia);
	$('#fisica\\.domicilioFiscal\\.descripcion').val(fisicaResponse.domicilioFiscal.descripcion);
	$('#fisica\\.domicilioFiscal\\.numExterior1').val(fisicaResponse.domicilioFiscal.numExterior1);
	$('#fisica\\.domicilioFiscal\\.numExterior2').val(fisicaResponse.domicilioFiscal.numExterior2);
	$('#fisica\\.domicilioFiscal\\.numExteriorAlf').val(fisicaResponse.domicilioFiscal.numExteriorAlf);
	$('#fisica\\.domicilioFiscal\\.numInterior').val(fisicaResponse.domicilioFiscal.numInterior);
	$('#fisica\\.domicilioFiscal\\.numInteriorAlf').val(fisicaResponse.domicilioFiscal.numInteriorAlf);
	$('#fisica\\.domicilioFiscal\\.vialidadPrimaria\\.clave').val(fisicaResponse.domicilioFiscal.vialidadPrimaria.clave);
	$('#fisica\\.domicilioFiscal\\.vialidadPrimaria\\.nombre').val(fisicaResponse.domicilioFiscal.vialidadPrimaria.nombre);
	if(fisicaResponse.domicilioFiscal.vialidadPrimaria.tipoVialidad!=undefined){
		$('#fisica\\.domicilioFiscal\\.vialidadPrimaria\\.tipoVialidad\\.clave').val(fisicaResponse.domicilioFiscal.vialidadPrimaria.tipoVialidad.clave);
		$('#fisica\\.domicilioFiscal\\.vialidadPrimaria\\.tipoVialidad\\.descripcion').val(fisicaResponse.domicilioFiscal.vialidadPrimaria.tipoVialidad.descripcion);
	}
	$('#fisica\\.domicilioFiscal\\.asentamiento\\.clave').val(fisicaResponse.domicilioFiscal.asentamiento.clave);
	$('#fisica\\.domicilioFiscal\\.asentamiento\\.nombre').val(fisicaResponse.domicilioFiscal.asentamiento.nombre);
	if(fisicaResponse.domicilioFiscal.asentamiento.codigoPostal!=undefined)
		$('#fisica\\.domicilioFiscal\\.asentamiento\\.codigoPostal\\.codigoPostal').val(fisicaResponse.domicilioFiscal.asentamiento.codigoPostal.codigoPostal);
	else if(fisicaResponse.domicilioFiscal.codigoPostal!=undefined)
		$('#fisica\\.domicilioFiscal\\.asentamiento\\.codigoPostal\\.codigoPostal').val(fisicaResponse.domicilioFiscal.codigoPostal.codigoPostal);
	
	$('#fisica\\.domicilioFiscal\\.asentamiento\\.localidad\\.clave').val(fisicaResponse.domicilioFiscal.asentamiento.localidad.clave);
	$('#fisica\\.domicilioFiscal\\.asentamiento\\.localidad\\.nombre').val(fisicaResponse.domicilioFiscal.asentamiento.localidad.nombre);
	$('#fisica\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.clave').val(fisicaResponse.domicilioFiscal.asentamiento.localidad.municipio.clave);
	$('#fisica\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.nombre').val(fisicaResponse.domicilioFiscal.asentamiento.localidad.municipio.nombre);
	$('#fisica\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val(fisicaResponse.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.clave);
	$('#fisica\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre').val(fisicaResponse.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
	
	var domicilioCompleto = obtenerDescripcionDomicilio(fisicaResponse.domicilioFiscal);
	$('#textoDomicilio').text(domicilioCompleto);
	
	actualizaDatosFiscales(fisicaResponse.mediosContactoFiscales);
	
	sendToServer('/afiliacion/almacenarTemporalmenteDatosMDM',objMDM,
			undefined, false);
	
	$("#divNombreComercial").show();
	$("#seccionMediosFiscales").show();
}


function configurarComponenteModificacionManualMoral(){
	$.getScript("/gestionIndividuo-consulta-web/static/resources/js/delta/personas/moral/modificacion/manual/modificacion-manual-datos.js", function(){
	modManualMoralCtrl = ModificacionManualDatosMoralCtrl;
	 
	 //ID de un div vacío
	modManualMoralCtrl.init('mdmMoralDialog');
	 
	 //Datos de entrada
	modManualMoralCtrl.datosEntrada.idPersona = $('#moral\\.idPersona').val();
	modManualMoralCtrl.datosEntrada.indCapturaRFC  = true;
	modManualMoralCtrl.datosEntrada.indCapturaDomicilioFiscal = true;
	modManualMoralCtrl.datosEntrada.indCapturaMediosContactoFiscales = true;
	modManualMoralCtrl.datosEntrada.indCapturaRazonSocial = true;
	modManualMoralCtrl.datosEntrada.indCapturaFechaConstitucion = false;
	modManualMoralCtrl.datosEntrada.indCapturaTipoSociedad = true;
	modManualMoralCtrl.datosEntrada.indCapturaActaConstitutiva = false;
 	modManualMoralCtrl.datosEntrada.indCapturaRegistroSindicato = false;
	modManualMoralCtrl.datosEntrada.indAutorizacion = false;
	modManualMoralCtrl.setOnCloseCallback(fnRetornoModificacionManualMoral);
	});
}

function mostrarModManualMoral(){
	 //Llamada al servicio de la modificación manual
	modManualMoralCtrl.modificacionManual();
}

function fnRetornoModificacionManualMoral(){
	
	if(modManualMoralCtrl.getDatosSalida()==undefined)
		return ;
	
	var objMDM = modManualMoralCtrl.getDatosSalida().mdmDatosEntrada;
	var moralResponse = objMDM.personaMoral;
	
	if(moralResponse==undefined)
		return; //Hubo error
	
	$('#moral\\.rfc').val(moralResponse.rfc);
	$('#moral\\.razonSocial').val(moralResponse.razonSocial);
	$('#moral\\.tipoSociedad\\.idTipoSociedad').val(moralResponse.tipoSociedad.idTipoSociedad);
	$('#moral\\.tipoSociedad\\.descripcion').val(moralResponse.tipoSociedad.descripcion);
	$('#moral\\.tipoSociedad\\.descripcionAbreviada').val(moralResponse.tipoSociedad.descripcionAbreviada);
	
	$('#moral\\.domicilioFiscal\\.calle').val(moralResponse.domicilioFiscal.calle);
	$('#moral\\.domicilioFiscal\\.colonia').val(moralResponse.domicilioFiscal.colonia);
	$('#moral\\.domicilioFiscal\\.descripcion').val(moralResponse.domicilioFiscal.descripcion);
	$('#moral\\.domicilioFiscal\\.numExterior1').val(moralResponse.domicilioFiscal.numExterior1);
	$('#moral\\.domicilioFiscal\\.numExterior2').val(moralResponse.domicilioFiscal.numExterior2);
	$('#moral\\.domicilioFiscal\\.numExteriorAlf').val(moralResponse.domicilioFiscal.numExteriorAlf);
	$('#moral\\.domicilioFiscal\\.numInterior').val(moralResponse.domicilioFiscal.numInterior);
	$('#moral\\.domicilioFiscal\\.numInteriorAlf').val(moralResponse.domicilioFiscal.numInteriorAlf);
	$('#moral\\.domicilioFiscal\\.vialidadPrimaria\\.clave').val(moralResponse.domicilioFiscal.vialidadPrimaria.clave);
	$('#moral\\.domicilioFiscal\\.vialidadPrimaria\\.nombre').val(moralResponse.domicilioFiscal.vialidadPrimaria.nombre);
	if(moralResponse.domicilioFiscal.vialidadPrimaria.tipoVialidad!=undefined){
		$('#moral\\.domicilioFiscal\\.vialidadPrimaria\\.tipoVialidad\\.clave').val(moralResponse.domicilioFiscal.vialidadPrimaria.tipoVialidad.clave);
		$('#moral\\.domicilioFiscal\\.vialidadPrimaria\\.tipoVialidad\\.descripcion').val(moralResponse.domicilioFiscal.vialidadPrimaria.tipoVialidad.descripcion);
	}
	$('#moral\\.domicilioFiscal\\.asentamiento\\.clave').val(moralResponse.domicilioFiscal.asentamiento.clave);
	$('#moral\\.domicilioFiscal\\.asentamiento\\.nombre').val(moralResponse.domicilioFiscal.asentamiento.nombre);
	$('#moral\\.domicilioFiscal\\.asentamiento\\.codigoPostal\\.codigoPostal').val(moralResponse.domicilioFiscal.codigoPostal.codigoPostal);
	$('#moral\\.domicilioFiscal\\.asentamiento\\.localidad\\.clave').val(moralResponse.domicilioFiscal.asentamiento.localidad.clave);
	$('#moral\\.domicilioFiscal\\.asentamiento\\.localidad\\.nombre').val(moralResponse.domicilioFiscal.asentamiento.localidad.nombre);
	$('#moral\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.clave').val(moralResponse.domicilioFiscal.asentamiento.localidad.municipio.clave);
	$('#moral\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.nombre').val(moralResponse.domicilioFiscal.asentamiento.localidad.municipio.nombre);
	$('#moral\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val(moralResponse.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.clave);
	$('#moral\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre').val(moralResponse.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
	
	var domicilioCompleto = obtenerDescripcionDomicilio(moralResponse.domicilioFiscal);
	$('#textoDomicilio').text(domicilioCompleto);
	
	actualizaDatosFiscales(moralResponse.mediosContactoFiscales);
	
	
	sendToServer('/afiliacion/almacenarTemporalmenteDatosMDM',objMDM,
			undefined, false);
	
	$("#divNombreComercial").show();
	$("#seccionMediosFiscales").show();

}

function obtenerDescripcionDomicilio(domicilioFiscal){
	var domicilioCompleto = domicilioFiscal.calle + ' #';
	if(domicilioFiscal.numExterior1!=undefined && domicilioFiscal.numExterior1!=null && domicilioFiscal.numExterior1!="")
		domicilioCompleto += domicilioFiscal.numExterior1;
	else if(domicilioFiscal.numExteriorAlf!=undefined && domicilioFiscal.numExteriorAlf!=null && domicilioFiscal.numExteriorAlf!="")
		domicilioCompleto += domicilioFiscal.numExteriorAlf;
		
	if(domicilioFiscal.numInterior!= undefined && domicilioFiscal.numInterior!=null && domicilioFiscal.numInterior!=0)
		domicilioCompleto += ' INTERIOR ' + domicilioFiscal.numInterior;
	else if(domicilioFiscal.numInteriorAlf!= undefined && domicilioFiscal.numInteriorAlf!=null && domicilioFiscal.numInteriorAlf!="")
		domicilioCompleto += ' INTERIOR ' + domicilioFiscal.numInteriorAlf;
			
	domicilioCompleto += ' COLONIA '+ domicilioFiscal.colonia + ', '+domicilioFiscal.asentamiento.localidad.municipio.nombre;
	domicilioCompleto += ', '+domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre;
	if(domicilioFiscal.asentamiento.codigoPostal!=undefined)
		domicilioCompleto += ', C\u00D3DIGO POSTAL '+domicilioFiscal.asentamiento.codigoPostal.codigoPostal;
	if(domicilioFiscal.codigoPostal!=undefined)
		domicilioCompleto += ', C\u00D3DIGO POSTAL '+domicilioFiscal.codigoPostal.codigoPostal;
	
	return domicilioCompleto;
}

function inicializaDatosContactoFiscales(){
	oTableMediosFiscales = $('#gridMediosFiscales').dataTable({
		bJQueryUI : false,
		bPaginate : true,
		bLengthChange : false,
		iDisplayLength : 4,
		bServerSide : false,
		bProcessing : false,
		sPaginationType : "full_numbers",
		bFilter : false,
		bSort : true,
		bInfo : false,
		bAutoWidth : true,
		aoColumns : columnasMedioContactoFiscales,
		sAjaxSource : '/delta-gestionPatronal-web/afiliacion/inicializarMediosFiscales',
		fnServerData : cargarDatosContactoFiscales
	});

	inicializaEstiloGrid($("#gridMediosFiscales tbody"), oTableMediosFiscales);

}

function cargarDatosContactoFiscales(sSource, aoData, fnCallback){
	var wrapper = new Object();
	wrapper.oForm = new Object();
	wrapper.aoData = aoData;
	$.postJSON(sSource, wrapper, function(data) {
		fnCallback(data);
	});
}

function actualizaDatosFiscales(listaDatosContactoFiscales){
	oTableMediosFiscales.fnClearTable();
	oTableMediosFiscales.fnAddData(listaDatosContactoFiscales);
	oTableMediosFiscales.fnDraw();
}

function obtenerListaMediosContactoFiscales(){
	var lista = [];
	var medioContacto;
	var dataOrigen = obtenerDatosGrid(oTableMediosFiscales);
	for(registro in dataOrigen){
		data = dataOrigen[registro]._aData;	
		medioContacto = new Object();
		medioContacto.idVista = data["idVista"];
		medioContacto.clave = data["clave"];
		medioContacto.desFormaContacto = data["desFormaContacto"];
			tipoMedioContacto = new Object();
			tipoMedioContacto.idTipoMedioContacto = data["tipoMedioContacto"]["idTipoMedioContacto"];
			tipoMedioContacto.descripcion = data["tipoMedioContacto"]["descripcion"];
		medioContacto.tipoMedioContacto = tipoMedioContacto;
		lista.push(medioContacto);
	}
	return lista;
};

function obtenerDatosGrid(grid){
	return grid.fnSettings().aoData;
}