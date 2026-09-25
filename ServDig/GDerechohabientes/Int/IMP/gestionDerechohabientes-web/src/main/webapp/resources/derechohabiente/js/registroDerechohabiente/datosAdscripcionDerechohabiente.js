/**
 * 
 */

STATIC_URL_VALIDACIONES_ADSCRIPCION = "/${mvn.web.app.root}/tramite/registro/validaciones/datosAdscripcion";
STATIC_MENSAJE_DOCUMENTOS = "Debe completar la documentaci&oacute;n del tr&aacute;mite para poder continuar con el registro";
STATIC_MENSAJE_DATOS = "Es necesario llenar todos los campos marcados como obligatorios.";

//Objetos de los fieldset
F_DATOS_REGISTRO = null;
F_DATOS_ADSCRIPCION = null;
FORM_REGISTRO = null;


/**
 * Filtra los documentos requieridos para el tr&aacute;mite
 */
funcionCallBackPinta = function(data){
	
	var tipoDocumentosNoMostrados = null;
	
	// -------------------------------------------------------------
	// Para registro de recien nacidos no se muestran las actas
	// Para registro de hijos no se muestran las constancias
	// -------------------------------------------------------------
	if( $("#tipoTramite").length > 0 ){
		if($("#razonRegistro\\.idRazonRegistro").length > 0){
			if( (typeof(RAZON_REGISTRO_ENUM) !== 'undefined') && (typeof(TIPO_DOCUMENTO_PROBATORIO_ENUM) !== 'undefined')
					&& (typeof(TIPO_TRAMITE_ENUM) !== 'undefined')){
				
				var tipoTramite = $("#tipoTramite").val(); 
				var razonRegistro = $("#razonRegistro\\.idRazonRegistro").val();
				var indHijos = $("#indHijosProcreados").val();
			
				if( tipoTramite == TIPO_TRAMITE_ENUM.REGISTRO_HIJOS  ){
					if( razonRegistro == RAZON_REGISTRO_ENUM.RECIEN_NACIDO){
						tipoDocumentosNoMostrados = TIPO_DOCUMENTO_PROBATORIO_ENUM.ACTAS;
					}else{
						tipoDocumentosNoMostrados = TIPO_DOCUMENTO_PROBATORIO_ENUM.CONSTANCIAS;
					}
				}
				
				if ( tipoTramite == TIPO_TRAMITE_ENUM.REGISTRO_CONCUBINA_RIO ){
					if(indHijos == "0"){
						tipoDocumentosNoMostrados = TIPO_DOCUMENTO_PROBATORIO_ENUM.ACTAS;
					}
				}
			}
		}
		
	}
	
	
	
	// ------------------------
	// Probatorios
	// ------------------------
	var listaDocumentoProbatorio = [];
	var lisDoc = data.tipoDocumentoProbatorioList;
	var len = lisDoc.length;
	
	for(var i=0;i<len;i++){
		if( tipoDocumentosNoMostrados != lisDoc[i].idTipoDocumentoProbatorio )
			listaDocumentoProbatorio.push(lisDoc[i]);
	}
	
	data.tipoDocumentoProbatorioList = listaDocumentoProbatorio;
	
	
	
	// ------------------------
	// Requeridos
	// ------------------------
	var tipoDocumentoProbatorioReqList = [];
	lisDoc = data.tipoDocumentoProbatorioReqList;
	len = lisDoc.length;
	
	for(var i=0;i<len;i++){
		if( tipoDocumentosNoMostrados != lisDoc[i].idTipoDocumentoProbatorio )
			tipoDocumentoProbatorioReqList.push(lisDoc[i]);
	}
	
	data.tipoDocumentoProbatorioReqList = tipoDocumentoProbatorioReqList;
	
	
	
	listDocReq = tipoDocumentoProbatorioReqList;
	return data;
	
};


$(document).ready(function() {
	blockBackButton();
	
	llenaObjetos();
	
	F_DATOS_ADSCRIPCION.deshabilitarContenido();
	F_DATOS_REGISTRO.deshabilitarContenido();
	
	$("#observacion").removeAttr("disabled");
	
	var doctosNoMostrar ="" + $("#doctosNoMostrar").val() =="1";
	var idsDocumentosNoMostrar = "" + $("#idsDoctosNoMostrar").val();
	var requiereDocs = $("#requiereDocs").val() == "1";
	if(requiereDocs) {
		//se agregan los ids de los documentos
		loadFileUpload($("#tipoTramite").val(), undefined, undefined, doctosNoMostrar, idsDocumentosNoMostrar);
	}
	
	//Asignamos el contador de caracteres al campo de observaciones
	asignartextAreaLimites("observacion",{styles: {}});
	//Obtenemos las umfs disponibles
	getUmfsDisponibles();
	//Seteamos el evento del boton de registrar
	$("#registrar").click(validacionesDocumentos);
	//Se setea el evento de cancelar
	if($("#cancelar").length > 0) {
		$("#cancelar").click(cancelarSolicitud);
	}
});

var cancelarSolicitud = function() {
	var idSolicitud = $("#idSolicitud").val();
	var idTramite = $("#tramiteId").val();
	var idTipoTramite = $("#tipoTramite\\.idTipoTramite").val();
	var idPersona = $("#fisica\\.idPersona").val();
	
//	cargarRazonRechazo(idSolicitud,idTramite,idPersona,idTipoTramite);
};

var validacionesDocumentos = function() {
	var requiereDocs = $("#requiereDocs").val() == "1";
	
	if(requiereDocs && !fileUploadFinish){
		muestraError(STATIC_MENSAJE_DOCUMENTOS);
	} else {
		validacionesFormulario();
	}
}
var validacionesFormulario = function() {
	
	//Se habilita el contenido del formulario para podder enviar el formulario
	habilitarContenido(true);
	//Se transforma el formulario en un objeto
	var tramiteRegistro = FORM_REGISTRO.toObject();
	//Se esconden los errores de captura
	fnHideErrores("form#registro");
	
	
	//se realiza la llamada al metodo de validaciones
	$.postJSON(STATIC_URL_VALIDACIONES_ADSCRIPCION, tramiteRegistro, function(data2) {
		habilitarContenido(false);
		muestraMensajeConfirmacion();
	}).error(function(data){
		$.unblockUI();
		habilitarContenido(false);
		fnProcesarErrores(data, "form#registro");
		
		muestraError(STATIC_MENSAJE_DATOS);
	});
}

var habilitarContenido = function(habilitar) {
	var permiteEleccionUmf = $("#indSeleccionMedico").val()=="1";
	
	if(habilitar) {
		FORM_REGISTRO.habilitarContenido();
	} else {
		FORM_REGISTRO.deshabilitarContenido();
		
		if(permiteEleccionUmf) {
			$("#medicoEnTurno\\.turno\\.idTurno").removeAttr("disabled");
			$("#medicoEnTurno\\.consultorio\\.idConsultorio").removeAttr("disabled");
		}
		
		$("#observacion").removeAttr("disabled");
	}
}

var llenaObjetos = function() {
	F_DATOS_REGISTRO = $("#datosRegistro");
	F_DATOS_ADSCRIPCION = $("#datosAdscripcion");
	FORM_REGISTRO = $("#registro");
}

var muestraMensajeConfirmacion = function() {
	$mensajeConfirmacionRegistro = $('<div></div');
	$mensajeConfirmacionRegistro.dialog({
		autoOpen : false,
		title: 'Confirmaci&oacute;n requerida',
		resizable: false,
		closeOnEscape: false,
		modal: true,
		width: 500,
		buttons: {
			"Aceptar" : function() {
				habilitarContenido(true);
				$(this).dialog("close");
				FORM_REGISTRO.on("submit",function(){$.blockUI();});
				FORM_REGISTRO.submit();
	        }, 
	        "Cancelar" : function() {
	        	$(this).dialog("close");
	        	$(this).dialog("destroy");
	        }
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	$mensajeConfirmacionRegistro.html("Se registrar&aacute; a la persona dentro del grupo familiar. &iquest;Est&aacute; seguro que desea continuar?");
	$mensajeConfirmacionRegistro.dialog('open');
}

var muestraError = function(mensaje) {
	
	$mensajeError = $('<div></div');
	$mensajeError.html(mensaje);
	$mensajeError.dialog({
		autoOpen : false,
		title: 'Error',
		resizable: false,
		closeOnEscape: false,
		modal: true,
		width: 500,
		buttons: {
			"Aceptar" : function() {
	          $(this).dialog("close");
	        }
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$mensajeError.dialog('open');
}