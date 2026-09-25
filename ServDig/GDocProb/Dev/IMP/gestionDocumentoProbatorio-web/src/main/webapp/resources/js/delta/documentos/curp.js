var nacional=true;
	$(document).ready(function() {  
		$.validator.addMethod("alphanumeric", function(value, element) { 
	        return this.optional(element) || /^[a-z0-9\-]+$/i.test(value); 
	    }, "Solo debe contener numero o letras.");	

		//Se toma el valor del curp que se capturo en el registro
		$('#curp').val($('#curpCap').val());
	
		if($.trim($('#curpCap').val()).length != 0)
			$('#curp').attr("disabled","disabled");
		
	$('#entidadFederativa').change(
			function (){
				muestraOcultaExtrangero();
			}
	);
		
	$("#fechaInscripcion").mask("99/99/9999");
	$( "#fechaInscripcion" ).datepicker(
			{
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				maxDate: new Date(), 
				yearRange : '-112:+0'
			}		
	);
	validateForm.allowOnlyRegularExpression( $('.alfanum'),regularExpression.alfanumerico);
	validateForm.allowOnlyRegularExpression( $('.entero_4'),regularExpression.entero_4);
	validateForm.allowOnlyRegularExpression( $('.entero_15'),regularExpression.entero_15);
	validateForm.allowOnlyRegularExpression( $('.entero_8'),regularExpression.entero_8);

	$.datepicker.setDefaults({
		onClose: function(){
			$(this).valid();
		}
	});
	
	var isDocumentacionValida =	$("#formdocument").validate({ 

			rules: { 

				curp: {
					required:true,
					maxlength:18,
					alphanumeric : true,
					curp: true
				}, 
				folio: {
					required:true,
					maxlength:15,
					number : true
				},
				fechaInscripcion: {
					required:true,
					validDate : true,
					validDateToDay : true
				}, 
				anioRegistro: {
					required:nacional,
					number:true,
					maxlength : 4 
				},
				noLibro: {
					required:nacional,
					maxlength:8,
					number : true
				}, 
				noActa: {
					required:nacional,
					maxlength:8,
					number : true
				},
				noFoja: {
					required:nacional,
					maxlength:8,
					number : true
				},
				noTomo: {
					required:nacional,
					maxlength:8,
					number : true
				}, 
				crip: {
					required:nacional,
					maxlength:15,
					alphanumeric : true
				},
				entidadFederativa: {
					required:true,
					min:0
					
				},
				municipio: {
					required:true,
					min:0
					
				}
				
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 

			curp:             { maxlength:"Debe ser de 18 caracteres como m\u00e1ximo", alphanumeric:"Debe ser alfan\u00famerico"},
			folio:            { maxlength:"Debe ser de 15 caracteres como m\u00e1ximo", number:"Debe ser alfan\u00famerico"},
			fechaInscripcion: { validDate : "Fecha inv\u00e1lida", validDateToDay : "La fecha no puede ser mayor a hoy."},
			anioRegistro:     { number:"Debe ser n\u00famerico", maxlength:"Debe ser de 4	 d\u00edgitos"},
			noLibro:          { maxlength:"Debe ser de 8 caracteres como m\u00e1ximo", number:"Debe ser n\u00famerico"},
			noActa:           { maxlength:"Debe ser de 8 caracteres como m\u00e1ximo", number:"Debe ser n\u00famerico"},
			noFoja:           { maxlength:"Debe ser de 8 caracteres como m\u00e1ximo", number:"Debe ser n\u00famerico"},
			noTomo:           { maxlength:"Debe ser de 8 caracteres como m\u00e1ximo", number:"Debe ser n\u00famerico"},
			crip:             { maxlength:"Debe ser de 15 caracteres como m\u00e1ximo", alphanumeric:"Debe ser alfan\u00famerico"},
			entidadFederativa:{min:LABEL_CAMPO_OBLIGATORIO},
			municipio:{min:LABEL_CAMPO_OBLIGATORIO}
 
		} 

	}); 
}); 
	
function muestraOcultaExtrangero(){
	if($('#entidadFederativa').val()!=98){
	
		$('#anioRegRow').show();
		$('#libroRow').show();
		$('#actaRow').show();
		$('#tomoRow').show();
		$('#cripRow').show();
		$('#fojaRow').show();
		nacional=false;
		
	}else{
	
		$('#anioRegRow').hide();
		$('#libroRow').hide();
		$('#actaRow').hide();
		$('#tomoRow').hide();
		$('#cripRow').hide();
		$('#fojaRow').hide();
		nacional=true;
	}
}

