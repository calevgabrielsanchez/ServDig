
var listaMedicos;

$(document).ready(function() {
		
		getMedicos();
				
		$.datepicker.setDefaults({
			onClose: function(){
				$(this).valid();
			}
		});
		
		$("#fechaCertificacionMedico").mask("99/99/9999");
		$("#fechaCertificacionMedico").datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					maxDate: new Date(), 
					yearRange : '-10:+0'
				}		
		);
		
		$("#fechaParto").mask("99/99/9999");
		$("#fechaParto").datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					yearRange : '-0:+1'
				}		
		);
		
		$("#fechaConcepcion").mask("99/99/9999");
		$("#fechaExpedicion").mask("99/99/9999");
		datePikerEstandar("#fechaConcepcion");
		datePikerEstandar("#fechaExpedicion");
		
		var isDocumentacionValida =	$("#formdocument").validate({ 

			rules: { 

				fechaParto: {
					required:true,
					validDate: true
				
				}, 
				fechaExpedicion: {
					required:true,
					validDate: true,
					validDateToDay : true
					
				},	
				fechaCertificacionMedico: {
					required:true,
					validDate: true,
					validDateToDay : true
					
				},	
				fechaConcepcion: {
					required:true,
					validDate: true,
					validDateToDay : true
					
				}
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 			
			fechaParto: {
				validDate : "Fecha inv\u00e1lida"
			},
			fechaCertificacionMedico: {
				validDateToDay : "La fecha no puede ser mayor a hoy.", 
				validDate : "Fecha inv\u00e1lida"
			},
			fechaConcepcion: {
				validDateToDay : "La fecha no puede ser mayor a hoy.", 
				validDate : "Fecha inv\u00e1lida"
			},
			fechaExpedicion: {
				validDateToDay : "La fecha no puede ser mayor a hoy.", 
				validDate : "Fecha inv\u00e1lida"
			}
		} 

	});
});  
	

function getMedicos(){
	
	var idUmf = $("#idUmf").val();
	$.getJSON(context_path + "/documentos/getMedicosByUmf/",
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
		$("#matriculaMed").val(medico.noMatricula);
		$("#idMedicoEspecialidad").val(medico.idMedicoEspecialidad);
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