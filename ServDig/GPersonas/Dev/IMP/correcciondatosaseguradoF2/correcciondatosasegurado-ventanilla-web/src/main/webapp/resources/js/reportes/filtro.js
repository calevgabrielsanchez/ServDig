$(document).ready(function() {  
	 $("#fechaSolicitud").datepicker(); 
		$("#fechaSolicitudHasta").datepicker();  	
		$("#fechaFinalizacion").datepicker();  
		$("#fechaFinalizacionHasta").datepicker();  
		$("#ultimaActualizacion").datepicker();  
		$("#ultimaActualizacionHasta").datepicker();  
		
	
});

function validarFormulario(){
    $("#formRegistro").validate({
		 rules:{ 
				folio: {
					number:true,
					minlength : 25
				},
				curp: {
					minlength: 18,
					maxlength: 18,
					curp: true
				}, 
				nssInvolucrados: {
					required:true,
					maxlength: 11,
					minlength: 10
				}
			},
	 		messages: {
				
				curp: {
					minlength: "Debe ser de 18 caracteres",
					maxlength: "Debe ser de 18 caracteres",
					curp: "Formato incorrecto"
				},
				folio: {required:"Obligatorio", maxlength:"Debe ser de 18 d\u00edgitos como m\u00e1ximo" 
				},
			
				nss:   {required:"Obligatorio", 
						maxlength:"Debe ser de 11 d\u00edgitos como m\u00e1ximo", 
						minlength:"La longitud del nss debe ser al menos de 10 d\u00edgitos.",
						numeric:"Debe ser num�rico"
						}
				
			}
	    });
   
 }