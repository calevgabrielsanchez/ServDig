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
	$('input.numerico').keypress(function(event) {
		var charAt = String.fromCharCode(event.which);
		var characterReg = /\d{1}/;
		if (!characterReg.test(charAt)) {
			return event.preventDefault();
		}
	});

	$('input:not(.numerico)').keypress(function(event) {
		var charAt = String.fromCharCode(event.which);
		var characterReg = /[\w\u00d1\u00f1,\s\/]{1}/;
		if (!characterReg.test(charAt)) {
			return event.preventDefault();
		}
	});
	
	/*
	 * Configuracion de la funcion de submit de la forma del registro de nuevo
	 * usuario
	 */
	
	$('#formRegistroUsuarioNuevo').submit(function(){
		fnOpenRegistroNuevoUsuario();
		return false;
	});
	
	/*Inicializacion del dialogo
	 * de confirmacion de cerrar sesion*/
	 oDialogoCerrarSesion = 	$( idDialogoCerrarSesion ).dialog({
	        autoOpen:false,
	        resizable: false,
	        height:'auto',
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
	  * Inclusion de la funcionalidad de mostrar el dialogo de procesando...
	  */

	$loaderDiv = $('<div id="loader"><img src="/gestionAsegurados-web/static/resources/imagenes/loading.gif" /></div>');

	$loaderDiv.dialog({
		autoOpen : false,
		position : "center",
		stack : true,
		title : "Cargando...",
		modal : true,
		height : 150,
		width : 200,
		resizable : false
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	// $loaderDiv.dialog("moveToTop");

	$loaderDiv.html('<center><div><img src="/gestionAsegurados-web/static/resources/imagenes/loading.gif" /><div><br><div> Espere un momento por favor.</div></center>');
	$('#loader').hide();
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




