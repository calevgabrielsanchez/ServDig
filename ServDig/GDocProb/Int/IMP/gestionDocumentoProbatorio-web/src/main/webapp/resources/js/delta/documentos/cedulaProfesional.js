
	$(document).ready(function() {  
		
		$.datepicker.setDefaults({
			onClose: function(){
				$(this).valid();
			}
		});
		
		$("#fechaExpedicionString").mask("99/99/9999");
		$( "#fechaExpedicionString" ).datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					maxDate: new Date(), 
					yearRange : '-112:+0'			
				}		
		);
		
		$.validator.addMethod("alphanumeric", function(value, element) { 
	        return this.optional(element) || /^[\s-\w]*$/i.test(value); 
	    }, "Debe ser alfanumerico."); 
		
		validateForm.allowOnlyRegularExpression( $('.entero_10'),regularExpression.entero_10);
		validateForm.allowOnlyRegularExpression( $('.entero_15'),regularExpression.entero_15);
		validateForm.allowOnlyRegularExpression( $('.alfanum'),regularExpression.alfanumerico_espacios);
		
		var isDocumentacionValida =	$("#formdocument").validate({ 

			rules: { 

				cedula: {
					required:true,
					maxlength: 10,
					number  : true
				}, 
				fechaExpedicionString: {
					required:true,
					validDate: true,
					validDateToDay : true

				},
				profesion: {
					required:true,
					maxlength: 50
					//alphanumeric:true
					
				}
							
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 

			cedula: {maxlength:"Debe ser de 10 caracteres como m\u00e1ximo", number: "Debe ser n\u00famerico"},
			fechaExpedicionString: {validDateToDay : "La fecha no puede ser mayor a hoy.", validDate : "Fecha inv\u00e1lida"},
			profesion: {maxlength:"Debe ser de 50 caracteres como m\u00e1ximo", alphanumeric: "Debe ser alfanum\u00e9rico"}
		} 
			
		}); 
	}); 
		
	