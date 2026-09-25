

$(document).ready(function() {
	
     
	recuperaPregunta();
	
	$("#btnRecuperarConf").click(function(e){
		
		var email = $('input#desMail').val();
		var respuesta = $('input#respuesta').val();
		
		var sUsuario = '{' +
		'"desRespuesta" : "'+respuesta+'",'+
		'"desEmail": "'+email+'"}';
		
		var usuario = jQuery.parseJSON(sUsuario);
		
		 $.postJSON(getAppContextParaJS() +"/denuncia/recuperarContrasena.do", usuario, function(data) {
			 	if(data==2){
			 		alert('La respuesta proporcionada no coincide con la registrada en el sistema para el correo electr\u00f3nico registrado.');
			 	}else if(data==3){
			 		alert('Su contrase\u00f1a ha sido enviada a la direcci\u00f3n de correo electr\u00f3nico que proporcion\u00f3 al registrarse.');
			 		$("#irLoginForm").submit(); 
			 	}
			 });					
	
});
	
	
}); 


function recuperaPregunta(){
	  var sMail = $("#desMail").val();
		var sUsuario = '{' +
		'"desEmail" : "'+sMail+'"}';
	  var mail = jQuery.parseJSON(sUsuario);
	  $.postJSON(getAppContextParaJS() +"/denuncia/consultaUsuario.do", mail, function(dataC) {
			 if(dataC!=null && dataC.cvePregunta>0){
				 var idPregunta = dataC.cvePregunta;
				 $.postJSON(getAppContextParaJS() +"/denuncia/obtenerPreguntasPorID.do",  idPregunta, function(dataP) {
					 if(dataP!=null){
						 $('input#desMail').prop("value", dataC.desEmail);
						 $('input#pregunta').prop("value", dataP[0].desPregunta);
					 }else{
						 $('input#pregunta').prop("value", ""); 
					 }
				 }).error(function(dataP){ 
				  
				 }).complete(function(){
					 // Instrucciones para el 'complete'
				 });
			 }else{
				 alert("El correo electronico no se encuentra registrado");
			 }
		  }).error(function(dataC){ 
			  
		  }).complete(function(){
			// Instrucciones para el 'complete'
		  });
}

function irInicio(){
	$("#wlForm").submit(); 
}