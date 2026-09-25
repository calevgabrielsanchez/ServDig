
	$(document).ready(function() {  
		
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
		
		validateForm.allowOnlyRegularExpression( $('.entero_20'),regularExpression.entero_20);
		
		var isDocumentacionValida =	$("#formdocument").validate({ 

			rules: { 

				folio: {
					required:true,
					maxlength: 20,
					number  : true
				}, 
				fechaExpedicion: {
					required:true,
					validDate: true,
					validDateToDay : true
				}
							
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 

			folio: {required:"Obligatorio", maxlength:"Debe ser de 20 caracteres como m\u00e1ximo", number: "Debe ser n\u00famerico"},
			fechaExpedicion: {required:"Obligatorio", validDateToDay : "La fecha no puede ser mayor a hoy.", validDate : "Fecha inv\u00e1lida"},			 
		} 
			
		}); 
	}); 
		
	