/*!
 * general.js
 * 
 * Archivo javascript que debera contener las funciones que son
 * generales a los largo de todo el sistema o modulo.
 * 
 */

var idDialogoCerrarSesion = "#dgCerrarSesion";
var oDialogoCerrarSesion;


var KEY_CODE_DEL = 8;
var KEY_TAB_SPACE = 9;


/*Seccion de codigo a ejecutar en cuanto el
 * DOM envie la se�ar de que esta listo para procesar 
 * de modificaciones al DOM
 */
$(document).ready(function(){
	
	
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
	 
	 
	 
	 /*
	  * Funcion para prevenir la captura 
	  * de caracteres especiales en los campos
	  * alfanumericos, se debe de colocar la 
	  * clase alfanumerico en los campos deseados 
	  * para validarlos correctamente
	  */

	 $("input.alfanumerico").keypress(function(event) {
	 	//Para borrar
	 	if(event.which == KEY_CODE_DEL ||event.which == KEY_TAB_SPACE){
	 		return true;
	 	}
	 	
	 	var charAt = String.fromCharCode(event.which);
	 	var characterReg = /^\s*[a-zA-Z0-9,\s//]+\s*$/;
	 	if(! characterReg.test(charAt)  ){
	 		return event.preventDefault();
	 	}
	 	        
	 });
	 /*
	  * Funcion para prevenir la captura 
	  * de caracteres  en los campos
	  * numericos, se debe de colocar la 
	  * clase numerico en los campos deseados 
	  * para validarlos correctamente
	  */
	 $("input.numerico").keypress(function(event) {
		 	//Para borrar
		 	if(event.which == KEY_CODE_DEL ||event.which == KEY_TAB_SPACE){
		 		return true;
		 	}
		 	
		 	var charAt = String.fromCharCode(event.which);
		 	var characterReg = /^\s*[0-9,\s//]+\s*$/;
		 	if(! characterReg.test(charAt)  ){
		 		return event.preventDefault();
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