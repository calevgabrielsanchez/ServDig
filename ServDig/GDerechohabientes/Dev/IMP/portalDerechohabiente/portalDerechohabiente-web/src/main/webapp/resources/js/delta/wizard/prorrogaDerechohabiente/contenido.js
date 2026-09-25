var dialogoConfirmarCancelar;
var dialogoConfirmar;
var dialogoError;
var idOrigenSolicitud = '${mvn.web.app.origin.id}';
var urlPrincipalWizard = '/${mvn.web.app.root}/wizard/prorroga';

$(document).ready(function() {
	
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	//Se agrega el limite al campo de observaciones de la prorroga
	if( $("#observaciones").length > 0 ){
		var offsetLeft = $("#observaciones").position().left - parseInt($("#observaciones").css("padding-left"));
		asignartextAreaLimites("observaciones",{styles: {float:"right"}});
	}
	
	
	if($("#tipoTramite\\.idTipoTramite").val() != 29){
		$("#_fechaInicioProrroga").datepicker({
			showOn: "both",
			yearRange : '-112:+0',
			maxDate: new Date()
		});
		
		$("#_fechaFinProrroga").change(function(){
			$("#tramite\\.fechaFinProrroga").val($("#_fechaFinProrroga").val());
		});
		
		
		$("#_fechaInicioProrroga").change(function(){
			$("#tramite\\.fechaInicioProrroga").val($("#_fechaInicioProrroga").val());
		});
	
		$("#_fechaFinProrroga").datepicker({
			showOn: "both",
			minDate: new Date()
		});
	} else {
		$("#inicioP").hide();
	}
	
	/*
	 * se iniciliza el componente del acordeon con la opcion 'autoHeight: false'
	 * para que cada DIV colapsable tenga la altura de acuerdo a su contenido
	 */
	$('#acordeon').accordion({
		autoHeight : false,
		collapsible : true
	});
	
	$('#capturarDocumentosProrroga').click(function() {
		var tipoTramite = $("#tipoTramite\\.idTipoTramite").val();
		var idTramite = $("#tramiteId").val();
		var idUmf = $("#idUmf").val();
		var urlCosntancia = urlPrincipalWizard+'/getConstanciaEstudios';
		
		var otros_parametros = {idUmf:idUmf};
		
		if(!parent.WizardCapturaDocumentosProbatoriosCtrl.isPrimeraCaptura()) {
			mostrarMensajeDocumentosExixtentes();
		} else {
			parent.WizardCapturaDocumentosProbatoriosCtrl.setOnCloseCallback(function() {
				if($("#tipoTramite\\.idTipoTramite").val() == 29 && parent.WizardCapturaDocumentosProbatoriosCtrl.isCapturaFinalizada()){
					$.postJSON(urlCosntancia,null, function(data) {
						if(data != null ) {
							$("#_fechaInicioProrroga").val(data.fechaInicioPeriodo);
							$("#tramite\\.fechaInicioProrroga").val(data.fechaInicioPeriodo);
							$("#_fechaFinProrroga").val(data.fechaFinPeriodo);
							$("#tramite\\.fechaFinProrroga").val(data.fechaFinPeriodo);
						}
					});
				}
			});
			parent.WizardCapturaDocumentosProbatoriosCtrl.init("divCapturaDocs",tipoTramite,idTramite, otros_parametros);
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
        if(idOrigenSolicitud == 6){
            cancelarTramite();
        }else{
            cerrarWizard();
        }
	});
	
	inicializarMensajes();
});

function validarInfo() {
	var url = urlPrincipalWizard+'/validaciones';
	var tramite = $("form#formularioProrrogaDerechohabiente").toObject();
	fnHideErrores("form#formularioProrrogaDerechohabiente");
		
	$.postJSON(url, tramite, function(data2) {
		validarDocumentos();
	}).error(function(data){
		fnProcesarErrores(data, "form#formularioProrrogaDerechohabiente");
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
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			finalizarTramite();
		}
	}, {
		text: 'CANCELAR',
		click : function() {
			$(this).dialog('close');
		}
	}    
	];
	
	var curp = $("#detalleCurp").val() != "" ? $("#detalleCurp").val() : "SIN CURP";
	dialogoConfirmar.dialog("option", "buttons",buttons);	
	$('#mensajeDialogo').html('Se dar&aacute; prorroga al derechohabiente con CURP <strong>'+curp+'</strong> . &iquest; Est&aacute; seguro que desea continuar?');
	dialogoConfirmar.dialog('open');
}

function invocarFirmaDigital() {
	
	var existeComponenteFirma = parent.FirmaDigitalCtrl != undefined;
	var requiereDocs = $("#requiereDocs").val() == 1;
	var numeroArchivos = 0;
	var requiereDoctos = false;
	
	/*
	if(requiereDocs) {
		if(parent.WizardCapturaDocumentosProbatoriosCtrl.getNumeroDocumentosRequeridos() > 0) {
			requiereDoctos = true;
			numeroArchivos = parent.WizardCapturaDocumentosProbatoriosCtrl.getNumeroDocumentosRequeridos();
		}
	}
	*/
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
}

function firmarTramite(firmaResponse) {
	var url = urlPrincipalWizard+'/procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function finalizarTramite() {
		
		var url = urlPrincipalWizard+'/solicitud/finalizar';
		var tramite = $("form#formularioProrrogaDerechohabiente").toObject();
		
		dialogoConfirmar.dialog("option", "buttons", [ {
			text : 'ACEPTAR',
			click : function() {
				$(this).dialog('close');
				cerrarWizard();
			}
		}]);
		
		$.postJSON(url, tramite, function(data) {
			parent.WizardCapturaDocumentosProbatoriosCtrl.limpiarIndicadores();
			$('#mensajeDialogo').text(data.mensaje);
			dialogoConfirmar.dialog('open');
			//Esto es para la parte de integrar con el OSB
//			var folioSolicitud = $('#folioSolicitud').val();
//			var idSolicitud = $('#idSolicitud').val();
//			parent.ProcesandoSolicitudCtrl.abrir(folioSolicitud, idSolicitud, codigoTipoSolicitud);
//			cerrarWizard();
		}).error(function(data){
			$('#mensajeDialogo').text(data.mensaje);
			dialogoConfirmar.dialog('open');
		});
}

function guardarTramite() {
		var url = urlPrincipalWizard+'/guardar';
		var tramite = $("form#formularioProrrogaDerechohabiente").toObject();
		$.postJSON(url, tramite , function(data) {
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
	var url = urlPrincipalWizard+'/solicitud/cancelar';

	$.postJSON(url, {solicitudId : idSolicitudPendiente}, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	});
}

function cerrarWizard() {	
	parent.WizardProrrogaDerechohabienteCtrl.cerrar();
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
				"ACEPTAR": function() {
					cancelarTramite();
			 	},
			 	"CANCELAR": function() {
			 		$( this ).dialog( "close" );
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
			 	"ACEPTAR": function() {
			 		$( this ).dialog( "close" );
			 	}
			 }
		 });
	}
}