var finalizacionCtrl = {
	contextApp: '/${mvn.web.app.root}',
	init: function() {
		$("#finalizar").on("click", finalizacionCtrl.salir);
		$("#imprimirReporte").on("click", finalizacionCtrl.imprimirReporte);
	},
	imprimirReporte: function() {
		$("#formImprimirReporte").submit();
	},
	salir: function() {
		$.blockUI();
		$.postJSON(finalizacionCtrl.contextApp + "/escrito/wizard/limpiarSession", null, function(data) {
			var $formImprimir = $("#formImprimirReporte");
			$formImprimir.removeClass("formNotBlock");
			$formImprimir.attr("action",finalizacionCtrl.contextApp + "/escrito");
			$formImprimir.attr("target","")
			$formImprimir.submit();
		}).error(function(){ 
		});
	}
}

$(document).ready(finalizacionCtrl.init);