
/** JS para las cedulas de construccion  */

$(document).ready(function() {
	

	var strMensaje = $('#idMensaje').val(); 

	if(strMensaje!='null')
		alert(strMensaje);
	

	var validaCaptura = $("#cargaMainForm").validate({
		  rules: {
			  folioCorreccion: {
		      required: true,
		      alphanumeric: true
		  },
		  fileData: {
	 		  required: true,
	 		  alphanumeric: true
		  },
		  idArchivoCarga: {
	 		  required: true
		  }
	 		  
		  },
		messages:{
			folioCorreccion:"Se requiere Ingresar un Folio de Correccion",
			fileData:"Se requiere el Archivo",
			idArchivoCarga:"Se requiere el tipo de Cedula"
		  }
	});
	

	$("a#btnCargar").click(function(event){
		event.preventDefault();
		if(validaCaptura.form())
		{
			var contexto = $('#idContexto').val();
			var idFolio = $('#folioCorreccion').val();
			var sFolio= '{"folioCorreccion":'+'"'+idFolio+'"}';
			var uploadItem = jQuery.parseJSON(sFolio);
			bloquear();
			$.postJSON(contexto +"/cedulasCorreccion/carga/validar.do", uploadItem, function(data) {
				if(data==null){
				alert("El Folio de Correccion no existe")	}
				else{
					document.forms[0].action = contexto + "/cedulasCorreccion/carga/archivo.do";
					document.forms[0].submit();
				}
			}).error(function(data){ 
				alert("error" + data);
			}).complete(function(){
				//Instrucciones para el 'complete'
				
			});	
		}
		
});



		
});//$(document).ready(function()

