var listaMedicos;
	$(document).ready(function() {  
		
		validateForm.allowOnlyRegularExpression( $('.alfanumerico_espacios'),regularExpression.alfanumerico_espacios);
		
		
		$.validator.addMethod("alphanumeric", function(value, element) { 
	        return this.optional(element) || /^[a-z0-9\-]+$/i.test(value); 
	    }, "Username must contain only letters, numbers, or dashes.");
		
		getMedicos();
		
		$.datepicker.setDefaults({
			onClose: function(){
				$(this).valid();
			}
		});
		
		$("#fechaExpedicion").mask("99/99/9999");
		datePikerEstandar("#fechaExpedicion" );
		$("#fechaProbableInicio").mask("99/99/9999");
		$("#fechaProbableInicio").datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					
					yearRange : '-10:+10',
					onSelect: function() {
						  $('#fechaTerminoIncapacidad').datepicker('option', {minDate: $("#fechaProbableInicio").datepicker('getDate')});
						}
						
				}		
		);
		
		$("#fechaTerminoIncapacidad").mask("99/99/9999");
		$("#fechaTerminoIncapacidad").datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					
					yearRange : '-10:+10'
				}		
		);
	var isDocumentacionValida =	$("#formdocument").validate({ 

			rules: { 

				enfermedadPadecida: {
					required:true,
					maxlength:50
					
				}, 
				fechaTerminoIncapacidad: {
					required:true, 
					validDate: true
				},
				medicoFamiliar: {
					required:false 
				},
				fechaExpedicionString: {
					required:true,
					validDate: true,
					validDateToDay : true
					
				},
				fechaProbableInicio: {
					required:true, 
					validDate: true,
					validDateToDay : true
				}
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 

			enfermedadPadecida: {maxlength:"Debe ser de 50 caracteres como m\u00e1ximo"},
			fechaTerminoIncapacidad: {validDate : "Fecha inv\u00e1lida"},
			fechaExpedicionString: {validDateToDay : "La fecha no puede ser mayor a hoy.", validDate : "Fecha inv\u00e1lida"},
			fechaProbableInicio: {validDateToDay : "La fecha no puede ser mayor a hoy.", validDate : "Fecha inv\u00e1lida"}
			
		} 

	}); 
	
	
	// ------------------------------------------------
	// Limita el n�mero de caracteres en la text area
	// ------------------------------------------------
	if( $("#enfermedadPadecida").length > 0 ){
		asignartextAreaLimites("enfermedadPadecida",{styles:{}});
	}
	
}); 
	
	function datePikerEstandar(id){
		$(id).datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					yearRange : '-112:+10',
					maxDate : new Date()
				}		
		);
	}
	function getMedicos(){
		
		var idUmf = $("#idUmf").val();
		$.getJSON("/gestionDocumentoProbatorio-web/documentos/getMedicosByUmf/",
				{
				//parametros de envio
				idUmf : idUmf
				},              
				function(data) {
					$("#selectMedico").html(pintaMedicos(data));
					listaMedicos=data;
				}   
			);
		
	}


	function pintaMedicos(data){
		var html = '';     
		var lista=data;
		var len = lista.length;
		
		var i=0;
		html +='<option value="-1">--Seleccione--</option>';
		for(i=0;i<len;i++){
			try{
				html += '<option value="' + lista[i].idMedicoFamiliar + '" >' + lista[i].nombre + ' ' + lista[i].primerApellido + ' ' + lista[i].segundoApellido +'</option>';
			}catch(e){
				//-----------------------------------------------
				// Campos nulos
				//-----------------------------------------------
			}

		}
		return html;
	}

	function showMatricula(){
		var index = $("#selectMedico")[0].selectedIndex;
		
		try{
			var medico=listaMedicos[index-1];
			$("#noMatricula").html(medico.noMatricula);
			$("#idMedicoEspecialidad").val(medico.idMedicoEspecialidad);
			$("#matriculaMed").val(medico.noMatricula);
			$("#nombreMed").val(medico.nombre);
			$("#primerApeidoMed").val(medico.primerApellido);
			$("#segundoApeidoMed").val(medico.segundoApellido);
		}catch(e){
			$("#noMatricula").html("N/A");
			$("#idMedicoEspecialidad").val("-1");
			$("#matriculaMed").val("");
			$("#nombreMed").val("");
			$("#primerApeidoMed").val("");
			$("#segundoApeidoMed").val("");
		}
		
	}
