var dialogoConfirmarCancelar;
var dialogoConfirmar;
var dialogoError;

$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	$("#fechaDefuncion").datepicker({
		show: "both",
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate: new Date(), 
		yearRange : '-112:+0'
	});
	
	/*
	 * se iniciliza el componente del acordeon con la opcion 'autoHeight: false'
	 * para que cada DIV colapsable tenga la altura de acuerdo a su contenido
	 */
	$('#acordeon').accordion({
		autoHeight : false,
		collapsible : true
	});
	
	$('#capturarDocumentosBaja').click(function() {
		var tipoTramite = $("#tipoTramite\\.idTipoTramite").val();
		var idTramite = $("#tramiteId").val();
		
		if(!parent.WizardCapturaDocumentosProbatoriosCtrl.isPrimeraCaptura()) {
			mostrarMensajeDocumentosExixtentes();
		} else {
			parent.WizardCapturaDocumentosProbatoriosCtrl.init("divCapturaDocs",tipoTramite,idTramite);
			parent.WizardCapturaDocumentosProbatoriosCtrl.abrir();
		}
	});
	
	$('#finalizarTramite').click(function() {
		validarInfo();
	});

	$('#guardarTramite').click(function() {
		guardarTramite();
	});

	$('#cancelarTramite').click(function() {
		dialogoConfirmarCancelar.dialog( "open" );
	});

	$('#cerrarWizard').click(function() {
		parent.WizardCapturaDocumentosProbatoriosCtrl.limpiarIndicadores();
		cerrarWizard();
	});
	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		dialogClass: "no-close",
	    closeOnEscape: false,
		buttons: {
			"ACEPTAR": function() {
				cancelarTramite();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		dialogClass: "no-close",
	    closeOnEscape: false
	 });
	
	dialogoError = $( "#dialog-error" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		dialogClass: "no-close",
	    closeOnEscape: false,
	    buttons: {
		 	"ACEPTAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });
});

function validarInfo() {
	var oForm = $("form#formularioBajaDerechohabiente").toObject();
	var url = '/portalDerechohabiente-web/wizard/baja/validaciones';
	fnHideErrores("form#formularioBajaDerechohabiente");
		
	$.postJSON(url, oForm, function(data2) {
		validarDocumentos();
	}).error(function(data){
		fnProcesarErrores(data, "form#formularioBajaDerechohabiente");
	});
}

function validarDocumentos() {
	var requiereDocs = $("#requiereDocs").val() == 1;
	var tipoTramite = $("#tipoTramite\\.idTipoTramite").val();
	
	if(requiereDocs) {
		if(parent.WizardCapturaDocumentosProbatoriosCtrl.isCapturaFinalizada()) {
			invocarFirmaDigital();
		} else {
			mostrarMensajeError("Debe completar la documentaci&oacute;n para finalizar el tr&aacute;mite");
		}
	} else {
		invocarFirmaDigital();
	}
	
	
}

function invocarFirmaDigital() {
	
	var numeroArchivos = 0;
	var requiereDoctos = false;
	
	if(requiereDocs) {
		if(parent.WizardCapturaDocumentosProbatoriosCtrl.getNumeroDocumentosRequeridos() > 0) {
			requiereDoctos = true;
			numeroArchivos = parent.WizardCapturaDocumentosProbatoriosCtrl.getNumeroDocumentosRequeridos();
		}
	}

	parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
		
		if(parent.FirmaDigitalCtrl.datosSalida == null) {
			mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada");
		}else {
			if(parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
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
				mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada");
			}
		}
	});
	
	var componenteFirma = {
		tipo_operacion :'firmaCMS',
		acuse:'AcuseV1.0',
		rfc: parent.FirmanteCtrl.rfc,
		validarRFC :true,
		curp: parent.FirmanteCtrl.curp,
		firma_archivo : requiereDoctos,
		min_archivos : numeroArchivos,
		max_archivos : numeroArchivos,
		fechaElectronica : datosEntradaFirma.fechaElectronica,
		cad_original:$('#contenidoFirmar').val(),
		registroPatronal : "",
		nombreCompleto : parent.FirmanteCtrl.nombreRazonSocial,
		idTipoSolicitud : codigoTipoSolicitud,
		descripcionTipoSolicitud : descripcionTipoSolicitud,
		folioSolicitud : $('#folioSolicitud').val(),
		idTipoTramite : arrayCodigoTipoTramite
	};

/*
	var componenteFirma = {
			idTipoSolicitud : codigoTipoSolicitud,
			descripcionTipoSolicitud : descripcionTipoSolicitud,
			folioSolicitud : $('#folioSolicitud').val(),
			idTipoTramite : arrayCodigoTipoTramite,
			curp : parent.FirmanteCtrl.curp,
			rfc : parent.FirmanteCtrl.rfc,
			validarRFC : true,
			registroPatronal : registrosPatronales,
			nombreCompleto : parent.FirmanteCtrl.nombreRazonSocial,
			fechaElectronica : datosEntradaFirma.fechaElectronica,
			cad_original : $('#contenidoFirmar').val(),
			tipo_operacion : 'firmaCMS',
			firma_archivo : false,
			min_archivos : 0,
			max_archivos : 0,
			afectado: [parent.AtributosPersonaCtrl.personaPortal],
			tipoAcuse: '1',
			acuse: 'CDCT'
		};
	*/
	parent.iniciarFirmaDigital(componenteFirma);
}

function firmarTramite(firmaResponse) {
	var url = '/portalDerechohabiente-web/wizard/baja/procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function finalizarTramite() {
	
		var url = '/portalDerechohabiente-web/wizard/baja/solicitud/finalizar';
		var tramiteBaja = $("form#formularioBajaDerechohabiente").toObject();
		
		dialogoConfirmar.dialog("option", "buttons", [ {
			text : 'ACEPTAR',
			click : function() {
				$(this).dialog('close');
				cerrarWizard();
			}
		}]);
		
		$.postJSON(url, tramiteBaja, function(data) {
			parent.WizardCapturaDocumentosProbatoriosCtrl.limpiarIndicadores();
			$('#mensajeDialogo').html(data.mensaje);
			dialogoConfirmar.dialog('open');
		}).error(function(data){
			$('#mensajeDialogo').html(data.mensaje);
			dialogoConfirmar.dialog('open');
		});
}

function guardarTramite() {
	
	
		var url = '/portalDerechohabiente-web/wizard/baja/guardar';
		var tramiteBaja = $("form#formularioBajaDerechohabiente").toObject();
		$.postJSON(url, tramiteBaja , function(data) {
			mostrarMensaje(data.mensaje);
		}).error(function(data){
			mostrarMensaje(data.mensaje);
		});
}

function mostrarMensajeError(mensaje) {
	$('#mensajeError').html(mensaje);
	dialogoError.dialog('open');
}

function mostrarMensajeDocumentosExixtentes() {
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'CONTINUAR',
		click : function() {
			var tipoTramite = $("#tipoTramite\\.idTipoTramite").val();
			var idTramite = $("#tramiteId").val();
			parent.WizardCapturaDocumentosProbatoriosCtrl.init("divCapturaDocs",tipoTramite,idTramite);
			parent.WizardCapturaDocumentosProbatoriosCtrl.abrir();
			$(this).dialog('close');
		}
	}, {
		text : 'CANCELAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	$('#mensajeDialogo').html("Se eliminaran los datos de los documentos que ya se han capturado");
	dialogoConfirmar.dialog('open');
}

function mostrarMensaje(mensaje) {
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	$('#mensajeDialogo').html(mensaje);
	dialogoConfirmar.dialog('open');
}

function cancelarTramite() {
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = '/portalDerechohabiente-web/wizard/baja/solicitud/cancelar';
	
	$.postJSON(url, {solicitudId : idSolicitudPendiente}, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	});
}

function cerrarWizard() {	
	parent.WizardBajaDerechohabienteCtrl.cerrar();
}
