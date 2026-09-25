
	$(document).ready(function() {  
		
		$.datepicker.setDefaults({
			onClose: function(){
				$(this).valid();
			}
		});
		
		$("#municipio").click(
				function (){
					$("#municipioHidden").val($("#municipio option:selected").html());
					$("#entidadHidden").val($("#entidad option:selected").html());
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
    }, "Debe contener unicamente numeros o letras");
	
	
	
	validateForm.allowOnlyRegularExpression( $('.entero_15'),regularExpression.entero_15);
	validateForm.allowOnlyRegularExpression( $('.entero_4'),regularExpression.entero_4);
	validateForm.allowOnlyRegularExpression( $('.alfanum'),regularExpression.alfanumerico);
	
	var isDocumentacionValida =	$("#formdocument").validate({ 

			rules: { 

				noActa: {
					required:true,
					maxlength: 16,
					alphanumeric:true
					
				}, 
				noFoja: {
					required:true,
					maxlength: 8,
					alphanumeric:true
				},
				
				noLibro: {
					required:true,
					maxlength: 8,
					alphanumeric:true
				}, 
				entidad: {
					required:true,
					min: 0
				},
				municipio: {
					required:true,
					min: 0
	
				}, 
				fechaSuceso: {
					required:true,
					validDate : true,
					validDateToDay : true
				},
				anio: {
					required:true,
					maxlength: 4,
					number:true
				},
				tomo: {
					maxlength: 20,
					alphanumeric:true
				},
				crip: {
					maxlength: 18,
					number:true
				},
				noJuzgado: {
					required:true,
					maxlength: 8,
					alphanumeric:true
				} 
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 
			noActa:  {maxlength: "Debe ser de 16 caracteres como m\u00e1ximo", alphanumeric: "Debe ser alfanum\u00E9rico"},
			noFoja:  {maxlength: "Debe ser de 8 caracteres como m\u00e1ximo", alphanumeric: "Debe ser alfanum\u00E9rico"},
			noLibro: {maxlength: "Debe ser de 8 caracteres como m\u00e1ximo", alphanumeric: "Debe ser alfanum\u00E9rico"},
			entidad: {min:LABEL_CAMPO_OBLIGATORIO},
			municipio: {min:LABEL_CAMPO_OBLIGATORIO}, 
			fechaSuceso: {validDate : "Fecha inv\u00e1lida",validDateToDay : "La fecha no puede ser mayor a hoy."},
			anio: {maxlength: "Debe ser de 4 digitos" , number:"Debe ser num\u00E9rico"},
			tomo: {maxlength: "Debe ser de 20 caracteres como m\u00e1ximo", alphanumeric: "Debe ser alfanum\u00E9rico"},
			crip: {maxlength: "Debe ser de 15 caracteres como m\u00e1ximo", number: "Debe ser num\u00E9rico"},
			noJuzgado: {maxlength: "Debe ser de 18 digitos",alphanumeric: "Debe ser alfanum\u00E9rico"}
		}
	}); 
}); 
	


