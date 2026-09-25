var objReturn;




/*
 * Seccion de codigo a ejecutar en cuanto el DOM envie la señar de que esta
 * listo para procesar de modificaciones al DOM
 */
$(document).ready(
		function() {
			var wrapperMedios = $('form#form').toObject();
			var ctrl = parent.MedioContactoCtrl;
			if(ctrl != null){
				ctrl.mediosContacto = wrapperMedios;
				ctrl.cerrar();
			}
		});
		
		
		
		
		
		