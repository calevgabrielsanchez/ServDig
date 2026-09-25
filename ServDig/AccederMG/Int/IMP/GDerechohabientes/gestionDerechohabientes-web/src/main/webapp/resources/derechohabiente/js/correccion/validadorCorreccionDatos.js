 $(document).ready(function(){
	 
	 $.validator.addMethod("alphanumeric", function(value, element) { 
	        return this.optional(element) || /^[a-z0-9\-]+$/i.test(value); 
	    }, "Username must contain only letters, numbers, or dashes."); 

  //inicio de funcion ready
    $("#correccionDatos").validate({
    	
    });
  
  //fin de funcion ready
  });
 	