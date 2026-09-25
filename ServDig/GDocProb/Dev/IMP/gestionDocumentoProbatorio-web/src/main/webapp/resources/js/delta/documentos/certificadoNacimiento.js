
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
		
		$("#fechaAlumbramiento").mask("99/99/9999");
		$( "#fechaAlumbramiento" ).datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					yearRange : '-112:+0'			
				}		
		);
		
		
		
		$.validator.addMethod("alphanumeric", function(value, element) { 
	        return this.optional(element) || /^[a-z0-9\-]+$/i.test(value); 
	    }, "Username must contain only letters, numbers, or dashes.");
		
		validateForm.allowOnlyRegularExpression( $('.entero_15'),regularExpression.entero_15);
		
	$("#formdocument").validate({ 

			rules: { 
				noFolio: {
					required:true,
					maxlength:8, 
					alphanumeric : true
				}, 
				fechaAlumbramiento: {
					required:true,
					validDate : true
				},
				idSexo: {
					required:true,
					min:1
				},	
				fechaExpedicion: {
					required:true,
					validDate : true,
					validDateToDay : true
				}, 
				desLugarAlumbramiento: {
					required:true,
					maxlength:150
				}
			}, 

			errorLabelContainer: "#warning", 

 
		messages: { 

			noFolio:            {maxlength:"Debe ser de 8 caracteres como m\u00e1ximo", alphanumeric: "Debe ser alfan\u00famerico"},
			fechaAlumbramiento: {validDate : "Fecha inv\u00e1lida"},
			fechaExpedicion:    {validDate : "Fecha inv\u00e1lida", validDateToDay : "La fecha no puede ser mayor a hoy."},
			idSexo : {min : LABEL_CAMPO_OBLIGATORIO}
 
		} 

	}); 
}); 
	
	
	
