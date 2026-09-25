
/** JS para las cedulas de construccion  */

$(document).ready(function() {
	
	var validaCaptura = $("#monitoreoMainForm").validate({
		  rules: {
			  folioCorreccion: {
		      required: true,
		      alphanumeric: true
		  },
		    
		  },
		messages:{
			folioCorreccion:"Se requiere Ingresar un Folio de Correccion"
		  }
	});
	
	
	

	$("a#btnMonitoreo").click(function(event){
		event.preventDefault();
		var contexto = $('#idContexto').val();
		if(validaCaptura.form())
		{
			
			bloquear();
			document.forms[0].action = contexto + "/cedulasCorreccion/monitor/monitor.do";
			document.forms[0].submit();

		}
		
	
     });



		
});//$(document).ready(function()

