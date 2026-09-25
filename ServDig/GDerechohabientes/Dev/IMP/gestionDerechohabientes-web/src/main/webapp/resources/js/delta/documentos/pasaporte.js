
	$(document).ready(function() {  
		
		$.datepicker.setDefaults({
			onClose: function(){
				$(this).valid();
			}
		});
		
		validateForm.allowOnlyRegularExpression( $('.entero_10'),regularExpression.entero_10);
		validateForm.allowOnlyRegularExpression( $('.alfanumerico'),regularExpression.alfanumerico);
		
				$("#fechaExpedicion").mask("99/99/9999");
				$( "#fechaExpedicion" ).datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					maxDate: new Date(), 
					yearRange : '-112:+0',
					onSelect: function() {
						  $('#fechaCaducidad').datepicker('option', {minDate: $("#fechaExpedicion").datepicker('getDate')});
						}
				}		
				
				);
				
				$("#fechaCaducidad").mask("99/99/9999");
				$( "#fechaCaducidad" ).datepicker(
						{
							dateFormat : 'dd/mm/yy',
							changeMonth : true,
							changeYear : true,
							yearRange : '-0:+10'
						}		

				);
				
		
		
		$.validator.addMethod("alphanumeric", function(value, element) { 
	        return this.optional(element) || /^[a-z0-9\-]+$/i.test(value); 
	    }, "Username must contain only letters, numbers, or dashes.");
		
	var isDocumentacionValida =	$("#formdocument").validate({ 

			rules: { 

				noPasaporte: {
					required:true,
					maxlength: 10,
					number : true
				}, 
				fechaCaducidad: {
					required:true,
					validDate : true
				},			
				fechaExpedicion: {
					required:true,
					validDate : true,
					validDateToDay : true
				}
			}, 
		errorLabelContainer: "#warning",
		messages: { 

			noPasaporte: {required:"Obligatorio", maxlength:"Debe ser de 10 caracteres como m\u00e1ximo", number: "Debe ser n\u00famerico"},
			fechaCaducidad: {required:"Obligatorio", validDate : "Fecha inv\u00e1lida"},
			fechaExpedicion: {required:"Obligatorio", validDate : "Fecha inv\u00e1lida", validDateToDay : "La fecha no puede ser mayor a hoy."}
		} 

	}); 
}); 
	


