var dialogoConfirmarCancelar;
var dialogoConfirmar;
var dialogoError;
var idOrigenSolicitud = '${mvn.web.app.origin.id}';
var urlPrincipalWizard = '/${mvn.web.app.root}/wizard/baja';

$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	//si existe el campo de fecha se le asocia el datepicker
	if($("#fechaDefuncion").length > 0) {
		$("#fechaDefuncion").datepicker({
			showOn: "both",
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate: new Date(), 
			yearRange : '-112:+0'
		});
	}
	
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

	if($('#guardarTramite').length > 0) {
		$('#guardarTramite').click(function() {
			guardarTramite();
		});
	}

	$('#cancelarTramite').click(function() {
		dialogoConfirmarCancelar.dialog( "open" );
	});

	$('#cerrarWizard').click(function() {
		parent.WizardCapturaDocumentosProbatoriosCtrl.limpiarIndicadores();
		cerrarWizard();
	});
	
	inicializarMensajes();

	setEventosChecarError("formularioBajaDerechohabiente",verificarErrores);
});

function validarInfo() {
	var oForm = $("form#formularioBajaDerechohabiente").toObject();
	var url = urlPrincipalWizard + '/validaciones';
	fnHideErrores("form#formularioBajaDerechohabiente");
		
	$.postJSON(url, oForm, function(data2) {
		validarDocumentos();
	}).error(function(data){
		fnProcesarErrores(data, "form#formularioBajaDerechohabiente");
		verificarErrores()
	});
}

function validarDocumentos() {
	var requiereDocs = $("#requiereDocs").val() == 1;
	var tipoTramite = $("#tipoTramite\\.idTipoTramite").val();
	
	if(requiereDocs) {
		if(parent.WizardCapturaDocumentosProbatoriosCtrl.isCapturaFinalizada()) {
			verificarOrigenYFinalizar();
		} else {
			mostrarMensajeError("Debe completar la documentaci&oacute;n para finalizar el tr&aacute;mite");
		}
	} else {
		verificarOrigenYFinalizar();
	}
	
	
}

function verificarOrigenYFinalizar() {
	if(idOrigenSolicitud == 2) {
		invocarFirmaDigital();
	} else if(idOrigenSolicitud == 6){
		mostrarMensajeConfirmacionBaja();
	}
}

function mostrarMensajeConfirmacionBaja() {
	var buttons = [{
		text: 'Cancelar',
		click : function() {
			$(this).dialog('close');
		}
	},{
		text : 'Aceptar',
		click : function() {
			$(this).dialog('close');
			finalizarTramite();
		}
	}    
	];
	
	var curp = $("#detalleCurp").val() != "" ? $("#detalleCurp").val() : "SIN CURP";
	dialogoConfirmar.dialog("option", "buttons",buttons);	
	$('#mensajeDialogo').html('Se dar&aacute; de baja al derechohabiente con CURP <strong>'+curp+'</strong> . &iquest; Est&aacute; seguro que desea continuar?');
	dialogoConfirmar.dialog('open');
}


function invocarFirmaDigital() {
	
	var existeComponenteFirma = parent.FirmaDigitalCtrl != undefined;
	var numeroArchivos = 0;
	var requiereDoctos = false;
	
	if(idOrigenSolicitud == 2 && existeComponenteFirma) {
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
		
		parent.iniciarFirmaDigital(componenteFirma);
	} 
}

function firmarTramite(firmaResponse) {
	var url = urlPrincipalWizard + '/procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function finalizarTramite() {
	
		var url = urlPrincipalWizard +'/solicitud/finalizar';
		
		var tramiteBaja = $("form#formularioBajaDerechohabiente").toObject();
		
		dialogoConfirmar.dialog("option", "buttons", [ {
			text : 'Aceptar',
			click : function() {
				$(this).dialog('close');
				cerrarWizard();
			}
		}]);
		
		$.postJSON(url, tramiteBaja, function(data) {
			parent.WizardCapturaDocumentosProbatoriosCtrl.limpiarIndicadores();
			var homoclave = $("#homoclaveTramite").val();
			$('#mensajeDialogo').html(data.mensaje);
			dialogoConfirmar.dialog('open');
			console.log("La homoclave del tramite es: " + homoclave);
			if($.trim(homoclave).length != 0 && parent.startEncuestaHC) {
				parent.startEncuestaHC(500,homoclave);
			}
		}).error(function(data){
			$('#mensajeDialogo').html(data.mensaje);
			dialogoConfirmar.dialog('open');
		});
}

function guardarTramite() {
	
	
		var url = urlPrincipalWizard + '/guardar';
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
		text : 'Cancelar',
		click : function() {
			$(this).dialog('close');
		}
	},{
		text : 'Continuar',
		click : function() {
			var tipoTramite = $("#tipoTramite\\.idTipoTramite").val();
			var idTramite = $("#tramiteId").val();
			parent.WizardCapturaDocumentosProbatoriosCtrl.init("divCapturaDocs",tipoTramite,idTramite);
			parent.WizardCapturaDocumentosProbatoriosCtrl.abrir();
			$(this).dialog('close');
		}
	}]);
	
	$('#mensajeDialogo').html("Se eliminaran los datos de los documentos que ya se han capturado");
	dialogoConfirmar.dialog('open');
}

function mostrarMensaje(mensaje) {
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'Aceptar',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	$('#mensajeDialogo').html(mensaje);
	dialogoConfirmar.dialog('open');
}

function cancelarTramite() {
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'Aceptar',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = urlPrincipalWizard +'/solicitud/cancelar';
	
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

function inicializarMensajes() {
	
	if($("#dialog-confirm-cancelar").length > 0) {
		dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false,
			dialogClass: "no-close",
		    closeOnEscape: false,
			buttons: {
				"Cancelar": function() {
			 		$( this ).dialog( "close" );
			 	},
				"Aceptar": function() {
					cancelarTramite();
			 	}
			 }
		 });
	}
	
	if($("#dialog-confirm").length > 0) {
		dialogoConfirmar = $( "#dialog-confirm" ).dialog({
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false,
			dialogClass: "no-close",
		    closeOnEscape: false
		 });
	}
	
	if($("#dialog-error").length > 0) {
		dialogoError = $( "#dialog-error" ).dialog({
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false,
			dialogClass: "no-close",
		    closeOnEscape: false,
		    buttons: {
			 	"Aceptar": function() {
			 		$( this ).dialog( "close" );
			 	}
			 }
		 });
	}
}

var verificarErrores = function() {
	//buscamos los erroresen el formulario de registro
	marcarCamposConErrores("formularioBajaDerechohabiente",".error","td");
}
