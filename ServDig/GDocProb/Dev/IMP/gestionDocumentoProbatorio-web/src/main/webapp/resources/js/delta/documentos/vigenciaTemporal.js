
	$(document).ready(function() {  
		
		validateForm.allowOnlyRegularExpression( $('.alfanumerico'),regularExpression.alfanumerico);
		
		 $.validator.addMethod("alphanumeric", function(value, element) { 
		        return this.optional(element) || /^[a-z0-9\-]+$/i.test(value); 
		    }, "Username must contain only letters, numbers, or dashes."); 
		 
		 
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
	var isDocumentacionValida =	$("#formdocument").validate({ 

			rules: { 

				noFolio: {
					required:true,
					maxlength: 8,
					alphanumeric :true
					
				}, 
				fechaExpedicionCadena: {
					required:true,
					validDate: true,
					validDateToDay: true
					
				} 
							
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 

			noFolio: {maxlength:"Debe ser de 8 caracteres como m\u00e1ximo", alphanumeric:"Debe ser alfan\u00famerico"},
			fechaExpedicionCadena:{
				validDateToDay : "La fecha no puede ser mayor a hoy.",
				validDate : "Fecha inv\u00e1lida"
			}
			 
		} 

	}); 
}); 
	


