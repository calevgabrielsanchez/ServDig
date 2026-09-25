/**
	 * FUncion para el procesamiento de errores cuando la peticion es asincrona
	 * @param data
	 * @param contenedor
	 */
	function fnProcesarErrores(data , contenedor){

		switch(data.status)
		{
		case 403:
			//La sesion expiro
			window.location.reload(true);
		  break;
		case 412:
			//Existen errores de captura
		  fnProcesarErroresDeCaptura(data, contenedor);
		  break;
		case 500:
			//Existen errores de negocio
			fnProcesarErrorNegocio(data, contenedor);
			break;
		  
		}
	}
	
	
	
	

/**
 * Funcion para mostrar los errores de captura 
 * (campos invalidos o vacios) cuando es una invocacion asincrona
 * y response con JSON.
 * @param data
 * @param contenedor
 */	
function fnProcesarErroresDeCaptura(data, contenedor){
	  var objErrores = jQuery.parseJSON(data.responseText);
	  var form = $(contenedor);
	 
	  for( index = 0 ; index < objErrores.erroresCaptura.length ; index ++){
		  var campo = objErrores.erroresCaptura[index].campo;
		  
		  //Si el nombre del campo contiene puntos debemos de escaparlos
		  campo = campo.replace(/\./g, "\\.");
		  
		  var mensaje = objErrores.erroresCaptura[index].mensaje;
		  var filtroCampoError = contenedor +' #'+campo +'Error';
		  var filtroInputError = contenedor +' #registro'+campo ;
		  fnShowError(   filtroCampoError , mensaje , filtroInputError);
	  }
	
}



/**
 * 
 * @param data
 * @param contenedor
 * @param campo
 */
function fnProcesarErrorNegocio (data, contenedor){
	
	var campo = 'errorNegocio';
	try{
	  var objError = jQuery.parseJSON(data.responseText);
            var mensaje = objError.erroresNegocio;
            var filtroCampoError = contenedor +' #'+campo +'Label';
            fnShowError(   filtroCampoError , mensaje  );
        }catch(err){
          var mensaje="Error interno";
          console.log(err);
        }
	
	
}

/**
 * 
 * @param mensaje
 * @param contenedor
 */
function fnSetErrorNegocio( mensaje, contenedor){
	var campo = 'errorNegocio';
	 var filtroCampoError = contenedor +' #'+campo +'Label';
	  fnShowError(   filtroCampoError , mensaje  );
	
}

var nClassShow = 'showElement';
var nClassHidden ='hiddenElement';		

/**
 * Funcion para mostrar el error
 * @param idCampoError
 * @param mensajeError
 */
function fnShowError(idCampoError , mensajeError){
	$(idCampoError).removeClass(nClassHidden);
	$(idCampoError).addClass(nClassShow);
	$(idCampoError).text(mensajeError);
}

/**
 * Funcion para mostrar el error
 * @param idCampoError
 * @param mensajeError
 */
function fnShowError(idCampoError , mensajeError,idInputError){
	$(idCampoError).removeClass(nClassHidden);
	$(idCampoError).addClass(nClassShow);
	$(idInputError).css("border","1px solid red");
	$(idInputError).addClass("error");
	$(idCampoError).text(mensajeError);
}

	
/**
 * Funcion para ocultar los errores 
 * de un contenedor.
 * span con clase error
 * 
 * @param contenedor
 */
function fnHideErrores(contenedor){
	
	var filtroErrores;
	if(contenedor == ""){
		 filtroErrores ='span.error';
	}else{
		filtroErrores = contenedor + ' span.error';
	}
	
	
	$(filtroErrores).each(function(index) {
	    
		$(this).removeClass(nClassShow);
		$(this).addClass(nClassHidden);
		$(this).text();
	});
}

/**
 * Funcion para ocultar los errores 
 * de un contenedor.
 * span con clase error
 * 
 * @param contenedor
 */
function fnHideErroresInput(contenedor){
	
	var filtroErrores;
	if(contenedor == ""){
		 filtroErrores ='input.error';
	}else{
		filtroErrores = contenedor + ' input.error';
	}
	
	$(filtroErrores).each(function(index) {	  
		$(this).removeClass("error");  
		$(this).removeAttr("style");
	});
}
	

/**
 * Funcion para mostrar el error
 * @param idCampoError
 * @param mensajeError
 */
function fnShowElement(element){
	
	$(element).removeClass(nClassHidden);
	$(element).addClass(nClassShow);
}
	
/**
 * Funcion para ocultar los errores 
 * de un contenedor.
 * span con clase error
 * 
 * @param contenedor
 */
function fnHideElement(element){
	    
		$(element).removeClass(nClassShow);
		$(element).addClass(nClassHidden);
}
	