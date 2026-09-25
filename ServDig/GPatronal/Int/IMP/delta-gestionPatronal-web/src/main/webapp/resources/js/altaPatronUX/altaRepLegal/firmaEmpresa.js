/**
 * Script de control para el logue de alta patronal
 */
DIV_COMPONENTE_FIRMA = "firmaElectronicaEmpresa";
DIV_COMPONENTE_DOCTOS = "contenedorDoctosEmpresa";
DIV_COMPONENTE_FIRMA_AUX = "firmaElectronicaRepresentante";
DIV_COMPONENTE_DOCTOS_AUX = "contenedorDoctosRepresentante";

$(document).ready(function() {
	$('#leyenda2').hide();
	$('#divTerminosCondiciones').hide();
	dialogoConfirmar = $( "#dialog-confirm-RepLegal" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		dialogClass: "no-close",
	    closeOnEscape: false,
		autoOpen: false
	 });
	
	$('#checkTermCond').change(function() {
        if($(this).is(":checked")) {
        	$('#btnContinuarTermCond').show();
        }else{
        	$('#btnContinuarTermCond').hide();
        }
    });

	
})

function inicializaFirmaEmpresa(){
	FirmaDigitalCtrl.init(DIV_COMPONENTE_FIRMA,DIV_COMPONENTE_DOCTOS);
	FirmaDigitalCtrl.setOnCloseCallback(procesarFirmaEmpresa);
	var componenteFirma = iniciarFirmaDigital(estructuraFirmaEmpresa);
};

function inicilizaFirmaRep(){
	FirmaDigitalCtrl.init(DIV_COMPONENTE_FIRMA_AUX,DIV_COMPONENTE_DOCTOS_AUX);
	FirmaDigitalCtrl.setOnCloseCallback(procesarRepresentante);
	var componenteFirma = iniciarFirmaDigital(estructuraFirmaRepresentante);
};

var procesarFirmaEmpresa = function () {
	if(FirmaDigitalCtrl.datosSalida == null) {
		mostrarMensajeEmp("La validaci&oacute;n de la autenticaci&oacute;n no pudo ser realizada");
	}else {
		if(FirmaDigitalCtrl.datosSalida.Resultado == 0) {
			
			var firmaResponse = {
				cadenaOriginal               : FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
				recibo                       : FirmaDigitalCtrl.datosSalida.firmas[0],
				reciboNotarial               : FirmaDigitalCtrl.datosSalida.folio,
				urlAcuseFirma                : FirmaDigitalCtrl.datosSalida.acuse,
				serialCertificado            : FirmaDigitalCtrl.datosSalida.serie_cert,
				strIniciaVigenciaCertificado : FirmaDigitalCtrl.datosSalida.vigIni,
				strFinVigenciaCertificado    : FirmaDigitalCtrl.datosSalida.vigFin
			};
			solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.moral = null;
			solicitudPrincipal.tramiteRepresentanteLegal.sujetoObligado.moral = null;
			solicitudPrincipal.firmaEmpresa = firmaResponse;
			firmaEmpresa();
			
		} else {
			mostrarMensajeEmp("La validaci&oacute;n de la autenticaci&oacute;n no pudo ser realizada");
		}
	}
}

function firmaEmpresa() {
	$.postJSON(getContext()+ '/alta/crear/solicitud.do', solicitudPrincipal, function(data) {
		if(data.solicitud.errorFormGeneral == null){
			solicitudPrincipal.solicitud = data.solicitud;
			solicitudPrincipal.tramite = data.tramite;
			solicitudPrincipal.sujetoObligado = data.sujetoObligado;
			$('#leyenda1').hide();
			$('#firmaElectronicaEmpresa').hide();
			$('#btnContinuarTermCond').hide();
			$('#divTerminosCondiciones').show();
            $(window).scrollTop(0);
		} else {
			mostrarMensajeEmp(data.solicitud.errorFormGeneral);
		}
	}).error(function(data){
		fnProcesarErrores(data, "form#busquedaPersona");
	});
}

var procesarRepresentante = function () {
	if(FirmaDigitalCtrl.datosSalida == null) {
		mostrarMensajeEmp("La validaci&oacute;n de la autenticaci&oacute;n no pudo ser realizada");
	}else {
		if(FirmaDigitalCtrl.datosSalida.Resultado == 0) {
			
			var firmaResponse = {
				cadenaOriginal               : FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
				recibo                       : FirmaDigitalCtrl.datosSalida.firmas[0],
				reciboNotarial               : FirmaDigitalCtrl.datosSalida.folio,
				urlAcuseFirma                : FirmaDigitalCtrl.datosSalida.acuse,
				serialCertificado            : FirmaDigitalCtrl.datosSalida.serie_cert,
				strIniciaVigenciaCertificado : FirmaDigitalCtrl.datosSalida.vigIni,
				strFinVigenciaCertificado    : FirmaDigitalCtrl.datosSalida.vigFin
			};
			solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.moral = null;
			solicitudPrincipal.tramiteRepresentanteLegal.sujetoObligado.moral = null;
			solicitudPrincipal.sujetoObligado.moral = null;
			solicitudPrincipal.firmaRepresentante = firmaResponse;
			firmaRepresentante();
		} else {
			mostrarMensajeEmp("La validaci&oacute;n de la autenticaci&oacute;n no pudo ser realizada");
			paginaSiguiente();
		}
	}
}

function firmaRepresentante() {
	$.postJSON(getContext()+ '/alta/finalizar/solicitud/registroRepresentado.do', solicitudPrincipal, function(data) {
		if(!data.error){
			$("#firmaElectronicaEmpresa").remove();
			paginaSiguiente();
		} else {
			mostrarMensajeEmp(data.mensaje);
		}
	}).error(function(data){
		fnProcesarErrores(data, "form#busquedaPersona");
	});
}


function mostrarMensajeEmp(mensaje) {
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	$('#mensajeDialogoRepLegal').html(mensaje);
	dialogoConfirmar.dialog('open');
}

function sigFirmaRep(){
	$('#divTerminosCondiciones').hide();
	$('#leyenda2').show();
	inicilizaFirmaRep();
	$('#firmaElectronicaEmpresa').show();
	$(window).scrollTop(0);
}

function muestraCarta(){
	$("#formTerminos").attr("action", getContext()+ '/alta/muestraCarta.do');
	$('#formTerminos').submit();
	$.unblockUI();
}