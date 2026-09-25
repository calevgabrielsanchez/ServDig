/*!
 * general.js
 * 
 * Archivo javascript que debera contener las funciones que son
 * generales a los largo de todo el sistema o modulo.
 * 
 */

var idDialogoCerrarSesion = "#dgCerrarSesion";
var oDialogoCerrarSesion;

var pDialogHeigthRepresentanteLegal = 550;
var pDialogWidthRepresentanteLegal = 550;

var nClassShow = 'showElement';
var nClassHidden ='hiddenElement';	


/*Seccion de codigo a ejecutar en cuanto el
 * DOM envie la señar de que esta listo para procesar 
 * de modificaciones al DOM
 */
$(document).ready(function(){
	$('input.numerico').keypress(function(event) {
    	
		var charAt = String.fromCharCode(event.which);
		var characterReg = /\d{1}/;
        if(!characterReg.test(charAt)){
            return event.preventDefault();
        }
        
    });
	
	$('input.rfc').keypress(function(event) {
    	
		var charAt = String.fromCharCode(event.which);
		//var characterReg = /[\w\&]{1}/;
		var characterReg = /^[a-zA-Z]{3,4}(\d{6})((\D|\d){3})?$/;
        if(!characterReg.test(charAt)){
            return event.preventDefault();
        }
        
        // solucionar el puto & para no tener que hacer esto
        if(event.which == 38){
        	if($(this).val().length < 12 && $(this).val().length < 13){
        		$(this).val($(this).val() + "&");
        	}
        }
        
    });
	
	$('input.alfanumerico').keypress(function(event) {
		var charAt = String.fromCharCode(event.which);
		//var characterReg = /[\w\&]{1}/;
		var characterReg = /^[a-zA-Z\d]?$/;
        if(!characterReg.test(charAt)){
            return event.preventDefault();
        }
    });
	
	
	/*Configuracion de la funcion de submit de la forma del
	 * registro de nuevo usuario*/
	
	$('#formRegistroUsuarioNuevo').submit(function(){
		fnOpenRegistroNuevoUsuario();
		return false;
	});
	
	/*Inicializacion del dialogo
	 * de confirmacion de cerrar sesion*/
	 oDialogoCerrarSesion = 	$( idDialogoCerrarSesion ).dialog({
	        autoOpen:false,
	        resizable: false,
	        height:150,
	        modal: true,
	        buttons: {
	            "Aceptar": function(data) {
	            	$("#formCerrarSesion").submit();
	            },
	            'Cancelar': function() {
	                $( this ).dialog( "close" );
	            }
	        }
	    });
});



/*Funcion para abrir el dialogo cerrar sesion*/
var fnAbrirDialogoCerrarSesion = function(){
	oDialogoCerrarSesion.dialog('open');
}


/*Funcion que abre la ventana alterna 
 * para el registro de usuario*/
var fnOpenRegistroNuevoUsuario = function(){
	var oSendData = new Object();
	var sUrl ="/gestionIndividuo-web/persona/fisica/registro/1/";
	var oReturn = window.showModalDialog( sUrl  , oSendData, 
	   "dialogWidth:1000px;dialogHeight:900px;status=yes,toolbar=no,menubar=no,location=no");
	
	
	
}







/**
 * Funcion para limpiar los campos de busqueda
 */		 
	$.fn.clearForm = function() {
    return this.each(function() {
        $('input,select,textarea', this).clearFields();
    });
};


/**
 * Clears the selected form elements.
 */
$.fn.clearFields = $.fn.clearInputs = function() {
    return this.each(function() {
        var t = this.type, tag = this.tagName.toLowerCase();
        if (t == 'text' || t == 'password' || tag == 'textarea')
            this.value = '';
        else if (t == 'checkbox' || t == 'radio')
            this.checked = false;
        else if (tag == 'select'){
            this.selectedIndex = 0;
        }
    });
};



function limpiarFormulario(idForm){
   
    $(idForm).clearForm();
};

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
		  var campo = objErrores.erroresCaptura[index].campo.replace(/\./g,"\\.");
		  var mensaje = objErrores.erroresCaptura[index].mensaje;
		  var filtroCampoError = contenedor +' #'+campo +'Error';
		  fnShowError(   filtroCampoError , mensaje  );
	  }
	
}

/**
 * Funcion para mostrar los errores de negocio
 * @param data
 * @param contenedor
 * @param campo
 */
function fnProcesarErrorNegocio (data, contenedor){
	
	var campo = 'errorNegocio';
	
	var objError = jQuery.parseJSON(data.responseText);
	var mensaje = objError.erroresNegocio;
	  var filtroCampoError = contenedor +' #'+campo +'Label';
          
	  fnShowError(   filtroCampoError , mensaje  );
	
}

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

/*FUncion que valida que se encuentre seleccionado al menos un radio*/

var fnValidaRegistroSeleccionado = function( oTableLocal ){
		
		
    var obRowSelected = fnGetRowSelected(oTableLocal);
		
    if(obRowSelected == null){
        return false;
    }else{
        return true;
    }
	
};

/* Obtiene la fila seleccionada */
function fnGetRowSelected( oTableLocal )
{
    var obRowSelected = null;
		
    $(oTableLocal.fnSettings().aoData).each(function (){
        if( $(this.nTr).hasClass('row_selected')){
            obRowSelected = this._aData;
        }
    });
		
    return obRowSelected;
		
};