 $(document).ready(function(){
  //inicio de funcion ready
    $("#frmRegistroProrroga").validate({
    	rules:{
    		fechaExpedicionCadena: {
				required:true
			},
    		noFolio: {
				required:true
			}				
			},
 		messages: { 
 			fechaExpedicionCadena: {
				required:"Obligatorio"
			},
    		noFolio: {
				required:"Obligatorio"
			}
		} 
    });
  
  //fin de funcion ready
  });