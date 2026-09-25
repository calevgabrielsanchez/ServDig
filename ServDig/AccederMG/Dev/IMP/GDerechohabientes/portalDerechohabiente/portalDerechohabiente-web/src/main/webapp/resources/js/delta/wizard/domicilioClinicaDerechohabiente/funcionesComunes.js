/**
 * 
 */


var dialogoConfirmarCancelar;
var dialogoConfirmar;
var dialogoError;
CONTEXT_PATH_APLICACION = '/${mvn.web.app.root}';

$(document).ready(
	function() {
		
		$("#regresar").click(function() {
			$.blockUI();
			$("#formRegistro").habilitarContenido(false);
			$("#formRegistro").attr("action",CONTEXT_PATH_APLICACION + "/wizard/domicilio/regresar");
			$("#formRegistro").submit();
		});
		
		$("#finalizarTramite").click(function() {
			//Dependiendo el tipo de tramite
			var idTipoTramite = $("#tipoTramite\\.idTipoTramite").val();
			if( idTipoTramite == 6 ){
//				finalizarActualizacionDomicilio();
				var requiereDocs = $("#requiereDocs").val() == 1;
				
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
		});
		
		$('#guardarCerrarTramite').click(function() {
			guardarTramite(true);
		});
		
		$('#guardarTramite').click(function() {
			guardarTramite(false);
		});

		$('#cancelarTramite').click(function() {
			dialogoConfirmarCancelar.dialog( "open" );
		});

		$('#cerrarWizard').click(function() {
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
					$( this ).dialog( "close" );
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
	}
);

function finalizarActualizacionDomicilio(){
	$.blockUI();
	fnHideErrores("form#formRegistro");
	var idSolicitud = $("#idSolicitud").val();
	var url = CONTEXT_PATH_APLICACION + '/wizard/domicilio/actualizar/'+idSolicitud;
	alert(url);
	habilitarDesabilitarCamposDatosBasicos(true,true);
	var tramiteRegistro = $("form#formRegistro").toObject();
	$.postJSON(url, tramiteRegistro , function(data) {
		habilitarDesabilitarCamposDatosBasicos(false,false);
		if(data.error != undefined && data.error != null && data.error != "") {
			mostrarMensajeError(data.error);
		} else {
			mostrarMensaje(data.mensaje,true);
		}
		$.unblockUI();
	}).error(function(data){
		habilitarDesabilitarCamposDatosBasicos(false,false);
		$.unblockUI()();
		mostrarMensajeError(data.mensaje);
	});
}

function guardarTramite(cerrar) {

	$.blockUI();
	fnHideErrores("form#formRegistro");
	var url = CONTEXT_PATH_APLICACION + '/wizard/domicilio/guardar';
	habilitarDesabilitarCamposDatosBasicos(true,true);
	var tramiteRegistro = $("form#formRegistro").toObject();
	$.postJSON(url, tramiteRegistro , function(data) {
		habilitarDesabilitarCamposDatosBasicos(false,false);
		if(data.error != undefined && data.error != null && data.error != "") {
			mostrarMensajeError(data.error);
		} else {
			mostrarMensaje(data.mensaje,cerrar);
		}
		$.unblockUI();
	}).error(function(data){
		habilitarDesabilitarCamposDatosBasicos(false,false);
		$.unblockUI()();
		mostrarMensajeError(data.mensaje);
	});
}


function mostrarMensajeError(mensaje) {
	$('#mensajeError').html(mensaje);
	dialogoError.dialog('open');
}

function mostrarMensaje(mensaje, cerrar) {
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			if(cerrar) {
				cerrarWizard();
			}
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
	var url = CONTEXT_PATH_APLICACION + '/wizard/registro/solicitud/cancelar';
	$.blockUI();
	$.postJSON(url, {solicitudId : idSolicitudPendiente}, function(data) {
		$.unblockUI();
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function calcularEdad(fechaNac) {
	
	var fecha = new Date(fechaNac);
	//console.debug("fecha de nacimiento al aplicarle el parser ISO8601: %s", fecha);
	var hoy = new Date();
	//console.debug("fecha de nacimiento del integrante: %s, fecha de hoy: %s",fechaNac,hoy);
	var ed = datediff(hoy, fecha);
	//console.debug("diferencias entre edad %s" , ed[0]);
	return ed[0];
}

function datediff(date1, date2) {

	var y1 = date1.getFullYear(), m1 = date1.getMonth(), d1 = date1.getDate(),
	y2 = date2.getFullYear(), m2 = date2.getMonth(), d2 = date2.getDate();

	//console.debug("Fechas 1: %s,%s,%d,%s,%s,%s",y1,m1,d1,y2,m2,d2);

	if (d1 < d2) {
		m1--;
		d1 += DaysInMonth(y2, m2);
	}
	if (m1 < m2) {
		y1--;
		m1 += 12;
	}

	return [y1 - y2, m1 - m2, d1 - d2];
}


function DaysInMonth(Y, M) {
	with (new Date(Y, M, 1, 12)) {
		setDate(0);
		return getDate();
	}
}
	
function cerrarWizard() {	
	parent.WizardCapturaDocumentosProbatoriosCtrl.limpiarIndicadores();
	parent.WizardDomicilioClinicaDerechohabienteCtrl.cerrar();

}

function invocarFirmaDigital() {
	
	var requiereDocs = $("#requiereDocs").val() == 1;
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
	parent.iniciarFirmaDigital(componenteFirma);
}

function firmarTramite(firmaResponse) {
	var url = CONTEXT_PATH_APLICACION + '/wizard/domicilio/procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		finalizarActualizacionDomicilio();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}