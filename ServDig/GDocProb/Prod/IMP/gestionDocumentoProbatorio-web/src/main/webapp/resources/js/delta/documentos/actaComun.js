
	$(document).ready(function() {  
	
		
		$.datepicker.setDefaults({
			onClose: function(){
				$(this).valid();
			}
		});
		
		$( "#municipio").click(
				function (){
					$("#municipioHidden").val($("#municipio option:selected").html());
					$("#entidadHidden").val($("#entidad option:selected").html());
				}
			);
			
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
				
				fechaExpedicionString: {
					required:true,
					validDate : true,
					validDateToDay : true
				},
				
				municipio: {
					required:true,
					min: 0
	
				}, 
				entidad: {
					required:true,
					min: 0
	
				},
				
				noJuzgado: {
					required:true,
					maxlength: 8,
					number : true
				}
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 
			noActa:  { maxlength: "Debe ser de 15 caracteres como m\u00e1ximo", number: "Debe ser n\u00famerico"},
			noFoja:  {maxlength: "Debe ser de 8 caracteres como m\u00e1ximo", number: "Debe ser n\u00famerico"},
			noLibro: {maxlength: "Debe ser de 8 caracteres como m\u00e1ximo", number: "Debe ser n\u00famerico"}, 
			fechaSuceso: {validDate : "Fecha inv\u00e1lida",validDateToDay : "La fecha no puede ser mayor a hoy."},
			fechaExpedicionString: {validDate : "Fecha inv\u00e1lida",validDateToDay : "La fecha no puede ser mayor a hoy."},
			municipio: {min:LABEL_CAMPO_OBLIGATORIO}, 
			entidad: {min:LABEL_CAMPO_OBLIGATORIO},
			noJuzgado: {maxlength: "Debe ser de 8 caracteres", number: "Debe ser n\u00famerico"}
		}
	}); 
}); 

	


