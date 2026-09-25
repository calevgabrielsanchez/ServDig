
/** JS para las cedulas de construccion  */

var descargaCedula;

$(document).ready(function() {
	

	var validaCaptura = $("#descCedulaCorreccionForm").validate({
		  rules: {
			  folioCorreccion: {
		      required: true,
		      alphanumeric: true
		  }, periodo: {
	 		  required: true,
	 		  digits: true
		  },
		  idArchivoDescarga: {
	 		  required: true
		   }
		  },
		messages:{
			folioCorreccion:"Se requiere Ingresar un Folio de Correccion",
			periodo:"Se requiere ingresar el Periodo",
			idArchivoDescarga:"Se requiere el tipo de Cedula"
		  }
	});
	
	

	$("a#btnDescargar").click(function(event){
		event.preventDefault();
	
		
		
		
		if(validaCaptura.form())
		{
			var contexto = $('#idContexto').val();
			descargaCedula = $("#descCedulaCorreccionForm").serializeObject(true);	
			bloquear();
			$.postJSON(contexto +"/cedulasCorreccion/descarga/validar.do", descargaCedula, function(data) {
				if(data==null){
				alert("No se ha encontrado informacion asociada con el Numero de Folio")	}
				else{
					document.forms[0].action = contexto + "/cedulasCorreccion/descarga/archivo.do";
					document.forms[0].submit();
				}
			}).error(function(data){ 
				alert("error" + data);
			}).complete(function(){
				//Instrucciones para el 'complete'
				desbloquear();
			});	
		}
		
  });

	
});//$(document).ready(function()


