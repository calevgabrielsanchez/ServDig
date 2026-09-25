$(document).ready(function() {
	
	$("#idBtnNoDeAcuerdo").click(function(event) {
		paginaPrevia();
	});
	
	$("#idBtnSiDeAcuerdo").click(function(event) {
		fimarTramiteAltaPatronal();
	});
	
});

function visualizaPrevio(){
	solicitudPrincipal.tramiteRepresentanteLegal.sujetoObligado.moral = null;
	$.postJSON(getContext()+ '/alta/visualizacionPrevia.do', solicitudPrincipal, function(data) {		
		var urlPDF = getContext() + "/servlet/EnviaArchivoServlet";	
		$('#archivoFrame').attr('src', urlPDF)
	}).error(function(data){
//		console.log("error");	
	});	
}


function fimarTramiteAltaPatronal(){
	
	var urlFirma = context_path + "/alta/getFirmaAP";
	FirmaDigitalCtrl.init("firmaElectronicaAP",DIV_COMPONENTE_DOCTOS_AUX);
	FirmaDigitalCtrl.setOnCloseCallback(procesarFirmadoAltaPatronal);
	
	$.postJSON(urlFirma, {} , function(data) {
		//invocamos el componente de firma digital
		iniciarFirmaDigital(data);
		//nos pasamos a la pantalla donde se muestra la firma
		paginaSiguiente();
	});
};

var procesarFirmadoAltaPatronal = function() {
	var buttons = [ {
		text : 'Aceptar',
		click : function() {
			$(this).dialog('close');
		}
	}];

	if(FirmaDigitalCtrl.datosSalida == null) {
		crearDialogo("La validaci\u00f3n de la firma no pudo ser realizada", buttons, "Error")
	} else {
		if(FirmaDigitalCtrl.datosSalida.Resultado == 0) {
			var firmaResponse = {
					cadenaOriginal               : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
					recibo                       : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cms,
					reciboNotarial               : parent.FirmaDigitalCtrl.datosSalida.folio,
					urlAcuseFirma                : parent.FirmaDigitalCtrl.datosSalida.acuse,
					serialCertificado            : parent.FirmaDigitalCtrl.datosSalida.serie_cert,
					strIniciaVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigIni,
					strFinVigenciaCertificado    : parent.FirmaDigitalCtrl.datosSalida.vigFin
			};

			finalizarTramiteAltaPatronal(firmaResponse);
		} else {
			crearDialogo("La validaci\u00f3n de la firma no pudo ser realizada", buttons, "Error")
		}
	}
}

function finalizarTramiteAltaPatronal(datosFirma) {
	var url = context_path + '/alta/procesarDatosFirmaAltaPatronal';
	$("#firmaElectronicaAP").remove();
	$.postJSON(url,datosFirma, function() {
		finalizarSolicitud();
	});
}