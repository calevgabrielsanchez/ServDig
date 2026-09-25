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




