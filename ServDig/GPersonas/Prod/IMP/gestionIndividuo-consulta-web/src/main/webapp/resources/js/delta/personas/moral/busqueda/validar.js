$(document).ready(function(){
	
	$("form#forma").submit(function(){
		var f = $("form#forma");
		//Limpiamos los errores
		fnHideErrores("form#forma");

		$('input.disabled , select').each(function() {
			$(this).removeAttr('disabled', 'disabled');
		});
		
		oForm = $(f).toObject();
		
		var ctrl = parent.PersonaMoralCtrl;
    	
		if(ctrl != null){
    		ctrl.setPersona(oForm);
    		ctrl.dialogo.dialog('close');
    	}
		return false;
	});
	
});