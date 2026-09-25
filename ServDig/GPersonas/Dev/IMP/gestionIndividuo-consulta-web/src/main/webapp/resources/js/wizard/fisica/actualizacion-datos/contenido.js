var urlCWizardTramite	= context_path + '/wizard/tramite/actualizar/datos/';

var dialogoConfirmarCancelar;
var dialogoConfirmar;
var idForma = 'soForm';

$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	/*
	 * se iniciliza el componente del acordeon con la opcion 'autoHeight: false'
	 * para que cada DIV colapsable tenga la altura de acuerdo a su contenido
	 */
	$('#acordeon').accordion({
		autoHeight : false,
		collapsible : true,
		change: function( event, ui ) {
			setSizeWithinIframe(document);
		}
	});

	// se renderea la tabla de resultados del renapo como dataTable
	$('#tabla-imss-renapo').dataTable({
		bInfo : false,
		sPaginationType : null,
		bJQueryUI : false,
		bSort : false,
		bFilter : false,
		bPaginate : false,
		bAutoWidth : true

	});

	// se renderea la tabla de resultados del sat como dataTable
	$('#tabla-imss-sat').dataTable({
		bInfo : false,
		sPaginationType : null,
		bJQueryUI : false,
		bSort : false,
		bFilter : false,
		bPaginate : false,
		bAutoWidth : true

	});

	//Siguiente - Seleccion de RL
	$('#mostrarRLVentanilla').click(function() {
		procesarSolicitudVentanilla();
	});
	
	
	$('#finalizarTramite').click(function() {
		validarDoctos();		
	});

	$('#finalizarTramiteVen').click(function() {
		finalizarTramite();
	});

	$('#guardarTramite').click(function() {
		guardarTramite();
	});

	$('#cancelarTramite').click(function() {
		dialogoConfirmarCancelar.dialog( "open" );
	});

	$('#cerrarWizard').click(function() {
		cerrarWizard();
	});
	
	$('#capturarDocumentos').click(function() {
		capturarDocumentos();
	});
	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"Cancelar": function() {
		 		$( this ).dialog( "close" );
		 	},
			"Aceptar": function() {
				cancelarTramite();
		 	}
		 	
		 }
	 });
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false
	 });
	
	
	if(parent.WizardActualizacionDatosCtrl.config.idOrigen == 2){
		//INTERNET
		/* 
		 * Al cerrar el di�logo de la firma digital,
		 * se ejecuta la funci�n para finalizar el tr�mite
		 */ 
		parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
			dialogoConfirmar.dialog("option", "buttons", [ {
				text : 'Aceptar',
				click : function() {
					$(this).dialog('close');
				}
			}]);
			
			if(parent.FirmaDigitalCtrl.datosSalida == null) {
				$('#mensajeDialogo').text("La validaci\u00f3n de la firma no pudo ser realizada");
				dialogoConfirmar.dialog('open');
			}else {
				if(parent.FirmaDigitalCtrl.datosSalida.Resultado == 0 && parent.FirmaDigitalCtrl.datosSalida.firmas) {
					var firmaResponse = {
						cadenaOriginal               : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
						recibo                       : parent.FirmaDigitalCtrl.datosSalida.firmas[0],
						reciboNotarial               : parent.FirmaDigitalCtrl.datosSalida.folio,
						urlAcuseFirma                : parent.FirmaDigitalCtrl.datosSalida.acuse,
						serialCertificado            : parent.FirmaDigitalCtrl.datosSalida.serie_cert,
						strIniciaVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigIni,
						strFinVigenciaCertificado    : parent.FirmaDigitalCtrl.datosSalida.vigFin
					};
	
					firmarTramite(firmaResponse);
				} else {
					$('#mensajeDialogo').text("La validaci\u00f3n de la firma no pudo ser realizada");
					dialogoConfirmar.dialog('open');
				}
			}
		});
	}

});

function validarDoctos() {
	var docs = $("#isDerechohabiente").val() == "1";
	if(docs && !parent.WizardCapturaDocumentosProbatoriosCtrl.isCapturaFinalizada()) {
		mostrarMensajeDocumentos();
	} else {		
		firmarTramiteActualizacion();		
	}
}

function mostrarMensajeDocumentos() {	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'Aceptar',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	$('#mensajeDialogo').html("Es necesario capturar la documentacion requerida para finalizar el tr&aacute;mite.");
	dialogoConfirmar.dialog('open');
}

function mostrarMensajeDocumentosExixtentes(){
	dialogoConfirmar.dialog("option", "buttons", [{
		text : 'Cancelar',
		click : function() {
			$(this).dialog('close');
		}
	} ,{
		text : 'Aceptar',
		click : function() {
			capturarDocumentos();
			$(this).dialog('close');
		}
	}]);
	
	$('#mensajeDialogo').html("Se eliminaran los datos de los documentos que ya se han capturado");
	dialogoConfirmar.dialog('open');
}

function capturarDocumentos () {
	var tipoTramite = 24;
	var idTramite = $("#tramiteId").val();
	var documentosANoMostrar = "10_11_12_13_14_15_16";
	
	if(!parent.WizardCapturaDocumentosProbatoriosCtrl.isPrimeraCaptura()) {
		mostrarMensajeDocumentosExixtentes();
	} else {
		var datosOpcionales = {'documentosNoMostrados': documentosANoMostrar};
		parent.WizardCapturaDocumentosProbatoriosCtrl.init("divCapturaDocs",tipoTramite,idTramite, datosOpcionales);
		parent.WizardCapturaDocumentosProbatoriosCtrl.abrir();
	}
}

function firmarTramiteActualizacion() {
	var componenteFirma = {
			idTipoSolicitud : codigoTipoSolicitud,
			descripcionTipoSolicitud : descripcionTipoSolicitud,
			idTipoTramite : arrayCodigoTipoTramite,
			folioSolicitud : $('#folioSolicitud').val(),
			curp : parent.FirmanteCtrl.curp,
			rfc : parent.FirmanteCtrl.rfc,
			validarRFC : true,
			registroPatronal : datosEntradaFirma.registroPatronal,
			nombreCompleto : parent.FirmanteCtrl.nombreRazonSocial,
			fechaElectronica : datosEntradaFirma.fechaElectronica,
			cad_original : $('#contenidoFirmar').val(),
			tipo_operacion : 'firmaCMS',
			firma_archivo : false,
			min_archivos : 0,
			max_archivos : 0,
			afectado: [parent.AtributosPersonaCtrl.personaPortal],
			afectadoNuevosValores: [nuevosDatosPersonales],
			tipoAcuse: '1',
			acuse: 'CNRS'
		};
		
		parent.iniciarFirmaDigital(componenteFirma);
}

function construirAfectado() {
	var personaAfectada = new Object();
	personaAfectada.nombreRazonSocial = datosEntradaFirma.nombreCompleto;
	personaAfectada.rfc = datosEntradaFirma.rfc;
	personaAfectada.curp = datosEntradaFirma.curp;
	
	return [personaAfectada];
}

function firmarTramite(firmaResponse) {
	var url = urlCWizardTramite + 'procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function finalizarTramite() {
	var idSolicitudPendiente = $('#idSolicitud').val();
	var folioSolicitudPendiente = $('#folioSolicitud').val();
	var personaFirmada = 0;
	
	if (typeof parent.AtributosPersonaCtrl !== 'undefined') {
		personaFirmada = parent.AtributosPersonaCtrl.personaFirmada.idPersona;
	}
	
	if(isEmpty(personaFirmada))
		personaFirmada=0;
		
	var url = urlCWizardTramite + 'finalizar/solicitud/' + idSolicitudPendiente+"/" + personaFirmada;
	var personaMoral=new Object();

	prepararRequest(url, personaMoral, true, function(response) {
		if (response.mensaje != undefined && response.mensaje != null) {
			var folioSolicitud = $('#folioSolicitud').val();
			var idSolicitud = $('#idSolicitud').val();

			parent.ProcesandoSolicitudCtrl.abrir(folioSolicitudPendiente);
			cerrarWizard();
		}else{
			var mensaje = "Ocurrio un error al procesar la solicitud.";
			if (response.error == "") {
			} else {
				mensaje = response.error;
			}
			mostrarDialogoH(mensaje);
		}
	});
}

function guardarTramite() {
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = urlCWizardTramite + 'guardar/solicitud/' + idSolicitudPendiente;
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'Aceptar',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	$.postJSON(url, null, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function cancelarTramite() {
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = urlCWizardTramite + 'cancelar/solicitud/'	+ idSolicitudPendiente;
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'Aceptar',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	$.postJSON(url, null, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function cerrarWizard() {	
	parent.WizardActualizacionDatosCtrl.cerrar();
}

function isEmpty(valor) {
	return (valor == undefined || valor == "");
}

function prepararRequest(sSource, data, async, callback) {
	var request = $.ajax({
		url : sSource,
		async : async,
		type : "POST",
		data : data ? JSON.stringify(data) : null,
		dataType : "json",
		contentType : "application/json; charset=utf-8"
	});
	request.done(callback);
	request.fail(callback);
}

function mostrarDialogoH(mensaje) {	
	$('#mensajeDialogo').html(mensaje);
	dialogoConfirmarCommon.dialog('open');
}

//Funciones para mostrar seleccion de Representantes Legales
function procesarSolicitudVentanilla() {
	var idSolicitudPendiente = $('#idSolicitud').val();
	var folioSolicitudPendiente = $('#folioSolicitud').val();
	var personaFirmada = 0;
	
	if (typeof parent.AtributosPersonaCtrl !== 'undefined') {
		personaFirmada = parent.AtributosPersonaCtrl.personaFirmada.idPersona;
	}
	
	if(isEmpty(personaFirmada))
		personaFirmada=0;
	var personaMoral=new Object();	
	var url = urlCWizardTramite + 'finalizar/solicitud/' + idSolicitudPendiente+"/" + personaFirmada;
	prepararRequest(url, personaMoral, true, mostrarRepresentantesLegales);
	
}

function mostrarRepresentantesLegales(response){
	if (response.mensaje != undefined && response.mensaje != null) {
		var idSolicitudPendiente = $('#idSolicitud').val();
		setTimeout(function(){
			var urlAction = parent.representantesLegalesCtrl.config.urlRLVentanilla
				+ idSolicitudPendiente+"/"+idTipoTramite;
			$.blockUI();
			$('form#'+idForma).attr('action', urlAction);
			$('form#'+idForma).submit();
		}, 90);
	}else{
		var mensaje = "Ocurrio un error al procesar la solicitud.";
		if (response.error == "") {			
		} else {
			mensaje = response.error;
		}
		mostrarDialogoH(mensaje);
	}	
}