
	$(document).ready(function() {  
		console.log('entroaljsdeactaunion');
		$.datepicker.setDefaults({
			onClose: function(){
				$(this).valid();
			}
		});
		
		$( "#autoridadEmisora").click(
				function (){
					$("#autoridadEmisoraHidden").val($("#autoridadEmisora option:selected").html());
				}
			);
		
		$( "#entidad").click(
				function (){
					$("#entidadFederativaHidden").val($("#entidad option:selected").html());
				}
			);
		
		
		
				$("#fechaEmision").mask("99/99/9999");
				$( "#fechaEmision" ).datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					maxDate: new Date(), 
					yearRange : '-112:+0'
				}
				);
				
		
		
		$.validator.addMethod("alphanumeric", function(value, element) {
				    return this.optional(element) || /^[a-z0-9\-*?!@#$/(){}=. ,;:]+$/i.test(value);
		}, "Debe contener solo letras, números o los siguientes caracteres especiales: - *?!@#$/(){}=. ,;:");

		validateForm.allowOnlyRegularExpression($('.alfanum'), /^[a-z0-9\-*?!@#$/(){}=. ,;:]+$/i);
		validateForm.allowOnlyRegularExpression( $('.entero_20'),regularExpression.entero_20);
		validateForm.allowOnlyRegularExpression( $('.alfanumerico_espacios'),regularExpression.alfanumerico_espacios);
		
		var isDocumentacionValida =	$("#formdocument").validate({ 

			rules: { 

				lugarEmision: {
					required:true,
					maxlength: 50
				}, 
				
				fechaEmision: {
					required:true,
					validDate : true,
					validDateToDay : true
				},
				
				autoridadEmisora: {
					required:true,
					min: 0
				},	
				
				entidadFederativa: {
					required:true,
					min: 0
				},	
				
				noReferencia: {
					required:true,
					maxlength: 20
				}
							
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 

			lugarEmision: {required:"Obligatorio", maxlength:"Debe ser de 50 caracteres como m\u00e1ximo"},
			fechaEmision: {required:"Obligatorio", validDate : "Fecha inv\u00e1lida", validDateToDay : "La fecha no puede ser mayor a hoy."},
			autoridadEmisora: {required:"Obligatorio",min:"Obligatorio"},
			entidadFederativa: {required:"Obligatorio",min:"Obligatorio"},
			noReferencia: {required:"Obligatorio", maxlength:"Debe ser de 20 caracteres como m\u00e1ximo"}
		} 	
			
		}); 
	}); 	

