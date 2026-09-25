var index = -1;
var oTableTramites;
var idTramites = "#tramites";
var indicadorTramiteClasifExistente;
var dialogoError;
var idDialogoError = "#dgErrorSinSeleccion";
var idDialogoTramiteExistente = "#tramitesClasificacionExistentes";
var dialogoNoProcedeTramite;
var idDialogoNoProcedeTramite = "#dgDialogoNoProcedeTramite";
var idFiltroDef = "#gridSolicitudes_filter";
var gridRegistrosPatronales;
var dialogoTramiteDatosFiscalesPrecargados;
var dialogoSolicitarAsignacion;
var firmaDigitalCtrl;

$(document)
		.ready(
				function() {
					$("#dialogoRechazo").hide();
					if (isOperadorIMSS) {
						$("#btnEnviarSolicitud").hide();
						$("#btnCancelarSolicitud").show();
						$("#btnConcluirSolicitud").show();
						$("#btnBusquedaRFC").show();
					}else if(isRL){
						$("#btnEnviarSolicitud").show();
						$("#btnCancelarSolicitud").show();
						$("#btnConcluirSolicitud").hide();
						$("#btnBusquedaRFC").show();
					}else{
						$("#btnEnviarSolicitud").show();
						$("#btnCancelarSolicitud").show();
						$("#btnConcluirSolicitud").hide();
						$("#btnBusquedaRFC").hide();
					}
					
					
					if(folioSolicitudDatosPatronales!= undefined && folioSolicitudDatosPatronales!=''){
						if($("#noFolioActual") != undefined ){
							$("#noFolioActual").html(folioSolicitudDatosPatronales);
						}
						if($("#infoFolio") != undefined ){
							$("#infoFolio").show();
						}
					}else{
						$("#infoFolio").hide();
					}
					
					evaluarBotonesSolicitud();
					// Set up a listener so that when anything with a class of
					// 'tab'
					// is clicked, this function is run.
					$('.tab')
							.click(
									function() {

										// Remove the 'active' class from the
										// active tab.
										$('#tabs_container > .tabs > li.active')
												.removeClass('active');

										// Add the 'active' class to the clicked
										// tab.
										$(this).parent().addClass('active');

										// Remove the 'tab_contents_active'
										// class from the visible tab contents.
										$(
												'#tabs_container > .tab_contents_container > div.tab_contents_active')
												.removeClass(
														'tab_contents_active');

										// Add the 'tab_contents_active' class
										// to the associated tab contents.
										$(this.rel).addClass(
												'tab_contents_active');
										$(this.rel).focus();

									});

					$(idFiltroDef).dialog({
						autoOpen : false
					});

					dialogoError = $(idDialogoError).dialog({
						autoOpen : false,
						resizable : false,
						height : 175,
						width : 300,
						modal : true,
						buttons : {
							'Aceptar' : function() {
								$(this).dialog("close");
							}
						}
					});

					dialogoNoProcedeTramite = $(idDialogoNoProcedeTramite)
							.dialog({
								autoOpen : false,
								resizable : false,
								height : 300,
								width : 300,
								modal : true,
								buttons : {
									'Aceptar' : function() {
										$(this).dialog("close");
									}
								}
							});

					dialogoTramites = $(idTramites).dialog({
						autoOpen : false,
						resizable : false,
						height : 550,
						width : 400,
						modal : true,
						buttons : {
							'Continuar' : validaSeleccion
						}
					});

					dialogoTramiteClasifExistente = $(idDialogoTramiteExistente)
							.dialog({
								autoOpen : false,
								resizable : false,
								height : 175,
								width : 400,
								modal : true,
								buttons : {
									'Aceptar' : function() {
										$(this).dialog("close");
									}
								}
							});

					dialogoTramiteDatosFiscalesPrecargados = $(
							'#dialogoMensajesTramitePrecargado').dialog({
						autoOpen : false,
						resizable : false,
						height : 175,
						width : 300,
						modal : true,
						buttons : {
							'Aceptar' : function() {
								$(this).dialog("close");
							}
						}
					});

					$("#selectable").selectable(
							{
								selected : function(event, ui) {
									$(ui.selected).siblings().removeClass(
											"ui-selected");
								},
								stop : function() {
									$(".ui-selected", this).each(
											function() {
												index = $("#selectable li")
														.index(this);
											});
								}
							});
		if(esPatronFisico)
			inhabilitaSeleccionRepresentante();
		
		inicializarComponenteFirmaDigital();
});




function validaSeleccion() {
	if (index != -1) {
		$("#idSolicitud").val("");
		$("#idTramite").val($("#selectable li")[index].id);
		dialogoTramites.dialog('close');
		navegar(context_path + '/clasificacion/');

	}
}

function navegar(url, tipoTramiteCodigo) {

	var proceder = true;

	if (tipoTramiteCodigo != undefined) {
		$(oTableTramites.fnSettings().aoData)
				.each(
						function() {
							// alert("codigo: " + tipoTramiteCodigo);
							if (this._aData.tipoTramite.idTipoTramite == tipoTramiteCodigo) {
								// alert("id de tramite para actualizar rep
								// legal: " +
								// this._aData.tipoTramite.idTipoTramite + ", no
								// se puede proceder ya que hay un tamite en
								// curso");
								$('#dgDialogoNoProcedeTramite').attr('tittle',
										this._aData.tipoTramite.descripcion);
								proceder = false;
								return;
							}
						});
	}

	if (proceder) {

		document.getElementById('patronForm').action = url;
		document.getElementById('patronForm').submit();
	} else {
		dialogoNoProcedeTramite.dialog('open');
	}

}

function validaSeleccionSol() {
	$("#idSolicitud").val("");
	$("#idTramite").val("");

	var obRowSelected = fnGetRowSelected(oTableTramites);
	if (obRowSelected == undefined) {
		dialogoError.dialog('open');
	} else {
		var url = "/afiliacion/evaluarSeleccionDeSolicitud";
		// var solicitudId=obRowSelected.solicitudId;
		var solicitudObj = new Object();
		solicitudObj.solicitudId = obRowSelected.solicitudId;
		solicitudObj.sujetoObligado = new Object();
		// solicitudObj.sujetoObligado.tipoPersonaFiscal = new Object();
		solicitudObj.sujetoObligado.tipoPersonaFiscal = $("#tipoPersonaFiscal")
				.val();
		if (obRowSelected.sujetoObligado != null
				|| obRowSelected.sujetoObligado != undefined)
			solicitudObj.sujetoObligado.numeroRegistroPatronal = obRowSelected.sujetoObligado.numeroRegistroPatronal;

		// sendToServer(url, solicitudId, callbackEjecutarOperacionEvaluada,
		// false);
		sendToServer(url, solicitudObj, callbackEjecutarOperacionEvaluada,
				false);
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
		sujetoObigadoTramite.fisica.nombre=$("#fisica\\.nombre").val();
		sujetoObigadoTramite.fisica.primerApellido=$("#fisica\\.primerApellido").val();
		sujetoObigadoTramite.fisica.segundoApellido=$("#fisica\\.segundoApellido").val();
	} else {
		sujetoObigadoTramite.moral = new Object();
		sujetoObigadoTramite.moral.idPersona = $("#moral\\.idPersona").val();
		sujetoObigadoTramite.moral.rfc = $("#moral\\.rfc").val();
		sujetoObigadoTramite.moral.razonSocial = $("#moral\\.razonSocial").val();
	}
}


function validaTramiteExiste() {
	var sSource = context_path
			+ '/sujetoObligado/validaTramiteClasificacionExistente';
	var request = $.ajax({
		url : sSource,
		async : false,
		type : "POST",
		data : idSujetoObligado ? JSON.stringify(idSujetoObligado) : null,
		dataType : "json",
		contentType : "application/json; charset=utf-8",
	});
	request
			.done(function(response) {
				if (response.errors != undefined) {
					alert(response.errors);
				} else {
					indicadorTramiteClasifExistente = response.existeTramiteClasificacion;
				}
			});

}

function enviarSolicitud(funcionEjecturar) {
	$("#textoConfirmacion")
			.html(
					"Una vez enviada la solicitud al Instituto usted no podr\u00E1 realizar modificaciones.<br>"
							+ "Favor de confirmar la Finalizaci\u00F3n de la Captura/Edici\u00F3n de la Solicitud.<br> ");
	var dialogo = $("#dialogoConfirmacion").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 150,
		width : 650,
		title : "Confirmaci&oacute;n",
		buttons : {
			"Aceptar" : function() {
				funcionEjecturar();
				$(this).dialog("close");
			},
			"Cancelar" : function() {
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');

}

function validarEnvioSolicitud() {		
	inicializaSujetoTramiteGeneral();
	sendToServer('/afiliacion/validarSolicitudCompletes', idSolicitud,
			callbackvalidarEnvioSolicitud, false);
}

function callbackvalidarEnvioSolicitud(response) {
	$.unblockUI();
	if (response.mensajeError=="valido"){
		ejecutarEnvioDeSolicitud();
	}
	else{
		procesarRespuestaServer(response, callbackConfirmarvalidarEnvioSolicitud);
	}
		
}

function callbackConfirmarvalidarEnvioSolicitud(response){	
	if(isRL)
		navegarTo("/sujetoObligado", "busquedaRFCForm");
	else
		navegarTo("/afiliacion/visualizarDetalleRFC","formSupport");
}

function ejecutarEnvioDeSolicitud() {	
	//Se muestra la ventana para que el usuario pueda firmar digitalmente
	dialogFirma =  $("#dialogoConcluirSolicitudFirma").dialog({
		autoOpen:false,
		resizable: false,
		height: 550,
		width: 400,
		modal: true		
	});
	$("#dialogoConcluirSolicitudFirma").css("display", "block");
	dialogFirma.dialog('open');		
}

function ejecutarEnvioDeSolicitudSinFirma(){
	dialogFirma.dialog('close');		
	$.blockUI();
	inicializaSujetoTramiteGeneral();	
	sendToServer('/afiliacion/enviarSolicitud', sujetoObigadoTramite,
			callbackEnvioSolicitud, false);
}

function callbackEnvioSolicitud(response) {
	$.unblockUI();
	var respuesta = procesarRespuestaServer(response, callbackConfirmarEnvioSolicitud, 200, 650);
	
	if (respuesta) {
		document.getElementById('formReporteModificacionPatronal').method = 'POST';
		document.getElementById('formReporteModificacionPatronal').target = '_blank';
		document.getElementById('formReporteModificacionPatronal').action = context_path
				+ '/afiliacion/procesarInformacionAcuseAfiliacion?origen='+response.tipoDocumento;
		document.getElementById('formReporteModificacionPatronal').submit();
	}	
}

function callbackConfirmarEnvioSolicitud(response){			
	if (!error) {	
		$("#btnEnviarSolicitud").hide();
		if (isOperadorIMSS) {
			$("#btnConcluirSolicitud").show();
		}

		var rfcParam;
		if (tipoPersonaFiscal == "FISICA") {
			rfcParam = $("#fisica\\.rfc").val();
		} else {
			rfcParam = $("#moral\\.rfc").val();
		}

		navegarTo('/sujetoObligado/recargarTramites?rfc=' + rfcParam,
				'formSupport');
	}
}

function concluirSolicitudDatosFiscales() {
	obtenerRepresentantesEnTramite();
}

//function obtenerRepresentantesEnTramite(){
//	var solicitud = new Object();
//	solicitud.sujetoObligado = new Object();
//	solicitud.solicitudId=idSolicitudActiva;
//	solicitud.sujetoObligado.tipoPersonaFiscal=tipoPersonaFiscal;
//	if(esPatronFisico){
//		solicitud.sujetoObligado.fisica = new Object();
//		solicitud.sujetoObligado.fisica.idPersona=$("#fisica\\.idPersona").val();
//		solicitud.sujetoObligado.fisica.tipoPersona = new Object();
//		solicitud.sujetoObligado.fisica.tipoPersona.idTipoPersona=idTipoPersonaFisica;
//	}else{
//		solicitud.sujetoObligado.moral = new Object();
//		solicitud.sujetoObligado.moral.idPersona=$("#moral\\.idPersona").val();
//		solicitud.sujetoObligado.moral.tipoPersona = new Object();
//		solicitud.sujetoObligado.moral.tipoPersona.idTipoPersona=idTipoPersonaMoral;
//	}
//		
//	sendToServer('/afiliacion/obtenerRepresentantesDisponibles?esPatronFisico='+esPatronFisico,
//			solicitud,
//			callbackObtenerRepresentantesEnTramite, false);
//}
//
//function callbackObtenerRepresentantesEnTramite(response){
//	var options = document.getElementById('idRepresentante').options;
//	options.length = 0;
//	options[options.length] = new Option('-- Seleccione --', '-1', true, true);
//	
//	for(indexRepresentante in response.representantesDisponibles){
//		options[options.length] = new Option(
//				response.representantesDisponibles[indexRepresentante].personaFisica.nombre+" "+
//				response.representantesDisponibles[indexRepresentante].personaFisica.primerApellido+" "+
//				response.representantesDisponibles[indexRepresentante].personaFisica.segundoApellido, 
//				response.representantesDisponibles[indexRepresentante].personaFisica.idPersona);
//	}
//	
//	var oDialogo;
//	construirDialogoParaPagina("#formaSolicitadoPor", oDialogo, "Especificar Solicitante", false, 
//			callbackSolicitadoPor, undefined, 250, 600);
//}

function callbackSolicitadoPor(response){
	var selectedRadio = getSelectedRadioButton(document.getElementsByName("radioSolicitadoPor"));
	var idSolicitante=$("#solicitadoPorForm #idRepresentante").val();
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
		inicializaSujetoTramiteGeneral();
		sendToServer('/afiliacion/concluirSolicitud?idSolicitud='
				+ idSolicitudActiva+'&rolSolicitante='+selectedRadio.value+'&idSolicitante='+idSolicitante, sujetoObigadoTramite,
				callbackConcluirSolicitudDatosFiscales, true);
	}
	
}

function callbackConcluirSolicitudDatosFiscales(response) {
	$.unblockUI();
	var height;
	var width=500;
	if (response.mensajeExito != undefined && response.mensajeExito != null){
		height = 150;
	}else{
		height = 300;
	}
	
	var respuestaProcesar = procesarRespuestaServer(response, callbackConfirmarConclusionSolicitud,height, width);
	if (respuestaProcesar) {
		document.getElementById('formReporteModificacionPatronal').method = 'POST';
		document.getElementById('formReporteModificacionPatronal').target = '_blank';
		document.getElementById('formReporteModificacionPatronal').action = context_path
				+ '/afiliacion/procesarInformacionAcuseAfiliacion?origen=COMPROBANTE';
		document.getElementById('formReporteModificacionPatronal').submit();
	}
}

function callbackConfirmarConclusionSolicitud(response){
	if (!error) {
		$("#btnConcluirSolicitud").hide();
		var rfcParam;
		if (tipoPersonaFiscal == "FISICA") {
			rfcParam = $("#fisica\\.rfc").val();
		} else {
			rfcParam = $("#moral\\.rfc").val();
		}

		navegarTo('/sujetoObligado/recargarTramites?rfc=' + rfcParam,
				'formSupport');
	}
}

function fnConcluirConFirma (){		
	$("#formConcluirConFirma #idSujetoObligado").val(idSujetoObligado);
	$("#formConcluirConFirma #numRegPatronal").val($('#numeroRegistroPatronal').val());
	$("#formConcluirConFirma #idSolicitudActiva").val(idSolicitudActiva);
	document.forms.formConcluirConFirma.action=contextPath+'../afiliacion/iniciaProcesoFirmaDigital';
	document.forms.formConcluirConFirma.submit();	
}//btnConcluirConFirma


function presentarSolicitudConfirmacionCancelacion(){
	var dialogoSolicitarConfirmacion;
	construirDialogoGenericoDeConfirmacion("#dialogoMensajes", dialogoSolicitarConfirmacion, "Confirmaci\u00F3n", 
			"\u00BFEst\u00E1 seguro que desea cancelar la solicitud?", false, cancelarSolicitudDatosFiscales);
}

function despliegaMensajeConfirmacionRechazo(){
	
	var oDialogo;
	construirDialogoGenericoDeConfirmacion("#dialogoMensajes", oDialogo, "Confirmaci\u00F3n", "\u00BFEst\u00E1 seguro que desea rechazar la solicitud?", false, 
			despliegaDialogoRazonRechazo);

}

function despliegaDialogoRazonRechazo(response){
	var oDialogo;
	construirDialogoParaPagina("#dialogoRechazo", oDialogo, "Raz\u00F3n de rechazo", false, 
			rechazarSolicitudDatosFiscales);
}


function cancelarSolicitudDatosFiscales(response) {
	$.blockUI();
	inicializaSujetoTramiteGeneral();
	var solicitudObj = new Object();
	solicitudObj.solicitudId=idSolicitudActiva;
	solicitudObj.sujetoObligado = new Object();
	solicitudObj.sujetoObligado = sujetoObigadoTramite;
	sendToServer('/afiliacion/cancelarSolicitud', solicitudObj,
			callbackCancelarSolicitudDatosFiscales, true);
}

function rechazarSolicitudDatosFiscales(){
	inicializaSujetoTramiteGeneral();
	var razonCancelacion = $("#idRazonCancelacion").val();
	
	if(razonCancelacion==undefined || (razonCancelacion!=undefined && razonCancelacion=='-1')){
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Debe seleccionar la raz\u00F3n del rechazo.", 
				true, despliegaDialogoRazonRechazo, undefined, 150, 450);
	}else{
		var solicitudObj = new Object();
		solicitudObj.solicitudId=idSolicitudActiva;
		solicitudObj.noFolioSolicitud = folioSolicitudDatosPatronales;
		solicitudObj.sujetoObligado = new Object();
		solicitudObj.sujetoObligado = sujetoObigadoTramite;
		solicitudObj.razonCancelacion = new Object();
		solicitudObj.razonCancelacion.idRazonCancelacion = razonCancelacion;
		sendToServer('/afiliacion/rechazarSolicitud', solicitudObj,
				callbackRechazarSolicitudDatosFiscales, true);
	}
}

function callbackCancelarSolicitudDatosFiscales(response) {
	procesarRespuestaServer(response, callbackConfirmarCancelacionSolicitud);
	$.unblockUI();
}

function callbackRechazarSolicitudDatosFiscales(response) {
	procesarRespuestaServer(response, callbackConfirmarCancelacionSolicitud);
	$.unblockUI();
}


function callbackConfirmarCancelacionSolicitud(response){
	if (!error) {
		$("#btnConcluirSolicitud").hide();
		var rfcParam;
		if (tipoPersonaFiscal == "FISICA") {
			rfcParam = $("#fisica\\.rfc").val();
		} else {
			rfcParam = $("#moral\\.rfc").val();
		}

		navegarTo('/sujetoObligado/recargarTramites?rfc=' + rfcParam,
				'formSupport');
	}
}

function validaRPSeleccionado(){
	var obRowSelected = fnGetRowSelected(gridRegistrosPatronales);
	if (obRowSelected == undefined && $("#numeroRegistroPatronal").val() == ''){
		dialogoError.dialog('open');
	}else{
		inicializaSujetoTramiteGeneral();
		var obRowSelected = fnGetRowSelected(gridRegistrosPatronales);
		if($("#numeroRegistroPatronal").val() != ''){
			sujetoObigadoTramite.numeroRegistroPatronal = $("#numeroRegistroPatronal").val();
		}else if(obRowSelected!= undefined){
			sujetoObigadoTramite.numeroRegistroPatronal = obRowSelected.numeroRegistroPatronal;
			document.getElementById('numeroRegistroPatronal').value = obRowSelected.numeroRegistroPatronal;
		}
		
		sendToServer('/afiliacion/validaRegistroPatronalPermitido', 
				sujetoObigadoTramite, callbackValidaRPSeleccionado, false);
	}
}

function callbackValidaRPSeleccionado(response){
	if(response.mensajeError != undefined
			&& response.mensajeError != null) {
		titulo = "Operaci&oacute;n Erronea";
		error = true;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}
		construirDialogoTramiteMensajeConfirmacion(titulo, mensaje, error);
	} else {
		navegarTo('/afiliacion/mostrarDetalleRegistroPatronal',
		'registrosPatronalesForm');
	}
}


function navegarDetalle() {
	var obRowSelected = fnGetRowSelected(gridRegistrosPatronales);
	if (obRowSelected == undefined) {
		dialogoError.dialog('open');
	} else {
		document.getElementById('numeroRegistroPatronal').value = obRowSelected.numeroRegistroPatronal;
		$.blockUI();
		navegarTo('/afiliacion/mostrarDetalleRegistroPatronal',
				'registrosPatronalesForm');
	}
}

function navegarDetalleCT() {	
	var obRowSelected = fnGetRowSelected(oTableTramites);
	if (obRowSelected == undefined) {
		dialogoError.dialog('open');
	} else {
		document.getElementById('numeroRegistroPatronal').value = obRowSelected.numeroRegistroPatronal;
		$.blockUI();
		navegarTo('/afiliacion/mostrarDetalleRegistroPatronal',
				'registrosPatronalesForm');
	}
}

function navegarAConsultaSolicitudes(){
	navegarTo('/solicitud/consultaAvanzadaDeSolicitudes',
	'detalleRPForm');
}

function navegarABusquedaRFC(){
	navegarTo("/sujetoObligado", "busquedaRFCForm");
}

function evaluarBotonesSolicitud(){
	if(idSolicitud!=undefined && idSolicitud>0){
		$("#formSupport #btnEnviarSolicitud").removeAttr("disabled");
		$("#formSupport #btnConcluirSolicitud").removeAttr("disabled");
		if($("#formSupport #btnCancelarSolicitud")!=undefined)
			$("#formSupport #btnCancelarSolicitud").removeAttr("disabled");
		if($("#formSupport #btnRechazarSolicitud")!=undefined)
			$("#formSupport #btnRechazarSolicitud").removeAttr("disabled");
	}else{
		$("#formSupport #btnEnviarSolicitud").attr("disabled", "true");
		$("#formSupport #btnConcluirSolicitud").attr("disabled", "true");
		if($("#formSupport #btnCancelarSolicitud")!=undefined)
			$("#formSupport #btnCancelarSolicitud").attr("disabled", "true");
		if($("#formSupport #btnRechazarSolicitud")!=undefined)
			$("#formSupport #btnRechazarSolicitud").attr("disabled", "true");
	}
}

function habilitaSeleccionRepresentante(){
	$("#idRepresentante").removeAttr("disabled");
}

function inhabilitaSeleccionRepresentante(){
	$("#idRepresentante").attr("disabled", "true");
}

function inicializarComponenteFirmaDigital(){
	$.getScript("/gestionSolicitud-web/static/resources/js/delta/firma-digital/FirmaDigital.js", function(){
		 firmaDigitalCtrl = FirmaDigitalCtrl;
		 
		 // Div para crear el diálogo
		 firmaDigitalCtrl.init('firmaDigitalDialogo');
		 
		 // Función de callback
		 firmaDigitalCtrl.setOnCloseCallback(procesarRespuestaFirmaDigital);
		});
	
	$('#btnConcluirConFirma').click(function(){
		 //Se settean los valores de entrada
		var rfcSujetoObligado=undefined;
		
		var registroPatronal = $('#numRegPatronal').val();
		
		if($("#fisica\\.rfc").val()!=undefined && $("#fisica\\.rfc").val()!="")
			rfcSujetoObligado = $("#fisica\\.rfc").val();
		else if ($("#moral\\.rfc").val()!=undefined && $("#moral\\.rfc").val()!="")
			rfcSujetoObligado = $("#moral\\.rfc").val();
		
		
		
		firmaDigitalCtrl.datosEntrada.rfc = rfcSujetoObligado;
		firmaDigitalCtrl.datosEntrada.nrp = rfcSujetoObligado;
		//TODO armar cadena original
		firmaDigitalCtrl.datosEntrada.contenido = rfcSujetoObligado;
		firmaDigitalCtrl.datosEntrada.firmarArchivo = false; 
		
		
		 // Se llama al servicio de firma digital
		firmaDigitalCtrl.firmaDigital();
	
	});

}

function procesarRespuestaFirmaDigital(response){
	var firmaResponse = firmaDigitalCtrl.getDatosSalida();
	if(firmaResponse!=undefined && firmaResponse!=null){
		var sSource = context_path + '/clasificacion/procesarDatosFirma';
		prepararRequest(sSource, firmaResponse, false, enviaSolicitudFirmada);
	}else{
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "La operaci\u00F3n de firma electr\u00F3nica no se realiz\u00F3 satisfactoriamente", true, undefined, undefined, 150, 450);
	}
}

function enviaSolicitudFirmada(){
	dialogFirma.dialog('close');		
	$.blockUI();
	inicializaSujetoTramiteGeneral();	
	sendToServer('/afiliacion/enviarSolicitud', sujetoObigadoTramite,
			callbackEnvioSolicitud, false);
}
