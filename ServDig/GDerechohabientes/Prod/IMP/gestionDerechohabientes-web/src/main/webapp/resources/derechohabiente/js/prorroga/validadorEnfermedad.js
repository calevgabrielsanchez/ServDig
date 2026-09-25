 $(document).ready(function(){
  //inicio de funcion ready
    $("#frmRegistroProrroga").validate({
    	rules:{ 
    		fechaInicio: {
					required:true,
					validDate : true
				},
				fechaFin:{
					required:true,
					validDate : true,
					fechaMayorQue : "#fechaInicio"
				}			
			},
 		messages: { 
 			fechaInicio: {
				required:"Obligatorio",
				validDate : "Fecha inv\u00e1lida"
				},
			fechaFin: {
				required:"Obligatorio",
				validDate : "Fecha inv\u00e1lida",
				fechaMayorQue : "La fecha de t\u00e9rmino debe ser mayor o igual que la fecha de inicio y/o mayor o igual al dia de hoy."
				}
			} 
    });
  
  //fin de funcion ready
  });