var dialogoConfirmarCancelar;
var dialogoConfirmarCommon;
var nuevoDomicilio;
$(document).ready(function() {
		
	$('#vialidadPrimaria\\.clave').change(function(){
		$('#vialidadPrimaria\\.nombre\\.hidden').val($('#vialidadPrimaria\\.clave option:selected').text());
		$('#vialidadPrimaria\\.clave\\.hidden').val($('#vialidadPrimaria\\.clave option:selected').val());
	});
	
	$('#vialidadReferenciaPrimaria\\.clave').change(function(){
		$('#vialidadReferenciaPrimaria\\.clave\\.hidden').val($('#vialidadReferenciaPrimaria\\.clave option:selected').val());
	});
	
	$('#vialidadReferenciaSecundaria\\.clave').change(function(){
		$('#vialidadReferenciaSecundaria\\.clave\\.hidden').val($('#vialidadReferenciaSecundaria\\.clave option:selected').val());
	});
	
	$('#vialidadReferenciaPosterior\\.clave').change(function(){
		$('#vialidadReferenciaPosterior\\.clave\\.hidden').val($('#vialidadReferenciaPosterior\\.clave option:selected').val());
	});
	
	$('#finalizarTramite').click(function() {
		
		var huboError = validarCapturaDomicilio();
		
		$.unblockUI();
		
		if (!huboError) {
			var componenteFirma = {
				idTipoSolicitud : codigoTipoSolicitud,
				descripcionTipoSolicitud : descripcionTipoSolicitud,
				idTipoTramite : arrayCodigoTipoTramite,
				folioSolicitud : $('#folioSolicitud').val(),
				curp : datosEntradaFirma.curp,
				rfc : datosEntradaFirma.rfc,
				validarRFC : true,
				registroPatronal : datosEntradaFirma.registroPatronal,
				nombreCompleto : datosEntradaFirma.nombreCompleto,
				fechaElectronica : datosEntradaFirma.fechaElectronica,
				cad_original : $('#contenidoFirmar').val(),
				tipo_operacion : 'firmaCMS',
				firma_archivo : false,
				min_archivos : 0,
				max_archivos : 0,
				domicilioActualizado: nuevoDomicilio,
				afectado: construirAfectado(),
				tipoAcuse: '1',
				acuse: 'CDP'
			};

			parent.iniciarFirmaDigital(componenteFirma);
		}
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
	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				cancelarTramite();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });
	
	dialogoConfirmarCommon = $( "#dialog-confirm-common" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false
	 });
	
	/* 
	 * Al cerrar el diálogo de la firma digital,
	 * se ejecuta la función para finalizar el trámite
	 */
	parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
		
		dialogoConfirmarCommon.dialog("option", "buttons", [ {
			text : 'ACEPTAR',
			click : function() {
				$(this).dialog('close');
			}
		}]);
		
		if(parent.FirmaDigitalCtrl.datosSalida == null) {
			$('#mensajeDialogo').text("La validaci\u00f3n de la firma no pudo ser realizada");
			dialogoConfirmarCommon.dialog('open');
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
				$('#mensajeDialogo').text("La validaci\u00f3n de la firma no pudo ser realizada");
				dialogoConfirmarCommon.dialog('open');
			}
		}
	});
});

function construirAfectado() {
	var personaAfectada = new Object();
	personaAfectada.nombreRazonSocial = datosEntradaFirma.nombreCompleto;
	personaAfectada.rfc = datosEntradaFirma.rfc;
	personaAfectada.curp = datosEntradaFirma.curp;
	
	return [personaAfectada];
}

function firmarTramite(firmaResponse) {
	var url = '/${mvn.web.app.root}'+'/wizard/tramite/modificar/domicilio/particular/procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function finalizarTramite() {
	
	$.blockUI();
	
	var url = '/${mvn.web.app.root}'+'/wizard/tramite/modificar/domicilio/particular/finalizar/solicitud/';
	
	dialogoConfirmarCommon.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	guardarFinalizarTramiteCommon(url, true);
}

function guardarTramite() {
	
	var huboError = validarCapturaDomicilio();
	
	if(!huboError) {
		var url = '/${mvn.web.app.root}'+'/wizard/tramite/modificar/domicilio/particular/guardar/solicitud/';
		
		dialogoConfirmarCommon.dialog("option", "buttons", [ {
			text : 'ACEPTAR',
			click : function() {
				$(this).dialog('close');
			}
		}]);
		
		guardarFinalizarTramiteCommon(url, false);
	} else {
		$.unblockUI();
	}
}

function guardarFinalizarTramiteCommon(url, isFinalizar) {
	
	var idSolicitudPendiente = $('#idSolicitud').val();
	var folioSolicitudPendiente = $('#folioSolicitud').val();
	url += idSolicitudPendiente;
	
	var oDomForm = $("form#formComplemento").toObject();
	var oForm = $("form#mdmPersonaFisicaForm").toObject();
	
	delete oDomForm.municipio;
	delete oDomForm.entidadFederativa;
	
	pasarAtributosDisabled(oDomForm);
	
	oForm.personaFisica.domicilios = new Array();
	oForm.personaFisica.domicilios[0] = oDomForm;
	
	$.postJSON(url, oForm, function(resultado) {
		
		$.unblockUI();
		
		if (isFinalizar) {
			parent.ProcesandoSolicitudCtrl.abrir(folioSolicitudPendiente);
			cerrarWizard();
		} else {
			$('#mensajeDialogo').text(resultado.mensaje);
			dialogoConfirmarCommon.dialog('open');
		}
	}).error(function(resultado){
		fnProcesarErrores(resultado, "form#formComplemento");
	});
}

function cancelarTramite() {
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = '/${mvn.web.app.root}'+'/wizard/tramite/modificar/domicilio/particular/cancelar/solicitud/'
				+ idSolicitudPendiente;
	
	dialogoConfirmarCommon.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	$.postJSON(url, null, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmarCommon.dialog('open');
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmarCommon.dialog('open');
	});
}

function cerrarWizard() {	
	parent.WizardModificacionDomicilioParticularCtrl.cerrar();
}

function validarCapturaDomicilio () {
	
	$.blockUI();
	
	fnHideErrores("form#formComplemento");
	
	var url = '/${mvn.web.app.root}'+'/wizard/tramite/modificar/domicilio/particular/validar';
	var huboError = null;
	
	var oDomForm = $("form#formComplemento").toObject();
	var oForm = $("form#mdmPersonaFisicaForm").toObject();
	
	delete oDomForm.municipio;
	delete oDomForm.entidadFederativa;
	
	pasarAtributosDisabled(oDomForm);
	
	oForm.personaFisica.domicilios = new Array();
	oForm.personaFisica.domicilios[0] = oDomForm;
	nuevoDomicilio = oDomForm;
	
	$.ajax({
		url: url,
        type: "POST",
        data: JSON.stringify(oForm),
        dataType: "json",
        async: false,
        contentType: "application/json; charset=utf-8",
        success:  function(data) {
        	huboError = false;
		}
	}).error(function(resultado){
		fnProcesarErrores(resultado, "form#formComplemento");
		huboError = true;
	});
		
	return huboError;
}