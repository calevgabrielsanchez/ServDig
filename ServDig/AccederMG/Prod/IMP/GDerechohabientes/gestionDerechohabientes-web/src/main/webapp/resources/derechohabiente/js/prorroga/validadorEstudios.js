 $(document).ready(function(){
  //inicio de funcion ready
    $("#frmRegistroProrroga").validate({
    	rules:{ 
    		nombreEscuela:{
				required:true
			},			
    		noIncorporacion:{
				required:true
			},
			claveEscuela:{
				required:true
			},
			fechaInicio:{
				required:true,
				validDate : true
			},
			fechaFin:{
				required:true,
				validDate : true
			},
			gradoEscolar:{
				required:true
			},
			fechaExpedicionCadena:{
				required:true,
				validDate : true
			}
    						
			},
 		messages: { 
 			nombreEscuela:{
				required:"Obligatorio"
			},			
    		noIncorporacion:{
				required:"Obligatorio"
			},
			claveEscuela:{
				required:"Obligatorio"
			},
			fechaInicio:{
				required:"Obligatorio",
				validDate : "Fecha inv\u00e1lida"
			},
			fechaFin:{
				required:"Obligatorio",
				validDate : "Fecha inv\u00e1lida"
			},
			gradoEscolar:{
				required:"Obligatorio"
			},
			fechaExpedicionCadena:{
				required:"Obligatorio",
				validDate : "Fecha inv\u00e1lida"
			}
 		} 
    });
  
  //fin de funcion ready
  });