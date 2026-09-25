/**
 * 
 */

$(document).ready(function() {
	$("#imprimirARP").on('click',imprimirArpAltaPatronal);
	$("#descargarARP").on('click',descargarArpAltaPatronal);
	$("#imprimirTIP").on('click',imprimirTipAltaPatronal);
	$("#descargarTIP").on('click',descargarTipAltaPatronal);
	$("#idBtnFinalizarTramite").on('click', function() {
		$.blockUI();
		window.close();
	})
});

var imprimirArpAltaPatronal = function() {
	imprimeReporteAltaPatronal(56,false);
}

var imprimirTipAltaPatronal = function() {
	imprimeReporteAltaPatronal(55,false);
}

var descargarArpAltaPatronal = function() {
	imprimeReporteAltaPatronal(56,true);
}

var descargarTipAltaPatronal = function() {
	imprimeReporteAltaPatronal(55,true);
}

var imprimeReporteAltaPatronal =function(tipoDoc, descargar) {
	var url = "/alta/" + (descargar ? "descargaDocumentoResultante" : "mostrarDocumentoResultante");
	$("#tipoDocumento").val(tipoDoc);
	$("#formImpresionReporte").attr("action",context_path + url);
	$("#formImpresionReporte").submit();
	$.unblockUI();
}