 $(document).ready(function(){
  //inicio de funcion ready
    $("#frmRegistroProrroga").validate({
    	rules:{ 
    	
				observaciones:{
					required:true
				}
			},
 		messages: { 
 			
				observaciones:{
					required:"Obligatorio"
				}
		} 
    });
  
  //fin de funcion ready
  });
 
 