
var URL_ACTION_DOCUMENTOS="/visor/adjuntarArchivos.do";
$(document).ready(function(){
	
	upclick(generaObjetoArchivo('anexaDocumentos',getFuncionValida(),URL_ACTION_DOCUMENTOS));
	
});


//funcion generica que genera la estructura del objeto para el envio del archivo, recibe el elemento(boton) y la funcion que valida el envio del archivo
function generaObjetoArchivo(elemento,funcionValida,url,cajaTexto){
	var archivo={
		      element: document.getElementById(elemento),
			  action: getAppContextParaJS() + url, 
		      valida:funcionValida,
		      onstart:
		        function(filename){	 		    	  
		        },
		      oncomplete:
		        function(response_data){
		    	  if((response_data+'').indexOf("Error")>=0 && (response_data+'').length>50){
		    		   alert("No se pudo adjuntar el documento,favor de reintentar");
		    	  }else if((response_data+'').indexOf("ERROR")>=0){
		    		  alert(response_data);
		    	  }else{
		    		 alert("Archivo "+response_data+" adjuntado exitosamente  ");			    		 
		    	  }
		        }
		     };
	return archivo;
}






// funcion generica que construye una funcion para validar los componentes requeridos de un denunciante
function getFuncionValida(idCampo){
	   var func=function(form){		   				
		   				if($("#idFolio").val()!="" && $( "input:checked" ).val() != undefined){
		   					var tramitePresentado = new Object();
		   					tramitePresentado.nuFolio = $("#idFolio").val();
		   					tramitePresentado.cveTramite = $("input:checked").val();
		   					
		   					var idTramite = 0;
		   					
			   					$.postJSON_Sync("visor/buscaTramitePresentado.do", tramitePresentado, function(data){
			   						var n=(form.action).split("?");
									form.action=n[0];
									form.action=form.action+"?idTramite="+data.idTramiteRefNotaria;
									form.submit();
			   					});
		   					}else{
		   						alert("Favor ingresar folio y tipo de tr\u00e1mite")
		   					}
				}		
	   return func;
}




