$(document).ready(function(){
	
	
	
	
	
	
	
	
	$("form#forma").submit(function(){
		
		var f = $("form#forma");
		
		//Limpiamos los errores
		fnHideErrores("form#forma");
		
		var sSource = $(f).attr("action");
		
		var oForm = $(f).toObject();
		
		$.postJSON(sSource, oForm, function(data) {
			
			
			/*
			 * Habilitamos todos los campos para que se envien...
			 */
			
			$('input.disabled , select').each(function() {
				$(this).removeAttr('disabled', 'disabled');
			});
			
			oForm = $(f).toObject();
			
			var ctrl = parent.PersonaFisicaCtrl;
        	if(ctrl != null){
        		ctrl.setPersona(oForm);
        		ctrl.dialogo.dialog('close');
        	}
			
		}).error(function(data) {
			fnProcesarErrores(data, "form#forma");
		});
		
		
		return false;
	});
	
});