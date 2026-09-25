var dataNivelEducativo;
	$(document).ready(function() {
		
		validateForm.allowOnlyRegularExpression( $('.alfanumerico_espacios'),regularExpression.alfanumerico_espacios);
		validateForm.allowOnlyRegularExpression( $('.alfanumerico'),regularExpression.alfanumerico);
		$.validator.addMethod("alphanumeric", function(value, element) { 
	        return this.optional(element) || /^[a-z0-9\-]+$/i.test(value); 
	    }, "Username must contain only letters, numbers, or dashes.");
		
		
		getNivelEducativo();
		
		$.datepicker.setDefaults({
			onClose: function(){
				$(this).valid();
			}
		});
		
		$("#fechaInicioPeriodo").mask("99/99/9999");
		$("#fechaExpedicion").mask("99/99/9999");
		$("#fechaFinPeriodo").mask("99/99/9999");
		
		datePikerEstandar("#fechaExpedicion");
		
		$("#fechaFinPeriodo").datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,					
					yearRange : '-0:+10',
					minDate: new Date()
				}		
		);
		
		$("#fechaInicioPeriodo").datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					yearRange : '-0:+10',
					maxDate : new Date(),
					onSelect: function() {
						var max = null;
						
						var str =  $("#fechaInicioPeriodo").val().match(/^(\d{2})\/(\d{2})\/(\d{4})$/);
						max = new Date(str[3], str[2]-1, str[1]);
						max.setFullYear(max.getFullYear() + 1);
						
						$('#fechaFinPeriodo').datepicker('option', {maxDate: max});
					}	
				}		
		);
		
		
		
		
	var isDocumentacionValida =	$("#formdocument").validate({ 
			
			rules: { 
				idTipoNivelEducativo: {
					required:true
				}, 
				idNivelEducativo: {
					required:true
				}, 
				nombreEscuela: {
					required:true,
					maxlength:30
				}, 
				
				claveEscuela: {
					required:true,
					maxlength:15,
					alphanumeric : true
				},

				noIncorporacion: {
					required:true,
					maxlength:15,
					alphanumeric : true
				}, 
				gradoEscolar: {
					required:true,
					maxlength:15,
					alphanumeric:true
	
				},

				fechaInicioPeriodo: {
					required:true,
					validDate: true
					
	
				}, 
				fechaFinPeriodo: {
					required:true,
					validDate: true,
					fechaMayorQue : "#fechaInicioPeriodo",
					fechaRangoUnAnio: "#fechaInicioPeriodo"
				},
				fechaExpedicionString:{
					required:true,
					validDate: true,
					validDateToDay : true
				}
				
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 

			nombreEscuela: {required:"Obligatorio", maxlength:"Debe ser de 30 caracteres como m\u00e1ximo"},
			claveEscuela: {required:"Obligatorio", maxlength:"Debe ser de 15 caracteres como m\u00e1ximo",  alphanumeric: "Debe ser alfan\u00famerico"},
			noIncorporacion: {required:"Obligatorio", maxlength:"Debe ser de 15 caracteres como m\u00e1ximo",  alphanumeric: "Debe ser alfan\u00famerico"},
			gradoEscolar: {required:"Obligatorio", maxlength:"Debe ser de 15 caracteres como m\u00e1ximo", alphanumeric: "Debe ser alfan\u00famerico"},
			fechaInicioPeriodo: {required:"Obligatorio", validDate : "Fecha inv\u00e1lida"},
			fechaFinPeriodo: {required:"Obligatorio", validDate : "Fecha inv\u00e1lida", fechaMayorQue: "La fecha fin debe ser mayor o igual que la fecha de inicio."},
			fechaExpedicionString: {required:"Obligatorio", validDateToDay : "La fecha no puede ser mayor a hoy.", validDate : "Fecha inv\u00e1lida"},
			idNivelEducativo: {required:"Obligatorio"},
			idTipoNivelEducativo: {required:"Obligatorio"}
			
		} 

	}); 
}); 
	

	function datePikerEstandar(id){
		$(id).datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					maxDate: new Date(), 
					yearRange : '-112:+0'
				}		
		);
	}
	
	function getNivelEducativo(){
		$.getJSON(contextPath + "/documentos/getNivelEducativo",
				{
	//parametros de envio		
				},              
				function(data) {
					//funciones despues e llamar el controlador
					$("#selectTipoNivelEducativo").html(pintaNivel(data));
					dataNivelEducativo=data;
				}   
			);
		
	}


	function pintaNivel(data){
		var html = '';     
		var lista=data;
		var len = lista.length;
		
		var i=0;
		html +='<option>--Seleccione--</option>';
		for(i=0;i<len;i++){
			
			html += '<option value="' + lista[i].idNivelEducativo + '" >' + lista[i].desNivelEducativo +'</option>';

		}
		return html;
		
	}

	function getDetalleNivel(){
		var data=dataNivelEducativo;
		var nivelEdu;
		var index = $("#selectTipoNivelEducativo")[0].selectedIndex;
			
		try{
			nivelEdu=data[index-1].nivelEducativos;
			$("#selectNivelEducativo").html(pintaDetalleNivel(nivelEdu));
		}catch(e){
				$("#selectNivelEducativo").html("");
		}
	}
	
	function pintaDetalleNivel(data){
		var html = '';     
		var lista=data;
		var len = lista.length;
		
		var i=0;
		html +='<option>--Seleccione--</option>';
		for(i=0;i<len;i++){
			
			html += '<option value="' + lista[i].idnivelEducativo + '" >' + lista[i].desNivelEducativo +'</option>';

		}
		return html;
	}
