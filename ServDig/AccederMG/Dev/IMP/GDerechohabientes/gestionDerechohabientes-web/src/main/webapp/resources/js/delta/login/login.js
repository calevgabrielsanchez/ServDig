
	$(document).ready(function() {  
		$("#formlogin").validate({ 

			rules: { 

				password: {
					required:true,
	
				}, 
				usuario: {
					required:true,
	
				},

				
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 

			password: {required:"Obligatorio"},
			usuario: {required:"Obligatorio"}

 
		} 

	}); 
}); 
	


