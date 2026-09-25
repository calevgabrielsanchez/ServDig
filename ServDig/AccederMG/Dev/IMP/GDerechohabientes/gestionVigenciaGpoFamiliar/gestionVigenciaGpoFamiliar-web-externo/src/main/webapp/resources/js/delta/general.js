/*!
 * general.js
 * 
 * Archivo javascript que debera contener las funciones que son
 * generales a los largo de todo el sistema o modulo.
 * 
 */

var idDialogoCerrarSesion = "#dgCerrarSesion";
var oDialogoCerrarSesion;


/*Seccion de codigo a ejecutar en cuanto el
 * DOM envie la señar de que esta listo para procesar 
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

/* Función para ajustar en automático el tamaño del iframe
 * de acuerdo a su contenido, recibe el id del iframe.
 * Para Internet Explorer busca un div con la clase 
 * .site_position_center, este div es el que engloba 
 * todo el contendio.
 */
function set_size(elemento) {
	var rootElement;
	var maxHeight = 0;
	var incrementoHeight = 0;
	
	if ($.browser.msie) {
		rootElement = ".site_position_center";
		incrementoHeight = 60;
	} else {
		rootElement = "html";
		incrementoHeight = 20;
	}

	if ($("#" + elemento).contents().find("html").height() != 0) {
		
		maxHeight = $("#" + elemento).contents().find(rootElement).height();
		
		maxHeight += incrementoHeight;
		
		$("#" + elemento).css('height', maxHeight + 'px');
	}

}