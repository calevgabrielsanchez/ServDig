/*
 * Seccion de codigo a ejecutar en cuanto el DOM envie la señar de que esta
 * listo para procesar de modificaciones al DOM
 */
$(document).ready(
		function() {
			
			var forma = $("form#forma");
			var medioContactoFormWrapper = new Object();
			medioContactoFormWrapper.twitter;
			
			$(forma).submit(function(){
				var oForm = $(this).toObject();
				var sSource = $(this).attr("action");
				
				
				
				
				$.postJSON(sSource, oForm, function(data) {
					
				}).error(function(data) {
					fnProcesarErrores(data, "form#forma");
				});
				
				
				return false;
			});
			
			
			var url = context_path + "/medios/contacto/get/25128640"
			$.getJSON(url, {}, function(data) {
				
			}).error(function(data) {
				fnProcesarErrores(data, "form#forma");
			});
			
		});