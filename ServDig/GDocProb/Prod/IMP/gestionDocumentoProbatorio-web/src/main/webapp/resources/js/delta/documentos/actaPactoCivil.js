
	$(document).ready(function() {  
	
		
		$.datepicker.setDefaults({
			onClose: function(){
				$(this).valid();
			}
		});
		

	$("#fechaSuceso").mask("99/99/9999");	
	$( "#fechaSuceso" ).datepicker(
			{
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				maxDate: new Date(), 
				yearRange : '-112:+0'	
			}		
	);
	
	$.validator.addMethod("alphanumeric", function(value, element) { 
        return this.optional(element) || /^[a-z0-9\-]+$/i.test(value); 
    }, "Username must contain only letters, numbers, or dashes.");
		
	validateForm.allowOnlyRegularExpression( $('.entero_15'),regularExpression.entero_15);
	validateForm.allowOnlyRegularExpression( $('.entero_8'),regularExpression.entero_8);
	
	
	var isDocumentacionValida =	$("#formdocument").validate({ 

			rules: { 

				noActa: {
					required:true,
					maxlength: 15,
					number:true
				}, 
				
				noFoja: {
					required:true,
					maxlength: 8,
					number:true
				},
				
				noLibro: {
					required:true,
					maxlength: 8,
					number:true
				}, 
				
				fechaSuceso: {
					required:true,
					validDate : true,
					validDateToDay : true
				},


				tomo: {
					maxlength: 20,
					alphanumeric:true
				}
				
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 
			noActa:  {maxlength: "Debe ser de 15 caracteres como m\u00e1ximo", number: "Debe ser n\u00famerico"},
			noFoja:  {maxlength: "Debe ser de 8 caracteres como m\u00e1ximo", number: "Debe ser n\u00famerico"},
			noLibro: {maxlength: "Debe ser de 8 caracteres como m\u00e1ximo", number: "Debe ser n\u00famerico"}, 
			fechaSuceso: {validDate : "Fecha inv\u00e1lida",validDateToDay : "La fecha no puede ser mayor a hoy."},
			tomo: {maxlength: "Debe ser de 20 caracteres como m\u00e1ximo", alphanumeric: "Debe ser alfanum\u00E9rico"}
	
		}
	}); 
}); 