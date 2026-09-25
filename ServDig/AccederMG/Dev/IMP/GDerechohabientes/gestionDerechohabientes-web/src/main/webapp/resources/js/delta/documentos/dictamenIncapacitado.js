var listaMedicos;
	$(document).ready(function() { 
		
		validateForm.allowOnlyRegularExpression( $('.entero_10'),regularExpression.entero_10);
	 	validateForm.allowOnlyRegularExpression( $('.alfanumerico_espacios'),regularExpression.alfanumerico_espacios);
		
		setValoresCombo("#unidadMedicaFamiliar","#nombreUMFHiddenId");
		setValoresCombo("#delegacion","#nombreDelegacionHiddenId");
		
		getMedicos();
		
		$.datepicker.setDefaults({
			onClose: function(){
				$(this).valid();
			}
		});
		
		$("#fechaInicioEnfermedad").mask("99/99/9999");
		$("#fechaExpedicion").mask("99/99/9999");
		
		datePikerEstandar("#fechaInicioEnfermedad" );
		datePikerEstandar("#fechaExpedicion" );

		
		var isDocumentacionValida =	$("#formdocument").validate({ 

			rules: { 

				existeEstadoIncapacidad: {
					required:true
				}, 
				gradoIncapacidad: {
					required:true
				},	
				diagnosticoPadecimiento: {
					required:true
				},	
				fechaInicioEnfermedad: {
					required:true,
					validDate: true,
					validDateToDay : true
					
				},	
				selectMedico: {
					required:false
				},
				unidadMedicaFamiliar: {
					required:true
				},
				delegacion: {
					required:true
				},
				fechaExpedicionString: {
					required:true,
					validDate: true,
					validDateToDay : true
					
				}
				
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 

			existeEstadoIncapacidad: {required:"Obligatorio"},
			gradoIncapacidad: {required:"Obligatorio"},
			diagnosticoPadecimiento: {required:"Obligatorio"},
			fechaInicioEnfermedad: {required:"Obligatorio", validDateToDay : "La fecha no puede ser mayor a hoy.", validDate : "Fecha inv\u00e1lida"},
			selectMedico: {required:"Obligatorio"},
			unidadMedicaFamiliar: {required:"Obligatorio"},
			delegacion: {required:"Obligatorio"},
			fechaExpedicionString: {required:"Obligatorio", validDateToDay : "La fecha no puede ser mayor a hoy.", validDate : "Fecha inv\u00e1lida"}
			
		} 

	}); 
		
		
		// ------------------------------------------------
		// Limita el número de caracteres en la text area
		// ------------------------------------------------
		if( $("#diagnosticoPadecimiento").length > 0 ){
			asignartextAreaLimites("diagnosticoPadecimiento",{styles:{}});
		}	
		
		
		
	}); 
	
	function datePikerEstandar(id){
		$(id).datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					maxDate : new Date(),
					yearRange : '-112:+0'
				}		
		);
	}
	function getMedicos(){
		
		var idUmf = $("#idUmf").val();
		
		
		$.getJSON(contextPath + "/documentos/getMedicosByUmf/",
				{
				//parametros de envio
				idUmf : idUmf
				},              
				function(data) {
					//funciones despues e llamar el controlador
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
		html +='<option value="-1">No localizado</option>';
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
	
	function setValoresCombo(comboId,setId){
		$(comboId).click(
				function (){
					$(setId).val($(comboId + " option:selected").html());
				}
			);
	}