
var objReturn;


/*
 * Seccion de codigo a ejecutar en cuanto el DOM envie la señar de que esta
 * listo para procesar de modificaciones al DOM
 */
$(document).ready(
		function() {
			
			
			
			$('div#control form#form').submit(function(){
				
				
				
				var telefonoFijo = $('div#telefonoFijo form#form').serializeObject(true);
				var telefonoMovil = $('div#telefonoMovil form#form').serializeObject(true);
				var correElectronico = $('div#correoElectronico form#form').serializeObject(true);
				
				var wrapperMedios = new Object();
				wrapperMedios.telefonoFijo = telefonoFijo;
				wrapperMedios.telefonoMovil = telefonoMovil;
				wrapperMedios.correElectronico = correElectronico;
				
				
				window.returnValue = wrapperMedios;
				window.close();
				
				return false;
			});
			
			
			
		})