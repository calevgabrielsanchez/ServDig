/*
 * Seccion de codigo a ejecutar en cuanto el DOM envie la señar de que esta
 * listo para procesar de modificaciones al DOM
 */
$(document).ready(
		function() {
			
			
			
			
			/**
			 * Funcionalidad para cerrar la ventana modal y regresar el objeto de domiclio.
			 */
			
			$("#botonControl").click(function(){
				var oForm = $("form#form").serializeObject(true);
				window.returnValue = oForm;
				window.close();
			});
			
			
			
		});