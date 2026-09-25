$(document).ready(function() {

	$("#buscar").click(function() {

		//Limpiamos los errores
		fnHideErrores("form#forma");
		
		var url = '/gestionIndividuo-consulta-web/persona/fisica/ubicar/obtener-objeto-respuesta';
		
		$.postJSON(url, null, function(data) {
			var datosRespuesta = data;
			
			var ctrl = parent.PersonaFisicaCtrl;
			if (ctrl != null) {
				ctrl.setPersona(datosRespuesta);
				ctrl.dialogo.dialog('close');
			} else {
				limpiarObjetoRespuesta();
			}
		}).error(function(data){
			fnProcesarErrores(data, "form#forma");
		});
		
	});

});

function limpiarObjetoRespuesta() {
	
	var url = '/gestionIndividuo-consulta-web/persona/fisica/ubicar/limpiar-objeto-respuesta';
	
	$.post(url);
}