var numeroPagina;
var oTableMedios;
var tipoPersona;
var tipoActa;
var radioTipoPersonaSeleccionado;
var radioTipoActaSeleccionado;
var idSolicitud=0;
var acceptFunctionPromptDialog;

// Seccion Representante Legal
var dtRepresentanteLegalAlta;
var dtMediosContactoRLAltaEnAgregar;
var dtMediosContactoRLAltaEnModificar;


// Seccion Socios
var dtMediosContactoSociosAlta;


var columnasMedioContacto = [ {
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

function visualizarPaginaPersona(radioSeleccionado){
	if(radioSeleccionado!=undefined)
		radioTipoPersonaSeleccionado=radioSeleccionado;
	if(radioTipoPersonaSeleccionado != undefined)
		tipoPersona = radioTipoPersonaSeleccionado.value;
	else
		tipoPersona = 0;
	
	if(tipoPersona==tipoPersonaFisica){
		$('#seccionPersonaFisica').show();
		$('#seccionPersonaMoral').hide();
		visualizarSeccionMediosContacto();
	}else if(tipoPersona==tipoPersonaMoral){
		$('#seccionPersonaFisica').hide();
		$('#seccionPersonaMoral').show();
		visualizarSeccionMediosContacto();
	}else{
		$('#seccionPersonaFisica').hide();
		$('#seccionPersonaMoral').hide();
		$('#seccionMedios').hide();
	}
}

function visualizarSeccionMediosContacto(){
	$('#seccionMedios').show();
}

function visualizarPaginaActa(tipoActaSeleccionado){
	
	if(tipoPersona==tipoPersonaFisica){
		visualizarSeccionRepresentante();
	}else if(tipoPersona==tipoPersonaMoral){
		if(tipoActaSeleccionado != undefined)
			tipoActa = tipoActaSeleccionado;
		else
			tipoActa = 0;
		
		if(tipoActa==1){
			$('#seccionEscritura').show();
			$('#seccionSindicato').hide();
			visualizarSeccionRepresentante();
			visualizarSeccionSocio();
		}else if(tipoActa==2){
			$('#seccionEscritura').hide();
			$('#seccionSindicato').show();
			visualizarSeccionRepresentante();
			visualizarSeccionSocio();
		}else{
			$('#seccionEscritura').hide();
			$('#seccionSindicato').hide();
		}
	}else{
		showErrorDialog("No se ha seleccionado el tipo de persona a dar de alta y este dato es requerido para cargar esta p\u00E1gina");
	}
}

function visualizarSeccionRepresentante(){
	$('#seccionRepresentante').show();
}

function visualizarSeccionSocio(){
	$('#seccionSocio').show();
}

function visualizarPagina(){
	
	if(!validarDatosPreviosRequeridos(numeroPagina))
		return false;
	
	if(numeroPagina==1){
		$('#seccionDatosPersonales').show();
		$('#seccionDatosFiscales').hide();
		$('#seccionCentroTrabajo').hide();
		$('#seccionActividadEconomica').hide();
		$('#btnAnterior').attr("disabled","true");
	}else if(numeroPagina==2){
		$('#seccionDatosPersonales').hide();
		$('#seccionDatosFiscales').show();
		$('#seccionCentroTrabajo').hide();
		$('#seccionActividadEconomica').hide();
		$('#btnAnterior').removeAttr("disabled");
		$('#btnSiguiente').removeAttr("disabled");
	}else if(numeroPagina==3){
		$('#seccionDatosPersonales').hide();
		$('#seccionDatosFiscales').hide();
		$('#seccionCentroTrabajo').show();
		$('#seccionActividadEconomica').hide();
		$('#btnAnterior').removeAttr("disabled");
		$('#btnSiguiente').removeAttr("disabled");
	}else if(numeroPagina==4){
		$('#seccionDatosPersonales').hide();
		$('#seccionDatosFiscales').hide();
		$('#seccionCentroTrabajo').hide();
		$('#seccionActividadEconomica').show();
		$('#btnSiguiente').attr("disabled","true");
	}
}

function validarDatosPreviosRequeridos(numeroPagina){
	if(numeroPagina==1){
		//No hay nada que validar
		return true;
	}else if(numeroPagina==2){
		if(!validaDatosPersonalesRequeridos()){
			numeroPagina-=1;
			return false;
		}
	}else if(numeroPagina==3){
		 
	}else if(numeroPagina==4){
		 
	}
	
	return true;
}

function avanzarPagina(){
	if(numeroPagina<4){
		numeroPagina=numeroPagina+1;
		visualizarBreadCrumb(numeroPagina);
		visualizarPagina();
	}
}

function retrocederPagina(){
	if(numeroPagina>1){
		numeroPagina=numeroPagina-1;
		visualizarBreadCrumb(numeroPagina);
		visualizarPagina();
	}
}


function visualizarBreadCrumb(pNumeroPagina){
	if(pNumeroPagina==1){
		$('#breadcrumbPaso1').show();
		$('#breadcrumbPaso2').hide();
		$('#breadcrumbPaso3').hide();
		$('#breadcrumbPaso4').hide();
	}else if(pNumeroPagina==2){
		$('#breadcrumbPaso1').hide();
		$('#breadcrumbPaso2').show();
		$('#breadcrumbPaso3').hide();
		$('#breadcrumbPaso4').hide();
	}else if(pNumeroPagina==3){
		$('#breadcrumbPaso1').hide();
		$('#breadcrumbPaso2').hide();
		$('#breadcrumbPaso3').show();
		$('#breadcrumbPaso4').hide();
	}else if(pNumeroPagina==4){
		$('#breadcrumbPaso1').hide();
		$('#breadcrumbPaso2').hide();
		$('#breadcrumbPaso3').hide();
		$('#breadcrumbPaso4').show();
	}
}

function inicializaDatosContacto(){
	oTableMedios = $('#gridMedios').dataTable({
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
		aoColumns : columnasMedioContacto,
		sAjaxSource : context_path + "/afiliacion/alta/cargaMediosContacto",
		fnServerData : cargarGridMedios
	});

	inicializaEstiloGrid($("#gridMedios tbody"), oTableMedios);

}

function cargarGridMedios(sSource, aoData, fnCallback){
		var wrapper = new Object();
		wrapper.oForm = new Object();
		wrapper.aoData = aoData;
		$.postJSON(sSource, wrapper, function(data) {
			fnCallback(data);
		});
}

/**
 * Funcion para invocar al proceso de buscar persona fisica
 */
function fnOpenBuscarPersonaFisica() {
	PersonaFisicaCtrl.init('personaFisica', PersonaFisicaCtrl.tipoServicio.COMPLETO, PersonaFisicaCtrl.tipoContexto.INTERNO);
	if (PersonaFisicaCtrl.persona != null){
		PersonaFisicaCtrl.persona = null;
		PersonaFisicaCtrl.personaEncontrada = false;
	}
	PersonaFisicaCtrl.setOnCloseCallback(fnOnPersonaRetorno);
	PersonaFisicaCtrl.buscar();
}

function fnOpenBuscarPersonaMoral() {
	PersonaMoralCtrl.init('personaMoral');
	if (PersonaMoralCtrl.persona != null){
		PersonaMoralCtrl.persona = null;
		PersonaMoralCtrl.personaEncontrada = false;
	}
	PersonaMoralCtrl.setOnCloseCallback(fnOnPersonaMoralRetorno);
	PersonaMoralCtrl.buscar();
}

var fnOnPersonaRetorno = function(){
	var p = this;
	
	if(p!=undefined && p!=null){
		$("#fisica\\.primerApellido").val(p.primerApellido);
		$("#fisica\\.segundoApellido").val(p.segundoApellido);
		$("#fisica\\.nombre").val(p.nombre);
		$("#fisica\\.curp").val(p.curp);
		$("#fisica\\.rfc").val(p.rfc);
		$("#fisica\\.nombreComercial").val("");
		$("#tipoPersonaFiscal").val(tipoPersonaFisicaEnum);
		$('#seccionPersonaFisica').show();
		$('#seccionPersonaMoral').hide();
		visualizarSeccionMediosContacto();
	}
	
	
}

var fnOnPersonaMoralRetorno = function(){
	var p = this;
	if(p!=undefined && p!=null){
		if(p.razonSocial == undefined)
			return;
		if(p.tipoSociedad == undefined)
			return;
		$("#moral\\.razonSocial").val(p.razonSocial);
		$("#moral\\.tipoSociedad\\.descripcion").val(p.tipoSociedad.descripcion);
		$("#moral\\.nombreComercial").val("");
		$('#seccionPersonaFisica').hide();
		$('#seccionPersonaMoral').show();
		$("#tipoPersonaFiscal").val(tipoPersonaMoralEnum);
		visualizarSeccionMediosContacto();
	}
}

function configurarComponenteBusquedaPersonaFisica(){
	/*
	 * configuracion para buscar a la persona fisica
	 */
	$.getScript("/gestionIndividuo-web/static/resources/js/delta/personas/fisica/PersonaFisica.js", function(){
	});
}

function configurarComponenteBusquedaPersonaMoral(){
	/*
	 * configuracion para buscar a la persona fisica
	 */
	$.getScript("/gestionIndividuo-web/static/resources/js/delta/personas/moral/PersonaMoral.js", function(){
		
	});
}

function configurarBusquedaDomicilio(){
	/*configuracion para buscar un domicilio*/	
	var urlDomicilios = '/${mvn.web.app.rootDomicilios}/static/resources/js/delta/domicilios/Domicilio.js';	
	$.getScript(urlDomicilios, function(script, textStatus){	
	});			 
}


function confirmarOperacion(callback){
	var objDialogo;
	var titulo="Confirmaci\u00F3n";
	var mensaje="Desea guardar la informaci\u00F3n capturada?.<br>Seleccione <Aceptar> para guardar los cambios o <Cancelar> para descartarlos."
	var callbackForXButton=undefined;
	construirDialogoGenericoDeConfirmacion("#dialogoMensajes", objDialogo, titulo, mensaje, error, callback, callbackForXButton, 200, 400);
}

function confirmarEnvio(callback){
	var objDialogo;
	var titulo="Confirmaci\u00F3n";
	var mensaje="Su solicitud de Alta ser\u00E1 enviada al INSTITUTO MEXICANO DEL SEGURO SOCIAL para ser evaluada, confirma que desea enviar su solicitud?.<br>Seleccione <Aceptar> para guardar los cambios o <Cancelar> para descartarlos."
	var callbackForXButton=undefined;
	construirDialogoGenericoDeConfirmacion("#dialogoMensajes", objDialogo, titulo, mensaje, error, callback, callbackForXButton, 200, 400);
}


function guardarSolicitud(){
	var datosAfil = $("form#altaPatronalForm").toObject(true);
	datosAfil=attachObjetoClasificacion(datosAfil);
	datosAfil=attachDatosFiscales(datosAfil);
	datosAfil=attachMediosCentroTrabajo(datosAfil,dtMediosContacto);
	var url="/afiliacion/alta/actualizarSolicitud?idSolicitud="+idSolicitud;
	sendToServer(url, datosAfil, callbackGuardarSolicitudAlta,
			false);
}

function enviarSolicitud(){
	var datosAfil = $("form#altaPatronalForm").toObject(true);
	datosAfil=attachObjetoClasificacion(datosAfil);
	datosAfil=attachDatosFiscales(datosAfil);
	datosAfil=attachMediosCentroTrabajo(datosAfil,dtMediosContacto);
	var url="/afiliacion/alta/enviarSolicitud?idSolicitud="+idSolicitud;
	sendToServer(url, datosAfil, callbackGuardarSolicitudAlta,
			false);
}

function callbackGuardarSolicitudAlta(response){
	var sinCallback = undefined;
	var sinCloseCallback = undefined;
	var requiereBotonCancelar = false;
	var eRespuesta=procesarResponse(response, sinCallback, 250, 500, procesarMensajeFolio, requiereBotonCancelar);
	if(eRespuesta)
		procesarMensajeFolio(response);
}


function procesarResponse(response, callback, dialogoHeight, dialogoWidth, closeCallback, requiereBotonCancelar) {
	var mensaje = "";
	var titulo = "";
	error = false;
	if (response.mensajeExito != undefined && response.mensajeExito != null) {
		titulo = "Operaci&oacute;n Exitosa";
		error = false;
		if (response.mensajeExito == "") {
			mensaje = "Operaci&oacute;n realizada con &eacute;xito.";
		} else {
			mensaje = response.mensajeExito;
		}
		if(requiereBotonCancelar != undefined && requiereBotonCancelar){
			construirDialogoDeSolicitudDeConfirmacion(titulo, mensaje, error, callback, dialogoHeight, dialogoWidth, closeCallback);
		}else{
			var oDialogo;
			construirDialogoGenericoMsjConfirmacion("#dialogoMensajesConfirmacion", oDialogo, titulo, mensaje, error, callback, closeCallback, dialogoHeight, dialogoWidth);
		}
		
		return true;
	} else if (response.mensajeError != undefined
			&& response.mensajeError != null) {
		titulo = "Operaci&oacute;n Erronea";
		error = true;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}
		construirDialogoTramiteMensajeConfirmacion(titulo, mensaje, error, callback, dialogoHeight, dialogoWidth);
	}
	return false;
}

function procesarMensajeFolio(response){
	folio = response.solicitud.noFolioSolicitud;
	var idSolicitudRespuesta = response.solicitud.solicitudId;
	if(idSolicitudRespuesta!=undefined)
		idSolicitud = idSolicitudRespuesta;
		
	if( folio != undefined){
		if($("#noFolioActual") != undefined ){
			$("#noFolioActual").html(folio);
		}
		if($("#infoFolio") != undefined ){
			$("#infoFolio").show();
		}
		$("#btnEnviar").removeAttr("disabled");
	}
}

function presentarSolicitudConfirmacionCancelacion(){
	var dialogoSolicitarConfirmacion;
	construirDialogoGenericoDeConfirmacion("#dialogoMensajes", dialogoSolicitarConfirmacion, "Confirmaci\u00F3n", 
			"\u00BFEst\u00E1 seguro que desea cancelar la solicitud?", false, cancelarSolicitudAlta);
}

function cancelarSolicitudAlta(response) {
	$.blockUI();
	var solicitudObj = new Object();
	solicitudObj.solicitudId=idSolicitud;
	
	sendToServer('/afiliacion/alta/cancelarSolicitud', solicitudObj,
			callbackCancelarSolicitudAlta, true);
}

function callbackCancelarSolicitudAlta(response) {
	procesarResponse(response, callbackConfirmarCancelacionSolicitud, 200, 550);
	$.unblockUI();
}

function callbackConfirmarCancelacionSolicitud(response){
	if (!error) {
		navegarTo('/sujetoObligado',
				'formSupport');
	}
}

function attachDatosFiscales(sujetoTramite) {
	sujetoTramite = attachEscrituraConstitutiva(sujetoTramite);
	var representantesLegales = new Object();
	representantesLegales = getRepLegalesAltaAsList();
	sujetoTramite.representantesLegales = representantesLegales; 
	return sujetoTramite;
}

function configurarMessageDialog(){
	$("#message_dialog").overlay({

	    // custom top position
	    top: 260,

	    // some mask tweaks suitable for facebox-looking dialogs
	    mask: {

	    // you might also consider a "transparent" color for the mask
	    color: '#000000',

	    // load mask a little faster
	    loadSpeed: 200,

	    // very transparent
	    opacity: 0.4
	    },

	    // disable this for modal dialog-type of overlays
	    closeOnClick: false,

	    // load it immediately after the construction
	    load: false

	    });
}

function configurarErrorDialog(){
 // select the overlay element - and "make it an overlay", error dialog
  $("#error_dialog").overlay({

    // custom top position
    top: 260,

    // some mask tweaks suitable for facebox-looking dialogs
    mask: {

    // you might also consider a "transparent" color for the mask
    color: '#BE0505',

    // load mask a little faster
    loadSpeed: 200,

    // very transparent
    opacity: 0.5
    },

    // disable this for modal dialog-type of overlays
    closeOnClick: false,

    // load it immediately after the construction
    load: false

    });
}
	
function configurarAcceptCancelPrompt(){
// select the overlay element - and "make it an overlay", error dialog
  $("#accept_cancel_prompt").overlay({

    // custom top position
    top: 260,

    // some mask tweaks suitable for facebox-looking dialogs
    mask: {

    // you might also consider a "transparent" color for the mask
    color: '#000000',

    // load mask a little faster
    loadSpeed: 200,

    // very transparent
    opacity: 0.4
    },

    // disable this for modal dialog-type of overlays
    closeOnClick: false,

    // load it immediately after the construction
    load: false

    });
}

function showMessageDialog(msg){
	$("#message_dialog #detalle").text(msg);
	$("#message_dialog").overlay().load();
}

function showErrorDialog(error_msg){
	$("#error_dialog #detalle").text(error_msg);
	$("#error_dialog").overlay().load();
}


function showPromptDialog(msg, callbackPromptDialog){
	$("#accept_cancel_prompt #detalle").text(msg);
	$("#accept_cancel_prompt").overlay().load();
	acceptFunctionPromptDialog = callbackPromptDialog; // ke chafa!!
}



function configButtonsPromptDialog(){
	var buttons = $("#accept_cancel_prompt button").click(function(e) {
 
		// get user input
		var accept = buttons.index(this) === 0;

		// process user input
		if (accept && acceptFunctionPromptDialog != undefined){
			acceptFunctionPromptDialog();
		}
	});
}

function inicializaJQueryValidationEngine(){
	$("#altaPatronalForm").validationEngine({promptPosition : "centerRight", scroll: false});
    $("#altaPatronalForm").validationEngine('init', {promptPosition : "centerRight", scroll: false});
    $("#altaPatronalForm").validationEngine('attach');
}

function obtenerListaMediosContactoFiscales(oTableMediosFiscalesParam){
	var lista = [];
	var medioContacto;
	var dataOrigen;
	
	if (oTableMediosFiscalesParam != undefined){
		dataOrigen = obtenerDatosGrid(oTableMediosFiscalesParam);
	} else {
		dataOrigen = obtenerDatosGrid(oTableMediosFiscales);
	}
	
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

function mostrarFolioSolicitud(){
	if(idSolicitud!=undefined && idSolicitud!=0){
		$("#noFolioActual").html(folio);
		$("#infoFolio").show();
		$("#btnEnviar").removeAttr("disabled");
	}
}

function inicializarPaginaDatosPersonales(){
	if(tipoPersonaEnTramite==tipoPersonaFisica){
		$('#seccionPersonaFisica').show();
		$('#seccionPersonaMoral').hide();
		visualizarSeccionMediosContacto();
	}else if(tipoPersonaEnTramite==tipoPersonaMoral){
		$('#seccionPersonaFisica').hide();
		$('#seccionPersonaMoral').show();
		visualizarSeccionMediosContacto();
	}else{
		$('#seccionPersonaFisica').hide();
		$('#seccionPersonaMoral').hide();
		$('#seccionMedios').hide();
	}
}

function inicializarSolicitud(){
	if(idSolicitud!=undefined && idSolicitud!=0){
		mostrarFolioSolicitud();
		inicializarPaginaDatosPersonales();
	}else{
		$("#btnEnviar").attr("disabled",true);
	}
}

function inicializarPaginaDatosFiscales(){
	if(existeTramiteActa){
		
	}
}

$(document).ready(
	function(){
		numeroPagina=1;
		visualizarBreadCrumb(numeroPagina);
		visualizarPagina();
		construirGridRepresentateLegal();
		construirGridMediosContactoRLAltaEnAgregar();
		construirGridMediosContactoRLAltaEnModificar();
		construirGridSociosAlta();
		inicializaPaginaClasificacion();
		inicializaEstiloGrid($("#gridRepresentantesAlta tbody"), dtRepresentanteLegalAlta);
		inicializaEstiloGridParaMediosContacto($("#gridMediosContactoRLAltaEnAgregar tbody"), dtMediosContactoRLAltaEnAgregar);
		inicializaEstiloGridParaMediosContacto($("#gridMediosContactoRLAltaEnModificar tbody"), dtMediosContactoRLAltaEnModificar);
		configurarComponenteBusquedaPersonaFisica();
		configurarComponenteBusquedaPersonaMoral();
		configurarBusquedaDomicilio();
		configurarMessageDialog();
		configurarErrorDialog();
		configurarAcceptCancelPrompt();
		configButtonsPromptDialog();
		inicializaJQueryValidationEngine();
		inicializarSolicitud();
	}
);