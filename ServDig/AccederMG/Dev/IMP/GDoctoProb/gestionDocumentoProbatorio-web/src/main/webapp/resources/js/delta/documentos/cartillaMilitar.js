
	$(document).ready(function() {  
		
		validateForm.allowOnlyRegularExpression( $('.entero_15'),regularExpression.entero_15);
		
		$.datepicker.setDefaults({
			onClose: function(){
				$(this).valid();
			}
		});
		$("#fechaExpedicion").mask("99/99/9999");
		$( "#fechaExpedicion" ).datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					maxDate: new Date(), 
					yearRange : '-112:+0'			
				}		
		);
		
		$.validator.addMethod("alphanumeric", function(value, element) { 
	        return this.optional(element) || /(^[a-zA-Z0-9]+$)/i.test(value); 
	    }, "Debe contener unicamente letras y numeros");
		
	var isDocumentacionValida =	$("#formdocument").validate({ 

			rules: { 

				noMatricula: {
					required:true,
					maxlength: 16,
					number  : true
					
				}, 
				fechaExpedicionString: {
					required:true,
					validDate: true,
					validDateToDay : true

				}
							
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 

			noMatricula: {maxlength:"Debe ser de 16 caracteres como m\u00e1ximo", number: "Debe ser n\u00famerico"},
			fechaExpedicionString: {validDateToDay : "La fecha no puede ser mayor a hoy.", validDate : "Fecha inv\u00e1lida"}
			 
		} 

	}); 
}); 
	


