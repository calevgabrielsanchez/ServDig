
	$(document).ready(function() {  
		
		
		$.datepicker.setDefaults({
			onClose: function(){
				$(this).valid();
			}
		});
		
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
	
	
	
	var isDocumentacionValida =	$("#formdocument").validate({ 

			rules: { 

				noActa: {
					required:true,
					maxlength: 15,
					alphanumeric:true
				}, 
				
				noFoja: {
					required:true,
					maxlength: 15,
					alphanumeric:true
				},
				
				noLibro: {
					required:true,
					maxlength: 15,
					alphanumeric:true
				}, 
				
				fechaSuceso: {
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
					maxlength: 15,
					alphanumeric : true
				}
				

				
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 
			noActa:  {required:"Obligatorio", maxlength: "Debe ser de 15 caracteres como m\u00e1ximo", alphanumeric: "Debe ser alfan\u00famerico"},
			noFoja:  {required:"Obligatorio", maxlength: "Debe ser de 15 caracteres como m\u00e1ximo", alphanumeric: "Debe ser alfan\u00famerico"},
			noLibro: {required:"Obligatorio", maxlength: "Debe ser de 15 caracteres como m\u00e1ximo", alphanumeric: "Debe ser alfan\u00famerico"}, 
			fechaSuceso: {required:"Obligatorio", validDate : "Fecha inv\u00e1lida",validDateToDay : "La fecha no puede ser mayor a hoy."},
			municipio: {required:"Obligatorio",min:"Obligatorio"}, 
			entidad: {required:"Obligatorio",min:"Obligatorio"},
			noJuzgado: {required:"Obligatorio", maxlength: "Debe ser de 15 caracteres como m\u00e1ximo", alphanumeric: "Debe ser alfan\u00famerico"} 
		}
	}); 
}); 
	


