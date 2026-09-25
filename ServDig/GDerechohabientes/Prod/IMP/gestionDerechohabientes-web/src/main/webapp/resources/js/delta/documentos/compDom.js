
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
					minDate: '-3m',
					maxDate: new Date(), 
					yearRange : '-112:+0'			
				}		
		);
		
		
		
		validateForm.allowOnlyRegularExpression( $('.entero_15'),regularExpression.entero_15);
		var isDocumentacionValida =	$("#formdocument").validate({ 

			rules: { 

				folio: {
					required:true,
					maxlength: 15,
					number : true
					
				}, 
				fechaExpedicionString: {
					required:true,
					validDate : true,
					validDateToDay : true,
					fechaMayorTresMeses: true
				}
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 

			folio: {required:"Obligatorio", maxlength:"Debe ser de 15 caracteres", number:"Debe ser n\u00famerico"},
			fechaExpedicionString: {required:"Obligatorio", validDate : "Fecha inv\u00e1lida", validDateToDay : "La fecha no puede ser mayor a hoy.", fechaMayorTresMeses: "La fecha no debe ser menos a tres meses"}
		} 

	}); 
		
		
		
	}); 
	


