/**
 * 
 */
;(function($, window) {
	
	$.fn.boveda = function(options) {
		
		if (typeof bovedaCtrl !== 'undefined' && bovedaCtrl != null && $.type(options) != 'string' ) {
			alert("Ya existe un componente de boveda en pantalla");
			return;
		}
		
		if($.type(options) == 'string') {
			if(options == "valid") {
				return bovedaCtrl.validar();
			} else if(options == "optional") {
				var idDocumento = arguments[1], opcional = arguments[2];
				if(typeof bovedaCtrl !== "undefined" && bovedaCtrl != null) {
					bovedaCtrl.marcarComoOpcional(idDocumento, opcional);
				}
				return;
			} else {
				alert("No se especifico ninguna operacion");
				return;
			}
		}
		
		return this.each(function() {
			
			var idContenedor = this.id, iniciales = $.extend({}, $.fn.boveda.defaults, options);
			
			$.ajax({
				type: 'POST',
			    beforeSend : $.blockUI,
			    contentType: 'application/json',
			    dataType: 'html',
				data: JSON.stringify(iniciales),
				url: "/gestionDocumentoProbatorio-web/boveda/",
				success: function(data){
					$("#"+idContenedor).html(data);
					$.unblockUI();
				}
			})
		})
	};
	
	$.fn.boveda.defaults = {
		tipoComponente: 1, //captura y consulta, mandar 2 para solo consulta
		idTramite: null, //el id de tramite en BD para relacionar el documento probatorio
		tipoTramite: null, //el tipo de tramite para obtener el listado de documentos probatorios
		folio: null,//el folio de la solicitud
		rutaBoveda: "/sisec",
		tipoDocumentos: "pdf",
		tipoDocumental: "D:sisec:imss"
	};
	
}(jQuery, window));